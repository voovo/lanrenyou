package com.lanrenyou.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LRYEncryptKeyProperties {
	
	protected static Properties properties = new Properties();
	
	static {
		FileInputStream fileinput=null;
		try {
			Class cls = Class.forName(ViewUtil.class.getName());
			ViewUtil gl = (ViewUtil) cls.newInstance();
			java.net.URL abspath=gl.getClass().getClassLoader().getResource("");
			String path=(abspath.getPath()).toString();
			fileinput = new FileInputStream(path+"/encrypt_keys.properties");
			properties.load(fileinput);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				fileinput.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String getProperty(String key){
		if( key == null ){
			return null;
		}else{
			return properties.getProperty(key);
		}
	}
	
}
