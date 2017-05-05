/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman;

import java.net.URL;

import com.dell.isg.smi.wsmanclient.impl.IWSManClientFactory;

public class InbandWSManClientFactory implements IWSManClientFactory {
    @Override
    public InbandWSManClient getClient(String ip, String username, String password) {
        return new InbandWSManClient(ip, username, password);
    }


    @Override
    public InbandWSManClient getClient(String ip, int port, String username, String password) {
        return new InbandWSManClient(ip, port, username, password);
    }


    @Override
    public InbandWSManClient getClient(URL connectionUrl, String username, String password) {
        return new InbandWSManClient(connectionUrl, username, password);
    }
}
