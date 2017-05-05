/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.utilities;

/**
 * EncryptionHelper is an easy to use interface for encypting and decrypting String values using an arbitrary number of salts.
 */
public interface EncryptionHelper {

    /**
     * Encrypts the provided value. All salts must be know to decrypt the value.
     * 
     * @param value The raw value to encrypt
     * @param salts The salts to strengthen the encryption
     * @return A BASE64 encoded String
     * @throws Exception If there was a problem encrypting the value
     */
    public String encrypt(String value, String... salts) throws Exception;


    /**
     * Decrpyts the provided value. All salts must be correct to decrypt the value.
     * 
     * @param value The BASE64 encoded encrypted value
     * @param salts The salts to strengthen the encryption
     * @return The decrpyted value
     * @throws Exception If there was a problem decrypting the value
     */
    public String decrypt(String value, String... salts) throws Exception;
}
