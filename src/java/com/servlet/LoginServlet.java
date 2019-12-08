/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servlet;

import com.userDAO.UserDAO;
import com.userDAO.UserDTO;
import java.io.IOException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Admin
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private static final String LOGIN_FAILED = "login.jsp";
    private static final String LOGIN_SUCCESSED = "search.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        final UserDAO dao = new UserDAO();
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDTO user = null;
        String finalUrl = LOGIN_FAILED;

        if (username == null || password == null
                || username.isEmpty() || password.isEmpty()) {
            response.sendError(response.SC_BAD_REQUEST);
        } else {
            try {
                user = dao.checkLogin(username, password);

                if (user == null) {
                    request.setAttribute("LOGIN_ERR", "Wrong username or password!!");
                } else {
                    finalUrl = LOGIN_SUCCESSED;
                    request.getSession().setAttribute("USER", user);
                }

            } catch (NamingException ex) {
                log("LoginServlet-NamingException " +ex.getMessage());
            } catch (SQLException ex) {
                log("LoginServlet-SQLException " +ex.getMessage());
            } finally {
                if (user != null) {
                    response.sendRedirect(finalUrl);
                } else {
                    request.getRequestDispatcher(finalUrl).forward(request, response);
                }
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
