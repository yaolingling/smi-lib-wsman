/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
/**
 * 
 */
package com.dell.isg.smi.wsman.command;

/**
 * @author Muhammad_R
 *
 */

import java.util.ArrayList;
import java.util.List;

import javax.xml.soap.SOAPBody;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.commons.elm.exception.RuntimeCoreException;
import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.command.entity.IDRACCardStringView;
import com.dell.isg.smi.wsman.entity.KeyValuePair;
import com.dell.isg.smi.wsman.utilities.ExceptionUtilities;
import com.sun.ws.management.addressing.Addressing;

public class SetEventsCmd extends WSManBaseCommand {

    private String trapDestinationIP;
    private int supported;
    private static final int DESTINATION_LIST_SIZE = 8;

    private boolean isDomainDestination = false;


    /**
     * @return the isDomainDestination
     */
    public boolean isDomainDestination() {
        return isDomainDestination;
    }


    /**
     * @param isDomainDestination the isDomainDestination to set
     */
    public void setDomainDestination(boolean isDomainDestination) {
        this.isDomainDestination = isDomainDestination;
    }


    /**
     * @return the trapDestinationIP
     */
    public String getTrapDestinationIP() {
        return trapDestinationIP;
    }

    public boolean duplicate = false;


    /**
     * @param trapDestinationIP the trapDestinationIP to set
     */
    public void setTrapDestinationIP(String trapDestinationIP) {
        this.trapDestinationIP = trapDestinationIP;
    }

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(SetEventsCmd.class);


    public SetEventsCmd(String ipAddr, String userName, String passwd) {

        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: SetEventsCmd(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        session = super.getSession();
        this.session.setResourceUri(getResourceURI());
        this.addSelectors(getResourceURI());
        this.session.setInvokeCommand(WSCommandRNDConstant.APPLY_ATTRIBUTES);
        logger.trace("Exiting constructor: SetEventsCmd()");

    }


    private String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSCommandRNDConstant.DCIM_iDRAC_CARD_SERVICE);
        return sb.toString();
    }


    public void addTargetValue(String target) {
        this.session.addUserParam(WSManMethodParamEnum.TARGET.toString(), target);
    }


    public void addAttributeKeyValue(String attributeName, String attributeValue) {
        this.session.addUserParam(WSManMethodParamEnum.ATTRIBUTE_NAME.toString(), attributeName);
        this.session.addUserParam(WSManMethodParamEnum.ATTRIBUTE_VALUE.toString(), attributeValue);

    }


    @Override
    public Object execute() throws Exception {
        logger.trace("Entering function: execute() ");

        List<IDRACCardStringView> dests = this.getAvailableDestination(this.getSession().getIpAddress(), this.getSession().getUser(), this.getSession().getPassword());
        this.addTargetValue(dests.get(0).getfQDD());
        for (IDRACCardStringView attribute : dests) {
            this.addAttributeKeyValue(attribute.getGroupID() + "#" + attribute.getAttributeName(), attribute.getCurrentValue());
        }

        String job_id = "";
        String status = "";
        Addressing addressing = session.sendInvokeRequest();
        status = this.parseResponseByXPath(addressing, "ApplyAttributes", "ReturnValue");
        List<KeyValuePair> keyValuePair = new ArrayList<KeyValuePair>();
        KeyValuePair keyVal1 = new KeyValuePair();
        keyVal1.setKey("returnStatus");
        keyVal1.setValue(status);
        keyValuePair.add(keyVal1);

        if (status.equals(WSCommandRNDConstant.SUCCESSFULL_CONFIG_JOB_RETURN) || status.equals(WSCommandRNDConstant.COMPLETED_WITH_NO_ERROR)) {
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


    private List<IDRACCardStringView> getAvailableDestination(String ipAddr, String userName, String password) throws Exception {

        String DEFAULT_VALUE = "0.0.0.0";
        String FQDD = "iDRAC.Embedded.1";
        String groupID = "SNMPTrapIPv4.1";
        String attributeName = "DestIPv4Addr";

        if (isDomainDestination()) {
            groupID = "SNMPAlert.1";
            attributeName = "Destination";
        }

        String hash = "#";
        duplicate = false;
        supported = 0;
        boolean alreadyAdded = false;
        List<IDRACCardStringView> attributes = new ArrayList<IDRACCardStringView>();
        IDRACCardStringView destination = null;
        EnumerateIDRACCardStrCmd stringCMD = new EnumerateIDRACCardStrCmd(ipAddr, userName, password);
        List<IDRACCardStringView> list = stringCMD.execute();

        if (list != null && list.size() > 0) {

            for (IDRACCardStringView item : list) {
                if (this.isDestinationFree(FQDD, groupID, attributeName, DEFAULT_VALUE, item)) {
                    if (!alreadyAdded) {
                        item.setCurrentValue(this.getTrapDestinationIP());
                        attributes.add(item);
                        alreadyAdded = true;
                    }
                    if (supported == 4)
                        break;
                }
            }
        }

        if (supported < 4) {
            RuntimeCoreException exception = new RuntimeCoreException();
            exception.setErrorID(238024);
            throw exception;
        }

        if (duplicate) {
            RuntimeCoreException exception = new RuntimeCoreException();
            exception.setErrorID(238023);
            throw exception;
        }

        if (attributes.size() <= 0) {
            RuntimeCoreException exception = new RuntimeCoreException();
            exception.setErrorID(238022);
            throw exception;
        }

        attributes.add(this.getStateInstanceID(attributes.get(0)));
        attributes.add(this.getAlertStateInstanceID(attributes.get(0)));

        return attributes;
    }


    private IDRACCardStringView getStateInstanceID(IDRACCardStringView item) {
        IDRACCardStringView attribute = new IDRACCardStringView();
        attribute.setfQDD(item.getfQDD());
        attribute.setGroupID(item.getGroupID());
        attribute.setAttributeName("State");
        attribute.setCurrentValue("Enabled");
        attribute.setInstanceID(item.getfQDD() + "#" + item.getGroupID() + "#State");

        return attribute;
    }


    private IDRACCardStringView getAlertStateInstanceID(IDRACCardStringView item) {
        IDRACCardStringView attribute = new IDRACCardStringView();
        attribute.setfQDD(item.getfQDD());
        attribute.setGroupID("IPMILan.1");
        attribute.setAttributeName("AlertEnable");
        attribute.setCurrentValue("Enabled");
        attribute.setInstanceID(attribute.getfQDD() + "#" + attribute.getGroupID() + "#AlertEnable");

        return attribute;
    }


    private boolean isDestinationFree(String fqdd, String groupID, String attributeName, String DEFAULT_VALUE, IDRACCardStringView item) {
        boolean status = false;
        groupID = StringUtils.substringBefore(groupID, ".");
        String tmp = "";
        for (int i = 1; i <= DESTINATION_LIST_SIZE; i++) {

            tmp = fqdd + "#" + groupID + "." + i + "#" + attributeName;

            if (item.getInstanceID().trim().equalsIgnoreCase(tmp))
                supported++;

            if ((item.getInstanceID().trim()).equalsIgnoreCase(tmp) && item.getCurrentValue().equalsIgnoreCase(this.getTrapDestinationIP())) {
                duplicate = true;
                status = true;
                break;
            }
            if ((item.getInstanceID().trim()).equalsIgnoreCase(tmp) && (item.getCurrentValue() == null || item.getCurrentValue().isEmpty() || item.getCurrentValue().equalsIgnoreCase(DEFAULT_VALUE))) {
                status = true;
                break;

            }
        }

        return status;
    }

}
