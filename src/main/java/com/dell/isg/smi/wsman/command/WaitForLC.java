/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dell.isg.smi.wsman.model.LCStatus;
import com.dell.isg.smi.wsmanclient.IWSManCompoundCommand;

/**
 * The {@code SetEventsCmd} compound command sets an SNMP alert destination for the iDRAC.
 */
public class WaitForLC implements IWSManCompoundCommand<WaitForLC.WaitForLCResponse> {
    private static final Logger logger = LoggerFactory.getLogger(WaitForLC.class);

    public enum WaitForLCResponse {
        LC_AVAILABLE, LC_NOTAVAILABLE, UNKNOWN;
    }

    private int NumberOfRetries = 10;
    private int WaitTimeBetweenRetriesInMilliSeconds = 60000; // in milliseconds.


    @Override
    public WaitForLCResponse execute(com.dell.isg.smi.wsmanclient.IWSManClient client) throws IOException, com.dell.isg.smi.wsmanclient.WSManException {
        WaitForLCResponse returnValue = WaitForLCResponse.UNKNOWN;
        for (int i = 0; i < NumberOfRetries; i++) {

            try {
                logger.info("Checking if LC is available for host " + client.getIpAddress());
                LCStatus lcStatus = client.execute(new InvokeGetLCStatusCmd());
                if (lcStatus.isLCAvailable()) {
                    returnValue = WaitForLCResponse.LC_AVAILABLE;
                    logger.info("LC is available for host " + client.getIpAddress());
                    break;
                } else {
                    returnValue = WaitForLCResponse.LC_NOTAVAILABLE;
                    logger.info(" LC is not available for host " + client.getIpAddress() + ":" + lcStatus.toString());
                }
                Thread.sleep(WaitTimeBetweenRetriesInMilliSeconds);
            } catch (Exception e) {
                logger.warn("InvokeGetLCStatusCmd threw an exception: " + e.getMessage());
                returnValue = WaitForLCResponse.UNKNOWN;
            }
        }
        return returnValue;
    }

}
