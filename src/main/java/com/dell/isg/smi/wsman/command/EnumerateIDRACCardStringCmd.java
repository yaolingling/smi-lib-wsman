/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import java.util.ArrayList;
import java.util.List;

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
import com.dell.isg.smi.wsman.command.entity.IDRACCardStringView;

public class EnumerateIDRACCardStringCmd extends WSManClientBaseCommand<List<IDRACCardStringView>> {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(EnumerateIDRACCardStringCmd.class);


    public EnumerateIDRACCardStringCmd() {
    }


    public EnumerateIDRACCardStringCmd(String ipAddr, String userName, String passwd) {

        // set the WSMan Session
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: EnumerateIDRACCardStringCmd(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        session = this.getSession();
        session.setResourceUri(getResourceURI());
        logger.trace("Exiting constructor: EnumerateIDRACCardStringCmd()");
    }


    // AttributeDisplayName = LDAP Bind Password
    // AttributeName = BindPassword
    // CurrentValue = ******
    // DefaultValue = null
    // Dependency = null
    // DisplayOrder = 1089
    // FQDD = iDRAC.Embedded.1
    // GroupDisplayName = LDAP
    // GroupID = LDAP.1
    // InstanceID = iDRAC.Embedded.1#LDAP.1#BindPassword
    // IsReadOnly = false
    // MaxLength = 254
    // MinLength = 0
    // PendingValue = null

    @Override
    public List<IDRACCardStringView> execute() throws Exception {
        NodeList response = this.sendRequestEnumerationReturnNodeList();
        List<IDRACCardStringView> iDracCardViewList = parse(response);
        return iDracCardViewList;
    }


    public String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_iDRACCardString);
        return sb.toString();
    }


    @Override
    public List<IDRACCardStringView> parse(String xml) throws Exception {
        List<IDRACCardStringView> iDracCardViewList = new ArrayList<IDRACCardStringView>();
        if (xml == null)
            return iDracCardViewList;

        // Create the document from the xml.
        Document doc;
        try {
            doc = WSManUtils.toDocument(xml);
        } catch (Exception e) {
            String msg = "Unable to create IDracCardSTringView document from xml result.";
            logger.error(msg, e);
            throw new WSManException(msg);
        }

        // Create a nodelist.
        Element element = doc.getDocumentElement();
        NodeList nodeList;
        try {
            nodeList = element.getElementsByTagNameNS(WSCommandRNDConstant.WS_MAN_NAMESPACE, WSCommandRNDConstant.WSMAN_ITEMS_TAG);
        } catch (DOMException e) {
            String msg = "Unable to extract nodelist from IDracCardSTringView document.";
            logger.error(msg, e);
            throw new WSManException(msg);
        }
        if (nodeList == null)
            return iDracCardViewList;

        iDracCardViewList = parse(nodeList);
        return iDracCardViewList;
    }


    private List<IDRACCardStringView> parse(NodeList response) throws Exception {
        List<IDRACCardStringView> iDracCardViewList = new ArrayList<IDRACCardStringView>();
        IDRACCardStringView idracCardString = null;
        if (null != response) {
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xpath = xPathFactory.newXPath();
            for (int i = 0; i < response.getLength(); i++) {
                idracCardString = new IDRACCardStringView();
                Node itemNode = response.item(i);
                idracCardString.setAttributeDisplayName(xpath.evaluate("//*[local-name()='AttributeDisplayName']/text()", itemNode));
                idracCardString.setAttributeName(xpath.evaluate("//*[local-name()='AttributeName']/text()", itemNode));
                idracCardString.setCurrentValue(xpath.evaluate("//*[local-name()='CurrentValue']/text()", itemNode));
                idracCardString.setDefaultValue(xpath.evaluate("//*[local-name()='DefaultValue']/text()", itemNode));
                idracCardString.setDependency(xpath.evaluate("//*[local-name()='Dependency']/text()", itemNode));
                idracCardString.setDisplayOrder(xpath.evaluate("//*[local-name()='DisplayOrder']/text()", itemNode));
                idracCardString.setfQDD(xpath.evaluate("//*[local-name()='FQDD']/text()", itemNode));
                idracCardString.setGroupDisplayName(xpath.evaluate("//*[local-name()='GroupDisplayName']/text()", itemNode));
                idracCardString.setGroupID(xpath.evaluate("//*[local-name()='GroupID']/text()", itemNode));
                idracCardString.setInstanceID(xpath.evaluate("//*[local-name()='InstanceID']/text()", itemNode));
                idracCardString.setIsReadOnly(xpath.evaluate("//*[local-name()='IsReadOnly']/text()", itemNode));
                idracCardString.setMaxLength(xpath.evaluate("//*[local-name()='MaxLength']/text()", itemNode));
                idracCardString.setMinLength(xpath.evaluate("//*[local-name()='MinLength']/text()", itemNode));
                idracCardString.setPendingValue(xpath.evaluate("//*[local-name()='PendingValue']/text()", itemNode));
                iDracCardViewList.add(idracCardString);
            }
        }
        return iDracCardViewList;
    }

}
