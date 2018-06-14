/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.utils;

/**
 *
 * @author hpasupuleti
 */
import com.pdms.exception.AppException;
import java.security.Key;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

/**
 *
 * @author hpasupuleti
 */
@Component
//@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)
public class EncryptDecrypt {

    private static final String ALGO = "AES";
    private static final byte[] keyValue
            = new byte[]{'P', 'd', 'M', '$', 'A', 'p', 'p',
                'l', '!', 'c', '@', 't', 'i', '0', 'N', '$'};

    private String applicationKey;

    public EncryptDecrypt() {

    }
   
    public String encrypt(String data) throws AppException {
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = c.doFinal(data.getBytes());
            String encryptedValue = new Base64(true).encodeToString(encVal);
            return encryptedValue;
        } catch (Exception e) {
            throw new AppException("Exception Occurred while Encrypting:" + e.getMessage());
        }
    }

    public String decrypt(String encryptedData) throws AppException {
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] decordedValue = new Base64().decode(encryptedData.getBytes());
            byte[] decValue = c.doFinal(decordedValue);
            String decryptedValue = new String(decValue);
            return decryptedValue;
        } catch (Exception e) {
            throw new AppException("Exception Occurred while Decrypting:" + e.getMessage());
        }
    }

    
    public static void main(String [] a)
    {
        try
        {
            EncryptDecrypt encryptDecrypt = new EncryptDecrypt();
            String rand = new GenerateRandomNumber().generateSessionKeyToken();
            System.out.println("rand:"+rand);
            //encryptDecrypt.setApplicationKey(rand);
            String enc = encryptDecrypt.encrypt("superadmin");
            String dec = encryptDecrypt.decrypt(enc);
            String dec1 = encryptDecrypt.decrypt("mo9QKlLgdSX-FPgs_2kkYQ");
            System.out.println("Enc:"+enc);
            System.out.println("Dec:"+dec);
            System.out.println("Dec11:"+dec1);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private Key generateKey() throws Exception {
        //Key key = new SecretKeySpec(getKeyValueBytes(), ALGO);
        Key key = new SecretKeySpec(keyValue, ALGO);
        return key;
    }

    private byte[] getKeyValueBytes() {
        byte[] hKeyValue = getApplicationKey().getBytes();
        return hKeyValue;
    }

    /**
     * @return the applicationKey
     */
    public String getApplicationKey() {
        return applicationKey;
    }

    /**
     * @param applicationKey the applicationKey to set
     */
    public void setApplicationKey(String applicationKey) {
        this.applicationKey = applicationKey;
    }
   
    
}
