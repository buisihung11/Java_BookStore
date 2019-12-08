/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userDAO;

/**
 *
 * @author Admin
 */
public class UserDTO {
    private String username;
    private String fullName;
    private String pasword;
    private String address;
    private String phone;
    private boolean isAdmin;

    public UserDTO(String username, String fullName, String address, String phone, boolean isAdmin) {
        this.username = username;
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
        this.isAdmin = isAdmin;
    }

    public UserDTO(String username, String fullName, String pasword, String address, String phone, boolean isAdmin) {
        this.username = username;
        this.fullName = fullName;
        this.pasword = pasword;
        this.address = address;
        this.phone = phone;
        this.isAdmin = isAdmin;
    }

    
    
    public String getPasword() {
        return pasword;
    }

    public void setPasword(String pasword) {
        this.pasword = pasword;
    }

    
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    
    
    
    
}
