package com.JH.dgather.frame.xmlparse.model;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.dom4j.Element;

import com.JH.dgather.frame.xmlparse.exception.ProcessException;
import com.JH.dgather.frame.xmlparse.exception.TagException;

public class ValidateTag extends BaseTag {
	private Logger logger = Logger.getLogger(ValidateTag.class);
	
	private String validateValue = "";
	
	public ValidateTag(Element element, BaseTag parent) throws TagException {
		super("validate", element, parent);
		this.parse();
	}
	
	@Override
	protected void parse() throws TagException {
		String errMsg = "";
		errMsg = this.getTagName() + " 标签规则: \r\n";
		errMsg += "标签必须包含内容。例如: <validate>display ip</validate>";
		
		/**
		 * 判断command标签的属性值
		 */
		List propLs = this.getElement().attributes();
		if (propLs.size() > 0) {
			logger.error(this.getTagName() + " 标签不允许包含任何属性.");
			logger.error(errMsg);
			throw new TagException(this.getTagName() + " 标签不允许包含任何属性.");
		}
		
		/**
		 * 获取command的内容
		 */
		if (this.getElement().getText().equals("")) {
			logger.error(this.getTagName() + " 标签的内容不为空。");
			logger.error(errMsg);
			throw new TagException(this.getTagName() + " 标签的内容不为空。");
		}
		this.validateValue = this.getElement().getText();
	}
	
	/**
	 * 
	 * @param result
	 * @return
	 * @throws ProcessException
	 */
	public boolean process(String result, List<String> logLs) throws ProcessException {
		if (result == null || "".equals(result)) {
			throw new ProcessException("需要验证的回显内容为null或空！");
		}
		
		logLs.add("开始校验，规则为：" + this.validateValue);
		Pattern pa = Pattern.compile(this.validateValue);
		Matcher ma = pa.matcher(result);
		if (ma.find()) {
			logLs.add("校验成功！");
			return true;
		}
		else {
			logLs.add("校验失败！");
			logger.error("校验失败！校验规则: " + this.validateValue + "，校验内容: " + result);
			return false;
			//throw new ProcessException("校验失败！校验规则: " + this.validateValue + "，校验内容: " + result);
		}
	}
	
	/**
	 * @return the validateValue
	 */
	public String getValidateValue() {
		return validateValue;
	}
}
