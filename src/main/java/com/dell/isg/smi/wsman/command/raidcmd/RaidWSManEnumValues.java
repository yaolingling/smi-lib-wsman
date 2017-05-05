/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.raidcmd;

import java.util.Hashtable;
import java.util.Map;

/**
 * 
 * @author Umer_Shabbir
 *
 */

public enum RaidWSManEnumValues {
    NoRAID("1"), RAID0("2"), RAID1("4"), RAID5("64"), RAID6("128"), RAID10("2048"), RAID50("8192"), RAID60("16384"), R1("NoRAID"), R2("0"), R4("1"), R64("5"), R128("6"), R2048("10"), R8192("50"), R16384("60"), DiskTypeAll("0"), DiskProtocolAll("0"), ScheduledStatTimeNow("TIME_NOW"), Reboot("1"), ControllerContains("integrated"), RaidIntegrated("raid.integrated");

    private String displayString;


    private RaidWSManEnumValues(String displayString) {
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

        for (RaidWSManEnumValues raidMethod : RaidWSManEnumValues.values()) {
            lov.put(raidMethod.toString(), raidMethod.getDisplayString());
        }

        return (lov);
    }
}
