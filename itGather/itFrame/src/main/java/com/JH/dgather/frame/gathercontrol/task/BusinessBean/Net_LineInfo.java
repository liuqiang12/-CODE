package com.JH.dgather.frame.gathercontrol.task.BusinessBean;

public class Net_LineInfo {
	private int LineId;
	private int srcId;
	private String desIp;
	private int schemeId;
	private int lineType;
	private String vpnPara;
	private String srcIp;
	private String lineName;
	
	public int getLineId() {
		return LineId;
	}
	
	public void setLineId(int lineId) {
		LineId = lineId;
	}
	
	public int getSrcId() {
		return srcId;
	}
	
	public void setSrcId(int srcId) {
		this.srcId = srcId;
	}
	
	public String getDesIp() {
		return desIp;
	}
	
	public void setDesIp(String desIp) {
		this.desIp = desIp;
	}
	
	public int getSchemeId() {
		return schemeId;
	}
	
	public void setSchemeId(int schemeId) {
		this.schemeId = schemeId;
	}
	
	public int getLineType() {
		return lineType;
	}
	
	public void setLineType(int lineType) {
		this.lineType = lineType;
	}
	
	public String getVpnPara() {
		return vpnPara;
	}
	
	public void setVpnPara(String vpnPara) {
		this.vpnPara = vpnPara;
	}
	
	/**
	 * @return the srcIp
	 */
	public String getSrcIp() {
		return srcIp;
	}
	
	/**
	 * @param srcIp the srcIp to set
	 */
	public void setSrcIp(String srcIp) {
		this.srcIp = srcIp;
	}

	/**
	 * @return the lineName
	 */
	public String getLineName() {
		return lineName;
	}

	/**
	 * @param lineName the lineName to set
	 */
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	
}
