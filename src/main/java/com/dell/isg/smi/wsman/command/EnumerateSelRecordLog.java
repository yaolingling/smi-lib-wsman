/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;

public class EnumerateSelRecordLog extends WSManBaseCommand {
    private static final Logger logger = LoggerFactory.getLogger(EnumerateSelRecordLog.class);

    private WSManageSession session = null;


    public EnumerateSelRecordLog(String ipAddr, String userName, String passwd) {

        // set the WSMan Session
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: EnumerateSelRecordLog(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        session = this.getSession();
        session.setResourceUri(getResourceURI());

        logger.trace("Exiting constructor: EnumerateSelRecordLogCmd()");

    }


    @Override
    public String execute() throws Exception {

        logger.trace("Entering method execute() ");

        NodeList response = this.sendRequestEnumerationReturnNodeList();
        String instanceID = null;
        if (null != response) {

            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xpath = xPathFactory.newXPath();
            for (int i = 0; i < response.getLength(); i++) {
                Node itemNode = response.item(i);
                instanceID = xpath.evaluate("//*[local-name()='InstanceID']/text()", itemNode);

            }
        }

        logger.trace("Exiting method execute() ");

        return instanceID;
    }


    private String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_SelRecordLog);

        return sb.toString();
    }
}
