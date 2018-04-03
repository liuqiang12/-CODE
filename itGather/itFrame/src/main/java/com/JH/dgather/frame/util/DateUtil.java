package com.JH.dgather.frame.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;

/**
 * @author YandDS
 * 
 * 操作时间的工具类
 *
 */
public class DateUtil {
	
	static Logger logger = Logger.getLogger(DateUtil.class.getName());
	
	public static String getTimeByMilli(long mill) {
		StringBuffer sb = new StringBuffer();
		int second = 1000;
		int mi = 60 * second;
		int hour = 60 * mi;
		int day = 24 * hour;
		
		int days = (int) (mill / day);
		long left = mill % day;
		int hours = (int) (left / hour);
		left = left % hour;
		int mis = (int) (left / mi);
		left = left % mi;
		int seconds = (int) (left / second);
		
		if (days != 0) {
			sb.append(days + "天");
		}
		if (hours != 0) {
			sb.append(hours + "小时");
		}
		if (mis != 0) {
			sb.append(mis + "分");
		}
		if (seconds != 0) {
			sb.append(seconds + "秒");
		}
		
		return sb.toString();
		
	}
	
	/**
	 * @param date 传入日期
	 * 
	 * @return 返回xxxx年xx月xx日 xx时xx分xx秒的字符
	 */
	public static String getChineseTimeStr(Date date) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
		
		return sdf.format(date);
		
	}
	
	/**
	 * 返回format指定格式的日期字符串
	 * 
	 * @param d
	 * @param format
	 * @return
	 */
	
	public static String getTimeStrInFormat(Date d, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(d);
	}
	
	/**
	 * 返回format指定格式的日期字符串
	 * 
	 * @param d
	 * @param format
	 * @return
	 */
	
	public static String getTimeStrInFormat(Date d, String format, String lang) {
		SimpleDateFormat sdf = new SimpleDateFormat(format, new Locale(lang));
		return sdf.format(d);
	}
	
	/**
	 * 根据字符串以及指定的格式，返回日期
	 * 
	 * @param timeStr
	 * @param format
	 * @return
	 */
	public static Date getDateFromFormatString(String timeStr, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(timeStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error("格式解析不成功: format=" + format + "||timestr=" + timeStr, e);
			return null;
		}
	}
	
	/**
	 * @return 返回现在的时间字符串 年月日时分秒
	 */
	public static String getNowTimeStr() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
		
		return sdf.format(new Date());
	}
	
	/**
	 * @return 返回现在时间字符串（数字）
	 */
	public static String getNowTimeStrNum() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		
		return sdf.format(new Date());
	}
	
	/**
	 * 
	 * 返回d的日期数字字符串
	 * 
	 * @param d
	 * @return
	 */
	public static String getTimeStrNum(Date d) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		
		return sdf.format(d);
	}
	
	/**
	 * 按照format的格式解析timeStr，得到日期（本地默认）
	 * 
	 * @param timeStr
	 * @param format
	 * @return
	 */
	public static Date getTimeByStr(String timeStr, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(timeStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error("日期转换错误：format==>" + format + "||timeStr==>" + timeStr, e);
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * 根据format的格式以及lang所标记的loacale来解析timeStr
	 * @param timeStr
	 * @param format
	 * @param lang
	 * @return
	 */
	public static Date getDateFromFormatString(String timeStr, String format, String lang) {
		Date returnDa = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format, new Locale(lang));
		try {
			returnDa = sdf.parse(timeStr);
			if (returnDa.getYear() == 70) {
				returnDa.setYear(new Date().getYear());
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			//			logger.error( "日期转换错误：format==>"+format+"||timeStr==>"+timeStr , e );
			//			e.printStackTrace();
		}
		return returnDa;
	}
	
	/**
	 * 判断是否是同一天
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean isSameDay(Date time1, Date time2) {
		if (time1 == null || time2 == null)
			throw new IllegalArgumentException("time parameter must be NOT-NULL");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		return sdf.format(time1).equalsIgnoreCase(sdf.format(time2));
	}
	

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
	
	/**
	 * 日期比较（不包含时间）
	 * @param day1
	 * @param day2
	 * @return  整数表示day1在day2之后，0表示日期相等，负数表明day1在day2之前
	 */
	public static int dayCompare(Date day1, Date day2) {
		//	    Calendar c1 = GregorianCalendar.getInstance();
		////	    c1.setTimeZone(TimeZone.getTimeZone(ID));
		//	    c1.clear();
		//	    c1.setTime(day1);
		//	    c1.set(Calendar.SECOND, 0);
		//	    c1.set(Calendar.MINUTE, 0);
		//	    c1.set(Calendar.HOUR, 0);
		//	   
		//	    Calendar c2 = GregorianCalendar.getInstance();
		//	    
		//	    
		//	    c2.clear();
		//	    c2.setTime(day2);
		//	    c2.set(Calendar.SECOND, 0);
		//            c2.set(Calendar.MINUTE, 0);
		//            c2.set(Calendar.HOUR, 0);
		//            logger.info("day1="+c1);
		//            logger.info("day2="+c2);
		//            logger.info("day1.compareTo day2:"+c1.compareTo(c2));
		
		String str1 = getTimeStrInFormat(day1, "yyyyMMdd");
		String str2 = getTimeStrInFormat(day2, "yyyyMMdd");
		//	    logger.info(str1);
		//	    logger.info(str2);
		return str1.compareTo(str2);
		
		//            return c1.compareTo(c2);
	}
	
	public static void main(String args[]) {
		//		String dateStr = getChineseTimeStr(new Date());
		//		System.out.println(dateStr);
		Date d = getDateFromFormatString("10 Apr 28 104300", "y MMM d HHmmss", "en");
		Date d2 = getDateFromFormatString("10 apr 29 104400", "y MMM d HHmmss", "de");
		//		String dateStr = getTimeStrInFormat(d,"M月");
		//		System.out.println(dateStr);
		//		System.out.println(isSameDay(d,d2));
		System.out.println(dayCompare(d, d2));
		//		System.out.println(new Date().getYear());
		
		//		getTimeByMilli(112323211);
	}
	
}
