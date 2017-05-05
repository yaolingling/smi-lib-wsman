/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.command.entity.DCIMSystemViewType;
import com.dell.isg.smi.wsman.entity.DeviceLicense;
import com.dell.isg.smi.wsman.entity.LicensableDevice;
import com.dell.isg.smi.wsman.entity.LicenseKey;
import com.dell.isg.smi.wsman.entity.ResourceURIInfo;

public class GetDeviceLicensesCmd extends WSManBaseCommand {

    private static final Logger logger = LoggerFactory.getLogger(GetDeviceLicensesCmd.class);
    private WSManageSession session = null;


    public GetDeviceLicensesCmd(String ipAddr, String userName, String passwd, boolean bCertCheck) {
        super(ipAddr, userName, passwd, bCertCheck);

        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: GetDeviceLicensesCmd(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }

        session = super.getSession();

        logger.trace("Exiting constructor: GetDeviceLicensesCmd()");

    }


    private String getResourceURI(String className) {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(className);
        return sb.toString();
    }


    @SuppressWarnings("unchecked")
    @Override
    public Object execute() throws Exception {

        logger.info("Executing the GetDeviceLicensesCommand for iDRAC: " + session.getIpAddress());
        final String UNBOUND = "Unbound";
        // FIX ME for ICEE
        List<DeviceLicense> licenses = new ArrayList<DeviceLicense>();
        // See if the license manager is present.
        // License Manager could be missing on 11G host.
        // Command throws exception if license manager is not present.
        logger.info("Checking if License Manager is availabe on the iDRAC: " + session.getIpAddress());

        GetResourceURIInfoCmd cmd = new GetResourceURIInfoCmd(session.getIpAddress(), session.getUser(), session.getPassword(), session.isCertificateCheck(), WSManClassEnum.DCIM_LicenseManagementService.toString());
        Object objResourceInfo = null;
        try {
            objResourceInfo = cmd.execute();
        } catch (Exception e) {
            logger.warn("License Manager is not available on iDRAC: " + session.getIpAddress());
            throw e;
        }

        if (objResourceInfo != null) {
            logger.info("License Manager is available");
            ResourceURIInfo info = (ResourceURIInfo) objResourceInfo;
            // Now get the licensable devices on the system.
            logger.info("First find the LicensableDevices for iDRAC: " + session.getIpAddress());
            GetLicensableDevicesCmd getLicDevCmd = new GetLicensableDevicesCmd(session.getIpAddress(), session.getUser(), session.getPassword(), session.isCertificateCheck());
            Object objDevices = getLicDevCmd.execute();
            if (objDevices != null) {
                List<LicensableDevice> devices = (List<LicensableDevice>) objDevices;
                if (devices.size() > 0) {
                    logger.info("Found the LicensableDevices for iDRAC: " + session.getIpAddress());
                    // Now get the licenses for the devices
                    session.setResourceUri(getResourceURI(WSManClassEnum.DCIM_License.toString()));
                    NodeList response = this.sendRequestEnumerationReturnNodeList();
                    logger.info("Found the Enumerate (DCIM_License) response for iDRAC: " + session.getIpAddress());
                    if (null != response) {
                        XPathFactory xPathFactory = XPathFactory.newInstance();
                        XPath xpath = xPathFactory.newXPath();
                        if (response.getLength() > 0) {
                            for (int i = 0; i < response.getLength(); i++) {
                                Node itemNode = response.item(i);
                                if (itemNode != null) {
                                    String hostId = session.getIpAddress();
                                    String entitlementId = xpath.evaluate("//*[local-name()='EntitlementID']/text()", itemNode);
                                    DeviceLicense license = new DeviceLicense(new LicenseKey(entitlementId, hostId));
                                    license.setLicenseType(xpath.evaluate("//*[local-name()='LicenseType']/text()", itemNode));
                                    license.setLicenseStatusMessage(xpath.evaluate("//*[local-name()='LicenseStatusMessage']/text()", itemNode));
                                    license.setLicenseStartDate(xpath.evaluate("//*[local-name()='LicenseStartDate']/text()", itemNode));
                                    license.setLicenseEndDate(xpath.evaluate("//*[local-name()='LicenseEndDate']/text()", itemNode));
                                    license.setLicenseInstallDate(xpath.evaluate("//*[local-name()='LicenseInstallDate']/text()", itemNode));
                                    license.setLicenseSoldDate(xpath.evaluate("//*[local-name()='LicenseSoldDate']/text()", itemNode));
                                    license.setLicensePrimaryStatus(xpath.evaluate("//*[local-name()='LicensePrimaryStatus']/text()", itemNode));
                                    license.setLicenseDuration(xpath.evaluate("//*[local-name()='LicenseDuration']/text()", itemNode));
                                    license.setLicenseDescription(xpath.evaluate("//*[local-name()='LicenseDescription']/text()", itemNode));
                                    license.setLicenseAttributes(xpath.evaluate("//*[local-name()='LicenseAttributes']/text()", itemNode));
                                    if (license.getLicenseAttributes().equalsIgnoreCase(UNBOUND)) {
                                        try {
                                            EnumerateSystemViewCmd viewCmd = new EnumerateSystemViewCmd(session.getIpAddress(), session.getUser(), session.getPassword());
                                            DCIMSystemViewType type = viewCmd.execute();
                                            if (type != null)
                                                license.setServiceTag(type.getServiceTag().getValue().toString());
                                        } catch (Exception exp) {
                                            license.setServiceTag("UNKNOWN");
                                            logger.error(exp.getMessage());
                                        }
                                    }

                                    license.setEvalLicenseTimeRemaining(xpath.evaluate("//*[local-name()='EvalLicenseTimeRemaining']/text()", itemNode));
                                    license.setiDracIp(session.getIpAddress());
                                    Object objNode = xpath.evaluate("//*[local-name()='AssignedDevices']", itemNode, XPathConstants.NODE);
                                    Node assignedDevices = null;
                                    if (objNode != null) {
                                        assignedDevices = (Node) objNode;
                                    }
                                    if (assignedDevices != null) {
                                        for (int j = 0; j < assignedDevices.getChildNodes().getLength(); j++) {
                                            String deviceFQDD = assignedDevices.getChildNodes().item(j).getNodeValue();
                                            // Check if the license is assigned to any device?
                                            for (LicensableDevice device : devices) {
                                                if (deviceFQDD.equalsIgnoreCase(device.getFQDD())) {
                                                    license.getDevices().add(device);
                                                }
                                            }
                                        }
                                        String licenseFile = "";
                                        try {
                                            logger.info("Finding License File");
                                            GetDeviceLicenseFileCmd licFileCmd = new GetDeviceLicenseFileCmd(session.getIpAddress(), session.getUser(), session.getPassword(), session.isCertificateCheck(), license.getLicenseKey().getEntitlementId());
                                            licFileCmd.setResourceUriInfo(info);
                                            Object objFile = licFileCmd.execute();
                                            if (objFile != null) {
                                                logger.info("License File Found");
                                                licenseFile = objFile.toString();
                                            }
                                        } catch (Exception e) {
                                            logger.error(e.getMessage());
                                            logger.error("Unable to get the license file for the device");
                                        }
                                        if (licenseFile != null && !licenseFile.isEmpty()) {

                                            license.setLicenseFile(licenseFile);
                                            String serviceTag = "";
                                            if (!license.getLicenseAttributes().equalsIgnoreCase(UNBOUND)) {
                                                logger.info("Extracting server ServiceTag from the license file.");
                                                serviceTag = getServiceTagFromLicenseFile(licenseFile);
                                                license.setServiceTag(serviceTag);
                                            }

                                        }
                                    }
                                    licenses.add(license);

                                }
                            }
                        } else {
                            logger.info("No Licenses found on iDRAC: " + session.getIpAddress());
                        }
                    } else {
                        logger.info("No Licenses found on iDRAC: " + session.getIpAddress());
                    }
                    // Searching for the feature bits
                    if (licenses.size() > 0) {
                        GetLicenseBits featureCmd = new GetLicenseBits(session.getIpAddress(), session.getUser(), session.getPassword());
                        String features = (String) featureCmd.execute();
                        if (features != null) {
                            (licenses.get(0)).setFeatureBits(features);
                        }
                    }
                } else {
                    logger.info("No Licensable Devices found on the given iDRAC: " + session.getIpAddress());
                }

            } else {
                logger.info("No Licensable Devices found on the given iDRAC: " + session.getIpAddress());
            }
        } else {
            logger.info("License Manager is not available on iDRAC: " + session.getIpAddress());
        }

        return licenses;
    }


    private String getServiceTagFromLicenseFile(String licenseFile) {
        String serviceTag = "";
        try {
            Document doc = this.convertStringToXMLDocument(licenseFile);
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xpath = xPathFactory.newXPath();
            serviceTag = xpath.evaluate("//*[local-name()='ServiceTag']", doc);
        } catch (ParserConfigurationException e) {
            logger.error(e.getMessage());
        } catch (SAXException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (XPathExpressionException e) {
            logger.error(e.getMessage());
        }
        return serviceTag;
    }

}
