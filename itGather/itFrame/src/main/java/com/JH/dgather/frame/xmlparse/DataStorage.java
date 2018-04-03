package com.JH.dgather.frame.xmlparse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.JH.dgather.frame.xmlparse.beans.VarBean;
import com.JH.dgather.frame.xmlparse.beans.VarBeanStatic;
import com.JH.dgather.frame.xmlparse.exception.DataStorageException;

/**
 * <code>DataStorage</code> 数据仓库 用于存储配置文件解析模块中所有涉及到的数据，该数据仓库可提供
 * 业务模块提取数据，也可以被模块模块内部用于参数的提取
 * 数据存储规则:
 * -----------------------------------------------------
 * |主键值     |   辅键值     |     参数数据(VarBean类型)    
 * -----------------------------------------------------
 * 如下例：
 * result     |   isisNeis  |   ......
 * result     |   ospfTotles|   ......
 * forList    |   ${result.isisNeis}
 * args       |   userList  |   ......
 * forItems   |   ${args.userList}
 * 
 * 说明：
 * result：是默认存在的主键值，用于存储配置文件解析时， result标签得出的结果
 * args:   是默认存在的主键值，用于业务模块向配置文件解析模块传递参数
 * buffer: 是默认存在的主键值，用于存放类似回显信息等结果，允许有buffer属性的标签的数据。
 * 
 * @author Wang Xuedan
 * @version 1.0, 2012/3/20
 */
public class DataStorage {
	private Logger logger = Logger.getLogger(DataStorage.class);
	
	private HashMap<String, HashMap<String, VarBean>> dataHm;
	
	private HashMap<String, VarBean> resultHm;
	private HashMap<String, VarBean> argsHm;
	private HashMap<String, VarBean> bufferHm;
	
	public static final String KEY_RESULT = "result";
	public static final String KEY_ARGS = "args";
	public static final String KEY_BUFFER = "buffer";
	public static final String KEY_SOURCE = "source";
	public DataStorage() {
		dataHm = new HashMap<String, HashMap<String, VarBean>>();
		
		resultHm = new HashMap<String, VarBean>();
		argsHm = new HashMap<String, VarBean>();
		bufferHm = new HashMap<String, VarBean>();
		dataHm.put("result", resultHm);
		dataHm.put("args", argsHm);
		dataHm.put("buffer", bufferHm);
	}
	
	/**
	 * <code>addData</code> 添加数据
	 * @param mainKey  主键值
	 * @param subKey   铺键值
	 * @param varBean  数据
	 */
	public void addData(String mainKey, String subKey, Object data) throws DataStorageException {
		logger.debug("mainKey:"+mainKey);
		logger.debug("subKey:"+subKey);
		//logger.error("data:"+data.toString());
		if (mainKey == null || subKey == null || data == null || "".equals(mainKey) || "".equals(subKey)) {
			logger.error("DataStorage类内addData方法的参数信息存在null或空字符串的错误！");
			throw new DataStorageException("DataStorage类内addData方法的参数信息存在null或空字符串的错误！");
		}
		
		HashMap<String, VarBean> mainHm = null;
		if (!this.dataHm.containsKey(mainKey)) {
			mainHm = new HashMap<String, VarBean>();
			this.dataHm.put(mainKey, mainHm);
		}
		else {
			mainHm = this.dataHm.get(mainKey);
		}
		
		if (mainHm.containsKey(subKey)) {
			logger.error("主键为:" + mainKey + "的数据仓库中，已经存在键值为： " + subKey + "的数据信息!");
			throw new DataStorageException("主键为:" + mainKey + "的数据仓库中，已经存在键值为： " + subKey + "的数据信息!");
		}
		
		VarBean varBean = null;
		if (data instanceof String) {
			varBean = new VarBean(VarBeanStatic.TYPE_STRING, data);
			mainHm.put(subKey, varBean);
		}
		else if (data instanceof Integer) {
			varBean = new VarBean(VarBeanStatic.TYPE_STRING, Integer.toString((Integer) data));
			mainHm.put(subKey, varBean);
		}
		else if (data instanceof List) {
			varBean = new VarBean(VarBeanStatic.TYPE_LIST, data);
			mainHm.put(subKey, varBean);
		}
		else if (data instanceof HashMap) {
			varBean = new VarBean(VarBeanStatic.TYPE_HASHMAP, data);
			mainHm.put(subKey, varBean);
		}
		else if (data instanceof VarBean) {
			varBean = (VarBean) data;
			mainHm.put(subKey, varBean);
		}
		else {
			logger.error("存储到全局数据仓库中的数据，必须是String或List或HashMap类型！");
			throw new DataStorageException("存储到全局数据仓库中的数据，必须是String或List或HashMap类型！");
		}
		
	}
	
