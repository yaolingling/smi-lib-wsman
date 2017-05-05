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
public class RaidViewCtrlEntity {

    private String fqddCtrl;
    private String nameCtrl;
    private String pciSlotCtrl;
    private String secStatusCtrl;
    private int countCtrl;

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


    public void setPciSlotCtrl(String pciSlotCtrl) {
        this.pciSlotCtrl = pciSlotCtrl;
    }


    public String getPciSlotCtrl() {
        return pciSlotCtrl;
    }


    public void setSecStatusCtrl(String secStatusCtrl) {
        this.secStatusCtrl = secStatusCtrl;
    }


    public String getSecStatusCtrl() {
        return secStatusCtrl;
    }


    public void setCountCtrl(int countCtrl) {
        this.countCtrl = countCtrl;
    }


    public int getCountCtrl() {
        return countCtrl;
    }


    public void setRaidCtrlGH(List<String> raidCtrlGH) {
        this.raidCtrlGH = raidCtrlGH;
    }


    public List<String> getRaidCtrlGH() {
        return raidCtrlGH;
    }

}
