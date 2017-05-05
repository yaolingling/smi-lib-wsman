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
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.command.entity.BootConfigSetting;

public class BootConfigSettingCmd extends WSManBaseCommand {
    private WSManageSession session = null;

    private static final Logger logger = LoggerFactory.getLogger(BootConfigSettingCmd.class);


    public BootConfigSettingCmd(String ipAddr, String userName, String passwd) {
        super(ipAddr, userName, passwd);
        logger.trace(String.format("Entering constructor: BootConfigSettingCmd(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));

        session = this.getSession();

        logger.trace("Exiting constructor:  BootConfigSettingCmd()");
    }


    public String getResourceURI() {
        StringBuilder sb = new StringBuilder();
        sb.append(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE);
        sb.append(WSManClassEnum.DCIM_BootConfigSetting);
        return sb.toString();
    }


    @Override
    public List<BootConfigSetting> execute() throws Exception {
        logger.trace("Entering function: execute()");
        session.setResourceUri(getResourceURI());

        Document doc = this.sendRequestEnumerationReturnDocument();
        if (null != doc) {
            logger.info(" document returned is " + doc);
        }

        List<BootConfigSetting> bootConfigSetting = getBootConfigSetting(doc);

        logger.trace("Exiting function: execute()");
        return bootConfigSetting;
    }


    private List<BootConfigSetting> getBootConfigSetting(Document document) {

        logger.debug("Entering the getBootConfigSetting ");

        Element element = document.getDocumentElement();

        NodeList nodeList = element.getElementsByTagNameNS(WSCommandRNDConstant.WS_MAN_NAMESPACE, WSCommandRNDConstant.WSMAN_ITEMS_TAG);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Element.ELEMENT_NODE) {
                if (node.hasChildNodes()) {
                    return getBootConfigSetting(node.getChildNodes());
                }
            }
        }

        return null;
    }


    private List<BootConfigSetting> getBootConfigSetting(NodeList bootConfigSettingNodeList) {

        logger.debug("Entering the getBootConfigSetting " + bootConfigSettingNodeList);

        List<BootConfigSetting> bootConfigSettingList = new ArrayList<BootConfigSetting>();

        for (int j = 0; j < bootConfigSettingNodeList.getLength(); j++) {
            Node bootConfigSettingNode = bootConfigSettingNodeList.item(j);

            if (bootConfigSettingNode.getNodeType() == Element.ELEMENT_NODE) {
                BootConfigSetting bootConfigSettingDetail = new BootConfigSetting();

                NodeList childNodes = bootConfigSettingNode.getChildNodes();
                for (int i = 0; i < childNodes.getLength(); i++) {
                    Node bootConfigSettingChildNode = childNodes.item(i);
                    setBootConfigDetail(bootConfigSettingChildNode, bootConfigSettingDetail);
                }

                bootConfigSettingList.add(bootConfigSettingDetail);
            }
        }

        logger.debug("Exiting the getBootConfigSetting " + bootConfigSettingNodeList);

        return bootConfigSettingList;
    }


    private void setBootConfigDetail(Node node, BootConfigSetting bootConfigDetail) {

        String localNodeName = node.getLocalName();
        String value = getNodeValue(node);

        if (StringUtils.equalsIgnoreCase(localNodeName, WSCommandRNDConstant.BOOTCONFIG_ELEMENT_NAME)) {
            bootConfigDetail.setElementName(value);
            return;
        }

        if (StringUtils.equalsIgnoreCase(localNodeName, WSCommandRNDConstant.BOOTCONFIG_INSTANCE_ID)) {
            bootConfigDetail.setInstanceID(value);
            return;
        }

        if (StringUtils.equalsIgnoreCase(localNodeName, WSCommandRNDConstant.BOOTCONFIG_IS_CURRENT)) {
            bootConfigDetail.setIsCurrent(value);
            return;
        }

        if (StringUtils.equalsIgnoreCase(localNodeName, WSCommandRNDConstant.BOOTCONFIG_IS_DEFAULT)) {
            bootConfigDetail.setIsDefault(value);
            return;
        }

        if (StringUtils.equalsIgnoreCase(localNodeName, WSCommandRNDConstant.BOOTCONFIG_IS_NEXT)) {
            bootConfigDetail.setIsNext(value);
            return;
        }
    }


    private String getNodeValue(Node node) {

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

        return value;

    }

}
