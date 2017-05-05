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
import com.dell.isg.smi.wsman.command.entity.AggregationMetricValue;
import com.sun.ws.management.addressing.Addressing;

public class EnumerateAggregationMetricValueCmd extends WSManClientBaseCommand<List<AggregationMetricValue>> {

    private static final Logger logger = LoggerFactory.getLogger(EnumerateAggregationMetricValueCmd.class);

    private WSManageSession session = null;


    public EnumerateAggregationMetricValueCmd() {
    }


    public EnumerateAggregationMetricValueCmd(String ipAddr, String userName, String passwd) {
        // set the WSMan Session
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: EnumerateAggregationMetricValueCmd(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        session = this.getSession();
        session.setResourceUri(getResourceURI());

        logger.trace("Exiting constructor: EnumerateAggregationMetricValueCmd()");
    }


    @Override
    public List<AggregationMetricValue> execute() throws Exception {

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
        List<AggregationMetricValue> aggregationMetricValueList = parse(items);
        logger.trace("Exiting method execute() ");
        return aggregationMetricValueList;
    }


    @Override
    public String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_AggregationMetricValue);

        return sb.toString();
    }


    @Override
    public List<AggregationMetricValue> parse(String xml) throws Exception {
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


    private List<AggregationMetricValue> parse(Node items) {
        List<AggregationMetricValue> aggregationMetricValueList = new ArrayList<AggregationMetricValue>();

        if (items == null || !items.hasChildNodes()) {
            return aggregationMetricValueList;
        }
        NodeList nodeList = items.getChildNodes();
        for (int j = 0; j < nodeList.getLength(); j++) {
            Node tmpNode = nodeList.item(j);
            if (tmpNode.getChildNodes() != null) {
                if (tmpNode.getNodeType() == Element.ELEMENT_NODE) {
                    NodeList nicList = tmpNode.getChildNodes();
                    AggregationMetricValue aggregationMetricValue = new AggregationMetricValue();
                    for (int k = 0; k < nicList.getLength(); k++) {
                        Node currentValue = nicList.item(k);
                        if (currentValue.getLocalName() != null && currentValue.getLocalName().equalsIgnoreCase("AggregationDuration")) {
                            aggregationMetricValue.setAggegationDuration(currentValue.getTextContent());
                        }
                        if (currentValue.getLocalName() != null && currentValue.getLocalName().equalsIgnoreCase("AggregationTimeStamp")) {
                            aggregationMetricValue.setAggregationTimestamp(currentValue.getTextContent());
                        }
                        if (currentValue.getLocalName() != null && currentValue.getLocalName().equalsIgnoreCase("BreakdownDimension")) {
                            aggregationMetricValue.setBreakdownDimension(currentValue.getTextContent());
                        }
                        if (currentValue.getLocalName() != null && currentValue.getLocalName().equalsIgnoreCase("BreakdownValue")) {
                            aggregationMetricValue.setBreakdownValue(currentValue.getTextContent());
                        }
                        if (currentValue.getLocalName() != null && currentValue.getLocalName().equalsIgnoreCase("InstanceID")) {
                            aggregationMetricValue.setInstanceId(currentValue.getTextContent().trim());
                        }
                        if (currentValue.getLocalName() != null && currentValue.getLocalName().equalsIgnoreCase("MetricDefinitionId")) {
                            aggregationMetricValue.setMetricDefinitionId(currentValue.getTextContent());
                        }
                        if (currentValue.getLocalName() != null && currentValue.getLocalName().equalsIgnoreCase("MetricValue")) {
                            aggregationMetricValue.setMetricValue(currentValue.getTextContent());
                        }
                        if (currentValue.getLocalName() != null && currentValue.getLocalName().equalsIgnoreCase("TimeStamp")) {
                            aggregationMetricValue.setTimestamp(currentValue.getTextContent());
                        }
                        if (currentValue.getLocalName() != null && currentValue.getLocalName().equalsIgnoreCase("Volatile")) {
                            aggregationMetricValue.setVolatile(Boolean.parseBoolean(currentValue.getTextContent()));
                        }
                    } // end for loop
                    aggregationMetricValueList.add(aggregationMetricValue);
                }
            }
        }

        return aggregationMetricValueList;
    }
}
