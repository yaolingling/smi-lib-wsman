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
import com.dell.isg.smi.wsman.command.entity.VirtualDiskView;

public class EnumerateVirtualDiskView extends WSManClientBaseCommand<List<VirtualDiskView>> {

    private static final Logger logger = LoggerFactory.getLogger(EnumerateBaseMetricValueCmd.class);
    private WSManageSession session = null;


    public EnumerateVirtualDiskView() {
    }


    public EnumerateVirtualDiskView(String ipAddr, String userName, String passwd) {
        // set the WSMan Session
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: EnumerateVirtualDiskView(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        session = this.getSession();
        session.setResourceUri(getResourceURI());
        logger.trace("Exiting constructor: EnumerateVirtualDiskView()");
    }


    @Override
    public List<VirtualDiskView> execute() throws Exception {
        logger.trace("Entering method execute() ");
        NodeList response = this.sendRequestEnumerationReturnNodeList();
        List<VirtualDiskView> virtualDiskViewList = parse(response);
        logger.trace("Exiting method execute() ");
        return virtualDiskViewList;
    }


    public String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_VirtualDiskView);

        return sb.toString();
    }


    private void populateVirtualDiskView(VirtualDiskView virtualDiskView, Node virtualDiskViewNode) {

        NodeList virtualDiskChildNodes = virtualDiskViewNode.getChildNodes();

        for (int i = 0; i < virtualDiskChildNodes.getLength(); i++) {
            Node childNode = virtualDiskChildNodes.item(i);

            if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "BlockSizeInBytes")) {
                virtualDiskView.setBlockSizeInBytes(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "Cachecade")) {
                virtualDiskView.setCacheCade(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "DiskCachePolicy")) {
                virtualDiskView.setDiskCachePolicy(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "FQDD")) {
                virtualDiskView.setfQDD(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "InstanceID")) {
                virtualDiskView.setInstanceID(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "LastSystemInventoryTime")) {
                virtualDiskView.setLastSystemInventoryTime(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "LastUpdateTime")) {
                virtualDiskView.setLastUpdateTime(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "LockStatus")) {
                virtualDiskView.setLockStatus(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "Name")) {
                virtualDiskView.setName(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "ObjectStatus")) {
                virtualDiskView.setObjectStatus(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "PhysicalDiskIDs")) {
                if (virtualDiskView.getPhysicalDiskIDs() == null) {
                    virtualDiskView.setPhysicalDiskIDs(new ArrayList<String>());
                }
                virtualDiskView.getPhysicalDiskIDs().add(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "PrimaryStatus")) {
                virtualDiskView.setPrimaryStatus(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "RAIDStatus")) {
                virtualDiskView.setRaidStatus(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "RAIDTypes")) {
                virtualDiskView.setRaidTypes(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "ReadCachePolicy")) {
                virtualDiskView.setReadCachePolicy(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "RemainingRedundancy")) {
                virtualDiskView.setRemainingRedundancy(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "SizeInBytes")) {
                virtualDiskView.setSizeInBytes(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "SpanDepth")) {
                virtualDiskView.setSpanDepth(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "Cachecade")) {
                virtualDiskView.setSpanLength(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "StartingLBAInBlocks")) {
                virtualDiskView.setStartingLBAInBlocks(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "StripeSize")) {
                virtualDiskView.setStripeSize(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "VirtualDiskTargetID")) {
                virtualDiskView.setVirtualDiskTargetID(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "WriteCachePolicy")) {
                virtualDiskView.setWriteCachePolicy(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "BusProtocol")) {
                virtualDiskView.setBusProtocol(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "MediaType")) {
                virtualDiskView.setMediaType(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "DeviceDescription")) {
                virtualDiskView.setDeviceDescription(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "T10PIStatus")) {
                virtualDiskView.setT10piStatus(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "OperationName")) {
                virtualDiskView.setOperationName(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "OperationPercentComplete")) {
                virtualDiskView.setOperationPercentComplete(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "PendingOperations")) {
                virtualDiskView.setPendingOperations(childNode.getTextContent());
            } else if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "RollupStatus")) {
                virtualDiskView.setRollupStatus(childNode.getTextContent());
            }
        }

    }


    @Override
    public List<VirtualDiskView> parse(String xml) throws Exception {
        Document response = WSManUtils.toDocument(xml);
        Element element = response.getDocumentElement();
        NodeList nodeList = element.getElementsByTagNameNS(WSCommandRNDConstant.WS_MAN_NAMESPACE, WSCommandRNDConstant.WSMAN_ITEMS_TAG);
        List<VirtualDiskView> virtualDiskViewList = parse(nodeList);
        return virtualDiskViewList;
    }


    private List<VirtualDiskView> parse(NodeList nodeList) throws Exception {
        List<VirtualDiskView> virtualDiskViewList = new ArrayList<VirtualDiskView>();
        if (null != nodeList) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node itemNode = nodeList.item(i);
                NodeList virtualDiskViewNodeList = itemNode.getChildNodes();
                for (int j = 0; j < virtualDiskViewNodeList.getLength(); j++) {
                    VirtualDiskView virtualDiskView = new VirtualDiskView();
                    populateVirtualDiskView(virtualDiskView, virtualDiskViewNodeList.item(j));
                    virtualDiskViewList.add(virtualDiskView);
                }
            }
        }
        return virtualDiskViewList;
    }
}
