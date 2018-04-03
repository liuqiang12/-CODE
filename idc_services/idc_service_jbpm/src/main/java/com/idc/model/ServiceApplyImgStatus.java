package com.idc.model;


import java.io.Serializable;

/**
 * 服务类型图标选中状态
 * @author Administrator
 */
@SuppressWarnings("serial")
public class ServiceApplyImgStatus implements Serializable {
	private Boolean rack = Boolean.FALSE;
	private Boolean port = Boolean.FALSE;
	private Boolean ip = Boolean.FALSE;
	private Boolean server = Boolean.FALSE;
	private Boolean add = Boolean.FALSE;

	private Long prodInstId;//订单ID
	public Boolean getRack() {
		return rack;
	}

	public void setRack(Boolean rack) {
		this.rack = rack;
	}

	public Boolean getPort() {
		return port;
	}

	public void setPort(Boolean port) {
		this.port = port;
	}

	public Boolean getIp() {
		return ip;
	}

	public void setIp(Boolean ip) {
		this.ip = ip;
	}

	public Boolean getServer() {
		return server;
	}

	public void setServer(Boolean server) {
		this.server = server;
	}

	public Boolean getAdd() {
		return add;
	}

	public void setAdd(Boolean add) {
		this.add = add;
	}

	public Long getProdInstId() {
		return prodInstId;
	}

	public void setProdInstId(Long prodInstId) {
		this.prodInstId = prodInstId;
	}
}
