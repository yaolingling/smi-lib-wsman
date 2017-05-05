/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.entity.LicensableDevice;

public class GetLicensableDevicesCmd extends WSManBaseCommand {

    private static final Logger logger = LoggerFactory.getLogger(GetLicensableDevicesCmd.class);

    private WSManageSession session = null;


    public GetLicensableDevicesCmd(String ipAddr, String userName, String passwd, boolean bCertCheck) {
        super(ipAddr, userName, passwd, bCertCheck);

        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: GetLicensableDevicesCmd(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        session = this.getSession();

        logger.trace("Exiting constructor: GetLicensableDevicesCmd()");
    }


    private String getResourceURI(String className) {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(className);
        return sb.toString();
    }


    @Override
    public Object execute() throws Exception {
        logger.info("Executing the GetLicensableDevices (DCIM_LicensableDevice) command for iDRAC: " + session.getIpAddress());
        List<LicensableDevice> licenses = new ArrayList<LicensableDevice>();
        try {
            String resourceUri = getResourceURI(WSManClassEnum.DCIM_LicensableDevice.toString());
            session.setResourceUri(resourceUri);
            NodeList response = this.sendRequestEnumerationReturnNodeList();
            logger.info("Successfully executed the GetLicensableDevices command for iDRAC: " + session.getIpAddress());
            if (null != response) {
                logger.info("Found the GetLicensableDevices command response for iDRAC: " + session.getIpAddress());
                XPathFactory xPathFactory = XPathFactory.newInstance();
                XPath xpath = xPathFactory.newXPath();
                logger.info("Found (" + response.getLength() + ") Licensable Devices for iDRAC: " + session.getIpAddress());
                for (int i = 0; i < response.getLength(); i++) {
                    Node itemNode = response.item(i);
                    if (itemNode != null) {
                        LicensableDevice license = new LicensableDevice();
                        license.setDeviceId(xpath.evaluate("//*[local-name()='DeviceID']/text()", itemNode));
                        license.setDeviceStatus(xpath.evaluate("//*[local-name()='DevicePrimaryStatus']/text()", itemNode));
                        license.setDeviceStatusMessage(xpath.evaluate("//*[local-name()='DeviceStatusMessage']/text()", itemNode));
                        license.setFQDD(xpath.evaluate("//*[local-name()='FQDD']/text()", itemNode));
                        license.setModel(xpath.evaluate("//*[local-name()='Model']/text()", itemNode));
                        licenses.add(license);
                    }
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.info("Successfully returning (" + licenses.size() + ") Licensable Devices for iDRAC: " + session.getIpAddress());
        return licenses;
    }

}
