/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.listener;

import com.CaterogyDAO.CategoryDAO;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Web application lifecycle listener.
 *
 * @author Admin
 */
@WebListener()
public class InitialationLister implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        
        ServletContext ctx = sce.getServletContext();
        Map<String,String> categories = null;

        CategoryDAO dao = new CategoryDAO();

        try {
            categories = dao.getCategories();
            ctx.setAttribute("CATEGORIES", categories);
        } catch (NamingException ex) {
            Logger.getLogger(InitialationLister.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(InitialationLister.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
