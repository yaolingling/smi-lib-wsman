/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.entity.serverhealth;

import java.util.ArrayList;
import java.util.List;

/**
 * class for warranty entity.
 */

public class ServerHealthInfo {
    // public enum
    // Component{POWER_SUPPLY,TEMPERATURE,FANS,VOLTAGES,PROCESSORS,BATTERIES,INTRUSION,MEMORY};

    private List<ComponentHealthInfo> components = new ArrayList<ComponentHealthInfo>();
    private String sourceOfHealth = null;


    public String getSourceOfHealth() {
        return sourceOfHealth;
    }


    public void setSourceOfHealth(String sourceOfHealth) {
        this.sourceOfHealth = sourceOfHealth;
    }


    /**
     * @return the components
     */
    public List<ComponentHealthInfo> getComponents() {
        return components;
    }


    /**
     * @param components the components to set
     */
    public void setComponents(List<ComponentHealthInfo> components) {
        this.components = components;
    }

    private int overallStatus;


    /**
     * @return the overallStatus
     */
    public int getOverallStatus() {
        return overallStatus;
    }


    /**
     * @param overallStatus the overallStatus to set
     */
    public void setOverallStatus(int overallStatus) {
        this.overallStatus = overallStatus;
    }
    /**
     * @return the componentHealth
     */

}
