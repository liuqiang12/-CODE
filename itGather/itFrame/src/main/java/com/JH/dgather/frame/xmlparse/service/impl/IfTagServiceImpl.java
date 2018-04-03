package com.JH.dgather.frame.xmlparse.service.impl;

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
import javax.script.ScriptException;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;

import com.JH.dgather.frame.common.cmd.CMDService;
import com.JH.dgather.frame.xmlparse.OperaterHandle;
import com.JH.dgather.frame.xmlparse.beans.VarBean;
import com.JH.dgather.frame.xmlparse.beans.VarBeanStatic;
import com.JH.dgather.frame.xmlparse.bo.IfBo;
import com.JH.dgather.frame.xmlparse.bo.StepBo;
import com.JH.dgather.frame.xmlparse.bo.iface.IBase;
import com.JH.dgather.frame.xmlparse.exception.DataStorageException;
import com.JH.dgather.frame.xmlparse.exception.TagException;
import com.JH.dgather.frame.xmlparse.service.IBaseService;

/**
 * systemRules.xml中icsmodel标签业务处理类
 * 
 * @author zhengbin
 * 
 */
public class IfTagServiceImpl implements IBaseService {
	private static Logger logger = Logger.getLogger(IfTagServiceImpl.class);

	// if标签对应的element对象
	private Element ifEl = null;

	public IfTagServiceImpl(Element element) {
		ifEl = element;
	}

	@Override
	public IBase parse() {

		IfBo ifBo = null;

		// 获取if标签参数信息，获取失败返回null
		try {
			ifBo = getTagInfo();
		} catch (TagException e) {
			logger.error(e.getMessage(), e);
			return null;
		}

		// 根据ifbo规则对配置的信息进行检查，检查失败返回null
		if (!checkTagInfo(ifBo)) {
			return null;
		}

		// 关联ifbo与子标签step，如果成功返回true
		if (connSubTag(ifBo)) {
			return ifBo;
		} else {
			return null;
		}

	}

	/**
	 * 从xml的if节点信息中获取标签信息
	 * 
	 * @return if标签业务对象
	 * @throws TagException
	 *             标签不允许包含属性异常
	 */
	@SuppressWarnings("rawtypes")
	private IfBo getTagInfo() throws TagException {
		IfBo ifBo = new IfBo();
		// 遍历if节点的所有属性
		for (Iterator it = ifEl.attributeIterator(); it.hasNext();) {
			Attribute attribute = (Attribute) it.next();
			if ("test".equals(attribute.getName())) {
				ifBo.setTest(attribute.getValue());
			} else if ("regex".equals(attribute.getName())) {
				ifBo.setRegex(attribute.getValue());
			} else if ("regexSource".equals(attribute.getName())) {
				ifBo.setRegexSource(attribute.getValue());
			} else {
				throw new TagException("if 标签不允许包含属性: " + attribute.getName());
			}
		}
		return ifBo;
	}

	/**
	 * 检查标签中的信息配置是否符合规则
	 * 
	 * @param ifBo
	 *            if标签业务对象
	 * @return true:检查成功 false:检查失败
	 */
	private boolean checkTagInfo(IfBo ifBo) {
		// test或regex属性值必须有一个不能为空!
		if ((null == ifBo.getTest() || "".equals(ifBo.getTest()))
				&& (null == ifBo.getRegex() || "".equals(ifBo.getRegex()))) {
			logger.error("if 标签中test和regex属性值必须有一个不能为空!");
			return false;
		}

		if (null != ifBo.getRegex() && !"".equals(ifBo.getRegex())) {
			if (null == ifBo.getRegexSource()
					|| "".equals(ifBo.getRegexSource())) {
				logger.error("if 标签中regex属性存在时regexSource值不允许为空!");
				return false;
			}
		}
		return true;
	}

	/**
	 * 关联子标签
	 * 
	 * @param ifBo
	 *            if标签业务对象（保存父子标签关联）
	 * @return true：关联成功 false：关联失败
	 */
	@SuppressWarnings("rawtypes")
	private boolean connSubTag(IfBo ifBo) {
		List<StepBo> stepBoList = new ArrayList<StepBo>();

		// 遍历if节点下子节点信息
		for (Iterator it = ifEl.elementIterator(); it.hasNext();) {
			Element element = (Element) it.next();
			// 当解析的标签为step时，对step进行处理
			if ("step".equals(element.getName())) {
				IBaseService baseService = new StepTagServiceImpl(element);
				StepBo stepBo = (StepBo) baseService.parse();
				// 如果step标签解析成功，则保存信息到内存中
				if (null != stepBo) {
					stepBoList.add(stepBo);
				} else {
					// stepTag解析失败原因的日志信息在parse（）方法中打印此处不再重复记录日志
					return false;
				}
			} else {
				logger.error("if 标签不允许包含子标签: " + element.getName());
				return false;
			}
		}

		// 成功解析出if标签下子节点信息时，解析结果大于0，解析成功
		if (0 < stepBoList.size()) {
			ifBo.setStepBoList(stepBoList);
			return true;
		} else {
			logger.error("解析配置文件失败，无法从配置文件中解析出if的子节点信息，" + ifBo);
			return false;
		}
	}

