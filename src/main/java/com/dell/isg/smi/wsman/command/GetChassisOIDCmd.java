/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.commons.utilities.xml.XmlHelper;
import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.command.entity.WsmanCredentials;
import com.sun.ws.management.addressing.Addressing;

/**
 * 
 * @author Dan_Phelps
 *
 */
public class GetChassisOIDCmd extends WSManBaseCommand {

    private WSManageSession session = null;
    // private static Logger logger = LoggerFactory.getLogger(GetChassisOIDCmd.class);
    private static final Logger logger = LoggerFactory.getLogger(GetChassisOIDCmd.class);


    public GetChassisOIDCmd(String hostName, WsmanCredentials credentials) {
        this(hostName, credentials.getUserid(), credentials.getPassword(), credentials.isCertificateCheck());
    }


    public GetChassisOIDCmd(String hostName, String user, String pw, boolean bCertCheck) {
        super(hostName, user, pw, bCertCheck);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: GetChassisOIDCmd(String hostName - %s, String user - %s, String pw - %s, boolean bCertCheck - %s)", hostName, user, "####", Boolean.toString(bCertCheck)));
        }
        session = this.getSession();

        session.setResourceUri(getResourceURI());
        session.addSelector("__cimnamespace", "root/dcim/sysman");
        session.addSelector("InstanceID", "DCIM_OEM_DataAccessModule1");
        session.setInvokeCommand("SendCmd");
        session.addUserParam("CommandAndArguments", "omacmd=getchassisprops __01oid=2 omausrmask=7 omausrinfo=administrator __04omausrtruemask=4 localLogin=TRUE daname=hipda");
        logger.trace("Exiting constructor: GetChassisOIDCmd()");
    }


    public String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);

        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_OEM_DataAccessModule);

        return sb.toString();
    }


    @Override
    public String execute() throws Exception {
        logger.trace("Entering function: execute()");
        String chassisOID = null;
        String content = null;
        String output = null;

        super.getLogger(getClass()).info("Execute GetChassisOIDCmd ");

        Addressing response = session.sendInvokeRequest();
        Document tempDoc = session.extractAddressBody(response);
        content = XmlHelper.convertDocumenttoString(tempDoc);
        output = content.toString().replaceAll("&gt;", ">");
        output = output.replaceAll("&lt;", "<");
        Document dom;
        dom = XmlHelper.convertStringToXMLDocument(output);
        NodeList nodes = dom.getElementsByTagName("ChassisProps2");
        for (int i = 0; i < nodes.getLength(); i++) {
            // for link tag
            Element tag = (Element) nodes.item(i);
            String attribute = tag.getAttribute("oid");
            if (attribute.isEmpty()) {
                continue;
            }
            chassisOID = attribute;
            return chassisOID;
        }
        logger.trace("Exiting function: execute()");
        return null;
    }

}
