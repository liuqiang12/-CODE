package com.idc.utils;


/**
 * 枚举列出流程模型KEY
 * @author Administrator
 */
public enum JBPMModelKEY {

	预占预勘察流程("idc_ticket_pre_accept","100","PA_",1,"预勘/预占工单","pre_accept_apply_form","jbpm/ticket/author_pre_apply",null),
	开通流程("idc_ticket_open","200","KT_",1,"开通工单","open_accept_apply_form","jbpm/ticket/open/author_pre_apply_create","100"),
	业务变更流程("idc_ticket_business_change","900","BA_",1,"业务变更工单","business_change_accept_apply_form","jbpm/ticket/open/businesschange/author_pre_apply_create","200"),
	开通变更流程("idc_ticket_open_change","700","BK_",1,"变更开通工单","open_change_accept_apply_form","jbpm/ticket/open/change/author_pre_apply_create","200"),//目前先做开通变更流程
	停机流程("idc_ticket_pause","400","TJ_",1,"停机工单","pause_accept_apply_form","jbpm/ticket/open/pause/author_pre_apply_create","200"),
	复通流程("idc_ticket_recover","500","FT_",1,"复通工单","recover_accept_apply_form","jbpm/ticket/open/recover/author_pre_apply_create","400"),
	/*预占变更流程("idc_ticket_pre_change","300","BGYZ_",1,"预占变更工单","","",""),*/
	下线流程("idc_ticket_halt","600","XX_",1,"下线工单","halt_accept_apply_form","jbpm/ticket/open/halt/author_pre_apply_create","200"),
	临时测试流程("idc_ticket_temporary","800","CS_",1,"临时测试工单","temporary_accept_apply_form","jbpm/ticket/open/temporary/author_pre_apply",null),

	自有业务_预占预勘察流程("idc_ticket_self_pre_accept","100","PA_",0,"预勘/预占工单","self_pre_accept_apply_form","jbpm/ticket/self/author_pre_apply",null),
	自有业务_开通流程("idc_ticket_self_open","200","KT_",0,"开通工单","self_open_accept_apply_form","jbpm/ticket/self/open/author_pre_apply_create","100"),
	自有业务_业务变更流程("idc_ticket_self_business_change","900","BA_",0,"业务变更工单","self_business_change_accept_apply_form","jbpm/ticket/self/open/businesschange/author_pre_apply_create","200"),
	自有业务_开通变更流程("idc_ticket_self_open_change","700","BK_",0,"变更开通工单","self_open_change_accept_apply_form","jbpm/ticket/self/open/change/author_pre_apply_create","200"),//目前先做开通变更流程
	自有业务_停机流程("idc_ticket_self_pause","400","TJ_",0,"停机工单","self_pause_accept_apply_form","jbpm/ticket/self/open/pause/author_pre_apply_create","200"),
	自有业务_复通流程("idc_ticket_self_recover","500","FT_",0,"复通工单","self_recover_accept_apply_form","jbpm/ticket/self/open/recover/author_pre_apply_create","400"),
	/*自有业务_预占变更流程("idc_ticket_self_pre_change","300","BGYZ_",0,"预占变更工单","","",null),*/
	自有业务_下线流程("idc_ticket_self_halt","600","XX_",0,"下线工单","self_halt_accept_apply_form","jbpm/ticket/self/open/halt/author_pre_apply_create","200");
	/*自有业务_临时测试流程("idc_ticket_self_halt","800","CS_",0,"临时测试工单","","",null);*/


	private final String value;
	private final String catalog;
	private final String prefix;
	private final Integer prodCategory;
	private final String tabsTitle;
	private final String formkey;
	private final String url;
	private final String parentTicketCategory;
	/*通过catalog  prodCategory  */

	JBPMModelKEY(final String value, final String catalog, final String prefix,final Integer prodCategory,final String tabsTitle,final String formkey,final String url,final String parentTicketCategory) {
		this.value = value;
		this.catalog = catalog;
		this.prefix = prefix;
		this.prodCategory = prodCategory;
		this.tabsTitle = tabsTitle;
		this.formkey = formkey;
		this.url = url;
		this.parentTicketCategory = parentTicketCategory;
	}
	public String value() {
		return this.value;
	}

