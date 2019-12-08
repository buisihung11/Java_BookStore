/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servlet.customer;

import com.cart.OrderCart;
import com.discountDAO.DiscountDAO;
import com.userDAO.UserDTO;
import java.io.IOException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
@WebServlet(name = "CheckOutServlet", urlPatterns = {"/customer/CheckOutPageServlet"})
public class CheckOutPageServlet extends HttpServlet {

    private static final String CHECKOUT_ERR = "viewCart.jsp";
//    private static final String CHECKOUT_INPUT_ERR = "viewCart.jsp";
    private static final String CHECKOUT_SUCCESS = "checkout.jsp"; //URL: contextpath/search.jsp

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //In this Servlet we only check if the user has entered the valid discount 
        //and forward to checkout page
        String discountCode = request.getParameter("discountCode");
        float discountValue = 0;
        boolean checkOutSuccess = false;
        DiscountDAO discountDao = new DiscountDAO();
        String recipientPhone = request.getParameter("recipientPhone");
        String recipientAddress = request.getParameter("recipientAddress");
        //if has discount, check valid
        try {
            HttpSession session = request.getSession();
            OrderCart cart = (OrderCart) session.getAttribute("CART");
            UserDTO currentUser = (UserDTO) session.getAttribute("USER");
            if(recipientPhone == null || recipientPhone.trim().isEmpty() 
                    || recipientAddress == null || recipientAddress.trim().isEmpty() ){
                request.setAttribute("FORM_INPUT_ERR", "Please enter recipient phone and address");
                throw new Exception("DIDN'T FULLFILLED FORM");
            }
            
            if (cart == null || cart.isEmpty()) {
                throw new Exception("DON'T HAVE ITEM IN CART");
            }

            if (discountCode != null && !discountCode.isEmpty()) {
                discountValue = discountDao.getDiscountValue(discountCode);
                if (discountValue == 0) {
                    request.setAttribute("DISCOUNT_NOT_VALID", "Not valid discount code");
                    throw new Exception("Not valid discount code");
                } else {
                    //if has discount set the discount to ordercart

                    cart.setDiscountCode(discountCode);
                    cart.setDiscountValue(discountValue);
                    session.setAttribute("CART", cart);
                    checkOutSuccess = true;
                }
            }
            cart.setUsername(currentUser.getUsername());
            cart.setRecipientPhone(recipientPhone);
            cart.setRecipientAddress(recipientAddress);
            //create session for STRIPE
            checkOutSuccess = true;
        } catch (NamingException ex) {
            log("CheckOutPageServlet-NamingException " + ex.getMessage());
        } catch (SQLException ex) {
            log("CheckOutPageServlet-SQLException " + ex.getMessage());
        } catch (Exception ex) {
            checkOutSuccess = false;
        } finally {
            String url = checkOutSuccess ? CHECKOUT_SUCCESS : CHECKOUT_ERR;
            if (checkOutSuccess) {
                response.sendRedirect(url);
            } else {
                request.getRequestDispatcher(url).forward(request, response);
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
