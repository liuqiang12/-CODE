package com.idc.utils;


/**
 * @author Administrator
 *
 */
public enum JbpmStatusEnum {
	流程同意(1),
	流程初始化(0),
	流程驳回或不同意(-1),
	流程作废(-2),
	删除到回收站(-3),
	流程结束(2),
	//下面两个只针对与流程模型
	审批同意(1),
	审批不同意(0)
	//状态:  1同意、0初始化工单、  -1不同意|驳回、作废-2、删除到回收站-3、2结束
	;
	private final Integer value;

	JbpmStatusEnum(final Integer value) {
		this.value = value;
	}
	public Integer value() {
		return this.value;
	}
}
