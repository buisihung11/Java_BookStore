/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author buisi
 */
public class DBUtils {

    public static Connection makeConnection() throws NamingException, SQLException {
        Context ctx = new InitialContext();
        Context tomcatCtx = (Context) ctx.lookup("java:comp/env");
        DataSource ds = (DataSource) tomcatCtx.lookup("BookStoreDB");
        
        return ds.getConnection();
    }
}
