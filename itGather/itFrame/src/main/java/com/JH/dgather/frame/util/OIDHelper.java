package com.JH.dgather.frame.util;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.JH.dgather.frame.common.bean.DeviceInfoBean;
import com.JH.dgather.frame.common.reflect.ReflectUtil;
import com.JH.dgather.frame.common.reflect.bean.ColumnBean;
import com.JH.dgather.frame.common.reflect.bean.TableBean;
import com.JH.dgather.frame.common.snmp.bean.OIDbean;
import com.JH.dgather.frame.common.xml.helper.TableColumnOIDXMlHelper;
import com.JH.dgather.frame.globaldata.DataCache;
import com.JH.dgather.frame.globaldata.GloableDataArea;

/**
 * 
 * 采集帮助类
 * @author yangDS
 *
 */
@SuppressWarnings("unchecked")
public class OIDHelper {
	static Logger logger = Logger.getLogger(OIDHelper.class.getName());
	public static HashMap<Integer, HashMap<String, ArrayList<TableBean>>> oidInfo = GloableDataArea.getOIDCollection();

	/**
	 * 根据厂家和model信息查找对应的table列表信息
	 * @param factory
	 * @param deviceModel
	 * @return
	 */
	public static ArrayList<TableBean> getGatherOIDTableList(int factory, String deviceModel) {

		if (deviceModel == null) {
			deviceModel = "default";
		}

		deviceModel = deviceModel.toLowerCase();
		HashMap<String, ArrayList<TableBean>> factoryTable = oidInfo.get(factory);
		//logger.info("厂家OID"+factoryTable.toString());
		if (factoryTable == null) {
			//                      logger.info( "There are no oid tables  by factory ="+factory+",will fixed with public table" );
			//                      factoryTable = oidInfo.get( -1 );
			return null;
		}
		String choosed = null;
		if (factoryTable.get(deviceModel) == null && !deviceModel.equals("default")) {//model is not default value and there is no specific model returned
			Set<String> keys = factoryTable.keySet();

			for (String key : keys) {
				String[] k = key.split(",");

				for (String keyStr : k) {
					if (keyStr.length() > deviceModel.length()) {
						if (keyStr.charAt(keyStr.length() - 1) == '*') {
							if (keyStr.length() - deviceModel.length() == 1) {//if the keyStr == deviceModel+'*',then it's ok
								String tempKey = keyStr.substring(0, keyStr.length() - 1);
								if (tempKey.equals(deviceModel)) {
									choosed = key;
									break;
								} else {
									continue;
								}
							} else {
								continue;
							}
						}
						continue;//if the length of key  is longer than the length of deviceModel +1 ,ignore
					}

					if (keyStr.length() == deviceModel.length() && keyStr.charAt(keyStr.length() - 1) == '*') {
						//                                      System.out.println( "key:"+key );
						String tempKey = keyStr.substring(0, keyStr.length() - 1);//delete the '*'
						//                                      logger.info( "tempKey:"+tempKey );
						if (deviceModel.indexOf(tempKey) == 0) {//find
							//                                              logger.info( "find "+key );
							choosed = key;
							break;
						}

						continue;

					} else if (keyStr.length() == deviceModel.length() && keyStr.charAt(keyStr.length() - 1) != '*') {
						//the length equal deviceModel's
						//there is no wildcard in key;
						//                                      logger.info( "length equal,but no '*'" );
						if (keyStr.equals(deviceModel)) {//find
							//                                              logger.info( "find "+key );
							choosed = key;
							break;
						}

						continue;
					} else if (keyStr.length() < deviceModel.length() && keyStr.charAt(keyStr.length() - 1) != '*') {
						//                                      logger.info( "the length is shorter than deviceModel's keyStr but indexof "+keyStr );
						//the length is shorter than deviceModel's
						//there is no wildcard in key,ignore;
						continue;
					} else if (keyStr.length() < deviceModel.length() && keyStr.charAt(keyStr.length() - 1) == '*') {
						//                                      logger.info( "deviceModel is more than keyStr but indexof "+keyStr );
						String tempkey = keyStr.substring(0, keyStr.length() - 1);//delete the wildcard '*'
						if (deviceModel.indexOf(tempkey) != -1) {
							//                                              logger.info( "deviceModel indexof "+tempkey );
							if (choosed == null) {// first time to find the model key
								choosed = key;
							} else {// not the first time
								if (tempkey.length() >= choosed.length()) {// cover the old and not accurate key
									choosed = key;
								}
							}
						}
					}
				}
			}
			if (choosed == null)
				choosed = "default";

			return factoryTable.get(choosed);
		} else {
			return factoryTable.get(deviceModel);
		}

	}

