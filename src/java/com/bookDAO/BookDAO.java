/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bookDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import utils.DBUtils;

/**
 *
 * @author Admin
 */
public class BookDAO {

    public boolean updateBook(String bookId,
            String title,
            String author,
            String imagePath,
            String description,
            float price,
            int quantity, String categoryID, boolean isActive) throws SQLException, NamingException {
        boolean result = false;

        Connection c = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {

            c = DBUtils.makeConnection();
            String sql = "UPDATE tbl_Book SET title = ? ,author = ? ,importDate = ? ,price = ? ,quantity = ?  "
                    + ",imagePath = ? ,description = ? ,categoryID = ? "
                    + ",status = ?"
                    + " WHERE bookId = ? ";

            stm = c.prepareCall(sql);

            stm.setString(1, title);
            stm.setString(2, author);
            java.util.Date currentDate = new java.util.Date();
            stm.setDate(3, new Date(currentDate.getTime()));
            stm.setFloat(4, price);
            stm.setInt(5, quantity);
            stm.setString(6, imagePath);
            stm.setString(7, description);
            stm.setString(8, categoryID);
            stm.setBoolean(9, isActive);
            stm.setString(10, bookId);

            result = stm.executeUpdate() > 0;

        } finally {
            if (stm != null) {
                stm.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (c != null) {
                c.close();
            }

        }

        return result;
    }

    public boolean addBook(String bookId,
            String title,
            String author,
            String imagePath,
            String description,
            float price,
            int quantity, String categoryID) throws SQLException, NamingException {
        boolean result = false;

        Connection c = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {

            c = DBUtils.makeConnection();
            String sql = "INSERT INTO tbl_Book (bookId,title,author,importDate,price,quantity,status,imagePath,description,categoryID) "
                    + " VALUES (? ,? ,?, ?,?,?, ?,? , ? ,?)";

            stm = c.prepareCall(sql);

            stm.setString(1, bookId);
            stm.setString(2, title);
            stm.setString(3, author);
            java.util.Date currentDate = new java.util.Date();
            stm.setDate(4, new Date(currentDate.getTime()));
            stm.setFloat(5, price);
            stm.setInt(6, quantity);
            stm.setBoolean(7, true);
            stm.setString(8, imagePath);
            stm.setString(9, description);
            stm.setString(10, categoryID);

            result = stm.executeUpdate() > 0;

        } finally {
            if (stm != null) {
                stm.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (c != null) {
                c.close();
            }

        }

        return result;
    }

    public List<BookDTO> searchBookByCategory(String categoryId, boolean isAdminSearch) throws NamingException, SQLException {
        List<BookDTO> results = null;

        Connection c = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {

            c = DBUtils.makeConnection();
            String sql = null;
            if (isAdminSearch) {
                sql = "SELECT bookId,title,author,importDate,price,quantity,status,imagePath,description,categoryID "
                        + " FROM tbl_Book "
                        + " WHERE categoryId = ?";
                stm = c.prepareCall(sql);
                stm.setString(1, categoryId);
            } else {
                sql = "SELECT bookId,title,author,importDate,price,quantity,status,imagePath,description,categoryID "
                        + " FROM tbl_Book "
                        + " WHERE categoryId = ? and quantity > ? and status = ?";
                stm = c.prepareCall(sql);
                stm.setString(1, categoryId);
                stm.setInt(2, 0);
                stm.setBoolean(3, true);
            }

            rs = stm.executeQuery();

            while (rs.next()) {
                if (results == null) {
                    results = new ArrayList<>();
                }

                String bookId = rs.getString("bookId");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String importDate = rs.getString("importDate");
                String imagePath = rs.getString("imagePath");
                String description = rs.getString("description");
                String categoryID = rs.getString("categoryID");
                float price = rs.getFloat("price");
                int quantity = rs.getInt("quantity");
                boolean status = rs.getBoolean("status");

                BookDTO book = new BookDTO(bookId, title, author, importDate, imagePath, description, categoryID, price, quantity, status);
                results.add(book);
            }

        } finally {
            if (stm != null) {
                stm.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (c != null) {
                c.close();
            }

        }

        return results;
    }

    public List<BookDTO> searchBookByName(String searchTitle, boolean isAdminSearch) throws NamingException, SQLException {
        List<BookDTO> results = null;

        Connection c = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {

            c = DBUtils.makeConnection();

            String sql = null;
            if (isAdminSearch) {
                sql = "SELECT bookId,title,author,importDate,price,quantity,status,imagePath,description,categoryID "
                        + " FROM tbl_Book "
                        + " WHERE title LIKE ? ";
                stm = c.prepareCall(sql);
                stm.setString(1, "%" + searchTitle + "%");

            } else {
                sql = "SELECT bookId,title,author,importDate,price,quantity,status,imagePath,description,categoryID "
                        + " FROM tbl_Book "
                        + " WHERE title LIKE ? and quantity > ? and status = ?";
                stm = c.prepareCall(sql);
                stm.setString(1, "%" + searchTitle + "%");
                stm.setInt(2, 0);
                stm.setBoolean(3, true);
            }

            rs = stm.executeQuery();

            while (rs.next()) {
                if (results == null) {
                    results = new ArrayList<>();
                }

                String bookId = rs.getString("bookId");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String importDate = rs.getString("importDate");
                String imagePath = rs.getString("imagePath");
                String description = rs.getString("description");
                String categoryID = rs.getString("categoryID");
                float price = rs.getFloat("price");
                int quantity = rs.getInt("quantity");
                boolean status = rs.getBoolean("status");

                BookDTO book = new BookDTO(bookId, title, author, importDate, imagePath, description, categoryID, price, quantity, status);
                results.add(book);
            }

        } finally {
            if (stm != null) {
                stm.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (c != null) {
                c.close();
            }

        }

        return results;
    }

    public List<BookDTO> searchBookInRange(float fromPrice, float toPrice, boolean isAdminSearch) throws NamingException, SQLException {
        List<BookDTO> results = null;

        Connection c = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {

            c = DBUtils.makeConnection();

            String sql = null;

            if (isAdminSearch) {
                sql = "SELECT bookId,title,author,importDate,price,quantity,status,imagePath,description,categoryID "
                        + " FROM tbl_Book "
                        + " WHERE price between ? and ?   ";
                stm = c.prepareCall(sql);
                stm.setFloat(1, fromPrice);
                stm.setFloat(2, toPrice);
            } else {
                sql = "SELECT bookId,title,author,importDate,price,quantity,status,imagePath,description,categoryID "
                        + " FROM tbl_Book "
                        + " WHERE status = ? and price between ? and ?  and quantity > 0  ";
                stm = c.prepareCall(sql);
                stm.setBoolean(1, true);
                stm.setFloat(2, fromPrice);
                stm.setFloat(3, toPrice);

            }

            rs = stm.executeQuery();

            while (rs.next()) {
                if (results == null) {
                    results = new ArrayList<>();
                }

                String bookId = rs.getString("bookId");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String importDate = rs.getString("importDate");
                String imagePath = rs.getString("imagePath");
                String description = rs.getString("description");
                String categoryID = rs.getString("categoryID");
                float price = rs.getFloat("price");
                int quantity = rs.getInt("quantity");
//                boolean status;

                BookDTO book = new BookDTO(bookId, title, author, importDate, imagePath, description, categoryID, price, quantity, true);
                results.add(book);
            }

        } finally {
            if (stm != null) {
                stm.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (c != null) {
                c.close();
            }

        }

        return results;
    }

    public boolean deleteBookById(String bookId) throws NamingException, SQLException {

        boolean deleteSuccess = false;

        Connection c = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {

            c = DBUtils.makeConnection();

            String sql = "UPDATE tbl_Book SET "
                    + "status = ?"
                    + " WHERE bookId = ? ";

            stm = c.prepareCall(sql);
            stm.setBoolean(1, false);
            stm.setString(2, bookId);

            deleteSuccess = stm.executeUpdate() > 0;

        } finally {
            if (stm != null) {
                stm.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (c != null) {
                c.close();
            }

        }
        return deleteSuccess;
    }

    public BookDTO getBookById(String bookId) throws NamingException, SQLException {

        BookDTO book = null;

        Connection c = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {

            c = DBUtils.makeConnection();

            String sql = "SELECT bookId,title,author,importDate,price,quantity,status,imagePath,description,categoryID "
                    + " FROM tbl_Book "
                    //                    + " WHERE status = ? and BookID = ?";
                    + " WHERE BookID = ?";

            stm = c.prepareCall(sql);
//            stm.setBoolean(1, true);
            stm.setString(1, bookId);

            rs = stm.executeQuery();

            while (rs.next()) {

                String title = rs.getString("title");
                String author = rs.getString("author");
                String importDate = rs.getString("importDate");
                String imagePath = rs.getString("imagePath");
                String description = rs.getString("description");
                String categoryID = rs.getString("categoryID");
                float price = rs.getFloat("price");
                int quantity = rs.getInt("quantity");
                boolean status = rs.getBoolean("status");

                book = new BookDTO(bookId, title, author, importDate, imagePath, description, categoryID, price, quantity, status);
            }

        } finally {
            if (stm != null) {
                stm.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (c != null) {
                c.close();
            }

        }
        return book;
    }

}
