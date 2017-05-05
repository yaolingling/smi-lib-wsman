/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
/**
 * 
 */
package com.dell.isg.smi.wsman.command.raidcmd;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.xml.soap.SOAPBody;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import com.sun.ws.management.addressing.Addressing;

import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.slf4j.LoggerFactory;

import com.dell.isg.smi.commons.elm.exception.RuntimeCoreException;
import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.entity.KeyValuePair;
import com.dell.isg.smi.wsman.utilities.ExceptionUtilities;

/**
 * @author Umer_Shabbir
 * @author Rahman_Muhammad
 */
public abstract class AbstractRaid extends WSManBaseCommand {
    public final static Logger log = LoggerFactory.getLogger(AbstractRaid.class);
    public final String SYSTEM_CREATION_CLASSNAME = "SystemCreationClassName";
    public final String CREATION_CLASSNAME = "CreationClassName";
    public final String SYSTEM_NAME = "SystemName";
    public final String NAME = "Name";
    private static final Logger logger = LoggerFactory.getLogger(AbstractRaid.class);
    WSManageSession session = null;

    private List<KeyValuePair> keyValues = new ArrayList<KeyValuePair>();


    protected AbstractRaid(String ipAddr, String userName, String passwd) {
        super(ipAddr, userName, passwd, false);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: AbstractRaid(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        session = super.getSession();
        logger.trace("Exiting constructor: AbstractRaid()");
    }


    public static String getBaseURI() {
        StringBuilder sbBaseUri = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sbBaseUri.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE);
        return sbBaseUri.toString();
    }


    protected void addSelectors() {
        session.setResourceUri(getResourceURI());
        session.addSelector(this.SYSTEM_CREATION_CLASSNAME, WSManClassEnum.DCIM_ComputerSystem.toString());
        session.addSelector(this.CREATION_CLASSNAME, WSManClassEnum.DCIM_RAIDService.toString());
        session.addSelector(this.SYSTEM_NAME, "DCIM:ComputerSystem");
        session.addSelector(this.NAME, "DCIM:RAIDService");
    }


    protected String getResourceURI() {
        String resourceURI = getBaseURI() + WSCommandRNDConstant.RAID_SERVICE_URI;
        return resourceURI;
    }


    protected List<KeyValuePair> parseResponseByXPath(Addressing response, String jobName) throws Exception {

        XPath xpath = XPathFactory.newInstance().newXPath();
        SOAPBody soapBody = response.getBody();
        String jobId = null;
        List<KeyValuePair> resp = new ArrayList<KeyValuePair>();
        KeyValuePair keyValue = new KeyValuePair();
        String retValue;
        try {
            retValue = xpath.evaluate("*[local-name()='" + jobName + "_OUTPUT']/*[local-name()='ReturnValue']", soapBody);
            if (retValue.equalsIgnoreCase("0") || retValue.equalsIgnoreCase("4096")) {
                jobId = getJobID(soapBody, xpath, jobName);
                keyValue.setKey("InstanceID");
                if (jobId != null)
                    keyValue.setValue(jobId);
                resp.add(keyValue);
            } else {
                Document document = response.getBody().extractContentAsDocument();
                parseResponse(document);
            }
        } catch (XPathExpressionException e) {
            ExceptionUtilities.handleRuntimeCore(267027, e, "Error in xpath Expression");
        }
        return resp;
    }


