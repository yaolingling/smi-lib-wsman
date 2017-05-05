/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.utilities;

import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * EncryptionHelperImpl handles the encryption and decryption of String values. AES encryption is used, and salts are converted into keys.
 * 
 * The Class Key and Configuration Salt are used to strengthen the encryption, and if they are changed decryption will be impossible.
 */
public class AESEncryptionHelperImpl implements EncryptionHelper {

    private static final String ALGORITHM = "AES";
    private static final String CLASS_KEY = "Sj/RCzWE4MVt5dYHHH59jQ";

    private String configSalt;


    @Override
    public String encrypt(String value, String... salts) throws Exception {
        if (value == null || "".equals(value)) {
            return value;
        }

        Cipher c = Cipher.getInstance(ALGORITHM);
        List<Key> keys = generateKeys(salts);
        byte[] encVal = value.getBytes();
        for (Key key : keys) {
            c.init(Cipher.ENCRYPT_MODE, key);
            encVal = c.doFinal(encVal);
        }
        byte[] encoded = Base64.encodeBase64(encVal, false);
        String returnStr = new String(encoded);

        // erase when finished
        Arrays.fill(encoded, (byte) 0x00);
        return returnStr;
    }


    @Override
    public String decrypt(String value, String... salts) throws Exception {
        if (value == null || "".equals(value)) {
            return value;
        }

        Cipher c = Cipher.getInstance(ALGORITHM);
        List<Key> keys = generateKeys(salts);
        Collections.reverse(keys);
        byte[] encVal = Base64.decodeBase64(value);
        for (Key key : keys) {
            c.init(Cipher.DECRYPT_MODE, key);
            encVal = c.doFinal(encVal);
        }

        String returnStr = new String(encVal);

        // erase when finished
        Arrays.fill(encVal, (byte) 0x00);
        return returnStr;
    }


    private List<Key> generateKeys(String... salts) throws Exception {
        List<Key> result = new ArrayList<Key>();
        result.add(new SecretKeySpec(Base64.decodeBase64(CLASS_KEY), "AES"));
        if (getConfigSalt() != null && !"".equals(getConfigSalt())) {
            byte[] configSalt = getConfigSalt().getBytes();
            byte[] configSaltKey = Arrays.copyOf(configSalt, 16);
            result.add(new SecretKeySpec(configSaltKey, "AES"));
        }

        if (salts != null && salts.length != 0) {
            for (String salt : salts) {
                if (salt != null && !"".equals(salt)) {
                    byte[] saltBytes = salt.getBytes();
                    byte[] saltKey = Arrays.copyOf(saltBytes, 16);
                    result.add(new SecretKeySpec(saltKey, "AES"));
                }
            }
        }

        return result;
    }


    /**
     * @return The Configuration Salt
     */
    public String getConfigSalt() {
        return configSalt;
    }


    /**
     * @param configSalt - The Configuration Salt
     */
    public void setConfigSalt(String configSalt) {
        this.configSalt = configSalt;
    }
}
