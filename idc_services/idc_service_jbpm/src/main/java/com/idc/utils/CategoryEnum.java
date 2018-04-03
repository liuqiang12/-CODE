package com.idc.utils;


/**
 * @author Administrator
 *
 */
public enum CategoryEnum {
	/*步骤顺序*/
	预堪预占流程("100"),
	开通流程("200"),
	停机流程("400"),
	复通流程("500"),
	下线流程("600"),
	开通变更流程("700"),
	临时测试流程("800"),
	业务变更流程("900"),

	把资源改成空闲状态("10000"),   /*如果ticketCategory=10000，说明要把资源 改成空闲状态*/

	添加资源附件("1111"),
	添加IDC_ISP附件("2222"),
	添加其他附件("3333"),
	添加合同附件("4444"),

	政企业务("1"),   //prod_category字段
	自有业务("0"),   //prod_category字段

	查看所有工单的角色("1"),
	查看所有客户("1");


	/*
	* 修改资源状态的时候，要在评分这一步进行修改
	*
	* */

	private final String value;

	CategoryEnum(final String value) {
		this.value = value;
	}
	public String value() {
		return this.value;
	}


	public static CategoryEnum getTicketName(String ticketCategory){
		/*遍历枚举形*/
		for (CategoryEnum e : CategoryEnum.values()) {
			if(ticketCategory.equalsIgnoreCase(e.value)){
				return e;
			}
		}
		return null;
	}



}
