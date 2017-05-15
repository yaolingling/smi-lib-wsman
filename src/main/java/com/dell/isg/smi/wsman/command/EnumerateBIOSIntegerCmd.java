/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.commons.utilities.xml.XmlHelper;
import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.command.entity.DCIMBIOSIntegerType;

public class EnumerateBIOSIntegerCmd extends WSManBaseCommand {
    private WSManageSession session = null;

    private static final Logger logger = LoggerFactory.getLogger(EnumerateBIOSIntegerCmd.class);


    // If the attributeName is null, all the Bios attributes will be returned . Otherwise
    // it returns only the attribute that is needed.
    public EnumerateBIOSIntegerCmd(String ipAddr, String userName, String passwd) {

        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: EnumerateBIOSIntegerCmd(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        session = super.getSession();
        session.setResourceUri(getResourceURI());

        logger.trace("Exiting constructor: EnumerateBIOSIntegerCmd()");

    }


    private String getResourceURI() {

        StringBuilder sb = new StringBuilder();
        sb.append(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE);
        sb.append(WSManClassEnum.DCIM_BIOSInteger);
        return sb.toString();
    }


    @Override
    public List<DCIMBIOSIntegerType> execute() throws Exception {
        logger.trace("Entering function: execute()");

        Document response = this.sendRequestEnumerationReturnDocument();

        List<DCIMBIOSIntegerType> integerTypeList = new ArrayList<DCIMBIOSIntegerType>();

        Element element = response.getDocumentElement();
        NodeList nodeList = element.getElementsByTagNameNS(WSCommandRNDConstant.WS_MAN_NAMESPACE, WSCommandRNDConstant.WSMAN_ITEMS_TAG);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Element.ELEMENT_NODE) {
                if (node.hasChildNodes()) {

                    NodeList biosIntegerNodeList = node.getChildNodes();
                    for (int j = 0; j < biosIntegerNodeList.getLength(); j++) {

                        Node biosIntegerNode = biosIntegerNodeList.item(j);

                        integerTypeList.add((DCIMBIOSIntegerType) XmlHelper.xmlToObject(biosIntegerNode, DCIMBIOSIntegerType.class));
                    }
                }
            }
        }

        logger.trace("Exiting function: execute()");
        return integerTypeList;
    }
}
