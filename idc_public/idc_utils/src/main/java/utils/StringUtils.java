package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mylove on 2016/11/22.
 */
public class StringUtils {
    public static String SYS_STR_SPLIT = ",";
    public static String charset = "UTF-8";

    public static String ArrayToString(List s, String split) {
        if (s == null || s.size() == 0)
            return "";
        if (org.apache.commons.lang.StringUtils.isEmpty(split)) {
            split = SYS_STR_SPLIT;
        }
        StringBuffer sb = new StringBuffer();
        Iterator iterator = s.iterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            sb.append(next).append(split);
        }
        if (sb.length() > 0) {
            return sb.substring(0, sb.lastIndexOf(split));
        }
        return "";
    }

    public static List<Integer> ArrayToIntArray(List s) {
        List<Integer> t = new ArrayList<>();
        if (s == null || s.size() == 0)
            return t;
        StringBuffer sb = new StringBuffer();
        Iterator iterator = s.iterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            t.add(Integer.parseInt(next.toString()));
        }
        return t;
    }

    public static String ArrayToString(List s) {
        return ArrayToString(s, null);
    }

    public static List<String> StringToArray(String values) {
        List<String> t = new ArrayList<>();
        if (values == null || values.trim().length() == 0) {
            return t;
        }
        String[] split = values.split(",");
        t.addAll(Arrays.asList(split));
        return t;
    }
    
    
}
