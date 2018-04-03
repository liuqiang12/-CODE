/*
 * @(#)EncryptAnalyse.java 01/09/2012
 *
 * Copyright 2011 Zone, All rights reserved.
 */
package com.JH.itBusi.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Date;

/**
 * <code>EncryptAnalyse</code> 密码加密/解密公用类
 * @author Wang Xuedan
 * @version 1.0, 01/09/2012
 */
public class EncryptAnalyse {
	private int StartKey = 981;
	private int MultKey = 12674;
	private int AddKey = 35891;
	
	//加密
	public String MNSA_Encrypt(String InString) throws NullPointerException {
		if(InString == null) {
			throw new NullPointerException("需要加密的字符串为null!");
		}
		
		int i;
		String tmp;
		char tmpchar;
		String Result = "";
		int tmpf;
		tmp = Encrypt(InString, StartKey, MultKey, AddKey);
		//将每个char转为3位整型，为分解做准备
		for (i = 0; i < tmp.length(); i++) {
			try {
				tmpchar = tmp.charAt(i);
			} catch (StringIndexOutOfBoundsException e) {//扑捉错误
				break;
			}
			tmpf = tmpchar + 1000;//转为3位整型
			Result = Result + (String.valueOf(tmpf)).substring(1);
		}
		return Result;
	}
	
	//解码
	public String MNSA_Decrypt(String InString) throws NullPointerException {
		if (InString == null) {
			throw new NullPointerException("需要解码的字符串为null!");
		}
		
		String tmp, tmp1, tmp2;
		int tmpint;
		tmp = InString;
		Integer tempint = new Integer(0);
		tmp2 = "";
		while (true) {
			if ("".equals(tmp)) {
				break;
			}
			tmp1 = tmp.substring(0, 3);
			tmp = tmp.substring(3);
			try {//捕捉错误
				tmpint = Integer.parseInt(tmp1);
				tmp2 = tmp2 + (char) tmpint;
			} catch (NumberFormatException e) {
				break;
			}
		}
		return Decrypt(tmp2, StartKey, MultKey, AddKey);
	}
	
	private String Encrypt(String InString, int StartKey, int MultKey, int AddKey) {
		int tempint;
		byte i;
		String Result = "";
		for (i = 0; i < InString.length(); i++) {
			tempint = ((InString.charAt(i)) ^ (StartKey >> 8));
			//取低8位
			Result = Result + (char) (tempint & 255);
			//每个char加密
			StartKey = ((int) (Result.charAt(i)) + StartKey) * MultKey + AddKey;
		}
		return Result;
	}
	
	private String Decrypt(String InString, int StartKey, int MultKey, int AddKey) {
		int tempint;
		byte i;
		String Result = "";
		for (i = 0; i < InString.length(); i++) {
			//每个char解密
			tempint = ((InString.charAt(i)) ^ (StartKey >> 8));
			//取低8位
			Result = Result + (char) (tempint & 255);
			StartKey = ((int) (InString.charAt(i)) + StartKey) * MultKey + AddKey;
		}
		return Result;
	}
	
	//验证用户使用日期
	public int getRegFlag(String regFileName, int delay) {
		int result = 0;
		String tmpstr = "";
		String oldDate = "";
		String toDate = DateFormate.getShortDate(new Date());
		String delayDate = "";
		try {
			FileReader read = new FileReader(regFileName);
			BufferedReader buf = new BufferedReader(read);
			oldDate = MNSA_Decrypt(buf.readLine());
			read.close();
			if (toDate.compareTo(oldDate) == -1)//如果日期被修改则认为失效
			{
				result = -1;
			}
			else {
				delayDate = DateFormate.getDateAdd(new Date(), -30);//将时间推移到delay天前
				int n = delayDate.compareTo(oldDate);
				if ((!oldDate.equals("2000-01-01")) && (delayDate.compareTo(oldDate) > 0))//超过delay天试用期
				{
					result = -1;
				}
				tmpstr = MNSA_Encrypt(toDate);
				FileWriter writer = new FileWriter(regFileName, false);
				writer.write(tmpstr);
				writer.close();
			}
			
		} catch (Exception e)//如果文件被变动认为失效
		{
			result = -1;
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	public String MNSA_Decrypt_date() {
		String toDate = DateFormate.getShortDate(new Date());
		String s = MNSA_Encrypt(toDate);
		String st = MNSA_Decrypt(s);
		return s;
	}
	
	public static void main(String args[]) {
		EncryptAnalyse enc = new EncryptAnalyse();
		//String s = EncryptAnalyse.class.getResource("/").toString(); 
		// s = s+"\\reg.txt";
		// s = s.substring(6);
		//System.out.println(enc.MNSA_Decrypt_date());
		//	  String s = enc.MNSA_Encrypt("Cisco#2948#");
		//	  String s="080210079253020056170061010005081";
		//	  System.out.println(s);
		String s = enc.MNSA_Decrypt("123101155144207107076157");
//		//int n= enc.getRegFlag(s,30);
		System.out.println(s);
//		System.out.println("执行完成");
		
//		String s = "xiaotong";
//		System.out.println(enc.MNSA_Encrypt(s));
	}
}
