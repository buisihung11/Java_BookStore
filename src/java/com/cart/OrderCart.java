/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cart;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Admin
 */


public class OrderCart implements Serializable {
    public Map<String, BookItem> items;
    
    private String discountCode;
    private float discountValue = 0;
    private float orderTotal;
    private String recipientPhone;
    private String recipientAddress;
    private String createdDate;
    private String username; //logged user who made this order
    private String cashedType; 
    private String paymentOnlineId; //from api 
    
    public OrderCart() {
        items = new HashMap<>();
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        
        this.createdDate = createdDate;
    }
  
    
    
    public void addItems(String bookId, String bookName, int addQuantity, float price) {
        BookItem item = items.get(bookId);
        if (item == null) {
            item = new BookItem(bookId, bookName, addQuantity, price);
            items.put(bookId, item);
        } else {
            item.quantity += addQuantity;
            items.put(bookId, item);
        }
    }

    public void updateItem(String bookId, int quantity) {
        BookItem item = items.get(bookId);
        if (item != null) {
            item.quantity = quantity;
            items.put(bookId, item);
        }
    }

    public void removeItem(String bookId) {
        BookItem item = items.get(bookId);
        if (item != null) {
            items.remove(bookId);
        }
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public Map<String, BookItem> getItems() {
        return items;
    }

    public float getTotal() {
        float total = 0;

        for (BookItem book : items.values()) {
            total += book.getAmount();
        }
        
        
        return total;
    }

    public String getPaymentOnlineId() {
        return paymentOnlineId;
    }

    public void setPaymentOnlineId(String paymentOnlineId) {
        this.paymentOnlineId = paymentOnlineId;
    }
    
    
    
    public float getTotalWithDiscount() {
        float total = 0;

        for (BookItem book : items.values()) {
            total += book.getAmount();
        }
        
        total -= total * discountValue;
        
        return total;
    }

    public String getCashedType() {
        return cashedType;
    }

    public void setCashedType(String cashedType) {
        this.cashedType = cashedType;
    }

    
    
    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }

    public String getRecipientAddress() {
        return recipientAddress;
    }

    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(float discountValue) {
        this.discountValue = discountValue;
    }

    public float getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(float orderTotal) {
        this.orderTotal = orderTotal;
    }

    
    
}
