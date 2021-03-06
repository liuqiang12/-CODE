package com.JH.dgather.frame.xmlparse.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;

import com.JH.dgather.frame.xmlparse.OperaterHandle;
import com.JH.dgather.frame.xmlparse.exception.ProcessException;
import com.JH.dgather.frame.xmlparse.exception.TagException;

public class SnmpRuleTag extends BaseTag {
	private Logger logger = Logger.getLogger(SnmpRuleTag.class);
	
	private List<StepTag> stepLs = new ArrayList<StepTag>();
	private String id = "";
	private String name = "";
	
	//当执行流程后，记录每一步的操作
	private List<String> recordLs = new ArrayList<String>();
	
	public SnmpRuleTag(Element element, BaseTag parent) throws TagException {
		super("rule", element, parent);
		this.parse();
	}
	
	protected void parse() throws TagException {
		String errMsg = "";
		errMsg = this.getTagName() + " 标签规则: \r\n";
		errMsg += "【必须包含属性】\r\n";
		errMsg += "id: 规则编号\r\n";
		errMsg += "【可选属性】\r\n";
		errMsg += "name: 规则名称";
		errMsg += "【可包含的子标签】\r\n";
		errMsg += "<step>";
		
		/**
		 * 判断rule标签的属性值
		 */
		List propLs = this.getElement().attributes();
		if (propLs.size() > 0) {
			for (Iterator it = propLs.iterator(); it.hasNext();) {
				Attribute attr = (Attribute) it.next();
				if (attr.getName().toLowerCase().equals("id")) {
					this.id = attr.getValue();
				}
				else if (attr.getName().equals("name")) {
					this.name = attr.getValue();
				}
				else {
					logger.error(this.getTagName() + " 标签不允许包含属性: " + attr.getName());
					logger.error(errMsg);
					throw new TagException(this.getTagName() + " 标签不允许包含属性: " + attr.getName());
				}
			}
			
			//id为必配属性
			if (this.id == null || "".equals(this.id)) {
				logger.error(this.getTagName() + "中id属性值为空!");
				logger.error(errMsg);
				throw new TagException(this.getTagName() + "中id属性值为空!");
			}
		}
		else {
			logger.error(errMsg);
			throw new TagException(errMsg);
		}
		
		/**
		 * 获取step步骤
		 */
		List tagls = this.getElement().elements();
		if (tagls.size() > 0) {
			for (Iterator it = tagls.iterator(); it.hasNext();) {
				Element em = (Element) it.next();
				if (em.getName().equals("step")) {
					StepTag stepTag = new StepTag(em, this);
					this.stepLs.add(stepTag);
				}
				else {
					logger.warn(this.getTagName() + " 标签不允许包含子标签: " + em.getName());
					logger.warn(errMsg);
//					throw new TagException(this.getTagName() + " 标签不允许包含子标签: " + em.getName());
				}
			}
		}
		else {
			logger.error(this.getTagName() + " 标签未找到step标签!");
		}
		
		logger.debug("Step size: " + this.stepLs.size());
	}
	
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the stepLs
	 */
	public List<StepTag> getStepLs() {
		return stepLs;
	}
	
}
