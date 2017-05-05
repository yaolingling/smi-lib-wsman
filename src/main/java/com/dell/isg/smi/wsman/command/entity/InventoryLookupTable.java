/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.entity;

import java.util.HashMap;
import java.util.Map;

public class InventoryLookupTable {

    public enum LookupTableIntMapName {
        VIRTUALDISKVIEW_BUSPROTOCOL, VIRTUALDISKVIEW_MEDIATYPE, VIRTUALDISKVIEW_DISKCACHEPOLICY, VIRTUALDISKVIEW_READCACHEPOLICY, VIRTUALDISKVIEW_WRITECACHEPOLICY, VIRTUALDISKVIEW_RAIDTYPES, PHYSICALDISKVIEW_BUSPROTOCOL, CONTROLLERVIEW_PATROLREADSTATE, MEMORYVIEW_MEMORYTYPE, POWERSUPPLYVIEW_TYPE, DCSTORAGEOBJECT_BUSPROTOCOL, DCSTORAGEOBJECT_DEFAULTREADPOLICY, DCSTORAGEOBJECT_DEFAULTWRITEPOLICY, DCSTORAGEOBJECT_DISKCACHEPOLICY, DCSTORAGEOBJECT_RAIDTYPE, DCSTORAGEOBJECT_STRIPESIZE, DCSTORAGEOBJECT_MEDIATYPE, DCSTPRAGEOBJECT_PATROLREADSTATE, POWERSUPPLYVIEW_STATUS, NICSTATISTICS_LINKSTATUS
    }

    public enum LookupTableStringMapName {
        DCIMNICVIEWTYPE_PCIVENDORID, POWERSUPPLYVIEW_DETAILEDSTATE
    }

    private static Map<Integer, String> mapRaidTypeToString = new HashMap<Integer, String>();
    private static Map<Integer, String> mapPatrolReadStateToString = new HashMap<Integer, String>();
    private static Map<Integer, String> mapBusProtocolToString = new HashMap<Integer, String>();
    private static Map<Integer, String> mapDefaultReadPolicyToString = new HashMap<Integer, String>();
    private static Map<Integer, String> mapDefaultWritePolicyToString = new HashMap<Integer, String>();
    private static Map<Integer, String> mapDiskCachePolicyToString = new HashMap<Integer, String>();
    private static Map<Integer, String> mapMediaTypeToString = new HashMap<Integer, String>();
    private static Map<Integer, String> mapStripeSizeToString = new HashMap<Integer, String>();
    private static Map<String, String> mapPciIdstoVendors = new HashMap<String, String>();
    private static Map<Integer, String> mapLinkStatusToString = new HashMap<Integer, String>();

    // VirtualDiskView
    private static Map<Integer, String> mapVirtualDiskBusProtocol = new HashMap<Integer, String>();
    private static Map<Integer, String> mapVirtualDiskMediaType = new HashMap<Integer, String>();
    private static Map<Integer, String> mapVirtualDiskCachePolicy = new HashMap<Integer, String>();
    private static Map<Integer, String> mapVirtualDiskReadCachePolicy = new HashMap<Integer, String>();
    private static Map<Integer, String> mapVirtualDiskWriteCachePolicy = new HashMap<Integer, String>();
    private static Map<Integer, String> mapVirtualDiskRaidType = new HashMap<Integer, String>();
    private static Map<Integer, String> mapEmpty = new HashMap<Integer, String>();

    // PhysicalDiskView
    private static Map<Integer, String> mapPhysicalDiskBusProtocol = new HashMap<Integer, String>();

    // ControllerView
    private static Map<Integer, String> mapControllerPatrolReadState = new HashMap<Integer, String>();

    // MemoryView
    private static Map<Integer, String> mapMemoryType = new HashMap<Integer, String>();

    // PowerSupplyView
    private static Map<Integer, String> mapPowerSupplyType = new HashMap<Integer, String>();
    private static Map<Integer, String> mapPowerSupplyStatus = new HashMap<Integer, String>();
    private static Map<String, String> mapPowerSupplyDetailedStatus = new HashMap<String, String>();


