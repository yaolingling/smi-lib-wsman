/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.sun.ws.management.addressing.Addressing;

public class ChangeBootSourceStateCmd extends WSManBaseCommand {

    private WSManageSession session = null;

    private String instanceType = null;
    private boolean isEnabled = false;
    private List<String> instanceIdList = null;

    private static final Logger logger = LoggerFactory.getLogger(ChangeBootSourceStateCmd.class);


    public ChangeBootSourceStateCmd(String ipAddr, String userName, String passwd) {
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: ChangeBootSourceStateCmd(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }

        session = super.getSession();
        session.setResourceUri(getResourceURI());

        session.setInvokeCommand(WSManMethodEnum.CHANGE_BOOT_STATUS.toString());

        logger.trace("Exiting constructor: ChangeBootSourceStateCmd()");

    }


    private String getResourceURI() {

        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);

        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_BootConfigSetting);

        return sb.toString();
    }


    @Override
    public String execute() throws Exception {
        logger.trace("Entering function: execute()");

        session.addSelector(WSManMethodParamEnum.INSTANCE_ID.toString(), instanceType);
        session.addUserParam(WSManMethodParamEnum.ENABLED_STATE.toString(), isEnabled ? "1" : "0");

        for (int i = 0; i < instanceIdList.size(); i++) {
            this.session.addUserParam(WSManMethodParamEnum.SOURCE.toString(), instanceIdList.get(i));
        }

        Addressing response = session.sendInvokeRequest();

        String retValue = (String) findObjectInDocument(response.getBody(), "//pre:ChangeBootSourceState_OUTPUT/pre:ReturnValue/text()", XPathConstants.STRING);

        logger.trace("Exiting function: execute()");

        return retValue;
    }


    private Object findObjectInDocument(SOAPElement doc, String xPathLocation, QName qname) throws XPathExpressionException {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        xpath.setNamespaceContext(new PersonalNamespaceContext("http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/root/dcim/DCIM_BootConfigSetting"));
        XPathExpression expr = xpath.compile(xPathLocation);
        Object result = expr.evaluate(doc, qname);
        return result;
    }


    public boolean isEnabled() {
        return isEnabled;
    }


    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }


    public List<String> getInstanceIdList() {
        return instanceIdList;
    }


    public void setInstanceIdList(List<String> instanceIdList) {
        this.instanceIdList = instanceIdList;
    }


    public String getInstanceType() {
        return instanceType;
    }


    public void setInstanceType(String instanceType) {
        this.instanceType = instanceType;
    }

}
