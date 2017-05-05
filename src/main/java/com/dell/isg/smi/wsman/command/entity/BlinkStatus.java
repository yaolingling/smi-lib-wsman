/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.entity;

public class BlinkStatus {
    boolean isBlinking;


    /**
     * @return the isBlinking
     */
    public boolean isBlinking() {
        return isBlinking;
    }


    /**
     * @param isBlinking the isBlinking to set
     */
    public void setBlinking(boolean isBlinking) {
        this.isBlinking = isBlinking;
    }

    int timeout;


    /**
     * @return the timeout
     */
    public int getTimeout() {
        return timeout;
    }


    /**
     * @param timeout the timeout to set
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
