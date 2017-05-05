/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.command.entity.WsmanCredentials;

/**
 * 
 * @author Dan_Phelps
 *
 */
public class UnBlinkLEDCmd extends WSManBaseCommand {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(UnBlinkLEDCmd.class);

    private String chassisOID = null;


    public UnBlinkLEDCmd(String hostName, WsmanCredentials credentials) throws Exception {
        super(hostName, credentials);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: UnBlinkLEDCmd(String hostName - %s, WsmanCredentials credentials - %s)", hostName, WsmanCredentials.class.getName()));
        }
        session = this.getSession();

        chassisOID = new GetChassisOIDCmd(hostName, credentials).execute();

        session.setResourceUri(getResourceURI());
        session.addSelector("__cimnamespace", "root/dcim/sysman");
        session.addSelector("InstanceID", "DCIM_OEM_DataAccessModule1");
        session.addUserParam("CommandAndArguments", "omacmd=setchassidentify omausrinfo=administrator oid=" + chassisOID + " ChassIdentify=0 daname=hipda");
        session.setInvokeCommand("SendCmd");
        logger.trace("Exiting constructor: UnBlinkLEDCmd()");
    }


    public String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);

        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_OEM_DataAccessModule);

        return sb.toString();
    }


    @Override
    public Object execute() throws Exception {
        logger.trace("Entering function: execute()");
        session.sendInvokeRequest();
        logger.trace("Exiting function: execute()");
        return null;
    }

}
