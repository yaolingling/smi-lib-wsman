/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.entity;

/**
 * @author Prashanth.Gowda
 *
 */
public class NumericSensorView {

    private String systemCreationClassName;
    private String systemName;
    private String creationClassName;
    private String deviceId;
    private String baseUnits;
    private String currentReading;
    private String currentState;
    private String elementName;
    private String enabledState;
    private String healthState;
    private String operationalStatus;
    private String possibleStates;
    private String primaryStatus;
    private String rateUnits;
    private String requestedState;
    private String sensorType;
    private String settableThresholds;
    private String supportedThresholds;
    private String unitModifier;
    private String lowerThresholdCritical;
    private String lowerThresholdNonCritical;
    private String upperThresholdCritical;
    private String upperThresholdNonCritical;
    private String transitioningToState;
    private String valueFormulation;
    private String enabledDefault;
    private String otherSensorTypeDescription;


    public String getCreationClassName() {
        return creationClassName;
    }


    public void setCreationClassName(String creationClassName) {
        this.creationClassName = creationClassName;
    }


    public String getCurrentState() {
        return currentState;
    }


    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }


    public String getDeviceId() {
        return deviceId;
    }


    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }


    public String getElementName() {
        return elementName;
    }


    public void setElementName(String elementName) {
        this.elementName = elementName;
    }


    public String getEnabledState() {
        return enabledState;
    }


    public void setEnabledState(String enabledState) {
        this.enabledState = enabledState;
    }


    public String getHealthState() {
        return healthState;
    }


    public void setHealthState(String healthState) {
        this.healthState = healthState;
    }


    public String getOperationalStatus() {
        return operationalStatus;
    }


    public void setOperationalStatus(String operationalStatus) {
        this.operationalStatus = operationalStatus;
    }


    public String getPossibleStates() {
        return possibleStates;
    }


    public void setPossibleStates(String possibleStates) {
        this.possibleStates = possibleStates;
    }


    public String getPrimaryStatus() {
        return primaryStatus;
    }


    public void setPrimaryStatus(String primaryStatus) {
        this.primaryStatus = primaryStatus;
    }


    public String getRequestedState() {
        return requestedState;
    }


    public void setRequestedState(String requestedState) {
        this.requestedState = requestedState;
    }


    public String getSensorType() {
        return sensorType;
    }


    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }


    public String getSystemCreationClassName() {
        return systemCreationClassName;
    }


    public void setSystemCreationClassName(String systemCreationClassName) {
        this.systemCreationClassName = systemCreationClassName;
    }


    public String getSystemName() {
        return systemName;
    }


    public void setSystemName(String systemName) {
        this.systemName = systemName;
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


    public String getRateUnits() {
        return rateUnits;
    }


    public void setRateUnits(String rateUnits) {
        this.rateUnits = rateUnits;
    }


    public String getSettableThresholds() {
        return settableThresholds;
    }


    public void setSettableThresholds(String settableThresholds) {
        this.settableThresholds = settableThresholds;
    }


    public String getSupportedThresholds() {
        return supportedThresholds;
    }


    public void setSupportedThresholds(String supportedThresholds) {
        this.supportedThresholds = supportedThresholds;
    }


    public String getUnitModifier() {
        return unitModifier;
    }


    public void setUnitModifier(String unitModifier) {
        this.unitModifier = unitModifier;
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


    public String getTransitioningToState() {
        return transitioningToState;
    }


    public void setTransitioningToState(String transitioningToState) {
        this.transitioningToState = transitioningToState;
    }


    public String getValueFormulation() {
        return valueFormulation;
    }


    public void setValueFormulation(String valueFormulation) {
        this.valueFormulation = valueFormulation;
    }


    public String getEnabledDefault() {
        return enabledDefault;
    }


    public void setEnabledDefault(String enabledDefault) {
        this.enabledDefault = enabledDefault;
    }


    public String getOtherSensorTypeDescription() {
        return otherSensorTypeDescription;
    }


    public void setOtherSensorTypeDescription(String otherSensorTypeDescription) {
        this.otherSensorTypeDescription = otherSensorTypeDescription;
    }

}
