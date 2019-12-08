/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servlet.admin;

import com.discountDAO.DiscountDAO;
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
@WebServlet(name = "AddDiscountServlet", urlPatterns = {"/admin/AddDiscountServlet"})
public class AddDiscountServlet extends HttpServlet {

    private static final String ADD_DISCOUNT_ERR = "addDiscount.jsp";
    private static final String ADD_DISCOUNT_SUCCESS = "search.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        boolean addSuccess = false;
        String responseUrl = ADD_DISCOUNT_ERR;
        final DiscountDAO discountDAO = new DiscountDAO();
        try {
            String discountCode = request.getParameter("discountCode");
            if (discountCode == null || discountCode.trim().isEmpty()) {
                request.setAttribute("DISCOUNT_CODE_ERR", "Please add discountCode");
                throw new Exception("DISCOUNT_CODE_ERR");
            }
            float discountValue = Float.parseFloat(request.getParameter("discountValue"));

            addSuccess = discountDAO.addDiscount(discountCode, discountValue / 100);

        } catch (NumberFormatException ex) {
            request.setAttribute("DISCOUNT_VALUE_ERR", "Invalid Discount Value");
            log("AddDiscountServlet-NumberFormatException " + ex.getMessage());
        } catch (NamingException ex) {
            log("AddDiscountServlet-NamingException " + ex.getMessage());
        } catch (SQLException ex) {
//            Logger.getLogger(AddDiscountServlet.class.getName()).log(Level.SEVERE, null, ex);
            log("AddDiscountServlet-SQLException " + ex.getMessage());
            if (ex.getMessage().contains("PK_tbl_Discount")) {
                request.setAttribute("DISCOUNT_CODE_DUPLICATE_ERR", "That discountcode has existed.");
            }

        } catch (Exception ex) {
//            Logger.getLogger(AddDiscountServlet.class.getName()).log(Level.SEVERE, null, ex);
            log("AddDiscountServlet-Exception " + ex.getMessage());
        } finally {
            if (addSuccess) {
                responseUrl = ADD_DISCOUNT_SUCCESS;
                responseUrl += "?ADD_DISCOUNT_SUCCESS=" + "Add Discount Successfully";
                response.sendRedirect(request.getContextPath() + "/" + responseUrl);
            } else {
                request.getRequestDispatcher(responseUrl).forward(request, response);
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