	/**
	 * 替换数据
	 * 
	 * @param mainKey
	 *            主键值
	 * @param subKey
	 *            铺键值
	 * @param varBean
	 *            数据
	 * @return 替换结果，true替换成功，替换失败
	 */
	public boolean replaceData(String mainKey, String subKey, Object data) {
		if (logger.isDebugEnabled()) {
			logger.debug("mainKey:" + mainKey);
			logger.debug("subKey:" + subKey);
		}
		if (null == mainKey || null == subKey || null == data
				|| "".equals(mainKey) || "".equals(subKey)) {
			logger.error("DataStorage类内replaceData方法的参数信息存在null或空字符串的错误！");
			return false;
		}

		HashMap<String, VarBean> mainHm = null;
		if (!this.dataHm.containsKey(mainKey)) {
			logger.error("主键为:" + mainKey + "的数据仓库不存在！无法进行数据替换");
			return false;
		} else {
			mainHm = this.dataHm.get(mainKey);
		}

		if (!mainHm.containsKey(subKey)) {
			logger.error("主键为:" + mainKey + "的数据仓库中，不存在键值为： " + subKey
					+ "的数据信息!无法进行数据替换");
			return false;
		}

		VarBean varBean = null;
		if (data instanceof String) {
			varBean = new VarBean(VarBeanStatic.TYPE_STRING, data);
			mainHm.put(subKey, varBean);
		} else if (data instanceof Integer) {
			varBean = new VarBean(VarBeanStatic.TYPE_STRING,
					Integer.toString((Integer) data));
			mainHm.put(subKey, varBean);
		} else if (data instanceof List) {
			varBean = new VarBean(VarBeanStatic.TYPE_LIST, data);
			mainHm.put(subKey, varBean);
		} else if (data instanceof HashMap) {
			varBean = new VarBean(VarBeanStatic.TYPE_HASHMAP, data);
			mainHm.put(subKey, varBean);
		} else if (data instanceof VarBean) {
			varBean = (VarBean) data;
			mainHm.put(subKey, varBean);
		} else {
			logger.error("存储到全局数据仓库中的数据，必须是String，Integer，List或HashMap类型！无法替换数据仓库中主键为:"
					+ mainKey + "，键值为： " + subKey + "的数据信息!");
			return false;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("替换数据仓库中主键为:" + mainKey + "，键值为： " + subKey
					+ "的数据信息成功!");
		}
		return true;
	}
	
	/**
	 * <code>getData</code> 获取数据
	 * @param mainKey 主键值
	 * @param subKey  铺键值
	 * @return 数据信息
	 * @throws DataStorageException
	 */
	public VarBean getData(String mainKey, String subKey) throws DataStorageException {
		VarBean varBean = null;
		logger.info("mainKey:"+mainKey+";subKey:"+subKey);
		if (mainKey == null || "".equals(mainKey)) {
			logger.error("DataStorage类内getData方法的参数中mainKey为null或空字符串的错误！");
			throw new DataStorageException("DataStorage类内getData方法的参数中mainKey为null或空字符串的错误！");
		}
		
		if (this.dataHm.containsKey(mainKey)) {
			if (subKey == null || "".equals(subKey)) {
				logger.error("DataStorage类内getData方法的参数中subKey为null或空字符串的错误！");
				throw new DataStorageException("DataStorage类内getData方法的参数中subKey为null或空字符串的错误！");
			}
			
			HashMap<String, VarBean> mainHm = this.dataHm.get(mainKey);
			if (mainHm.containsKey(subKey)) {
				varBean = mainHm.get(subKey);
			}
			else {
				//在规则配置里面规则名称不存在，但是取得值得时候需要改规则名的数据。返回null，web层需要处理
				logger.error("主键为:" + mainKey + "的数据仓库中，不存在键值为： " + subKey + " 的数据信息，请确认！");
				return null;
				//throw new DataStorageException("主键为:" + mainKey + " 的数据仓库中，不存在键值为： " + subKey + "的数据信息，请确认！");
			}
		}
		else {
			logger.warn("全局数据列表中不存在主键为： " + mainKey + " 的数据仓库，请确认！");
			return null;
			//logger.error("全局数据列表中不存在主键为： " + mainKey + " 的数据仓库，请确认！");
			//throw new DataStorageException("全局数据列表中不存在主键为： " + mainKey + " 的数据仓库，请确认！");
		}
		
		return varBean;
	}
	
