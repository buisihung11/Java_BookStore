/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.orderDAO;

import com.cart.BookItem;
import com.cart.OrderCart;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import static java.util.stream.Collectors.toMap;
import javax.naming.NamingException;
import utils.DBUtils;

/**
 *
 * @author Admin
 */
public class OrderDAO {

    public boolean createOrder(OrderCart cart) throws NamingException, SQLException {

        Connection c = null;
        PreparedStatement stm = null;
        boolean success = false;
        try {
            c = DBUtils.makeConnection();
            c.setAutoCommit(false);

            //1. Insert into Orders firstly
            String orderSql = "INSERT INTO tbl_Order "
                    + "(CreatedDate, Username, Total, DiscountCode,recipientPhone, recipientAddress , cashedType , paymentOnlineId ) "
                    + " VALUES (?,?,?,?,?,? , ?, ?)";

            stm = c.prepareStatement(orderSql);

//            java.sql.Timestamp currentDate = new java.sql.Timestamp(new java.util.Date().getTime());
//            stm.setTimestamp(1, currentDate);
            java.util.Date currentDate = new java.util.Date();
            stm.setDate(1, new Date(currentDate.getTime()));
            stm.setString(2, cart.getUsername());
            stm.setFloat(3, cart.getTotalWithDiscount());
            stm.setString(4, cart.getDiscountCode());
            stm.setString(5, cart.getRecipientPhone());
            stm.setString(6, cart.getRecipientAddress());
            stm.setString(7, cart.getCashedType());
            stm.setString(8, cart.getPaymentOnlineId());

            stm.executeUpdate();

            //1.1get the last orderID
            String getOrderIDSql = "Select Max( OrderId )  as orderId"
                    + " from tbl_Order ";

            Integer orderId = null;
            stm = c.prepareCall(getOrderIDSql);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                orderId = rs.getInt("orderId");
            }
            //2. Update tbl_discount
            if (cart.getDiscountCode() != null) {
                String updateDiscountSql = " UPDATE tbl_Discount "
                        + " SET Username = ? , isUsed = ? "
                        + " WHERE DiscountCode = ?";

                stm = c.prepareCall(updateDiscountSql);
                stm.setString(1, cart.getUsername());
                stm.setBoolean(2, true);
                stm.setString(3, cart.getDiscountCode());
                stm.executeUpdate();
            }

            //3. Update quantity of product table
            String productSql = "UPDATE tbl_Book"
                    + " SET quantity = quantity - ? "
                    + "WHERE BookId = ?";

            stm = c.prepareStatement(productSql);
            for (Map.Entry item : cart.getItems().entrySet()) {
                String bookId = (String) item.getKey();
                BookItem book = (BookItem) item.getValue();
                stm.setInt(1, book.getQuantity());
                stm.setString(2, bookId);
                stm.executeUpdate();
            }

            //3.Insert to OrderDetail
            String orderDetailSql = "  INSERT INTO tbl_OrerDetail "
                    + " (BookID,Quantity,Amount,OrderID) "
                    + "VALUES (?,?,?,?)";

            stm = c.prepareStatement(orderDetailSql);
            for (Map.Entry item : cart.getItems().entrySet()) {
                String bookId = (String) item.getKey();
                BookItem book = (BookItem) item.getValue();
                int quantity = book.getQuantity();

                stm.setString(1, bookId);
                stm.setInt(2, quantity);
                stm.setFloat(3, book.getAmount());
                stm.setInt(4, orderId);
                stm.executeUpdate();
            }

            c.commit();
            success = true;
        } catch (SQLException ex) {
            c.rollback();
            ex.printStackTrace();
            throw ex;
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (c != null) {
                c.close();
            }
        }

