/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.Permission;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;

import com.dell.isg.smi.commons.elm.exception.RuntimeCoreException;
import com.dell.isg.smi.wsman.utilities.StreamUtils;

import org.slf4j.LoggerFactory;

import com.sun.ws.management.Management;
import com.sun.ws.management.addressing.Addressing;

public class HttpConnection {

    private HttpsURLConnection connection = null;
    private String destination = null;
    private String destIP = null;
    private int timeout = 15000; // default timeout is 15 seconds
    private boolean bCheckCertificate = false; // default policy for cert check is false

    // set up a logger
    private static final Logger logger = LoggerFactory.getLogger(HttpConnection.class);

    private static String WSMAN_CERTS = "/usr/lib/jvm/java/jre/lib/security/wsmancerts";
    private static String WSMAN_CERTS_PWD = "changeit";
    private static KeyStore truststore = null; // making this static since we want to load keystore
                                               // only once
    private X509Certificate[] certChain = null;

    public SSLContext sslContext = null;
    private String userName;
    private String password;


    /**
     *
     * @param dest dest is target URL
     */
    public HttpConnection(String dest, String destIP, boolean bCheckCert, String user, String password) {
        destination = dest;
        this.destIP = destIP;
        this.bCheckCertificate = bCheckCert;
        this.userName = user;
        this.password = password;
    }


    public void setTimeout(int to) {
        timeout = to;
    }


