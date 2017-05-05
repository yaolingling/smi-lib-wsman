/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dell.isg.smi.wsman.WSCommandRNDConstant;
import com.dell.isg.smi.wsman.WSManClientBaseCommand;
import com.dell.isg.smi.wsman.WSManageSession;
import com.sun.ws.management.addressing.Addressing;

public class EnumerateSoftwareIdentityForHostCmd extends WSManClientBaseCommand<Map<String, String>> {

    private WSManageSession session = null;


    public EnumerateSoftwareIdentityForHostCmd(String hostIP, String username, String password) {
        super(hostIP, username, password);

        this.session = super.getSession();

        this.session.setResourceUri(getResourceURI());
    }


    @Override
    public Map<String, String> execute() throws Exception {
        Addressing addressing = this.sendRequestEnumeration();
        return parse(addressing.getBody());
    }


    public String getResourceURI() {
        StringBuilder sb = new StringBuilder(WSCommandRNDConstant.WSMAN_BASE_URI);

        sb.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE).append(WSManClassEnum.CIM_InstalledSoftwareIdentity);

        return sb.toString();
    }


    public Map<String, String> parse(String xml) throws Exception {
        Map<String, String> hypervisorDtlMap = parse(xml);
        return hypervisorDtlMap;
    }


    private Map<String, String> parse(SOAPBody soapBody) throws Exception {
        Map<String, String> hypervisorDtlMap = new HashMap<String, String>();
        Node node = (Node) findObjectInDocument(soapBody, "//*[local-name()='VMware_InstalledSoftwareIdentity']", XPathConstants.NODE);

        NodeList childNodes = node.getChildNodes();
        System.out.println("nodeList :: " + childNodes.getLength());
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);

            if (StringUtils.equalsIgnoreCase(childNode.getLocalName(), "InstalledSoftware")) {
                NodeList installedSoftwareChilds = childNode.getChildNodes();

                for (int j = 0; j < installedSoftwareChilds.getLength(); j++) {
                    Node softwareChildNode = installedSoftwareChilds.item(j);

                    if (StringUtils.equalsIgnoreCase(softwareChildNode.getLocalName(), "ReferenceParameters")) {

                        NodeList referenceParamsList = softwareChildNode.getChildNodes();

                        for (int n = 0; n < referenceParamsList.getLength(); n++) {

                            Node referenceParamChild = referenceParamsList.item(n);

                            if (StringUtils.equalsIgnoreCase(referenceParamChild.getLocalName(), "SelectorSet")) {

                                NodeList selectorSetChildList = referenceParamChild.getChildNodes();

                                for (int k = 0; k < selectorSetChildList.getLength(); k++) {
                                    Node selectorSetChild = selectorSetChildList.item(k);

                                    NamedNodeMap namedNodeMap = selectorSetChild.getAttributes();

                                    for (int l = 0; l < namedNodeMap.getLength(); l++) {
                                        Node attributeNode = namedNodeMap.getNamedItem("Name");
                                        if (StringUtils.equalsIgnoreCase(attributeNode.getTextContent(), "InstanceID")) {
                                            hypervisorDtlMap.put("Name", selectorSetChild.getTextContent());
                                            hypervisorDtlMap.put("VersionString", selectorSetChild.getTextContent());
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return hypervisorDtlMap;
    }


    public Object findObjectInDocument(SOAPElement doc, String xPathLocation, QName qname) throws XPathExpressionException {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        xpath.setNamespaceContext(new PersonalNamespaceContext(getResourceURI()));

        XPathExpression expr = xpath.compile(xPathLocation);
        Object result = expr.evaluate(doc, qname);
        return result;
    }
}
