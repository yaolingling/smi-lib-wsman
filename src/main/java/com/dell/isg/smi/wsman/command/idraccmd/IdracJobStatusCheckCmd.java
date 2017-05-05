/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.idraccmd;

/**
 * @author Umer_Shabbir
 *
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.commons.elm.exception.RuntimeCoreException;
import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.command.PersonalNamespaceContext;
import com.dell.isg.smi.wsman.model.XmlConfig;
import com.sun.ws.management.addressing.Addressing;

public class IdracJobStatusCheckCmd extends WSManBaseCommand {

    private static final Logger logger = LoggerFactory.getLogger(IdracJobStatusCheckCmd.class);

    private WSManageSession session = null;

    private static String SUCCESS = Boolean.TRUE.toString();
    private static String FAILURE = Boolean.FALSE.toString();

    private String jobID;

    private int sleepTime = 5000;
    private int MAX_RETRIES = 20;
    private int failed_counter = 10;

    private static List<String> successStrings = new ArrayList<String>();
    private static List<String> failureStrings = new ArrayList<String>();

    static {
        updateSuccessStrings();
        updateFailureStrings();
    }


    static void updateSuccessStrings() {
        successStrings.add("Completed");
        successStrings.add("Success");
    }


    static void updateFailureStrings() {
        failureStrings.add("Failed");
        failureStrings.add("Completed with Errors");
    }


    public IdracJobStatusCheckCmd(String ipAddr, String userName, String passwd, String jobID, int sleepTime, int max_retries) {

        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: IdracJobStatusCheckCmd(String ipAddr - %s, String userName - %s, String passwd - %s, String jobID - %s, int sleepTime - %d, int max_retries - %d)", ipAddr, userName, "####", jobID, sleepTime, max_retries));
        }
        session = super.getSession();

        this.jobID = jobID;
        this.sleepTime = sleepTime;
        this.MAX_RETRIES = max_retries;
        this.session.setResourceUri(getResourceURI());
        this.addSelectors();
        logger.trace("Exiting constructor: IdracJobStatusCheckCmd()");
    }


    public XmlConfig execute() throws Exception {
        logger.trace("Entering function: execute()");
        XmlConfig unpackJobStatus = checkUnpackJobStatus();
        logger.trace("Exiting function: execute()");

        return unpackJobStatus;
    }


    private Object findObjectInDocument(SOAPElement doc, String xPathLocation, QName qname) throws XPathExpressionException {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        xpath.setNamespaceContext(new PersonalNamespaceContext(this.getResourceURI()));
        XPathExpression expr = xpath.compile(xPathLocation);
        Object result = expr.evaluate(doc, qname);
        return result;
    }


    private XmlConfig checkUnpackJobStatus() throws SOAPException {
        XmlConfig response = new XmlConfig();
        Object result;

        try {
            Addressing reqGetManagement;
            int numRetries = 0;

            while (numRetries < MAX_RETRIES) {
                numRetries++;

                Thread.sleep(sleepTime);

                try {
                    // Call Get command
                    reqGetManagement = session.sendGetRequest();
                    result = findObjectInDocument(reqGetManagement.getBody(), "//pre:JobStatus/text()", XPathConstants.STRING);
                    if (result == null || ((String) result).isEmpty()) { // Execute only when findObjectInDocument() return nothing
                        result = this.getJobStatus(reqGetManagement.getBody());

                    }

                } catch (Exception ex) {
                    if (numRetries > MAX_RETRIES) {
                        throw ex;
                    }

                    continue;
                }

                boolean isSuccess = false;
                boolean isFailure = false;
                if (result != null && ((String) result).length() != 0) {

                    logger.info("checkUnpackJobStatus: Result: " + result);

                    isSuccess = successStrings.contains((String) result);
                    isFailure = failureStrings.contains((String) result);

                    if (isSuccess || isFailure) {
                        if (isFailure) {
                            // Means the task failed and get the failed message and return that in the exception
                            String failureMessage = (String) findObjectInDocument(reqGetManagement.getBody(), "//pre:Message/text()", XPathConstants.STRING);
                            logger.info("failureMessage:: " + failureMessage);
                            if (failureMessage.trim().equalsIgnoreCase("Job failed")) {
                                --failed_counter;
                                if (failed_counter > 0) {
                                    logger.info("JobStatusRaidCheckCmd(): " + failureMessage);
                                    continue;
                                }
                            }
                            response.setResult(XmlConfig.JobStatus.FAILURE.toString());
                            response.setJobID(this.getJobID());
                            response.setMessage((String) findObjectInDocument(reqGetManagement.getBody(), "//*/pre:Message/text()", XPathConstants.STRING));
                            return response;
                        }
                        response.setResult(XmlConfig.JobStatus.SUCCESS.toString());
                        response.setJobID(this.getJobID());
                        response.setMessage((String) findObjectInDocument(reqGetManagement.getBody(), "//*/pre:Message/text()", XPathConstants.STRING));
                        return response;
                    }

                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeCoreException(e.getMessage());
        }
        response.setResult(XmlConfig.JobStatus.FAILURE.toString());
        response.setJobID(this.getJobID());
        response.setMessage(this.getLCErrorStr());
        return response;

    }


    protected void addSelectors() {
        session.setResourceUri(getResourceURI());
        session.addSelector("InstanceID", this.getJobID());
    }


    private String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);

        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_LifecycleJob);
        return sb.toString();
    }


    public String getJobID() {
        return jobID;
    }


    public int getSleepTime() {
        return sleepTime;
    }


    @SuppressWarnings("unchecked")
    private String getJobStatus(SOAPElement doc) throws Exception {
        String tmpStatus = "";
        Iterator iterator = null;
        Node node = null;
        try {
            iterator = doc.getChildElements();
            while (iterator.hasNext()) {
                node = (Node) iterator.next();
                tmpStatus = getKeyValue(node, "JobStatus");
                if (tmpStatus != null && !tmpStatus.isEmpty())
                    break;
            }
        } catch (Exception exp) {
            logger.error(exp.getMessage());
        }
        return tmpStatus;
    }


    /**
     * Common method used to search for an attribute value
     * 
     * @param Node
     * @param KEY
     * @return String - value for an attribute
     **/

    protected String getKeyValue(Node node, String key) {
        String value = "";
        try {
            NodeList childList = node.getChildNodes();
            for (int i = 0; i < childList.getLength(); i++) {
                Node childNode = childList.item(i);
                if (childNode.getNodeName().contains(key)) {
                    if (childNode.hasChildNodes()) {
                        NodeList nodeList1 = childNode.getChildNodes();
                        for (int k = 0; k < nodeList1.getLength(); k++) {
                            Node finalNode = nodeList1.item(k);
                            if (Element.TEXT_NODE == finalNode.getNodeType()) {
                                value = finalNode.getNodeValue();
                                break;
                            }
                        }

                        if (!value.isEmpty()) {
                            break;
                        }
                    }
                }
            }
        } catch (Exception exp) {
            logger.error(exp.getMessage());
        }

        return value;
    }
}