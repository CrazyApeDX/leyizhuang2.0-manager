package com.ynyes.lyz.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	public static String md5(String plainText,int bit) {
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            if(bit == 32)
            	result = buf.toString();  //md5 32bit
            else if(bit == 16)
            	result = buf.toString().substring(8, 24);//md5 16bit
            else throw new NoSuchAlgorithmException();
            
          } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }
public static void main(String[] args) {
    String str="123";
   System.out.println(MD5.md5(str, 32));
}
}
