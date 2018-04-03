//package com.idc.model;
//
//import javax.persistence.Column;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import java.io.Serializable;
//
///**
// * <br>
// * <b>实体类</b><br>
// * <b>功能：业务表</b>alarm_config_level:<br>
// * <b>作者：Administrator</b><br>
// * <b>日期：</b> Dec 23,2016 <br>
// * <b>版权所有：<b>版权所有(C) 2016<br>
// */
//@SuppressWarnings("serial")
//public class AlarmConfigLevel implements Serializable {
//
//	public static final String tableName = "alarm_config_level";
//
//    @Id@GeneratedValue
// 	private Integer id; //
//
//    private Integer kpiid; //
//
//	private Integer alarmType; //   告警类别
//
//    private Integer alarmLimit; //   阀值门限，目前仅考虑高门限,对于状态类告警无门限
//
//	private Integer alarmLevel; //   告警级别（0=一般；1=重要；2=严重）
//
//	private String description; //
//
///************************get set method**************************/
//
//	public Integer getId() {
//	    return this.id;
//	}
//
//	@Column(name = "ID" , nullable =  false)
//	public void setId(Integer id) {
//	    this.id=id;
//	}
//
//	public Integer getAlarmType() {
//	    return this.alarmType;
//	}
//
//	@Column(name = "alarm_type" , columnDefinition="告警类别", nullable =  false)
//	public void setAlarmType(Integer alarmType) {
//	    this.alarmType=alarmType;
//	}
//
//	public Integer getAlarmLimit() {
//	    return this.alarmLimit;
//	}
//
//	@Column(name = "alarm_limit" , columnDefinition="阀值门限，目前仅考虑高门限,对于状态类告警无门限" , nullable =  true)
//	public void setAlarmLimit(Integer alarmLimit) {
//	    this.alarmLimit=alarmLimit;
//	}
//
//	public Integer getAlarmLevel() {
//	    return this.alarmLevel;
//	}
//
//	@Column(name = "alarm_level" , columnDefinition="告警级别（0=一般；1=重要；2=严重）" , nullable =  false)
//	public void setAlarmLevel(Integer alarmLevel) {
//	    this.alarmLevel=alarmLevel;
//	}
//
//	public String getDescription() {
//	    return this.description;
//	}
//
//	@Column(name = "description" , nullable =  true, length = 100)
//	public void setDescription(String description) {
//	    this.description=description;
//	}
//
//
//
//	@Override
//	public String toString() {
//			return  "alarmConfigLevel [id = "+this.id+",alarmType = "+this.alarmType+",alarmLimit = "+this.alarmLimit+",alarmLevel = "+this.alarmLevel+",description = "+this.description+
//		" ]";
//	}
//
//
// }