    public Addressing sendHttp(Addressing msg) throws IOException, JAXBException, SOAPException {
        Addressing reply = null;
        long connectionTimeoutTime = System.currentTimeMillis() + timeout;
        String sOutput = "";
        StringBuilder result = new StringBuilder();

        while (true) {
            OutputStream out = null;
            InputStream in = null;
            BufferedReader bufferedReader = null;
            InputStreamReader inputStringReader = null;
            ByteArrayInputStream byteArrayInputStream = null;
            try {
                initializeConnection();
                result.setLength(0);
                logger.info(destIP + " Doing HTTP Connect(Addressing)...");
                connection.connect();
                logger.info(destIP + " Doing HTTP Connect Successful");
                out = connection.getOutputStream();
                msg.writeTo(out);
                in = connection.getInputStream();

                inputStringReader = new InputStreamReader(in);
                bufferedReader = new BufferedReader(inputStringReader);
                String inputLine = "";
                while ((inputLine = bufferedReader.readLine()) != null) {
                    result.append(inputLine.trim());
                }

                int c;
                for (int index = 0; index < result.length(); index++) {
                    c = result.charAt(index);
                    if (c < 32) {
                        result.setCharAt(index, ' ');
                    }
                }

                sOutput = result.toString();
                checkResponse();
                byteArrayInputStream = new ByteArrayInputStream(sOutput.getBytes());
                reply = new Addressing(byteArrayInputStream);
                break;
            } catch (IOException ioe) {
                logger.debug("Caught IO exception: " + ioe.getMessage());
                int rc = this.checkResponse();
                if (rc == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    logger.error(ioe.getMessage());
                    throw new NotAuthorizedException(ioe);
                }
                if (rc == HttpURLConnection.HTTP_BAD_REQUEST) {
                    logger.error(ioe.getMessage());
                    throw new BadRequestException(ioe);
                }
                if (System.currentTimeMillis() > connectionTimeoutTime) {
                    logger.error(ioe.getMessage());
                    throw ioe;
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage());
                }
            } catch (SOAPException soe) {
                logger.error("SOAPException sendHttp: " + destIP, soe);
                // dump raw data to log file
                sOutput = sOutput.replaceAll("&gt;", ">");
                sOutput = sOutput.replaceAll("&lt;", "<");
                logger.error("SOAPException sendHttp: " + sOutput);
                // throw exception to top level code
                throw soe;
            }

            finally {
                connection.disconnect();
                StreamUtils.closeStreamQuietly(in);
                StreamUtils.closeStreamQuietly(out);
                StreamUtils.closeStreamQuietly(inputStringReader);
                StreamUtils.closeStreamQuietly(bufferedReader);
                StreamUtils.closeStreamQuietly(byteArrayInputStream);
            }
        }
        return reply;
    }


    public Addressing sendHttp(Management msg) throws IOException, JAXBException, SOAPException {

        Addressing reply = null;
        String sOutput = "";
        long connectionTimeoutTime = System.currentTimeMillis() + timeout;
        StringBuilder result = new StringBuilder();

        while (true) {
            OutputStream out = null;
            InputStream in = null;
            BufferedReader buffer = null;
            InputStreamReader inputStreamReader = null;
            ByteArrayInputStream byteArrayInputStream = null;

            try {
                initializeConnection();
                result.setLength(0);
                logger.info(destIP + " Doing HTTP Connect(Management)...");
                connection.connect();
                logger.info(destIP + " Doing HTTP Connect Successful");
                out = connection.getOutputStream();
                msg.writeTo(out);
                in = connection.getInputStream();
                inputStreamReader = new InputStreamReader(in);
                buffer = new BufferedReader(inputStreamReader);
                String inputLine = "";
                while ((inputLine = buffer.readLine()) != null) {
                    result.append(inputLine.trim());
                }

                int c;
                for (int index = 0; index < result.length(); index++) {
                    c = result.charAt(index);
                    if (c < 32) {
                        result.setCharAt(index, ' ');
                    }
                }

                sOutput = result.toString();
                checkResponse();
                byteArrayInputStream = new ByteArrayInputStream(sOutput.getBytes());
                reply = new Addressing(byteArrayInputStream);
                break;
            } catch (IOException ioe) {
                logger.debug("Caught IO exception: " + ioe.getMessage());
                int rc = this.checkResponse();
                if (rc == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    logger.error(ioe.getMessage());
                    throw new NotAuthorizedException(ioe);
                }
                if (rc == HttpURLConnection.HTTP_BAD_REQUEST) {
                    logger.error(ioe.getMessage());
                    throw new BadRequestException(ioe);
                }
                if (System.currentTimeMillis() > connectionTimeoutTime) {
                    logger.error(ioe.getMessage());
                    throw ioe;
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage());
                }
            } catch (SOAPException soe) {
                logger.error("SOAPException sendHttp: " + destIP, soe);
                // dump raw data to log file
                sOutput = sOutput.replaceAll("&gt;", ">");
                sOutput = sOutput.replaceAll("&lt;", "<");
                logger.error("SOAPException sendHttp: " + sOutput);
                // throw exception to top level code
                throw soe;
            } finally {
                connection.disconnect();
                StreamUtils.closeStreamQuietly(inputStreamReader);
                StreamUtils.closeStreamQuietly(buffer);
                StreamUtils.closeStreamQuietly(in);
                StreamUtils.closeStreamQuietly(out);
                StreamUtils.closeStreamQuietly(byteArrayInputStream);
            }
        }
        return reply;
    }


    private void initializeConnectionRacadm(String sessionId, String sXMLData) throws IOException {

        URL url = new URL(destination);

        connection = (HttpsURLConnection) url.openConnection();
        connection.setSSLSocketFactory(sslContext.getSocketFactory());

        if (this.bCheckCertificate == true && truststore != null) {
            // Now check if this is the first time we are making a connection.
            try {
                logger.info("Checking if SSL Cert for " + destIP + " is present in keystore.");
                if (truststore.containsAlias(destIP) == false) // if this destIP not present in keystore then add it.
                {
                    AddWsmanCertToKeyStore(destIP);
                } else {
                    logger.info("SSL Cert for host " + destIP + " already present in keystore.");
                }
            } catch (Exception e) {
                logger.error("Error in initializeConnection while checking/adding cert: " + e.getMessage(), e);
            }
        }
        // else no cert check requested so no need to do anything.

        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.setDefaultUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setReadTimeout(timeout);

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "text/xml");
        connection.setRequestProperty("Connection", "keep-alive");
        connection.setRequestProperty("User-Agent", "Dell HTTP/SSL");
        if (sessionId != null)
            connection.setRequestProperty("Cookie", "sid=" + StringEscapeUtils.escapeHtml4(sessionId));
        if (sXMLData != null)
            connection.setRequestProperty("Content-Length", "" + sXMLData.length());
    }


    private void initializeConnectionRacadm(String sessionId, int contentLength) throws IOException {

        URL url = new URL(destination);

        connection = (HttpsURLConnection) url.openConnection();
        connection.setSSLSocketFactory(sslContext.getSocketFactory());

        if (this.bCheckCertificate == true && truststore != null) {
            // Now check if this is the first time we are making a connection.
            try {
                logger.info("Checking if SSL Cert for " + destIP + " is present in keystore.");
                if (truststore.containsAlias(destIP) == false) // if this destIP not present in keystore then add it.
                {
                    AddWsmanCertToKeyStore(destIP);
                } else {
                    logger.info("SSL Cert for host " + destIP + " already present in keystore.");
                }
            } catch (Exception e) {
                logger.error("Error in initializeConnection while checking/adding cert: " + e.getMessage(), e);
            }
        }
        // else no cert check requested so no need to do anything.

        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.setDefaultUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setReadTimeout(timeout);

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/octet-stream");
        connection.setRequestProperty("Connection", "keep-alive");
        connection.setRequestProperty("User-Agent", "Dell HTTP/SSL");
        if (sessionId != null)
            connection.setRequestProperty("Cookie", "sid=" + StringEscapeUtils.escapeHtml4(sessionId));
        connection.setRequestProperty("Content-Length", "" + contentLength);
    }


    public String sendHttpPutfile(String sessionId, byte[] content) throws IOException, JAXBException, SOAPException, RuntimeCoreException {
        InputStream in = null;
        OutputStream writer = null;
        String reply = null;
        long connectionTimeoutTime = System.currentTimeMillis() + timeout;
        String sOutput = "";
        StringBuilder result = new StringBuilder();

        while (true) {
            try {
                initializeConnectionRacadm(sessionId, content.length);

                logger.info(destIP + " Doing HTTP Connect(Racadm)...");
                connection.connect();
                logger.info(destIP + " Doing HTTP Connect Successful");
                if (content != null) {
                    writer = connection.getOutputStream();
                    writer.write(content);
                    writer.flush();
                }

                in = connection.getInputStream();

                java.io.BufferedReader br = new java.io.BufferedReader(new java.io.InputStreamReader(in));
                String inputLine = "";
                while ((inputLine = br.readLine()) != null) {
                    result.append(inputLine.trim() + "\n");
                }

                sOutput = result.toString();
                checkResponse();
                reply = sOutput;
                break;
            } catch (SSLHandshakeException she) {
                logger.debug("Caught ssl handshake expression" + she.getMessage());
                RuntimeCoreException rse = new RuntimeCoreException(239093);
                throw rse;
            } catch (IOException ioe) {
                logger.debug("Caught IO exception: " + ioe.getMessage());
                int rc = this.checkResponse();
                if (rc == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    logger.error(ioe.getMessage());
                    throw new RuntimeCoreException(210050);
                }
                if (System.currentTimeMillis() > connectionTimeoutTime) {
                    logger.error(ioe.getMessage());
                    throw new RuntimeCoreException(210050);
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage());
                }
            } finally {
                connection.disconnect();
                IOUtils.closeQuietly(in);
                IOUtils.closeQuietly(writer);
            }
        }
        return reply;
    }


    public String sendHttpRacadm(String sessionId, String sXMLData) throws IOException, JAXBException, SOAPException, RuntimeCoreException {
        InputStream in = null;
        OutputStreamWriter writer = null;
        String reply = null;
        long connectionTimeoutTime = System.currentTimeMillis() + timeout;
        String sOutput = "";
        StringBuilder result = new StringBuilder();

        while (true) {
            try {
                initializeConnectionRacadm(sessionId, sXMLData);
                // result.setLength(0);
                logger.info(destIP + " Doing HTTP Connect(Racadm)...");
                connection.connect();
                logger.info(destIP + " Doing HTTP Connect Successful");
                if (sXMLData != null) {
                    writer = new OutputStreamWriter(connection.getOutputStream());
                    writer.write(sXMLData);
                    writer.flush();
                }

                in = connection.getInputStream();

                java.io.BufferedReader br = new java.io.BufferedReader(new java.io.InputStreamReader(in));
                String inputLine = "";
                while ((inputLine = br.readLine()) != null) {
                    result.append(inputLine.trim() + "\n");
                }

                // int c;
                // for(int index=0; index < result.length(); index++){
                // c = result.charAt(index);
                // if(c < 32){
                // result.setCharAt(index, ' ');
                // }
                // }
                // reading the response
                // InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                // StringBuilder buf = new StringBuilder();
                // char[] cbuf = new char[2048];
                // int num;
                // while (-1 != (num = reader.read(cbuf))) {
                // buf.append(cbuf, 0, num);
                // }
                // result = buf.toString();

                sOutput = result.toString();
                checkResponse();
                reply = sOutput;
                break;
            } catch (SSLHandshakeException she) {
                logger.debug("Caught ssl handshake expression" + she.getMessage());
                RuntimeCoreException rse = new RuntimeCoreException(239093);
                throw rse;
            } catch (IOException ioe) {
                logger.debug("Caught IO exception: " + ioe.getMessage());
                int rc = this.checkResponse();
                if (rc == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    logger.error(ioe.getMessage());
                    throw new RuntimeCoreException(210050);
                }
                if (System.currentTimeMillis() > connectionTimeoutTime) {
                    logger.error(ioe.getMessage());
                    throw new RuntimeCoreException(210050);
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage());
                }
            } finally {
                connection.disconnect();
                IOUtils.closeQuietly(in);
                IOUtils.closeQuietly(writer);
            }
        }
        return reply;
    }


    private void initializeConnection() throws IOException {

        URL url = new URL(destination);

        connection = (HttpsURLConnection) url.openConnection();
        connection.setSSLSocketFactory(sslContext.getSocketFactory());
        connection.setConnectTimeout(15 * 1000); // 15 seconds

        if (this.bCheckCertificate == true && truststore != null) {
            // Now check if this is the first time we are making a connection.
            try {
                logger.info("Checking if SSL Cert for " + destIP + " is present in keystore.");
                if (truststore.containsAlias(destIP) == false) // if this destIP not present in
                                                               // keystore then add it.
                {
                    AddWsmanCertToKeyStore(destIP);
                } else {
                    logger.info("SSL Cert for host " + destIP + " already present in keystore.");
                }
            } catch (Exception e) {
                logger.error("Error in initializeConnection while checking/adding cert: " + e.getMessage(), e);
            }
        }
        // else no cert check requsted so no need to do anything.

        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setAllowUserInteraction(false);
        connection.setInstanceFollowRedirects(false);
        connection.setReadTimeout(timeout);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/soap+xml;charset=utf-8");
        connection.setRequestProperty("User-Agent", "https://wiseman.dev.java.net");
        connection.setRequestProperty("Accept", "application/soap+xml;charset=utf-8, application/soap+xml;charset=utf-16");
        connection.setRequestProperty("Connection", "close");
        configureAuthorization(connection);
    }

    private void configureAuthorization(URLConnection uc) {
        String userpass = this.userName + ":" + this.password;
        String authString = null;
        try {
            authString = "Basic " + DatatypeConverter.printBase64Binary(userpass.getBytes("ISO-8859-1"));
        } catch (UnsupportedEncodingException e) {
            logger.warn("unsupported Encoding exception", e);
        }
        uc.setRequestProperty("Authorization", authString);
    }

    private int checkResponse() throws IOException {
        int respCode = 0;
        try {
            respCode = connection.getResponseCode();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return HttpURLConnection.HTTP_INTERNAL_ERROR;
        }
        switch (respCode) {
        case HttpURLConnection.HTTP_OK:
            logger.debug("HTTP response: OK");
            break;
        case HttpURLConnection.HTTP_BAD_REQUEST:
            logger.info("HTTP response: Bad Request");
            break;
        case HttpURLConnection.HTTP_UNAUTHORIZED:
            logger.info("HTTP response: Unauthorized");
            break;
        case HttpURLConnection.HTTP_BAD_METHOD:
            logger.info("HTTP response: Method Not Allowed");
            break;
        case HttpURLConnection.HTTP_CLIENT_TIMEOUT:
            logger.info("HTTP response: Request Time-out");
            break;
        case HttpURLConnection.HTTP_UNAVAILABLE:
            logger.info("HTTP response: Service Unavailable");
            break;
        default:
            logger.info("HTTP response: Unknown Reason");
            break;
        }
        return respCode;

    }


    @SuppressWarnings("unused")
    private void getConnectionDetails() throws IOException {
        String contentEncoding = connection.getContentEncoding();
        String contentType = connection.getContentType();
        Map<String, List<String>> headerFields = connection.getHeaderFields();
        Permission perm = connection.getPermission();
    }


    public void AddWsmanCertToKeyStore(String destIP) {
        SSLSocket sslSocket = null;
        this.certChain = null; // set chain to NULL to clear any previous chains stored there
        // strIPAddress = vCenter.getIpAddress();

        logger.info("Trying connection to " + destIP + " to get SSL cert");
        if (truststore == null) {
            logger.info("Trust store is NULL");
            return;
        }

        try {
            sslSocket = (SSLSocket) sslContext.getSocketFactory().createSocket(destIP, 443);
            sslSocket.setSoTimeout(timeout);
            try {
                sslSocket.startHandshake(); // this call should give us the certificate. It will
                                            // also throw exception
            } catch (Exception e) {
                logger.info("Exception: " + e.getMessage());
            }

            if (this.certChain == null) {
                logger.error("No certificate returned from server " + destIP);
            } else {
                for (int j = 0; j < this.certChain.length; j++) {
                    logger.info("WSMAN: Server certificate information:");
                    logger.info("  Subject DN: " + this.certChain[j].getSubjectDN());
                    logger.info("  Issuer DN: " + this.certChain[j].getIssuerDN());
                    logger.info("  Serial number from server: " + this.certChain[j].getSerialNumber());
                    logger.info("");
                    if (j == 0) {
                        truststore.setCertificateEntry(destIP, this.certChain[j]);
                    } else {
                        truststore.setCertificateEntry(destIP + "." + j, this.certChain[j]);
                    }
                }
                WriteTrustStore();
                // String msg = FriendlyLogMessage.createLogMessage("SECURITY_CERTIFICATE_ADDED", destIP);
                // logger.log(SpectreLogLevel.USER_FACING_SUCCESS, msg);

                logger.info("Resetting socket factory to use the new keystore values.");

                TrustManagerFactory tmFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                tmFactory.init(truststore);
                X509TrustManager tm = (X509TrustManager) tmFactory.getTrustManagers()[0];
                TrustManagerInterceptor tmInterceptor = new TrustManagerInterceptor(tm, this);
                sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, new TrustManager[] { tmInterceptor }, new SecureRandom());
                connection.setSSLSocketFactory(sslContext.getSocketFactory());
            }

        } catch (Exception e2) {
            logger.error("Exception while checking for cert " + e2.getMessage(), e2);
        } finally {
            try {
                if (sslSocket != null)
                    sslSocket.close();
            } catch (Exception e) {
            }
        }
    }


    public void InitializeTrustStore() {
        if (truststore == null) // initialize trust store
        {

            logger.info("Initializing Trust Store for WSMAN connections.");
            // if file does not exist then the following may fail...this is okay since we will write
            // back the file.
            InputStream input = null;
            try {
                try {
                    input = new FileInputStream(WSMAN_CERTS);
                } catch (FileNotFoundException e) {
                    logger.error("File  " + WSMAN_CERTS + "Not Found: " + e.getMessage(), e);
                }

                // if "input" is null then a new keystore will be created
                try {
                    truststore = KeyStore.getInstance(KeyStore.getDefaultType());
                    truststore.load(input, WSMAN_CERTS_PWD.toCharArray());
                } catch (Exception e) {
                    logger.error("Exception in InitializeTrustStore " + e.getMessage(), e);
                }
            } finally {
                StreamUtils.closeStreamQuietly(input);
            }
        }
    }


    public void RemoveHostFromTrustStore(String destIP) {
        if (truststore != null) {
            try {
                if (truststore.containsAlias(destIP) == true) // if this destIP not present in
                                                              // keystore then add it.
                {
                    truststore.deleteEntry(destIP);
                }
                for (int j = 1; j < 10; j++) {
                    if (truststore.containsAlias(destIP + "." + j) == true) // if this destIP not
                                                                            // present in keystore
                                                                            // then add it.
                    {
                        truststore.deleteEntry(destIP + "." + j);
                    } else {
                        break;
                    }
                }
            } catch (Exception e) {
                logger.error("RemoveHostFromTrustStore: " + e.getMessage(), e);
            }
        }
    }


    public void WriteTrustStore() {
        if (truststore != null) // initialize trust store
        {
            logger.info("Writing Trust Store to file");
            OutputStream output = null;
            try {
                // Now write back the trust store
                output = new FileOutputStream(WSMAN_CERTS);
                truststore.store(output, WSMAN_CERTS_PWD.toCharArray());
            } catch (Exception e) {
                logger.error("Exception in WriteTrustStore " + e.getMessage(), e);
            } finally {
                StreamUtils.closeStreamQuietly(output);
            }
        }
    }


    public void setTrustManager()

            throws NoSuchAlgorithmException, KeyManagementException {
        // TrustManager[] tm = { trustManager };
        // sslContext = SSLContext.getInstance("SSL");
        // sslContext.init(null, tm, new SecureRandom());
        // HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        if (this.bCheckCertificate == true) // users wants to check certificate
        {
            logger.info("Certificate checking is enabled for this connection.");
            if (truststore == null) // initialize trust store
            {
                InitializeTrustStore();
            }

            if (truststore != null) {
                try {
                    TrustManagerFactory tmFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                    tmFactory.init(truststore);
                    X509TrustManager tm = (X509TrustManager) tmFactory.getTrustManagers()[0];
                    TrustManagerInterceptor tmInterceptor = new TrustManagerInterceptor(tm, this);
                    sslContext = SSLContext.getInstance("SSL");
                    sslContext.init(null, new TrustManager[] { tmInterceptor }, new SecureRandom());
                } catch (Exception e) {
                    logger.error("setTrustManager: " + e.getMessage(), e);
                }
            }
        } else
        // users does not want to do check certificate
        {
            logger.info("Certificate checking is disabled for this connection.");
            TrustManager[] tm2 = { tmNoCheck };
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, tm2, new SecureRandom());
        }

    }


    public void setHostnameVerifier(HostnameVerifier hv) {
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }

    // Trust Manager which will do certificate checks
    static class TrustManagerInterceptor implements X509TrustManager {
        private X509TrustManager tm;
        private HttpConnection conn;


        // private X509Certificate[] chain;

        TrustManagerInterceptor(X509TrustManager tm, HttpConnection c) {
            this.tm = tm;
            this.conn = c;

        }


        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
            // TODO Auto-generated method stub
        }


        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
            // TODO Auto-generated method stub
            conn.certChain = arg0;
            logger.info("Spectre: Doing SSL Certificate checking for this connection.");

            /*
             * for (int j = 0; j < arg0.length; j++) { logger.info("Spectre1: Server certificate information:"); logger.info("  Subject DN: " + arg0[j].getSubjectDN());
             * logger.info("  Issuer DN: " + arg0[j].getIssuerDN()); logger.info("  Serial number from server: " + arg0[j].getSerialNumber()); logger.info("  Signature: "+
             * arg0[j].getSignature().toString()); logger.info(""); }
             */
            tm.checkServerTrusted(arg0, arg1);
        }


        @Override
        public X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }
    };

    // Trust Manager which will NOT do any Cert checking
    static X509TrustManager tmNoCheck = new X509TrustManager() {

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
            // TODO Auto-generated method stub
        }


        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
            // TODO Auto-generated method stub
            logger.info("Spectre: Skipping SSL Certificate checking for this connection.");
        }


        @Override
        public X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }

    };
}
