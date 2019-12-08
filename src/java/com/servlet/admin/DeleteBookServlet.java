/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servlet.admin;

import com.bookDAO.BookDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.CreateUrlutil;

/**
 *
 * @author Admin
 */
@WebServlet(name = "DeleteBookServlet", urlPatterns = {"/admin/DeleteBookServlet"})
public class DeleteBookServlet extends HttpServlet {

    private static final String DELETE_BOOK_ERR = "/search.jsp";
    private static final String DELETE_BOOK_SUCCESS = "/Search"; //URL: contextpath/search.jsp

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        final BookDAO dao = new BookDAO();
        String bookId = request.getParameter("bookId");

        boolean deleteSuccess = false;
        String finalUrl = DELETE_BOOK_ERR;
        if (bookId == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            try {
                deleteSuccess = dao.deleteBookById(bookId);
            } catch (NamingException ex) {
                log("UpdateBookPageServlet-NamingException " +ex.getMessage());
            } catch (SQLException ex) {
                log("UpdateBookPageServlet-SQLException " +ex.getMessage());
            } finally {
                if (deleteSuccess) {
                    finalUrl = DELETE_BOOK_SUCCESS;

                    Map<String, String> params = new HashMap<>();
                    Enumeration<String> e = request.getParameterNames();
                    while (e.hasMoreElements()) {
                        String paramKey = e.nextElement();
                        params.put(paramKey, request.getParameter(paramKey));
                    }
                    params.put("DELETE_BOOK_SUCCESS", "Delete Book Successfully");

                    finalUrl = CreateUrlutil.createUrl(finalUrl, params);
                    response.sendRedirect(request.getContextPath() + finalUrl);
//                    request.getRequestDispatcher(finalUrl).forward(request, response);
                } else {
                    response.sendError(response.SC_INTERNAL_SERVER_ERROR);
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
