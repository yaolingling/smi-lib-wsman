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
public class BlinkLEDCmd extends WSManBaseCommand {

    private int timeout = 300;
    private String chassisOID = null;
    private String hostName = null;
    private WsmanCredentials credentials = null;
    private WSManageSession session = null;

    private static final Logger logger = LoggerFactory.getLogger(BlinkLEDCmd.class);


    public void setTimeout(int to) {
        timeout = to;
    }


    public int getTimeout() {
        return timeout;
    }


    public BlinkLEDCmd(String host, WsmanCredentials cred, int to) throws Exception {
        super(host, cred);

        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: BlinkLEDCmd(String host - %s, WsmanCredentials cred - %s, int to - %d)", host, WsmanCredentials.class.toString(), to));
        }

        timeout = to;
        session = this.getSession();

        // save these for creation of SetTimeout
        hostName = host;
        credentials = cred;

        // get chassis OID to use in WSMAN command to set timeout and blink LED
        // on host
        chassisOID = new GetChassisOIDCmd(hostName, credentials).execute();

        session.setResourceUri(getResourceURI());
        session.addSelector("__cimnamespace", "root/dcim/sysman");
        session.addSelector("InstanceID", "DCIM_OEM_DataAccessModule1");
        session.setInvokeCommand("SendCmd");
        session.addUserParam("CommandAndArguments", "omacmd=setchassidentify omausrinfo=administrator oid=" + chassisOID + " ChassIdentify=1 daname=hipda");
        logger.trace("Exiting constructor: BlinkLEDCmd()");
    }


    public String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);

        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_OEM_DataAccessModule);

        return sb.toString();
    }


    @Override
    public Object execute() throws Exception {
        logger.trace("Entering function: execute()");
        // set
        SetTimeoutCmd stc = new SetTimeoutCmd(hostName, credentials, timeout, chassisOID);
        stc.execute();
        // execute wsman command to blink LED
        super.getLogger(getClass()).info("Execute BlinkLEDCmd ");
        session.sendInvokeRequest();

        logger.trace("Exiting function: execute()");
        return null;
    }

}

class SetTimeoutCmd extends WSManBaseCommand {

    private int timeout = 300;
    private String chassisOID = null;
    private WSManageSession session = this.getSession();
    private static final Logger wsman_logger = LoggerFactory.getLogger(SetTimeoutCmd.class);


    public SetTimeoutCmd(String hostName, WsmanCredentials credentials, int to, String targetChassis) {
        super(hostName, credentials);
        wsman_logger.trace("Entering constructor: SetTimeoutCmd(String hostName, WsmanCredentials credentials, int to, String targetChassis)");
        timeout = to;
        chassisOID = targetChassis;
        this.session.setResourceUri(getResourceURI());
        this.session.addSelector("__cimnamespace", "root/dcim/sysman");
        this.session.addSelector("InstanceID", "DCIM_OEM_DataAccessModule1");
        this.session.setInvokeCommand("SendCmd");
        if (timeout > 0)
            this.session.addUserParam("CommandAndArguments", "omacmd=setchassidentify ChassIdentifyTimeout=" + timeout + " omausrinfo=administrator oid=" + chassisOID + " daname=hipda");
        else
            ;
        // FIX ME for ICEE
        // this.session.addUserParam("CommandAndArguments", "omacmd=setchassidentify ChassIdentifyTimeout=" + ConfigurationProperties.DEFAULT_BLINK_LED_TIMEOUT + "
        // omausrinfo=administrator oid=" + chassisOID + " daname=hipda");

        wsman_logger.trace("Exiting constructor: SetTimeoutCmd(String hostName, WsmanCredentials credentials, int to, String targetChassis)");
    }


    public String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);

        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_OEM_DataAccessModule);

        return sb.toString();
    }


    @Override
    public Object execute() throws Exception {
        super.getLogger(getClass()).info("Execute SetTimeoutCmd ");
        session.sendInvokeRequest();
        return null;
    }

}