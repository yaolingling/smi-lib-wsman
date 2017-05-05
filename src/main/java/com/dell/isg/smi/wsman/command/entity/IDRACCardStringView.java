/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.entity;

import java.io.Serializable;

public class IDRACCardStringView implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String attributeDisplayName = null;
    private String attributeName = null;
    private String currentValue = null;
    private String defaultValue = null;
    private String dependency = null;
    private String displayOrder = null;
    private String fQDD = null;
    private String groupDisplayName = null;
    private String groupID = null;
    private String instanceID = null;
    private String isReadOnly = null;
    private String maxLength = null;
    private String minLength = null;
    private String pendingValue = null;


    /**
     * @return the attributeDisplayName
     */
    public String getAttributeDisplayName() {
        return attributeDisplayName;
    }


    /**
     * @param attributeDisplayName the attributeDisplayName to set
     */
    public void setAttributeDisplayName(String attributeDisplayName) {
        this.attributeDisplayName = attributeDisplayName;
    }


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
     * @return the defaultValue
     */
    public String getDefaultValue() {
        return defaultValue;
    }


    /**
     * @param defaultValue the defaultValue to set
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }


    /**
     * @return the dependency
     */
    public String getDependency() {
        return dependency;
    }


    /**
     * @param dependency the dependency to set
     */
    public void setDependency(String dependency) {
        this.dependency = dependency;
    }


    /**
     * @return the displayOrder
     */
    public String getDisplayOrder() {
        return displayOrder;
    }


    /**
     * @param displayOrder the displayOrder to set
     */
    public void setDisplayOrder(String displayOrder) {
        this.displayOrder = displayOrder;
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
     * @return the groupDisplayName
     */
    public String getGroupDisplayName() {
        return groupDisplayName;
    }


    /**
     * @param groupDisplayName the groupDisplayName to set
     */
    public void setGroupDisplayName(String groupDisplayName) {
        this.groupDisplayName = groupDisplayName;
    }


    /**
     * @return the groupID
     */
    public String getGroupID() {
        return groupID;
    }


    /**
     * @param groupID the groupID to set
     */
    public void setGroupID(String groupID) {
        this.groupID = groupID;
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
