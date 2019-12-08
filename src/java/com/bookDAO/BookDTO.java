/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bookDAO;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class BookDTO implements Serializable{
    private String bookId;
    private String title;
    private String author;
    private String importDate;
    private String imagePath;
    private String description;
    private String categoryID;
    private float price;
    private int quantity;
    private boolean status;

    public BookDTO() {
    }

    
    
    
    public BookDTO(String bookId, String title, String author, String importDate, String imagePath, String description, String categoryID, float price, int quantity, boolean status) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.importDate = importDate;
        this.imagePath = imagePath;
        this.description = description;
        this.categoryID = categoryID;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
    
    @Override
    public String toString() {
        return "BookDTO{" + "bookId=" + bookId + ", title=" + title + ", author=" + author + ", importDate=" + importDate + ", price=" + price + ", quantity=" + quantity + ", status=" + status + '}';
    }

    
    
    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImportDate() {
        return importDate;
    }

    public void setImportDate(String importDate) {
        this.importDate = importDate;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    
    
}
