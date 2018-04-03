/*
 * @(#)XMLParseProcess.java 03/06/2012
 *
 * Copyright 2011 Zone, All rights reserved.
 */
package com.JH.dgather.frame.xmlparse;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.JH.dgather.PropertiesHandle;
import com.JH.dgather.frame.common.bean.DeviceInfoBean;
import com.JH.dgather.frame.common.cmd.CMDManager;
import com.JH.dgather.frame.common.cmd.CMDService;
import com.JH.dgather.frame.common.cmd.bean.FlowPointBean;
import com.JH.dgather.frame.common.cmd.bean.TelnetFlowBean;
import com.JH.dgather.frame.util.EncryptAnalyse;
import com.JH.dgather.frame.xmlparse.beans.VarBean;
import com.JH.dgather.frame.xmlparse.exception.ProcessException;
import com.JH.dgather.frame.xmlparse.exception.TagException;
import com.JH.dgather.frame.xmlparse.model.FlowTag;
import com.JH.dgather.frame.xmlparse.model.GatherclassTag;
import com.JH.dgather.frame.xmlparse.model.IcsmodelTag;
import com.JH.dgather.frame.xmlparse.model.RuleIdTag;
import com.JH.dgather.frame.xmlparse.model.RuleTag;

/**
 * <code>XMLParseProcess</code> 配置文件解析处理类
 * @author Wang Xuedan
 * @version 1.0 2012/3/6
 */
public class XMLParseProcess {
	private Logger logger = Logger.getLogger(XMLParseProcess.class);
	//private static XMLParseProcess xmlParseProcess = new XMLParseProcess();
	
	private SAXReader reader;
	//用于存储icsmodel标签数据的HashMap
	private HashMap<String, IcsmodelTag> icsmodelHm = new HashMap<String, IcsmodelTag>();
	//用于存储gatherclass标签数据的HashMap
	private HashMap<String, GatherclassTag> gatherclassHm = new HashMap<String, GatherclassTag>();
	
	public XMLParseProcess() {
		reader = new SAXReader();
	}
	
//	/**
//	 * <code>getInstance</code> 获取XMLParseProcess对象实例
//	 * @return
//	 */
//	public static XMLParseProcess getInstance() {
//		if (xmlParseProcess == null) {
//			xmlParseProcess = new XMLParseProcess();
//		}
//		return xmlParseProcess;
//	}
	
//	/**
//	 * <code>iterator</code> XML标签迭代器，可用来遍历所有XML下的标签值
//	 * @param element
//	 * @param tab : 用于日志打印
//	 */
//	public static void iterator(Element element, String tab) {
//		List tagls = element.elements();
//		String tabS = tab + "    ";
//		//判断标签属性
//		List propLs = element.attributes();
//		if (propLs.size() > 0) {
//			for (Iterator it = propLs.iterator(); it.hasNext();) {
//				Attribute name = (Attribute) it.next();
//				System.out.print("    [" + name.getName() + "]: " + name.getValue());
//			}
//		}
//		
//		//判断当前标签是否还存在其它标签，如果存在，则直接遍历下来
//		if (tagls.size() == 0) {
//			//当前标签内无其它标签，判断标签是否有值
//			System.out.print("    : " + element.getText());
//		}
//		else {
//			for (Iterator it = tagls.iterator(); it.hasNext();) {
//				Element em = (Element) it.next();
//				System.out.print("\r\n" + tabS + em.getName());
//				
//				//继续遍历
//				XMLParseProcess.iterator(em, tabS);
//			}
//		}
//	}
	
	/**
	 * <code>parseFactoryAndRuleId</code> 解析factoryAndRules.xml文件
	 */
	public void parseFactoryAndRuleId() throws TagException {
		String fileDir = PropertiesHandle.getResuouceInfo("factoryAndRules");
		try {
			File xmlFile = new File(fileDir);
			if (xmlFile.exists()) {
				Document document = reader.read(xmlFile);
				Element rootElm = document.getRootElement();
				
				List tagls = rootElm.elements();
				if (tagls.size() > 0) {
					for (Iterator it = tagls.iterator(); it.hasNext();) {
						Element em = (Element) it.next();
						if (em.getName().equals("icsmodel")) {
							IcsmodelTag icsmodeTag = new IcsmodelTag(em, null);
							String key = icsmodeTag.getGatherclass();
							this.icsmodelHm.put(key, icsmodeTag);
						}else {
							logger.error("root 不允许包含标签：" + em.getName());
							throw new TagException("root 不允许包含标签：" + em.getName());
						}
					}
				}
			}else {
				logger.error("不存在此文件！文件路径: " + fileDir);
			}
		} catch (Exception e) {
			logger.error("解析发生异常！", e);
		}
	}
	
