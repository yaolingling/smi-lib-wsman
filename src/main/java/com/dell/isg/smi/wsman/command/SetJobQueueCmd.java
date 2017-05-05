/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dell.isg.smi.wsman.command;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.xpath.XPathExpressionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManageSession;

/**
 *
 * @author Rahman_Muhammad
 */
public class SetJobQueueCmd extends UpdateBaseCmd {

    private WSManageSession session = null;
    private int port;
    private static final Logger logger = LoggerFactory.getLogger(SetJobQueueCmd.class);


    // public static final String PREFIX = "n1";
    /**
     * 
     * @param DRACIP
     * @param DRACPort
     * @param DRACUser
     * @param DRACPassword
     * @param jobIDs
     * @throws SOAPException
     */
    public SetJobQueueCmd(String DRACIP, String DRACPort, String DRACUser, String DRACPassword, List<String> jobIDs) throws SOAPException {

        super(DRACIP, Integer.parseInt(DRACPort), DRACUser, DRACPassword, false);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: SetJobQueueCmd(String DRACIP - %s, String DRACPort - %s, String DRACUser - %s, String DRACPassword - %s, List<String> jobIDs - %s)", DRACIP, DRACPort, DRACUser, "####", jobIDs.toString()));
        }
        // set the WSMan Session
        session = this.getSession();
        session.setResourceUri(getResourceURI());
        session.addSelector("CreationClassName", WSManClassEnum.DCIM_JobService.toString());
        session.addSelector("SystemCreationClassName", WSManClassEnum.DCIM_ComputerSystem.toString());
        session.addSelector("SystemName", WSCommandRNDConstant.SYSTEM_NAME);
        session.addSelector("Name", WSCommandRNDConstant.JOB_SERVICE);

        SOAPFactory factory = SOAPFactory.newInstance();

        SOAPElement element = null;
        for (String id : jobIDs) {
            // element = factory.createElement("JobArray", PREFIX, getResourceURI());
            element = factory.createElement("JobArray");
            element.setValue(id);
            session.addUserParam(id, element);
        }

        // element = factory.createElement("StartTimeInterval", PREFIX, getResourceURI());
        element = factory.createElement("StartTimeInterval");
        element.setValue("TIME_NOW");
        session.addUserParam("StartTimeInterval", element);

        session.setInvokeCommand(WSManMethodEnum.SETUP_JOB_QUEUE.toString());
        logger.trace("Exiting constructor: SetJobQueueCmd()");

    }


    private String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);

        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_JobService);

        return sb.toString();
    }


    @Override
    public String execute() throws IllegalArgumentException, SOAPException, JAXBException, DatatypeConfigurationException, IOException, XPathExpressionException {
        logger.trace("Entering function: execute()");
        String result = session.sendInvokeRequest().toString();
        if (result != null) {
            if (result.toLowerCase().contains("ReturnValue>0".toLowerCase())) {
                result = "0";
            } else {
                result = this.getLCErrorStr() + "@" + this.getLCErrorCode();
            }
        } else {
            result = "Failed scheduling update";
        }
        logger.trace("Exiting function: execute()");
        return result;

    }
}
