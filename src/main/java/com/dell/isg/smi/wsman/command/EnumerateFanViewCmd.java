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
import com.dell.isg.smi.wsman.command.entity.FanView;
import com.sun.ws.management.addressing.Addressing;

public class EnumerateFanViewCmd extends WSManClientBaseCommand<List<FanView>> {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(EnumerateSensorViewCmd.class);


    public EnumerateFanViewCmd() {
    }


    public EnumerateFanViewCmd(String ipAddr, String userName, String passwd) {
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: EnumerateFanViewCmd(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        session = this.getSession();
        session.setResourceUri(getResourceURI());

        logger.trace("Exiting constructor: EnumerateFanViewCmd()");
    }


    @Override
    public List<FanView> execute() throws Exception {

        logger.trace("Entering function: execute()");

        Addressing addressing = this.sendRequestEnumeration();

        NodeList nodeset = (NodeList) session.findObjectInDocument(addressing.getBody(), "//*[local-name()='DCIM_FanView']", XPathConstants.NODESET);

        List<FanView> FanViewList = parse(nodeset);

        logger.trace("Exiting function: execute()");
        return FanViewList;

    }


    public String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_FanView);

        return sb.toString();
    }


    @Override
    public List<FanView> parse(String xml) throws Exception {
        Document document = WSManUtils.toDocument(xml);
        NodeList nodeset = (NodeList) findObjectInDocument(document, "//*[local-name()='DCIM_FanView']", XPathConstants.NODESET);
        List<FanView> FanViewList = parse(nodeset);
        return FanViewList;
    }


    private List<FanView> parse(NodeList nodeset) throws Exception {
        List<FanView> FanViewList = new ArrayList<FanView>();
        FanView fanView = null;

        for (int i = 0; i < nodeset.getLength(); i++) {
            Node node = nodeset.item(i);

            fanView = new FanView();
            FanViewList.add(fanView);

            NodeList nodeList = node.getChildNodes();

            for (int j = 0; j < nodeList.getLength(); j++) {
                Node itemNode = nodeList.item(j);

                String nodeValue = itemNode.getTextContent();

                if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "ActiveCooling")) {
                    fanView.setActiveCooling(nodeValue);
                }

                if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "BaseUnits")) {
                    fanView.setBaseUnits(nodeValue);
                }

                if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "CurrentReading")) {
                    fanView.setCurrentReading(nodeValue);
                }

                if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "DeviceDescription")) {
                    fanView.setDeviceDescription(nodeValue);
                }

                if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "FQDD")) {
                    fanView.setFQDD(nodeValue);
                }

                if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "InstanceID")) {
                    fanView.setInstanceID(nodeValue);
                }

                if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "LastSystemInventoryTime")) {
                    fanView.setLastSystemInventoryTime(nodeValue);
                }

                if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "LastUpdateTime")) {
                    fanView.setLastUpdateTime(nodeValue);
                }

                if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "PWM")) {
                    fanView.setPwm(nodeValue);
                }

                if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "PrimaryStatus")) {
                    fanView.setPrimaryStatus(nodeValue);
                }

                if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "RateUnits")) {
                    fanView.setRateUnits(nodeValue);
                }

                if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "RedundancyStatus")) {
                    fanView.setRedundancyStatus(nodeValue);
                }

                if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "UnitModifier")) {
                    fanView.setUnitModifier(nodeValue);
                }

                if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "VariableSpeed")) {
                    fanView.setVariableSpeed(nodeValue);
                }

            }

        }

        logger.trace("Exiting function: execute()");
        return FanViewList;
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
