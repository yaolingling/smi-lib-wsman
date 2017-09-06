package com.dell.isg.smi.wsman.command;

import com.dell.isg.smi.wsmanclient.IWSManClassEnum;

public enum WSManClassEnum implements IWSManClassEnum {

    DCIM_SoftwareIdentity, DCIM_SoftwareInstallationService, DCIM_SoftwareUpdateConcreteJob, DCIM_SystemView, DCIM_ControllerView, DCIM_VirtualDiskView, DCIM_PhysicalDiskView, DCIM_BIOSEnumeration, DCIM_BootSourceSetting, DCIM_BootConfigSetting, DCIM_BIOSString, DCIM_BIOSInteger, DCIM_NICView, DCIM_NICString, DCIM_NICInteger, DCIM_NICEnumeration, DCIM_FCView, DCIM_FCString, DCIM_FCInteger, DCIM_FCEnumeration, DCIM_LifecycleJob, DCIM_JobService, InstallFromURI, CreateRebootJob, SetupJobQueue, DCIM_ComputerSystem, DCIM_OSDeploymentService, DCIM_BIOSService, DCIM_OEM_DataAccessModule, DCIM_RAIDService, CIM_ComputerSystem, DCIM_LCService, DCIM_IDRACCardView, DCIM_SPComputerSystem, CIM_IPProtocolEndpoint, CIM_Chassis, CIM_SoftwareIdentity, CIM_InstalledSoftwareIdentity, DCIM_Memoryview, DCIM_Powersupplyview, DCIM_PCIDeviceView, DCIM_CPUView, DCIM_EnclosureView, DCIM_iDRACCardAttribute, DCIM_PSNumericsensor, DCIM_iDRACCardService, DCIM_iDRACCardString, DCIM_iDRACCardEnumeration, DCIM_NICStatistics, DCIM_NICCapabilities, DCIM_BaseMetricValue, DCIM_AggregationMetricValue, CIM_Account, CIM_PhysicalElement, DCIM_FanView, DCIM_VFlashView, DCIM_Sensor, DCIM_NumericSensor, DCIM_ControllerBatteryView, DCIM_PCIeSSDView, DCIM_RAIDEnumeration, DCIM_RAIDString, DCIM_RAIDInteger, DCIM_SystemString, DCIM_LCLogEntry, DCIM_Sellogentry,
    DCIM_MFAAccount, // 11g account service
    DCIM_Account, // 12g account service
    DCIM_LicenseManagementService, DCIM_LicensableDevice, DCIM_SelRecordLog, DCIM_License, DCIM_SystemManagementService, DCIM_BladeServerView, DCIM_ModularChassisView, DCIM_SELLogEntry, DCIM_CSPowerManagementService;
}