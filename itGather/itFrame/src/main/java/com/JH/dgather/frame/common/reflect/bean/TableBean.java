package com.JH.dgather.frame.common.reflect.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.JH.dgather.frame.common.snmp.bean.OIDbean;
import com.JH.dgather.frame.util.StringUtil;

/**
 * TableColumnOID.xml中的table元素映射javaBean
 * @author yangDS
 */

@SuppressWarnings("unchecked")
public class TableBean {
	
	private String name; //table的名称
	private String mappingTable; //table的映射表或在内存mapping中的键名
	private String mappingBean; //映射的bean类
	private String type; //类型，与厂商相关
	private String keyColumn; //主键列
	private String hiddenColumn; //隐藏列，是指在mib中并无法直接获取的列，而是要依靠其他可直接获取列而获取值的列
	private ArrayList<HiddenColumnBean> hiddenColumns = new ArrayList<HiddenColumnBean>();
	//	private ArrayList<ColumnBean> columns = new ArrayList<ColumnBean>();
	private HashMap<String, ColumnBean> columns = new HashMap<String, ColumnBean>();
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getMappingTable() {
		return mappingTable;
	}
	
	public void setMappingTable(String mappingTable) {
		this.mappingTable = mappingTable;
	}
	
	public String getMappingBean() {
		return mappingBean;
	}
	
	public void setMappingBean(String mappingBean) {
		this.mappingBean = mappingBean;
	}
	
	public void addColumn(ColumnBean cb) {
		
		columns.put(cb.getName(), cb);
		
	}
	
	public Collection<ColumnBean> getColumnBeans() {
		return columns.values();
	}
	
