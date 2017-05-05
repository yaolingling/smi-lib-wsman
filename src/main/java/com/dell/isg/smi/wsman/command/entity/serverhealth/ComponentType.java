/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.entity.serverhealth;

public enum ComponentType {
    CHASSIS("Main System Chassis", "MainSystemChassis"), POWER_SUPPLY("Power Supplies", "PowerSupplyObj"), TEMPERATURE("Temperatures", "TemperatureObj"), FAN("Fans", "FanObj"), VOLTAGE("Voltages", "VoltageObj"), PROCESSOR("Processors", "ProcessorStatusObj"), BATTERY("Batteries", "BatteryObj"), INTRUSION("Intrusion", "IntrusionObj"), LOG("Hardware Log", "LogObj"), POWER_CONSUMPTION("Power Management", "CurrentObj"), MEMORY("Memory", "MemDevObj"), GLOBAL_CHASSIS("Global Chassis", "GlobalChassis");
    private String label;
    private String xmlTagName;


    private ComponentType(String label, String xmlTagName) {
        this.xmlTagName = xmlTagName;
        this.label = label;

    }


    /**
     * @return label of element
     */
    public String getLabel() {
        return this.label;
    }


    /**
     * @return value of element
     */
    public String getXmlTagName() {
        return this.xmlTagName;
    }


    @Override
    public String toString() {
        return this.label;
    }
}
