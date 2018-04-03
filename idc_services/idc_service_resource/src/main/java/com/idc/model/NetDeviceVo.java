package com.idc.model;

import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/6.
 */
public class NetDeviceVo extends IdcDeviceVo implements Serializable {
    @Excel(name="网络设备类别")
    private Integer ndeviceclass; //
    @Excel(name="网络设备类型")
    private Integer routtype; //
    @Excel(name="路由器名称")
    private String routname; //
    @Excel(name="网络层次")
    private String networklayer; //
    @Excel(name="登陆用户名")
    private String loginuser; //
    @Excel(name="登陆密码")
    private String loginpsw; //
    @Excel(name="特权密码")
    private String privilegepsw; //
    @Excel(name="归属厂家")
    private Integer factory; //
    @Excel(name="采集状态")
    private Integer nstatus; //
    @Excel(name="SNMP版本")
    private Integer snmpversion; //
    @Excel(name="SNMP采集端口")
    private Integer snmpport; //
    @Excel(name="SNMP采集团体字(GET)")
    private String snmpcommunity; //
    @Excel(name="SNMP采集团体字(SET)")
    private String snmpsetcommunity; //
    @Excel(name="SNMP密码")
    private String snmppassword; //
    @Excel(name="SNMP密码加密方式")
    private String snmpscttype; //
    @Excel(name="SNMP密钥加密方式")
    private String snmpkeyscttype; //
    @Excel(name="SNMP密钥")
    private String snmpkey; //
    @Excel(name="设备版本")
    private String deviceversion; //
    @Excel(name="登录模式")
    private Integer loginmodel; //
    @Excel(name="Telnet端口")
    private Integer telnetport; //
    @Excel(name="登录流程")
    private Integer telnetflowid; //
    @Excel(name="跳转流程")
    private Integer telnetjumpid; //
    @Excel(name="跳转VPN参数")
    private String telnetvpnparm; //
    @Excel(name="网络设备描述")
    private String sysdescr; //
    @Excel(name="设备运行时间")
    private Integer sysuptime; //
    @Excel(name="设备归属采集点")
    private Integer distributednodeid; //

    public Integer getNdeviceclass() {
        return ndeviceclass;
    }

    public void setNdeviceclass(Integer ndeviceclass) {
        this.ndeviceclass = ndeviceclass;
    }

    public Integer getRouttype() {
        return routtype;
    }

    public void setRouttype(Integer routtype) {
        this.routtype = routtype;
    }

    public String getRoutname() {
        return routname;
    }

    public void setRoutname(String routname) {
        this.routname = routname;
    }

    public String getNetworklayer() {
        return networklayer;
    }

    public void setNetworklayer(String networklayer) {
        this.networklayer = networklayer;
    }

    public String getLoginuser() {
        return loginuser;
    }

    public void setLoginuser(String loginuser) {
        this.loginuser = loginuser;
    }

    public String getLoginpsw() {
        return loginpsw;
    }

    public void setLoginpsw(String loginpsw) {
        this.loginpsw = loginpsw;
    }

    public String getPrivilegepsw() {
        return privilegepsw;
    }

    public void setPrivilegepsw(String privilegepsw) {
        this.privilegepsw = privilegepsw;
    }

    public Integer getFactory() {
        return factory;
    }

    public void setFactory(Integer factory) {
        this.factory = factory;
    }

    public Integer getNstatus() {
        return nstatus;
    }

    public void setNstatus(Integer nstatus) {
        this.nstatus = nstatus;
    }

    public Integer getSnmpversion() {
        return snmpversion;
    }

    public void setSnmpversion(Integer snmpversion) {
        this.snmpversion = snmpversion;
    }

    public Integer getSnmpport() {
        return snmpport;
    }

    public void setSnmpport(Integer snmpport) {
        this.snmpport = snmpport;
    }

    public String getSnmpcommunity() {
        return snmpcommunity;
    }

    public void setSnmpcommunity(String snmpcommunity) {
        this.snmpcommunity = snmpcommunity;
    }

    public String getSnmpsetcommunity() {
        return snmpsetcommunity;
    }

    public void setSnmpsetcommunity(String snmpsetcommunity) {
        this.snmpsetcommunity = snmpsetcommunity;
    }

    public String getSnmppassword() {
        return snmppassword;
    }

    public void setSnmppassword(String snmppassword) {
        this.snmppassword = snmppassword;
    }

    public String getSnmpscttype() {
        return snmpscttype;
    }

    public void setSnmpscttype(String snmpscttype) {
        this.snmpscttype = snmpscttype;
    }

    public String getSnmpkeyscttype() {
        return snmpkeyscttype;
    }

    public void setSnmpkeyscttype(String snmpkeyscttype) {
        this.snmpkeyscttype = snmpkeyscttype;
    }

    public String getSnmpkey() {
        return snmpkey;
    }

    public void setSnmpkey(String snmpkey) {
        this.snmpkey = snmpkey;
    }

    public String getDeviceversion() {
        return deviceversion;
    }

    public void setDeviceversion(String deviceversion) {
        this.deviceversion = deviceversion;
    }

    public Integer getLoginmodel() {
        return loginmodel;
    }

    public void setLoginmodel(Integer loginmodel) {
        this.loginmodel = loginmodel;
    }

    public Integer getTelnetport() {
        return telnetport;
    }

    public void setTelnetport(Integer telnetport) {
        this.telnetport = telnetport;
    }

    public Integer getTelnetflowid() {
        return telnetflowid;
    }

    public void setTelnetflowid(Integer telnetflowid) {
        this.telnetflowid = telnetflowid;
    }

    public Integer getTelnetjumpid() {
        return telnetjumpid;
    }

    public void setTelnetjumpid(Integer telnetjumpid) {
        this.telnetjumpid = telnetjumpid;
    }

    public String getTelnetvpnparm() {
        return telnetvpnparm;
    }

    public void setTelnetvpnparm(String telnetvpnparm) {
        this.telnetvpnparm = telnetvpnparm;
    }

    public String getSysdescr() {
        return sysdescr;
    }

    public void setSysdescr(String sysdescr) {
        this.sysdescr = sysdescr;
    }

    public Integer getSysuptime() {
        return sysuptime;
    }

    public void setSysuptime(Integer sysuptime) {
        this.sysuptime = sysuptime;
    }

    public Integer getDistributednodeid() {
        return distributednodeid;
    }

    public void setDistributednodeid(Integer distributednodeid) {
        this.distributednodeid = distributednodeid;
    }
}
