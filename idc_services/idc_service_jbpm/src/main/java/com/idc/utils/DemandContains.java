package com.idc.utils;


/**
 * @author Administrator
 *
 */
public enum DemandContains {
	/*步骤顺序*/
	此字段属于机架("first_rack_"),
	此字段属于端口("first_port_"),
	此字段属于IP("first_ipip_"),
	此字段属于主机("first_serv_"),
	此字段属于增值业务("first_newl_");
	/*
	* 修改资源状态的时候，要在评分这一步进行修改
	*
	* */

	private final String value;

	DemandContains(final String value) {
		this.value = value;
	}
	public String value() {
		return this.value;
	}





}
