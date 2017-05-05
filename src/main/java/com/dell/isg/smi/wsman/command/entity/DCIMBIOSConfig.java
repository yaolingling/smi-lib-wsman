/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.entity;

import java.util.List;

/**
 * @author rajesh.varada
 *
 */
public class DCIMBIOSConfig {

    private List<DCIMBIOSEnumerationType> dcimBIOSEnumerationTypeList;

    private List<DCIMBIOSIntegerType> dcimBIOSIntegerType;

    private List<DCIMBIOSStringType> DCIMBIOSStringType;


    /**
     * @return the dcimBIOSEnumerationTypeList
     */
    public List<DCIMBIOSEnumerationType> getDcimBIOSEnumerationTypeList() {
        return dcimBIOSEnumerationTypeList;
    }


    /**
     * @param dcimBIOSEnumerationTypeList the dcimBIOSEnumerationTypeList to set
     */
    public void setDcimBIOSEnumerationTypeList(List<DCIMBIOSEnumerationType> dcimBIOSEnumerationTypeList) {
        this.dcimBIOSEnumerationTypeList = dcimBIOSEnumerationTypeList;
    }


    /**
     * @return the dcimBIOSIntegerType
     */
    public List<DCIMBIOSIntegerType> getDcimBIOSIntegerType() {
        return dcimBIOSIntegerType;
    }


    /**
     * @param dcimBIOSIntegerType the dcimBIOSIntegerType to set
     */
    public void setDcimBIOSIntegerType(List<DCIMBIOSIntegerType> dcimBIOSIntegerType) {
        this.dcimBIOSIntegerType = dcimBIOSIntegerType;
    }


    /**
     * @return the dCIMBIOSStringType
     */
    public List<DCIMBIOSStringType> getDCIMBIOSStringType() {
        return DCIMBIOSStringType;
    }


    /**
     * @param dCIMBIOSStringType the dCIMBIOSStringType to set
     */
    public void setDCIMBIOSStringType(List<DCIMBIOSStringType> dCIMBIOSStringType) {
        DCIMBIOSStringType = dCIMBIOSStringType;
    }

}
