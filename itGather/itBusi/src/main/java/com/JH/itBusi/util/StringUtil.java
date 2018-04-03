package com.JH.itBusi.util;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.JH.dgather.frame.common.bean.DeviceInfoBean;

/**
 * string一些常用操作的工具类
 * 
 * @author yangDS
 */
public class StringUtil {
	public static String LINE = System.getProperty("line.separator");
	public static final int IP_V4 = 0;// ipV4常量
	public static final int IP_V6 = 1;// ipV6常量

	/**
	 * 判断字符串是否全为数字
	 * 
	 * @param source
	 * @return
	 */
	public static boolean isNumberString(String source) {

		if (source == null || source.length() == 0)
			return false;

		Pattern p = Pattern.compile("\\d+");

		Matcher m = p.matcher(source);
		return m.matches();

	}

	/**
	 * 根据输入的两个ip得到系统所要记录的标识. 如:ip1 =192.168.70.11 ip2=192.168.70.23 将得到结果为
	 * 192.168.70.0/24
	 * 
	 * @param ip1
	 * @param ip2
	 * @return
	 */
	public static String getIpRecordByTwoIps(String ip1, String ip2) {
		if (ip1.equals(ip2)) {
			System.err.println("相同的ip值，无法得到标识，使用默认");
			return "default";
		}

		String[] split1 = ip1.split("\\.");
		String[] split2 = ip2.split("\\.");
		int length = split1.length;
		int num = 0;
		StringBuffer ipRecord = new StringBuffer();
		for (int i = 0; i < length; i++) {

			if (!split1[i].trim().equals(split2[i].trim())) {
				num = i;
				break;
			} else {

				ipRecord.append(split1[i]);
				ipRecord.append(".");
			}

		}

		for (int i = length; i > num; i--) {
			if (ipRecord.charAt(ipRecord.length() - 1) == '.') {
				ipRecord.append("0");
			} else {
				ipRecord.append(".0");
			}

		}

		ipRecord.append("/");

		ipRecord.append(num * 8);

		return ipRecord.toString();

	}

	/**
	 * 根据shortType得到记录值
	 * 
	 * @param shortType
	 * @return
	 */
	public static String getIpRecordByShortType(String shortType) {
		String[] split = shortType.split("/");
		if (split[0].endsWith("0"))
			return shortType;
		String[] sArr = split[0].split("\\.");
		StringBuffer result = new StringBuffer();
		int length = sArr.length;
		for (int i = 0; i < length - 1; i++) {

			result.append(sArr[i]);
			result.append(".");

		}

		result.append("0/");
		result.append(split[1]);

		return result.toString();
	}

	/**
	 * 将ip:192.168.0.3 netMask: 255.255.255.254 转换为 192.168.0.2/31的形式
	 * 
	 * @param ip
	 * @param netMask
	 * @return
	 */
	public static String getNetIpByIpAndNetmask(String ip, String netMask) {
		// System.out.println("ip::"+ip);
		if (!isIpFormat(ip))
			return null;
		if (!isNetMask(netMask))
			return null;
		StringBuffer result = new StringBuffer("");
		StringBuffer maskBinaryString = new StringBuffer("");
		int maskCount = 0;
		ip = ip.trim();
		// System.out.println(ip);
		netMask = netMask.trim();
		// System.out.println(netMask);
		String[] ipElements = ip.split("\\.");

		String[] netMaskElements = netMask.split("\\.");
		try {
			for (int i = 0; i < ipElements.length; i++) {
				int ipInt = Integer.parseInt(ipElements[i]);
				int masInt = Integer.parseInt(netMaskElements[i]);

				result.append(ipInt & masInt);
				maskBinaryString.append(Integer.toBinaryString(masInt));
				if (i < ipElements.length - 1) {
					result.append(".");
				} else {
					result.append("/");
				}

				// System.out.println(result.toString());
			}

			// System.out.println(maskBinaryString);
			for (int i = 0; i < maskBinaryString.length(); i++) {
				if (maskBinaryString.charAt(i) == '1') {
					maskCount++;
				}

			}

			result.append(maskCount);
			// System.out.println(result.toString());
		} catch (Exception e) {
			return ip + "/" + netMask + "【无法转换】";
		}

		// System.out.println("netNo:"+result.toString());
		return result.toString();

	}

