/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import java.util.List;

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

import com.dell.isg.smi.commons.elm.exception.RuntimeCoreException;
import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.WSManageSession.EnumReferenceParam;
import com.dell.isg.smi.wsman.entity.CommandResponse;
import com.sun.ws.management.addressing.Addressing;
import com.sun.ws.management.enumeration.Enumeration;
import com.sun.ws.management.enumeration.EnumerationExtensions.Mode;

public class ConnectNetworkISOImageCmd extends WSManBaseCommand {
    public static enum HashTypeEnum {
        MD5(1), SHA1(2);

        int hashType;


        HashTypeEnum(int value) {
            hashType = value;
        }


        public int toInt() {
            return hashType;
        }
    }

    public static enum ShareTypeEnum {
        NFS(0), CIFS(2);

        int shareType;


        ShareTypeEnum(int value) {
            shareType = value;
        }


        public int toInt() {
            return shareType;
        }
    }

    private WSManageSession session = null;
    private String ipAddressISO;
    private String strShareName;
    private String strImageName;
    private int shareType = 0;
    private String shareUserName;
    private String sharePassword;
    private String domain;
    private int hashType;
    private String hashValue;
    private boolean autoConnect = false;

    private static final Logger logger = LoggerFactory.getLogger(ConnectNetworkISOImageCmd.class);


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


    public int getShareType() {
        return shareType;
    }


    public void setShareType(int shareType) {
        this.shareType = shareType;
    }


    public String getShareUserName() {
        return shareUserName;
    }


    public void setShareUserName(String shareUserName) {
        this.shareUserName = shareUserName;
    }


    public String getSharePassword() {
        return sharePassword;
    }


    public void setSharePassword(String sharePassword) {
        this.sharePassword = sharePassword;
    }


    public String getDomain() {
        return domain;
    }


    public void setDomain(String domain) {
        this.domain = domain;
    }


    public int getHashType() {
        return hashType;
    }


    public void setHashType(int hashType) {
        this.hashType = hashType;
    }


    public String getHashValue() {
        return hashValue;
    }


    public void setHashValue(String hashValue) {
        this.hashValue = hashValue;
    }


    public boolean isAutoConnect() {
        return autoConnect;
    }


    public void setAutoConnect(boolean autoConnect) {
        this.autoConnect = autoConnect;
    }


    public ConnectNetworkISOImageCmd(String iDRACIP, String iDRACUsername, String iDRACPwd, String isoLocationIPAddress, String shareName, String imageName) {
        super(iDRACIP, iDRACUsername, iDRACPwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: ConnectNetworkISOImageCmd(String iDRACIP - %s, String iDRACUsername - %s, String iDRACPwd - %s, String isoLocationIPAddress - %s, String shareName - %s, String imageName - %s)", iDRACIP, iDRACUsername, "####", isoLocationIPAddress, shareName, imageName));
        }
        this.shareType = ShareTypeEnum.NFS.toInt();
        this.ipAddressISO = isoLocationIPAddress;
        this.strImageName = imageName;
        this.strShareName = shareName;
        this.autoConnect = false;
        session = this.getSession();

        logger.trace("Exiting constructor: ConnectNetworkISOImageCmd()");

    }


