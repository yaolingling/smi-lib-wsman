/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
/**
 * 
 */
package com.dell.isg.smi.wsman;

import java.net.PasswordAuthentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to handle Java's Static Authenticator. Returns passwordAuthentication based on Thread ID.
 * 
 * 
 * @author anthony_crouch
 *
 */
public class SpectreAuthenticator extends java.net.Authenticator {
    private final static Logger log = LoggerFactory.getLogger(SpectreAuthenticator.class);

    private PasswordAuthentication passwordAuthentication;


    // private static Object lock = new Object();
    // private static String lastRequestingURL = "";
    //
    // public static SpectreAuthenticator.PasswordStash stash = null;
    /**
     * Constructor
     * 
     * @param login
     * @param password
     * @param url
     */
    public SpectreAuthenticator(String login, String password, String url) {
        super();
        passwordAuthentication = new PasswordAuthentication(login, password.toCharArray());
        // synchronized(lock){
        // if(stash == null)
        // stash = new SpectreAuthenticator.PasswordStash();
        // }
        // stash.put(login, password, url);
        // stash.clean();//Remove old old entries.

    }


    /**
     * 
     */
    protected PasswordAuthentication getPasswordAuthentication() {
        return passwordAuthentication;

        // synchronized(lock){
        // if(! StringUtils.equals(lastRequestingURL, this.getRequestingURL().toString())){
        // lastRequestingURL = this.getRequestingURL().toString();
        // log.debug("System request Authenticator for Url: " + this.getRequestingURL());
        // }
        // AuthTracker tracker = stash.passwordMap.get(this.getRequestingURL().toString());
        // if ( tracker != null ) {
        // return tracker.auth;
        // } else {
        // log.debug("PasswordAuthentication was not found for: " + this.getRequestingURL());
        // return null;
        // }
        // }
    }
    /**
     * 
     * @author anthony_crouch
     *
     */
    // private class PasswordStash{
    // private ConcurrentHashMap<String,AuthTracker> passwordMap = new ConcurrentHashMap<String,AuthTracker>();
    // /**
    // *
    // * @param url
    // * @param password
    // */
    // private void flushCacheIfNeeded(String url, String password, String userName){
    // log.debug("Checking if cache needes Flushing" );
    // AuthTracker authOld = passwordMap.get(url);
    // if(authOld != null && authOld.auth != null){
    // char[] oldPW = authOld.auth.getPassword();
    // String oldUsername = authOld.auth.getUserName();
    // if(oldPW != null && oldUsername != null){
    // if( !(Arrays.equals(oldPW,password.toCharArray())) || !(StringUtils.equals(oldUsername, userName)) ){
    // log.debug("Flushing Cache. Authenticator changed for URL: " + url);
    // AuthCacheValue.setAuthCache(new AuthCacheImpl());
    // }
    // }
    // }
    // }
    // /**
    // *
    // * @param login
    // * @param password
    // * @param url
    // */
    // private void put(String login,String password, String url){
    // synchronized(lock){
    // flushCacheIfNeeded(url, password, login);
    //
    // log.debug("Putting Authenticator for URL: " + url);
    // PasswordAuthentication pa = new PasswordAuthentication(login,password.toCharArray());
    // AuthTracker auth = new AuthTracker();
    // auth.auth = pa;
    // auth.createTime = System.currentTimeMillis();
    // passwordMap.put(url, auth);
    // }
    // }
    //
    // private void clean(){
    // synchronized(lock){
    // Enumeration<String> elems = passwordMap.keys();
    // while(elems.hasMoreElements()){
    // String key =elems.nextElement();
    // AuthTracker tracker = passwordMap.get(key);
    // if((System.currentTimeMillis() - tracker.createTime) > (24*60*60*1000)){
    // log.debug("Cleaning Authenticator for URL: "+ key );
    // //older than a day
    // passwordMap.remove(key);
    // }
    // }
    // }
    // }
    //
    // }
    // /**
    // * Used to keep track how old.
    // * Will remove if too old. Keeps the hashmap
    // * from being memory leak over long long time.
    // *
    // * @author anthony_crouch
    // *
    // */
    // private class AuthTracker{
    // public PasswordAuthentication auth;
    // public long createTime;
    //
    // }

}
