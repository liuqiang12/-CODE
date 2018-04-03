package com.idc.utils;


/**A
 * 任务节点对应的资源枚举类型
 * @author Administrator
 */
public enum TASKNodeURL {

	/*步骤顺序*/
	预占预勘察流程_预占申请("idc_ticket_pre_accept","预占申请","pre_accept_apply_form","jbpm/ticket/author_pre_apply",1,true,false,null,false,false,"100",1,"预勘/预占"),
	预占预勘察流程_地市政企审批("idc_ticket_pre_accept","地市政企审批","city_government_apply_form","jbpm/ticket/city_government_apply",2,false,false,"pre_accept_apply_form",false,false,"100",1,"预勘/预占"),
	预占预勘察流程_省政企审批("idc_ticket_pre_accept","省政企审批","province_government_apply_form","jbpm/ticket/province_government_apply",3,false,false,"city_government_apply_form",false,false,"100",1,"预勘/预占"),
	预占预勘察流程_预占勘查("idc_ticket_pre_accept","预占勘查","occupy_relonnissanle_form","jbpm/ticket/occupy_relonnissanle_form",4,true,true,"province_government_apply_form",true,true,"100",1,"预勘/预占"),
	预占预勘察流程_评价("idc_ticket_pre_accept","评价","comment_form","jbpm/ticket/comment_form",5,false,false,"occupy_relonnissanle_form",true,false,"100",1,"预勘/预占"),
	/*开通流程*/
	开通流程_开通申请("idc_ticket_open","开通申请","open_accept_apply_form","jbpm/ticket/open/author_pre_apply",1,false,false,null,true,false,"200",1,"业务开通"),
	开通流程_地市政企审批("idc_ticket_open","地市政企审批","open_city_government_apply_form","jbpm/ticket/open/city_government_apply",2,false,false,"open_accept_apply_form",true,false,"200",1,"业务开通"),
	开通流程_省政企审批("idc_ticket_open","省政企审批","open_province_government_apply_form","jbpm/ticket/open/province_government_apply",3,false,false,"open_city_government_apply_form",true,false,"200",1,"业务开通"),
	开通流程_资源上架("idc_ticket_open","资源上架","open_shelveuping_form","jbpm/ticket/open/open_shelveuping_form",4,false,false,"open_province_government_apply_form",true,false,"200",1,"业务开通"),
	开通流程_评价("idc_ticket_open","评价","open_comment_form","jbpm/ticket/open/comment_form",5,false,false,"open_shelveuping_form",true,false,"200",1,"业务开通"),

	/*业务变更*/
	业务变更_变更申请("idc_ticket_business_change","变更申请","business_change_accept_apply_form","jbpm/ticket/open/businesschange/author_pre_apply",1,true,false,null,true,false,"900",1,"业务变更"	),
	业务变更_地市政企审批("idc_ticket_business_change","地市政企审批","business_change_city_government_apply_form","jbpm/ticket/open/businesschange/city_government_apply",2,false,false,"business_change_accept_apply_form",true,false,"900",1,"业务变更"),
	业务变更_省政企审批("idc_ticket_business_change","省政企审批","business_change_province_government_apply_form","jbpm/ticket/open/businesschange/province_government_apply",3,false,false,"business_change_city_government_apply_form",true,false,"900",1,"业务变更"),
	业务变更_资源上架("idc_ticket_business_change","资源预占","business_change_shelveuping_form","jbpm/ticket/open/businesschange/occupy_relonnissanle_form",4,true,true,"business_change_province_government_apply_form",true,true,"900",1,"业务变更"),
	业务变更_评价("idc_ticket_business_change","评价","business_change_comment_form","jbpm/ticket/open/businesschange/comment_form",5,false,false,"business_change_shelveuping_form",true,false,"900",1,"业务变更"),

