package com.idc.model;

import java.io.Serializable;
/**
 * 域名信息
 * Created by DELL on 2017/8/15.
 */
public class ISPDomain implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long aid;
    private String  name;//	对应该域名ID的域名

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
