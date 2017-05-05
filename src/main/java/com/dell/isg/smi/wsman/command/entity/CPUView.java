/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.entity;

import java.io.Serializable;

public class CPUView implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String fQDD = null;
    private String instanceID = null;
    private String lastSystemInventoryTime = null;
    private String lastUpdateTime = null;
    private String numberOfEnabledThreads = null;
    private String cpuFamily = null;
    private String cpuStatus = null;
    private String cache1Level = null;
    private String cache2Level = null;
    private String cache3Level = null;
    private String cache1Size = null;
    private String cache2Size = null;
    private String cache3Size = null;
    private String cache1Type = null;
    private String cache2Type = null;
    private String cache3Type = null;
    private String cache1WritePolicy = null;
    private String cache2WritePolicy = null;
    private String cache3WritePolicy = null;
    private String cache1Associativity = null;
    private String cache2Associativity = null;
    private String cache3Associativity = null;
    private String cache1ErrorMethodology = null;
    private String cache2ErrorMethodology = null;
    private String cache3ErrorMethodology = null;
    private String cache1PrimaryStatus = null;
    private String cache2PrimaryStatus = null;
    private String cache3PrimaryStatus = null;
    private String currentClockSpeed = null;
    private String externalBusClockSpeed = null;
    private String manufacturer = null;
    private String maxClockSpeed = null;
    private String model = null;
    private String numberOfEnabledCores = null;
    private String numberOfProcessorCores = null;
    private String primaryStatus = null;
    private String voltage = null;
    private String characteristics = null;
    private String cache1Location = null;
    private String cache2Location = null;
    private String cache3Location = null;
    private String cache1SramType = null;
    private String cache2SramType = null;
    private String cache3SramType = null;
    private String deviceDescription = null;
    private String executeDisabledCapable = null;
    private String executeDisabledEnabled = null;
    private String hyperThreadingCapable = null;
    private String hyperThreadingEnabled = null;
    private String virtualizationTechnologyCapable = null;
    private String virtualizationTechnologyEnabled = null;
    private String turboModeCapable = null;
    private String turboModeEnabled = null;


    /**
     * @return the cpuFamily
     */
    public String getCpuFamily() {
        return cpuFamily;
    }


    /**
     * @param cpuFamily the cpuFamily to set
     */
    public void setCpuFamily(String cpuFamily) {
        this.cpuFamily = cpuFamily;
    }


    /**
     * @return the cpuStatus
     */
    public String getCpuStatus() {
        return cpuStatus;
    }


    /**
     * @param cpuStatus the cpuStatus to set
     */
    public void setCpuStatus(String cpuStatus) {
        this.cpuStatus = cpuStatus;
    }


    /**
     * @return the cache1Associativity
     */
    public String getCache1Associativity() {
        return cache1Associativity;
    }


    /**
     * @param cache1Associativity the cache1Associativity to set
     */
    public void setCache1Associativity(String cache1Associativity) {
        this.cache1Associativity = cache1Associativity;
    }


    /**
     * @return the cache1ErrorMethodology
     */
    public String getCache1ErrorMethodology() {
        return cache1ErrorMethodology;
    }


    /**
     * @param cache1ErrorMethodology the cache1ErrorMethodology to set
     */
    public void setCache1ErrorMethodology(String cache1ErrorMethodology) {
        this.cache1ErrorMethodology = cache1ErrorMethodology;
    }


    /**
     * @return the cache1Level
     */
    public String getCache1Level() {
        return cache1Level;
    }


    /**
     * @param cache1Level the cache1Level to set
     */
    public void setCache1Level(String cache1Level) {
        this.cache1Level = cache1Level;
    }


    /**
     * @return the cache1PrimaryStatus
     */
    public String getCache1PrimaryStatus() {
        return cache1PrimaryStatus;
    }


    /**
     * @param cache1PrimaryStatus the cache1PrimaryStatus to set
     */
    public void setCache1PrimaryStatus(String cache1PrimaryStatus) {
        this.cache1PrimaryStatus = cache1PrimaryStatus;
    }


    /**
     * @return the cache1Size
     */
    public String getCache1Size() {
        return cache1Size;
    }


    /**
     * @param cache1Size the cache1Size to set
     */
    public void setCache1Size(String cache1Size) {
        this.cache1Size = cache1Size;
    }


    /**
     * @return the cache1Type
     */
    public String getCache1Type() {
        return cache1Type;
    }


    /**
     * @param cache1Type the cache1Type to set
     */
    public void setCache1Type(String cache1Type) {
        this.cache1Type = cache1Type;
    }


    /**
     * @return the cache1WritePolicy
     */
    public String getCache1WritePolicy() {
        return cache1WritePolicy;
    }


    /**
     * @param cache1WritePolicy the cache1WritePolicy to set
     */
    public void setCache1WritePolicy(String cache1WritePolicy) {
        this.cache1WritePolicy = cache1WritePolicy;
    }


    /**
     * @return the cache2Associativity
     */
    public String getCache2Associativity() {
        return cache2Associativity;
    }


    /**
     * @param cache2Associativity the cache2Associativity to set
     */
    public void setCache2Associativity(String cache2Associativity) {
        this.cache2Associativity = cache2Associativity;
    }


    /**
     * @return the cache2ErrorMethodology
     */
    public String getCache2ErrorMethodology() {
        return cache2ErrorMethodology;
    }


    /**
     * @param cache2ErrorMethodology the cache2ErrorMethodology to set
     */
    public void setCache2ErrorMethodology(String cache2ErrorMethodology) {
        this.cache2ErrorMethodology = cache2ErrorMethodology;
    }


    /**
     * @return the cache2Level
     */
    public String getCache2Level() {
        return cache2Level;
    }


    /**
     * @param cache2Level the cache2Level to set
     */
    public void setCache2Level(String cache2Level) {
        this.cache2Level = cache2Level;
    }


    /**
     * @return the cache2PrimaryStatus
     */
    public String getCache2PrimaryStatus() {
        return cache2PrimaryStatus;
    }


    /**
     * @param cache2PrimaryStatus the cache2PrimaryStatus to set
     */
    public void setCache2PrimaryStatus(String cache2PrimaryStatus) {
        this.cache2PrimaryStatus = cache2PrimaryStatus;
    }


    /**
     * @return the cache2Size
     */
    public String getCache2Size() {
        return cache2Size;
    }


    /**
     * @param cache2Size the cache2Size to set
     */
    public void setCache2Size(String cache2Size) {
        this.cache2Size = cache2Size;
    }


    /**
     * @return the cache2Type
     */
    public String getCache2Type() {
        return cache2Type;
    }


    /**
     * @param cache2Type the cache2Type to set
     */
    public void setCache2Type(String cache2Type) {
        this.cache2Type = cache2Type;
    }


    /**
     * @return the cache2WritePolicy
     */
    public String getCache2WritePolicy() {
        return cache2WritePolicy;
    }


    /**
     * @param cache2WritePolicy the cache2WritePolicy to set
     */
    public void setCache2WritePolicy(String cache2WritePolicy) {
        this.cache2WritePolicy = cache2WritePolicy;
    }


    /**
     * @return the cache3Associativity
     */
    public String getCache3Associativity() {
        return cache3Associativity;
    }


    /**
     * @param cache3Associativity the cache3Associativity to set
     */
    public void setCache3Associativity(String cache3Associativity) {
        this.cache3Associativity = cache3Associativity;
    }


    /**
     * @return the cache3ErrorMethodology
     */
    public String getCache3ErrorMethodology() {
        return cache3ErrorMethodology;
    }


    /**
     * @param cache3ErrorMethodology the cache3ErrorMethodology to set
     */
    public void setCache3ErrorMethodology(String cache3ErrorMethodology) {
        this.cache3ErrorMethodology = cache3ErrorMethodology;
    }


    /**
     * @return the cache3Level
     */
    public String getCache3Level() {
        return cache3Level;
    }


    /**
     * @param cache3Level the cache3Level to set
     */
    public void setCache3Level(String cache3Level) {
        this.cache3Level = cache3Level;
    }


    /**
     * @return the cache3PrimaryStatus
     */
    public String getCache3PrimaryStatus() {
        return cache3PrimaryStatus;
    }


    /**
     * @param cache3PrimaryStatus the cache3PrimaryStatus to set
     */
    public void setCache3PrimaryStatus(String cache3PrimaryStatus) {
        this.cache3PrimaryStatus = cache3PrimaryStatus;
    }


    /**
     * @return the cache3Size
     */
    public String getCache3Size() {
        return cache3Size;
    }


    /**
     * @param cache3Size the cache3Size to set
     */
    public void setCache3Size(String cache3Size) {
        this.cache3Size = cache3Size;
    }


    /**
     * @return the cache3Type
     */
    public String getCache3Type() {
        return cache3Type;
    }


    /**
     * @param cache3Type the cache3Type to set
     */
    public void setCache3Type(String cache3Type) {
        this.cache3Type = cache3Type;
    }


    /**
     * @return the cache3WritePolicy
     */
    public String getCache3WritePolicy() {
        return cache3WritePolicy;
    }


    /**
     * @param cache3WritePolicy the cache3WritePolicy to set
     */
    public void setCache3WritePolicy(String cache3WritePolicy) {
        this.cache3WritePolicy = cache3WritePolicy;
    }


    /**
     * @return the characteristics
     */
    public String getCharacteristics() {
        return characteristics;
    }


    /**
     * @param characteristics the characteristics to set
     */
    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }


    /**
     * @return the currentClockSpeed
     */
    public String getCurrentClockSpeed() {
        return currentClockSpeed;
    }


    /**
     * @param currentClockSpeed the currentClockSpeed to set
     */
    public void setCurrentClockSpeed(String currentClockSpeed) {
        this.currentClockSpeed = currentClockSpeed;
    }


    /**
     * @return the externalBusClockSpeed
     */
    public String getExternalBusClockSpeed() {
        return externalBusClockSpeed;
    }


    /**
     * @param externalBusClockSpeed the externalBusClockSpeed to set
     */
    public void setExternalBusClockSpeed(String externalBusClockSpeed) {
        this.externalBusClockSpeed = externalBusClockSpeed;
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
     * @return the maxClockSpeed
     */
    public String getMaxClockSpeed() {
        return maxClockSpeed;
    }


    /**
     * @param maxClockSpeed the maxClockSpeed to set
     */
    public void setMaxClockSpeed(String maxClockSpeed) {
        this.maxClockSpeed = maxClockSpeed;
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
     * @return the numberOfEnabledCores
     */
    public String getNumberOfEnabledCores() {
        return numberOfEnabledCores;
    }


    /**
     * @param numberOfEnabledCores the numberOfEnabledCores to set
     */
    public void setNumberOfEnabledCores(String numberOfEnabledCores) {
        this.numberOfEnabledCores = numberOfEnabledCores;
    }


    /**
     * @return the numberOfEnabledThreads
     */
    public String getNumberOfEnabledThreads() {
        return numberOfEnabledThreads;
    }


    /**
     * @param numberOfEnabledThreads the numberOfEnabledThreads to set
     */
    public void setNumberOfEnabledThreads(String numberOfEnabledThreads) {
        this.numberOfEnabledThreads = numberOfEnabledThreads;
    }


    /**
     * @return the numberOfProcessorCores
     */
    public String getNumberOfProcessorCores() {
        return numberOfProcessorCores;
    }


    /**
     * @param numberOfProcessorCores the numberOfProcessorCores to set
     */
    public void setNumberOfProcessorCores(String numberOfProcessorCores) {
        this.numberOfProcessorCores = numberOfProcessorCores;
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
     * @return the voltage
     */
    public String getVoltage() {
        return voltage;
    }


    /**
     * @param voltage the voltage to set
     */
    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }


    public String getCache1Location() {
        return cache1Location;
    }


    public void setCache1Location(String cache1Location) {
        this.cache1Location = cache1Location;
    }


    public String getCache2Location() {
        return cache2Location;
    }


    public void setCache2Location(String cache2Location) {
        this.cache2Location = cache2Location;
    }


    public String getCache3Location() {
        return cache3Location;
    }


    public void setCache3Location(String cache3Location) {
        this.cache3Location = cache3Location;
    }


    public String getCache1SramType() {
        return cache1SramType;
    }


    public void setCache1SramType(String cache1SramType) {
        this.cache1SramType = cache1SramType;
    }


    public String getCache2SramType() {
        return cache2SramType;
    }


    public void setCache2SramType(String cache2SramType) {
        this.cache2SramType = cache2SramType;
    }


    public String getCache3SramType() {
        return cache3SramType;
    }


    public void setCache3SramType(String cache3SramType) {
        this.cache3SramType = cache3SramType;
    }


    public String getDeviceDescription() {
        return deviceDescription;
    }


    public void setDeviceDescription(String deviceDescription) {
        this.deviceDescription = deviceDescription;
    }


    public String getExecuteDisabledCapable() {
        return executeDisabledCapable;
    }


    public void setExecuteDisabledCapable(String executeDisabledCapable) {
        this.executeDisabledCapable = executeDisabledCapable;
    }


    public String getExecuteDisabledEnabled() {
        return executeDisabledEnabled;
    }


    public void setExecuteDisabledEnabled(String executeDisabledEnabled) {
        this.executeDisabledEnabled = executeDisabledEnabled;
    }


    public String getHyperThreadingCapable() {
        return hyperThreadingCapable;
    }


    public void setHyperThreadingCapable(String hyperThreadingCapable) {
        this.hyperThreadingCapable = hyperThreadingCapable;
    }


    public String getHyperThreadingEnabled() {
        return hyperThreadingEnabled;
    }


    public void setHyperThreadingEnabled(String hyperThreadingEnabled) {
        this.hyperThreadingEnabled = hyperThreadingEnabled;
    }


    public String getVirtualizationTechnologyCapable() {
        return virtualizationTechnologyCapable;
    }


    public void setVirtualizationTechnologyCapable(String virtualizationTechnologyCapable) {
        this.virtualizationTechnologyCapable = virtualizationTechnologyCapable;
    }


    public String getVirtualizationTechnologyEnabled() {
        return virtualizationTechnologyEnabled;
    }


    public void setVirtualizationTechnologyEnabled(String virtualizationTechnologyEnabled) {
        this.virtualizationTechnologyEnabled = virtualizationTechnologyEnabled;
    }


    public String getTurboModeCapable() {
        return turboModeCapable;
    }


    public void setTurboModeCapable(String turboModeCapable) {
        this.turboModeCapable = turboModeCapable;
    }


    public String getTurboModeEnabled() {
        return turboModeEnabled;
    }


    public void setTurboModeEnabled(String turboModeEnabled) {
        this.turboModeEnabled = turboModeEnabled;
    }


    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
