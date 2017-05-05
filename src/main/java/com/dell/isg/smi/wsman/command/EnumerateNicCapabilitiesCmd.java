/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.wsmanclient.util.WSManUtils;
import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManClientBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.command.entity.NicCapabilities;
import com.sun.ws.management.addressing.Addressing;

/**
 * @author Prashanth.Gowda
 *
 */

public class EnumerateNicCapabilitiesCmd extends WSManClientBaseCommand<List<NicCapabilities>> {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(EnumerateNicCapabilitiesCmd.class);


    public EnumerateNicCapabilitiesCmd() {
    }


    public EnumerateNicCapabilitiesCmd(String ipAddr, String userName, String passwd) {
        super(ipAddr, userName, passwd);
        logger.trace("Entering constructor: EnumerateNicCapabilitiesCmd (ipAddr{} userName{} passwd{})", ipAddr, userName, "####");
        session = this.getSession();
        session.setResourceUri(getResourceURI());
        logger.trace("Exiting constructor: EnumerateNicCapabilitiesCmd()");
    }


    @Override
    public List<NicCapabilities> execute() throws Exception {
        logger.trace("Entering function: execute()");
        Addressing addressing = this.sendRequestEnumeration();
        NodeList nodeset = (NodeList) session.findObjectInDocument(addressing.getBody(), "//*[local-name()='DCIM_NICCapabilities']", XPathConstants.NODESET);
        List<NicCapabilities> nicCapabilitiesList = parse(nodeset);
        logger.trace("Exiting function: execute()");
        return nicCapabilitiesList;
    }


    public String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_NICCapabilities);
        return sb.toString();
    }


    @Override
    public List<NicCapabilities> parse(String xml) throws Exception {
        Document document = WSManUtils.toDocument(xml);
        NodeList nodeset = (NodeList) findObjectInDocument(document, "//*[local-name()='DCIM_NICCapabilities']", XPathConstants.NODESET);
        List<NicCapabilities> nicCapabilitiesList = parse(nodeset);
        return nicCapabilitiesList;
    }


    private List<NicCapabilities> parse(NodeList nodeset) throws Exception {
        logger.trace("Entering function: parse()");
        List<NicCapabilities> nicCapabilitiesList = new ArrayList<NicCapabilities>();
        for (int i = 0; i < nodeset.getLength(); i++) {
            Node node = nodeset.item(i);
            NicCapabilities nicCapabilities = new NicCapabilities();
            NodeList nodeList = node.getChildNodes();
            for (int j = 0; j < nodeList.getLength(); j++) {
                Node itemNode = nodeList.item(j);
                String nodeValue = itemNode.getTextContent();
                String nodeName = itemNode.getLocalName();
                if (nodeName == null) {
                    continue;
                }
                nodeName = nodeName.trim();
                switch (nodeName) {
                case NicCapabilitiesConstants.FCOE_BOOT_MODE: {
                    nicCapabilities.setFcoeBootSupport(nodeValue);
                    break;
                }

                case NicCapabilitiesConstants.FQDD: {
                    nicCapabilities.setFqdd(nodeValue);
                    break;
                }

                case NicCapabilitiesConstants.PXE_BOOT_MODE: {
                    nicCapabilities.setPxeBootSupport(nodeValue);
                    break;
                }
                case NicCapabilitiesConstants.ISCSI_BOOT_MODE: {
                    nicCapabilities.setIscsiBootSupport(nodeValue);
                    break;
                }

                default: {
                    logger.trace("New Attribute {} is added and not handled by code: ", nodeName);
                }
                }
            }

            nicCapabilitiesList.add(nicCapabilities);

        }
        logger.trace("Exiting function: parse()");
        return nicCapabilitiesList;
    }


    public Object findObjectInDocument(Document doc, String xPathLocation, QName qname) throws XPathExpressionException {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        xpath.setNamespaceContext(new PersonalNamespaceContext(getResourceURI()));
        XPathExpression expr = xpath.compile(xPathLocation);
        Object result = expr.evaluate(doc, qname);
        return result;
    }

}
