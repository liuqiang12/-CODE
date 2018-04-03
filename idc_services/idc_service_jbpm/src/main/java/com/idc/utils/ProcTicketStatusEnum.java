package com.idc.utils;


/**
 * 枚举列出流程模型KEY
 * @author Administrator
 */
public enum ProcTicketStatusEnum {
	//流程工单状态
	草稿(10l,"",""),
	预勘中(20l,"100","0"),已预勘(21l,"100","1"),
	开通中(30l,"200","0"),已开通(31l,"200","1"),
	停机中(40l,"400","0"),已停机(41l,"400","1"),
	下线中(50l,"600","0"),已下线(51l,"600","1"),
	变更预勘中(60l,"300","0"),变更预勘结束(61l,"300","1"),
	变更开通中(70l,"700","0"),变更开通结束(71l,"700","1"),
	开通复通中(80l,"500","0"),已复通(81l,"500","1"),
	测试中(90l,"800","0"),测试结束(91l,"800","1"),
	业务变更中(100l,"900","0"),业务变更结束(101l,"900","1");
	private final Long value;
	private final String catalog;
	private final String status;
	ProcTicketStatusEnum(final Long value,final String catalog,final String status ) {
		this.value = value;
		this.catalog = catalog;
		this.status = status;
	}
	public Long value() {
		return this.value;
	}
	public String catalog(){
		return this.catalog;
	}
	public String status(){
		return this.status;
	}
	public String toString(){
		return this.value+"";
	}
	/*获取对应的enmu*/
	public static ProcTicketStatusEnum getCurrentEnumWithCatalog(String catalog,String status){
		/*遍历枚举形*/
		for (ProcTicketStatusEnum e : ProcTicketStatusEnum.values()) {
			if(catalog.equalsIgnoreCase(e.catalog())  && status.equals(e.status())){
				return e;
			}
		}
		return null;
	}
}
