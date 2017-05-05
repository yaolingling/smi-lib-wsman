/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.entity;

import java.io.Serializable;

public class VFlashView implements Serializable {

    private static final long serialVersionUID = 1L;
    private String componentName = null;
    private String deviceDescription = null;
    private String FQDD = null;
    private String instanceID = null;
    private String availableSize = null;
    private String capacity = null;
    private String healthStatus = null;
    private String initializedState = null;
    private String licensed = null;
    private String vFlashEnabledState = null;
    private String writeProtected = null;
    private String lastSystemInventoryTime = null;
    private String lastUpdateTime = null;


    public String getComponentName() {
        return componentName;
    }


    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }


    public String getDeviceDescription() {
        return deviceDescription;
    }


    public void setDeviceDescription(String deviceDescription) {
        this.deviceDescription = deviceDescription;
    }


    public String getFQDD() {
        return FQDD;
    }


    public void setFQDD(String fQDD) {
        FQDD = fQDD;
    }


    public String getInstanceID() {
        return instanceID;
    }


    public void setInstanceID(String instanceID) {
        this.instanceID = instanceID;
    }


    public String getAvailableSize() {
        return availableSize;
    }


    public void setAvailableSize(String availableSize) {
        this.availableSize = availableSize;
    }


    public String getCapacity() {
        return capacity;
    }


    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }


    public String getHealthStatus() {
        return healthStatus;
    }


    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }


    public String getInitializedState() {
        return initializedState;
    }


    public void setInitializedState(String initializedState) {
        this.initializedState = initializedState;
    }


    public String getLicensed() {
        return licensed;
    }


    public void setLicensed(String licensed) {
        this.licensed = licensed;
    }


    public String getvFlashEnabledState() {
        return vFlashEnabledState;
    }


    public void setvFlashEnabledState(String vFlashEnabledState) {
        this.vFlashEnabledState = vFlashEnabledState;
    }


    public String getWriteProtected() {
        return writeProtected;
    }


    public void setWriteProtected(String writeProtected) {
        this.writeProtected = writeProtected;
    }


    public String getLastSystemInventoryTime() {
        return lastSystemInventoryTime;
    }


    public void setLastSystemInventoryTime(String lastSystemInventoryTime) {
        this.lastSystemInventoryTime = lastSystemInventoryTime;
    }


    public String getLastUpdateTime() {
        return lastUpdateTime;
    }


    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

}