    private String getJobID(SOAPBody soapBody, XPath xpath, String jobName) throws Exception {

        Object result = null;
        if (jobName.equalsIgnoreCase(RaidWSManMethodEnum.CreateVirtualDisk.getDisplayString())) {
            result = xpath.evaluate("*[local-name()='" + jobName + "_OUTPUT']/*[local-name()='NewVirtualDisk']", soapBody, XPathConstants.NODE);
        } else if (jobName.equalsIgnoreCase(RaidWSManMethodEnum.CreateTargetedConfigJob.getDisplayString())) {
            result = xpath.evaluate("*[local-name()='" + jobName + "_OUTPUT']/*[local-name()='Job']", soapBody, XPathConstants.NODE);
        } else if (jobName.equalsIgnoreCase(RaidWSManMethodEnum.CreateRebootJob.getDisplayString())) {
            result = xpath.evaluate("*[local-name()='" + jobName + "_OUTPUT']/*[local-name()='RebootJobID']", soapBody, XPathConstants.NODE);
        }

        Node referenceNode = null;
        Node node = (Node) result;
        if (node != null && node.hasChildNodes()) {
            NodeList nodeList = node.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node childNode = nodeList.item(i);
                if (childNode.getLocalName().equalsIgnoreCase("EndpointReference")) {
                    if (childNode.hasChildNodes()) {
                        NodeList nodeList2 = childNode.getChildNodes();
                        for (int j = 0; j < nodeList2.getLength(); j++) {
                            Node childNode2 = nodeList2.item(j);
                            if (childNode2.getLocalName().equalsIgnoreCase("ReferenceParameters")) {
                                referenceNode = childNode2;
                                break;
                            }
                        }
                    } else {
                        referenceNode = null;
                        break;
                    }
                } else if (childNode.getLocalName().equalsIgnoreCase("ReferenceParameters")) {
                    referenceNode = childNode;
                    break;
                }
            }

            if (referenceNode != null && referenceNode.hasChildNodes()) {
                NodeList nodeList2 = referenceNode.getChildNodes();
                for (int i = 0; i < nodeList2.getLength(); i++) {
                    Node childNode = nodeList2.item(i);
                    if (childNode.getLocalName().equalsIgnoreCase("SelectorSet")) {
                        if (childNode.hasChildNodes()) {
                            NodeList nodeList3 = childNode.getChildNodes();
                            for (int j = 0; j < nodeList3.getLength(); j++) {
                                Node selectorNode = nodeList3.item(j);
                                if (selectorNode.getLocalName().equalsIgnoreCase("Selector")) {
                                    NamedNodeMap attributes = selectorNode.getAttributes();
                                    if (attributes != null && attributes.getLength() > 0) {
                                        Node attrNode = attributes.getNamedItem("Name");
                                        if (attrNode != null && attrNode.getNodeValue() != null) {
                                            if (attrNode.getNodeValue().equalsIgnoreCase("InstanceID")) {
                                                if (selectorNode.hasChildNodes()) {
                                                    NodeList nodeList4 = selectorNode.getChildNodes();
                                                    for (int k = 0; k < nodeList4.getLength(); k++) {
                                                        Node finalNode = nodeList4.item(k);
                                                        if (Element.TEXT_NODE == finalNode.getNodeType()) {
                                                            String val = finalNode.getNodeValue();
                                                            return val;
                                                        }
                                                    }
                                                    return null;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            return null;
                        }
                    }
                }
            } else {
                return null;
            }
        }
        return null;
    }


    protected List<KeyValuePair> parseResponse(Document document) {

        List<KeyValuePair> resp = new ArrayList<KeyValuePair>();
        Element element = document.getDocumentElement();
        NodeList nodeList = element.getElementsByTagName("*");
        for (int i = 0; i < nodeList.getLength(); i++) {
            KeyValuePair keyValue = new KeyValuePair();
            Element elem = (Element) nodeList.item(i);
            keyValue.setKey(elem.getLocalName());
            if (elem.getFirstChild().getNodeValue() != null)
                keyValue.setValue(elem.getFirstChild().getNodeValue());
            resp.add(keyValue);
        }

        checkRetVal(resp);
        return resp;
    }


    public void setKeyValues(List<KeyValuePair> keyValues) {
        this.keyValues = keyValues;
    }


    public List<KeyValuePair> getKeyValues() {
        return keyValues;
    }


    private void checkRetVal(List<KeyValuePair> resp) {
        Hashtable<String, String> hVal = new Hashtable<String, String>();
        for (KeyValuePair kval : resp) {
            if (kval.getValue() != null)
                hVal.put(kval.getKey(), kval.getValue());
        }
        for (KeyValuePair kval : resp) {
            String key = kval.getKey();
            String val = kval.getValue();
            if (key.equalsIgnoreCase("ReturnValue") && !val.equalsIgnoreCase("0")) {
                if (hVal.get("MessageID") != null || hVal.get("Message") != null) {
                    log.error("WSMan Command failed with message: [" + hVal.get("MessageID") + ": " + hVal.get("Message") + "]");
                    RuntimeCoreException r = new RuntimeCoreException(hVal.get("MessageID") + ":" + hVal.get("Message"));
                    r.setErrorID(267027);
                    // r.setLCErrorCode(this.getLCErrorCode());
                    // r.setLCErrorStr(this.getLCErrorStr());
                    throw r;
                }
            }
        }
    }
}
