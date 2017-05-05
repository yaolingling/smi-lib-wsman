/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dell.isg.smi.commons.elm.CommonConstants;
import com.dell.isg.smi.wsman.ComponentIds;
import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.command.entity.update.UpdateDevice;
import com.dell.isg.smi.wsman.command.entity.update.UpdateFileInfo;
import com.dell.isg.smi.wsman.entity.LifeCycleJob;
import com.dell.isg.smi.wsman.entity.SoftwareIdentity;
import com.dell.isg.smi.wsman.exceptions.LCJobIsAlreadyRunningException;
import com.dell.isg.smi.wsman.exceptions.PackageNotApplicableException;
import com.dell.isg.smi.wsman.exceptions.PackageNotSupportedException;
import com.dell.isg.smi.wsman.utilities.Utilities;
import com.dell.isg.smi.wsman.utilities.XMLTool;

public class HardwareUpdateCmd extends UpdateBaseCmd {
    int port = 443;
    boolean bCertCheck;
    String instanceId = "";
    static int MAX_RETRY = 20;

    private static final Logger logger = LoggerFactory.getLogger(HardwareUpdateCmd.class);

    /**
     *
     * @param DRACIP
     * @param DRACPort
     * @param DRACUser
     * @param DRACPassword
     * @param filePath
     * @param userName
     * @param password
     * @param updateType
     * @throws SOAPException
     * @throws MalformedURLException
     */
    UpdateFileInfo fileInfo;
    List<SoftwareIdentity> inventory;
    private WSManageSession session = null;
    List<LifeCycleJob> currentJobs = null;


    public List<LifeCycleJob> getCurrentJobs() {
        return currentJobs;
    }


    public void setCurrentJobs(List<LifeCycleJob> currentJobs) {
        this.currentJobs = currentJobs;
    }


