package com.lanrenyou.common;

import java.security.MessageDigest;
import java.util.Arrays;

import sun.security.provider.MD5;

public class PasswordUtil {

	public static final char[] base64EncodeChars = new char[]{'.','/','0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

	public static String base64Encode(byte[] md5)  
	{  
	    int num_24bits = 5;  
	    int i;
	    char[] result = new char[22];
	    
	    for (i = 0; i < num_24bits; ++i)  
	    {  
	        result[4 * i + 0] = base64EncodeChars[(md5[3 * i] >> 2) & 0x3F];    
	        result[4 * i + 1] = base64EncodeChars[(((md5[3 * i] & 0x3) << 4) | (md5[3 * i + 1] >> 4)) & 0x3F];    
	        result[4 * i + 2] = base64EncodeChars[((md5[3 * i + 1] << 2) | (md5[3 * i + 2] >> 6)) & 0x3F];    
	        result[4 * i + 3] = base64EncodeChars[md5[3 * i + 2] & 0x3F];  
	    }  
	    result[20] = base64EncodeChars[md5[15] >> 2];  
	    result[21] = base64EncodeChars[(md5[15] << 4) & 0x3F];  
	    return new String(result);
	}


	public static String wordpress(String salt, String passwd, int count) {
		int i;
		String symbol = null;
		
		if (salt.length() != 8)
		{
			System.out.println("illegal salt!");
			return null;
		}

		if (count != 8192 && count != 2048)
		{
			System.out.println("illegal count!");
			return null;
		}

		//first time use md5 function with salt
		String buffer = salt + passwd;
		buffer = convertToMd5(buffer);

		//count times md5 function
		for (i = 0; i < 8192; i++)
		{
			buffer = convertToMd5(buffer + passwd);
		}

		String code = base64Encode( buffer.getBytes());

		if (count == 8192){
			symbol = "B";
		} else if(count == 2048) {
			symbol = "9";
		}
		code = "$P$" + symbol + salt + code;
		System.out.println(code);
		return code;

	}
	
	public static String convertToMd5(String str){
		
		if ( str == null ){
			return null;
		}
		byte newByte1[] = str.getBytes();
		try{
			MessageDigest messagedigest = MessageDigest.getInstance("MD5");
			byte newByte2[] = messagedigest.digest(newByte1);
			String cryptograph = "";
			for(int i=0; i<newByte2.length; i++){
				String temp = Integer.toHexString( newByte2[i] & 0x000000ff );
				if( temp.length()<2 ) temp = "0"+temp;
				cryptograph += temp;
			}
			return cryptograph;
		}
		catch(Exception e){
			System.out.println("[ "+new java.util.Date().toString()+" ] [ CodeUtil.convertToMd5 ] "+e.toString());
			return null;
		}
	}
	
	public static void main(String[] args){
		String passwd = "$P$B0l3LiT8Eb2nFlENoZDfMDcMqbGNCk.";
		System.out.println("Correct Password:\n"+ passwd);
		PasswordUtil.wordpress("0l3LiT8E", "jsan126", 8192);
	}
}
