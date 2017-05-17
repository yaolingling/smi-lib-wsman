/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
/**
 * 
 */
package com.dell.isg.smi.wsman.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.entity.ResourceURIInfo;
import com.sun.ws.management.addressing.Addressing;

/**
 * @author rahman.muhammad
 *
 */
public class SetPowerCappingCmd extends WSManBaseCommand {

    private static final Logger logger = LoggerFactory.getLogger(SetPowerCappingCmd.class);
    private WSManageSession session = null;
    private ResourceURIInfo resourceUriInfo = null;


    public SetPowerCappingCmd(String ipAddr, String userName, String passwd) {
        super(ipAddr, userName, passwd);
        session = super.getSession();

    }


    @Override
    public Object execute() throws Exception {
        initCommand();
        Addressing response = session.sendInvokeRequest();
        return response;

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

        }

        session.setInvokeCommand(WSManMethodEnum.SET_ATTRIBUTES.toString());
        session.addUserParam(WSManMethodParamEnum.TARGET.toString(), "System.Embedded.1");
    }


    public void setResourceUriInfo(ResourceURIInfo resourceUriInfo) {
        this.resourceUriInfo = resourceUriInfo;
    }


    public void addAttributeKeyValue(String attributeName, String attributeValue) {
        this.session.addUserParam(WSManMethodParamEnum.ATTRIBUTE_NAME.toString(), attributeName);
        this.session.addUserParam(WSManMethodParamEnum.ATTRIBUTE_VALUE.toString(), attributeValue);

    }

}
