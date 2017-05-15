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

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManClientBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.command.entity.SensorView;
import com.dell.isg.smi.wsmanclient.util.WSManUtils;
import com.sun.ws.management.addressing.Addressing;

/**
 * @author rahman.muhammad
 *
 */

public class EnumerateSensorViewCmd extends WSManClientBaseCommand<List<SensorView>> {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(EnumerateSensorViewCmd.class);


    public EnumerateSensorViewCmd() {
    }


    public EnumerateSensorViewCmd(String ipAddr, String userName, String passwd) {
        super(ipAddr, userName, passwd);
        session = this.getSession();
        session.setResourceUri(getResourceURI());
    }


    @Override
    public List<SensorView> execute() throws Exception {

        logger.trace("Entering function: execute()");

        Addressing addressing = this.sendRequestEnumeration();

        NodeList nodeset = (NodeList) session.findObjectInDocument(addressing.getBody(), "//*[local-name()='DCIM_SensorView']", XPathConstants.NODESET);

        List<SensorView> SensorViewList = parse(nodeset);

        logger.trace("Exiting function: execute()");
        return SensorViewList;

    }


    public String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_Sensor);

        return sb.toString();
    }


    @Override
    public List<SensorView> parse(String xml) throws Exception {
        Document document = WSManUtils.toDocument(xml);
        NodeList nodeset = (NodeList) findObjectInDocument(document, "//*[local-name()='DCIM_Sensor']", XPathConstants.NODESET);
        List<SensorView> SensorViewList = parse(nodeset);
        return SensorViewList;
    }


    private List<SensorView> parse(NodeList nodeset) throws Exception {
        List<SensorView> sensorViewList = new ArrayList<SensorView>();
        SensorView sensorView = null;
        String nodeValue = null;
        String nodeName = null;
        Node node = null;
        Node itemNode = null;

        for (int i = 0; i < nodeset.getLength(); i++) {
            node = nodeset.item(i);
            sensorView = new SensorView();
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
                    sensorView.setCreationClassName(nodeValue);
                    break;
                }

                case "CurrentState": {
                    sensorView.setCurrentState(nodeValue);
                    break;
                }

                case "DeviceID": {
                    sensorView.setDeviceId(nodeValue);
                    break;
                }
                case "ElementName": {
                    sensorView.setElementName(nodeValue);
                    break;
                }
                case "EnabledState": {
                    sensorView.setEnabledState(nodeValue);
                    break;
                }

                /*
                 * This state is the duplicate of the above one , LC team needs to look into it , then we will change it accordingly
                 * 
                 * case "EnabledState" :{ sensorView.setEnabledState(nodeValue); break; }
                 */

                case "HealthState": {
                    sensorView.setHealthState(nodeValue);
                    break;
                }

                case "OperationalStatus": {
                    sensorView.setOperationalStatus(nodeValue);
                    break;
                }

                case "OtherSensorTypeDescription": {
                    sensorView.setOtherSensorTypeDescription(nodeValue);
                    break;
                }

                case "PossibleStates": {
                    sensorView.setPossibleStates(nodeValue);
                    break;
                }

                case "PrimaryStatus": {
                    sensorView.setPrimaryStatus(nodeValue);
                    break;
                }

                case "RequestedState": {
                    sensorView.setRequestedState(nodeValue);
                    break;
                }

                case "SensorType": {
                    sensorView.setSensorType(nodeValue);
                    break;
                }

                case "SystemCreationClassName": {
                    sensorView.setSystemCreationClassName(nodeValue);
                    break;
                }

                case "SystemName": {
                    sensorView.setSystemName(nodeValue);
                    break;

                }
                default: {
                    logger.error("New Attribute {} is added and not handled by code: ", nodeName);
                }
                }
            }

            sensorViewList.add(sensorView);

        }
        logger.trace("Exiting function: execute()");
        return sensorViewList;
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
