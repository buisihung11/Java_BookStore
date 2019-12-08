/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import utils.DBUtils;

/**
 *
 * @author Admin
 */
public class UserDAO {
    
    
    public boolean addUser( String username,
     String fullName,
     String pasword,
     String address,
     String phone) throws NamingException, SQLException{
        boolean result = false;
        
        Connection c = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {

            c = DBUtils.makeConnection();
            String sql = "INSERT INTO tbl_User (username,fullName,password,address,phone,status,isAdmin) "
                    + " VALUES (? ,? ,?, ?,?,?, ?)";

            stm = c.prepareCall(sql);

            stm.setString(1, username);
            stm.setString(2, fullName);
            stm.setString(3, pasword);
            stm.setString(4, address);
            stm.setString(5, phone);
            stm.setBoolean(6, true);
            stm.setBoolean(7, false);
            
            result = stm.executeUpdate() > 0;

            

        } finally {
            if (stm != null) {
                stm.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (c != null) {
                c.close();
            }

        }

        return result;
        
        
    }
    
    public UserDTO checkLogin(String username,String password) throws NamingException, SQLException{
        UserDTO user = null;
        Connection c = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {

            c = DBUtils.makeConnection();
            String sql = "SELECT username,fullName,address,phone,isAdmin "
                    + " FROM  tbl_User"
                    + " WHERE username = ? and password = ? and status = ?";

            stm = c.prepareCall(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            stm.setBoolean(3, true);
            rs = stm.executeQuery();

            while (rs.next()) {
               

                String fullName = rs.getString("fullName");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                boolean isAdmin = rs.getBoolean("isAdmin");
//                boolean status,

                user = new UserDTO(username, fullName, address, phone, isAdmin);
            }

        } finally {
            if (stm != null) {
                stm.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (c != null) {
                c.close();
            }

        }
        
        
        return user;
        
    }
    
}