	/*变更开通*/
	变更开通_变更开通申请("idc_ticket_open_change","变更开通申请","open_change_accept_apply_form","jbpm/ticket/open/change/author_pre_apply",1,false,false,null,true,true,"700",1,"变更开通"),
	变更开通_地市政企审批("idc_ticket_open_change","地市政企审批","open_change_city_government_apply_form","jbpm/ticket/open/change/city_government_apply",2,false,false,"open_change_accept_apply_form",true,false,"700",1,"变更开通"),
	变更开通_省政企审批("idc_ticket_open_change","省政企审批","open_change_province_government_apply_form","jbpm/ticket/open/change/province_government_apply",3,false,false,"open_change_city_government_apply_form",true,false,"700",1,"变更开通"),
	变更开通_资源上架("idc_ticket_open_change","资源分配上架","open_change_shelveuping_form","jbpm/ticket/open/change/shelveuping_form",4,true,false,"open_change_province_government_apply_form",true,false,"700",1,"变更开通"),
	变更开通_评价("idc_ticket_open_change","评价","open_change_comment_form","jbpm/ticket/open/change/comment_form",5,false,false,"open_change_shelveuping_form",true,false,"700",1,"变更开通"),


	/*停机*/
	停机_停机申请("idc_ticket_pause","停机申请","pause_accept_apply_form","jbpm/ticket/open/pause/author_pre_apply",1,true,false,null,true,false,"400",1,"停机"),
	停机_地市政企审批("idc_ticket_pause","地市政企审批","pause_city_government_apply_form","jbpm/ticket/open/pause/city_government_apply",2,false,false,"pause_accept_apply_form",true,false,"400",1,"停机"),
	停机_省政企审批("idc_ticket_pause","省政企审批","pause_province_government_apply_form","jbpm/ticket/open/pause/province_government_apply",3,false,false,"pause_city_government_apply_form",true,false,"400",1,"停机"),
	停机_资源上架("idc_ticket_pause","停机","pause_shelveuping_form","jbpm/ticket/open/pause/shelveuping_form",4,true,false,"pause_province_government_apply_form",true,false,"400",1,"停机"),
	停机_评价("idc_ticket_pause","评价","pause_comment_form","jbpm/ticket/open/pause/comment_form",5,false,false,"pause_shelveuping_form",true,false,"400",1,"停机"),

	/*复通流程*/
	复通_复通申请("idc_ticket_recover","复通申请","recover_accept_apply_form","jbpm/ticket/open/recover/author_pre_apply",1,true,false,null,true,false,"500",1,"复通"),
	复通_地市政企审批("idc_ticket_recover","地市政企审批","recover_city_government_apply_form","jbpm/ticket/open/recover/city_government_apply",2,false,false,"recover_accept_apply_form",true,false,"500",1,"复通"),
	复通_省政企审批("idc_ticket_recover","省政企审批","recover_province_government_apply_form","jbpm/ticket/open/recover/province_government_apply",3,false,false,"recover_city_government_apply_form",true,false,"500",1,"复通"),
	复通_资源上架("idc_ticket_recover","复通","recover_shelveuping_form","jbpm/ticket/open/recover/shelveuping_form",4,true,false,"recover_province_government_apply_form",true,false,"500",1,"复通"),
	复通_评价("idc_ticket_recover","评价","recover_comment_form","jbpm/ticket/open/recover/comment_form",5,false,false,"recover_shelveuping_form",true,false,"500",1,"复通"),

	/*复通流程*/
	业务下线_下线申请("idc_ticket_halt","下线申请","halt_accept_apply_form","jbpm/ticket/open/halt/author_pre_apply",1,true,false,null,true,false,"600",1,"业务下线"),
	业务下线_地市政企审批("idc_ticket_halt","地市政企审批","halt_city_government_apply_form","jbpm/ticket/open/halt/city_government_apply",2,false,false,"halt_accept_apply_form",true,false,"600",1,"业务下线"),
	业务下线_省政企审批("idc_ticket_halt","省政企审批","halt_province_government_apply_form","jbpm/ticket/open/halt/province_government_apply",3,false,false,"halt_city_government_apply_form",true,false,"600",1,"业务下线"),
	业务下线_下线("idc_ticket_halt","下线","halt_shelveuping_form","jbpm/ticket/open/halt/shelveuping_form",4,true,false,"halt_province_government_apply_form",true,false,"600",1,"业务下线"),
	业务下线_评价("idc_ticket_halt","评价","halt_comment_form","jbpm/ticket/open/halt/comment_form",5,false,false,"halt_shelveuping_form",true,false,"600",1,"业务下线"),


