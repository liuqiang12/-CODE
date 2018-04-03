package com.JH.dgather.frame.common.snmpGather;

/**
 * @author muyp
 *
 */
public class HostSystem {
	private String hrSystemDescr;
	private String hrSystemName;
	private String hrSystemNumUsers;
	private String hrSystemUptime;
	
	public String getHrSystemDescr() {
		return hrSystemDescr;
	}
	
	public void setHrSystemDescr(String hrSystemDescr) {
		this.hrSystemDescr = hrSystemDescr;
	}
	
	public String getHrSystemName() {
		return hrSystemName;
	}
	
	public void setHrSystemName(String hrSystemName) {
		this.hrSystemName = hrSystemName;
	}
	
	public String getHrSystemNumUsers() {
		return hrSystemNumUsers;
	}
	
	public void setHrSystemNumUsers(String hrSystemNumUsers) {
		this.hrSystemNumUsers = hrSystemNumUsers;
	}
	
	public String getHrSystemUptime() {
		return hrSystemUptime;
	}
	
	public void setHrSystemUptime(String hrSystemUptime) {
		this.hrSystemUptime = hrSystemUptime;
	}
}
