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
import com.dell.isg.smi.wsman.WSManClientBaseCommand.WSManClassEnum;

public class WsmanEnumerate extends WSManClientBaseCommand<Object> {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(WsmanEnumerate.class);

    private String profileCommand = null;
    private String instanceId = null;

    public WsmanEnumerate(String ipAddr, String userName, String passwd, String profileCommand) {
        super(ipAddr, userName, passwd);
        logger.trace("Entering constructor: DcimEnumerationCmd(ipAddr {}, userName {}, passwd {}, profileCommand {} )", ipAddr, userName, "####", profileCommand);
        session = this.getSession();
        session.setResourceUri(getResourceURI(profileCommand));
        logger.trace("Exiting constructor: DcimEnumerationCmd()");
    }


    public WsmanEnumerate(String ipAddr, String userName, String passwd, String profileCommand, String instanceId) {
        super(ipAddr, userName, passwd);
        logger.trace("Entering constructor: DcimEnumerationCmd(ipAddr {}, userName {}, passwd {}, profileCommand {}, instanceId {})", ipAddr, userName, "####", profileCommand, instanceId);
        session.addUserParam("InstanceID", instanceId);
        session = this.getSession();
        session.setResourceUri(getResourceURI(profileCommand));
        logger.trace("Exiting constructor: DcimEnumerationCmd()");
    }


	public WsmanEnumerate(String profileCommand) {
		this(profileCommand, null);
	}

	public WsmanEnumerate(String profileCommand, String instanceId) {
		this.profileCommand = profileCommand;
		this.instanceId = instanceId;
	}
	

	@SuppressWarnings("unused")
	public static void main(String [] args){
    	//EnumerateBIOSStringCmd cmd = new EnumerateBIOSStringCmd("100.68.124.34", "root", "calvin");
    	try {
    		Object result;
    		//result = cmd.execute();
    		
            IdracWSManClient idracWsManClient = WSManClientFactory.getIdracWSManClient("100.68.124.34", "root", "calvin");
            result = idracWsManClient.execute(new WsmanEnumerate(WSManClassEnum.DCIM_SoftwareIdentity.name(), null));
            
            int x = 0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	@Override
    public Object execute() throws Exception{
        logger.trace("Entering EnumerateIDRACCardStrCmd function: execute()");
        Object jsonData = this.sendRequestEnumerationReturnJson();
        logger.trace("Exiting EnumerateIDRACCardStrCmd function: execute()");
        return jsonData;
    }
//    @Override
//    public List<DcimEnumCmdView> execute() throws Exception {
//        List<DcimEnumCmdView> dcimEnumViewList = new ArrayList<DcimEnumCmdView>();
//        NodeList response = this.sendRequestEnumerationReturnNodeList();
//        DcimEnumCmdView dcimEnumView = null;
//        if (null != response) {
//            response = response.item(0).getChildNodes();
//            for (int i = 0; i < response.getLength(); i++) {
//                dcimEnumView = new DcimEnumCmdView();
//                Node itemNode = response.item(i);
//                dcimEnumView.setAttributeName(getKeyValue(itemNode, "AttributeName"));
//                dcimEnumView.setCurrentValue(getKeyValue(itemNode, "CurrentValue"));
//                dcimEnumView.setfQDD(getKeyValue(itemNode, "FQDD"));
//                dcimEnumView.setInstanceID(getKeyValue(itemNode, "InstanceID"));
//                dcimEnumView.setIsReadOnly(getKeyValue(itemNode, "IsReadOnly"));
//                dcimEnumView.setPendingValue(getKeyValue(itemNode, "PendingValue"));
//                dcimEnumView.setPossibleValues(getKeyValue(itemNode, "PossibleValues"));
//                dcimEnumViewList.add(dcimEnumView);
//            }
//        }
//        return dcimEnumViewList;
//    }
//

	public String getResourceURI(){
		return getResourceURI(profileCommand);
	}
	
    private String getResourceURI(String profileCommand) {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(profileCommand);
        return sb.toString();
    }


}
