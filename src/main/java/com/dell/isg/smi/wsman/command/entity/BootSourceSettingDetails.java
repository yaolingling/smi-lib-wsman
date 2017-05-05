/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.entity;

public class BootSourceSettingDetails {

    private String biosBootString;
    private String bootString;
    private int currentSequence;
    private int currentEnabledState;
    private String elementName;
    private int failThroughSupported;
    private String instanceId;
    private int pendingAssignedSequence;
    private int pendingEnabledState;


    public String getBiosBootString() {
        return biosBootString;
    }


    public void setBiosBootString(String biosBootString) {
        this.biosBootString = biosBootString;
    }


    public String getBootString() {
        return bootString;
    }


    public void setBootString(String bootString) {
        this.bootString = bootString;
    }


    public int getCurrentSequence() {
        return currentSequence;
    }


    public void setCurrentSequence(int currentSequence) {
        this.currentSequence = currentSequence;
    }


    public int getCurrentEnabledState() {
        return currentEnabledState;
    }


    public void setCurrentEnabledState(int currentEnabledState) {
        this.currentEnabledState = currentEnabledState;
    }


    public String getElementName() {
        return elementName;
    }


    public void setElementName(String elementName) {
        this.elementName = elementName;
    }


    public int getFailThroughSupported() {
        return failThroughSupported;
    }


    public void setFailThroughSupported(int failThroughSupported) {
        this.failThroughSupported = failThroughSupported;
    }


    public String getInstanceId() {
        return instanceId;
    }


    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }


    public int getPendingAssignedSequence() {
        return pendingAssignedSequence;
    }


    public void setPendingAssignedSequence(int pendingAssignedSequence) {
        this.pendingAssignedSequence = pendingAssignedSequence;
    }


    public int getPendingEnabledState() {
        return pendingEnabledState;
    }


    public void setPendingEnabledState(int pendingEnabledState) {
        this.pendingEnabledState = pendingEnabledState;
    }

}
