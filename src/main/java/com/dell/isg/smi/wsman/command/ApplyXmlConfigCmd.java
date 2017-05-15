/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.command.WaitForLC.WaitForLCResponse;
import com.dell.isg.smi.wsman.command.idraccmd.IdracJobStatusCheckCmd;
import com.dell.isg.smi.wsman.entity.ResourceURIInfo;
import com.dell.isg.smi.wsman.model.XmlConfig;
import com.dell.isg.smi.wsman.utilities.XMLTool;
import com.dell.isg.smi.wsmanclient.WSManClientFactory;

/**
 * @author rahman.muhammad
 *
 */

public class ApplyXmlConfigCmd extends WSManBaseCommand {

    private static final Logger logger = LoggerFactory.getLogger(ApplyXmlConfigCmd.class);
    private WSManageSession session = null;
    private ResourceURIInfo resourceUriInfo = null;
    public static final int TIME_DELAY_MILLISECONDS = 30000;
    public static final int POLL_JOB_RETRY = 12;


    public ApplyXmlConfigCmd(String ipAddr, String userName, String passwd, String shareType, String shareName, String shareAddress, String FileName, String shareUserName, String sharePassword, int shutdownType) throws Exception {

        super(ipAddr, userName, passwd);

        session = super.getSession();
        intiCommand();
        session.addUserParam("IPAddress", shareAddress);
        session.addUserParam("ShareName", shareName);
        session.addUserParam("ShareType", shareType);
        session.addUserParam("FileName", FileName);
        session.addUserParam("Username", shareUserName);
        session.addUserParam("Password", sharePassword);

        /*
         * Optional Param
         */
        if (shutdownType != 0) {
            session.addUserParam("ShutdownType", Integer.toString(shutdownType));
            logger.info(" custom shutdownType: " + Integer.toString(shutdownType));
        } else {
            session.addUserParam("ShutdownType", "0");
        }

        // session.addUserParam("ShutdownType", "0");
        session.addUserParam("TimeToWait", "300");
        session.addUserParam("EndHostPowerState", "1");

        this.session.setInvokeCommand(WSManMethodEnum.IMPORT_SYSTEM_CONFIGURATION.toString());

    }


    public void intiCommand() throws Exception {

        session.setResourceUri(getResourceURI());

        if (resourceUriInfo == null) {
            GetResourceURIInfoCmd cmd = new GetResourceURIInfoCmd(session.getIpAddress(), session.getUser(), session.getPassword(), session.isCertificateCheck(), WSManClassEnum.DCIM_LCService.toString());
            Object result = cmd.execute();
            if (result != null) {
                resourceUriInfo = (ResourceURIInfo) result;
            }
        }

        session.addSelector(WSManMethodParamEnum.CREATION_CLASS_NAME.toString(), resourceUriInfo.getCreationClassName());
        session.addSelector(WSManMethodParamEnum.SYSTEM_NAME.toString(), resourceUriInfo.getSystemName());
        session.addSelector(WSManMethodParamEnum.NAME.toString(), resourceUriInfo.getName());
        session.addSelector(WSManMethodParamEnum.SYSTEM_CLASS_NAME.toString(), resourceUriInfo.getSystemCreationClassName());

    }


    @Override
    public XmlConfig execute() throws Exception {
        XmlConfig xmlConfigResponse = new XmlConfig();
        // wait for LC to be available
        WaitForLC waitForLC = new WaitForLC();
        com.dell.isg.smi.wsmanclient.IWSManClient client = WSManClientFactory.getClient(session.getIpAddress(), session.getUser(), session.getPassword());
        WaitForLCResponse waitForLCResponse = waitForLC.execute(client);
        if (waitForLCResponse != WaitForLCResponse.LC_AVAILABLE) {
            xmlConfigResponse.setResult(XmlConfig.JobStatus.FAILURE.toString());
            xmlConfigResponse.setMessage("LC is not available");
            return xmlConfigResponse;
        }

        logger.info("Entering execute(), Apply XML configuration for server {} onboarding ", session.getIpAddress());
        String content = XMLTool.convertAddressingToString(session.sendInvokeRequest());
        String jobId = getUpdateJobID(content);
        logger.trace("Exiting function: execute()");
        IdracJobStatusCheckCmd cmd = new IdracJobStatusCheckCmd(session.getIpAddress(), session.getUser(), session.getPassword(), jobId, TIME_DELAY_MILLISECONDS, POLL_JOB_RETRY);
        xmlConfigResponse = cmd.execute();
        return xmlConfigResponse;

    }


    protected String getUpdateJobID(String xmlSource) throws Exception {

        String jobId = "";
        if (xmlSource != null && !StringUtils.isEmpty(xmlSource)) {
            Document doc = convertStringToXMLDocument(xmlSource);
            if (doc != null) {
                NodeList selectors = doc.getElementsByTagName("wsman:Selector");
                if (selectors != null) {
                    for (int i = 0; i < selectors.getLength(); i++) {
                        Node selectorNode = selectors.item(i);

                        if (selectorNode.hasAttributes()) {
                            NamedNodeMap attribs = selectorNode.getAttributes();
                            Node attribNode = attribs.getNamedItem("Name");
                            if (attribNode != null) {
                                if (attribNode.hasChildNodes()) {
                                    Node instanceNode = attribNode.getChildNodes().item(0);
                                    if (instanceNode != null) {
                                        String instance = instanceNode.getNodeValue();
                                        if (instance.equals("InstanceID")) {
                                            jobId = selectorNode.getFirstChild().getNodeValue();
                                            break;
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
        return jobId;
    }


    private String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);

        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_LCService);

        return sb.toString();
    }
}
