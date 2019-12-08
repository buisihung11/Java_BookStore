/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 *
 * @author Admin
 */
public class HashPasswordUtil {

    private static final String HASH_TYPE = "SHA-256";
    private static byte[] createRandomSalt() {
        SecureRandom random = new SecureRandom();
        
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String passwordToHash) {

        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance(HASH_TYPE);
//            md.update(createRandomSalt());
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
    
    public static void main(String[] args) {
        String password = "admin";
        
        System.out.println(hashPassword(password).length());
    }
    //TO VALIDATION 
    //1. GET SALT OF THAT USERNAME FROM DB (EACH USER WILL HAS DIFFERENT SALT (RANDOM))
    //2. HASH THE RETRIVED PASSWORD WITH SALT FROM DB
    //3. CHECK THE HASHED PASSWORD WITH PASSWORD IN DB
}
