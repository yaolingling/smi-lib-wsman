/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.entity;

import java.io.Serializable;

public class PhysicalDiskView implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String busProtocol = null;
    private String connector = null;
    private String driveFormFactor = null;
    private String fQDD = null;
    private String freeSizeInBytes = null;
    private String hotSpareStatus = null;
    private String instanceID = null;
    private String lastSystemInventoryTime = null;
    private String lastUpdateTime = null;
    private String manufacturer = null;
    private String manufacturingDay = null;
    private String manufacturingWeek = null;
    private String manufacturingYear = null;
    private String maxCapableSpeed = null;
    private String mediaType = null;
    private String model = null;
    private String operationName = null;
    private String operationPercentComplete = null;
    private String predictiveFailureState = null;
    private String primaryStatus = null;
    private String raidStatus = null;
    private String revision = null;
    private String rollUpStatus = null;
    private String SASAddress = null;
    private String securityState = null;
    private String serialNumber = null;
    private String sizeInBytes = null;
    private String Slot = null;
    private String supportedEncryptionTypes = null;
    private String usedSizeInBytes = null;
    private String PPID = null;
    private String deviceDescription = null;
    private String deviceLifeStatus = null;
    private String remainingRatedWriteEndurance = null;
    private String T10PICapability = null;
    private String BlockSizeInBytes = null;


    /**
     * @return the busProtocol
     */
    public String getBusProtocol() {
        return busProtocol;
    }


    /**
     * @param busProtocol the busProtocol to set
     */
    public void setBusProtocol(String busProtocol) {
        this.busProtocol = busProtocol;
    }


    /**
     * @return the connector
     */
    public String getConnector() {
        return connector;
    }


    /**
     * @param connector the connector to set
     */
    public void setConnector(String connector) {
        this.connector = connector;
    }


    /**
     * @return the driveFormFactor
     */
    public String getDriveFormFactor() {
        return driveFormFactor;
    }


    /**
     * @param driveFormFactor the driveFormFactor to set
     */
    public void setDriveFormFactor(String driveFormFactor) {
        this.driveFormFactor = driveFormFactor;
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
     * @return the freeSizeInBytes
     */
    public String getFreeSizeInBytes() {
        return freeSizeInBytes;
    }


    /**
     * @param freeSizeInBytes the freeSizeInBytes to set
     */
    public void setFreeSizeInBytes(String freeSizeInBytes) {
        this.freeSizeInBytes = freeSizeInBytes;
    }


    /**
     * @return the hotSpareStatus
     */
    public String getHotSpareStatus() {
        return hotSpareStatus;
    }


    /**
     * @param hotSpareStatus the hotSpareStatus to set
     */
    public void setHotSpareStatus(String hotSpareStatus) {
        this.hotSpareStatus = hotSpareStatus;
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
     * @return the manufacturingDay
     */
    public String getManufacturingDay() {
        return manufacturingDay;
    }


    /**
     * @param manufacturingDay the manufacturingDay to set
     */
    public void setManufacturingDay(String manufacturingDay) {
        this.manufacturingDay = manufacturingDay;
    }


    /**
     * @return the manufacturingWeek
     */
    public String getManufacturingWeek() {
        return manufacturingWeek;
    }


    /**
     * @param manufacturingWeek the manufacturingWeek to set
     */
    public void setManufacturingWeek(String manufacturingWeek) {
        this.manufacturingWeek = manufacturingWeek;
    }


    /**
     * @return the manufacturingYear
     */
    public String getManufacturingYear() {
        return manufacturingYear;
    }


    /**
     * @param manufacturingYear the manufacturingYear to set
     */
    public void setManufacturingYear(String manufacturingYear) {
        this.manufacturingYear = manufacturingYear;
    }


    /**
     * @return the maxCapableSpeed
     */
    public String getMaxCapableSpeed() {
        return maxCapableSpeed;
    }


    /**
     * @param maxCapableSpeed the maxCapableSpeed to set
     */
    public void setMaxCapableSpeed(String maxCapableSpeed) {
        this.maxCapableSpeed = maxCapableSpeed;
    }


    /**
     * @return the mediaType
     */
    public String getMediaType() {
        return mediaType;
    }


    /**
     * @param mediaType the mediaType to set
     */
    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
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
     * @return the operationName
     */
    public String getOperationName() {
        return operationName;
    }


    /**
     * @param operationName the operationName to set
     */
    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }


    /**
     * @return the operationPercentComplete
     */
    public String getOperationPercentComplete() {
        return operationPercentComplete;
    }


    /**
     * @param operationPercentComplete the operationPercentComplete to set
     */
    public void setOperationPercentComplete(String operationPercentComplete) {
        this.operationPercentComplete = operationPercentComplete;
    }


    /**
     * @return the predictiveFailureState
     */
    public String getPredictiveFailureState() {
        return predictiveFailureState;
    }


    /**
     * @param predictiveFailureState the predictiveFailureState to set
     */
    public void setPredictiveFailureState(String predictiveFailureState) {
        this.predictiveFailureState = predictiveFailureState;
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
     * @return the raidStatus
     */
    public String getRaidStatus() {
        return raidStatus;
    }


    /**
     * @param raidStatus the raidStatus to set
     */
    public void setRaidStatus(String raidStatus) {
        this.raidStatus = raidStatus;
    }


    /**
     * @return the revision
     */
    public String getRevision() {
        return revision;
    }


    /**
     * @param revision the revision to set
     */
    public void setRevision(String revision) {
        this.revision = revision;
    }


    /**
     * @return the rollUpStatus
     */
    public String getRollUpStatus() {
        return rollUpStatus;
    }


    /**
     * @param rollUpStatus the rollUpStatus to set
     */
    public void setRollUpStatus(String rollUpStatus) {
        this.rollUpStatus = rollUpStatus;
    }


    /**
     * @return the sASAddress
     */
    public String getSASAddress() {
        return SASAddress;
    }


    /**
     * @param sASAddress the sASAddress to set
     */
    public void setSASAddress(String sASAddress) {
        SASAddress = sASAddress;
    }


    /**
     * @return the securityState
     */
    public String getSecurityState() {
        return securityState;
    }


    /**
     * @param securityState the securityState to set
     */
    public void setSecurityState(String securityState) {
        this.securityState = securityState;
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
     * @return the sizeInBytes
     */
    public String getSizeInBytes() {
        return sizeInBytes;
    }


    /**
     * @param sizeInBytes the sizeInBytes to set
     */
    public void setSizeInBytes(String sizeInBytes) {
        this.sizeInBytes = sizeInBytes;
    }


    /**
     * @return the slot
     */
    public String getSlot() {
        return Slot;
    }


    /**
     * @param slot the slot to set
     */
    public void setSlot(String slot) {
        Slot = slot;
    }


    /**
     * @return the supportedEncryptionTypes
     */
    public String getSupportedEncryptionTypes() {
        return supportedEncryptionTypes;
    }


    /**
     * @param supportedEncryptionTypes the supportedEncryptionTypes to set
     */
    public void setSupportedEncryptionTypes(String supportedEncryptionTypes) {
        this.supportedEncryptionTypes = supportedEncryptionTypes;
    }


    /**
     * @return the usedSizeInBytes
     */
    public String getUsedSizeInBytes() {
        return usedSizeInBytes;
    }


    /**
     * @param usedSizeInBytes the usedSizeInBytes to set
     */
    public void setUsedSizeInBytes(String usedSizeInBytes) {
        this.usedSizeInBytes = usedSizeInBytes;
    }


    /**
     * @param pPID the pPID to set
     */
    public void setPPID(String pPID) {
        PPID = pPID;
    }


    /**
     * @return the pPID
     */
    public String getPPID() {
        return PPID;
    }


    public String getDeviceDescription() {
        return deviceDescription;
    }


    public void setDeviceDescription(String deviceDescription) {
        this.deviceDescription = deviceDescription;
    }


    public String getDeviceLifeStatus() {
        return deviceLifeStatus;
    }


    public void setDeviceLifeStatus(String deviceLifeStatus) {
        this.deviceLifeStatus = deviceLifeStatus;
    }


    public String getRemainingRatedWriteEndurance() {
        return remainingRatedWriteEndurance;
    }


    public void setRemainingRatedWriteEndurance(String remainingRatedWriteEndurance) {
        this.remainingRatedWriteEndurance = remainingRatedWriteEndurance;
    }


    public String getT10PICapability() {
        return T10PICapability;
    }


    public void setT10PICapability(String t10PICapability) {
        T10PICapability = t10PICapability;
    }


    public String getBlockSizeInBytes() {
        return BlockSizeInBytes;
    }


    public void setBlockSizeInBytes(String blockSizeInBytes) {
        BlockSizeInBytes = blockSizeInBytes;
    }

}
