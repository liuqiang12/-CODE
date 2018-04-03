package com.JH.dgather.frame.xmlparse.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;

import com.JH.dgather.frame.xmlparse.exception.TagException;

public class FactoryTag extends BaseTag {
	private Logger logger = Logger.getLogger(FactoryTag.class);
	
	private HashMap<String, ModelTag> modelHm = new HashMap<String, ModelTag>();
	
	private String value = "";
	private String name = "";
	
	public FactoryTag(Element factoryElement, BaseTag parent) throws TagException {
		super("factory", factoryElement, parent);
		this.parse();
	}
	
	protected void parse() throws TagException {
		String errMsg = "";
		errMsg = this.getTagName() + " 标签规则: \r\n";
		errMsg += "【必须包含属性】\r\n";
		errMsg += "value: 厂家编号\r\n";
		errMsg += "【可选属性】\r\n";
		errMsg += "name: 厂家名称\r\n";
		errMsg += "【可包含的子标签】\r\n";
		errMsg += "<model>";
		
		/**
		 * 判断factory标签的属性值
		 * 规则：
		 * 必须包含属性: value: 厂家编号
		 * 可选属性：		name: 厂家名称
		 */
		List propLs = this.getElement().attributes();
		if (propLs.size() > 0) {
			for (Iterator it = propLs.iterator(); it.hasNext();) {
				Attribute attr = (Attribute) it.next();
				if (attr.getName().equals("value")) {
					this.value = attr.getValue();
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
			
			//value属性为必配属性
			if (this.value == null || this.value.equals("")) {
				logger.error(this.getTagName() + "中value属性值为空!");
				logger.error(errMsg);
				throw new TagException(this.getTagName() + "中value属性值为空!");
			}
		}
		else {
			logger.error(errMsg);
			throw new TagException(errMsg);
		}
		
		/**
		 * 判断factory子标签
		 * 
		 */
		List tagls = this.getElement().elements();
		if (tagls.size() > 0) {
			for (Iterator it = tagls.iterator(); it.hasNext();) {
				Element em = (Element) it.next();
				if (em.getName().equals("model")) {
					ModelTag modelTag = new ModelTag(em, this);
					String key = modelTag.getValue().toLowerCase();
					this.modelHm.put(key, modelTag);
				}
				else {
					logger.error(this.getTagName() + " 标签不允许包含子标签: " + em.getName());
					logger.error(errMsg);
					throw new TagException(this.getTagName() + " 标签不允许包含子标签: " + em.getName());
				}
			}
		}
	}
	
	public ModelTag getModelTag(String modelValue) throws TagException {
		ModelTag modelTag = this.modelHm.get(modelValue);
		if (modelTag == null) {
			modelTag = this.modelHm.get("*");
			if (modelTag == null) {
				throw new TagException(this.getTagName() + "中未找到型号为" + modelValue + "的数据! 通用型号*也未找到！");
			}
		}
		return modelTag;
	}
	
	/**
	 * @return the modelHm
	 */
	public HashMap<String, ModelTag> getModelHm() {
		return modelHm;
	}
	
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
}
