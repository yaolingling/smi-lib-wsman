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

import com.dell.isg.smi.commons.elm.utilities.xml.XmlHelper;
import com.dell.isg.smi.wsmanclient.util.WSManUtils;
import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManClientBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.command.entity.DCIMBIOSEnumerationType;

public class EnumerateBIOSAttributeCmd extends WSManClientBaseCommand<List<DCIMBIOSEnumerationType>> {

    private String attributeName = null;
    private static final Logger logger = LoggerFactory.getLogger(EnumerateBIOSAttributeCmd.class);


    public EnumerateBIOSAttributeCmd() {
    }

    private WSManageSession session = null;


    // If the attributeName is null, all the Bios attributes will be returned . Otherwise
    // it returns only the attribute that is needed.
    public EnumerateBIOSAttributeCmd(String ipAddr, String userName, String passwd, String attributeName) {

        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: EnumerateBIOSAttributeCmd(String ipAddr - %s, String userName - %s, String passwd - %s, String attributeName)", ipAddr, userName, "####", attributeName));
        }
        session = super.getSession();
        this.attributeName = attributeName;

        session.setResourceUri(getResourceURI());
        logger.trace("Exiting constructor: EnumerateBIOSAttributeCmd()");

    }


    /*
     * (non-Javadoc)
     * 
     * @see com.dell.pg.spectre.common.protocol.command.wsman.IWSManClientCommand#getResourceURI()
     */
    @Override
    public String getResourceURI() {

        StringBuilder sb = new StringBuilder();
        sb.append(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE);
        sb.append(WSManClassEnum.DCIM_BIOSEnumeration);
        return sb.toString();

    }


    @Override
    public List<DCIMBIOSEnumerationType> execute() throws Exception {
        logger.trace("Entering function: execute()");
        Document response = this.sendRequestEnumerationReturnDocument();
        List<DCIMBIOSEnumerationType> list = parseDocument(response);
        logger.trace("Exiting function: execute()");
        return list;
    }


    private List<DCIMBIOSEnumerationType> parseDocument(Document response) throws Exception {
        logger.trace("Entering function: parseDocument()");

        List<DCIMBIOSEnumerationType> biosEnumTypeList = new ArrayList<DCIMBIOSEnumerationType>();
        boolean isAttributeNull = StringUtils.isBlank(attributeName) || StringUtils.isEmpty(attributeName) ? true : false;

        Element element = response.getDocumentElement();
        NodeList nodeList = element.getElementsByTagNameNS(WSCommandRNDConstant.WS_MAN_NAMESPACE, WSCommandRNDConstant.WSMAN_ITEMS_TAG);

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Element.ELEMENT_NODE) {
                if (node.hasChildNodes()) {

                    NodeList biosAttributesNodeList = node.getChildNodes();

                    for (int j = 0; j < biosAttributesNodeList.getLength(); j++) {

                        Node biosAttributesNode = biosAttributesNodeList.item(j);

                        DCIMBIOSEnumerationType dcimBiosEnum = (DCIMBIOSEnumerationType) XmlHelper.xmlToObject(biosAttributesNode, DCIMBIOSEnumerationType.class);

                        if (isAttributeNull) {
                            biosEnumTypeList.add(dcimBiosEnum);
                        } else if (StringUtils.equalsIgnoreCase(dcimBiosEnum.getAttributeName().getValue(), attributeName)) {
                            biosEnumTypeList.add(dcimBiosEnum);

                            logger.trace("Exiting function: parseDocument()");
                            return biosEnumTypeList;
                        }
                    }
                }
            }
        }

        logger.trace("Exiting function: parseDocument()");
        return biosEnumTypeList;
    }


    public String getAttributeName() {
        return attributeName;
    }


    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }


    @Override
    public List<DCIMBIOSEnumerationType> parse(String xml) throws Exception {
        Document response = WSManUtils.toDocument(xml);
        return parseDocument(response);
    }
}
