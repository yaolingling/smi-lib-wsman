/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
/**
 *
 */
package com.dell.isg.smi.wsman.command;

import static com.dell.isg.smi.commons.utilities.constants.CommonConstants.THIRTY_SEC;
import static com.dell.isg.smi.commons.utilities.constants.CommonConstants.TWENTY_SEC;

import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.soap.SOAPException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.command.entity.InstanceCSIR;
import com.sun.ws.management.addressing.Addressing;

/**
 * @author anthony_crouch
 *
 */
public class ChangeCSIRStatusCmd extends WSManBaseCommand {

    private final static Logger logger = LoggerFactory.getLogger(ChangeCSIRStatusCmd.class);
    private final static int MAX_RETRY = 5;

    private CSIREnum updateValue;
    private WSManageSession session = null;


    /**
     * Default Constructor
     *
     * @param ipAddr
     * @param userName
     * @param passwd
     * @param updateValue
     */
    public ChangeCSIRStatusCmd(String ipAddr, String userName, String passwd, CSIREnum updateValue) {
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: ChangeCSIRStatusCmd(String ipAddr - %s, String userName - %s, String passwd - %s, CSIREnum updateValue - %s)", ipAddr, userName, "####", CSIREnum.class.getName()));
        }
        session = super.getSession();
        this.updateValue = updateValue;

        logger.trace("Exiting constructor: ChangeCSIRStatusCmd()");

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


    /**
     * -1 failed, needed to change but could not do. 0 No change was made or needed 1 Made change to enabled, must be set back
     */
    @Override
    public Integer execute() throws Exception {
        logger.trace("Entering function: execute()");
        EnumerateCSIRCmd enumerator = new EnumerateCSIRCmd(session.getIpAddress(), session.getUser(), session.getPassword());
        InstanceCSIR instanceCSIR = enumerator.execute();

        int outcome;

        if (instanceCSIR.currentValue != null && !instanceCSIR.currentValue.equalsIgnoreCase(updateValue.toString())) {
            outcome = invokeCSIR(instanceCSIR);

            logger.trace("Exiting function: execute()");
            if (outcome == 0) {
                return 1;
            }
            if (outcome > 0)
                return -1;
        }

        logger.trace("Exiting function: execute()");
        return 0;
    }


    /**
     * Actual update call
     *
     * @return Addressing object
     *
     * @throws IllegalArgumentException
     * @throws SOAPException
     * @throws JAXBException
     * @throws DatatypeConfigurationException
     * @throws IOException
     * @throws XPathExpressionException
     */
    private Addressing invoke(InstanceCSIR instanceCSIR) throws IllegalArgumentException, SOAPException, JAXBException, DatatypeConfigurationException, IOException, XPathExpressionException {
        logger.info("Entering execute() ");
        this.session.setResourceUri(getInvokeResourceURI());
        this.addSelectors(getInvokeResourceURI());
        this.session.setInvokeCommand(WSManMethodEnum.SET_ATTRIBUTE.toString());
        // this.session.addUserParam(WSManMethodParamEnum.TARGET.toString(), "BIOS.Setup.1-1");
        this.session.addUserParam(WSManMethodParamEnum.ATTRIBUTE_NAME.toString(), instanceCSIR.elementName);
        this.session.addUserParam(WSManMethodParamEnum.ATTRIBUTE_VALUE.toString(), this.updateValue.toString());
        Addressing response = this.session.sendInvokeRequest();

        return response;
    }


    /**
     * Update the parameters, try several times.
     * 
     * @return String
     * @throws Exception
     */
    private int invokeCSIR(InstanceCSIR instanceCSIR) throws Exception {
        /* Default to error Occured */
        int setAttributeOutput = 2;

        for (int i = 0; i < MAX_RETRY; i++) {
            Addressing response = null;

            if (null != (response = invoke(instanceCSIR))) {
                // setAttributeOutput = getObjectFromSoapBody(response.getBody());
                String rVal = (String) findObjectInDocument(response.getBody(), "//pre:SetAttribute_OUTPUT/pre:ReturnValue/text()", getInvokeResourceURI(), XPathConstants.STRING);
                setAttributeOutput = Integer.valueOf(rVal);

                // Only if it is a Successful return , which is 0 - get the Message and set it on the success string
                if (setAttributeOutput == 0) {

                    // Create the config job
                    LCServiceCreateConfigJobCmd cmd = new LCServiceCreateConfigJobCmd(this.session.getIpAddress(), this.session.getUser(), this.session.getPassword());
                    boolean isSuccessReboot = (Boolean) cmd.execute();

                    // See if changes were set correctly.
                    if (isSuccessReboot)
                        return makeSureChangesSet();

                } else {
                    logger.info("Retrying ChangeCSIRStatus command...");
                    Thread.sleep(THIRTY_SEC);
                }
            }
        }

        logger.error("Error while setting the attribute ");
        return setAttributeOutput;
    }


    /**
     * Method that retries checking that the attributes were set.
     *
     * @throws Exception
     */
    private int makeSureChangesSet() throws Exception {
        int isComplete = 2;
        isComplete = checkIfAttributeSet();

        if (isComplete == 2) {
            for (int j = 0; j < 3; j++) {

                Thread.sleep(TWENTY_SEC);
                isComplete = checkIfAttributeSet();

                if (isComplete == 0) {
                    break;
                }
            }
        }
        return isComplete;
    }


    /**
     * Check and make sure the change went through.
     *
     * @return true if successful
     * @throws Exception
     */
    private int checkIfAttributeSet() throws Exception {

        EnumerateCSIRCmd enumerator = new EnumerateCSIRCmd(session.getIpAddress(), session.getUser(), session.getPassword());
        InstanceCSIR instanceCSIR = enumerator.execute();

        if (instanceCSIR.currentValue != null && instanceCSIR.currentValue.equalsIgnoreCase(updateValue.toString())) {
            return 0;
        }

        return 2;
    }

    public static enum CSIREnum {
        Enabled, Disabled
    }

}