	/**
	 * 返回oid表
	 * @param factory
	 * @param deviceModel
	 * @param tableName
	 * @return
	 * myp modify 2014-12-16
	 */
	public static TableBean getOIDTable(int factory, String deviceModel, String tableName) {

		ArrayList<TableBean> list = getGatherOIDTableList(factory, deviceModel);
		TableBean tb = null, temp = null;
		if (list != null && !list.isEmpty()) {
			Iterator it = list.iterator();

			while (it.hasNext()) {
				temp = (TableBean) it.next();
				//                              logger.info( temp.getName( ) );

				if (temp.getName().equals(tableName)) {
					tb = temp;
					break;
				}
			}
		}
		//muyp delete 2014-12-17
//		if (tb == null) {//if haven't find the specific table
//			//try to find the table in public factory,means factory is -1
//			list = getGatherOIDTableList(-1, "default");
//			if (list != null && !list.isEmpty()) {
//				Iterator it = list.iterator();
//
//				while (it.hasNext()) {
//					temp = (TableBean) it.next();
//					//                                                      logger.info( temp.getName( ) );
//					if (temp.getName().equals(tableName)) {
//						tb = temp;
//						break;
//					}
//				}
//			}
//
//		}
//end
		if (tb == null) {
			return null;
		} else {
			try {
				//                              logger.info( tb );
				//                              logger.info( "找到"+tb.getName( ) );
				return (TableBean) BeanUtils.cloneBean(tb);
			} catch (Exception e) {
				logger.error("there is no table named '" + tableName + "' which factory is " + factory + " and model is " + deviceModel);
			}
			return null;
		}

		//              logger.error( "there is no table named '"+tableName+"' which factory is "+factory+" and model is "+deviceModel );

	}

	/**
	 * 
	 * 返回根据指定列返回table
	 * @param factory
	 * @param deviceModel
	 * @param tableName
	 * @param columnNames
	 * @return
	 */
	public static TableBean getOIDTableWithSpecifiedColumn(int factory, String deviceModel, String tableName, ArrayList<String> columnNames) {

		TableBean tb = getOIDTable(factory, deviceModel, tableName);

		if (tb == null) {
		//                      logger.info( "tb is null factory:"+factory+" deviceModel:"+deviceModel+" tableName:"+tableName );
			tb = getOIDTable(-1, "default", tableName);
			if (tb == null)
				return null;
		}
		if (columnNames == null || columnNames.size() <= 0)
			return tb;

		HashMap<String, ColumnBean> columns = new HashMap<String, ColumnBean>();
		ColumnBean cb = null;
		for (String name : columnNames) {
			cb = tb.getColumnBeanByName(name);
			if (cb == null) {
				//                               logger.warn( "there is no column named "+name+" defined in table named "+tb.getName( ) );
				continue;
			}
			columns.put(cb.getName(), cb);
		}

		tb.setColumns(columns);

		return tb;
	}

	/**
	 * 返回列
	 * @param factory
	 * @param deviceModel
	 * @param tableName
	 * @param columnName
	 * @return
	 * muyp modify 2014-12-16 解决XML中配置不灵活的问题 同步修改了getOIDTable方法
	 */
//	public static ColumnBean getColumnBean(int factory, String deviceModel, String tableName, String columnName) {
//		TableBean table = getOIDTable(factory, deviceModel, tableName);
//		if (table == null) {
//			return null;
//		}
//		return table.getColumnBeanByName(columnName);
//	}

