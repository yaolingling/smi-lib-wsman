/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.entity.ResourceURIInfo;
import com.dell.isg.smi.wsman.utilities.XMLTool;

/**
 * @author Prashanth.Gowda
 *
 */

public class ExportTechSupportReportCmd extends WSManBaseCommand {

    private static final Logger logger = LoggerFactory.getLogger(ExportTechSupportReportCmd.class);
    private WSManageSession session = null;
    private ResourceURIInfo resourceUriInfo = null;


    public ExportTechSupportReportCmd(String ipAddr, String userName, String passwd, String shareType, String shareName, String shareAddress, String shareUserName, String sharePassword) throws Exception {
        super(ipAddr, userName, passwd);
        session = super.getSession();
        intiCommand();
        session.addUserParam("DataSelectorArrayIn", "0");
        session.addUserParam("IPAddress", shareAddress);
        session.addUserParam("ShareName", shareName);
        session.addUserParam("ShareType", shareType);
        session.addUserParam("Username", shareUserName);
        session.addUserParam("Password", sharePassword);
        this.session.setInvokeCommand(WSManMethodEnum.EXPORT_TECH_SUPPORT_REPORT.toString());
    }


    public void intiCommand() throws Exception {
        session.setResourceUri(getResourceURI());
        if (resourceUriInfo == null) {
            GetResourceURIInfoCmd cmd = new GetResourceURIInfoCmd(session.getIpAddress(), session.getUser(), session.getPassword(), session.isCertificateCheck(), WSManClassEnum.DCIM_LCService.toString());
            Object result = cmd.execute();
            if (result != null) {
                resourceUriInfo = (ResourceURIInfo) result;
            }
        }
        session.addSelector(WSManMethodParamEnum.CREATION_CLASS_NAME.toString(), resourceUriInfo.getCreationClassName());
        session.addSelector(WSManMethodParamEnum.SYSTEM_NAME.toString(), resourceUriInfo.getSystemName());
        session.addSelector(WSManMethodParamEnum.NAME.toString(), resourceUriInfo.getName());
        session.addSelector(WSManMethodParamEnum.SYSTEM_CLASS_NAME.toString(), resourceUriInfo.getSystemCreationClassName());
    }


    @Override
    public String execute() throws Exception {
        logger.info("Entering execute(), export TSR into local appliance share for server {} ", session.getIpAddress());
        String content = XMLTool.convertAddressingToString(session.sendInvokeRequest());
        String jobId = getUpdateJobID(content);
        if (jobId == null) {
            logger.info("Unable to create job to export TSR into local appliance share for server {} ", session.getIpAddress());
        }
        logger.trace("Exiting function: execute()");
        return jobId;
    }


    private String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_LCService);
        return sb.toString();
    }
}
