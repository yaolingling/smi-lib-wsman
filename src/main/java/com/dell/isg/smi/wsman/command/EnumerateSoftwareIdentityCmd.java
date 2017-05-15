/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
/**
 * 
 */
package com.dell.isg.smi.wsman.command;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.commons.utilities.xml.XmlHelper;
import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManClientBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.command.entity.DCIMSoftwareIdentityType;
import com.dell.isg.smi.wsmanclient.util.WSManUtils;

/**
 * @author jayalakshmi_shankara
 *
 */
public class EnumerateSoftwareIdentityCmd extends WSManClientBaseCommand<List<DCIMSoftwareIdentityType>> {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(EnumerateSoftwareIdentityCmd.class);


    public EnumerateSoftwareIdentityCmd() {
    }


    /**
     * @param ipAddr
     * @param userName
     * @param passwd
     */
    public EnumerateSoftwareIdentityCmd(String ipAddr, String userName, String passwd) {
        super(ipAddr, userName, passwd);
        // TODO Auto-generated constructor stub
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: EnumerateSoftwareIdentityCmd(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        this.session = super.getSession();

        this.session.setResourceUri(getResourceURI());

        logger.trace("Exiting constructor: EnumerateSoftwareIdentityCmd()");
    }


    public String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);

        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_SoftwareIdentity);

        return sb.toString();
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.dell.pg.spectre.common.protocol.wsman.WSManBaseCommand#execute()
     */
    @Override
    public List<DCIMSoftwareIdentityType> execute() throws Exception {
        logger.trace("Entering function: execute()");
        NodeList nodeList = this.sendRequestEnumerationReturnNodeList();
        List<DCIMSoftwareIdentityType> softwareIdentityTypeList = parse(nodeList);
        logger.trace("Exiting function: execute()");
        return softwareIdentityTypeList;
    }


    @Override
    public List<DCIMSoftwareIdentityType> parse(String xml) throws Exception {
        Document response = WSManUtils.toDocument(xml);
        Element element = response.getDocumentElement();
        NodeList nodeList = element.getElementsByTagNameNS(WSCommandRNDConstant.WS_MAN_NAMESPACE, WSCommandRNDConstant.WSMAN_ITEMS_TAG);
        List<DCIMSoftwareIdentityType> softwareIdentityTypeList = parse(nodeList);
        return softwareIdentityTypeList;
    }


    public List<DCIMSoftwareIdentityType> parse(NodeList nodeList) throws Exception {
        List<DCIMSoftwareIdentityType> softwareIdentityTypeList = new ArrayList<DCIMSoftwareIdentityType>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Element.ELEMENT_NODE) {
                if (node.hasChildNodes()) {

                    NodeList softwareIdentityList = node.getChildNodes();
                    for (int j = 0; j < softwareIdentityList.getLength(); j++) {

                        Node softwareIdentityNode = softwareIdentityList.item(j);

                        DCIMSoftwareIdentityType softwareIdentityType = (DCIMSoftwareIdentityType) XmlHelper.xmlToObject(softwareIdentityNode, DCIMSoftwareIdentityType.class);

                        softwareIdentityTypeList.add(softwareIdentityType);

                    }
                }
            }
        }
        return softwareIdentityTypeList;
    }

}
