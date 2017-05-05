/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import javax.xml.soap.SOAPException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.sun.ws.management.addressing.Addressing;

public class BootToPXECmd extends WSManBaseCommand {

    private WSManageSession session = null;
    private final static Logger logger = LoggerFactory.getLogger(BootToPXECmd.class);


    public BootToPXECmd(String ipAddr, String userName, String passwd) {
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: BootToPXECmd(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        session = super.getSession();
        session.setResourceUri(getResourceURI());
        logger.trace("Exiting constructor: BootToPXECmd()");
    }


    @Override
    public Object execute() throws Exception {
        logger.trace("Entering function: execute()");

        this.addSelectors(getResourceURI());
        session.setInvokeCommand(WSManMethodEnum.BOOT_TO_PXE.toString());
        /*
         * FIX ME for ICEE CommandResponse cr = new CommandResponse();
         * 
         * for(int retry = 5; retry > 0; retry--) { Addressing response = session.sendInvokeRequest();
         * 
         * String retValue = (String) this.findObjectInDocument(response.getBody(), "//pre:BootToPXE_OUTPUT/pre:ReturnValue/text()", getResourceURI(), XPathConstants.STRING);
         * 
         * if ( ("0").equals(retValue) ) { cr.setJobID(retValue); cr.setbSuccess(Boolean.TRUE); logger.info("PXE Boot command successful."); break; } else { String msgVal =
         * (String) findObjectInDocument(response.getBody(), "//pre:BootToPXE_OUTPUT/pre:Message/text()", getResourceURI(), XPathConstants.STRING); cr.setDetails(msgVal);
         * cr.setLCErrorCode(this.getLCErrorCode()); cr.setLCErrorStr(this.getLCErrorStr()); logger.error("Return Value: (" + retValue + ") - " + msgVal);
         * cr.setbSuccess(Boolean.FALSE); if(retry > 1){ // super.waitForReadyStatus(CommonConstants.FOUR_MIN); logger.info("Retrying BootToPXE command...");
         * Thread.sleep(CommonConstants.THIRTY_SEC); } } }
         */
        logger.trace("Exiting function: execute()");
        return null;

    }


    private String getResourceURI() {
        StringBuilder sb = new StringBuilder();
        sb.append(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE);
        sb.append(WSManClassEnum.DCIM_OSDeploymentService);
        return sb.toString();
    }


    private Object extractCommandResponse(Addressing addressing) throws SOAPException {
        /*
         * FIX ME for ICEE
         *
         * SOAPElement doc = addressing.getBody(); String resourceURI = null; String jobID = null; CommandResponse response = new CommandResponse();
         * 
         * try { //Get ResourceURI Object result = findObjectInDocument(doc, "//wsman:ResourceURI/text()", getResourceURI(), XPathConstants.STRING); if ( result == null ||
         * ((String)result).length() == 0 ) { response.setbSuccess(Boolean.FALSE); response.setDetails("Unable to get ResourceURI from response"); return response; } else {
         * resourceURI = (String) result; }
         * 
         * //Get Selectors result = findObjectInDocument(doc, "//wsman:SelectorSet", getResourceURI(), XPathConstants.NODESET); NodeList nodeLst = (NodeList) result; String
         * attrNode = null;
         * 
         * if ( nodeLst == null ) { response.setbSuccess(Boolean.FALSE); response.setDetails("Unable to get SelectorSet from response"); return response; } else { for (int i = 0; i
         * < nodeLst.getLength(); i++) { attrNode = nodeLst.item(i).getFirstChild().getAttributes().item(0).getTextContent(); if ( attrNode.equals("InstanceID") ) { jobID =
         * nodeLst.item(i).getFirstChild().getTextContent(); break; }
         * 
         * attrNode = nodeLst.item(i).getLastChild().getAttributes().item(0).getTextContent(); if ( attrNode != null ) { jobID = nodeLst.item(i).getLastChild().getTextContent();
         * break; } }
         * 
         * if ( jobID == null ) { response.setbSuccess(Boolean.FALSE); response.setDetails("Unable to get Instance Selector from response"); return response; } else {
         * response.setResourceURI(resourceURI); response.setJobID(jobID); response.setbSuccess(Boolean.TRUE); return response; } } } catch (Exception e) { e.printStackTrace();
         * response.setbSuccess(Boolean.FALSE); response.setDetails(e.getMessage()); return response; }
         */
        return null;
    }

}
