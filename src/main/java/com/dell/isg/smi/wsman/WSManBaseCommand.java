/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.slf4j.LoggerFactory;

import com.dell.isg.smi.commons.elm.exception.RuntimeCoreException;
import com.dell.isg.smi.wsman.WSManageSession.EnumReferenceParam;
import com.dell.isg.smi.wsman.command.PersonalNamespaceContext;
import com.dell.isg.smi.wsman.command.entity.WsmanCredentials;
import com.sun.ws.management.addressing.Addressing;
import com.sun.ws.management.enumeration.Enumeration;
import com.sun.ws.management.enumeration.EnumerationExtensions.Mode;

public abstract class WSManBaseCommand {

    private WSManageSession WSManSession = null;

    private static final Logger logger = LoggerFactory.getLogger(WSManBaseCommand.class);


    protected WSManBaseCommand(String ipAddr, WsmanCredentials credentials) {
        this(ipAddr, credentials.getUserid(), credentials.getPassword(), credentials.isCertificateCheck());

    }


    protected WSManBaseCommand(String ipAddr, String userName, String passwd) {
        this(ipAddr, "443", userName, passwd, false);
    }


    protected WSManBaseCommand(String ipAddr, String userName, String passwd, boolean certCheck) {
        this(ipAddr, "443", userName, passwd, certCheck);
    }


    protected WSManBaseCommand(String ipAddr, String port, String userName, String passwd) {
        this(ipAddr, port, userName, passwd, false);
    }


    protected WSManBaseCommand(String ipAddr, String port, String userName, String passwd, boolean certCheck) {
        logger.trace("Entering Constructor: WSManBaseCommand(ipAddr{} port{} userName{} passwd{} certCheck {})", ipAddr, port, userName, "####", Boolean.toString(certCheck));
        WSManSession = new WSManageSession(ipAddr, userName, passwd, port, certCheck);
        WSManSession.owningCmd = this.getClass().getName();
        logger.trace("Exiting Constructor: WSManBaseCommand()");
    }


    protected WSManageSession getSession() {
        return WSManSession;
    }


    protected void setUserParam(String key, String value) {
        getSession().addUserParam(key, value);
    }


    public abstract Object execute() throws Exception;


    protected NodeList sendRequestEnumerationReturnNodeList() throws Exception {

        Document response = sendRequestEnumerationReturnDocument();
        Element element = response.getDocumentElement();
        NodeList nodeList = element.getElementsByTagNameNS(WSCommandRNDConstant.WS_MAN_NAMESPACE, WSCommandRNDConstant.WSMAN_ITEMS_TAG);

        return nodeList;

    }


    protected org.w3c.dom.Document sendRequestEnumerationReturnDocument() throws Exception {
        Addressing retAddressing = getSession().sendRequestEnumeration();
        return getSession().extractAddressBody(retAddressing);
    }


    protected org.w3c.dom.Document sendRequestEnumerationReturnDocument(Mode enumMode) throws Exception {
        Addressing retAddressing = getSession().sendRequestEnumeration(enumMode);
        return getSession().extractAddressBody(retAddressing);
    }


    protected Enumeration sendRequestEnumeration() throws Exception {
        return new Enumeration(getSession().sendRequestEnumeration());
    }


    protected Enumeration sendRequestEnumeration(Mode enumMode) throws SOAPException, JAXBException, DatatypeConfigurationException, IOException, XPathExpressionException {
        return new Enumeration(getSession().sendRequestEnumeration(enumMode));
    }