	/**
	 * <code>createList</code> 创建一个List数据集合
	 * @return
	 */
	public List<VarBean> createList() {
		return new ArrayList<VarBean>();
	}
	
	/**
	 * <code>createHashMap</code> 创建一个HashMap数据集合
	 * @return
	 */
	public HashMap<String, VarBean> createHashMap() {
		return new HashMap<String, VarBean>();
	}
	
	/**
	 * <code>addDataToList</code> 往指定的一个List数据集合中增加数据
	 * @param list
	 * @param data
	 * @throws DataStorageException
	 */
	public void addDataToList(List<VarBean> list, Object data) throws DataStorageException {
		if (list == null) {
			logger.error("数据list为null!");
			throw new DataStorageException("数据list为null!");
		}
		if (data == null) {
			logger.error("数据data为null!");
			throw new DataStorageException("数据data为null!");
		}
		
		VarBean varBean = null;
		if (data instanceof String) {
			varBean = new VarBean(VarBeanStatic.TYPE_STRING, data);
			list.add(varBean);
		}
		else if (data instanceof List) {
			varBean = new VarBean(VarBeanStatic.TYPE_LIST, data);
			list.add(varBean);
		}
		else if (data instanceof HashMap) {
			varBean = new VarBean(VarBeanStatic.TYPE_HASHMAP, data);
			list.add(varBean);
		}
		else {
			logger.error("数据data必须是String或List或HashMap类型！");
			throw new DataStorageException("数据data必须是String或List或HashMap类型！");
		}
	}
	
	/**
	 * <code>addDataToHashMap</code> 往指定的一个HashMap数据集合中增加数据
	 * @param hashMap
	 * @param key
	 * @param data
	 * @throws DataStorageException
	 */
	public void addDataToHashMap(HashMap<String, VarBean> hashMap, String key, Object data) throws DataStorageException {
		if (hashMap == null) {
			logger.error("数据list为null!");
			throw new DataStorageException("数据list为null!");
		}
		if (key == null || "".equals(key)) {
			logger.error("数据key为null或空!");
			throw new DataStorageException("数据key为null或空!");
		}
		if (data == null) {
			logger.error("数据data为null!");
			throw new DataStorageException("数据data为null!");
		}
		if (hashMap.containsKey(key)) {
			logger.error("数据集合中已经存在此键值的数据！key: " + key);
			throw new DataStorageException("数据集合中已经存在此键值的数据！key: " + key);
		}
		
		VarBean varBean = null;
		if (data instanceof String) {
			varBean = new VarBean(VarBeanStatic.TYPE_STRING, data);
			hashMap.put(key, varBean);
		}
		else if (data instanceof List) {
			varBean = new VarBean(VarBeanStatic.TYPE_LIST, data);
			hashMap.put(key, varBean);
		}
		else if (data instanceof HashMap) {
			varBean = new VarBean(VarBeanStatic.TYPE_HASHMAP, data);
			hashMap.put(key, varBean);
		}
		else {
			logger.error("数据data必须是String或List或HashMap类型！");
			throw new DataStorageException("数据data必须是String或List或HashMap类型！");
		}
	}
}