	/**
	 * <code>getRuleIdHm</code> 获取指定设备，指定采集类型的规则列表
	 * @return HashMap<String, RuleIdTag> 规则列表
	 */
	public HashMap<String, RuleIdTag> getRuleIdHm(DeviceInfoBean deviceInfoBean, int gatherClass) throws TagException {
		HashMap<String, RuleIdTag> ruleIdHm = new HashMap<String, RuleIdTag>();
		
		IcsmodelTag icsmodel = this.icsmodelHm.get(Integer.toString(gatherClass));
		if (icsmodel == null) {
			throw new TagException("未找到gatherclass属性为：" + gatherClass + " 的icsmode标签!");
		}
		else {
			logger.debug(deviceInfoBean.getModelName());
			ruleIdHm = icsmodel.getFactoryTag(Integer.toString(deviceInfoBean.getFactory())).getModelTag(
					deviceInfoBean.getModelName()).getVersionTag(deviceInfoBean.getDeviceVersion()).getRuleIdHm();
		}
		
		return ruleIdHm;
	}
	
	/**
	 * <code>parseSystemRules</code> 解析systemRules.xml文件
	 * @throws TagException
	 */
	public void parseSystemRules() throws TagException {
		String fileDir = PropertiesHandle.getResuouceInfo("systemRules");
		try {
			File xmlFile = new File(fileDir);
			if (xmlFile.exists()) {
				Document document = reader.read(xmlFile);
				Element rootElm = document.getRootElement();
				
				List tagls = rootElm.elements();
				if (tagls.size() > 0) {
					for (Iterator it = tagls.iterator(); it.hasNext();) {
						Element em = (Element) it.next();
						if (em.getName().equals("gatherclass")) {
							GatherclassTag gatherclassTag = new GatherclassTag(em, null);
							String key = gatherclassTag.getValue();
							this.gatherclassHm.put(key, gatherclassTag);
						}
						else {
							logger.error("root 不允许包含标签：" + em.getName());
							throw new TagException("root 不允许包含标签：" + em.getName());
						}
					}
				}
			}
			else {
				logger.error("不存在此文件！文件路径: " + fileDir);
			}
		} catch (Exception e) {
			logger.error("解析发生异常.", e);
		}
	}
	
	/**
	 * <code>getFlowById</code> 根据流程的ID编号找到流程内容
	 * @param id: 流程编号
	 * @param gatherclass: 采集类型
	 * @throws TagException
	 */
	public FlowTag getFlowById(String id, String gatherclass) throws TagException {
		if (id == null || gatherclass == null) {
			throw new TagException("id == null || gatherclass == null");
		}
		if ("".equals(id) || "".equals(gatherclass)) {
			throw new TagException("id或gatherclass为空");
		}
		
		GatherclassTag gatherclassTag = this.gatherclassHm.get(gatherclass);
		if (gatherclassTag == null) {
			throw new TagException("未找到value为" + gatherclass + "的gatherclass标签!");
		}
		FlowTag flowTag = gatherclassTag.getFlowById(id);
		
		return flowTag;
	}
	
	/**
	 * <code>getRuleById</code> 根据规则的ID编号找到规则内容
	 * @param ruleIdTag
	 * @param gatherclass
	 * @return
	 * @throws TagException
	 */
	public RuleTag getRuleById(RuleIdTag ruleIdTag, String gatherclass) throws TagException {
		if (ruleIdTag == null || gatherclass == null) {
			throw new TagException("ruleIdTag == null || gatherclass == null");
		}
		if ("".equals(gatherclass)) {
			throw new TagException("gatherclass为空");
		}
		String id = "";
		if (ruleIdTag.getValue().matches("\\$\\{rules\\.(.+?)}$")) {
			Pattern pa = Pattern.compile("\\$\\{rules\\.(.+?)}$");
			Matcher ma = pa.matcher(ruleIdTag.getValue());
			if(ma.find()) {
				id = ma.group(1);
				logger.debug("Rule ID为 " + id);
			}
		}
		else {
			throw new TagException("ruleIdTag的值不符合${rules.xxxxx}, 例如: ${rules.11-001}");
		}
		
		GatherclassTag gatherclassTag = this.gatherclassHm.get(gatherclass);
		if (gatherclassTag == null) {
			throw new TagException("未找到value为" + gatherclass + "的gatherclass标签!");
		}
		
		RuleTag ruleTag = gatherclassTag.getRuleById(id);
		return ruleTag;
	}
	
