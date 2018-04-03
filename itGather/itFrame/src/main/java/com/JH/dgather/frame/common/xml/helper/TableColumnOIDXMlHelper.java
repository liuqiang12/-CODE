package com.JH.dgather.frame.common.xml.helper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;

import com.JH.dgather.frame.common.reflect.ReflectUtil;
import com.JH.dgather.frame.common.reflect.bean.ColumnBean;
import com.JH.dgather.frame.common.reflect.bean.TableBean;
import com.JH.dgather.frame.common.xml.XMLFileHandler;

public class TableColumnOIDXMlHelper {
	private static XMLFileHandler xmlFileHandler = null;
	static{
		//String currentDir = System.getProperty("user.dir");
		//String fileName = currentDir + File.separator + "TableColumnOID_v3.xml";
		
		String currentDir = TableColumnOIDXMlHelper.class.getProtectionDomain().getCodeSource().getLocation().getFile();
		if(currentDir.indexOf("/lib")!=-1){
			currentDir = currentDir.split("/lib")[0];
		}else if(currentDir.indexOf("/target")!=-1){
			currentDir = currentDir.split("/target")[0];
		}
		String fileName = currentDir+ "/TableColumnOID_v3.xml";
		//System.out.println(fileName);
		xmlFileHandler = new XMLFileHandler(fileName);
	}
	
	private TableColumnOIDXMlHelper() {
		
	}
	
	/**
	 * 初始化oid池的方法
	 * 
	 * @return
	 */
	
	public static HashMap<Integer, HashMap<String, ArrayList<TableBean>>> initGloadbleOidPool() {
		HashMap<Integer, HashMap<String, ArrayList<TableBean>>> result = new HashMap<Integer, HashMap<String, ArrayList<TableBean>>>();
		
		Element e, mE, tE, cE = null;
		String factory = null;
		String model = null;
		HashMap aav = null;
		
		TableBean tb = null;
		ColumnBean cb = null;
		HashMap<String, ArrayList<TableBean>> modelMapping = null;
		ArrayList<TableBean> modelTable = null;
		
		List factoryList = xmlFileHandler.loadElementsByAttribute("factory", null, null);
		Iterator it = factoryList.iterator();
		Iterator modelIt = null;
		Iterator tableIt = null;
		Iterator columnIt = null;
		for (; it.hasNext();) {// 遍历factory
			e = (Element) it.next();
			factory = e.attributeValue("value");
			modelIt = e.elementIterator("model");
			modelMapping = new HashMap<String, ArrayList<TableBean>>();
			result.put(Integer.parseInt(factory), modelMapping);
			
			for (; modelIt.hasNext();) {// 遍历factory下的model
			
				mE = (Element) modelIt.next();
				model = mE.attributeValue("value").toLowerCase();
				tableIt = mE.elementIterator("table");
				modelTable = new ArrayList<TableBean>();
				modelMapping.put(model, modelTable);
				
				for (; tableIt.hasNext();) {// 遍历model下的table
				
					tE = (Element) tableIt.next();
					aav = xmlFileHandler.getElementAttributesAndValue(tE);
					tb = (TableBean) ReflectUtil.getBeans(TableBean.class.getName(), aav);
					columnIt = tE.elementIterator("column");
					
					while (columnIt.hasNext()) {// 遍历table下的column
					
						cE = (Element) columnIt.next();
						aav = xmlFileHandler.getElementAttributesAndValue(cE);
						cb = (ColumnBean) ReflectUtil.getBeans(ColumnBean.class.getName(), aav);
						tb.addColumn(cb);
						cb = null;
					}
					modelTable.add(tb);
					tb = null;
					
				}
			}
		}
		
		return result;
		
	}
	
	/**
	 * 将TableColumnOID.xml中的信息读入内存并以TableBean和ColumnBean进行存储
	 * 
	 * @return
	 */
	public static ArrayList<TableBean> getTableBeanFromXML() {
		
		Element e = null;
		HashMap aav = null;
		
		TableBean tb = null;
		ColumnBean cb = null;
		List columnList = null;
		Iterator colIt = null;
		ArrayList<TableBean> result = new ArrayList<TableBean>();
		/***
		 * table元素
		 */
		List tableList = xmlFileHandler.loadElementsByAttribute("table", null, null);
		
		Iterator it = tableList.iterator();
		
		while (it.hasNext()) {
			
			e = (Element) it.next();
			
			aav = xmlFileHandler.getElementAttributesAndValue(e);
			
			tb = (TableBean) ReflectUtil.getBeans(TableBean.class.getName(), aav);
			/**
			 * table下的子元素
			 */
			columnList = xmlFileHandler.getSubElements(e);
			
			colIt = columnList.iterator();
			
			while (colIt.hasNext()) {
				
				e = (Element) colIt.next();
				
				aav = xmlFileHandler.getElementAttributesAndValue(e);
				
				cb = (ColumnBean) ReflectUtil.getBeans(ColumnBean.class.getName(), aav);
				
				tb.addColumn(cb);
				
				cb = null;
			}
			
			result.add(tb);
			
			tb = null;
		}
		
		return result;
		
	}
	
	public static void main(String[] args) {
		initGloadbleOidPool();
	}
	
}