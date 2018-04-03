package com.JH.dgather.frame.common.snmp;

import java.util.List;

import com.JH.dgather.frame.common.bean.DeviceInfoBean;
import com.JH.dgather.frame.common.snmp.bean.OIDbean;
import com.JH.dgather.frame.globaldata.GloableDataArea;

public class SnmpService {
	
	int m_timeout = 30 * 1000;
	
	private DeviceInfoBean device;
	
	public SnmpService(DeviceInfoBean device, int timeout) {
		this.device = device;
		if(timeout > 0)
			m_timeout = timeout;
	}
	
	public SnmpService(DeviceInfoBean device) {
		this(device, 30 * 1000);
	}
	
	public List<OIDbean> walk(String oid) {
		/*SnmpUtil su = new SnmpUtil(m_ipAddr, m_port);
		su.setVersion(m_version);
		su.setTimeOut(m_timeout);*/
		return GloableDataArea.su.walk(device, oid,0,m_timeout,60*1000l);
	}
}
