/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman;

import java.net.URL;

/**
 * {@code WSManClientFactory} is a factory class for obtaining {@link IWSManClient} instances.
 */
public class WSManClientFactory {
    private static final IdracWSManClientFactory idracFactory = new IdracWSManClientFactory();
    private static final InbandWSManClientFactory inbandFactory = new InbandWSManClientFactory();


    private WSManClientFactory() {
    }


    /**
     * Returns an {@link IWSManClient} instance.
     *
     * @param ip The iDRAC IP address.
     * @param username The iDRAC username.
     * @param password The iDRAC password.
     * @return The {@link IWSManClient}
     */
    public static IdracWSManClient getIdracWSManClient(String ip, String username, String password) {
        return idracFactory.getClient(ip, username, password);
    }


    /**
     * Returns an {@link IWSManClient} instance.
     *
     * @param ip The iDRAC IP address.
     * @param port The port that the iDRAC WS-Management service listens on.
     * @param username The iDRAC username.
     * @param password The iDRAC password.
     * @return The {@link IWSManClient}
     */
    public static IdracWSManClient getIdracWSManClient(String ip, int port, String username, String password) {
        return idracFactory.getClient(ip, port, username, password);
    }


    /**
     * Returns an {@link IWSManClient} instance.
     *
     * @param connectionUrl The connection URL for the iDRAC WS-Management service.
     * @param username The iDRAC username.
     * @param password The iDRAC password.
     * @return The {@link IWSManClient}
     */
    public static IdracWSManClient getIdracWSManClient(URL connectionUrl, String username, String password) {
        return idracFactory.getClient(connectionUrl, username, password);
    }


    // ------------------------------------

    /**
     * Returns an {@link IWSManClient} instance.
     *
     * @param ip The iDRAC IP address.
     * @param username The iDRAC username.
     * @param password The iDRAC password.
     * @return The {@link IWSManClient}
     */
    public static InbandWSManClient getInbandWSManClient(String ip, String username, String password) {
        return inbandFactory.getClient(ip, username, password);
    }


    /**
     * Returns an {@link IWSManClient} instance.
     *
     * @param ip The iDRAC IP address.
     * @param port The port that the iDRAC WS-Management service listens on.
     * @param username The iDRAC username.
     * @param password The iDRAC password.
     * @return The {@link IWSManClient}
     */
    public static InbandWSManClient getInbandWSManClient(String ip, int port, String username, String password) {
        return inbandFactory.getClient(ip, port, username, password);
    }


    /**
     * Returns an {@link IWSManClient} instance.
     *
     * @param connectionUrl The connection URL for the iDRAC WS-Management service.
     * @param username The iDRAC username.
     * @param password The iDRAC password.
     * @return The {@link IWSManClient}
     */
    public static InbandWSManClient getInbandWSManClient(URL connectionUrl, String username, String password) {
        return inbandFactory.getClient(connectionUrl, username, password);
    }

}
