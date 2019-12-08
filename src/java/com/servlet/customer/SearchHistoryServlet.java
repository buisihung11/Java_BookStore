/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servlet.customer;

import com.cart.OrderCart;
import com.orderDAO.OrderDAO;
import com.userDAO.UserDTO;
import java.io.IOException;
import java.sql.SQLException;
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
@WebServlet(name = "SearchHistoryServlet", urlPatterns = {"/customer/SearchHistoryServlet"})
public class SearchHistoryServlet extends HttpServlet {

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
        String searchBy = request.getParameter("searchBy");
        Map<Integer, OrderCart> orders = null;
        OrderDAO orderDAO = new OrderDAO();

        HttpSession session = request.getSession(false);
        UserDTO currentUser = (UserDTO) session.getAttribute("USER");
        String username = currentUser.getUsername();

        try {
            if (searchBy.equals("bookTitle")) {
                String bookTitle = request.getParameter("bookTitle");
                if (bookTitle == null) {
                    bookTitle = "";
                }

                orders = orderDAO.getOrderByBookTitle(bookTitle, username);

            } else if (searchBy.equals("date")) {
                String orderDate = request.getParameter("orderDate");

                //convert string date from mm/dd/yyy format to yyyy-[m]m-[d]d hh:mm:ss[.f...]
//                java.util.Date date = new SimpleDateFormat("mm/dd/yyyy").parse(orderDate);
                
//                SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                String formatedDate = sqlFormat.format(date);
                orders = orderDAO.getOrderByDate(orderDate, username);
            }
        } catch (NamingException ex) {
            log("SearchHistoryServlet-NamingException " + ex.getMessage());
        } catch (SQLException ex) {
            log("SearchHistoryServlet-SQLException " + ex.getMessage());
        } finally {
            request.setAttribute("SEARCH_RESULTS", orders);
            request.getRequestDispatcher("shoppingHistory.jsp").forward(request, response);

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
