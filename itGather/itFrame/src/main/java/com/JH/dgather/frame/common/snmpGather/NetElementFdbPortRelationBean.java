package com.JH.dgather.frame.common.snmpGather;

/**
 * fdb关系映射bean
 * 
 * @author yang
 *
 */
public class NetElementFdbPortRelationBean {
	private String dot1dBasePort;
	private String dot1dBasePortIfIndex;
	
	public String getDot1dBasePort() {
		return dot1dBasePort;
	}
	
	public void setDot1dBasePort(String dot1dBasePort) {
		this.dot1dBasePort = dot1dBasePort;
	}
	
	public String getDot1dBasePortIfIndex() {
		return dot1dBasePortIfIndex;
	}
	
	public void setDot1dBasePortIfIndex(String dot1dBasePortIfIndex) {
		this.dot1dBasePortIfIndex = dot1dBasePortIfIndex;
	}
}
