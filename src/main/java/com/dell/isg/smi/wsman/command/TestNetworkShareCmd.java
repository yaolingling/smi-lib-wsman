/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.dell.isg.smi.commons.utilities.xml.XmlHelper;
import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.entity.ResourceURIInfo;
import com.sun.ws.management.addressing.Addressing;

/**
 * @author Prashanth.Gowda
 *
 */

public class TestNetworkShareCmd extends WSManBaseCommand {

    private static final Logger logger = LoggerFactory.getLogger(TestNetworkShareCmd.class);
    private WSManageSession session = null;
    private ResourceURIInfo resourceUriInfo = null;

    public TestNetworkShareCmd(String ipAddr, String userName, String passwd, String shareType, String shareName, String shareAddress,String fileName,String shareUserName, String sharePassword) throws Exception {
        super(ipAddr, userName, passwd);
        session = super.getSession();
        intiCommand();
        session.addUserParam("FileName", fileName);
        session.addUserParam("IPAddress", shareAddress);
        session.addUserParam("ShareName", shareName);
        session.addUserParam("ShareType", shareType);
        session.addUserParam("Username", shareUserName);
        session.addUserParam("Password", sharePassword);
        this.session.setInvokeCommand(WSManMethodEnum.TEST_NETWORK_SHARE.toString());
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
		logger.info("Entering execute(), test share {} ", session.getIpAddress());
		Addressing doc = session.sendInvokeRequest();
		Document tempDoc = session.extractAddressBody(doc);
		String content = (String)XmlHelper.findObjectInDocument(tempDoc, "n1:ReturnValue");
		logger.trace("Exiting function: execute()");
		return content;
    }


    private String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_LCService);
        return sb.toString();
    }
    
    public static void main(String[] args){
    	try {
			TestNetworkShareCmd cmd = new TestNetworkShareCmd("100.68.123.39","root","calvin","0","100.68.124.32","/home/proxyserver/share","Clone.xml","","");
			Object result = cmd.execute();
			System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
}
