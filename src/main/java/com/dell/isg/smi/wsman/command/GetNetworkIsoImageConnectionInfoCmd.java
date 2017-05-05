/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
/**
 * 
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
 * @author Umer_Shabbir
 *
 */

public class GetNetworkIsoImageConnectionInfoCmd extends WSManBaseCommand {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(GetNetworkIsoImageConnectionInfoCmd.class);


    public GetNetworkIsoImageConnectionInfoCmd(String ipAddr, String userName, String passwd) {
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: GetNetworkIsoImageConnectionInfoCmd(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        session = this.getSession();
        logger.trace("Exiting constructor: GetNetworkIsoImageConnectionInfoCmd()");
    }


    @Override
    public Object execute() throws Exception {
        logger.trace("Entering function: execute()");
        // Enumerate on DCIM_OSDeploymentService with EPR
        List<EnumReferenceParam> items = callEnumWithEPR();

        SelectorSetType settype = items.get(0).getSelectorSetTypes();
        session.getSelectors().addAll(settype.getSelector());

        session.setResourceUri(this.getNetworkIsoImageConnectionInfoResourceURI());

        session.setInvokeCommand(WSManMethodEnum.GET_NETWORK_ISO_IMAGE_CONNECTION_INFO.toString());
        Addressing response = session.sendInvokeRequest();

        // Get JobID
        String retValue = (String) findObjectInDocument(response.getBody(), "//pre:GetNetworkISOImageConnectionInfo_OUTPUT/pre:ReturnValue/text()", XPathConstants.STRING);

        /*
         * NetworkISOImageConnectionInfo connInfo = new NetworkISOImageConnectionInfo(); if ( ("0").equals(retValue) ) { this.extractResponse(response, connInfo); } else { String
         * msgId = (String) findObjectInDocument(response.getBody(), "//pre:GetNetworkISOImageConnectionInfo_OUTPUT//pre:MessageID/text()", XPathConstants.STRING); String msgValue
         * = (String) findObjectInDocument(response.getBody(), "//pre:GetNetworkISOImageConnectionInfo_OUTPUT//pre:Message/text()", XPathConstants.STRING);
         * 
         * String returnMsg = ""; if(("1").equals(retValue)) { returnMsg = "Method is not supported in the implementation."; } else if(("2").equals(retValue)) { returnMsg =
         * "Error occurred."; }
         * 
         * connInfo.setbSuccess(Boolean.FALSE); connInfo.setDetails(returnMsg); connInfo.setLCErrorCode(msgId); connInfo.setLCErrorStr(msgValue); }
         */
        logger.trace("Exiting function: execute()");
        return null;
    }


    private void extractResponse(Addressing addressing, Object connInfo) throws SOAPException {
        /*
         * FIX ME for ICEE SOAPElement doc = addressing.getBody(); List<String> messageArgs = new ArrayList<String>();
         * 
         * connInfo.setbSuccess(Boolean.TRUE); connInfo.setDetails(""); connInfo.setLCErrorCode(""); connInfo.setLCErrorStr("");
         * 
         * try { //Get IPAddr Object result = findObjectInDocument(doc, "//pre:IPAddr", XPathConstants.STRING); connInfo.setIpAddress((String) result); //Get ShareName result =
         * findObjectInDocument(doc, "//pre:ShareName", XPathConstants.STRING); connInfo.setShareName((String) result); //Get ShareType result = findObjectInDocument(doc,
         * "//pre:ShareType", XPathConstants.STRING); connInfo.setShareType((String) result); //Get ImageName result = findObjectInDocument(doc, "//pre:ImageName",
         * XPathConstants.STRING); connInfo.setImageName((String) result); //Get Workgroup result = findObjectInDocument(doc, "//pre:Workgroup", XPathConstants.STRING);
         * connInfo.setWorkGroup((String) result); //Get ISOConnectionStatus result = findObjectInDocument(doc, "//pre:ISOConnectionStatus", XPathConstants.STRING);
         * connInfo.setIsoConnectionStatus((String) result); //Get HostAttachedStatus result = findObjectInDocument(doc, "//pre:HostAttachedStatus", XPathConstants.STRING);
         * connInfo.setHostAttachedStatus((String) result); //Get HostBootedFromISO result = findObjectInDocument(doc, "//pre:HostBootedFromISO", XPathConstants.STRING);
         * connInfo.setHostBootedFromISO((String) result); //Get UserName result = findObjectInDocument(doc, "//pre:UserName", XPathConstants.STRING); connInfo.setUserName((String)
         * result); //Get MessageID result = findObjectInDocument(doc, "//pre:MessageID", XPathConstants.STRING); connInfo.setMessageID((String) result); //Get Message result =
         * findObjectInDocument(doc, "//pre:Message", XPathConstants.STRING); connInfo.setMessage((String) result); //Get MessageArguments result = findObjectInDocument(doc,
         * "//pre:MessageArguments", XPathConstants.NODESET); NodeList nodeLst = (NodeList) result; if(nodeLst != null && nodeLst.getLength() > 0) { for (int i = 0; i <
         * nodeLst.getLength(); i++) { messageArgs.add(nodeLst.item(i).getFirstChild().getTextContent()); } }
         * 
         * connInfo.setMessageArgument(messageArgs); } catch (Exception e) { e.printStackTrace(); connInfo.setbSuccess(Boolean.FALSE); connInfo.setDetails(e.getMessage()); }
         */
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


    private String getNetworkIsoImageConnectionInfoResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);

        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_OSDeploymentService);

        return sb.toString();
    }


    private String getResourceForEnumWithEPRURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);

        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_OSDeploymentService);

        return sb.toString();
    }
}
