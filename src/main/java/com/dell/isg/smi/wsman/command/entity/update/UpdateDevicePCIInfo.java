/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.entity.update;

import java.io.Serializable;

public class UpdateDevicePCIInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    String vendorID = "";


    public String getVendorID() {
        return vendorID;
    }


    public void setVendorID(String vendorID) {
        if (!vendorID.equals("?")) {
            this.vendorID = vendorID;
        }
    }


    public String getSubVendorID() {
        return subVendorID;
    }


    public void setSubVendorID(String subVendorID) {
        if (!subVendorID.equals("?")) {
            this.subVendorID = subVendorID;
        }
    }


    public String getDeviceID() {
        return deviceID;
    }


    public void setDeviceID(String deviceID) {
        if (!deviceID.equals("?")) {
            this.deviceID = deviceID;
        }

    }


    public String getSubDeviceID() {
        return subDeviceID;
    }


    public void setSubDeviceID(String subDeviceID) {
        if (!subDeviceID.equals("?")) {
            this.subDeviceID = subDeviceID;
        }
    }

    String subVendorID = "";
    String deviceID = "";
    String subDeviceID = "";
}
