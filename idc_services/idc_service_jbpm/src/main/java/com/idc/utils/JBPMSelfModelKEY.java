package com.idc.utils;


/**
 * 都是自有业务模型
 * @author Administrator
 */
public enum JBPMSelfModelKEY {

	预占预勘察流程("idc_ticket_self_pre_accept","100","PA_"),
	开通流程("idc_ticket_self_open","200","KT_"),
	停机流程("idc_ticket_self_pause","400","TJ_"),
	复通流程("idc_ticket_self_recover","500","FT_"),
	/*预占变更流程("idc_ticket_self_pre_change","300","BGYZ_"),*/
	下线流程("idc_ticket_self_halt","600","XX_"),
	开通变更流程("idc_ticket_self_open_change","700","BK_"),//目前先做开通变更流程
	临时测试流程("idc_ticket_temporary","800","CS_"),
	业务变更流程("idc_ticket_self_business_change","900","BA_");



	private final String value;
	private final String catalog;
	private final String prefix;

	JBPMSelfModelKEY(final String value, final String catalog, final String prefix) {
		this.value = value;
		this.catalog = catalog;
		this.prefix = prefix;
	}
	public String value() {
		return this.value;
	}

	public String catalog() {
		return this.catalog;
	}

	public String prefix(){
		return this.prefix;
	}

	public String toString(){
		return this.value;
	}
	/*通过catalog 获取*/

	public static JBPMSelfModelKEY getCurrentWithCatalog(String catalog){
		/*遍历枚举形*/
		for (JBPMSelfModelKEY e : JBPMSelfModelKEY.values()) {
			if(catalog.equalsIgnoreCase(e.catalog())){
				return e;
			}
		}
		return null;
	}


	/**
	 * 通过key获取model配置项
	 * @param key
	 * @return
	 */
	public static JBPMSelfModelKEY JBPMModelKEYCurrentWithKey(String key){
		/*遍历枚举形*/
		for (JBPMSelfModelKEY e : JBPMSelfModelKEY.values()) {
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
	public static JBPMSelfModelKEY JBPMModelKEYWithKey(String key){
		/*遍历枚举形*/
		String catalog = "";
		for (JBPMSelfModelKEY e : JBPMSelfModelKEY.values()) {
			if(key.equalsIgnoreCase(e.toString())){
					catalog = e.catalog();
				 break;
			}
		}
		if(catalog != null && !"".equals(catalog)){
			//得到下一个
			String nextCatalog = String.valueOf(Integer.valueOf(catalog)+100);
			for (JBPMSelfModelKEY e : JBPMSelfModelKEY.values()) {
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
	public static JBPMSelfModelKEY upJBPMModelKEYWithKey(String key){
		/*遍历枚举形*/
		String catalog = "";
		for (JBPMSelfModelKEY e : JBPMSelfModelKEY.values()) {
			if(key.equalsIgnoreCase(e.toString())){
				catalog = e.catalog();
				break;
			}
		}
		if(catalog != null && !"".equals(catalog)){
			//得到下一个
			String upCatalog = String.valueOf(Integer.valueOf(catalog)-100);
			for (JBPMSelfModelKEY e : JBPMSelfModelKEY.values()) {
				if(upCatalog.equalsIgnoreCase(e.catalog())){
					return e;
				}
			}
		}
		return null;
	}
	public static void main(String[] args) {
		JBPMSelfModelKEY key = JBPMModelKEYWithKey("idc_ticket_open");
		System.out.println(key.toString());
		System.out.println(key.catalog());
	}

}
