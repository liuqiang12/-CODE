package com.JH.dgather.frame.globaldata;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

/**
 * 数据缓存
 * 
 * @author yangDS
 */
@SuppressWarnings("unchecked")
public class DataCache {
	
	private Logger logger = Logger.getLogger(DataCache.class.getName());
	/**
	 * 数据区域
	 */
	
	private HashMap data;
	
	public DataCache() {
		
		data = new HashMap();
		
	}
	
	/**
	 * 创建表
	 * 
	 * @param name
	 */
	private HashMap createTable(String name, boolean cover) {
		
		HashMap hm = (HashMap) data.get(name);
		if (hm != null) {
			if (!cover) {
				
				logger.error("已经存在table[" + name + "]");
			}
			else {
				hm.clear();
				data.put(name, hm);
			}
			
		}
		else {
			hm = new HashMap();
			data.put(name, hm);
		}
		
		return hm;
	}
	
	/**
	 * 返回表
	 * 
	 * @param name
	 */
	public HashMap getTable(String name, boolean delete) {
		
		if (delete) {
			return (HashMap) data.remove(name);
		}
		else {
			return (HashMap) data.get(name);
		}
		
	}
	
	/**
	 * 删除表
	 * 
	 * @param name
	 */
	public void dropTable(String name) {
		
		Object obj = data.remove(name);
		if (obj != null) {
			logger.info(" succeed in dropping Table [" + name + "] ");
		}
		else {
			logger.info(" failed in dropping Table [" + name + "] ");
		}
		
	}
	
	/**
	 * 返回一个表中的所有数据
	 * 
	 * @param tableName
	 *            要查询的表名称
	 * @param clear
	 *            数否要删除该表的数据
	 * @return
	 */
	public Collection getAllRecordsFromTable(String tableName, boolean clear) {
		
		HashMap temp = null;
		
		temp = (HashMap) data.get(tableName);
		if (temp != null) {
			
			if (clear) {
				
				data.remove(tableName);
			}
			return temp.values();
		}
		else {
			logger.info("there is no table named [" + tableName + "] in dataCache");
			return null;
		}
	}
	
	/**
	 * 返回的table中记录主键值为key的记录
	 * 
	 * @param tableName
	 * @param key
	 * @return
	 */
	public Object getRecordByKey(String tableName, Object key) {
		
		HashMap temp = (HashMap) data.get(tableName);
		Object record = null;
		
		if (temp == null) {
			logger.error(" Table [" + tableName + "] dosn't exist  ");
			return null;
		}
		else {
			record = temp.get(key);
			if (record == null) {
				logger.warn(" There's no record can be return by your key [" + key + "] in table [" + tableName + "]");
			}
			return record;
		}
		
	}
	
	/**
	 * 从tableName指定的表中返回key指定的记录的fieldName的值
	 * 
	 * @param tableName
	 * @param key
	 * @param fieldName
	 * @param beanClazz
	 * @return
	 */
	public Object getValueByFieldName(String tableName, Object key, String fieldName, Class beanClazz) {
		
		Object record = getRecordByKey(tableName, key);
		
		if (record == null) {
			return null;
		}
		
		try {
			
			Field f = beanClazz.getDeclaredField(fieldName);
			return f.get(record);
		} catch (SecurityException e) {
			logger.error("Field named [" + fieldName + "] in " + beanClazz.getName() + " can't be access");
			// e.printStackTrace();
		} catch (NoSuchFieldException e) {
			logger.error("No field named [" + fieldName + "] in " + beanClazz.getName());
			// e.printStackTrace();
		} catch (IllegalArgumentException e) {
			logger.error("Illegal Argument in accessing field named [" + fieldName + "] in " + beanClazz.getName());
			// e.printStackTrace();
		} catch (IllegalAccessException e) {
			logger.error("Field named [" + fieldName + "] in " + beanClazz.getName() + " can't be access");
			// e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 将对象保存到tableName指定的表中
	 * 
	 * @param obj
	 *            存储对象
	 * @param tableName
	 *            表名称
	 * @param fieldName
	 *            对象的主键域
	 */
	public void saveObj(Object obj, String tableName, String keyField) {
		
		if (obj == null) {
			logger.error("obj is null");
			return;
		}
		Class beanClazz = obj.getClass();
		
		HashMap hm = createTable(tableName, false);
		Field f;
		try {
			f = beanClazz.getDeclaredField(keyField);
			Object key = f.get(obj);
			hm.put(key, obj);
		} catch (SecurityException e) {
			logger.error("Field named [" + keyField + "] in " + beanClazz.getName() + " can't be access");
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			logger.error("No field named [" + keyField + "] in " + beanClazz.getName());
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			logger.error("Illegal Argument in accessing field named [" + keyField + "] in " + beanClazz.getName());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			logger.error("Field named [" + keyField + "] in " + beanClazz.getName() + " can't be access");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 向tableName指定的表中存储c对象集合
	 * 
	 * @param c
	 *            对象集合
	 * @param beanType
	 *            集合中bean的类型
	 * @param tableName
	 *            存储表名称
	 * @param keyField
	 *            集合中bean存储时采用的主键域
	 * @param delete
	 *           是否删除原表数据
	 */
	public void saveObjectList(Collection c, Class beanType, String tableName, String keyField, boolean delete) {
		
		HashMap table = createTable(tableName, delete);
		Iterator it = c.iterator();
		Field f = null;
		try {
			f = beanType.getDeclaredField(keyField);
		} catch (SecurityException e) {
			logger.error("Field named [" + keyField + "] in " + beanType.getName() + " can't be access");
			return;
			// e.printStackTrace();
		} catch (NoSuchFieldException e) {
			logger.error("No field named [" + keyField + "] in " + beanType.getName());
			return;
		}
		if (f == null) {
			logger.error("some error occurs");
			return;
		}
		Object temp = null;
		Object key = null;
		while (it.hasNext()) {
			temp = it.next();
			try {
				key = f.get(temp);
				
				table.put(key, temp);
			} catch (IllegalArgumentException e) {
				logger.error("Illegal Argument in accessing field named [" + keyField + "] in " + beanType.getName());
				
			} catch (IllegalAccessException e) {
				
				logger.error("Field named [" + keyField + "] in " + beanType.getName() + " can't be access");
			}
		}
	}
	
	/**
	 * 设置名为tableName的表格的数据
	 * 
	 * @param data	需要设置的数据
	 * @param tableName 需要设置的表格名称
	 * @param append 是否追加，true-追加，false-删除原数据
	 */
	public void setTableData(HashMap data, String tableName, boolean append) {
		HashMap table = createTable(tableName, !append);
		table.putAll(data);
		
	}
	
	/**
	 * 清除所有临时表数据。慎用
	 */
	public void clearCache() {
		
		this.data.clear();
	}
	
	public static void main(String[] args) {
		
	}
}
