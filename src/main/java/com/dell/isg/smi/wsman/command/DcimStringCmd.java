/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.command.entity.DcimStringCmdView;

public class DcimStringCmd extends WSManBaseCommand {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(DcimStringCmd.class);


    public DcimStringCmd(String ipAddr, String userName, String passwd, String profileCommand) {
        super(ipAddr, userName, passwd);
        logger.trace("Entering constructor: DcimStringCmd(ipAddr {}, userName {}, passwd {})", ipAddr, userName, "####", profileCommand);
        session = this.getSession();
        session.setResourceUri(getResourceURI(profileCommand));
        logger.trace("Exiting constructor: DcimStringCmd()");
    }


    public DcimStringCmd(String ipAddr, String userName, String passwd, String profileCommand, String instanceId) {
        super(ipAddr, userName, passwd);
        logger.trace("Entering constructor: DcimStringCmd(ipAddr {}, userName {}, passwd {} instanceId {})", ipAddr, userName, "####", profileCommand, instanceId);
        session.addUserParam("InstanceID", instanceId);
        session = this.getSession();
        session.setResourceUri(getResourceURI(profileCommand));
        logger.trace("Exiting constructor: DcimStringCmd()");
    }


    @Override
    public List<DcimStringCmdView> execute() throws Exception {
        List<DcimStringCmdView> dcimStringViewList = new ArrayList<DcimStringCmdView>();
        NodeList response = this.sendRequestEnumerationReturnNodeList();
        DcimStringCmdView dcimStringView = null;
        if (null != response) {
            response = response.item(0).getChildNodes();
            for (int i = 0; i < response.getLength(); i++) {
                dcimStringView = new DcimStringCmdView();
                Node itemNode = response.item(i);
                dcimStringView.setAttributeName(getKeyValue(itemNode, "AttributeName"));
                dcimStringView.setCurrentValue(getKeyValue(itemNode, "CurrentValue"));
                dcimStringView.setfQDD(getKeyValue(itemNode, "FQDD"));
                dcimStringView.setInstanceID(getKeyValue(itemNode, "InstanceID"));
                dcimStringView.setIsReadOnly(getKeyValue(itemNode, "IsReadOnly"));
                dcimStringView.setMaxLength(getKeyValue(itemNode, "MaxLength"));
                dcimStringView.setMinLength(getKeyValue(itemNode, "MinLength"));
                dcimStringView.setPendingValue(getKeyValue(itemNode, "PendingValue"));
                dcimStringViewList.add(dcimStringView);
            }
        }
        return dcimStringViewList;
    }


    private String getResourceURI(String profileCommand) {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(profileCommand);
        return sb.toString();
    }
}
