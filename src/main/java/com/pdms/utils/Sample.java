/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.utils;

/**
 *
 * @author hpasupuleti
 */
public class Sample {
    
    public static void main(String [] a)
    {
        String str1 = new String("Hi");
        String str2 = new String(str1);
        
        System.err.println(str1 == str2);
    }
    
}
