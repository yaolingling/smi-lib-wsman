/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dell.isg.smi.wsman.command;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.soap.SOAPException;
import javax.xml.xpath.XPathExpressionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;

/**
 *
 * @author Matthew_G_Stemen
 */
public class UpdateCmd extends WSManBaseCommand {
    /**
     * 
     * @param DRACIP
     * @param DRACPort
     * @param DRACUser
     * @param DRACPassword
     * @param filePath
     * @param userName
     * @param password
     * @param updateType
     * @throws SOAPException
     * @throws MalformedURLException
     */

    WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(UpdateCmd.class);


    public UpdateCmd(String DRACIP, String DRACPort, String DRACUser, String DRACPassword, String filePath, String userName, String password) throws SOAPException, MalformedURLException {
        // set the WSMan Session
        super(DRACIP, DRACPort, DRACUser, DRACPassword);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: UpdateCmd(String DRACIP - %s, String DRACPort - %s, String DRACUser - %s, String DRACPassword - %s, String filePath - %s, String userName - %s, String password - %s)", DRACIP, DRACUser, "####", filePath, userName, "####"));
        }
        session = this.getSession();
        session.setResourceUri(this.getResourceURI());
        String uri = getFileUri(filePath, userName, password);
        session.addUserParam("URI", uri);

        session.setInvokeCommand(WSManMethodEnum.INSTALL_FROM_URI.toString());
        logger.trace("Exiting constructor: UpdateCmd()");

    }


    /**
     * 
     * @return resource uri
     */
    private String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);

        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_SoftwareInstallationService);

        return sb.toString();
    }


    @Override
    public String execute() throws IllegalArgumentException, SOAPException, JAXBException, DatatypeConfigurationException, IOException, XPathExpressionException {
        logger.trace("Entering function: execute()");
        String invokeRequest = session.sendInvokeRequest().toString();
        logger.trace("Exiting function: execute()");
        return invokeRequest;
    }


    /*
     * private SOAPElement buildTarget(String filePath) throws SOAPException {
     * 
     * SOAPFactory factory = SOAPFactory.newInstance();
     * 
     * SOAPElement elementTarget = factory.createElement("Target", session.PREFIX, "http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/root/dcim/DCIM_SoftwareInstallationService");
     * 
     * elementTarget.addNamespaceDeclaration("a", "http://schemas.xmlsoap.org/ws/2004/08/addressing"); elementTarget.addNamespaceDeclaration("w",
     * "http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd");
     * 
     * SOAPElement elementAddress = elementTarget.addChildElement("Address", "a"); elementAddress.setValue("http://schemas.xmlsoap.org/ws/2004/08/addressing/role/anonymous");
     * 
     * SOAPElement params = elementTarget.addChildElement("ReferenceParameters", "a"); params.addChildElement("ResourceURI",
     * "w").setValue("http://schemas.dell.com/wbem/wscim/1/cim-schema/2/DCIM_SoftwareIdentity");
     * 
     * SOAPElement selectorSet = params.addChildElement("SelectorSet", "w"); SOAPElement selector = selectorSet.addChildElement("Selector", "w");
     * 
     * selector.addAttribute(new QName("Name"), "InstanceID");
     * 
     * selector.setValue(getInstanceId(filePath));
     * 
     * return elementTarget; }
     */

    private String getFileUri(String filePath, String userName, String password) throws MalformedURLException {
        String uri = "";

        if (filePath == null || filePath.equals("")) {
            throw new MalformedURLException("Update File Path is Null or Empty");
        }
        // Check if File path is not ftp, tftp, or http
        if (!filePath.contains("ftp://") && !filePath.contains("tftp://") && !filePath.contains("http://") && !filePath.contains("https://")) {
            if (filePath.contains("\\")) {
                filePath = filePath.replace("\\", "/");
                String[] values = filePath.split("/");
                if (values.length >= 5) {
                    StringBuilder file = new StringBuilder();
                    for (int i = 4; i < values.length; i++) {
                        file.append("/");
                        file.append(values[i]);
                    }
                    uri = String.format("cifs://%s:%s@%s:%s;mountpoint=%s", userName, password, values[2], file.toString(), values[3]);
                } else {
                    throw new MalformedURLException(filePath + ": is not a valid network share path");
                }
            } else {
                throw new MalformedURLException(filePath + ": is not a valid path");
            }
        } else {
            URL url = new URL(filePath);
            uri = url.toString();
        }
        // Check for the validity of http, https, ftp, tftp URLs
        return uri;
    }

}
