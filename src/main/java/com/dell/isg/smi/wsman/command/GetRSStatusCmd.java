/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.dmtf.schemas.wbem.wsman._1.wsman.SelectorSetType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.WSManageSession.EnumReferenceParam;
import com.sun.ws.management.addressing.Addressing;
import com.sun.ws.management.enumeration.Enumeration;
import com.sun.ws.management.enumeration.EnumerationExtensions.Mode;

public class GetRSStatusCmd extends WSManBaseCommand {
    public static enum RSStatusEnum {
        NOT_READY("Not Ready"), RELOADING("Reloading"), READY("Ready"), ERROR("Error");

        String rebootNum;


        RSStatusEnum(String value) {
            rebootNum = value;
        }


        public String toString() {
            return rebootNum;
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(GetRSStatusCmd.class);
    private WSManageSession session = null;


    public GetRSStatusCmd(String ipAddr, String userName, String passwd) {
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: GetRSStatusCmd(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        session = this.getSession();
        logger.trace("Exiting constructor: GetRSStatusCmd()");
    }


    @Override
    public RSStatusEnum execute() {
        logger.trace("Entering function: execute()");
        try {
            // Enumerate on DCIM_OSDeploymentService with EPR
            List<EnumReferenceParam> items = callEnumWithEPR();

            SelectorSetType settype = items.get(0).getSelectorSetTypes();
            session.getSelectors().addAll(settype.getSelector());
            session.setResourceUri(getResourceURI());

            session.setInvokeCommand(WSManMethodEnum.GET_RS_STATUS.toString());
            Addressing response = session.sendInvokeRequest();

            String retValue = (String) findObjectInDocument(response.getBody(), "//pre:GetRSStatus_OUTPUT/pre:ReturnValue/text()", XPathConstants.STRING);

            if (("0").equals(retValue)) {
                // check the status
                retValue = (String) findObjectInDocument(response.getBody(), "//pre:GetRSStatus_OUTPUT/pre:Status/text()", XPathConstants.STRING);
                logger.debug("GetRSStatus return status is: " + retValue);
                logger.trace("Exiting function: execute()");
                if (RSStatusEnum.READY.toString().equals(retValue)) {
                    return RSStatusEnum.READY;
                } else if (RSStatusEnum.NOT_READY.toString().equals(retValue)) {
                    return RSStatusEnum.NOT_READY;
                } else if (RSStatusEnum.RELOADING.toString().equals(retValue)) {
                    return RSStatusEnum.RELOADING;
                } else {
                    return RSStatusEnum.ERROR;
                }
            } else {
                return RSStatusEnum.ERROR;
            }
        } catch (Exception e) {
            logger.error("Exception calling GetRSStatusCmd.execute: " + e.getMessage() + " .  Returning RSStatusEnum.ERROR");
            return RSStatusEnum.ERROR;
        }
    }


    private List<EnumReferenceParam> callEnumWithEPR() throws SOAPException, JAXBException, DatatypeConfigurationException, IOException, Exception {
        session.setResourceUri(getResourceURI());
        Enumeration responseEnum = this.sendRequestEnumeration(Mode.EnumerateEPR);
        List<EnumReferenceParam> items = session.extractRequestEnumerationRefParameters(responseEnum);
        return items;
    }


    private String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_LCService);
        return sb.toString();
    }


    private Object findObjectInDocument(SOAPElement doc, String xPathLocation, QName qname) throws XPathExpressionException {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        xpath.setNamespaceContext(new PersonalNamespaceContext("http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/root/dcim/DCIM_LCService"));
        XPathExpression expr = xpath.compile(xPathLocation);
        Object result = expr.evaluate(doc, qname);
        return result;
    }
}