	public static void main(String[] args) {
		XMLParseProcess xml = new XMLParseProcess();
		try {
			xml.parseFactoryAndRuleId();
			//----------------------------------------//
			DeviceInfoBean device = new DeviceInfoBean();
			EncryptAnalyse ea = new EncryptAnalyse();
			device.setFactory(0);
			device.setModelName("NE40E");
			device.setIPAddress("192.168.70.112");
			device.setRoutName("switch one");
			device.setLoginModel(0);
			device.setLoginPsw(ea.MNSA_Encrypt("xiaotong"));
			device.setLoginUser("xiaotong");
			device.setPriviledge(false);
			device.setTelnetPort(23);
			
			HashMap<String, RuleIdTag> routHm = xml.getRuleIdHm(device, 11);
			for (Entry<String, RuleIdTag> entry : routHm.entrySet()) {
				System.out.println(">>>> " + entry.getKey() + " = " + entry.getValue().getValue());
			}
			//----------------------------------------//
			System.out.println("-------------------------");
			OperaterHandle handle = new OperaterHandle();
			/*
			 * 组装一组登录流程
			 */
			TelnetFlowBean telnetFlowBean = new TelnetFlowBean();
			telnetFlowBean.setDevice(device);
			ArrayList<FlowPointBean> flowList = new ArrayList<FlowPointBean>();
			
			FlowPointBean step2 = new FlowPointBean();
			step2.setSequenceNo(2);
			step2.setPrompt("*Username:");
			step2.setFillParam("%usr%");
			flowList.add(step2);
			
			FlowPointBean step3 = new FlowPointBean();
			step3.setSequenceNo(3);
			step3.setPrompt("*Password:");
			step3.setFillParam("%psw%");
			flowList.add(step3);
			
			FlowPointBean step4 = new FlowPointBean();
			step4.setSequenceNo(4);
			step4.setPrompt("*>");
			step4.setFillParam("%order%");
			flowList.add(step4);
			
			
			telnetFlowBean.setFlowList(flowList);
			CMDService tl = null;
			try {
				//			TelnetFlow tf = new TelnetFlow();
				//			TelnetFlowBean flow = tf.initTelnetFlow(device);
				CMDManager cmdManager = new CMDManager(telnetFlowBean);
				
				tl = cmdManager.getCMDService();
				tl.land(telnetFlowBean);
			} catch (Exception e) {
				e.printStackTrace();
			}
			xml.parseSystemRules();
			
			List<VarBean> foreachLs = handle.getDataStorage().createList();
			
			List<VarBean> l1 = handle.getDataStorage().createList();
			List<VarBean> l2 = handle.getDataStorage().createList();			
			HashMap<String, VarBean> h1 = handle.getDataStorage().createHashMap();
			HashMap<String, VarBean> h2 = handle.getDataStorage().createHashMap();
			
 			handle.getDataStorage().addDataToHashMap(h1, "portname", "Vlan-interface1"); 			
 			handle.getDataStorage().addDataToHashMap(h2, "portname", "LoopBack1");
 			
 			handle.getDataStorage().addDataToList(l1, h1);
 			handle.getDataStorage().addDataToList(l2, h2);

 			handle.getDataStorage().addDataToList(foreachLs, l1);
 			handle.getDataStorage().addDataToList(foreachLs, l2); 			
			handle.addArgs("foreachLs", foreachLs);
			handle.addData("pname", "portname", "NULL0");
			
			FlowTag flowTag = xml.getFlowById("11-888", "11");
			//logger.info(flowTag.getId());
			handle.flowProcess(tl, flowTag);
			
			
		} catch (TagException te) {
			//logger.error("异常！", te);
		} catch (ProcessException fpe) {
			fpe.printStackTrace();
		} catch (Exception te) {
			te.printStackTrace();
		}
	}
}