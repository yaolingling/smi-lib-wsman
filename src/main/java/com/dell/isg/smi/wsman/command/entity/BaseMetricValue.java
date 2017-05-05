/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.entity;

import java.io.Serializable;

public class BaseMetricValue implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String duration = null;
    private String instanceId = null;
    private String metricDefinitionId = null;
    private String metricValue = null;
    private String timeStamp = null;
    private boolean isVolatile = false;


    public String getDuration() {
        return duration;
    }


    public void setDuration(String duration) {
        this.duration = duration;
    }


    public String getInstanceId() {
        return instanceId;
    }


    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }


    public String getMetricDefinitionId() {
        return metricDefinitionId;
    }


    public void setMetricDefinitionId(String metricDefinitionId) {
        this.metricDefinitionId = metricDefinitionId;
    }


    public String getMetricValue() {
        return metricValue;
    }


    public void setMetricValue(String metricValue) {
        this.metricValue = metricValue;
    }


    public String getTimeStamp() {
        return timeStamp;
    }


    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }


    public boolean isVolatile() {
        return isVolatile;
    }


    public void setVolatile(boolean isVolatile) {
        this.isVolatile = isVolatile;
    }

}
