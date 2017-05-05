/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.entity;

import java.io.Serializable;

/**
 * @author prashanth.gowda
 *
 */
public class ControllerView implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String bus = null;
    private String cacheSizeInMB = null;
    private String cacheCadeCapability = null;
    private String controllerFirmwareVersion = null;
    private String device = null;
    private String deviceCardDataBusWidth = null;
    private String deviceCardManufacturer = null;
    private String deviceCardSlotLength = null;
    private String deviceCardSlotType = null;
    private String deviceDescription = null;
    private String driverVersion = null;
    private String encryptionCapability = null;
    private String encryptionMode = null;
    private String fQDD = null;
    private String function = null;
    private String instanceID = null;
    private String keyID = null;
    private String lastSystemInventoryTime = null;
    private String lastUpdateTime = null;
    private String maxAvailablePciLinkSpeed = null;
    private String maxPossiblePciLinkSpeed = null;
    private String PCIDeviceID = null;
    private String PCISlot = null;
    private String PCISubDeviceID = null;
    private String PCISubVendorID = null;
    private String PCIVendorID = null;
    private String patrolReadState = null;
    private String primaryStatus = null;
    private String productName = null;
    private String realtimeCapability = null;
    private String rollUpStatus = null;
    private String SASAddress = null;
    private String securityStatus = null;
    private String slicedVDCapability = null;
    private String supportControllerBootMode = null;
    private String supportEnhancedAutoForeignImport = null;
    private String supportRaid10UnevenSpans = null;
    private String t10piCapability = null;
    private String possibleSpeed = null;
    private String patrolReadMode = null;
    private String ccMode = null;
    private String ccRate = null;
    private String copyBackMode = null;
    private String bgiRate = null;
    private String rebuildRate = null;
    private String batteryState = null;
    private String preservedCache = null;
    private String batteryStatus = null;


    /**
     * @return the bus
     */
    public String getBus() {
        return bus;
    }


    /**
     * @param bus the bus to set
     */
    public void setBus(String bus) {
        this.bus = bus;
    }


    /**
     * @return the cacheSizeInMB
     */
    public String getCacheSizeInMB() {
        return cacheSizeInMB;
    }


    /**
     * @param cacheSizeInMB the cacheSizeInMB to set
     */
    public void setCacheSizeInMB(String cacheSizeInMB) {
        this.cacheSizeInMB = cacheSizeInMB;
    }


    /**
     * @return the cacheCadeCapability
     */
    public String getCacheCadeCapability() {
        return cacheCadeCapability;
    }


    /**
     * @param cacheCadeCapability the cacheCadeCapability to set
     */
    public void setCacheCadeCapability(String cacheCadeCapability) {
        this.cacheCadeCapability = cacheCadeCapability;
    }


    /**
     * @return the controllerFirmwareVersion
     */
    public String getControllerFirmwareVersion() {
        return controllerFirmwareVersion;
    }


    /**
     * @param controllerFirmwareVersion the controllerFirmwareVersion to set
     */
    public void setControllerFirmwareVersion(String controllerFirmwareVersion) {
        this.controllerFirmwareVersion = controllerFirmwareVersion;
    }


    /**
     * @return the device
     */
    public String getDevice() {
        return device;
    }


    /**
     * @param device the device to set
     */
    public void setDevice(String device) {
        this.device = device;
    }


    /**
     * @return the deviceCardDataBusWidth
     */
    public String getDeviceCardDataBusWidth() {
        return deviceCardDataBusWidth;
    }


    /**
     * @param deviceCardDataBusWidth the deviceCardDataBusWidth to set
     */
    public void setDeviceCardDataBusWidth(String deviceCardDataBusWidth) {
        this.deviceCardDataBusWidth = deviceCardDataBusWidth;
    }


    /**
     * @return the deviceCardManufacturer
     */
    public String getDeviceCardManufacturer() {
        return deviceCardManufacturer;
    }


    /**
     * @param deviceCardManufacturer the deviceCardManufacturer to set
     */
    public void setDeviceCardManufacturer(String deviceCardManufacturer) {
        this.deviceCardManufacturer = deviceCardManufacturer;
    }


    /**
     * @return the deviceCardSlotLength
     */
    public String getDeviceCardSlotLength() {
        return deviceCardSlotLength;
    }


    /**
     * @param deviceCardSlotLength the deviceCardSlotLength to set
     */
    public void setDeviceCardSlotLength(String deviceCardSlotLength) {
        this.deviceCardSlotLength = deviceCardSlotLength;
    }


    /**
     * @return the deviceCardSlotType
     */
    public String getDeviceCardSlotType() {
        return deviceCardSlotType;
    }


    /**
     * @param deviceCardSlotType the deviceCardSlotType to set
     */
    public void setDeviceCardSlotType(String deviceCardSlotType) {
        this.deviceCardSlotType = deviceCardSlotType;
    }


    /**
     * @return the driverVersion
     */
    public String getDriverVersion() {
        return driverVersion;
    }


    /**
     * @param driverVersion the driverVersion to set
     */
    public void setDriverVersion(String driverVersion) {
        this.driverVersion = driverVersion;
    }


    /**
     * @return the encryptionCapability
     */
    public String getEncryptionCapability() {
        return encryptionCapability;
    }


    /**
     * @param encryptionCapability the encryptionCapability to set
     */
    public void setEncryptionCapability(String encryptionCapability) {
        this.encryptionCapability = encryptionCapability;
    }


    /**
     * @return the encryptionMode
     */
    public String getEncryptionMode() {
        return encryptionMode;
    }


    /**
     * @param encryptionMode the encryptionMode to set
     */
    public void setEncryptionMode(String encryptionMode) {
        this.encryptionMode = encryptionMode;
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
     * @return the function
     */
    public String getFunction() {
        return function;
    }


    /**
     * @param function the function to set
     */
    public void setFunction(String function) {
        this.function = function;
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
     * @return the keyID
     */
    public String getKeyID() {
        return keyID;
    }


    /**
     * @param keyID the keyID to set
     */
    public void setKeyID(String keyID) {
        this.keyID = keyID;
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
     * @return the pCIDeviceID
     */
    public String getPCIDeviceID() {
        return PCIDeviceID;
    }


    /**
     * @param pCIDeviceID the pCIDeviceID to set
     */
    public void setPCIDeviceID(String pCIDeviceID) {
        PCIDeviceID = pCIDeviceID;
    }


    /**
     * @return the pCISlot
     */
    public String getPCISlot() {
        return PCISlot;
    }


    /**
     * @param pCISlot the pCISlot to set
     */
    public void setPCISlot(String pCISlot) {
        PCISlot = pCISlot;
    }


    /**
     * @return the pCISubDeviceID
     */
    public String getPCISubDeviceID() {
        return PCISubDeviceID;
    }


    /**
     * @param pCISubDeviceID the pCISubDeviceID to set
     */
    public void setPCISubDeviceID(String pCISubDeviceID) {
        PCISubDeviceID = pCISubDeviceID;
    }


    /**
     * @return the pCISubVendorID
     */
    public String getPCISubVendorID() {
        return PCISubVendorID;
    }


    /**
     * @param pCISubVendorID the pCISubVendorID to set
     */
    public void setPCISubVendorID(String pCISubVendorID) {
        PCISubVendorID = pCISubVendorID;
    }


    /**
     * @return the pCIVendorID
     */
    public String getPCIVendorID() {
        return PCIVendorID;
    }


    /**
     * @param pCIVendorID the pCIVendorID to set
     */
    public void setPCIVendorID(String pCIVendorID) {
        PCIVendorID = pCIVendorID;
    }


    /**
     * @return the patrolReadState
     */
    public String getPatrolReadState() {
        return patrolReadState;
    }


    /**
     * @param patrolReadState the patrolReadState to set
     */
    public void setPatrolReadState(String patrolReadState) {
        this.patrolReadState = patrolReadState;
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
     * @return the productName
     */
    public String getProductName() {
        return productName;
    }


    /**
     * @param productName the productName to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
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
     * @return the securityStatus
     */
    public String getSecurityStatus() {
        return securityStatus;
    }


    /**
     * @param securityStatus the securityStatus to set
     */
    public void setSecurityStatus(String securityStatus) {
        this.securityStatus = securityStatus;
    }


    /**
     * @return the slicedVDCapability
     */
    public String getSlicedVDCapability() {
        return slicedVDCapability;
    }


    /**
     * @param slicedVDCapability the slicedVDCapability to set
     */
    public void setSlicedVDCapability(String slicedVDCapability) {
        this.slicedVDCapability = slicedVDCapability;
    }


    /**
     * @return
     */

    public String getDeviceDescription() {
        return deviceDescription;
    }


    public void setDeviceDescription(String deviceDescription) {
        this.deviceDescription = deviceDescription;
    }


    public String getMaxAvailablePciLinkSpeed() {
        return maxAvailablePciLinkSpeed;
    }


    public void setMaxAvailablePciLinkSpeed(String maxAvailablePciLinkSpeed) {
        this.maxAvailablePciLinkSpeed = maxAvailablePciLinkSpeed;
    }


    public String getMaxPossiblePciLinkSpeed() {
        return maxPossiblePciLinkSpeed;
    }


    public void setMaxPossiblePciLinkSpeed(String maxPossiblePciLinkSpeed) {
        this.maxPossiblePciLinkSpeed = maxPossiblePciLinkSpeed;
    }


    public String getRealtimeCapability() {
        return realtimeCapability;
    }


    public void setRealtimeCapability(String realtimeCapability) {
        this.realtimeCapability = realtimeCapability;
    }


    public String getSupportControllerBootMode() {
        return supportControllerBootMode;
    }


    public void setSupportControllerBootMode(String supportControllerBootMode) {
        this.supportControllerBootMode = supportControllerBootMode;
    }


    public String getSupportEnhancedAutoForeignImport() {
        return supportEnhancedAutoForeignImport;
    }


    public void setSupportEnhancedAutoForeignImport(String supportEnhancedAutoForeignImport) {
        this.supportEnhancedAutoForeignImport = supportEnhancedAutoForeignImport;
    }


    public String getSupportRaid10UnevenSpans() {
        return supportRaid10UnevenSpans;
    }


    public void setSupportRaid10UnevenSpans(String supportRaid10UnevenSpans) {
        this.supportRaid10UnevenSpans = supportRaid10UnevenSpans;
    }


    public String getT10piCapability() {
        return t10piCapability;
    }


    public void setT10piCapability(String t10piCapability) {
        this.t10piCapability = t10piCapability;
    }


    public String getPossibleSpeed() {
        return possibleSpeed;
    }


    public void setPossibleSpeed(String possibleSpeed) {
        this.possibleSpeed = possibleSpeed;
    }


    public String getPatrolReadMode() {
        return patrolReadMode;
    }


    public void setPatrolReadMode(String patrolReadMode) {
        this.patrolReadMode = patrolReadMode;
    }


    public String getCcMode() {
        return ccMode;
    }


    public void setCcMode(String ccMode) {
        this.ccMode = ccMode;
    }


    public String getCcRate() {
        return ccRate;
    }


    public void setCcRate(String ccRate) {
        this.ccRate = ccRate;
    }


    public String getCopyBackMode() {
        return copyBackMode;
    }


    public void setCopyBackMode(String copyBackMode) {
        this.copyBackMode = copyBackMode;
    }


    public String getBgiRate() {
        return bgiRate;
    }


    public void setBgiRate(String bgiRate) {
        this.bgiRate = bgiRate;
    }


    public String getRebuildRate() {
        return rebuildRate;
    }


    public void setRebuildRate(String rebuildRate) {
        this.rebuildRate = rebuildRate;
    }


    public String getBatteryState() {
        return batteryState;
    }


    public void setBatteryState(String batteryState) {
        this.batteryState = batteryState;
    }


    public String getPreservedCache() {
        return preservedCache;
    }


    public void setPreservedCache(String preservedCache) {
        this.preservedCache = preservedCache;
    }


    public String getBatteryStatus() {
        return batteryStatus;
    }


    public void setBatteryStatus(String batteryStatus) {
        this.batteryStatus = batteryStatus;
    }
}
