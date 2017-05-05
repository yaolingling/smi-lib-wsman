/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "XmlConfig", description = "Captures the Result/JobStatus, Message and JobID. ")
public class XmlConfig {

    public enum JobStatus {
        SUCCESS("success"), FAILURE("failure"), UNKNOWN("unknown");

        private String label;


        private JobStatus(String _label) {
            label = _label;
        }


        public String getLabel() {
            return label;
        }
    }

    @ApiModelProperty(value = "Message corresponding to the transaction.", dataType = "string", required = false)
    private String message;

    @ApiModelProperty(value = "This is the result for a operation. Success or Failure or Unknown.", dataType = "string", required = false)
    private String result;

    @ApiModelProperty(value = "This is the JobID retunred from DELL Server.", dataType = "string", required = false)
    private String jobID;


    public String getResult() {
        return result;
    }


    public void setResult(String result) {
        this.result = result;
    }


    public void setMessage(String message) {
        this.message = message;
    }


    public void setJobID(String jobID) {
        this.jobID = jobID;
    }


    public String getMessage() {
        return message;
    }


    public String getJobID() {
        return jobID;
    }

}
