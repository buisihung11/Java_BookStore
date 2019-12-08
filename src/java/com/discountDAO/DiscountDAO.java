/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.discountDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import utils.DBUtils;

/**
 *
 * @author Admin
 */
public class DiscountDAO {

    public boolean addDiscount(String discountCode, float value) throws NamingException, SQLException {

        boolean addSuccess = false;

        Connection c = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {

            c = DBUtils.makeConnection();
            String sql = "INSERT INTO tbl_Discount (DiscountCode , DiscountValue ,IsUsed , CreatedDate) "
                    + " VALUES ( ? ,? ,? ,?) ";

            stm = c.prepareCall(sql);
            stm.setString(1, discountCode);
            stm.setFloat(2, value);
            stm.setBoolean(3, false);
            java.util.Date currentDate = new java.util.Date();
            stm.setDate(4, new Date(currentDate.getTime()));

            addSuccess = stm.executeUpdate() > 0;

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
        return addSuccess;

    }

    public boolean isValidDiscount(String discountCode) throws NamingException, SQLException {

        boolean isValid = false;

        Connection c = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {

            c = DBUtils.makeConnection();
            String sql = "SELECT username "
                    + " from tbl_Discount "
                    + " WHERE DiscountCode = ? and isUsed = ? and username IS NULL";

            stm = c.prepareCall(sql);
            stm.setString(1, discountCode);
            stm.setBoolean(2, false);

            rs = stm.executeQuery();
            if (rs.next()) {
                isValid = true;
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
        return isValid;
    }

    public float getDiscountValue(String discountCode) throws NamingException, SQLException {

        float discountValue = 0;

        Connection c = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {

            c = DBUtils.makeConnection();
            String sql = "SELECT DiscountValue "
                    + " from tbl_Discount "
                    + " WHERE DiscountCode = ? and isUsed = ? and username IS NULL";

            stm = c.prepareCall(sql);
            stm.setString(1, discountCode);
            stm.setBoolean(2, false);

            rs = stm.executeQuery();
            if (rs.next()) {
                discountValue = rs.getFloat("DiscountValue");
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
        return discountValue;
    }
}
