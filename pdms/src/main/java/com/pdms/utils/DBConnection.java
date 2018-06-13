/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.utils;

import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

/**
 *
 * @author hpasupuleti
 */
public class DBConnection {

   
    public Connection getConnection() {
        Connection con = null;
        try {
            //InputStream input = new FileInputStream("/dbconn.properties");
            InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("dbconnection.properties");
            Properties prop = new Properties();
            prop.load(input);

            String DB_URL = prop.get("jdbc.url").toString();
            String USER = prop.get("jdbc.user").toString();
            String PASS = prop.get("jdbc.password").toString();
            String driverClassName = prop.get("jdbc.driver").toString();
            
            Class.forName(driverClassName);
            
            con = DriverManager.getConnection(DB_URL, USER, PASS);
            
            if (!con.isClosed()) {
                System.out.println("Successfully connected to MySQL server using TCP/IP...");
            }
            
//            ApplicationContext context= new ClassPathXmlApplicationContext("seedapmis-servlet.xml");
//
//            if (context.containsBean("dataSource")) {
//                BasicDataSource bds = (BasicDataSource) context.getBean("dataSource");
//
//                con = bds.getConnection();
//                if (!con.isClosed()) {
//                    System.out.println("Successfully connected to MySQL server using TCP/IP...");
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
    
    public void closeAll(ResultSet rs, CallableStatement cstmt, Connection con) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (cstmt != null) {
                cstmt.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (Exception e) {
            System.out.println("in close all connection");
            e.printStackTrace();
        }
    }

    public void closePreparedStmt(PreparedStatement pStmt, Connection con) {
        try {
            if (pStmt != null) {
                pStmt.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (Exception e) {
            System.out.println("in close all connection");
            e.printStackTrace();
        }
    }

    public void closeResultset(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
            System.out.println("in close Resultset");
            e.printStackTrace();
        }

    }

    public void closeCallableStatement(CallableStatement cstmt) {
        try {
            if (cstmt != null) {
                cstmt.close();
            }
        } catch (Exception e) {
            System.out.println("in close CallableStatement");
            e.printStackTrace();
        }

    }
}
