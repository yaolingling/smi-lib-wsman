/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.entity;

import java.io.Serializable;

public class ControllerBatteryView implements Serializable {

    private static final long serialVersionUID = 1L;

    private String instanceId;
    private String fqdd;
    private String primaryStatus;
    private String deviceDescription;
    private String raidState;


    public String getInstanceId() {
        return instanceId;
    }


    public void setInstanceIdd(String instanceId) {
        this.instanceId = instanceId;
    }


    public String getFqdd() {
        return fqdd;
    }


    public void setFqdd(String fqdd) {
        this.fqdd = fqdd;
    }


    public String getPrimaryStatus() {
        return primaryStatus;
    }


    public void setPrimaryStatus(String primaryStatus) {
        this.primaryStatus = primaryStatus;
    }


    public String getDeviceDescription() {
        return deviceDescription;
    }


    public void setDeviceDescription(String deviceDescription) {
        this.deviceDescription = deviceDescription;
    }


    public String getRaidState() {
        return raidState;
    }


    public void setRaidState(String raidState) {
        this.raidState = raidState;
    }

}
