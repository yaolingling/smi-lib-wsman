/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.entity;

import java.io.Serializable;

public class SoftwareIdentity implements Cloneable, Serializable {

    private static final long serialVersionUID = 5886980529255451962L;

    private long id;

    private Object device; // FIX ME for ICEE
    private String buildNumber = "";


    public String getBuildNumber() {
        return buildNumber;
    }


    public void setBuildNumber(String buildNumber) {
        this.buildNumber = buildNumber;
    }


    public String getClassifications() {
        return Classifications;
    }


    public void setClassifications(String classifications) {
        Classifications = classifications;
    }


    public String getComponentID() {
        return componentID;
    }


    public void setComponentID(String componentID) {
        this.componentID = componentID;
    }


    public String getComponentType() {
        return componentType;
    }


    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public String getDetailedStatus() {
        return detailedStatus;
    }


    public void setDetailedStatus(String detailedStatus) {
        this.detailedStatus = detailedStatus;
    }


    public String getDeviceID() {
        return deviceID;
    }


    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }


    public String getElementName() {
        return elementName;
    }


    public void setElementName(String elementName) {
        this.elementName = elementName;
    }


    public String getIdentityInfoValue() {
        return identityInfoValue;
    }


    public void setIdentityInfoValue(String identityInfoValue) {
        this.identityInfoValue = identityInfoValue;
    }


    public String getInstallDate() {
        return installDate;
    }


    public void setInstallDate(String installDate) {
        this.installDate = installDate;
    }


    public String getInstallationDate() {
        return installationDate;
    }


    public void setInstallationDate(String installationDate) {
        this.installationDate = installationDate;
    }


    public String getInstanceID() {
        return instanceID;
    }


    public void setInstanceID(String instanceID) {
        this.instanceID = instanceID;
    }


    public String getIsEntity() {
        return isEntity;
    }


    public void setIsEntity(String isEntity) {
        this.isEntity = isEntity;
    }


    public String getMajorVersion() {
        return majorVersion;
    }


    public void setMajorVersion(String majorVersion) {
        this.majorVersion = majorVersion;
    }


    public String getMinorVersion() {
        return minorVersion;
    }


    public void setMinorVersion(String minorVersion) {
        this.minorVersion = minorVersion;
    }


    public String getReleaseDate() {
        return releaseDate;
    }


    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }


    public String getRevisionNumber() {
        return revisionNumber;
    }


    public void setRevisionNumber(String revisionNumber) {
        this.revisionNumber = revisionNumber;
    }


    public String getRevisionString() {
        return revisionString;
    }


    public void setRevisionString(String revisionString) {
        this.revisionString = revisionString;
    }


    public String getSerialNumber() {
        return serialNumber;
    }


    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public String getSubDeviceID() {
        return subDeviceID;
    }


    public void setSubDeviceID(String subDeviceID) {
        this.subDeviceID = subDeviceID;
    }


    public String getSubVendorID() {
        return subVendorID;
    }


    public void setSubVendorID(String subVendorID) {
        this.subVendorID = subVendorID;
    }


    public String getTargetOSTypes() {
        return targetOSTypes;
    }


    public void setTargetOSTypes(String targetOSTypes) {
        this.targetOSTypes = targetOSTypes;
    }


    public String getTargetOperatingSystems() {
        return targetOperatingSystems;
    }


    public void setTargetOperatingSystems(String rargetOperatingSystems) {
        this.targetOperatingSystems = rargetOperatingSystems;
    }


    public String getVendorID() {
        return vendorID;
    }


    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }


    public String getVersionString() {
        return versionString;
    }


    public void setVersionString(String versionString) {
        this.versionString = versionString;
    }

    private String Classifications = "";

    private String componentID = "";

    private String componentType = "";

    private String description = "";

    private String detailedStatus = "";

    private String deviceID = "";

    private String elementName = "";

    private String identityInfoValue = "";

    private String installDate = "";

    private String installationDate = "";

    private String instanceID = "";

    private String isEntity = "";

    private String majorVersion = "";

    private String minorVersion = "";

    private String releaseDate = "";

    private String revisionNumber = "";

    private String revisionString = "";

    private String serialNumber = "";

    private String status = "";

    private String subDeviceID = "";

    private String subVendorID = "";

    private String targetOSTypes = "";

    private String targetOperatingSystems = "";

    private String vendorID = "";

    private String versionString = "";

    private String manufacturer = "";

    private String nextVersion = "N/A";

    // not saving updateable for 1.7 release

    private boolean updateable = true;


    public boolean isUpdateable() {
        return updateable;
    }


    public void setUpdateable(boolean updateable) {
        this.updateable = updateable;
    }

    // Would store the job id of the iDRAC job if this device is currently updating

    private String jobIdIfScheduled = "";


    public String getJobIdIfScheduled() {
        return jobIdIfScheduled;
    }


    public void setJobIdIfScheduled(String jobIdIfScheduled) {
        this.jobIdIfScheduled = jobIdIfScheduled;
    }


    public String getNextVersion() {
        return nextVersion;
    }


    public void setNextVersion(String nextVersion) {
        this.nextVersion = nextVersion;
    }


    public String getManufacturer() {
        return manufacturer;
    }


    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }


    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {

        }
        return null;

    }


    @Override
    public boolean equals(Object object) {
        if (object != null && object instanceof SoftwareIdentity) {
            SoftwareIdentity identity = (SoftwareIdentity) object;
            return this.instanceID.equals(identity.getInstanceID());
        }
        return false;
    }


    /**
     * @return the id
     */
    public long getId() {
        return id;
    }


    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }


    /**
     * @return the device
     */
    public Object getDevice() {
        return device;
    }


    /**
     * @param device the device to set
     */
    public void setDevice(Object device) {
        this.device = device;
    }
}
