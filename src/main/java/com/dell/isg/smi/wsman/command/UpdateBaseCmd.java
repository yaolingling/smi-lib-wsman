/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.dell.isg.smi.commons.elm.utilities.xml.XmlHelper;
import com.dell.isg.smi.wsman.WSManBaseCommand;
import com.dell.isg.smi.wsman.entity.LifeCycleJob;
import com.dell.isg.smi.wsman.utilities.IceeUtils;

public abstract class UpdateBaseCmd extends WSManBaseCommand {

    private static final Logger logger = LoggerFactory.getLogger(UpdateBaseCmd.class);


    protected UpdateBaseCmd(String ipAddr, int port, String userName, String passwd, boolean bCertCheck) {
        super(ipAddr, port + "", userName, passwd, bCertCheck);
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Entering constructor: UpdateBaseCmd(String ipAddr - %s, int port - %d, String userName - %s, String passwd - %s, boolean bCertCheck - %s)", ipAddr, port, userName, passwd, Boolean.toString(bCertCheck)));
        }
        logger.trace("Exiting constructor: UpdateBaseCmd()");
        // TODO Auto-generated constructor stub
    }


    protected LifeCycleJob getJobStatus(Node lifeCycleJobNode) {

        LifeCycleJob jobStatus = null;

        if (lifeCycleJobNode != null) {
            if (lifeCycleJobNode.getNodeName().contains("DCIM_LifecycleJob")) {
                jobStatus = new LifeCycleJob();

                NodeList childNodes = lifeCycleJobNode.getChildNodes();

                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node node = childNodes.item(j);

                    if (node.getLocalName().equals("InstanceID")) {
                        if (node.getFirstChild() != null) {
                            jobStatus.setInstanceID(node.getFirstChild().getNodeValue());
                        }
                    } else if (node.getLocalName().equals("JobStartTime")) {
                        if (node.getFirstChild() != null) {
                            jobStatus.setJobStartTime(node.getFirstChild().getNodeValue());
                        }
                    } else if (node.getLocalName().equals("JobStatus")) {
                        if (node.getFirstChild() != null) {
                            jobStatus.setJobStatus(node.getFirstChild().getNodeValue());
                        }
                    } else if (node.getLocalName().equals("JobUntilTime")) {
                        if (node.getFirstChild() != null) {
                            jobStatus.setJobUntilTime(node.getFirstChild().getNodeValue());
                        }
                    } else if (node.getLocalName().equals("Message")) {
                        if (node.getFirstChild() != null) {
                            jobStatus.setMessage(node.getFirstChild().getNodeValue());
                        }
                    } else if (node.getLocalName().equals("MessageID")) {
                        if (node.getFirstChild() != null) {
                            jobStatus.setMessageID(node.getFirstChild().getNodeValue());
                        }
                    } else if (node.getLocalName().equals("Name")) {
                        if (node.getFirstChild() != null) {
                            jobStatus.setJobName(node.getFirstChild().getNodeValue());
                        }
                    }
                }

                if (jobStatus.getJobStatus() != null && jobStatus.getJobStatus().equalsIgnoreCase("New")) {
                    jobStatus.setJobStatus("New Job");
                }
                // Changing the running status to scheuled to make 12G updates compatible with 11G
                else if (jobStatus.getJobStatus() != null && jobStatus.getJobStatus().equalsIgnoreCase("running")) {
                    jobStatus.setJobStatus("Scheduled");
                }

                if (jobStatus.getMessage() == null || jobStatus.getMessage().equals("") || jobStatus.getMessage().equalsIgnoreCase("na")) {
                    jobStatus.setMessage(jobStatus.getJobStatus());
                }

            }
        }

        return jobStatus;
    }


    private Object findObjectInDocument(Document doc, String xPathLocation, QName qname) throws XPathExpressionException {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        XPathExpression expr = xpath.compile(xPathLocation);
        Object result = expr.evaluate(doc, qname);
        return result;
    }


    protected Document convertStringToXMLDocument(String xmlSource) {
        if (xmlSource != null && !xmlSource.isEmpty()) {
            try {
                return XmlHelper.convertStringToXMLDocument(xmlSource);
            } catch (ParserConfigurationException e) {
            } catch (SAXException e) {
            } catch (IOException e) {
            }
        }
        return null;
    }


    protected String getErrorMessage(String xmlSource) {

        if (xmlSource != null && !xmlSource.equals("")) {
            String failureMessage = "";
            String failureCode = "";
            try {
                Document doc = convertStringToXMLDocument(xmlSource);
                failureMessage = (String) findObjectInDocument(doc, "//*[local-name()='Message']/text()", XPathConstants.STRING);
                failureCode = (String) findObjectInDocument(doc, "//*[local-name()='MessageID']/text()", XPathConstants.STRING);
                failureMessage = failureMessage.trim();
                failureCode = failureCode.trim();
                return failureMessage + "@" + failureCode;
            } catch (XPathExpressionException e) {
            }
        }
        return "Unknown error occurred while processing current request on the iDRAC";
    }


    protected String getUpdateJobID(String xmlSource) {

        String jobId = "";
        if (xmlSource != null && !xmlSource.equals("")) {
            Document doc = convertStringToXMLDocument(xmlSource);
            if (doc != null) {
                NodeList selectors = doc.getElementsByTagName("wsman:Selector");
                if (selectors != null) {
                    for (int i = 0; i < selectors.getLength(); i++) {
                        Node selectorNode = selectors.item(i);

                        if (selectorNode.hasAttributes()) {
                            NamedNodeMap attribs = selectorNode.getAttributes();
                            Node attribNode = attribs.getNamedItem("Name");
                            if (attribNode != null) {
                                if (attribNode.hasChildNodes()) {
                                    Node instanceNode = attribNode.getChildNodes().item(0);
                                    if (instanceNode != null) {
                                        String instance = instanceNode.getNodeValue();
                                        if (instance.equals("InstanceID")) {
                                            jobId = selectorNode.getFirstChild().getNodeValue();
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
        return jobId;
    }


    protected String getFileUri(String filePath, String userName, String password) throws MalformedURLException {
        String uri = "";

        if (filePath == null || filePath.equals("")) {
            throw new MalformedURLException();
        }
        // Check if File path is not ftp, tftp, or http
        if (!filePath.contains("ftp.dell.com") && !filePath.contains("ftp://") && !filePath.contains("tftp://") && !filePath.contains("http://") && !filePath.contains("https://")) {
            if (filePath.startsWith("cifs") || filePath.startsWith("nfs")) {
                return filePath;
            } else if (filePath.startsWith("\\\\") || filePath.startsWith("//")) {
                String path = filePath.replace("\\", "/");
                String[] values = path.split("/");
                // Here Values[2] = Share.
                // And Values[3] = mount point.
                if (values.length >= 5) {
                    StringBuilder file = new StringBuilder();
                    for (int i = 4; i < values.length; i++) {
                        file.append("/");
                        file.append(values[i]);
                    }
                    uri = String.format("cifs://%s:%s@%s%s;mountpoint=%s", userName, password, resolveShareIP(values[2]), file.toString(), values[3]);
                } else {
                    throw new MalformedURLException(filePath);
                }
            }

            else if (IceeUtils.isNFSFilePath(filePath)) {
                String[] values = filePath.split(":");
                if (values.length == 2) {
                    String fileName = ""; // FIX ME for ICEEUtilities.getFileName(filePath);
                    String nfsServer = resolveShareIP(values[0]);
                    String mountPoint = values[1].replace("/" + fileName, "");
                    uri = String.format("nfs://%s/%s;mountpoint=%s", nfsServer, fileName, mountPoint);
                } else {
                    throw new MalformedURLException(filePath);
                }

            } else {
                throw new MalformedURLException(filePath);
            }
        } else {
            try {
                if (filePath.contains("tftp")) {

                    return filePath;

                } else if (!filePath.contains("http://") && !filePath.contains("ftp://")) {
                    filePath = "ftp://" + filePath;
                }
                URL url = new URL(filePath);
                uri = url.toString();
            } catch (MalformedURLException e) {
                throw new MalformedURLException(filePath);
            }

        }
        // Check for the validity of http, https, ftp, tftp URLs
        return uri;
    }


    private String resolveShareIP(String ipOrHostName) {

        if (ipOrHostName == null || ipOrHostName.trim().isEmpty()) {
            return "";
        }

        String result = ipOrHostName.trim();
        /*
         * FIX ME for ICEE try {
         * 
         * if(!DNSJavaResolver.isIPAddress(ipOrHostName)){ DNSRecord record = DNSJavaResolver.dnsLookup(ipOrHostName); result = record.getIpAddress(); }
         * 
         * } catch (IOException e) { logger.error("Unable to resolve the Share IP from the given Hostname or FQDN "+ipOrHostName); }
         */

        return result;
    }

}
