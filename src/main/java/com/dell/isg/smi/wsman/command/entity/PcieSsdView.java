/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.entity;

import java.io.Serializable;

public class PcieSsdView implements Serializable {

    private static final long serialVersionUID = 1L;

    private String instanceId;
    private String fqdd;
    private String primaryStatus;
    private String model;
    private String state;
    private String sizeInBytes;
    private String busProtocol;
    private String deviceProtocol;
    private String driverVersion;
    private String manufacturer;
    private String mediaType;
    private String productId;
    private String serialNumber;
    private String pcieNegotiatedLinkWidth;
    private String pcieCapableLinkWidth;
    private String maxCapableSpeed;
    private String negotiatedSpeed;
    private String driveFormFactor;
    private String revision;
    private String deviceLifeStatus;
    private String remainingRatedWriteEndurance;
    private String failurePredicted;


    public String getInstanceId() {
        return instanceId;
    }


    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }


    public String getFqdd() {
        return fqdd;
    }


    public void setFqdd(String fqdd) {
        this.fqdd = fqdd;
    }


    public String getPrimaryStatus() {
        return primaryStatus;
    }


    public void setPrimaryStatus(String primaryStatus) {
        this.primaryStatus = primaryStatus;
    }


    public String getModel() {
        return model;
    }


    public void setModel(String model) {
        this.model = model;
    }


    public String getState() {
        return state;
    }


    public void setState(String state) {
        this.state = state;
    }


    public String getSizeInBytes() {
        return sizeInBytes;
    }


    public void setSizeInBytes(String sizeInBytes) {
        this.sizeInBytes = sizeInBytes;
    }


    public String getBusProtocol() {
        return busProtocol;
    }


    public void setBusProtocol(String busProtocol) {
        this.busProtocol = busProtocol;
    }


    public String getDeviceProtocol() {
        return deviceProtocol;
    }


    public void setDeviceProtocol(String deviceProtocol) {
        this.deviceProtocol = deviceProtocol;
    }


    public String getDriverVersion() {
        return driverVersion;
    }


    public void setDriverVersion(String driverVersion) {
        this.driverVersion = driverVersion;
    }


    public String getManufacturer() {
        return manufacturer;
    }


    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }


    public String getMediaType() {
        return mediaType;
    }


    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }


    public String getProductId() {
        return productId;
    }


    public void setProductId(String productId) {
        this.productId = productId;
    }


    public String getSerialNumber() {
        return serialNumber;
    }


    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }


    public String getPcieNegotiatedLinkWidth() {
        return pcieNegotiatedLinkWidth;
    }


    public void setPcieNegotiatedLinkWidth(String pcieNegotiatedLinkWidth) {
        this.pcieNegotiatedLinkWidth = pcieNegotiatedLinkWidth;
    }


    public String getPcieCapableLinkWidth() {
        return pcieCapableLinkWidth;
    }


    public void setPcieCapableLinkWidth(String pcieCapableLinkWidth) {
        this.pcieCapableLinkWidth = pcieCapableLinkWidth;
    }


    public String getMaxCapableSpeed() {
        return maxCapableSpeed;
    }


    public void setMaxCapableSpeed(String maxCapableSpeed) {
        this.maxCapableSpeed = maxCapableSpeed;
    }


    public String getNegotiatedSpeed() {
        return negotiatedSpeed;
    }


    public void setNegotiatedSpeed(String negotiatedSpeed) {
        this.negotiatedSpeed = negotiatedSpeed;
    }


    public String getDriveFormFactor() {
        return driveFormFactor;
    }


    public void setDriveFormFactor(String driveFormFactor) {
        this.driveFormFactor = driveFormFactor;
    }


    public String getRevision() {
        return revision;
    }


    public void setRevision(String revision) {
        this.revision = revision;
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


    public String getFailurePredicted() {
        return failurePredicted;
    }


    public void setFailurePredicted(String failurePredicted) {
        this.failurePredicted = failurePredicted;
    }
}
