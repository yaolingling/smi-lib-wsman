/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.entity;

public class BootConfigSetting {

    private String elementName;
    private String instanceID;
    private String isCurrent;
    private String isDefault;
    private String isNext;


    public String getElementName() {
        return elementName;
    }


    public void setElementName(String elementName) {
        this.elementName = elementName;
    }


    public String getInstanceID() {
        return instanceID;
    }


    public void setInstanceID(String instanceID) {
        this.instanceID = instanceID;
    }


    public String getIsCurrent() {
        return isCurrent;
    }


    public void setIsCurrent(String isCurrent) {
        this.isCurrent = isCurrent;
    }


    public String getIsDefault() {
        return isDefault;
    }


    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }


    public String getIsNext() {
        return isNext;
    }


    public void setIsNext(String isNext) {
        this.isNext = isNext;
    }
}
