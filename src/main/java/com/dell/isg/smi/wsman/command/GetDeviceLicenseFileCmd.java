/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import java.io.UnsupportedEncodingException;

import javax.xml.soap.SOAPBody;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.entity.ResourceURIInfo;
import com.sun.ws.management.addressing.Addressing;

public class GetDeviceLicenseFileCmd extends WSManBaseCommand {

    private static final Logger logger = LoggerFactory.getLogger(GetDeviceLicenseFileCmd.class);
    private WSManageSession session = null;
    private String entitlementId;
    private ResourceURIInfo resourceUriInfo = null;


    public GetDeviceLicenseFileCmd(String ipAddr, String userName, String passwd, boolean bCertCheck, String entitlementId) {
        super(ipAddr, userName, passwd, bCertCheck);

        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: GetDeviceLicenseFileCmd(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }

        session = super.getSession();
        this.entitlementId = entitlementId;

        logger.trace("Exiting constructor: GetDeviceLicenseFileCmd()");
    }


    @Override
    public Object execute() throws Exception {
        logger.info("Executing GetDeviceLicenseFileCmd (DCIM_LicenseManagementService/ExportLicense for iDRAC: " + session.getIpAddress());
        String licenseFile = "";
        intiCommand();
        Addressing response = session.sendInvokeRequest();
        // Get the jobID out of the response.
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();
        SOAPBody soapBody = response.getBody();
        licenseFile = xpath.evaluate("//*[local-name()='LicenseFile']", soapBody);
        if (licenseFile != null && !licenseFile.isEmpty()) {
            logger.info("Successfully got the license file for iDRAC: " + session.getIpAddress());
            logger.info("Decoding the license file");
            licenseFile = base64Decode(licenseFile);
            logger.info("Successfully decoded the license file");
        } else {
            logger.info("Empty license file is retrieved from iDRAC");
        }
        return licenseFile;
    }


    private String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);

        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_LicenseManagementService);

        return sb.toString();
    }


    public void intiCommand() throws Exception {

        session.setResourceUri(getResourceURI());

        if (resourceUriInfo == null) {
            GetResourceURIInfoCmd cmd = new GetResourceURIInfoCmd(session.getIpAddress(), session.getUser(), session.getPassword(), session.isCertificateCheck(), WSManClassEnum.DCIM_LicenseManagementService.toString());
            Object result = cmd.execute();
            if (result != null) {
                resourceUriInfo = (ResourceURIInfo) result;
            }
        }
        if (resourceUriInfo != null) {
            session.addSelector(WSManMethodParamEnum.CREATION_CLASS_NAME.toString(), resourceUriInfo.getCreationClassName());
            session.addSelector(WSManMethodParamEnum.SYSTEM_NAME.toString(), resourceUriInfo.getSystemName());
            session.addSelector(WSManMethodParamEnum.NAME.toString(), resourceUriInfo.getName());
            session.addSelector(WSManMethodParamEnum.SYSTEM_CLASS_NAME.toString(), resourceUriInfo.getSystemCreationClassName());
            session.addUserParam("EntitlementID", entitlementId);
        }

        session.setInvokeCommand(WSManMethodEnum.EXPORT_LICENSE.toString());

    }


    public void setResourceUriInfo(ResourceURIInfo resourceUriInfo) {
        this.resourceUriInfo = resourceUriInfo;
    }


    static String base64Decode(String str) {
        try {
            return new String(base64Decode(str.getBytes("UTF-8")), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }


    static byte[] base64Decode(byte[] byteArray) {
        return Base64.decodeBase64(byteArray);
    }

}
