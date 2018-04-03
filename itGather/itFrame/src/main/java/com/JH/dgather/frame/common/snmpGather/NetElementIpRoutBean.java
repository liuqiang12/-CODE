package com.JH.dgather.frame.common.snmpGather;

/**
 * ip rout table映射bean
 * 
 * @author yangDS
 *
 */
public class NetElementIpRoutBean {
	
	private String ipRouteDest;
	private String ipRouteIfIndex;
	private String ipRouteNextHop;
	private String ipRouteType;
	private String ipRouteMask;
	
	public String getIpRouteDest() {
		return ipRouteDest;
	}
	
	public void setIpRouteDest(String ipRouteDest) {
		this.ipRouteDest = ipRouteDest;
	}
	
	public String getIpRouteIfIndex() {
		return ipRouteIfIndex;
	}
	
	public void setIpRouteIfIndex(String ipRouteIfIndex) {
		this.ipRouteIfIndex = ipRouteIfIndex;
	}
	
	public String getIpRouteNextHop() {
		return ipRouteNextHop;
	}
	
	public void setIpRouteNextHop(String ipRouteNextHop) {
		this.ipRouteNextHop = ipRouteNextHop;
	}
	
	public String getIpRouteType() {
		return ipRouteType;
	}
	
	public void setIpRouteType(String ipRouteType) {
		this.ipRouteType = ipRouteType;
	}
	
	public String getIpRouteMask() {
		return ipRouteMask;
	}
	
	public void setIpRouteMask(String ipRouteMask) {
		this.ipRouteMask = ipRouteMask;
	}
	
}