    public ConnectNetworkISOImageCmd(String iDRACIP, String iDRACUsername, String iDRACPwd, String isoLocationIPAddress, String shareName, String imageName, boolean autoConnect) {
        this(iDRACIP, iDRACUsername, iDRACPwd, isoLocationIPAddress, shareName, imageName);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: ConnectNetworkISOImageCmd(String iDRACIP - %s, String iDRACUsername - %s, String iDRACPwd - %s, String isoLocationIPAddress - %s, String shareName - %s, String imageName - %s, boolean autoConnect - %s)", iDRACIP, iDRACUsername, "####", isoLocationIPAddress, shareName, imageName, Boolean.toString(autoConnect)));
        }
        this.autoConnect = autoConnect;
        logger.trace("Exiting constructor: ConnectNetworkISOImageCmd()");
    }


    public ConnectNetworkISOImageCmd(String iDRACIP, String iDRACUsername, String iDRACPwd, String isoLocationIPAddress, String shareName, String imageName, String shareUserName, String sharePassword, String domain) {
        this(iDRACIP, iDRACUsername, iDRACPwd, isoLocationIPAddress, shareName, imageName);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: ConnectNetworkISOImageCmd(String iDRACIP - %s, String iDRACUsername - %s, String iDRACPwd - %s, String isoLocationIPAddress - %s, String shareName - %s, String imageName - %s, String shareUserName - %s, String sharePassword - %s, String domain - %s)", iDRACIP, iDRACUsername, "####", isoLocationIPAddress, shareName, imageName, shareUserName, "####", domain));
        }
        this.shareUserName = shareUserName;
        this.sharePassword = sharePassword;
        this.domain = domain;
        this.shareType = ShareTypeEnum.CIFS.toInt();
        logger.trace("Exiting constructor: ConnectNetworkISOImageCmd()");
    }


    public ConnectNetworkISOImageCmd(String iDRACIP, String iDRACUsername, String iDRACPwd, String isoLocationIPAddress, String shareName, String imageName, String shareUserName, String sharePassword, String domain, boolean autoConnect) {
        this(iDRACIP, iDRACUsername, iDRACPwd, isoLocationIPAddress, shareName, imageName, shareUserName, sharePassword, domain);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: ConnectNetworkISOImageCmd(String iDRACIP - %s, String iDRACUsername - %s, String iDRACPwd - %s, String isoLocationIPAddress - %s, String shareName - %s, String imageName - %s, String shareUserName - %s, String sharePassword - %s, String domain - %s, boolean autoConnect - %s)", iDRACIP, iDRACUsername, "####", isoLocationIPAddress, shareName, imageName, shareUserName, "####", domain, Boolean.toString(autoConnect)));
        }
        this.autoConnect = autoConnect;
        logger.trace("Exiting constructor: ConnectNetworkISOImageCmd()");
    }


    public ConnectNetworkISOImageCmd(String iDRACIP, String iDRACUsername, String iDRACPwd, String isoLocationIPAddress, String shareName, String imageName, ShareTypeEnum shareType, String shareUserName, String sharePassword, String domain) {
        this(iDRACIP, iDRACUsername, iDRACPwd, isoLocationIPAddress, shareName, imageName, shareUserName, sharePassword, domain);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: ConnectNetworkISOImageCmd(String iDRACIP - %s, String iDRACUsername - %s, String iDRACPwd - %s, String isoLocationIPAddress - %s, String shareName - %s, String imageName - %s, ShareTypeEnum shareType - %s, String shareUserName - %s, String sharePassword - %s, String domain - %s)", iDRACIP, iDRACUsername, "####", isoLocationIPAddress, shareName, imageName, ShareTypeEnum.class.getName(), shareUserName, "####", domain));
        }
        this.shareType = shareType.toInt();
        logger.trace("Exiting constructor: ConnectNetworkISOImageCmd()");
    }


    public ConnectNetworkISOImageCmd(String iDRACIP, String iDRACUsername, String iDRACPwd, String isoLocationIPAddress, String shareName, String imageName, ShareTypeEnum shareType, String shareUserName, String sharePassword, String domain, boolean autoConnect) {
        this(iDRACIP, iDRACUsername, iDRACPwd, isoLocationIPAddress, shareName, imageName, shareType, shareUserName, sharePassword, domain);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: ConnectNetworkISOImageCmd(String iDRACIP - %s, String iDRACUsername - %s, String iDRACPwd - %s, String isoLocationIPAddress - %s, String shareName - %s, String imageName - %s, ShareTypeEnum shareType - %s, String shareUserName - %s, String sharePassword - %s, String domain - %s, boolean autoConnect - %s)", iDRACIP, iDRACUsername, "####", isoLocationIPAddress, shareName, imageName, ShareTypeEnum.class.getName(), shareUserName, "####", domain, Boolean.toString(autoConnect)));
        }
        this.autoConnect = autoConnect;
        logger.trace("Exiting constructor: ConnectNetworkISOImageCmd()");
    }


    public ConnectNetworkISOImageCmd(String iDRACIP, String iDRACUsername, String iDRACPwd, String isoLocationIPAddress, String shareName, String imageName, ShareTypeEnum shareType, String shareUserName, String sharePassword, String domain, boolean autoConnect, HashTypeEnum hashType, String hashValue) {
        this(iDRACIP, iDRACUsername, iDRACPwd, isoLocationIPAddress, shareName, imageName, shareType, shareUserName, sharePassword, domain, autoConnect);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: ConnectNetworkISOImageCmd(String iDRACIP - %s, String iDRACUsername - %s, String iDRACPwd - %s, String isoLocationIPAddress - %s, String shareName - %s, String imageName - %s, ShareTypeEnum shareType - %s, String shareUserName - %s, String sharePassword - %s, String domain - %s, boolean autoConnect - %s, HashTypeEnum hashType - %s,	String  hashValue - %s)", iDRACIP, iDRACUsername, "####", isoLocationIPAddress, shareName, imageName, ShareTypeEnum.class.getName(), shareUserName, "####", domain, Boolean.toString(autoConnect), HashTypeEnum.class.getName(), hashValue));
        }
        this.hashType = hashType.toInt();
        this.hashValue = hashValue;
        if (hashValue == null) {
            throw new RuntimeCoreException("HashValue not provided");
        }
        logger.trace("Exiting constructor: ConnectNetworkISOImageCmd()");
    }


    @Override
    public Object execute() throws Exception {
        logger.trace("Entering function: execute()");
        // Enumerate on DCIM_OSDeploymentService with EPR
        List<EnumReferenceParam> items = callEnumWithEPR();

        SelectorSetType settype = items.get(0).getSelectorSetTypes();
        session.getSelectors().addAll(settype.getSelector());

        addUserPararms();

        session.setResourceUri(getResourceURI());

        session.setInvokeCommand(WSManMethodEnum.CONNECT_TO_NEWORK_ISO_IMAGE.toString());
        Addressing response = session.sendInvokeRequest();

        // Get JobID
        String retValue = (String) findObjectInDocument(response.getBody(), "//pre:ConnectNetworkISOImage_OUTPUT/pre:ReturnValue/text()", XPathConstants.STRING);

        CommandResponse resCmd = new CommandResponse();
        if (("0").equals(retValue)) {
            resCmd.setJobID(retValue);
            resCmd.setbSuccess(Boolean.TRUE);
            return resCmd;
        } else if (("4096").equals(retValue)) {
            return extractCommandResponse(response);
        } else {
            String msgVal = (String) findObjectInDocument(response.getBody(), "//pre:ConnectNetworkISOImage_OUTPUT/pre:Message/text()", XPathConstants.STRING);
            resCmd.setDetails(msgVal);
            resCmd.setLCErrorCode(this.getLCErrorCode());
            resCmd.setLCErrorStr(this.getLCErrorStr());
        }
        resCmd.setbSuccess(Boolean.FALSE);
        logger.trace("Exiting function: execute()");
        return resCmd;
    }


    private String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);

        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_OSDeploymentService);

        return sb.toString();
    }


    private List<EnumReferenceParam> callEnumWithEPR() throws Exception {
        session.setResourceUri(getResourceURI());
        Enumeration responseEnum = this.sendRequestEnumeration(Mode.EnumerateEPR);
        List<EnumReferenceParam> items = session.extractRequestEnumerationRefParameters(responseEnum);
        return items;
    }


    private void addUserPararms() {
        session.addUserParam("IPAddress", this.getIpAddressISO());
        session.addUserParam("ShareName", this.getStrShareName());
        session.addUserParam("ShareType", shareType + "");
        session.addUserParam("ImageName", this.getStrImageName());
        // session.addUserParam("AutoConnect",
        // Boolean.toString(this.autoConnect));

        if (StringUtils.isNotBlank(shareUserName) && StringUtils.isNotEmpty(shareUserName)) {
            this.session.addUserParam("Username", shareUserName);
        }

        if (StringUtils.isNotBlank(sharePassword) && StringUtils.isNotEmpty(sharePassword)) {
            this.session.addUserParam("Password", sharePassword);
        }

        if (StringUtils.isNotBlank(domain) && StringUtils.isNotEmpty(domain)) {
            this.session.addUserParam("Workgroup", domain);
        }

        if (this.hashType != 0) {
            this.session.addUserParam("HashType", hashType);
            this.session.addUserParam("HashValue", hashValue);
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


    private Object extractCommandResponse(Addressing addressing) throws SOAPException {
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

            if (nodeLst == null) {
                response.setbSuccess(Boolean.FALSE);
                response.setDetails("Unable to get SelectorSet from response");
                return response;
            } else {
                for (int i = 0; i < nodeLst.getLength(); i++) {
                    attrNode = nodeLst.item(i).getFirstChild().getAttributes().item(0).getTextContent();
                    if (attrNode.equals("InstanceID")) {
                        jobID = nodeLst.item(i).getFirstChild().getTextContent();
                        break;
                    }

                    attrNode = nodeLst.item(i).getLastChild().getAttributes().item(0).getTextContent();
                    if (attrNode != null) {
                        jobID = nodeLst.item(i).getLastChild().getTextContent();
                        break;
                    }
                }

                if (jobID == null) {
                    response.setbSuccess(Boolean.FALSE);
                    response.setDetails("Unable to get Instance Selector from response");
                    return response;
                } else {
                    response.setResourceURI(resourceURI);
                    response.setJobID(jobID);
                    response.setbSuccess(Boolean.TRUE);
                    return response;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setbSuccess(Boolean.FALSE);
            response.setDetails(e.getMessage());
            return response;
        }
    }
}
