/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import java.util.List;

import org.dmtf.schemas.wbem.wsman._1.wsman.SelectorType;
import org.w3._2003._05.soap_envelope.Body;

public interface IWSManClientCommand<T> {

    String getResourceURI();


    /**
     * Parses the SOAP XML response into a POJO of type {@code T}
     *
     * @param xml The SOAP XML response.
     * @return The POJO response.
     * @throws Exception If an invalid XML response was received.
     */
    T parse(String xml) throws Exception;


    /**
     * The Body for the SOAP request.
     *
     * @return The body.
     */
    Body getBody();


    /**
     * The action URI for the request.
     *
     * @return The action URI.
     */
    String getAction();


	List<SelectorType> getSelectors();

}