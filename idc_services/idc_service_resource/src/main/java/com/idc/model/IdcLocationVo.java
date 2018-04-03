package com.idc.model;

import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * Created by gaowei on 2017/5/31.
 */
public class IdcLocationVo implements Serializable {
    @Excel(name = "数据中心名称")
    private String name;

    @Excel(name = "具体地址")
    private String address;

    @Excel(name = "数据中心编码")
    private String code;

    @Excel(name = "所在省份")
    private String province;

    @Excel(name = "所在地市")
    private String city;

    @Excel(name = "邮编")
    private String zipcode;
    @Excel(name = "传真")
    private String contactfax;

    @Excel(name = "备注")
    private String remark;

    @Excel(name = "互联网出入口带宽")
    private String totalbandwidth;//gb

    @Excel(name = "网关IP地址")
    private String gatewayip;

    @Excel(name = "额定电量")
    private String ratedcapacity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getContactfax() {
        return contactfax;
    }

    public void setContactfax(String contactfax) {
        this.contactfax = contactfax;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTotalbandwidth() {
        return totalbandwidth;
    }

    public void setTotalbandwidth(String totalbandwidth) {
        this.totalbandwidth = totalbandwidth;
    }

    public String getGatewayip() {
        return gatewayip;
    }

    public void setGatewayip(String gatewayip) {
        this.gatewayip = gatewayip;
    }

    public String getRatedcapacity() {
        return ratedcapacity;
    }

    public void setRatedcapacity(String ratedcapacity) {
        this.ratedcapacity = ratedcapacity;
    }
}
