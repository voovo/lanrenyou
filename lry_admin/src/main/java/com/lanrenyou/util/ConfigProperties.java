package com.lanrenyou.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigProperties {
	
	protected static Properties configProperties = new Properties();
	
	static {
		FileInputStream fileinput=null;
		try {
			Class cls = Class.forName(ConfigProperties.class.getName());
			ConfigProperties gl = (ConfigProperties) cls.newInstance();
			java.net.URL abspath=gl.getClass().getClassLoader().getResource("");
			String path=(abspath.getPath()).toString();
			fileinput = new FileInputStream(path+"/configs.properties");
			configProperties.load(fileinput);
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
			return configProperties.getProperty(key);
		}
	}
}
