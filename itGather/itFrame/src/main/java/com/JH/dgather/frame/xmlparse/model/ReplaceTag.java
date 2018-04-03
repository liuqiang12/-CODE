package com.JH.dgather.frame.xmlparse.model;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;

import com.JH.dgather.frame.xmlparse.exception.TagException;

public class ReplaceTag extends BaseTag {
	private Logger logger = Logger.getLogger(ReplaceTag.class);
	
	//当type='regex'时，表示是正则匹配替换; 当type='string'时，表示是字符串匹配替换
	private String type = "";
	//替换后的内容
	private String replacement = "";
	//标签内容
	private String replaceValue = "";
	
	public ReplaceTag(Element element, BaseTag parent) throws TagException {
		super("replace", element, parent);
	}
	
	@Override
	protected void parse() throws TagException {
		String errMsg = "";
		errMsg = this.getTagName() + " 标签规则: \r\n";
		errMsg += "【必须包含属性】\r\n";
		errMsg += "type: \r\n";
		errMsg += "当type='regex'时，表示是正则匹配替换\r\n";
		errMsg += "当type='string'时，表示是字符串匹配替换\r\n";
		errMsg += "replacement: 替换后的内容";
		errMsg += "【不包含子标签】\r\n";
		errMsg += "标签必须包含内容，表示的是type属性所对应的数据。例如: <replace type='regex' replacement='abc'>\\s+</replace> //表示将空格替换为abc字符。";
		
		/**
		 * 判断replace标签属性
		 */
		List propLs = this.getElement().attributes();
		if (propLs.size() > 0) {
			for (Iterator it = propLs.iterator(); it.hasNext();) {
				Attribute attr = (Attribute) it.next();
				if (attr.getName().equals("type")) {
					this.type = attr.getValue();
					if (!this.type.equals("regex") && !this.type.equals("string")) {
						logger.error(this.getTagName() + "标签中type属性的值只能为'regex'或'string'，并区分大小写!");
						logger.error(errMsg);
						throw new TagException(this.getTagName() + "标签中type属性的值只能为'regex'或'string'，并区分大小写!");
					}
				}
				else if (attr.getName().equals("replacement")) {
					this.replacement = attr.getValue();
				}
				else {
					logger.error(this.getTagName() + " 标签不允许包含属性: " + attr.getName());
					logger.error(errMsg);
					throw new TagException(this.getTagName() + " 标签不允许包含属性: " + attr.getName());
				}
			}
			
			//type,replacement为必配属性
			if (this.type == null || this.replacement == "" || "".equals(this.type) || "".equals(this.replacement)) {
				logger.error(this.getTagName() + "中type或replacement属性值为空!");
				logger.error(errMsg);
				throw new TagException(this.getTagName() + "中type或replacement属性值为空!");
			}
		}
		
		/**
		 * 获取标签内容
		 */
		if (this.getElement().getText().equals("")) {
			logger.error(this.getTagName() + " 标签的内容不为空。");
			logger.error(errMsg);
			throw new TagException(this.getTagName() + " 标签的内容不为空。");
		}
		this.replaceValue = this.getElement().getText();
	}
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * @return the replacement
	 */
	public String getReplacement() {
		return replacement;
	}
	
	/**
	 * @return the replaceValue
	 */
	public String getReplaceValue() {
		return replaceValue;
	}
	
}
