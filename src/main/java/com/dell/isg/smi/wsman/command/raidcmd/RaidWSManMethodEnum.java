/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
/**
 * 
 */
package com.dell.isg.smi.wsman.command.raidcmd;

import java.util.Hashtable;
import java.util.Map;

/**
 * @author Anshu_Virk
 *
 */
public enum RaidWSManMethodEnum {

    ResetConfig("ResetConfig"), ClearForeignConfig("ClearForeignConfig"), CheckVDValues("CheckVDValues"), CreateConfigJob("CreateConfigJob"), GetAvailableDisks("GetAvailableDisks"), GetRAIDLevels("GetRAIDLevels"), GetDHSDisks("GetDHSDisks"), CreateVirtualDisk("CreateVirtualDisk"), CreateTargetedConfigJob("CreateTargetedConfigJob"), DeleteVirtualDisk("DeleteVirtualDisk"), DeletePendingConfiguration("DeletePendingConfiguration"), LockVirtualDisk("LockVirtualDisk"), SetControllerKey("SetControllerKey"), SetAttribute("SetAttribute"), SetAttributes("SetAttributes"), RemoveControllerKey("RemoveControllerKey"), EnableControllerEncryption("EnableControllerEncryption"), ReKey("ReKey"), AssignSpare("AssignSpare"), JobStatusRaidCheckCmd("JobStatusRaidCheckCmd"), DeleteJobQueue("DeleteJobQueue"), CreateRebootJob("CreateRebootJob"), ConvertToRAID("ConvertToRAID"), ConvertToNonRAID("ConvertToNonRAID");

    private String displayString;


    private RaidWSManMethodEnum(String displayString) {
        this.displayString = displayString;
    }


    public String getDisplayString() {
        return displayString;
    }


    public String getValue() {
        return (this.toString());
    }


    public static Map<String, String> getLOV() {
        Map<String, String> lov = new Hashtable<String, String>();

        for (RaidWSManMethodEnum raidMethod : RaidWSManMethodEnum.values()) {
            lov.put(raidMethod.toString(), raidMethod.getDisplayString());
        }

        return (lov);
    }

}
