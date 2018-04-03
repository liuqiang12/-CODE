package com.JH.dgather.frame.common.xml.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;

import com.JH.dgather.PropertiesHandle;
import com.JH.dgather.frame.common.reflect.ReflectUtil;
import com.JH.dgather.frame.common.reflect.bean.DeviceTypeBean;
import com.JH.dgather.frame.common.reflect.bean.PortTypeMappingBean;
import com.JH.dgather.frame.common.xml.XMLFileHandler;

public class DeviceTypeXMLHelper {
//	private static URL file = DeviceTypeXMLHelper.class.getResource("/oidMappingPortType.xml");
	private static String filepath = PropertiesHandle.getResuouceInfo("sysobjectId");
	
	private static XMLFileHandler xmlFileHandler = new XMLFileHandler(filepath);
	
	public static HashMap<String, DeviceTypeBean> deviceTypeMapping = getDeviceTypeBean();
	
	private static ArrayList<PortTypeMappingBean> portTypeMapping = getPortTypeMapping();
	/**
	 * 
	 * 得到mainClass的name属性
	 * 
	 * @param mainClass
	 * @return
	 */
	public static String getNameByValueOfMainClass(int mainClass) {
		
		String name = "";
		
		Element e = xmlFileHandler.getElementByXpath("//devices/mainclass[@value='" + mainClass + "']");
		
		if (e != null) {
			
			name = e.attributeValue("name");
			
		}
		
		return name;
	}
	
	/**
	 * 得到type的name属性
	 * 
	 * @param mainClass
	 * @param type
	 * @return
	 */
	public static String getNameByValueOfType(int mainClass, int type) {
		
		String name = "";
		
		Element e = xmlFileHandler.getElementByXpath("//devices/mainclass[@value='" + mainClass + "']/type[@value='" + type
				+ "']");
		
		if (e != null)
			name = e.attributeValue("name");
		
		return name;
	}
	
	/**
	 * 得到厂家的名称
	 * 
	 * @param factory
	 * @return
	 */
	public static String getNameByFactory(int factory) {
		
		String name = "";
		
		Element e = xmlFileHandler.getElementByXpath("//factory[@value='" + factory + "']");
		
		if (e != null)
			name = e.attributeValue("name");
		
		return name;
	}
	
	/**
	 * 
	 * 得到xml中deviceType的配置信息
	 * 
	 */
	
	private static HashMap<String, DeviceTypeBean> getDeviceTypeBean() {
		HashMap<String, DeviceTypeBean> result = new HashMap<String, DeviceTypeBean>();
		
		List deviceTypeList = xmlFileHandler.loadElementsByAttribute("mainclass", null, null);
		
		Element e = null;
		HashMap avv = null;
		
		DeviceTypeBean dtbTemp = new DeviceTypeBean(), dtb = null;
		List typeList = null, factoryList = null, oidList = null;
		
		for (Iterator it = deviceTypeList.iterator(); it.hasNext();) {
			e = (Element) it.next();
			avv = xmlFileHandler.getElementAttributesAndValue(e);
			
			ReflectUtil.setBeanFromInnerClass(dtbTemp, "MainClass", avv);// 生成一个mainClass实例
			
			typeList = xmlFileHandler.getSubElements(e);
			
			for (Iterator typeIt = typeList.iterator(); typeIt.hasNext();) {
				e = (Element) typeIt.next();
				avv = xmlFileHandler.getElementAttributesAndValue(e);
				
				ReflectUtil.setBeanFromInnerClass(dtbTemp, "Type", avv);// 生成一个Type实例
				
				factoryList = xmlFileHandler.getSubElements(e);
				
				for (Iterator facIt = factoryList.iterator(); facIt.hasNext();) {
					e = (Element) facIt.next();
					avv = xmlFileHandler.getElementAttributesAndValue(e);
					ReflectUtil.setBeanFromInnerClass(dtbTemp, "Factory", avv);// 生成一个factory实例
					
					oidList = xmlFileHandler.getSubElements(e);
					for (Iterator oidIt = oidList.iterator(); oidIt.hasNext();) {
						e = (Element) oidIt.next();
						avv = xmlFileHandler.getElementAttributesAndValue(e);
						dtb = (DeviceTypeBean) ReflectUtil.getBeans("com.zone.ics.frame.common.reflect.bean.DeviceTypeBean", avv);
						dtb.setSysObjectId(e.getTextTrim());
						dtb.setMainClass(dtbTemp.getMainClass());
						dtb.setFactory(dtbTemp.getFactory());
						dtb.setType(dtbTemp.getType());
						dtb.setModel(e.attributeValue("model") == null ? null : e.attributeValue("model").trim());
						result.put(dtb.getSysObjectId(), dtb);
						
					}
				}
				
			}
			
		}
		
		return result;
		
	}
	
	/**
	 * 得到portType的映射
	 * 
	 * @return
	 */
	private static ArrayList<PortTypeMappingBean> getPortTypeMapping() {
		ArrayList<PortTypeMappingBean> mapping = new ArrayList<PortTypeMappingBean>();
		
		List<Element> elements = xmlFileHandler.loadElementsByAttribute("port", null, null);
		
		HashMap<String, String> temp = null;
		String type = null, name = null, shortName = null;
		PortTypeMappingBean p = null;
		for (Element e : elements) {
			temp = xmlFileHandler.getElementAttributesAndValue(e);
			type = temp.get("type");
			name = temp.get("name");
			shortName = temp.get("shortName");
			p = new PortTypeMappingBean();
			p.setType(type);
			p.setName(name);
			p.setShortName(shortName);
			mapping.add(p);
			
		}
		
		return mapping;
	}
	
	/**
	 * 返回端口名称的简写形式
	 * 
	 * @param portName
	 * @return
	 */
	public static String getPortMapping(String portName) {
		for (PortTypeMappingBean map : portTypeMapping) {
			if (portName.toLowerCase().indexOf(map.getName()) != -1) {
				return map.getShortName();
			}
		}
		return portName;
	}
	
	public static void main(String[] args) {
		getNameByValueOfMainClass(1);
		getNameByValueOfType(1, 2);
		getNameByFactory(47);
	}
	
}
