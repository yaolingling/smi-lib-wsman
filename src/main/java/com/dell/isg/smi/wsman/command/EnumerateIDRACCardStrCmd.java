/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.wsmanclient.WSManException;
import com.dell.isg.smi.wsmanclient.util.WSManUtils;
import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManClientBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.command.entity.IDRACCardStringView;

public class EnumerateIDRACCardStrCmd extends WSManClientBaseCommand<List<IDRACCardStringView>> {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(EnumerateIDRACCardStrCmd.class);


    public EnumerateIDRACCardStrCmd() {
    }


    public EnumerateIDRACCardStrCmd(String ipAddr, String userName, String passwd) {

        // set the WSMan Session
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: EnumerateIDRACCardStringCmd(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        session = this.getSession();
        session.setResourceUri(getResourceURI());

        logger.trace("Exiting constructor: EnumerateIDRACCardEnumerationCmd()");

    }


    @Override
    public List<IDRACCardStringView> execute() throws Exception {
        NodeList response = this.sendRequestEnumerationReturnNodeList();
        List<IDRACCardStringView> iDracCardViewList = parse(response);
        return iDracCardViewList;
    }


    public String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_iDRACCardString);

        return sb.toString();
    }


    private String getKeyValue(Node node, String key) {
        String value = "";
        try {
            NodeList childList = node.getChildNodes();
            for (int i = 0; i < childList.getLength(); i++) {
                Node childNode = childList.item(i);
                if (childNode.getNodeName().contains(key)) {
                    if (childNode.hasChildNodes()) {
                        NodeList nodeList1 = childNode.getChildNodes();
                        for (int k = 0; k < nodeList1.getLength(); k++) {
                            Node finalNode = nodeList1.item(k);
                            if (Element.TEXT_NODE == finalNode.getNodeType()) {
                                value = finalNode.getNodeValue();
                                break;
                            }
                        }

                        if (!value.isEmpty()) {
                            break;
                        }
                    }
                }
            }
        } catch (Exception exp) {
            logger.error(exp.getMessage());
        }

        return value;
    }


    @Override
    public List<IDRACCardStringView> parse(String xml) throws Exception {
        List<IDRACCardStringView> iDracCardViewList = new ArrayList<IDRACCardStringView>();
        if (xml == null)
            return iDracCardViewList;

        // Create the document from the xml.
        Document doc;
        try {
            doc = WSManUtils.toDocument(xml);
        } catch (Exception e) {
            String msg = "Unable to create IDracCardSTringView document from xml result.";
            logger.error(msg, e);
            throw new WSManException(msg);
        }

        // Create a nodelist.
        Element element = doc.getDocumentElement();
        NodeList nodeList;
        try {
            nodeList = element.getElementsByTagNameNS(WSCommandRNDConstant.WS_MAN_NAMESPACE, WSCommandRNDConstant.WSMAN_ITEMS_TAG);
        } catch (DOMException e) {
            String msg = "Unable to extract nodelist from IDracCardSTringView document.";
            logger.error(msg, e);
            throw new WSManException(msg);
        }
        if (nodeList == null)
            return iDracCardViewList;

        iDracCardViewList = parse(nodeList);
        return iDracCardViewList;
    }


    private List<IDRACCardStringView> parse(NodeList response) throws Exception {
        // String instanceIDValue="iDRAC.Embedded.1#IPMILan.1#AlertEnable";
        // String INSTANCE_ID="InstanceID";
        // String currentValue="CurrentValue";
        List<IDRACCardStringView> iDracCardViewList = new ArrayList<IDRACCardStringView>();
        IDRACCardStringView idracCardString = null;
        if (null != response) {
            response = response.item(0).getChildNodes();

            for (int i = 0; i < response.getLength(); i++) {

                idracCardString = new IDRACCardStringView();
                Node itemNode = response.item(i);
                idracCardString.setAttributeDisplayName(getKeyValue(itemNode, "AttributeDisplayName"));
                idracCardString.setAttributeName(getKeyValue(itemNode, "AttributeName"));
                idracCardString.setCurrentValue(getKeyValue(itemNode, "CurrentValue"));
                idracCardString.setDefaultValue(getKeyValue(itemNode, "DefaultValue"));
                idracCardString.setDependency(getKeyValue(itemNode, "Dependency"));
                idracCardString.setDisplayOrder(getKeyValue(itemNode, "DisplayOrder"));
                idracCardString.setfQDD(getKeyValue(itemNode, "FQDD"));
                idracCardString.setGroupDisplayName(getKeyValue(itemNode, "GroupDisplayName"));
                idracCardString.setGroupID(getKeyValue(itemNode, "GroupID"));
                idracCardString.setInstanceID(getKeyValue(itemNode, "InstanceID"));
                idracCardString.setIsReadOnly(getKeyValue(itemNode, "IsReadOnly"));
                idracCardString.setMaxLength(getKeyValue(itemNode, "MaxLength"));
                idracCardString.setMinLength(getKeyValue(itemNode, "MinLength"));
                idracCardString.setPendingValue(getKeyValue(itemNode, "PendingValue"));
                iDracCardViewList.add(idracCardString);
            }
        }
        return iDracCardViewList;
    }

}
