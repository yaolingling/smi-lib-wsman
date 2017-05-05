/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
/**
 * 
 */
package com.dell.isg.smi.wsman.command;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;

/**
 * @author Jayalakshmi_Shankara
 *
 */
public class EnumerateiDRACCardView extends WSManBaseCommand {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(EnumerateiDRACCardView.class);


    /**
     * @param ipAddr
     * @param userName
     * @param passwd
     */
    public EnumerateiDRACCardView(String ipAddr, String userName, String passwd) {
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: EnumerateiDRACCardView(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        session = this.getSession();
        session.setResourceUri(getResourceURI());
        logger.trace("Exiting constructor: EnumerateiDRACCardView()");
    }


    private String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_IDRACCardView);

        return sb.toString();
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.dell.pg.spectre.common.protocol.wsman.WSManBaseCommand#execute()
     */
    @Override
    public Object execute() throws Exception {
        logger.trace("Entering function: execute()");
        String iDracModel = "Enterprise";

        NodeList response = this.sendRequestEnumerationReturnNodeList();
        if (null != response) {

            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xpath = xPathFactory.newXPath();

            Node itemNode = response.item(0);

            iDracModel = xpath.evaluate("//*[local-name()='Model']/text()", itemNode);
        }
        logger.trace("Exiting function: execute()");
        return iDracModel;
    }
}
