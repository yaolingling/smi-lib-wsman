/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.entity;

import java.io.Serializable;

public class EnclosureView implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String assetTag = null;
    private String connector = null;
    private String eMMCount = null;
    private String fQDD = null;
    private String fanCount = null;
    private String instanceID = null;
    private String lastSystemInventoryTime = null;
    private String lastUpdateTime = null;
    private String pSUCount = null;
    private String primaryStatus = null;
    private String productName = null;
    private String rollupStatus = null;
    private String serviceTag = null;
    private String slotCount = null;
    private String tempProbeCount = null;
    private String version = null;
    private String wiredOrder = null;
    private String state = null;
    private String deviceDescription = null;


    /**
     * @return the assetTag
     */
    public String getAssetTag() {
        return assetTag;
    }


    /**
     * @param assetTag the assetTag to set
     */
    public void setAssetTag(String assetTag) {
        this.assetTag = assetTag;
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
     * @return the eMMCount
     */
    public String geteMMCount() {
        return eMMCount;
    }


    /**
     * @param eMMCount the eMMCount to set
     */
    public void seteMMCount(String eMMCount) {
        this.eMMCount = eMMCount;
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
     * @return the fanCount
     */
    public String getFanCount() {
        return fanCount;
    }


    /**
     * @param fanCount the fanCount to set
     */
    public void setFanCount(String fanCount) {
        this.fanCount = fanCount;
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
     * @return the pSUCount
     */
    public String getpSUCount() {
        return pSUCount;
    }


    /**
     * @param pSUCount the pSUCount to set
     */
    public void setpSUCount(String pSUCount) {
        this.pSUCount = pSUCount;
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
     * @return the rollupStatus
     */
    public String getRollupStatus() {
        return rollupStatus;
    }


    /**
     * @param rollupStatus the rollupStatus to set
     */
    public void setRollupStatus(String rollupStatus) {
        this.rollupStatus = rollupStatus;
    }


    /**
     * @return the serviceTag
     */
    public String getServiceTag() {
        return serviceTag;
    }


    /**
     * @param serviceTag the serviceTag to set
     */
    public void setServiceTag(String serviceTag) {
        this.serviceTag = serviceTag;
    }


    /**
     * @return the slotCount
     */
    public String getSlotCount() {
        return slotCount;
    }


    /**
     * @param slotCount the slotCount to set
     */
    public void setSlotCount(String slotCount) {
        this.slotCount = slotCount;
    }


    /**
     * @return the tempProbeCount
     */
    public String getTempProbeCount() {
        return tempProbeCount;
    }


    /**
     * @param tempProbeCount the tempProbeCount to set
     */
    public void setTempProbeCount(String tempProbeCount) {
        this.tempProbeCount = tempProbeCount;
    }


    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }


    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }


    /**
     * @return the wiredOrder
     */
    public String getWiredOrder() {
        return wiredOrder;
    }


    /**
     * @param wiredOrder the wiredOrder to set
     */
    public void setWiredOrder(String wiredOrder) {
        this.wiredOrder = wiredOrder;
    }


    public String getState() {
        return state;
    }


    public void setState(String state) {
        this.state = state;
    }


    public String getDeviceDescription() {
        return deviceDescription;
    }


    public void setDeviceDescription(String deviceDescription) {
        this.deviceDescription = deviceDescription;
    }

}
