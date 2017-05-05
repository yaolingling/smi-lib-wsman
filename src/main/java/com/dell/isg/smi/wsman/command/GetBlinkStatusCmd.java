/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.commons.elm.exception.RuntimeCoreException;
import com.dell.isg.smi.commons.elm.utilities.xml.XmlHelper;
import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.command.entity.BlinkStatus;
import com.dell.isg.smi.wsman.command.entity.WsmanCredentials;
import com.sun.ws.management.addressing.Addressing;

/**
 * 
 * @author Dan_Phelps
 *
 */
public class GetBlinkStatusCmd extends WSManBaseCommand {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(GetBlinkStatusCmd.class);


    public GetBlinkStatusCmd(String hostName, WsmanCredentials credentials) {
        super(hostName, credentials);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: GetBlinkStatusCmd(String hostName - %s, WsmanCredentials credentials - %s)", hostName, WsmanCredentials.class.getName()));
        }
        session = this.getSession();

        session.setResourceUri(getResourceURI());
        session.addSelector("__cimnamespace", "root/dcim/sysman");
        session.addSelector("InstanceID", "DCIM_OEM_DataAccessModule1");
        session.setInvokeCommand("SendCmd");
        session.addUserParam("CommandAndArguments", "daname=hipda omacmd=getchassisprops oid=2");

        logger.trace("Exiting constructor: GetBlinkStatusCmd()");
    }


    public String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);

        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_OEM_DataAccessModule);

        return sb.toString();
    }


    @Override
    public BlinkStatus execute() throws Exception {
        logger.trace("Entering function: execute()");

        String content = null;
        String output = null;
        String blinkStatusfromWSMAN = "";
        String timeoutfromWSMAN = null;

        Addressing doc = session.sendInvokeRequest();
        Document tempDoc = session.extractAddressBody(doc);
        content = XmlHelper.convertDocumenttoString(tempDoc);
        output = content.toString().replaceAll("&gt;", ">");
        output = output.replaceAll("&lt;", "<");

        try {
            Document dom;
            dom = XmlHelper.convertStringToXMLDocument(output);
            NodeList nodeLst = dom.getElementsByTagName("ChassIdentifyState");
            if (nodeLst != null && nodeLst.getLength() > 0) {
                Element blinkingStatus = (Element) nodeLst.item(0);
                if (blinkingStatus != null) {
                    NodeList blinkStatusLst = blinkingStatus.getChildNodes();
                    if (blinkStatusLst != null) {
                        blinkStatusfromWSMAN = ((Node) blinkStatusLst.item(0)).getNodeValue();
                    }
                }
            }
            nodeLst = dom.getElementsByTagName("ChassIdentifyTimeout");
            if (nodeLst != null && nodeLst.getLength() > 0) {
                Element timeOut = (Element) nodeLst.item(0);
                if (timeOut != null) {
                    NodeList timeOutLst = timeOut.getChildNodes();
                    if (timeOutLst != null) {
                        timeoutfromWSMAN = ((Node) timeOutLst.item(0)).getNodeValue();
                    }
                }
            }
        } catch (Exception e) {
            // logger.error("Error converting output from wiseman to valid XML : " + e.toString());
            super.getLogger(getClass()).error(e.getMessage(), e);
            throw new RuntimeCoreException(e);

        }
        BlinkStatus result = new BlinkStatus();
        if (blinkStatusfromWSMAN.equals("0"))
            result.setBlinking(false);
        else
            result.setBlinking(true);
        result.setTimeout(Integer.parseInt(timeoutfromWSMAN));

        logger.trace("Exiting function: execute()");
        return result;
    }

}
