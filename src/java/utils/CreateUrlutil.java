/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class CreateUrlutil {
    public static String createUrl(String root,Map<String,String> params){
        String finalUrl = root + "?";
        
        ArrayList<String> paramsString = new ArrayList<>();
        
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String paramStr = null;
            if(entry.getValue() != null && entry.getValue().trim().length() > 0){
                paramStr  = entry.getKey() + "=" + entry.getValue();
                paramsString.add(paramStr);
            }
        }
        
        finalUrl += String.join("&", paramsString);
        
        return finalUrl;
    }
    
    
    public static void main(String[] args) {
        Map<String,String> params = new HashMap<>();
        
        params.put("key1", "value1");
        params.put("key2", "value2");
        params.put("key3", null);
        
        System.out.println(createUrl("Hung", params));
        
    }
    
}
