package com.lanrenyou.util;

import java.net.URLEncoder;

public class UrlEncoderUtil {
    private UrlEncoderUtil(){}
    public static String encode(String toBeEncoded,String charSet,String defaultReturnValue){
        String encodedString;
        try{
            encodedString = URLEncoder.encode(toBeEncoded,charSet);
        }catch(Exception e){
            System.err.println(String.format("Error when url-encode:%s with charset:%s",(toBeEncoded == null ? "" : toBeEncoded), (charSet == null ? "" : charSet)));
            e.printStackTrace();
            encodedString = defaultReturnValue;
        }
        return encodedString;
    }

    public static String encodeByUtf8(String toBeEncoded,String defaultReturnValue){
        return encode(toBeEncoded,"utf-8",defaultReturnValue);
    }

    public static String encodeByUtf8(String toBeEncoded){
        return encodeByUtf8(toBeEncoded,null);
    }

}
