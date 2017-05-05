/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

/**
 * @author prashanth.gowda
 *
 */

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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.command.entity.IDRACCardStringView;
import com.dell.isg.smi.wsman.entity.KeyValuePair;
import com.dell.isg.smi.wsman.utilities.ExceptionUtilities;
import com.sun.ws.management.addressing.Addressing;

public class UpdateEventsCmd extends WSManBaseCommand {

    private WSManageSession session = null;
    private IDRACCardStringView idracCardStringView = null;


    /**
     * @return the idracCardStringView
     */
    public IDRACCardStringView getIdracCardStringView() {
        return idracCardStringView;
    }


    /**
     * @param idracCardStringView the idracCardStringView to set
     */
    public void setIdracCardStringView(IDRACCardStringView idracCardStringView) {
        this.idracCardStringView = idracCardStringView;
    }

    private static final Logger logger = LoggerFactory.getLogger(SetEventsCmd.class);


    public UpdateEventsCmd(String ipAddr, String userName, String passwd) {

        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: UpdateEventsCmd(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        session = super.getSession();
        this.session.setResourceUri(getResourceURI());
        this.addSelectors(getResourceURI());
        this.session.setInvokeCommand(WSCommandRNDConstant.APPLY_ATTRIBUTES);
        logger.trace("Exiting constructor: SetEventsCmd()");

    }


    private String getResourceURI() {
        String resourceUri = WSCommandRNDConstant.WSMAN_BASE_URI + WSCommandRNDConstant.WS_OS_SVC_NAMESPACE + WSCommandRNDConstant.DCIM_iDRAC_CARD_SERVICE;
        return resourceUri;
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
        IDRACCardStringView attribute = getIdracCardStringView();
        this.addTargetValue(attribute.getfQDD());
        this.addAttributeKeyValue(attribute.getGroupID() + "#" + attribute.getAttributeName(), attribute.getCurrentValue());
        String status = "";
        Addressing addressing = session.sendInvokeRequest();
        status = this.parseResponseByXPath(addressing, "ApplyAttributes", "ReturnValue");
        List<KeyValuePair> keyValuePair = new ArrayList<KeyValuePair>();
        KeyValuePair keyVal1 = new KeyValuePair();
        keyVal1.setKey("returnStatus");
        keyVal1.setValue(status);
        keyValuePair.add(keyVal1);

        logger.trace("Exiting function: execute()");
        return keyValuePair;
    }


    protected String parseResponseByXPath(Addressing response, String jobName, String elementName) throws Exception {
        String itemResponse = "";
        XPath xpath = XPathFactory.newInstance().newXPath();
        SOAPBody soapBody = response.getBody();
        try {
            if (elementName.equalsIgnoreCase("ReturnValue")) {
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

}
