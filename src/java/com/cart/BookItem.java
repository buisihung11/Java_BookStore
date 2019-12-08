/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cart;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class BookItem implements Serializable {

    public String bookId;
    public String bookName;
    public int quantity;
    public float price;
    public float amountItem;

    public BookItem(String bookId, String bookName, int quantity, float price) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.quantity = quantity;
        this.price = price;
    }

    public BookItem() {
    }

    public float getAmountItem() {
        return amountItem;
    }

    public void setAmountItem(float amountItem) {
        this.amountItem = amountItem;
    }
    
    

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getAmount() {
        return price * quantity;
    }

}
