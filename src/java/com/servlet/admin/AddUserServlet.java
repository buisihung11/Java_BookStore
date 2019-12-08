/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servlet.admin;

import com.userDAO.UserDAO;
import com.userDAO.UserErr;
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
@WebServlet(name = "AddUserServlet", urlPatterns = {"/admin/AddUserServlet"})
public class AddUserServlet extends HttpServlet {

    private static final String ADD_USER_SUCCESS = "/search.jsp";
    private static final String ADD_USER_FAILED = "addUser.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        final UserDAO dao = new UserDAO();
        UserErr err = new UserErr();
        String finalUrl = ADD_USER_FAILED;
        String username = request.getParameter("username");
        String phone = request.getParameter("phone");
        String fullName = request.getParameter("fullName");
        String address = request.getParameter("address");
        String password = request.getParameter("password");
        String passwordConfirm = request.getParameter("passwordConfirm");

        boolean isErr = false;

        if (isEmptyString(username)) {
            err.setUsernameErr("Vui long nhap username");
            isErr = isErr || true;
        }
        if (isEmptyString(phone)) {
            err.setPhoneErr("Vui long nhap phone");
            isErr = isErr || true;
        }
        if (isEmptyString(fullName)) {
            err.setFullNameErr("Vui long nhap name");
            isErr = isErr || true;
        }

        if (isEmptyString(password)) {
            err.setPasswordErr("Vui long nhap password");
            isErr = isErr || true;
        } else {
            if (isEmptyString(passwordConfirm)) {
                err.setConfirmPasswordErr("Vui long confrim password");
                isErr = isErr || true;
            }
            if (!password.equals(passwordConfirm)) {
                err.setConfirmPasswordErr("Khong khop password");
                isErr = isErr || true;
            }
        }

        try {
            if (isErr) {
                finalUrl = ADD_USER_FAILED;
            } else {
                isErr = !dao.addUser(username, fullName, password, address, phone);
            }
        } catch (NamingException ex) {
            log("AddUserServlet-NamingException " +ex.getMessage());
        } catch (SQLException ex) {
            log("AddUserServlet-SQLException " +ex.getMessage());
            if(ex.getMessage().contains("PK_tbl_User")){
                isErr = true;
                err.setDuplicateUsernameErr("That username was existed!");
            }
        } finally {
            if (isErr) {
                request.setAttribute("ADD_USER_ERR", err);
                request.getRequestDispatcher(finalUrl).forward(request, response);
            } else {
                finalUrl = ADD_USER_SUCCESS;
//                request.setAttribute("ADD_USER_SUCCESS", "Add user successfully");
//                request.getRequestDispatcher(finalUrl).forward(request, response);

                finalUrl += "?ADD_USER_SUCCESS=Add user successfully";
                response.sendRedirect(request.getContextPath()+finalUrl);
            }
        }

    }

    private static boolean isEmptyString(String text) {
        return text == null || text.trim().length() == 0;
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