	/**
	 * 192.168.0.2/31 得到192.168.0.2 255.255.255.252 在结果中 get("ip")将得到ip值
	 * get("mask")将得到 mask
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getNetMaskByShortType(String shortType)
			throws Exception {
		if (shortType == null || shortType.trim().length() == 0)
			return null;
		// System.out.println("ip:"+shortType);
		String[] splitStr = shortType.split("/");

		if (splitStr == null || splitStr.length < 2) {
			throw new Exception("您输入的不是ip精简格式：" + shortType);
		}

		HashMap<String, String> returnMap = new HashMap<String, String>();

		returnMap.put("ip", splitStr[0]);
		String mask = splitStr[1];
		StringBuilder maskStr = new StringBuilder();
		StringBuilder theMask = new StringBuilder();
		int maskNum = Integer.parseInt(mask);
		for (int i = 0; i < 32; i++) {
			if (i < maskNum)
				maskStr.append("1");
			else
				maskStr.append("0");
		}

		for (int i = 0; i < 4; i++) {
			theMask.append(maskStr.substring(i * 8, (i + 1) * 8));
			if (i != 3)
				theMask.append(".");
		}
		mask = theMask.toString();

		theMask.delete(0, theMask.length());

		String[] maskSplit = mask.split("\\.");

		for (int i = 0; i < maskSplit.length; i++) {
			theMask.append(getIntValueByBinaryString(maskSplit[i]));
			if (i != maskSplit.length - 1)
				theMask.append(".");
		}

		returnMap.put("mask", theMask.toString());
		return returnMap;
	}

	/**
	 * 去除sourceString中word之间多余的空白。如：ss ssss ssss 将变为：ss ssss ssss
	 * 
	 * @param sourceString
	 * @return
	 */
	public static String trimRedundancyWhiteSpaceInStr(String sourceString) {
		if (sourceString == null || sourceString.trim().length() == 0)
			return null;
		StringBuilder sb = new StringBuilder();
		int length = sourceString.length();
		boolean ctrl = false;
		for (int i = 0; i < length; i++) {

			if (sourceString.charAt(i) == '\t' || sourceString.charAt(i) == ' ') {

				if (ctrl == true) {
					ctrl = false;
					sb.append(sourceString.charAt(i));
				}

			} else {
				ctrl = true;
				sb.append(sourceString.charAt(i));
			}

		}

		return sb.toString();
	}

	/**
	 * 得到二维图表格式的字符串的二维字符串数组
	 * 
	 * @param sourceString
	 * @return
	 */
	public static String[][] getStringMapIn2D(String sourceString) {

		if (sourceString == null || sourceString.trim().length() == 0)
			return null;

		String[][] stringMap;

		String[] rowString = sourceString.split(LINE);

		int columns = 0;
		int rows = rowString.length;
		/***
		 * 选取最大column
		 */
		for (int i = 0; i < rows; i++) {
			columns = Math
					.max(columns, trimRedundancyWhiteSpaceInStr(rowString[0])
							.split(" ").length);
		}

		if (columns == 0)
			return null;

		stringMap = new String[rowString.length][columns];

		String[] temp;
		for (int i = 0; i < rows; i++) {// 对stringMap 进行填充
			temp = trimRedundancyWhiteSpaceInStr(rowString[i].trim())
					.split(" ");

			for (int j = 0; j < columns; j++) {
				if (j >= temp.length) {
					stringMap[i][j] = " ";
				} else {
					stringMap[i][j] = temp[j];
				}
			}

		}

		return stringMap;

	}

	/**
	 * 得出 有二维图表格式的字符串 行(row) 列(column)的字符串值
	 * 
	 * @param sourceString
	 * @param column
	 * @param row
	 * @return
	 */
	public static String getTheCellStringIn2D(String sourceString, int row,
			int column) {

		if (sourceString == null || sourceString.trim().length() == 0)
			return null;
		if (column == -1 || row == -1)
			return null;
		String[][] stringMap = getStringMapIn2D(sourceString);

		if (stringMap == null || stringMap.length == 0)
			return null;
		if (stringMap[0].length == 0 || stringMap[0].length <= column)
			return null;
		if (stringMap.length <= row)
			return null;

		return stringMap[row][column];

	}

