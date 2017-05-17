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

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManClientBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.command.entity.PSNumericView;
import com.dell.isg.smi.wsmanclient.util.WSManUtils;
import com.sun.ws.management.addressing.Addressing;

public class EnumeratePSNumericSensorCmd extends WSManClientBaseCommand<List<PSNumericView>> {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(EnumeratePSNumericSensorCmd.class);


    public EnumeratePSNumericSensorCmd() {
    }


    public EnumeratePSNumericSensorCmd(String ipAddr, String userName, String passwd) {

        // set the WSMan Session
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: EnumeratePSNumericSensorCmd(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        session = this.getSession();
        session.setResourceUri(getResourceURI());

        logger.trace("Exiting constructor: EnumeratePSNumericSensorCmd()");

    }


    @Override
    public List<PSNumericView> execute() throws Exception {

        Addressing addressing = this.sendRequestEnumeration();
        SOAPBody tempDoc = addressing.getBody();

        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();
        Object objNode = xpath.evaluate("//*[local-name()='Items']", tempDoc, XPathConstants.NODE);
        Node items = null;
        if (objNode != null) {
            items = (Node) objNode;

        }
        return parse(items);
    }


    public String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_PSNumericsensor);

        return sb.toString();
    }


    @Override
    public List<PSNumericView> parse(String xml) throws Exception {
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


    private List<PSNumericView> parse(Node items) {
        List<PSNumericView> psNumericViewList = new ArrayList<PSNumericView>();
        if (items != null) {
            PSNumericView psNumericView = null;
            if (items.hasChildNodes()) {
                NodeList nodeList = items.getChildNodes();
                for (int j = 0; j < nodeList.getLength(); j++) {
                    Node tmpNode = nodeList.item(j);
                    if (tmpNode.getChildNodes() != null) {
                        if (tmpNode.getNodeType() == Element.ELEMENT_NODE) {
                            NodeList nicList = tmpNode.getChildNodes();
                            psNumericView = new PSNumericView();
                            for (int k = 0; k < nicList.getLength(); k++) {
                                Node currentValue = nicList.item(k);
                                if (currentValue.getLocalName() != null && currentValue.getLocalName().equalsIgnoreCase("BaseUnits")) {
                                    psNumericView.setBaseUnits(currentValue.getTextContent());
                                }
                                if (currentValue.getLocalName() != null && currentValue.getLocalName().equalsIgnoreCase("CurrentReading")) {
                                    psNumericView.setCurrentReading(currentValue.getTextContent());
                                }
                                if (currentValue.getLocalName() != null && currentValue.getLocalName().equalsIgnoreCase("CurrentState")) {
                                    psNumericView.setCurrentState(currentValue.getTextContent());
                                }
                                if (currentValue.getLocalName() != null && currentValue.getLocalName().equalsIgnoreCase("DeviceID")) {
                                    psNumericView.setDeviceId(currentValue.getTextContent());
                                }
                                if (currentValue.getLocalName() != null && currentValue.getLocalName().equalsIgnoreCase("ElementName")) {
                                    psNumericView.setElementName(currentValue.getTextContent());
                                }
                                if (currentValue.getLocalName() != null && currentValue.getLocalName().equalsIgnoreCase("EnabledDefault")) {
                                    psNumericView.setEnabledDefault(currentValue.getTextContent());
                                }
                                if (currentValue.getLocalName() != null && currentValue.getLocalName().equalsIgnoreCase("EnabledState")) {
                                    psNumericView.setEnabledSate(currentValue.getTextContent());
                                }
                                if (currentValue.getLocalName() != null && currentValue.getLocalName().equalsIgnoreCase("HealthState")) {
                                    psNumericView.setHealthState(currentValue.getTextContent());
                                }
                                if (currentValue.getLocalName() != null && currentValue.getLocalName().equalsIgnoreCase("LowerThresholdCritical")) {
                                    psNumericView.setLowerThresholdCritical(currentValue.getTextContent());
                                }
                                if (currentValue.getLocalName() != null && currentValue.getLocalName().equalsIgnoreCase("LowerThresholdNonCritical")) {
                                    psNumericView.setLowerThresholdNonCritical(currentValue.getTextContent());
                                }
                                if (currentValue.getLocalName() != null && currentValue.getLocalName().equalsIgnoreCase("OperationalStatus")) {
                                    psNumericView.setOperationalStatus(currentValue.getTextContent());
                                }
                                if (currentValue.getLocalName() != null && currentValue.getLocalName().equalsIgnoreCase("OtherSensorTypeDescription")) {
                                    psNumericView.setOtherSensorTypeDesc(currentValue.getTextContent());
                                }
                                if (currentValue.getLocalName() != null && currentValue.getLocalName().equalsIgnoreCase("PrimaryStatus")) {
                                    psNumericView.setPrimaryStatus(currentValue.getTextContent());
                                }
                                if (currentValue.getLocalName() != null && currentValue.getLocalName().equalsIgnoreCase("RateUnits")) {
                                    psNumericView.setRateUnits(currentValue.getTextContent());
                                }
                                if (currentValue.getLocalName() != null && currentValue.getLocalName().equalsIgnoreCase("RequestedState")) {
                                    psNumericView.setRequestedStatus(currentValue.getTextContent());
                                }
                                if (currentValue.getLocalName() != null && currentValue.getLocalName().equalsIgnoreCase("SensorType")) {
                                    psNumericView.setSensorType(currentValue.getTextContent());
                                }
                                if (currentValue.getLocalName() != null && currentValue.getLocalName().equalsIgnoreCase("SettableThresholds")) {
                                    psNumericView.setSettableThresholds(currentValue.getTextContent());
                                }
                                if (currentValue.getLocalName() != null && currentValue.getLocalName().equalsIgnoreCase("SupportedThresholds")) {
                                    psNumericView.setSupportedThresholds(currentValue.getTextContent());
                                }
                                if (currentValue.getLocalName() != null && currentValue.getLocalName().equalsIgnoreCase("SystemName")) {
                                    psNumericView.setSystemName(currentValue.getTextContent());
                                }
                                if (currentValue.getLocalName() != null && currentValue.getLocalName().equalsIgnoreCase("TransitioningToState")) {
                                    psNumericView.setTransitioningToState(currentValue.getTextContent());
                                }
                                if (currentValue.getLocalName() != null && currentValue.getLocalName().equalsIgnoreCase("UnitModifier")) {
                                    psNumericView.setUnitModifier(currentValue.getTextContent());
                                }
                                if (currentValue.getLocalName() != null && currentValue.getLocalName().equalsIgnoreCase("UpperThresholdCritical")) {
                                    psNumericView.setUpperThresholdCritical(currentValue.getTextContent());
                                }
                                if (currentValue.getLocalName() != null && currentValue.getLocalName().equalsIgnoreCase("UpperThresholdNonCritical")) {
                                    psNumericView.setUpperThresholdNonCritical(currentValue.getTextContent());
                                }
                                if (currentValue.getLocalName() != null && currentValue.getLocalName().equalsIgnoreCase("ValueFormulation")) {
                                    psNumericView.setValueFormulation(currentValue.getTextContent());
                                }
                            }
                            psNumericViewList.add(psNumericView);
                        }
                    }
                }
            }
        }
        return psNumericViewList;
    }

}
