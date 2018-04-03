package com.idc.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Id;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>sys_region:区域表信息<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 22,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class SysRegion implements Serializable {

    public static final String tableName = "sys_region";

    @Id@GeneratedValue
    private Long id; //

    private String name; //   省市区县名称

    private String code; //   省市区县code

    private String remark; //   备注

    private Long parentid;//父code

    private List<SysRegion> children = new ArrayList<SysRegion>();

    private Boolean forceDefault = false;//强制设置默认选中值 标志
    private Boolean selected = false;

//    private SysRegion parent;

    /************************get set method**************************/

    public Long getId() {
        return this.id;
    }

    @Column(name = "id" , nullable =  false)
    public void setId(Long id) {
        this.id=id;
    }

    public String getName() {
        return this.name;
    }

    @Column(name = "name" , columnDefinition="省市区县名称" , nullable =  true, length = 100)
    public void setName(String name) {
        this.name=name;
    }

    public String getCode() {
        return this.code;
    }

    @Column(name = "code" , columnDefinition="省市区县code" , nullable =  true, length = 50)
    public void setCode(String code) {
        this.code=code;
    }

    public String getRemark() {
        return this.remark;
    }

    @Column(name = "remark" , columnDefinition="备注" , nullable =  true, length = 250)
    public void setRemark(String remark) {
        this.remark=remark;
    }

    public Long getParentid() {
        return parentid;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    public List<SysRegion> getChildren() {
        return children;
    }

    public void setChildren(List<SysRegion> children) {
        this.children = children;
    }

//    public SysRegion getParent() {
//        return parent;
//    }
//
//    public void setParent(SysRegion parent) {
//        this.parent = parent;
//    }

    @Override
    public String toString() {
        return "sysRegion [id = " + this.id + ",name = " + this.name + ",code = " + this.code + ",remark = " + this.remark + ",parentid=" + this.parentid + " ]";
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public Boolean getForceDefault() {
        return forceDefault;
    }

    public void setForceDefault(Boolean forceDefault) {
        this.forceDefault = forceDefault;
    }


}