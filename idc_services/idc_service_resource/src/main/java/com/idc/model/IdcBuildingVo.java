package com.idc.model;

import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by gaowei on 2017/5/31.
 */
public class IdcBuildingVo implements Serializable {

    @Id
    @GeneratedValue
    private Integer id; //

    @Excel(name = "机楼名")
    private String name;

    @Excel(name = "机楼编码")
    private String code;

    @Excel(name = "归属数据中心编码")
    private String locationid;

    @Excel(name = "抗震等级")
    private String seismicgrade;

    @Excel(name = "互联网出口带宽")
    private String totalbandwidth;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLocationid() {
        return locationid;
    }

    public void setLocationid(String locationid) {
        this.locationid = locationid;
    }

    public String getSeismicgrade() {
        return seismicgrade;
    }

    public void setSeismicgrade(String seismicgrade) {
        this.seismicgrade = seismicgrade;
    }

    public String getTotalbandwidth() {
        return totalbandwidth;
    }

    public void setTotalbandwidth(String totalbandwidth) {
        this.totalbandwidth = totalbandwidth;
    }
}
