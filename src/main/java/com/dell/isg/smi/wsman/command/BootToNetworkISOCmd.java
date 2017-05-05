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

import org.apache.commons.lang3.StringUtils;
import org.dmtf.schemas.wbem.wsman._1.wsman.SelectorSetType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.WSManageSession.EnumReferenceParam;
import com.dell.isg.smi.wsman.entity.CommandResponse;
import com.sun.ws.management.addressing.Addressing;
import com.sun.ws.management.enumeration.Enumeration;
import com.sun.ws.management.enumeration.EnumerationExtensions.Mode;

public class BootToNetworkISOCmd extends WSManBaseCommand {

    private String ipAddressISO;
    private String strShareName;
    private String strImageName;
    private int shareType = 0;
    private String shareUserName;
    private String sharePassword;
    private String workGroup;
    private String exposeDuration;

    private WSManageSession session = null;

    private static final Logger logger = LoggerFactory.getLogger(BootToNetworkISOCmd.class);


    public BootToNetworkISOCmd(String ipAddr, String userName, String passwd, String ipAddressISO, String strShareName, String strImageName) {
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            String msg = String.format("Entering constructor: BootToNetworkISOCmd(String ipAddr - %s, String userName - %s, String passwd - %s, String ipAddressISO - %s, String strShareName - %s, String strImageName - %s)", ipAddr, userName, "####", ipAddressISO, strShareName, strImageName);
            logger.trace(msg);
        }
        this.ipAddressISO = ipAddressISO;
        this.strImageName = strImageName;
        this.strShareName = strShareName;
        session = this.getSession();

