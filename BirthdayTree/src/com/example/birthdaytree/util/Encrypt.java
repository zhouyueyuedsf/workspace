package com.example.birthdaytree.util;

import java.security.MessageDigest;

public class Encrypt {
	 public static String MD5(String s) {  
	        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};         
	        try {  
	            byte[] btInput = s.getBytes();  
	            // 获得MD5摘要算法的 MessageDigest 对象  
	            MessageDigest mdInst = MessageDigest.getInstance("MD5");  
	            // 使用指定的字节更新摘要  
	            mdInst.update(btInput);  
	            // 获得密文  
	            byte[] md = mdInst.digest();  
	            // 把密文转换成十六进制的字符串形式  
	            int j = md.length;  
	            char str[] = new char[j * 2];  
	            int k = 0;  
	            for (int i = 0; i < j; i++) {  
	                byte byte0 = md[i];  
	                str[k++] = hexDigits[byte0 >>> 4 & 0xf];  
	                str[k++] = hexDigits[byte0 & 0xf];  
	            } 
	            //这里可以优化一下,取中间的十位数也可以
	            String str1 = new String(str).substring(0 , 10);
	            
	            return str1;  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	            return null;  
	        }  
	    }  
	 public static String SHA(String s) {  
	        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};         
	        try {  
	            byte[] btInput = s.getBytes();  
	            // 获得SHA摘要算法的 MessageDigest 对象  
	            MessageDigest sha = MessageDigest.getInstance("SHA");  
	            // 使用指定的字节更新摘要  
	            sha.update(btInput);  
	            // 获得密文  
	            byte[] md = sha.digest();  
	            // 把密文转换成十六进制的字符串形式  
	            int j = md.length;  
	            char str[] = new char[j * 2];  
	            int k = 0;  
	            for (int i = 0; i < j; i++) {  
	                byte byte0 = md[i];  
	                str[k++] = hexDigits[byte0 >>> 4 & 0xf];  
	                str[k++] = hexDigits[byte0 & 0xf];  
	            }  
	            return new String(str);  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	            return null;  
	        }  
	    }  
	      
}
