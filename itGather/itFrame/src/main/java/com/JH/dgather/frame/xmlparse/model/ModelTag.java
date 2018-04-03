package com.JH.dgather.frame.xmlparse.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;

import com.JH.dgather.frame.xmlparse.exception.TagException;

public class ModelTag extends BaseTag {
	private Logger logger = Logger.getLogger(ModelTag.class);
	
	private HashMap<String, VersionTag> versionHm = new HashMap<String, VersionTag>();
	
	private String value = "";
	
	public ModelTag(Element modelElm, BaseTag parent) throws TagException {
		super("model", modelElm, parent);
		this.parse();
	}
	
	protected void parse() throws TagException {
		String errMsg = "";
		errMsg = this.getTagName() + " 标签规则: \r\n";
		errMsg += "【必须包含属性】\r\n";
		errMsg += "value: 型号编号(*表示通过所有型号)\r\n";
		errMsg += "【可选属性】\r\n";
		errMsg += "无";
		errMsg += "【可包含的子标签】\r\n";
		errMsg += "<version>";
		
		/**
		 * 判断model标签的属性值
		 * 规则：
		 * 必须包含属性: value: 型号编号
		 */
		List propLs = this.getElement().attributes();
		if (propLs.size() > 0) {
			for (Iterator it = propLs.iterator(); it.hasNext();) {
				Attribute attr = (Attribute) it.next();
				if (attr.getName().equals("value")) {
					this.value = attr.getValue();
				}
				else {
					logger.error(this.getTagName() + " 标签不允许包含属性: " + attr.getName());
					logger.error(errMsg);
					throw new TagException(this.getTagName() + " 标签不允许包含属性: " + attr.getName());
				}
			}
			
			//value为必配属性
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
		 * 判断model标签下的子标签
		 */
		List tagls = this.getElement().elements();
		if (tagls.size() > 0) {
			for (Iterator it = tagls.iterator(); it.hasNext();) {
				Element em = (Element) it.next();
				if (em.getName().equals("version")) {
					VersionTag versionTag = new VersionTag(em, this);
					String key = versionTag.getValue();
					this.versionHm.put(key, versionTag);
				}
				else {
					logger.error(this.getTagName() + " 标签不允许包含子标签: " + em.getName());
					logger.error(errMsg);
					throw new TagException(this.getTagName() + " 标签不允许包含子标签: " + em.getName());
				}
			}
		}
	}
	
	public VersionTag getVersionTag(String version) throws TagException {
		VersionTag versionTag = this.versionHm.get(version);
		if (versionTag == null) {
			versionTag = this.versionHm.get("*.*");
			if (versionTag == null) {
				throw new TagException(this.getTagName() + "标签中未包含版本为：" + version + "的数据，也不包含*.*通用版本数据");
			}
		}
		return versionTag;
	}
	
	/**
	 * @return the versionHm
	 */
	public HashMap<String, VersionTag> getVersionHm() {
		return versionHm;
	}
	
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
}
