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

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManClientBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.command.entity.PhysicalDiskView;
import com.dell.isg.smi.wsmanclient.util.WSManUtils;

public class EnumeratePhysicalDiskView extends WSManClientBaseCommand<List<PhysicalDiskView>> {

    private static final Logger logger = LoggerFactory.getLogger(EnumerateBaseMetricValueCmd.class);
    private WSManageSession session = null;


    public EnumeratePhysicalDiskView() {
    }


    public EnumeratePhysicalDiskView(String ipAddr, String userName, String passwd) {
        // set the WSMan Session
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: EnumeratePhysicalDiskView(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        session = this.getSession();
        session.setResourceUri(getResourceURI());

        logger.trace("Exiting constructor: EnumeratePhysicalDiskView()");
    }


    @Override
    public List<PhysicalDiskView> execute() throws Exception {
        logger.trace("Entering method execute() ");
        NodeList response = this.sendRequestEnumerationReturnNodeList();
        List<PhysicalDiskView> physicalDiskViewList = parse(response);
        logger.trace("Exiting method execute() ");
        return physicalDiskViewList;
    }


    public String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_PhysicalDiskView);
        return sb.toString();
    }


    private void populatePhysicalDiskView(PhysicalDiskView physicalDiskView, Node physicalDiskViewNode) {
        NodeList physicalDiskChildNodes = physicalDiskViewNode.getChildNodes();
        for (int i = 0; i < physicalDiskChildNodes.getLength(); i++) {
            Node childNode = physicalDiskChildNodes.item(i);

            if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "BusProtocol")) {
                physicalDiskView.setBusProtocol(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "Connector")) {
                physicalDiskView.setConnector(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "DriveFormFactor")) {
                physicalDiskView.setDriveFormFactor(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "FQDD")) {
                physicalDiskView.setfQDD(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "FreeSizeInBytes")) {
                physicalDiskView.setFreeSizeInBytes(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "HotSpareStatus")) {
                physicalDiskView.setHotSpareStatus(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "InstanceID")) {
                physicalDiskView.setInstanceID(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "LastSystemInventoryTime")) {
                physicalDiskView.setLastSystemInventoryTime(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "LastUpdateTime")) {
                physicalDiskView.setLastUpdateTime(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "Manufacturer")) {
                physicalDiskView.setManufacturer(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "ManufacturingDay")) {
                physicalDiskView.setManufacturingDay(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "ManufacturingWeek")) {
                physicalDiskView.setManufacturingWeek(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "ManufacturingYear")) {
                physicalDiskView.setManufacturingYear(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "MaxCapableSpeed")) {
                physicalDiskView.setMaxCapableSpeed(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "MediaType")) {
                physicalDiskView.setMediaType(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "Model")) {
                physicalDiskView.setModel(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "OperationName")) {
                physicalDiskView.setOperationName(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "OperationPercentComplete")) {
                physicalDiskView.setOperationPercentComplete(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "PredictiveFailureState")) {
                physicalDiskView.setPredictiveFailureState(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "PrimaryStatus")) {
                physicalDiskView.setPrimaryStatus(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "RaidStatus")) {
                physicalDiskView.setRaidStatus(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "Revision")) {
                physicalDiskView.setRevision(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "RollUpStatus")) {
                physicalDiskView.setRollUpStatus(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "SASAddress")) {
                physicalDiskView.setSASAddress(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "SecurityState")) {
                physicalDiskView.setSecurityState(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "SerialNumber")) {
                physicalDiskView.setSerialNumber(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "SizeInBytes")) {
                physicalDiskView.setSizeInBytes(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "Slot")) {
                physicalDiskView.setSlot(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "SupportedEncryptionTypes")) {
                physicalDiskView.setSupportedEncryptionTypes(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "UsedSizeInBytes")) {
                physicalDiskView.setUsedSizeInBytes(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "PPID")) {
                physicalDiskView.setPPID(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "DeviceDescription")) {
                physicalDiskView.setDeviceDescription(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "RemainingRatedWriteEndurance")) {
                physicalDiskView.setRemainingRatedWriteEndurance(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "T10PICapability")) {
                physicalDiskView.setT10PICapability(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "BlockSizeInBytes")) {
                physicalDiskView.setBlockSizeInBytes(childNode.getTextContent());
            }
        }
    }


    @Override
    public List<PhysicalDiskView> parse(String xml) throws Exception {
        Document response = WSManUtils.toDocument(xml);
        Element element = response.getDocumentElement();
        NodeList nodeList = element.getElementsByTagNameNS(WSCommandRNDConstant.WS_MAN_NAMESPACE, WSCommandRNDConstant.WSMAN_ITEMS_TAG);
        List<PhysicalDiskView> physicalDiskViewList = parse(nodeList);
        return physicalDiskViewList;
    }


    private List<PhysicalDiskView> parse(NodeList nodeList) throws Exception {
        List<PhysicalDiskView> physicalDiskViewList = new ArrayList<PhysicalDiskView>();
        if (null != nodeList) {

            for (int i = 0; i < nodeList.getLength(); i++) {

                Node itemNode = nodeList.item(i);

                if (itemNode.hasChildNodes()) {
                    NodeList physicalDiskViewNodeList = itemNode.getChildNodes();
                    for (int j = 0; j < physicalDiskViewNodeList.getLength(); j++) {
                        Node physicalDiskViewNode = physicalDiskViewNodeList.item(j);

                        PhysicalDiskView physicalDiskView = new PhysicalDiskView();
                        populatePhysicalDiskView(physicalDiskView, physicalDiskViewNode);
                        physicalDiskViewList.add(physicalDiskView);

                    }
                }
            }
        }
        return physicalDiskViewList;
    }

}
