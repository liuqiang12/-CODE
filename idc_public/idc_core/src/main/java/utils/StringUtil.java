package utils;

import net.sf.json.JSONArray;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串相关方法
 */
public class StringUtil {

    /**
     * 将以逗号分隔的字符串转换成字符串数组
     *
     * @param valStr
     * @return String[]
     */
    public static String[] str2Array(String valStr, String partition) {
        int i = 0;
        String TempStr = valStr;

        String[] returnStr = new String[valStr.length() + 1
                - TempStr.replace(partition, "").length()];

        valStr = valStr + partition;

        while (valStr.indexOf(partition) > 0) {
            returnStr[i] = valStr.substring(0, valStr.indexOf(partition));
            valStr = valStr.substring(valStr.indexOf(partition) + 1, valStr.length());

            i++;
        }

        return returnStr;
    }

    /**
     * 获取字符串编码
     *
     * @param str
     * @return
     */
    public static String getEncoding(String str) {
        String encode = "GB2312";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s = encode;
                return s;
            }
        } catch (Exception exception) {
        }
        encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s1 = encode;
                return s1;
            }
        } catch (Exception exception1) {
        }
        encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s2 = encode;
                return s2;
            }
        } catch (Exception exception2) {
        }
        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {
        }
        return "";
    }

    public static String trimAll(String str) {
        str = str.trim();
        Pattern pattern = Pattern.compile("\t|\r|\n");
        Matcher matcher = pattern.matcher(str);
        str = matcher.replaceAll("");

        return str;
    }

//    /**
//     * 去掉制表符
//     */
//    public static String trimTableFlag(String s) {
//        if (Tools.notEmpty(s)) {
//            String regex = "    "; // 去掉制表符
//            s = s.replaceAll(regex, "");
//        }
//        return s;
//    }

    /**
     * 将String类型的数值转换为int类型
     *
     * @param val
     * @return
     */
    public static int getIntByString(String val) {
        if (Tools.notEmpty(val)) {
            if ((val.indexOf("e") < 0)
                    && (val.indexOf("E") < 0)
                    && (val.indexOf(".") < 0)) {
                //
                return Integer.parseInt(val);
            } else {
                // Convert floating point
                return (int) (Double.parseDouble(val));
            }
        } else {
            return 0;
        }
    }

    public static String[] split(String str, String tag) {
        // 如果字符串的值为空
        if (Tools.isEmpty(str)) {
            return null;
        }

        // 如果字符串的值等于拆分标记
        if (str.equalsIgnoreCase(tag)) {
            return null;
        }

        // 如果字符串的长度为1，或者，字符串中不包含拆分标志
        int len = str.length();
        if (len == 1 || str.indexOf(tag) < 0) {
            String[] strArr = new String[1];
            strArr[0] = str;

            return strArr;
        }

        // 如果字符串第一个字符的值等于拆分标记
        String firstStr = str.substring(0, 1);
        if (firstStr.equalsIgnoreCase(tag)) {
            str = str.substring(1, len);
        }

        return str.split(tag);
    }

    /**
     * 将字符串集合转换为字符串
     * 字符串以指定分隔符分隔
     */
    public static String list2String(List<String> list, String partition) {
        if (null != list && list.size() > 0) {
            list = (List) (Tools.removeNullValue(list));

            StringBuffer bf = new StringBuffer("");

            int firstIndex = 0;

            if (Tools.isEmpty(partition)) {
                partition = ",";
            }

            for (int i = 0; i < list.size(); i++) {
                String str = list.get(i);

                if (i == firstIndex) {
                    bf.append(str);
                } else {
                    bf.append(partition).append(str);
                }
            }

            if (bf.length() > 0) {
                return bf.toString();
            }
        }

        return null;
    }

    /**
     * 将字符串数组转换为字符串
     * 字符串以指定分隔符分隔
     */
    public static String array2String(String[] array, String partition) {
        if (null != array && array.length > 0) {
            array = Tools.removeNullValue(array);

            StringBuffer bf = new StringBuffer("");

            int firstIndex = 0;

            if (Tools.isEmpty(partition)) {
                partition = ",";
            }

            for (int i = 0; i < array.length; i++) {
                String str = array[i];

                if (i == firstIndex) {
                    bf.append(str);
                } else {
                    bf.append(partition).append(str);
                }
            }

            if (bf.length() > 0) {
                return bf.toString();
            }
        }

        return null;
    }

    /**
     * 将JSONArray转换为字符串
     * 字符串以指定分隔符分隔
     * 要求：JSONArray的元素必须是字符串
     */
    public static String jsonArray2String(JSONArray jsonArray, String partition) {
        if (null != jsonArray && jsonArray.size() > 0) {
            String[] array = Tools.removeNullValueByJSONArray(jsonArray);

            StringBuffer bf = new StringBuffer("");

            int firstIndex = 0;

            if (Tools.isEmpty(partition)) {
                partition = ",";
            }

            for (int i = 0; i < array.length; i++) {
                String str = array[i];

                if (i == firstIndex) {
                    bf.append(str);
                } else {
                    bf.append(partition).append(str);
                }
            }

            if (bf.length() > 0) {
                return bf.toString();
            }
        }

        return null;
    }

}
