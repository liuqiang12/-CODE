package com.JH.dgather.frame.xmlparse.service;

import com.JH.dgather.frame.xmlparse.bo.iface.IBase;

/**
 * 标签业务处理基础接口
 * 
 * @author zhengbin
 * 
 */
public interface IBaseService {

	/**
	 * 对应标签解析方式
	 * 
	 * @return 该标签的业务对象实体
	 */
	IBase parse();
}
