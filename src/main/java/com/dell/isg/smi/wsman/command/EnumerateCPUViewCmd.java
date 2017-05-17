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
import com.dell.isg.smi.wsman.command.entity.CPUView;
import com.dell.isg.smi.wsmanclient.util.WSManUtils;
import com.sun.ws.management.addressing.Addressing;

public class EnumerateCPUViewCmd extends WSManClientBaseCommand<List<CPUView>> {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(EnumerateCPUViewCmd.class);


    public EnumerateCPUViewCmd() {
    }


    public EnumerateCPUViewCmd(String ipAddr, String userName, String passwd) {

        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: EnumerateCPUViewCmd(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        session = this.getSession();
        session.setResourceUri(getResourceURI());

        logger.trace("Exiting constructor: EnumerateCPUViewCmd()");

    }


    @Override
    public List<CPUView> execute() throws Exception {

        logger.trace("Entering function: execute()");

        Addressing addressing = this.sendRequestEnumeration();

        NodeList nodeset = (NodeList) session.findObjectInDocument(addressing.getBody(), "//*[local-name()='DCIM_CPUView']", XPathConstants.NODESET);
        List<CPUView> cpuViewList = parse(nodeset);
        logger.trace("Exiting function: execute()");
        return cpuViewList;
    }


    public String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_CPUView);

        return sb.toString();
    }


    @Override
    public List<CPUView> parse(String xml) throws Exception {
        Document document = WSManUtils.toDocument(xml);
        NodeList nodeset = (NodeList) findObjectInDocument(document, "//*[local-name()='DCIM_CPUView']", XPathConstants.NODESET);
        return parse(nodeset);
    }


    private List<CPUView> parse(NodeList nodeset) throws Exception {
        List<CPUView> cpuViewList = new ArrayList<CPUView>();
        CPUView cpuView = null;

        for (int i = 0; i < nodeset.getLength(); i++) {
            Node node = nodeset.item(i);

            cpuView = new CPUView();

            NodeList nodeList = node.getChildNodes();

            for (int j = 0; j < nodeList.getLength(); j++) {
                Node itemNode = nodeList.item(j);

                String nodeValue = itemNode.getTextContent();

                if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "CPUFamily")) {
                    cpuView.setCpuFamily(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "CPUStatus")) {
                    cpuView.setCpuStatus(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Cache1Associativity")) {
                    cpuView.setCache1Associativity(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Cache1ErrorMethodology")) {
                    cpuView.setCache1ErrorMethodology(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Cache1Level")) {
                    cpuView.setCache1Level(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Cache1PrimaryStatus")) {
                    cpuView.setCache1PrimaryStatus(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Cache1SRAMType")) {
                    cpuView.setCache1SramType(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Cache1Size")) {
                    cpuView.setCache1Size(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Cache1Type")) {
                    cpuView.setCache1Type(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Cache1WritePolicy")) {
                    cpuView.setCache1WritePolicy(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Cache2Associativity")) {
                    cpuView.setCache2Associativity(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Cache2ErrorMethodology")) {
                    cpuView.setCache2ErrorMethodology(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Cache2Level")) {
                    cpuView.setCache2Level(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Cache2PrimaryStatus")) {
                    cpuView.setCache2PrimaryStatus(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Cache2SRAMType")) {
                    cpuView.setCache2SramType(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Cache2Size")) {
                    cpuView.setCache2Size(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Cache2Type")) {
                    cpuView.setCache2Type(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Cache2WritePolicy")) {
                    cpuView.setCache2WritePolicy(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Cache3Associativity")) {
                    cpuView.setCache3Associativity(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Cache3ErrorMethodology")) {
                    cpuView.setCache3ErrorMethodology(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Cache3Level")) {
                    cpuView.setCache3Level(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Cache3PrimaryStatus")) {
                    cpuView.setCache3PrimaryStatus(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Cache3SRAMType")) {
                    cpuView.setCache3SramType(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Cache3Size")) {
                    cpuView.setCache3Size(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Cache3Type")) {
                    cpuView.setCache3Type(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Cache3WritePolicy")) {
                    cpuView.setCache3WritePolicy(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Characteristics")) {
                    cpuView.setCharacteristics(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "CurrentClockSpeed")) {
                    cpuView.setCurrentClockSpeed(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "ExternalBusClockSpeed")) {
                    cpuView.setExternalBusClockSpeed(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "FQDD")) {
                    cpuView.setfQDD(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "InstanceID")) {
                    cpuView.setInstanceID(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "LastSystemInventoryTime")) {
                    cpuView.setLastSystemInventoryTime(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "LastUpdateTime")) {
                    cpuView.setLastUpdateTime(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Manufacturer")) {
                    cpuView.setManufacturer(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "MaxClockSpeed")) {
                    cpuView.setMaxClockSpeed(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Model")) {
                    cpuView.setModel(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "NumberOfEnabledCores")) {
                    cpuView.setNumberOfEnabledCores(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "NumberOfEnabledThreads")) {
                    cpuView.setNumberOfEnabledThreads(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "NumberOfProcessorCores")) {
                    cpuView.setNumberOfProcessorCores(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "PrimaryStatus")) {
                    cpuView.setPrimaryStatus(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Voltage")) {
                    cpuView.setVoltage(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Cache1Location")) {
                    cpuView.setCache1Location(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Cache2Location")) {
                    cpuView.setCache2Location(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "Cache3Location")) {
                    cpuView.setCache3Location(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "DeviceDescription")) {
                    cpuView.setDeviceDescription(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "ExecuteDisabledCapable")) {
                    cpuView.setExecuteDisabledCapable(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "ExecuteDisabledEnabled")) {
                    cpuView.setExecuteDisabledEnabled(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "HyperThreadingCapable")) {
                    cpuView.setHyperThreadingCapable(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "HyperThreadingEnabled")) {
                    cpuView.setHyperThreadingEnabled(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "VirtualizationTechnologyCapable")) {
                    cpuView.setVirtualizationTechnologyCapable(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "VirtualizationTechnologyEnabled")) {
                    cpuView.setVirtualizationTechnologyEnabled(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "TurboModeCapable")) {
                    cpuView.setTurboModeCapable(nodeValue);
                } else if (StringUtils.equalsIgnoreCase(itemNode.getLocalName(), "TurboModeEnabled")) {
                    cpuView.setTurboModeEnabled(nodeValue);
                }
            }

            cpuViewList.add(cpuView);
        }

        logger.trace("Exiting function: execute()");
        return cpuViewList;
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
