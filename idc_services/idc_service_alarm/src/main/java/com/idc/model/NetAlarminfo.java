package com.idc.model;import com.fasterxml.jackson.annotation.JsonFormat;import java.io.Serializable;import java.text.SimpleDateFormat;import java.util.Date;/** * <br> * <b>实体类</b><br> * <b>功能：业务表</b>NET_ALARMINFO_CURR:${tableData.tableComment}<br> * <b>作者：Administrator</b><br> * <b>日期：</b> Aug 02,2017 <br> * <b>版权所有：<b>版权所有(C) 2016<br> */@SuppressWarnings("serial")public class NetAlarminfo implements Serializable {    private Long id;    private Long alarmlevel;    private Long alarmtype;    private String alarmobj;    private String alarminfo;    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")    private Date alarmtime;    private Long alarmsendflag;    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")    private Date alarmsendtime;    private Long portid;    private String portName;    private String alarmtimeStr;    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")    private Date resumetime;    private String resumeuser;    public String getPortName() {        return portName;    }    public void setPortName(String portName) {        this.portName = portName;    }    private static final long serialVersionUID = 1L;    public Long getId() {        return id;    }    public void setId(Long id) {        this.id = id;    }    public Long getAlarmlevel() {        return alarmlevel;    }    public void setAlarmlevel(Long alarmlevel) {        this.alarmlevel = alarmlevel;    }    public Long getAlarmtype() {        return alarmtype;    }    public void setAlarmtype(Long alarmtype) {        this.alarmtype = alarmtype;    }    public String getAlarmobj() {        return alarmobj;    }    public void setAlarmobj(String alarmobj) {        this.alarmobj = alarmobj;    }    public String getAlarminfo() {        return alarminfo;    }    public void setAlarminfo(String alarminfo) {        this.alarminfo = alarminfo;    }    public Date getAlarmtime() {        return alarmtime;    }    public void setAlarmtime(Date alarmtime) {        this.alarmtime = alarmtime;    }    public Long getAlarmsendflag() {        return alarmsendflag;    }    public void setAlarmsendflag(Long alarmsendflag) {        this.alarmsendflag = alarmsendflag;    }    public Date getAlarmsendtime() {        return alarmsendtime;    }    public void setAlarmsendtime(Date alarmsendtime) {        this.alarmsendtime = alarmsendtime;    }    public Long getPortid() {        return portid;    }    public void setPortid(Long portid) {        this.portid = portid;    }    public String getAlarmtimeStr() {        if (this.alarmtimeStr == null) {            setAlarmtimeStr(this.getAlarmtime());        }        return this.alarmtimeStr;    }    public void setAlarmtimeStr(Date alarmtime) {        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");        String date_ = null;        try {            date_ = sdf.format(alarmtime);        } catch (Exception e) {            e.printStackTrace();        }        this.alarmtimeStr = date_;    }    @Override    public boolean equals(Object that) {        if (this == that) {            return true;        }        if (that == null) {            return false;        }        if (getClass() != that.getClass()) {            return false;        }        AlarmInfoCurr other = (AlarmInfoCurr) that;        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))                && (this.getAlarmlevel() == null ? other.getAlarmlevel() == null : this.getAlarmlevel().equals(other.getAlarmlevel()))                && (this.getAlarmtype() == null ? other.getAlarmtype() == null : this.getAlarmtype().equals(other.getAlarmtype()))                && (this.getAlarmobj() == null ? other.getAlarmobj() == null : this.getAlarmobj().equals(other.getAlarmobj()))                && (this.getAlarminfo() == null ? other.getAlarminfo() == null : this.getAlarminfo().equals(other.getAlarminfo()))                && (this.getAlarmtime() == null ? other.getAlarmtime() == null : this.getAlarmtime().equals(other.getAlarmtime()))                && (this.getAlarmsendflag() == null ? other.getAlarmsendflag() == null : this.getAlarmsendflag().equals(other.getAlarmsendflag()))                && (this.getAlarmsendtime() == null ? other.getAlarmsendtime() == null : this.getAlarmsendtime().equals(other.getAlarmsendtime()))                && (this.getPortid() == null ? other.getPortid() == null : this.getPortid().equals(other.getPortid()));    }    @Override    public int hashCode() {        final int prime = 31;        int result = 1;        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());        result = prime * result + ((getAlarmlevel() == null) ? 0 : getAlarmlevel().hashCode());        result = prime * result + ((getAlarmtype() == null) ? 0 : getAlarmtype().hashCode());        result = prime * result + ((getAlarmobj() == null) ? 0 : getAlarmobj().hashCode());        result = prime * result + ((getAlarminfo() == null) ? 0 : getAlarminfo().hashCode());        result = prime * result + ((getAlarmtime() == null) ? 0 : getAlarmtime().hashCode());        result = prime * result + ((getAlarmsendflag() == null) ? 0 : getAlarmsendflag().hashCode());        result = prime * result + ((getAlarmsendtime() == null) ? 0 : getAlarmsendtime().hashCode());        result = prime * result + ((getPortid() == null) ? 0 : getPortid().hashCode());        return result;    }    @Override    public String toString() {        StringBuilder sb = new StringBuilder();        sb.append(getClass().getSimpleName());        sb.append(" [");        sb.append("Hash = ").append(hashCode());        sb.append(", id=").append(id);        sb.append(", alarmlevel=").append(alarmlevel);        sb.append(", alarmtype=").append(alarmtype);        sb.append(", alarmobj=").append(alarmobj);        sb.append(", alarminfo=").append(alarminfo);        sb.append(", alarmtime=").append(alarmtime);        sb.append(", alarmsendflag=").append(alarmsendflag);        sb.append(", alarmsendtime=").append(alarmsendtime);        sb.append(", portid=").append(portid);        sb.append(", serialVersionUID=").append(serialVersionUID);        sb.append("]");        return sb.toString();    }    public void setResumetime(Date resumetime) {        this.resumetime = resumetime;    }    public Date getResumetime() {        return resumetime;    }    public void setResumeuser(String resumeuser) {        this.resumeuser = resumeuser;    }    public String getResumeuser() {        return resumeuser;    }}