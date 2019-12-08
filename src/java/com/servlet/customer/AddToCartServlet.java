/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servlet.customer;

import com.cart.OrderCart;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utils.CreateUrlutil;

/**
 *
 * @author Admin
 */
@WebServlet(name = "AddToCartServlet", urlPatterns = {"/customer/AddToCartServlet"})
public class AddToCartServlet extends HttpServlet {

    private static final String ADD_TO_CART_ERR = "updateBook.jsp";
    private static final String ADD_TO_CART_SUCCESS = "/Search"; //URL: contextpath/search.jsp

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        boolean validRequest = true;
        //SERVER VALIDATION
        try {
            String bookId = request.getParameter("bookId");
            if (bookId == null) {
                validRequest = false;
                throw new Exception("DONT'T HAVE BOOKID");
            }

            int quantity = Integer.parseInt(request.getParameter("quantity"));
            if (quantity <= 0) {
                validRequest = false;
                throw new Exception("NOT VALID QUANTITY");
            }

            String bookName = request.getParameter("bookName");
            float price = Float.parseFloat(request.getParameter("price"));
            //PERFORM REQUEST

            HttpSession session = request.getSession();
            OrderCart cart = (OrderCart) session.getAttribute("CART");
            if (cart == null) {
                cart = new OrderCart();
            }

            cart.addItems(bookId, bookName, quantity, price);
            session.setAttribute("CART", cart);

        } catch (Exception ex) {
//            ex.printStackTrace();
            log("AddToCartServlet-Exception " + ex.getMessage());
            validRequest = false;
        } finally {
            if (!validRequest) {
                response.sendError(response.SC_BAD_REQUEST);
            } else {
                String url = ADD_TO_CART_SUCCESS;
                Map<String, String> redirectParams = new HashMap<>();

                redirectParams.put("searchBy", request.getParameter("searchBy"));
                redirectParams.put("searchTitle", request.getParameter("searchTitle"));
                redirectParams.put("fromPrice", request.getParameter("fromPrice"));
                redirectParams.put("toPrice", request.getParameter("toPrice"));
                redirectParams.put("searchCategoryId", request.getParameter("searchCategoryId"));
                redirectParams.put("ADD_TO_CART_SUCCESS", "Add successfully");

                url = CreateUrlutil.createUrl(url, redirectParams);
                //RESPONSE
                response.sendRedirect(request.getContextPath() + url);
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
