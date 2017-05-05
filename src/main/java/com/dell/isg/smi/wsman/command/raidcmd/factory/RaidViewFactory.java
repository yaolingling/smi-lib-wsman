/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
/**
 * 
 */
package com.dell.isg.smi.wsman.command.raidcmd.factory;

import java.util.ArrayList;
import java.util.List;

import com.dell.isg.smi.wsman.WSManBaseCommand.WSManClassEnum;
import com.dell.isg.smi.wsman.command.raidcmd.AbstractRaid;
import com.dell.isg.smi.wsman.command.raidcmd.CreateRebootJobCmd;
import com.dell.isg.smi.wsman.command.raidcmd.RaidAssignSpareCmd;
import com.dell.isg.smi.wsman.command.raidcmd.RaidCheckVDValuesCmd;
import com.dell.isg.smi.wsman.command.raidcmd.RaidClearForeignConfig;
import com.dell.isg.smi.wsman.command.raidcmd.RaidCreateCmd;
import com.dell.isg.smi.wsman.command.raidcmd.RaidCreateTargetedConfigJobCmd;
import com.dell.isg.smi.wsman.command.raidcmd.RaidDHSDiskCmd;
import com.dell.isg.smi.wsman.command.raidcmd.RaidDelPendConfigCmd;
import com.dell.isg.smi.wsman.command.raidcmd.RaidDeleteCmd;
import com.dell.isg.smi.wsman.command.raidcmd.RaidDeleteJobQueueCmd;
import com.dell.isg.smi.wsman.command.raidcmd.RaidGetAvaiDiskCmd;
import com.dell.isg.smi.wsman.command.raidcmd.RaidGetRaidLevelCmd;
import com.dell.isg.smi.wsman.command.raidcmd.RaidResetConfigCmd;
import com.dell.isg.smi.wsman.command.raidcmd.RaidSetControllerKey;
import com.dell.isg.smi.wsman.command.raidcmd.RaidViewCtrlCmd;
import com.dell.isg.smi.wsman.command.raidcmd.RaidViewPDCmd;
import com.dell.isg.smi.wsman.command.raidcmd.RaidViewVDCmd;
import com.dell.isg.smi.wsman.command.raidcmd.RaidWSManMethodEnum;
import com.dell.isg.smi.wsman.entity.KeyValuePair;

/**
 * @author Anshu_Virk
 */
public class RaidViewFactory extends RaidFactory {

    private String ipAddress;
    private String userName;
    private String pwd;
    private List<KeyValuePair> keyValues = new ArrayList<KeyValuePair>();


    public RaidViewFactory() {
    }