    public enum WSManClassEnum {
        DCIM_SoftwareIdentity, DCIM_SoftwareInstallationService, DCIM_SoftwareUpdateConcreteJob, DCIM_SystemView, DCIM_SystemEnumeration, DCIM_SystemString, DCIM_SystemInteger, DCIM_ControllerView, DCIM_VirtualDiskView, DCIM_PhysicalDiskView, DCIM_BIOSEnumeration, DCIM_BootSourceSetting, DCIM_BootConfigSetting, DCIM_BIOSString, DCIM_BIOSInteger, DCIM_NICView, DCIM_LifecycleJob, DCIM_JobService, InstallFromURI, CreateRebootJob, SetupJobQueue, DCIM_ComputerSystem, DCIM_OSDeploymentService, DCIM_BIOSService, DCIM_OEM_DataAccessModule, DCIM_RAIDService, CIM_ComputerSystem, DCIM_LCService, DCIM_IDRACCardView, DCIM_SPComputerSystem, CIM_IPProtocolEndpoint, CIM_Chassis, CIM_SoftwareIdentity, CIM_InstalledSoftwareIdentity, DCIM_Sellogentry, DCIM_Memoryview, DCIM_Powersupplyview, DCIM_PCIDeviceView, DCIM_CPUView, DCIM_EnclosureView, DCIM_iDRACCardAttribute, DCIM_PSNumericsensor, DCIM_iDRACCardString, DCIM_iDRACCardEnumeration, DCIM_NICStatistics, DCIM_BaseMetricValue, DCIM_AggregationMetricValue, CIM_Account, DCIM_LicenseManagementService, DCIM_LicensableDevice, DCIM_SelRecordLog, DCIM_License, DCIM_SystemManagementService, DCIM_ModularChassisView, DCIM_BladeServerView, CIM_PhysicalElement, DCIM_LCLogEntry, DCIM_RAIDEnumeration, DCIM_RAIDString, DCIM_RAIDInteger, DCIM_NICEnumeration, DCIM_NICString, DCIM_NICInteger;
    }

    public enum WSManMethodParamEnum {
        INSTANCE_ID("InstanceID"), SOURCE("source"), ENABLED_STATE("EnabledState"), REBOOT_JOB_TYPE("RebootJobType"), SCHEDULED_START_TIME("ScheduledStartTime"), SYSTEM_CLASS_NAME("SystemCreationClassName"), CREATION_CLASS_NAME("CreationClassName"), SYSTEM_NAME("SystemName"), NAME("Name"), TARGET("Target"), REBOOT_IF_REQUIRED("RebootIfRequired"), REQUESTED_STATE("RequestedState"), TIME_OUT_PERIOD("TimeoutPeriod"), ATTRIBUTE_NAME("AttributeName"), ATTRIBUTE_VALUE("AttributeValue"), PROVISIONING_SERVER("ProvisioningServer"), RESET_TO_FACTORY_DEFAULTS("ResetToFactoryDefaults"), PERFORM_AUTO_DISCOVERY("PerformAutoDiscovery");
        String enumValue;


        WSManMethodParamEnum(String value) {
            enumValue = value;
        }


        @Override
        public String toString() {
            return enumValue;
        }
    }

