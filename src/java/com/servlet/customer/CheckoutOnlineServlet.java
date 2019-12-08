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
import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.userDAO.UserDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
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
@WebServlet(name = "CheckoutOnlineServlet", urlPatterns = {"/customer/CheckoutOnlineServlet"})
public class CheckoutOnlineServlet extends HttpServlet {

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
        
        String userEmail = request.getParameter("stripeEmail");
        String stripeToken = request.getParameter("stripeToken");
        HttpSession session = request.getSession();
        UserDTO currentUser = (UserDTO) session.getAttribute("USER");
        OrderCart cart = (OrderCart) session.getAttribute("CART");
        boolean checkOutSuccess = true;
        try {
            if (cart == null || cart.isEmpty()
                    || currentUser == null) {
                throw new Exception("DON'T HAVE ITEM IN CART");
            }
            //START CHECKOUT

            //1.Create transaction with Stripe
            Map<String, Object> userBillingData = new HashMap<>();
            Stripe.apiKey = "sk_test_p54pKzPEhII4U0Mvb6pnV8ll0032kWbAv5";//secret key
            userBillingData.put("email", userEmail);
            userBillingData.put("stripeToken", stripeToken);
            Map<String, Object> params = new HashMap<>();
            params.put("amount", Math.round(cart.getTotalWithDiscount() * 100)); // or get from request
            params.put("currency", "usd");  // or get from request
            params.put("source", userBillingData.get("stripeToken"));// or get from request
            params.put("receipt_email", userEmail);
            params.put("description", "Order Book");
            Charge charge;

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
            //3. CALL transaction
            //Khi goi ham create thi ta se thuc hien finished traction
            charge = Charge.create(params);
            String chargeID = charge.getId();
            String status = charge.getStatus();
            if (status.equals("succeeded")) {
                //Khi transcation thanh cong ta se tao order record xuong DB
                //4. Create row in tbl_order && tbl_orderDetail
//                    cart.setUsername(currentUser.getUsername());
//                    cart.setRecipientPhone(recipientPhone);
//                    cart.setRecipientAddress(recipientAddress);
                cart.setCashedType("Online");
                cart.setPaymentOnlineId(chargeID);
                checkOutSuccess = orderDAO.createOrder(cart);
                if (checkOutSuccess) {
                    //4. Remove CART 
                    session.removeAttribute("CART");
                }
            }
        } catch (SQLException ex) {
            checkOutSuccess = false;
            log("CheckoutServlet-SQLException " + ex.getMessage());
        } catch (Exception ex) {
            checkOutSuccess = false;
            log("CheckoutServlet-Exception " + ex.getMessage());
        } finally {
            if (checkOutSuccess) {
                response.sendRedirect(request.getContextPath() + "/search.jsp?CHECKOUT_SUCCESS=" + "Your order has been created");
            } else {
//                request.setAttribute("ERROR_SUMMARY", "Error occur");
                request.getRequestDispatcher("viewCart.jsp").forward(request, response);
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
