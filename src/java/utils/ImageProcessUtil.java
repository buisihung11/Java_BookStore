/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javax.servlet.http.Part;

/**
 *
 * @author Admin
 */
public class ImageProcessUtil {

    public static String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.lastIndexOf('\\') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
