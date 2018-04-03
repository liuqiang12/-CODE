package com.JH.dgather.frame.util;


/**
 * 正则工具类
 * @author yangDS
 *
 */
public class RegexUtil {
	public static String BAD_FILENAME = "[\\\\/:*?\"<>]";
	
	public static String formatFileName(String sourceName) {
		return sourceName.replaceAll(BAD_FILENAME, "_");
	}
	
	public static boolean isHexString(String source) {
		if (source.startsWith(":") || source.endsWith(":"))
			return false;
		//		Pattern p = Pattern.compile( "[0-9|a-f]{2}(:[0-9|a-f]{2}){1,}" );
		String regex = "[0-9|a-f]{2}(:[0-9|a-f]{2}){1,}";
		
		return source.matches(regex);
	}
	
	public static boolean isMacHexV2(String source) {
		
		//		Pattern p = Pattern.compile( "[0-9|a-f]{2}(:[0-9|a-f]{2}){1,}" );
		source = source.toLowerCase();
		String regex = "[0-9|a-f]{2}([\\s\\p{Zs}][0-9|a-f]{2}){5}";
		
		return source.matches(regex);
	}
	
	public static boolean isChineseHexV2(String source) {
		source = source.toLowerCase();
		String regex = "[0-9|a-f]{2}([\\s\\p{Zs}][0-9|a-f]{2}){1,}";
		return source.matches(regex);
	}
	
	public static void main(String[] args) {
		//		System.out.println( formatFileName("\\\\ssst/t") );
		
		String hex = "40:5f:2s:54:43:50:20:4c:6f:6f:70:62:61:63:6b:20:69:6e:74:65:72:66:61:63:65";
		System.out.println(isHexString(hex));
		hex = "00 06 28 61 68 FF";
		System.out.println(isChineseHexV2(hex));
	}
	
}
