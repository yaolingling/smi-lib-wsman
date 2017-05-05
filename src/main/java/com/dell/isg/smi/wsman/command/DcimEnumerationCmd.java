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
import com.dell.isg.smi.wsman.command.entity.DcimEnumCmdView;

public class DcimEnumerationCmd extends WSManBaseCommand {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(DcimEnumerationCmd.class);


    public DcimEnumerationCmd(String ipAddr, String userName, String passwd, String profileCommand) {
        super(ipAddr, userName, passwd);
        logger.trace("Entering constructor: DcimEnumerationCmd(ipAddr {}, userName {}, passwd {}, profileCommand {} )", ipAddr, userName, "####", profileCommand);
        session = this.getSession();
        session.setResourceUri(getResourceURI(profileCommand));
        logger.trace("Exiting constructor: DcimEnumerationCmd()");
    }


    public DcimEnumerationCmd(String ipAddr, String userName, String passwd, String profileCommand, String instanceId) {
        super(ipAddr, userName, passwd);
        logger.trace("Entering constructor: DcimEnumerationCmd(ipAddr {}, userName {}, passwd {}, profileCommand {}, instanceId {})", ipAddr, userName, "####", profileCommand, instanceId);
        session.addUserParam("InstanceID", instanceId);
        session = this.getSession();
        session.setResourceUri(getResourceURI(profileCommand));
        logger.trace("Exiting constructor: DcimEnumerationCmd()");
    }


    @Override
    public List<DcimEnumCmdView> execute() throws Exception {
        List<DcimEnumCmdView> dcimEnumViewList = new ArrayList<DcimEnumCmdView>();
        NodeList response = this.sendRequestEnumerationReturnNodeList();
        DcimEnumCmdView dcimEnumView = null;
        if (null != response) {
            response = response.item(0).getChildNodes();
            for (int i = 0; i < response.getLength(); i++) {
                dcimEnumView = new DcimEnumCmdView();
                Node itemNode = response.item(i);
                dcimEnumView.setAttributeName(getKeyValue(itemNode, "AttributeName"));
                dcimEnumView.setCurrentValue(getKeyValue(itemNode, "CurrentValue"));
                dcimEnumView.setfQDD(getKeyValue(itemNode, "FQDD"));
                dcimEnumView.setInstanceID(getKeyValue(itemNode, "InstanceID"));
                dcimEnumView.setIsReadOnly(getKeyValue(itemNode, "IsReadOnly"));
                dcimEnumView.setPendingValue(getKeyValue(itemNode, "PendingValue"));
                dcimEnumView.setPossibleValues(getKeyValue(itemNode, "PossibleValues"));
                dcimEnumViewList.add(dcimEnumView);
            }
        }
        return dcimEnumViewList;
    }


    private String getResourceURI(String profileCommand) {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(profileCommand);
        return sb.toString();
    }
}
