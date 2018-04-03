package com.idc.model;

/**
 * Created by mylove on 2016/12/9.
 */
public enum LogType {
    ADD("添加", 1000), DELETE("删除", 1001), UPDATE("修改", 1002), QUERY("查询", 1003),OTHER("其他", 1004);;
    private int ivalue;
    private String action;

    private LogType(String action, int index) {
        this.action = action;
        this.ivalue = index;
    }

    @Override
    public String toString() {
        return this.action;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getIvalue() {

        return ivalue;
    }
    public static String getNameByvalue(int value) {
        for (LogType logType : values()) {
            if(logType.getIvalue()==value){
                return logType.getAction();
            }
        }
        return "";
    }

    public void setIvalue(int ivalue) {
        this.ivalue = ivalue;
    }
}