	/**
	 * 从stringMap中获得一列
	 * 
	 * @param sourceString
	 * @param column
	 * @return
	 */
	public static String[] getTheColumnStringIn2D(String sourceString,
			int column) {

		if (sourceString == null || sourceString.trim().length() == 0)
			return null;
		if (column == -1)
			return null;
		String[][] stringMap = getStringMapIn2D(sourceString);
		if (stringMap == null || stringMap.length == 0)
			return null;
		if (stringMap[0].length == 0 || stringMap[0].length <= column)
			return null;
		String[] columnArray = new String[stringMap.length];

		for (int i = 0; i < columnArray.length; i++) {
			columnArray[i] = stringMap[i][column];
		}

		return columnArray;

	}

	/**
	 * 判断字符串是否符合ip地址格式（ipV4）
	 * 
	 * @param ipStr
	 * @return
	 */
	public static boolean isIpFormat(String ipStr) {
		if (ipStr == null || ipStr.length() == 0)
			return false;

		Pattern p = Pattern
				.compile("^([1-9]|[1-9]\\d|1\\d{2}|2[0-1]\\d|22[0-3])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}$");

		Matcher m = p.matcher(ipStr);

		return m.matches();
	}

	/**
	 * 判断是否是简写形式
	 * 
	 * @param shortType
	 * @return
	 * @throws Exception
	 */
	public static boolean isShortTypeFormat(String shortType) {
		if (shortType.indexOf("/") == -1)
			return false;
		String[] split = shortType.split("/");
		if (split.length != 2)
			return false;
		int number = 0;
		try {
			number = Integer.parseInt(split[1]);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return isIpFormat(split[0]) && number > 0 && number < 32;
	}

	/**
	 * 判断字符串是否是子网掩码格式(ipv4)
	 * 
	 * @param netMask
	 * @return
	 */
	public static boolean isNetMask(String netMask) {
		// System.out.println("netMask::"+netMask);
		if (netMask == null || netMask.length() == 0)
			return false;

		Pattern p = Pattern
				.compile("^(254|252|248|240|224|192|128|0)\\.0\\.0\\.0"
						+ "|"
						+ "255\\.(254|252|248|240|224|192|128|0)\\.0\\.0"
						+ "|"
						+ "255\\.255\\.(254|252|248|240|224|192|128|0)\\.0"
						+ "|"
						+ "255\\.255\\.255\\.(255|254|252|248|240|224|192|128|0)$");

		Matcher m = p.matcher(netMask);

		return m.matches();
	}

	/**
	 * 将给定的source字符串转换为二进制字符串
	 * 
	 * @param source
	 * @return
	 */
	public static String getBinaryStr(String source) {

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < source.length(); i++) {
			char c = source.charAt(i);
			sb.append(binaryStr(c));

		}
		// System.out.println("source:::"+source);
		// System.out.println("2jinzhi："+sb.toString());
		return sb.toString();
	}

	/**
	 * 将字符c(16进制中允许的字符)转换为二进制字符串
	 * 
	 * @param c
	 * @return
	 */
	public static String binaryStr(char c) {
		String num = "";
		switch (Character.toUpperCase(c)) {
		case '0':
			num = "0000";
			break;
		case '1':
			num = "0001";
			break;
		case '2':
			num = "0010";
			break;
		case '3':
			num = "0011";
			break;
		case '4':
			num = "0100";
			break;
		case '5':
			num = "0101";
			break;
		case '6':
			num = "0110";
			break;
		case '7':
			num = "0111";
			break;
		case '8':
			num = "1000";
			break;
		case '9':
			num = "1001";
			break;
		case 'A':
			num = "1010";
			break;
		case 'B':
			num = "1011";
			break;
		case 'C':
			num = "1100";
			break;
		case 'D':
			num = "1101";
			break;
		case 'E':
			num = "1110";
			break;
		case 'F':
			num = "1111";
			break;
		default:
			return null;
		}

		return num;
	}

	/**
	 * 将字符c
	 * 
	 * @param c
	 * @return
	 */
	public static int num(char c) {
		int num = 0;
		switch (c) {
		case '0':
			num = 0;
			break;
		case '1':
			num = 1;
			break;
		case '2':
			num = 2;
			break;
		case '3':
			num = 3;
			break;
		case '4':
			num = 4;
			break;
		case '5':
			num = 5;
			break;
		case '6':
			num = 6;
			break;
		case '7':
			num = 7;
			break;
		case '8':
			num = 8;
			break;
		case '9':
			num = 9;
			break;
		case 'A':
			num = 10;
			break;
		case 'B':
			num = 11;
			break;
		case 'C':
			num = 12;
			break;
		case 'D':
			num = 13;
			break;
		case 'E':
			num = 14;
			break;
		case 'F':
			num = 15;
			break;

		default:
			return -1;
		}

		return num;
	}

