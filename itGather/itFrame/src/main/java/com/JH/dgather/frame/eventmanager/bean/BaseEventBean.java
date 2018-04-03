package com.JH.dgather.frame.eventmanager.bean;

public abstract class BaseEventBean {
	private int id;				// 主键ID
	private int objId;			// 对象ID
	private int alarmType;//告警类别0=采集器告警	1=房机用能突变告警2=PUE告警	3=机楼用能突变告警
	private int kpiId;			// 对应net_kpibase表的主键，即指标ID
	private String portName;	// 端口名
	private long portId;			// 引用Net_ResPortInfo表的PortId字段
	//主要是为了方便告警事件发送时，容易匹配规则。
	//如果链路告警，objId存储的是链路ID，那么在发送时，需要再次查询链路信息表，来关联到设备，然后匹配发送规则，增加查询负担
	//所以业务端在组装事件时，一并将设备ID组装进来。
	private int deviceId;		//设备ID

	private String alarmInfo; // 告警详细信息
	private int alarmlevel; // 告警级别 0:一般，1：重要，2：严重， 5：关键
	private String objname;//告警对象名称，避免发送时查询
	private boolean isSend = false; //当前记录是否达到发送条件
	/**
	 * 0表示insert
	 * 1：如果是告警数据，表示update，如果是非告警数据，表示delete并移至历史表
	 */
	private int saveFlag = 0;		//0表示insert, 1表示update
	public BaseEventBean(int objId, int alarmType,long portId, int alarmlevel, String objname,String alarmInfo) {
		this.objId = objId;
		this.alarmType = alarmType;
		this.alarmlevel = alarmlevel;
		this.setPortId(portId);
		if(objname==null)
			this.objname = "";
		else
			this.objname = objname;
		if(alarmInfo==null)
			this.alarmInfo = "";
		else
			this.alarmInfo = alarmInfo;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getObjId() {
		return objId;
	}

	public void setObjId(int objId) {
		this.objId = objId;
	}

	public int getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(int alarmType) {
		this.alarmType = alarmType;
	}

	public String getAlarmInfo() {
		return alarmInfo;
	}

	public void setAlarmInfo(String alarmInfo) {
		this.alarmInfo = alarmInfo;
	}

	public int getAlarmlevel() {
		return alarmlevel;
	}

	public void setAlarmlevel(int alarmlevel) {
		this.alarmlevel = alarmlevel;
	}

	public boolean isSend() {
		return isSend;
	}

	public void setSend(boolean isSend) {
		this.isSend = isSend;
	}

	public int getSaveFlag() {
		return saveFlag;
	}

	public void setSaveFlag(int saveFlag) {
		this.saveFlag = saveFlag;
	}


	public String getObjname() {
		return objname;
	}

	public void setObjname(String objname) {
		this.objname = objname;
	}

	public int getKpiId() {
		return kpiId;
	}

	public void setKpiId(int kpiId) {
		this.kpiId = kpiId;
	}

	public long getPortId() {
		return portId;
	}

	public void setPortId(long portId) {
		this.portId = portId;
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public String getPortName() {
		return portName;
	}

	public void setPortName(String portName) {
		this.portName = portName;
	}

	
}