	/*临时测试_*/
	临时测试_测试申请("idc_ticket_temporary","测试申请","temporary_accept_apply_form","jbpm/ticket/open/temporary/author_pre_apply",1,true,false,null,false,false,"800",1,"临时测试"),
	临时测试_地市政企审批("idc_ticket_temporary","地市政企审批","temporary_city_government_apply_form","jbpm/ticket/open/temporary/city_government_apply",2,false,false,"temporary_accept_apply_form",false,false,"800",1,"临时测试"),
	临时测试_省政企审批("idc_ticket_temporary","省政企审批","temporary_province_government_apply_form","jbpm/ticket/open/temporary/province_government_apply",3,false,false,"temporary_city_government_apply_form",false,false,"800",1,"临时测试"),
	临时测试_配合测试("idc_ticket_temporary","配合测试","temporary_shelveuping_form","jbpm/ticket/open/temporary/shelveuping_form",4,true,true,"temporary_province_government_apply_form",true,true,"800",1,"临时测试"),
	临时测试_评价("idc_ticket_temporary","评价","temporary_comment_form","jbpm/ticket/open/temporary/comment_form",5,false,false,"temporary_shelveuping_form",true,false,"800",1,"临时测试"),



	/** 自有业务_  **/

	自有业务_预占预勘察流程_预占申请("idc_ticket_self_pre_accept","预占申请","self_pre_accept_apply_form","jbpm/ticket/self/author_pre_apply",1,true,false,null,false,false,"100",0,"预勘/预占"),
	自有业务_预占预勘察流程_网络部审批("idc_ticket_self_pre_accept","网络部审批","self_network_apply_form","jbpm/ticket/self/network_apply",2,false,false,"self_pre_accept_apply_form",false,false,"100",0,"预勘/预占"),
	自有业务_预占预勘察流程_预占勘查("idc_ticket_self_pre_accept","预占勘查","self_occupy_relonnissanle_form","jbpm/ticket/self/occupy_relonnissanle_form",3,true,true,"self_network_apply_form",true,true,"100",0,"预勘/预占"),
	自有业务_预占预勘察流程_评价("idc_ticket_self_pre_accept","评价","self_comment_form","jbpm/ticket/self/comment_form",4,false,false,"self_occupy_relonnissanle_form",true,false,"100",0,"预勘/预占"),
	/*开通流程*/
	自有业务_开通流程_开通申请("idc_ticket_self_open","开通申请","self_open_accept_apply_form","jbpm/ticket/self/open/author_pre_apply",1,true,false,null,true,false,"200",0,"业务开通"),
	自有业务_开通流程_网络部审批("idc_ticket_self_open","网络部审批","self_open_network_apply_form","jbpm/ticket/self/open/network_apply",2,false,false,"self_open_accept_apply_form",true,false,"200",0,"业务开通"),
	自有业务_开通流程_资源上架("idc_ticket_self_open","资源上架","self_open_shelveuping_form","jbpm/ticket/self/open/open_shelveuping_form",3,true,false,"self_open_network_apply_form",true,false,"200",0,"业务开通"),
	自有业务_开通流程_评价("idc_ticket_self_open","评价","self_open_comment_form","jbpm/ticket/self/open/comment_form",4,false,false,"self_open_shelveuping_form",true,false,"200",0,"业务开通"),

	/*业务变更*/
	自有业务_业务变更_变更申请("idc_ticket_self_business_change","变更申请","self_business_change_accept_apply_form","jbpm/ticket/self/open/businesschange/author_pre_apply",1,true,false,null,true,false,"900",0,"业务变更"),
	自有业务_业务变更_网络部审批("idc_ticket_self_business_change","网络部审批","self_business_change_network_apply_form","jbpm/ticket/self/open/businesschange/network_apply",2,false,false,"self_business_change_accept_apply_form",true,false,"900",0,"业务变更"),
	自有业务_业务变更_资源上架("idc_ticket_self_business_change","资源预占","self_business_change_shelveuping_form","jbpm/ticket/self/open/businesschange/occupy_relonnissanle_form",3,true,true,"self_business_change_network_apply_form",true,true,"900",0,"业务变更"),
	自有业务_业务变更_评价("idc_ticket_self_business_change","评价","self_business_change_comment_form","jbpm/ticket/self/open/businesschange/comment_form",4,false,false,"self_business_change_shelveuping_form",true,false,"900",0,"业务变更"),