    public HardwareUpdateCmd(String DRACIP, int DRACPort, String DRACUser, String DRACPassword, UpdateFileInfo fileInfo, List<SoftwareIdentity> inventory, boolean bCertCheck) { // set
                                                                                                                                                                                 // the
                                                                                                                                                                                 // WSMan
                                                                                                                                                                                 // Session
        super(DRACIP, DRACPort, DRACUser, DRACPassword, bCertCheck);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: HardwareUpdateCmd(String DRACIP - %s, int DRACPort - %s, String DRACUser - %s, String DRACPassword - %s, UpdateFileInfo fileInfo - %s, List<SoftwareIdentity> inventory - List of %s, boolean bCertCheck - %s)", DRACIP, DRACPort, DRACUser, "####", UpdateFileInfo.class.getName(), SoftwareIdentity.class.getName(), Boolean.toString(bCertCheck)));
        }
        port = DRACPort;
        session = super.getSession();
        session.setResourceUri(getResourceURI());
        session.addSelector("CreationClassName", WSManClassEnum.DCIM_SoftwareInstallationService.toString());
        session.addSelector("SystemCreationClassName", WSManClassEnum.DCIM_ComputerSystem.toString());
        session.addSelector("SystemName", "IDRAC:ID");
        session.addSelector("Name", "SoftwareUpdate");
        session.setInvokeCommand(WSManMethodEnum.INSTALL_FROM_URI.toString());
        this.fileInfo = fileInfo;
        this.inventory = inventory;
        this.bCertCheck = bCertCheck;
        logger.trace("Exiting constructor: HardwareUpdateCmd()");
    }


    /**
     *
     * @return resource uri
     */
    private String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);

        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_SoftwareInstallationService);

        return sb.toString();
    }


    @Override
    public String execute() throws Exception {
        logger.trace("Entering function: execute()");
        String jobId = "";

        initCommand();
        deletePreviousJob(instanceId);

        String content = "";
        int retryCount = 1;
        Exception e = null;

        while (retryCount <= MAX_RETRY) {
            try {
                logger.info("Sending get request to send firmware update ... try = " + retryCount);
                content = XMLTool.convertAddressingToString(session.sendInvokeRequest());
                e = null;
                if (content != null) {
                    break;
                }
            } catch (SocketException ex) {
                logger.error(ex.getMessage());
                e = ex;
            } catch (IOException ex) {
                logger.error(ex.getMessage());
                e = ex;
            }
            retryCount++;
            Thread.sleep(CommonConstants.THIRTY_SEC);

        }

        if (e != null) {
            throw e;
        }

        jobId = getUpdateJobID(content);

        logger.trace("Exiting function: execute()");
        if (jobId != null && !jobId.isEmpty()) {
            return jobId;
        } else {
            return this.getLCErrorStr() + "@" + this.getLCErrorCode();
        }

    }


    private void initCommand() throws Exception {
        SOAPFactory factory = SOAPFactory.newInstance();

        // SOAPElement elementTarget = factory.createElement("Target", AccessHardwareInventoryByWiseman.PREFIX,
        // "http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/root/dcim/DCIM_SoftwareInstallationService");
        SOAPElement elementTarget = factory.createElement("Target");
        elementTarget.addNamespaceDeclaration("a", "http://schemas.xmlsoap.org/ws/2004/08/addressing");
        elementTarget.addNamespaceDeclaration("w", "http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd");

        SOAPElement elementAddress = elementTarget.addChildElement("Address", "a");
        elementAddress.setValue("http://schemas.xmlsoap.org/ws/2004/08/addressing/role/anonymous");

        SOAPElement params = elementTarget.addChildElement("ReferenceParameters", "a");
        params.addChildElement("ResourceURI", "w").setValue("http://schemas.dell.com/wbem/wscim/1/cim-schema/2/DCIM_SoftwareIdentity");

        SOAPElement selectorSet = params.addChildElement("SelectorSet", "w");
        SOAPElement selector = selectorSet.addChildElement("Selector", "w");

        selector.addAttribute(new QName("Name"), "InstanceID");

        instanceId = this.getInstId(fileInfo);

        selector.setValue(instanceId);

        String uri = getFileUri(fileInfo.getSourceFilePath(), fileInfo.getSourceUser(), fileInfo.getSourcePassword());

        session.addUserParam("URI", uri);
        session.addUserParam("Target", elementTarget);
    }


    private String getInstId(UpdateFileInfo fileInfo) throws PackageNotApplicableException, MalformedURLException, PackageNotSupportedException, Exception {
        if (fileInfo.getSourceFilePath() == null || fileInfo.getSourceFilePath().equals("")) {

            throw new MalformedURLException(fileInfo.getSourceFilePath());
        }

        String file = "";

        file = Utilities.getFileName(fileInfo.getSourceFilePath());
        file = file.toLowerCase();

        if (inventory != null && fileInfo.getDevices() != null && fileInfo.getDevices().size() > 0) {
            boolean devicePresentFlag = false;
            for (UpdateDevice device : fileInfo.getDevices()) {
                boolean isPCI = false;

                if (device != null) {
                    // String instanceInfo = "";

                    if (device.getPciDeviceInfo() != null && !device.getPciDeviceInfo().getVendorID().isEmpty() && !device.getDeviceID().isEmpty()) {
                        // instanceInfo = String.format("DCIM:INSTALLED:PCI:%s:%s:%s:%s", device.getPciDeviceInfo().getVendorID(),
                        // device.getPciDeviceInfo().getDeviceID(),device.getPciDeviceInfo().getSubDeviceID(),
                        // device.getPciDeviceInfo().getSubVendorID());
                        devicePresentFlag = true;
                        isPCI = true;
                    }

                    else if (!device.getDeviceID().isEmpty() && !device.getDeviceID().equals("N/A")) {
                        // instanceInfo = String.format("DCIM:INSTALLED:NONPCI:%s", device.getDeviceID());
                        devicePresentFlag = true;
                    }

                    if (devicePresentFlag) {
                        for (SoftwareIdentity identity : inventory) {
                            boolean deviceMatches = false;

                            if (identity.getInstanceID() != null && !identity.getInstanceID().isEmpty()) {
                                if (isPCI) {
                                    if (device.getPciDeviceInfo().getDeviceID().equalsIgnoreCase(identity.getDeviceID()) && device.getPciDeviceInfo().getVendorID().equalsIgnoreCase(identity.getVendorID())) {
                                        deviceMatches = true;
                                        if (device.getPciDeviceInfo().getSubDeviceID() != null && !device.getPciDeviceInfo().getSubDeviceID().isEmpty() && identity.getSubDeviceID() != null && !identity.getSubDeviceID().isEmpty()) {
                                            if (!device.getPciDeviceInfo().getSubDeviceID().equalsIgnoreCase(identity.getSubDeviceID())) {
                                                deviceMatches = false;
                                            }
                                        }
                                        if (device.getPciDeviceInfo().getSubVendorID() != null && !device.getPciDeviceInfo().getSubVendorID().isEmpty() && identity.getSubVendorID() != null && !identity.getSubVendorID().isEmpty()) {
                                            if (!device.getPciDeviceInfo().getSubVendorID().equalsIgnoreCase(identity.getSubVendorID())) {
                                                deviceMatches = false;
                                            }
                                        }
                                    }
                                } else if (device.getDeviceID() != null && device.getDeviceID().equalsIgnoreCase(identity.getComponentID())) {
                                    deviceMatches = true;
                                }
                                if (deviceMatches) {
                                    return identity.getInstanceID();
                                }

                            }
                        }
                    }
                }
            }
            // Devices are present in the input but they don't match the inventory.
            // Continue on the manual file name check if there is no valid device information present.
            if (devicePresentFlag) {
                throw new PackageNotApplicableException("The package is not applicable on this device");
            }

        }
        // This is a 1x1 update
        String instanceId = "";
        List<String> tokens = new ArrayList<String>();
        if (file.contains("bios")) {
            for (ComponentIds.BIOS id : ComponentIds.BIOS.values()) {
                tokens.add(id.toString());
            }
        } else if (file.contains("drvpk")) {
            for (ComponentIds.DRVPK id : ComponentIds.DRVPK.values()) {
                tokens.add(id.toString());
            }
        } else if (file.contains("diags") || file.contains("diag") || file.contains("diagnostics_")) {
            for (ComponentIds.DIAGS id : ComponentIds.DIAGS.values()) {
                tokens.add(id.toString());
            }
        } else if (file.contains("psu") || file.contains("power_")) {
            for (ComponentIds.PSU id : ComponentIds.PSU.values()) {
                tokens.add(id.toString());
            }
        } else if (file.contains("idrac") && file.contains("lifecycle-controller_")) {
            for (ComponentIds.iDRAC id : ComponentIds.iDRAC.values()) {
                tokens.add(id.toString());
            }
            for (ComponentIds.LC id : ComponentIds.LC.values()) {
                tokens.add(id.toString());
            }
        } else if (file.contains("lc_app") || file.contains("usc_app") || file.contains("lifecycle-controller_")) {
            for (ComponentIds.LC id : ComponentIds.LC.values()) {
                tokens.add(id.toString());
            }
        } else if (file.contains("idrac") || file.contains("esm_")) {
            for (ComponentIds.iDRAC id : ComponentIds.iDRAC.values()) {
                tokens.add(id.toString());
            }
        } else {
            throw new PackageNotSupportedException("The package is not supported by Dell Management Plug-in");
        }

        instanceId = getInstanceID(tokens);

        if (instanceId == null || instanceId.isEmpty()) {
            throw new PackageNotApplicableException("The package is not applicable on this device");
        } else {
            return instanceId;
        }

    }


    @SuppressWarnings("unchecked")
    private String getInstanceID(List<String> componentIds) throws Exception {
        String instanceID = "";
        EnumerateFirmwareInventory cmd = new EnumerateFirmwareInventory(super.getSession().getIpAddress(), port, super.getSession().getUser(), super.getSession().getPassword(), bCertCheck);
        Object result = null;
        try {
            result = cmd.execute();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        if (result != null) {
            List<SoftwareIdentity> listFirmware = (List<SoftwareIdentity>) result;
            for (String componentId : componentIds) {
                logger.info("======================== ComponentId to Check ===================" + componentId);
                for (SoftwareIdentity identity : listFirmware) {
                    logger.info("========================= ComponentId to Compare = " + identity.getComponentID() + " ============= InstanceId = " + identity.getInstanceID());
                    if (identity.getComponentID().trim().equals(componentId.trim()) && identity.getInstanceID().trim().toLowerCase().contains("installed")) {
                        logger.info(" ****************************  DEVICE MATCHED *******************************");
                        logger.info("InstanceId = " + identity.getInstanceID());
                        return identity.getInstanceID().trim();
                    }
                }
            }
        }
        return instanceID;
    }


    @SuppressWarnings("unchecked")
    private void deletePreviousJob(String instanceId) throws LCJobIsAlreadyRunningException {
        Object result = null;

        if (currentJobs == null) {

            EnumerateJobs cmd = new EnumerateJobs(super.getSession().getIpAddress(), port, super.getSession().getUser(), super.getSession().getPassword(), bCertCheck);
            try {
                result = cmd.execute();
            } catch (Exception e) {
                logger.debug(e.getMessage());
            }
            if (result != null) {
                currentJobs = (List<LifeCycleJob>) result;
            }
        }

        if (currentJobs != null) {
            for (LifeCycleJob job : currentJobs) {
                // Get the strat of instance id.
                int index = job.getJobName().indexOf(":");
                String instance = "";
                if (index >= 0) {
                    instance = job.getJobName().substring(index);
                    // Check instance id in the instance. Means another job of the same type is already running.
                    if (instance.toLowerCase().contains(instanceId.toLowerCase())) {
                        if (!job.getJobStatus().equalsIgnoreCase("completed")) {
                            DeleteJobCmd delCmd = new DeleteJobCmd(super.getSession().getIpAddress(), super.getSession().getUser(), super.getSession().getPassword(), job.getInstanceID());
                            delCmd.setDeleteAnyJob(true);
                            try {
                                int returnValue = delCmd.execute();
                                if (returnValue != 0) {
                                    throw new LCJobIsAlreadyRunningException("A task for this device is already under progress by system services.");
                                }
                            } catch (LCJobIsAlreadyRunningException e) {
                                logger.debug(e.getMessage());
                                throw e;
                            } catch (RuntimeException re) {
                                logger.debug(re.getMessage());
                            } catch (Exception e) {
                                logger.debug(e.getMessage());
                            } catch (Throwable t) {
                                logger.debug(t.getMessage());
                            }

                        }
                    }
                }
            }
        }

    }
}
