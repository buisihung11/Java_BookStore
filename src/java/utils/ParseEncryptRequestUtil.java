/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

/**
 *
 * @author Admin
 */
public class ParseEncryptRequestUtil {

    public static Hashtable getParams(HttpServletRequest req) {
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List items = null;
        Hashtable params = new Hashtable();
        try {
            items = upload.parseRequest(new ServletRequestContext(req));
        } catch (FileUploadException ex) {
            ex.printStackTrace();
        }

        Iterator iter = items.iterator();

        while (iter.hasNext()) {
            FileItem item = (FileItem) iter.next();
            if (item.isFormField()) {
                params.put(item.getFieldName(), item.getString());
            } else {
                //for image
                String itemName = item.getName();
                String fileName = itemName.substring(itemName.lastIndexOf("\\") + 1);
                params.put(item.getFieldName(), fileName);
            }
        }
        return params;
    }
}
