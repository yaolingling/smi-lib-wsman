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
import com.dell.isg.smi.wsman.command.entity.SelLogEntry;
import com.sun.ws.management.addressing.Addressing;

public class EnumerateSelLogEntryCmd extends WSManBaseCommand {

    private WSManageSession session = null;


    public EnumerateSelLogEntryCmd(String ipAddr, String userName, String passwd) {

        super(ipAddr, userName, passwd);

        this.session = super.getSession();

        this.session.setResourceUri(getResourceURI());

    }


    @Override
    public List<SelLogEntry> execute() throws Exception {

        Addressing addressing = this.sendRequestEnumeration();

        NodeList nodeset = (NodeList) session.findObjectInDocument(addressing.getBody(), "//*[local-name()='DCIM_Sellogentry']", XPathConstants.NODESET);

        List<SelLogEntry> selLogEntryList = new ArrayList<SelLogEntry>();

        SelLogEntry selLogEntry = null;

        for (int i = 0; i < nodeset.getLength(); i++) {
            selLogEntry = new SelLogEntry();

            Node node = nodeset.item(i);

            NodeList nodeList = node.getChildNodes();

            for (int j = 0; j < nodeList.getLength(); j++) {
                Node itemNode = nodeList.item(j);

                String nodeValue = itemNode.getTextContent();

                if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "CreationTimeStamp")) {
                    selLogEntry.setCreationTimeStamp(nodeValue);
                }

                if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "ElementName")) {
                    selLogEntry.setElementName(nodeValue);
                }

                if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "InstanceID")) {
                    selLogEntry.setInstanceID(nodeValue);
                }

                if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "LogInstanceID")) {
                    selLogEntry.setLogInstanceID(nodeValue);
                }

                if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "LogName")) {
                    selLogEntry.setLogName(nodeValue);
                }

                if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "PerceivedSeverity")) {
                    selLogEntry.setPerceivedSeverity(nodeValue);
                }

                if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "RecordData")) {
                    selLogEntry.setRecordData(nodeValue);
                }

                if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "RecordFormat")) {
                    selLogEntry.setRecordFormat(nodeValue);
                }

                if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "RecordID")) {
                    selLogEntry.setRecordID(nodeValue);
                }

            }

            selLogEntryList.add(selLogEntry);
        }

        return selLogEntryList;
    }


    private String getResourceURI() {

        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);

        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_Sellogentry);

        return sb.toString();
    }

}
