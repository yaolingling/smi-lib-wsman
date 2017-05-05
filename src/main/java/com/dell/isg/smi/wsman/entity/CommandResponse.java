/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
/**
 * 
 */
package com.dell.isg.smi.wsman.entity;

import java.io.Serializable;

/**
 * @author Umer_Shabbir
 *
 */
public class CommandResponse implements Serializable {

    private static final long serialVersionUID = 361632112072750930L;

    private String resourceURI;
    private String jobID;
    private boolean bSuccess;
    private String details;
    private String LCErrorCode;
    private String LCErrorStr;


    public String getResourceURI() {
        return resourceURI;
    }


    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }


    public String getJobID() {
        return jobID;
    }


    public void setJobID(String jobID) {
        this.jobID = jobID;
    }


    public boolean isbSuccess() {
        return bSuccess;
    }


    public void setbSuccess(boolean bSuccess) {
        this.bSuccess = bSuccess;
    }


    public String getDetails() {
        return details;
    }


    public void setDetails(String details) {
        this.details = details;
    }


    public void setLCErrorCode(String lCErrorCode) {
        LCErrorCode = lCErrorCode;
    }


    public String getLCErrorCode() {
        return LCErrorCode;
    }


    public void setLCErrorStr(String lCErrorStr) {
        LCErrorStr = lCErrorStr;
    }


    public String getLCErrorStr() {
        return LCErrorStr;
    }
}
