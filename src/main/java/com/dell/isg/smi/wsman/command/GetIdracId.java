/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dell.isg.smi.commons.elm.exception.RuntimeCoreException;
import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.sun.ws.management.addressing.Addressing;

public class GetIdracId extends WSManBaseCommand {

    WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(GetIdracId.class);


    public GetIdracId(String ipAddr, String userName, String passwd, String DRACPort, boolean bCertCheck) {
        super(ipAddr, userName, passwd, bCertCheck);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: GetIdracId(String ipAddr - %s, String userName - %s, String passwd - %s, String DRACPort - %s, boolean bCertCheck - %s)", ipAddr, userName, "####", DRACPort, Boolean.toString(bCertCheck)));
        }
        session = super.getSession();
        session.setResourceUri(getResourceURI());
        logger.trace("Exiting constructor: GetIdracId()");
    }


    private String getResourceURI() {

        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.CIM_ComputerSystem);
        return sb.toString();

    }


    @Override
    public Object execute() throws Exception {
        logger.trace("Entering function: execute()");
        Addressing response = null;
        try {
            response = session.sendRequestEnumeration(null);

        } catch (Exception e) {
            throw new RuntimeCoreException(e.getMessage(), e);
        }

        logger.trace("Exiting function: execute()");
        return response;
    }

}
