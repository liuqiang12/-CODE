package com.idc.model;

import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/6.
 */
public class IdcMcbVo extends IdcDeviceVo implements Serializable{
    @Excel(name="MCB名称")
    private String name;
    @Excel(name="MCB使用状态")
    private Integer pwrPwrstatus;
    @Excel(name="MCBNO")
    private String pwrMcbno;
    @Excel(name="PDF机架")
    private Long pwrInstalledrackId;
    @Excel(name="客户机架")
    private Long pwrServicerackId;
    @Excel(name="MCB描述")
    private String sysdescr;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPwrPwrstatus() {
        return pwrPwrstatus;
    }

    public void setPwrPwrstatus(Integer pwrPwrstatus) {
        this.pwrPwrstatus = pwrPwrstatus;
    }

    public String getPwrMcbno() {
        return pwrMcbno;
    }

    public void setPwrMcbno(String pwrMcbno) {
        this.pwrMcbno = pwrMcbno;
    }

    public Long getPwrInstalledrackId() {
        return pwrInstalledrackId;
    }

    public void setPwrInstalledrackId(Long pwrInstalledrackId) {
        this.pwrInstalledrackId = pwrInstalledrackId;
    }

    public Long getPwrServicerackId() {
        return pwrServicerackId;
    }

    public void setPwrServicerackId(Long pwrServicerackId) {
        this.pwrServicerackId = pwrServicerackId;
    }

    public String getSysdescr() {
        return sysdescr;
    }

    public void setSysdescr(String sysdescr) {
        this.sysdescr = sysdescr;
    }
}
