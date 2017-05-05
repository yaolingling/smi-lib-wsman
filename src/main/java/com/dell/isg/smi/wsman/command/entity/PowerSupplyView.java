/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.entity;

import java.io.Serializable;

public class PowerSupplyView implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String detailedState = null;
    private String fQDD = null;
    private String firmwareVersion = null;
    private String inputVoltage = null;
    private String instanceID = null;
    private String lastSystemInventoryTime = null;
    private String lastUpdateTime = null;
    private String manufacturer = null;
    private String model = null;
    private String partNumber = null;
    private String primaryStatus = null;
    private String redundancyStatus = null;
    private String serialNumber = null;
    private String totalOutputPower = null;
    private String deviceDescription = null;
    private String range1MaxInputPower = null;
    private String type = null;


    /**
     * @return the detailedState
     */
    public String getDetailedState() {
        return detailedState;
    }


    /**
     * @param detailedState the detailedState to set
     */
    public void setDetailedState(String detailedState) {
        this.detailedState = detailedState;
    }


    /**
     * @return the fQDD
     */
    public String getfQDD() {
        return fQDD;
    }


    /**
     * @param fQDD the fQDD to set
     */
    public void setfQDD(String fQDD) {
        this.fQDD = fQDD;
    }


    /**
     * @return the firmwareVersion
     */
    public String getFirmwareVersion() {
        return firmwareVersion;
    }


    /**
     * @param firmwareVersion the firmwareVersion to set
     */
    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }


    /**
     * @return the inputVoltage
     */
    public String getInputVoltage() {
        return inputVoltage;
    }


    /**
     * @param inputVoltage the inputVoltage to set
     */
    public void setInputVoltage(String inputVoltage) {
        this.inputVoltage = inputVoltage;
    }


    /**
     * @return the instanceID
     */
    public String getInstanceID() {
        return instanceID;
    }


    /**
     * @param instanceID the instanceID to set
     */
    public void setInstanceID(String instanceID) {
        this.instanceID = instanceID;
    }


    /**
     * @return the lastSystemInventoryTime
     */
    public String getLastSystemInventoryTime() {
        return lastSystemInventoryTime;
    }


    /**
     * @param lastSystemInventoryTime the lastSystemInventoryTime to set
     */
    public void setLastSystemInventoryTime(String lastSystemInventoryTime) {
        this.lastSystemInventoryTime = lastSystemInventoryTime;
    }


    /**
     * @return the lastUpdateTime
     */
    public String getLastUpdateTime() {
        return lastUpdateTime;
    }


    /**
     * @param lastUpdateTime the lastUpdateTime to set
     */
    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }


    /**
     * @return the manufacturer
     */
    public String getManufacturer() {
        return manufacturer;
    }


    /**
     * @param manufacturer the manufacturer to set
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }


    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }


    /**
     * @param model the model to set
     */
    public void setModel(String model) {
        this.model = model;
    }


    /**
     * @return the partNumber
     */
    public String getPartNumber() {
        return partNumber;
    }


    /**
     * @param partNumber the partNumber to set
     */
    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }


    /**
     * @return the primaryStatus
     */
    public String getPrimaryStatus() {
        return primaryStatus;
    }


    /**
     * @param primaryStatus the primaryStatus to set
     */
    public void setPrimaryStatus(String primaryStatus) {
        this.primaryStatus = primaryStatus;
    }


    /**
     * @return the redundancyStatus
     */
    public String getRedundancyStatus() {
        return redundancyStatus;
    }


    /**
     * @param redundancyStatus the redundancyStatus to set
     */
    public void setRedundancyStatus(String redundancyStatus) {
        this.redundancyStatus = redundancyStatus;
    }


    /**
     * @return the serialNumber
     */
    public String getSerialNumber() {
        return serialNumber;
    }


    /**
     * @param serialNumber the serialNumber to set
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }


    /**
     * @return the totalOutputPower
     */
    public String getTotalOutputPower() {
        return totalOutputPower;
    }


    /**
     * @param totalOutputPower the totalOutputPower to set
     */
    public void setTotalOutputPower(String totalOutputPower) {
        this.totalOutputPower = totalOutputPower;
    }


    /**
     * @return the type
     */
    public String getType() {
        return type;
    }


    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }


    public String getDeviceDescription() {
        return deviceDescription;
    }


    public void setDeviceDescription(String deviceDescription) {
        this.deviceDescription = deviceDescription;
    }


    public String getRange1MaxInputPower() {
        return range1MaxInputPower;
    }


    public void setRange1MaxInputPower(String range1MaxInputPower) {
        this.range1MaxInputPower = range1MaxInputPower;
    }

}
