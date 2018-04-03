/*
 * @(#)OperaterHandle.java 03/19/2012
 *
 * Copyright 2011 Zone, All rights reserved.
 */
package com.JH.dgather.frame.xmlparse;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.JH.dgather.frame.common.cmd.CMDService;
import com.JH.dgather.frame.common.exception.TelnetException;
import com.JH.dgather.frame.xmlparse.beans.VarBean;
import com.JH.dgather.frame.xmlparse.exception.DataStorageException;
import com.JH.dgather.frame.xmlparse.exception.ProcessException;
import com.JH.dgather.frame.xmlparse.exception.TagException;
import com.JH.dgather.frame.xmlparse.model.FlowTag;
import com.JH.dgather.frame.xmlparse.model.RuleTag;

/**
 * <code>OperaterHandle</code> 操作类
 * @author Wang Xuedan
 * @version 1.0 2012/3/19
 */
public class OperaterHandle {
	private Logger logger = Logger.getLogger(OperaterHandle.class);
	
	private DataStorage dataStorage = null;
	
	public OperaterHandle() {
		dataStorage = new DataStorage();
	}
	
	/**
	 * @return the dataStorage
	 */
	public DataStorage getDataStorage() {
		return dataStorage;
	}
	
	/**
	 * <code>addArgs</code> 增加一个全局参数
	 * @param key: 键值
	 * @param obj： 数据内容
	 * @throws DataStorageException
	 */
	public void addArgs(String key, Object obj) throws DataStorageException {
		this.dataStorage.addData(DataStorage.KEY_ARGS, key, obj);
	}
	
	/**
	 * <code>addResult</code> 增加一个数据结果
	 * @param key: 键值
	 * @param obj： 数据内容
	 * @throws DataStorageException
	 */
	public void addResult(String key, Object obj) throws DataStorageException {
		this.dataStorage.addData(DataStorage.KEY_RESULT, key, obj);
	}
	
	/**
	 * <code>addSource</code> 增加一个数据结果
	 * @param key: 键值
	 * @param obj： 数据内容
	 * @throws DataStorageException
	 */
	public void addSource(String key, Object obj) throws DataStorageException {
		this.dataStorage.addData(DataStorage.KEY_SOURCE, key, obj);
	}
	
	/**
	 * <code>addBuffer</code> 增加一个Buffer结果
	 * @param key: 键值
	 * @param obj： 数据内容
	 * @throws DataStorageException
	 */
	public void addBuffer(String key, Object obj) throws DataStorageException {
		this.dataStorage.addData(DataStorage.KEY_BUFFER, key, obj);
	}
	
	/**
	 * <code>addData</code> 增加一个新数据
	 * @param paramName 仓库名称
	 * @param key 键值
	 * @param obj 数据内容
	 * @throws DataStorageException
	 */
	public void addData(String paramName, String key, Object obj) throws DataStorageException {
		this.dataStorage.addData(paramName, key, obj);
	}
	
	/**
	 * 替换全局参数
	 * 
	 * @param 仓库名称
	 * @param key
	 *            键值
	 * @param obj
	 *            数据内容
	 * @return 替换结果，true替换成功，false替换失败
	 */
	public boolean replaceData(String paramName, String key, Object obj) {
		return this.dataStorage.replaceData(paramName, key, obj);
	}
	
	/**
	 * <code>getArgs</code> 获取全局参数
	 * @param key: 键值
	 * @return 参数数据
	 * @throws DataStorageException
	 */
	public VarBean getArgs(String key) throws DataStorageException {
		return this.dataStorage.getData(DataStorage.KEY_ARGS, key);
	}
	
	/**
	 * <code>getResult</code> 获取结果数据
	 * @param key: 键值
	 * @return 参数数据
	 * @throws DataStorageException
	 */
	public VarBean getSource(String key) throws DataStorageException {
		return this.dataStorage.getData(DataStorage.KEY_SOURCE, key);
	}
	
	/**
	 * <code>getResult</code> 获取结果数据
	 * @param key: 键值
	 * @return 参数数据
	 * @throws DataStorageException
	 */
	public VarBean getResult(String key) throws DataStorageException {
		return this.dataStorage.getData(DataStorage.KEY_RESULT, key);
	}
	
	/**
	 * <code>getBuffer</code> 获取buffer数据
	 * @param key: 键值
	 * @return 参数数据
	 * @throws DataStorageException
	 */
	public VarBean getBuffer(String key) throws DataStorageException {
		return this.dataStorage.getData(DataStorage.KEY_BUFFER, key);
	}
	
	/**
	 * <code>getData</code> 获取数据
	 * @param paramName 仓库名称
	 * @param key 键值
	 * @return 参数数据
	 * @throws DataStorageException
	 */
	public VarBean getData(String paramName, String key) throws DataStorageException {
		return this.dataStorage.getData(paramName, key);
	}
	
	/**
	 * <code>flowProcess</code> 执行流程
	 * @param device
	 * @param flowid
	 * @param gatherclass
	 */
	public void flowProcess(CMDService tl, FlowTag flowTag) throws TagException, ProcessException, IOException, Exception,
			TelnetException {
		if (tl == null) {
			throw new TelnetException("执行流程前请确认设备是否telnet！");
		}
		
		logger.debug("开始执行流程!");
		flowTag.process(tl, this);
	}
	
	public void ruleProcess(CMDService tl, RuleTag ruleTag) throws TagException, ProcessException, IOException, Exception, TelnetException {
		if (tl == null) {
			throw new TelnetException("执行流程前请确认设备是否telnet！");
		}
		
		logger.debug("开始执行规则!");
		ruleTag.process(tl, this);
	}
}
