/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
/**
 * 
 */
package com.dell.isg.smi.wsman.command.raidcmd;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.slf4j.LoggerFactory;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.entity.RaidViewPDEntity;
import com.dell.isg.smi.wsman.utilities.IceeUtils;

/**
 * Rahman_Muhammad
 *
 */
public class RaidViewPDCmd extends AbstractRaid {

    private RaidViewPDEntity raidPDDetail = new RaidViewPDEntity();
    private static final Logger logger = LoggerFactory.getLogger(RaidViewPDCmd.class);


    public RaidViewPDCmd(String ipAddr, String userName, String passwd) {
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: RaidViewPDCmd(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        logger.trace("Exiting constructor: RaidViewPDCmd()");
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.dell.pg.spectre.common.protocol.wsman.WSManBaseCommand#run()
     */
    @Override
    public List<RaidViewPDEntity> execute() throws Exception {
        logger.trace("Entering function: execute()");
        session.setResourceUri(getResourceURI());
        Document doc = this.sendRequestEnumerationReturnDocument();
        List<RaidViewPDEntity> raidConfig = this.getRaidConfig(doc);
        logger.trace("Exiting function: execute()");
        return raidConfig;
    }


    protected String getResourceURI() {
        String resourceURI = getBaseURI() + WSManClassEnum.DCIM_PhysicalDiskView.toString();
        return resourceURI;
    }


    private List<RaidViewPDEntity> getRaidConfig(Document doc) {
        Element element = doc.getDocumentElement();
        NodeList nodeList = element.getElementsByTagNameNS(WSCommandRNDConstant.WS_MAN_NAMESPACE, WSCommandRNDConstant.WSMAN_ITEMS_TAG);
        if (nodeList != null) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Element.ELEMENT_NODE) {
                    if (node.hasChildNodes()) {
                        return getSourceBySourceName(node.getChildNodes());
                    }
                }
            }
        }

        return null;
    }


    private List<RaidViewPDEntity> getSourceBySourceName(NodeList raidConfigNodeList) {
        List<RaidViewPDEntity> raidConfigList = new ArrayList<RaidViewPDEntity>();
        for (int j = 0; j < raidConfigNodeList.getLength(); j++) {
            Node raidConfigSettingNode = raidConfigNodeList.item(j);
            if (raidConfigSettingNode.getNodeType() == Element.ELEMENT_NODE) {
                NodeList childNodes = raidConfigSettingNode.getChildNodes();
                for (int i = 0; i < childNodes.getLength(); i++) {
                    Node raidConfigSettingChildNode = childNodes.item(i);
                    setRaidConfigSettingDetail(raidConfigSettingChildNode, raidPDDetail);
                }

                raidConfigList.add(raidPDDetail);
                raidPDDetail = new RaidViewPDEntity();
            }
        }

        return raidConfigList;
    }


    private void setRaidConfigSettingDetail(Node node, RaidViewPDEntity raidConfigDtl) {

        String localNodeName = node.getLocalName();
        String value = null;

        if (node.hasChildNodes()) {
            NodeList valueNodeList = node.getChildNodes();
            for (int i = 0; i < valueNodeList.getLength(); i++) {
                Node valueNode = valueNodeList.item(i);
                if (valueNode.getNodeType() == Node.TEXT_NODE) {
                    value = valueNode.getNodeValue();
                }
            }
        }

        if (localNodeName.equalsIgnoreCase("FQDD")) {
            raidPDDetail.setFdqqPD(value);
            return;
        }

        if (localNodeName.equalsIgnoreCase("FQDD")) {
            raidPDDetail.setFullFdqqPD(value);
            return;
        }

        if (localNodeName.equalsIgnoreCase("SizeInBytes")) {
            raidPDDetail.setSizeInBytesPD(IceeUtils.bytesToMegaBytes(Long.parseLong(value)));
            return;
        }

        if (localNodeName.equalsIgnoreCase("HotSpareStatus")) {
            if (value != null) {
                if (value.equalsIgnoreCase("1"))
                    raidPDDetail.setAssignSparePD("Dedicated");
                else if (value.equalsIgnoreCase("2"))
                    raidPDDetail.setAssignSparePD("Global");
                else
                    raidPDDetail.setAssignSparePD("No");
            }
            return;
        }

        if (localNodeName.equalsIgnoreCase("BusProtocol")) {
            if (value != null) {
                if (value.equalsIgnoreCase("6"))
                    raidPDDetail.setBusProtocolPD("SAS");
                else
                    raidPDDetail.setBusProtocolPD("SATA");
            }
            return;
        }

        if (localNodeName.equalsIgnoreCase("MediaType")) {
            if (value != null) {
                if (value.equalsIgnoreCase("0"))
                    raidPDDetail.setMediaTypePD("HDD");
                else
                    raidPDDetail.setMediaTypePD("SDD");
            }
            return;
        }

        if (localNodeName.equalsIgnoreCase("SerialNumber")) {
            raidPDDetail.setDiskSerialNumber(value);
            return;
        }

        if (localNodeName.equalsIgnoreCase("RaidStatus")) {
            raidPDDetail.setRaidStatus(value);
            return;
        }
    }
}
