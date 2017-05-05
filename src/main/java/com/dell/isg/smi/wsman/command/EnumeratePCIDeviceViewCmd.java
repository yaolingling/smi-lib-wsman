/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.commons.elm.utilities.xml.XmlHelper;
import com.dell.isg.smi.wsmanclient.WSManException;
import com.dell.isg.smi.wsmanclient.util.WSManUtils;
import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManClientBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.command.entity.DCIMPCIDeviceViewType;

public class EnumeratePCIDeviceViewCmd extends WSManClientBaseCommand<List<DCIMPCIDeviceViewType>> {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(EnumeratePCIDeviceViewCmd.class);


    public EnumeratePCIDeviceViewCmd() {
    }


    public EnumeratePCIDeviceViewCmd(String ipAddr, String userName, String passwd) {
        super(ipAddr, userName, passwd);

        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: EnumeratePCIDeviceViewCmd(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        session = this.getSession();
        session.setResourceUri(getResourceURI());

        logger.trace("Exiting constructor: EnumeratePCIDeviceViewCmd()");

    }


    public String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_PCIDeviceView);

        return sb.toString();
    }


    @Override
    public List<DCIMPCIDeviceViewType> execute() throws Exception {
        NodeList response = this.sendRequestEnumerationReturnNodeList();
        List<DCIMPCIDeviceViewType> pciDeviceViewList = parse(response);
        return pciDeviceViewList;
    }


    @Override
    public List<DCIMPCIDeviceViewType> parse(String xml) throws Exception {
        List<DCIMPCIDeviceViewType> pciDeviceViewList = new ArrayList<DCIMPCIDeviceViewType>();
        if (xml == null)
            return pciDeviceViewList;

        // Create the document from the xml.
        Document doc;
        try {
            doc = WSManUtils.toDocument(xml);
        } catch (Exception e) {
            String msg = "Unable to create PCIDeviceView document from xml result.";
            logger.error(msg, e);
            throw new WSManException(msg);
        }

        // Create a nodelist.
        Element element = doc.getDocumentElement();
        NodeList nodeList;
        try {
            nodeList = element.getElementsByTagNameNS(WSCommandRNDConstant.WS_MAN_NAMESPACE, WSCommandRNDConstant.WSMAN_ITEMS_TAG);
        } catch (DOMException e) {
            String msg = "Unable to extract nodelist from PCIDeviceView document.";
            logger.error(msg, e);
            throw new WSManException(msg);
        }
        if (nodeList == null)
            return pciDeviceViewList;

        pciDeviceViewList = parse(nodeList);
        return pciDeviceViewList;
    }


    public List<DCIMPCIDeviceViewType> parse(NodeList response) throws Exception {
        List<DCIMPCIDeviceViewType> pciDeviceViewList = new ArrayList<DCIMPCIDeviceViewType>();
        if (null != response) {
            for (int i = 0; i < response.getLength(); i++) {
                Node itemsNode = response.item(i);
                if (itemsNode.getNodeType() == Node.ELEMENT_NODE) {
                    if (itemsNode.hasChildNodes()) {
                        NodeList nodeList = itemsNode.getChildNodes();
                        for (int j = 0; j < nodeList.getLength(); j++) {
                            Node itemNode = nodeList.item(j);
                            DCIMPCIDeviceViewType pciDeviceType = (DCIMPCIDeviceViewType) XmlHelper.xmlToObject(itemNode, DCIMPCIDeviceViewType.class);
                            pciDeviceViewList.add(pciDeviceType);
                        }
                    }
                }
            }
        }
        return pciDeviceViewList;
    }
}
