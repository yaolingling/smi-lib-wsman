/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.entity;

import java.io.Serializable;

import com.dell.isg.smi.wsman.logging.HardwareLogEntry;

public class LcLogEntry extends HardwareLogEntry implements Serializable {

    /**
     * 
     */

    private static final long serialVersionUID = 1L;

    private String elementName = null;

    private String instanceId = null;

    private String logInstanceId = null;

    private String comment = null;

    private String agentId = null;

    private String configResultsAvailable = null;

    private String fqdd = null;

    private String messageArguments = null;

    private String owningEntity = null;

    private String rawEventData = null;

    private long sequenceNumber;


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
     * @return the instanceId
     */
    public String getInstanceId() {
        return instanceId;
    }


    /**
     * @param instanceId the instanceId to set
     */
    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }


    /**
     * @return the logInstanceId
     */
    public String getLogInstanceId() {
        return logInstanceId;
    }


    /**
     * @param logInstanceId the logInstanceId to set
     */
    public void setLogInstanceId(String logInstanceId) {
        this.logInstanceId = logInstanceId;
    }


    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }


    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }


    /**
     * @return the agentId
     */
    public String getAgentId() {
        return agentId;
    }


    /**
     * @param agentId the agentId to set
     */
    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }


    /**
     * @return the configResultsAvailable
     */
    public String getConfigResultsAvailable() {
        return configResultsAvailable;
    }


    /**
     * @param configResultsAvailable the configResultsAvailable to set
     */
    public void setConfigResultsAvailable(String configResultsAvailable) {
        this.configResultsAvailable = configResultsAvailable;
    }


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
     * @return the messageArguments
     */
    public String getMessageArguments() {
        return messageArguments;
    }


    /**
     * @param messageArguments the messageArguments to set
     */
    public void setMessageArguments(String messageArguments) {
        this.messageArguments = messageArguments;
    }


    /**
     * @return the owningEntity
     */
    public String getOwningEntity() {
        return owningEntity;
    }


    /**
     * @param owningEntity the owningEntity to set
     */
    public void setOwningEntity(String owningEntity) {
        this.owningEntity = owningEntity;
    }


    /**
     * @return the rawEventData
     */
    public String getRawEventData() {
        return rawEventData;
    }


    /**
     * @param rawEventData the rawEventData to set
     */
    public void setRawEventData(String rawEventData) {
        this.rawEventData = rawEventData;
    }


    /**
     * @return the sequenceNumber
     */
    public long getSequenceNumber() {
        return sequenceNumber;
    }


    /**
     * @param sequenceNumber the sequenceNumber to set
     */
    public void setSequenceNumber(long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

}