        logger.trace("Exiting constructor: BootToNetworkISOCmd()");

    }


    public BootToNetworkISOCmd(String iDRACIP, String iDRACUsername, String iDRACPwd, String isoLocationIPAddress, String shareName, String imageName, int shareType, String shareUserName, String sharePassword, String workgroup) {

        this(iDRACIP, iDRACUsername, iDRACPwd, isoLocationIPAddress, shareName, imageName);
        if (logger.isTraceEnabled()) {
            String msg = String.format("Entering constructor: BootToNetworkISOCmd(String iDRACIP - %s, String iDRACUsername - %s, String iDRACPwd - %s, String isoLocationIPAddress - %s, String shareName - %s, String imageName -%s, int shareType - %d, String shareUserName - %s, String sharePassword - %s, String workgroup - %s)", iDRACIP, iDRACUsername, "####", isoLocationIPAddress, shareName, imageName, shareType, shareUserName, "####", workgroup);
            logger.trace(msg);
        }
        this.shareType = shareType;
        this.shareUserName = shareUserName;
        this.sharePassword = sharePassword;
        this.workGroup = workgroup;

        logger.trace("Exiting constructor: BootToNetworkISOCmd()");

    }


    public BootToNetworkISOCmd(String ipAddr, String userName, String passwd, String ipAddressISO, String strShareName, String strImageName, String exposeDuration) {
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            String msg = String.format("Entering constructor: BootToNetworkISOCmd(String ipAddr - %s, String userName - %s, String passwd - %s, String ipAddressISO - %s, String strShareName - %s, String strImageName - %s, String exposeDuration - %s)", ipAddr, userName, "####", ipAddressISO, strShareName, strImageName, exposeDuration);
            logger.trace(msg);
        }
        this.ipAddressISO = ipAddressISO;
        this.strImageName = strImageName;
        this.strShareName = strShareName;
        this.exposeDuration = exposeDuration;
        session = this.getSession();
        logger.trace("Exiting constructor: BootToNetworkISOCmd()");

    }


    public BootToNetworkISOCmd(String iDRACIP, String iDRACUsername, String iDRACPwd, String isoLocationIPAddress, String shareName, String imageName, int shareType, String shareUserName, String sharePassword, String workgroup, String exposeDuration) {
        this(iDRACIP, iDRACUsername, iDRACPwd, isoLocationIPAddress, shareName, imageName);

        if (logger.isTraceEnabled()) {
            String msg = String.format("Entering constructor: BootToNetworkISOCmd(String iDRACIP - %s, String iDRACUsername - %s, String iDRACPwd - %s, String isoLocationIPAddress - %s, String shareName - %s, String imageName - %s, int shareType - %d, String shareUserName - %s, String sharePassword - %s, String workgroup - %s, String exposeDuration - %s)", iDRACIP, iDRACUsername, "####", isoLocationIPAddress, shareName, imageName, shareType, shareUserName, "####", workgroup, exposeDuration);
            logger.trace(msg);
        }
        this.shareType = shareType;
        this.shareUserName = shareUserName;
        this.sharePassword = sharePassword;
        this.workGroup = workgroup;
        this.exposeDuration = exposeDuration;

        logger.trace("Exiting constructor: BootToNetworkISOCmd()");

    }


    private void addUserPararms() {

        if (StringUtils.isNotBlank(shareUserName) && StringUtils.isNotEmpty(shareUserName)) {
            this.session.addUserParam("Username", shareUserName);
        }

        if (StringUtils.isNotBlank(sharePassword) && StringUtils.isNotEmpty(sharePassword)) {
            this.session.addUserParam("Password", sharePassword);
        }

        if (StringUtils.isNotBlank(workGroup) && StringUtils.isNotEmpty(workGroup)) {
            this.session.addUserParam("Workgroup", workGroup);
        }

        if (StringUtils.isNotBlank(exposeDuration) && StringUtils.isNotEmpty(exposeDuration)) {
            this.session.addUserParam("ExposeDuration", exposeDuration);
        }
    }


    @Override
    public Object execute() throws Exception {
        logger.trace("Entering function: execute()");

        // Enumerate on DCIM_OSDeploymentService with EPR
        List<EnumReferenceParam> items = callEnumWithEPR();

        SelectorSetType settype = items.get(0).getSelectorSetTypes();
        session.getSelectors().addAll(settype.getSelector());

        session.addUserParam("IPAddress", this.getIpAddressISO());
        session.addUserParam("ShareName", this.getStrShareName());
        session.addUserParam("ShareType", shareType + "");
        session.addUserParam("ImageName", this.getStrImageName());
        addUserPararms(); // CODE MERGE MAVERICK -- was not in trunk (added from branch)

        session.setResourceUri(getBootToNetworkISOResourceURI());

        session.setInvokeCommand(WSManMethodEnum.BOOT_REMOTE_ISO.toString());
        Addressing response = session.sendInvokeRequest();

        // Get JobID
        String retValue = (String) findObjectInDocument(response.getBody(), "//pre:BootToNetworkISO_OUTPUT/pre:ReturnValue/text()", XPathConstants.STRING);

        CommandResponse resCmd = new CommandResponse();
        if (("0").equals(retValue)) {
            resCmd.setJobID(retValue);
            resCmd.setbSuccess(Boolean.TRUE);
            return resCmd;
        } else if (("4096").equals(retValue)) {
            return extractCommandResponse(response);
        } else {
            String msgVal = (String) findObjectInDocument(response.getBody(), "//pre:BootToNetworkISO_OUTPUT/pre:Message/text()", XPathConstants.STRING);
            resCmd.setDetails(msgVal);
            resCmd.setLCErrorCode(this.getLCErrorCode());
            resCmd.setLCErrorStr(this.getLCErrorStr());
        }
        resCmd.setbSuccess(Boolean.FALSE);

        logger.trace("Exiting function: execute()");
        return resCmd;
    }


    private List<EnumReferenceParam> callEnumWithEPR() throws SOAPException, JAXBException, DatatypeConfigurationException, IOException, Exception {
        session.setResourceUri(getResourceForEnumWithEPRURI());
        Enumeration responseEnum = this.sendRequestEnumeration(Mode.EnumerateEPR);
        List<EnumReferenceParam> items = session.extractRequestEnumerationRefParameters(responseEnum);
        return items;
    }


    private Object findObjectInDocument(SOAPElement doc, String xPathLocation, QName qname) throws XPathExpressionException {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        xpath.setNamespaceContext(new PersonalNamespaceContext("http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/root/dcim/DCIM_OSDeploymentService"));
        XPathExpression expr = xpath.compile(xPathLocation);
        Object result = expr.evaluate(doc, qname);
        return result;
    }


    private CommandResponse extractCommandResponse(Addressing addressing) throws SOAPException {
        SOAPElement doc = addressing.getBody();
        String resourceURI = null;
        String jobID = null;
        CommandResponse response = new CommandResponse();

        try {
            // Get ResourceURI
            Object result = findObjectInDocument(doc, "//wsman:ResourceURI/text()", XPathConstants.STRING);
            if (result == null || ((String) result).length() == 0) {
                response.setbSuccess(Boolean.FALSE);
                response.setDetails("Unable to get ResourceURI from response");
                return response;
            } else {
                resourceURI = (String) result;
            }

            // Get Selectors
            result = findObjectInDocument(doc, "//wsman:SelectorSet", XPathConstants.NODESET);
            NodeList nodeLst = (NodeList) result;
            String attrNode = null;

            // FIX ME for ICEE

            /*
             * if ( nodeLst == null ) { response.setbSuccess(Boolean.FALSE); response.setDetails("Unable to get SelectorSet from response"); return response; } else { for (int i =
             * 0; i < nodeLst.getLength(); i++) { attrNode = nodeLst.item(i).getFirstChild().getAttributes().item(0).getTextContent(); if ( attrNode.equals("InstanceID") ) { jobID
             * = nodeLst.item(i).getFirstChild().getTextContent(); break; }
             * 
             * attrNode = nodeLst.item(i).getLastChild().getAttributes().item(0).getTextContent(); if ( attrNode != null ) { jobID =
             * nodeLst.item(i).getLastChild().getTextContent(); break; } }
             * 
             * if ( jobID == null ) { response.setbSuccess(Boolean.FALSE); response.setDetails("Unable to get Instance Selector from response"); return response; } else {
             * response.setResourceURI(resourceURI); response.setJobID(jobID); response.setbSuccess(Boolean.TRUE); return response; } }
             */
        } catch (Exception e) {
            e.printStackTrace();
            response.setbSuccess(Boolean.FALSE);
            response.setDetails(e.getMessage());
            return response;
        }
        return response;
    }


    private String getBootToNetworkISOResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);

        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_OSDeploymentService);

        return sb.toString();
    }


    private String getResourceForEnumWithEPRURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);

        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_OSDeploymentService);

        return sb.toString();
    }


    public String getIpAddressISO() {
        return ipAddressISO;
    }


    public void setIpAddressISO(String ipAddressISO) {
        this.ipAddressISO = ipAddressISO;
    }


    public String getStrShareName() {
        return strShareName;
    }


    public void setStrShareName(String strShareName) {
        this.strShareName = strShareName;
    }


    public String getStrImageName() {
        return strImageName;
    }


    public void setStrImageName(String strImageName) {
        this.strImageName = strImageName;
    }
}
