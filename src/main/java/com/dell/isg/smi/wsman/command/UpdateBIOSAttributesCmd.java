/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import java.util.ArrayList;
import java.util.List;

import javax.xml.soap.SOAPBody;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.commons.utilities.xml.XmlHelper;
import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.command.entity.CimString;
import com.dell.isg.smi.wsman.command.entity.ConfigJobDetail;
import com.dell.isg.smi.wsman.command.entity.DCIMBIOSEnumerationType;
import com.dell.isg.smi.wsman.command.entity.SetAttributesOUTPUT;
import com.dell.isg.smi.wsman.utilities.ExceptionUtilities;
import com.sun.ws.management.addressing.Addressing;
import com.dell.isg.smi.commons.utilities.constants.CommonConstants;

public class UpdateBIOSAttributesCmd extends WSManBaseCommand {

    private final static Logger logger = LoggerFactory.getLogger(UpdateBIOSAttributesCmd.class);

    private boolean createConfigJob = false;

    private final static int MAX_RETRY = 5;
    private WSManageSession session = null;

    private List<String> attributeNames = new ArrayList<String>();


    public UpdateBIOSAttributesCmd(String ipAddr, String userName, String passwd, List<String> attributeNames, List<String> attributeValues, boolean isCreateConfigJob) {

        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: UpdateBIOSAttributesCmd(String ipAddr - %s, String userName - %s, String passwd - %s, List<String> attributeNames - %s, List<String> attributeValues - %s, boolean isCreateConfigJob - %s)", ipAddr, userName, "####", attributeNames.toString(), attributeValues.toString(), Boolean.toString(isCreateConfigJob)));
        }
        session = this.getSession();

        this.createConfigJob = isCreateConfigJob;
        this.attributeNames = attributeNames;

        this.session.setResourceUri(getResourceURI());

        this.addSelectors(getResourceURI());

        this.session.setInvokeCommand(WSManMethodEnum.SET_ATTRIBUTES.toString());
        this.session.addUserParam(WSManMethodParamEnum.TARGET.toString(), "BIOS.Setup.1-1");

        for (String attrName : attributeNames) {
            this.session.addUserParam("AttributeName", attrName);
        }

