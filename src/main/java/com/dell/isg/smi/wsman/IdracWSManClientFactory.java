/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman;

import java.net.URL;

import com.dell.isg.smi.wsmanclient.impl.IWSManClientFactory;

public class IdracWSManClientFactory implements IWSManClientFactory {

    @Override
    public IdracWSManClient getClient(String ip, String username, String password) {
        return new IdracWSManClient(ip, username, password);
    }


    @Override
    public IdracWSManClient getClient(String ip, int port, String username, String password) {
        return new IdracWSManClient(ip, port, username, password);
    }


    @Override
    public IdracWSManClient getClient(URL connectionUrl, String username, String password) {
        return new IdracWSManClient(connectionUrl, username, password);
    }
}
