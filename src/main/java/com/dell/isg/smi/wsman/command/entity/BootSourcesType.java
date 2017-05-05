/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for BootSourcesType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BootSourcesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CurrentSequence" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Enabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="InstanceId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BootSourcesType", namespace = "http://pg.dell.com/spectre/HardwareProfile/xsd", propOrder = { "name", "currentSequence", "type", "enabled", "instanceId" })
public class BootSourcesType {

    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "CurrentSequence", required = true)
    protected String currentSequence;
    @XmlElement(name = "Type", required = true)
    protected String type;
    @XmlElement(name = "Enabled")
    protected boolean enabled;
    @XmlElement(name = "InstanceId", required = true)
    protected String instanceId;


    /**
     * Gets the value of the name property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getName() {
        return name;
    }


    /**
     * Sets the value of the name property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setName(String value) {
        this.name = value;
    }


    /**
     * Gets the value of the currentSequence property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCurrentSequence() {
        return currentSequence;
    }


    /**
     * Sets the value of the currentSequence property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setCurrentSequence(String value) {
        this.currentSequence = value;
    }


    /**
     * Gets the value of the type property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getType() {
        return type;
    }


    /**
     * Sets the value of the type property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setType(String value) {
        this.type = value;
    }


    /**
     * Gets the value of the enabled property.
     * 
     */
    public boolean isEnabled() {
        return enabled;
    }


    /**
     * Sets the value of the enabled property.
     * 
     */
    public void setEnabled(boolean value) {
        this.enabled = value;
    }


    /**
     * Gets the value of the instanceId property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getInstanceId() {
        return instanceId;
    }


    /**
     * Sets the value of the instanceId property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setInstanceId(String value) {
        this.instanceId = value;
    }

}
