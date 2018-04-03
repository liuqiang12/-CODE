package com.JH.dgather.frame.xmlparse.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;

import com.JH.dgather.frame.util.StringUtil;
import com.JH.dgather.frame.xmlparse.OperaterHandle;
import com.JH.dgather.frame.xmlparse.beans.VarBean;
import com.JH.dgather.frame.xmlparse.beans.VarBeanStatic;
import com.JH.dgather.frame.xmlparse.exception.DataStorageException;
import com.JH.dgather.frame.xmlparse.exception.ProcessException;
import com.JH.dgather.frame.xmlparse.exception.TagException;

/**
 * <result name="test">Total Nets: (\d++)</result> 
 * 			//将通过正则提取的数据存放到test键值中
 * <result name="test" operater="count">${result.isisNeighbors}</result> 
 * 			//将 ${result.isisNeighbors} 的数据求合，将结果数据存到test键值中
 * <result name="test" operater="count">Total Nets: (\d++)<result>
 * 			//将正则提取的数据求合，将结果存到test键值中
 * <result name="test" operater="size">${result.isisNeighbors}</result>
 * 			//将 ${result.isisNeighbors} 数据长度存到test键值中
 * <result name="test" operater="size">Total Nets: (\d++)</result>
 * 			//将符合正则的数据长度存到test键值中
 * <result name="test" operater="list"> Total Nets: (\d++)></result>
 * 			//将符合正则的所有数据以List方式存到test键值中
 * 
 * @author Administrator
 *
 */
public class ResultTag extends BaseTag {
	private Logger logger = Logger.getLogger(ResultTag.class);
	
	//存在数据仓库中的键值，不得重复
	private String name = "";
	//操作符
	private String operater = "";
	private String resultValue = "";
	
	private String prefix = "";
	private List<String> includeLs = new ArrayList<String>();
	private List<String> excludeLs = new ArrayList<String>();
	
	public ResultTag(Element element, BaseTag parent) throws TagException {
		super("result", element, parent);
		this.parse();
	}
	
	@Override
	protected void parse() throws TagException {
		String errMsg = "";
		errMsg = this.getTagName() + " 标签规则: \r\n";
		errMsg += "【必选属性】\r\n";
		errMsg += "name: 存储到全局数据仓库中的键值，必须唯一，不得重复\r\n";
		errMsg += "【可选属性】\r\n";
		errMsg += "operater: 操作符号，如果当前属性存在，那么result标签的内容根据操作符号的不同，验正规则也不同。\r\n";
		errMsg += "operater操作符说明: 1. count: 求合操作符号.  2. size: 求长度操作符合.  3. list: 取出所有符合正则的数据\r\n";
		errMsg += "prefix: 前缀，取出的结果必须加上此前缀 \r\n";
		errMsg += "include: 包含，取出的结果必须包含指定的字符串，多个确定关系以|间隔，他们之间是'或'关系，只要满足一个即可。\r\n";
		errMsg += "exclude: 不包换，取出的结果不得包含指定的字符串，多个确定关系以|间隔。\r\n";
		errMsg += "【不包含的子标签】\r\n";
		
		/**
		 * 判断result标签属性值
		 */
		List propLs = this.getElement().attributes();
		if (propLs.size() > 0) {
			for (Iterator it = propLs.iterator(); it.hasNext();) {
				Attribute attr = (Attribute) it.next();
				if (attr.getName().equals("name")) {
					this.name = attr.getValue();
				}
				else if (attr.getName().equals("operater")) {
					this.operater = attr.getValue();
				}
				else if (attr.getName().equals("prefix")) {
					this.prefix = attr.getValue();
				}
				else if (attr.getName().equals("include")) {
					checkIncludeProperty(attr.getValue());
				}
				else if (attr.getName().equals("exclude")) {
					checkExcludeProperty(attr.getValue());
				}
				else {
					logger.error(this.getTagName() + " 标签不允许包含属性: " + attr.getName());
					logger.error(errMsg);
					throw new TagException(this.getTagName() + " 标签不允许包含属性: " + attr.getName());
				}
			}
			
			if (this.name == null || "".equals(this.name)) {
				logger.error(this.getTagName() + " 标签中的name属性为null或空！");
				throw new TagException(this.getTagName() + " 标签中的name属性为null或空！");
			}
		}
		
		/**
		 * 获取标签的内容
		 */
		if (this.getElement().getText().equals("")) {
			logger.error(this.getTagName() + " 标签的内容不能为空。");
			logger.error(errMsg);
			throw new TagException(this.getTagName() + " 标签的内容不能为空。");
		}
		this.resultValue = this.getElement().getText();
		
		//操作符为 count
		if (this.operater != null) {
			//如果操作符号为count或size，那么标签值要么为${xxx.xxx}格式，要么为正则
			if ("count".equals(operater) || "size".equals(operater)) {
				//标签内容必须为 ${xxx.xxx} 格式
				if (this.resultValue.matches("\\$\\{(.+?)\\}$")) {
					if (!this.resultValue.matches("\\$\\{(\\w+)\\.?(\\w+)?\\}")) {
						logger.error("当" + this.getTagName() + "标签中operater操作符为count或size时，标签内容必须符合 ${xxx.xxx} 的规则，当前内容为： "
								+ this.resultValue);
						throw new TagException("当" + this.getTagName()
								+ "标签中operater操作符为count或size时，标签内容必须符合 ${xxx.xxx} 的规则，当前内容为： " + this.resultValue);
					}
				}
			}
			else if ("list".equals(operater)) {
				if (this.resultValue.matches("\\$\\{(.+?)\\}$")) {
					logger.error("当" + this.getTagName() + "标签中operater操作符为list时，标签内容必须为正则表达式，当前内容为： " + this.resultValue);
					throw new TagException("当" + this.getTagName() + "标签中operater操作符为list时，标签内容必须为正则表达式，当前内容为： "
							+ this.resultValue);
				}
			}
			
		}
		else {
			logger.error(this.getTagName() + " 标签中operater属性不符合规范!");
			logger.error(errMsg);
			throw new TagException(this.getTagName() + " 标签中operater属性不符合规范!");
		}
	}
	
