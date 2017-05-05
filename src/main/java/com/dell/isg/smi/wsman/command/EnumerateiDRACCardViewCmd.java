/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.wsmanclient.WSManException;
import com.dell.isg.smi.wsmanclient.util.WSManUtils;
import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManClientBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.command.entity.IDracCardView;

public class EnumerateiDRACCardViewCmd extends WSManClientBaseCommand<IDracCardView> {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(EnumerateiDRACCardViewCmd.class);


    public EnumerateiDRACCardViewCmd() {
    }


    /**
     * @param ipAddr
     * @param userName
     * @param passwd
     */
    public EnumerateiDRACCardViewCmd(String ipAddr, String userName, String passwd) {
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: EnumerateiDRACCardView(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        session = this.getSession();
        session.setResourceUri(getResourceURI());
        logger.trace("Exiting constructor: EnumerateiDRACCardView()");
    }


    public String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_IDRACCardView);

        return sb.toString();
    }


    @Override
    public IDracCardView execute() throws Exception {
        logger.trace("Entering function: execute()");
        NodeList response = this.sendRequestEnumerationReturnNodeList();
        IDracCardView idracCardView = parse(response);
        logger.trace("Exiting function: execute()");
        return idracCardView;
    }


    @Override
    public IDracCardView parse(String xml) throws Exception {

        // Create the document from the xml.
        IDracCardView idracCardView = null;
        Document doc;
        try {
            doc = WSManUtils.toDocument(xml);
        } catch (Exception e) {
            String msg = "Unable to create IDracCardView document from xml result.";
            logger.error(msg, e);
            throw new WSManException(msg);
        }

        // Create a nodelist.
        Element element = doc.getDocumentElement();
        NodeList nodeList;
        try {
            nodeList = element.getElementsByTagNameNS(WSCommandRNDConstant.WS_MAN_NAMESPACE, WSCommandRNDConstant.WSMAN_ITEMS_TAG);
        } catch (DOMException e) {
            String msg = "Unable to extract nodelist from IDracCardView document.";
            logger.error(msg, e);
            throw new WSManException(msg);
        }
        if (nodeList == null)
            return idracCardView;
        return parse(nodeList);
    }


    private IDracCardView parse(NodeList response) throws Exception {

        IDracCardView idracCardView = null;
        if (null != response) {

            idracCardView = new IDracCardView();

            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xpath = xPathFactory.newXPath();

            Node itemNode = response.item(0);

            idracCardView.setFirmwareVersion(xpath.evaluate("//*[local-name()='FirmwareVersion']/text()", itemNode));
            idracCardView.setFqdd(xpath.evaluate("//*[local-name()='FQDD']/text()", itemNode));
            idracCardView.setGuid(xpath.evaluate("//*[local-name()='GUID']/text()", itemNode));
            idracCardView.setInstanceID(xpath.evaluate("//*[local-name()='InstanceID']/text()", itemNode));
            idracCardView.setiPMIVersion(xpath.evaluate("//*[local-name()='IPMIVersion']/text()", itemNode));
            idracCardView.setlANEnabledState(xpath.evaluate("//*[local-name()='LANEnabledState']/text()", itemNode));
            idracCardView.setLastSystemInventoryTime(xpath.evaluate("//*[local-name()='LastSystemInventoryTime']/text()", itemNode));
            idracCardView.setLastUpdateTime(xpath.evaluate("//*[local-name()='LastUpdateTime']/text()", itemNode));
            idracCardView.setModel(xpath.evaluate("//*[local-name()='Model']/text()", itemNode));
            idracCardView.setPermanentMACAddress(xpath.evaluate("//*[local-name()='PermanentMACAddress']/text()", itemNode));
            idracCardView.setProductDescription(xpath.evaluate("//*[local-name()='ProductDescription']/text()", itemNode));
            idracCardView.setSolEnabledState(xpath.evaluate("//*[local-name()='SOLEnabledState']/text()", itemNode));
            idracCardView.setUrlString(xpath.evaluate("//*[local-name()='URLString']/text()", itemNode));

        }

        return idracCardView;
    }

}
