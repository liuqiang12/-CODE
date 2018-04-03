package com.idc.model;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Id;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>sys_department_tree_closure:部门树形闭包<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Dec 28,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class SysDepartmentTreeClosure implements Serializable {

    public static final String tableName = "sys_department_tree_closure";

    @Id@GeneratedValue
    private Integer ancestor; //   祖先ID(主键ID，外键ID)

    @Id@GeneratedValue
    private Integer descendant; //   祖先ID(主键ID，外键ID)

    private Integer distance; //   祖先距离后代的距离:直接引用为0，直接子节点为1，再一下层为2

    private Timestamp createTime; //   创建时间

    private String createTimeStr; //   创建时间 [日期格式化后的字符串]

    /************************get set method**************************/

    public Integer getAncestor() {
        return this.ancestor;
    }

    @Column(name = "ancestor" , columnDefinition="祖先ID(主键ID，外键ID)" , nullable =  false)
    public void setAncestor(Integer ancestor) {
        this.ancestor=ancestor;
    }

    public Integer getDescendant() {
        return this.descendant;
    }

    @Column(name = "descendant" , columnDefinition="祖先ID(主键ID，外键ID)" , nullable =  false)
    public void setDescendant(Integer descendant) {
        this.descendant=descendant;
    }

    public Integer getDistance() {
        return this.distance;
    }

    @Column(name = "distance" , columnDefinition="祖先距离后代的距离:直接引用为0，直接子节点为1，再一下层为2" , nullable =  true)
    public void setDistance(Integer distance) {
        this.distance=distance;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    @Column(name = "create_time" , columnDefinition="创建时间" , nullable =  false)
    public void setCreateTime(Timestamp createTime) {
        this.createTime=createTime;
    }


    public String getCreateTimeStr() {
        return this.createTimeStr;
    }

    public void setCreateTimeStr(Timestamp createTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date_ = null;
        try {
            date_ = sdf.format(createTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.createTimeStr = date_;
    }


    @Override
    public String toString() {
        return  "sysDepartmentTreeClosure [ancestor = "+this.ancestor+",descendant = "+this.descendant+",distance = "+this.distance+",createTime = "+this.createTime+" ]";
    }


}