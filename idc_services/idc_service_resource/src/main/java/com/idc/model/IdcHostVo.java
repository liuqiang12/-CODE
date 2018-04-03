package com.idc.model;

import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/6.
 */
public class IdcHostVo extends IdcDeviceVo implements Serializable {

    @Excel(name="主机设备类型")
    private String devicetype;
    @Excel(name="操作系统")
    private String os;
    @Excel(name="CPU描述")
    private String cpusize;
    @Excel(name="内存大小")
    private Long memsize;
    @Excel(name="硬盘大小")
    private Long disksize;
    @Excel(name="操作用户")
    private String userid;
    @Excel(name="主机设备描述")
    private String sysdescr;

    public String getDevicetype() {
        return devicetype;
    }

    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getCpusize() {
        return cpusize;
    }

    public void setCpusize(String cpusize) {
        this.cpusize = cpusize;
    }

    public Long getMemsize() {
        return memsize;
    }

    public void setMemsize(Long memsize) {
        this.memsize = memsize;
    }

    public Long getDisksize() {
        return disksize;
    }

    public void setDisksize(Long disksize) {
        this.disksize = disksize;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getSysdescr() {
        return sysdescr;
    }

    public void setSysdescr(String sysdescr) {
        this.sysdescr = sysdescr;
    }
}