	/*变更开通*/
	自有业务_变更开通_变更开通申请("idc_ticket_self_open_change","变更开通申请","self_open_change_accept_apply_form","jbpm/ticket/self/open/change/author_pre_apply",1,true,false,null,true,false,"700",0,"变更开通"),
	自有业务_变更开通_网络部审批("idc_ticket_self_open_change","网络部审批","self_open_change_network_apply_form","jbpm/ticket/self/open/change/network_apply",2,false,false,"self_open_change_accept_apply_form",true,false,"700",0,"变更开通"),
	自有业务_变更开通_资源上架("idc_ticket_self_open_change","资源分配上架","self_open_change_shelveuping_form","jbpm/ticket/self/open/change/shelveuping_form",3,true,false,"self_open_change_network_apply_form",true,false,"700",0,"变更开通"),
	自有业务_变更开通_评价("idc_ticket_self_open_change","评价","self_open_change_comment_form","jbpm/ticket/self/open/change/comment_form",4,false,false,"self_open_change_shelveuping_form",true,false,"700",0,"变更开通"),


	/*停机*/
	自有业务_停机_停机申请("idc_ticket_self_pause","停机申请","self_pause_accept_apply_form","jbpm/ticket/self/open/pause/author_pre_apply",1,true,false,null,true,false,"400",0,"停机"),
	自有业务_停机_网络部审批("idc_ticket_self_pause","网络部审批","self_pause_network_apply_form","jbpm/ticket/self/open/pause/network_apply",2,false,false,"self_pause_accept_apply_form",true,false,"400",0,"停机"),
	自有业务_停机_资源上架("idc_ticket_self_pause","停机","self_pause_shelveuping_form","jbpm/ticket/self/open/pause/shelveuping_form",3,true,false,"self_pause_network_apply_form",true,false,"400",0,"停机"),
	自有业务_停机_评价("idc_ticket_self_pause","评价","self_pause_comment_form","jbpm/ticket/self/open/pause/comment_form",4,false,false,"self_pause_shelveuping_form",true,false,"400",0,"停机"),

	/*复通流程*/
	自有业务_复通_复通申请("idc_ticket_self_recover","复通申请","self_recover_accept_apply_form","jbpm/ticket/self/open/recover/author_pre_apply",1,true,false,null,false,false,"500",0,"复通"),
	自有业务_复通_网络部审批("idc_ticket_self_recover","网络部审批","self_recover_network_apply_form","jbpm/ticket/self/open/recover/network_apply",2,false,false,"self_recover_accept_apply_form",true,false,"500",0,"复通"),
	自有业务_复通_资源上架("idc_ticket_self_recover","复通","self_recover_shelveuping_form","jbpm/ticket/self/open/recover/shelveuping_form",3,true,false,"self_recover_network_apply_form",true,false,"500",0,"复通"),
	自有业务_复通_评价("idc_ticket_self_recover","评价","self_recover_comment_form","jbpm/ticket/self/open/recover/comment_form",4,false,false,"self_recover_shelveuping_form",true,false,"500",0,"复通"),

	/*下线流程*/
	自有业务_业务下线_下线申请("idc_ticket_self_halt","下线申请","self_halt_accept_apply_form","jbpm/ticket/self/open/halt/author_pre_apply",1,true,false,null,true,false,"600",0,"业务下线"),
	自有业务_业务下线_网络部审批("idc_ticket_self_halt","网络部审批","self_halt_city_government_apply_form","jbpm/ticket/self/open/halt/network_apply",2,false,false,"self_halt_accept_apply_form",true,false,"600",0,"业务下线"),
	自有业务_业务下线_下线("idc_ticket_self_halt","下线","self_halt_shelveuping_form","jbpm/ticket/self/open/halt/shelveuping_form",4,true,false,"self_halt_city_government_apply_form",true,false,"600",0,"业务下线"),
	自有业务_业务下线_评价("idc_ticket_self_halt","评价","self_halt_comment_form","jbpm/ticket/self/open/halt/comment_form",5,false,false,"self_halt_shelveuping_form",true,false,"600",0,"业务下线");