	public static boolean process(CMDService cmdService, OperaterHandle handle,
			IBase base, HashMap<String, VarBean> localHm) {
		IfBo ifBo = (IfBo) base;
		String test = ifBo.getTest();
		Object result = null;
		if (null != test && !"".equals(test)) {
			if (logger.isInfoEnabled()) {
				logger.info("开始从数据仓库中获取验证表达式源数据，test数据为:" + test);
			}
			Map<String, VarBean> varBeanMap = getItemsData(handle, test,
					localHm);
			// 对象内的所有值都取出来得了~~需要做替换认证 替换算数运算表达式
			for (Entry<String, VarBean> entry : varBeanMap.entrySet()) {
				test = test.replaceAll(entry.getKey(), entry.getValue()
						.getObject().toString());
			}

			// 执行脚本
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("js");
			try {
				result = engine.eval(test);
			} catch (ScriptException e) {
				logger.error("执行脚本异常，test = " + test, e);
				return false;
			}
		} else {
			String regexSource = ifBo.getRegexSource();
			if (logger.isInfoEnabled()) {
				logger.info("开始从数据仓库中获取验证表达式源数据，regexSource数据为:" + regexSource
						+ ";regex:" + ifBo.getRegex());
			}
			Map<String, VarBean> varBeanMap = getItemsData(handle, regexSource,
					localHm);
			// 对象内的所有值都取出来得了~~需要做替换认证 替换算数运算表达式
			for (Entry<String, VarBean> entry : varBeanMap.entrySet()) {
				regexSource = regexSource.replaceAll(entry.getKey(), entry
						.getValue().getObject().toString());
			}
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("js");
			try {
				result = engine.eval(test);
			} catch (ScriptException e) {
				logger.error("执行脚本异常，test = " + test, e);
				return false;
			}

			Pattern pa = Pattern.compile(ifBo.getRegex());
			Matcher ma = pa.matcher(regexSource);
			result = ma.find();
		}

		// 当脚本执行成功，则继续后续操作
		if (null != result && "true".equals((String) result)) {
			for (StepBo stepBo : ifBo.getStepBoList()) {
				// 如果step指令执行成功，则继续操作
				if ("step".equals(stepBo.getTagNameByObject())) {
					if (!StepTagServiceImpl.process(cmdService, handle, stepBo,
							null)) {
						return false;
					}
				}
			}
		} else {
			logger.error("执行脚本返回值不为true，无法继续执行后续操作，result = " + result);
			return false;
		}
		return true;

	}

	/**
	 * 从内存中提取propertieName对应的数据
	 * 
	 * @param handle
	 *            操作类
	 * @param localVarHm
	 *            局部参数
	 * @param proStr
	 *            需要提取数据的名称来源
	 * @return 提取数据结果集合
	 */
	private static Map<String, VarBean> getItemsData(OperaterHandle handle,
			String proStr, HashMap<String, VarBean> localVarHm) {
		VarBean varBean = null;
		Map<String, VarBean> map = new HashMap<String, VarBean>();

		// 判断数据是否符合${xxx.xxx} 或${xxx}结果
		if (proStr.matches("\\$\\{\\w+(?:\\.\\w+)?}$")) {
			Pattern pa = Pattern.compile("\\$\\{(\\w+)(?:\\.(\\w+))?\\}");
			Matcher ma = pa.matcher(proStr);

			while (ma.find()) {
				String mainKey = ma.group(1);
				String subKey = ma.group(2);

				// 先在局部参数中查找
				if (null != localVarHm && localVarHm.containsKey(mainKey)) {
					if (logger.isDebugEnabled()) {
						logger.debug("在局部数据变量中找到主键为:" + mainKey + "的数据!");
					}
					VarBean vb = localVarHm.get(mainKey);
					// 如果subkey为null
					if (null == subKey) {
						varBean = vb;
					} else {
						if (VarBeanStatic.TYPE_STRING.equals(vb.getType()))
							map.put("${" + mainKey + "." + subKey + "}",
									varBean);
						else {
							logger.error("if 标签中" + proStr
									+ "，指向的VarBean数据的类型不能为集合类型");
							return null;
						}
					}
				}

				// 在全局参数中查找
				if (null == varBean) {
					try {
						varBean = handle.getData(mainKey, subKey);
					} catch (DataStorageException e) {
						logger.error("无法从全局数据中，查找主键为：" + mainKey + "子健为: "
								+ subKey + "的数据!)", e);
						return null;
					}
					if (VarBeanStatic.TYPE_STRING.equals(varBean.getType())) {
						map.put("${" + mainKey + "." + subKey + "}", varBean);
					} else {
						logger.error("if 标签中" + proStr
								+ "，指向的VarBean数据的类型不能为集合类型");
						return null;
					}
				}
			}

		} else {
			logger.error("if 标签中" + proStr + "的内容不符合${xxx} 或 ${xxx.xxx}规则!");
			return null;
		}
		return map;
	}

}
