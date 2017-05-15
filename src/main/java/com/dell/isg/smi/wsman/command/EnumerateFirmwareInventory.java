/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.commons.utilities.xml.XmlHelper;
import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.command.entity.CimString;
import com.dell.isg.smi.wsman.command.entity.Classifications;
import com.dell.isg.smi.wsman.command.entity.DCIMSoftwareIdentityType;
import com.dell.isg.smi.wsman.command.entity.IdentityInfoValue;
import com.dell.isg.smi.wsman.command.entity.TargetOSTypes;
import com.dell.isg.smi.wsman.entity.SoftwareIdentity;
import com.sun.ws.management.addressing.Addressing;

public class EnumerateFirmwareInventory extends WSManBaseCommand {

    private WSManageSession session = null;

    private String hostAddress;

    private static final Logger logger = LoggerFactory.getLogger(EnumerateFirmwareInventory.class);


    public EnumerateFirmwareInventory(String ipAddr, int port, String userName, String passwd, boolean bCertcheck) {

        super(ipAddr, port + "", userName, passwd, bCertcheck);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: EnumerateFirmwareInventory(String ipAddr - %s, String userName - %s, String passwd - %s, boolean bCertcheck - %s)", ipAddr, userName, "####", Boolean.toString(bCertcheck)));
        }
        session = super.getSession();
        hostAddress = ipAddr;
        session.setResourceUri(getResourceURI());
        logger.trace("Exiting constructor: EnumerateFirmwareInventory()");

    }


    private String getResourceURI() {

        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_SoftwareIdentity);
        return sb.toString();

        // return ("http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/root/dcim/DCIM_SoftwareIdentity");
    }


    @Override
    public Object execute() throws Exception {
        logger.trace("Entering function: execute()");
        Object result = null;
        Addressing response = null;
        response = session.sendRequestEnumeration();

        if (response != null) {
            Document document = response.getBody().extractContentAsDocument();

            if (document != null) {
                result = parseResponse(document);
            }
        }
        logger.trace("Exiting function: execute()");
        return result;
    }


    protected SoftwareIdentity getSoftwareIdentity(Node identityNode) {
        SoftwareIdentity identity = null;

        if (identityNode.getNodeName().contains(WSManClassEnum.DCIM_SoftwareIdentity.toString())) {
            identity = new SoftwareIdentity();

            NodeList childNodes = identityNode.getChildNodes();

            for (int j = 0; j < childNodes.getLength(); j++) {
                Node node = childNodes.item(j);

                if (node.getLocalName().equals("BuildNumber")) {
                    if (node.getFirstChild() != null) {
                        identity.setBuildNumber(node.getFirstChild().getNodeValue());
                    }
                } else if (node.getLocalName().equals("Classifications")) {
                    if (node.getFirstChild() != null) {
                        identity.setClassifications(node.getFirstChild().getNodeValue());
                    }
                } else if (node.getLocalName().equals("ComponentID")) {
                    if (node.getFirstChild() != null) {
                        identity.setComponentID(node.getFirstChild().getNodeValue());
                    }
                } else if (node.getLocalName().equals("ComponentType")) {
                    if (node.getFirstChild() != null) {
                        identity.setComponentType(node.getFirstChild().getNodeValue());
                    }
                } else if (node.getLocalName().equals("Description")) {
                    if (node.getFirstChild() != null) {
                        identity.setDescription(node.getFirstChild().getNodeValue());
                    }
                } else if (node.getLocalName().equals("DetailedStatus")) {
                    if (node.getFirstChild() != null) {
                        identity.setDetailedStatus(node.getFirstChild().getNodeValue());
                    }
                }

                else if (node.getLocalName().equals("DeviceID")) {
                    if (node.getFirstChild() != null) {
                        identity.setDeviceID(node.getFirstChild().getNodeValue());
                    }
                }

                else if (node.getLocalName().equals("ElementName")) {
                    if (node.getFirstChild() != null) {
                        identity.setElementName(node.getFirstChild().getNodeValue());
                    }
                }

                else if (node.getLocalName().equals("IdentityInfoValue")) {
                    if (node.getFirstChild() != null) {
                        identity.setIdentityInfoValue(node.getFirstChild().getNodeValue());
                    }
                }

                else if (node.getLocalName().equals("InstallDate")) {
                    if (node.getFirstChild() != null) {
                        identity.setInstallDate(node.getFirstChild().getNodeValue());
                    }
                }

                else if (node.getLocalName().equals("InstallationDate")) {
                    if (node.getFirstChild() != null) {
                        identity.setInstallationDate(node.getFirstChild().getNodeValue());
                    }
                }

                else if (node.getLocalName().equals("InstanceID")) {
                    if (node.getFirstChild() != null) {
                        identity.setInstanceID(node.getFirstChild().getNodeValue());
                    }
                }

                else if (node.getLocalName().equals("IsEntity")) {
                    if (node.getFirstChild() != null) {
                        identity.setIsEntity(node.getFirstChild().getNodeValue());
                    }
                }

                else if (node.getLocalName().equals("MajorVersion")) {
                    if (node.getFirstChild() != null) {
                        identity.setMajorVersion(node.getFirstChild().getNodeValue());
                    }
                }

                else if (node.getLocalName().equals("Manufacturer")) {
                    if (node.getFirstChild() != null) {
                        identity.setManufacturer(node.getFirstChild().getNodeValue());
                    }
                }

                else if (node.getLocalName().equals("MinorVersion")) {
                    if (node.getFirstChild() != null) {
                        identity.setMinorVersion(node.getFirstChild().getNodeValue());
                    }
                }

                else if (node.getLocalName().equals("ReleaseDate")) {
                    if (node.getFirstChild() != null) {
                        identity.setReleaseDate(node.getFirstChild().getNodeValue());
                    }
                }

                else if (node.getLocalName().equals("RevisionNumber")) {
                    if (node.getFirstChild() != null) {
                        identity.setRevisionNumber(node.getFirstChild().getNodeValue());
                    }
                }

                else if (node.getLocalName().equals("RevisionString")) {
                    if (node.getFirstChild() != null) {
                        identity.setRevisionString(node.getFirstChild().getNodeValue());
                    }
                }

                else if (node.getLocalName().equals("SerialNumber")) {
                    if (node.getFirstChild() != null) {
                        identity.setSerialNumber(node.getFirstChild().getNodeValue());
                    }
                }

                else if (node.getLocalName().equals("Status")) {
                    if (node.getFirstChild() != null) {
                        identity.setStatus(node.getFirstChild().getNodeValue());
                    }
                }

                else if (node.getLocalName().equals("SubDeviceID")) {
                    if (node.getFirstChild() != null) {
                        identity.setSubDeviceID(node.getFirstChild().getNodeValue());
                    }
                }

                else if (node.getLocalName().equals("SubVendorID")) {
                    if (node.getFirstChild() != null) {
                        identity.setSubVendorID(node.getFirstChild().getNodeValue());
                    }
                }

                else if (node.getLocalName().equals("TargetOSTypes")) {
                    if (node.getFirstChild() != null) {
                        identity.setTargetOSTypes(node.getFirstChild().getNodeValue());
                    }
                }

                else if (node.getLocalName().equals("TargetOperatingSystems")) {
                    if (node.getFirstChild() != null) {
                        identity.setTargetOperatingSystems(node.getFirstChild().getNodeValue());
                    }
                }

                else if (node.getLocalName().equals("Updateable")) {
                    if (node.getFirstChild() != null) {
                        // by default, treat updateable as true to support updates for
                        // the DCIM_SoftwareIdentity 1.0 profile that did not include an
                        // updateable property
                        boolean updateable = true;
                        if ((null != node.getFirstChild().getNodeValue()) && (node.getFirstChild().getNodeValue().equalsIgnoreCase("false"))) {
                            updateable = false;
                        }
                        identity.setUpdateable(updateable);
                    }
                }

                else if (node.getLocalName().equals("VendorID")) {
                    if (node.getFirstChild() != null) {
                        identity.setVendorID(node.getFirstChild().getNodeValue());
                    }
                }

                else if (node.getLocalName().equals("VersionString")) {
                    if (node.getFirstChild() != null) {
                        identity.setVersionString(node.getFirstChild().getNodeValue());
                    }
                }
            }
        }

        return identity;
    }


    protected List<SoftwareIdentity> parseResponse(Document document) {

        List<SoftwareIdentity> resp = new ArrayList<SoftwareIdentity>();

        Element element = document.getDocumentElement();

        NodeList nodeList = element.getElementsByTagNameNS(WSCommandRNDConstant.WS_MAN_NAMESPACE, WSCommandRNDConstant.SOFTWARE_IDENTITY_ITEMS_TAG);

        if (nodeList != null) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                if (nodeList.item(i).getNodeType() == Element.ELEMENT_NODE) {
                    NodeList softwareIdentityNodes = nodeList.item(i).getChildNodes();

                    if (softwareIdentityNodes != null) {
                        for (int j = 0; j < softwareIdentityNodes.getLength(); j++) {
                            Node identityNode = softwareIdentityNodes.item(j);

                            try {
                                DCIMSoftwareIdentityType dcimSI = (DCIMSoftwareIdentityType) XmlHelper.xmlToObject(identityNode, DCIMSoftwareIdentityType.class);
                                if (dcimSI != null) {
                                    SoftwareIdentity identity = convertDCIMSoftwareIdentity(dcimSI);
                                    if (identity != null && !resp.contains(identity)) {
                                        if (identity.getStatus().equals("Installed")) {
                                            resp.add(identity);
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }

                        }
                    }
                }
            }
        }
        return resp;
    }


    private SoftwareIdentity convertDCIMSoftwareIdentity(DCIMSoftwareIdentityType type) {
        SoftwareIdentity identity = new SoftwareIdentity();

        if (type.getBuildNumber() != null) {
            identity.setBuildNumber(type.getBuildNumber().getValue() + "");
        }

        if (type.getComponentID() != null) {
            identity.setComponentID(type.getComponentID().getValue());
        }

        if (type.getComponentType() != null) {
            identity.setComponentType(type.getComponentType().getValue());
        }

        if (type.getDescription() != null) {
            identity.setDescription(type.getDescription().getValue());
        }

        if (type.getDetailedStatus() != null && type.getDetailedStatus().getValue() != null) {
            identity.setDetailedStatus(type.getDetailedStatus().getValue().toString());
        }

        if (type.getDeviceID() != null) {
            identity.setDeviceID(type.getDeviceID().getValue());
        }

        if (type.getElementName() != null) {
            identity.setElementName(type.getElementName().getValue());
        }

        if (type.getIdentityInfoValue() != null) {
            List<IdentityInfoValue> list = type.getIdentityInfoValue();
            if (list.size() > 0) {
                identity.setIdentityInfoValue(list.get(0).getValue());
            }
        }

        if (type.getInstallDate() != null && type.getInstallDate().getDatetime() != null) {
            identity.setInstallDate(type.getInstallDate().getDatetime().toString());
        }

        if (type.getInstallationDate() != null) {
            identity.setInstallationDate(type.getInstallationDate().getValue());
        }

        if (type.getInstanceID() != null) {
            identity.setInstanceID(type.getInstanceID().getValue());
        }

        if (type.getIsEntity() != null) {
            if (type.getIsEntity().isValue()) {
                identity.setIsEntity("TRUE");
            } else {
                identity.setIsEntity("FALSE");
            }
        }

        if (type.isUpdateable() != null) {
            if (type.isUpdateable().isValue()) {
                identity.setUpdateable(true);
            } else {
                identity.setUpdateable(false);
            }
        }

        if (type.getMajorVersion() != null) {
            identity.setMajorVersion(type.getMajorVersion().getValue() + "");
        }

        if (type.getMinorVersion() != null) {
            identity.setMinorVersion(type.getMinorVersion().getValue() + "");
        }

        if (type.getReleaseDate() != null && type.getReleaseDate().getDatetime() != null) {
            identity.setReleaseDate(type.getReleaseDate().getDatetime().toString());
        }

        if (type.getRevisionNumber() != null) {
            identity.setRevisionNumber(type.getRevisionNumber().getValue() + "");
        }

        if (type.getRevisionString() != null) {
            identity.setRevisionString(type.getRevisionString().getValue());
        }

        if (type.getVersionString() != null) {
            identity.setVersionString(type.getVersionString().getValue());
        }

        if (type.getSerialNumber() != null) {
            identity.setSerialNumber(type.getSerialNumber().getValue());
        }

        if (type.getStatus() != null) {
            identity.setStatus(type.getStatus().getValue());
        }

        if (type.getSubDeviceID() != null) {
            identity.setSubDeviceID(type.getSubDeviceID().getValue());
        }

        if (type.getSubVendorID() != null) {
            identity.setSubVendorID(type.getSubVendorID().getValue());
        }

        if (type.getTargetOSTypes() != null) {
            List<TargetOSTypes> list = type.getTargetOSTypes();
            StringBuilder val = new StringBuilder();

            if (list != null) {
                for (TargetOSTypes os : list) {
                    if (os != null) {
                        val.append(os.getValue());
                        val.append(",");
                    }
                }
                if (val.length() > 0) {
                    val.deleteCharAt(val.length() - 1);
                }
            }

            identity.setTargetOSTypes(val.toString());
        }

        if (type.getTargetOperatingSystems() != null) {
            List<CimString> list = type.getTargetOperatingSystems();
            StringBuilder val = new StringBuilder();

            if (list != null) {
                for (CimString os : list) {
                    if (os != null && !os.getValue().isEmpty()) {
                        val.append(os.getValue());
                        val.append(",");
                    }
                }

                if (val.length() > 0) {
                    val.deleteCharAt(val.length() - 1);
                }
            }

            identity.setTargetOperatingSystems(val.toString());
        }

        if (type.getClassifications() != null) {
            List<Classifications> list = type.getClassifications();

            StringBuilder val = new StringBuilder();

            if (list != null) {
                for (Classifications cls : list) {
                    if (cls != null) {
                        val.append(cls.getValue());
                        val.append(",");
                    }
                }

                if (val.length() > 0) {
                    val.deleteCharAt(val.length() - 1);
                }
            }

            identity.setClassifications(val.toString());
        }

        if (type.getVendorID() != null) {
            identity.setVendorID(type.getVendorID().getValue());
        }

        return identity;
    }

}
