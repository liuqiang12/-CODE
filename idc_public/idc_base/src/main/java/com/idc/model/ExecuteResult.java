package com.idc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mylove on 2016/12/3.
 * 方法执行结果
 */
public class ExecuteResult implements Serializable {
    private boolean state;//状态
    /***
     * 信息
     */
    private String msg;//信息
    private Map<String, Object> infos = new HashMap<String, Object>();//存放一些其他信息
    //    @JsonSerialize()
    @JsonIgnore
    private Exception e;//异常信息

    public Exception getE() {
        return e;
    }

    public void setE(Exception e) {
        this.e = e;
    }

    public Map<String, Object> getInfos() {

        return infos;
    }

    public void setInfos(Map<String, Object> infos) {
        this.infos = infos;
    }

    /***
     * Add参数
     * @param name
     * @param value
     */
    public void addInfo(String name, Object value) {
        this.infos.put(name, value);
    }

    public String getMsg() {

        return msg;
    }

    /***
     * 设置信息
     * @param msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isState() {

        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "ExecuteResult [state = " + this.state + ",msg = " + this.msg + ",infos = " + this.infos + ",e = " + this.e + " ]";
    }
}
