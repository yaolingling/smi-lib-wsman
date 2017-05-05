/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.idraccmd;

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
import com.dell.isg.smi.wsman.command.EnumerateIDRACCardEnumCmd;
import com.dell.isg.smi.wsman.command.entity.IDRACCardStringView;

public class GetIdracEnumByInstanceId extends WSManBaseCommand {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(EnumerateIDRACCardEnumCmd.class);
    private String instanceId = null;


    public GetIdracEnumByInstanceId(String ipAddr, String userName, String passwd) {

        super(ipAddr, userName, passwd);
        logger.trace(String.format("Entering constructor: GetIdracEnumByInstanceId(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        session = this.getSession();
        session.setResourceUri(getResourceURI());
        logger.trace("Exiting constructor: GetIdracEnumByInstanceId()");

    }


    @Override
    public List<IDRACCardStringView> execute() throws Exception {

        List<IDRACCardStringView> iDracCardViewList = new ArrayList<IDRACCardStringView>();
        NodeList response = this.sendRequestEnumerationReturnNodeList();
        IDRACCardStringView idracCardString = null;
        if (null != response) {
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xpath = xPathFactory.newXPath();
            if (response.getLength() > 0) {
                response = response.item(0).getChildNodes();
                for (int i = 0; i < response.getLength(); i++) {

                    idracCardString = new IDRACCardStringView();
                    Node itemNode = response.item(i);
                    int k = i + 1;
                    idracCardString.setAttributeDisplayName(xpath.evaluate("//*[local-name()='DCIM_iDRACCardEnumeration'][" + k + "]/*[local-name()='AttributeDisplayName']/text()", itemNode));
                    idracCardString.setAttributeName(xpath.evaluate("//*[local-name()='DCIM_iDRACCardEnumeration'][" + k + "]/*[local-name()='AttributeName']/text()", itemNode));
                    idracCardString.setCurrentValue(xpath.evaluate("//*[local-name()='DCIM_iDRACCardEnumeration'][" + k + "]/*[local-name()='CurrentValue']/text()", itemNode));
                    idracCardString.setDefaultValue(xpath.evaluate("//*[local-name()='DCIM_iDRACCardEnumeration'][" + k + "]/*[local-name()='DefaultValue']/text()", itemNode));
                    idracCardString.setDependency(xpath.evaluate("//*[local-name()='DCIM_iDRACCardEnumeration'][" + k + "]/*[local-name()='Dependency']/text()", itemNode));
                    idracCardString.setDisplayOrder(xpath.evaluate("//*[local-name()='DCIM_iDRACCardEnumeration'][" + k + "]/*[local-name()='DisplayOrder']/text()", itemNode));
                    idracCardString.setfQDD(xpath.evaluate("//*[local-name()='DCIM_iDRACCardEnumeration'][" + k + "]/*[local-name()='FQDD']/text()", itemNode));
                    idracCardString.setGroupDisplayName(xpath.evaluate("//*[local-name()='DCIM_iDRACCardEnumeration'][" + k + "]/*[local-name()='GroupID']/text()", itemNode));
                    idracCardString.setGroupID(xpath.evaluate("//*[local-name()='DCIM_iDRACCardEnumeration'][" + k + "]/*[local-name()='GroupID']/text()", itemNode));
                    idracCardString.setInstanceID(xpath.evaluate("//*[local-name()='DCIM_iDRACCardEnumeration'][" + k + "]/*[local-name()='InstanceID']/text()", itemNode));
                    idracCardString.setIsReadOnly(xpath.evaluate("//*[local-name()='DCIM_iDRACCardEnumeration'][" + k + "]/*[local-name()='IsReadOnly']/text()", itemNode));
                    idracCardString.setMaxLength(xpath.evaluate("//*[local-name()='DCIM_iDRACCardEnumeration'][" + k + "]/*[local-name()='MaxLength']/text()", itemNode));
                    idracCardString.setMinLength(xpath.evaluate("//*[local-name()='DCIM_iDRACCardEnumeration'][" + k + "]/*[local-name()='MinLength']/text()", itemNode));
                    idracCardString.setPendingValue(xpath.evaluate("//*[local-name()='DCIM_iDRACCardEnumeration'][" + k + "]/*[local-name()='PendingValue']/text()", itemNode));

                    if (idracCardString.getInstanceID().equals(instanceId)) {
                        iDracCardViewList.add(idracCardString);
                        break;
                    }
                }
            }
        }
        return iDracCardViewList;
    }


    private String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_iDRACCardEnumeration);

        return sb.toString();
    }


    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

}
