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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManClientBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.command.entity.MemoryView;
import com.dell.isg.smi.wsmanclient.util.WSManUtils;
import com.sun.ws.management.addressing.Addressing;

public class EnumerateMemoryViewCmd extends WSManClientBaseCommand<List<MemoryView>> {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(EnumerateMemoryViewCmd.class);


    public EnumerateMemoryViewCmd() {
    }


    public EnumerateMemoryViewCmd(String ipAddr, String userName, String passwd) {
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: EnumerateMemoryViewCmd(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        session = this.getSession();
        session.setResourceUri(getResourceURI());

        logger.trace("Exiting constructor: EnumerateMemoryViewCmd()");
    }


    @Override
    public List<MemoryView> execute() throws Exception {

        logger.trace("Entering function: execute()");

        Addressing addressing = this.sendRequestEnumeration();

        NodeList nodeset = (NodeList) session.findObjectInDocument(addressing.getBody(), "//*[local-name()='DCIM_Memoryview']", XPathConstants.NODESET);

        List<MemoryView> memoryViewList = parse(nodeset);

        logger.trace("Exiting function: execute()");
        return memoryViewList;

    }


    public String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_Memoryview);

        return sb.toString();
    }


    @Override
    public List<MemoryView> parse(String xml) throws Exception {
        Document document = WSManUtils.toDocument(xml);
        NodeList nodeset = (NodeList) findObjectInDocument(document, "//*[local-name()='DCIM_Memoryview']", XPathConstants.NODESET);
        List<MemoryView> memoryViewList = parse(nodeset);
        return memoryViewList;
    }


    private List<MemoryView> parse(NodeList nodeset) throws Exception {
        List<MemoryView> memoryViewList = new ArrayList<MemoryView>();
        MemoryView memoryView = null;

        for (int i = 0; i < nodeset.getLength(); i++) {
            Node node = nodeset.item(i);

            memoryView = new MemoryView();
            memoryViewList.add(memoryView);

            NodeList nodeList = node.getChildNodes();

            for (int j = 0; j < nodeList.getLength(); j++) {
                Node itemNode = nodeList.item(j);

                String nodeValue = itemNode.getTextContent();

                if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "BankLabel")) {
                    memoryView.setBankLabel(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "CurrentOperatingSpeed")) {
                    memoryView.setCurrentOperatingSpeed(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "FQDD")) {
                    memoryView.setfQDD(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "InstanceID")) {
                    memoryView.setInstanceID(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "LastSystemInventoryTime")) {
                    memoryView.setLastSystemInventoryTime(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "LastUpdateTime")) {
                    memoryView.setLastUpdateTime(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "ManufactureDate")) {
                    memoryView.setManufactureDate(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Manufacturer")) {
                    memoryView.setManufacturer(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "DeviceDescription")) {
                    memoryView.setDeviceDescription(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "MemoryType")) {
                    memoryView.setMemoryType(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Model")) {
                    memoryView.setModel(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "PartNumber")) {
                    memoryView.setPartNumber(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "PrimaryStatus")) {
                    memoryView.setPrimaryStatus(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Rank")) {
                    memoryView.setRank(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "SerialNumber")) {
                    memoryView.setSerialNumber(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Size")) {
                    memoryView.setSize(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Speed")) {
                    memoryView.setSpeed(nodeValue);
                }

            }

        }

        logger.trace("Exiting function: execute()");
        return memoryViewList;
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
