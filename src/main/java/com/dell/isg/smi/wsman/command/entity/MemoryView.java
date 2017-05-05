/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.entity;

import java.io.Serializable;

public class MemoryView implements Serializable {

    private static final long serialVersionUID = 1L;

    private String bankLabel = null;
    private String currentOperatingSpeed = null;
    private String fQDD = null;
    private String instanceID = null;
    private String lastSystemInventoryTime = null;
    private String lastUpdateTime = null;
    private String manufactureDate = null;
    private String manufacturer = null;
    private String deviceDescription = null;
    private String memoryType = null;
    private String model = null;
    private String partNumber = null;
    private String primaryStatus = null;
    private String rank = null;
    private String serialNumber = null;
    private String size = null;
    private String speed = null;


    /**
     * @return the bankLabel
     */
    public String getBankLabel() {
        return bankLabel;
    }


    /**
     * @param bankLabel the bankLabel to set
     */
    public void setBankLabel(String bankLabel) {
        this.bankLabel = bankLabel;
    }


    /**
     * @return the currentOperatingSpeed
     */
    public String getCurrentOperatingSpeed() {
        return currentOperatingSpeed;
    }


    /**
     * @param currentOperatingSpeed the currentOperatingSpeed to set
     */
    public void setCurrentOperatingSpeed(String currentOperatingSpeed) {
        this.currentOperatingSpeed = currentOperatingSpeed;
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
     * @return the manufactureDate
     */
    public String getManufactureDate() {
        return manufactureDate;
    }


    /**
     * @param manufactureDate the manufactureDate to set
     */
    public void setManufactureDate(String manufactureDate) {
        this.manufactureDate = manufactureDate;
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
     * @return the memoryType
     */
    public String getMemoryType() {
        return memoryType;
    }


    /**
     * @param memoryType the memoryType to set
     */
    public void setMemoryType(String memoryType) {
        this.memoryType = memoryType;
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
     * @return the rank
     */
    public String getRank() {
        return rank;
    }


    /**
     * @param rank the rank to set
     */
    public void setRank(String rank) {
        this.rank = rank;
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
     * @return the size
     */
    public String getSize() {
        return size;
    }


    /**
     * @param size the size to set
     */
    public void setSize(String size) {
        this.size = size;
    }


    /**
     * @return the speed
     */
    public String getSpeed() {
        return speed;
    }


    /**
     * @param speed the speed to set
     */
    public void setSpeed(String speed) {
        this.speed = speed;
    }


    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }


    public String getDeviceDescription() {
        return deviceDescription;
    }


    public void setDeviceDescription(String deviceDescription) {
        this.deviceDescription = deviceDescription;
    }

}
