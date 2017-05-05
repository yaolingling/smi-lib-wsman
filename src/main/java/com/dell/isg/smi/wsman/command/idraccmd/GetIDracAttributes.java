/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
/**
 * 
 */
package com.dell.isg.smi.wsman.command.idraccmd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.dell.isg.smi.commons.elm.utilities.xml.XmlHelper;
import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManageSession;

/**
 * @author Muhammad_R
 *
 */
public class GetIDracAttributes extends AbstractIdrac {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(GetIDracAttributes.class);


    /**
     * @param ipAddr
     * @param userName
     * @param passwd
     */
    public GetIDracAttributes(String ipAddr, String userName, String passwd) {

        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: GetIDracAttributes(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        session = super.getSession();
        session.setResourceUri(getResourceURI());
        logger.trace("Exiting constructor: GetIDracAttributes()");
    }


    private String getResourceURI() {

        StringBuilder sb = new StringBuilder();
        sb.append(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE);
        sb.append(WSManClassEnum.DCIM_iDRACCardAttribute);
        return sb.toString();
    }


    @Override
    public String execute() throws Exception {
        logger.trace("Entering function: execute()");
        logger.info("processing execute for wsman command");
        Document response = this.sendRequestEnumerationReturnDocument();
        String tmp = XmlHelper.convertDocumenttoString(response);
        logger.trace("Exiting function: execute()");
        return tmp;
    }
}
