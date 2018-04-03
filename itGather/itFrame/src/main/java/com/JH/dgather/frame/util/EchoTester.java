package com.JH.dgather.frame.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.apache.log4j.Logger;

import com.JH.dgather.PropertiesHandle;

/**
 * 通过文件方法读取指定回显
 * @author Administrator
 *
 */
public class EchoTester {
	private Logger logger = Logger.getLogger(EchoTester.class);
	private static String mode = PropertiesHandle.getResuouceInfo("GET_ECHO_MODE");
	private static String saveEcho = PropertiesHandle.getResuouceInfo("SAVE_ECHO");
	
	public EchoTester() {
	}
	
	/**
	 * @method: isLandTelnet
	 * @return: true: 登录telnet获取回显, false: 通过文件读取回显测试
	 * @node: 是否是否是通过文件或telnet获取回显, 用于测试使用
	 */
	public boolean isLandTelnet() {
		if(mode == null) {
			logger.info("telnet获取回显");
			return true;
		}
		
		if ("file".equals(mode)) {
			logger.info("本地文件获取回显");
			return false;
		}
		else {
			logger.info("telnet获取回显");
			return true;
		}
	}
	
	public boolean isSaveEcho() {
		if(saveEcho == null) {
			logger.info("不保存回显");
			return false;
		}
		
		if ("true".equals(saveEcho)) {
			return true;
		}else {
			logger.info("不保存回显");
			return false;
		}
	}
	
	/**
	 * @method: getEchoFromTXTFile
	 * @return: 回显内容
	 * @note: 通过读取TXT文件，获取回显内容
	 */
	public String getEchoFromTXTFile(String portName) {
		String geString = "c:/echo_GE.txt";
		String posString = "c:/echo_POS.txt";
		String filepath = "";
		String result = "";
		Reader reader = null;
		BufferedReader br = null;
		
		try {
			if (portName.toLowerCase().contains("pos")) {
				filepath = posString;
			}
			else {
				filepath = geString;
			}
			
			reader = new FileReader(filepath);
			br = new BufferedReader(reader);
			StringBuffer sb = new StringBuffer();
			String data = null;
			while ((data = br.readLine()) != null) {
				sb.append(data + "\r\n");
			}
			
			result = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				reader.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		
		return result;
	}
	
	/**
	 * @method: getEchoByFileName
	 * @param filename: 文件名
	 * @return: 回显内容
	 * @note: 通过文件名，读取回显内容，文件路径默认为C:/
	 */
	public String getEchoByFileName(String filename) {
		String result = "";
		String path = filename;
		Reader reader = null;
		BufferedReader br = null;
		try {
			logger.info("获取回显的文件是：" + path);
			reader = new FileReader(path);
			br = new BufferedReader(reader);
			StringBuffer sb = new StringBuffer();
			String data = null;
			while ((data = br.readLine()) != null) {
				sb.append(data + "\r");
			}
			
			result = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				reader.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return result;
	}
	
	/*
	 * 
	 */
	public String getEchoByDevice(String echoType) {
		String result = "";
		String path = "c:/echoTester/";
		String device = EchoTester.DEVICE_ZX;
		
		path += device + "/" + echoType;
		Reader reader = null;
		BufferedReader br = null;
		
		try {
			logger.info("获取回显的文件是：" + path);
			reader = new FileReader(path);
			br = new BufferedReader(reader);
			StringBuffer sb = new StringBuffer();
			String data = null;
			while ((data = br.readLine()) != null) {
				sb.append(data + "\r\n");
			}
			
			result = sb.toString();
			logger.info("回显为:" + result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				reader.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		InputStream in = null;
		try {
			in = new FileInputStream(new File("c:/xdwang.txt"));
			StringBuffer sb = new StringBuffer();
			while (true) {
				if (in.available() < 1) {
					System.out.println("最一个字符了！");
					break;
				}
				
				char ch = (char) in.read();
				sb.append(ch);
			}
			System.out.println(sb);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ioE) {
					ioE.printStackTrace();
				}
			}
		}
		
	}
	
	//设备厂家名
	public static final String DEVICE_HW = "HW";
	public static final String DEVICE_ZX = "ZX";
	public static final String DEVICE_AEKT = "AEKT";
	public static final String DEVICE_JUNIPER = "JUNIPER";
	
	//类型
	public static final String ECHO_MPLS = "mpls.txt";
	public static final String ECHO_ISIS = "isis.txt";
	public static final String ECHO_OSPF = "ospf.txt";
	public static final String ECHO_BGP = "bgp.txt";
}
