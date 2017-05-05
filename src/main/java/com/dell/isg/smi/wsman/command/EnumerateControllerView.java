/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import com.dell.isg.smi.wsman.command.entity.ControllerView;

public class EnumerateControllerView extends WSManClientBaseCommand<List<ControllerView>> {

    private static final Logger logger = LoggerFactory.getLogger(EnumerateControllerView.class);
    private WSManageSession session = null;


    public EnumerateControllerView() {
    }


    public EnumerateControllerView(String ipAddr, String userName, String passwd) {
        // set the WSMan Session
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: EnumerateControllerView(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        session = this.getSession();
        session.setResourceUri(getResourceURI());

        logger.trace("Exiting constructor: EnumerateControllerView()");
    }


    @Override
    public List<ControllerView> execute() throws Exception {
        logger.trace("Entering method execute() ");
        NodeList response = this.sendRequestEnumerationReturnNodeList();
        List<ControllerView> controllerViewList = parse(response);
        logger.trace("Exiting method execute() ");
        return controllerViewList;
    }


    public String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_ControllerView);
        return sb.toString();
    }


    private void populateControllerView(ControllerView controllerView, Node controllerViewNode) {

        NodeList controllerViewChildNodes = controllerViewNode.getChildNodes();

        for (int i = 0; i < controllerViewChildNodes.getLength(); i++) {
            Node childNode = controllerViewChildNodes.item(i);

            if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "Bus")) {
                controllerView.setBus(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "CacheSizeInMB")) {
                controllerView.setCacheSizeInMB(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "CachecadeCapability")) {
                controllerView.setCacheCadeCapability(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "ControllerFirmwareVersion")) {
                controllerView.setControllerFirmwareVersion(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "Device")) {
                controllerView.setDevice(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "DeviceCardDataBusWidth")) {
                controllerView.setDeviceCardDataBusWidth(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "DeviceCardManufacturer")) {
                controllerView.setDeviceCardManufacturer(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "DeviceCardSlotLength")) {
                controllerView.setDeviceCardSlotLength(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "DeviceCardSlotType")) {
                controllerView.setDeviceCardSlotType(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "DriverVersion")) {
                controllerView.setDriverVersion(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "EncryptionCapability")) {
                controllerView.setEncryptionCapability(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "EncryptionMode")) {
                controllerView.setEncryptionMode(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "FQDD")) {
                controllerView.setfQDD(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "Function")) {
                controllerView.setFunction(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "InstanceID")) {
                controllerView.setInstanceID(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "KeyID")) {
                controllerView.setKeyID(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "LastSystemInventoryTime")) {
                controllerView.setLastSystemInventoryTime(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "LastUpdateTime")) {
                controllerView.setLastUpdateTime(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "PCIDeviceID")) {
                controllerView.setPCIDeviceID(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "PCISlot")) {
                controllerView.setPCISlot(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "PCISubDeviceID")) {
                controllerView.setPCISubDeviceID(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "PCISubVendorID")) {
                controllerView.setPCISubVendorID(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "PatrolReadState")) {
                controllerView.setPatrolReadState(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "PCIVendorID")) {
                controllerView.setPCIVendorID(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "PrimaryStatus")) {
                controllerView.setPrimaryStatus(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "ProductName")) {
                controllerView.setProductName(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "RollUpStatus")) {
                controllerView.setRollUpStatus(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "SASAddress")) {
                controllerView.setSASAddress(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "SecurityStatus")) {
                controllerView.setSecurityStatus(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "SlicedVDCapability")) {
                controllerView.setSlicedVDCapability(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "DeviceDescription")) {
                controllerView.setDeviceDescription(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "MaxAvailablePCILinkSpeed")) {
                controllerView.setMaxAvailablePciLinkSpeed(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "MaxPossiblePICLinkSpeed")) {
                controllerView.setMaxPossiblePciLinkSpeed(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "RealtimeCapability")) {
                controllerView.setRealtimeCapability(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "SupportControllerBootMode")) {
                controllerView.setSupportControllerBootMode(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "SupportEnhancedAutoForeignImport")) {
                controllerView.setSupportEnhancedAutoForeignImport(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "SupportRAID10UnevenSpans")) {
                controllerView.setSupportRaid10UnevenSpans(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "T10PICapability")) {
                controllerView.setT10piCapability(childNode.getTextContent());
            }
        }
    }


    @Override
    public List<ControllerView> parse(String xml) throws Exception {
        Document response = WSManUtils.toDocument(xml);
        Element element = response.getDocumentElement();
        NodeList nodeList = element.getElementsByTagNameNS(WSCommandRNDConstant.WS_MAN_NAMESPACE, WSCommandRNDConstant.WSMAN_ITEMS_TAG);
        List<ControllerView> controllerViewList = parse(nodeList);
        return controllerViewList;
    }


    private List<ControllerView> parse(NodeList nodeList) throws Exception {
        List<ControllerView> controllerViewList = new ArrayList<ControllerView>();
        if (null != nodeList) {

            for (int i = 0; i < nodeList.getLength(); i++) {

                Node itemNode = nodeList.item(i);

                NodeList controllerNodeList = itemNode.getChildNodes();

                for (int j = 0; j < controllerNodeList.getLength(); j++) {

                    ControllerView controllerView = new ControllerView();

                    Node controllerNode = controllerNodeList.item(j);
                    populateControllerView(controllerView, controllerNode);
                    controllerViewList.add(controllerView);

                }
            }
        }
        return controllerViewList;
    }

}
