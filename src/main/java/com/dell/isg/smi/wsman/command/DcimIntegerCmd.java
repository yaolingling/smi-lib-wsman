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
import com.dell.isg.smi.wsman.command.entity.DcimIntegerCmdView;

public class DcimIntegerCmd extends WSManBaseCommand {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(DcimIntegerCmd.class);


    public DcimIntegerCmd(String ipAddr, String userName, String passwd, String profileCommand) {
        super(ipAddr, userName, passwd);
        logger.trace("Entering constructor: DcimIntegerCmd(ipAddr {}, userName {}, passwd {})", ipAddr, userName, "####", profileCommand);
        session = this.getSession();
        session.setResourceUri(getResourceURI(profileCommand));
        logger.trace("Exiting constructor: DcimIntegerCmd()");
    }


    public DcimIntegerCmd(String ipAddr, String userName, String passwd, String profileCommand, String instanceId) {
        super(ipAddr, userName, passwd);
        logger.trace("Entering constructor: DcimIntegerCmd(ipAddr {}, userName {}, passwd {} instanceId {})", ipAddr, userName, "####", profileCommand, instanceId);
        session.addUserParam("InstanceID", instanceId);
        session = this.getSession();
        session.setResourceUri(getResourceURI(profileCommand));
        logger.trace("Exiting constructor: DcimIntegerCmd()");
    }


    @Override
    public List<DcimIntegerCmdView> execute() throws Exception {
        List<DcimIntegerCmdView> dcimIntegerViewList = new ArrayList<DcimIntegerCmdView>();
        NodeList response = this.sendRequestEnumerationReturnNodeList();
        DcimIntegerCmdView dcimIntegerView = null;
        if (null != response) {
            response = response.item(0).getChildNodes();
            for (int i = 0; i < response.getLength(); i++) {
                dcimIntegerView = new DcimIntegerCmdView();
                Node itemNode = response.item(i);
                dcimIntegerView.setAttributeName(getKeyValue(itemNode, "AttributeName"));
                dcimIntegerView.setCurrentValue(getKeyValue(itemNode, "CurrentValue"));
                dcimIntegerView.setfQDD(getKeyValue(itemNode, "FQDD"));
                dcimIntegerView.setInstanceID(getKeyValue(itemNode, "InstanceID"));
                dcimIntegerView.setIsReadOnly(getKeyValue(itemNode, "IsReadOnly"));
                dcimIntegerView.setLowerBound(getKeyValue(itemNode, "LowerBound"));
                dcimIntegerView.setUpperBound(getKeyValue(itemNode, "UpperBound"));
                dcimIntegerView.setPendingValue(getKeyValue(itemNode, "PendingValue"));
                dcimIntegerViewList.add(dcimIntegerView);
            }
        }
        return dcimIntegerViewList;
    }


    private String getResourceURI(String profileCommand) {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);
        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(profileCommand);
        return sb.toString();
    }
}
