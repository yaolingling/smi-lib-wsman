/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
/**
 * 
 */
package com.dell.isg.smi.wsman.command.raidcmd;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dell.isg.smi.commons.elm.exception.RuntimeCoreException;
import com.dell.isg.smi.wsman.WSManBaseCommand.WSManClassEnum;
import com.dell.isg.smi.wsman.command.raidcmd.factory.RaidFactory;
import com.dell.isg.smi.wsman.command.raidcmd.factory.RaidViewFactory;
import com.dell.isg.smi.wsman.entity.KeyValuePair;
import com.dell.isg.smi.wsman.entity.RaidConfigDetails;
import com.dell.isg.smi.wsman.entity.RaidViewCtrlEntity;
import com.dell.isg.smi.wsman.entity.RaidViewPDEntity;
import com.dell.isg.smi.wsman.entity.RaidViewVDEntity;

/**
 * 
 * @author Rahamn_MUhammad
 */
public class RaidTopologyCmd {

    private static final Logger logger = LoggerFactory.getLogger(RaidTopologyCmd.class);

    private List<RaidViewCtrlEntity> lsCtrl = new ArrayList<RaidViewCtrlEntity>();
    private List<RaidViewPDEntity> lsPDs = new ArrayList<RaidViewPDEntity>();
    private List<RaidViewVDEntity> lsVDs = new ArrayList<RaidViewVDEntity>();
    private int value = 0;


    /**
     * 
     */
    @SuppressWarnings("unchecked")
    public RaidTopologyCmd(String ipAddress, String userName, String pwd) {
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: RaidTopologyCmd(String ipAddress - %s, String userName - %s, String pwd - %s)", ipAddress, userName, "####"));
        }
        RaidFactory fac = new RaidViewFactory();
        RaidViewFactory facView = (RaidViewFactory) fac;
        facView.setCredentials(ipAddress, userName, pwd);

        try {
            lsCtrl = (List<RaidViewCtrlEntity>) facView.getRaidView(WSManClassEnum.DCIM_ControllerView.toString()).execute();
            lsPDs = (List<RaidViewPDEntity>) facView.getRaidView(WSManClassEnum.DCIM_PhysicalDiskView.toString()).execute();
            lsVDs = (List<RaidViewVDEntity>) facView.getRaidView(WSManClassEnum.DCIM_VirtualDiskView.toString()).execute();

        } catch (Exception e) {
            logger.debug("Cannot get Raid Topology:" + e.getMessage());
        }

