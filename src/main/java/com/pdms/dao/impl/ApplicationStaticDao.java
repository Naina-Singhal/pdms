/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dao.impl;

import com.pdms.exception.AppException;
import com.pdms.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author hpasupuleti
 */
public class ApplicationStaticDao {
    
    public int deleteSessionInfoForLogoutUser(final long userID) throws AppException
    {
        DBConnection dbCon = new DBConnection();
        PreparedStatement stmt=null;
        int res = 0;
        Connection con =null;
        String qry = "DELETE FROM session_info WHERE fk_emp_id=?";
        try
        {
            con = dbCon.getConnection();
            stmt = con.prepareStatement(qry);
            stmt.setLong(1, userID);
            
            res = stmt.executeUpdate();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            dbCon.closePreparedStmt(stmt, con);
        }
        return res;
    }
    
    
}
