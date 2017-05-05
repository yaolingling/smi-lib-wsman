/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.entity;

import java.io.Serializable;
import java.util.List;

public class VirtualDiskView implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String blockSizeInBytes = null;
    private String busProtocol = null;
    private String cacheCade = null;
    private String deviceDescription = null;
    private String diskCachePolicy = null;
    private String fQDD = null;
    private String instanceID = null;
    private String lastSystemInventoryTime = null;
    private String lastUpdateTime = null;
    private String lockStatus = null;
    private String mediaType = null;
    private String name = null;
    private String objectStatus = null;
    private String operationName = null;
    private String operationPercentComplete = null;
    private String pendingOperations = null;
    private List<String> physicalDiskIDs = null;
    private String primaryStatus = null;
    private String raidStatus = null;
    private String raidTypes = null;
    private String readCachePolicy = null;
    private String remainingRedundancy = null;
    private String rollupStatus = null;
    private String sizeInBytes = null;
    private String spanDepth = null;
    private String spanLength = null;
    private String startingLBAInBlocks = null;
    private String stripeSize = null;
    private String t10piStatus = null;
    private String virtualDiskTargetID = null;
    private String writeCachePolicy = null;


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
     * @return the cacheCade
     */
    public String getCacheCade() {
        return cacheCade;
    }


    /**
     * @param cacheCade the cacheCade to set
     */
    public void setCacheCade(String cacheCade) {
        this.cacheCade = cacheCade;
    }


    /**
     * @return the diskCachePolicy
     */
    public String getDiskCachePolicy() {
        return diskCachePolicy;
    }


    /**
     * @param diskCachePolicy the diskCachePolicy to set
     */
    public void setDiskCachePolicy(String diskCachePolicy) {
        this.diskCachePolicy = diskCachePolicy;
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
     * @return the lockStatus
     */
    public String getLockStatus() {
        return lockStatus;
    }


    /**
     * @param lockStatus the lockStatus to set
     */
    public void setLockStatus(String lockStatus) {
        this.lockStatus = lockStatus;
    }


    /**
     * @return the name
     */
    public String getName() {
        return name;
    }


    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * @return the objectStatus
     */
    public String getObjectStatus() {
        return objectStatus;
    }


    /**
     * @param objectStatus the objectStatus to set
     */
    public void setObjectStatus(String objectStatus) {
        this.objectStatus = objectStatus;
    }


    /**
     * @return the physicalDiskIDs
     */
    public List<String> getPhysicalDiskIDs() {
        return physicalDiskIDs;
    }


    /**
     * @param physicalDiskIDs the physicalDiskIDs to set
     */
    public void setPhysicalDiskIDs(List<String> physicalDiskIDs) {
        this.physicalDiskIDs = physicalDiskIDs;
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
     * @return the readCachePolicy
     */
    public String getReadCachePolicy() {
        return readCachePolicy;
    }


    /**
     * @param readCachePolicy the readCachePolicy to set
     */
    public void setReadCachePolicy(String readCachePolicy) {
        this.readCachePolicy = readCachePolicy;
    }


    /**
     * @return the remainingRedundancy
     */
    public String getRemainingRedundancy() {
        return remainingRedundancy;
    }


    /**
     * @param remainingRedundancy the remainingRedundancy to set
     */
    public void setRemainingRedundancy(String remainingRedundancy) {
        this.remainingRedundancy = remainingRedundancy;
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
     * @return the spanDepth
     */
    public String getSpanDepth() {
        return spanDepth;
    }


    /**
     * @param spanDepth the spanDepth to set
     */
    public void setSpanDepth(String spanDepth) {
        this.spanDepth = spanDepth;
    }


    /**
     * @return the spanLength
     */
    public String getSpanLength() {
        return spanLength;
    }


    /**
     * @param spanLength the spanLength to set
     */
    public void setSpanLength(String spanLength) {
        this.spanLength = spanLength;
    }


    /**
     * @return the startingLBAInBlocks
     */
    public String getStartingLBAInBlocks() {
        return startingLBAInBlocks;
    }


    /**
     * @param startingLBAInBlocks the startingLBAInBlocks to set
     */
    public void setStartingLBAInBlocks(String startingLBAInBlocks) {
        this.startingLBAInBlocks = startingLBAInBlocks;
    }


    /**
     * @return the stripeSize
     */
    public String getStripeSize() {
        return stripeSize;
    }


    /**
     * @param stripeSize the stripeSize to set
     */
    public void setStripeSize(String stripeSize) {
        this.stripeSize = stripeSize;
    }


    /**
     * @return the virtualDiskTargetID
     */
    public String getVirtualDiskTargetID() {
        return virtualDiskTargetID;
    }


    /**
     * @param virtualDiskTargetID the virtualDiskTargetID to set
     */
    public void setVirtualDiskTargetID(String virtualDiskTargetID) {
        this.virtualDiskTargetID = virtualDiskTargetID;
    }


    /**
     * @return the writeCachePolicy
     */
    public String getWriteCachePolicy() {
        return writeCachePolicy;
    }


    /**
     * @param writeCachePolicy the writeCachePolicy to set
     */
    public void setWriteCachePolicy(String writeCachePolicy) {
        this.writeCachePolicy = writeCachePolicy;
    }


    /**
     * @param raidTypes the raidTypes to set
     */
    public void setRaidTypes(String raidTypes) {
        this.raidTypes = raidTypes;
    }


    /**
     * @return the raidTypes
     */
    public String getRaidTypes() {
        return raidTypes;
    }


    public String getDeviceDescription() {
        return deviceDescription;
    }


    public void setDeviceDescription(String deviceDescription) {
        this.deviceDescription = deviceDescription;
    }


    public String getT10piStatus() {
        return t10piStatus;
    }


    public void setT10piStatus(String t10piStatus) {
        this.t10piStatus = t10piStatus;
    }


    public String getBlockSizeInBytes() {
        return blockSizeInBytes;
    }


    public void setBlockSizeInBytes(String blockSizeInBytes) {
        this.blockSizeInBytes = blockSizeInBytes;
    }


    public String getOperationName() {
        return operationName;
    }


    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }


    public String getOperationPercentComplete() {
        return operationPercentComplete;
    }


    public void setOperationPercentComplete(String operationPercentComplete) {
        this.operationPercentComplete = operationPercentComplete;
    }


    public String getPendingOperations() {
        return pendingOperations;
    }


    public void setPendingOperations(String pendingOperations) {
        this.pendingOperations = pendingOperations;
    }


    public String getRollupStatus() {
        return rollupStatus;
    }


    public void setRollupStatus(String rollupStatus) {
        this.rollupStatus = rollupStatus;
    }

}
