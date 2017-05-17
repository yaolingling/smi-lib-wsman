/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman;

public class LCMessage {
    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public String getRecommendedActions() {
        return recommendedActions;
    }


    public void setRecommendedActions(String actions) {
        this.recommendedActions = actions;
    }


    public String getErrorType() {
        return errorType;
    }


    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    String message;
    String description;
    String recommendedActions;
    String errorType;
    boolean hasRecommendedActions;


    public boolean hasRecommendedActions() {
        return hasRecommendedActions;
    }


    public void setHasRecommendedActions(boolean hasActions) {
        this.hasRecommendedActions = hasActions;
    }

    String errorCode;


    public String getErrorCode() {
        return errorCode;
    }


    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }


    public static String getFormattedMessageId(String errCode) {
        String formattedId = "";
        if (errCode != null && !errCode.isEmpty()) {
            // FIX ME for ICEE
            // formattedId = String.format("%s~%s", CommonConstants.IDRAC_MSG_PREFIX,errCode);
        }
        return formattedId;
    }


    public static String getErrorCodeFromFormattedMessageId(String formattedId) {
        String errCode = "";
        if (formattedId != null && !formattedId.isEmpty()) {
            String[] values = formattedId.split("~");
            if (values.length == 2) {
                errCode = values[1];
            }
        }
        return errCode;
    }
}
