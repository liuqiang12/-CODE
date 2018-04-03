package com.JH.dgather.frame.xmlparse.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;

import com.JH.dgather.frame.common.cmd.CMDService;
import com.JH.dgather.frame.util.StringUtil;
import com.JH.dgather.frame.xmlparse.OperaterHandle;
import com.JH.dgather.frame.xmlparse.beans.VarBean;
import com.JH.dgather.frame.xmlparse.beans.VarBeanStatic;
import com.JH.dgather.frame.xmlparse.exception.DataStorageException;
import com.JH.dgather.frame.xmlparse.exception.ProcessException;
import com.JH.dgather.frame.xmlparse.exception.TagException;

/**
 * <code>ForeachTag</code> 循环标签
 * 使用说明
 * ForeachTag标签中必须包含以下属性:
 * var: 局部参数定义的名称，foreach标签内部可以使用当前的局部参数，使用规则如下：
 * 		假设var="test"
 * 		如果var指向的参数（即items属性）类型为HashMap, 那么${test.xxxx}, xxxx为键值
 *      如果var指向的参数类型为List，那么${test}，使用当前循环到的数据。
 *      注意：HashMap与List存储的数据类型只能为String
 * items: 指向的参数，必须为${xxxx.xxxx}格式
 * 可选择的属性如下：
 * varStatus: 定义一个循环索引参数名称
 * startWith: 当startWith存在时，varStatus必须存在，表示索引的起始值
 * 			  startWith默认值为0，必须大于等于0
 * 			标签中可使用当前索引，如下：
 * 			假设：varStatus="testA" startWith="1"
 * 			<foreach 
 * 					var="test"    				//必须属性 
 * 					items="${args.portlist}"	//必须属性
 * 					varStatus="testA"			//可选属性
 * 					startWith="1"				//可选属性
 * 			>
 * 				<step>
 * 						<command>dis ip interface ${testA}</command>
 * 				</step>
 * 			</foreach>
 * @author Wang xueDan
 * @version 1.0, 2012/3/09
 */
public class ForeachTag extends BaseTag {
	private Logger logger = Logger.getLogger(ForeachTag.class);
	
	private List<BaseTag> tagLs = new ArrayList<BaseTag>();
	
	//定义的参数
	private String var = "";
	//参数路径
	private String items = "";
	//需要使用循环递增值是，递增值参数的名称
	private String varStatus = "";
	//递增值起始值，必须为数字
	private String startWith = "";
	
	public ForeachTag(Element element, BaseTag parent) throws TagException {
		super("foreach", element, parent);
		this.parse();
	}
	
