package com.JH.dgather.frame.xmlparse.model;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;

import com.JH.dgather.frame.xmlparse.exception.TagException;
/**
 * 定位或忽略标签
 * @author Administrator
 *
 */
public class FixTag extends BaseTag {
	private Logger logger = Logger.getLogger(FixTag.class);
	
	//true表示'忽略'; 否则表示'定位'
	private String ignore = "";
	//标签的内容
	private String fixValue = "";
	
	public FixTag(Element element, BaseTag parent) throws TagException {
		super("fix", element, parent);
		this.parse();
	}
	
	@Override
	protected void parse() throws TagException {
		String errMsg = "";
		errMsg = this.getTagName() + " 标签规则: \r\n";
		errMsg += "【可选属性】\r\n";
		errMsg += "ignore: 值为true表示'忽略'; 否则表示'定位'";
		errMsg += "【不包含子标签】\r\n";
		errMsg += "标签包含内容可为空，当为空，该标签不起任务作用。例如: <fix ignore='true'>1,3,4</fix> //表示忽略拆分出来的数组中1,3,4列";
		
		/**
		 * 判断fix标签属性值
		 */
		List propLs = this.getElement().attributes();
		if (propLs.size() > 0) {
			for (Iterator it = propLs.iterator(); it.hasNext();) {
				Attribute attr = (Attribute) it.next();
				if (attr.getName().equals("ignore")) {
					this.ignore = attr.getValue();
				}
				else {
					logger.error(this.getTagName() + " 标签不允许包含属性: " + attr.getName());
					logger.error(errMsg);
					throw new TagException(this.getTagName() + " 标签不允许包含属性: " + attr.getName());
				}
			}
		}
		
		/**
		 * 获取标签的内容
		 */
		this.fixValue = this.getElement().getText();
	}
	
	/**
	 * @return the ignore
	 */
	public String getIgnore() {
		return ignore;
	}
	
	/**
	 * @return the fixValue
	 */
	public String getFixValue() {
		return fixValue;
	}
	
}
