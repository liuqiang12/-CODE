/*
 * @(#)DateFormate.java 01/09/2012
 *
 * Copyright 2011 Zone, All rights reserved.
 */
package com.JH.dgather.comm;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;

public class DateFormate {
	private static final Logger log = Logger.getLogger(DateFormate.class);
	
	/**
	 * 取得当前时间（格式为：yyyy-MM-dd HH:mm:ss）
	 * @return String
	 */
	public static String getDateTime() {
		Calendar calendar = Calendar.getInstance();
		Date cdate = calendar.getTime();
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sdate = dt.format(cdate);
		return sdate;
	}
	
	public static final String getDateTime2(Date date) {
		return getShortDate(date) + " " + getShortTime(date);
	}
	
	public static final String getDateTime(Date date) {
		return getDate(date) + " " + getTime(date);
	}
	
	public static final String getDate(Calendar cal) {
		return getDate(cal.getTime());
	}
	
	/**
	 * 
	 * METHOD: getDate
	 * CLASS: DateFormater
	 *  
	 * @param date
	 * @return
	 */
	public static final String getDate(Date date) {
		if (null == date) {
			return "&nbsp;";
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		return year + "年" + (month - Calendar.JANUARY + 1) + "月" + day + "日";
	}
	
	public static final String getDAYOFWEEK(int day){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
		Calendar calendar=Calendar.getInstance(Locale.CHINA);
		//当前时间，貌似多余，其实是为了所有可能的系统一致
		calendar.setTimeInMillis(System.currentTimeMillis());
		Date date1 = calendar.getTime();
		calendar.set(Calendar.DAY_OF_WEEK, (day+1)%7);
		Date date2 = calendar.getTime();
		if(date1.after(date2)){
			calendar.add(calendar.DATE, 7);
		}
		return format.format(calendar.getTime());
	}
	
	public static final String getDAYOFMONTH(int day){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
		Calendar calendar=Calendar.getInstance(Locale.CHINA);
		//当前时间，貌似多余，其实是为了所有可能的系统一致
		calendar.setTimeInMillis(System.currentTimeMillis());
		Date date1 = calendar.getTime();
		calendar.set(Calendar.DAY_OF_MONTH, day);
		Date date2 = calendar.getTime();
		if(date1.after(date2)){
			calendar.add(calendar.MONTH, 1);
		}
		return format.format(calendar.getTime());
	}
	/*获取几天前的日期*/
	public static final String getDateAdd(Date date, int days) {
		if (null == date) {
			return "";
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(cal.DATE, days);
		//cal.add(cal.D)
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		return year + "-" + (month - Calendar.JANUARY + 1) + "-" + day;
	}
	
	/*获取两日期相差的秒*/
	public static final long comparaSecon(Date date1, Date date2) {
		if (date1 == null)
			date1 = new java.util.Date();
		if (date2 == null)
			date2 = new java.util.Date();
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		long Millis = cal1.getTimeInMillis() - cal2.getTimeInMillis();
		return Millis / 1000;
	}
	
	/**
	 * 
	 * METHOD: getShortDate
	 * CLASS: DateFormater
	 * 
	 * @param date
	 * @return
	 */
	public static final String getShortDate(Date date) {
		if (null == date) {
			return "&nbsp;";
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		return year + "-" + (month - Calendar.JANUARY + 1) + "-" + day;
	}
	
	/**
	 * 
	 * METHOD: getMonth
	 * CLASS: DateFormater
	 * 
	 * @param cal
	 * @return
	 */
	public static final String getMonth(Calendar cal) {
		return getMonth(cal.getTime());
	}
	
	/**
	 * 
	 * METHOD: getMonth
	 * CLASS: DateFormater
	 * 
	 * @param date
	 * @return
	 */
	public static final String getMonth(Date date) {
		if (null == date) {
			return "&nbsp;";
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		return year + "年" + (month - Calendar.JANUARY + 1) + "月";
	}
	
	/**
	 * 
	 * METHOD: getTimme
	 * CLASS: DateFormater
	 * 
	 * @param cal
	 * @return
	 */
	public static final String getTimme(Calendar cal) {
		return getTime(cal.getTime());
	}
	
	/**
	 * 
	 * METHOD: getTime
	 * CLASS: DateFormater
	 * 
	 * @param date
	 * @return
	 */
	public static final String getTime(Date date) {
		
		if (null == date) {
			return "&nbsp;";
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int hour = cal.get(Calendar.HOUR);
		int ampm = cal.get(Calendar.AM_PM);
		int minute = cal.get(Calendar.MINUTE);
		
		if (0 == hour) {
			hour = 12;
			ampm = ampm == Calendar.AM ? Calendar.PM : Calendar.AM;
		}
		
		return (Calendar.AM == ampm ? "上午 " : "下午 ") + hour + ":" + minute;
		
		/*
		 if(null == date){
		    return "&nbsp;";
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int hour =  cal.get(cal.HOUR_OF_DAY);
		int minute = cal.get(cal.MINUTE);
		int second = cal.get(cal.SECOND);
		return  hour + ":" + minute + ":" + second;
		*/
	}
	
	private static final String HH24_AmPm(int hour) {
		if (hour <= 6)
			return "凌晨";
		if (hour <= 8)
			return "早上";
		if (hour <= 11)
			return "上午";
		if (hour <= 13)
			return "中午";
		if (hour <= 18)
			return "下午";
		if (hour <= 20)
			return "傍晚";
		if (hour <= 24)
			return "晚上";
		return "";
	}
	
	/**
	 * 
	 * METHOD: getTime
	 * CLASS: DateFormater
	 * 
	 * @param date
	 * @return
	 */
	public static final String getTime2(Date date) {
		
		if (null == date) {
			return "&nbsp;";
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int hour = cal.get(Calendar.HOUR);
		int ampm = cal.get(Calendar.AM_PM);
		int minute = cal.get(Calendar.MINUTE);
		
		int hour24 = hour;
		if (ampm == Calendar.PM)
			hour24 += 12;
		String AmPm = HH24_AmPm(hour24);
		
		//return AmPm + hour + "点" + minute+"分";
		return hour24 + "点" + minute + "分";
	}
	
	/**
	 * 
	 * METHOD: getShortTime
	 * CLASS: DateFormater
	 * 
	 * @param date
	 * @return
	 */
	public static final String getShortTime(Date date) {
		if (null == date) {
			return "&nbsp;";
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int hour = cal.get(Calendar.HOUR);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		
		if (Calendar.PM == cal.get(Calendar.AM_PM))
			hour += 12;
		return hour + ":" + minute + ":" + second;
	}
	
	/**
	 * 
	 * METHOD: getLongDate
	 * CLASS: DateFormater
	 * 
	 * @param date
	 * @return
	 */
	public static final String getLongDate(Date date) {
		return getDate(date) + " " + getTime(date);
	}
	
	/**
	 * 
	 * METHOD: isSameMonth
	 * CLASS: DateFormater
	 * 
	 * @param cal1
	 * @param cal2
	 * @return
	 */
	public static final boolean isSameMonth(Calendar cal1, Calendar cal2) {
		boolean y = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
		boolean m = cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
		return y && m;
	}
	
	/**
	 * 
	 * METHOD: isSameMonth
	 * CLASS: DateFormater
	 * 
	 * @param day1
	 * @param day2
	 * @return
	 */
	public static final boolean isSameMonth(Date day1, Date day2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(day1);
		cal2.setTime(day2);
		
		return isSameMonth(cal1, cal2);
	}
	
	/**
	 * 
	 * METHOD: isSameDay
	 * CLASS: DateFormater
	 * 
	 * @param cal1
	 * @param cal2
	 * @return
	 */
	public static final boolean isSameDay(Calendar cal1, Calendar cal2) {
		boolean y = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
		boolean m = cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
		boolean d = cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
		return y && m && d;
	}
	
	/**
	 * 
	 * METHOD: isSameDay
	 * CLASS: DateFormater
	 * 
	 * @param day1
	 * @param day2
	 * @return
	 */
	public static final boolean isSameDay(Date day1, Date day2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(day1);
		cal2.setTime(day2);
		
		return isSameDay(cal1, cal2);
		
	}
	
	/**
	 * 
	 * METHOD: maxDayOfMonth
	 * CLASS: DateFormater
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static final int maxDayOfMonth(int year, int month) {
		if (Calendar.FEBRUARY == month) {
			return (0 == year % 4) ? 29 : 28;
		}
		else {
			return (0 == (month + (month > 6 ? 1 : 0)) % 2) ? 31 : 30;
		}
	}
	
	/**
	 * 
	 * METHOD: maxDayOfMonth
	 * CLASS: DateFormater
	 * 
	 * @param day
	 * @return
	 */
	static final public int maxDayOfMonth(Date day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		return maxDayOfMonth(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH));
	}
	
	/**
	 * 
	 * METHOD: DateToOracleStr
	 * CLASS: DateFormater
	 * 
	 * @param day
	 * @return
	 */
	public String DateToOracleStr(Date d) {
		if ((d == null) || d.equals(new Date(0))) {
			//return "to_date('1970-1-1','yyyy-mm-dd')";
			return "null";
		}
		else {
			return "to_date('" + getShortDate(d) + " " + getShortTime(d) + "','yyyy-mm-dd hh24:mi:ss')";
		}
	}
	
	/**
	 * 将字符串转换成格式为：yyyy-MM-dd HH:mm 日期.
	 * @param str
	 * @return
	 */
	public static Date strToDate(final String str) {
		if (str == null || str.trim().length() == 0)
			return null;
		try {
			DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			return fmt.parse(str.trim());
			
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * 将字符串转换成指定格式的日期.
	 * @param str 日期字符串.
	 * @param dateFormat 日期格式. 如果为空，默认为:yyyy-MM-dd HH:mm:ss.
	 * @return
	 */
	public static Date strToDate(final String str, String dateFormat) {
		if (str == null || str.trim().length() == 0)
			return null;
		try {
			if (dateFormat == null || dateFormat.length() == 0)
				dateFormat = "yyyy-MM-dd HH:mm:ss";
			DateFormat fmt = new SimpleDateFormat(dateFormat);
			return fmt.parse(str.trim());
			
		} catch (Exception ex) {
			log.error("将字符串(" + str + ")转换成指定格式(" + dateFormat + ")的日期时失败！错误原因：" + ex.getMessage());
			return null;
		}
	}
	
	/**
	 * 将当前日期转换成yyyyMMddHHmmss的字符串. 如：20071012141350
	 * @return
	 */
	public static String currDateToStr() {
		return dateToStr(new java.util.Date(), "yyyyMMddHHmmss");
	}
	
	/**
	 * 将日期转换成 日期(yyyy-MM-dd)字符串.
	 * @param date 日期
	 * @return
	 */
	public static String dateTodateStr(final Date date) {
		return dateToStr(date, "yyyy-MM-dd");
	}
	
	/**
	 * 将日期转换成 yyyy-MM-dd HH:mm 字符串.
	 * @param date 日期
	 * @return
	 */
	public static String dateToStr(final Date date) {
		return dateToStr(date, "yyyy-MM-dd HH:mm");
	}
	
	/**
	 * 将日期转换成指定格式的字符串.
	 * @param date 日期
	 * @param dateFormat 日期格式. 如果为空，默认为:yyyy-MM-dd HH:mm:ss.
	 * @return
	 */
	public static String dateToStr(final Date date, String dateFormat) {
		if (date == null || "".equals(date)) {
			//log.debug("未知时间");
			return "";
			//return "未知时间";
		}
		try {
			if (dateFormat == null || dateFormat.trim().length() == 0)
				dateFormat = "yyyy-MM-dd HH:mm:ss";
			DateFormat fmt = new SimpleDateFormat(dateFormat.trim());
			return fmt.format(date);
		} catch (Exception ex) {
			log.error("将日期转换成指定格式(" + dateFormat + ")的字符串时失败！错误原因：" + ex.getMessage());
			return "";
			
		}
	}
	
	/**
	 * 返回减去指定天数的日期字符串.
	 * 获取计算后的日期：指定日期dateStr前day天
	 * @param date 将运算日期.
	 * @param day 减去的天数.
	 * return
	 */
	public static String addDateToString(final Date date, final int day) {
		try {
			java.util.Date tempDate = addDate(date, day);
			String dateStr = dateToStr(tempDate, null);
			return dateStr;
		} catch (Exception ex) {
			return "";
		}
	}
	
	/**
	 * 返回减去指定天数的日期.
	 * @param date 将运算日期.
	 * @param day 加上的天数.
	 * return
	 */
	public static Date addDate(final Date date, final int day) {
		try {
			java.util.Calendar calendar = java.util.Calendar.getInstance();
			calendar.setTime(date);
			int currDay = java.util.Calendar.DAY_OF_MONTH;
			calendar.set(currDay, calendar.get(currDay) + day); //让日期加 day天 
			
			return calendar.getTime();
		} catch (Exception ex) {
			
			return null;
		}
	}
	
	/*
	 * 当前日期加n月运算
	 */
	public static Date AddMonth(final Date date, final int month){	
		try{
			java.util.Calendar calendar=java.util.Calendar.getInstance();   
	        calendar.setTime(date);
	        int currMonth = java.util.Calendar.MONTH;
	        calendar.set(currMonth, calendar.get(currMonth) + month);	//让日期加month月 
		    return calendar.getTime();
		}catch(Exception ex) {
			
			return null;
		}
  	}	
	/*
	 * 当前时间添加小时
	 */	
		public static Date addHour(final Date date, final int nHour) {
			try {
				java.util.Calendar calendar = java.util.Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(java.util.Calendar.HOUR_OF_DAY, nHour);
				return calendar.getTime();
			} catch (Exception e) {
				return null;
			}
		}

	/*
	 * 当前日期加n月运算
	 */
	public static Date AddMinute(final Date date, final int minute){	
		try{
			java.util.Calendar calendar=java.util.Calendar.getInstance();   
	        calendar.setTime(date);
	        int currMinute = java.util.Calendar.MINUTE;
	        calendar.set(currMinute, calendar.get(currMinute) + minute);	//让日期加month月 
		    return calendar.getTime();
		}catch(Exception ex) {
			
			return null;
		}
  	}	
	
	/**
	 * 返回加上指定天数的日期.
	 * @param date 将运算日期.
	 * @param day 加上的天数.
	 * return
	 */
	public static Date plusDate(final Date date, final int day) {
		try {
			java.util.Calendar calendar = java.util.Calendar.getInstance();
			calendar.setTime(date);
			int currDay = java.util.Calendar.DAY_OF_MONTH;
			
			calendar.set(currDay, calendar.get(currDay) + day); //让日期加 day天
			
			return calendar.getTime();
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * 返回加上指定天数的日期字符串.
	 * @param date 将运算日期.
	 * @param day 加上的天数.
	 * return
	 */
	public static String plusDateToString(final Date date, final int day) {
		try {
			java.util.Date tempDate = addDate(date, day);
			String dateStr = dateToStr(tempDate, null);
			
			return dateStr;
		} catch (Exception ex) {
			return "";
		}
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(DateFormate.dateTodateStr(DateFormate.AddMinute(new Date(),900)));
		System.out.println(DateFormate.dateToStr((DateFormate.AddMinute(new Date(),900))));
		System.out.println(DateFormate.getDateAdd(new Date(), 1));

	}
}
