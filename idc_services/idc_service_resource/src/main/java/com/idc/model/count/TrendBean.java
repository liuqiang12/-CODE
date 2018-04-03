package com.idc.model.count;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 趋势统计对象
 * Created by mylove on 2017/8/14.
 */
public class TrendBean implements Serializable {
    //时间
    public Date time;
    //统计类别
    private String type;
    //统计值   Float Double Long Integer
    private Object value;
    private String timestr;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getTimestr() {
        String date_ = null;
        try {
            date_ = DateFormatUtils.format(time, "yyyy-MM-dd HH:mm:ss");
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.timestr = date_;
        return timestr;
    }
}
