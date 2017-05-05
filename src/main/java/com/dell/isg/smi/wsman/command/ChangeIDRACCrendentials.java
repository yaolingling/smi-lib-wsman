/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xmlsoap.schemas.ws._2004._08.addressing.ReferenceParametersType;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.sun.ws.management.addressing.Addressing;
import com.sun.ws.management.enumeration.EnumerationExtensions.Mode;
import com.sun.ws.management.enumeration.EnumerationUtility;
import com.sun.ws.management.server.EnumerationItem;

public class ChangeIDRACCrendentials extends WSManBaseCommand {

    private WSManageSession session = null;
    private String changePwd = null;
    private String changeUser = null;

    private static final Logger logger = LoggerFactory.getLogger(ChangeIDRACCrendentials.class);


    public ChangeIDRACCrendentials(String ipAddr, String userName, String passwd, String changeUser, String changePwd) {
        super(ipAddr, userName, passwd, false);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: ChangeIDRACCrendentials(String ipAddr - %s, String userName - %s, String passwd - %s, String changeUser - %s, String changePwd - %s)", ipAddr, userName, "####", changeUser, "####"));
        }
        session = this.getSession();
        this.changePwd = changePwd;
        this.changeUser = changeUser;

        logger.trace("Exiting constructor: ChangeIDRACCrendentials()");
    }


    private Object findObjectInDocument(Element doc, String xPathLocation, QName qname, String resourceURI) throws XPathExpressionException {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        xpath.setNamespaceContext(new PersonalNamespaceContext(resourceURI));
        XPathExpression expr = xpath.compile(xPathLocation);
        Object result = expr.evaluate(doc, qname);
        return result;
    }


    @Override
    public Object execute() throws Exception {
        logger.trace("Entering function: execute()");

        // Enumerate with EPR and Object
        session.setResourceUri(getResourceForEnumWithEPRURI());
        Addressing addressing = getSession().sendRequestEnumeration(Mode.EnumerateObjectAndEPR);
        List<EnumerationItem> items = EnumerationUtility.extractEnumeratedValues(addressing);
        boolean bFound = false;
        boolean bCurFound = false;
        org.dmtf.schemas.wbem.wsman._1.wsman.SelectorSetType tgtSelectorSetTypes = null;
        org.dmtf.schemas.wbem.wsman._1.wsman.SelectorSetType curSelectorSetTypes = null;
        org.dmtf.schemas.wbem.wsman._1.wsman.AttributableURI tgtAttrURI = null;
        org.dmtf.schemas.wbem.wsman._1.wsman.AttributableURI curAttrURI = null;

        if (items != null && items.size() > 0) {

            List<Object> objects = null;
            JAXBElement<?> object1 = null;
            JAXBElement<?> object2 = null;
            ReferenceParametersType refParam = null;

            String retValue = null;
            org.dmtf.schemas.wbem.wsman._1.wsman.AttributableURI attrURI = null;

            for (EnumerationItem item : items) {
                refParam = item.getEndpointReference().getReferenceParameters();

                objects = refParam.getAny();
                object1 = (JAXBElement<?>) objects.get(0);
                object2 = (JAXBElement<?>) objects.get(1);
                attrURI = (org.dmtf.schemas.wbem.wsman._1.wsman.AttributableURI) object1.getValue();

                retValue = (String) findObjectInDocument((Element) item.getItem(), "//*/pre:UserID/text()", XPathConstants.STRING, attrURI.getValue());

                if (retValue.equalsIgnoreCase(changeUser)) {
                    tgtSelectorSetTypes = (org.dmtf.schemas.wbem.wsman._1.wsman.SelectorSetType) object2.getValue();
                    tgtAttrURI = attrURI;
                    bFound = true;
                } else if (retValue.equalsIgnoreCase(session.getUser())) {
                    curSelectorSetTypes = (org.dmtf.schemas.wbem.wsman._1.wsman.SelectorSetType) object2.getValue();
                    curAttrURI = attrURI;
                    bCurFound = true;
                }
            }
        }

        if (bFound) {
            session.getSelectors().addAll(tgtSelectorSetTypes.getSelector());
            session.setResourceUri(tgtAttrURI.getValue());
            Addressing reqGetManagement = session.sendGetRequest();

            Document requestBody = session.extractAddressBody(reqGetManagement);
            NodeList elements = (NodeList) findObjectInDocument(requestBody.getDocumentElement(), "//pre:UserPassword", XPathConstants.NODESET, tgtAttrURI.getValue());
            // FIX ME elements.item(0).setTextContent(changePwd);
            session.sendPutRequest(requestBody);
            logger.trace("Exiting function: execute()");
            return 0;
        }
        if (bCurFound) {
            session.getSelectors().addAll(curSelectorSetTypes.getSelector());
            session.setResourceUri(curAttrURI.getValue());
            Addressing reqGetManagement = session.sendGetRequest();

            Document requestBody = session.extractAddressBody(reqGetManagement);
            NodeList elements = (NodeList) findObjectInDocument(requestBody.getDocumentElement(), "//pre:UserPassword", XPathConstants.NODESET, curAttrURI.getValue());
            // FIX ME elements.item(0).setTextContent(changePwd);
            elements = (NodeList) findObjectInDocument(requestBody.getDocumentElement(), "//pre:UserID", XPathConstants.NODESET, curAttrURI.getValue());
            // FIX ME elements.item(0).setTextContent(changeUser);
            session.sendPutRequest(requestBody);
            logger.trace("Exiting function: execute()");
            return 1;
        }
        logger.trace("Exiting function: execute()");
        return -1;
    }


    private String getResourceForEnumWithEPRURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);

        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.CIM_Account);

        return sb.toString();
    }
}
