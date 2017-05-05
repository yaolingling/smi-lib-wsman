/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
/**
 * 
 */
package com.dell.isg.smi.wsman.command.idraccmd;

import java.util.ArrayList;
import java.util.List;

import javax.xml.soap.SOAPBody;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.entity.KeyValuePair;
import com.dell.isg.smi.wsman.utilities.ExceptionUtilities;
import com.sun.ws.management.addressing.Addressing;

/**
 * @author Umer_Shabbir
 * @author Muhammad_R
 */

public class UpdateIdracAttributeCmd extends WSManBaseCommand {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(UpdateIdracAttributeCmd.class);


    public UpdateIdracAttributeCmd(String ipAddr, String userName, String passwd) {

        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: UpdateIdracAttributeCmd(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        session = super.getSession();
        this.session.setResourceUri(getResourceURI());
        this.addSelectors(getResourceURI());
        this.session.setInvokeCommand("ApplyAttributes");
        logger.trace("Exiting constructor: UpdateIdracAttributeCmd()");

    }


    private String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);

        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append("DCIM_iDRACCardService");

        return sb.toString();
    }


    public void addTargetValue(String target) {
        this.session.addUserParam(WSManMethodParamEnum.TARGET.toString(), target);
    }


    public void addAttributeKeyValue(String attributeName, String attributeValue) {
        this.session.addUserParam(WSManMethodParamEnum.ATTRIBUTE_NAME.toString(), attributeName);
        this.session.addUserParam(WSManMethodParamEnum.ATTRIBUTE_VALUE.toString(), attributeValue);

    }


    /**
     * (non-Javadoc)
     * 
     * @see com.dell.isg.smi.wsman.WSManBaseCommand#execute()
     */

    @Override
    public Object execute() throws Exception {
        logger.trace("Entering function: execute()");
        String job_id = "";
        String status = "";
        session.setTimeoutInSeconds(360);
        Addressing addressing = session.sendInvokeRequest();
        status = this.parseResponseByXPath(addressing, "ApplyAttributes", "ReturnValue");
        List<KeyValuePair> keyValuePair = new ArrayList<KeyValuePair>();
        KeyValuePair keyVal1 = new KeyValuePair();
        keyVal1.setKey("returnStatus");
        keyVal1.setValue(status);
        keyValuePair.add(keyVal1);

        if (status.equals("4096") || status.equals("0")) {
            job_id = this.parseResponseByXPath(addressing, "ApplyAttributes", "InstanceID");
            KeyValuePair keyVal2 = new KeyValuePair();
            keyVal2.setKey("jobId");
            keyVal2.setValue(job_id);
            keyValuePair.add(keyVal2);
        }

        logger.trace("Exiting function: execute()");
        return keyValuePair;
    }


    protected String parseResponseByXPath(Addressing response, String jobName, String elementName) throws Exception {
        String itemResponse = "";
        XPath xpath = XPathFactory.newInstance().newXPath();
        SOAPBody soapBody = response.getBody();
        try {
            if (elementName.equalsIgnoreCase("InstanceID")) {
                itemResponse = this.getJobID(soapBody, xpath, jobName, elementName);
            } else if (elementName.equalsIgnoreCase("ReturnValue")) {
                itemResponse = this.getReturnValue(soapBody, xpath, jobName, elementName);
            }
        } catch (XPathExpressionException e) {
            // FIX ME for ICEE
            ExceptionUtilities.handleRuntimeCore(267027, "Error in xpath Expression");
        }

        return itemResponse;
    }


    private String getReturnValue(SOAPBody soapBody, XPath xpath, String jobName, String elementName) throws Exception {

        Object result = null;
        result = xpath.evaluate("*[local-name()='" + jobName + "_OUTPUT']", soapBody, XPathConstants.NODE);

        Node node = (Node) result;
        if (node.hasChildNodes()) {
            NodeList nodeList = node.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node childNode = nodeList.item(i);
                if (childNode.getLocalName().equalsIgnoreCase(elementName)) {
                    if (childNode.hasChildNodes()) {
                        NodeList nodeList1 = childNode.getChildNodes();
                        for (int k = 0; k < nodeList1.getLength(); k++) {
                            Node finalNode = nodeList1.item(k);
                            if (Element.TEXT_NODE == finalNode.getNodeType()) {
                                String val = finalNode.getNodeValue();
                                return val;
                            }
                        }

                        return null;
                    } else {
                        break;
                    }
                }
            }
        }

        return null;
    }


    private String getJobID(SOAPBody soapBody, XPath xpath, String jobName, String elementName) throws Exception {

        Object result = null;
        result = xpath.evaluate("*[local-name()='" + jobName + "_OUTPUT']/*[local-name()='Job']", soapBody, XPathConstants.NODE);

        Node referenceNode = null;
        Node node = (Node) result;
        if (node.hasChildNodes()) {
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
                                            if (attrNode.getNodeValue().equalsIgnoreCase(elementName)) {
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
}
