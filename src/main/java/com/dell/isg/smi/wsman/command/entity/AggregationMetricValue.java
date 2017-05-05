/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.entity;

import java.io.Serializable;

public class AggregationMetricValue implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String aggegationDuration = null;
    private String aggregationTimestamp = null;
    private String breakdownDimension = null;
    private String breakdownValue = null;
    private String instanceId = null;
    private String metricDefinitionId = null;
    private String metricValue = null;
    private String timestamp = null;
    private boolean isVolatile = false;


    public String getAggegationDuration() {
        return aggegationDuration;
    }


    public void setAggegationDuration(String aggegationDuration) {
        this.aggegationDuration = aggegationDuration;
    }


    public String getAggregationTimestamp() {
        return aggregationTimestamp;
    }


    public void setAggregationTimestamp(String aggregationTimestamp) {
        this.aggregationTimestamp = aggregationTimestamp;
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


    public String getTimestamp() {
        return timestamp;
    }


    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


    public boolean isVolatile() {
        return isVolatile;
    }


    public void setVolatile(boolean isVolatile) {
        this.isVolatile = isVolatile;
    }


    public String getBreakdownDimension() {
        return breakdownDimension;
    }


    public void setBreakdownDimension(String breakdownDimension) {
        this.breakdownDimension = breakdownDimension;
    }


    public String getBreakdownValue() {
        return breakdownValue;
    }


    public void setBreakdownValue(String breakdownValue) {
        this.breakdownValue = breakdownValue;
    }

}
