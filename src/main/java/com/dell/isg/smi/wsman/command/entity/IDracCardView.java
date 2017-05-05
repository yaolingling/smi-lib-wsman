/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.entity;

import java.io.Serializable;

public class IDracCardView implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String fqdd = null;
    private String firmwareVersion = null;
    private String guid = null;
    private String iPMIVersion = null;
    private String instanceID = null;
    private String lANEnabledState = null;
    private String lastSystemInventoryTime = null;
    private String lastUpdateTime = null;
    private String model = null;
    private String permanentMACAddress = null;
    private String productDescription = null;
    private String solEnabledState = null;
    private String urlString = null;


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


    /**
     * @return the firmwareVersion
     */
    public String getFirmwareVersion() {
        return firmwareVersion;
    }


    /**
     * @param firmwareVersion the firmwareVersion to set
     */
    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }


    /**
     * @return the guid
     */
    public String getGuid() {
        return guid;
    }


    /**
     * @param guid the guid to set
     */
    public void setGuid(String guid) {
        this.guid = guid;
    }


    /**
     * @return the iPMIVersion
     */
    public String getiPMIVersion() {
        return iPMIVersion;
    }


    /**
     * @param iPMIVersion the iPMIVersion to set
     */
    public void setiPMIVersion(String iPMIVersion) {
        this.iPMIVersion = iPMIVersion;
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
     * @return the lANEnabledState
     */
    public String getlANEnabledState() {
        return lANEnabledState;
    }


    /**
     * @param lANEnabledState the lANEnabledState to set
     */
    public void setlANEnabledState(String lANEnabledState) {
        this.lANEnabledState = lANEnabledState;
    }


    /**
     * @return the lastSystemInventoryTime
     */
    public String getLastSystemInventoryTime() {
        return lastSystemInventoryTime;
    }


    /**
     * @param lastSystemInventoryTime the lastSystemInventoryTime to set
     */
    public void setLastSystemInventoryTime(String lastSystemInventoryTime) {
        this.lastSystemInventoryTime = lastSystemInventoryTime;
    }


    /**
     * @return the lastUpdateTime
     */
    public String getLastUpdateTime() {
        return lastUpdateTime;
    }


    /**
     * @param lastUpdateTime the lastUpdateTime to set
     */
    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }


    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }


    /**
     * @param model the model to set
     */
    public void setModel(String model) {
        this.model = model;
    }


    /**
     * @return the permanentMACAddress
     */
    public String getPermanentMACAddress() {
        return permanentMACAddress;
    }


    /**
     * @param permanentMACAddress the permanentMACAddress to set
     */
    public void setPermanentMACAddress(String permanentMACAddress) {
        this.permanentMACAddress = permanentMACAddress;
    }


    /**
     * @return the productDescription
     */
    public String getProductDescription() {
        return productDescription;
    }


    /**
     * @param productDescription the productDescription to set
     */
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }


    /**
     * @return the solEnabledState
     */
    public String getSolEnabledState() {
        return solEnabledState;
    }


    /**
     * @param solEnabledState the solEnabledState to set
     */
    public void setSolEnabledState(String solEnabledState) {
        this.solEnabledState = solEnabledState;
    }


    /**
     * @return the urlString
     */
    public String getUrlString() {
        return urlString;
    }


    /**
     * @param urlString the urlString to set
     */
    public void setUrlString(String urlString) {
        this.urlString = urlString;
    }

}
