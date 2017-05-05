/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.entity.update;

import java.io.Serializable;

public class UpdateDevice implements Serializable {

    private static final long serialVersionUID = 1L;

    String deviceID = "N/A";


    public String getDeviceID() {
        return deviceID;
    }


    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
        if (this.deviceID.equals("?")) {
            this.deviceID = "N/A";
        }
    }

    String deviceDescription = "N/A";


    public String getDeviceDescription() {
        return deviceDescription;
    }


    public void setDeviceDescription(String deviceDescription) {
        this.deviceDescription = deviceDescription;
    }

    UpdateDevicePCIInfo pciDeviceInfo;


    public UpdateDevicePCIInfo getPciDeviceInfo() {
        return pciDeviceInfo;
    }


    public void setPciDeviceInfo(UpdateDevicePCIInfo pciDeviceInfo) {
        this.pciDeviceInfo = pciDeviceInfo;

    }

}
