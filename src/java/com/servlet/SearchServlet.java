package com.servlet;

import com.bookDAO.BookDAO;
import com.bookDAO.BookDTO;
import com.userDAO.UserDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Admin
 *
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {

    private final static String RESULT_PAGE = "search.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String searchBy = request.getParameter("searchBy");

        String searchTitle = null;
        String searchCategoryId = null;

        Float searchInRangeFrom = null;
        Float searchInRangeTo = null;

        List<BookDTO> results = null;
        final BookDAO dao = new BookDAO();

        UserDTO currentUser = (UserDTO) request.getSession().getAttribute("USER");

        boolean isAdmin = false;
        
        if(currentUser != null)
            isAdmin = currentUser.isIsAdmin();
        
        try {
            if (searchBy.equals("searchByName")) {
                //get name value
                searchTitle = request.getParameter("searchTitle");
                results = dao.searchBookByName(searchTitle, isAdmin);

            } else if (searchBy.equals("searchInRange")) {
                //get from and to value
                searchInRangeFrom = Float.parseFloat(request.getParameter("fromPrice"));
                searchInRangeTo = Float.parseFloat(request.getParameter("toPrice"));
                results = dao.searchBookInRange(searchInRangeFrom, searchInRangeTo, isAdmin);

            } else if (searchBy.equals("searchByCategory")) {
                //get category
                searchCategoryId = request.getParameter("searchCategoryId");
                results = dao.searchBookByCategory(searchCategoryId, isAdmin);

            }
        } catch (NamingException ex) {
            log("SearchServlet-NamingException " +ex.getMessage());
        } catch (SQLException ex) {
            log("SearchServlet-SQLException " +ex.getMessage());
        } finally {
            request.setAttribute("SEARCH_RESULT", results);
            request.getRequestDispatcher(RESULT_PAGE).forward(request, response);
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
