/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;

public class EnumerateSystemChassis extends WSManBaseCommand {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(EnumerateSystemChassis.class);


    public EnumerateSystemChassis(String ipAddr, String userName, String passwd) {
        super(ipAddr, userName, passwd);

        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: EnumerateSystemChassis(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        session = this.getSession();

        session.setResourceUri(getResourceURI());

        logger.trace("Exiting constructor: EnumerateSystemChassis()");

    }


    private String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.CIM_Chassis);

        return sb.toString();
    }


    @Override
    public Object execute() throws Exception {
        logger.trace("Entering function: execute()");

        NodeList nodeList = this.sendRequestEnumerationReturnNodeList();

        String serverModel = "";

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Element.ELEMENT_NODE) {
                if (node.hasChildNodes()) {

                    NodeList itemNodeList = node.getChildNodes();

                    for (int index = 0; index < itemNodeList.getLength(); index++) {
                        NodeList innerNodeList = itemNodeList.item(index).getChildNodes();
                        System.out.println(innerNodeList.getLength());
                        Node tmp = null;
                        for (int j = 0; j < innerNodeList.getLength(); j++) {
                            Node tmpNode = innerNodeList.item(j);
                            if (tmpNode.getLocalName().equalsIgnoreCase("Model")) {
                                serverModel = tmpNode.getChildNodes().item(0).getNodeValue();
                                break;
                            }
                        }
                    }
                }
            }
        }

        logger.trace("Exiting function: execute()");
        return serverModel;
    }

}