	public int getColumnCount() {
		return columns.size();
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getKeyColumn() {
		return keyColumn;
	}
	
	public void setKeyColumn(String keyColumn) {
		this.keyColumn = keyColumn;
	}
	
	public String getHiddenColumn() {
		return hiddenColumn;
	}
	
	public void setHiddenColumn(String hiddenColumn) {
		this.hiddenColumn = hiddenColumn;
		//的到hiddenColumn的表达式后，解析出hiddenColumn列表
		
		if (hiddenColumn == null || hiddenColumn.trim().length() == 0)
			return;
		
		String[] statements = hiddenColumn.split("\\|");//多个隐藏列表达式用‘|’分隔
		HiddenColumnBean hc = null;
		String[] temp = null;
		for (String s : statements) {
			hc = new HiddenColumnBean();
			hc.setColumnName(s.substring(0, s.indexOf("=")).trim());
			s = s.substring(s.indexOf("=") + 1);
			
			temp = s.split("\\.");
			hc.setValueColumnName(temp[0]);
			hc.setOperateName(temp[2]);
			hc.setOperateEim(temp[1]);
			s = temp[2];//last2{point}
			int index1 = s.indexOf("{");
			int index2 = s.indexOf("}");
			hc.setEimFlag(s.substring(index1 + 1, index2).trim());
			s = s.substring(0, index1);
			index1 = StringUtil.getFirstIndexOfNumberInStr(s);
			hc.setOperateName(s.substring(0, index1).trim());
			//			hc.setOperateParam1(Integer.parseInt(s.substring(index1).trim()));
			s = s.substring(index1);
			temp = s.split("-");
			hc.setOperateParam1(Integer.parseInt(temp[0].trim()));
			if (temp.length == 2) {
				hc.setOperateParam2(Integer.parseInt(temp[1].trim()));
			}
			
			hiddenColumns.add(hc);
		}
		
	}
	
	public HashMap<String, ColumnBean> getColumns() {
		return columns;
	}
	
	public void setColumns(HashMap<String, ColumnBean> columns) {
		this.columns = columns;
	}
	
	/**
	 * 返回table中名为name的oid值
	 * @param name
	 * @return
	 */
	public String getOIDByName(String name) {
		
		ColumnBean cb = getColumnBeanByName(name);
		if (cb != null) {
			return cb.getOid();
		}
		else {
			return null;
		}
		
	}
	
	public ColumnBean getColumnBeanByName(String name) {
		return columns.get(name);
	}
	
	public ArrayList<HiddenColumnBean> getHiddenColumns() {
		return hiddenColumns;
	}
	
	public void setHiddenColumns(ArrayList<HiddenColumnBean> hiddenColumns) {
		this.hiddenColumns = hiddenColumns;
	}
	
	/**
	 * 计算hiddenColumn的值，并存储在提供的hashmap中
	 */
	
	public void calHiddenColumnValue(ColumnBean cb, OIDbean beanInfo, HashMap store) {
		
		String columnName = cb.getName();
		ArrayList<HiddenColumnBean> choosedList = new ArrayList<HiddenColumnBean>();
		
		for (HiddenColumnBean hcb : this.hiddenColumns) {
			if (hcb.getValueColumnName().equals(columnName)) {
				choosedList.add(hcb);
				
			}
		}
		
		if (choosedList.size() == 0)
			return;
		
		//以下是计算隐藏列的值
		String value = null;
		String operateName = null;
		String flag = null;
		String eim = null;
		int param1 = 0;
		int param2 = 0;
		for (HiddenColumnBean choosed : choosedList) {
			
			operateName = choosed.getOperateName();//取出操作方法
			flag = choosed.getEimFlag();//取出目标
			eim = choosed.getOperateEim();
			param1 = choosed.getOperateParam1();
			param2 = choosed.getOperateParam2();
			String operateStr = null;
			if (eim.equals("oid")) {
				operateStr = beanInfo.getOid();
			}
			else {//否则为value
				operateStr = beanInfo.getValue();
			}
			
			String strTemp = operateStr, strTemp1 = operateStr;
			
			if (operateName.equals("last")) {//如果是进行last截取
				if (flag.equals("point")) {
					for (int i = 0; i < param1; i++) {
						strTemp = strTemp.substring(0, strTemp.lastIndexOf("."));
					}
					if (param2 != 0) {//如果有第二个参数
						for (int i = 0; i < param2; i++) {
							strTemp1 = strTemp1.substring(0, strTemp1.lastIndexOf("."));
						}
						
						value = operateStr.substring(strTemp.length() + 1, strTemp1.length());
					}
					else {
						value = operateStr.substring(strTemp.length() + 1);
					}
					
				}
				else {//暂时视为字符串的倒数第param1位开始
				
					if (param2 != 0) {
						value = operateStr.substring(operateStr.length() - param1, operateStr.length() - param2);
					}
					else {
						value = operateStr.substring(operateStr.length() - param1);
					}
					
				}
				
			}
			else
				if (operateName.equals("first")) {//如果用first截取
					if (flag.equals("point")) {
						for (int i = 0; i < param1; i++) {
							strTemp = strTemp.substring(strTemp.indexOf(".") + 1);
						}
						if (param2 != 0) {//如果有第二个参数
							for (int i = 0; i < param2; i++) {
								strTemp1 = strTemp1.substring(strTemp1.indexOf(".") + 1);
							}
							
							value = operateStr.substring(operateStr.length() - strTemp.length(), operateStr.length()
									- strTemp1.length() - 1);
							
						}
						else {
							value = strTemp;
						}
						
					}
					else {//暂时视为字符串的顺数第param1位开始
						if (param2 != 0) {
							value = operateStr.substring(param1, param2);
						}
						else {
							value = operateStr.substring(param1);
						}
						
					}
					
				}
				else
					if (operateName.equals("sub")) {//如果为sub操作
						if (flag.equals("point")) {
							for (int i = 0; i < param1; i++) {
								strTemp = strTemp.substring(strTemp.indexOf(".") + 1);
							}
							
							for (int i = 0; i < param2; i++) {
								strTemp = strTemp.substring(0, strTemp.lastIndexOf("."));
							}
							
							value = strTemp;
							
						}
						else {
							value = operateStr.substring(param1, param2);
						}
					}
			
			store.put(choosed.getColumnName(), value);
		}
	}
	
	public String toString() {
		return this.columns.toString();
	}
	
}
