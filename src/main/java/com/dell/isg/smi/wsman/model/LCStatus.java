/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.model;

import java.io.Serializable;

public class LCStatus implements Serializable {
    /**
     *
     */
    public enum LCSTATUS {
        UNKNOWN, READY, NOT_INITIALIZED, RELOADING, DISABLED, IN_RECOVERY, IN_USE;
    }

    public enum SERVERSTATUS {
        UNKNOWN, OFF, IN_POST, OUT_OF_POST, COLLECTING_INVENTORY, RUNNING_TASKS, In_USC, POST_ERROR, NO_BOOT_DEVICES, IN_F2_SETUP, IN_F11_BOOTMGR;
    }

    public enum STATUS {
        UNKNOWN, READY, NOT_READY;
    }

    public enum RETURNSTATUS {
        UNKNOWN, SUCCESSFUL, ERROR;
    }

    private static final long serialVersionUID = 1L;

    private LCSTATUS LCStatus = LCSTATUS.UNKNOWN;
    private SERVERSTATUS ServerStatus = SERVERSTATUS.UNKNOWN;
    private String MessageID;
    private String Message;
    private RETURNSTATUS ReturnValue = RETURNSTATUS.UNKNOWN;
    private STATUS Status = STATUS.UNKNOWN;


    public LCSTATUS getLCStatus() {
        return LCStatus;
    }


    public void setLCStatus(LCSTATUS lCStatus) {
        LCStatus = lCStatus;
    }


    public SERVERSTATUS getServerStatus() {
        return ServerStatus;
    }


    public void setServerStatus(SERVERSTATUS serverStatus) {
        ServerStatus = serverStatus;
    }


    public String getMessageID() {
        return MessageID;
    }


    public void setMessageID(String messageID) {
        MessageID = messageID;
    }


    public String getMessage() {
        return Message;
    }


    public void setMessage(String message) {
        Message = message;
    }


    public RETURNSTATUS getReturnValue() {
        return ReturnValue;
    }


    public void setReturnValue(RETURNSTATUS returnValue) {
        ReturnValue = returnValue;
    }


    public STATUS getStatus() {
        return Status;
    }


    public void setStatus(STATUS status) {
        Status = status;
    }


    public boolean isLCAvailable() {
        if (LCStatus == LCStatus.READY && (ServerStatus == SERVERSTATUS.OUT_OF_POST || ServerStatus == SERVERSTATUS.OFF || ServerStatus == SERVERSTATUS.NO_BOOT_DEVICES || ServerStatus == SERVERSTATUS.IN_F2_SETUP || ServerStatus == SERVERSTATUS.IN_F11_BOOTMGR || ServerStatus == SERVERSTATUS.POST_ERROR)) {
            return true;
        }
        return false;
    }


    public String toString() {
        String s = "LCStatus = " + LCStatus;
        s += " ServerStatus = " + ServerStatus;
        s += " ReturnValue = " + ReturnValue;
        s += " Status = " + Status;
        s += " MessageID = " + MessageID;
        s += " Message = " + Message;
        s += " isLCAvailable = " + isLCAvailable();
        return s;
    }
}