	private void checkIncludeProperty(String contains) {
		String ctx = contains;
		if (!ctx.endsWith("|")) {
			ctx += "|";
		}
		String[] s = ctx.split("\\|");
		for (String sr : s) {
			if (sr.equals("")) {
				continue;
			}
			else {
				this.includeLs.add(sr);
			}
		}
	}
	
	private void checkExcludeProperty(String contains) {
		String ctx = contains;
		if (!ctx.endsWith("|")) {
			ctx += "|";
		}
		String[] s = ctx.split("\\|");
		for (String sr : s) {
			if (sr.equals("")) {
				continue;
			}
			else {
				this.excludeLs.add(sr);
			}
		}
	}
	
	/**
	 * <code>process</code> 执行标签操作
	 * @param handle: 
	 * @param result: 回显内容
	 * @param logLs: 日志信息
	 * @return 日志信息
	 * @throws ProcessException
	 */
	public VarBean process(OperaterHandle handle, String result, List<String> logLs) throws ProcessException,
			DataStorageException {
		/*
		if (result == null || "".equals(result)) {
			logger.error("在执行" + this.getTagName() + "标签操作时，用于分析的回显内容为null或空！");
			throw new ProcessException("在执行" + this.getTagName() + "标签操作时，用于分析的回显内容为null或空！");
		}
		*/
		VarBean varBean = null;
		
		//情况一: <result name="test">Total Nets: (\d++)</result> 
		if (this.operater == null || "".equals(this.operater)) {
			logger.info("Result operater："+operater);
			if (result == null || "".equals(result)) {
				logger.error("在执行" + this.getTagName() + "标签操作时，用于分析的回显内容为null或空！");
				throw new ProcessException("在执行" + this.getTagName() + "标签操作时，用于分析的回显内容为null或空！");
			}
			logLs.add("通过正则取结果，正则为: " + this.resultValue);
			logger.info("通过正则取结果，正则为: " + this.resultValue);
			Pattern p = Pattern.compile(this.resultValue);
			Matcher m = p.matcher(result);
			if (m.find()) {
				String r = m.group(1);
				logLs.add("匹配到结果，为: " + r + ", 存储到result仓库中的键值为: " + this.name);
				//handle.addResult(this.name, r);
				//varBean = new VarBean(VarBean.TYPE_STRING, r);
				logger.info("通过正则取结果，结果为: " + r);
				varBean = transResult(r);
				return varBean;
			}
			else {
				logLs.add("未找到匹配正则的结果，将存储一个空值");
				//handle.addResult(this.name, "");
				//varBean = new VarBean(VarBean.TYPE_STRING, "");
				varBean = transResult("");
				return varBean;
			}
		}
		else {
			if ("count".equals(this.operater)) {
				//情况二: <result name="test" operater="count">${result.isisNeighbors}</result>
				//判断是否是${}关联的参数
				if (this.resultValue.matches("\\$\\{(\\w+)\\.?(\\w+)?\\}")) {
					Pattern p = Pattern.compile("\\$\\{(\\w+)\\.?(\\w+)?\\}");
					Matcher m = p.matcher(this.resultValue);
					String mainK = "", subK = "";
					int count = 0;
					if (m.find()) {
						mainK = m.group(1);
						subK = m.group(2);
					}
					
					VarBean vb = handle.getData(mainK, subK);
					if (vb.getType().equals(VarBeanStatic.TYPE_LIST)) {
						List ls = (List) vb.getObject();
						for (int i = 0; i < ls.size(); i++) {
							String s = (String) ls.get(i);
							if (StringUtil.isNumberString(s)) {
								count += Integer.parseInt(s);
							}
							else {
								logger.error(this.getTagName() + " 标签通过 " + this.resultValue + " 关联的数据列表求和时，存在数据不是整数，当前查询出的数据为: "
										+ s);
								throw new ProcessException(this.getTagName() + " 标签通过 " + this.resultValue
										+ " 关联的数据列表求和时，存在数据不是整数，当前查询出的数据为: " + s);
							}
						}
						logLs.add("计算的数据和值为: " + count + ", 存储到result仓库中的键值为: " + this.name);
						//handle.addResult(this.name, Integer.toString(count));
						//varBean = new VarBean(VarBean.TYPE_STRING, Integer.toString(count));
						varBean = transResult(Integer.toString(count));
						return varBean;
					}
					else {
						logger.error("在执行" + this.getTagName() + "标签操作时，计算指定数据列表的和值时，数据类型不是List类型。 当前类型为: " + vb.getType()
								+ ", 数据关联值为：" + this.resultValue);
						throw new ProcessException("在执行" + this.getTagName() + "标签操作时，计算指定数据列表的和值时，数据类型不是List类型。 当前类型为: "
								+ vb.getType() + ", 数据关联值为：" + this.resultValue);
					}
				}
				//情况三: <result name="test" operater="count">Total Nets: (\d++)<result>
				else {
					logLs.add("通过正则取符合结果的数据列表，并将所有数据求和保存，正则为: " + this.resultValue);
					int count = 0;
					Pattern p = Pattern.compile(this.resultValue);
					Matcher m = p.matcher(result);
					while (m.find()) {
						if (StringUtil.isNumberString(m.group(1))) {
							count += Integer.parseInt(m.group(1));
						}
						else {
							logger.error(this.getTagName() + " 标签通过 " + this.resultValue + " 取符合数据列表，并求和时，存在数据不是整数，当前查询出的数据为: "
									+ m.group(1));
							throw new ProcessException(this.getTagName() + " 标签通过 " + this.resultValue
									+ " 取符合数据列表，并求和时，存在数据不是整数，当前查询出的数据为: " + m.group(1));
						}
					}
					logLs.add("计算的数据和值为: " + count + ", 存储到result仓库中的键值为: " + this.name);
					//handle.addResult(this.name, Integer.toString(count));
					//varBean = new VarBean(VarBean.TYPE_STRING, Integer.toString(count));
					varBean = transResult(Integer.toString(count));
					return varBean;
				}
			}
			//情况四: <result name="test" operater="list"> Total Nets: (\d++)></result>
			else if ("list".equals(this.operater)) {
				List<String> resultLs = new ArrayList<String>();
				logLs.add("通过正则取符合结果的数据列表，正则为: " + this.resultValue);
				Pattern p = Pattern.compile(this.resultValue);
				Matcher m = p.matcher(result);
				while (m.find()) {
					resultLs.add(m.group(1));
				}
				logLs.add("数据列表长度为: " + resultLs.size() + ", 存储到result仓库中的键值为: " + this.name);
				//handle.addResult(this.name, resultLs);
				//varBean = new VarBean(VarBean.TYPE_LIST, resultLs);
				varBean = transResult(resultLs);
				return varBean;
			}
			else if ("size".equals(this.operater)) {
				//情况五: <result name="test" operater="size">${result.isisNeighbors}</result>
				//判断是否是${}关联的参数
				if (this.resultValue.matches("\\$\\{(\\w+)\\.?(\\w+)?\\}")) {
					Pattern p = Pattern.compile("\\$\\{(\\w+)\\.?(\\w+)?\\}");
					Matcher m = p.matcher(this.resultValue);
					String mainK = "", subK = "";
					int count = 0;
					if (m.find()) {
						mainK = m.group(1);
						subK = m.group(2);
					}
					
					VarBean vb = handle.getData(mainK, subK);
					if (vb.getType().equals(VarBeanStatic.TYPE_LIST)) {
						List ls = (List) vb.getObject();
						count = ls.size();
						logLs.add("数据列表长度为: " + count + ", 存储到result仓库中的键值为: " + this.name);
						//handle.addResult(this.name, Integer.toString(count));
						//varBean = new VarBean(VarBean.TYPE_STRING, Integer.toString(count));
						varBean = transResult(Integer.toString(count));
						return varBean;
					}
					else {
						logger.error("在执行" + this.getTagName() + "标签操作时，计算指定数据列表长度时，数据类型不是List类型。 当前类型为: " + vb.getType()
								+ ", 数据关联值为：" + this.resultValue);
						throw new ProcessException("在执行" + this.getTagName() + "标签操作时，计算指定数据列表长度时，数据类型不是List类型。 当前类型为: "
								+ vb.getType() + ", 数据关联值为：" + this.resultValue);
					}
				}
				//情况六: <result name="test" operater="size">Total Nets: (\d++)</result>
				else {
					logLs.add("通过正则取符合结果的数据长度，正则为: " + this.resultValue);
					Pattern p = Pattern.compile(this.resultValue);
					Matcher m = p.matcher(result);
					int size = 0;
					while (m.find()) {
						size++;
					}
					logLs.add("长度为: " + size + ", 存储到result仓库中的键值为: " + this.name);
					//handle.addResult(this.name, Integer.toString(size));
					//varBean = new VarBean(VarBean.TYPE_STRING, Integer.toString(size));
					varBean = transResult(Integer.toString(size));
					return varBean;
				}
			}
			else {
				logger.error(this.getTagName() + " 标签的operater属性不能为: " + this.operater);
				throw new ProcessException(this.getTagName() + " 标签的operater属性不能为: " + this.operater);
			}
		}
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	private boolean judgeXXclude(String s) {
		boolean inOk = false;
		boolean exOk = true;
		if (this.includeLs.size() > 0) {
			for (String in : this.includeLs) {
				if (s.contains(in)) {
					inOk = true;
					break;
				}
			}
		}
		else {
			inOk = true;
		}
		
		if (this.excludeLs.size() > 0) {
			for (String ex : this.excludeLs) {
				if (s.contains(ex)) {
					exOk = false;
					break;
				}
			}
		}
		
		if (inOk && exOk) {
			return true;
		}
		else
			return false;
		
	}
	
	private VarBean transResult(Object result) {
		String rtnSresult = "";
		VarBean varbean = null;
		
		if (result instanceof String) {
			if (judgeXXclude((String) result)) {
				if (!this.prefix.equals("")) {
					rtnSresult = this.prefix + result;
				}
				else {
					rtnSresult = (String) result;
				}
				varbean = new VarBean(VarBeanStatic.TYPE_STRING, rtnSresult);
			}
			else {
				return null;
			}
		}
		else if (result instanceof List) {
			List ls = (List) result;
			List<String> rtnLs = new ArrayList<String>();
			for (int i = 0; i < ls.size(); i++) {
				Object obj = ls.get(i);
				VarBean vb = transResult(obj);
				if (vb.getType().equals(VarBeanStatic.TYPE_STRING)) {
					if (judgeXXclude((String) result)) {
						if (!this.prefix.equals("")) {
							rtnSresult = this.prefix + result;
						}
						else {
							rtnSresult = (String) result;
						}
						rtnLs.add(rtnSresult);
					}
				}
			}
			if (rtnLs.size() > 0) {
				varbean = new VarBean(VarBeanStatic.TYPE_LIST, rtnLs);
			}
			else
				return null;
		}
		
		return varbean;
	}
}
