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

import com.dell.isg.smi.wsmanclient.util.WSManUtils;
import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManClientBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.command.entity.VFlashView;
import com.sun.ws.management.addressing.Addressing;

public class EnumerateVFlashViewCmd extends WSManClientBaseCommand<List<VFlashView>> {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(EnumerateVFlashViewCmd.class);


    public EnumerateVFlashViewCmd() {
    }


    public EnumerateVFlashViewCmd(String ipAddr, String userName, String passwd) {
        super(ipAddr, userName, passwd);
        logger.trace("Entering constructor: EnumerateVFlashViewCmd(ipAddr{} userName{} passwd{})", ipAddr, userName, "####");
        session = this.getSession();
        session.setResourceUri(getResourceURI());
        logger.trace("Exiting constructor: EnumerateVFlashViewCmd()");
    }


    @Override
    public List<VFlashView> execute() throws Exception {
        logger.trace("Entering function: execute()");
        Addressing addressing = this.sendRequestEnumeration();
        NodeList nodeset = (NodeList) session.findObjectInDocument(addressing.getBody(), "//*[local-name()='DCIM_VFlashView']", XPathConstants.NODESET);
        List<VFlashView> VFlashViewList = parse(nodeset);
        logger.trace("Exiting function: execute()");
        return VFlashViewList;
    }


    public String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_VFlashView);

        return sb.toString();
    }


    @Override
    public List<VFlashView> parse(String xml) throws Exception {
        Document document = WSManUtils.toDocument(xml);
        NodeList nodeset = (NodeList) findObjectInDocument(document, "//*[local-name()='DCIM_VFlashView']", XPathConstants.NODESET);
        List<VFlashView> VFlashViewList = parse(nodeset);
        return VFlashViewList;
    }


    private List<VFlashView> parse(NodeList nodeset) throws Exception {
        List<VFlashView> VFlashViewList = new ArrayList<VFlashView>();
        VFlashView VFlashView = null;
        for (int i = 0; i < nodeset.getLength(); i++) {
            Node node = nodeset.item(i);
            VFlashView = new VFlashView();
            VFlashViewList.add(VFlashView);
            NodeList nodeList = node.getChildNodes();
            for (int j = 0; j < nodeList.getLength(); j++) {
                Node itemNode = nodeList.item(j);
                String nodeValue = itemNode.getTextContent();
                String nodeName = itemNode.getLocalName();
                if (StringUtils.equalsIgnoreCase(nodeName, "DeviceDescription")) {
                    VFlashView.setDeviceDescription(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(nodeName, "FQDD")) {
                    VFlashView.setFQDD(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(nodeName, "InstanceID")) {
                    VFlashView.setInstanceID(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(nodeName, "ComponentName")) {
                    VFlashView.setComponentName(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(nodeName, "AvailableSize")) {
                    VFlashView.setAvailableSize(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(nodeName, "Capacity")) {
                    VFlashView.setCapacity(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(nodeName, "HealthStatus")) {
                    VFlashView.setHealthStatus(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(nodeName, "InitializedState")) {
                    VFlashView.setInitializedState(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(nodeName, "Licensed")) {
                    VFlashView.setLicensed(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(nodeName, "VFlashEnabledState")) {
                    VFlashView.setvFlashEnabledState(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(nodeName, "WriteProtected")) {
                    VFlashView.setWriteProtected(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(nodeName, "LastSystemInventoryTime")) {
                    VFlashView.setLastSystemInventoryTime(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(nodeName, "LastUpdateTime")) {
                    VFlashView.setLastUpdateTime(nodeValue);
                }
            }
        }
        logger.trace("Exiting function: execute()");
        return VFlashViewList;
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
