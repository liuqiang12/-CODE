package utils.typeHelper;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Administrator 字符串方法
 */
public class StringHelper {
	private static StringHelper instance;

	private StringHelper() {
	}

    /**
	 * 单例初始化实体类
	 * @return
	 */
	public static synchronized StringHelper getInstance() {
		if (instance == null) {
			instance = new StringHelper();
		}
		return instance;
	}
	//字符串补全 0
	public static String LPADStr(Integer intStr){
		 String numStr=String.format("%02d",intStr);
		 return numStr;
	}
	public static boolean isNullOrEmpty(String str){
		return str==null || str.trim().isEmpty();
	}

	/**
	 * 删除起始字符
	 * @param
	 * @return
	 *  @author xxj 2017年4月27日
	 */
	public static String trimStart(String str,String trim){
		if(str==null)
			return null;
		return str.replaceAll("^("+trim+")+", "");
	}
	/**
	 * 删除末尾字符
	 * @param
	 * @return
	 *  @author xxj 2017年4月27日
	 */
	public static String trimEnd(String str,String trim){
		if(str==null)
			return null;
		return str.replaceAll("("+trim+")+$", "");
	}


	//获取月份中文[主要是月份]
	public String getChineseStrWithNum(String numstr){
		String result = null;
		if(numstr != null && !"".equals(numstr)){
			Integer numint = Integer.valueOf(numstr);
			switch (numint) {
			case 1:
				result = "一月";
				break;
			case 2:
				result = "二月";
				break;
			case 3:
				result = "三月";
				break;
			case 4:
				result = "四月";
				break;
			case 5:
				result = "五月";
				break;
			case 6:
				result = "六月";
				break;
			case 7:
				result = "七月";
				break;
			case 8:
				result = "八月";
				break;
			case 9:
				result = "九月";
				break;
			case 10:
				result = "十月";
				break;
			case 11:
				result = "十一月";
				break;
			case 12:
				result = "十二月";
				break;
			default:
				break;
			}
		}
		return result;
	}



	/**
	 * 以"/"字符开头
	 * 去掉点以后的所有字符
	 * @param url
	 * @return
	 */
	public String antUrl(String url){
		if(url != null && !"".equals(url)){
			if(url.startsWith("/")){
				return trim(url.replaceFirst("\\..*$", ""));
			}else{
				return "/"+trim(url.replaceFirst("\\..*$", ""));
			}
		}
		return null;
	}
	
	//任何的url都获取以字符"/"开头且.do之间的所有字符
	//正则表达式
	public String subAntUrl(String url){
		
		if(url !=  null && !"".equals(url)){
			 if(!url.startsWith("/")){
				 url = "/"+url;
			 }
			 //然后获取.do的所有数据
			 String regex = "(.*\\.do).*";
			 Pattern p = Pattern.compile(regex);
			 Matcher m = p.matcher(url);
			 while(m.find()){
				 return m.group(1);
			 }
		}
		return url;
	}
	public String matchKhnr(String content,String flag){

		if(content !=  null && !"".equals(content)){
			Pattern pat = Pattern.compile("\\\\"+flag+"\\{(.*?)\\}");
			Matcher mat = pat.matcher(content);
			if (mat.find()) {
				return mat.group(1);
			} 
		}
		return null;
	}
	public static void main(String[] args) {
		System.out.println(splitTiketParams("prodInstId:9,ticketInstId:11"));
		/*StringHelper.splitBusinessKey("role_key_","role_key_idc_customer_manager@id_22");
		StringHelper.splitBusinessKey("usernmae_","usernmae_ecmp_2017@id_22");*/
		/*String str = "ASDFASD1F${ASDFASDFASD}ASDFAS1D";
		Pattern pat = Pattern.compile("\\"+"$"+"\\{(.*?)\\}");
		Matcher mat = pat.matcher(str);
		if (mat.find()) {
			System.out.println(mat.group(1));
		} 
		System.out.println(LPADStr(122));*/
//		subAntUrl("/asdfas/asdf_dodfas.do?asdfl.co");
//		if("".startsWith("/")){
//			System.out.println("1");
//		}
//		if("/".startsWith("/")){
//			System.out.println("2");
//		}
//		if("/asdf".startsWith("/")){
//			System.out.println("3");
//		}
//		if("asdfasd/".startsWith("/")){
//			System.out.println("4");
//		}
		//以字符"/"开头且去掉点后面的所有字符
//		System.out.println("/asdf/sdf".replaceFirst("^/[^/]+", "").replaceFirst("\\..*$", ""));
	}
	// 填写以/开头
	
	
	/**
	 * 去除字符串的收尾空白
	 * 给定一个含有空格的字符串,要求:去掉该字符串前导空格和尾部空格,并把字符串内部的多个空格合并成一个.输出修改后的字符串
	 * @param content
	 * @return
	 */
	public String removeNullEmpty(String content){
		String finalstr="";//定义最终要求的字符串，并赋值为空串
		if(content != null && !"".equals(content)){
			String newstr=content.trim();
			char arr[] = newstr.toCharArray();//把修改后的字符串转换成字符数组
			for(int i=0;i<arr.length;i++) {
				if(arr[i]!=' ')
				{
	 				finalstr=finalstr+arr[i];
				}
				//如果arr[i]不是空格，则把对应的字符添加到最终要求的字符串
				else if(arr[i]!=arr[i-1])
				{
	 				finalstr=finalstr+arr[i];
				} 
			}
		}
		return finalstr;
	}
	// /**
	// * 使用正则表达式进行字符串内容替换
	// * @param regularExpression 正则表达式
	// * @param sub 替换的字符串
	// * @param input 要替换的字符串
	// * @return String 替换后的字符串
	// */
	// public  synchronized String regexReplace(String regularExpression,
	// String sub, String input)
	// {
	// Pattern pattern = PatternFactory.getPattern(regularExpression);
	// Matcher matcher = pattern.matcher(input);
	// StringBuffer sb = new StringBuffer();
	// while (matcher.find())
	// {
	// matcher.appendReplacement(sb, sub);
	// }
	// matcher.appendTail(sb);
	// return sb.toString();
	// }

