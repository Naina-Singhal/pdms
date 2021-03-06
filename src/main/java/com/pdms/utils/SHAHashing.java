/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.utils;

import com.pdms.exception.AppException;
import java.security.MessageDigest;
import org.springframework.stereotype.Component;

/**
 *
 * @author hpasupuleti
 */
@Component
public class SHAHashing {

    public String generateSHAHash(String param) throws AppException
    {
        String hashedString="";
        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(param.getBytes());
            byte byteData[] = md.digest();

            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                String hex = Integer.toHexString(0xff & byteData[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            
            hashedString = hexString.toString();
        }
        catch(Exception e)
        {
            throw new AppException("Exception Occurred while hashing:" + e.getMessage());
        }
        return hashedString;
    }
   

}
