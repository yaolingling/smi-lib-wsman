/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.entity;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="CurrentBootMode">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="BIOS"/>
 *               &lt;enumeration value="UEFI"/>
 *               &lt;enumeration value="VFLASH"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="IncludeBootOrderInProfile" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="BootSequenceRetry">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="possibleValues" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *                   &lt;element name="currentValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="HardDrives" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="HardDrive" type="{http://pg.dell.com/spectre/HardwareProfile/xsd}BootSourcesType" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="BootSourcesByBootModes">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="BootSourcesByBootMode" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="BootMode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="BootSources" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="BootSource" type="{http://pg.dell.com/spectre/HardwareProfile/xsd}BootSourcesType" maxOccurs="unbounded"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "currentBootMode", "includeBootOrderInProfile", "bootSequenceRetry", "hardDrives", "bootSourcesByBootModes" })
@XmlRootElement(name = "BootOrderDetails", namespace = "http://pg.dell.com/spectre/HardwareProfile/xsd")
public class BootOrderDetails {

    @XmlElement(name = "CurrentBootMode", namespace = "http://pg.dell.com/spectre/HardwareProfile/xsd", required = true)
    protected String currentBootMode;
    @XmlElement(name = "IncludeBootOrderInProfile", namespace = "http://pg.dell.com/spectre/HardwareProfile/xsd")
    protected boolean includeBootOrderInProfile;
    @XmlElement(name = "BootSequenceRetry", namespace = "http://pg.dell.com/spectre/HardwareProfile/xsd", required = true)
    protected BootOrderDetails.BootSequenceRetry bootSequenceRetry;
    @XmlElement(name = "HardDrives", namespace = "http://pg.dell.com/spectre/HardwareProfile/xsd")
    protected BootOrderDetails.HardDrives hardDrives;
    @XmlElement(name = "BootSourcesByBootModes", namespace = "http://pg.dell.com/spectre/HardwareProfile/xsd", required = true)
    protected BootOrderDetails.BootSourcesByBootModes bootSourcesByBootModes;


    /**
     * Gets the value of the currentBootMode property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCurrentBootMode() {
        return currentBootMode;
    }


    /**
     * Sets the value of the currentBootMode property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setCurrentBootMode(String value) {
        this.currentBootMode = value;
    }


    /**
     * Gets the value of the includeBootOrderInProfile property.
     * 
     */
    public boolean isIncludeBootOrderInProfile() {
        return includeBootOrderInProfile;
    }


    /**
     * Sets the value of the includeBootOrderInProfile property.
     * 
     */
    public void setIncludeBootOrderInProfile(boolean value) {
        this.includeBootOrderInProfile = value;
    }


    /**
     * Gets the value of the bootSequenceRetry property.
     * 
     * @return possible object is {@link BootOrderDetails.BootSequenceRetry }
     * 
     */
    public BootOrderDetails.BootSequenceRetry getBootSequenceRetry() {
        return bootSequenceRetry;
    }


    /**
     * Sets the value of the bootSequenceRetry property.
     * 
     * @param value allowed object is {@link BootOrderDetails.BootSequenceRetry }
     * 
     */
    public void setBootSequenceRetry(BootOrderDetails.BootSequenceRetry value) {
        this.bootSequenceRetry = value;
    }


    /**
     * Gets the value of the hardDrives property.
     * 
     * @return possible object is {@link BootOrderDetails.HardDrives }
     * 
     */
    public BootOrderDetails.HardDrives getHardDrives() {
        return hardDrives;
    }


    /**
     * Sets the value of the hardDrives property.
     * 
     * @param value allowed object is {@link BootOrderDetails.HardDrives }
     * 
     */
    public void setHardDrives(BootOrderDetails.HardDrives value) {
        this.hardDrives = value;
    }


    /**
     * Gets the value of the bootSourcesByBootModes property.
     * 
     * @return possible object is {@link BootOrderDetails.BootSourcesByBootModes }
     * 
     */
    public BootOrderDetails.BootSourcesByBootModes getBootSourcesByBootModes() {
        return bootSourcesByBootModes;
    }


    /**
     * Sets the value of the bootSourcesByBootModes property.
     * 
     * @param value allowed object is {@link BootOrderDetails.BootSourcesByBootModes }
     * 
     */
    public void setBootSourcesByBootModes(BootOrderDetails.BootSourcesByBootModes value) {
        this.bootSourcesByBootModes = value;
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
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="possibleValues" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
     *         &lt;element name="currentValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = { "possibleValues", "currentValue" })
    public static class BootSequenceRetry {

        @XmlElement(namespace = "http://pg.dell.com/spectre/HardwareProfile/xsd", required = true)
        protected List<String> possibleValues;
        @XmlElement(namespace = "http://pg.dell.com/spectre/HardwareProfile/xsd", required = true)
        protected String currentValue;


        /**
         * Gets the value of the possibleValues property.
         * 
         * <p>
         * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB
         * object. This is why there is not a <CODE>set</CODE> method for the possibleValues property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * 
         * <pre>
         * getPossibleValues().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list {@link String }
         * 
         * 
         */
        public List<String> getPossibleValues() {
            if (possibleValues == null) {
                possibleValues = new ArrayList<String>();
            }
            return this.possibleValues;
        }


        /**
         * Gets the value of the currentValue property.
         * 
         * @return possible object is {@link String }
         * 
         */
        public String getCurrentValue() {
            return currentValue;
        }


        /**
         * Sets the value of the currentValue property.
         * 
         * @param value allowed object is {@link String }
         * 
         */
        public void setCurrentValue(String value) {
            this.currentValue = value;
        }

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
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="BootSourcesByBootMode" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="BootMode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="BootSources" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="BootSource" type="{http://pg.dell.com/spectre/HardwareProfile/xsd}BootSourcesType" maxOccurs="unbounded"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = { "bootSourcesByBootMode" })
    public static class BootSourcesByBootModes {

        @XmlElement(name = "BootSourcesByBootMode", namespace = "http://pg.dell.com/spectre/HardwareProfile/xsd")
        protected List<BootOrderDetails.BootSourcesByBootModes.BootSourcesByBootMode> bootSourcesByBootMode;


        /**
         * Gets the value of the bootSourcesByBootMode property.
         * 
         * <p>
         * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB
         * object. This is why there is not a <CODE>set</CODE> method for the bootSourcesByBootMode property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * 
         * <pre>
         * getBootSourcesByBootMode().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list {@link BootOrderDetails.BootSourcesByBootModes.BootSourcesByBootMode }
         * 
         * 
         */
        public List<BootOrderDetails.BootSourcesByBootModes.BootSourcesByBootMode> getBootSourcesByBootMode() {
            if (bootSourcesByBootMode == null) {
                bootSourcesByBootMode = new ArrayList<BootOrderDetails.BootSourcesByBootModes.BootSourcesByBootMode>();
            }
            return this.bootSourcesByBootMode;
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
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="BootMode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="BootSources" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="BootSource" type="{http://pg.dell.com/spectre/HardwareProfile/xsd}BootSourcesType" maxOccurs="unbounded"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = { "bootMode", "bootSources" })
        public static class BootSourcesByBootMode {

            @XmlElement(name = "BootMode", namespace = "http://pg.dell.com/spectre/HardwareProfile/xsd")
            protected String bootMode;
            @XmlElement(name = "BootSources", namespace = "http://pg.dell.com/spectre/HardwareProfile/xsd")
            protected BootOrderDetails.BootSourcesByBootModes.BootSourcesByBootMode.BootSources bootSources;


            /**
             * Gets the value of the bootMode property.
             * 
             * @return possible object is {@link String }
             * 
             */
            public String getBootMode() {
                return bootMode;
            }


            /**
             * Sets the value of the bootMode property.
             * 
             * @param value allowed object is {@link String }
             * 
             */
            public void setBootMode(String value) {
                this.bootMode = value;
            }


            /**
             * Gets the value of the bootSources property.
             * 
             * @return possible object is {@link BootOrderDetails.BootSourcesByBootModes.BootSourcesByBootMode.BootSources }
             * 
             */
            public BootOrderDetails.BootSourcesByBootModes.BootSourcesByBootMode.BootSources getBootSources() {
                return bootSources;
            }


            /**
             * Sets the value of the bootSources property.
             * 
             * @param value allowed object is {@link BootOrderDetails.BootSourcesByBootModes.BootSourcesByBootMode.BootSources }
             * 
             */
            public void setBootSources(BootOrderDetails.BootSourcesByBootModes.BootSourcesByBootMode.BootSources value) {
                this.bootSources = value;
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
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="BootSource" type="{http://pg.dell.com/spectre/HardwareProfile/xsd}BootSourcesType" maxOccurs="unbounded"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = { "bootSource" })
            public static class BootSources {

                @XmlElement(name = "BootSource", namespace = "http://pg.dell.com/spectre/HardwareProfile/xsd", required = true)
                protected List<BootSourcesType> bootSource;


                /**
                 * Gets the value of the bootSource property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the
                 * JAXB object. This is why there is not a <CODE>set</CODE> method for the bootSource property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * 
                 * <pre>
                 * getBootSource().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list {@link BootSourcesType }
                 * 
                 * 
                 */
                public List<BootSourcesType> getBootSource() {
                    if (bootSource == null) {
                        bootSource = new ArrayList<BootSourcesType>();
                    }
                    return this.bootSource;
                }

            }

        }

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
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="HardDrive" type="{http://pg.dell.com/spectre/HardwareProfile/xsd}BootSourcesType" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = { "hardDrive" })
    public static class HardDrives {

        @XmlElement(name = "HardDrive", namespace = "http://pg.dell.com/spectre/HardwareProfile/xsd")
        protected List<BootSourcesType> hardDrive;


        /**
         * Gets the value of the hardDrive property.
         * 
         * <p>
         * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB
         * object. This is why there is not a <CODE>set</CODE> method for the hardDrive property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * 
         * <pre>
         * getHardDrive().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list {@link BootSourcesType }
         * 
         * 
         */
        public List<BootSourcesType> getHardDrive() {
            if (hardDrive == null) {
                hardDrive = new ArrayList<BootSourcesType>();
            }
            return this.hardDrive;
        }

    }

}
