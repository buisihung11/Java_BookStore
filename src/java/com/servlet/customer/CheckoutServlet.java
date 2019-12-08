/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servlet.customer;

import com.bookDAO.BookDAO;
import com.bookDAO.BookDTO;
import com.cart.BookItem;
import com.cart.OrderCart;
import com.orderDAO.OrderDAO;
import com.userDAO.UserDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
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
@WebServlet(name = "CheckoutServlet", urlPatterns = {"/customer/CheckoutServlet"})
public class CheckoutServlet extends HttpServlet {

    //2. tao mot transaction
    //3.redirect user toi trang paypal voi url cua success va cancel
    //4. O Servlet xu li khi user san sang tra cho order ta thuc hien 
    //4.1.check quantity
    //4.2luu thong tin xuong db
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

        boolean checkOutSuccess = true;
        try {

            HttpSession session = request.getSession();

            UserDTO currentUser = (UserDTO) session.getAttribute("USER");
            OrderCart cart = (OrderCart) session.getAttribute("CART");
            if (cart == null || cart.isEmpty()
                    || currentUser == null) {
                throw new Exception("DON'T HAVE ITEM IN CART");
            }
            //START CHECKOUT
            //1. If has discount , update tbl_discount status (DO IN DAO)
            //2. Check quantity 
            Map<String, BookItem> items = cart.items;
            Map<String, Integer> lackItems = new HashMap<>();//bookId and number of item available

            BookDAO bookDAO = new BookDAO();
            OrderDAO orderDAO = new OrderDAO();
            for (Map.Entry<String, BookItem> entry : items.entrySet()) {
                String bookId = entry.getKey();
                BookItem bookOrder = entry.getValue();

                BookDTO book = bookDAO.getBookById(bookId);

                if (book.getQuantity() < bookOrder.quantity) {
                    checkOutSuccess = false;
                    lackItems.put(bookId, book.getQuantity());
                }
            }

            if (!checkOutSuccess) {
                request.setAttribute("LACK_OF_QUANTITY", lackItems);
                throw new Exception("LACK_OF_QUANTITY");
            }
            //2. Create row in tbl_order && tbl_orderDetail

            cart.setCashedType("Normal");
            checkOutSuccess = orderDAO.createOrder(cart);
            if(checkOutSuccess){
                //3. Remove CART 
                session.removeAttribute("CART");
            }

        } catch (NamingException ex) {
            checkOutSuccess = false;
            log("CheckoutServlet-NamingException " + ex.getMessage());
        } catch (SQLException ex) {
            checkOutSuccess = false;
            log("CheckoutServlet-SQLException " + ex.getMessage());
        } catch (Exception ex) {
            checkOutSuccess = false;
            log("CheckoutServlet-Exception " + ex.getMessage());
//            ex.printStackTrace();
        } finally {
            if (checkOutSuccess) {
                response.sendRedirect(request.getContextPath() + "/search.jsp?CHECKOUT_SUCCESS=" + "Your order has been created");
            } else {
//                request.setAttribute("ERROR_SUMMARY", "Error occur");
                request.getRequestDispatcher("viewCart.jsp").forward(request, response);
            }
        }

//        response.sendRedirect("checkout.jsp");
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
