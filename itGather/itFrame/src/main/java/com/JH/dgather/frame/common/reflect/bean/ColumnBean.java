package com.JH.dgather.frame.common.reflect.bean;


/**
 * TableColumnOID.xml中的column元素映射javaBean
 * 
 * @author yangDS
 *
 */
public class ColumnBean {
	/**
	 * 
	 */
	private String name;
	private String oid;
	private String index;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getOid() {
		return oid;
	}
	
	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}
	
	
	
}