        return success;
    }

    public Map<Integer, OrderCart> getOrderByBookTitle(String bookTitle, String username) throws NamingException, SQLException {
        Map<Integer, OrderCart> orders = null;
        Connection c = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {

            c = DBUtils.makeConnection();
            String sql = "select o.OrderID, b.Title, b.Description, o.CreatedDate,o.Total,o.recipientAddress,o.recipientPhone , od.Quantity ,"
                    + "  od.Amount , od.BookId , o.cashedType , o.paymentOnlineId"
                    + "					 from tbl_Book b , tbl_Order o , tbl_OrerDetail od"
                    + "					 where  b.BookID = od.BookID and o.Username = ? and o.OrderID = od.OrderID and o.OrderID In ("
                    + "					 select DISTINCT  od.OrderID"
                    + "					 from tbl_OrerDetail od , tbl_Book b"
                    + "					 where b.Title like ? and b.BookID = od.BookID"
                    + "					 ) order by OrderID desc";

            stm = c.prepareCall(sql);
            stm.setString(2, "%" + bookTitle + "%");
            stm.setString(1, username);

            rs = stm.executeQuery();
            while (rs.next()) {
                if (orders == null) {
                    orders = new HashMap<>();
                }
                OrderCart order = null;
                int orderID = rs.getInt("OrderID");

                if (orders.containsKey(orderID)) {
                    order = orders.get(orderID);
                } else {
                    order = new OrderCart();

                    String recipientPhone = rs.getString("recipientPhone");
                    String recipientAddress = rs.getString("recipientAddress");
                    String createdDate = rs.getString("CreatedDate");
                    float orderTotal = rs.getFloat("Total");

                    String cashedType = rs.getString("cashedType");
                    String paymentOnlineId = rs.getString("paymentOnlineId");

                    order.setCashedType(cashedType);
                    order.setPaymentOnlineId(paymentOnlineId);
                    order.setOrderTotal(orderTotal);
                    order.setCreatedDate(createdDate);
                    order.setRecipientAddress(recipientAddress);
                    order.setRecipientPhone(recipientPhone);
                    order.setUsername(username);
                }

                String bookId = rs.getString("BookId");
                String bookName = rs.getString("Title");

                int buyQuantity = rs.getInt("Quantity");
                float amount = rs.getFloat("Amount");

                BookItem bookItem = new BookItem();

                bookItem.bookId = bookId;
                bookItem.bookName = bookName;
                bookItem.quantity = buyQuantity;
                bookItem.amountItem = amount;

                order.items.put(bookId, bookItem);
                orders.put(orderID, order);
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

        if (orders != null) {
            Map<Integer, OrderCart> descOrders = orders.entrySet()
                    .stream()
                    .sorted(Collections.reverseOrder(Map.Entry.comparingByKey()))
                    .collect(toMap(Map.Entry::getKey, Map.Entry::getValue,
                            (e1, e2) -> e2, LinkedHashMap::new));
            return descOrders;
        }
        return orders;
    }

    public Map<Integer, OrderCart> getOrderByDate(String orderDate, String username) throws NamingException, SQLException {
        Map<Integer, OrderCart> orders = null;
        Connection c = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {

            c = DBUtils.makeConnection();
            String sql = "select o.OrderID, b.Title, b.Description, o.CreatedDate,o.Total,o.recipientAddress,o.recipientPhone , od.Quantity , od.Amount , od.BookId"
                    + "					 from tbl_Book b , tbl_Order o , tbl_OrerDetail od"
                    + "					 where  b.BookID = od.BookID and o.Username = ? and o.OrderID = od.OrderID and o.CreatedDate = ?"
                    + " order by OrderID desc ";
            stm = c.prepareCall(sql);
            stm.setString(1, username);

//            stm.setTimestamp(2, java.sql.Timestamp.valueOf(orderDate));
            stm.setString(2, orderDate);

            rs = stm.executeQuery();
            while (rs.next()) {
                if (orders == null) {
                    orders = new HashMap<>();
                }
                OrderCart order = null;
                int orderID = rs.getInt("OrderID");

                if (orders.containsKey(orderID)) {
                    order = orders.get(orderID);
                } else {
                    order = new OrderCart();

                    String recipientPhone = rs.getString("recipientPhone");
                    String recipientAddress = rs.getString("recipientAddress");
                    String createdDate = rs.getString("CreatedDate");
                    float orderTotal = rs.getFloat("Total");
                    order.setOrderTotal(orderTotal);
                    order.setCreatedDate(createdDate);
                    order.setRecipientAddress(recipientAddress);
                    order.setRecipientPhone(recipientPhone);
                    order.setUsername(username);
                }

                String bookId = rs.getString("BookId");
                String bookName = rs.getString("Title");
                int buyQuantity = rs.getInt("Quantity");
                float amount = rs.getFloat("Amount");

                BookItem bookItem = new BookItem();

                bookItem.bookId = bookId;
                bookItem.bookName = bookName;
                bookItem.quantity = buyQuantity;
                bookItem.amountItem = amount;

                order.items.put(bookId, bookItem);
                orders.put(orderID, order);
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
        if (orders != null) {
            Map<Integer, OrderCart> descOrders = orders.entrySet()
                    .stream()
                    .sorted(Collections.reverseOrder(Map.Entry.comparingByKey()))
                    .collect(toMap(Map.Entry::getKey, Map.Entry::getValue,
                            (e1, e2) -> e2, LinkedHashMap::new));
            return descOrders;
        }
        return orders;
    }
}
