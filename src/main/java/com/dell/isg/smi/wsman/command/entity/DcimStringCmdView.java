/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.entity;

import java.io.Serializable;

public class DcimStringCmdView implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String attributeName = null;
    private String currentValue = null;
    private String fQDD = null;
    private String instanceID = null;
    private String isReadOnly = null;
    private String maxLength = null;
    private String minLength = null;
    private String pendingValue = null;


    /**
     * @return the attributeName
     */
    public String getAttributeName() {
        return attributeName;
    }


    /**
     * @param attributeName the attributeName to set
     */
    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }


    /**
     * @return the currentValue
     */
    public String getCurrentValue() {
        return currentValue;
    }


    /**
     * @param currentValue the currentValue to set
     */
    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }


    /**
     * @return the fQDD
     */
    public String getfQDD() {
        return fQDD;
    }


    /**
     * @param fQDD the fQDD to set
     */
    public void setfQDD(String fQDD) {
        this.fQDD = fQDD;
    }


    /**
     * @return the instanceID
     */
    public String getInstanceID() {
        return instanceID;
    }


    /**
     * @param instanceID the instanceID to set
     */
    public void setInstanceID(String instanceID) {
        this.instanceID = instanceID;
    }


    /**
     * @return the isReadOnly
     */
    public String getIsReadOnly() {
        return isReadOnly;
    }


    /**
     * @param isReadOnly the isReadOnly to set
     */
    public void setIsReadOnly(String isReadOnly) {
        this.isReadOnly = isReadOnly;
    }


    /**
     * @return the maxLength
     */
    public String getMaxLength() {
        return maxLength;
    }


    /**
     * @param maxLength the maxLength to set
     */
    public void setMaxLength(String maxLength) {
        this.maxLength = maxLength;
    }


    /**
     * @return the minLength
     */
    public String getMinLength() {
        return minLength;
    }


    /**
     * @param minLength the minLength to set
     */
    public void setMinLength(String minLength) {
        this.minLength = minLength;
    }


    /**
     * @return the pendingValue
     */
    public String getPendingValue() {
        return pendingValue;
    }


    /**
     * @param pendingValue the pendingValue to set
     */
    public void setPendingValue(String pendingValue) {
        this.pendingValue = pendingValue;
    }
}
