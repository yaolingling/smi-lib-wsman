/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.commons.utilities.xml.XmlHelper;
import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.command.entity.ipprotocolendpoint.CIMIPProtocolEndpointType;

public class EnumerateIPProtocolEndpoint extends WSManBaseCommand {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(EnumerateIPProtocolEndpoint.class);


    public EnumerateIPProtocolEndpoint(String ipAddr, String userName, String passwd) {
        super(ipAddr, userName, passwd);

        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: EnumerateIPProtocolEndpoint(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        session = this.getSession();

        session.setResourceUri(getResourceURI());

        logger.trace("Exiting constructor: EnumerateIPProtocolEndpoint()");

    }


    private String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.CIM_IPProtocolEndpoint);

        return sb.toString();
    }


    @Override
    public CIMIPProtocolEndpointType execute() throws Exception {
        logger.trace("Entering function: execute()");

        NodeList nodeList = this.sendRequestEnumerationReturnNodeList();

        CIMIPProtocolEndpointType ipprotocolType = null;

        if (nodeList != null) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node != null && node.getNodeType() == Element.ELEMENT_NODE) {
                    if (node.hasChildNodes()) {

                        NodeList ipProtocolEndpointList = node.getChildNodes();
                        if (ipProtocolEndpointList != null) {
                            for (int j = 0; j < ipProtocolEndpointList.getLength(); j++) {

                                Node ipprotocolTypeNode = ipProtocolEndpointList.item(j);
                                if (ipprotocolTypeNode != null && ipprotocolTypeNode.getLocalName() != null && ipprotocolTypeNode.getLocalName().equalsIgnoreCase("OMC_IPMIIPProtocolEndpoint")) {
                                    ipprotocolType = (CIMIPProtocolEndpointType) XmlHelper.xmlToObject(ipprotocolTypeNode, CIMIPProtocolEndpointType.class);
                                    logger.trace(String.format("iDRAC IP for host %s is %s", this.getSession().getIpAddress(), ipprotocolType));
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    logger.error("Cannot get IDrac IP from EnumerateIPProtocolEndpoint");
                }
            }
        } else {
            logger.error("Cannot get IDrac IP from EnumerateIPProtocolEndpoint as nodeList is null");
        }

        logger.trace("Exiting function: execute()");
        return ipprotocolType;
    }

}
