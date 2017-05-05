/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
/**
 * 
 */
package com.dell.isg.smi.wsman.command.idraccmd;

import com.dell.isg.smi.wsman.WSManBaseCommand;

/**
 * @author Muhammad_R
 *
 */
public abstract class AbstractIdrac extends WSManBaseCommand {

    /**
     * @param ipAddr
     * @param userName
     * @param passwd
     */
    protected AbstractIdrac(String ipAddr, String userName, String passwd) {
        super(ipAddr, userName, passwd);
        // TODO Auto-generated constructor stub
    }

}
