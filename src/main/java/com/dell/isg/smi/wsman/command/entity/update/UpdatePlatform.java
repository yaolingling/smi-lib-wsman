/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.entity.update;

import java.io.Serializable;

public class UpdatePlatform implements Serializable {

    private static final long serialVersionUID = 1L;

    String brand;


    public String getBrand() {
        return brand;
    }


    public void setBrand(String brand) {
        this.brand = brand;
    }

    String systemID;


    public String getSystemID() {
        return systemID;
    }


    public void setSystemID(String systemID) {
        this.systemID = systemID;
    }

    String display;


    public String getDisplay() {
        return display;
    }


    public void setDisplay(String display) {
        this.display = display;
    }
}
