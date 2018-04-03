package com.JH.dgather.frame.xmlparse.model;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;

import com.JH.dgather.frame.xmlparse.OperaterHandle;
import com.JH.dgather.frame.xmlparse.exception.DataStorageException;
import com.JH.dgather.frame.xmlparse.exception.TagException;

/**
 * <source name="test">Total Nets: (\d++)</source> //将Total Nets:
 * (\d++)作为数据存放到test键值中
 * 
 * @author gamesdoa
 * @email gamesdoa@gmail.com
 * @date 2012-6-12
 */

public class SourceTag extends BaseTag {
	private Logger logger = Logger.getLogger(SourceTag.class);
	// 存在数据仓库中的键值，不得重复
	private String name = "";
	// 值
	private String sourceValue = "";

	public SourceTag(Element element, BaseTag parentTag) throws TagException {
		super("source", element, parentTag);
		this.parse();
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected void parse() throws TagException {
		String errMsg = "";
		errMsg = this.getTagName() + " 标签规则: \r\n";
		errMsg += "【必选属性】\r\n";
		errMsg += "name: 存储到全局数据仓库中的键值，必须唯一，不得重复\r\n";
		errMsg += "【不包含的子标签】\r\n";
		errMsg += "标签必须包含内容。例如: <source name=\"test\">Total Nets: (\\d++)</source>";

		/**
		 * 判断source标签的属性值
		 */
		List propLs = this.getElement().attributes();
		if (propLs.size() > 0) {
			for (Iterator it = propLs.iterator(); it.hasNext();) {
				Attribute attr = (Attribute) it.next();
				if (attr.getName().equals("name")) {
					this.name = attr.getValue();
				} else {
					logger.error(this.getTagName() + " 标签不允许包含属性: " + attr.getName());
					logger.error(errMsg);
					throw new TagException(this.getTagName() + " 标签不允许包含属性: " + attr.getName());
				}
			}
		}

		/**
		 * 获取source的内容
		 */
		if (this.getElement().getText().equals("")) {
			logger.error(this.getTagName() + " 标签的内容不能为空。");
			logger.error(errMsg);
			throw new TagException(this.getTagName() + " 标签的内容不能为空。");
		}
		this.sourceValue = this.getElement().getText();
	}

	/**
	 * <code>process</code> 执行source指令并提取回显
	 * 
	 * @param handle
	 * @throws DataStorageException 
	 */
	public void process(OperaterHandle handle) throws DataStorageException {
		logger.debug("执行SourceTag标签操作！");
		handle.addSource(name, this.sourceValue);
	}
}
