package com.JH.dgather.frame.xmlparse.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;

import com.JH.dgather.frame.common.exception.TelnetException;
import com.JH.dgather.frame.xmlparse.OperaterHandle;
import com.JH.dgather.frame.xmlparse.exception.ProcessException;
import com.JH.dgather.frame.xmlparse.exception.TagException;

public class MatcherTag extends BaseTag {
	private Logger logger = Logger.getLogger(MatcherTag.class);
	
	private List<BaseTag> tagLs = new ArrayList<BaseTag>();
	//标签的内容
	private String regexValue = "";

	// 标签的buffer属性
	private String buffer = "";
	public MatcherTag(Element element, BaseTag parent) throws TagException {
		super("matcher", element, parent);
		this.parse();
	}
	
	@Override
	protected void parse() throws TagException {
		String errMsg = "";
		errMsg = this.getTagName() + " 标签规则: \r\n";
		errMsg += "【必选属性】\r\n";
		errMsg += "regex: 提取内容的正则";
		errMsg += "buffer: 表示需要保存当前指令执行的回显内容，存储的键值为buffer定义的字符串\r\n";
		errMsg += "【可包含子标签】\r\n";
		errMsg += "";
		
		/**
		 * 判断matcher标签属性值
		 */
		List propLs = this.getElement().attributes();
		if (propLs.size() > 0) {
			for (Iterator it = propLs.iterator(); it.hasNext();) {
				Attribute attr = (Attribute) it.next();
				if (attr.getName().equals("buffer")) {
					this.buffer = attr.getValue();
				} else {
					logger.error(this.getTagName() + " 标签不允许包含属性: " + attr.getName());
					logger.error(errMsg);
					throw new TagException(this.getTagName() + " 标签不允许包含属性: " + attr.getName());
				}
			}
			
			
			// buffer属性为必配属性，不得为空
			if (buffer == null || "".equals(buffer)) {
				logger.error(this.getTagName() + " 标签中buffer属性不得为空!");
				logger.error(errMsg);
				throw new TagException(this.getTagName() + " 标签中buffer属性不得为空!");
			}
		}else {
			logger.error(this.getTagName() + " 标签必须包含属性: buffer");
			logger.error(errMsg);
			throw new TagException(this.getTagName() + " 标签必须包含属性: buffer");
		}
		
		/**
		 * 获取matcher标签下的内容
		 */
		if (this.getElement().getText().equals("")) {
			logger.error(this.getTagName() + " 标签的内容不能为空。");
			logger.error(errMsg);
			throw new TagException(this.getTagName() + " 标签的内容不能为空。");
		}
		this.regexValue = this.getElement().getText();
		//		List tagls = this.getElement().elements();
		//		boolean isMatcher = false;
		//		if (tagls.size() > 0) {
		//			for (Iterator it = tagls.iterator(); it.hasNext();) {
		//				Element em = (Element) it.next();
		//				if (!isMatcher) {
		//					if (em.getName().equals("split")) {
		//						checkSameTag("split");
		//						SplitTag splitT = new SplitTag(em, this);
		//						tagLs.add(splitT);
		//					}
		//					else if (em.getName().equals("validate")) {
		//						checkSameTag("split");
		//						ValidateTag validateTag = new ValidateTag(em, this);
		//						tagLs.add(validateTag);
		//					}
		//					else if (em.getName().equals("matcher")) {
		//						isMatcher = true;
		//						//					if (this.stat != null && !this.stat.equals("")) {
		//						//						throw new TagException("matcher标签与stat属性不能同时出现！");
		//						//					}
		//						checkSameTag("matcher");
		//						MatcherTag matcherTag = new MatcherTag(em, this);
		//						tagLs.add(matcherTag);
		//					}
		//					else if (em.getName().equals("fix")) {
		//						checkSameTag("fix");
		//						FixTag fixTag = new FixTag(em, this);
		//						tagLs.add(fixTag);
		//					}
		//					else if (em.getName().equals("replace")) {
		//						checkSameTag("replace");
		//						ReplaceTag replaceTag = new ReplaceTag(em, this);
		//						tagLs.add(replaceTag);
		//					}
		//					else if (em.getName().equals("result")) {
		//						ResultTag resultTag = new ResultTag(em, this);
		//						tagLs.add(resultTag);
		//					}
		//					else {
		//						logger.error(this.getTagName() + " 标签不允许包含子标签: " + em.getName());
		//						logger.error(errMsg);
		//						throw new TagException(this.getTagName() + " 标签不允许包含子标签: " + em.getName());
		//					}
		//				}
		//				else {
		//					logger.error(this.getTagName() + " 标签子标签中matcher标签后不得包含其它子标签，当前子标签为： " + em.getName());
		//					logger.error(errMsg);
		//					throw new TagException(this.getTagName() + " 标签子标签中matcher标签后不得包含其它子标签，当前子标签为： " + em.getName());
		//				}
		//			}
		//		}
	}
	
	/**
	 * <code>checkSameTag</code> 判断当前的标签表列中是否已包含相同名称的标签。因为有些标签只能出现一次
	 * @param tagName: 需要判断的标签名称
	 * @return
	 */
	private void checkSameTag(String tagName) throws TagException {
		for (BaseTag tag : this.tagLs) {
			if (tag.getTagName().equals(tagName)) {
				throw new TagException(this.getTagName() + "标签中不得重复出现 " + tag.getTagName());
			}
		}
	}
	
	/**
	 * @return the regex
	 */
	public String getRegex() {
		return regexValue;
	}
	
	/**
	 * <code>process</code> 执行split标签
	 * @param handle
	 * @param result 上一级回显信息
	 * @param logLs
	 * @return matcher的回显
	 * @throws TelnetException
	 * @throws IOException
	 * @throws Exception
	 */
	public String process(OperaterHandle handle, String result, List<String> logLs) throws ProcessException {
		logLs.add("开始执行matcher操作");
		
		String resultStr = "";
		try {
			Pattern pa = Pattern.compile(this.regexValue);
			Matcher ma = pa.matcher(result);
			if (ma.find()) {
				resultStr = ma.group(1);
			}
			else {
				resultStr = result;
			}
			handle.addBuffer(this.buffer, resultStr);
			return resultStr;
		} catch (Exception e) {
			logger.error(this.getTagName() + " 标签在执行时发生异常！", e);
			throw new ProcessException(this.getTagName() + " 标签在执行时发生异常！");
		}
	}
}
