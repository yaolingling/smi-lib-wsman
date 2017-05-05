/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.dmtf.schemas.wbem.wsman._1.wsman.SelectorSetType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.WSManageSession.EnumReferenceParam;
import com.sun.ws.management.addressing.Addressing;
import com.sun.ws.management.enumeration.Enumeration;
import com.sun.ws.management.enumeration.EnumerationExtensions.Mode;

public class GetRemoteServicesAPIStatus extends WSManBaseCommand {
    /*****************************************************************************************************************************
     * Output Possible values Description parameter Name
     * ------------------------------------------------------------------------------------------------------------------------------- Status 0 (Ready) Lifecycle Controller Remote
     * Services is ready to accept any web services request. 1 (Not Ready) Lifecycle Controller Remote Services is currently not ready to accept web services request. This could be
     * because the instrumentation in iDRAC might be reloading /not_ready or server is in POST or performing scheduled provisioning requests or Lifecycle Controller Unified Server
     * Configurator is in use. ------------------------------------------------------------------------------------------------------------------------------- MessageID LC060 LC061
     * ------------------------------------------------------------------------------------------------------------------------------- Message Lifecycle Controller Remote Services
     * is not ready. Message for ID LC060 Lifecycle Controller Remote Services is ready. Message for ID LC061
     * ------------------------------------------------------------------------------------------------------------------------------- ServerStatus 0 (Powered off) Server is
     * powered off 1 (In POST) Server is performing normal POST operation 2 (Out of POST) Server is out of POST 3 (Collecting System Inventory) Server is currently executing UEFI
     * Collect System Inventory On Restart application 4 (Automated Task Execution) Server is currently executing scheduled jobs using UEFI Automated Task application 5 (Lifecycle
     * Controller Unified Server Configurator) Server is executing UEFI Lifecycle Controller Unified Server Configurator application
     * ------------------------------------------------------------------------------------------------------------------------------- LCStatus 0 (Ready) Lifecycle Controller
     * instrumentation is up to date and enabled 1 (Not Initialized) Lifecycle Controller instrumentation is not initialized. The initialization operation may take up to a minute.
     * 2 (Reloading Data) Lifecycle Controller instrumentation is currently refreshing its cache because of a recent configuration change. The reloading operation typically takes
     * few seconds and could take up to few minutes to complete. 3 (Disabled) Lifecycle Controller is disabled on the server. Lifecycle Controller can be enabled thru Remote
     * Services or F2 iDRAC configuration. 4 (In Recovery) Lifecycle Controller is in Recovery mode. Refer to iDRAC users guide on instructions on how to repair Lifecycle
     * Controller. 5 (In Use) Lifecycle Controller is being currently used by another process.
     * -------------------------------------------------------------------------------------------------------------------------------
     * 
     *****************************************************************************************************************************/
    public static enum RSAPIStatusEnum {
        READY("Ready"), NOT_READY("Not Ready"), ERROR("Error");

        String statusNum;


        RSAPIStatusEnum(String value) {
            statusNum = value;
        }


        public String toString() {
            return statusNum;
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(GetRemoteServicesAPIStatus.class);
    private WSManageSession session = null;


    public GetRemoteServicesAPIStatus(String ipAddr, String userName, String passwd) {
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: GetRemoteServicesAPIStatus(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        session = this.getSession();
        logger.trace("Exiting constructor: GetRemoteServicesAPIStatus()");
    }


    @Override
    public RSAPIStatusEnum execute() {
        logger.trace("Entering GetRemoteServicesAPIStatus function: execute()");
        try {
            // Enumerate on DCIM_OSDeploymentService with EPR
            List<EnumReferenceParam> items = callEnumWithEPR();

            SelectorSetType settype = items.get(0).getSelectorSetTypes();
            session.getSelectors().addAll(settype.getSelector());
            session.setResourceUri(getResourceURI());

            session.setInvokeCommand(WSManMethodEnum.GET_REMOTE_SERVICES_API_STATUS.toString());
            Addressing response = session.sendInvokeRequest();

            String retValue = (String) findObjectInDocument(response.getBody(), "//pre:GetRemoteServicesAPIStatus_OUTPUT/pre:ReturnValue/text()", XPathConstants.STRING);
            logger.debug("Return Value is: " + retValue);

            String retStatus = (String) findObjectInDocument(response.getBody(), "//pre:GetRemoteServicesAPIStatus_OUTPUT/pre:ServerStatus/text()", XPathConstants.STRING);
            logger.debug("Server Status is: " + retStatus);

            retStatus = (String) findObjectInDocument(response.getBody(), "//pre:GetRemoteServicesAPIStatus_OUTPUT/pre:LCStatus/text()", XPathConstants.STRING);
            logger.debug("LC Status is: " + retStatus);

            retStatus = (String) findObjectInDocument(response.getBody(), "//pre:GetRemoteServicesAPIStatus_OUTPUT/pre:Status/text()", XPathConstants.STRING);
            logger.debug("Status is: " + retStatus);

            if (("0").equals(retValue)) {
                // check the status
                logger.trace("Exiting function: execute()");
                if ("0".equals(retStatus)) {
                    return RSAPIStatusEnum.READY;
                } else {
                    return RSAPIStatusEnum.NOT_READY;
                }
            } else {
                logger.debug("GetRemoteServicesAPIStatus Return Status is: " + retValue);
                return RSAPIStatusEnum.ERROR;
            }
        } catch (Exception e) {
            logger.error("Exception calling GetRemoteServicesAPIStatus.execute: " + e.getMessage() + " .  Returning RSStatusEnum.ERROR");
            return RSAPIStatusEnum.ERROR;
        }
    }


    private List<EnumReferenceParam> callEnumWithEPR() throws SOAPException, JAXBException, DatatypeConfigurationException, IOException, Exception {
        session.setResourceUri(getResourceURI());
        Enumeration responseEnum = this.sendRequestEnumeration(Mode.EnumerateEPR);
        List<EnumReferenceParam> items = session.extractRequestEnumerationRefParameters(responseEnum);
        return items;
    }


    private String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_LCService);
        return sb.toString();
    }


    private Object findObjectInDocument(SOAPElement doc, String xPathLocation, QName qname) throws XPathExpressionException {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        xpath.setNamespaceContext(new PersonalNamespaceContext("http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/root/dcim/DCIM_LCService"));
        XPathExpression expr = xpath.compile(xPathLocation);
        Object result = expr.evaluate(doc, qname);
        return result;
    }
}
