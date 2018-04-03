package com.idc.utils;


/**
 * @author Administrator
 *
 */
public enum ResourceEnum {
	/*步骤顺序*/
	在服(2),
	新增(1),
	删除(-1),
	修改(0),
	强制删除(1),
	只是修改(1),

	机架空闲(40),
	机架预占(50),
	机架占用(60),

	MCB空闲(20),
	MCB预占(50),
	MCB占用(60),

	U位空闲(20),
	U位预占(50),
	U位占用(55),
	U位上架(60),

	主机空闲(40),
	主机预占(50),
	主机占用(60),

	IP空闲(0),
	IP已用(1),
	IP分配占用(2),
	IP等待回收(3),
	//IP资源实际状态。枚举：ALLCTYPE ====》FREE(0, "空闲"), USED(1, "已用"), ALLCUSED(2, "分配占用"), WAITRECY(3, "等待回收");


	端口带宽空闲(40),
	端口带宽预占(50),
	端口带宽占用(60),
	//端口带宽未知(4),
	端口带宽不可用(110),

	增值业务空闲(9999),
	增值业务预占(9999),
	增值业务占用(9999),

	按照机架分(66001),
	按照机位分(66002);
	private final Integer value;

	ResourceEnum(final Integer value) {
		this.value = value;
	}
	public Integer value() {
		return this.value;
	}
}
