/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.entity;

import java.io.Serializable;

public class FanView implements Serializable {

    private static final long serialVersionUID = 1L;

    private String activeCooling = null;
    private String baseUnits = null;
    private String currentReading = null;
    private String deviceDescription = null;
    private String FQDD = null;
    private String instanceID = null;
    private String lastSystemInventoryTime = null;
    private String lastUpdateTime = null;
    private String pwm = null;
    private String primaryStatus = null;
    private String rateUnits = null;
    private String redundancyStatus = null;
    private String unitModifier = null;
    private String variableSpeed = null;
    private String lowerThresholdCritical;
    private String lowerThresholdNonCritical;
    private String upperThresholdCritical;
    private String upperThresholdNonCritical;


    public String getActiveCooling() {
        return activeCooling;
    }


    public void setActiveCooling(String activeCooling) {
        this.activeCooling = activeCooling;
    }


    public String getBaseUnits() {
        return baseUnits;
    }


    public void setBaseUnits(String baseUnits) {
        this.baseUnits = baseUnits;
    }


    public String getCurrentReading() {
        return currentReading;
    }


    public void setCurrentReading(String currentReading) {
        this.currentReading = currentReading;
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


    public String getPwm() {
        return pwm;
    }


    public void setPwm(String pWM) {
        this.pwm = pWM;
    }


    public String getPrimaryStatus() {
        return primaryStatus;
    }


    public void setPrimaryStatus(String primaryStatus) {
        this.primaryStatus = primaryStatus;
    }


    public String getRateUnits() {
        return rateUnits;
    }


    public void setRateUnits(String rateUnits) {
        this.rateUnits = rateUnits;
    }


    public String getRedundancyStatus() {
        return redundancyStatus;
    }


    public void setRedundancyStatus(String redundancyStatus) {
        this.redundancyStatus = redundancyStatus;
    }


    public String getUnitModifier() {
        return unitModifier;
    }


    public void setUnitModifier(String unitModifier) {
        this.unitModifier = unitModifier;
    }


    public String getVariableSpeed() {
        return variableSpeed;
    }


    public void setVariableSpeed(String variableSpeed) {
        this.variableSpeed = variableSpeed;
    }


    public static long getSerialversionuid() {
        return serialVersionUID;
    }


    public String getLowerThresholdCritical() {
        return lowerThresholdCritical;
    }


    public void setLowerThresholdCritical(String lowerThresholdCritical) {
        this.lowerThresholdCritical = lowerThresholdCritical;
    }


    public String getLowerThresholdNonCritical() {
        return lowerThresholdNonCritical;
    }


    public void setLowerThresholdNonCritical(String lowerThresholdNonCritical) {
        this.lowerThresholdNonCritical = lowerThresholdNonCritical;
    }


    public String getUpperThresholdCritical() {
        return upperThresholdCritical;
    }


    public void setUpperThresholdCritical(String upperThresholdCritical) {
        this.upperThresholdCritical = upperThresholdCritical;
    }


    public String getUpperThresholdNonCritical() {
        return upperThresholdNonCritical;
    }


    public void setUpperThresholdNonCritical(String upperThresholdNonCritical) {
        this.upperThresholdNonCritical = upperThresholdNonCritical;
    }

}