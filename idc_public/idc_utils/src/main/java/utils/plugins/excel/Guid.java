package utils.plugins.excel;

import utils.typeHelper.DateHelper;

import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Guid {
	/**
	 * 生成uuid
	 * 由于数据库中的字段长都为60位，故无需将间隔符去掉
	 * @return
	 */
	public static String Instance() {
		UUID uuid = UUID.randomUUID();
		String uuidStr = uuid.toString();
		uuidStr = uuidStr.replace("-", "");
		return uuidStr;
	}
	public static Long getLongUuid(){
		int max=500000000;
		long min=1;
		Random random = new Random();

		long s = random.nextInt(max)%(max-min+1) + min;
		System.out.println(s);
		return s;
	}
	//合同信息_yyyyMMDDHHmiss_id_xxx.log
	public static String getAttchUUID(String category,String attachSuffix){
		try {
			if(category == null){
                throw new Exception("类别不能为空..............");
            }
            return category+"["+ DateHelper.getsdfShortLongTimePlusCn_(new Date())+"]"+attachSuffix;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String getKhPreChar(String str){
		if(str != null){
			String firstKH = "^([^\\[]*?)\\[";
			Pattern pattern = Pattern.compile(firstKH);

			Matcher matcher = pattern.matcher(str);

			while (matcher.find()) {
				return matcher.group(1);
			}
		}
		return null;
	}

	public static String getFirstDkhPreChar(String str){
		if(str != null){
			String firstKH = "(?<=直接保存\\{).+(?=\\}.*)";
			Pattern pattern = Pattern.compile(firstKH);

			Matcher matcher = pattern.matcher(str);

			while (matcher.find()) {
				return matcher.group();
			}
		}
		return null;
	}
	public static String getCodeDkhPreChar(String str){
		if(str != null){
			String firstKH = "(?<=.?_CODE_\\().+(?=\\):_Label_.*)";
			Pattern pattern = Pattern.compile(firstKH);

			Matcher matcher = pattern.matcher(str);

			while (matcher.find()) {
				return matcher.group();
			}
		}
		return null;
	}
	public static String getLabelDkhPreChar(String str){
		if(str != null){
			String firstKH = "(?<=.?:_Label_\\().+(?=\\).*)";
			Pattern pattern = Pattern.compile(firstKH);

			Matcher matcher = pattern.matcher(str);

			while (matcher.find()) {
				return matcher.group();
			}
		}
		return null;
	}


	public static void main(String[] args) {
		getLongUuid();

		/*String s = getKhPreChar(str);
		System.out.println(s);*/
	}
}
