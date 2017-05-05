/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dell.isg.smi.wsman.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.utilities.XMLTool;

/**
 *
 * @author Matthew_G_Stemen
 */
public class RebootCmd extends UpdateBaseCmd {

    private WSManageSession session = null;

    private static final Logger logger = LoggerFactory.getLogger(RebootCmd.class);


    public RebootCmd(String DRACIP, String DRACPort, String DRACUser, String DRACPassword) {
        // set the WSMan Session
        super(DRACIP, 443, DRACUser, DRACPassword, false);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering Constructor: RebootCmd(String DRACIP - %s, String DRACPort - %s, String DRACUser - %s, String DRACPassword - %s)", DRACIP, DRACPort, DRACUser, "####"));
        }
        session = this.getSession();
        session.setResourceUri(getResourceURI());
        session.addSelector("CreationClassName", WSManClassEnum.DCIM_SoftwareInstallationService.toString());
        session.addSelector("__cimnamespace", "root/dcim");
        session.addSelector("SystemCreationClassName", WSManClassEnum.DCIM_ComputerSystem.toString());
        session.addSelector("SystemName", "IDRAC:ID");
        session.addSelector("Name", "SoftwareUpdate");
        // session.addSelector("RebootJobType", "2");
        session.addUserParam("RebootJobType", "2");
        session.setInvokeCommand(WSManMethodEnum.CREATE_REBOOT_JOB.toString());
        // session.setInvokeCommand(WSManMethodEnum.SEND_CMD.toString());
        logger.trace("Exiting Constructor: RebootCmd()");

    }


    @Override
    public String execute() throws Exception {
        logger.trace("Entering function: execute()");
        String content = XMLTool.convertAddressingToString(session.sendInvokeRequest());
        String jobId = getUpdateJobID(content);
        logger.trace("Exiting function: execute()");
        return jobId;
        // return session.sendInvokeRequest().toString();
    }


    private String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);

        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_SoftwareInstallationService);

        return sb.toString();
    }


    public static void main(String[] args) {
        RebootCmd rebootCmd = new RebootCmd("10.36.0.177", "443", "root", "calvin");
        try {
            String rebootCmdRes = rebootCmd.execute();

            System.out.println(rebootCmdRes);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
