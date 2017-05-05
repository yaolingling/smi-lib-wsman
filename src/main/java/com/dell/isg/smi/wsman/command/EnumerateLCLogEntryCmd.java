/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPathConstants;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.command.entity.LcLogEntry;
import com.sun.ws.management.addressing.Addressing;

public class EnumerateLCLogEntryCmd extends WSManBaseCommand {

    private WSManageSession session = null;


    public EnumerateLCLogEntryCmd(String ipAddr, String userName, String passwd) {

        super(ipAddr, userName, passwd);
        this.session = super.getSession();
        this.session.setResourceUri(getResourceURI());
    }


    @Override
    public List<LcLogEntry> execute() throws Exception {

        Addressing addressing = this.sendRequestEnumeration();
        NodeList nodeset = (NodeList) session.findObjectInDocument(addressing.getBody(), "//*[local-name()='DCIM_LCLogEntry']", XPathConstants.NODESET);
        List<LcLogEntry> lcLogEntryList = new ArrayList<LcLogEntry>();
        LcLogEntry lcLogEntry = null;

        for (int i = 0; i < nodeset.getLength(); i++) {

            lcLogEntry = new LcLogEntry();
            Node node = nodeset.item(i);
            NodeList nodeList = node.getChildNodes();

            for (int j = 0; j < nodeList.getLength(); j++) {
                Node itemNode = nodeList.item(j);
                String nodeValue = itemNode.getTextContent();

                if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "CreationTimeStamp")) {
                    lcLogEntry.setCreationTimeStamp(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "ElementName")) {
                    lcLogEntry.setElementName(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "InstanceID")) {
                    lcLogEntry.setInstanceId(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "LogInstanceID")) {
                    lcLogEntry.setLogInstanceId(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "LogName")) {
                    lcLogEntry.setLogName(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "PerceivedSeverity")) {
                    lcLogEntry.setSeverity(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "RecordID")) {
                    lcLogEntry.setRecordId(Long.parseLong(nodeValue));
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "AgentID")) {
                    lcLogEntry.setAgentId(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Category")) {
                    lcLogEntry.setCategory(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Comment")) {
                    lcLogEntry.setComment(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "ConfigResultsAvailable")) {
                    lcLogEntry.setConfigResultsAvailable(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "FQDD")) {
                    lcLogEntry.setFqdd(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Message")) {
                    lcLogEntry.setMessage(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "MessageArguments")) {
                    lcLogEntry.setMessageArguments(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "MessageID")) {
                    lcLogEntry.setMessageId(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "OwningEntity")) {
                    lcLogEntry.setOwningEntity(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "RawEventData")) {
                    lcLogEntry.setRawEventData(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "SequenceNumber")) {
                    lcLogEntry.setSequenceNumber(Long.parseLong(nodeValue));
                }
            }
            lcLogEntryList.add(lcLogEntry);
        }
        return lcLogEntryList;
    }


    private String getResourceURI() {

        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_LCLogEntry);
        return sb.toString();
    }

}
