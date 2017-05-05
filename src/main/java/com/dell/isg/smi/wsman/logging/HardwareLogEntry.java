/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.logging;

/**
 * @author rajesh.varada
 *
 */
public abstract class HardwareLogEntry {

    private long recordId;

    private String logName = null;

    private String creationTimeStamp = null;

    private String message = null;

    private String severity = null;

    private String category = null;

    private String messageId = null;


    /**
     * @return the recordId
     */
    public long getRecordId() {
        return recordId;
    }


    /**
     * @param recordId the recordId to set
     */
    public void setRecordId(long recordId) {
        this.recordId = recordId;
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
     * @return the message
     */
    public String getMessage() {
        return message;
    }


    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }


    /**
     * @return the severity
     */
    public String getSeverity() {
        return severity;
    }


    /**
     * @param severity the severity to set
     */
    public void setSeverity(String severity) {
        this.severity = severity;
    }


    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }


    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }


    /**
     * @return the messageId
     */
    public String getMessageId() {
        return messageId;
    }


    /**
     * @param messageId the messageId to set
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

}
