/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.entity;

import java.io.Serializable;

public class NICStatistics implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String linkStatus;
    private String fqdd;
    private String osDriverState;


    /**
     * @return the linkStatus
     */
    public String getLinkStatus() {
        return linkStatus;
    }


    /**
     * @param linkStatus the linkStatus to set
     */
    public void setLinkStatus(String linkStatus) {
        this.linkStatus = linkStatus;
    }


    /**
     * @return the fqdd
     */
    public String getFqdd() {
        return fqdd;
    }


    /**
     * @param fqdd the fqdd to set
     */
    public void setFqdd(String fqdd) {
        this.fqdd = fqdd;
    }


    public String getOsDriverState() {
        return osDriverState;
    }


    public void setOsDriverState(String osDriverState) {
        this.osDriverState = osDriverState;
    }
}