    @Override
    public AbstractRaid getRaidView(String view) {

        AbstractRaid raidView = null;
        if (view.equalsIgnoreCase(WSManClassEnum.DCIM_ControllerView.toString())) {
            raidView = new RaidViewCtrlCmd(getIpAddress(), getUserName(), getPwd());
        } else if (view.equalsIgnoreCase(WSManClassEnum.DCIM_VirtualDiskView.toString())) {
            raidView = new RaidViewVDCmd(getIpAddress(), getUserName(), getPwd());
        } else if (view.equalsIgnoreCase(WSManClassEnum.DCIM_PhysicalDiskView.toString())) {
            raidView = new RaidViewPDCmd(getIpAddress(), getUserName(), getPwd());
        } else if (view.equalsIgnoreCase(RaidWSManMethodEnum.DeleteVirtualDisk.getDisplayString())) {
            raidView = new RaidDeleteCmd(getIpAddress(), getUserName(), getPwd());
            raidView.setKeyValues(keyValues);
        } else if (view.equalsIgnoreCase(RaidWSManMethodEnum.GetAvailableDisks.getDisplayString())) {
            raidView = new RaidGetAvaiDiskCmd(getIpAddress(), getUserName(), getPwd());
            raidView.setKeyValues(keyValues);
        } else if (view.equalsIgnoreCase(RaidWSManMethodEnum.GetRAIDLevels.getDisplayString())) {
            raidView = new RaidGetRaidLevelCmd(getIpAddress(), getUserName(), getPwd());
            raidView.setKeyValues(keyValues);
        } else if (view.equalsIgnoreCase(RaidWSManMethodEnum.CheckVDValues.getDisplayString())) {
            raidView = new RaidCheckVDValuesCmd(getIpAddress(), getUserName(), getPwd());
            raidView.setKeyValues(keyValues);
        } else if (view.equalsIgnoreCase(RaidWSManMethodEnum.CreateVirtualDisk.getDisplayString())) {
            raidView = new RaidCreateCmd(getIpAddress(), getUserName(), getPwd());
            raidView.setKeyValues(keyValues);
        } else if (view.equalsIgnoreCase(RaidWSManMethodEnum.AssignSpare.getDisplayString())) {
            raidView = new RaidAssignSpareCmd(getIpAddress(), getUserName(), getPwd());
            raidView.setKeyValues(keyValues);
        } else if (view.equalsIgnoreCase(RaidWSManMethodEnum.DeletePendingConfiguration.getDisplayString())) {
            raidView = new RaidDelPendConfigCmd(getIpAddress(), getUserName(), getPwd());
            raidView.setKeyValues(keyValues);
        } else if (view.equalsIgnoreCase(RaidWSManMethodEnum.ResetConfig.getDisplayString())) {
            raidView = new RaidResetConfigCmd(getIpAddress(), getUserName(), getPwd());
            raidView.setKeyValues(keyValues);
        } else if (view.equalsIgnoreCase(RaidWSManMethodEnum.CreateTargetedConfigJob.getDisplayString())) {
            raidView = new RaidCreateTargetedConfigJobCmd(getIpAddress(), getUserName(), getPwd());
            raidView.setKeyValues(keyValues);
        } else if (view.equalsIgnoreCase(RaidWSManMethodEnum.CreateRebootJob.getDisplayString())) {
            raidView = new CreateRebootJobCmd(getIpAddress(), getUserName(), getPwd());
            raidView.setKeyValues(keyValues);
        } else if (view.equalsIgnoreCase(RaidWSManMethodEnum.GetDHSDisks.getDisplayString())) {
            raidView = new RaidDHSDiskCmd(getIpAddress(), getUserName(), getPwd());
            raidView.setKeyValues(keyValues);
        } else if (view.equalsIgnoreCase(RaidWSManMethodEnum.JobStatusRaidCheckCmd.getDisplayString())) {
            raidView = new RaidDHSDiskCmd(getIpAddress(), getUserName(), getPwd());
        } else if (view.equalsIgnoreCase(RaidWSManMethodEnum.DeleteJobQueue.getDisplayString())) {
            raidView = new RaidDeleteJobQueueCmd(getIpAddress(), getUserName(), getPwd());
            raidView.setKeyValues(keyValues);
        } else if (view.equalsIgnoreCase(RaidWSManMethodEnum.ClearForeignConfig.getDisplayString())) {
            raidView = new RaidClearForeignConfig(getIpAddress(), getUserName(), getPwd());
            raidView.setKeyValues(keyValues);
        } else if (view.equalsIgnoreCase(RaidWSManMethodEnum.SetControllerKey.getDisplayString())) {
            raidView = new RaidSetControllerKey(getIpAddress(), getUserName(), getPwd());
            raidView.setKeyValues(keyValues);
        }
        return raidView;
    }


    public void setCredentials(String ipAddress, String userName, String pwd) {
        this.ipAddress = ipAddress;
        this.userName = userName;
        this.pwd = pwd;
    }


    public String getIpAddress() {
        return ipAddress;
    }


    public String getUserName() {
        return userName;
    }


    public String getPwd() {
        return pwd;
    }


    public void setKeyValues(List<KeyValuePair> keyValues) {
        this.keyValues = keyValues;
    }


    public List<KeyValuePair> getKeyValues() {
        return keyValues;
    }

}
