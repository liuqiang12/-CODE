package com.JH.dgather.frame.xmlparse.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;

import com.JH.dgather.frame.common.cmd.CMDService;
import com.JH.dgather.frame.xmlparse.OperaterHandle;
import com.JH.dgather.frame.xmlparse.beans.VarBean;
import com.JH.dgather.frame.xmlparse.beans.VarBeanStatic;
import com.JH.dgather.frame.xmlparse.exception.DataStorageException;
import com.JH.dgather.frame.xmlparse.exception.ProcessException;
import com.JH.dgather.frame.xmlparse.exception.TagException;

/**
 * @author gamesdoa
 * @email gamesdoa@gmail.com
 * @date 2013-3-11
 */

public class IfTag extends BaseTag {
	private Logger logger = Logger.getLogger(IfTag.class);
	
	private List<BaseTag> tagLs = new ArrayList<BaseTag>();
	
	//验证表达式
	private String test = "";
	//正则的表达式
	private String regex = "";
	//正则情况下对比的源
	private String regexSource = "";
	
	public IfTag(Element element, BaseTag parent) throws TagException {
		super("If", element, parent);
		this.parse();
	}

	@Override
	protected void parse() throws TagException {
		String errMsg = "";
		errMsg = this.getTagName() + " 标签规则: \r\n";
		errMsg += "【必须包含属性】\r\n";
		errMsg += "test: 定义的参数，真实的数据必须为条件表达式\r\n";
		errMsg += "【或者属性】\r\n";
		errMsg += "regex: 正则表达式\r\n";
		errMsg += "regexSource: 正则表达式对应的源信息，${xxx.xxx}或${xxx}结构\r\n";
		errMsg += "属性regex存在时必须包含regexSource属性";;
		
		/**
		 * 判断foreach标签的属性
		 */
		List propLs = this.getElement().attributes();
		if (propLs.size() > 0) {
			for (Iterator it = propLs.iterator(); it.hasNext();) {
				Attribute attr = (Attribute) it.next();
				if (attr.getName().equals("test")) {
					this.test = attr.getValue();
				}
				else if (attr.getName().equals("regex")) {
					this.regex = attr.getValue();
				}
				else if (attr.getName().equals("regexSource")) {
					this.regexSource = attr.getValue();
				}
				else {
					logger.error(this.getTagName() + " 标签不允许包含属性: " + attr.getName());
					logger.error(errMsg);
					throw new TagException(this.getTagName() + " 标签不允许包含属性: " + attr.getName());
				}
			}
			
			if ((this.test == null || this.test.equals("")) && (this.regex == null || this.regex.equals(""))) {
				logger.error(this.getTagName() + "中test或regex属性值必须有一个不为空!");
				logger.error(errMsg);
				throw new TagException(this.getTagName() + "中test或regex属性值必须有一个不为空!");
			}
			
			if(!this.regex.equals("")&&(this.regexSource == null || this.regexSource.equals(""))){
				logger.error(this.getTagName() + "中regex属性存在时regexSource值不允许为空!");
				logger.error(errMsg);
				throw new TagException(this.getTagName() + "中regex属性存在时regexSource值不允许为空!");
			}
		}
		
		/**
		 * 获取标签内容
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
			logger.error(this.getTagName() + "标签中未配置任何内容！");
		}
	}
	
	/**
	 * <code>process</code> 执行foreach操作
	 * @param tl: 设备telnet登录对象
	 * @param globalArgHm: 全局参数列表
	 * @param forArgHm: foreach迭代参数列表
	 * @param logLs: 执行的log信息
	 * @param localVarHm: 局部变量
	 * @throws Exception 
	 * @throws IOException 
	 */
	public void process(CMDService tl, OperaterHandle handle, List<String> logLs, HashMap<String, VarBean> localHm)
			throws IOException, Exception {
		Object result="";
		if(test!=null&&!test.equals("")){
			logLs.add("开始从数据仓库中获取验证表达式源数据，test数据为:" + this.test);
			logger.info("开始从数据仓库中获取验证表达式源数据，test数据为:" + this.test);
			Map<String,VarBean> varBeanMap = this.getItemsData(handle, localHm,test);
			//对象内的所有值都取出来得了~~需要做替换认证 替换算数运算表达式
			Iterator<Entry<String,VarBean>> it = varBeanMap.entrySet().iterator();
			Entry<String,VarBean> entry;
			while(it.hasNext()){
				entry=it.next();
				test = test.replaceAll(entry.getKey(), entry.getValue().getObject().toString());
			}
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("js");
			result = engine.eval(test);
		}else{
			logLs.add("开始从数据仓库中获取验证表达式源数据，regexSource数据为:" + this.regexSource+";regex:"+regex);
			logger.info("开始从数据仓库中获取验证表达式源数据，regexSource数据为:" + this.regexSource+";regex:"+regex);
			Map<String,VarBean> varBeanMap = this.getItemsData(handle, localHm,regexSource);
			//对象内的所有值都取出来得了~~需要做替换认证 替换算数运算表达式
			Iterator<Entry<String,VarBean>> it = varBeanMap.entrySet().iterator();
			Entry<String,VarBean> entry;
			while(it.hasNext()){
				entry=it.next();
				regexSource = regexSource.replaceAll(entry.getKey(), entry.getValue().getObject().toString());
			}
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("js");
			result = engine.eval(test);
			
			Pattern pa = Pattern.compile(regex);
			Matcher ma = pa.matcher(regexSource);
			result = ma.find();
		}
		
		if(result.toString().equals("true")){
			
			for (BaseTag tag : this.tagLs) {
				if (tag.getTagName().equals("step")) {
					StepTag stepTag = (StepTag) tag;
					try {
						stepTag.process(tl, handle, logLs, localHm);
					} catch (Exception e) {
						continue;
					}
				}
			}
		}
		
	}

	/**
	 * <code>getItemsData</code> 提取参数值
	 * @param handle
	 * @param localVarHm
	 * @return
	 */
	private Map<String,VarBean> getItemsData(OperaterHandle handle, HashMap<String, VarBean> localVarHm,String propertieName) throws ProcessException,
			DataStorageException {
		VarBean varBean = null;
		Map<String,VarBean> map= new HashMap<String, VarBean>();
		boolean isFind = false;
		/**
		 * 提取参数值
		 */
		//判断数据是否符合${xxx.xxx} 或${xxx}结果
		if (propertieName.matches("\\$\\{\\w+(\\.\\w+)?}$")) {
			Pattern pa = Pattern.compile("\\$\\{(\\w+)\\.?(\\w+)?\\}");
			Matcher ma = pa.matcher(propertieName);
			
			while(ma.find()){
				String mainKey = ma.group(1);
				String subKey = ma.group(2);
				
				//先在局部参数中查找
				if (localVarHm != null && localVarHm.containsKey(mainKey)) {
					logger.debug("在局部数据变量中找到主键为:" + mainKey + "的数据!");
					VarBean vb = localVarHm.get(mainKey);
					if (subKey == null) {
						varBean = vb;
						isFind = true;
					} else {
						if(varBean.getType().equals(VarBeanStatic.TYPE_STRING))
							map.put("${"+mainKey+"."+subKey+"}",varBean);
						else{
							logger.error(this.getTagName() + "标签中"+propertieName+"指向的VarBean数据的类型不能为集合类型");
							throw new ProcessException(this.getTagName() + "标签中"+propertieName+"指向的VarBean数据的类型不能为集合类型");
						}
					}
				}
				
				//在全局参数中查找
				if (!isFind) {
					varBean = handle.getData(mainKey, subKey);
					if(varBean.getType().equals(VarBeanStatic.TYPE_STRING))
						map.put("${"+mainKey+"."+subKey+"}",varBean);
					else{
						logger.error(this.getTagName() + "标签中"+propertieName+"指向的VarBean数据的类型不能为集合类型");
						throw new ProcessException(this.getTagName() + "标签中"+propertieName+"指向的VarBean数据的类型不能为集合类型");
					}
				}
			}
			
		}
		else {
			logger.error(this.getTagName() + "标签中"+propertieName+"的内容不符合${xxx} 或 ${xxx.xxx}规则!");
			throw new ProcessException(this.getTagName() + "标签中"+propertieName+"的内容不符合${xxx} 或 ${xxx.xxx}规则!");
		}
		return map;
	}
	
	public Logger getLogger() {
		return logger;
	}

	public List<BaseTag> getTagLs() {
		return tagLs;
	}

	public String getTest() {
		return test;
	}

	public String getRegex() {
		return regex;
	}

	public String getRegexSource() {
		return regexSource;
	}
	
	
}