	/**
	 * 从ip的16进制表示法中得到常用的10进制字符串代表，如：
	 * 
	 * @param hex
	 * @return
	 * @throws Exception
	 */
	public static String getIpFromHex(String hex) throws Exception {
		if (hex == null || hex.trim().length() == 0) {
			throw new Exception("hex:" + hex + " 为空");
		}

		hex = hex.toUpperCase();

		String[] split = hex.split(":");

		if (split == null || split.length != 4) {
			throw new Exception("hex:" + hex + " 格式不对");
		}

		StringBuilder result = new StringBuilder();
		String s = null;
		int hexNum1 = 0, num1 = 0, num2 = 0;
		for (int i = 0; i < split.length; i++) {
			s = split[i].trim();
			if (s.length() < 2)
				throw new Exception("hex:" + hex + " 格式不对");
			num1 = num(s.charAt(0));
			num2 = num(s.charAt(1));
			if (num1 < 0 || num2 < 0)
				throw new Exception("hex:" + hex + " 格式不对");
			hexNum1 = num1 * 16 + num2;
			result.append(hexNum1);
			if (i < split.length - 1)
				result.append(".");
		}

		return result.toString();
	}

	/**
	 * 根据给定的二进制字符串得出该字符串所代表的数值
	 * 
	 * @param binary
	 * @return
	 */
	public static int getIntValueByBinaryString(String binary) {
		// System.out.println("binary:"+binary);
		if (binary == null || binary.length() == 0)
			return 0;
		int sum = 0;
		int length = binary.length();
		for (int i = 0; i < length; i++) {
			if (binary.charAt(i) != '0' && binary.charAt(i) != '1')
				try {
					throw new Exception("您输入的不是二进制字符串:" + binary);
				} catch (Exception e) {
					e.printStackTrace();
				}

			if (binary.charAt(i) == '1') {
				sum += Math.pow(2, length - 1 - i);
			}
		}

		return sum;
	}

	/**
	 * 将非负整数转换为二进制字符串
	 * 
	 * @param num
	 * @return
	 * @throws Exception
	 */
	public static String getBinaryString(String num) throws Exception {
		if (num == null || num.trim().length() == 0)
			return null;
		for (int i = 0; i < num.length(); i++) {
			if (!Character.isDigit(num.charAt(i))) {
				throw new Exception("您输入的不是非负整数型字符串:" + num);
			}
		}

		int numValue = Integer.parseInt(num);
		String trans = Integer.toBinaryString(numValue);
		int feedNum = 8 - trans.length() % 8;
		StringBuilder returnVar = new StringBuilder();

		if (feedNum != 8) {
			for (int i = 0; i < feedNum; i++) {
				returnVar.append("0");
			}
		}

		returnVar.append(trans);
		return returnVar.toString();
	}

	/**
	 * 根据ip地址与 掩码得到该子网中的可用ip
	 * 
	 * @deprecated
	 * @param ip
	 * @param netMask
	 * @return
	 * @throws Exception
	 */
	public static String[] _getIPsV4InSubnet(String ip, String netMask)
			throws Exception {
		if (!isIpFormat(ip))
			throw new Exception("ip:" + ip + " 格式不正确");
		if (!isNetMask(netMask))
			throw new Exception("mask:" + netMask + " 掩码格式不正确");

		String prefixIp = ip.substring(0, ip.lastIndexOf(".") + 1);

		String[] netMaskElements = netMask.split("\\.");
		int length = netMaskElements.length;
		int index = 3;// 记录掩码中不全为255
		String flagString = "255";// 用于记录掩码中不全为255的值
		for (int i = 0; i < length; i++) {
			if (!netMaskElements[i].equals("255")) {
				index = i;
				flagString = netMaskElements[i];
			}
		}

		if (index != 3)
			throw new Exception("搜寻的网络过于庞大");
		// flagString = getIntValueByBinaryString(flagString)+"";//转换为二进制字符串
		int baseNum = Integer.parseInt(ip.substring(ip.lastIndexOf(".") + 1)
				.trim()) & Integer.parseInt(netMaskElements[index]);
		flagString = getBinaryString(flagString);
		// System.out.println(flagString);
		int bitNum = 0;
		for (int i = 0; i < flagString.length(); i++) {
			if (flagString.charAt(i) == '0') {
				bitNum = flagString.length() - i;
				break;
			}
		}

		int max = (int) Math.pow(2, bitNum);
		// System.out.println("max::"+max);
		// count = count-1;
		String[] ips = new String[max];

		for (int i = 0; i < max; i++) {
			// if((baseNum+i)==0)
			ips[i] = prefixIp + (baseNum + i);
			// System.out.println(ips[i]);
		}

		return ips;

	}