    public static String lookup(LookupTableIntMapName mapNameEnum, Short key) {
        Integer intKey = new Integer(key);
        return lookup(mapNameEnum, intKey);
    }


    public static String lookup(LookupTableIntMapName mapNameEnum, Integer key) {
        String returnValue = "NA";
        int notFoundKey = -99;
        Map<Integer, String> map = null;
        switch (mapNameEnum) {
        case VIRTUALDISKVIEW_BUSPROTOCOL:
            map = mapVirtualDiskBusProtocol;
            notFoundKey = 0;
            break;
        case VIRTUALDISKVIEW_MEDIATYPE:
            map = mapVirtualDiskMediaType;
            notFoundKey = 0;
            break;
        case VIRTUALDISKVIEW_DISKCACHEPOLICY:
            map = mapVirtualDiskCachePolicy;
            notFoundKey = 0;
            break;
        case VIRTUALDISKVIEW_READCACHEPOLICY:
            map = mapVirtualDiskReadCachePolicy;
            notFoundKey = 0;
            break;
        case VIRTUALDISKVIEW_WRITECACHEPOLICY:
            map = mapVirtualDiskWriteCachePolicy;
            notFoundKey = 0;
            break;
        case VIRTUALDISKVIEW_RAIDTYPES:
            map = mapVirtualDiskRaidType;
            notFoundKey = 0;
            break;
        case PHYSICALDISKVIEW_BUSPROTOCOL:
            map = mapPhysicalDiskBusProtocol;
            notFoundKey = 0;
            break;
        case CONTROLLERVIEW_PATROLREADSTATE:
            map = mapControllerPatrolReadState;
            notFoundKey = 0;
            break;
        case MEMORYVIEW_MEMORYTYPE:
            map = mapMemoryType;
            notFoundKey = 2;
            break;
        case POWERSUPPLYVIEW_TYPE:
            map = mapPowerSupplyType;
            break;
        case DCSTORAGEOBJECT_BUSPROTOCOL:
            map = mapBusProtocolToString;
            notFoundKey = 0;
            break;
        case DCSTORAGEOBJECT_DEFAULTREADPOLICY:
            map = mapDefaultReadPolicyToString;
            break;
        case DCSTORAGEOBJECT_DEFAULTWRITEPOLICY:
            map = mapDefaultWritePolicyToString;
            break;
        case DCSTORAGEOBJECT_DISKCACHEPOLICY:
            map = mapDiskCachePolicyToString;
            break;
        case DCSTORAGEOBJECT_RAIDTYPE:
            map = mapRaidTypeToString;
            break;
        case DCSTORAGEOBJECT_STRIPESIZE:
            map = mapStripeSizeToString;
            break;
        case DCSTORAGEOBJECT_MEDIATYPE:
            map = mapMediaTypeToString;
            break;
        case DCSTPRAGEOBJECT_PATROLREADSTATE:
            map = mapPatrolReadStateToString;
            break;
        case POWERSUPPLYVIEW_STATUS:
            map = mapPowerSupplyStatus;
            break;
        case NICSTATISTICS_LINKSTATUS:
            map = mapLinkStatusToString;
            notFoundKey = 0;
            break;

        default:
            map = mapEmpty;
            break;
        }
        if (map.containsKey(key)) {
            returnValue = map.get(key);
        } else if ((notFoundKey != -99) && map.containsKey(notFoundKey)) {
            returnValue = map.get(notFoundKey);
        }
        return returnValue;
    }


    public static String lookup(LookupTableStringMapName mapNameEnum, String key) {
        String returnValue = "NA";
        Map<?, String> map = null;
        switch (mapNameEnum) {
        case DCIMNICVIEWTYPE_PCIVENDORID:
            map = mapPciIdstoVendors;
            break;
        case POWERSUPPLYVIEW_DETAILEDSTATE:
            map = mapPowerSupplyDetailedStatus;
            returnValue = key; // return the key if not in map
            break;
        default:
            map = mapEmpty;
            break;
        }

        if (map.containsKey(key)) {
            returnValue = map.get(key);
        }
        return returnValue;
    }

