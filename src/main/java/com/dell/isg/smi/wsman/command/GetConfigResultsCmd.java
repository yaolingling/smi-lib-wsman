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
import com.sun.ws.management.addressing.Addressing;

/**
 * 
 *
 */
public class GetConfigResultsCmd extends WSManBaseCommand {

	private WSManageSession session = null;
	private static final Logger logger = LoggerFactory.getLogger(GetConfigResultsCmd.class);

	public GetConfigResultsCmd(String ipAddr, String userName, String passwd, String jobId) {
		super(ipAddr, userName, passwd);
		if (logger.isTraceEnabled()) {
			logger.trace(String.format("Entering constructor: GetConfigResultsCmd (String hostName - %s, User - %s)",
					ipAddr, userName));
		}
		session = this.getSession();
		session.setResourceUri(getResourceURI());
		session.addSelector(WSCommandRNDConstant.INSTANCE_ID, WSCommandRNDConstant.DCIM_LCLOG);
		session.addSelector(WSCommandRNDConstant.CIM_NAMESPACE, WSCommandRNDConstant.osdsvcnamespace);
		session.setInvokeCommand(WSManMethodEnum.GET_CONFIG_RESULTS.toString());
		session.addUserParam("JobID", jobId);

		logger.trace("Exiting constructor: GetConfigResultsCmd()");
	}

	public String getResourceURI() {
		StringBuilder sb = new StringBuilder(WSCommandRNDConstant.osdsvcdellbaseuri);
		sb.append(WSManClassEnum.DCIM_LCRecordLog);
		return sb.toString();
	}

	@Override
	public Object execute() throws Exception {
		logger.trace("Entering function: execute()");
		Addressing doc = session.sendInvokeRequest();
		Document tempDoc = session.extractAddressBody(doc);
		String content = XmlHelper.convertDocumenttoString(tempDoc);
		String output  = content.toString().replaceAll("&gt;", ">");
		output = output.replaceAll("&lt;", "<");
		logger.trace("Exiting function: execute()");
		return output;
	}

}
