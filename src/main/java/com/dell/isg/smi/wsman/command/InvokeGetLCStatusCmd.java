/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.tuple.Pair;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.wsmanclient.WSCommandRNDConstant;
import com.dell.isg.smi.wsmanclient.WSManException;
import com.dell.isg.smi.wsmanclient.util.WSManUtils;
import com.dell.isg.smi.wsman.model.LCStatus;
import com.dell.isg.smi.wsman.model.LCStatus.LCSTATUS;
import com.dell.isg.smi.wsman.model.LCStatus.RETURNSTATUS;
import com.dell.isg.smi.wsman.model.LCStatus.SERVERSTATUS;
import com.dell.isg.smi.wsman.model.LCStatus.STATUS;

public class InvokeGetLCStatusCmd extends com.dell.isg.smi.wsmanclient.WSManBaseInvokeCommand<LCStatus> {

    private static final Logger logger = LoggerFactory.getLogger(InvokeGetLCStatusCmd.class);


    public InvokeGetLCStatusCmd() {
        super(com.dell.isg.smi.wsmanclient.WSManConstants.WSManInvokableEnum.DCIM_LCService, WSCommandRNDConstant.GetRemoteServicesAPIStatus);
    }


    @Override
    public LCStatus parse(String xml) throws WSManException {

        // System.out.println(xml);
        LCStatus status = new LCStatus();
        Document doc = null;
        try {
            doc = WSManUtils.toDocument(xml);
        } catch (Exception e) {
            String msg = "Unable to create NICView document from xml result.";
            logger.error(msg, e);
            throw new WSManException(msg);
        }

        // Create a nodelist.
        Element element = doc.getDocumentElement();
        NodeList nodeList = null;
        try {
            nodeList = element.getElementsByTagNameNS("http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/root/dcim/DCIM_LCService", "GetRemoteServicesAPIStatus_OUTPUT");
        } catch (DOMException e) {
            String msg = "Unable to extract nodelist from NICView document.";
            logger.error(msg, e);
            throw new WSManException(msg);
        }
        if (nodeList.getLength() == 1) // nodeList.get(0) is pointer to the root element
        {
            NodeList lcAttrnodeList = nodeList.item(0).getChildNodes();
            for (int i = 0; i < lcAttrnodeList.getLength(); i++) {
                Node node = lcAttrnodeList.item(i);
                if (node != null && node.getNodeType() == Element.ELEMENT_NODE && node.getLocalName() != null) {
                    String sValue = getValue(node);
                    if (sValue == null) {
                        continue;
                    }
                    if (node.getLocalName().compareToIgnoreCase("LCStatus") == 0) {
                        if (sValue.compareToIgnoreCase("0") == 0)
                            status.setLCStatus(LCSTATUS.READY);
                        else if (sValue.compareToIgnoreCase("1") == 0)
                            status.setLCStatus(LCSTATUS.NOT_INITIALIZED);
                        else if (sValue.compareToIgnoreCase("2") == 0)
                            status.setLCStatus(LCSTATUS.RELOADING);
                        else if (sValue.compareToIgnoreCase("3") == 0)
                            status.setLCStatus(LCSTATUS.DISABLED);
                        else if (sValue.compareToIgnoreCase("4") == 0)
                            status.setLCStatus(LCSTATUS.IN_RECOVERY);
                        else if (sValue.compareToIgnoreCase("5") == 0)
                            status.setLCStatus(LCSTATUS.IN_USE);
                    } else if (node.getLocalName().compareToIgnoreCase("Message") == 0) {
                        status.setMessage(sValue);
                    } else if (node.getLocalName().compareToIgnoreCase("MessageID") == 0) {
                        status.setMessageID(sValue);
                    } else if (node.getLocalName().compareToIgnoreCase("ServerStatus") == 0) {
                        if (sValue.compareToIgnoreCase("0") == 0)
                            status.setServerStatus(SERVERSTATUS.OFF);
                        else if (sValue.compareToIgnoreCase("1") == 0)
                            status.setServerStatus(SERVERSTATUS.IN_POST);
                        else if (sValue.compareToIgnoreCase("2") == 0)
                            status.setServerStatus(SERVERSTATUS.OUT_OF_POST);
                        else if (sValue.compareToIgnoreCase("3") == 0)
                            status.setServerStatus(SERVERSTATUS.COLLECTING_INVENTORY);
                        else if (sValue.compareToIgnoreCase("4") == 0)
                            status.setServerStatus(SERVERSTATUS.RUNNING_TASKS);
                        else if (sValue.compareToIgnoreCase("5") == 0)
                            status.setServerStatus(SERVERSTATUS.In_USC);
                        else if (sValue.compareToIgnoreCase("6") == 0)
                            status.setServerStatus(SERVERSTATUS.POST_ERROR);
                        else if (sValue.compareToIgnoreCase("7") == 0)
                            status.setServerStatus(SERVERSTATUS.NO_BOOT_DEVICES);
                        else if (sValue.compareToIgnoreCase("8") == 0)
                            status.setServerStatus(SERVERSTATUS.IN_F2_SETUP);
                        else if (sValue.compareToIgnoreCase("9") == 0)
                            status.setServerStatus(SERVERSTATUS.IN_F11_BOOTMGR);
                    }
                }

                status.setReturnValue(RETURNSTATUS.SUCCESSFUL);
                status.setStatus(STATUS.READY);
            }

        }

        return status;
    }


    private String getValue(Node childNode) {
        if (childNode.hasChildNodes()) {
            NodeList nodeList1 = childNode.getChildNodes();
            for (int k = 0; k < nodeList1.getLength(); k++) {
                Node finalNode = nodeList1.item(k);
                if (Element.TEXT_NODE == finalNode.getNodeType()) {
                    return finalNode.getNodeValue();
                }
            }
        }
        return null;
    }


    @Override
    public List<Pair<String, String>> getUserParams() {
        return new ArrayList<Pair<String, String>>();
    }


    @Override
    public String getFilterWQL() {
        return null;
    }
}
