/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servlet.admin;

import com.bookDAO.BookDAO;
import com.bookDAO.BookDTO;
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
@WebServlet(name = "UpdateBookPageServlet", urlPatterns = {"/admin/UpdateBookPageServlet"})
public class UpdateBookPageServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        final BookDAO dao = new BookDAO();
        String bookId = request.getParameter("bookId");
        BookDTO updateBook = null;

        if (bookId == null) {
            response.sendError(response.SC_BAD_REQUEST);
        } else {
            try {
                updateBook = dao.getBookById(bookId);
            } catch (NamingException ex) {
                log("UpdateBookPageServlet-NamingException " +ex.getMessage());
            } catch (SQLException ex) {
                log("UpdateBookPageServlet-SQLException " +ex.getMessage());
            } finally {
                if (updateBook == null) {
                    
                    response.sendRedirect(request.getContextPath() + "/admin/search.jsp");
                } else {
                    request.setAttribute("UPDATE_BOOK", updateBook);
                    request.getRequestDispatcher("updateBook.jsp").forward(request, response);
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
