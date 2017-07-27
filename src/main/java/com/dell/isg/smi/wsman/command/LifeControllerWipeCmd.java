/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.command.WaitForLC.WaitForLCResponse;
import com.dell.isg.smi.wsman.command.idraccmd.IdracJobStatusCheckCmd;
import com.dell.isg.smi.wsman.entity.ResourceURIInfo;
import com.dell.isg.smi.wsman.model.XmlConfig;
import com.dell.isg.smi.wsman.utilities.XMLTool;
import com.dell.isg.smi.wsmanclient.WSManClientFactory;

/**
 * @author Prashanth.Gowda
 *
 */

public class LifeControllerWipeCmd extends WSManBaseCommand {

    private static final Logger logger = LoggerFactory.getLogger(LifeControllerWipeCmd.class);
    private WSManageSession session = null;
    private ResourceURIInfo resourceUriInfo = null;
    private static final int TIME_DELAY_MILLISECONDS = 30000;
    private static final int POLL_JOB_RETRY = 12;

    public LifeControllerWipeCmd(String ipAddr, String userName, String passwd) throws Exception {
        super(ipAddr, userName, passwd);
        session = super.getSession();
        intiCommand();
        this.session.setInvokeCommand(WSManMethodEnum.LC_WIPE.toString());
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
    public XmlConfig execute() throws Exception {
        XmlConfig xmlConfigResponse = new XmlConfig();
        // wait for LC to be available
        WaitForLC waitForLC = new WaitForLC();
        com.dell.isg.smi.wsmanclient.IWSManClient client = WSManClientFactory.getClient(session.getIpAddress(), session.getUser(), session.getPassword());
        WaitForLCResponse waitForLCResponse = waitForLC.execute(client);
        if (waitForLCResponse != WaitForLCResponse.LC_AVAILABLE) {
            xmlConfigResponse.setResult(XmlConfig.JobStatus.FAILURE.toString());
            xmlConfigResponse.setMessage("LC is not available");
            return xmlConfigResponse;
        }

        logger.info("Entering execute(), LC_WIPE for server {} ", session.getIpAddress());
        String content = XMLTool.convertAddressingToString(session.sendInvokeRequest());
        String jobId = getUpdateJobID(content);
        logger.trace("Exiting function: LC_WIPE execute()");
        IdracJobStatusCheckCmd cmd = new IdracJobStatusCheckCmd(session.getIpAddress(), session.getUser(), session.getPassword(), jobId, TIME_DELAY_MILLISECONDS, POLL_JOB_RETRY);
        xmlConfigResponse = cmd.execute();
        return xmlConfigResponse;
    }


    private String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_LCService);
        return sb.toString();
    }
    
    public static void main(String[] args){
    	try {
			LifeControllerWipeCmd cmd = new LifeControllerWipeCmd("100.68.123.39","root","calvin");
			Object result = cmd.execute();
			System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
}
