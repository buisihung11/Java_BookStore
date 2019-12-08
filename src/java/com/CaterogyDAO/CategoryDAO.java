/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CaterogyDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import utils.DBUtils;

/**
 *
 * @author Admin
 */
public class CategoryDAO {
    
    public Map<String,String> getCategories() throws NamingException, SQLException{
        
        Map<String,String> categories = null;
        
        Connection c = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {

            c = DBUtils.makeConnection();
            String sql = "SELECT categoryID, CategoryName "
                    + " from tbl_Category";

            stm = c.prepareCall(sql);

            
            rs = stm.executeQuery();
            while(rs.next()){
                if(categories ==null)
                    categories = new HashMap<>();
                
                categories.put(rs.getString("categoryID"), rs.getString("CategoryName"));
                
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
        return  categories;
    }
    
}
