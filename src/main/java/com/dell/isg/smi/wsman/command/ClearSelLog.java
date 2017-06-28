/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dell.isg.smi.wsman.IdracWSManClient;
import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManClientBaseCommand;
import com.dell.isg.smi.wsman.WSManClientFactory;
import com.dell.isg.smi.wsman.WSManageSession;

public class ClearSelLog extends WSManClientBaseCommand<Object> {
    private WSManageSession session = null;
    private String instanceID = null;


    public String getInstanceID() {
        return instanceID;
    }


    public void setInstanceID(String instanceID) {
        this.instanceID = instanceID;
    }

    private static final Logger logger = LoggerFactory.getLogger(ClearSelLog.class);


    public ClearSelLog(String ipAddr, String userName, String passwd) {
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: ClearSelLog(String ipAddr - %s, String userName - %s, String passwd - %s)", ipAddr, userName, "####"));
        }

        session = super.getSession();
        session.setResourceUri(getResourceURI());

        session.setInvokeCommand(WSManMethodEnum.CLEAR_SEL_LOG.toString());

        logger.trace("Exiting constructor: ClearSelLog()");

    }

    
    public ClearSelLog() {
    	if(instanceID != null){
    		this.addSelector(WSManMethodParamEnum.INSTANCE_ID.toString(), instanceID);
    	}
	}


	public String getResourceURI() {

        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);

        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_SelRecordLog);

        return sb.toString();
    }

    
	@SuppressWarnings("unused")
	public static void main(String [] args){
    	//EnumerateBIOSStringCmd cmd = new EnumerateBIOSStringCmd("100.68.124.34", "root", "calvin");
    	try {
    		Object result;
    		//result = cmd.execute();
    		
            IdracWSManClient idracWsManClient = WSManClientFactory.getIdracWSManClient("100.68.124.34", "root", "calvin");
            result = idracWsManClient.execute(new ClearSelLog());
            
            int x = 0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    
	@Override
    public String getAction() {
        return getResourceURI() + "/" + WSManMethodEnum.CLEAR_SEL_LOG.toString();
    }

    
	@Override
    public String execute() throws Exception {
        logger.trace("Entering function: execute()");

        session.addSelector(WSManMethodParamEnum.INSTANCE_ID.toString(), instanceID);
        String invokeRequest = session.sendInvokeRequest().toString();
        logger.trace("Exiting function: execute()");

        return invokeRequest;
    }

}
