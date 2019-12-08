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
@WebServlet(name = "RemoveFromCartServlet", urlPatterns = {"/customer/RemoveFromCartServlet"})
public class RemoveFromCartServlet extends HttpServlet {

    private static final String REMOVE_CART_ERR = "viewCart.jsp";
    private static final String REMOVE_CART_SUCCESS = "viewCart.jsp"; //URL: contextpath/search.jsprows IOException if an I/O error occurs

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        boolean validRequest = true;
        boolean removeSuccess = false;
        //SERVER VALIDATION
        try {
            String bookId = request.getParameter("bookId");
            if (bookId == null) {
                validRequest = false;
                throw new Exception("DONT'T HAVE BOOKID");
            }


            //PERFORM REQUEST
            HttpSession session = request.getSession();
            OrderCart cart = (OrderCart) session.getAttribute("CART");
            if (cart != null) {
                cart.removeItem(bookId);
                if(cart.isEmpty())
                    cart = null;
                session.setAttribute("CART", cart);
                removeSuccess =true;
            }

        } catch (Exception ex) {
            log("RemoveFromCartServlet-Exception " + ex.getMessage());
            validRequest = false;
        } finally {
            if (!validRequest) {
                response.sendError(response.SC_BAD_REQUEST);
            } else {
                String url = REMOVE_CART_SUCCESS;
                Map<String, String> redirectParams = new HashMap<>();

                if (removeSuccess) {
                    redirectParams.put("REMOVE_CART_SUCCESS", "Remove successfully");
                }else{
                    redirectParams.put("REMOVE_CART_ERR", "Error occur");
                }
                url = CreateUrlutil.createUrl(url, redirectParams);
                //RESPONSE
                response.sendRedirect(url);
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
