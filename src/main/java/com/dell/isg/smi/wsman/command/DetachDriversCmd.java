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

/**
 * 
 * @author Umer_Shabbir
 *
 */

public class DetachDriversCmd extends WSManBaseCommand {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(DetachDriversCmd.class);


    public DetachDriversCmd(String ipAddr, String userName, String passwd) {
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: DetachDriversCmd(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        session = this.getSession();

        logger.trace("Exiting constructor: DetachDriversCmd()");
    }


    @Override
    public Object execute() throws Exception {
        logger.trace("Entering function: execute()");
        // Enumerate on DCIM_OSDeploymentService with EPR
        List<EnumReferenceParam> items = callEnumWithEPR();

        SelectorSetType settype = items.get(0).getSelectorSetTypes();

        session.getSelectors().addAll(settype.getSelector());

        session.setResourceUri(getDetachDriversResourceURI());

        session.setInvokeCommand(WSManMethodEnum.DETACH_DRIVERS.toString());

        Addressing response = session.sendInvokeRequest();

        String retValue = (String) findObjectInDocument(response.getBody(), "//pre:DetachDrivers_OUTPUT//pre:ReturnValue/text()", XPathConstants.STRING);
        String msgValue = (String) findObjectInDocument(response.getBody(), "//pre:DetachDrivers_OUTPUT//pre:Message/text()", XPathConstants.STRING);

        logger.trace("Exiting function: execute()");
        if ("0".equals(retValue) || (("2".equals(retValue)) && msgValue.equals("ISO image is not attached"))) {
            return Boolean.TRUE;
        } else {
            return msgValue;
        }
    }


    private Object findObjectInDocument(SOAPElement doc, String xPathLocation, QName qname) throws XPathExpressionException {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        xpath.setNamespaceContext(new PersonalNamespaceContext("http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/root/dcim/DCIM_OSDeploymentService"));

        XPathExpression expr = xpath.compile(xPathLocation);
        Object result = expr.evaluate(doc, qname);
        return result;
    }


    private List<EnumReferenceParam> callEnumWithEPR() throws SOAPException, JAXBException, DatatypeConfigurationException, IOException, Exception {
        session.setResourceUri(getResourceForEnumWithEPRURI());
        Enumeration responseEnum = this.sendRequestEnumeration(Mode.EnumerateEPR);
        List<EnumReferenceParam> items = session.extractRequestEnumerationRefParameters(responseEnum);
        return items;
    }


    private String getDetachDriversResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);

        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_OSDeploymentService);

        return sb.toString();
    }


    private String getResourceForEnumWithEPRURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);

        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_OSDeploymentService);

        return sb.toString();
    }


    public static void main(String[] args) {
        DetachDriversCmd cmd = new DetachDriversCmd("10.36.0.178", "spectre", "Spectre123");
        try {
            Object obj = cmd.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
