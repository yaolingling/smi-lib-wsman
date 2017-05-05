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
public class RaidConfigDetails {

    private String fqddCtrl;
    private String nameCtrl;
    private String locationCtrl;
    private String secStatusCtrl;
    private int countPD;

    private List<RaidViewVDEntity> raidCtrlVD = new ArrayList<RaidViewVDEntity>();
    private List<String> raidCtrlGH = new ArrayList<String>();


    public void setFqddCtrl(String fqddCtrl) {
        this.fqddCtrl = fqddCtrl;
    }


    public String getFqddCtrl() {
        return fqddCtrl;
    }


    public void setNameCtrl(String nameCtrl) {
        this.nameCtrl = nameCtrl;
    }


    public String getNameCtrl() {
        return nameCtrl;
    }


    public void setLocationCtrl(String locationCtrl) {
        this.locationCtrl = locationCtrl;
    }


    public String getLocationCtrl() {
        return locationCtrl;
    }


    public void setSecStatusCtrl(String secStatusCtrl) {
        this.secStatusCtrl = secStatusCtrl;
    }


    public String getSecStatusCtrl() {
        return secStatusCtrl;
    }


    public void setCountPD(int countPD) {
        this.countPD = countPD;
    }


    public int getCountPD() {
        return countPD;
    }


    public void setRaidCtrlVD(List<RaidViewVDEntity> raidCtrlVD) {
        this.raidCtrlVD = raidCtrlVD;
    }


    public List<RaidViewVDEntity> getRaidCtrlVD() {
        return raidCtrlVD;
    }


    public void setRaidCtrlGH(List<String> raidCtrlGH) {
        this.raidCtrlGH = raidCtrlGH;
    }


    public List<String> getRaidCtrlGH() {
        return raidCtrlGH;
    }

}
