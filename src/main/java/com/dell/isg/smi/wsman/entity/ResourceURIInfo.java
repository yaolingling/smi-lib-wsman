/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.entity;

public class ResourceURIInfo {
    public String getCreationClassName() {
        return creationClassName;
    }


    public void setCreationClassName(String creationClassName) {
        this.creationClassName = creationClassName;
    }


    public String getSystemCreationClassName() {
        return systemCreationClassName;
    }


    public void setSystemCreationClassName(String systemCreationClassName) {
        this.systemCreationClassName = systemCreationClassName;
    }


    public String getSystemName() {
        return systemName;
    }


    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getElementName() {
        return elementName;
    }


    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    String creationClassName = "";
    String systemCreationClassName = "";
    String systemName = "";
    String name = "";
    String elementName = "";
}
