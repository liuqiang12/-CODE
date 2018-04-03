package com.JH.dgather.frame.util.json;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Project: Lema
 * Package: com.lema.common.util
 * Description:
 * Author: MaZhonghai
 * Date:2016/8/1 11:02
 */
public class JsonDateValueProcessor implements JsonValueProcessor {

    private String datePattern = "yyyy-MM-dd HH:mm:ss";

    public JsonDateValueProcessor() {
        super();
    }

    public JsonDateValueProcessor(String format) {
        super();
        if(!format.isEmpty()){
            this.datePattern = format;
        }
    }

    @Override
    public Object processArrayValue(Object value, JsonConfig jsonConfig) {
        return process(value);
    }

    @Override
    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
        return process(value);
    }

    private Object process(Object value) {
        try {
            if (value instanceof Date) {
                DateFormat sdf = new SimpleDateFormat(datePattern, Locale.UK);
                return sdf.format((Date) value);
            }

            return value == null ? "" : value.toString();
        } catch (Exception e) {
            return "";
        }

    }


    public String getDatePattern() {
        return datePattern;
    }


    public void setDatePattern(String pDatePattern) {
        datePattern = pDatePattern;
    }
}
