/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.entity;

import java.util.List;

/**
 * 
 * @author Dan_Phelps
 *
 */

public class ConfigJobDetail {

    // public static final int FAIL = 0;
    // public static final int JOB_COMPLETED = 1;
    // public static final int NO_JOB_PENDING = 2;
    // public static final int JOB_LIST = 3;

    private ConfigJobReturnCode returnCode;
    private List<String> jobList;


    /**
     * @return the returnCode
     */
    public ConfigJobReturnCode getReturnCode() {
        return returnCode;
    }


    /**
     * @param returnCode the returnCode to set
     */
    public void setReturnCode(ConfigJobReturnCode returnCode) {
        this.returnCode = returnCode;
    }


    /**
     * @return the jobList
     */
    public List<String> getJobList() {
        return jobList;
    }


    /**
     * @param jobList the jobList to set
     */
    public void setJobList(List<String> jobList) {
        this.jobList = jobList;
    }

    public enum ConfigJobReturnCode {
        FAIL, JOB_COMPLETED, JOB_COMPLETED_WITH_ERRORS, NO_JOB_PENDING, JOB_LIST
    }

}
