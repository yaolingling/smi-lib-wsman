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
 * @author Umer_Shabbir
 *
 */
public enum RaidWSManMethParmEnum {

    Target("Target"), DiskType("DiskType"), Diskprotocol("Diskprotocol"), DiskEncrypt("DiskEncrypt"), RAIDLevel("RAIDLevel"), VirtualDiskName("VirtualDiskName"), Initialize("Initialize"), FastInitialization("0"), RaidLevel("RaidLevel"), PDArray("PDArray"), VDPropNameArray("VDPropNameArray"), VDPropValueArray("VDPropValueArray"), VDPropNameArrayIn("VDPropNameArrayIn"), VDPropValueArrayIn("VDPropValueArrayIn"), VirtualDiskArray("VirtualDiskArray"), RebootJobType("RebootJobType"), CLEARALL("JID_CLEARALL"), ScheduledStartTime("ScheduledStartTime"), VDRaidEnumArray("VDRAIDEnumArray"), InstanceID("InstanceID"), SpanLength("SpanLength"), ReturnValue("ReturnValue"), RebootRequired("RebootRequired"), Success("0"), Failure("1"), LifeCycleURI("http://schemas.dell.com/wbem/wscim/1/cim-schema/2/DCIM_LifecycleJob"), DefaultController("RAID.Integrated.1-1"), Key("Key"), KeyId("Keyid"), JobID("JobID");

    private String displayString;


    private RaidWSManMethParmEnum(String displayString) {
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

        for (RaidWSManMethParmEnum raidParm : RaidWSManMethParmEnum.values()) {
            lov.put(raidParm.toString(), raidParm.getDisplayString());
        }

        return (lov);
    }

}