	public static ColumnBean getColumnBean(int factory, String deviceModel, String tableName, String columnName) {
		TableBean table = getOIDTable(factory, deviceModel, tableName);
		ColumnBean cb = null;
		if (table == null) {//如果该厂家型号没找到对应table 就到公共库中找
				table = getOIDTable(-1, "default", tableName);
			if(table != null)
			  cb = table.getColumnBeanByName(columnName);
		}else{
			cb = table.getColumnBeanByName(columnName);
			if(cb == null ){//如果该对应厂家型号的table找到了，但是table中没有制定元素，仍然到公共库中找
				table = getOIDTable(-1, "default", tableName);
			if(table != null)
				cb = table.getColumnBeanByName(columnName);
			}
		}
		return cb;
	}
	
	/**
	 * 返回列对应的oid值
	 * @param factory
	 * @param deviceModel
	 * @param tableName
	 * @param columnName
	 * @return
	 */
	public static String getOIDby(int factory, String deviceModel, String tableName, String columnName) {
		ColumnBean cb = getColumnBean(factory, deviceModel, tableName, columnName);
		if (cb == null) {
			return null;
		}
		return cb.getOid();
	}

	/**
	 * 从oidInfo中取出适合机型的snmp oid
	 *myp?该方法不应该放到这里，应该在XML处理中
	 * @return
	 */
	public static ArrayList<TableBean> getSnmpOidFromOidInfo(int factory, String model, ArrayList<String> tableNames) {

		if (model == null) {
			model = "default";
		}
		ArrayList<TableBean> result = new ArrayList<TableBean>();
		HashMap<String, ArrayList<TableBean>> publicTable = oidInfo.get(-1);
		ArrayList<TableBean> modelList = null;
		for (Entry<String, ArrayList<TableBean>> entry : publicTable.entrySet()) {
			//先将默认的加入                       result.addAll(entry.getValue());
			modelList = entry.getValue();
			for (TableBean tb : modelList) {
				//查找符合要求的table
				if (tableNames.contains(tb.getName())) {
					result.add(tb);
				}
			}
		}

		HashMap<String, ArrayList<TableBean>> factoryTable = oidInfo.get(factory);

		if (factoryTable == null) {
			return result;
		} else {//有该厂家

			modelList = factoryTable.get(model);
			boolean found = false;
			if (modelList == null) {
				//如果没找到该型号
				String modelName = null;
				for (Entry<String, ArrayList<TableBean>> entry : factoryTable.entrySet()) {
					modelName = entry.getKey();

					if (modelName.indexOf(model) != -1) {
						//找到该型号
						modelList = entry.getValue();
						for (TableBean tb : modelList) {//查找符合要求的table
							if (tableNames.contains(tb.getName())) {
								result.add(tb);
							}
						}
						found = true;

						break;
					}

				}

				if (!found) {
					modelList = factoryTable.get("default");
					for (TableBean tb : modelList) {//查找符合要求的table
						if (tableNames.contains(tb.getName())) {

							result.add(tb);

						}
					}
				}

			} else {//如果找到该型号

				for (TableBean tb : modelList) {//查找符合要求的table
					if (tableNames.contains(tb.getName())) {

						result.add(tb);

					}
				}

			}

		}

		return result;
	}

	/**
	 * 采集snmp信息并存入缓存中
	 * @param oidInfo 需要采集的oid列表
	 * @param device 目标设备
	 * @param dc 返回数据的存储区域
	 * @throws SocketException 
	 * @throws UnknownHostException 
	 * 
	 */

