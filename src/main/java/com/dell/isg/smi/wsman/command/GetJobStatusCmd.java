/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.dell.isg.smi.commons.elm.CommonConstants;
import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.entity.LifeCycleJob;
import com.sun.ws.management.addressing.Addressing;

public class GetJobStatusCmd extends UpdateBaseCmd {
    // private static String resourceUri ="http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/root/dcim/DCIM_LifecycleJob";
    private WSManageSession session = null;
    static int MAX_RETRY = 20;

    private static final Logger logger = LoggerFactory.getLogger(GetJobStatusCmd.class);


    public GetJobStatusCmd(String DRACIP, int DRACPort, String DRACUser, String DRACPassword, String jobID, boolean bCertCheck) {

        // set the WSMan Session
        super(DRACIP, DRACPort, DRACUser, DRACPassword, bCertCheck);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: GetJobStatusCmd(String DRACIP - %s, int DRACPort - %s, String DRACUser - %s, String DRACPassword - %s, String jobID - %s, boolean bCertCheck - %s)", DRACIP, DRACPort, DRACUser, "####", jobID, Boolean.toString(bCertCheck)));
        }
        session = super.getSession();
        session.setResourceUri(getResourceURI());
        session.addSelector("InstanceID", jobID);
        logger.trace("Exiting constructor: GetJobStatusCmd()");

    }


    public String getResourceURI() {

        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_LifecycleJob);
        return sb.toString();
    }


    @Override
    public Object execute() throws Exception {
        logger.trace("Entering function: execute()");
        Addressing returnDoc = null;
        int retryCount = 1;
        Exception e = null;
        while (retryCount <= MAX_RETRY) {
            try {
                logger.info("Sending get request to get jobstatus ... try = " + retryCount);
                returnDoc = session.sendGetRequest();
                e = null;
                if (returnDoc != null) {
                    break;
                }
            } catch (Exception ex) {
                logger.error(ex.getMessage());
                e = ex;
            }
            // catch(IOException ex){
            // logger.error(ex);
            // e = ex;
            // }
            retryCount++;
            Thread.sleep(CommonConstants.THIRTY_SEC);

        }

        if (e != null) {
            throw e;
        }
        if (returnDoc != null) {
            Document doc = returnDoc.getBody().extractContentAsDocument();
            if (doc != null) {
                LifeCycleJob lcj = getJobStatus(doc.getFirstChild());
                logger.trace("Exiting function: execute()");
                return lcj;
            } else {
                logger.trace("Exiting function: execute()");
                return null;
            }
        } else {
            logger.trace("Exiting function: execute()");
            return null;
        }
    }
}
