package com.idc.utils;


/**
 * @author Administrator
 */
public enum MainViewEnum {
	/*步骤顺序*/
	客户经理界面视图("main_cus_manager"),
	维护人员界面视图("main_maintainer"),
	管理者界面视图("main_manager_sitepowerchart") ;

	private final String value;

	MainViewEnum(final String value) {
		this.value = value;
	}
	public String value() {
		return this.value;
	}
}
