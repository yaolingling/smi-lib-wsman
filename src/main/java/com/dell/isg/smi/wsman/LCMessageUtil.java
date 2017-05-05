/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dell.isg.smi.wsman.errormessage.MESSAGE;
import com.dell.isg.smi.wsman.errormessage.MESSAGECOMPONENTS;
import com.dell.isg.smi.wsman.errormessage.RECOMMENDEDACTION;
import com.dell.isg.smi.wsman.errormessage.REGISTRY;
import com.dell.isg.smi.wsman.errormessage.REGISTRYENTRIES;

public class LCMessageUtil {
    static REGISTRY registry;
    static HashMap<String, LCMessage> messages;
    private final static Logger log = LoggerFactory.getLogger(LCMessageUtil.class);


    @SuppressWarnings("unchecked")
    static void initMessages() {
        if (messages == null) {
            log.debug("..................LCMessageUtil: Initializing Messagse ............");
            messages = new HashMap<String, LCMessage>();
            JAXBContext jc = null;
            Unmarshaller u = null;
            REGISTRY registry = null;

            XMLInputFactory xmlInputFactory = null; // FIX ME for ICEE XMLInputFactory.newFactory();
            xmlInputFactory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
            xmlInputFactory.setProperty(XMLInputFactory.SUPPORT_DTD, false);

            try {
                jc = JAXBContext.newInstance("com.dell.pg.spectre.common.protocol.wsman.errormessage");
            } catch (JAXBException e) {
                log.error("Unable to initialize JAXB for the LC error messages package");
                log.error(e.getMessage(), e);
            }

            try {
                u = jc.createUnmarshaller();
            } catch (JAXBException e) {
                log.error("Unable to initialize unmarshaller for the LC error messages package");
                log.error(e.getMessage(), e);
            }

            XMLStreamReader xmlStreamReader = null;
            try {
                File file = new File(Thread.currentThread().getContextClassLoader().getResource("/fileResource/emsg_en.xml").getFile());
                xmlStreamReader = xmlInputFactory.createXMLStreamReader(new StreamSource(file));
                registry = (REGISTRY) u.unmarshal(xmlStreamReader);

            } catch (JAXBException e) {
                log.error("Unable to load or parse the LC messages files");
                log.error(e.getMessage(), e);
            } catch (XMLStreamException e) {
                log.error("Unable to load or parse the LC messages files");
                log.error(e.getMessage(), e);
            } finally {
                if (null != xmlStreamReader) {
                    try {
                        xmlStreamReader.close();
                    } catch (XMLStreamException e) {
                        log.error("exception while closing streamreader");
                        log.error(e.getMessage(), e);
                    }
                }
            }

            REGISTRYENTRIES entries = (REGISTRYENTRIES) registry.getREGISTRYENTRIES();
            List<MESSAGE> msgs = null;
            if (entries != null) {
                msgs = entries.getMESSAGE();
                for (MESSAGE msg : msgs) {
                    if (msg != null) {

                        LCMessage lcMsg = new LCMessage();

                        lcMsg.setDescription(msg.getMESSAGEDESCRIPTION());
                        lcMsg.setMessage(msg.getMESSAGEDESCRIPTION());

                        MESSAGECOMPONENTS components = msg.getMESSAGECOMPONENTS();
                        if (components != null) {
                            List<Object> content = components.getContent();
                            if (content.size() > 0 && content.get(0) != null) {
                                JAXBElement<String> element = (JAXBElement<String>) content.get(0);
                                lcMsg.setMessage(element.getValue());
                            }
                        }

                        lcMsg.setErrorType("ERROR");

                        if (msg.getFIXEDMESSAGEINSTANCEVALUES() != null) {
                            lcMsg.setErrorType(msg.getFIXEDMESSAGEINSTANCEVALUES().getTYPE());
                            List<RECOMMENDEDACTION> actions = msg.getFIXEDMESSAGEINSTANCEVALUES().getRECOMMENDEDACTION();
                            if (actions.size() > 0) {
                                String strActions = actions.get(0).getDESCRIPTION();
                                lcMsg.setRecommendedActions(actions.get(0).getDESCRIPTION());
                                if (strActions != null && !strActions.equals("") && !strActions.toLowerCase().startsWith("none")) {
                                    lcMsg.setHasRecommendedActions(true);
                                } else {
                                    lcMsg.setHasRecommendedActions(false);
                                }

                            }
                        }
                        messages.put(msg.getNAME(), lcMsg);
                    }
                }
            }

        }
    }


    public LCMessage getLCMessage(String msgId) {
        LCMessage message = null;
        if (msgId != null && !msgId.trim().equals("")) {
            try {
                initMessages();
            } catch (Exception e) {
            }
            if (messages != null && messages.containsKey(msgId)) {
                message = messages.get(msgId);
                message.setErrorCode(msgId);
            }
        }
        return message;
    }

}
