package com.JH.dgather.frame.xmlparse.beans;

import org.apache.log4j.Logger;

/**
 * <code>VarBean</code> 用来描述参数信息的Bean
 * @author Administrator
 *
 */
public class VarBean {
	private Logger logger = Logger.getLogger(VarBean.class);
	
	private String type = "";
	private Object object = "";
	
	public VarBean(String type, Object object) {
		this.type = type;
		this.object = object;
	}
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * @return the object
	 */
	public Object getObject() {
		return object;
	}
	
}
