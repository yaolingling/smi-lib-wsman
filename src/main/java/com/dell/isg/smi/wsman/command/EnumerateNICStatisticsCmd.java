/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import java.util.ArrayList;
import java.util.List;

import javax.xml.soap.SOAPBody;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManClientBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.command.entity.NICStatistics;
import com.dell.isg.smi.wsmanclient.util.WSManUtils;
import com.sun.ws.management.addressing.Addressing;

public class EnumerateNICStatisticsCmd extends WSManClientBaseCommand<List<NICStatistics>> {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(EnumerateNICStatisticsCmd.class);


    public EnumerateNICStatisticsCmd() {
    }


    public EnumerateNICStatisticsCmd(String ipAddr, String userName, String passwd) {
        super(ipAddr, userName, passwd);
        logger.trace("Entering constructor: EnumerateNICStatisticsCmd (ipAddr{} userName{} passwd{})", ipAddr, userName, "####");
        session = this.getSession();
        session.setResourceUri(getResourceURI());
        logger.trace("Exiting constructor: EnumerateNICStatisticsCmd()");

    }


    @Override
    public List<NICStatistics> execute() throws Exception {
        logger.trace("Entering method execute() ");
        Addressing addressing = this.sendRequestEnumeration();
        SOAPBody tempDoc = addressing.getBody();
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();
        Object objNode = xpath.evaluate("//*[local-name()='Items']", tempDoc, XPathConstants.NODE);
        Node items = null;
        if (objNode != null) {
            items = (Node) objNode;
        }
        List<NICStatistics> nicStatisticsList = parse(items);
        logger.trace("Exiting method execute() ");
        return nicStatisticsList;
    }


    private List<NICStatistics> parse(Node items) throws Exception {
        logger.trace("Entering function: execute()");
        List<NICStatistics> nicStatisticsList = new ArrayList<NICStatistics>();
        if (items == null || !items.hasChildNodes()) {
            return nicStatisticsList;
        }
        NodeList nodeList = items.getChildNodes();
        for (int j = 0; j < nodeList.getLength(); j++) {
            Node tmpNode = nodeList.item(j);
            NodeList nicList = tmpNode.getChildNodes();
            if (nicList == null || tmpNode.getNodeType() != Element.ELEMENT_NODE) {
                continue;
            }
            NICStatistics nicStatistics = new NICStatistics();
            for (int k = 0; k < nicList.getLength(); k++) {
                Node currentNic = nicList.item(k);
                String value = currentNic.getTextContent();
                if (StringUtils.equals(currentNic.getLocalName(), NicStatisticsConstants.FQDD)) {
                    nicStatistics.setFqdd(value.trim());
                } else if (StringUtils.equals(currentNic.getLocalName(), NicStatisticsConstants.OS_DRIVER_STATE)) {
                    nicStatistics.setOsDriverState(value.trim());
                } else if (StringUtils.equals(currentNic.getLocalName(), NicStatisticsConstants.LINK_STATUS)) {
                    nicStatistics.setLinkStatus(value.trim());
                }
            }
            nicStatisticsList.add(nicStatistics);
        }
        logger.trace("Exiting method execute() ");
        return nicStatisticsList;
    }


    public String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_NICStatistics);
        return sb.toString();
    }


    @Override
    public List<NICStatistics> parse(String xml) throws Exception {
        Document document = WSManUtils.toDocument(xml);
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();
        Object objNode = xpath.evaluate("//*[local-name()='Items']", document, XPathConstants.NODE);
        Node items = null;
        if (objNode != null) {
            items = (Node) objNode;
        }
        List<NICStatistics> nicStatisticsList = parse(items);
        return nicStatisticsList;
    }

}
