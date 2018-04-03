package com.idc.model;

/**
 * 机房网络信息安全责任人信息
 * Created by DELL on 2017/8/15.
 */
@Deprecated
public class HouseOfficer {
    private String name;//名称
    private String idType;//人员的证件类型
    private String id;//对应的证件号码
    private String tel;//固定电话
    private String mobile;//移动电话
    private String email;//email地址

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
