/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.raidcmd;

import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;

import com.sun.ws.management.addressing.Addressing;

import org.slf4j.LoggerFactory;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.entity.KeyValuePair;

/**
 * @author Umer_Shabbir
 */

public class CreateRebootJobCmd extends AbstractRaid {

    private List<KeyValuePair> keyValue = new ArrayList<KeyValuePair>();
    private static final Logger logger = LoggerFactory.getLogger(CreateRebootJobCmd.class);


    public CreateRebootJobCmd(String ipAddr, String userName, String passwd) {
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: CreateRebootJobCmd(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        logger.trace("Exiting constructor: CreateRebootJobCmd()");
    }


    @Override
    public Object execute() throws Exception {
        logger.trace("Entering function: execute()");
        this.addSelectors();
        this.addUserParms();

        session.setInvokeCommand(RaidWSManMethodEnum.CreateRebootJob.getDisplayString());
        Addressing response = session.sendInvokeRequest();
        Object result = parseResponseByXPath(response, RaidWSManMethodEnum.CreateRebootJob.getDisplayString());
        logger.trace("Exiting function: execute()");
        return result;
    }


    protected void addSelectors() {
        session.setResourceUri(getResourceURI());
        session.addSelector(this.CREATION_CLASSNAME, WSManClassEnum.DCIM_SoftwareInstallationService.toString());
        session.addSelector(this.SYSTEM_CREATION_CLASSNAME, WSManClassEnum.DCIM_ComputerSystem.toString());
        session.addSelector(this.SYSTEM_NAME, "IDRAC:ID");
        session.addSelector(this.NAME, "SoftwareUpdate");
    }


    protected String getResourceURI() {
        String resourceURI = getBaseURI() + WSCommandRNDConstant.SOFTWARE_INSTALLATION_SERVICE_URI;
        return resourceURI;
    }


    protected void addUserParms() {
        if (keyValue != null && keyValue.size() > 0) {
            for (KeyValuePair pair : keyValue) {
                session.addUserParam(pair);
            }
        }
    }


    public void setKeyValues(List<KeyValuePair> keyValues) {
        KeyValuePair keyVal = null;
        for (KeyValuePair keyValuePair : keyValues) {
            if (keyValuePair.getKey().equalsIgnoreCase(RaidWSManMethParmEnum.RebootJobType.getDisplayString())) {
                keyVal = new KeyValuePair();
                keyVal.setKey(keyValuePair.getKey());
                keyVal.setValue(keyValuePair.getValue());
                keyValue.add(keyVal);
            }
        }
    }
}