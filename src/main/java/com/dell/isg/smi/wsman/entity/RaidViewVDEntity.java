/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
/**
 * 
 */
package com.dell.isg.smi.wsman.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anshu_Virk
 *
 */
public class RaidViewVDEntity {

    private String fqddVD;
    private String nameVD;
    private String ObjectStatus;
    private String PhysicalIDsVD[];
    private String raidStatusVD;
    private String raidTypesVD;
    private Long spanLengthVD;
    private Long sizeInBytesVD;
    private int pdCount;

    private List<RaidViewPDEntity> raidCtrlPD = new ArrayList<RaidViewPDEntity>();
    private List<KeyValuePair> raidCtrlDH = new ArrayList<KeyValuePair>();


    public void setFqddVD(String fqddVD) {
        this.fqddVD = fqddVD;
    }


    public String getFqddVD() {
        return fqddVD;
    }


    public void setNameVD(String nameVD) {
        this.nameVD = nameVD;
    }


    public String getNameVD() {
        return nameVD;
    }


    public void setPhysicalIDsVD(String physicalIDsVD[]) {
        PhysicalIDsVD = physicalIDsVD;
    }


    public String[] getPhysicalIDsVD() {
        return PhysicalIDsVD;
    }


    public void setRaidStatusVD(String raidStatusVD) {
        this.raidStatusVD = raidStatusVD;
    }


    public String getRaidStatusVD() {
        return raidStatusVD;
    }


    public void setRaidTypesVD(String raidTypesVD) {
        this.raidTypesVD = raidTypesVD;
    }


    public String getRaidTypesVD() {
        return raidTypesVD;
    }


    public void setSizeInBytesVD(Long sizeInBytesVD) {
        this.sizeInBytesVD = sizeInBytesVD;
    }


    public Long getSizeInBytesVD() {
        return sizeInBytesVD;
    }


    public void setSpanLengthVD(Long spanLength) {
        this.spanLengthVD = spanLength;
    }


    public Long getSpanLengthVD() {
        return spanLengthVD;
    }


    public void setRaidCtrlPD(List<RaidViewPDEntity> raidCtrlPD) {
        this.raidCtrlPD = raidCtrlPD;
    }


    public List<RaidViewPDEntity> getRaidCtrlPD() {
        return raidCtrlPD;
    }


    public void setPdCount(int pdCount) {
        this.pdCount = pdCount;
    }


    public int getPdCount() {
        return pdCount;
    }


    public void setRaidCtrlDH(List<KeyValuePair> raidCtrlDH) {
        this.raidCtrlDH = raidCtrlDH;
    }


    public List<KeyValuePair> getRaidCtrlDH() {
        return raidCtrlDH;
    }


    public void setObjectStatus(String objectStatus) {
        ObjectStatus = objectStatus;
    }


    public String getObjectStatus() {
        return ObjectStatus;
    }

}
