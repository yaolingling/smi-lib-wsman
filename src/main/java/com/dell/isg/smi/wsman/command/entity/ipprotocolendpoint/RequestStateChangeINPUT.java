/**
 * Copyright � 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-146 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.07.08 at 11:53:49 AM CDT 
//

package com.dell.isg.smi.wsman.command.entity.ipprotocolendpoint;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RequestedState">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;restriction base="&lt;http://schemas.dmtf.org/wbem/wscim/1/common>cimAnySimpleType">
 *                 &lt;anyAttribute processContents='lax'/>
 *               &lt;/restriction>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="TimeoutPeriod" type="{http://schemas.dmtf.org/wbem/wscim/1/common}cimDateTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "requestedState", "timeoutPeriod" })
@XmlRootElement(name = "RequestStateChange_INPUT", namespace = "http://schema.omc-project.org/wbem/wscim/1/cim-schema/2/OMC_IPMIIPProtocolEndpoint")
public class RequestStateChangeINPUT {

    @XmlElement(name = "RequestedState", namespace = "http://schema.omc-project.org/wbem/wscim/1/cim-schema/2/OMC_IPMIIPProtocolEndpoint", required = true, nillable = true)
    protected RequestStateChangeINPUT.RequestedState requestedState;
    @XmlElement(name = "TimeoutPeriod", namespace = "http://schema.omc-project.org/wbem/wscim/1/cim-schema/2/OMC_IPMIIPProtocolEndpoint", required = true, nillable = true)
    protected CimDateTime timeoutPeriod;


    /**
     * Gets the value of the requestedState property.
     * 
     * @return possible object is {@link RequestStateChangeINPUT.RequestedState }
     * 
     */
    public RequestStateChangeINPUT.RequestedState getRequestedState() {
        return requestedState;
    }


    /**
     * Sets the value of the requestedState property.
     * 
     * @param value allowed object is {@link RequestStateChangeINPUT.RequestedState }
     * 
     */
    public void setRequestedState(RequestStateChangeINPUT.RequestedState value) {
        this.requestedState = value;
    }


    /**
     * Gets the value of the timeoutPeriod property.
     * 
     * @return possible object is {@link CimDateTime }
     * 
     */
    public CimDateTime getTimeoutPeriod() {
        return timeoutPeriod;
    }


    /**
     * Sets the value of the timeoutPeriod property.
     * 
     * @param value allowed object is {@link CimDateTime }
     * 
     */
    public void setTimeoutPeriod(CimDateTime value) {
        this.timeoutPeriod = value;
    }

    /**
     * <p>
     * Java class for anonymous complex type.
     * 
     * <p>
     * The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;restriction base="&lt;http://schemas.dmtf.org/wbem/wscim/1/common>cimAnySimpleType">
     *       &lt;anyAttribute processContents='lax'/>
     *     &lt;/restriction>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class RequestedState extends CimAnySimpleType {

    }

}
