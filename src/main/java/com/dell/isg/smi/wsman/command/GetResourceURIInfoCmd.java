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
import com.dell.isg.smi.wsman.entity.ResourceURIInfo;

public class GetResourceURIInfoCmd extends WSManBaseCommand {

    private static final Logger logger = LoggerFactory.getLogger(GetResourceURIInfoCmd.class);
    private WSManageSession session = null;


    public GetResourceURIInfoCmd(String ipAddr, String userName, String passwd, boolean bCertCheck, String className) {

        super(ipAddr, userName, passwd);

        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: GetResourceURIInfoCmd(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        session = this.getSession();
        session.setResourceUri(getResourceURI(className));

        logger.trace("Exiting constructor: GetResourceURIInfoCmd()");

    }


    private String getResourceURI(String className) {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(className);

        return sb.toString();
    }


    // TODO Auto-generated constructor stub
    @Override
    public Object execute() throws Exception {
        logger.info("Executing the ResourceURIInfo command for iDRAC: " + session.getIpAddress());
        NodeList response = this.sendRequestEnumerationReturnNodeList();
        logger.info("Successfully executed the ResourceURIInfo command for iDRAC: " + session.getIpAddress());
        ResourceURIInfo info = new ResourceURIInfo();
        if (null != response) {
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xpath = xPathFactory.newXPath();

            for (int i = 0; i < response.getLength(); i++) {
                Node itemNode = response.item(i);
                if (itemNode != null) {
                    info.setCreationClassName(xpath.evaluate("//*[local-name()='CreationClassName']/text()", itemNode));
                    info.setSystemCreationClassName(xpath.evaluate("//*[local-name()='SystemCreationClassName']/text()", itemNode));
                    info.setSystemName(xpath.evaluate("//*[local-name()='SystemName']/text()", itemNode));
                    info.setName(xpath.evaluate("//*[local-name()='Name']/text()", itemNode));
                    info.setElementName(xpath.evaluate("//*[local-name()='ElementName']/text()", itemNode));
                    logger.info("Response found, returning the resource URI information");
                }
            }
        }
        return info;
    }

}
