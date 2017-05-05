/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
/**
 * 
 */
package com.dell.isg.smi.wsman.command;

import java.util.HashSet;
import java.util.Set;

import javax.xml.soap.SOAPBody;

import org.dmtf.schemas.wbem.wsman._1.wsman.SelectorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.sun.ws.management.addressing.Addressing;
import com.sun.ws.management.enumeration.EnumerationExtensions.Mode;

/**
 * Can be used to reboot computer for any reason.
 * 
 * http://dmtf.org/sites/default/files/standards/documents/DSP1052_1.0.1.pdf
 * 
 * @author anthony_crouch
 *
 */
public class PowerBootCmd extends WSManBaseCommand {
    private WSManageSession session;
    private final static String DEDICATED_KEY = "Dedicated";
    private final static String DEDICATED_KEY_VALUE = "0";
    private final static String ENDPOINT_REF = "EndpointReference";
    private final static String RETURN_VALUE = "ReturnValue";
    private final static String RESOURCE_URI = "ResourceURI";
    private final static String SELECTOR_SET = "SelectorSet";
    private final static String REFERENCE_PARAMS = "ReferenceParameters";

    private PowerRebootEnum setting;
    private static final Logger logger = LoggerFactory.getLogger(PowerBootCmd.class);


    /**
     * default constructor
     * 
     * @param ipAddr
     * @param userName
     * @param passwd
     */
    public PowerBootCmd(String ipAddr, String userName, String passwd, PowerRebootEnum type) {
        super(ipAddr, userName, passwd);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: PowerBootCmd(String ipAddr - %s, String userName - %s, String passwd - %s, PowerRebootEnum type - %s)", ipAddr, userName, "####", PowerRebootEnum.class.getName()));
        }
        setting = type;
        session = this.getSession();
        logger.trace("Exiting constructor: PowerBootCmd()");
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.dell.pg.spectre.common.protocol.wsman.WSManBaseCommand#execute()
     */
    @Override
    public Integer execute() throws Exception {
        logger.trace("Entering function: execute()");

        EnumerateCIMComputerSystem enumerateCIM = new EnumerateCIMComputerSystem();
        EndPointReference endPointRef = enumerateCIM.execute();

        if (endPointRef.currentState.equalsIgnoreCase(this.setting.toString())) {
            return 0;
        }

        if (endPointRef.currentState.equalsIgnoreCase("3") && setting.equals(PowerRebootEnum.REBOOT)) {
            this.setting = PowerRebootEnum.ON;
        }

        Integer returnValue = invoke(endPointRef);
        logger.trace("Exiting function: execute()");

        return returnValue;
    }


    /**
     * Call action against the endpoint.
     * 
     * @param endPointRef
     * @return
     * @throws Exception
     */
    private int invoke(EndPointReference endPointRef) throws Exception {
        int status = 1;
        ;
        this.session.setResourceUri(endPointRef.resourceURI);
        session.setSelectors(endPointRef.selectors);
        this.session.setInvokeCommand(WSManMethodEnum.REQUESTED_STATE_CHANGE.toString());
        this.session.addUserParam(WSManMethodParamEnum.REQUESTED_STATE.toString(), setting.toString());
        Addressing response = this.session.sendInvokeRequest();
        status = getObjectFromSoapBody(response.getBody());
        return status;
    }


    /**
     * Get return value. 0 good
     * 
     * @param soapBody
     * @return int 0,1,2
     * @throws Exception
     */
    private int getObjectFromSoapBody(SOAPBody soapBody) throws Exception {

        NodeList nodeList = soapBody.getChildNodes();
        Node node = nodeList.item(0);
        NodeList childNodeList = node.getChildNodes();
        for (int i = 0; i < childNodeList.getLength(); i++) {
            Node childNode = childNodeList.item(i);
            if (childNode.getLocalName().equalsIgnoreCase(RETURN_VALUE)) {
                String value = childNode.getTextContent();
                return Integer.valueOf(value);

            }
        }

        return 2;
    }

    /**
     * Enumerate CIMComputerSystem for enabled class
     * 
     * @author anthony_crouch
     *
     */
    private class EnumerateCIMComputerSystem {
        /**
         * Return Resource URI
         * 
         * @return String
         */
        private String getEnumResourceURI() {
            return "http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/CIM_ComputerSystem";
        }


