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
public class BookErr implements Serializable{
    private String bookIdErr;
    private String bookIdDuplicateErr;
    private String titleErr;
    private String authorErr;
    private String imagePathErr;
    private String descriptionErr;
    private String categoryIDErr;
    private String priceErr;
    private String quantityErr;

    
    
    public String getBookIdErr() {
        return bookIdErr;
    }

    public void setBookIdErr(String bookIdErr) {
        this.bookIdErr = bookIdErr;
    }

    public String getBookIdDuplicateErr() {
        return bookIdDuplicateErr;
    }

    public void setBookIdDuplicateErr(String bookIdDuplicateErr) {
        this.bookIdDuplicateErr = bookIdDuplicateErr;
    }

    public String getTitleErr() {
        return titleErr;
    }

    public void setTitleErr(String titleErr) {
        this.titleErr = titleErr;
    }

    public String getAuthorErr() {
        return authorErr;
    }

    public void setAuthorErr(String authorErr) {
        this.authorErr = authorErr;
    }

    public String getImagePathErr() {
        return imagePathErr;
    }

    public void setImagePathErr(String imagePathErr) {
        this.imagePathErr = imagePathErr;
    }

    public String getDescriptionErr() {
        return descriptionErr;
    }

    public void setDescriptionErr(String descriptionErr) {
        this.descriptionErr = descriptionErr;
    }

    public String getCategoryIDErr() {
        return categoryIDErr;
    }

    public void setCategoryIDErr(String categoryIDErr) {
        this.categoryIDErr = categoryIDErr;
    }

    public String getPriceErr() {
        return priceErr;
    }

    public void setPriceErr(String priceErr) {
        this.priceErr = priceErr;
    }

    public String getQuantityErr() {
        return quantityErr;
    }

    public void setQuantityErr(String quantityErr) {
        this.quantityErr = quantityErr;
    }

    
    
    
    
}
