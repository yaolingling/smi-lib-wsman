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
import com.dell.isg.smi.wsman.utilities.ExceptionUtilities;
import com.sun.ws.management.addressing.Addressing;
import com.sun.ws.management.enumeration.Enumeration;
import com.sun.ws.management.enumeration.EnumerationExtensions.Mode;

/**
 * @author Dan_Phelps
 *
 */
public class GetDriverPackInfoCmd extends WSManBaseCommand {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(GetDriverPackInfoCmd.class);


    public GetDriverPackInfoCmd(String ipAddr, String userName, String passwd) {
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: GetDriverPackInfoCmd(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        session = this.getSession();
        logger.trace("Exiting constructor: GetDriverPackInfoCmd()");
    }


    /**
     * @return If successful, a list containing Version and Supported OS's. Upon failure this method returns a List with 1 entry ("FAILED:" + Error message). First entry in list
     * will always be either version number or error message.
     */
    @Override
    public List<String> execute() throws Exception {
        logger.trace("Entering function: execute()");
        // Enumerate on DCIM_OSDeploymentService with EPR
        List<EnumReferenceParam> items = callEnumWithEPR();
        SelectorSetType settype = items.get(0).getSelectorSetTypes();

        session.getSelectors().addAll(settype.getSelector());
        session.setResourceUri(getResourceURI());
        session.setInvokeCommand(WSManMethodEnum.DRIVER_PACK_INFO.toString());

        List<String> osList = null;

        Addressing response = session.sendInvokeRequest();
        String retValue = (String) findObjectInDocument(response.getBody(), "//pre:GetDriverPackInfo_OUTPUT//pre:ReturnValue/text()", getResourceURI(), XPathConstants.STRING);

        if ("0".equals(retValue)) {
            osList = buildList(response.getBody());
        } else {
            String errorMessage = "FAILED: " + (String) findObjectInDocument(response.getBody(), "//pre:GetDriverPackInfo_OUTPUT//pre:Message/text()", getResourceURI(), XPathConstants.STRING);
            if (errorMessage.toLowerCase().contains("System Services is currently in use".toLowerCase()) || errorMessage.toLowerCase().contains("Lifecycle Controller is being used by another process".toLowerCase())) {
                throw ExceptionUtilities.getCoreRuntimeException(267132, errorMessage);
            } else {
                throw ExceptionUtilities.getCoreRuntimeException(267131, errorMessage);
            }
        }
        logger.trace("Exiting function: execute()");
        return osList;

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
                    list.addFirst(node.getFirstChild().getNodeValue());
                    continue;
                }
                list.add(node.getFirstChild().getNodeValue());
            }
        }
        return list;
    }


    private List<EnumReferenceParam> callEnumWithEPR() throws SOAPException, JAXBException, DatatypeConfigurationException, IOException, Exception {
        session.setResourceUri(getResourceURI());
        Enumeration responseEnum = this.sendRequestEnumeration(Mode.EnumerateEPR);
        List<EnumReferenceParam> items = session.extractRequestEnumerationRefParameters(responseEnum);
        return items;
    }


    private String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);

        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_OSDeploymentService);

        return sb.toString();
    }


    public static void main(String[] args) {

        GetDriverPackInfoCmd dao = new GetDriverPackInfoCmd("10.36.0.178", "spectre", "Spectre123");
        try {
            Object o = dao.execute();
            int i = 0;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
