/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

/**
 * @author Umer_Shabbir
 *
 */

import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.dmtf.schemas.wbem.wsman._1.wsman.SelectorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.xmlsoap.schemas.ws._2004._08.addressing.ReferenceParametersType;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.sun.ws.management.addressing.Addressing;
import com.sun.ws.management.enumeration.EnumerationExtensions.Mode;
import com.sun.ws.management.enumeration.EnumerationUtility;
import com.sun.ws.management.server.EnumerationItem;

public class ResetIdracCmd extends WSManBaseCommand {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(ResetIdracCmd.class);
    private String requestedState = "";


    public ResetIdracCmd(String ipAddr, String userName, String passwd, String requestedState) {
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: ResetIdracCmd(String ipAddr - %s, String userName - %s, String passwd - %s, String requestedState - %s)", ipAddr, userName, "####", requestedState));
        }
        session = this.getSession();
        this.requestedState = requestedState;
        logger.trace("Exiting constructor: ResetIdracCmd()");
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
        String result = "-1";
        session.setResourceUri(getResourceForEnumWithEpruri());
        Addressing addressing = getSession().sendRequestEnumeration(Mode.EnumerateObjectAndEPR);
        List<EnumerationItem> items = EnumerationUtility.extractEnumeratedValues(addressing);

        org.dmtf.schemas.wbem.wsman._1.wsman.SelectorSetType tgtSelectorSetTypes = null;
        org.dmtf.schemas.wbem.wsman._1.wsman.AttributableURI tgtAttrURI = null;

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

                retValue = (String) findObjectInDocument((Element) item.getItem(), "//*/pre:Dedicated/text()", XPathConstants.STRING, attrURI.getValue());

                if (retValue.trim().equalsIgnoreCase(WSCommandRNDConstant.RESET_IDRAC_CHECK)) {
                    tgtSelectorSetTypes = (org.dmtf.schemas.wbem.wsman._1.wsman.SelectorSetType) object2.getValue();
                    tgtAttrURI = attrURI;
                    result = this.resetIdrac(tgtSelectorSetTypes.getSelector(), tgtAttrURI.getValue());
                }
            }
        }

        logger.trace("Exiting function: execute()");
        return result;
    }


    /**
     * This method resets the iDrac.
     * 
     * @param selectorSetTypes
     * @param resourceUri
     * @throws Exception
     */
    private String resetIdrac(List<SelectorType> selectorSetTypes, String resourceUri) throws Exception {
        session.getSelectors().clear();
        session.getUserParam().clear();
        session.getSelectors().addAll(selectorSetTypes);
        session.setResourceUri(resourceUri);
        session.addUserParam(WSManMethodParamEnum.REQUESTED_STATE.toString(), this.requestedState);
        session.setInvokeCommand(WSManMethodEnum.REQUESTED_STATE_CHANGE.toString());
        Addressing response = session.sendInvokeRequest();
        String retValue = (String) findObjectInDocument(response.getBody(), "//pre:RequestStateChange_OUTPUT/pre:ReturnValue/text()", XPathConstants.STRING, resourceUri);
        return retValue;
    }


    /**
     * This method construct the resource URI.
     * 
     * @return resource URI
     */
    private String getResourceForEnumWithEpruri() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);

        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.CIM_ComputerSystem);

        return sb.toString();
    }
}
