package com.lanrenyou.search.index.util;


import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

public class DateUtil {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy",Locale.US);
	
	/*
	 * java编程
	 */
	public DateUtil() {
	}

	public static Date strToDate(String sStr) {
		if (sStr == null)
			return null;
		SimpleDateFormat formatter;
		if (sStr.length() == 19)
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		else if (sStr.length() == 10)
			formatter = new SimpleDateFormat("yyyy-MM-dd");
		else if (sStr.length() == 8)
			formatter = new SimpleDateFormat("yyyyMMdd");
		else if (sStr.length() == 14)
			formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		else
			formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		ParsePosition pos = new ParsePosition(0);
		return formatter.parse(sStr, pos);
	}

	public static Timestamp strToDatetime(String s) {
		return new Timestamp(strToDate(s).getTime());
	}

	public static String datetimeToChinese(Date dtSource) {
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy年MM月dd日HH时mm分ss秒");
		return formatter.format(dtSource);
	}

	public static String dateToStr(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		return format.format(date);
	}
	/**
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToStrYYYY_MM_DD(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if(date!=null){
		return format.format(date);
		}else{
			return "";
		}
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToStrYYYY_MM_DD_HH_mm(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(date!=null){
		return format.format(date);
		}else{
			return "";
		}
	}
	
	public static String datetimeToStr(Date date) {
		if (date == null) {
			return "";
		} else {
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			return formatter.format(date);
		}
	}

	public static String today() {
		return dateToStr(new Date());
	}

	public static String now() {
		return datetimeToStr(new Date());
	}

	public static String calcTime(String time, int diffYear, int diffMonth,
			int diffDate, int diffHour, int diffMinute, int diffSecond) {
		Timestamp timestamp = strToDatetime(time);
		timestamp.setYear(timestamp.getYear() + diffYear);
		timestamp.setMonth(timestamp.getMonth() + diffMonth);
		timestamp.setDate(timestamp.getDate() + diffDate);
		timestamp.setHours(timestamp.getHours() + diffHour);
		timestamp.setMinutes(timestamp.getMinutes() + diffMinute);
		timestamp.setSeconds(timestamp.getSeconds() + diffSecond);
		return datetimeToStr(timestamp);
	}

	public static String getCurrentTime() {
		String s = (new Time(System.currentTimeMillis())).toString();
		return s;
	}

	public static String getCurrentDate() {
		String s = (new java.sql.Date(System.currentTimeMillis())).toString();
		return s;
	}

	public static String getCurrentNow() {
		String s = getCurrentDate() + " " + getCurrentTime();
		return s;
	}

	public static String dateFromStr(String date) {
		if (date == null || date.length() < 10)
			return getCurrentDate();
		else
			return date.substring(0, 10);
	}

	public static String timeFromStr(String date) {
		if (date == null || date.length() < 19)
			return "00:00:00";
		else
			return date.substring(11, 19);
	}
	
	public static String isNullShow(String str){
		if(str==null){
			return "";
		}else{
			return str;
		}
	}
	
	/**
	 * 
	 * @param str
	 * @return true 为空  false 不为空
	 */
	public static boolean isNullOrNullStr(String str){
		if(str==null||"".equals(str.trim())){
			return true;
		}else{
			return false;
		}
		
	}
		
	/**
	 * 本地时间转为UTC时间（UTC时间比北京时间早8个小时）
	 * @param date
	 * @return
	 */
	public static Date converUTCDate(Date date){
		if(date==null){
			return null;
		}
		//1、取得本地时间：  
		java.util.Calendar cal = java.util.Calendar.getInstance();  
		try{
			cal.setTime(date);
			//2、取得时间偏移量：  
			int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);  
			//3、取得夏令时差：  
			int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);  
			//4、从本地时间里扣除这些差量，即可以取得UTC时间：  
			cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));  
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return (new Date(cal.getTimeInMillis()));
	}
	
	/**
	 * UTC时间转为当地时间（UTC时间比北京时间早8个小时）
	 * @param date
	 * @return
	 */
	public static Date converBJDate(Date date){
		if(date==null){
			return null;
		}
		//1、取得本地时间：  
		java.util.Calendar cal = java.util.Calendar.getInstance();  
		try{
			cal.setTime(date);
			//2、取得时间偏移量：  
			int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);  
			//3、取得夏令时差：  
			int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);  
			//4、UTC时间加上这些差量，即可得到当地时间：  
			cal.add(java.util.Calendar.MILLISECOND, +(zoneOffset + dstOffset));  
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return (new Date(cal.getTimeInMillis()));
	}

	/**
	 * 加8小时
	 * @param utcStr
	 * @return
	 */
	public static synchronized String converUTCStr2BJStr(String utcStr){
		if(!StringUtils.isNotBlank(utcStr)){
			return "";
		}else{
			try {
				return sdf.format(converBJDate(sdf2.parse(utcStr)));
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
		}
		
	}
	/**
	 * 减8小时
	 * @param bjStr
	 * @return
	 */
	public static synchronized String converBJStr2UTCStr(String bjStr){
		if(!StringUtils.isNotBlank(bjStr.trim())){
			return "";
		}else{
			try {
				return sdf.format(converUTCDate(sdf2.parse(bjStr)));
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
		}
		
	}
	
	public static void main(String[] args) throws Exception{

//			System.out.println(DateUtils.converUTCDate(sdf2.parse("Mon Aug 09 08:00:00 CST 1993")));
//			System.out.println(DateUtils.converBJDate(sdf2.parse("Mon Aug 09 08:00:00 CST 1993")));
//			System.out.println(DateUtils.converUTCStr2BJStr("Mon Aug 09 08:00:00 CST 1993"));
		System.out.println(DateUtil.converBJStr2UTCStr("Tue Aug 02 06:58:30 CST 2011"));
		
//			System.out.println(DateUtils.converUTCDate(new Date("Mon Aug 09 08:00:00 CST 1993")));
//			System.out.println(DateUtils.converBJDate(new Date("Mon Aug 09 08:00:00 CST 1993")));
		//		System.out.println(DateUtils.converBJDate(new Date()));
//			System.out.println(DateUtils.converBJDate(sdf.parse("2011-08-15 04:28:55")));
	}


}