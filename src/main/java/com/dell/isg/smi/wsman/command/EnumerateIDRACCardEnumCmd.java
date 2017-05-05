/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.command.entity.IDRACCardStringView;

public class EnumerateIDRACCardEnumCmd extends WSManBaseCommand {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(EnumerateIDRACCardEnumCmd.class);


    public EnumerateIDRACCardEnumCmd(String ipAddr, String userName, String passwd) {

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

        List<IDRACCardStringView> iDracCardViewList = new ArrayList<IDRACCardStringView>();
        // String instanceIDValue="iDRAC.Embedded.1#IPMILan.1#AlertEnable";
        // String INSTANCE_ID="InstanceID";
        // String currentValue="CurrentValue";
        NodeList response = this.sendRequestEnumerationReturnNodeList();
        IDRACCardStringView idracCardString = null;
        if (null != response) {
            response = response.item(0).getChildNodes();

            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xpath = xPathFactory.newXPath();
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


    private String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_iDRACCardEnumeration);

        return sb.toString();
    }
}
