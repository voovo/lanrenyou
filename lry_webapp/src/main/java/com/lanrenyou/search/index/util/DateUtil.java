package com.lanrenyou.search.index.util;


import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
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

	public static void main(String args[]) {
		System.out.println(now());
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
}