	public String formkey() {
		return this.formkey;
	}

	public String url(){
		return this.url;
	}

	public String parentTicketCategory(){
		return this.parentTicketCategory;
	}


	public String catalog() {
		return this.catalog;
	}

	public String prefix(){
		return this.prefix;
	}


	public String tabsTitle(){
		return this.tabsTitle;
	}

	public Integer prodCategory(){
		return this.prodCategory;
	}

	public String toString(){
		return this.value;
	}


	public static JBPMModelKEY getCurrentWithCatalog(String catalog,Integer prodCategory){
		/*遍历枚举形*/
		for (JBPMModelKEY e : JBPMModelKEY.values()) {
			if(catalog.equalsIgnoreCase(e.catalog()) && prodCategory == e.prodCategory()){
				return e;
			}
		}
		return null;
	}

	public static Boolean traverseFormKey(String formKeys){
		/*遍历枚举形*/
		for (JBPMModelKEY e : JBPMModelKEY.values()) {
			if(formKeys.equals(e.formkey)){
				return true;
			}
		}
		return false;
	}
	/*获取开通流程*/
	public static JBPMModelKEY getKTLCWithkey(String key) {
		/*遍历枚举形*/
		Integer prodCategory = 1;
		for (JBPMModelKEY e : JBPMModelKEY.values()) {
			if (key.equalsIgnoreCase(e.toString())) {
				prodCategory = e.prodCategory();
				break;
			}
		}
		if (prodCategory == 1) {
			return JBPMModelKEY.开通流程;
		} else if (prodCategory == 0) {
			return JBPMModelKEY.自有业务_开通流程;
		}
		return null;
	}




	/**
	 * 通过key获取model配置项
	 * @param key
	 * @return
	 */
	public static JBPMModelKEY JBPMModelKEYCurrentWithKey(String key){
		/*遍历枚举形*/
		for (JBPMModelKEY e : JBPMModelKEY.values()) {
			if(key.equalsIgnoreCase(e.toString())){
				return e;
			}
		}
		return null;
	}

	/**
	 * 通过key获取下一个model配置项
	 *
	 * @param key
	 * @return
	 */
	public static JBPMModelKEY JBPMModelKEYWithKey(String key){
		/*遍历枚举形*/
		String catalog = "";
		for (JBPMModelKEY e : JBPMModelKEY.values()) {
			if(key.equalsIgnoreCase(e.toString())){
					catalog = e.catalog();
				 break;
			}
		}
		if(catalog != null && !"".equals(catalog)){
			//得到下一个
			String nextCatalog = String.valueOf(Integer.valueOf(catalog)+100);
			for (JBPMModelKEY e : JBPMModelKEY.values()) {
				if(nextCatalog.equalsIgnoreCase(e.catalog())){
					return e;
				}
			}
		}
		return null;
	}



	/**
	 * 通过key获取上一个model配置项
	 * @param key
	 */
	public static JBPMModelKEY upJBPMModelKEYWithKey(String key){
		/*遍历枚举形*/
		String catalog = "";
		for (JBPMModelKEY e : JBPMModelKEY.values()) {
			if(key.equalsIgnoreCase(e.toString())){
				catalog = e.catalog();
				break;
			}
		}
		if(catalog != null && !"".equals(catalog)){
			//得到下一个
			String upCatalog = String.valueOf(Integer.valueOf(catalog)-100);
			for (JBPMModelKEY e : JBPMModelKEY.values()) {
				if(upCatalog.equalsIgnoreCase(e.catalog())){
					return e;
				}
			}
		}
		return null;
	}
	public static void main(String[] args) {
		JBPMModelKEY key = JBPMModelKEYWithKey("idc_ticket_open");
		System.out.println(key.toString());
		System.out.println(key.catalog());
	}

}
