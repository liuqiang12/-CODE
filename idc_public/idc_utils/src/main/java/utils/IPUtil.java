package utils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 计算ip的工具类
 *
 */
public class IPUtil {
	
	/**
	 * 根据218.200.163.88-89 返回对应的IP
	 * 
	 * @param ip
	 * @return
	 */
	public static String[] getIPArrays(String ip) {
		String ips[] = null;
		if (isIPFormat(ip)) {
			ips = new String[1];
			ips[0] = ip;
		}
		else
			if (isIPAddNum(ip)) {
				String temps[] = ip.split("-");
				String num[] = temps[0].replace(".", ",").split(",");
				int index = Integer.parseInt(num[3]);
				int end = Integer.parseInt(temps[1]);
				ips = new String[end - index + 1];
				for (int i = index; i <= end; i++) {
					ips[i - index] = num[0] + "." + num[1] + "." + num[2] + "." + i + "";
				}
			}
		return ips;
	}
	
	/**
	 * 判断字符串是否符合IP 218.200.163.88-89格式
	 * 
	 * @param ipAddress IP地址字符串
	 * @return
	 */
	public static boolean isIPAddNum(String ipAddress) {
		if (ipAddress == null || ipAddress.length() == 0)
			return false;
		String temp = ipAddress.toUpperCase();
		if (temp.indexOf("-") < 0)
			return false;
		String temps[] = temp.split("-");
		if (temps.length == 2) {
			if (isIPFormat(temps[0]) && isNumeric(temps[1])) {
				String IPTemp[] = temps[0].replace(".", ",").split(",");
				return IPTemp[3].compareTo(temps[1]) < 0;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	
	/**
	 * 数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 计算ip或mask转换为2进制后的整数值(只支持IPv4)
	 * @param ip  ip字符串
	 * @return 如果为-1 则说明不是正确的ip格式
	 */
	private static int getIPIntValue(String ip) {
		if (!isIPFormat(ip) && !isNetMask(ip)) {
			return -1;
		}
		
		String[] ipOcts = ip.split("\\.");
		int result = 0;
		for (int i = 0; i < ipOcts.length; i++) {
			result |= Integer.valueOf(ipOcts[i]) << (8 * (3 - i));
		}
		
		return result;
	}
	
	/**
	 * IPv4
	 * 从ip整数值得出对应的ip字符串
	 * @param value
	 * @return
	 */
	private static String getIPByIntValue(int value) {
		StringBuilder result = new StringBuilder();
		int val1 = value;
		for (int i = 0; i < 4; i++) {
			val1 = (val1 >> (8 * (3 - i))) & 0xff;//与255可以让数值变为正数
			
			result.append(val1);
			if (i != 3)
				result.append(".");
			val1 = value;
		}
		
		return result.toString();
	}
	
	/**
	 * 
	 * 根据起始ip 和结束ip 计算出中间的ip
	 * @param startIP
	 * @param endIP
	 * @return
	 */
	public static String[] _getIPArraysBetween(String startIP, String endIP) {
		if (startIP == null || startIP.trim().length() == 0 || endIP == null || endIP.trim().length() == 0)
			return null;
		if (!isIPFormat(startIP) || !isIPFormat(endIP))
			return null;
		int startValue = getIPIntValue(startIP);
		int endValue = getIPIntValue(endIP);
		int mod = endValue - startValue;
		ArrayList<String> arrays = new ArrayList<String>();
		for (int i = 0; i <= mod; i++) {
			arrays.add(getIPByIntValue(startValue + i));
		}
		
		return arrays.toArray(new String[0]);
		
	}
	
	public static String[] _getIPArrays(String ip, String mask) throws Exception {
		//首先要判断给定的ip是不是子网ip
		if (!isIPFormat(ip))
			throw new Exception("bad ip format");
		if (!isNetMask(mask))
			throw new Exception("bad mask format");
		String[] ipSplit = ip.split("\\.");
		String[] maskSplit = mask.split("\\.");
		
		/*找出掩码中第一个不为255的作为标识*/
		int flagIndex = -1;
		int flagValue = 0;
		for (int i = 0; i < maskSplit.length; i++) {
			if (!maskSplit[i].trim().equals("255")) {
				flagIndex = i;
				flagValue = Integer.valueOf(maskSplit[i].trim());
				break;
			}
		}
		if (flagIndex == -1) {//说明是主机ip
			return new String[] { ip };
		}
		int ipFlag = Integer.valueOf(ipSplit[flagIndex].trim());
		int temp = ~flagValue & ipFlag & 0xff;//如果temp>0，则说明是ip，否则是网络号
		
		String flagString = getBinaryString(maskSplit[flagIndex].trim());
		//      System.out.println(flagString);
		int bitNum = 0;
		for (int i = 0; i < flagString.length(); i++) {
			if (flagString.charAt(i) == '0') {
				bitNum = flagString.length() - i;
				break;
			}
		}
		/*计算该网络的ip地址总个数*/
		int max = (int) Math.pow(2, 8 * (3 - flagIndex) + bitNum);
		//        System.out.println("bitNum="+bitNum);
		//       System.out.println("max="+max);
		/*如果用户给出的是ip地址而非网络号，则需要减掉ip地址之前的地址*/
		//        int delete = temp>>8*(3-flagIndex);
		int startIPValue = getIPIntValue(ip);
		int maskIPValue = getIPIntValue(mask);
		//        System.out.println("maskIPValue="+maskIPValue);
		int delete = startIPValue & (~maskIPValue);
		//        System.out.println(~maskIPValue);
		//        System.out.println("delete="+delete);
		int count = max - delete - 1;
		String[] ipList = null;
		if (temp > 0) {//说明是ip
		
			ipList = new String[count];
			for (int i = 0; i < count; i++) {
				ipList[i] = getIPByIntValue(startIPValue + i);
			}
		}
		else {//说明是网络号
			ipList = new String[count - 1];
			for (int i = 0; i < count - 1; i++) {
				ipList[i] = getIPByIntValue(startIPValue + (i + 1));
			}
		}
		return ipList;
		
	}
	
	/**
	 * 判断字符串是否符合ip地址格式（ipV4）
	 * 
	 * @param ipStr
	 * @return
	 */
	public static boolean isIPFormat(String ipStr) {
		if (ipStr == null || ipStr.length() == 0)
			return false;
		
		Pattern p = Pattern
				.compile("^([1-9]|[1-9]\\d|1\\d{2}|2[0-1]\\d|22[0-3])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}$");
		
		Matcher m = p.matcher(ipStr);
		
		return m.matches();
	}
	
	/**
	 * 判断字符串是否是子网掩码格式(ipv4)
	 * 
	 * @param netMask
	 * @return
	 */
	public static boolean isNetMask(String netMask) {
		//          System.out.println("netMask::"+netMask);
		if (netMask == null || netMask.length() == 0)
			return false;
		
		Pattern p = Pattern.compile("^(254|252|248|240|224|192|128|0)\\.0\\.0\\.0" + "|"
				+ "255\\.(254|252|248|240|224|192|128|0)\\.0\\.0" + "|" + "255\\.255\\.(254|252|248|240|224|192|128|0)\\.0" + "|"
				+ "255\\.255\\.255\\.(255|254|252|248|240|224|192|128|0)$");
		
		Matcher m = p.matcher(netMask);
		
		return m.matches();
	}
	
	/**
	 * 
	 * 将非负整数转换为二进制字符串
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
	
	public static void main(String[] args) {
		//    int value =getIPIntValue("192.168.70.1");
		//    System.out.println(value);
		//    System.out.println( Integer.toBinaryString(value));
		//    String ip =
		//    getIPByIntValue(-1062713855);
		//    System.out.println(ip);
		
		//    int value =getIPIntValue("255.255.255.0");
		//    System.out.println( Integer.toBinaryString(value));
		//    String ip =
		//      getIPByIntValue(-1062713855);
		
		try {
			System.out.println(getIPIntValue("192.168.0.255"));
			System.out.println(getIPIntValue("192.168.1.0"));
			System.out.println(getIPIntValue("11.12.13.255"));
			System.out.println(getIPIntValue("11.12.14.0"));
//			String[] ips = _getIPArraysBetween("191.10.3.100", "191.16.4.191");
//			for (int i = 0; i < ips.length; i++) {
//				System.out.println(ips[i]);
//			}
//			System.out.println(ips.length);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
}
