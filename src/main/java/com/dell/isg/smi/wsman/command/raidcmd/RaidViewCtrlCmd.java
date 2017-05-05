/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
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
import com.dell.isg.smi.wsman.entity.RaidViewCtrlEntity;

/**
 * @author Umer_Shabbir
 * @author Anshu_Virk
 */
public class RaidViewCtrlCmd extends AbstractRaid {

    private RaidViewCtrlEntity raidConfigDetail = new RaidViewCtrlEntity();
    private static final Logger logger = LoggerFactory.getLogger(RaidViewCtrlCmd.class);


    public RaidViewCtrlCmd(String ipAddr, String userName, String passwd) {
        super(ipAddr, userName, passwd);
        // TODO Auto-generated constructor stub
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: RaidViewCtrlCmd(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        logger.trace("Exiting constructor: RaidViewCtrlCmd()");
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.dell.pg.spectre.common.protocol.wsman.WSManBaseCommand#run()
     */
    @Override
    public List<RaidViewCtrlEntity> execute() throws Exception {
        logger.trace("Entering function: execute()");
        session.setResourceUri(getResourceURI());
        Document doc = this.sendRequestEnumerationReturnDocument();
        List<RaidViewCtrlEntity> raidConfig = this.getRaidConfig(doc);
        logger.trace("Exiting function: execute()");
        return raidConfig;
    }


    protected String getResourceURI() {
        String resourceURI = getBaseURI() + WSManClassEnum.DCIM_ControllerView.toString();
        return resourceURI;
    }


    private List<RaidViewCtrlEntity> getRaidConfig(Document doc) {

        Element element = doc.getDocumentElement();

        NodeList nodeList = element.getElementsByTagNameNS(WSCommandRNDConstant.WS_MAN_NAMESPACE, WSCommandRNDConstant.WSMAN_ITEMS_TAG);
        if (nodeList != null) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Element.ELEMENT_NODE) {
                    if (node.hasChildNodes()) {
                        return this.getCtrlList(node.getChildNodes());
                    }
                }
            }
        }

        return null;
    }


    private List<RaidViewCtrlEntity> getCtrlList(NodeList raidConfigNodeList) {

        List<RaidViewCtrlEntity> raidConfigList = new ArrayList<RaidViewCtrlEntity>();
        for (int j = 0; j < raidConfigNodeList.getLength(); j++) {
            Node raidConfigSettingNode = raidConfigNodeList.item(j);
            if (raidConfigSettingNode.getNodeType() == Element.ELEMENT_NODE) {

                NodeList childNodes = raidConfigSettingNode.getChildNodes();
                for (int i = 0; i < childNodes.getLength(); i++) {
                    Node raidConfigSettingChildNode = childNodes.item(i);
                    setRaidConfigSettingDetail(raidConfigSettingChildNode, raidConfigDetail);
                }
                raidConfigList.add(raidConfigDetail);
                raidConfigDetail = new RaidViewCtrlEntity();
            }
        }

        return raidConfigList;
    }


    private void setRaidConfigSettingDetail(Node node, RaidViewCtrlEntity raidConfigDtl) {

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
            raidConfigDtl.setFqddCtrl(value);
            return;
        }
        if (localNodeName.equalsIgnoreCase("ProductName")) {
            raidConfigDtl.setNameCtrl(value);
            return;
        }
        if (localNodeName.equalsIgnoreCase("PCISlot")) {
            raidConfigDtl.setPciSlotCtrl(value);
            return;
        }
        if (localNodeName.equalsIgnoreCase("SecurityStatus")) {
            if (value != null) {
                if (value.equalsIgnoreCase("1"))
                    raidConfigDtl.setSecStatusCtrl("Not Assigned");
                else if (value.equalsIgnoreCase("2"))
                    raidConfigDtl.setSecStatusCtrl("Assigned");
                else
                    raidConfigDtl.setSecStatusCtrl("Not Applicable");
            }
            return;
        }
    }

}
