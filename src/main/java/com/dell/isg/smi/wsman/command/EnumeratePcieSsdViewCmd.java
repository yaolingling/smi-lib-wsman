/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManClientBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.command.entity.PcieSsdView;
import com.dell.isg.smi.wsmanclient.util.WSManUtils;
import com.sun.ws.management.addressing.Addressing;

/**
 * @author Prashanth.Gowda
 *
 */

public class EnumeratePcieSsdViewCmd extends WSManClientBaseCommand<List<PcieSsdView>> {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(EnumeratePcieSsdViewCmd.class);


    public EnumeratePcieSsdViewCmd() {
    }


    public EnumeratePcieSsdViewCmd(String ipAddr, String userName, String passwd) {
        super(ipAddr, userName, passwd);
        logger.trace("Entering constructor: EnumeratePcieSsdViewCmd (ipAddr{} userName{} passwd{})", ipAddr, userName, "####");
        session = this.getSession();
        session.setResourceUri(getResourceURI());
        logger.trace("Exiting constructor: EnumeratePcieSsdViewCmd()");
    }


    @Override
    public List<PcieSsdView> execute() throws Exception {
        logger.trace("Entering function: execute()");
        Addressing addressing = this.sendRequestEnumeration();
        NodeList nodeset = (NodeList) session.findObjectInDocument(addressing.getBody(), "//*[local-name()='DCIM_PCIeSSDView']", XPathConstants.NODESET);
        List<PcieSsdView> pcieSsdViewList = parse(nodeset);
        logger.trace("Exiting function: execute()");
        return pcieSsdViewList;
    }


    public String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_PCIeSSDView);
        return sb.toString();
    }


    @Override
    public List<PcieSsdView> parse(String xml) throws Exception {
        Document document = WSManUtils.toDocument(xml);
        NodeList nodeset = (NodeList) findObjectInDocument(document, "//*[local-name()='DCIM_PCIeSSDView']", XPathConstants.NODESET);
        List<PcieSsdView> pcieSsdViewList = parse(nodeset);
        return pcieSsdViewList;
    }


    private List<PcieSsdView> parse(NodeList nodeset) throws Exception {
        logger.trace("Entering function: parse()");
        List<PcieSsdView> pcieSsdViewList = new ArrayList<PcieSsdView>();
        for (int i = 0; i < nodeset.getLength(); i++) {
            Node node = nodeset.item(i);
            PcieSsdView pcieSsdView = new PcieSsdView();
            NodeList nodeList = node.getChildNodes();
            for (int j = 0; j < nodeList.getLength(); j++) {
                Node itemNode = nodeList.item(j);
                String nodeValue = itemNode.getTextContent();
                String nodeName = itemNode.getLocalName();
                if (nodeName == null) {
                    continue;
                }
                nodeName = nodeName.trim();
                switch (nodeName) {
                case PcieSsdViewConstants.INSTANCE_ID: {
                    pcieSsdView.setInstanceId(nodeValue);
                    break;
                }

                case PcieSsdViewConstants.FQDD: {
                    pcieSsdView.setFqdd(nodeValue);
                    break;
                }

                case PcieSsdViewConstants.PRIMARY_STATUS: {
                    pcieSsdView.setPrimaryStatus(nodeValue);
                    break;
                }
                case PcieSsdViewConstants.MODEL: {
                    pcieSsdView.setModel(nodeValue);
                    break;
                }

                case PcieSsdViewConstants.STATE: {
                    pcieSsdView.setState(nodeValue);
                    break;
                }
                case PcieSsdViewConstants.SIZE_IN_BYTES: {
                    pcieSsdView.setSizeInBytes(nodeValue);
                    break;
                }

                case PcieSsdViewConstants.BUS_PROTOCOL: {
                    pcieSsdView.setBusProtocol(nodeValue);
                    break;
                }
                case PcieSsdViewConstants.DEVICE_PROTOCOL: {
                    pcieSsdView.setDeviceProtocol(nodeValue);
                    break;
                }

                case PcieSsdViewConstants.DRIVER_VERSION: {
                    pcieSsdView.setDriverVersion(nodeValue);
                    break;
                }
                case PcieSsdViewConstants.MANUFACTURER: {
                    pcieSsdView.setManufacturer(nodeValue);
                    break;
                }

                case PcieSsdViewConstants.MEDIA_TYPE: {
                    pcieSsdView.setMediaType(nodeValue);
                    break;
                }
                case PcieSsdViewConstants.PRODUCT_ID: {
                    pcieSsdView.setProductId(nodeValue);
                    break;
                }

                case PcieSsdViewConstants.SERIAL_NUMBER: {
                    pcieSsdView.setSerialNumber(nodeValue);
                    break;
                }
                case PcieSsdViewConstants.PCIE_NEGOTIATED_WIDTH: {
                    pcieSsdView.setPcieNegotiatedLinkWidth(nodeValue);
                    break;
                }

                case PcieSsdViewConstants.PCIE_CAPABLE_LINK_WIDTH: {
                    pcieSsdView.setPcieCapableLinkWidth(nodeValue);
                    break;
                }
                case PcieSsdViewConstants.MAX_CAPABLE_SPEED: {
                    pcieSsdView.setMaxCapableSpeed(nodeValue);
                    break;
                }

                case PcieSsdViewConstants.NEGOTIATED_SPEED: {
                    pcieSsdView.setNegotiatedSpeed(nodeValue);
                    break;
                }
                case PcieSsdViewConstants.DRIVE_FORM_FACTOR: {
                    pcieSsdView.setDriveFormFactor(nodeValue);
                    break;
                }

                case PcieSsdViewConstants.REVISION: {
                    pcieSsdView.setRevision(nodeValue);
                    break;
                }
                case PcieSsdViewConstants.DEVICE_LIFE_STATUS: {
                    pcieSsdView.setDeviceLifeStatus(nodeValue);
                    break;
                }
                case PcieSsdViewConstants.REMAINING_RATED_WIRE_ENDURANCE: {
                    pcieSsdView.setRemainingRatedWriteEndurance(nodeValue);
                    break;
                }

                case PcieSsdViewConstants.FAILURE_PREDICTED: {
                    pcieSsdView.setFailurePredicted(nodeValue);
                    break;
                }

                default: {
                    logger.trace("New Attribute {} is added and not handled by code: ", nodeName);
                }
                }
            }

            pcieSsdViewList.add(pcieSsdView);

        }
        logger.trace("Exiting function: parse()");
        return pcieSsdViewList;
    }


    public Object findObjectInDocument(Document doc, String xPathLocation, QName qname) throws XPathExpressionException {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        xpath.setNamespaceContext(new PersonalNamespaceContext(getResourceURI()));
        XPathExpression expr = xpath.compile(xPathLocation);
        Object result = expr.evaluate(doc, qname);
        return result;
    }

}
