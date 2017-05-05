/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.entity;

import com.dell.isg.smi.wsman.utilities.AESEncryptionHelperImpl;

/**
 * @author Rahman.Muhammad
 *
 */
public class WsmanCredentials implements java.io.Serializable {
    private static final long serialVersionUID = -5653708523600543969L;

    private static final AESEncryptionHelperImpl encryptionHelper = new AESEncryptionHelperImpl();

    private static final String CLASS_KEY = "1rx2W78iqkfckY";

    private int wsmanCredentialsId;
    private WsmanType type;

    private boolean ADAccount;

    private String userid;

    private String password;

    private boolean certificateCheck;


    public int getWsmanCredentialsId() {
        return this.wsmanCredentialsId;
    }


    public void setWsmanCredentialsId(int wsmanCredentialsId) {
        this.wsmanCredentialsId = wsmanCredentialsId;
    }


    /**
     * @return type
     */
    public WsmanType getType() {
        return this.type;
    }


    /**
     * @param type
     */
    public void setType(WsmanType type) {
        this.type = type;
    }


    /**
     * @return userid
     */
    public String getUserid() {
        return this.userid;
    }


    /**
     * @param userid
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }


    /**
     * @return password
     */
    public String getPassword() {
        try {
            return encryptionHelper.decrypt(this.password, CLASS_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.password;
    }


    /**
     * @param password
     */
    public void setPassword(String password) {
        try {
            this.password = encryptionHelper.encrypt(password, CLASS_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @return certificateCheck
     */
    public boolean isCertificateCheck() {
        return this.certificateCheck;
    }


    /**
     * @param certificateCheck
     */
    public void setCertificateCheck(boolean certificateCheck) {
        this.certificateCheck = certificateCheck;
    }


    /**
     * @param src
     * @param dst
     */
    public static void copyExceptId(WsmanCredentials src, WsmanCredentials dst) {
        dst.setCertificateCheck(src.isCertificateCheck());
        dst.setPassword(src.getPassword());
        // dst.setType(src.getType());
        dst.setUserid(src.getUserid());
        dst.setADAccount(src.isADAccount());
    }


    /**
     * @return the aDAccount
     */
    public boolean isADAccount() {
        return ADAccount;
    }


    /**
     * @param aDAccount the aDAccount to set
     */
    public void setADAccount(boolean aDAccount) {
        ADAccount = aDAccount;
    }
}
