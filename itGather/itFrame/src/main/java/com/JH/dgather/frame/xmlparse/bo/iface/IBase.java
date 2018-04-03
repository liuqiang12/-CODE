package com.JH.dgather.frame.xmlparse.bo.iface;

/**
 * 所有标签业务对象都需要继承该类
 * 
 * @author zhengbin
 * 
 */
public interface IBase {
	
	/**
	 * 通过对象获取标签名
	 * @return 当前对象对应的标签名称
	 */
	String getTagNameByObject();
}
