/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
/**
 * 
 */
package com.dell.isg.smi.wsman.command;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.xpath.XPathConstants;

import org.dmtf.schemas.wbem.wsman._1.wsman.SelectorSetType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.WSManageSession.EnumReferenceParam;
import com.dell.isg.smi.wsman.entity.CommandResponse;
import com.sun.ws.management.addressing.Addressing;
import com.sun.ws.management.enumeration.Enumeration;
import com.sun.ws.management.enumeration.EnumerationExtensions.Mode;

/**
 * @author Dan_Phelps
 *
 */

public class UnpackAndAttachDriverPackCmd extends WSManBaseCommand {

    private WSManageSession session = null;
    private String osName = null;
    private static final Logger logger = LoggerFactory.getLogger(UnpackAndAttachDriverPackCmd.class);


    public UnpackAndAttachDriverPackCmd(String ipAddr, String userName, String passwd, String osName) {
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: UnpackAndAttachDriverPackCmd(String ipAddr - %s, String userName - %s, String passwd - %s, String osName - %s)", ipAddr, userName, "####", osName));
        }
        session = this.getSession();
        this.osName = osName;
        logger.trace("Exiting constructor: UnpackAndAttachDriverPackCmd()");
    }


    /**
     * @return 0 = Success, 1 = Not implemented, 2 = Failure, 4096 = Success + Job created.
     */
    @Override
    public CommandResponse execute() throws Exception {
        logger.trace("Entering function: execute()");
        GetDriverPackInfoCmd infoCmd = new GetDriverPackInfoCmd(session.getIpAddress(), session.getUser(), session.getPassword());
        List<String> validOSList = infoCmd.execute(); // this call will block on RS Status if LC is busy
        CommandResponse resCmd = new CommandResponse();
        resCmd.setbSuccess(Boolean.FALSE); // default to failure
        for (String s : validOSList) {
            if (osName.equalsIgnoreCase(s.trim())) {
                List<EnumReferenceParam> items = callEnumWithEPR();
                SelectorSetType settype = items.get(0).getSelectorSetTypes();

                session.getSelectors().addAll(settype.getSelector());
                session.setResourceUri(getResourceURI());
                session.addUserParam("OSName", osName);
                session.setInvokeCommand(WSManMethodEnum.UNPACK_AND_ATTACH.toString());
                Addressing response = session.sendInvokeRequest();

                String retValue = (String) findObjectInDocument(response.getBody(), "//pre:UnpackAndAttach_OUTPUT//pre:ReturnValue/text()", getResourceURI(), XPathConstants.STRING);

                if (("0").equals(retValue)) {
                    resCmd.setJobID(retValue);
                    resCmd.setbSuccess(Boolean.TRUE);
                    logger.trace("Exiting function: execute()");
                    return resCmd;
                } else if (("4096").equals(retValue)) {

                    CommandResponse result = extractCommandResponse(response);
                    logger.trace("Exiting function: execute()");
                    return result;
                } else {
                    String msgVal = (String) findObjectInDocument(response.getBody(), "//pre:BootToNetworkISO_OUTPUT/pre:Message/text()", this.getResourceURI(), XPathConstants.STRING);
                    resCmd.setDetails(msgVal);
                    resCmd.setbSuccess(Boolean.FALSE);
                    resCmd.setLCErrorCode(this.getLCErrorCode());
                    resCmd.setLCErrorStr(this.getLCErrorStr());
                    logger.trace("Exiting function: execute()");
                    return resCmd;
                }
            }
        }
        logger.trace("Exiting function: execute()");
        return resCmd;
    }


    private List<EnumReferenceParam> callEnumWithEPR() throws SOAPException, JAXBException, DatatypeConfigurationException, IOException, Exception {
        session.setResourceUri(getResourceURI());
        Enumeration responseEnum = this.sendRequestEnumeration(Mode.EnumerateEPR);
        List<EnumReferenceParam> items = session.extractRequestEnumerationRefParameters(responseEnum);
        return items;
    }


    private CommandResponse extractCommandResponse(Addressing addressing) throws SOAPException {
        SOAPElement doc = addressing.getBody();
        String resourceURI = null;
        String jobID = null;
        CommandResponse response = new CommandResponse();

        try {
            // Get ResourceURI
            Object result = findObjectInDocument(doc, "//wsman:ResourceURI/text()", this.getResourceURI(), XPathConstants.STRING);
            if (result == null || ((String) result).length() == 0) {
                response.setbSuccess(Boolean.FALSE);
                response.setDetails("Unable to get ResourceURI from response");
                return response;
            } else {
                resourceURI = (String) result;
            }

            // Get Selectors
            result = findObjectInDocument(doc, "//wsman:SelectorSet", this.getResourceURI(), XPathConstants.NODESET);
            NodeList nodeLst = (NodeList) result;
            String attrNode = null;

            if (nodeLst == null) {
                response.setbSuccess(Boolean.FALSE);
                response.setDetails("Unable to get SelectorSet from response");
                return response;
            } else {
                for (int i = 0; i < nodeLst.getLength(); i++) {
                    attrNode = nodeLst.item(i).getFirstChild().getAttributes().item(0).getTextContent();
                    if (attrNode.equals("InstanceID")) {
                        jobID = nodeLst.item(i).getFirstChild().getTextContent();
                        break;
                    }

                    attrNode = nodeLst.item(i).getLastChild().getAttributes().item(0).getTextContent();
                    if (attrNode != null) {
                        jobID = nodeLst.item(i).getLastChild().getTextContent();
                        break;
                    }
                }

                if (jobID == null) {
                    response.setbSuccess(Boolean.FALSE);
                    response.setDetails("Unable to get Instance Selector from response");
                    return response;
                } else {
                    response.setResourceURI(resourceURI);
                    response.setJobID(jobID);
                    response.setbSuccess(Boolean.TRUE);
                    return response;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setbSuccess(Boolean.FALSE);
            response.setDetails(e.getMessage());
            return response;
        }
    }


    private String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);

        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_OSDeploymentService);

        return sb.toString();
    }


    /**
     * @param body
     * @return
     */
    private List<String> buildList(SOAPBody body) {

        LinkedList<String> list = new LinkedList<String>();

        NodeList nl = body.getChildNodes().item(0).getChildNodes();
        Node node;
        for (int i = 0; i < nl.getLength(); i++) {
            node = nl.item(i);
            if (node.getFirstChild().getNodeType() == Node.TEXT_NODE) {
                if (node.getLocalName().equalsIgnoreCase("ReturnValue"))
                    continue;
                if (node.getLocalName().equalsIgnoreCase("Version")) {
                    list.addFirst(node.getFirstChild().getNodeName());
                    continue;
                }
                list.add(node.getFirstChild().getNodeValue());
            }
        }
        return list;
    }


    public static void main(String[] args) {

        UnpackAndAttachDriverPackCmd dao = new UnpackAndAttachDriverPackCmd("10.35.155.199", "spectre", "Spectre123", "Windows Server 2008");
        try {
            Object o = dao.execute();
            int i = 0;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
