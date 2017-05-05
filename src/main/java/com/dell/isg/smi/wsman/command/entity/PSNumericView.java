/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.entity;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class PSNumericView implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String baseUnits = null;
    private String currentReading = null;
    private String currentState = null;
    private String deviceId = null;
    private String elementName = null;
    private String enabledDefault = null;
    private String enabledSate = null;
    private String healthState = null;
    private String lowerThresholdCritical = null;
    private String lowerThresholdNonCritical = null;
    private String operationalStatus = null;
    private String otherSensorTypeDesc = null;
    private String primaryStatus = null;
    private String rateUnits = null;
    private String requestedStatus = null;
    private String sensorType = null;
    private String settableThresholds = null;
    private String supportedThresholds = null;
    private String systemName = null;
    private String transitioningToState = null;
    private String unitModifier = null;
    private String upperThresholdCritical = null;
    private String upperThresholdNonCritical = null;
    private String valueFormulation = null;

    public enum HealthStatus {

        UNKNOWN("0", "Unknown"), OK("5", "OK"), NORMAL("5", "Normal"), DEGRADED("10", "Degraded/Warning"), UPPERNONCRITICAL("10", "Upper Non-Critical"), LOWERNONCRITICAL("10", "Lower Non-Critical"), MINOR_FAILURE("15", "Minor failure"), MAJOR_FAILURE("20", "Major failure"), CRITICAL_FAILURE("25", "Critical failure"), FATAL("30", "Fatal"), UPPERFATAL("30", "Upper Fatal"), UPPERCRITICAL("30", "Upper Critical"), LOWERCRITICAL("30", "Lower Critical"), NONRECOVERABLE_FAILURE("30", "Non-recoverable error"), DMTF_RESERVED("..", "DMTF Reserved");

        // Unknown, Fatal, Normal, Upper Fatal, Upper Critical, Upper Non-Critical, Lower Non-Critical, Lower Critical

        private String value = null;
        private String status = null;


        private HealthStatus(String value, String status) {
            this.value = value;
            this.status = status;
        }


        /**
         * @return the value
         */
        public String getValue() {
            return value;
        }


        /**
         * @param value the value to set
         */
        public void setValue(String value) {
            this.value = value;
        }


        /**
         * @return the status
         */
        public String getStatus() {
            return status;
        }


        /**
         * @param status the status to set
         */
        public void setStatus(String status) {
            this.status = status;
        }


        public static HealthStatus getHealthStatus(String value) {

            HealthStatus[] healthStatusArr = HealthStatus.values();
            for (HealthStatus healthStatus : healthStatusArr) {
                if (StringUtils.equalsIgnoreCase(healthStatus.getValue(), value)) {
                    return healthStatus;
                }
            }
            return null;
        }


        public static HealthStatus getHealthStatusInt(String status) {

            HealthStatus[] healthStatusArr = HealthStatus.values();
            for (HealthStatus healthStatus : healthStatusArr) {
                if (StringUtils.equalsIgnoreCase(healthStatus.getStatus(), status)) {
                    return healthStatus;
                }
            }
            return null;
        }

    }


    /**
     * @return the baseUnits
     */
    public String getBaseUnits() {
        return baseUnits;
    }


    /**
     * @param baseUnits the baseUnits to set
     */
    public void setBaseUnits(String baseUnits) {
        this.baseUnits = baseUnits;
    }


    /**
     * @return the currentReading
     */
    public String getCurrentReading() {
        return currentReading;
    }


    /**
     * @param currentReading the currentReading to set
     */
    public void setCurrentReading(String currentReading) {
        this.currentReading = currentReading;
    }


    /**
     * @return the currentState
     */
    public String getCurrentState() {
        return currentState;
    }


    /**
     * @param currentState the currentState to set
     */
    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }


    /**
     * @return the deviceId
     */
    public String getDeviceId() {
        return deviceId;
    }


    /**
     * @param deviceId the deviceId to set
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }


    /**
     * @return the elementName
     */
    public String getElementName() {
        return elementName;
    }


    /**
     * @param elementName the elementName to set
     */
    public void setElementName(String elementName) {
        this.elementName = elementName;
    }


    /**
     * @return the enabledDefault
     */
    public String getEnabledDefault() {
        return enabledDefault;
    }


    /**
     * @param enabledDefault the enabledDefault to set
     */
    public void setEnabledDefault(String enabledDefault) {
        this.enabledDefault = enabledDefault;
    }


    /**
     * @return the enabledSate
     */
    public String getEnabledSate() {
        return enabledSate;
    }


    /**
     * @param enabledSate the enabledSate to set
     */
    public void setEnabledSate(String enabledSate) {
        this.enabledSate = enabledSate;
    }


    /**
     * @return the healthState
     */
    public String getHealthState() {
        return healthState;
    }


    /**
     * @param healthState the healthState to set
     */
    public void setHealthState(String healthState) {
        this.healthState = healthState;
    }


    /**
     * @return the lowerThresholdCritical
     */
    public String getLowerThresholdCritical() {
        return lowerThresholdCritical;
    }


    /**
     * @param lowerThresholdCritical the lowerThresholdCritical to set
     */
    public void setLowerThresholdCritical(String lowerThresholdCritical) {
        this.lowerThresholdCritical = lowerThresholdCritical;
    }


    /**
     * @return the lowerThresholdNonCritical
     */
    public String getLowerThresholdNonCritical() {
        return lowerThresholdNonCritical;
    }


    /**
     * @param lowerThresholdNonCritical the lowerThresholdNonCritical to set
     */
    public void setLowerThresholdNonCritical(String lowerThresholdNonCritical) {
        this.lowerThresholdNonCritical = lowerThresholdNonCritical;
    }


    /**
     * @return the operationalStatus
     */
    public String getOperationalStatus() {
        return operationalStatus;
    }


    /**
     * @param operationalStatus the operationalStatus to set
     */
    public void setOperationalStatus(String operationalStatus) {
        this.operationalStatus = operationalStatus;
    }


    /**
     * @return the otherSensorTypeDesc
     */
    public String getOtherSensorTypeDesc() {
        return otherSensorTypeDesc;
    }


    /**
     * @param otherSensorTypeDesc the otherSensorTypeDesc to set
     */
    public void setOtherSensorTypeDesc(String otherSensorTypeDesc) {
        this.otherSensorTypeDesc = otherSensorTypeDesc;
    }


    /**
     * @return the primaryStatus
     */
    public String getPrimaryStatus() {
        return primaryStatus;
    }


    /**
     * @param primaryStatus the primaryStatus to set
     */
    public void setPrimaryStatus(String primaryStatus) {
        this.primaryStatus = primaryStatus;
    }


    /**
     * @return the rateUnits
     */
    public String getRateUnits() {
        return rateUnits;
    }


    /**
     * @param rateUnits the rateUnits to set
     */
    public void setRateUnits(String rateUnits) {
        this.rateUnits = rateUnits;
    }


    /**
     * @return the requestedStatus
     */
    public String getRequestedStatus() {
        return requestedStatus;
    }


    /**
     * @param requestedStatus the requestedStatus to set
     */
    public void setRequestedStatus(String requestedStatus) {
        this.requestedStatus = requestedStatus;
    }


    /**
     * @return the sensorType
     */
    public String getSensorType() {
        return sensorType;
    }


    /**
     * @param sensorType the sensorType to set
     */
    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }


    /**
     * @return the settableThresholds
     */
    public String getSettableThresholds() {
        return settableThresholds;
    }


    /**
     * @param settableThresholds the settableThresholds to set
     */
    public void setSettableThresholds(String settableThresholds) {
        this.settableThresholds = settableThresholds;
    }


    /**
     * @return the supportedThresholds
     */
    public String getSupportedThresholds() {
        return supportedThresholds;
    }


    /**
     * @param supportedThresholds the supportedThresholds to set
     */
    public void setSupportedThresholds(String supportedThresholds) {
        this.supportedThresholds = supportedThresholds;
    }


    /**
     * @return the systemName
     */
    public String getSystemName() {
        return systemName;
    }


    /**
     * @param systemName the systemName to set
     */
    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }


    /**
     * @return the transitioningToState
     */
    public String getTransitioningToState() {
        return transitioningToState;
    }


    /**
     * @param transitioningToState the transitioningToState to set
     */
    public void setTransitioningToState(String transitioningToState) {
        this.transitioningToState = transitioningToState;
    }


    /**
     * @return the unitModifier
     */
    public String getUnitModifier() {
        return unitModifier;
    }


    /**
     * @param unitModifier the unitModifier to set
     */
    public void setUnitModifier(String unitModifier) {
        this.unitModifier = unitModifier;
    }


    /**
     * @return the upperThresholdCritical
     */
    public String getUpperThresholdCritical() {
        return upperThresholdCritical;
    }


    /**
     * @param upperThresholdCritical the upperThresholdCritical to set
     */
    public void setUpperThresholdCritical(String upperThresholdCritical) {
        this.upperThresholdCritical = upperThresholdCritical;
    }


    /**
     * @return the upperThresholdNonCritical
     */
    public String getUpperThresholdNonCritical() {
        return upperThresholdNonCritical;
    }


    /**
     * @param upperThresholdNonCritical the upperThresholdNonCritical to set
     */
    public void setUpperThresholdNonCritical(String upperThresholdNonCritical) {
        this.upperThresholdNonCritical = upperThresholdNonCritical;
    }


    /**
     * @return the valueFormulation
     */
    public String getValueFormulation() {
        return valueFormulation;
    }


    /**
     * @param valueFormulation the valueFormulation to set
     */
    public void setValueFormulation(String valueFormulation) {
        this.valueFormulation = valueFormulation;
    }

}
