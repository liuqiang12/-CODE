package com.idc.model;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Id;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>sys_operate_log:用户操作日志<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Dec 09,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class SysOperateLog implements Serializable {
		
	public static final String tableName = "sys_operate_log";
	    
    @Id@GeneratedValue 	
 	private Integer id; //   主键
 				
	private Integer userId; //   用户id 
	 				
	private BigDecimal type; //   操作类型:1000增加1001删除1002修改1003查询 
	 				
	private String description; //   描述

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Timestamp createTime; //   创建时间 
	
	private String createTimeStr; //   创建时间 [日期格式化后的字符串]

    private String typestr;
	 	
/************************get set method**************************/
 	
	public Integer getId() {
	    return this.id;
	}
 	
	@Column(name = "id" , columnDefinition="主键" , nullable =  false)
	public void setId(Integer id) {
	    this.id=id;
	}
 	
	public Integer getUserId() {
	    return this.userId;
	}
 	
	@Column(name = "user_id" , columnDefinition="用户id" , nullable =  false)
	public void setUserId(Integer userId) {
	    this.userId=userId;
	}
 	
	public BigDecimal getType() {
	    return this.type;
	}
 	
	@Column(name = "type" , columnDefinition="操作类型:1000增加1001删除1002修改1003查询" , nullable =  true)
	public void setType(BigDecimal type) {
	    this.type=type;
	}
 	
	public String getDescription() {
	    return this.description;
	}
 	
	@Column(name = "description" , columnDefinition="描述" , nullable =  true, length = 300)
	public void setDescription(String description) {
	    this.description=description;
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

    public String getTypestr() {
//        Enum.valueOf()
//        LogType. this.getType().
        return LogType.getNameByvalue(this.getType().intValue());
    }

//    public void setTypestr(String typestr) {
//        this.typestr = typestr;
//    }

    @Override
	public String toString() {
			return  "时间:"+new Date(this.getCreateTime().getTime())+"  用户["+this.userId+"]  "+LogType.ADD.getAction()+" "+this.getDescription();
	}
	

 }