	public static void getOIDinfoAndSaveInCache(ArrayList<TableBean> oidInfo, DeviceInfoBean device, DataCache dataCache,int retries,long snmpResponsTimeout,long timeout) throws UnknownHostException, SocketException {

		if (oidInfo == null || oidInfo.size() == 0) {
			logger.info("oidInfo:"+oidInfo);
			return;
		}
		Collection<ColumnBean> columns = null;
		HashMap<String, HashMap> dataStore = new HashMap<String, HashMap>();
		HashMap<String, Object> table = null;
		String tableName = null;
		for (TableBean tb : oidInfo) {
			if (tb == null)
				continue;

			tableName = tb.getName();

			if (dataStore == null) {
				dataStore = new HashMap<String, HashMap>();
			}

			columns = tb.getColumnBeans();

			for (ColumnBean c : columns) {
				try {
					getOIDInfo( device, c, dataStore, tb,  retries,snmpResponsTimeout, timeout);
				} catch (Exception e) {
					logger.warn("getOIDINFO", e);
					continue;
				}
			}

			table = getMappingTable(tb.getKeyColumn(), tb.getMappingBean(), dataStore);
			logger.info("Column:"+tb.getKeyColumn()+";MappingBean:"+tb.getMappingBean()+";data:"+dataStore);
			dataCache.setTableData(table, tableName, false);
			dataStore.clear();
		}


	}
//
//	/**
//	 * muyp modify
//	 * @param oidInfo
//	 * @param sp
//	 * @param dataCache
//	 * @throws UnknownHostException
//	 * @throws SocketException
//	 */
//
//	public static void getOIDinfoAndSaveInCache(ArrayList<TableBean> oidInfo, SnmpParam sp, DataCache dataCache) throws UnknownHostException, SocketException {
//
//		if (oidInfo == null || oidInfo.size() == 0) {
//			return;
//		}
//		SnmpUtil util = new SnmpUtil(sp.getIp(), sp.getPort());
//		logger.info("device version:" + sp.getSnmpVersion());
//		util.setVersion(sp.getSnmpVersion());
//		util.setRetries(3);
//		util.setTimeOut(1500);
//		Collection<ColumnBean> columns = null;
//		HashMap<String, HashMap> dataStore = new HashMap<String, HashMap>();
//		HashMap<String, Object> table = null;
//		String tableName = null;
//		for (TableBean tb : oidInfo) {
//			if (tb == null)
//				continue;
//
//			tableName = tb.getName();
//
//			if (dataStore == null) {
//				dataStore = new HashMap<String, HashMap>();
//			}
//
//			columns = tb.getColumnBeans();
//
//			for (ColumnBean c : columns) {
//
//				getOIDInfo(util, sp.getSnmpCommunity(), c, dataStore, tb);
//
//			}
//
//			table = getMappingTable(tb.getKeyColumn(), tb.getMappingBean(), dataStore);
//			dataCache.setTableData(table, tableName, false);
//			dataStore.clear();
//		}
//
//		util.close();
//
//	}

	/**
	 * 得到keyColumn与实例的映射记录
	 * 
	 * @param keyColumn
	 * @param mappingBean
	 * @param data
	 * @return
	 */
	private static HashMap<String, Object> getMappingTable(String keyColumn, String mappingBean, HashMap<String, HashMap> data) {
		HashMap<String, Object> record = new HashMap<String, Object>();
		Object obj = null;
		StringBuffer key = new StringBuffer();

		if (keyColumn == null) {
			logger.error("keyColumn数据为NULL!");
			return record;
		}
		if (mappingBean == null) {
			logger.error("mappingBean数据为NULL!");
			return record;
		}
		for (HashMap unit : data.values()) {

			obj = ReflectUtil.getBeans(mappingBean, unit);
			if (obj == null) {
				logger.error("通过 ReflectUtil.getBeans(" + mappingBean + ", unit:" + unit + ") 取出的obj为null!");
				continue;
			}

			//计算K
			if (keyColumn.equals("null")) {

				key.append(obj.toString());

			} else if (keyColumn.equals("all")) {

				for (Object o : unit.values()) {

					key.append(o.toString());

				}

			} else {

				if (unit.get(keyColumn) == null) {

				} else {
					key.append(unit.get(keyColumn).toString());
				}

			}

			record.put(key.toString(), obj);

			key.delete(0, key.length());
		}

		logger.info("record:"+record);
		return record;
	}

