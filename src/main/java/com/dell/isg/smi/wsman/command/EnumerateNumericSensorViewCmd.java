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
import com.dell.isg.smi.wsman.command.entity.NumericSensorView;
import com.sun.ws.management.addressing.Addressing;

/**
 * @author Prashanth.Gowda
 *
 */

public class EnumerateNumericSensorViewCmd extends WSManClientBaseCommand<List<NumericSensorView>> {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(EnumerateNumericSensorViewCmd.class);


    public EnumerateNumericSensorViewCmd() {
    }


    public EnumerateNumericSensorViewCmd(String ipAddr, String userName, String passwd) {
        super(ipAddr, userName, passwd);
        session = this.getSession();
        session.setResourceUri(getResourceURI());
    }


    @Override
    public List<NumericSensorView> execute() throws Exception {
        logger.trace("Entering function: execute()");
        Addressing addressing = this.sendRequestEnumeration();
        NodeList nodeset = (NodeList) session.findObjectInDocument(addressing.getBody(), "//*[local-name()='DCIM_NumericSensor']", XPathConstants.NODESET);
        List<NumericSensorView> numericSensorViewList = parse(nodeset);
        logger.trace("Exiting function: execute()");
        return numericSensorViewList;
    }


    public String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_NumericSensor);
        return sb.toString();
    }


    @Override
    public List<NumericSensorView> parse(String xml) throws Exception {
        Document document = WSManUtils.toDocument(xml);
        NodeList nodeset = (NodeList) findObjectInDocument(document, "//*[local-name()='DCIM_NumericSensor']", XPathConstants.NODESET);
        List<NumericSensorView> numericSensorViewList = parse(nodeset);
        return numericSensorViewList;
    }


    private List<NumericSensorView> parse(NodeList nodeset) throws Exception {
        List<NumericSensorView> numericSensorViewList = new ArrayList<NumericSensorView>();
        NumericSensorView numericSensorView = null;
        String nodeValue = null;
        String nodeName = null;
        Node node = null;
        Node itemNode = null;
        for (int i = 0; i < nodeset.getLength(); i++) {
            node = nodeset.item(i);
            numericSensorView = new NumericSensorView();
            NodeList nodeList = node.getChildNodes();
            for (int j = 0; j < nodeList.getLength(); j++) {
                itemNode = nodeList.item(j);
                nodeValue = itemNode.getTextContent();
                nodeName = itemNode.getLocalName();
                if (nodeName == null) {
                    continue;
                }
                nodeName = nodeName.trim();

                switch (nodeName) {
                case "CreationClassName": {
                    numericSensorView.setCreationClassName(nodeValue);
                    break;
                }

                case "CurrentState": {
                    numericSensorView.setCurrentState(nodeValue);
                    break;
                }

                case "DeviceID": {
                    numericSensorView.setDeviceId(nodeValue);
                    break;
                }
                case "ElementName": {
                    numericSensorView.setElementName(nodeValue);
                    break;
                }
                case "EnabledState": {
                    numericSensorView.setEnabledState(nodeValue);
                    break;
                }

                case "HealthState": {
                    numericSensorView.setHealthState(nodeValue);
                    break;
                }

                case "OperationalStatus": {
                    numericSensorView.setOperationalStatus(nodeValue);
                    break;
                }

                case "PossibleStates": {
                    numericSensorView.setPossibleStates(nodeValue);
                    break;
                }

                case "PrimaryStatus": {
                    numericSensorView.setPrimaryStatus(nodeValue);
                    break;
                }

                case "RequestedState": {
                    numericSensorView.setRequestedState(nodeValue);
                    break;
                }

                case "SensorType": {
                    numericSensorView.setSensorType(nodeValue);
                    break;
                }

                case "SystemCreationClassName": {
                    numericSensorView.setSystemCreationClassName(nodeValue);
                    break;
                }

                case "SystemName": {
                    numericSensorView.setSystemName(nodeValue);
                    break;
                }

                case "BaseUnits": {
                    numericSensorView.setBaseUnits(nodeValue);
                    break;
                }

                case "CurrentReading": {
                    numericSensorView.setCurrentReading(nodeValue);
                    break;
                }

                case "RateUnits": {
                    numericSensorView.setRateUnits(nodeValue);
                    break;
                }

                case "SettableThresholds": {
                    numericSensorView.setSettableThresholds(nodeValue);
                    break;
                }

                case "SupportedThresholds": {
                    numericSensorView.setSupportedThresholds(nodeValue);
                    break;
                }

                case "UnitModifier": {
                    numericSensorView.setUnitModifier(nodeValue);
                    break;
                }

                case "LowerThresholdCritical": {
                    numericSensorView.setLowerThresholdCritical(nodeValue);
                    break;
                }

                case "LowerThresholdNonCritical": {
                    numericSensorView.setLowerThresholdNonCritical(nodeValue);
                    break;
                }

                case "UpperThresholdCritical": {
                    numericSensorView.setUpperThresholdCritical(nodeValue);
                    break;
                }

                case "UpperThresholdNonCritical": {
                    numericSensorView.setUpperThresholdNonCritical(nodeValue);
                    break;
                }

                case "TransitioningToState": {
                    numericSensorView.setTransitioningToState(nodeValue);
                    break;
                }

                case "ValueFormulation": {
                    numericSensorView.setValueFormulation(nodeValue);
                    break;
                }

                case "EnabledDefault": {
                    numericSensorView.setEnabledDefault(nodeValue);
                    break;
                }

                case "OtherSensorTypeDescription": {
                    numericSensorView.setOtherSensorTypeDescription(nodeValue);
                    break;
                }

                default: {
                    logger.error("New Attribute {} is added and not handled by code: ", nodeName);
                }
                }
            }

            numericSensorViewList.add(numericSensorView);

        }
        logger.trace("Exiting function: execute()");
        return numericSensorViewList;
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
