/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
/**
 * 
 */
package com.dell.isg.smi.wsman.command.raidcmd.factory;

import com.dell.isg.smi.wsman.command.raidcmd.AbstractRaid;

/**
 * @author Anshu_Virk
 *
 */
public abstract class RaidFactory {

    /**
     * 
     */
    public RaidFactory() {
        // TODO Auto-generated constructor stub
    }


    public abstract AbstractRaid getRaidView(String view);

}
