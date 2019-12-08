/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servlet.admin;

import com.bookDAO.BookDAO;
import com.bookDAO.BookErr;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Hashtable;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import utils.CreateUrlutil;
import utils.ParseEncryptRequestUtil;

/**
 *
 * @author Admin
 */
@WebServlet(name = "UpdateBookServlet", urlPatterns = {"/admin/UpdateBookServlet"})
@MultipartConfig
public class UpdateBookServlet extends HttpServlet {

    private static final String UPDATE_BOOK_ERR = "updateBook.jsp";
    private static final String UPDATE_BOOK_SUCCESS = "/Search"; //URL: contextpath/search.jsp

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        final BookDAO dao = new BookDAO();
        BookErr addBookErr = new BookErr();
        String finalUrl = UPDATE_BOOK_ERR;
        boolean isValidInput = true;
        Hashtable params = null;
        if (ServletFileUpload.isMultipartContent(request)) {

            params = ParseEncryptRequestUtil.getParams(request);

            if (params == null) {
                response.sendError(response.SC_INTERNAL_SERVER_ERROR);
                return;
            }

            String bookId = (String) params.get("bookId");
            String title = (String) params.get("title");
            String author = (String) params.get("author");
            String description = (String) params.get("description");
            String categoryId = (String) params.get("categoryId");
            int quantity = Integer.parseInt((String) params.get("quantity"));
            float price = Float.parseFloat((String) params.get("price"));
            boolean isActive = (String) params.get("updateBookStatus") != null;
//            final Part filePart = request.getPart("imagePath");

            if (categoryId == null || categoryId.trim().isEmpty()) {
                addBookErr.setCategoryIDErr("Please choose categoryID");
                isValidInput = false;
            }
            if (bookId == null || bookId.trim().isEmpty()) {
                addBookErr.setBookIdErr("Please enter bookId");
                isValidInput = false;
            }
            if (title == null || title.trim().isEmpty()) {
                addBookErr.setTitleErr("Please enter title");
                isValidInput = false;
            }
            if (author == null || author.trim().isEmpty()) {
                addBookErr.setAuthorErr("Please enter author");
                isValidInput = false;
            }
            String imagePath = (String) params.get("imagePath");
            //TODO: validation input
            boolean updateSuccess = false;
            try {

                if (isValidInput) {
                    updateSuccess = dao.updateBook(bookId, title, author, imagePath, description, price, quantity, categoryId, isActive);
                }
            } catch (SQLException ex) {
                log("AddBookServlet-SQLException " + ex.getMessage());
                
            } catch (NamingException ex) {
                log("AddBookServlet-NamingException " + ex.getMessage());
            } finally {
                if (updateSuccess && isValidInput) {
                    finalUrl = UPDATE_BOOK_SUCCESS;

//                    params.put("searchBy", request.getParameter("searchBy"));
//                    params.put("searchTitle", request.getParameter("searchTitle"));
//                    params.put("fromPrice", request.getParameter("fromPrice"));
//                    params.put("toPrice", request.getParameter("toPrice"));
//                    params.put("searchCategoryId", request.getParameter("searchCategoryId"));
                    params.put("UPDATE_BOOK_SUCCESS", "Update book successfully");
                    finalUrl = CreateUrlutil.createUrl(finalUrl, params);
                    response.sendRedirect(request.getContextPath() + finalUrl);

                    //redirect
                } else {
                    finalUrl = UPDATE_BOOK_ERR;
                    request.setAttribute("UPDATE_BOOK_ERR", addBookErr);
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
