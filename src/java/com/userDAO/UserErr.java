/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userDAO;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class UserErr implements Serializable {

    String usernameErr;
    String duplicateUsernameErr;
    String passwordErr;
    String confirmPasswordErr;
    String fullNameErr;
    String addressErr;
    String phoneErr;

    public String getUsernameErr() {
        return usernameErr;
    }

    public void setUsernameErr(String usernameErr) {
        this.usernameErr = usernameErr;
    }

    public String getDuplicateUsernameErr() {
        return duplicateUsernameErr;
    }

    public void setDuplicateUsernameErr(String duplicateUsernameErr) {
        this.duplicateUsernameErr = duplicateUsernameErr;
    }

    public String getPasswordErr() {
        return passwordErr;
    }

    public void setPasswordErr(String passwordErr) {
        this.passwordErr = passwordErr;
    }

    public String getConfirmPasswordErr() {
        return confirmPasswordErr;
    }

    public void setConfirmPasswordErr(String confirmPasswordErr) {
        this.confirmPasswordErr = confirmPasswordErr;
    }

    public String getFullNameErr() {
        return fullNameErr;
    }

    public void setFullNameErr(String fullNameErr) {
        this.fullNameErr = fullNameErr;
    }

    public String getAddressErr() {
        return addressErr;
    }

    public void setAddressErr(String addressErr) {
        this.addressErr = addressErr;
    }

    public String getPhoneErr() {
        return phoneErr;
    }

    public void setPhoneErr(String phoneErr) {
        this.phoneErr = phoneErr;
    }
    
    
    
    
}
