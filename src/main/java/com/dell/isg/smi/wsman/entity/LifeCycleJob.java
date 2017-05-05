/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.entity;

public class LifeCycleJob {

    String instanceID;


    public String getInstanceID() {
        return instanceID;
    }


    public void setInstanceID(String instanceID) {
        this.instanceID = instanceID;
    }

    String jobStartTime;


    public String getJobStartTime() {
        return jobStartTime;
    }


    public void setJobStartTime(String jobStartTime) {
        this.jobStartTime = jobStartTime;
    }

    String jobStatus;


    public String getJobStatus() {
        return jobStatus;
    }


    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    String jobUntilTime;


    public String getJobUntilTime() {
        return jobUntilTime;
    }


    public void setJobUntilTime(String jobUntilTime) {
        this.jobUntilTime = jobUntilTime;
    }

    String message;


    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }

    String messageID;


    public String getMessageID() {
        return messageID;
    }


    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    String jobName;


    public String getJobName() {
        return jobName;
    }


    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    String hostName;


    public String getHostName() {
        return hostName;
    }


    public void setHostName(String hostName) {
        this.hostName = hostName;
    }


    public String getFileName() {
        return fileName;
    }


    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    String fileName;


    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof LifeCycleJob) {
            LifeCycleJob job = (LifeCycleJob) obj;
            return this.getInstanceID().equalsIgnoreCase(job.getInstanceID());
        }
        return false;
    }
}