	/**
	 * 判断一个字符串中是否包含符合正则表达式定义的匹配条件的子串
	 * 
	 * @param regularExpression
	 *            - 正则表达式
	 * @param input
	 *            - 待检查字符串
	 * @return - 若输入字符串中包含符合正则表达式定义的匹配条件的子串，则返回true，否则返回false
	 */
	// //正则表达式匹配判断
	// public  synchronized boolean exist(String regularExpression, String
	// input)
	// {
	// Pattern pattern = PatternFactory.getPattern(regularExpression);
	// Matcher matcher = pattern.matcher(input);
	// return matcher.find();
	// }

	/**
	 * 用"0"补足一个字符串到指定长度
	 * 
	 * @param str
	 *            - 源字符串
	 * @param size
	 *            - 补足后应达到的长度
	 * @return - 补零后的结果
	 */
	public  String fillZero(String str, int size) {
		String result;
		if (str.length() < size) {
			char[] s = new char[size - str.length()];
			for (int i = 0; i < (size - str.length()); i++) {
				s[i] = '0';
			}
			result = new String(s) + str;
		} else {
			result = str;
		}
		return result;
	}

	/**
	 * 根据字符串（文件类型或者多行输入类型）获取字符串数组
	 * 
	 * @param str1
	 *            String 输入字符串
	 * @return String[] 返回结果
	 */
	public  String[] getStrArryByString(String str1) {
		if (str1.indexOf("\t") > 0) {
			for (int i = 0; i < str1.length(); i++) {
				if (str1.substring(i, i + 1).equals("\t")) {
					str1 = str1.substring(0, i) + " "
							+ str1.substring(i + 1, str1.length());
				}
			}
		}
		StringTokenizer stringTokenizer = new StringTokenizer(str1, "\r\n");
		String[] strId = new String[stringTokenizer.countTokens()];
		int i = 0;
		while (stringTokenizer.hasMoreTokens()) {
			strId[i] = stringTokenizer.nextToken();
			i++;
		}
		return strId;
	}

	/**
	 * 判断一个字符串是否为 NUll 或为空
	 * 
	 * @param inStr
	 *            inStr
	 * @return boolean
	 */
	public  boolean isValid(String inStr) {
		if (inStr == null) {
			return false;
		} else if (inStr.equals("")) {
			return false;
		} else return !inStr.equals("null");
	}

	/**
	 * 判断一个字符串是否为 NUll 或为空
	 * 
	 * @param
	 *
	 * @return boolean
	 */
	public  boolean checkNotNull(String str) {
		boolean flag = false;

		if (str != null && str.trim().length() != 0)
			flag = true;
		return flag;
	}

	/**
	 * 获得指定长度的空格
	 * 
	 * @param spaceNum
	 *            spaceNum
	 * @return String
	 */
	public  String getStrSpace(int spaceNum) {
		return getStrWithSameElement(spaceNum, " ");
	}

