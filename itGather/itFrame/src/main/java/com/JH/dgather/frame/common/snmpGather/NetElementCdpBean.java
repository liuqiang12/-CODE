package com.JH.dgather.frame.common.snmpGather;

import com.JH.dgather.frame.util.StringUtil;

/**
 * 思科cdp协议映射bean
 * 
 * @author yangDS
 *
 */
public class NetElementCdpBean {
	private String ifIndex;
	private String cdpCacheAddress;
	private String cdpCacheDeviceId;
	private String cdpCacheDevicePort;
	private String cdpCachePlatform;
	
	public String getIfIndex() {
		return ifIndex;
	}
	
	public void setIfIndex(String ifIndex) {
		this.ifIndex = ifIndex;
	}
	
	public String getCdpCacheAddress() {
		return cdpCacheAddress;
	}
	
	public void setCdpCacheAddress(String cdpCacheAddress) {
		try {
			this.cdpCacheAddress = StringUtil.getIpFromHex(cdpCacheAddress);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			this.cdpCacheAddress = cdpCacheAddress;
			//			e.printStackTrace();
		}
	}
	
	public String getCdpCacheDeviceId() {
		return cdpCacheDeviceId;
	}
	
	public void setCdpCacheDeviceId(String cdpCacheDeviceId) {
		this.cdpCacheDeviceId = cdpCacheDeviceId;
	}
	
	public String getCdpCacheDevicePort() {
		return cdpCacheDevicePort;
	}
	
	public void setCdpCacheDevicePort(String cdpCacheDevicePort) {
		this.cdpCacheDevicePort = cdpCacheDevicePort;
	}
	
	public String getCdpCachePlatform() {
		return cdpCachePlatform;
	}
	
	public void setCdpCachePlatform(String cdpCachePlatform) {
		this.cdpCachePlatform = cdpCachePlatform;
	}
	
}
