/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dell.isg.smi.commons.elm.exception.RuntimeCoreException;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;

public class JobStatusCheckCmd extends WSManBaseCommand {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(JobStatusCheckCmd.class);
    // FIX ME for ICEE

    private static String SUCCESS = Boolean.TRUE.toString();
    private static String FAILURE = Boolean.FALSE.toString();

    private String resourceURI;
    private String jobID;
    private boolean bDeleteJob;

    private int sleepTime = 60000;
    private int MAX_RETRIES = 20;

    private static List<String> successStrings = new ArrayList<String>();
    private static List<String> failureStrings = new ArrayList<String>();

    static {
        updateSuccessStrings();
        updateFailureStrings();
    }


    static void updateSuccessStrings() {
        successStrings.add("completed");
        successStrings.add("success");
    }


    static void updateFailureStrings() {
        failureStrings.add("failed");
        failureStrings.add("completed with errors");
    }


    public JobStatusCheckCmd(String ipAddr, String userName, String passwd, String jobID, String strResourceURI, int sleepTime, int max_retries) {
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: JobStatusCheckCmd(String ipAddr - %s, String userName - %s, String passwd - %s, String jobID - %s, String strResourceURI - %s, int sleepTime - %d, int max_retries - %s)", ipAddr, userName, "####", jobID, strResourceURI, sleepTime, max_retries));
        }
        session = super.getSession();
        this.resourceURI = strResourceURI;
        this.jobID = jobID;
        this.sleepTime = sleepTime;
        this.MAX_RETRIES = max_retries;
        this.bDeleteJob = true;
        logger.trace("Exiting constructor: JobStatusCheckCmd()");
    }


    public JobStatusCheckCmd(String ipAddr, String userName, String passwd, String jobID, String strResourceURI, int sleepTime, int max_retries, boolean bDeleteJob) {
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: JobStatusCheckCmd(String ipAddr - %s, String userName - %s, String passwd - %s, String jobID - %s, String strResourceURI - %s, int sleepTime - %d, int max_retries - %d, boolean bDeleteJob - %s)", ipAddr, userName, "####", jobID, strResourceURI, sleepTime, max_retries, Boolean.toString(bDeleteJob)));
        }
        session = super.getSession();
        this.resourceURI = strResourceURI;
        this.jobID = jobID;
        this.sleepTime = sleepTime;
        this.MAX_RETRIES = max_retries;
        this.bDeleteJob = bDeleteJob;
        logger.trace("Exiting constructor: JobStatusCheckCmd()");
    }


    public Object execute() throws RuntimeCoreException, SOAPException {
        logger.trace("Entering function: execute()");
        String unpackJobStatus = checkUnpackJobStatus();

        logger.trace("Exiting function: execute()");
        return unpackJobStatus;

    }


    // No need to throw the exception as it shouldn't stop to continue with the other steps even if delete job fails.
    private int deleteJob() {
        if (bDeleteJob) {

            DeleteJobCmd deleteCmd = new DeleteJobCmd(this.session.getIpAddress(), this.session.getUser(), this.session.getPassword(), jobID);

            int deleteCmdReturn = 0;
            try {
                deleteCmdReturn = deleteCmd.execute().intValue();
            } catch (Exception e) {
                logger.info(e.getMessage());
            }

            return deleteCmdReturn;
        }

        return -1;
    }


    private Object findObjectInDocument(SOAPElement doc, String xPathLocation, QName qname) throws XPathExpressionException {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        XPathExpression expr = xpath.compile(xPathLocation);
        Object result = expr.evaluate(doc, qname);
        return result;
    }


    private String checkUnpackJobStatus() throws SOAPException {
        Object result = null;
        return null;
    }


    /*
     * Trigger t = new Trigger(sleepTime, sleepTime*MAX_RETRIES);
     * 
     * try { //Set ResourceURI session.setResourceUri(this.getResourceURI());
     * 
     * //Set Selectors session.addSelector("InstanceID", this.getJobID());
     * 
     * Addressing reqGetManagement = null ; // int numRetries = 0;
     * 
     * // while (numRetries < MAX_RETRIES) { // numRetries++; // // Thread.sleep(sleepTime);
     * 
     * while ( t.block() ) { try { //Call Get command reqGetManagement = session.sendGetRequest(); } catch(NoRouteToHostException noHostEx){ logger.error("", noHostEx); throw new
     * RuntimeCoreException(noHostEx); } catch(Exception soapExc){ logger.error(soapExc); continue; }
     * 
     * if( null != reqGetManagement) {
     * 
     * result = findObjectInDocument(reqGetManagement.getBody(), "//*[local-name()='JobStatus']/text()", XPathConstants.STRING); boolean isSuccess = false; boolean isFailure =
     * false; if ( result != null && ((String)result).length() != 0 ) {
     * 
     * isSuccess = successStrings.contains( ((String)result).toLowerCase() ); isFailure = failureStrings.contains( ((String)result).toLowerCase() );
     * 
     * if( isSuccess || isFailure){ if( isFailure ) { //Means the task failed and get the failed message and return that in the exception String failureMessage =
     * (String)findObjectInDocument(reqGetManagement.getBody(), "//*[local-name()='Message']/text()", XPathConstants.STRING); logger.error("Job: " + this.jobID +
     * " for resource URI: " + this.resourceURI + " failed with failure message: " + failureMessage); RuntimeCoreException exception = new RuntimeCoreException(failureMessage);
     * exception.setErrorID(267068); exception.addAttribute(jobID); exception.addAttribute(failureMessage); exception.addAttribute(this.getSession().getIpAddress());
     * exception.setLCErrorCode(this.getLCErrorCode()); exception.setLCErrorStr(this.getLCErrorStr()); throw exception ; }
     * 
     * //else it should be success. logger.debug("Job: " + this.jobID + " Status: " + result + " for resource URI: " + this.resourceURI); return SUCCESS; } logger.debug("Job: " +
     * this.jobID + " Status: " + result); } } }
     * 
     * } catch (RuntimeCoreException e) { logger.error("",e); throw e; } catch (Exception e) { logger.error("",e); throw new RuntimeCoreException(e); } finally { t.cancel(); }
     * 
     * String message = "Job status check for Job ID " + this.jobID + " timed out in " + ((sleepTime * MAX_RETRIES) / 60000) + " minutes"; logger.error(message); throw
     * ExceptionUtilities.getSpectreRuntimeException(267139,message, this.getLCErrorCode(), this.getLCErrorStr()); // return FAILURE; }
     * 
     * public String getResourceURI() { return resourceURI; }
     * 
     * public String getJobID() { return jobID; }
     */
    public int getSleepTime() {
        return sleepTime;
    }

}
