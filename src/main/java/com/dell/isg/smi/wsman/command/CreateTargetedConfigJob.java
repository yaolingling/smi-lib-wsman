/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.xml.soap.SOAPBody;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.sun.ws.management.addressing.Addressing;

/**
 * @author Muqeeth.Kowkab
 *
 */
public class CreateTargetedConfigJob extends WSManBaseCommand {
	
	    private static final Logger logger = LoggerFactory.getLogger(CreateTargetedConfigJob.class);
	    
	    private WSManageSession session = null;

		private Map<String, String> result = new HashMap<String, String>();

	public CreateTargetedConfigJob(String ipAddr, String userName, String passwd, String target, int rebootJobType,
			String scheduledStartTime, String untilTime) {		
		    super(ipAddr, userName, passwd);
	       
	        session = super.getSession();
	        session.setResourceUri(getResourceURI(target));
	        this.addSelectors(getResourceURI(target));

	        session.setInvokeCommand(WSManMethodEnum.CREATE_TARGET_CONFIG_JOB.toString());
	        if (rebootJobType <=0 || rebootJobType >3) {
	        	session.addUserParam(WSManMethodParamEnum.REBOOT_JOB_TYPE.toString(), Integer.toString(rebootJobType));
	        } else {
	        	session.addUserParam(WSManMethodParamEnum.REBOOT_JOB_TYPE.toString(), "3");
	        }      

	        session.addUserParam(WSManMethodParamEnum.TARGET.toString(), target);
	        session.addUserParam(WSManMethodParamEnum.SCHEDULED_START_TIME.toString(), scheduledStartTime);
	        session.addUserParam(WSManMethodParamEnum.UNTIL_TIME.toString(), untilTime);

	        logger.trace("Exiting constructor: CreateTargetedConfigJob()");
	}

	private String getResourceURI(String target) {
		StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
		if (StringUtils.equals(target, "System.Embedded.1")) {
	    	sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_SystemManagementService);
	    } else if (StringUtils.equals(target, "BIOS.Setup.1-1")) {
	    	sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_BIOSService);
	    }    

	    return sb.toString();
	}

	@Override
	public Map<String, String> execute() throws Exception {
		long interval = 30;
		TimeUnit unit = TimeUnit.SECONDS;
		try {
			final TargetConfigJob targetConfigJob = new TargetConfigJob();
			targetConfigJob.configJob();
			
			if (!result.containsKey("JobId")) {
				final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
								
				for (int i=0; i<5; i++) {					
					ScheduledFuture<Map<String, String>> future = scheduler.schedule(targetConfigJob, interval, unit);
					Map<String, String> output = future.get();
					if (StringUtils.isNotBlank(output.get("JobId"))) {						
						future.cancel(true);
						return result;
					}
					
				}
				scheduler.shutdown();
				try {
					if (!scheduler.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
						scheduler.shutdownNow();
					}
				} catch (InterruptedException e) {
					scheduler.shutdown();
				}	
				return result;
			} else {
				logger.info("Skipping the scheduler. Result contains the JobId.");
				return result;
			}
			
			
		} catch (Exception e) {
			logger.error("Exception in CreateTargedConfigJob: execute: "+ e.getMessage());
			return result;
		}
		
	}
	
	 public class TargetConfigJob  implements Callable {

		private static final String MESSAGE_ID = "MessageID";
		private static final String JOB_ID = "JobId";
		private static final String MESSAGE = "Message";

		@Override
		public Map<String, String>  call() throws Exception {
			try {
				configJob();
			} catch (Exception e) {
				throw e;
			}
			return result;
		}

		private void configJob() throws Exception {
			try {
				if (result.isEmpty() || (result.containsKey(JOB_ID) && StringUtils.isBlank(result.get(JOB_ID)))) {
					Addressing response = session.sendInvokeRequest();
					XPathFactory xPathFactory = XPathFactory.newInstance();
		            XPath xpath = xPathFactory.newXPath();
		            SOAPBody soapBody = response.getBody();
		            String returnValue = xpath.evaluate("*[local-name()='CreateTargetedConfigJob_OUTPUT']/*[local-name()='ReturnValue']", soapBody);
		            
					if (StringUtils.equalsIgnoreCase(returnValue, WSCommandRNDConstant.SUCCESSFULL_CONFIG_JOB_RETURN)
							|| (StringUtils.equalsIgnoreCase(returnValue,
									WSCommandRNDConstant.SUCCESSFULL_UPDATE_JOB_RETURN))) {
		            	String jobId = getJobID(soapBody, xpath);
		            	if (StringUtils.isNotBlank(jobId)) {
		            		result.put(JOB_ID, jobId);
		            	}
		            	logger.info("CreateTargedConfigJob: ConfigJob: JOBId: " + jobId);
		            } else {
		            	String messageFromConfigJob = xpath.evaluate("*[local-name()='CreateTargetedConfigJob_OUTPUT']/*[local-name()='Message']", soapBody);
		            	logger.info("CreateTargedConfigJob: ConfigJob: messageFromConfigJob: " + messageFromConfigJob);
		            	
		            	if (StringUtils.isNotBlank(messageFromConfigJob)) {
		            		result.put(MESSAGE, messageFromConfigJob);
		            	}            	
		            	
		            	String LCErrorCode = session.getLCMessageID();
		            	logger.info("CreateTargedConfigJob: ConfigJob: LCErrorCode: " + LCErrorCode);	            	
		            	
		            	if (StringUtils.isNotBlank(LCErrorCode)) {
		            		result.put(MESSAGE_ID, LCErrorCode);
		            		
		            	}
		            	// if we have a job ID of a failed job, delete it.
		                String jobId = getJobID(soapBody, xpath);
		                if (StringUtils.isNotBlank(jobId)) {
		                	deleteJob(jobId);
		                }
		            }
				}			
			} catch (Exception e) {
				logger.error("Exception in CreateTargedConfigJob: configJob: " + e.getMessage());
			}			
		}
	}

	

	private int deleteJob(String jobId) {
		
		DeleteJobCmd deleteCmd = new DeleteJobCmd(this.session.getIpAddress(), this.session.getUser(), this.session.getPassword(), jobId);

        int deleteCmdReturn = 0;
        try {
            deleteCmdReturn = deleteCmd.execute().intValue();
        } catch (Exception e) {
            logger.info(e.getMessage());
        }

        return deleteCmdReturn;
		
	}

	private String getResourceURI(SOAPBody soapBody, XPath xpath) throws Exception {
		Object result = xpath.evaluate("//*[local-name()='ResourceURI']/text()", soapBody, XPathConstants.STRING);
		logger.info("Returning the ResourceURI :: " + (String) result);
		return (String) result;
	}

	private String getJobID(SOAPBody soapBody, XPath xpath) throws Exception {
		Object result = xpath.evaluate("//*[local-name()='Selector'][@Name='InstanceID']/text()", soapBody, XPathConstants.STRING);

        logger.info("Returning the jobID :: " + result);
        return (String) result;
	}

}
