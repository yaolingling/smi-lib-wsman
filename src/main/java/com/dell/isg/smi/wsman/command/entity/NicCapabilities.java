/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.entity;

import java.io.Serializable;

public class NicCapabilities implements Serializable {

    private static final long serialVersionUID = 1L;

    private String iscsiBootSupport;
    private String fqdd;
    private String fcoeBootSupport;
    private String pxeBootSupport;


    public String getIscsiBootSupport() {
        return iscsiBootSupport;
    }


    public void setIscsiBootSupport(String iscsiBootSupport) {
        this.iscsiBootSupport = iscsiBootSupport;
    }


    public String getFcoeBootSupport() {
        return fcoeBootSupport;
    }


    public void setFcoeBootSupport(String fcoeBootSupport) {
        this.fcoeBootSupport = fcoeBootSupport;
    }


    public String getPxeBootSupport() {
        return pxeBootSupport;
    }


    public void setPxeBootSupport(String pxeBootSupport) {
        this.pxeBootSupport = pxeBootSupport;
    }


    public String getFqdd() {
        return fqdd;
    }


    public void setFqdd(String fqdd) {
        this.fqdd = fqdd;
    }

}
