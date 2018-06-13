/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.utils;

import com.pdms.exception.AppException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.NumberUtils;
import org.springframework.stereotype.Component;

/**
 *
 * @author hpasupuleti
 */
@Component
public class CommonUtil {

    public String replaceSpaceAndSpecialChars(final String paramString) {
        String returnString = "";
        Pattern pattern = Pattern.compile("[^a-z A-Z 0-9]");
        Matcher matcher = pattern.matcher(paramString);
        returnString = matcher.replaceAll("");
        returnString = returnString.replaceAll("\\s+", "_");

        return returnString;
    }

    public boolean isNewPWDContainsOldPWDChars(final String newPWD, final String oldPWD) {
        String str = oldPWD;
        String newString = newPWD;
        int count = 0;
        char chrArr[] = newString.toCharArray();
        for (char c : chrArr) {
            if (str.contains(c + "")) {
                count++;
            }
        }
        if (count >= 4) {
            return true;
        } else {
            return false;
        }
    }

    public static String convertToCommaDelimited(String[] list) {
        StringBuffer ret = new StringBuffer("");
        for (int i = 0; list != null && i < list.length; i++) {
            ret.append(list[i]);
            if (i < list.length - 1) {
                ret.append(',');
            }
        }
        return ret.toString();
    }

    public static String replaceCommaByCommaAndSpace(final Object obj) {
        String repValue = "";
        if (obj != null) {
            repValue = obj.toString();
            repValue = repValue.replaceAll(",", ", ");

        } else {
            return repValue;
        }
        return repValue;
    }

    public String encodeHTMLEntities(String inputString) throws AppException {
        if (inputString.contains("<")) {
            inputString = inputString.replaceAll("<", "&lt;");
        }
        if (inputString.contains(">")) {
            inputString = inputString.replaceAll(">", "&gt;");
        }
        if (inputString.contains("\"")) {
            inputString = inputString.replaceAll("\"", "&quot;");
        }
        if (inputString.contains("'")) {
            inputString = inputString.replaceAll("<", "&#x27;");
        }
        if (inputString.contains("/")) {
            inputString = inputString.replaceAll("/", "&#x2F;");
        }
        if (inputString.contains("&")) {
            inputString = inputString.replaceAll("<", "&amp;");
        }
        return inputString;
    }

    public static String trimTheValue(final Object obj) {
        String repValue = "";
        if (obj != null) {
            repValue = obj.toString().trim();
        } else {
            return repValue;
        }
        return repValue;
    }
    
     public static String replaceNullWithEmpty(final Object obj) {
        String repValue = "";
        if (obj != null) {
            repValue = obj.toString().trim();
        } else {
            return repValue;
        }
        return repValue;
    }

     
    public String prepareUserName(final String firstName, final String lastName) 
    {
        String username="";
        
        return username;
    } 
     
    public String generateUserName(final String firstName, final String lastName, final long empID) 
    {
        String username="";
        String genLastName = lastName+empID;
//        int strLen = lastName.length();
//        char lastChar = lastName.charAt(strLen-1);
//        
//        if(NumberUtils.isNumber(lastChar+""))
//        {
//            int counter = Integer.parseInt(lastChar+"");
//            genLastName = lastName+counter;
//        }
        username = firstName.substring(0,1)+genLastName;
        
        return username;
    }
    
    
     
}