	/**
	 * 得到oid值, 按照一个个table获取值，以行的方式保存table的采集结果dataStore中的结构为<index,<oidname,oidvalue>>
	 * 
	 * @param rout
	 * @param c
	 * @param dataStore
	 * @throws Exception 
	 */
	private static void getOIDInfo(DeviceInfoBean device, ColumnBean c, HashMap<String, HashMap> dataStore, TableBean tb,int retries,long snmpResponsTimeout,long timeout) throws Exception {
		try {
			String oid = c.getOid();
			if (oid == null || oid.isEmpty())
				return;
			
			List<OIDbean> beans = GloableDataArea.su.walk(device, oid, retries,snmpResponsTimeout, timeout);
			HashMap<String, String> hm = null;
			if (beans != null && beans.size() > 0) {
				for (int i = 0; i < beans.size(); i++) {
					String key = getKey(c.getOid(), beans.get(i).getOid());
					hm = dataStore.get(key);
					if (hm == null) {
						hm = new HashMap<String, String>();
						dataStore.put(key, hm);
					}
					if("ifAlias".equalsIgnoreCase(c.getName())|| "ifName".equalsIgnoreCase(c.getName()))
					{
						logger.debug("dddTest:"+c.getName()+":"+beans.get(i).getValue());
					}
					hm.put(c.getName(), beans.get(i).getValue());
					tb.calHiddenColumnValue(c, beans.get(i), hm);//

				}
			}
		} catch (Exception e) {
			logger.error("团体字:"+device.getSnmpCommunity()+"；OID:"+c.getOid(),e);
			throw e;
		}
		

	}

	private static String getKey(String columnOID, String curOID) {
		//                if(curOID.startsWith("."))curOID = curOID.substring(1);
		int index = curOID.indexOf(columnOID);
		if (index > -1) {
			return curOID.substring(index + columnOID.length());
		} else {
			logger.info("计算key值出现异常: columnOID=" + columnOID + "|curOID=" + curOID);
			return curOID.substring(curOID.length() - 1);//否则保留最后一个
		}
	}

	public static void main(String[] args) {
		oidInfo = TableColumnOIDXMlHelper.initGloadbleOidPool();
//		ColumnBean ifInOctets32 = OIDHelper.getColumnBean(0, "ma5608", "Net_Element_If_Info", "ifInOctets");
//		ColumnBean ifOutOctets32 = OIDHelper.getColumnBean(0, "ma5608", "Net_Element_If_Info", "ifOutOctets");
//		System.out.println("ifInOctets32="+ifInOctets32.getOid());
//		System.out.println("ifOutOctets32="+ifOutOctets32.getOid());
//		ColumnBean ifOperStatus = OIDHelper.getColumnBean(0, "ma5608", "Net_Element_If_Info", "ifOperStatus");
//		System.out.println("ifOperStatus="+ifOperStatus.getOid());
		ArrayList<String> columnNames = new ArrayList<String>(); 
			
				columnNames.add("entPhysicalDescr");
				columnNames.add("entPhysicalContainedIn");
				columnNames.add("entPhysicalClass");
				columnNames.add("entPhysicalParentRelPos");
				columnNames.add("entPhysicalName");
				columnNames.add("entPhysicalSoftwareRev");
				columnNames.add("entPhysicalSerialNum");
			
		

		TableBean table  = OIDHelper.getOIDTableWithSpecifiedColumn(72,"AN5516-01", "Net_EntPhysicalEntry", columnNames);
		System.out.println("ipAdEntAddr="+table.getOIDByName("entPhysicalDescr"));
	}
}
