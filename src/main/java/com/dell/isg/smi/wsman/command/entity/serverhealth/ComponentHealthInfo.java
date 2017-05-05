/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.entity.serverhealth;

public class ComponentHealthInfo {
    private ComponentType componentName;


    /**
     * @return the componentName
     */
    public ComponentType getComponentName() {
        return componentName;
    }


    /**
     * @param componentName the componentName to set
     */
    public void setComponentName(ComponentType componentName) {
        this.componentName = componentName;
    }


    /**
     * @return the componentStatus
     */
    public int getComponentStatus() {
        return componentStatus;
    }


    /**
     * @param componentStatus the componentStatus to set
     */
    public void setComponentStatus(int componentStatus) {
        this.componentStatus = componentStatus;
    }

    private int componentStatus;


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((componentName == null) ? 0 : componentName.hashCode());
        // commenting out component Status deliberately from hash code computation
        // result = prime * result + componentStatus;
        return result;
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ComponentHealthInfo other = (ComponentHealthInfo) obj;
        if (componentName == null) {
            if (other.componentName != null)
                return false;
        } else if (!componentName.equals(other.componentName))
            return false;
        // commenting out component Status deliberately
        // if (componentStatus != other.componentStatus)
        // return false;
        return true;
    }

}
