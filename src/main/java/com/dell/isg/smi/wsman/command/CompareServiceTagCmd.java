/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
/**
 * 
 */
package com.dell.isg.smi.wsman.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dell.isg.smi.commons.elm.exception.RuntimeCoreException;
import com.dell.isg.smi.wsman.command.entity.CimString;
import com.dell.isg.smi.wsman.command.entity.DCIMSystemViewType;

/**
 * @author Rahman_Muhammad
 *
 */
public class CompareServiceTagCmd {
    private EnumerateSystemViewCmd cmd = null;
    private String serviceTag;
    private static final Logger logger = LoggerFactory.getLogger(CompareServiceTagCmd.class);


    /**
     * @param ipAddr
     * @param userName
     * @param passwd
     */
    public CompareServiceTagCmd(String ipAddr, String userName, String passwd, String serviceTag) {
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: CompareServiceTagCmd(String ipAddr - %s, String userName - %s, String passwd - %s,String serviceTag - %s)", ipAddr, userName, "####", serviceTag));
        }
        cmd = new EnumerateSystemViewCmd(ipAddr, userName, passwd);
        this.serviceTag = serviceTag;

        logger.trace("Exiting constructor: CompareServiceTagCmd()");
    }


    public Boolean execute() throws Exception {
        logger.trace("Entering function: execute()");

        // Paranoia is the only policy...
        if (cmd == null) {
            String msg = "Null EnumerateSystemViewCmd object - Unable to compare service tags.";
            logger.error(msg);
            throw new RuntimeCoreException(msg);
        }

        // Initialize result.
        Boolean result = null;

        // Exhibit more paranoia.
        DCIMSystemViewType systemView = cmd.execute();
        if (systemView != null) {
            CimString cim = systemView.getServiceTag();
            if (cim != null) {
                String value = cim.getValue();
                if (value != null)
                    result = value.equalsIgnoreCase(serviceTag);
            }
        }

        // See what happened.
        if (result == null) {
            logger.warn("Unable to compare service tags - using default FALSE result.");
            result = Boolean.FALSE;
        }

        logger.trace("Exiting function: execute()");

        return result;
    }
}