	/**
	 * 根据数据的二进制
	 * 
	 * @param bitNum
	 * @return
	 */
	public static int getMaxValueByBitNum(int bitNum) {
		int base = 0;
		for (int i = bitNum; i > 0; i++) {
			base += Math.pow(2, bitNum - 1);
		}
		return base;
	}

	/**
	 * 根据位数得到mask的ip表示串
	 * 
	 * @param maskNum
	 * @return
	 */
	public static String getMaskIPStr(int maskNum) {
		StringBuilder sb = new StringBuilder();
		int leftNum = 0;
		int fullNum = maskNum / 8;
		int mod = maskNum % 8;
		for (int i = 0; i < fullNum; i++) {
			sb.append("255");
			if (i < 3)
				sb.append(".");

		}
		int modNum = getMaxValueByBitNum(mod);
		leftNum = 4 - fullNum;
		leftNum--;
		sb.append(modNum);
		for (int i = 0; i < leftNum; i++) {
			sb.append(".");
			sb.append("0");
		}

		return sb.toString();
	}

	/**
	 * @param ip
	 * @param maskNum
	 * @return
	 */
	public static String getNetNOBy(String ip, int maskNum) {
		String mask = getMaskIPStr(maskNum);
		System.out.println("mask:" + mask);
		StringTokenizer st = new StringTokenizer(ip, ".");
		StringTokenizer maskSt = new StringTokenizer(mask, ".");
		StringBuilder result = new StringBuilder();
		while (st.hasMoreTokens()) {
			int i = Integer.parseInt(st.nextToken().trim());
			int m = Integer.parseInt(maskSt.nextToken().trim());
			result.append(i & m);
			result.append(".");
		}
		if (result.charAt(result.length() - 1) == '.') {
			result.delete(result.lastIndexOf("."), result.length());
		}

		return result.toString();
	}

	/**
	 * 根据掩码位数获得掩码二进制字符串
	 * 
	 * @param maskNum
	 *            掩码位数
	 * @param ipVersion
	 *            0 为ipv4 1为ipv6
	 * @return
	 */
	public static String getMaskBinaryStr(int maskNum, int ipVersion) {
		StringBuilder sb = new StringBuilder();
		int maxNum = 0;
		if (ipVersion == IP_V4) {// IPv4
			maxNum = 32;
		} else if (ipVersion == IP_V6) {// IPv6
			maxNum = 64;
		} else {
			return null;
		}
		for (int i = 0; i < maxNum; i++) {
			if (i < maskNum) {
				sb.append(1);
			} else {
				sb.append(0);
			}
		}
		return sb.toString();
	}

	/**
	 * 得到str中第一个为数字字符的索引位置
	 * 
	 * @param str
	 * @return
	 */
	public static int getFirstIndexOfNumberInStr(String str) {
		if (str == null || str.trim().length() == 0)
			return -1;

		for (int i = 0; i < str.length(); i++) {
			if (Character.isDigit(str.charAt(i))) {
				// System.out.println("index::"+i);
				return i;
			}
		}

		return -1;
	}
	
    public static String get32UUID() {
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return uuid;
    }

	
	public static void main(String[] args) {

	}

	public  static void  logError(Logger logger ,DeviceInfoBean device,String msg)
	{
		logger.error("设备id："+device.getDeviceId()+",设备name："+device.getDeviceName()
				+","+msg);
	}
	public static boolean isEmpty(String paramsStr) {

		if (paramsStr == null || "".equals(paramsStr.trim()))
			return true;
		return false;
	}
	
	public  static String getMesage(DeviceInfoBean deviceInfo,String msg)
	{
		StringBuffer  sb = new  StringBuffer();
		sb.append("设备id=").append(deviceInfo.getDeviceId()).append(",deviceName=")
		.append(deviceInfo.getDeviceName()).append(",facotry=").append(deviceInfo.getFactory())
		.append(",").append(msg);
		return sb.toString();
	}
}
