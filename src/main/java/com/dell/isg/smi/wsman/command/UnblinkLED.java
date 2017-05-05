/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import javax.xml.xpath.XPathConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dell.isg.smi.wsmanclient.WSManConstants;
import com.dell.isg.smi.wsmanclient.util.WSManUtils;
import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.entity.ResourceURIInfo;
import com.sun.ws.management.addressing.Addressing;

public class UnblinkLED extends WSManBaseCommand {

    private static final Logger logger = LoggerFactory.getLogger(GetDeviceLicenseFileCmd.class);
    private WSManageSession session = null;
    private ResourceURIInfo resourceUriInfo = null;


    public UnblinkLED(String ipAddr, String userName, String passwd) {
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: UnBlinkLED(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        session = super.getSession();
        logger.trace("Exiting constructor: UnBlinkLED()");
    }


    @Override
    public Object execute() throws Exception {
        logger.info("Executing UnBlinkLED (DCIM_SystemManagementService/IdentifyChassis for iDRAC: " + session.getIpAddress());
        initCommand();
        Addressing response = session.sendInvokeRequest();
        String retValue = (String) WSManUtils.findObjectInDocument(response.getBody(), "//pre:IdentifyChassis_OUTPUT/pre:ReturnValue/text()", XPathConstants.STRING, WSManConstants.WSManClassEnum.DCIM_SystemManagementService);

        if (retValue.equals("0")) {
            return true;
        }

        return false;
    }


    private String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);

        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_SystemManagementService);

        return sb.toString();
    }


    public void initCommand() throws Exception {

        session.setResourceUri(getResourceURI());
        if (resourceUriInfo == null) {
            GetResourceURIInfoCmd cmd = new GetResourceURIInfoCmd(session.getIpAddress(), session.getUser(), session.getPassword(), session.isCertificateCheck(), WSManClassEnum.DCIM_SystemManagementService.toString());
            Object result = cmd.execute();
            if (result != null) {
                resourceUriInfo = (ResourceURIInfo) result;
            }
        }
        if (resourceUriInfo != null) {
            session.addSelector(WSManMethodParamEnum.CREATION_CLASS_NAME.toString(), resourceUriInfo.getCreationClassName());
            session.addSelector(WSManMethodParamEnum.SYSTEM_NAME.toString(), resourceUriInfo.getSystemName());
            session.addSelector(WSManMethodParamEnum.NAME.toString(), resourceUriInfo.getName());
            session.addSelector(WSManMethodParamEnum.SYSTEM_CLASS_NAME.toString(), resourceUriInfo.getSystemCreationClassName());
            session.addUserParam("IdentifyState", "0");

        }

        session.setInvokeCommand(WSManMethodEnum.BLINK_LED.toString());

    }


    public void setResourceUriInfo(ResourceURIInfo resourceUriInfo) {
        this.resourceUriInfo = resourceUriInfo;
    }

}