        logger.trace("Exiting constructor: RaidTopologyCmd()");

    }


    public List<RaidConfigDetails> getRaidTopology() throws RuntimeCoreException {

        logger.debug("Entering getRaidTopology()");

        List<RaidConfigDetails> raidTopologyList = new ArrayList<RaidConfigDetails>();
        RaidConfigDetails raidTopologyObj = null;
        if (lsCtrl == null || lsCtrl.size() <= 0) {
            logger.debug("Cannot get Raid Topology:Controllers are missing");
            return raidTopologyList;
        }

        List<RaidViewPDEntity> tempPDList = new ArrayList<RaidViewPDEntity>();
        RaidViewPDEntity tempPD = null;

        List<RaidViewVDEntity> tempVDList = new ArrayList<RaidViewVDEntity>();
        RaidViewVDEntity tempVD = null;

        List<KeyValuePair> tempDHList = new ArrayList<KeyValuePair>();
        KeyValuePair tempDH = null;

        List<String> tempGHList = new ArrayList<String>();
        String tempGH = null;
        boolean isGhs = false;

        for (RaidViewCtrlEntity ctrlElement : lsCtrl) {
            logger.debug("Entering the getRaidTopology:Ctrl");

            reset();

            isGhs = false;

            raidTopologyObj = new RaidConfigDetails();
            raidTopologyObj.setFqddCtrl(ctrlElement.getFqddCtrl());
            raidTopologyObj.setNameCtrl(ctrlElement.getNameCtrl());
            raidTopologyObj.setLocationCtrl(ctrlElement.getPciSlotCtrl());
            raidTopologyObj.setSecStatusCtrl(ctrlElement.getSecStatusCtrl());

            try {
                updatePDCount(lsPDs, ctrlElement.getFqddCtrl());
            } catch (Exception e) {
                logger.debug("Physial Disk count could not be updated:updatePDCount");
            }

            raidTopologyObj.setCountPD(value);
            if (lsVDs == null) {
                logger.debug("Raid Topology:Virtual Disks absent.");

                if (lsPDs != null) {
                    tempDHList = new ArrayList<KeyValuePair>();
                    tempPDList = new ArrayList<RaidViewPDEntity>();

                    for (java.util.Iterator<RaidViewPDEntity> iterPDs = lsPDs.iterator(); iterPDs.hasNext();) {
                        RaidViewPDEntity pdElement = iterPDs.next();

                        /*
                         * FIXC ME for ICEE if(SpectreUtils.isSubStrMatch(pdElement.getFdqqPD(),ctrlElement.getFqddCtrl(), ":")) { logger.debug("Entering the getRaidTopology:PD");
                         * tempPD = new RaidViewPDEntity(); tempPD.setPhysicalID(pdElement.getFdqqPD()); tempPD.setFdqqPD(getSplitString(pdElement.getFdqqPD(), ":"));
                         * tempPD.setFullFdqqPD(pdElement.getFdqqPD()); tempPD.setSizeInBytesPD(pdElement.getSizeInBytesPD());
                         * tempPD.setAssignSparePD(pdElement.getAssignSparePD()); tempPD.setMediaTypePD(pdElement.getMediaTypePD());
                         * tempPD.setBusProtocolPD(pdElement.getBusProtocolPD()); tempPD.setDiskSerialNumber(pdElement.getDiskSerialNumber()); tempPDList.add(tempPD); }
                         */
                    }
                }

                if (tempPDList != null && tempPDList.size() > 0) {
                    tempVD = new RaidViewVDEntity();
                    tempVD.setRaidCtrlPD(tempPDList);
                }

                tempVDList.add(tempVD);

                raidTopologyObj.setRaidCtrlVD(tempVDList);
                raidTopologyList.add(raidTopologyObj);

                tempVDList = new ArrayList<RaidViewVDEntity>();
                tempGHList = new ArrayList<String>();

                continue;
            }

            for (RaidViewVDEntity vdElement : lsVDs) {
                logger.debug("Entering the getRaidTopology:VD");
                if (vdElement.getNameVD().startsWith("Pending")) {
                    logger.debug("Config Job might be pending");
                    continue;
                }

                tempDHList = new ArrayList<KeyValuePair>();
                tempPDList = new ArrayList<RaidViewPDEntity>();

                /*
                 * FIX ME for ICEE
                 * 
                 * if(SpectreUtils.isSubStrMatch(vdElement.getFqddVD(),ctrlElement.getFqddCtrl(), ":")) { tempVD = new RaidViewVDEntity(); tempVD.setFqddVD(vdElement.getFqddVD());
                 * tempVD.setNameVD(vdElement.getNameVD()); tempVD.setRaidStatusVD(vdElement.getRaidStatusVD()); tempVD.setRaidTypesVD(vdElement.getRaidTypesVD());
                 * tempVD.setSizeInBytesVD(vdElement.getSizeInBytesVD()); tempVD.setSpanLengthVD(vdElement.getSpanLengthVD()); tempVDList.add(tempVD); }
                 */

                for (java.util.Iterator<RaidViewPDEntity> iterPDs = lsPDs.iterator(); iterPDs.hasNext();) {
                    tempPD = new RaidViewPDEntity();
                    RaidViewPDEntity pdElement = iterPDs.next();
                    /*
                     * FIX ME for ICEE
                     * 
                     * if(SpectreUtils.isSubStrMatch(vdElement.getFqddVD(),ctrlElement.getFqddCtrl(), ":") &&
                     * (SpectreUtils.isMatch(vdElement.getPhysicalIDsVD(),pdElement.getFdqqPD()))) { logger.debug("Entering the getRaidTopology:PD");
                     * 
                     * if(pdElement.getAssignSparePD().equalsIgnoreCase("Dedicated") && (SpectreUtils.isSubStrMatch(pdElement.getFdqqPD(),ctrlElement.getFqddCtrl(), ":"))){ tempDH
                     * = new KeyValuePair(); tempDH.setKey(pdElement.getFdqqPD()); tempDH.setValue(getSplitString(pdElement.getFdqqPD(), ":")); tempDHList.add(tempDH); } else {
                     * tempPD.setPhysicalID(pdElement.getFdqqPD()); tempPD.setFdqqPD(getSplitString(pdElement.getFdqqPD(), ":")); tempPD.setFullFdqqPD(pdElement.getFdqqPD());
                     * tempPD.setSizeInBytesPD(pdElement.getSizeInBytesPD()); tempPD.setAssignSparePD(pdElement.getAssignSparePD());
                     * tempPD.setMediaTypePD(pdElement.getMediaTypePD()); tempPD.setBusProtocolPD(pdElement.getBusProtocolPD());
                     * tempPD.setDiskSerialNumber(pdElement.getDiskSerialNumber()); tempPDList.add(tempPD); } }
                     * 
                     * if( !isGhs && pdElement.getAssignSparePD().equalsIgnoreCase("Global") && (SpectreUtils.isSubStrMatch(pdElement.getFdqqPD(),ctrlElement.getFqddCtrl(), ":"))){
                     * tempGH = pdElement.getFdqqPD(); tempGHList.add(tempGH); }
                     */
                }

                if (tempPDList != null && tempPDList.size() > 0 && tempVD != null) {
                    tempVD.setRaidCtrlPD(tempPDList);
                }

                if (tempDHList != null && tempDHList.size() > 0 && tempVD != null) {
                    tempVD.setRaidCtrlDH(tempDHList);
                }

                if (tempGHList != null && tempGHList.size() > 0) {
                    isGhs = true;
                    raidTopologyObj.setRaidCtrlGH(tempGHList);
                }

            }

            raidTopologyObj.setRaidCtrlVD(tempVDList);

            tempVDList = new ArrayList<RaidViewVDEntity>();
            tempGHList = new ArrayList<String>();

            raidTopologyList.add(raidTopologyObj);
        }

        logger.debug("Exiting getRaidTopology()");
        return raidTopologyList;
    }


    private String getSplitString(String inputStr, String separator) {
        logger.debug("Entering the getSplitString(String, String):" + inputStr);
        StringBuilder retStr = new StringBuilder();
        try {
            if (StringUtils.isEmpty(inputStr) && StringUtils.isBlank(inputStr)) {
                return "0:0:0";
            }

            String[] splitStrs = StringUtils.split(inputStr, separator);
            if (splitStrs != null && splitStrs.length >= 2) {
                if (splitStrs.length == 3) {
                    retStr.append(splitStrs[1].substring(splitStrs[1].lastIndexOf(".") + 1).replace("-", separator));
                } else if (splitStrs.length == 2) {
                    retStr.append(0);
                }

                retStr.append(separator);
                retStr.append(splitStrs[0].substring(splitStrs[0].lastIndexOf(".") + 1));
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.debug("getSplitString(String, String):ArrayIndexOutOfBoundsException" + inputStr);
        } catch (Exception e) {
            logger.debug("getSplitStrig(String, String):Exception" + inputStr);
        }

        logger.debug("Exiting the getSplitString");
        return retStr.toString();
    }


    private void updatePDCount(List<RaidViewPDEntity> rawResults, String ctrlName) {
        logger.debug("updatePDCount:Setting PD count:" + ctrlName);
        for (RaidViewPDEntity rawResult : rawResults) {
            if (isMatch(rawResult.getFdqqPD(), ctrlName, ":"))
                setValue();
        }
    }


    private boolean isMatch(String inputStr, String patternSr, String separator) {

        boolean isMatch = false;
        try {
            if (StringUtils.isEmpty(inputStr) && StringUtils.isBlank(inputStr))
                return false;
            String[] splitStrs = StringUtils.split(inputStr, separator);
            if (splitStrs != null && splitStrs.length >= 2) {
                if (splitStrs.length == 3) {
                    if (splitStrs[2].matches(patternSr))
                        return true;
                } else if (splitStrs.length == 2) {
                    if (splitStrs[1].matches(patternSr))
                        return true;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.debug("isMatch(String, String, String):" + inputStr);
        } catch (Exception e) {
            logger.debug("isMatch(String, String, String):" + inputStr);
        }
        return isMatch;
    }


    private int setValue() {
        click();
        return value;
    }


    @SuppressWarnings("unused")
    private int getValue() {
        return value;
    }


    private void reset() {
        value = 0;
    }


    private void click() {
        value = (value + 1);
    }

}