	/**
	 * 获得指定长度的字符串
	 * 
	 * @param num
	 *            int
	 * @param element
	 *            String
	 * @return String
	 */
	public  String getStrWithSameElement(int num, String element) {
		if (num <= 0) {
			return "";
		}

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < num; i++) {
			sb.append(element);
		}
		return sb.toString();
	}

	/**
	 * 从右或左加空格
	 * 
	 * @param strIn
	 *            String
	 * @param totalLength
	 *            int
	 * @param isRightAlign
	 *            boolean
	 * @return String
	 */
	public  String getFillString(String strIn, int totalLength,
			boolean isRightAlign) {
		int spaceLength = 0;
		String spaces = null;
		String strOut = null;

		if (strIn == null) {
			strIn = "";
		}

		spaceLength = totalLength - strIn.length();

		if (spaceLength < 0) {
			spaceLength = 0;
		}
		spaces = getStrSpace(spaceLength);

		if (isRightAlign) {
			strOut = spaces + strIn;
		} else {
			strOut = strIn + spaces;

		}
		return strOut;
	}

	/**
	 * 以String类型返回错误抛出的堆栈信息
	 * 
	 * @param t
	 *            Throwable
	 * @return String
	 */
	public  String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		t.printStackTrace(pw);
		return sw.toString();
	}

	/**
	 * 转换字符串第一个字符为大写
	 * 
	 * @param str
	 *            String
	 * @return String
	 */
	public  String getStrByUpperFirstChar(String str) {
		try {
			return str.substring(0, 1).toUpperCase() + str.substring(1);
		} catch (Exception e) {
			return "";
		}

	}

	/**
	 * 转换字符串第一个字符为小写
	 * 
	 * @param str
	 *            String
	 * @return String
	 */
	public  String getStrByLowerFirstChar(String str) {
		try {
			return str.substring(0, 1).toLowerCase() + str.substring(1);
		} catch (Exception e) {
			return "";
		}

	}

	/**
	 * 通过字符串转换成相应的整型，并返回。
	 * 
	 * @param strValue
	 *            String 待转换的字符串
	 * @return int 转换完成的整型
	 * */
	public  int getStrToInt(String strValue) {
		if (null == strValue) {
			return 0;
		}
		int iValue = 0;
		try {
			iValue = new java.lang.Integer(strValue.trim()).intValue();
		} catch (Exception ex) {
			iValue = 0;
		}
		return iValue;
	}

	/**
	 * 通过字符串转换成相应的DOUBLE，并返回。
	 * 
	 * @param strValue
	 *            String 待转换的字符串
	 * @return double 转换完成的DOUBLE
	 * */
	public  double getStrToDouble(String strValue) {
		if (null == strValue) {
			return 0;
		}
		double dValue = 0;
		try {
			dValue = Double.parseDouble(strValue.trim());
		} catch (Exception ex) {
			dValue = 0;
		}
		return dValue;
	}

	/**
	 * 通过字符串转换成相应的短整型，并返回。
	 * 
	 * @param strValue
	 *            String 待转换的字符串
	 * @return short 转换完成的短整型
	 * */
	public  short getStrToShort(String strValue) {
		if (null == strValue) {
			return 0;
		}
		short iValue = 0;
		try {
			iValue = new java.lang.Short(strValue.trim()).shortValue();
		} catch (Exception ex) {
			iValue = 0;
		}
		return iValue;
	}

	/**
	 * 通过字符串转换成相应的长整型，并返回。
	 * 
	 * @param strValue
	 *            String 待转换的字符串
	 * @return long 转换完成的长整型
	 * */
	public  long getStrToLong(String strValue) {
		if (null == strValue) {
			return 0;
		}
		long lValue = 0;
		try {
			lValue = new java.lang.Long(strValue.trim()).longValue();
		} catch (Exception ex) {
			lValue = 0;
		}
		return lValue;
	}

	public  String toLengthForEn(String str, int length) {
		if (null != str) {
			if (str.length() <= length) {
				return str;
			} else {
				str = str.substring(0, length - 2);
				str = str + "..";
				return str;
			}
		} else {
			return "";
		}
	}

	/**
	 * 降字符串转换成给定长度的字符串，如超出的话截断，并在最后以两个点结尾
	 * 
	 * @param str
	 *            String
	 * @param length
	 *            int
	 * @return String
	 */
	public  String toLengthForIntroduce(String str, int length) {
		str = delTag(str);

		byte[] strByte = str.getBytes();
		int byteLength = strByte.length;
		char[] charArray;
		StringBuffer buff = new StringBuffer();
		if (byteLength > (length * 2)) {
			charArray = str.toCharArray();
			int resultLength = 0;
			for (int i = 0; i < charArray.length; i++) {
				resultLength += String.valueOf(charArray[i]).getBytes().length;
				if (resultLength > (length * 2)) {
					break;
				}
				buff.append(charArray[i]);

			}
			buff.append("..");
			str = buff.toString();
		}

		// str = replace(str, "'", "\\'");
		str = replace(str, "\"", "\\\"");
		str = replace(str, "，", ",");
		return str;

	}

	public  String delTag(String str) {
		str = str + "<>";
		StringBuffer buff = new StringBuffer();
		int start = 0;
		int end = 0;

		while (str.length() > 0) {
			start = str.indexOf("<");
			end = str.indexOf(">");
			if (start > 0) {
				buff.append(str.substring(0, start));
			}
			if (end > 0 && end <= str.length()) {
				str = str.substring(end + 1, str.length());
			} else {
				str = "";
			}

		}
		String result = buff.toString();

		while (result.startsWith(" ")) {

			result = result.substring(result.indexOf(" ") + 1, result.length());

		}
		return result;

	}

	public  final String replace(String line, String oldString,
			String newString) {
		if (line == null) {
			return null;
		}
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = line.indexOf(oldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;

	}

	// Replace
	public  String Replace(String source, String oldString,
			String newString) {
		if (source == null) {
			return null;
		}
		StringBuffer output = new StringBuffer();
		int lengOfsource = source.length();
		int lengOfold = oldString.length();
		int posStart = 0;
		int pos;
		while ((pos = source.indexOf(oldString, posStart)) >= 0) {
			output.append(source.substring(posStart, pos));
			output.append(newString);
			posStart = pos + lengOfold;
		}
		if (posStart < lengOfsource) {
			output.append(source.substring(posStart));
		}
		return output.toString();
	}

	// 此函数前台使用中，请勿随便修改，不然会造成显示混乱(以前修改版本在下方注释中)
	public  String toHtml(String s) {
		s = Replace(s, "<", "&lt;");
		s = Replace(s, ">", "&gt;");
		s = Replace(s, "\t", "    ");
		s = Replace(s, "\r\n", "\n");
		s = Replace(s, "\n", "<br>");
		// s = Replace(s, " ", "&nbsp;");
		s = Replace(s, "'", "&#39;");
		s = Replace(s, "\"", "&quot;");
		s = Replace(s, "\\", "&#92;");
		s = Replace(s, "%", "％");
		// s = Replace(s, "&", "&amp;");
		return s;
	}

	// 逆
	public  String unHtml(String s) {

		// s = Replace(s, "&lt;", "<");
		// s = Replace(s, "&gt;", ">");
		// s = Replace(s, "    ", "\t");
		// s = Replace(s, "\n", "\r\n");
		s = Replace(s, "<br>", "\n");
		// s = Replace(s, "&nbsp;", " ");
		// s = Replace(s, "&amp;", "&");
		// s = Replace(s, "&#39;", "'");
		// s = Replace(s, "&#92;", "\\");
		// s = Replace(s, "％", "%");
		return s;
	}

	// 此函数后台使用中，请勿随便修改，不然会造成显示混乱(以前修改版本在下方注释中)
	public  String toHtmlBack(String s) {
		// 显示
		s = Replace(s, "&", "&amp;");
		s = Replace(s, "\\", "&#92;");
		s = Replace(s, "\"", "&quot;");
		s = Replace(s, "<", "&lt;");
		s = Replace(s, ">", "&gt;");
		s = Replace(s, "\t", "    ");
		s = Replace(s, "\r\n", "\n");
		// s = Replace(s, "\n", "<br>");
		// s = Replace(s, " ", "&nbsp;");
		// s = Replace(s, "'", "&#39;");
		// s = Replace(s, "%", "%");

		return s;
	}

	// 逆
	public  String unHtmlBack(String s) {
		s = Replace(s, "&lt;", "<");
		s = Replace(s, "&gt;", ">");
		s = Replace(s, "    ", "\t");
		s = Replace(s, "\n", "\r\n");
		s = Replace(s, "<br>", "\n");
		s = Replace(s, "&nbsp;", " ");
		s = Replace(s, "&amp;", "&");
		s = Replace(s, "&#39;", "'");
		s = Replace(s, "&#92;", "\\");
		s = Replace(s, "％", "%");
		return s;
	}

	/*
	 * public  String toHtml(String s) { //显示 s = Replace(s, "&",
	 * "&amp;"); s = Replace(s, "\\", "&#92;"); s = Replace(s, "\"", "&quot;");
	 * s = Replace(s, "<", "&lt;"); s = Replace(s, ">", "&gt;"); s = Replace(s,
	 * "\t", "    "); s = Replace(s, "\r\n", "\n"); // s = Replace(s, "\n",
	 * "<br>"); s = Replace(s, " ", "&nbsp;"); // s = Replace(s, "'", "&#39;");
	 * // s = Replace(s, "%", "%");
	 * 
	 * return s; }
	 * 
	 * public  String unHtml(String s) { s = Replace(s, "&lt;", "<"); s =
	 * Replace(s, "&gt;", ">"); s = Replace(s, "    ", "\t"); s = Replace(s,
	 * "\n", "\r\n"); s = Replace(s, "<br>", "\n"); s = Replace(s, "&nbsp;",
	 * " "); s = Replace(s, "&amp;", "&"); s = Replace(s, "&#39;", "'"); s =
	 * Replace(s, "&#92;", "\\"); s = Replace(s, "％", "%"); return s; }
	 */
	// 判断是否含有中文，如果含有中文返回ture
	public  boolean containsChinese(String str)
			throws UnsupportedEncodingException {

        return str.length() < (str.getBytes()).length;

        // for (int i = 0; i < username.length(); i++) {
		// String unit=Character.toString(username.charAt(i));
		// byte[] unitByte=unit.getBytes("GBK");
		// // ((unitByte[0]+256)*256 + (unitByte[1]+256)) <= 0xFEFE)
		// if (unitByte.length == 2)
		// {
		// return true;
		// }
		// }
		// return false;

	}

	public  String[] split(String str1, String str2) {
		return org.apache.commons.lang.StringUtils.split(str1, str2);
	}

	/**
	 * 
	 * <br>
	 * <b>功能：</b>将字符串转成list<br>
	 * <b>作者：</b>www.jeecg.org<br>
	 * <b>日期：</b> Oct 28, 2013 <br>
	 * 
	 * @param exp
	 *            分割符 如","
	 * @param value
	 * @return
	 */
	public  List<String> StringToList(String value, String exp) {
		List<String> resultList = new ArrayList<String>();
		String[] vals = split(value, exp);
		for (String val : vals) {
			resultList.add(val);
		}
		return resultList;
	}

	/**
	 * 
	 * <br>
	 * <b>功能：</b>数字转换成字符串<br>
	 * <b>作者：</b>www.jeecg.org<br>
	 * <b>日期：</b> Jul 30, 2013 <br>
	 * 
	 * @param arrs
	 * @return
	 */
	public  String arrayToString(String[] arrs) {
		StringBuffer result = new StringBuffer("");
		if (arrs != null && arrs.length > 0) {
			for (int i = 0; i < arrs.length; i++) {

				if (!result.toString().equals("")) {
					result.append(",");
				}
				if (arrs[i] != null && !"".equals(arrs[i].trim())) {
					result.append(arrs[i]);
				}
			}
		}
		return result.toString();
	}

	/**
	 * 
	 * <br>
	 * <b>功能：</b>数字转换成字符串<br>
	 * <b>作者：</b>www.jeecg.org<br>
	 * <b>日期：</b> Jul 30, 2013 <br>
	 * 
	 * @param arrs
	 * @return
	 */
	public  String arrayToString(Object[] arrs) {
		StringBuffer result = new StringBuffer("");
		if (arrs != null && arrs.length > 0) {
			for (int i = 0; i < arrs.length; i++) {

				if (!result.toString().equals("")) {
					result.append(",");
				}
				if (arrs[i] != null && !"".equals(arrs[i].toString().trim())) {
					result.append(arrs[i]);
				}
			}
		}
		return result.toString();
	}

	public  String left(String str, int length) {
		return org.apache.commons.lang.StringUtils.left(str, length);
	}

	/**
	 * 
	 * <br>
	 * <b>功能：</b>替换回车<br>
	 * <b>作者：</b>www.jeecg.org<br>
	 * <b>日期：</b> Oct 26, 2013 <br>
	 * 
	 * @param str
	 * @return
	 */
	public  String replaceHuiche(String str) {
		String after = str.replaceAll("\r\n", "");
		return after;
	}

	/**
	 * 根据输入的长度截取字符串，单个单词超过输入长度的强制加<BR>
	 * 换行
	 * 
	 * @param str
	 *            输入的字符串
	 * @param len
	 *            输入的长度
	 * @return 处理过后的字符串
	 */
	public  String truncateStr(String str, int len) {
		if (str != null && !("").equalsIgnoreCase(str)) {

			String strs[] = str.split(" ");
			StringBuffer buff = new StringBuffer();
			if (strs.length > 0) {
				for (int i = 0; i < strs.length; i++) {
					StringBuffer temp = new StringBuffer();
					while (strs[i].length() > len) {
						temp.append(strs[i].substring(0, len) + "<BR>");
						strs[i] = strs[i].substring(len);
					}
					temp.append(strs[i]);
					buff.append(temp.toString() + " ");
				}

			}
			return buff.toString();
		} else {
			return "";
		}
	}

	public  String truncateStr2(String str, int len) {
		if (str != null && !("").equalsIgnoreCase(str) && len != 0) {
			String strs[] = str.split(" ");

			StringBuffer buff = new StringBuffer();
			for (int i = 0; i < strs.length; i++) {
				StringBuffer temp = new StringBuffer();
				String tempstr = "";
				while (strs[i].length() > len) {
					tempstr = strs[i].substring(0, len);
					tempstr = tempstr.replaceAll(" ", "&nbsp; ");
					tempstr = tempstr.replaceAll("<", "&lt; ");
					tempstr = tempstr.replaceAll("\n", "<br> ")
							.replaceAll("\"", "&quot; ")
							.replaceAll("'", "&#39; ");
					tempstr = tempstr + "<br>";
					temp.append(tempstr);

					strs[i] = strs[i].substring(len);
				}
				tempstr = strs[i];
				tempstr = tempstr.replaceAll(" ", "&nbsp; ");
				tempstr = tempstr.replaceAll("<", "&lt; ");
				tempstr = tempstr.replaceAll("\n", "<br> ")
						.replaceAll("\"", "&quot; ").replaceAll("'", "&#39; ");

				temp.append(tempstr);
				buff.append(temp.toString() + " ");
			}

			if (buff.length() > 0)
				return buff.toString().substring(0, buff.length() - 1);
			else
				return str;
		} else {
			return "";
		}
	}

	/**
	 * 编码转换，从unicode转换为GBK
	 * 
	 * @param
	 *
	 * @return str编码转换后的字符串
	 * @throws UnsupportedEncodingException
	 */
	public  String unicodeToGB(String l_S_Source)
			throws UnsupportedEncodingException {
		String l_S_Desc = "";
		if (l_S_Source != null && !l_S_Source.trim().equals("")) {
			byte l_b_Proc[] = l_S_Source.getBytes("GBK");
			l_S_Desc = new String(l_b_Proc, "ISO8859_1");
		}
		return l_S_Desc;
	}

	/**
	 * 编码转换，从GBK转换为unicode
	 * 
	 * @param
	 *
	 * @return str 编码转换后的字符串
	 * @throws UnsupportedEncodingException
	 */
	public  String GBToUnicode(String l_S_Source)
			throws UnsupportedEncodingException {
		String l_S_Desc = "";
		if (l_S_Source != null && !l_S_Source.trim().equals("")) {
			byte l_b_Proc[] = l_S_Source.getBytes("ISO8859_1");
			l_S_Desc = new String(l_b_Proc, "GBK");
		}
		return l_S_Desc;
	}

	/**
	 * Escapes a <code>String</code> according the JavaScript string literal
	 * escaping rules. The resulting string will not be quoted.
	 * 
	 * <p>
	 * It escapes both <tt>'</tt> and <tt>"</tt>. In additional it escapes
	 * <tt>></tt> as <tt>\></tt> (to avoid <tt>&lt;/script></tt>). Furthermore,
	 * all characters under UCS code point 0x20, that has no dedicated escape
	 * sequence in JavaScript language, will be replaced with hexadecimal escape
	 * (<tt>\x<i>XX</i></tt>).
	 */
	public  String javaScriptStringEnc(String s) {
		int ln = s.length();
		for (int i = 0; i < ln; i++) {
			char c = s.charAt(i);
			if (c == '"' || c == '\'' || c == '\\' || c == '>' || c < 0x20) {
				StringBuffer b = new StringBuffer(ln + 4);
				b.append(s.substring(0, i));
				while (true) {
					if (c == '"') {
						b.append("\\\"");
					} else if (c == '\'') {
						b.append("\\'");
					} else if (c == '\\') {
						b.append("\\\\");
					} else if (c == '>') {
						b.append("\\>");
					} else if (c < 0x20) {
						if (c == '\n') {
							b.append("\\n");
						} else if (c == '\r') {
							b.append("\\r");
						} else if (c == '\f') {
							b.append("\\f");
						} else if (c == '\b') {
							b.append("\\b");
						} else if (c == '\t') {
							b.append("\\t");
						} else {
							b.append("\\x");
							int x = c / 0x10;
							b.append((char) (x < 0xA ? x + '0' : x - 0xA + 'A'));
							x = c & 0xF;
							b.append((char) (x < 0xA ? x + '0' : x - 0xA + 'A'));
						}
					} else {
						b.append(c);
					}
					i++;
					if (i >= ln) {
						return b.toString();
					}
					c = s.charAt(i);
				}
			} // if has to be escaped
		} // for each characters
		return s;
	}

	/**
	 * 将多个连续空格替换为一个,"a  b"-->"a b"
	 * 
	 * @param src
	 * @return
	 * @author WilliamLau
	 * @desc
	 */
	public  String trimContinuousSpace(String src) {
		return src.replaceAll("\\s+", " ");
	}

	public  String replace(String src, String target, String rWith,
			int maxCount) {
		return org.apache.commons.lang.StringUtils.replace(src, target, rWith,
				maxCount);
	}

	public  boolean equals(String str1, String str2) {
		return org.apache.commons.lang.StringUtils.equals(str1, str2);
	}

	public  boolean isAlphanumeric(String str) {
		return org.apache.commons.lang.StringUtils.isAlphanumeric(str);
	}

	public  boolean isNumeric(String str) {
		return org.apache.commons.lang.StringUtils.isNumeric(str);
	}

	public  String[] stripAll(String[] strs) {
		return org.apache.commons.lang.StringUtils.stripAll(strs);
	}
//	public static void main(String[] args) {
//		String reg = "(?<w>\\w+)(?="+"institutions"+")";
//		Boolean ref = regexContentWithEndChar("",reg);
//		System.out.println(ref);
//	}
	// public  void main(String[] args)
	// {
	// System.out.println(wcsUnescape("#lt;strong#gt;#lt;span style=#quot;color:#e53333;#quot;#gt;1111111111#lt;/span#gt;#lt;/strong#gt;"));
	// // String testStr = "<input > &    \\r\\n    \\n", newStr;
	// // newStr = toHtml(testStr);
	// // System.out.println(testStr);
	// // System.out.println(newStr);
	// // System.out.println(unHtml(newStr));
	// // String aaa = "中文中文中文bcdefghijk";
	//
	// // String bbb = toLengthForIntroduce(aaa,5);
	// // System.out.println(bbb);
	// // Object aa = null;
	// // String bb = "  aaaa  ";
	//
	// try
	// {
	// // System.out.println(getNotNullStr(aa));
	// // System.out.println(getNotNullStr(bb));
	// // System.out.println(containsChinese(aaa));
	// // System.out.println(containsChinese(aaa));
	// // System.out.println(containsChinese(bb));
	// //String abc = null;
	// //System.out.println(toLengthForEn(abc, 3));
	// /*String num = "05";
	// if(num.indexOf(".")==-1){
	// num = num + ".00";
	// }*/
	// // String str="<p >ksdkks </br> </p>    jkskskeeee <div>  lllll </div>";
	// // System.out.println(delTag(str));
	// //System.out.println(toFloatNumber("5.2"));
	// }
	// catch (Exception e)
	// {
	// // TODO: handle exception
	// e.printStackTrace();
	// }
	//
	// }
	//
	public  String toFloatNumber(String num) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(2);
		return nf.format(Double.parseDouble(num));
	}

	public  String toFloatNumber(Double num, int accuracy) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(accuracy);
		nf.setMinimumFractionDigits(accuracy);
		return nf.format(num);
	}

	public  String wcsUnescape(String str) {
		str = str.replace("#lt;", "<");
		str = str.replace("#gt;", ">");
		str = str.replace("#quot;", "\"");
		str = str.replace("#amp;amp;", "&");
		str = str.replace("#amp;", "&");
		str = str.replace("#039;", "'");
		return str;
	}

	/**
	 * 
	 * <br>
	 * <b>功能：</b>返回string型的字节数<br>
	 * <b>作者：</b>www.jeecg.org<br>
	 * <b>日期：</b> Sep 2, 2013 <br>
	 * 
	 * @param str
	 * @return
	 */
	public  int getByteLength(String str) {
		if (str == null) {
			return 0;
		}
		return str.getBytes().length;
	}

	/**
	 * 
	 * <br>
	 * <b>功能：</b>详细的功能描述<br>
	 * <b>作者：</b>www.jeecg.org<br>
	 * <b>日期：</b> Sep 2, 2013 <br>
	 * 
	 * @param str
	 *            字符
	 * @param limitLen
	 *            长度
	 * @return
	 */
	// public String getByteStr(String str,int limitLen){
	// StringBuffer sb = new StringBuffer();
	// char[] chars =getNotNullStr(str).toCharArray();
	// int len = 0;
	// for(char c : chars){
	// len += getByteLength(String.valueOf(c));
	// if(len<= limitLen){
	// sb.append(c);
	// }
	// }
	// return sb.toString();
	//
	// }

	/**
	 * @param content
	 *            内容
	 * @param length
	 *            指定长度。 超出这个长度就截取字符串。
	 * @param padding
	 *            超出长度后，尾加上字符，如"..."，可以为空
	 * @return 返回结果 如果内容没有超出指定的长度。则返回原字符串，超出长度后则截取到指定的长度的内容
	 */
	public  String subStr(String content, Integer length, String padding)
			throws UnsupportedEncodingException {
		String str = "";
		int paddSize = StringUtils.isBlank(padding) ? 0 : padding.length();
		// 如果内容为空，或者小于指定的长度。则返回原内容
		if (StringUtils.isBlank(content) || content.length() <= length) {
			return content;
		}
		str = content.substring(0, length - paddSize);
		if (StringUtils.isNotBlank(padding)) {
			str += padding;
		}
		return str;
	}
	public Boolean contains(String src,String str){
		if (src != null && !"".equals(src)) {
			return src.contains(str);
		}
		return false;
	}

	/**
	 * 字符串的分类处理方式
	 */
	public  String StringTokenizer(String replace, String delim, String src) {
		StringTokenizer st = new StringTokenizer(src, delim, true);
		StringBuffer sb = new StringBuffer();

		while (st.hasMoreElements()) {
			String token = st.nextToken();
			String tmp = token;
			if (token.equals(delim)) {
				tmp = replace;
			}
			sb.append(tmp);
		}
		return sb.toString();
	}

	/**
	 * 首字母大写
	 * 
	 * @param str
	 * @return
	 */
	public String firstCharUpper(String str) {
		String restr = "";
		if (!"".equals(str) && null != str) {
			char c = str.charAt(0);
			restr = (char) (c - ' ') + str.substring(1);
		}
		return restr;
	}

	/**
	 * 去掉空白
	 * 
	 * @param textContent
	 * @return
	 */
	public String trim(String textContent) {
		textContent = textContent.trim();
		while (textContent.startsWith("　")) {// 这里判断是不是全角空格
			textContent = textContent.substring(1, textContent.length()).trim();
		}
		while (textContent.endsWith("　")) {
			textContent = textContent.substring(0, textContent.length() - 1)
					.trim();
		}
		return textContent;
	}

	/**
	 * 随机生成六位数验证码
	 * 
	 * @return
	 */
	public  int getRandomNum() {
		Random r = new Random();
		return r.nextInt(900000) + 100000;// (Math.random()*(999999-100000)+100000)
	}

	/**
	 * 检测字符串是否不为空(null,"","null")
	 * 
	 * @param s
	 * @return 不为空则返回true，否则返回false
	 */
	public  boolean notEmpty(String s) {
		return s != null && !"".equals(s) && !"null".equals(s);
	}

	/**
	 * 检测字符串是否为空(null,"","null")
	 * 
	 * @param s
	 * @return 为空则返回true，不否则返回false
	 */
	public  boolean isEmpty(String s) {
		return s == null || "".equals(s) || "null".equals(s);
	}

	/**
	 * 
	 * @param
	 *
	 * @param start
	 * @return
	 */
	public String jdbcTojavaBeanName(String jdbcName, int start) {
		StringBuffer sb = new StringBuffer();

		if (jdbcName != null && !"".equals(jdbcName)) {
			jdbcName = jdbcName.toLowerCase();
			String[] tbStrs = jdbcName.split("_");
			for (int i = 0; i < tbStrs.length; i++) {
				String tmp = tbStrs[i];
				if (i >= start) {
					// if(i > 0){
					// sb.append(firstCharUpper(tmp));
					// }else{
					// }
					sb.append(tmp);
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 
	 * @param tableName
	 *            转换成实体Bean的名称【act_id_user】
	 * @param start
	 * @return
	 */
	public String dbtableTojavaBeanName(String tableName, int start) {
		StringBuffer sb = new StringBuffer();

		if (tableName != null && !"".equals(tableName)) {
			tableName = tableName.toLowerCase();
			String[] tbStrs = tableName.split("_");
			for (int i = 0; i < tbStrs.length; i++) {
				String tmp = tbStrs[i];
				if (i >= start) {
					sb.append(firstCharUpper(tmp));
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 正则表达式替换
	 * 
	 * @param content
	 * @param replaceChar
	 * @return
	 */
	public String appendReplacement(String content, String replaceChar,
			String toChar) {

		Pattern p = Pattern.compile(replaceChar);

		Matcher m = p.matcher(content);

		StringBuffer sb = new StringBuffer();

		while (m.find()) {
			m.appendReplacement(sb, toChar);
		}

		m.appendTail(sb);

		return sb.toString();
	}
	/**
	 * 获取花括号内容 (?=exp)也叫零宽度正预测先行断言，它断言自身出现的位置的后面能匹配表达式exp。
	 * (?<=exp)也叫零宽度正回顾后发断言，它断言自身出现的位置的前面能匹配表达式
	 *  参数前后去掉空格的内容
	 * @param preChar
	 * @return
	 */
	public List<String> regexBraceContent(String content,String preChar) {
//		String regexStr = "\\b(?<w>\\w+)\\s+(\\k<w>)\\b";//正则表达式 匹配重叠单词中间有空格的组
//		String regexStr = "\\b(?<w>\\w+)\\b";//正则表达式匹配所有的单词
		String regexStr = "(?<="+preChar+"\\{)\\s*\\b(?<w>\\w+)\\b\\s*(?=\\})";//正则表达式匹配括号里面内容
		
		Pattern p = Pattern.compile(regexStr);
		
		Matcher m = p.matcher(content);
		
		List<String> list = Lists.newArrayList();
		
		while (m.find()) {
			list.add(m.group(0));
		}
		
		return list;
	}
	/**
	 * 以regex结尾的字符串
	 * @param content
	 * @param
	 * @return
	 */
	public static Boolean regexContentWithEndChar(String content,String regexStr) {

		Pattern p = Pattern.compile(regexStr);
		
		Matcher m = p.matcher(content);
		
		while (m.find()) {
			return true;
		}
		
		return false;
	}
	
	
	
	/**
	 * 匹配花括号字符串 包括preChar前缀
	 * @param content
	 * @param preChar
	 * @return
	 */
	public List<String> regexBraceContentList(String content,String preChar){
		List<String> list =  Lists.newArrayList();
		String regexStr = preChar+"\\{\\s*\\b(?<w>\\w+)\\b\\s*\\}"; //匹配花括号字符串 包括preChar前缀 
		
		Pattern p = Pattern.compile(regexStr);
		
		Matcher m = p.matcher(content);
		
		while (m.find()) {
			list.add(m.group(0));
		}
		
		return list;
	}
	/**
	 * 匹配花括号字符串 包括preChar前缀
	 * @param content
	 * @param preChar
	 * @return
	 */
	public String regexBrace(String content,String preChar){
		
		String regexStr = preChar+"\\{\\s*\\b(?<w>\\w+)\\b\\s*\\}"; //匹配花括号字符串 包括preChar前缀 
		
		Pattern p = Pattern.compile(regexStr);
		
		Matcher m = p.matcher(content);
		
		StringBuffer sb = new StringBuffer();
		
		while (m.find()) {
			sb.append(m.group(0));
		}
		
		return sb.toString();
	}
	/**
	 * <p> Merge of two arrays </p>
	 * @param oldArray
	 * @param newArray
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<String> mergeArray(String[] oldArray,String[] newArray){
		List<String> list = new ArrayList<String>();
	    if(oldArray != null && oldArray.length > 0){
	    	list = new ArrayList(Arrays.asList(oldArray));
	    }
	    if(newArray != null && newArray.length > 0){
	    	list.addAll(Arrays.asList(newArray));
	    }
		return list;
	}
	/**
	 * <p>  Before and after adding point string  </p>
	 * <p>  e.g.: person     'person'  </p>
	 * @param str
	 * @return
	 */
	public String beforeAfterPointChar(String str){
		if(str != null && !"".equals(str)){
			return "'"+str+"'";
		}
		return null;
	}
	/**
	 * <p>  在此解析字符串  将下滑线改成右侧紧挨着变大写字母 </p>
	 * <p>  e.g.: if isNormal is null or false sys_person 'sysPerson' ;else  sysperson</p>
	 * @param str
	 * @param isNormal
	 * @return
	 */
	public   String reEscapeName(String str,Boolean isNormal){
		if (isNormal) {
			StringBuffer sb = new StringBuffer();
			if(notEmpty(str)){
				StringTokenizer st = new StringTokenizer(str, "_", true);
				int i = 0;
				while (st.hasMoreElements()) {
					String token = st.nextToken();
					String tmp = token;
					if (token.equals("_")) {
						tmp = "";
					}
					if (i != 0) {
						sb.append(getStrByUpperFirstChar(tmp));
					}else{
						sb.append(tmp);
					}
					i++;
				}
			}
			return sb.toString();
		}else{
			//解析字符串
			return StringTokenizer("", "_", str);
		}
	}
	//role_key_idc_customer_manager@id_22
	public static Map<String,String> splitBusinessKey(String preChar,String content){
		Map<String,String> map = new HashMap<String,String>();
		//正则表达式
		String regexStr = "(?<="+preChar+")(?<w>\\w+)(?=\\@id_)";//正则表达式匹配括号里面内容

		Pattern p = Pattern.compile(regexStr);

		Matcher m = p.matcher(content);

		List<String> list = Lists.newArrayList();

		while (m.find()) {
			list.add(m.group(0));
			System.out.println(m.group(0));
		}

		return map;
	}
	/**
	 * prodInstId:9,ticketInstId:11
	 */

	public static Map<String,Object> splitTiketParams(String businessKey){
		Map<String,Object> map = new HashMap<String,Object>();
		String[] businessKeys = businessKey.split(",");
		for(String mapContent :businessKeys){
			String[] columnJavaVals = mapContent.split(":");
			map.put(StringUtils.trim(columnJavaVals[0]),StringUtils.trim(columnJavaVals[1]));
		}
		return map;
	}
	/**
	 * 内部类 string的转义
	 * @author Administrator
	 *
	 */
	public class EscapeUtils{
		/**
	     * <p> An array of additional configuration of the escape character .</p>
	     * <p> default escape characters : "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|", "#".</p>
		 * 字符串的转义方法
		 * @param content
		 * @param escape: An array of additional configuration of the escape character:
		 * @return
		 */
		public String escape(String content,String[] escape){
			if (StringUtils.isNotBlank(content)) {  
		        String[] fbsArr = { "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|" , "#"};
		        List<String> list = mergeArray(fbsArr, escape);
		        for (String key : list) {  
		            if (content.contains(key)) {  
		            	content = content.replace(key, "\\" + key);  
		            }  
		        }
		    }  
			return content;
			
		}



	}
}