    public enum WSManMethodEnum {
        DRIVER_PACK_INFO("GetDriverPackInfo"), HOST_MAC_INFO("GetHostMACInfo"), BOOT_TO_PXE("BootToPXE"), BOOT_REMOTE_ISO("BootToNetworkISO"), UNPACK_AND_ATTACH("UnpackAndAttach"), DETACH_ISO("DetachISOImage"), DETACH_DRIVERS("DetachDrivers"), DETACH_ISO_VFLASH("DetachISOFromVFlash"), DELETE_ISO_VFLASH("DeleteISOFromVFlash"), DOWNLOAD_ISO_VFLASH("DownloadISOToVFlash"), BOOT_ISO_VFLASH("BootToISOFromVFlash"), CREATE_REBOOT_JOB("CreateRebootJob"), DELETE_JOB_QUEUE("DeleteJobQueue"), SETUP_JOB_QUEUE("SetupJobQueue"), INSTALL_FROM_URI("InstallFromURI"), CHANGE_BOOT_ORDER("ChangeBootOrderByInstanceID"), CHANGE_BOOT_STATUS("ChangeBootSourceState"), CREATE_TARGET_CONFIG_JOB("CreateTargetedConfigJob"), CREATE_LC_SERVICE_CONFIG_JOB("CreateConfigJob"), SET_ATTRIBUTE("SetAttribute"), SET_ATTRIBUTES("SetAttributes"), SEND_CMD("SendCmd"), REQUESTED_STATE_CHANGE("RequestStateChange"), REINITIATE_DHS("ReInitiateDHS"), GET_RS_STATUS("GetRSStatus"), GET_REMOTE_SERVICES_API_STATUS("GetRemoteServicesAPIStatus"), GET_NETWORK_ISO_IMAGE_CONNECTION_INFO("GetNetworkISOImageConnectionInfo"), CONNECT_TO_NEWORK_ISO_IMAGE("ConnectNetworkISOImage"), DISCONNECT_NETWORK_ISO_IMAGE("DisconnectNetworkISOImage"), EXPORT_LICENSE("ExportLicense"), CLEAR_SEL_LOG("ClearLog"), BLINK_LED("IdentifyChassis"), EXPORT_SYSTEM_CONFIGURATION("ExportSystemConfiguration"), IMPORT_SYSTEM_CONFIGURATION("ImportSystemConfiguration"), EXPORT_TECH_SUPPORT_REPORT("ExportTechSupportReport");
        String enumValue;


        WSManMethodEnum(String value) {
            enumValue = value;
        }


        @Override
        public String toString() {
            return enumValue;
        }
    }


