/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.commons.elm.utilities.xml.XmlHelper;
import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.dell.isg.smi.wsman.command.entity.WsmanCredentials;
import com.dell.isg.smi.wsman.command.entity.serverhealth.ComponentHealthInfo;
import com.dell.isg.smi.wsman.command.entity.serverhealth.ComponentType;
import com.dell.isg.smi.wsman.command.entity.serverhealth.ServerHealthInfo;
import com.sun.ws.management.addressing.Addressing;

/**
 * 
 * @author Chetna_Shastry
 *
 */
public class GetSystemHealthStatus extends WSManBaseCommand {

    private WSManageSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(GetSystemHealthStatus.class);


    public GetSystemHealthStatus(String hostName, WsmanCredentials credentials) {
        this(hostName, credentials.getUserid(), credentials.getPassword(), credentials.isCertificateCheck());
    }


    public GetSystemHealthStatus(String hostName, String user, String pw, boolean bCertCheck) {
        super(hostName, user, pw, bCertCheck);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: GetSystemHealthStatus(String hostName - %s, String user - %s, String pw - %s, boolean bCertCheck - %s)", hostName, user, "####", Boolean.toString(bCertCheck)));
        }
        session = this.getSession();
        session.setResourceUri(getResourceURI());
        session.addSelector("__cimnamespace", "root/dcim/sysman");
        session.addSelector("InstanceID", "DCIM_OEM_DataAccessModule1");
        session.setInvokeCommand("SendCmd");
        session.addUserParam("CommandAndArguments", "omacmd=getchildlist recurse=true showobjhead=true showbody=false byobjtype=25 byobjtype=17 byobjtype=21 byobjtype=22 byobjtype=23 byobjtype=24  byobjtype=26 byobjtype=27 byobjtype=28 byobjtype=31 byobjtype=40 byobjtype=225 computeobjstatus=true daname=dceda");
        logger.trace("Exiting constructor: GetSystemHealthStatus()");
    }


    public String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);

        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.DCIM_OEM_DataAccessModule);

        return sb.toString();
    }


    @Override
    public ServerHealthInfo execute() throws Exception {
        logger.trace("Entering function: execute()");
        Addressing result = session.sendInvokeRequest();
        Document tempDoc = session.extractAddressBody(result);
        String strContent = XmlHelper.convertDocumenttoString(tempDoc);
        // wiseman bug...
        strContent = strContent.replaceAll("&lt;", "<");
        strContent = strContent.replaceAll("&gt;", ">");

        // ------------------
        Document doc = convertStringToXMLDocument(strContent);
        List<ComponentHealthInfo> components = new ArrayList<ComponentHealthInfo>();
        int rollupStatusValue = 0;
        ServerHealthInfo serverHealth = new ServerHealthInfo();
        // look for every possible component specified in the enum
        for (ComponentType component : ComponentType.values()) {
            NodeList nodeLst = doc.getElementsByTagName(component.getXmlTagName());
            int currStatus = -1, prevStatus = -1;
            for (int s = 0; s < nodeLst.getLength(); s++) {
                // obtain and set name and status for current component
                ComponentHealthInfo currentComponent = new ComponentHealthInfo();
                currentComponent.setComponentName(component);
                // extracting the objstatus tag
                Node fstNode = nodeLst.item(s);
                Element fstElmnt = (Element) fstNode;
                NodeList listElmnts = fstElmnt.getElementsByTagName("objstatus");
                if (listElmnts != null) {
                    Element ComponentStatus = (Element) listElmnts.item(0);
                    if (ComponentStatus != null) {
                        // obtaining the value
                        NodeList ComponentStatusValue = ComponentStatus.getChildNodes();
                        if (ComponentStatusValue != null) {
                            currStatus = Integer.parseInt(((Node) ComponentStatusValue.item(0)).getNodeValue());
                            currentComponent.setComponentStatus(currStatus);
                            // if the component isnt in the list, add it blindly
                            if (!components.contains(currentComponent)) {
                                components.add(currentComponent);
                            }
                            // if not check if the status is higher and if yes,replace the element with the status
                            else {
                                if (prevStatus < currStatus) {
                                    /*
                                     * the hash code for componentHealthInfo is calculated using only the name and not the status. The equals function behaves the same way.
                                     * Therefore the remove operation removes the object with the name equal to the name of currentComponent(which is the only check performed)
                                     * which has prevStatus as its component status and adds the object whose status has been updated to currStatus before the if-else block
                                     */
                                    components.remove(currentComponent);
                                    components.add(currentComponent);
                                }

                            }

                        }
                    }
                    // set the prevstatus
                    prevStatus = currStatus;
                }

            }

        }
        // obtaining overall server health status
        NodeList nodeLst2 = doc.getElementsByTagName("computedobjstatus");
        if (nodeLst2 != null) {
            // retrieving the value
            Element rollupStatusElement = (Element) nodeLst2.item(0);
            if (rollupStatusElement != null) {
                NodeList rollupStatus = rollupStatusElement.getChildNodes();
                if (rollupStatus != null) {
                    rollupStatusValue = Integer.parseInt(((Node) rollupStatus.item(0)).getNodeValue());
                }
            }
        }
        serverHealth.setComponents(components);
        serverHealth.setOverallStatus(rollupStatusValue);
        logger.trace("Exiting function: execute()");
        return serverHealth;
    }

}
