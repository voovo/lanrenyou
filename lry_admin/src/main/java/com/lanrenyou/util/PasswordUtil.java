package com.lanrenyou.util;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Encoder;

public class PasswordUtil 
{
	/**
	 * in this class, method convertToMd5 will definitely return a String with length MD5_LENGTH
	 */
	public static final int MD5_LENGTH = 32;
	
	private static final String seed_prifex = "lanrenyou";
	
	private static final String seed_suffex = "EJwtkfTC";
	
	/**
	 * 生成管理后台密码
	 * @param password
	 * @return
	 */
	public static String encryptAdminPassword(String password){
		String entryPassword = PasswordUtil.convertToMd5(password.trim());
		String finalPassword = PasswordUtil.convertToMd5(seed_prifex+entryPassword+"admin");
		return finalPassword;
	}
	
	/**
	 * 生成最终密码
	 * @param password
	 * @return
	 */
	public static String encryptPassword(String password){
		String entryPassword = PasswordUtil.convertToMd5(password.trim());
		String finalPassword = PasswordUtil.convertToMd5(seed_prifex+entryPassword+seed_suffex);
		return finalPassword;
	}
	
	/*
     * 将字符数组转换为16进制字符串
     */
	public static final String bytesToHexStr(
			byte[] bcd)
		{
			StringBuffer s = new StringBuffer(bcd.length * 2);

			for (int i = 0; i < bcd.length; i++)
			{
				s.append(bcdLookup[(bcd[i] >>> 4) & 0x0f]);
				s.append(bcdLookup[bcd[i] & 0x0f]);
			}

			return s.toString();
		}
	 /*
     * 将16进制字符串转换为字符数组
     */
	public static final byte[] hexStrToBytes(
			String	s)
		{
		
		if ( s == null ){
			return null;
		}
			byte[]	bytes;

			bytes = new byte[s.length() / 2];

			for (int i = 0; i < bytes.length; i++)
			{
				bytes[i] = (byte)Integer.parseInt(
						s.substring(2 * i, 2 * i + 2), 16);
			}

			return bytes;
		}
	

	 /*
    * 将16进制字符串转换为字符数组
    */
	public static final String hexStrToString(String	s)
		{
		
		if ( s == null ){
			return null;
		}
		
			byte[]	bytes;

			bytes = new byte[s.length() / 2];

			for (int i = 0; i < bytes.length; i++)
			{
				bytes[i] = (byte)Integer.parseInt(
						s.substring(2 * i, 2 * i + 2), 16);
			}

			return new String(bytes);
		}
	
	private static final char[] bcdLookup =
	{
		'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'
	};
	
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
	
	public static String convertToSHA1(String str){
		
		if ( str == null ){
			return null;
		}
		byte newByte1[] = str.getBytes();
		try{
			MessageDigest messagedigest = MessageDigest.getInstance("SHA-1");
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
			System.out.println("[ "+new java.util.Date().toString()+" ] [ CodeUtil.convertToSHA1 ] "+e.toString());
			return null;
		}
	}
	
	public static String getHmacSha1(String data, String key) {
		byte[] byteHMAC = null;
		try {
			Mac mac = Mac.getInstance("HmacSHA1");
			SecretKeySpec spec = new SecretKeySpec(key.getBytes(), "HmacSHA1");
			mac.init(spec);
			byteHMAC = mac.doFinal(data.getBytes());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException ignore) {
		}
		String oauth = new BASE64Encoder().encode(byteHMAC);
		return oauth;
	}
	
	public static String getPasswordContainsUpperCaseLowerCaseNumber() {
		String base1 = "ABCDEFGHJKLMNPQRSTUVWXYZ";
		Random random = new Random();
		Set<String> set = new HashSet<String>();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 2; i++) {
			int number = random.nextInt(base1.length());
			set.add(String.valueOf(base1.charAt(number)));
		}
		String base2 = "abcdefghijklmnopqrstuvwxyz";
		for (int i = 0; i < 2; i++) {
			int number = random.nextInt(base2.length());
			set.add(String.valueOf(base2.charAt(number)));
		}
		String base3 = "0123456789";
		for (int i = 0; i < 2; i++) {
			int number = random.nextInt(base3.length());
			set.add(String.valueOf(base3.charAt(number)));
		}
		for(String s : set){
			sb.append(s);
		}
		return sb.toString();
	}
	
	public static String getRandomString(int length) {
		if ( length < 1 ){
			length = 1;
		}
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
		int number = random.nextInt(base.length());
		sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	/**
	 * public static String getRandomString(int length)是用来生成BaiduPassportBean里的签名的
	 * 本方法是用来生成登录和注册的验证码的
	 * 验证码不区分大小写，所以大小写之前的易混淆不考虑
	 * 已去除[I, l, 1]，[O, o, 0]等易混淆字母，如发现还有易混淆的，应该继续去除
	 * @param length
	 * @return
	 */
	public static String getRandomStringForVerifyCode(int length) {
		if ( length < 1 ){
			length = 1;
		}
		String base = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz23456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	
	public static String bSubstring(String s, int length) throws Exception  {
		return bSubstring(s, length, "");
	}
    /**
     * 截取字符串
     * @param s
     * @param length
     * @param suffix
     * @return
     * @throws Exception
     */
	public static String bSubstring(String s, int length, String suffix) throws Exception  
    {  
		if(null==s )
		{
			return s;
		}
        byte[] bytes = s.getBytes("Unicode");  
        
        if(bytes.length<length)
        {
        	return s;
        }
        
        int n = 0; // 表示当前的字节数  
        int i = 2; // 要截取的字节数，从第3个字节开始  
        for (; i < bytes.length && n < length; i++)  
        {  
            // 奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节  
            if (i % 2 == 1)  
            {  
                n++; // 在UCS2第二个字节时n加1  
            }  
            else 
            {  
                // 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节  
                if (bytes[i] != 0)  
                {  
                    n++;  
                }  
            }  
        }  
        // 如果i为奇数时，处理成偶数  
        if (i % 2 == 1)  
     
        {  
            // 该UCS2字符是汉字时，去掉这个截一半的汉字  
            if (bytes[i - 1] != 0)  
                i = i - 1;  
            // 该UCS2字符是字母或数字，则保留该字符  
            else 
                i = i + 1;  
        }  
        String  result = new String(bytes, 0, i, "Unicode");
        if(suffix != null && length < bytes.length) {
        	result += suffix; 
        }
        return result;  
    }  
	
	/**
	 * 取得字符串的字节长度
	 * @param word
	 * @return
	 */
	public static int getBytesLength(String word) {
		String str = word.replaceAll("[^\\x00-\\xff]", "**");
		return str.length();
	}
	
	public static void main(String[] args) {
		String aa = getPasswordContainsUpperCaseLowerCaseNumber();;
		System.out.println("aa==>"+aa);
	}
	
}
