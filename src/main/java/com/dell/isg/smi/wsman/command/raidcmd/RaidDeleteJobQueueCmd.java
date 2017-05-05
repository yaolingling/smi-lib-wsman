/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
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
 * @author Anshu Virk
 */

public class RaidDeleteJobQueueCmd extends AbstractRaid {

    private List<KeyValuePair> keyValue = new ArrayList<KeyValuePair>();
    private static final Logger logger = LoggerFactory.getLogger(RaidDeleteJobQueueCmd.class);


    public RaidDeleteJobQueueCmd(String ipAddr, String userName, String passwd) {
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: RaidDeleteJobQueueCmd(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }
        logger.trace("Exiting constructor: RaidDeleteJobQueueCmd()");

    }


    /**
     * This method runs the actual command.
     */
    @Override
    public Object execute() throws Exception {
        logger.trace("Entering function: execute()");
        this.addSelectors();
        this.addUserParms();
        session.setInvokeCommand(RaidWSManMethodEnum.DeleteJobQueue.getDisplayString());
        Addressing response = session.sendInvokeRequest();
        Document document = response.getBody().extractContentAsDocument();
        Object result = parseResponse(document);
        logger.trace("Exiting function: execute()");
        return result;
    }


    protected String getResourceURI() {
        String resourceURI = getBaseURI() + "DCIM_JobService";
        return resourceURI;
    }


    protected void addSelectors() {
        session.setResourceUri(getResourceURI());
        session.addSelector(this.CREATION_CLASSNAME, WSManClassEnum.DCIM_JobService.toString());
        session.addSelector(this.SYSTEM_NAME, "Idrac");
        session.addSelector(this.NAME, "JobService");
        session.addSelector(this.SYSTEM_CREATION_CLASSNAME, WSManClassEnum.DCIM_ComputerSystem.toString());
    }


    public void setKeyValues(List<KeyValuePair> keyValues) {
        KeyValuePair keyVal = null;
        for (KeyValuePair keyValuePair : keyValues) {
            if (keyValuePair.getKey().equalsIgnoreCase(RaidWSManMethParmEnum.JobID.getDisplayString())) {
                keyVal = new KeyValuePair();
                keyVal.setKey(RaidWSManMethParmEnum.JobID.getDisplayString());
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
