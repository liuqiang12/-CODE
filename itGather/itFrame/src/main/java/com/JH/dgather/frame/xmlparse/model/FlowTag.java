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

import com.JH.dgather.frame.common.cmd.CMDService;
import com.JH.dgather.frame.common.exception.TelnetException;
import com.JH.dgather.frame.xmlparse.OperaterHandle;
import com.JH.dgather.frame.xmlparse.exception.ProcessException;
import com.JH.dgather.frame.xmlparse.exception.TagException;

public class FlowTag extends BaseTag {
	private Logger logger = Logger.getLogger(FlowTag.class);
	
	private List<StepTag> tagLs = new ArrayList<StepTag>();
	private String id = "";
	private String name = "";
	
	//当执行流程后，记录每一步的操作
	private List<String> recordLs = new ArrayList<String>();
	//是否执行流程;
	private boolean isExecFlow = false;
	
	public FlowTag(Element element, BaseTag parent) throws TagException {
		super("flow", element, parent);
		this.parse();
	}
	
	protected void parse() throws TagException {
		String errMsg = "";
		errMsg = this.getTagName() + " 标签规则: \r\n";
		errMsg += "【必须包含属性】\r\n";
		errMsg += "id: 流程编号\r\n";
		errMsg += "【可选属性】\r\n";
		errMsg += "name: 流程名称\r\n";
		errMsg += "【可包含的子标签】\r\n";
		errMsg += "<interaction> <foreach>";
		
		/**
		 * 判断flow标签的属性值
		 */
		List propLs = this.getElement().attributes();
		if (propLs.size() > 0) {
			for (Iterator it = propLs.iterator(); it.hasNext();) {
				Attribute attr = (Attribute) it.next();
				if (attr.getName().equals("id")) {
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
		 * 获取flow流程
		 */
		List tagls = this.getElement().elements();
		if (tagls.size() > 0) {
			for (Iterator it = tagls.iterator(); it.hasNext();) {
				Element em = (Element) it.next();
				if (em.getName().equals("step")) {
					StepTag stepTag = new StepTag(em, this);
					this.tagLs.add(stepTag);
				}
				else {
					logger.error(this.getTagName() + " 标签不允许包含子标签: " + em.getName());
					logger.error(errMsg);
					throw new TagException(this.getTagName() + " 标签不允许包含子标签: " + em.getName());
				}
			}
		}
		else {
			logger.error(this.getTagName() + " 标签未找到iteraction标签!");
		}
		
		logger.debug("tag size: " + this.tagLs.size());
	}
	
	/**
	 * <code>process</code> 执行流程
	 */
	public void process(CMDService tl, OperaterHandle handle) throws ProcessException, TelnetException, IOException, Exception {
		int step = 1;
		this.isExecFlow = true;
		try {
			for (StepTag stepTag : this.tagLs) {
				recordLs.add("<<<第 " + step + " 步操作>>>");
				stepTag.process(tl, handle, recordLs, null);
				step++;
			}
		} catch (ProcessException pe) {
			logger.error("执行流程失败!", pe);
		}
		
		for (String log : this.recordLs) {
			logger.debug(log);
		}
	}
	
	public static void main(String[] args) {
		Pattern p = Pattern.compile("\\$\\{args\\.(.+)\\}");
		Matcher m = p.matcher("${args.1}");
		if (m.find()) {
			System.out.println(m.group(1));
		}
		else {
			System.out.println("err");
		}
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
	 * @return the tagLs
	 */
	public List<StepTag> getTagLs() {
		return tagLs;
	}
	
	/**
	 * @return the recordLs
	 */
	public List<String> getRecordLs() {
		return recordLs;
	}
	
}
