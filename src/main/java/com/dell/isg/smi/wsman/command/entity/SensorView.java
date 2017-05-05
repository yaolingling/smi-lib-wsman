/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rahman.muhammad
 *
 */
public class SensorView {
    private String creationClassName;
    private String currentState;
    private String deviceId;
    private String elementName;
    private String enabledState;
    private String healthState;
    private String operationalStatus;
    private String otherSensorTypeDescription;
    private String possibleStates;
    private String primaryStatus;
    private String requestedState;
    private String sensorType;
    private String systemCreationClassName;
    private String systemName;
    private final String BATTERY = "Battery";
    private final String VOLTAGE = "3";


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


    public String getOtherSensorTypeDescription() {
        return otherSensorTypeDescription;
    }


    public void setOtherSensorTypeDescription(String otherSensorTypeDescription) {
        this.otherSensorTypeDescription = otherSensorTypeDescription;
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


    public boolean isBattery() {

        return (otherSensorTypeDescription == null ? false : otherSensorTypeDescription.trim().equalsIgnoreCase(BATTERY));
    }


    public boolean isVoltage() {

        return (sensorType == null ? false : sensorType.trim().equalsIgnoreCase(VOLTAGE));
    }

}
