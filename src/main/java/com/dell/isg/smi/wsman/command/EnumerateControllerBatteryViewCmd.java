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
import com.dell.isg.smi.wsman.command.entity.ControllerBatteryView;
import com.dell.isg.smi.wsmanclient.util.WSManUtils;
import com.sun.ws.management.addressing.Addressing;

/**
 * @author Prashanth.Gowda
 *
 */

public class EnumerateControllerBatteryViewCmd extends WSManClientBaseCommand<List<ControllerBatteryView>> {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(EnumerateControllerBatteryViewCmd.class);


    public EnumerateControllerBatteryViewCmd() {
    }


    public EnumerateControllerBatteryViewCmd(String ipAddr, String userName, String passwd) {
        super(ipAddr, userName, passwd);
        logger.trace("Entering constructor: EnumerateControllerBatteryViewCmd (ipAddr{} userName{} passwd{})", ipAddr, userName, "####");
        session = this.getSession();
        session.setResourceUri(getResourceURI());
        logger.trace("Exiting constructor: EnumerateControllerBatteryViewCmd()");
    }


    @Override
    public List<ControllerBatteryView> execute() throws Exception {
        logger.trace("Entering function: execute()");
        Addressing addressing = this.sendRequestEnumeration();
        NodeList nodeset = (NodeList) session.findObjectInDocument(addressing.getBody(), "//*[local-name()='DCIM_ControllerBatteryView']", XPathConstants.NODESET);
        List<ControllerBatteryView> controllerBatteryViewList = parse(nodeset);
        logger.trace("Exiting function: execute()");
        return controllerBatteryViewList;
    }


    public String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_ControllerBatteryView);
        return sb.toString();
    }


    @Override
    public List<ControllerBatteryView> parse(String xml) throws Exception {
        Document document = WSManUtils.toDocument(xml);
        NodeList nodeset = (NodeList) findObjectInDocument(document, "//*[local-name()='DCIM_ControllerBatteryView']", XPathConstants.NODESET);
        List<ControllerBatteryView> controllerBatteryViewList = parse(nodeset);
        return controllerBatteryViewList;
    }


    private List<ControllerBatteryView> parse(NodeList nodeset) throws Exception {
        logger.trace("Entering function: parse()");
        List<ControllerBatteryView> controllerBatteryViewList = new ArrayList<ControllerBatteryView>();
        for (int i = 0; i < nodeset.getLength(); i++) {
            Node node = nodeset.item(i);
            ControllerBatteryView controllerBatteryView = new ControllerBatteryView();
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
                case ControllerBatteryViewConstants.DEVICE_DESCRIPTION: {
                    controllerBatteryView.setDeviceDescription(nodeValue);
                    break;
                }

                case ControllerBatteryViewConstants.FQDD: {
                    controllerBatteryView.setFqdd(nodeValue);
                    break;
                }

                case ControllerBatteryViewConstants.INSTANCE_ID: {
                    controllerBatteryView.setInstanceIdd(nodeValue);
                    break;
                }
                case ControllerBatteryViewConstants.PRIMARY_STATUS: {
                    controllerBatteryView.setPrimaryStatus(nodeValue);
                    break;
                }
                case ControllerBatteryViewConstants.RAID_STATE: {
                    controllerBatteryView.setRaidState(nodeValue);
                    break;
                }

                default: {
                    logger.trace("New Attribute {} is added and not handled by code: ", nodeName);
                }
                }
            }

            controllerBatteryViewList.add(controllerBatteryView);

        }
        logger.trace("Exiting function: parse()");
        return controllerBatteryViewList;
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
