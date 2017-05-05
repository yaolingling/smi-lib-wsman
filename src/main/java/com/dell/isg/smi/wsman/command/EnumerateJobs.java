/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.commons.elm.exception.RuntimeCoreException;
import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.entity.LifeCycleJob;
import com.sun.ws.management.addressing.Addressing;

public class EnumerateJobs extends UpdateBaseCmd {

    private WSManageSession session = null;

    private String hostAddress;
    private boolean ignoreClearAll;
    private static final Logger logger = LoggerFactory.getLogger(EnumerateJobs.class);


    public EnumerateJobs(String ipAddr, int DRACPort, String userName, String passwd, boolean bCertCheck) {
        super(ipAddr, DRACPort, userName, passwd, bCertCheck);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: EnumerateJobs(String ipAddr - %s, int DRACPort - %d, String userName - %s, String passwd - %s, boolean bCertCheck - %s)", ipAddr, DRACPort, userName, "####", Boolean.toString(bCertCheck)));
        }
        session = super.getSession();
        this.hostAddress = ipAddr;
        session.setResourceUri(getResourceURI());
        ignoreClearAll = true;

        logger.trace("Exiting constructor: EnumerateJobs()");
    }


    public EnumerateJobs(String ipAddr, int DRACPort, String userName, String passwd, boolean bCertCheck, boolean ignoreClearAll) {
        super(ipAddr, DRACPort, userName, passwd, bCertCheck);
        logger.trace("Entering constructor: EnumerateJobs(String ipAddr, int DRACPort, String userName, String passwd, boolean bCertCheck, boolean ignoreClearAll)");
        session = super.getSession();
        this.hostAddress = ipAddr;
        session.setResourceUri(getResourceURI());
        this.ignoreClearAll = ignoreClearAll;
        logger.trace("Exiting constructor: EnumerateJobs()");
    }


    private String getResourceURI() {

        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_LifecycleJob);

        return sb.toString();
        // return ("http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/root/dcim/DCIM_SoftwareIdentity");
    }


    @Override
    public Object execute() throws Exception {
        logger.trace("Entering function: execute()");

        Object result = null;
        Addressing response = null;
        try {
            response = session.sendRequestEnumeration();

            if (response != null) {
                Document document = response.getBody().extractContentAsDocument();
                if (document != null) {
                    result = parseResponse(document);
                } else {
                    RuntimeCoreException e = new RuntimeCoreException();
                    e.setErrorID(200006);
                    e.addAttribute(hostAddress);
                    throw e;
                }
            }
        } catch (Exception e) {
            super.getLogger(getClass()).error(e.getMessage());
            throw new RuntimeCoreException(e.getMessage(), e);
        }

        logger.trace("Exiting function: execute()");
        return result;
    }


    protected List<LifeCycleJob> parseResponse(Document document) {
        List<LifeCycleJob> jobs = new ArrayList<LifeCycleJob>();

        Element element = document.getDocumentElement();

        NodeList nodeList = element.getElementsByTagNameNS(WSCommandRNDConstant.WS_MAN_NAMESPACE, WSCommandRNDConstant.SOFTWARE_IDENTITY_ITEMS_TAG);

        if (nodeList != null) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                if (nodeList.item(i).getNodeType() == Element.ELEMENT_NODE) {
                    NodeList lifeCycleJobNodes = nodeList.item(i).getChildNodes();

                    if (lifeCycleJobNodes != null) {
                        for (int j = 0; j < lifeCycleJobNodes.getLength(); j++) {
                            Node lifeCycleJobNode = lifeCycleJobNodes.item(j);
                            LifeCycleJob job = getJobStatus(lifeCycleJobNode);

                            if (job != null) {
                                if (!job.getInstanceID().contains("CLEARALL") || (!ignoreClearAll && job.getInstanceID().contains("CLEARALL"))) {
                                    jobs.add(job);
                                }
                            }
                        }
                    }
                }
            }
        }

        return jobs;
    }

}
