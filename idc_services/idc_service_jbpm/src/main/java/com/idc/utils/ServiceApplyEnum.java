package com.idc.utils;


/**
 * 服务申请枚举
 * @author Administrator
 */
public enum ServiceApplyEnum {
	机架("100"),
	端口带宽("200"),
	IP租用("300"),
	主机租赁("400"),
	增值业务("500"),
	U位("600"),
	MCB("700"),
	修改需要工单ID("releaseReourceByTicketId");

	private final String value;
	ServiceApplyEnum(final String value) {
		this.value = value;
	}
	public String value() {
		return this.value;
	}
	public String toString(){
		return this.value;
	}
}
