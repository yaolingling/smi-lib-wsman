/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.entity.update;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.dell.isg.smi.wsman.utilities.Utilities;

public class UpdateFileInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    String sourceFilePath = "unknown";


    public String getSourceFilePath() {
        return sourceFilePath;
    }


    public void setSourceFilePath(String sourceFilePath) {
        this.sourceFilePath = sourceFilePath;
    }

    String sourceUser = "unknown";


    public String getSourceUser() {
        return sourceUser;
    }


    public void setSourceUser(String sourceUser) {
        this.sourceUser = sourceUser;
    }

    String sourcePassword = "unknown";


    public String getSourcePassword() {
        return sourcePassword;
    }


    public void setSourcePassword(String sourcePassword) {
        this.sourcePassword = sourcePassword;
    }

    List<UpdateDevice> devices = new ArrayList<UpdateDevice>();


    public List<UpdateDevice> getDevices() {
        return devices;
    }


    public void setDevices(List<UpdateDevice> devices) {
        this.devices = devices;
    }


    public void appendDevice(UpdateDevice device) {
        this.devices.add(device);
    }

    List<UpdatePlatform> platforms = new ArrayList<UpdatePlatform>();


    public List<UpdatePlatform> getPlatforms() {
        return platforms;
    }


    public void setPlatforms(List<UpdatePlatform> platforms) {
        this.platforms = platforms;
    }


    public void appendPlatform(UpdatePlatform platform) {
        this.platforms.add(platform);
    }


    public void appendPlatforms(List<UpdatePlatform> platforms) {
        for (UpdatePlatform platform : platforms) {
            this.platforms.add(platform);
        }
    }

    String criticality = "N/A";


    public String getCriticality() {
        return criticality;
    }


    public void setCriticality(String criticality) {
        this.criticality = criticality;
    }

    String device = "N/A";


    public String getDevice() {
        return device;
    }


    public void setDevice(String device) {
        this.device = device;
    }

    String componentType = "N/A";


    public String getComponentType() {
        return componentType;
    }


    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    String componentTypeDisplay = "N/A";


    public String getComponentTypeDisplay() {
        return componentTypeDisplay;
    }


    public void setComponentTypeDisplay(String componentTypeDisplay) {
        this.componentTypeDisplay = componentTypeDisplay;
    }

    String description = "N/A";


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    String releaseID = "N/A";


    public String getReleaseID() {
        return releaseID;
    }


    public void setReleaseID(String releaseID) {
        this.releaseID = releaseID;
    }

    String releaseDate = "N/A";


    public String getReleaseDate() {
        return releaseDate;
    }


    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    String version = "0.0";


    public String getVersion() {
        return version;
    }


    public void setVersion(String version) {
        this.version = version;
    }

    String packageType = "N/A";


    public String getPackageType() {
        return packageType;
    }


    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof UpdateFileInfo) {
            UpdateFileInfo info = (UpdateFileInfo) obj;
            try {
                return Utilities.getFileName(info.getSourceFilePath()).equalsIgnoreCase(Utilities.getFileName(this.getSourceFilePath()));
            } catch (MalformedURLException e) {
                return false;
            }
        } else {
            return false;
        }

    }
}
