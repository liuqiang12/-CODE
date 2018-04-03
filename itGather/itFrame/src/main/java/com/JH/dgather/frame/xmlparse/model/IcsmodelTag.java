package com.JH.dgather.frame.xmlparse.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;

import com.JH.dgather.frame.xmlparse.exception.TagException;

public class IcsmodelTag extends BaseTag {
	private Logger logger = Logger.getLogger(IcsmodelTag.class);
	
	private HashMap<String, FactoryTag> factoryHm = new HashMap<String, FactoryTag>();
	
	private String id = "";
	private String gatherclass = "";
	private String name = "";
	
	public IcsmodelTag(Element icsmodelElement, BaseTag parent) throws TagException {
		super("icsmodel", icsmodelElement, parent);
		this.parse();
	}
	
	protected void parse() throws TagException {
		String errMsg = "";
		errMsg = this.getTagName() + " 标签规则: \r\n";
		errMsg += "【必须包含属性】\r\n";
		errMsg += "id: 唯一编号\r\n";
		errMsg += "gatherclass: 对应的采集类型\r\n";
		errMsg += "【可选属性】\r\n";
		errMsg += "name: 名称\r\n";
		errMsg += "【可包含的子标签】\r\n";
		errMsg += "<factory>";
		
		/**
		 * 判断icsmode标签的属性值
		 * 规则：
		 * 必须包含: id: 唯一编号
		 * 			gatherclass: 对应的采集类型
		 * 可选包含: name: 名称
		 */
		List propLs = this.getElement().attributes();
		if (propLs.size() > 0) {
			for (Iterator it = propLs.iterator(); it.hasNext();) {
				Attribute attr = (Attribute) it.next();
				if (attr.getName().equals("id")) {
					this.id = attr.getValue();
				}
				else if (attr.getName().equals("gatherclass")) {
					this.gatherclass = attr.getValue();
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
			
			//id,gatherclass为必配属性
			if (this.id == null || this.gatherclass == null || this.id.equals("") || this.gatherclass.equals("")) {
				logger.error(this.getTagName() + "中id或gatherclass属性值为空!");
				logger.error(errMsg);
				throw new TagException(this.getTagName() + "中id或gatherclass属性值为空!");
			}
		}
		else {
			logger.error(errMsg);
			throw new TagException(errMsg);
		}
		/**
		 * 判断icsmodel标签的子标签
		 * 规则：
		 * 包含: factory标签
		 */
		List tagls = this.getElement().elements();
		if (tagls.size() > 0) {
			for (Iterator it = tagls.iterator(); it.hasNext();) {
				Element em = (Element) it.next();
				if (em.getName().equals("factory")) {
					FactoryTag factoryT = new FactoryTag(em, this);
					String key = factoryT.getValue();
					this.factoryHm.put(key, factoryT);
				}
				else {
					logger.error(this.getTagName() + " 标签不允许包含子标签: " + em.getName());
					logger.error(errMsg);
					throw new TagException(this.getTagName() + " 标签不允许包含子标签: " + em.getName());
				}
			}
		}
	}
	
	public FactoryTag getFactoryTag(String factoryId) throws TagException {
		FactoryTag factoryTag = this.factoryHm.get(factoryId);
		if (factoryTag == null) {
			throw new TagException(this.getTagName() + "中未找到factory标签中value属性为:" + factoryId + "的标签数据");
		}
		else {
			return factoryTag;
		}
	}
	
	/**
	 * @return the factoryHm
	 */
	public HashMap<String, FactoryTag> getFactoryHm() {
		return factoryHm;
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @return the gatherclass
	 */
	public String getGatherclass() {
		return gatherclass;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
}
