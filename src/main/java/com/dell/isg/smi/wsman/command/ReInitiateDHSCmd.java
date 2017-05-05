/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import javax.xml.soap.SOAPBody;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.model.CommandResponse;
import com.sun.ws.management.addressing.Addressing;

/**
 * This command is used to re-initiate the auto discovery and handshake.
 * 
 *
 */
public class ReInitiateDHSCmd extends WSManBaseCommand {

    public static enum PerformAutoDiscoveryEnum {
        Off(1), Now(2), NextBoot(3);

        private int code;


        private PerformAutoDiscoveryEnum(int c) {
            code = c;
        }


        public int getCode() {
            return code;
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(ReInitiateDHSCmd.class);

    private WSManageSession session = null;
    private String provisioningServer = null;
    private PerformAutoDiscoveryEnum autoEnum;


    /**
     * Default Constructor
     * 
     * @param ipAddr
     * @param userName
     * @param passwd
     * @param updateValue
     */
    public ReInitiateDHSCmd(String ipAddr, String userName, String passwd, String provisioningServer, PerformAutoDiscoveryEnum autoEnum) {
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: ReInitiateDHSCmd(String ipAddr - %s, String userName - %s, String passwd - %s, String provisioningServer - %s, PerformAutoDiscoveryEnum autoEnum - %s)", ipAddr, userName, "####", provisioningServer, PerformAutoDiscoveryEnum.class.getName()));
        }
        session = super.getSession();
        this.provisioningServer = provisioningServer;
        this.autoEnum = autoEnum;
        logger.trace("Exiting constructor: ReInitiateDHSCmd()");
    }


    /**
     * Create invoke resource url.
     * 
     * @return String
     */
    private String getInvokeResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);

        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_LCService);
        return sb.toString();
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.dell.pg.spectre.common.protocol.wsman.WSManBaseCommand#execute()
     */
    @Override
    public CommandResponse execute() throws Exception {
        logger.trace("Entering function: execute()");
        CommandResponse outcome = invokeDHS();
        logger.trace("Exiting function: execute()");
        return outcome;
    }


    /**
     * @return
     * @throws Exception
     */
    private CommandResponse invokeDHS() throws Exception {
        logger.info("Entering execute() ");
        this.session.setResourceUri(getInvokeResourceURI());
        this.addSelectors(getInvokeResourceURI());
        this.session.setInvokeCommand(WSManMethodEnum.REINITIATE_DHS.toString());
        this.session.addUserParam(WSManMethodParamEnum.PROVISIONING_SERVER.toString(), provisioningServer);
        this.session.addUserParam(WSManMethodParamEnum.RESET_TO_FACTORY_DEFAULTS.toString(), "TRUE");
        this.session.addUserParam(WSManMethodParamEnum.PERFORM_AUTO_DISCOVERY.toString(), String.valueOf(autoEnum.getCode()));
        Addressing response = this.session.sendInvokeRequest();

        CommandResponse cmdResponse = new CommandResponse();

        if (null != response) {
            cmdResponse.setReturnCode(getObjectFromSoapBody(response.getBody(), "ReturnValue"));
            cmdResponse.setReturnMessage(getObjectFromSoapBody(response.getBody(), "Message"));
        } else {
            cmdResponse.setReturnCode("2");
            cmdResponse.setReturnMessage("Unknown exception occurred");
        }

        return cmdResponse;
    }


    private String getObjectFromSoapBody(SOAPBody soapBody, String elementName) throws Exception {

        NodeList nodeList = soapBody.getChildNodes();
        Node node = nodeList.item(0);
        NodeList childNodeList = node.getChildNodes();
        String value = null;
        for (int i = 0; i < childNodeList.getLength(); i++) {
            Node childNode = childNodeList.item(i);
            System.out.println(childNode.getLocalName());
            if (childNode.getLocalName().equalsIgnoreCase(elementName)) {
                // FIX ME for ICEE
                // value = childNode.getTextContent();
            }
        }
        return value;
    }


    public String getProvisioningServer() {
        return provisioningServer;
    }


    public void setProvisioningServer(String provisioningServer) {
        this.provisioningServer = provisioningServer;
    }


    public void setAutoEnum(PerformAutoDiscoveryEnum autoEnum) {
        this.autoEnum = autoEnum;
    }


    public PerformAutoDiscoveryEnum getAutoEnum() {
        return autoEnum;
    }
}
