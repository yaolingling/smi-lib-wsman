/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.XPathConstants;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.sun.ws.management.addressing.Addressing;

/**
 * 
 * @author Rahman_Muhammad
 *
 */

public class EnumerateHypervisorSoftwareIdentityCmd extends WSManBaseCommand {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(EnumerateHypervisorSoftwareIdentityCmd.class);


    public EnumerateHypervisorSoftwareIdentityCmd(String hostIP, String username, String password) {
        super(hostIP, username, password);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: EnumerateHypervisorSoftwareIdentityCmd(String hostIP - %s, String username - %s, String password - %s)", hostIP, username, "####"));
        }
        this.session = super.getSession();
        this.session.setResourceUri(getResourceURI());

        logger.trace("Exiting constructor: EnumerateHypervisorSoftwareIdentityCmd()");
    }


    @Override
    public Map<String, String> execute() throws Exception {

        logger.trace("Entering function: execute()");

        Map<String, String> hypervisorDtlMap = new HashMap<String, String>();
        Addressing addressing = this.sendRequestEnumeration();
        Node node = (Node) this.session.findObjectInDocument(addressing.getBody(), "//*[local-name()='EnumerateResponse']/*[local-name()='Items']", XPathConstants.NODE);

        NodeList childNodes = node.getChildNodes();
        System.out.println("nodeList :: " + childNodes.getLength());
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "VMware_HypervisorSoftwareIdentity")) {
                NodeList hypervisorSoftwareIdentityChilds = childNode.getChildNodes();
                for (int j = 0; j < hypervisorSoftwareIdentityChilds.getLength(); j++) {
                    Node softwareChildNode = hypervisorSoftwareIdentityChilds.item(j);
                    if (StringUtils.equalsIgnoreCase(softwareChildNode.getLocalName(), "VersionString")) {
                        // FIX ME for ICEE
                        // hypervisorDtlMap.put(softwareChildNode.getLocalName().trim(), softwareChildNode.getTextContent());
                        break;
                    }
                }
            }

            if (hypervisorDtlMap.size() > 0) {
                break;
            }
        }

        logger.trace("Exiting function: execute()");
        return hypervisorDtlMap;
    }


    private String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);

        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.CIM_SoftwareIdentity);

        return sb.toString();
    }
}
