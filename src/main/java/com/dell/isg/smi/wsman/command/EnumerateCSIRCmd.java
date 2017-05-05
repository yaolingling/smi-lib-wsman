/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.command.entity.InstanceCSIR;

public class EnumerateCSIRCmd extends WSManBaseCommand {

    private final static String CSIR_KEY = "Collect System Inventory on Restart";
    private final static String CSIR_KEY_VALUE = "CurrentValue";

    private InstanceCSIR data = new InstanceCSIR();
    private WSManageSession session = null;

    private static final Logger logger = LoggerFactory.getLogger(EnumerateCSIRCmd.class);


    /**
     * Default Constructor
     * 
     * @param ipAddr
     * @param userName
     * @param passwd
     * @param updateValue
     */
    public EnumerateCSIRCmd(String ipAddr, String userName, String passwd) {
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: EnumerateCSIRCmd(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        session = super.getSession();
        session.setResourceUri(getEnumResourceURI());

        logger.trace("Exiting constructor: EnumerateCSIRCmd()");
    }


    /**
     * Return Resource URI
     * 
     * @return String
     */
    private String getEnumResourceURI() {
        return "http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/root/dcim/DCIM_LCEnumeration";

    }


    /**
     * 
     * @throws Exception
     */
    public InstanceCSIR execute() throws Exception {
        logger.trace("Entering function: execute()");

        session.setResourceUri(getEnumResourceURI());
        InstanceCSIR value = null;
        Document response = sendRequestEnumerationReturnDocument();

        Element element = response.getDocumentElement();
        NodeList nodeList = element.getElementsByTagNameNS(WSCommandRNDConstant.WS_MAN_NAMESPACE, WSCommandRNDConstant.WSMAN_ITEMS_TAG);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Element.ELEMENT_NODE) {
                if (node.hasChildNodes()) {
                    NodeList CSIRAttributesNodeList = node.getChildNodes();

                    for (int j = 0; j < CSIRAttributesNodeList.getLength(); j++) {
                        Node CSIRAttributesNode = CSIRAttributesNodeList.item(j);

                        if (findCSIRNode(CSIRAttributesNode)) {
                            value = findCSIRNodeValue(CSIRAttributesNode);
                            logger.trace("Exiting function: execute()");
                            return value;
                        }
                    }
                }
            }
        }
        logger.trace("Exiting function: execute()");
        return null;
    }


    /**
     * Find the CSIR Element
     * 
     * @param childnodes
     * @return True/False
     */
    private boolean findCSIRNode(Node CSIRAttributesNode) {
        NodeList childNodes = CSIRAttributesNode.getChildNodes();
        /*
         * FIX ME for ICEE
         * 
         * for (int t = 0; t < childNodes.getLength(); t++) { Node child = childNodes.item(t); System.out.println(child.getTextContent()); if (child.getTextContent() != null &&
         * child.getTextContent().equalsIgnoreCase(CSIR_KEY)) { data.elementName = CSIR_KEY; return true; } }
         * 
         */
        return false;
    }


    /**
     * Find the CSIRNodeValue
     * 
     * @param CSIRAttributesNode
     * @return String - value.
     */
    private InstanceCSIR findCSIRNodeValue(Node CSIRAttributesNode) {
        NodeList childNodes = CSIRAttributesNode.getChildNodes();
        // FIX ME for ICEE
        /*
         * for (int t = 0; t < childNodes.getLength(); t++) { Node child = childNodes.item(t);
         * 
         * System.out.println(child.getLocalName()); String nodeName; if (child.getLocalName() != null) { nodeName = child.getLocalName(); if
         * (nodeName.equalsIgnoreCase(CSIR_KEY_VALUE)) { System.out.println(child.getTextContent()); data.currentValue = child.getTextContent(); } } } if (data.elementName != null
         * && data.currentValue != null) return data;
         */

        return null;
    }
}