    protected Document convertStringToXMLDocument(String xmlSource) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(xmlSource)));
        return document;
    }


    protected static Document callCmdLine(String cmdLine) {
        Document retDoc = null;
        StringBuilder cmdSb = new StringBuilder("cmd //c \"");
        cmdSb.append(cmdLine);
        cmdSb.append("\"");
        try {
            Process p = Runtime.getRuntime().exec(cmdLine);
            p.waitFor();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = null;
            try {
                builder = factory.newDocumentBuilder();
            } catch (ParserConfigurationException ex) {
                Marker fatal = MarkerFactory.getMarker("FATAL");
                LoggerFactory.getLogger(WSManBaseCommand.class).error(fatal, ex.getMessage(), ex);
            }
            InputStream xmlStream = p.getInputStream();
            try {
                if (builder != null)
                    retDoc = builder.parse(xmlStream);
            } catch (SAXException ex) {
                Marker fatal = MarkerFactory.getMarker("FATAL");
                LoggerFactory.getLogger(WSManBaseCommand.class).error(fatal, ex.getMessage(), ex);
            }

        } catch (InterruptedException ex) {
            Marker fatal = MarkerFactory.getMarker("FATAL");
            LoggerFactory.getLogger(WSManBaseCommand.class).error(fatal, ex.getMessage(), ex);

        } catch (IOException e1) {
            Marker fatal = MarkerFactory.getMarker("FATAL");
            LoggerFactory.getLogger(WSManBaseCommand.class).error(fatal, e1.getMessage(), e1);
        }
        return retDoc;
    }


    protected List<EnumReferenceParam> sendRequestEnumerationWithEPR(Mode enumMode) throws Exception {
        Addressing addressing = getSession().sendRequestEnumeration(enumMode);
        return getSession().extractRequestEnumerationRefParameters(new Enumeration(addressing));
    }


    protected void addSelectors(String resourceUri) {
        try {
            if (StringUtils.isEmpty(this.WSManSession.getResourceUri()) || StringUtils.isBlank(this.WSManSession.getResourceUri())) {
                this.WSManSession.setResourceUri(resourceUri);
            }
            List<EnumReferenceParam> enumRefParams = this.sendRequestEnumerationWithEPR(Mode.EnumerateEPR);
            this.WSManSession.getSelectors().addAll(enumRefParams.get(0).getSelectorSetTypes().getSelector());
        } catch (Exception e) {
            throw new RuntimeCoreException(e);
        }
    }


    public Document getlastRequestBody() {
        return getSession().getLastRequestBody();
    }


    public Addressing getlastAddressingResponse() {
        return getSession().getlastAddressingResponse();
    }


    public Addressing getlastAddressingRequest() {
        return getSession().getlastAddressingRequest();
    }


    public void resetSession(String destIP, String username, String password, String port) {
        getSession().resetSession(destIP, username, password, port);
    }


    protected Logger getLogger(Class c) {
        return LoggerFactory.getLogger(c);
    }


    protected void setTimeoutInSeconds(int to) {
        getSession().setTimeoutInSeconds(to);
    }


    public void addEnumerateFilter(String name, String value) {
        getSession().addFilter(name, value);
    }


    protected Object findObjectInDocument(SOAPElement doc, String xPathLocation, String namespace, QName qname) throws XPathExpressionException {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        xpath.setNamespaceContext(new PersonalNamespaceContext(namespace));
        XPathExpression expr = xpath.compile(xPathLocation);
        Object result = expr.evaluate(doc, qname);
        return result;
    }


    public boolean waitForReadyStatus(long timeout) {
        return WSManUtilities.waitForLCReadyStatus(WSManSession.getIpAddress(), WSManSession.getUser(), WSManSession.getPassword(), timeout);
    }


    public boolean waitForReadyStatus(long timeout, long readyStateTimeout) {
        return WSManUtilities.waitForLCReadyStatus(WSManSession.getIpAddress(), WSManSession.getUser(), WSManSession.getPassword(), timeout);
    }


    public boolean waitForReadyStatusPostCSIOR(long timeout) {
        return WSManUtilities.waitForLCReadyStatusPostCSIOR(WSManSession.getIpAddress(), WSManSession.getUser(), WSManSession.getPassword(), timeout);
    }


    public String getLCErrorCode() {
        return this.getSession().getLCMessageID();
    }


    public String getLCErrorStr() {
        return this.getSession().getLCMessageStr();
    }


    protected String getUpdateJobID(String xmlSource) throws Exception {
        String jobId = "";
        if (xmlSource != null && !StringUtils.isEmpty(xmlSource)) {
            Document doc = convertStringToXMLDocument(xmlSource);
            if (doc != null) {
                NodeList selectors = doc.getElementsByTagName("wsman:Selector");
                if (selectors != null) {
                    for (int i = 0; i < selectors.getLength(); i++) {
                        Node selectorNode = selectors.item(i);
                        if (selectorNode.hasAttributes()) {
                            NamedNodeMap attribs = selectorNode.getAttributes();
                            Node attribNode = attribs.getNamedItem("Name");
                            if (attribNode != null) {
                                if (attribNode.hasChildNodes()) {
                                    Node instanceNode = attribNode.getChildNodes().item(0);
                                    if (instanceNode != null) {
                                        String instance = instanceNode.getNodeValue();
                                        if (instance.equals("InstanceID")) {
                                            jobId = selectorNode.getFirstChild().getNodeValue();
                                            break;
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
        return jobId;
    }


    protected String getKeyValue(Node node, String key) {
        String value = "";
        try {
            NodeList childList = node.getChildNodes();
            for (int i = 0; i < childList.getLength(); i++) {
                Node childNode = childList.item(i);
                if (childNode.getNodeName().contains(key)) {
                    if (childNode.hasChildNodes()) {
                        NodeList nodeList1 = childNode.getChildNodes();
                        for (int k = 0; k < nodeList1.getLength(); k++) {
                            Node finalNode = nodeList1.item(k);
                            if (Element.TEXT_NODE == finalNode.getNodeType()) {
                                value = finalNode.getNodeValue();
                                break;
                            }
                        }
                        if (!value.isEmpty()) {
                            break;
                        }
                    }
                }
            }
        } catch (Exception exp) {
            logger.error(exp.getMessage(), exp);
        }
        return value;
    }
}
