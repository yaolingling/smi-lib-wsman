/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved. 
 */
package com.dell.isg.smi.wsman.command;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import com.dell.isg.smi.wsmanclient.WSManException;
import com.dell.isg.smi.wsman.command.InvokeBlinkLedCmd;
import com.dell.isg.smi.wsmanclient.model.InvokeCmdResponse;

/**
 * The Class InvokeBlinkLedCmdIT.
 */
public class InvokeBlinkLedCmdIT extends BaseCmdIT {
	
	/**
	 * Test execute.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws WSManException the WS man exception
	 */
	@Test
	public void testExecute() throws IOException, WSManException {
		try {
			// Test blinking the led 
			InvokeCmdResponse execute = client.execute(new InvokeBlinkLedCmd(true));
			assertNotNull(execute);
			assertTrue("Invalid return code.", 0 == execute.getReturnValue());
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
