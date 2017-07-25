/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import org.junit.After;
import org.junit.Before;

import com.dell.isg.smi.wsmanclient.WSManClientFactory;
import com.dell.isg.smi.wsmanclient.IWSManClient;


/**
 * The Class BaseCmdIT.
 */
public abstract class BaseCmdIT
{
	protected IWSManClient client;

 /**
  * Sets the up.
  */
 @Before
 public void setUp()
 {
  String ip = "100.68.123.160";
  	client = WSManClientFactory.getClient(ip, "root", "calvin");
 }

 /**
  * Tear down.
  */
 @After
 public void tearDown()
 {
  client.close();
 }
}
