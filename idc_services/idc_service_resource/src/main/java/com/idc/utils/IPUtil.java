package com.idc.utils;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 计算ip的工具类
 *
 * @author yangDS
 */
public class IPUtil {
    private static Logger logger = Logger.getLogger(IPUtil.class);

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
        } else if (isIPAddNum(ip)) {
            String temps[] = ip.split("-");
            String num[] = temps[0].replace(".", ",").split(",");
            int index = Integer.parseInt(num[3]);
            int end = Integer.parseInt(temps[1]);
            ips = new String[end - index + 1];
            for (int i = index; i <= end; i++) {
                ips[i - index] = num[0] + "." + num[1] + "." + num[2] + "." + i
                        + "";
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
            } else {
                return false;
            }
        } else {
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
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 计算ip或mask转换为2进制后的整数值(只支持IPv4)
     *
     * @param ip ip字符串
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
     * IPv4 从ip整数值得出对应的ip字符串
     *
     * @param value
     * @return
     */
    private static String getIPByIntValue(int value) {
        StringBuilder result = new StringBuilder();
        int val1 = value;
        for (int i = 0; i < 4; i++) {
            val1 = (val1 >> (8 * (3 - i))) & 0xff;// 与255可以让数值变为正数
            result.append(val1);
            if (i != 3)
                result.append(".");
            val1 = value;
        }
        return result.toString();
    }

    /**
     * 根据起始ip 和结束ip 计算出中间的ip
     *
     * @param startIP
     * @param endIP
     * @return
     */
    public static String[] _getIPArraysBetween(String startIP, String endIP) {
        if (startIP == null || startIP.trim().length() == 0 || endIP == null
                || endIP.trim().length() == 0)
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

    public static int _getIPAddCount(String ip, String mask)
            throws Exception {
        // 首先要判断给定的ip是不是子网ip
        if (!isIPFormat(ip))
            throw new Exception("bad ip format");
        if (!isNetMask(mask))
            throw new Exception("bad mask format");
        String[] ipSplit = ip.split("\\.");
        String[] maskSplit = mask.split("\\.");
        /* 找出掩码中第一个不为255的作为标识 */
        int flagIndex = -1;
        int flagValue = 0;
        for (int i = 0; i < maskSplit.length; i++) {
            if (!maskSplit[i].trim().equals("255")) {
                flagIndex = i;
                flagValue = Integer.valueOf(maskSplit[i].trim());
                break;
            }
        }
        if (flagIndex == -1) {// 说明是主机ip
            return 1;
        }
        int ipFlag = Integer.valueOf(ipSplit[flagIndex].trim());
        int temp = ~flagValue & ipFlag & 0xff;// 如果temp>0，则说明是ip，否则是网络号
        String flagString = getBinaryString(maskSplit[flagIndex].trim());
        int bitNum = 0;
        for (int i = 0; i < flagString.length(); i++) {
            if (flagString.charAt(i) == '0') {
                bitNum = flagString.length() - i;
                break;
            }
        }
		/* 计算该网络的ip地址总个数 */
        int max = (int) Math.pow(2, 8 * (3 - flagIndex) + bitNum);
        // System.out.println("bitNum="+bitNum);
        // System.out.println("max="+max);
		/* 如果用户给出的是ip地址而非网络号，则需要减掉ip地址之前的地址 */
        // int delete = temp>>8*(3-flagIndex);
        int startIPValue = getIPIntValue(ip);
        int maskIPValue = getIPIntValue(mask);
        // System.out.println("maskIPValue="+maskIPValue);
        int delete = startIPValue & (~maskIPValue);
        // System.out.println(~maskIPValue);
        // System.out.println("delete="+delete);
        int count = max - delete - 1;
        return count;

    }


    public static String[] _getIPArrays(String ip, String mask)
            throws Exception {
        // 首先要判断给定的ip是不是子网ip
        if (!isIPFormat(ip))
            throw new Exception("bad ip format");
        if (!isNetMask(mask))
            throw new Exception("bad mask format");
        String[] ipSplit = ip.split("\\.");
        String[] maskSplit = mask.split("\\.");
		/* 找出掩码中第一个不为255的作为标识 */
        int flagIndex = -1;
        int flagValue = 0;
        for (int i = 0; i < maskSplit.length; i++) {
            if (!maskSplit[i].trim().equals("255")) {
                flagIndex = i;
                flagValue = Integer.valueOf(maskSplit[i].trim());
                break;
            }
        }
        if (flagIndex == -1) {// 说明是主机ip
            return new String[]{ip};
        }
        int ipFlag = Integer.valueOf(ipSplit[flagIndex].trim());
        int temp = ~flagValue & ipFlag & 0xff;// 如果temp>0，则说明是ip，否则是网络号
        String flagString = getBinaryString(maskSplit[flagIndex].trim());
        int bitNum = 0;
        for (int i = 0; i < flagString.length(); i++) {
            if (flagString.charAt(i) == '0') {
                bitNum = flagString.length() - i;
                break;
            }
        }
		/* 计算该网络的ip地址总个数 */
        int max = (int) Math.pow(2, 8 * (3 - flagIndex) + bitNum);
        // System.out.println("bitNum="+bitNum);
        // System.out.println("max="+max);
		/* 如果用户给出的是ip地址而非网络号，则需要减掉ip地址之前的地址 */
        // int delete = temp>>8*(3-flagIndex);
        int startIPValue = getIPIntValue(ip);
        int maskIPValue = getIPIntValue(mask);
        // System.out.println("maskIPValue="+maskIPValue);
        int delete = startIPValue & (~maskIPValue);
        // System.out.println(~maskIPValue);
        // System.out.println("delete="+delete);
        int count = max - delete - 1;
        String[] ipList = null;
        if (temp > 0) {// 说明是ip
            ipList = new String[count];
            for (int i = 0; i < count; i++) {
                ipList[i] = getIPByIntValue(startIPValue + i);
            }
        } else {// 说明是网络号
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
     * @param ipStr 需要验证是否为ip地址的字符串
     * @return true，该字符串是ip地址格式，false,该字符串不是ip地址格式
     */
    public static boolean isIPFormat(String ipStr) {
        if (null == ipStr || 0 == ipStr.length()) {
            logger.warn("ip地址信息不能为空");
            return false;
        }
        Pattern p = Pattern
                .compile("^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                        + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                        + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                        + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$");
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
        if (null == netMask || 0 == netMask.length()) {
            logger.warn("子网掩码地址不能为空");
            return false;
        }
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
     * 将非负整数转换为二进制字符串
     *
     * @param num 需要进行二进制转换字符串
     * @return 8位长度二进制字符串
     */
    public static String getBinaryString(String num) {
        if (null == num || 0 == num.trim().length()) {
            logger.warn("由于需要转换的数据为空，因此无法将其转换为二进制字符串");
            return null;
        }
        for (int i = 0; i < num.length(); i++) {
            if (!Character.isDigit(num.charAt(i))) {
                logger.warn("需要转换的数据为不是非负整数型字符串:" + num);
                return null;
            }
        }
        // 空以及正整数已验证
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
     * 将IP地址装换成十进制数 add by liuxh 2012-03-07
     *
     * @param ip ip地址字符串
     * @return 验证结果：-1表示不符合ip地址
     */
    public static long ipToLong(String ip) {
        if (!isIPFormat(ip)) {
            logger.warn("验证数据参数不符合IP地址格式，参数：ip = " + ip);
            return -1L;
        }
        int firstDot = ip.indexOf(".");
        int secDot = ip.indexOf(".", firstDot + 1);
        int thirdDot = ip.indexOf(".", secDot + 1);
        String firstStr = ip.substring(0, firstDot);
        String secStr = ip.substring(firstDot + 1, secDot);
        String thirdStr = ip.substring(secDot + 1, thirdDot);
        String fourthStr = ip.substring(thirdDot + 1, ip.length());
        long firstLong = Long.parseLong(firstStr);
        firstLong = firstLong << 24;
        long secLong = Long.parseLong(secStr);
        secLong = secLong << 16;
        long thirdLong = Long.parseLong(thirdStr);
        thirdLong = thirdLong << 8;
        long fourthLong = Long.parseLong(fourthStr);
        return firstLong + secLong + thirdLong + fourthLong;
    }

    /**
     * 将Long 型的数字转换为IP字符串
     *
     * @param longIp long数值
     * @return
     */
    public static String longToIP(long longIp) {
        StringBuffer sb = new StringBuffer("");
        // 直接右移24位
        sb.append(String.valueOf((longIp >>> 24)));
        sb.append(".");
        // 将高8位置0，然后右移16位 0x00FFFFFF 16777215
        sb.append(String.valueOf((longIp & 0x00FFFFFF) >>> 16));
        sb.append(".");
        // 将高16位置0，然后右移8位 0x0000FFFF 65536
        sb.append(String.valueOf((longIp & 0x0000FFFF) >>> 8));
        sb.append(".");
        // 将高24位置0 0x000000FF 255
        sb.append(String.valueOf((longIp & 255)));
        return sb.toString();
    }

    /**
     * 将IP形式的掩码 转换为 数字形式的掩码
     *
     * @param netMask 子网掩码
     * @return
     * @see ：255.255.255.0 转换为 24的形式
     */
    public static Integer maskStrTo32BitNum(String netMask) {
        try {
            int maskBit = 0;
            String[] netMaskElements = netMask.split("\\.");
            for (int i = 0; i < netMaskElements.length; i++) {
                // 转换成8进制，然后计算1的个数；四个相加为 掩码位
                maskBit += Integer
                        .toBinaryString(Integer.parseInt(netMaskElements[i]))
                        .replace("0", "").length();
            }
            return maskBit;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据IP地址和子网掩码计算子网IP
     *
     * @param ipAddress
     * @param netMask
     * @return 192.168.70.1 255.255.255.0 返回192.168.0.0
     */
    public static String ipAndMaskToSubNetIp(String ipAddress, String netMask) {
        if (!isIPFormat(ipAddress))
            return null;
		/*
		 * if (!isNetMask(netMask)) return null;
		 */
        StringBuffer result = new StringBuffer("");
        ipAddress = ipAddress.trim();
        netMask = netMask.trim();
        String[] ipAddressElements = ipAddress.split("\\.");
        String[] netMaskElements = netMask.split("\\.");
        try {
            int size = ipAddressElements.length;
            for (int i = 0; i < size; i++) {
                int ipAddresInt = Integer.parseInt(ipAddressElements[i]);
                int maskInt = Integer.parseInt(netMaskElements[i]);
                result.append(ipAddresInt & maskInt);
                if (i < size - 1)
                    result.append(".");
            }
        } catch (Exception e) {
            return null;
        }
        return result.toString();
    }

    /**
     * 子网拆分
     *
     * @param netIp   网段地址
     * @param oldMask 拆分前掩码位
     * @param newMask 拆分后掩码位
     * @returns {Array} 拆分后的网段Array
     */
    public static List<String> subNetSplit(String netIp, int oldMask,
                                           int newMask) {
        // 第一步 将网段地址 换算成 32位的二进制数
        String netVal = ipStrTo32BitBinary(netIp);
        // alert(netVal+":length:"+netVal.length);

        // 第二步 根据掩码位 对 网段进行 位移拆分运算
        List<String> binaryArr = new ArrayList<String>();
        binaryArr.add(netVal);
        for (int i = oldMask; i < newMask; i++) {
            binaryArr = splitFun(i, binaryArr);
        }
        // 第三步 对拆分后的 32位二进制数形式的 网段 转换成 IP形式的地址
        List<String> result = new ArrayList<String>();
        for (String binar : binaryArr) {
            result.add(bitBinaryToIpStr(binar));
        }
        binaryArr = null;
        return result;
    }

    /**
     * 按位移运算拆分 子网信息
     *
     * @param index 起始位
     * @param arr   被拆分前的网段Array
     * @returns {Array} 被拆分后的网段Array
     */
    public static List<String> splitFun(int index, List<String> arr) {
        String net2 = null;
        List<String> returnArr = new ArrayList<String>();
        // String regex=new RegExp("^(\\d{"+index+"})\\d","g");
        for (String netVal : arr) {
            returnArr.add(netVal);
            net2 = netVal.replaceAll("^(\\d{" + index + "})\\d", "$11");
            returnArr.add(net2);
        }
        return returnArr;
    }

    /**
     * 根据IP/网段地址 和掩码 获取 网段内的网段开始IP值、止 网段结束IP值
     *
     * @param ip   IP/网段地址
     * @param mask 掩码 ip形式
     * @return IP/网段地址 和掩码 获取 网段内的网段开始IP值、止 网段结束IP值
     */
    public static long[] subnetIpNumRange(String ip, String mask) {
        long[] ipRange = new long[2];
        String[] ipRangStr = getNetSegBegAndEndIpAddress(ip, mask);
        if (null != ipRangStr && 2 == ipRangStr.length) {
            ipRange[0] = ipToLong(ipRangStr[0]);
            ipRange[1] = ipToLong(ipRangStr[1]);
        } else {
            return null;
        }
        return ipRange;
    }


    /**
     * 将掩码位 转换为 IP 形式的掩码
     *
     * @param ipCounts IP位
     * @return
     * @see ：ipCounts：24 转换为255.255.255.0的形式
     */
    public static String bitToMaskStr(Integer ipCounts) {
        String bitNetMask = "";
        for (int i = 0; i < ipCounts; i++)
            bitNetMask += "1";
        for (int i = 0; i < 32 - ipCounts; i++)
            bitNetMask += "0";

        return bitBinaryToIpStr(bitNetMask);

    }


    /**
     * 根据IP/网段地址 和掩码 获取 网段内的起、止 IP地址
     *
     * @param segStr IP/网段地址
     * @param mask   掩码
     * @return
     */
    public static String[] getNetSegBegAndEndIpAddress(String segStr,
                                                       String mask) {
        StringBuffer begIP = new StringBuffer();
        StringBuffer endIP = new StringBuffer();
        String[] ipAddressElements = segStr.split("\\.");
        String[] netMaskElements = mask.split("\\.");
        try {
            int size = ipAddressElements.length;
            for (int i = 0; i < size; i++) {
                int ipAddresInt = Integer.parseInt(ipAddressElements[i]);
                int maskInt = Integer.parseInt(netMaskElements[i]);
                begIP.append(ipAddresInt & maskInt);
                endIP.append((ipAddresInt | ~maskInt) & 0xff);
                if (i < size - 1) {
                    begIP.append(".");
                    endIP.append(".");
                }
            }
            return new String[]{begIP.toString(), endIP.toString()};
        } catch (Exception e) {
            logger.error("根据IP/网段地址 和掩码 获取 网段内的起、止 IP地址异常", e);
            return null;
        }
    }

    /**
     * 将IP地址转换为 32位 二进制数
     *
     * @param ipAddress IP地址
     * @return
     */
    public static String ipStrTo32BitBinary(String ipAddress) {
        String[] temps = ipAddress.split("\\.");
        String returnVal = "";
        String binary = null;
        for (String str : temps) {
            binary = Integer.toBinaryString(Integer.parseInt(str));
            if ("0".equals(binary))
                returnVal += "00000000";
            else if (binary.length() == 8) {
                returnVal += binary;
            } else {
                int len = 8 - binary.length();
                for (int x = 0; x < len; x++)
                    returnVal += "0";
                returnVal += binary;
            }
        }
        return returnVal;
    }

    /**
     * 将32位二进制数 转换成IP地址形式的字符串
     *
     * @param bitBinary 32位二进制数
     * @return
     */
    public static String bitBinaryToIpStr(String bitBinary) {
        bitBinary = bitBinary.replaceAll("(\\d{8})", ".$1");
        bitBinary = bitBinary.substring(1);
        String[] temps = bitBinary.split("\\.");
        String returnVal = "";
        for (String str : temps) {
            returnVal += "." + Integer.parseInt(str, 2);
        }
        returnVal = returnVal.substring(1);
        return returnVal;
    }

    /**
     * 根据IP地址和掩码 计算该IP所属的子网地址
     *
     * @param ipAddress IP地址
     * @param netMask   掩码
     * @return
     */
    public static String getSubNetByIpAndMask(String ipAddress, String netMask) {
        String[] ipAddressElements = ipAddress.split("\\.");
        String[] netMaskElements = netMask.split("\\.");
        try {
            String result = "";
            int size = ipAddressElements.length;
            for (int i = 0; i < size; i++) {
                int ipAddresInt = Integer.parseInt(ipAddressElements[i]);
                int maskInt = Integer.parseInt(netMaskElements[i]);
                if (i == 3)
                    result += (((ipAddresInt & maskInt) | 0));
                else
                    result += (ipAddresInt & maskInt) + ".";
            }
            return result;
        } catch (Exception e) {
            logger.error("根据IP地址和掩码 计算该IP所属的子网地址异常，ipaddress：" + ipAddress
                    + "，netMask：" + netMask, e);
            return null;
        }
    }

    /**
     * 根据其实地址和结束地址获取IP地址列表
     *
     * @param startIp
     * @param endIp
     * @return
     */
    public static List<String> getIpAddresss(String startIp, String endIp) {
        List<String> ipList = new ArrayList<String>();
        if (isEmpty(startIp)) {
            return ipList;
        } else {
            // 如果endip为空
            if (isEmpty(endIp)) {
                ipList.add(startIp);
            } else {
                getIpAddresss(startIp, endIp, ipList);
            }
        }
        return ipList;
    }

    private static void getIpAddresss(String startIp, String endIp,
                                      List<String> ipList) {

        ipList.add(startIp);
        if (startIp.equals(endIp)) {
            return;
        }
        String[] startIpArr = startIp.split("\\.");
        String[] endIpArr = endIp.split("\\.");
        int max = 0;
        int index = 0;
        for (int i = 0; i < startIpArr.length; i++) {
            if (!startIpArr[i].equals(endIpArr[i])) {
                max = (int) Math.pow(255, 4 - i);
                index = i;
                break;
            }
        }
        int temp = 3;
        String tempAddress = null;
        int addressNum;

        for (int i = 0; i < max && i < 1000; i++) {
            ipAddresspp(startIpArr, temp);
            ipList.add(getIpAddress(startIpArr));
            if (compare(startIpArr, endIpArr)) {
                break;
            }

        }

    }

    private static String getIpAddress(String[] ipAdd) {
        int index = 0;
        StringBuffer sb = new StringBuffer();
        for (String string : ipAdd) {
            if (index == 0)
                index++;
            else
                sb.append(".");
            sb.append(string);
        }

        return sb.toString();
    }

    private static boolean compare(String[] arr1, String[] arr2) {

        for (int i = 0; i < arr2.length; i++) {
            if (!arr2[i].equals(arr1[i]))

                return false;
        }
        return true;
    }

    /**
     * IP地址自增
     *
     * @param ipAdd
     */
    private static void ipAddresspp(String[] ipAdd, int index) {

        if (index < 0 || ipAdd.length <= index)
            return;
        String ipItemStr = ipAdd[index];
        Integer ipItemNum = Integer.parseInt(ipItemStr);
        if (ipItemNum == 254) {
            ipAddresspp(ipAdd, index - 1);
            ipAdd[index] = String.valueOf(0);
        } else {
            ipAdd[index] = String.valueOf(ipItemNum + 1);
        }
    }

    public static String getNetworkBygateway(String gateway) {
        if (gateway == null || !isIPFormat(gateway))

            return null;

        String[] arr = gateway.split("\\.");

        int index = 0;
        for (int i = arr.length - 1; i >= 0; i--) {
            int num = Integer.parseInt(arr[i]);
            if (num > 0) {
                arr[i] = String.valueOf(num - 1);
                break;
            } else {
                index++;
            }
        }
        if (index > 0) {
            for (int i = arr.length - index; i < arr.length; i++) {

                arr[i] = String.valueOf(254);
            }
        }
        StringBuilder sb = new StringBuilder();
        index = 0;
        for (String string : arr) {
            if (index == 0)
                index++;
            else
                sb.append(".");
            sb.append(string);
        }

        return sb.toString();
    }

    public static void main(String[] args) {

		/*List<String> ipList = getIpAddresss("192.168.1.1", "192.168.1.2");
		
		for (String string : ipList) {
			
			System.out.println(string);
		}*/
		
	/*	
		 try {
			 
			 String mask = bitToMaskStr(24);
			 System.out.println(mask);
			 
			 
			 
			 
			String[] result = _getIPArrays("10.169.79.0", mask);
			for (String string : result) {
				System.out.println(string);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

        //	System.out.println(getNetworkBygateway("129.0.1.0"));
		
		
		
		/*String ga= bitToMaskStr(30);
		
		System.out.println(ga);
		
		
		long[] ips = subnetIpNumRange("111.59.144.0", bitToMaskStr(26));
		
	   String[] arr = getNetSegBegAndEndIpAddress("111.59.144.0", bitToMaskStr(26));*/
	/*   System.out.println("忧伤的分割线");
	   for (String string : arr) {
		
		   System.out.println(string);
		   
	}*/

        // System.out.println(getSubNetByIpAndMask("111.9.0.13",
        // "255.255.240.0"));
        // System.out.println(bitToMaskStr(32));
        // System.out.println(longToIP(169087778));
        // int value =getIPIntValue("192.168.70.1");
        // System.out.println(value);
        // System.out.println( Integer.toBinaryString(value));
        // String ip =
        // getIPByIntValue(-1062713855);
        // System.out.println(ip);

        // int value =getIPIntValue("255.255.255.0");
        // System.out.println( Integer.toBinaryString(value));
        // String ip =
        // getIPByIntValue(-1062713855);

		/*
		 * try { String[] ips = _getIPArraysBetween("191.10.3.100",
		 * "191.16.4.191"); for (int i = 0; i < ips.length; i++) {
		 * //System.out.println(ips[i]); } System.out.println(ips.length); }
		 * catch (Exception e) {
		 * 
		 * e.printStackTrace(); }
		 */
        // subNetSplit("192.168.70.0",24,26);
        // subnetIpNumRange("192.168.70.192",26);
        //	System.out.println(IPUtil.bitToMaskStr(24));
		
		
		/*String[] ips = getIPArrays("10.169.79.0/24");
		for (String string : ips) {
			System.out.println(string);
		}*/

        //	System.out.println(ipToLong("111.11.143.33"))	;
		 
		/*  String[] ips = getNetSegBegAndEndIpAddress("10.169.79.0", "255.255.255.128");
		  for (String string : ips) {
			
			  System.out.println(string);
		}*/


        String[] ips = getNetSegBegAndEndIpAddress("192.168.1.0", "255.255.255.0");

        System.out.println("------------");
        for (String string : ips) {

            System.out.println(string);
        }
		/*
		 try {
			String[] result = _getIPArrays("192.168.1.0", "255.255.255.0");
			
			for (String string : result) {
				
				System.out.println(string);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/


        String ga = bitToMaskStr(30);
        System.out.println(ga);
        String reverseMask = reverseMask(ga);
        System.out.println("reverse:" + reverseMask);

        System.out.println("mask:" + reverseMask(reverseMask));
    }

    public static boolean isEmpty(String str) {

        if (str == null || "".equals(str.trim())) {
            return true;
        }

        return false;
    }

    /**
     * 正掩码与反掩码之间相互转换
     *
     * @param mask
     * @return
     */
    public static String reverseMask(String mask) {
        mask = mask.trim();
        String regex = "(\\d{1,3}\\.){3}\\d{1,3}";
        if (!mask.matches(regex)) {
            throw new RuntimeException("mask格式异常：" + mask);
        }
        String[] maskArr = mask.split("\\.");
        int in = 0;
        StringBuilder sb = new StringBuilder();
        for (String arr : maskArr) {
            in = Integer.parseInt(arr);
            sb.append(255 - in).append(".");
        }
        return sb.substring(0, sb.length() - 1).toString();
    }


    public static Long[] parseIpscope(String network, String mask) {
        Long[] result = new Long[2];
        String maskTemp = null;
        if (mask.indexOf(".") == -1) {
            // 反掩码
            if ("0".equals(mask)) {
                maskTemp = IPUtil.reverseMask("0.0.0.0");
            }
            // masknum
            else {
                maskTemp = IPUtil.bitToMaskStr(Integer.parseInt(mask));
            }

        } else {
            if (mask.startsWith("0")) {
                maskTemp = IPUtil.reverseMask(mask);
            } else {
                maskTemp = mask;
            }
        }
        String[] iparr = IPUtil.getNetSegBegAndEndIpAddress(network,
                maskTemp);


        result[0] = IPUtil.ipToLong(iparr[0]);
        result[1] = IPUtil.ipToLong(iparr[1]);

        return result;
    }

    /**
     * 判断字符串是否符合IP地址格式（IPV4）
     *
     * @param ipAddress IP地址字符串
     * @return
     */
    public static boolean isIPAddress(String ipAddress) {
        if (ipAddress == null || ipAddress.length() == 0) return false;

        Pattern p = Pattern.compile("^([1-9]|[1-9]\\d|1\\d{2}|2[0-1]\\d|22[0-3])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}$");

        Matcher m = p.matcher(ipAddress);

        return m.matches();
    }

    /**
     * 转换为IP网段
     *
     * @param ipAddress IP地址
     * @param netMask   子网掩码
     * @return
     * @see ：ipAddress：192.168.0.3  netMask：255.255.255.254 转换为 192.168.0.2/31的形式
     */
    public static String toIPNetSegment(String ipAddress, String netMask) {
        if (!isIPAddress(ipAddress)) return null;
        if (!isNetMask(netMask)) return null;

        StringBuffer result = new StringBuffer("");
        StringBuffer maskBinaryString = new StringBuffer("");
        int maskCount = 0;
        ipAddress = ipAddress.trim();
        netMask = netMask.trim();
        String[] ipAddressElements = ipAddress.split("\\.");
        String[] netMaskElements = netMask.split("\\.");
        try {
            int size = ipAddressElements.length;
            for (int i = 0; i < size; i++) {
                int ipAddresInt = Integer.parseInt(ipAddressElements[i]);
                int maskInt = Integer.parseInt(netMaskElements[i]);
                result.append(ipAddresInt & maskInt);
                maskBinaryString.append(Integer.toBinaryString(maskInt));
                if (i < size - 1) {
                    result.append(".");
                } else {
                    result.append("/");
                }
            }

            for (int i = 0; i < maskBinaryString.length(); i++) {
                if (maskBinaryString.charAt(i) == '1')
                    maskCount++;
            }

            result.append(maskCount);

        } catch (Exception e) {
            //return  ipAddress+"/"+ netMask+"【无法转换】";
            return null;
        }

        return result.toString();
    }

    /**
     * 转换为IP网起始地址
     *
     * @param ipAddress IP地址
     * @param netMask   子网掩码
     * @return
     */
    public static String toIPNetStartAddress(String ipAddress, String netMask) {
        if (!isIPAddress(ipAddress)) return null;
        if (!isNetMask(netMask)) return null;

        StringBuffer result = new StringBuffer("");
        ipAddress = ipAddress.trim();
        netMask = netMask.trim();
        String[] ipAddressElements = ipAddress.split("\\.");
        String[] netMaskElements = netMask.split("\\.");
        try {
            int size = ipAddressElements.length;
            for (int i = 0; i < size; i++) {
                int ipAddresInt = Integer.parseInt(ipAddressElements[i]);
                int maskInt = Integer.parseInt(netMaskElements[i]);
                result.append(ipAddresInt & maskInt);
                if (i < size - 1)
                    result.append(".");
            }


        } catch (Exception e) {
            return null;
        }

        return result.toString();
    }

    /**
     * 转换为IP子网掩码位
     *
     * @param netMask 子网掩码
     * @return
     */
    public static Integer toIPNetMaskBit(String netMask) {
        try {
            if (!isNetMask(netMask)) return null;

            StringBuffer maskBinaryString = new StringBuffer("");
            int maskBit = 0;
            String[] netMaskElements = netMask.split("\\.");
            for (int i = 0; i < netMaskElements.length; i++) {
                int maskInt = Integer.parseInt(netMaskElements[i]);
                maskBinaryString.append(Integer.toBinaryString(maskInt));
            }

            for (int i = 0; i < maskBinaryString.length(); i++) {
                if (maskBinaryString.charAt(i) == '1')
                    maskBit++;
            }

            if (maskBit > 0)
                return maskBit;
            return null;
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * 根据IP/网段地址  和掩码 获取 网段内的网段开始IP值、止 网段结束IP值
     * @param segStr		IP/网段地址
     * @param mask			掩码 ip形式
     * @return
     */
    public static synchronized Long[] getLongIPStartEnd(String segStr,String mask){
        Long[] ipLongs = new Long[2];
        String[] ipArray = getNetSegBegAndEndIpAddress(segStr,mask);
        long ipLongStar = ipToLong(ipArray[0]);
        long ipLongEnd = ipToLong(ipArray[1]);
        ipLongs[0] = ipLongStar;
        ipLongs[1] = ipLongEnd;
        return ipLongs;
    }
}
