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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManClientBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.command.entity.EnclosureView;
import com.dell.isg.smi.wsmanclient.util.WSManUtils;
import com.sun.ws.management.addressing.Addressing;

public class EnumerateEnclosureViewcmd extends WSManClientBaseCommand<List<EnclosureView>> {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(EnumerateEnclosureViewcmd.class);


    public EnumerateEnclosureViewcmd() {
    }


    public EnumerateEnclosureViewcmd(String ipAddr, String userName, String passwd) {

        super(ipAddr, userName, passwd);

        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: EnumerateEnclosureViewcmd(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        session = this.getSession();
        session.setResourceUri(getResourceURI());

        logger.trace("Exiting constructor: EnumerateEnclosureViewcmd()");
    }


    // <n1:DCIM_EnclosureView>
    // <n1:AssetTag/>
    // <n1:Connector>0</n1:Connector>
    // <n1:EMMCount>0</n1:EMMCount>
    // <n1:FQDD>Enclosure.Internal.0-1:RAID.Integrated.1-1</n1:FQDD>
    // <n1:FanCount>0</n1:FanCount>
    // <n1:InstanceID>Enclosure.Internal.0-1:RAID.Integrated.1-1</n1:InstanceID>
    // <n1:LastSystemInventoryTime>20110722063110.000000+000</n1:LastSystemInventoryTime>
    // <n1:LastUpdateTime>20110722062832.000000+000</n1:LastUpdateTime>
    // <n1:PSUCount>0</n1:PSUCount>
    // <n1:PrimaryStatus>1</n1:PrimaryStatus>
    // <n1:ProductName>BACKPLANE 0:1</n1:ProductName>
    // <n1:RollupStatus>1</n1:RollupStatus>
    // <n1:ServiceTag/>
    // <n1:SlotCount>0</n1:SlotCount>
    // <n1:TempProbeCount>0</n1:TempProbeCount>
    // <n1:Version>0.12</n1:Version>
    // <n1:WiredOrder>1</n1:WiredOrder>
    // </n1:DCIM_EnclosureView>

    @Override
    public List<EnclosureView> execute() throws Exception {
        logger.trace("Entering function: execute()");
        Addressing addressing = this.sendRequestEnumeration();
        NodeList nodeset = (NodeList) session.findObjectInDocument(addressing.getBody(), "//*[local-name()='DCIM_EnclosureView']", XPathConstants.NODESET);
        List<EnclosureView> enclosureViewList = parse(nodeset);
        logger.trace("Exiting function: execute()");
        return enclosureViewList;
    }


    public String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_EnclosureView);

        return sb.toString();
    }


    @Override
    public List<EnclosureView> parse(String xml) throws Exception {
        Document document = WSManUtils.toDocument(xml);
        NodeList nodeset = (NodeList) findObjectInDocument(document, "//*[local-name()='DCIM_EnclosureView']", XPathConstants.NODESET);
        List<EnclosureView> enclosureViewList = parse(nodeset);
        return enclosureViewList;
    }


    public List<EnclosureView> parse(NodeList nodeset) throws Exception {
        List<EnclosureView> enclosureViewList = new ArrayList<EnclosureView>();

        for (int i = 0; i < nodeset.getLength(); i++) {
            Node node = nodeset.item(i);

            NodeList nodeList = node.getChildNodes();

            EnclosureView enclosureView = new EnclosureView();

            for (int j = 0; j < nodeList.getLength(); j++) {
                Node itemNode = nodeList.item(j);

                String nodeValue = itemNode.getTextContent();

                if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "AssetTag")) {
                    enclosureView.setAssetTag(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Connector")) {
                    enclosureView.setConnector(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "EMMCount")) {
                    enclosureView.seteMMCount(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "FQDD")) {
                    enclosureView.setfQDD(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "FanCount")) {
                    enclosureView.setFanCount(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "InstanceID")) {
                    enclosureView.setInstanceID(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "LastSystemInventoryTime")) {
                    enclosureView.setLastSystemInventoryTime(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "LastUpdateTime")) {
                    enclosureView.setLastUpdateTime(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "PSUCount")) {
                    enclosureView.setpSUCount(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "PrimaryStatus")) {
                    enclosureView.setPrimaryStatus(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "ProductName")) {
                    enclosureView.setProductName(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "RollupStatus")) {
                    enclosureView.setRollupStatus(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "ServiceTag")) {
                    enclosureView.setServiceTag(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "SlotCount")) {
                    enclosureView.setSlotCount(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "TempProbeCount")) {
                    enclosureView.setTempProbeCount(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Version")) {
                    enclosureView.setVersion(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "WiredOrder")) {
                    enclosureView.setWiredOrder(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "DeviceDescription")) {
                    enclosureView.setDeviceDescription(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "State")) {
                    enclosureView.setState(nodeValue);
                }
            }
            enclosureViewList.add(enclosureView);
        }

        return enclosureViewList;
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
