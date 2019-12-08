/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.filter;

import com.userDAO.UserDTO;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
@MultipartConfig
public class DispatchFilter implements Filter {

    private static final boolean debug = true;
    private static HashMap<String, String> GUEST_RESOURCE = new HashMap<>();
    private static HashMap<String, String> CUSTOMER_RESOURCE = new HashMap<>();
    private static HashMap<String, String> ADMIN_RESOURCE = new HashMap<>();

    private FilterConfig filterConfig = null;

    public DispatchFilter() {
        loadResourceMap();
    }

    private static void loadResourceMap() {
        GUEST_RESOURCE.put("login.jsp", "login.jsp");
        GUEST_RESOURCE.put("", "search.jsp");
        GUEST_RESOURCE.put("search.jsp", "search.jsp");
        GUEST_RESOURCE.put("Login", "LoginServlet");
        GUEST_RESOURCE.put("Logout", "LogoutServlet");
        GUEST_RESOURCE.put("Search", "SearchServlet");

        
        CUSTOMER_RESOURCE.put("viewCart.jsp", "viewCart.jsp");
        CUSTOMER_RESOURCE.put("checkout.jsp", "checkout.jsp");
        CUSTOMER_RESOURCE.put("shoppingHistory.jsp", "shoppingHistory.jsp");
        CUSTOMER_RESOURCE.put("AddToCart", "AddToCartServlet");
        CUSTOMER_RESOURCE.put("RemoveFromCart", "RemoveFromCartServlet");
        CUSTOMER_RESOURCE.put("UpdateItemInCart", "UpdateItemInCartServlet");
        CUSTOMER_RESOURCE.put("CheckOutPage", "CheckOutPageServlet");
        CUSTOMER_RESOURCE.put("CheckOut", "CheckoutServlet");
        CUSTOMER_RESOURCE.put("SearchHistory", "SearchHistoryServlet");
        CUSTOMER_RESOURCE.put("CheckoutOnline", "CheckoutOnlineServlet");
        

        ADMIN_RESOURCE.put("addBook.jsp", "addBook.jsp");
        ADMIN_RESOURCE.put("addUser.jsp", "addUser.jsp");
        ADMIN_RESOURCE.put("addDiscount.jsp", "addDiscount.jsp");
        ADMIN_RESOURCE.put("AddBook", "AddBookServlet");
        ADMIN_RESOURCE.put("AddUser", "AddUserServlet");
        ADMIN_RESOURCE.put("DeleteBook", "DeleteBookServlet");
        ADMIN_RESOURCE.put("UpdateBookPage", "UpdateBookPageServlet");
        ADMIN_RESOURCE.put("UpdateBook", "UpdateBookServlet");
        ADMIN_RESOURCE.put("AddDiscount", "AddDiscountServlet");

    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
//            log("DispatchFilter:DoBeforeProcessing");
        }

        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log items on the request object,
        // such as the parameters.
        /*
	for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    String values[] = request.getParameterValues(name);
	    int n = values.length;
	    StringBuffer buf = new StringBuffer();
	    buf.append(name);
	    buf.append("=");
	    for(int i=0; i < n; i++) {
	        buf.append(values[i]);
	        if (i < n-1)
	            buf.append(",");
	    }
	    log(buf.toString());
	}
         */
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
//            log("DispatchFilter:DoAfterProcessing");
        }

        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
        /*
	for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    Object value = request.getAttribute(name);
	    log("attribute: " + name + "=" + value.toString());

	}
         */
        // For example, a filter might append something to the response.
        /*
	PrintWriter respOut = new PrintWriter(response.getWriter());
	respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        if (debug) {
//            log("DispatchFilter:doFilter()");
        }

        doBeforeProcessing(request, response);

        Throwable problem = null;
        try {

            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            String requestUrl = req.getRequestURI()
                    .substring(req.getRequestURI().lastIndexOf("/") + 1);
//            System.out.println("[FILTER_URL] : " + requestUrl);
          
            
            
            if (GUEST_RESOURCE.containsKey(requestUrl)) {
                req.getRequestDispatcher(GUEST_RESOURCE.get(requestUrl))
                        .forward(req, res);
            } else if (CUSTOMER_RESOURCE.containsKey(requestUrl)) {
                //check logged in

                HttpSession session = req.getSession(false);

                if (session == null || session.getAttribute("USER") == null) {
                    res.sendRedirect(req.getContextPath() + "/login.jsp");
                } else {
                    UserDTO currentUser = (UserDTO) session.getAttribute("USER");

                    if (!currentUser.isIsAdmin()) {
                        req.getRequestDispatcher(CUSTOMER_RESOURCE.get(requestUrl)).forward(req, res);
                    } else {
                        res.sendError(res.SC_FORBIDDEN);
                    }

                }

            } else if (ADMIN_RESOURCE.containsKey(requestUrl)) {
                //check logged in

                HttpSession session = req.getSession(false);

                if (session == null || session.getAttribute("USER") == null) {
                    res.sendRedirect(req.getContextPath() + "/login.jsp");
                } else {
                    UserDTO currentUser = (UserDTO) session.getAttribute("USER");

                    if (currentUser.isIsAdmin()) {
                        req.getRequestDispatcher(ADMIN_RESOURCE.get(requestUrl)).forward(req, res);
                    } else {
                        res.sendError(res.SC_FORBIDDEN);
                    }

                }

            } else if(requestUrl.endsWith(".html") || requestUrl.endsWith(".js")
                    || requestUrl.endsWith(".css") || requestUrl.endsWith(".jpg")
                    || requestUrl.endsWith(".png") || requestUrl.endsWith(".jpg")){
                chain.doFilter(request, response);

            }else{
                //TRUONG HOP URL LA DUONG DAN CHINH XAC CUA URL THI TRA VE 404
                //TUC LA CHI CHO PHEP TRUY CAP THONG QUA CAC LABEL URL CHO TRUOC 
                res.sendError(res.SC_NOT_FOUND);
            }

        } catch (Throwable t) {
            // If an exception is thrown somewhere down the filter chain,
            // we still want to execute our after processing, and then
            // rethrow the problem after that.
            problem = t;
            t.printStackTrace();
        }

        doAfterProcessing(request, response);

        // If there was a problem, we want to rethrow it if it is
        // a known type, otherwise log it.
        if (problem != null) {
            if (problem instanceof ServletException) {
                throw (ServletException) problem;
            }
            if (problem instanceof IOException) {
                throw (IOException) problem;
            }
            sendProcessingError(problem, response);
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
//                log("DispatchFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("DispatchFilter()");
        }
        StringBuffer sb = new StringBuffer("DispatchFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
