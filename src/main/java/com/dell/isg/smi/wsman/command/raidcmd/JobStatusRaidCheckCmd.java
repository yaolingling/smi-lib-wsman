/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.raidcmd;

/**
 * @author Umer_Shabbir
 *
 */

import java.util.ArrayList;
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

import com.dell.isg.smi.commons.elm.exception.RuntimeCoreException;
import com.dell.isg.smi.wsman.command.PersonalNamespaceContext;
import com.sun.ws.management.addressing.Addressing;

public class JobStatusRaidCheckCmd extends AbstractRaid {

    private static final Logger logger = LoggerFactory.getLogger(JobStatusRaidCheckCmd.class);

    private static String SUCCESS = Boolean.TRUE.toString();
    private static String FAILURE = Boolean.FALSE.toString();

    private String resourceURI;
    private String jobID;

    private int sleepTime = 30000;
    private int MAX_RETRIES = 40;
    private int failed_counter = 20;

    private static List<String> successStrings = new ArrayList<String>();
    private static List<String> failureStrings = new ArrayList<String>();

    static {
        updateSuccessStrings();
        updateFailureStrings();
    }


    static void updateSuccessStrings() {
        successStrings.add("Completed");
        successStrings.add("Success");
        successStrings.add("Reboot Completed"); // for reboot jobs.
    }


    static void updateFailureStrings() {
        failureStrings.add("Failed");
        failureStrings.add("Completed with Errors");
        failureStrings.add("Job Cannot be Scheduled");
        failureStrings.add("Duplicated/Invalid JOBID Entries");
    }


    public JobStatusRaidCheckCmd(String ipAddr, String userName, String passwd, String jobID, String strResourceURI, int sleepTime, int max_retries) {
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: JobStatusRaidCheckCmd(String ipAddr - %s, String userName - %s, String passwd - %s, String jobID - %s, String strResourceURI - %s, int sleepTime - %d, int max_retries - %d)", ipAddr, userName, "####", jobID, strResourceURI, sleepTime, max_retries));
        }
        this.resourceURI = strResourceURI;
        this.jobID = jobID;
        this.sleepTime = sleepTime;
        this.MAX_RETRIES = max_retries;
        logger.trace("Exiting constructor: JobStatusRaidCheckCmd()");
    }


    public Object execute() throws Exception {
        logger.trace("Entering function: execute()");
        String unpackJobStatus = checkUnpackJobStatus();
        ;
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


    private String checkUnpackJobStatus() throws SOAPException {
        Object result;

        try {
            this.addSelectors();
            Addressing reqGetManagement;
            int numRetries = 0;

            while (numRetries < MAX_RETRIES) {
                numRetries++;

                Thread.sleep(sleepTime);

                try {
                    // Call Get command
                    reqGetManagement = super.session.sendGetRequest();
                    result = findObjectInDocument(reqGetManagement.getBody(), "//pre:JobStatus/text()", XPathConstants.STRING);
                } catch (Exception ex) {
                    if (numRetries > MAX_RETRIES) {
                        throw ex;
                    }

                    continue;
                }

                boolean isSuccess = false;
                boolean isFailure = false;
                if (result != null && ((String) result).length() != 0) {

                    isSuccess = successStrings.contains((String) result);
                    isFailure = failureStrings.contains((String) result);

                    if (isSuccess || isFailure) {
                        if (isFailure) {
                            // Means the task failed and get the failed message and return that in the exception
                            String failureMessage = (String) findObjectInDocument(reqGetManagement.getBody(), "//pre:Message/text()", XPathConstants.STRING);
                            if (failureMessage.trim().equalsIgnoreCase("Job failed")) {
                                --failed_counter;
                                if (failed_counter > 0) {
                                    logger.info("JobStatusRaidCheckCmd(): " + failureMessage);
                                    continue;
                                }
                            }

                            RuntimeCoreException exception = new RuntimeCoreException(failureMessage);
                            exception.addAttribute(jobID);
                            exception.setLCErrorCode(this.getLCErrorCode());
                            exception.setLCErrorStr(this.getLCErrorStr());
                            throw exception;
                        }

                        // else it should be success.
                        return SUCCESS;
                    }

                }

                logger.debug("Raid Job Id: " + this.jobID + ", Job Status: " + result);
            }

        } catch (RuntimeCoreException e) {
            logger.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeCoreException(e.getMessage());
        }

        return FAILURE;
    }


    protected void addSelectors() {
        session.setResourceUri(getResourceURI());
        session.addSelector("InstanceID", this.getJobID());
    }


    public String getResourceURI() {
        return resourceURI;
    }


    public String getJobID() {
        return jobID;
    }


    public int getSleepTime() {
        return sleepTime;
    }
}