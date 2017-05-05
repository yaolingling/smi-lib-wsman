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
import com.dell.isg.smi.wsman.command.entity.DCIMNICViewType;

public class EnumerateNICView extends WSManClientBaseCommand<List<DCIMNICViewType>> {
    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(EnumerateNICView.class);


    public EnumerateNICView() {
    }


    public EnumerateNICView(String ipAddr, String userName, String passwd) {
        // set the WSMan Session
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: EnumerateNICView(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        session = this.getSession();
        session.setResourceUri(getResourceURI());

        logger.trace("Exiting constructor: EnumerateNICView()");
    }


    public String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_NICView);

        return sb.toString();
    }


    @Override
    public List<DCIMNICViewType> execute() throws Exception {
        logger.trace("Entering function: execute()");
        super.getLogger(this.getClass()).debug("Entering execute() method ");
        NodeList nodeList = this.sendRequestEnumerationReturnNodeList();
        List<DCIMNICViewType> dcimViewList = parse(nodeList);
        super.getLogger(this.getClass()).debug("Exiting execute() method ");
        logger.trace("Exiting function: execute()");
        return dcimViewList;
    }


    @Override
    public List<DCIMNICViewType> parse(String xml) throws Exception {
        // Create the document from the xml.
        List<DCIMNICViewType> dcimViewList = null;
        Document doc;
        try {
            doc = WSManUtils.toDocument(xml);
        } catch (Exception e) {
            String msg = "Unable to create DCIMNICViewType document from xml result.";
            logger.error(msg, e);
            throw new WSManException(msg);
        }

        // Create a nodelist.
        Element element = doc.getDocumentElement();
        NodeList nodeList;
        try {
            nodeList = element.getElementsByTagNameNS(WSCommandRNDConstant.WS_MAN_NAMESPACE, WSCommandRNDConstant.WSMAN_ITEMS_TAG);
        } catch (DOMException e) {
            String msg = "Unable to extract nodelist from DCIMNICViewType document.";
            logger.error(msg, e);
            throw new WSManException(msg);
        }
        if (nodeList == null)
            return dcimViewList;
        dcimViewList = parse(nodeList);
        return dcimViewList;
    }


    private List<DCIMNICViewType> parse(NodeList nodeList) throws Exception {
        List<DCIMNICViewType> dcimViewList = new ArrayList<DCIMNICViewType>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Element.ELEMENT_NODE) {
                if (node.hasChildNodes()) {

                    NodeList nicViewNodeList = node.getChildNodes();
                    for (int j = 0; j < nicViewNodeList.getLength(); j++) {

                        Node NicViewNode = nicViewNodeList.item(j);
                        DCIMNICViewType viewType = (DCIMNICViewType) XmlHelper.xmlToObject(NicViewNode, DCIMNICViewType.class);
                        dcimViewList.add(viewType);

                    }
                }
            }
        }
        return dcimViewList;
    }

}