	@Override
	protected void parse() throws TagException {
		String errMsg = "";
		errMsg = this.getTagName() + " 标签规则: \r\n";
		errMsg += "【必须包含属性】\r\n";
		errMsg += "var: 定义的参数，真实的数据必须为List类型\r\n";
		errMsg += "items: 参数路径，${xxx.xxx}或${xxx}结构\r\n";
		errMsg += "【可选属性】\r\n";
		errMsg += "varStatus: 需要使用循环递增值是，递增值参数的名称\r\n";
		errMsg += "startWith: 递增值起始值，必须为整数\r\n";
		errMsg += "【可包含的子标签】\r\n";
		errMsg += "<var> <step> <result> <interaction>";
		
		/**
		 * 判断foreach标签的属性
		 */
		List propLs = this.getElement().attributes();
		if (propLs.size() > 0) {
			for (Iterator it = propLs.iterator(); it.hasNext();) {
				Attribute attr = (Attribute) it.next();
				if (attr.getName().equals("var")) {
					this.var = attr.getValue();
				}
				else if (attr.getName().equals("items")) {
					this.items = attr.getValue();
				}
				else if (attr.getName().equals("varStatus")) {
					this.varStatus = attr.getValue();
				}
				else if (attr.getName().equals("startWith")) {
					this.startWith = attr.getValue();
				}
				else {
					logger.error(this.getTagName() + " 标签不允许包含属性: " + attr.getName());
					logger.error(errMsg);
					throw new TagException(this.getTagName() + " 标签不允许包含属性: " + attr.getName());
				}
			}
			
			if (this.var == null || this.var.equals("") || this.items == null || this.items.equals("")) {
				logger.error(this.getTagName() + "中var或items属性值为空!");
				logger.error(errMsg);
				throw new TagException(this.getTagName() + "中var或items属性值为空!");
			}
			
			if ((this.varStatus == null || this.varStatus.equals("")) && !this.startWith.equals("")) {
				logger.error(this.getTagName() + "标签中，startWith属性存在时，varStatus属性必须存在！");
				throw new TagException(this.getTagName() + "标签中，startWith属性存在时，varStatus属性必须存在！");
			}
			
			if (!this.varStatus.equals("")) {
				if (this.startWith == null || this.startWith.equals("")) {
					this.startWith = "0";
				}
				else if (!StringUtil.isNumberString(this.startWith)) {
					logger.error(this.getTagName() + "标签中，startWith必须是整数！startWith:" + this.startWith);
					throw new TagException(this.getTagName() + "标签中，startWith必须是整数！startWith:" + this.startWith);
				}
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
				else if (em.getName().equals("foreach")) {
					ForeachTag foreachTag = new ForeachTag(em, this);
					this.tagLs.add(foreachTag);
				}
				else if (em.getName().equals("result")) {
					ResultTag resultTag = new ResultTag(em, this);
					this.tagLs.add(resultTag);
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
	 * <code>judgeSaveTag</code> 判断当前的标签表列中是否已包含相同名称的标签。因为有些标签只能出现一次
	 * @param tagName: 需要判断的标签名称
	 * @return
	 */
	private void judgeSameTag(String tagName) throws TagException {
		for (BaseTag tag : this.tagLs) {
			if (tag.getTagName().equals(tagName)) {
				throw new TagException(this.getTagName() + "标签中不得重复出现 " + tag.getTagName());
			}
		}
	}
	
	/**
	 * <code>getItemsData</code> 提取参数值
	 * @param handle
	 * @param localVarHm
	 * @return
	 */
	private VarBean getItemsData(OperaterHandle handle, HashMap<String, VarBean> localVarHm) throws ProcessException,
			DataStorageException {
		VarBean varBean = null;
		boolean isFind = false;
		/**
		 * 提取参数值
		 */
		//判断数据是否符合${xxx.xxx} 或${xxx}结果
		if (this.items.matches("\\$\\{\\w+(\\.\\w+)?}$")) {
			Pattern pa = Pattern.compile("\\$\\{(\\w+)\\.?(\\w+)?\\}");
			Matcher ma = pa.matcher(this.items);
			
			if (ma.find()) {
				String mainKey = ma.group(1);
				String subKey = ma.group(2);
				
				//先在局部参数中查找
				if (localVarHm != null && localVarHm.containsKey(mainKey)) {
					logger.debug("在局部数据变量中找到主键为:" + mainKey + "的数据!");
					VarBean vb = localVarHm.get(mainKey);
					if (subKey == null) {
						varBean = vb;
						isFind = true;
					}
					else {
						if (vb.getType().equals(VarBeanStatic.TYPE_HASHMAP)) {
							HashMap<String, VarBean> thm = (HashMap<String, VarBean>) vb.getObject();
							if (thm.containsKey(subKey)) {
								varBean = thm.get(subKey);
								logger.debug(">>> " + varBean.getType());
								isFind = true;
							}
							else {
								logger.error("局部数据中，主键为:" + mainKey + "的HashMap数据集合中，不存在键值为: " + subKey + "的数据!");
								throw new ProcessException("局部数据中，主键为:" + mainKey + "的HashMap数据集合中，不存在键值为: " + subKey + "的数据!");
							}
						}
						else {
							logger.error("局部数据中，主键为：" + mainKey + "的数据，不是HashMap类型，因为要从中取键值为: " + subKey + "的数据!, 当前数据类型为: "
									+ vb.getType());
							throw new ProcessException("局部数据中，主键为：" + mainKey + "的数据，不是HashMap类型，因为要从中取键值为: " + subKey
									+ "的数据!, 当前数据类型为: " + vb.getType());
						}
					}
				}
				
				//在全局参数中查找
				if (!isFind) {
					varBean = handle.getData(mainKey, subKey);
					if (!varBean.getType().equals(VarBeanStatic.TYPE_HASHMAP) && !varBean.getType().equals(VarBeanStatic.TYPE_LIST)) {
						logger.error("foreach标签中items指向的VarBean数据的类型不是HashMap或List!");
						throw new ProcessException("foreach标签中items指向的VarBean数据的类型不是HashMap或List!");
					}
				}
			}
		}
		else {
			logger.error("foreach标签中items的内容不符合${xxx} 或 ${xxx.xxx}规则!");
			throw new ProcessException("foreach标签中items的内容不符合${xxx} 或 ${xxx.xxx}规则!");
		}
		return varBean;
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
	public void process(CMDService tl, OperaterHandle handle, List<String> logLs, HashMap<String, VarBean> localVarHm)
			throws IOException, Exception {
		logLs.add("开始从数据仓库中获取foreach的迭代数据，item数据为:" + this.items);
		VarBean varBean = this.getItemsData(handle, localVarHm);
		logLs.add("取到迭代数据, 数据类型为: " + varBean.getType());
		
		if (!varBean.getType().equals(VarBeanStatic.TYPE_LIST)) {
			logger.error("foreach标签中items指向的数据类型必须为List!, 当前的类型是:" + varBean.getType());
			throw new ProcessException("foreach标签中items指向的数据类型必须为List!, 当前的类型是:" + varBean.getType());
		}
		
		List<VarBean> vbLs = (List<VarBean>) varBean.getObject();
		
		logger.debug("开始执行foreach，共循环 " + vbLs.size() + " 次, var属性为:" + this.var);
		logLs.add("开始执行foreach，共循环 " + vbLs.size());
		
		for (int i = 0; i < vbLs.size(); i++) {
			logLs.add("执行第 " + (i + 1) + "次循环");
			HashMap<String, VarBean> localHm = new HashMap<String, VarBean>();
			VarBean localVar = vbLs.get(i);
			logger.debug("执行第 " + (i + 1) + "次循环, var值为:" + this.var + "，item: " + localVar.getType()+",var对应的数据："+localVar.getObject().toString());
			//为防止foreach嵌套使用时，定义同时名称的var
			if (localVarHm != null) {
				if (localVarHm.containsKey(this.var)) {
					logger.error("当前foreach标签中var属性值与外层的foreach标签中的var属性值相同！");
					throw new ProcessException("当前foreach标签中var属性值与外层的foreach标签中的var属性值相同！");
				}
				else {
					localHm.putAll(localVarHm);
					
				}
			}
			//将迭代参数存到局部参数中
			logger.debug("添加一个局部变量, key: " + this.var);
			localHm.put(this.var, localVar);
			//如果有设置循环索引，也存到局部参数中
			if (!this.varStatus.equals("")) {
				int mId = Integer.parseInt(this.startWith) + i;
				VarBean vsVB = new VarBean(VarBeanStatic.TYPE_STRING, Integer.toString(mId));
				logger.debug("添加一个局部变量, key: " + this.varStatus + ", 值为String类型，数据是: " + mId);
				localHm.put(this.varStatus, vsVB);
			}
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
	 * @return the tagLs
	 */
	public List<BaseTag> getTagLs() {
		return tagLs;
	}
	
	/**
	 * @return the var
	 */
	public String getVar() {
		return var;
	}
	
	/**
	 * @return the items
	 */
	public String getItems() {
		return items;
	}
	
	/**
	 * @return the varStatus
	 */
	public String getVarStatus() {
		return varStatus;
	}
	
	/**
	 * @return the startWith
	 */
	public String getStartWith() {
		return startWith;
	}
}
