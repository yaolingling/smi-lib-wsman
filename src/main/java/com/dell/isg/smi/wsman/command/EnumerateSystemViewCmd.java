/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.commons.utilities.xml.XmlHelper;
import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManClientBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.command.entity.CimString;
import com.dell.isg.smi.wsman.command.entity.DCIMSystemViewType;
import com.dell.isg.smi.wsmanclient.util.WSManUtils;

public class EnumerateSystemViewCmd extends WSManClientBaseCommand<DCIMSystemViewType> {

    private static final Logger logger = LoggerFactory.getLogger(EnumerateSystemViewCmd.class);

    private WSManageSession session = null;

    private static final int MAX_RETRIES = 5;


    public EnumerateSystemViewCmd() {
    }


    public EnumerateSystemViewCmd(String ipAddr, String userName, String passwd) {

        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: EnumerateSystemViewCmd(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        session = super.getSession();
        session.setResourceUri(getResourceURI());
        logger.trace("Exiting constructor: EnumerateSystemViewCmd()");

    }


    public String getResourceURI() {
        StringBuilder sb = new StringBuilder();
        sb.append(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE);
        sb.append(WSManClassEnum.DCIM_SystemView);
        return sb.toString();
    }


    public DCIMSystemViewType execute() throws Exception {
        logger.trace("Entering function: execute()");
        for (int retry = 0; retry < MAX_RETRIES; retry++) {

            Document response = this.sendRequestEnumerationReturnDocument();
            logger.trace("Exiting function: execute()");
            return parse(response);
        }

        logger.info("Returning null ");
        logger.trace("Exiting function: execute()");
        return null;
    }


    public DCIMSystemViewType executeRaw() throws Exception {
        logger.trace("Entering function: executeRaw()");
        for (int retry = 0; retry < MAX_RETRIES; retry++) {

            Document response = this.sendRequestEnumerationReturnDocument();
            logger.trace("Exiting function: executeRaw()");
            return parse(response);
        }

        logger.info("Returning null ");
        logger.trace("Exiting function: executeRaw()");
        return null;
    }


    public DCIMSystemViewType parse(String xml) throws Exception {
        Document tempDoc = WSManUtils.toDocument(xml);
        return parse(tempDoc);
    }


    private DCIMSystemViewType parse(Document response) throws Exception {
        Element element = response.getDocumentElement();
        NodeList nodeList = element.getElementsByTagNameNS(WSCommandRNDConstant.WS_MAN_NAMESPACE, WSCommandRNDConstant.WSMAN_ITEMS_TAG);

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Element.ELEMENT_NODE) {
                if (node.hasChildNodes()) {

                    NodeList systemViewNodeList = node.getChildNodes();
                    for (int j = 0; j < systemViewNodeList.getLength(); j++) {

                        Node systemViewNode = systemViewNodeList.item(j);

                        DCIMSystemViewType systemView = (DCIMSystemViewType) XmlHelper.xmlToObject(systemViewNode, DCIMSystemViewType.class);

                        // Make sure that the data is returned from the iDRAC by checking at-least one property
                        // Servicetag should always come back with a value.
                        if (null != systemView && null != systemView.getServiceTag()) {
                            CimString cimString = systemView.getServiceTag();
                            if (StringUtils.isNotBlank(cimString.getValue()) && StringUtils.isNotEmpty(cimString.getValue())) {

                                logger.info("Returning the DCIMSystemViewType");
                                logger.trace("Exiting function: execute()");
                                return systemView;
                            } else if (null != systemView.getBiosVersionString()) { // In some cases Service tag is not reliable
                                return systemView;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}
