/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.entity;

/**
 * @author Prashanth.Gowda
 *
 */

public enum SensorTypeEnum {

    OTHER("1"), TEMPERATURE("2"), VOLTAGE("3"), CURRENT("4"), TACHOMETER("5");

    public static final String IDENTIFIER_PROPERTY = "sensorType";


    private SensorTypeEnum(String value) {
        this.value = value;
    }

    private final String value;


    public String getValue() {
        return value;
    }
}
