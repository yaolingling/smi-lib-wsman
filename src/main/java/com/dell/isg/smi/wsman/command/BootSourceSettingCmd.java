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
import com.dell.isg.smi.wsman.command.entity.BootSourceSettingDetails;

/**
 *
 * @author Jaya
 *
 */
public class BootSourceSettingCmd extends WSManBaseCommand {
    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(BootSourceSettingCmd.class);


    public BootSourceSettingCmd(String ipAddr, String userName, String passwd) {
        // set the WSMan Session
        super(ipAddr, userName, passwd);

        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: BootSourceSettingCmd(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }

        session = this.getSession();
        session.setResourceUri(getResourceURI());

        logger.trace("Exiting constructor: BootSourceSettingCmd()");

    }


    private String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);

        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_BootSourceSetting);

        return sb.toString();
    }


    @Override
    public List<BootSourceSettingDetails> execute() throws Exception {
        super.getLogger(getClass()).info("Execute BootSourceSettingCmd ");
        logger.trace("Entering function: execute()");
        Document doc = this.sendRequestEnumerationReturnDocument();

        List<BootSourceSettingDetails> bootSourceSettingBySourceName = this.getBootSourceSettingBySourceName(doc, null);
        logger.trace("Exiting function: execute()");

        return bootSourceSettingBySourceName;

    }


    /**
     * The document will look like below. <?xml version="1.0" encoding="UTF-8"?> <wsen:EnumerateResponse xmlns:wsen="http://schemas.xmlsoap.org/ws/2004/09/enumeration">
     * <wsman:Items xmlns:wsman="http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd">
     * <n1:DCIM_BootSourceSetting xmlns:n1="http://schemas.dell.com/wbem/wscim/1/cim-schema/2/DCIM_BootSourceSetting"> <n1:BIOSBootString>VFLASH:1</n1:BIOSBootString>
     * <n1:BootString>VFLASH:1</n1:BootString> <n1:CurrentAssignedSequence>0</n1:CurrentAssignedSequence> <n1:CurrentEnabledStatus>0</n1:CurrentEnabledStatus>
     * <n1:ElementName>VFLASH:1</n1:ElementName> <n1:FailThroughSupported>0</n1:FailThroughSupported> <n1:InstanceID>vFlash:VFLASH:1</n1:InstanceID>
     * <n1:PendingAssignedSequence>0</n1:PendingAssignedSequence> <n1:PendingEnabledStatus>0</n1:PendingEnabledStatus> </n1:DCIM_BootSourceSetting> </wsman:Items>
     * <wsen:EnumerationContext/> <wsman:EndOfSequence xmlns:wsman="http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd"/> </wsen:EnumerateResponse>
     * 
     * @param doc
     * @param sourceName
     * @return
     */
    private List<BootSourceSettingDetails> getBootSourceSettingBySourceName(Document doc, String sourceName) {

        Element element = doc.getDocumentElement();

        NodeList nodeList = element.getElementsByTagNameNS(WSCommandRNDConstant.WS_MAN_NAMESPACE, WSCommandRNDConstant.WSMAN_ITEMS_TAG);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Element.ELEMENT_NODE) {
                if (node.hasChildNodes()) {
                    return getBootSourceBySourceName(node.getChildNodes(), sourceName);
                }
            }
        }

        return null;
    }


    private List<BootSourceSettingDetails> getBootSourceBySourceName(NodeList bootSourceSettingNodeList, String sourceName) {

        List<BootSourceSettingDetails> bootSourceSettingList = new ArrayList<BootSourceSettingDetails>();

        for (int j = 0; j < bootSourceSettingNodeList.getLength(); j++) {
            Node bootSourceSettingNode = bootSourceSettingNodeList.item(j);

            if (bootSourceSettingNode.getNodeType() == Element.ELEMENT_NODE) {
                BootSourceSettingDetails bootSourceSettingDetail = new BootSourceSettingDetails();

                NodeList childNodes = bootSourceSettingNode.getChildNodes();
                for (int i = 0; i < childNodes.getLength(); i++) {
                    Node bootSourceSettingChildNode = childNodes.item(i);
                    setBootSettingDetail(bootSourceSettingChildNode, bootSourceSettingDetail);
                }

                // If there is a given SourceName - filter by it.
                if (StringUtils.isNotBlank(sourceName) && StringUtils.isNotEmpty(sourceName)) {
                    if (StringUtils.contains(bootSourceSettingDetail.getInstanceId(), sourceName)) {
                        bootSourceSettingList.add(bootSourceSettingDetail);
                    }
                } else { // If there is no sourcename given , add the bootSourceSetting without filtering/checking.
                    bootSourceSettingList.add(bootSourceSettingDetail);
                }
            }
        }

        return bootSourceSettingList;
    }


    private void setBootSettingDetail(Node node, BootSourceSettingDetails bootSourceSettingDetail) {

        String localNodeName = node.getLocalName();
        String value = getNodeValue(node);

        // Check the node name and set the value for it in the object
        setBootSourceSettings(localNodeName, value, bootSourceSettingDetail);

    }


    private void setBootSourceSettings(String localNodeName, String value, BootSourceSettingDetails bootSourceSettingDetail) {

        if (StringUtils.isNotBlank(value) && StringUtils.isNotEmpty(value)) {

            if (StringUtils.equalsIgnoreCase(localNodeName, WSCommandRNDConstant.BOOTSOURCE_BIOS_BOOT_STRING)) {
                bootSourceSettingDetail.setBiosBootString(value);
                return;
            }

            if (StringUtils.equalsIgnoreCase(localNodeName, WSCommandRNDConstant.BOOTSOURCE_BOOT_STRING)) {
                bootSourceSettingDetail.setBootString(value);
                return;
            }

            if (StringUtils.equalsIgnoreCase(localNodeName, WSCommandRNDConstant.BOOTSOURCE_CURRENT_ASSIGNED_SEQ)) {
                bootSourceSettingDetail.setCurrentSequence(Integer.parseInt(value));
                return;
            }

            if (StringUtils.equalsIgnoreCase(localNodeName, WSCommandRNDConstant.BOOTSOURCE_CURRENT_ENABLED_STATUS)) {
                bootSourceSettingDetail.setCurrentEnabledState(Integer.parseInt(value));
                return;
            }

            if (StringUtils.equalsIgnoreCase(localNodeName, WSCommandRNDConstant.BOOTSOURCE_ELEMENT_NAME)) {
                bootSourceSettingDetail.setElementName(value);
                return;
            }

            if (StringUtils.equalsIgnoreCase(localNodeName, WSCommandRNDConstant.BOOTSOURCE_FAIL_THROUGH_SUPPORTED)) {
                bootSourceSettingDetail.setFailThroughSupported(Integer.parseInt(value));
                return;
            }

            if (StringUtils.equalsIgnoreCase(localNodeName, WSCommandRNDConstant.BOOTSOURCE_INSTANCE_ID)) {
                bootSourceSettingDetail.setInstanceId(value);
                return;
            }

            if (StringUtils.equalsIgnoreCase(localNodeName, WSCommandRNDConstant.BOOTSOURCE_PENDING_ENABLED_SEQ)) {
                bootSourceSettingDetail.setPendingAssignedSequence(Integer.parseInt(value));
                return;
            }

            if (StringUtils.equalsIgnoreCase(localNodeName, WSCommandRNDConstant.BOOTSOURCE_PENDING_ENABLED_STATUS)) {
                bootSourceSettingDetail.setPendingEnabledState(Integer.parseInt(value));
                return;
            }

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