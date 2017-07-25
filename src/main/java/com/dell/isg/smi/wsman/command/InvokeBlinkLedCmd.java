/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.dell.isg.smi.wsmanclient.WSManBaseInvokeCommand;
import com.dell.isg.smi.wsmanclient.WSManException;
import com.dell.isg.smi.wsmanclient.model.InvokeCmdResponse;
import com.dell.isg.smi.wsmanclient.util.UpdateUtils;

/**
 * The Class InvokeBlinkLedCmd.
 */
public class InvokeBlinkLedCmd extends WSManBaseInvokeCommand<InvokeCmdResponse> {

	protected int identifyState = 0; 
	protected int duration = 0;
	
	/**
	 * Instantiates a new invoke blink led cmd.
	 *
	 * @param enable the enable
	 */
	public InvokeBlinkLedCmd(Boolean enable) {
		super(WSManInvokableEnum.DCIM_SystemManagementService, WSManMethodEnum.BLINK_LED.toString());
		this.duration = 0; // clear out any previous value just in case, though not used
		if (enable) {
			this.identifyState = 1; // 1 = Enabled
		} else {
			this.identifyState = 0; // 0 = Disabled
		}
	}
	
	/**
	 * Instantiates a new invoke blink led cmd.
	 *
	 * @param enable the enable
	 * @param secondsToBlink the seconds to blink
	 */
	public InvokeBlinkLedCmd(Boolean enable, int secondsToBlink) {
		super(WSManInvokableEnum.DCIM_SystemManagementService, WSManMethodEnum.BLINK_LED.toString());
		this.duration = 0; // clear out any previous value
		if (enable) {
			if (secondsToBlink > 0) {
				this.identifyState = 2; // 2 = Time Limited Enabled
				this.duration = secondsToBlink;
			} else {
				this.identifyState = 1; // 1 = Enabled
			}
		} else {
			this.identifyState = 0; // 0 = Disabled
		}
	}
	
    /* (non-Javadoc)
     * @see com.dell.isg.smi.wsmanclient.IWSManCommand#getFilterWQL()
     */
    @Override
    public String getFilterWQL() {
        return null;
    }

    /* (non-Javadoc)
     * @see com.dell.isg.smi.wsmanclient.IWSManCommand#parse(java.lang.String)
     */
    @Override
    public InvokeCmdResponse parse(String xml) throws WSManException {
        String uri = getResourceURI();
        return UpdateUtils.getAsInvokeCmdResponse(xml, uri);
    }

    /* (non-Javadoc)
     * @see com.dell.isg.smi.wsmanclient.WSManBaseInvokeCommand#getUserParams()
     */
    @Override
    public List<Pair<String, String>> getUserParams() {
        List<Pair<String, String>> ret = new ArrayList<Pair<String, String>>();
    	ret.add(Pair.of("IdentifyState", Integer.toString(identifyState)));
    	if (identifyState == 2) {
    		ret.add(Pair.of("DurationLimit", Integer.toString(this.duration)));
    	}
        return ret;
    }
    
}
