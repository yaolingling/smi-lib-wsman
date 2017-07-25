/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import static junit.framework.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dell.isg.smi.wsmanclient.IWSManClient;
import com.dell.isg.smi.wsmanclient.WSManClientFactory;
import com.dell.isg.smi.wsman.command.WSManClassEnum;
import com.dell.isg.smi.wsmanclient.impl.DefaultEnumerate;
import com.dell.isg.smi.wsmanclient.WSManException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class EnumerateSystemViewParseTest.
 */
public class EnumerateSystemViewParseTest
{
	 protected IWSManClient client;

	 /**
 	 * Sets the up.
 	 */
 	@Before
	 public void setUp()
	 {
		 client = WSManClientFactory.getClient("foo", "bar", "foo");
	 }

	 /**
 	 * Tear down.
 	 */
 	@After
	 public void tearDown()
	 {
		 client.close();
	 }
	
	
	/**
	 * Should find known string value in J son object.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws WSManException the WS man exception
	 * @throws Exception the exception
	 */
	@Test
	public void shouldFindKnownStringValueInJSonObject() throws IOException, WSManException, Exception
	{
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("responses/DCIM_SystemView/1.xml");
	 
		String xml = IOUtils.toString(inputStream, "UTF-8");
		
		DefaultEnumerate<Object> defaultEnumerate = new DefaultEnumerate<Object>(WSManClassEnum.DCIM_SystemView);
		Object systemViewObject = defaultEnumerate.parse(xml);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(systemViewObject);
		 
		JSONObject jsonObject = new JSONObject(jsonInString);
		String serviceTag = jsonObject.getString("ServiceTag");
		 
		assertEquals("Service tag does not match expected value", "4V63TS1", serviceTag);
	}
}