    static {
        mapRaidTypeToString.put(1, "RAID_TYPE_CONCAT");
        mapRaidTypeToString.put(2, "RAID_TYPE_RAID0");
        mapRaidTypeToString.put(4, "RAID_TYPE_RAID1");
        mapRaidTypeToString.put(8, "RAID_TYPE_RAID2");
        mapRaidTypeToString.put(16, "RAID_TYPE_RAID3");
        mapRaidTypeToString.put(32, "RAID_TYPE_RAID4");
        mapRaidTypeToString.put(64, "RAID_TYPE_RAID5");
        mapRaidTypeToString.put(128, "RAID_TYPE_RAID6");
        mapRaidTypeToString.put(256, "RAID_TYPE_RAID7");
        mapRaidTypeToString.put(512, "RAID_TYPE_RAID10");
        mapRaidTypeToString.put(1024, "RAID_TYPE_RAID30");
        mapRaidTypeToString.put(2048, "RAID_TYPE_RAID50");
        mapRaidTypeToString.put(4096, "RAID_TYPE_CONCAT_RAID1");
        mapRaidTypeToString.put(8192, "RAID_TYPE_CONCAT_RAID5");
        mapRaidTypeToString.put(16384, "RAID_TYPE_NONE");
        mapRaidTypeToString.put(32768, "RAID_TYPE_VOLUME");
        mapRaidTypeToString.put(65536, "RAID_TYPE_RAID_MORPH");

        mapPatrolReadStateToString.put(1, "CONTROLLER_PATROL_READ_STATE_STOPPED");
        mapPatrolReadStateToString.put(2, "CONTROLLER_PATROL_READ_STATE_READY");
        mapPatrolReadStateToString.put(4, "CONTROLLER_PATROL_READ_STATE_ACTIVE");
        mapPatrolReadStateToString.put(8, "CONTROLLER_PATROL_READ_STATE_ABORTED");

        mapBusProtocolToString.put(0, "DISK_BUS_PROTOCOL_UNKNOWN");
        mapBusProtocolToString.put(1, "DISK_BUS_PROTOCOL_SCSI");
        mapBusProtocolToString.put(2, "DISK_BUS_PROTOCOL_PATA");
        mapBusProtocolToString.put(3, "DISK_BUS_PROTOCOL_FIBRE");
        mapBusProtocolToString.put(4, "DISK_BUS_PROTOCOL_SSA");
        mapBusProtocolToString.put(5, "DISK_BUS_PROTOCOL_USB");
        mapBusProtocolToString.put(7, "DISK_BUS_PROTOCOL_SATA");
        mapBusProtocolToString.put(8, "DISK_BUS_PROTOCOL_SAS");

        mapDefaultReadPolicyToString.put(1, "DEFAULT_READ_POLICY_ENABLED");
        mapDefaultReadPolicyToString.put(2, "DEFAULT_READ_POLICY_DISABLED");
        mapDefaultReadPolicyToString.put(4, "DEFAULT_READ_POLICY_CONTROLLER_READ_AHEAD");
        mapDefaultReadPolicyToString.put(8, "DEFAULT_READ_POLICY_ADAPTIVE_READ_AHEAD");
        mapDefaultReadPolicyToString.put(16, "DEFAULT_READ_POLICY_NO_READ_AHEAD");

        mapDefaultWritePolicyToString.put(1, "DEFAULT_WRITE_POLICY_ENABLED");
        mapDefaultWritePolicyToString.put(2, "DEFAULT_WRITE_POLICY_DISABLED");
        mapDefaultWritePolicyToString.put(4, "DEFAULT_WRITE_POLICY_WRITE_BACK");
        mapDefaultWritePolicyToString.put(8, "DEFAULT_WRITE_POLICY_WRITE_THROUGH");
        mapDefaultWritePolicyToString.put(16, "DEFAULT_WRITE_POLICY_CACHE_ALWAYS_ENABLED");
        mapDefaultWritePolicyToString.put(32, "DEFAULT_WRITE_POLICY_WRITE_BACK_FORCE");

        mapDiskCachePolicyToString.put(0, "DISK_CACHE_POLICY_UNCHANGED");
        mapDiskCachePolicyToString.put(1, "DISK_CACHE_POLICY_ENABLED");
        mapDiskCachePolicyToString.put(2, "DISK_CACHE_POLICY_DISABLED");

        mapRaidTypeToString.put(1, "RAID_TYPE_CONCAT");
        mapRaidTypeToString.put(2, "RAID_TYPE_RAID0");
        mapRaidTypeToString.put(4, "RAID_TYPE_RAID1");
        mapRaidTypeToString.put(8, "RAID_TYPE_RAID2");
        mapRaidTypeToString.put(16, "RAID_TYPE_RAID3");
        mapRaidTypeToString.put(32, "RAID_TYPE_RAID4");
        mapRaidTypeToString.put(64, "RAID_TYPE_RAID5");
        mapRaidTypeToString.put(128, "RAID_TYPE_RAID6");
        mapRaidTypeToString.put(256, "RAID_TYPE_RAID7");
        mapRaidTypeToString.put(512, "RAID_TYPE_RAID10");
        mapRaidTypeToString.put(1024, "RAID_TYPE_RAID30");
        mapRaidTypeToString.put(2048, "RAID_TYPE_RAID50");
        mapRaidTypeToString.put(4096, "RAID_TYPE_CONCAT_RAID1");
        mapRaidTypeToString.put(8192, "RAID_TYPE_CONCAT_RAID5");
        mapRaidTypeToString.put(16384, "RAID_TYPE_NONE");
        mapRaidTypeToString.put(32768, "RAID_TYPE_VOLUME");
        mapRaidTypeToString.put(65536, "RAID_TYPE_RAID_MORPH");

        mapMediaTypeToString.put(1, "DISK_MEDIA_TYPE_HDD");
        mapMediaTypeToString.put(2, "DISK_MEDIA_TYPE_SSD");

        mapStripeSizeToString.put(0, "Default");
        mapStripeSizeToString.put(1, "512 Byte");
        mapStripeSizeToString.put(2, "1K");
        mapStripeSizeToString.put(4, "2K");
        mapStripeSizeToString.put(8, "4K");
        mapStripeSizeToString.put(16, "8K");
        mapStripeSizeToString.put(32, "16K");
        mapStripeSizeToString.put(64, "32K");
        mapStripeSizeToString.put(128, "64K");
        mapStripeSizeToString.put(256, "128K");
        mapStripeSizeToString.put(512, "256K");
        mapStripeSizeToString.put(1024, "512K");
        mapStripeSizeToString.put(2048, "1M");
        mapStripeSizeToString.put(4096, "2M");
        mapStripeSizeToString.put(8192, "4M");
        mapStripeSizeToString.put(16384, "8M");
        mapStripeSizeToString.put(32768, "16M");

        mapPciIdstoVendors.put("8086", "VENDOR_INTEL");
        mapPciIdstoVendors.put("14E4", "VENDOR_BROADCOM");
        mapPciIdstoVendors.put("1077", "VENDOR_QLOGIC");
        mapPciIdstoVendors.put("10DF", "VENDOR_EMULEX");
        mapPciIdstoVendors.put("15B3", "VENDOR_MALLANOX");

        mapLinkStatusToString.put(0, "INVENTORY_NETWORK_LINK_STATUS_UNKNOWN");
        mapLinkStatusToString.put(1, "INVENTORY_NETWORK_LINK_STATUS_CONNECTED");
        mapLinkStatusToString.put(3, "INVENTORY_NETWORK_LINK_STATUS_DISCONNECTED");

        // VirtualDiskView
        mapVirtualDiskBusProtocol.put(0, "DISK_BUS_PROTOCOL_UNKNOWN");
        mapVirtualDiskBusProtocol.put(1, "DISK_BUS_PROTOCOL_SCSI");
        mapVirtualDiskBusProtocol.put(2, "DISK_BUS_PROTOCOL_PATA");
        mapVirtualDiskBusProtocol.put(3, "DISK_BUS_PROTOCOL_FIBRE");
        mapVirtualDiskBusProtocol.put(4, "DISK_BUS_PROTOCOL_USB");
        mapVirtualDiskBusProtocol.put(5, "DISK_BUS_PROTOCOL_SATA");
        mapVirtualDiskBusProtocol.put(6, "DISK_BUS_PROTOCOL_SAS");
        mapVirtualDiskMediaType.put(0, "DISK_MEDIA_TYPE_UNKNOWN");
        mapVirtualDiskMediaType.put(1, "DISK_MEDIA_TYPE_MAGNETIC_DRIVE");
        mapVirtualDiskMediaType.put(2, "DISK_MEDIA_TYPE_SOLID_STATE_DRIVE");
        mapVirtualDiskCachePolicy.put(0, "DISK_CACHE_POLICY_UNKNOWN");
        mapVirtualDiskCachePolicy.put(256, "DISK_CACHE_POLICY_DEFAULT");
        mapVirtualDiskCachePolicy.put(512, "DISK_CACHE_POLICY_ENABLED");
        mapVirtualDiskCachePolicy.put(1024, "DISK_CACHE_POLICY_DISABLED");
        mapVirtualDiskReadCachePolicy.put(0, "READ_CACHE_POLICY_UNKNOWN");
        mapVirtualDiskReadCachePolicy.put(16, "READ_CACHE_POLICY_NO_READ_AHEAD");
        mapVirtualDiskReadCachePolicy.put(32, "READ_CACHE_POLICY_READ_AHEAD");
        mapVirtualDiskReadCachePolicy.put(64, "READ_CACHE_POLICY_ADAPTIVE");
        mapVirtualDiskWriteCachePolicy.put(0, "WRITE_CACHE_POLICY_UNKNOWN");
        mapVirtualDiskWriteCachePolicy.put(1, "WRITE_CACHE_POLICY_WRITE_THROUGH");
        mapVirtualDiskWriteCachePolicy.put(2, "WRITE_CACHE_POLICY_WRITE_BACK");
        mapVirtualDiskWriteCachePolicy.put(4, "WRITE_CACHE_POLICY_WRITE_BACK_FORCE");
        mapVirtualDiskRaidType.put(0, "RAID_TYPE_UNKNOWN");
        mapVirtualDiskRaidType.put(1, "RAID_TYPE_NONE");
        mapVirtualDiskRaidType.put(2, "RAID_TYPE_RAID0");
        mapVirtualDiskRaidType.put(4, "RAID_TYPE_RAID1");
        mapVirtualDiskRaidType.put(64, "RAID_TYPE_RAID5");
        mapVirtualDiskRaidType.put(128, "RAID_TYPE_RAID6");
        mapVirtualDiskRaidType.put(2048, "RAID_TYPE_RAID10");
        mapVirtualDiskRaidType.put(8192, "RAID_TYPE_RAID50");
        mapVirtualDiskRaidType.put(16384, "RAID_TYPE_RAID60");

        // PhysicalDiskView
        mapPhysicalDiskBusProtocol.put(0, "DISK_BUS_PROTOCOL_UNKNOWN");
        mapPhysicalDiskBusProtocol.put(1, "DISK_BUS_PROTOCOL_SCSI");
        mapPhysicalDiskBusProtocol.put(2, "DISK_BUS_PROTOCOL_PATA");
        mapPhysicalDiskBusProtocol.put(3, "DISK_BUS_PROTOCOL_FIBRE");
        mapPhysicalDiskBusProtocol.put(4, "DISK_BUS_PROTOCOL_USB");
        mapPhysicalDiskBusProtocol.put(5, "DISK_BUS_PROTOCOL_SATA");
        mapPhysicalDiskBusProtocol.put(6, "DISK_BUS_PROTOCOL_SAS");

        // ControllerView
        mapControllerPatrolReadState.put(0, "CONTROLLER_PATROL_READ_STATE_UNKNOWN");
        mapControllerPatrolReadState.put(1, "CONTROLLER_PATROL_READ_STATE_STOPPED");
        mapControllerPatrolReadState.put(2, "CONTROLLER_PATROL_READ_STATE_RUNNING");

        // MemoryView MemoryType
        mapMemoryType.put(1, "MEMORY_TYPE_OTHER");
        mapMemoryType.put(2, "MEMORY_TYPE_UNKNOWN");
        mapMemoryType.put(3, "MEMORY_TYPE_DRAM");
        mapMemoryType.put(4, "MEMORY_TYPE_EDRAM");
        mapMemoryType.put(5, "MEMORY_TYPE_VRAM");
        mapMemoryType.put(6, "MEMORY_TYPE_SRAM");
        mapMemoryType.put(7, "MEMORY_TYPE_RAM");
        mapMemoryType.put(8, "MEMORY_TYPE_ROM");
        mapMemoryType.put(9, "MEMORY_TYPE_FLASH");
        mapMemoryType.put(10, "MEMORY_TYPE_EEPROM");
        mapMemoryType.put(11, "MEMORY_TYPE_FEPROM");
        mapMemoryType.put(12, "MEMORY_TYPE_EPROM");
        mapMemoryType.put(13, "MEMORY_TYPE_CDRAM");
        mapMemoryType.put(14, "MEMORY_TYPE_3DRAM");
        mapMemoryType.put(15, "MEMORY_TYPE_SDRAM");
        mapMemoryType.put(16, "MEMORY_TYPE_SGRAM");
        mapMemoryType.put(17, "MEMORY_TYPE_RDRAM");
        mapMemoryType.put(18, "MEMORY_TYPE_DDR");
        mapMemoryType.put(19, "MEMORY_TYPE_DDR2");
        mapMemoryType.put(20, "MEMORY_TYPE_DDR2FBDIMM");
        mapMemoryType.put(24, "MEMORY_TYPE_DDR3");
        mapMemoryType.put(25, "MEMORY_TYPE_FBD2");
        mapMemoryType.put(26, "MEMORY_TYPE_DDR4");

        // PowerSupplyView Type
        mapPowerSupplyType.put(0, "POWER_SUPPLY_TYPE_AC");
        mapPowerSupplyType.put(1, "POWER_SUPPLY_TYPE_DC");

        // PowerSupplyView Status
        // ValueMap { "0", "1", "2", "3", "0x8000", "0xFFFF"}
        // Values { "Unknown", "OK", "Degraded", "Error", "DMTF Reserved", "Vendor Reserved" }
        mapPowerSupplyStatus.put(0, "POWER_SUPPLY_STATUS_UNKNOWN");
        mapPowerSupplyStatus.put(1, "POWER_SUPPLY_STATUS_OK");
        mapPowerSupplyStatus.put(2, "POWER_SUPPLY_STATUS_DEGRADED");
        mapPowerSupplyStatus.put(3, "POWER_SUPPLY_STATUS_ERROR");
        mapPowerSupplyStatus.put(32768, "POWER_SUPPLY_STATUS_DMTF_RESERVED");
        mapPowerSupplyStatus.put(65535, "POWER_SUPPLY_STATUS_VENDOR_RESERVED");

        // PowerSuplyView DetailedStatus
        mapPowerSupplyDetailedStatus.put("Absent", "POWER_SUPPLY_DETAILED_STATUS_ABSENT");
        mapPowerSupplyDetailedStatus.put("Presence Detected", "POWER_SUPPLY_DETAILED_STATUS_PRESENT");
        mapPowerSupplyDetailedStatus.put("Failure Detected", "POWER_SUPPLY_DETAILED_STATUS_FAILURE");
        mapPowerSupplyDetailedStatus.put("Failed to read", "POWER_SUPPLY_DETAILED_STATUS_FAILURE");
        mapPowerSupplyDetailedStatus.put("Unknown", "POWER_SUPPLY_DETAILED_STATUS_UNKNOWN");
    }
}
