/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
/**
 * 
 */
package com.dell.isg.smi.wsman.command.raidcmd;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.slf4j.LoggerFactory;

import com.dell.isg.smi.wsman.entity.KeyValuePair;
import com.sun.ws.management.addressing.Addressing;

/**
 * @author Anshu_Virk
 *
 */
public class RaidGetRaidLevelCmd extends AbstractRaid {

    private List<KeyValuePair> keyValue = new ArrayList<KeyValuePair>();
    private static final Logger logger = LoggerFactory.getLogger(RaidGetRaidLevelCmd.class);


    public RaidGetRaidLevelCmd(String ipAddr, String userName, String passwd) {
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: RaidGetRaidLevelCmd(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        logger.trace("Exiting constructor: RaidGetRaidLevelCmd()");
    }


    @Override
    public Object execute() throws Exception {
        logger.trace("Entering function: execute()");
        this.addSelectors();
        this.addUserParms();

        session.setInvokeCommand(RaidWSManMethodEnum.GetRAIDLevels.getDisplayString());
        Addressing response = session.sendInvokeRequest();
        Document document = response.getBody().extractContentAsDocument();
        Object result = parseResponse(document);
        logger.trace("Exiting function: execute()");
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
            } else if (keyValuePair.getKey().equalsIgnoreCase(RaidWSManMethParmEnum.DiskType.getDisplayString())) {
                keyVal = new KeyValuePair();
                keyVal.setKey(RaidWSManMethParmEnum.DiskType.getDisplayString());
                keyVal.setValue(keyValuePair.getValue());
                keyValue.add(keyVal);
            } else if (keyValuePair.getKey().equalsIgnoreCase(RaidWSManMethParmEnum.Diskprotocol.getDisplayString())) {
                keyVal = new KeyValuePair();
                keyVal.setKey(RaidWSManMethParmEnum.Diskprotocol.getDisplayString());
                keyVal.setValue(keyValuePair.getValue());
                keyValue.add(keyVal);
            } else if (keyValuePair.getKey().equalsIgnoreCase(RaidWSManMethParmEnum.PDArray.getDisplayString())) {
                keyVal = new KeyValuePair();
                keyVal.setKey(RaidWSManMethParmEnum.PDArray.getDisplayString());
                keyVal.setValue(keyValuePair.getValue());
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
