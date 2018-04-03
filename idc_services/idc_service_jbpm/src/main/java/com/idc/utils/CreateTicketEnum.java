package com.idc.utils;


/**
 *
 * @author Administrator
 */
public enum CreateTicketEnum {
	新建后部署阶段("100");

	private final String value;
	CreateTicketEnum(final String value) {
		this.value = value;
	}
	public String value() {
		return this.value;
	}
	public String toString(){
		return this.value;
	}
}