        /**
         * returns Node instance of item that is needed.
         * 
         * @throws Exception
         */
        private EndPointReference execute() throws Exception {
            session.setResourceUri(getEnumResourceURI());
            Document response = sendRequestEnumerationReturnDocument(Mode.EnumerateObjectAndEPR);
            Node instance = null;

            Element element = response.getDocumentElement();
            /* get Item nodes */
            NodeList nodeList = element.getElementsByTagNameNS(WSCommandRNDConstant.WS_MAN_NAMESPACE, WSCommandRNDConstant.WSMAN_ITEMS_TAG);

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Element.ELEMENT_NODE) {
                    if (node.hasChildNodes()) {
                        NodeList attributesNodeList = node.getChildNodes();

                        for (int j = 0; j < attributesNodeList.getLength(); j++) {
                            Node attributesNode = attributesNodeList.item(j);

                            if (findDedicatedNode(attributesNode)) {
                                instance = attributesNode;
                                break;
                            }
                        }
                    }
                    if (instance != null)
                        break;
                }
            }

            return buildSelectSet(instance);
        }


        /**
         * Build EndPointReference
         * 
         * <wsa:EndpointReference> <wsa:Address>https://127.0.0.1:443/wsman</wsa:Address> <wsa:ReferenceParameters>
         * <wsman:ResourceURI>http://schemas.dell.com/wbem/wscim/1/cim-schema/2/DCIM_SPComputerSystem</wsman:ResourceURI> <wsman:SelectorSet>
         * <wsman:Selector Name="__cimnamespace">root/dcim</wsman:Selector> <wsman:Selector Name="CreationClassName">DCIM_SPComputerSystem</wsman:Selector>
         * <wsman:Selector Name="Name">systemmc</wsman:Selector> </wsman:SelectorSet> </wsa:ReferenceParameters> </wsa:EndpointReference>
         * 
         * @param item
         * @return EndPointReference
         */
        private EndPointReference buildSelectSet(Node item) {
            EndPointReference ref = new EndPointReference();
            Set<SelectorType> set = new HashSet<SelectorType>();
            NodeList children = item.getChildNodes();
            for (int y = 0; y < children.getLength(); y++) {
                Node itemChild = children.item(y);
                if (itemChild.getLocalName().equalsIgnoreCase(ENDPOINT_REF)) {
                    NodeList grandChildren = itemChild.getChildNodes();
                    for (int z = 0; z < grandChildren.getLength(); z++) {
                        Node grandChild = grandChildren.item(z);
                        if (grandChild.getLocalName().equalsIgnoreCase(REFERENCE_PARAMS)) {
                            NodeList refParams = grandChild.getChildNodes();
                            for (int t = 0; t < refParams.getLength(); t++) {
                                Node param = refParams.item(t);
                                if (param.getLocalName().equalsIgnoreCase(RESOURCE_URI))

                                    ref.resourceURI = param.getTextContent();

                                if (param.getLocalName().equalsIgnoreCase(SELECTOR_SET)) {
                                    NodeList selectors = param.getChildNodes();
                                    for (int b = 0; b < selectors.getLength(); b++) {
                                        Node selNode = selectors.item(b);

                                        SelectorType selector = new SelectorType();
                                        selector.setName(selNode.getAttributes().item(0).getNodeValue());

                                        selector.getContent().add(selNode.getTextContent());
                                        set.add(selector);
                                    }
                                    ref.selectors = set;
                                }
                            }
                        }
                    }
                } else if (itemChild.getLocalName().contains("ComputerSystem")) {
                    NodeList compNodeinfoList = itemChild.getChildNodes();
                    for (int n = 0; n < compNodeinfoList.getLength(); n++) {
                        Node compInfoNode = compNodeinfoList.item(n);
                        if (compInfoNode.getLocalName().equalsIgnoreCase("EnabledState")) {
                            ref.currentState = compInfoNode.getTextContent();
                            break;
                        }
                    }
                }
            }

            return ref;
        }


        /**
         * Find the Dedicated Attribute that is *
         * 
         * @param childnodes
         * @return True/False
         */
        private boolean findDedicatedNode(Node PRAttributesNode) {
            NodeList childNodes = PRAttributesNode.getChildNodes();

            for (int t = 0; t < childNodes.getLength(); t++) {
                Node child = childNodes.item(t);
                if (child.hasChildNodes()) {
                    NodeList moreChildren = child.getChildNodes();
                    for (int x = 0; x < moreChildren.getLength(); x++) {
                        Node grandChild = moreChildren.item(x);
                        if (grandChild.getLocalName() != null && grandChild.getLocalName().equalsIgnoreCase(DEDICATED_KEY)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

    }

    /**
     * Requested States Available.
     * 
     * @author anthony_crouch
     *
     */
    public static enum PowerRebootEnum {
        OFF("3"), ON("2"), REBOOT("11");

        String rebootNum;


        PowerRebootEnum(String value) {
            rebootNum = value;
        }


        public String toString() {
            return rebootNum;
        }
    }

    /**
     * End point reference that contains Address, resourceURI, and selectors.
     * 
     * @author anthony_crouch
     *
     */
    private class EndPointReference {

        private String resourceURI;
        private Set<SelectorType> selectors;
        private String currentState;
    }

}
