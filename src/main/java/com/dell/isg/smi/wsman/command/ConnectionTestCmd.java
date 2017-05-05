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
import com.sun.ws.management.addressing.Addressing;

/**
 * 
 * @author Dan_Phelps
 *
 */
public class ConnectionTestCmd extends WSManBaseCommand {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(ConnectionTestCmd.class);


    public ConnectionTestCmd(String hostName, WsmanCredentials credentials) {
        super(hostName, credentials);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: ConnectionTestCmd(String hostName - %s, WsmanCredentials credentials - %s)", hostName, WsmanCredentials.class.getName()));
        }
        session = this.getSession();

        session.setResourceUri(getResourceURI());
        session.addSelector("__cimnamespace", "root/dcim/sysman");
        session.addSelector("InstanceID", "DCIM_OEM_DataAccessModule1");
        session.addUserParam("CommandAndArguments", "omacmd=getsysmgmtinfo daname=hipda");
        session.setInvokeCommand("SendCmd");

        logger.trace("Exiting constructor: ConnectionTestCmd()");

    }


    public String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);

        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_OEM_DataAccessModule);

        return sb.toString();
    }


    @Override
    public String execute() throws Exception {
        logger.trace("Entering function: execute()");

        Addressing doc = session.sendInvokeRequest();
        String getTo = doc.getTo();

        logger.trace("Exiting function: execute()");

        return getTo;
    }

}
