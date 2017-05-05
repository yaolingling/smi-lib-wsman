/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import javax.xml.soap.SOAPBody;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.sun.ws.management.addressing.Addressing;

public class GetLicenseBits extends WSManBaseCommand {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(GetLicenseBits.class);


    public GetLicenseBits(String ipAddr, String userName, String passwd) {

        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: GetLicenseBits(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        session = this.getSession();
        session.setResourceUri(getResourceURI());

        logger.trace("Exiting constructor: GetLicenseBits()");

    }


    @Override
    public String execute() throws Exception {

        String licenseBits = "";
        Addressing response = session.sendInvokeRequest();
        SOAPBody soapBody = response.getBody();
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();
        licenseBits = xpath.evaluate("//*[local-name()='LicenseBits']", soapBody);

        return licenseBits;
    }


    private String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append("DCIM_LicenseManagementService");
        session.addSelector("SystemCreationClassName", "DCIM_SPComputerSystem");
        session.addSelector("CreationClassName", "DCIM_LicenseManagementService");
        session.addSelector("SystemName", "systemmc");
        session.addSelector("Name", "DCIM:LicenseManagementService:1");
        session.setInvokeCommand("ShowLicenseBits");
        return sb.toString();
    }

}
