/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

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
import com.dell.isg.smi.wsman.command.entity.SetAttributeOUTPUT;
import com.dell.isg.smi.wsman.utilities.ExceptionUtilities;
import com.sun.ws.management.addressing.Addressing;
import com.dell.isg.smi.commons.utilities.constants.CommonConstants;

public class UpdateBIOSAttributeCmd extends WSManBaseCommand {

    private static final Logger logger = LoggerFactory.getLogger(UpdateBIOSAttributeCmd.class);

    private boolean createConfigJob = false;

    private final static int MAX_RETRY = 5;

    private String attributeName = null;
    private String attributeValue = null;

    private WSManageSession session = null;


    public UpdateBIOSAttributeCmd(String ipAddr, String userName, String passwd, String attributeName, String attributeValue, boolean createConfigJob) {

        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: UpdateBIOSAttributeCmd(String ipAddr - %s, String userName - %s, String passwd - %s,  String attributeName - %s, String attributeValue - %s, boolean createConfigJob - %s)", ipAddr, userName, "####", attributeName, attributeValue, Boolean.toString(createConfigJob)));
        }
        session = super.getSession();

        this.attributeName = attributeName;
        this.attributeValue = attributeValue;
        this.createConfigJob = createConfigJob;

        this.session.setResourceUri(getResourceURI());

        this.addSelectors(getResourceURI());

        this.session.setInvokeCommand(WSManMethodEnum.SET_ATTRIBUTE.toString());

        this.session.addUserParam(WSManMethodParamEnum.TARGET.toString(), "BIOS.Setup.1-1");
        this.session.addUserParam(WSManMethodParamEnum.ATTRIBUTE_NAME.toString(), attributeName);
        this.session.addUserParam(WSManMethodParamEnum.ATTRIBUTE_VALUE.toString(), attributeValue);
        logger.trace("Exiting constructor: UpdateBIOSAttributeCmd()");
    }


    @Override
    public String execute() throws Exception {
        logger.trace("Entering function: execute()");
        logger.info("Entering execute() ");

        SetAttributeOUTPUT setAttributeOutput = null;

        for (int i = 0; i < MAX_RETRY; i++) {

            Addressing response = this.session.sendInvokeRequest();

            if (null != response) {

                setAttributeOutput = getObjectFromSoapBody(response.getBody());

                if (null != setAttributeOutput) {

                    // Only if it is a Successful return , which is 0 - get the Message and set it on the success string
                    if (StringUtils.equalsIgnoreCase(setAttributeOutput.getReturnValue().getValue() + "", WSCommandRNDConstant.SUCCESSFULL_UPDATE_JOB_RETURN)) {

                        String attrSucceededStr = setAttributeOutput.getMessage().getValue();

                        // Then create the config job if it is set to true.
                        if (createConfigJob) {
                            // Create the config job
                            createConfigJob();
                        }
                        logger.trace("Exiting function: execute()");
                        return attrSucceededStr;

                    } else {

                        // handlePendingConfigurationJob(setAttributeOutput.getMessage().getValue());
                        logger.info("Retrying UpdateBIOSAttribute command...");
                        Thread.sleep(CommonConstants.THIRTY_SEC);

                    }
                }
            }
        }

        if (setAttributeOutput != null)
            logger.error("Error while setting the attribute : " + setAttributeOutput.getMessage().getValue());

        // If it comes here - means the call was not successful. Throw the exception
        throw ExceptionUtilities.getCoreRuntimeException(267031, setAttributeOutput.getMessage().getValue());

    }


    private void createConfigJob() throws Exception {

        CreateConfigJobCmd cmd = new CreateConfigJobCmd(this.session.getIpAddress(), this.session.getUser(), this.session.getPassword(), true);
        // boolean jobCompleted = (Boolean)cmd.execute();

        boolean jobCompleted = false;
        ConfigJobDetail jobStatus = cmd.execute();
        if (jobStatus.getReturnCode() == ConfigJobDetail.ConfigJobReturnCode.JOB_COMPLETED) {
            jobCompleted = true;
        } else if (jobStatus.getReturnCode() == ConfigJobDetail.ConfigJobReturnCode.NO_JOB_PENDING) {
            jobCompleted = true;
            logger.debug("no job pending in UpdateBIOSAttributeCmd");
        }

        boolean isComplete = false;

        if (jobCompleted) {
            logger.info("Returning from jobStatusCheckCmd()" + jobCompleted);

            // If it comes back with true - check if the attributes are set.
            isComplete = checkIfAttributeSet();

            if (!isComplete) {
                for (int j = 0; j < MAX_RETRY; j++) {

                    Thread.sleep(CommonConstants.THIRTY_SEC);
                    isComplete = checkIfAttributeSet();

                    if (isComplete) {
                        break;
                    }
                }
            }
        } else {
            logger.error("Error completing the reboot job " + jobCompleted);
            throw ExceptionUtilities.getCoreRuntimeException(267030, jobCompleted + "");
        }

    }


    private SetAttributeOUTPUT getObjectFromSoapBody(SOAPBody soapBody) throws Exception {

        NodeList nodeList = soapBody.getChildNodes();
        // for(int j = 0 ; j < nodeList.getLength() ;j++){
        Node node = nodeList.item(0);

        return (SetAttributeOUTPUT) XmlHelper.xmlToObject(node, SetAttributeOUTPUT.class);
        // }

        // return null;
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


    private boolean checkIfAttributeSet() throws Exception {

        EnumerateBIOSAttributeCmd biosAttrCmd = new EnumerateBIOSAttributeCmd(this.session.getIpAddress(), this.session.getUser(), this.session.getPassword(), this.attributeName);
        List<DCIMBIOSEnumerationType> biosEnumTypeList = biosAttrCmd.execute();

        if (biosEnumTypeList != null && biosEnumTypeList.size() > 0) {

            DCIMBIOSEnumerationType biosAttribute = biosEnumTypeList.get(0);
            List<CimString> pendingValueList = biosAttribute.getPendingValue();
            if (pendingValueList != null && pendingValueList.size() > 0) {
                CimString pendingValue = (CimString) pendingValueList.get(0);

                if (null == pendingValue) {
                    return true;
                } else {
                    String pendingValueStr = pendingValue.getValue();
                    if (StringUtils.isEmpty(pendingValueStr) || StringUtils.isBlank(pendingValueStr)) {
                        return true;
                    } else {
                        if (biosAttribute.getAttributeName() != null && biosAttribute.getAttributeName().getValue() != null) {
                            logger.error("Attribute not set for: " + biosAttribute.getAttributeName().getValue() + " to value: " + pendingValueStr);
                        }
                    }

                }

            }

        }

        return false;
    }


    private String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);

        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_BIOSService);

        return sb.toString();
    }

}
