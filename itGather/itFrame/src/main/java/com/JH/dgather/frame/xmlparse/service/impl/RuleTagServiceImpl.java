package com.JH.dgather.frame.xmlparse.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;

import com.JH.dgather.frame.common.cmd.CMDService;
import com.JH.dgather.frame.xmlparse.OperaterHandle;
import com.JH.dgather.frame.xmlparse.beans.VarBean;
import com.JH.dgather.frame.xmlparse.bo.RuleBo;
import com.JH.dgather.frame.xmlparse.bo.StepBo;
import com.JH.dgather.frame.xmlparse.bo.iface.IBase;
import com.JH.dgather.frame.xmlparse.exception.TagException;
import com.JH.dgather.frame.xmlparse.service.IBaseService;

/**
 * systemRules.xml中rule标签业务处理类
 * 
 * @author zhengbin
 * 
 */
public class RuleTagServiceImpl implements IBaseService {
	private static Logger logger = Logger.getLogger(RuleTagServiceImpl.class);

	// rule标签对应的element对象
	private Element ruleEl = null;

	public RuleTagServiceImpl(Element element) {
		ruleEl = element;
	}

	@Override
	public IBase parse() {

		RuleBo ruleBo = null;

		// 获取rule标签参数
		try {
			ruleBo = getTagAttribute();
		} catch (TagException e) {
			logger.error(e.getMessage(), e);
			return null;
		}

		// 判断rule标签必填参数是否存在，不存在则返回null表示解析xml失败
		if (!isGetRequiredAtt(ruleBo)) {
			return null;
		}

		// 获取rule子节点step信息并放入ruleBo，获取成功返回true
		if (isConnSubTag(ruleBo)) {
			return ruleBo;
		} else {
			return null;
		}
	}

	/**
	 * 从xml的rule节点信息中获取标签属性
	 * 
	 * @return rule标签业务对象
	 * @throws TagException
	 *             标签不允许包含属性异常
	 */
	@SuppressWarnings("rawtypes")
	private RuleBo getTagAttribute() throws TagException {
		RuleBo ruleBo = new RuleBo();

		// 遍历rule节点的所有属性
		for (Iterator it = ruleEl.attributeIterator(); it.hasNext();) {
			Attribute attribute = (Attribute) it.next();
			if ("id".equals(attribute.getName())) {
				ruleBo.setId(attribute.getValue());
			} else if ("name".equals(attribute.getName())) {
				ruleBo.setName(attribute.getValue());
			} else {
				throw new TagException("rule 标签不允许包含属性: " + attribute.getName());
			}
		}
		return ruleBo;
	}

	/**
	 * 是否从标签属性中获取到必填参数
	 * 
	 * @param ruleBo
	 *            rule标签业务对象
	 * @return true:成功获取必填参数 false:未获取到必填参数
	 */
	private boolean isGetRequiredAtt(RuleBo ruleBo) {
		// rule标签的id为必配属性
		if (null == ruleBo || null == ruleBo.getId()
				|| "".equals(ruleBo.getId())) {
			logger.error("rule 标签中id属性为必填项不能为空！");
			return false;
		}
		return true;
	}

	/**
	 * 是否关联子标签
	 * 
	 * @param ruleBo
	 *            rule标签业务对象（保存父子标签关联）
	 * @return true：关联成功 false：关联失败
	 */
	@SuppressWarnings("rawtypes")
	private boolean isConnSubTag(RuleBo ruleBo) {

		List<StepBo> stepBoList = new ArrayList<StepBo>();
		// 遍历rule节点下子节点信息
		for (Iterator stepI = ruleEl.elementIterator(); stepI.hasNext();) {
			Element element = (Element) stepI.next();
			// 当解析的标签tag为step时，对tag进行处理
			if ("step".equals(element.getName())) {
				IBaseService baseService = new StepTagServiceImpl(element);
				StepBo stepBo = (StepBo) baseService.parse();
				// 如果stepTag解析成功，则保存信息到内存中
				if (null != stepBo) {
					stepBoList.add(stepBo);
				} else {
					// stepTag解析失败原因的日志信息在parse（）方法中打印此处不再重复记录日志
					return false;
				}
			} else {
				logger.error("rule 不允许包含标签：" + element.getName());
				return false;
			}
		}
		// 成功解析出rule标签下step子节点信息时，解析结果大于0，解析成功
		if (0 < stepBoList.size()) {
			ruleBo.setStepBoList(stepBoList);
			return true;
		} else {
			logger.error("解析配置文件失败，无法从配置文件中解析出step节点信息，" + ruleBo);
			return false;
		}
	}

	/**
	 * rule标签业务执行处理
	 * 
	 * @param cmdService
	 *            服务器信息
	 * @param handle
	 *            操作类
	 * @param ruleBo
	 *            rule业务对象
	 * @param localVarHm
	 *            临时参数
	 * @return 执行结果：true执行成功，false执行失败
	 */
	public static boolean process(CMDService cmdService, OperaterHandle handle,
			RuleBo ruleBo, Map<String, VarBean> localVarHm) {
		if (null == ruleBo) {
			logger.error("rule标签业务对象为空，无法获取rule的子标签信息");
			return false;
		}
		// 如果rule标签中的step规则，逐一执行
		for (StepBo stepBo : ruleBo.getStepBoList()) {
			// 如果执行step失败，则返回false
			if (!StepTagServiceImpl.process(cmdService, handle, stepBo,
					localVarHm)) {
				return false;
			}
		}

		// 当rule规则中所有step执行成功才返回true,执行成功
		return true;
	}
}
