package com.dell.isg.smi.wsman.command;

import com.dell.isg.smi.wsmanclient.WSCommandRNDConstant;
import com.dell.isg.smi.wsmanclient.IWSManInvokableEnum;

public enum WSManInvokableEnum implements IWSManInvokableEnum{
    DCIM_LicenseManagementService(WSManClassEnum.DCIM_LicenseManagementService, "DCIM_SPComputerSystem", "systemmc", "DCIM:LicenseManagementService:1", "License Management Service"), DCIM_iDRACCardService(WSManClassEnum.DCIM_iDRACCardService, "DCIM_ComputerSystem", "DCIM:ComputerSystem", "DCIM:iDRACCardService", "DRAC Service"), DCIM_SystemManagementService(WSManClassEnum.DCIM_SystemManagementService, "DCIM_ComputerSystem", "srv:system", "DCIM:SystemManagementService", "System Management Service"), DCIM_LCService(WSManClassEnum.DCIM_LCService, "DCIM_ComputerSystem", "DCIM:ComputerSystem", "DCIM:LCService", "LC Service"), DCIM_SoftwareInstallationService(WSManClassEnum.DCIM_SoftwareInstallationService, WSManClassEnum.DCIM_ComputerSystem.toString(), "IDRAC:ID", "SoftwareUpdate", "Software Installation Service"),

    DCIM_PowerBootService(WSManClassEnum.DCIM_ComputerSystem, null, null, "srv:system", "Power Boot Service"),
    // DCIM_SoftwareInstallationService(WSManClassEnum.DCIM_SoftwareInstallationService,
    // "DCIM_SoftwareInstallationService", "DCIM:ComputerSystem", "DCIM:SoftwareInstallationService", "Software Installation Service"),

    DCIM_JobService(WSManClassEnum.DCIM_JobService, WSManClassEnum.DCIM_ComputerSystem.toString(), WSCommandRNDConstant.SYSTEM_NAME, WSCommandRNDConstant.JOB_SERVICE, "Job Service"), DCIM_CSPowerManagementService(WSManClassEnum.DCIM_CSPowerManagementService, "DCIM_SPComputerSystem", "systemmc", "pwrmgtsvc:1", "Power Management Service"), DCIM_SelRecordLog(WSManClassEnum.DCIM_SelRecordLog, WSManClassEnum.DCIM_ComputerSystem.toString(), WSCommandRNDConstant.SYSTEM_NAME, "SelRecordLogService", "SEL Record Log Service");
    private final WSManClassEnum cmdEnum;
    private final String systemCreationClassName;
    private final String creationClassName;
    private final String systemName;
    private final String name;
    private final String elementName;


    private WSManInvokableEnum(WSManClassEnum cmdEnum, String systemCreationClassName, String systemName, String name, String elementName) {
        this.cmdEnum = cmdEnum;
        this.systemCreationClassName = systemCreationClassName;
        this.creationClassName = cmdEnum.name();
        this.systemName = systemName;
        this.name = name;
        this.elementName = elementName;
    }

    @Override
    public WSManClassEnum getCommandEnum() {
        return cmdEnum;
    }

    @Override
    public String getSystemCreationClassName() {
        return systemCreationClassName;
    }

    @Override
    public String getCreationClassName() {
        return creationClassName;
    }

    @Override
    public String getSystemName() {
        return systemName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getElementName() {
        return elementName;
    }
}
