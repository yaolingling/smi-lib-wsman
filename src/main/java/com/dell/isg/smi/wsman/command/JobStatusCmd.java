/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.entity.LifeCycleJob;
import com.dell.isg.smi.wsman.utilities.XMLTool;
import com.dell.isg.smi.wsman.utilities.XMLTool.NameScope;
import com.sun.ws.management.addressing.Addressing;

/**
 *
 * @author Matthew_G_Stemen
 */
public class JobStatusCmd extends WSManBaseCommand {
    private WSManageSession session = null;
    private String jobId = null;

    private static final Logger logger = LoggerFactory.getLogger(JobStatusCmd.class);


    // private static String resourceUri ="http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/root/dcim/DCIM_LifecycleJob";

    public JobStatusCmd(String DRACIP, String DRACPort, String DRACUser, String DRACPassword, String jobId) {

        // set the WSMan Session
        super(DRACIP, DRACPort, DRACUser, DRACPassword);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: JobStatusCmd(String DRACIP - %s, String DRACPort - %s, String DRACUser - %s, String DRACPassword - %s, String jobId - %s)", DRACIP, DRACPort, DRACUser, "####", jobId));
        }
        session = this.getSession();
        this.jobId = jobId;
        logger.trace("Exiting constructor: JobStatusCmd()");
    }


    public JobStatusCmd(String DRACIP, String DRACPort, String DRACUser, String DRACPassword) {

        // set the WSMan Session
        super(DRACIP, DRACPort, DRACUser, DRACPassword);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: JobStatusCmd(String DRACIP - %s, String DRACPort - %s, String DRACUser - %s, String DRACPassword - %s)", DRACIP, DRACPort, DRACUser, "####"));
        }
        session = this.getSession();
        logger.trace("Exiting constructor: JobStatusCmd()");

    }


    public String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);

        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_LifecycleJob);

        return sb.toString();
    }


    @Override
    public List<LifeCycleJob> execute() throws Exception {

        logger.trace("Entering function: execute()");
        session.setResourceUri(getResourceURI());
        Addressing returnDoc = null;
        // jobId = "JID_001281437210";
        if (jobId != null) {
            session.addSelector("InstanceID", jobId);
            returnDoc = session.sendGetRequest();
        } else {
            returnDoc = session.sendRequestEnumeration();
        }

        Document retDoc = returnDoc.getBody().extractContentAsDocument();
        XMLTool.printToSysOut(retDoc);
        List<LifeCycleJob> lcj = getJobStatus(retDoc);

        logger.trace("Exiting function: execute()");
        return lcj;
    }


    public void setJobID(String jobId) {
        this.jobId = jobId;
    }


    /**
     * Create job from document
     * 
     * @param doc
     * @return LifeCycleJob
     */

    private List<LifeCycleJob> getJobStatus(Document doc) {

        List<LifeCycleJob> retArray = new ArrayList<LifeCycleJob>();
        XMLTool xmlTool = new XMLTool(doc);
        xmlTool.setSearchScope(NameScope.LocalName);
        NodeList nl = xmlTool.getTargetNodes("DCIM_LifecycleJob");

        for (int i = 0; i < nl.getLength(); i++) {
            Node lcjNode = nl.item(i);
            LifeCycleJob lcj = new LifeCycleJob();

            lcj.setInstanceID(xmlTool.getTextValueFromNode(lcjNode, "InstanceID"));
            lcj.setJobStartTime(xmlTool.getTextValueFromNode(lcjNode, "JobStartTime"));
            lcj.setJobStatus(xmlTool.getTextValueFromNode(lcjNode, "JobStatus"));
            if (lcj.getJobStatus() != null && lcj.getJobStatus().equals("New")) {
                lcj.setJobStatus("New Update Job");
            }
            lcj.setJobUntilTime(xmlTool.getTextValueFromNode(lcjNode, "JobUntilTime"));
            lcj.setMessage(xmlTool.getTextValueFromNode(lcjNode, "Message"));
            lcj.setMessageID(xmlTool.getTextValueFromNode(lcjNode, "MessageID"));
            lcj.setJobName(xmlTool.getTextValueFromNode(lcjNode, "Name"));
            retArray.add(lcj);
        }
        return retArray;
    }

}
