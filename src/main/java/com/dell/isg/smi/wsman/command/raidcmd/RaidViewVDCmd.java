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
import com.dell.isg.smi.wsman.entity.RaidViewVDEntity;

/**
 * @author Umer_Shabbir
 * @author Anshu_Virk
 */
public class RaidViewVDCmd extends AbstractRaid {

    private static final Logger logger = LoggerFactory.getLogger(RaidViewVDCmd.class);
    private RaidViewVDEntity raidConfigDetail = new RaidViewVDEntity();
    private List<String> lstPDs = new ArrayList<String>();
    private int counter = 0;


    public RaidViewVDCmd(String ipAddr, String userName, String passwd) {
        super(ipAddr, userName, passwd);
        // TODO Auto-generated constructor stub
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: RaidViewVDCmd((String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        logger.trace("Exiting constructor: RaidViewVDCmd()");
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.dell.pg.spectre.common.protocol.wsman.WSManBaseCommand#run()
     */
    @Override
    public List<RaidViewVDEntity> execute() throws Exception {
        logger.trace("Entering function: execute()");
        session.setResourceUri(getResourceURI());
        Document doc2 = this.sendRequestEnumerationReturnDocument();
        List<RaidViewVDEntity> raidConfig = this.getRaidConfig(doc2);
        logger.trace("Exiting function: execute()");
        return raidConfig;
    }


    protected String getResourceURI() {
        String resourceURI = getBaseURI() + WSManClassEnum.DCIM_VirtualDiskView.toString();
        return resourceURI;
    }


    private List<RaidViewVDEntity> getRaidConfig(Document doc) {

        Element element = doc.getDocumentElement();

        NodeList nodeList = element.getElementsByTagNameNS(WSCommandRNDConstant.WS_MAN_NAMESPACE, WSCommandRNDConstant.WSMAN_ITEMS_TAG);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Element.ELEMENT_NODE) {
                if (node.hasChildNodes()) {
                    return getBootSourceBySourceName(node.getChildNodes());
                }
            }
        }
        return null;
    }


    // Child nodes
    private List<RaidViewVDEntity> getBootSourceBySourceName(NodeList raidConfigNodeList) {

        List<RaidViewVDEntity> raidConfigList = new ArrayList<RaidViewVDEntity>();
        for (int j = 0; j < raidConfigNodeList.getLength(); j++) {
            Node raidConfigSettingNode = raidConfigNodeList.item(j);
            if (raidConfigSettingNode.getNodeType() == Element.ELEMENT_NODE) {
                NodeList childNodes = raidConfigSettingNode.getChildNodes();
                for (int i = 0; i < childNodes.getLength(); i++) {
                    Node raidConfigSettingChildNode = childNodes.item(i);
                    setRaidConfigSettingDetail(raidConfigSettingChildNode, raidConfigDetail);
                }
                raidConfigDetail.setPhysicalIDsVD(lstPDs.toArray(new String[0]));
                raidConfigList.add(raidConfigDetail);
                counter = 0;
                raidConfigDetail = new RaidViewVDEntity();
                lstPDs = new ArrayList<String>();
            }
        }
        return raidConfigList;
    }


    private void setRaidConfigSettingDetail(Node node, RaidViewVDEntity raidConfigDtl) {

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
            raidConfigDetail.setFqddVD(value);
            return;
        }
        if (localNodeName.equalsIgnoreCase("Name")) {
            if (value == null)
                raidConfigDetail.setNameVD("");
            else
                raidConfigDetail.setNameVD(value);
            return;
        }
        if (localNodeName.equalsIgnoreCase("PhysicalDiskIDs")) {
            lstPDs.add(value);
        }
        if (localNodeName.equalsIgnoreCase("RAIDStatus")) {
            raidConfigDtl.setRaidStatusVD(value);
            return;
        }
        if (localNodeName.equalsIgnoreCase("RAIDTypes")) {
            if (value != null) {
                if (value.equalsIgnoreCase("2"))
                    raidConfigDtl.setRaidTypesVD("0");
                else if (value.equalsIgnoreCase("4"))
                    raidConfigDtl.setRaidTypesVD("1");
                else if (value.equalsIgnoreCase("64"))
                    raidConfigDtl.setRaidTypesVD("5");
                else if (value.equalsIgnoreCase("128"))
                    raidConfigDtl.setRaidTypesVD("6");
                else if (value.equalsIgnoreCase("2048"))
                    raidConfigDtl.setRaidTypesVD("10");
                else if (value.equalsIgnoreCase("8192"))
                    raidConfigDtl.setRaidTypesVD("50");
                else if (value.equalsIgnoreCase("16384"))
                    raidConfigDtl.setRaidTypesVD("60");
                else
                    raidConfigDtl.setRaidTypesVD("No RAID");
            }
            return;
        }
        if (localNodeName.equalsIgnoreCase("SpanLength")) {
            raidConfigDtl.setSpanLengthVD(Long.parseLong(value));
            return;
        }
        if (localNodeName.equalsIgnoreCase("SizeInBytes")) {
            // FIX ME for ICEE
            // raidConfigDtl.setSizeInBytesVD(SpectreUtils.bytesToMegaBytes(Long.parseLong(value)));
            return;
        }
        if (localNodeName.equalsIgnoreCase("ObjectStatus")) {
            raidConfigDtl.setObjectStatus(value);
            return;
        }

    }
}
