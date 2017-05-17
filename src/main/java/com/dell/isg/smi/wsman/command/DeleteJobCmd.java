/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import static com.dell.isg.smi.commons.utilities.constants.CommonConstants.FIVE_MIN;

import javax.xml.soap.SOAPBody;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.entity.LifeCycleJob;
import com.sun.ws.management.addressing.Addressing;

public class DeleteJobCmd extends WSManBaseCommand {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(DeleteJobCmd.class);

    private String jobIDToDelete = null;
    private boolean sleepAtClearAll = true;
    private boolean deleteAnyJob = false;


    public boolean isDeleteAnyJob() {
        return deleteAnyJob;
    }


    public void setDeleteAnyJob(boolean deleteAnyJob) {
        this.deleteAnyJob = deleteAnyJob;
    }


    public DeleteJobCmd(String ipAddr, String userName, String passwd, String jobIDToDelete) {
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: DeleteJobCmd(String ipAddr - %s, String userName - %s, String passwd - %s, String jobIDToDelete - %s)", ipAddr, userName, "####", jobIDToDelete));
        }
        this.jobIDToDelete = jobIDToDelete;

        session = super.getSession();
        session.setResourceUri(getResourceURI());
        this.addSelectors();
        session.setInvokeCommand(WSManMethodEnum.DELETE_JOB_QUEUE.toString());
        session.addUserParam("JobID", jobIDToDelete);
        sleepAtClearAll = true;

        logger.trace("Exiting constructor: DeleteJobCmd()");
    }


    public DeleteJobCmd(String ipAddr, String userName, String passwd, String jobIDToDelete, boolean sleepAtClearAll) {
        super(ipAddr, userName, passwd);
        logger.trace("Entering constructor: DeleteJobCmd(String ipAddr, String userName, String passwd,String jobIDToDelete, boolean sleepAtClearAll)");
        this.jobIDToDelete = jobIDToDelete;

        session = super.getSession();
        session.setResourceUri(getResourceURI());
        this.addSelectors();
        session.setInvokeCommand(WSManMethodEnum.DELETE_JOB_QUEUE.toString());
        session.addUserParam("JobID", jobIDToDelete);
        this.sleepAtClearAll = sleepAtClearAll;

        logger.trace("Exiting constructor: DeleteJobCmd()");
    }


    /**
     * This method will return the return value returned from the command. Output looks like below. <n1:DeleteJobQueue_OUTPUT> <n1:Message>The specified job was
     * deleted</n1:Message> <n1:MessageID>SUP020</n1:MessageID> <n1:ReturnValue>0</n1:ReturnValue> </n1:DeleteJobQueue_OUTPUT>
     * 
     */

    @Override
    public Integer execute() throws Exception {
        logger.trace("Entering function: execute()");

        if (!StringUtils.equalsIgnoreCase(jobIDToDelete, "JID_CLEARALL") && !deleteAnyJob) {

            GetJobStatusCmd jobStatusCmd = new GetJobStatusCmd(session.getIpAddress(), 443, session.getUser(), session.getPassword(), jobIDToDelete, false);
            LifeCycleJob lcj = (LifeCycleJob) jobStatusCmd.execute();
            if (lcj == null || lcj.getJobStatus() == null) {
                return 0; // assume LC has or will clean this one. Return success, can't delete jobs unless we know status (LC 1.5.1)
            }
            if (!(lcj.getJobStatus().equalsIgnoreCase("failed")) && !(lcj.getJobStatus().equalsIgnoreCase("completed with errors"))) {
                return 0; // return success, these jobs are cleaned up by LC (LC 1.5.1)
            } else {
                logger.debug("Deleting LC job: " + jobIDToDelete);
            }
        }

        Addressing response = session.sendInvokeRequest();

        // Get the jobID out of the response.
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();
        SOAPBody soapBody = response.getBody();

        String returnValue = xpath.evaluate("//*[local-name()='ReturnValue']", soapBody);

        if (!"0".equalsIgnoreCase(returnValue)) {
            if (this.getLCErrorStr() != null || this.getLCErrorCode() != null) {
                logger.error("WSMan command failed to delete job queue with return code: " + returnValue + ", error [" + this.getLCErrorCode() + ": " + this.getLCErrorStr() + "]");
            } else {
                logger.error("WSMan command failed to delete job queue with return code: " + returnValue);
            }
        }

        // Sleep for 5 minutes if it is CLEARALL command.
        if (StringUtils.equalsIgnoreCase(jobIDToDelete, "JID_CLEARALL")) {
            if (sleepAtClearAll) {
                this.waitForReadyStatus(FIVE_MIN); // wait for RS Status "READY"
                // Thread.sleep(SLEEP_IN_MILLIS*5);
            }
        }

        Integer returnVal = NumberUtils.toInt(returnValue);
        logger.trace("Exiting function: execute()");
        return returnVal;

    }

    public final String SYSTEM_CREATION_CLASSNAME = "SystemCreationClassName";
    public final String CREATION_CLASSNAME = "CreationClassName";
    public final String SYSTEM_NAME = "SystemName";
    public final String NAME = "Name";


    protected void addSelectors() {
        session.setResourceUri(getResourceURI());
        session.addSelector(this.CREATION_CLASSNAME, WSManClassEnum.DCIM_JobService.toString());
        session.addSelector(this.SYSTEM_NAME, "Idrac");
        session.addSelector(this.NAME, "JobService");
        session.addSelector(this.SYSTEM_CREATION_CLASSNAME, WSManClassEnum.DCIM_ComputerSystem.toString());
    }


    private String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);

        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_JobService);

        return sb.toString();
    }

}
