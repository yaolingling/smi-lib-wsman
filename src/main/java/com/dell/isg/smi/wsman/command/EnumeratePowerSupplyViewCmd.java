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

import com.dell.isg.smi.wsmanclient.util.WSManUtils;
import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManClientBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.command.entity.PowerSupplyView;
import com.sun.ws.management.addressing.Addressing;

public class EnumeratePowerSupplyViewCmd extends WSManClientBaseCommand<List<PowerSupplyView>> {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(EnumeratePowerSupplyViewCmd.class);


    public EnumeratePowerSupplyViewCmd() {
    }


    public EnumeratePowerSupplyViewCmd(String ipAddr, String userName, String passwd) {

        super(ipAddr, userName, passwd);

        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: EnumeratePowerSupplyViewCmd(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        session = this.getSession();
        session.setResourceUri(getResourceURI());

        logger.trace("Exiting constructor: EnumeratePowerSupplyViewCmd()");
    }


    @Override
    public List<PowerSupplyView> execute() throws Exception {
        logger.trace("Entering function: execute()");
        Addressing addressing = this.sendRequestEnumeration();
        NodeList nodeset = (NodeList) session.findObjectInDocument(addressing.getBody(), "//*[local-name()='DCIM_Powersupplyview']", XPathConstants.NODESET);

        List<PowerSupplyView> powerSupplyViewList = parse(nodeset);
        logger.trace("Entering function: execute()");
        return powerSupplyViewList;
    }


    public String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_Powersupplyview);

        return sb.toString();
    }


    @Override
    public List<PowerSupplyView> parse(String xml) throws Exception {
        Document tempDoc = WSManUtils.toDocument(xml);
        NodeList nodeset = (NodeList) findObjectInDocument(tempDoc, "//*[local-name()='DCIM_Powersupplyview']", XPathConstants.NODESET);
        List<PowerSupplyView> powerSupplyViewList = parse(nodeset);
        return powerSupplyViewList;
    }


    private List<PowerSupplyView> parse(NodeList nodeset) throws Exception {
        List<PowerSupplyView> powerSupplyViewList = new ArrayList<PowerSupplyView>();
        PowerSupplyView powerSupplyView = null;

        for (int i = 0; i < nodeset.getLength(); i++) {
            Node node = nodeset.item(i);

            NodeList nodeList = node.getChildNodes();

            powerSupplyView = new PowerSupplyView();
            powerSupplyViewList.add(powerSupplyView);

            for (int j = 0; j < nodeList.getLength(); j++) {
                Node itemNode = nodeList.item(j);

                String nodeValue = itemNode.getTextContent();

                if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "DetailedState")) {
                    powerSupplyView.setDetailedState(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "FQDD")) {
                    powerSupplyView.setfQDD(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "FirmwareVersion")) {
                    powerSupplyView.setFirmwareVersion(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "InputVoltage")) {
                    powerSupplyView.setInputVoltage(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "InstanceID")) {
                    powerSupplyView.setInstanceID(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "LastSystemInventoryTime")) {
                    powerSupplyView.setLastSystemInventoryTime(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "LastUpdateTime")) {
                    powerSupplyView.setLastUpdateTime(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Manufacturer")) {
                    powerSupplyView.setManufacturer(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Model")) {
                    powerSupplyView.setModel(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "PartNumber")) {
                    powerSupplyView.setPartNumber(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "PrimaryStatus")) {
                    powerSupplyView.setPrimaryStatus(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "RedundancyStatus")) {
                    powerSupplyView.setRedundancyStatus(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "SerialNumber")) {
                    powerSupplyView.setSerialNumber(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "TotalOutputPower")) {
                    powerSupplyView.setTotalOutputPower(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Type")) {
                    powerSupplyView.setType(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "DeviceDescription")) {
                    powerSupplyView.setDeviceDescription(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Range1MaxInputPower")) {
                    powerSupplyView.setRange1MaxInputPower(nodeValue);
                }
            }
        }
        return powerSupplyViewList;
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
