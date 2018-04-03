package com.idc.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>alarm_config_send:<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Dec 23,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class NetKpiAlarmSendConfig implements Serializable {
		
	public static final String tableName = "alarm_config_send";
	    
    @Id@GeneratedValue 	
 	private Integer id; //

    private Integer kpiid; //
 				
	private Integer alarmlevel; //   告警级别（0=一般；1=重要；2=严重） 
	 				
	private Integer alarmmode; //   0=不做任何处理；1=声音；2=短信；3=邮件 
	 				
	private String alarmaddress; //   告警接收地址。邮件地址或者手机号码，多个地址用“/”分开；如果AlarmMode=邮件就写邮件地址，如果果AlarmMode=短信就写手机号码 
	 	
/************************get set method**************************/
 	
	public Integer getId() {
	    return this.id;
	}
 	
	@Column(name = "ID" , nullable =  false)
	public void setId(Integer id) {
	    this.id=id;
	}
 	
	public Integer getAlarmlevel() {
	    return this.alarmlevel;
	}
 	
	@Column(name = "alarmlevel" , columnDefinition="告警级别（0=一般；1=重要；2=严重）" , nullable =  false)
	public void setAlarmlevel(Integer alarmlevel) {
	    this.alarmlevel=alarmlevel;
	}
 	
	public Integer getAlarmmode() {
	    return this.alarmmode;
	}
 	
	@Column(name = "alarmMode" , columnDefinition="0=不做任何处理；1=声音；2=短信；3=邮件" , nullable =  false)
	public void setAlarmmode(Integer alarmmode) {
	    this.alarmmode=alarmmode;
	}
 	
	public String getAlarmaddress() {
	    return this.alarmaddress;
	}
 	
	@Column(name = "alarmAddress" , columnDefinition="告警接收地址。邮件地址或者手机号码，多个地址用“/”分开；如果AlarmMode=邮件就写邮件地址，如果果AlarmMode=短信就写手机号码" , nullable =  true, length = 200)
	public void setAlarmaddress(String alarmaddress) {
	    this.alarmaddress=alarmaddress;
	}

    public Integer getKpiid() {
        return kpiid;
    }

    public void setKpiid(Integer kpiid) {
        this.kpiid = kpiid;
    }

    @Override
	public String toString() {
			return  "alarmConfigSend [id = "+this.id+",alarmlevel = "+this.alarmlevel+",alarmmode = "+this.alarmmode+",alarmaddress = "+this.alarmaddress+" ]"; 
	}
	

 }