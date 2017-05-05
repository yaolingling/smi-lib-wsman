/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.raidcmd;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dell.isg.smi.wsman.entity.KeyValuePair;
import com.sun.ws.management.addressing.Addressing;

/**
 * @author Umer_Shabbir
 * @author Anshu Virk
 */

public class RaidCreateTargetedConfigJobCmd extends AbstractRaid {

    private List<KeyValuePair> keyValue = new ArrayList<KeyValuePair>();
    private static final Logger logger = LoggerFactory.getLogger(RaidCreateTargetedConfigJobCmd.class);


    public RaidCreateTargetedConfigJobCmd(String ipAddr, String userName, String passwd) {
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: RaidCreateTargetedConfigJobCmd(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        logger.trace("Exiting constructor: RaidCreateTargetedConfigJobCmd()");

    }


    /**
     * This method runs the actual command.
     */
    @Override
    public Object execute() throws Exception {
        logger.trace("Entering function: execute()");
        this.addSelectors();
        this.addUserParms();

        session.setInvokeCommand(RaidWSManMethodEnum.CreateTargetedConfigJob.getDisplayString());
        Addressing response = session.sendInvokeRequest();
        Object result = parseResponseByXPath(response, RaidWSManMethodEnum.CreateTargetedConfigJob.getDisplayString());
        logger.trace("Entering function: execute()");
        return result;
    }


    public void setKeyValues(List<KeyValuePair> keyValues) {
        KeyValuePair keyVal = null;
        for (KeyValuePair keyValuePair : keyValues) {
            if (keyValuePair.getKey().equalsIgnoreCase(RaidWSManMethParmEnum.Target.getDisplayString())) {
                keyVal = new KeyValuePair();
                keyVal.setKey(RaidWSManMethParmEnum.Target.getDisplayString());
                keyVal.setValue(keyValuePair.getValue());
                keyValue.add(keyVal);
            } else if (keyValuePair.getKey().equalsIgnoreCase(RaidWSManMethParmEnum.RebootJobType.getDisplayString())) {
                keyVal = new KeyValuePair();
                keyVal.setKey(RaidWSManMethParmEnum.RebootJobType.getDisplayString());
                keyVal.setValue("1");
                keyValue.add(keyVal);
            } else if (keyValuePair.getKey().equalsIgnoreCase(RaidWSManMethParmEnum.ScheduledStartTime.getDisplayString())) {
                keyVal = new KeyValuePair();
                keyVal.setKey(RaidWSManMethParmEnum.ScheduledStartTime.getDisplayString());
                keyVal.setValue("TIME_NOW");
                keyValue.add(keyVal);
            }
        }

    }


    protected void addUserParms() {
        if (keyValue != null && keyValue.size() > 0) {
            for (KeyValuePair pair : keyValue) {
                session.addUserParam(pair);
            }
        }
    }
}