	private final String processKey;
	private final String taskNode;
	private final String formkey;
	private final String url;
	private final Integer number;
	private final String parentFormKey;
	private final String ticketCategory;


	private final Boolean isUpdateHandler;/*需要修改操作吗?默认是false 不进行修改操作:[目的是控制单个业务的相关数据属性]*/
	private final Boolean isShowHandBar;//显示最后的（grid中的操作按钮）
	private final Boolean resourceAllocationStatus;//显示资源分配 1:可以查看资源分配，0不能查看资源分配
	private final Boolean ticketResourceHandStatus;//控制资源操作
	private final Integer prodCategory;
	private final String jbpmName;


	TASKNodeURL(final String processKey, final String taskNode, final String formkey, final String url,final Integer number,final Boolean isUpdateHandler,final Boolean isShowHandBar,final String parentFormKey,final Boolean resourceAllocationStatus,final Boolean ticketResourceHandStatus,final String ticketCategory,final Integer prodCategory,final String jbpmName) {
		this.processKey = processKey;
		this.taskNode = taskNode;
		this.formkey = formkey;
		this.url = url;
		this.number = number;
		this.isUpdateHandler = isUpdateHandler;
		this.isShowHandBar = isShowHandBar;
		this.parentFormKey = parentFormKey;
		this.resourceAllocationStatus = resourceAllocationStatus;
		this.ticketResourceHandStatus = ticketResourceHandStatus;
		this.ticketCategory = ticketCategory;
		this.prodCategory = prodCategory;
		this.jbpmName = jbpmName;
	}

	public String jbpmName() {
		return this.jbpmName;
	}
	public String ticketCategory() {
		return this.ticketCategory;
	}
	public Integer prodCategory() {
		return this.prodCategory;
	}

	public String processKey() {
		return this.processKey;
	}
	public Boolean resourceAllocationStatus(){
		return this.resourceAllocationStatus;
	}
	public Boolean ticketResourceHandStatus(){
		return this.ticketResourceHandStatus;
	}


	public String taskNode() {
		return this.taskNode;
	}

	public String formkey(){
		return this.formkey;
	}

	public String url(){
		return this.url;
	}
	public String parentFormKey(){
		return this.parentFormKey;
	}


	public Integer number(){
		return this.number;
	}
	public Boolean isShowHandBar(){
		return this.isShowHandBar;
	}

	public Boolean isUpdateHandler(){
		return this.isUpdateHandler;
	}

	public String toString(){
		return this.url;
	}

	public static String urlWithFormKey(String formkey){
		/*遍历枚举形*/
		for (TASKNodeURL e : TASKNodeURL.values()) {
			if(formkey.equalsIgnoreCase(e.formkey())){
				return e.toString();
			}
		}
		return "";
	}
	public static TASKNodeURL currentBeanWithFormKey(String formkey){
		/*遍历枚举形*/
		for (TASKNodeURL e : TASKNodeURL.values()) {
			if(formkey.equalsIgnoreCase(e.formkey())){
				return e;
			}
		}
		return null;
	}

	/**
	 *
	 * @param formkey
	 * @return
	 */
	public static Integer numbuerWithFormKey(String formkey){
		/*遍历枚举形*/
		for (TASKNodeURL e : TASKNodeURL.values()) {
			if(formkey.equalsIgnoreCase(e.formkey())){
				return e.number();
			}
		}
		return null;
	}

	public static String taskNodeWithFormKey(String formkey){
		/*遍历枚举形*/
		for (TASKNodeURL e : TASKNodeURL.values()) {
			if(formkey.equalsIgnoreCase(e.formkey())){
				return e.taskNode();
			}
		}
		return "";
	}
	public static String processKeyWithFormKey(String formkey){
		for (TASKNodeURL e : TASKNodeURL.values()) {
			if(formkey.equalsIgnoreCase(e.formkey())){
				return e.processKey();
			}
		}
		return "";
	}

}