        for (String attrValue : attributeValues) {
            this.session.addUserParam("AttributeValue", attrValue);
        }
        logger.trace("Exiting constructor: UpdateBIOSAttributesCmd()");

    }


    private void handlePendingConfigurationJob(String messageFromConfigJob) {

        // Configuration job already created, cannot set attribute on specified target until existing job is completed or is cancelled
        if (StringUtils.contains(messageFromConfigJob, "existing job is completed or is cancelled")) {

            DeleteJobCmd deleteCmd = new DeleteJobCmd(this.session.getIpAddress(), this.session.getUser(), this.session.getPassword(), "JID_CLEARALL");

            try {
                int deleteCmdReturn = deleteCmd.execute().intValue();

                // //Wait for 5 minutes after the job is deleted
                // if(deleteCmdReturn == 0){
                // Thread.sleep(SLEEP_IN_MILLIS*5);
                // }
            } catch (Exception e) {
                logger.info(e.getMessage());
            }
        }
    }


    private String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);

        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_BIOSService);

        return sb.toString();
    }


    private SetAttributesOUTPUT getObjectFromSoapBody(SOAPBody soapBody) throws Exception {

        NodeList nodeList = soapBody.getChildNodes();
        for (int j = 0; j < nodeList.getLength(); j++) {
            Node node = nodeList.item(j);
            return (SetAttributesOUTPUT) XmlHelper.xmlToObject(node, SetAttributesOUTPUT.class);
        }

        return null;
    }


    @Override
    public String execute() throws Exception {
        logger.trace("Entering function: execute()");
        logger.info("Entering execute() ");

        StringBuilder exceptionString = null;
        String attrSucceededStr = null;

        for (int i = 0; i < MAX_RETRY; i++) {

            exceptionString = new StringBuilder();

            Addressing response = this.session.sendInvokeRequest();

            if (null != response) {

                SetAttributesOUTPUT setAttributesOutput = getObjectFromSoapBody(response.getBody());

                if (null != setAttributesOutput) {
                    // Only if it is a Successful return , which is 0 - get the Message and set it on the success string
                    if (StringUtils.equalsIgnoreCase(setAttributesOutput.getReturnValue().getValue() + "", WSCommandRNDConstant.SUCCESSFULL_UPDATE_JOB_RETURN)) {
                        CimString message = setAttributesOutput.getMessage().get(0);
                        attrSucceededStr = message.getValue();
                    } else {

                        // check if there are more than one message and if atleast one of them is completed successfully.
                        if (setAttributesOutput.getMessage() != null && setAttributesOutput.getMessage().size() > 0) {
                            for (CimString cimString : setAttributesOutput.getMessage()) {
                                String message = cimString.getValue();
                                // If the command is successful, the string will be "The command was successful"
                                if (null == attrSucceededStr && StringUtils.contains(message, "success")) {
                                    attrSucceededStr = message;
                                } else if (!StringUtils.contains(message, "success")) {
                                    exceptionString.append(message + "\n");
                                }
                            }
                        }
                    }

                    // if there is atleast one successfull command - we don't have to worry about the error - saying "System Services currently in use"
                    if (StringUtils.isNotBlank(attrSucceededStr) && StringUtils.isNotEmpty(attrSucceededStr)) {
                        if (exceptionString.length() > 0) { // Some failures occurred. Log them.
                            logger.debug("Not all attribute updates were successfully scheduled: " + exceptionString.toString());
                        }
                        break;
                    } else {
                        if (exceptionString.length() > 0) {
                            logger.error("Exception while updating the attributes : \n" + exceptionString);
                            throw ExceptionUtilities.getCoreRuntimeException(267033, exceptionString.toString(), this.getLCErrorCode(), this.getLCErrorStr());

                        }
                        // handlePendingConfigurationJob(exceptionString.toString());
                        logger.info("Retrying UpdateBIOSAttributes command...");
                        Thread.sleep(CommonConstants.THIRTY_SEC);
                    }
                }
            }
        }

        // Create the job and keep checking for the job to complete
        if (null != attrSucceededStr) {
            if (createConfigJob) {
                createConfigJob(exceptionString);
            }
        } else {
            logger.error("Exception while updating the attributes : \n" + exceptionString);
            throw ExceptionUtilities.getCoreRuntimeException(267033, exceptionString.toString(), this.getLCErrorCode(), this.getLCErrorStr());
        }

        // if the exception is not blank - throw the exception.
        if (StringUtils.isNotEmpty(exceptionString.toString()) && StringUtils.isNotBlank(exceptionString.toString())) {

            logger.error("Exception while updating attributes: " + exceptionString);
            throw ExceptionUtilities.getCoreRuntimeException(267033, exceptionString.toString(), this.getLCErrorCode(), this.getLCErrorStr());

        }

        logger.info("Returning from execute() " + attrSucceededStr.toString());

        logger.trace("Exiting function: execute()");
        return attrSucceededStr.toString();
    }


    private void createConfigJob(StringBuilder exceptionString) throws Exception {

        // Create the config job
        CreateConfigJobCmd cmd = new CreateConfigJobCmd(this.session.getIpAddress(), this.session.getUser(), this.session.getPassword(), true);

        // boolean jobCompleted = (Boolean)cmd.execute();

        boolean jobCompleted = false;
        ConfigJobDetail jobStatus = cmd.execute();
        if (jobStatus.getReturnCode() == ConfigJobDetail.ConfigJobReturnCode.JOB_COMPLETED) {
            jobCompleted = true;
        } else if (jobStatus.getReturnCode() == ConfigJobDetail.ConfigJobReturnCode.NO_JOB_PENDING) {
            logger.debug("no job pending in UpdateBIOSAttributesCmd");
            jobCompleted = true;
        }

        if (jobCompleted) {

            logger.info("Returning from jobStatusCheckCmd()" + jobCompleted);

            // Check if all the attributes have been set properly and then exit.
            boolean complete = checkIfAttributesSet();

            if (!complete) {
                for (int j = 0; j < MAX_RETRY; j++) {
                    Thread.sleep(CommonConstants.THIRTY_SEC);
                    complete = checkIfAttributesSet();

                    if (complete) {
                        break;
                    }
                }

                if (!complete) {
                    exceptionString.append("Setting the attributes failed ");
                }
            }

        } else {
            exceptionString.append("Exception while restarting the server " + jobCompleted);
        }

    }


    private boolean checkIfAttributesSet() throws Exception {

        EnumerateBIOSAttributeCmd biosAttrCmd = new EnumerateBIOSAttributeCmd(this.session.getIpAddress(), this.session.getUser(), this.session.getPassword(), null);
        List<DCIMBIOSEnumerationType> biosEnumTypeList = biosAttrCmd.execute();

        boolean bReturn = true;
        if (biosEnumTypeList != null && biosEnumTypeList.size() > 0) {

            for (DCIMBIOSEnumerationType biosAttribute : biosEnumTypeList) {
                CimString attributeName = biosAttribute.getAttributeName();
                if (attributeNames.contains(attributeName.getValue())) {
                    List<CimString> pendingValueList = biosAttribute.getPendingValue();

                    if (pendingValueList != null && pendingValueList.size() > 0) {

                        CimString pendingValue = (CimString) pendingValueList.get(0);

                        if (null != pendingValue) {

                            String pendingValueStr = pendingValue.getValue();
                            if (StringUtils.isNotBlank(pendingValueStr) || StringUtils.isNotEmpty(pendingValueStr)) {
                                logger.error("Attribute not set for: " + attributeName.getValue() + " to value: " + pendingValueStr);
                                bReturn = false;
                            }
                        }
                    }
                }
            }
        }

        return bReturn;
    }

}
