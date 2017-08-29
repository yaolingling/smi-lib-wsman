/**
 * Copyright � 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import static com.dell.isg.smi.commons.utilities.constants.CommonConstants.FIFTEEN_MIN;
import static com.dell.isg.smi.commons.utilities.constants.CommonConstants.FIVE_MIN;
import static com.dell.isg.smi.commons.utilities.constants.CommonConstants.TEN_MIN;

import java.util.ArrayList;
import java.util.List;

import javax.xml.soap.SOAPBody;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dell.isg.smi.commons.elm.exception.RuntimeCoreException;
import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManUtilities;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.command.entity.ConfigJobDetail;
import com.dell.isg.smi.wsman.utilities.ExceptionUtilities;
import com.sun.ws.management.addressing.Addressing;

public class CreateConfigJobCmd extends WSManBaseCommand {

    private static final Logger logger = LoggerFactory.getLogger(CreateConfigJobCmd.class);

    private WSManageSession session = null;

    private int MAX_RETRY_COUNT = 40;
    private int THIRTY_SEC = 30000;
    private boolean pollJobStatus = false;


    public CreateConfigJobCmd(String ipAddr, String userName, String passwd, boolean pollJobStatus, String target) {
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: CreateConfigJobCmd(String ipAddr - %s, String userName - %s, String passwd - %s, boolean pollJobStatus - %s)", ipAddr, userName, "####", Boolean.toString(pollJobStatus)));
        }
        session = super.getSession();
        session.setResourceUri(getResourceURI(target));
        this.pollJobStatus = pollJobStatus;

        this.addSelectors(getResourceURI(target));

        session.setInvokeCommand(WSManMethodEnum.CREATE_TARGET_CONFIG_JOB.toString());
        // session.addUserParam(WSManMethodParamEnum.TARGET.toString(), "BIOS.Setup.1-1");
        session.addUserParam(WSManMethodParamEnum.REBOOT_JOB_TYPE.toString(), "3");

        session.addUserParam(WSManMethodParamEnum.TARGET.toString(), target);
        session.addUserParam(WSManMethodParamEnum.SCHEDULED_START_TIME.toString(), "TIME_NOW");

        logger.trace("Exiting constructor: CreateConfigJobCmd()");

    }


    /**
     * This method returns a list containing the JobId and the ResourceURI if the pollJobstatus is set to false. If the pollJobStatus is set to true , it will return Boolean true
     * or false. This method will block until RS Status returns READY or timeout occurs.
     */
    @Override
    public ConfigJobDetail execute() throws Exception {
        logger.trace("Entering function: execute()");

        // Create the job
        ConfigJobDetail detail = new ConfigJobDetail();
        detail.setJobList(createConfigJob());
        List<String> jobIdURIList = detail.getJobList();

        if (pollJobStatus) {

            // Poll for the Job status
            // If 0 is the return code - it wouldn't have created any job
            // It would have an empty jobID and ResourceURI and hence no need to poll for the job.
            if ((jobIdURIList != null && jobIdURIList.size() > 0) && (StringUtils.isNotBlank(jobIdURIList.get(0)) && StringUtils.isNotEmpty(jobIdURIList.get(0))) && (StringUtils.isNotBlank(jobIdURIList.get(1)) && StringUtils.isNotEmpty(jobIdURIList.get(1)))) {

                // Poll the job for the status

                JobStatusCheckCmd jobChCmd = new JobStatusCheckCmd(this.session.getIpAddress(), this.session.getUser(), this.session.getPassword(), jobIdURIList.get(0), jobIdURIList.get(1), THIRTY_SEC, MAX_RETRY_COUNT);

                try {
                    jobChCmd.execute(); // this can only return "true" or throw exception
                } catch (RuntimeCoreException rse) {
                    if (rse.getErrorID() == 267068) { // if config job failed, wait for reboot/csior and delete failed job
                        if (WSManUtilities.is12g(session.getIpAddress(), session.getUser(), session.getPassword())) {
                            this.waitForReadyStatus(FIFTEEN_MIN, FIVE_MIN);
                        } else {
                            waitForReadyStatusPostCSIOR(FIFTEEN_MIN);
                        }
                        WSManUtilities.deleteJob(session.getIpAddress(), session.getUser(), session.getPassword(), jobIdURIList.get(0));
                    }
                    throw rse;
                }
                // if JobStatusCheckCmd returns success...
                if (WSManUtilities.is12g(session.getIpAddress(), session.getUser(), session.getPassword())) {
                    this.waitForReadyStatus(FIFTEEN_MIN, FIVE_MIN);
                } else {
                    waitForReadyStatusPostCSIOR(FIFTEEN_MIN);
                }
                detail.setReturnCode(ConfigJobDetail.ConfigJobReturnCode.JOB_COMPLETED);

            } else {
                // There is no need to poll the job - return true.
                detail.setReturnCode(ConfigJobDetail.ConfigJobReturnCode.NO_JOB_PENDING);
            }

        } else { // if the pollJobStatus is set to false , return the JObID and the URI List
            detail.setReturnCode(ConfigJobDetail.ConfigJobReturnCode.JOB_LIST);
        }

        logger.trace("Exiting function: execute()");
        return detail;
    }


    private List<String> createConfigJob() throws Exception {

        int attempts = 0;
        String messageFromConfigJob = null;
        List<String> returnList = new ArrayList<String>();
        int retries = 5;

        for (int i = 0; i < retries; i++) {
            attempts++;

            Addressing response = session.sendInvokeRequest();

            // Get the jobID out of the response.
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xpath = xPathFactory.newXPath();
            SOAPBody soapBody = response.getBody();

            String returnValue = xpath.evaluate("*[local-name()='CreateTargetedConfigJob_OUTPUT']/*[local-name()='ReturnValue']", soapBody);

            // Only if it is a Successful return - get the JOB-Id and return that
            // If the job is created - the return code would be 4096.
            // It would be 0 if there is no job created
            if (StringUtils.equalsIgnoreCase(returnValue, WSCommandRNDConstant.SUCCESSFULL_CONFIG_JOB_RETURN) || (StringUtils.equalsIgnoreCase(returnValue, WSCommandRNDConstant.SUCCESSFULL_UPDATE_JOB_RETURN))) {

                returnList.add(getJobID(soapBody, xpath));
                returnList.add(getResourceURI(soapBody, xpath));

                return returnList;

            } else {
                messageFromConfigJob = xpath.evaluate("*[local-name()='CreateTargetedConfigJob_OUTPUT']/*[local-name()='Message']", soapBody);
                logger.info("Received the message from createJob :: " + messageFromConfigJob);

                String LCErrorCode = session.getLCMessageID();
                if (LCErrorCode != null && !LCErrorCode.isEmpty()) {

                    if (WSManUtilities.noPendingDataErrorCodes.contains(LCErrorCode)) { // No pending data present to create a Configuration job
                        return returnList;
                    }
                    if (WSManUtilities.systemServicesInUseErrorCodes.contains(LCErrorCode)) { // System services is currently in use
                        this.waitForReadyStatus(TEN_MIN); // block until system services are available or timeout
                        logger.info("Retrying CreateBlockingConfigJob command...");
                        continue;
                    }
                    if (WSManUtilities.existingJobCompleteOrCancelErrorCodes.contains(LCErrorCode)) {
                        logger.error("Existing job must be completed or cancelled before creating new config job. No retry will be attempted. ");
                        break;
                    }
                }

                // if we have a job ID of a failed job, delete it.
                String jobId = getJobID(soapBody, xpath);
                if (jobId != null && !jobId.isEmpty()) {
                    logger.info("Deleting failed job " + jobId + " (with return code: " + returnValue + ")");
                    deleteJob(jobId);
                }

                logger.error("Retrying CreateConfigJob command...");
                Thread.sleep(THIRTY_SEC);
            }
        }

        logger.error("Completed " + retries + " attempts: Error while creating the reboot job :: " + messageFromConfigJob);
        RuntimeCoreException ex = ExceptionUtilities.getCoreRuntimeException(267065, messageFromConfigJob, this.getLCErrorCode(), this.getLCErrorStr());
        throw ex;

    }


    private int deleteJob(String jobID) {

        DeleteJobCmd deleteCmd = new DeleteJobCmd(this.session.getIpAddress(), this.session.getUser(), this.session.getPassword(), jobID);

        int deleteCmdReturn = 0;
        try {
            deleteCmdReturn = deleteCmd.execute().intValue();
        } catch (Exception e) {
            logger.info(e.getMessage());
        }

        return deleteCmdReturn;

    }


    private String getResourceURI(SOAPBody soapBody, XPath xpath) throws Exception {

        Object result = xpath.evaluate("//*[local-name()='ResourceURI']/text()", soapBody, XPathConstants.STRING);

        logger.info("Returning the ResourceURI :: " + (String) result);
        return (String) result;
    }


    private String getJobID(SOAPBody soapBody, XPath xpath) throws Exception {

        Object result = xpath.evaluate("//*[local-name()='Selector'][@Name='InstanceID']/text()", soapBody, XPathConstants.STRING);

        logger.info("Returning the jobID :: " + result);
        return (String) result;

    }


    private String getResourceURI(String target) {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        
        if (StringUtils.equals(target, "System.Embedded.1")) {
        	sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_SystemManagementService);
        } else if (StringUtils.equals(target, "BIOS.Setup.1-1")) {
        	sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_BIOSService);
        }    

        return sb.toString();
    }

}
