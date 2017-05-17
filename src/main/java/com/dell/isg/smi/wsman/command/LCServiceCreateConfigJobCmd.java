/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
/**
 * 
 */
package com.dell.isg.smi.wsman.command;

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
import com.dell.isg.smi.wsman.WSManageSession;
import com.sun.ws.management.addressing.Addressing;
import com.dell.isg.smi.commons.utilities.constants.CommonConstants;

/**
 * @author anthony_crouch
 *
 */
public class LCServiceCreateConfigJobCmd extends WSManBaseCommand {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(LCServiceCreateConfigJobCmd.class);

    private int MAX_RETRY_COUNT = 15;
    private int SLEEP_IN_MILLIS = 30000;


    public LCServiceCreateConfigJobCmd(String ipAddr, String userName, String passwd) {
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: LCServiceCreateConfigJobCmd(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, passwd));
        }
        session = super.getSession();
        session.setResourceUri(getResourceURI());

        this.addSelectors(getResourceURI());

        session.setInvokeCommand(WSManMethodEnum.CREATE_LC_SERVICE_CONFIG_JOB.toString());
        logger.trace("Exiting constructor: LCServiceCreateConfigJobCmd()");

    }


    @Override
    /**
     * This method returns a list containing the JobId and the ResourceURI if the pollJobstatus is set to false. If the pollJobStatus is set to true , it will return Boolean true
     * or false.
     */
    public Boolean execute() throws Exception {
        logger.trace("Entering function: execute()");

        logger.info("Entering createConfigJob.execute() ");

        // Create the job
        List<String> jobIdURIList = createConfigJob();

        if (jobIdURIList != null) {

            // Poll for the Job status
            if (jobIdURIList != null && jobIdURIList.size() > 0) {

                // Poll the job for the status
                JobStatusCheckCmd jobChCmd = new JobStatusCheckCmd(this.session.getIpAddress(), this.session.getUser(), this.session.getPassword(), jobIdURIList.get(0), jobIdURIList.get(1), SLEEP_IN_MILLIS, MAX_RETRY_COUNT);

                Object obj = jobChCmd.execute();

                if (Boolean.TRUE.toString().equals(obj.toString())) {
                    logger.trace("Exiting function: execute()");
                    return true;
                }
            }
        }
        // There is no need to poll the job - return true.
        logger.trace("Exiting function: execute()");
        return true;

    }


    private List<String> createConfigJob() throws Exception {

        String messageFromConfigJob = null;
        List<String> returnList = new ArrayList<String>();
        int retries = 5;

        for (int i = 0; i < retries; i++) {

            // first enumerate on the
            Addressing response = session.sendInvokeRequest();

            // Get the jobID out of the response.
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xpath = xPathFactory.newXPath();
            SOAPBody soapBody = response.getBody();

            String returnValue = xpath.evaluate("*[local-name()='CreateConfigJob_OUTPUT']/*[local-name()='ReturnValue']", soapBody);

            // Only if it is a Successful return - get the JOB-Id and return that
            if (StringUtils.equalsIgnoreCase(returnValue, "4096")) {

                returnList.add(getJobID(soapBody, xpath));
                returnList.add(getResourceURI(soapBody, xpath));

                return returnList;

            } else if (StringUtils.equalsIgnoreCase(returnValue, "0")) {
                return null;
            }
            logger.info("Retrying LCServiceCreateConfigJob command...");
            Thread.sleep(CommonConstants.THIRTY_SEC);
        }

        throw new RuntimeCoreException("Error while creating the reboot job :: " + messageFromConfigJob);

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


    private String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);

        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_LCService);

        return sb.toString();
    }
}
