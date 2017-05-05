/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.entity;

import java.io.Serializable;

public class SelLogEntry implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String creationTimeStamp = null;

    private String elementName = null;

    private String instanceID = null;

    private String logInstanceID = null;

    private String logName = null;

    private String perceivedSeverity = null;

    private String recordData = null;

    private String recordFormat = null;

    private String recordID = null;


    /**
     * @return the creationTimeStamp
     */
    public String getCreationTimeStamp() {
        return creationTimeStamp;
    }


    /**
     * @param creationTimeStamp the creationTimeStamp to set
     */
    public void setCreationTimeStamp(String creationTimeStamp) {
        this.creationTimeStamp = creationTimeStamp;
    }


    /**
     * @return the elementName
     */
    public String getElementName() {
        return elementName;
    }


    /**
     * @param elementName the elementName to set
     */
    public void setElementName(String elementName) {
        this.elementName = elementName;
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
     * @return the logInstanceID
     */
    public String getLogInstanceID() {
        return logInstanceID;
    }


    /**
     * @param logInstanceID the logInstanceID to set
     */
    public void setLogInstanceID(String logInstanceID) {
        this.logInstanceID = logInstanceID;
    }


    /**
     * @return the logName
     */
    public String getLogName() {
        return logName;
    }


    /**
     * @param logName the logName to set
     */
    public void setLogName(String logName) {
        this.logName = logName;
    }


    /**
     * @return the perceivedSeverity
     */
    public String getPerceivedSeverity() {
        return perceivedSeverity;
    }


    /**
     * @param perceivedSeverity the perceivedSeverity to set
     */
    public void setPerceivedSeverity(String perceivedSeverity) {
        this.perceivedSeverity = perceivedSeverity;
    }


    /**
     * @return the recordData
     */
    public String getRecordData() {
        return recordData;
    }


    /**
     * @param recordData the recordData to set
     */
    public void setRecordData(String recordData) {
        this.recordData = recordData;
    }


    /**
     * @return the recordFormat
     */
    public String getRecordFormat() {
        return recordFormat;
    }


    /**
     * @param recordFormat the recordFormat to set
     */
    public void setRecordFormat(String recordFormat) {
        this.recordFormat = recordFormat;
    }


    /**
     * @return the recordID
     */
    public String getRecordID() {
        return recordID;
    }


    /**
     * @param recordID the recordID to set
     */
    public void setRecordID(String recordID) {
        this.recordID = recordID;
    }

}
