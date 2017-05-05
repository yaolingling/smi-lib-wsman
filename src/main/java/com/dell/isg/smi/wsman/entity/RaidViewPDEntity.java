/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
/**
 * 
 */
package com.dell.isg.smi.wsman.entity;

/**
 * @author Umer_Shabbir
 * @author Anshu_Virk
 *
 */
public class RaidViewPDEntity {

    private String fdqqPD;
    private String fullFdqqPD;
    private String physicalID;
    private Long sizeInBytesPD;
    private String assignSparePD;
    private String busProtocolPD;
    private String mediaTypePD;
    private String diskSerialNumber;
    private String raidStatus;


    public void setFdqqPD(String fdqqPD) {
        this.fdqqPD = fdqqPD;
    }


    public String getFdqqPD() {
        return fdqqPD;
    }


    public void setSizeInBytesPD(Long sizeInBytesPD) {
        this.sizeInBytesPD = sizeInBytesPD;
    }


    public Long getSizeInBytesPD() {
        return sizeInBytesPD;
    }


    public void setAssignSparePD(String assignSparePD) {
        this.assignSparePD = assignSparePD;
    }


    public String getAssignSparePD() {
        return assignSparePD;
    }


    public void setBusProtocolPD(String busProtocolPD) {
        this.busProtocolPD = busProtocolPD;
    }


    public String getBusProtocolPD() {
        return busProtocolPD;
    }


    public void setMediaTypePD(String mediaTypePD) {
        this.mediaTypePD = mediaTypePD;
    }


    public String getMediaTypePD() {
        return mediaTypePD;
    }


    public void setPhysicalID(String physicalID) {
        this.physicalID = physicalID;
    }


    public String getPhysicalID() {
        return physicalID;
    }


    public void setFullFdqqPD(String fullFdqqPD) {
        this.fullFdqqPD = fullFdqqPD;
    }


    public String getFullFdqqPD() {
        return fullFdqqPD;
    }


    public String getDiskSerialNumber() {
        return diskSerialNumber;
    }


    public void setDiskSerialNumber(String diskSerialNumber) {
        this.diskSerialNumber = diskSerialNumber;
    }


    public String getRaidStatus() {
        return raidStatus;
    }


    public void setRaidStatus(String raidStatus) {
        this.raidStatus = raidStatus;
    }
}
