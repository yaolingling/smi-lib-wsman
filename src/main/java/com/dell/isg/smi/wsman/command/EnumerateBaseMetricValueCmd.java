/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import java.util.ArrayList;
import java.util.List;

import javax.xml.soap.SOAPBody;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.wsmanclient.util.WSManUtils;
import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManClientBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.command.entity.BaseMetricValue;
import com.sun.ws.management.addressing.Addressing;

public class EnumerateBaseMetricValueCmd extends WSManClientBaseCommand<List<BaseMetricValue>> {

    private static final Logger logger = LoggerFactory.getLogger(EnumerateBaseMetricValueCmd.class);

    private WSManageSession session = null;


    public EnumerateBaseMetricValueCmd() {
    }


    public EnumerateBaseMetricValueCmd(String ipAddr, String userName, String passwd) {

        // set the WSMan Session
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: EnumerateBaseMetricValueCmd(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        session = this.getSession();
        session.setResourceUri(getResourceURI());

        logger.trace("Exiting constructor: EnumerateBaseMetricValueCmd()");

    }


    @Override
    public List<BaseMetricValue> execute() throws Exception {
        logger.trace("Entering method execute() ");
        Addressing addressing = this.sendRequestEnumeration();
        SOAPBody tempDoc = addressing.getBody();
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();
        Object objNode = xpath.evaluate("//*[local-name()='Items']", tempDoc, XPathConstants.NODE);
        Node items = null;
        if (objNode != null) {
            items = (Node) objNode;

        }
        List<BaseMetricValue> baseMetricValueList = parse(items);
        logger.trace("Exiting method execute() ");
        return baseMetricValueList;
    }


    @Override
    public String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_BaseMetricValue);

        return sb.toString();
    }


    @Override
    public List<BaseMetricValue> parse(String xml) throws Exception {
        Document tempDoc = WSManUtils.toDocument(xml);
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();
        Object objNode = xpath.evaluate("//*[local-name()='Items']", tempDoc, XPathConstants.NODE);
        Node items = null;
        if (objNode != null) {
            items = (Node) objNode;

        }
        return parse(items);
    }


    private List<BaseMetricValue> parse(Node items) throws Exception {
        List<BaseMetricValue> baseMetricValueList = new ArrayList<BaseMetricValue>();
        if (items != null) {

            BaseMetricValue baseMetricValue = null;
            if (items.hasChildNodes()) {
                NodeList nodeList = items.getChildNodes();
                for (int j = 0; j < nodeList.getLength(); j++) {
                    Node tmpNode = nodeList.item(j);
                    if (tmpNode.getChildNodes() != null) {
                        if (tmpNode.getNodeType() == Element.ELEMENT_NODE) {
                            NodeList nicList = tmpNode.getChildNodes();
                            baseMetricValue = new BaseMetricValue();
                            for (int k = 0; k < nicList.getLength(); k++) {
                                Node currentValue = nicList.item(k);
                                if (currentValue.getLocalName() != null && currentValue.getLocalName().equalsIgnoreCase("Duration")) {
                                    baseMetricValue.setDuration(currentValue.getTextContent());
                                }
                                if (currentValue.getLocalName() != null && currentValue.getLocalName().equalsIgnoreCase("InstanceID")) {
                                    baseMetricValue.setInstanceId(currentValue.getTextContent().trim());
                                }
                                if (currentValue.getLocalName() != null && currentValue.getLocalName().equalsIgnoreCase("MetricDefinitionId")) {
                                    baseMetricValue.setMetricDefinitionId(currentValue.getTextContent());
                                }
                                if (currentValue.getLocalName() != null && currentValue.getLocalName().equalsIgnoreCase("MetricValue")) {
                                    baseMetricValue.setMetricValue(currentValue.getTextContent());
                                }
                                if (currentValue.getLocalName() != null && currentValue.getLocalName().equalsIgnoreCase("TimeStamp")) {
                                    baseMetricValue.setTimeStamp(currentValue.getTextContent());
                                }
                                if (currentValue.getLocalName() != null && currentValue.getLocalName().equalsIgnoreCase("Volatile")) {
                                    baseMetricValue.setVolatile(Boolean.parseBoolean(currentValue.getTextContent()));
                                }
                            }
                            baseMetricValueList.add(baseMetricValue);

                        }
                    }
                }
            }
        }
        return baseMetricValueList;
    }

}
