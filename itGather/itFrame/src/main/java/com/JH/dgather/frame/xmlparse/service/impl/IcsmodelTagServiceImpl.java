package com.JH.dgather.frame.xmlparse.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;

import com.JH.dgather.frame.xmlparse.bo.FactoryBo;
import com.JH.dgather.frame.xmlparse.bo.IcsmodelBo;
import com.JH.dgather.frame.xmlparse.bo.iface.IBase;
import com.JH.dgather.frame.xmlparse.exception.TagException;
import com.JH.dgather.frame.xmlparse.service.IBaseService;

/**
 * systemRules.xml中icsmodel标签业务处理类
 * 
 * @author zhengbin
 * 
 */
public class IcsmodelTagServiceImpl implements IBaseService {
	private static Logger logger = Logger
			.getLogger(IcsmodelTagServiceImpl.class);

	// icsmodel对应的element对象
	private Element icsmodelEl = null;

	public IcsmodelTagServiceImpl(Element element) {
		icsmodelEl = element;
	}

	@Override
	public IBase parse() {

		IcsmodelBo icsmodelBo = null;

		// 获取icsmodel标签参数信息，获取失败返回null
		try {
			icsmodelBo = getTagInfo();
		} catch (TagException e) {
			logger.error(e.getMessage(), e);
			return null;
		}

		// 根据icsmodel规则对配置的属性进行检查，检查失败返回null
		if (!checkTagAttribute(icsmodelBo)) {
			return null;
		}

		// 关联icsmodel与子标签factory，如果成功返回true
		if (connSubTag(icsmodelBo)) {
			return icsmodelBo;
		} else {
			return null;
		}
	}

	/**
	 * 从xml的icsmodel节点信息中获取标签信息
	 * 
	 * @return icsmodel标签业务对象
	 * @throws TagException
	 *             标签不允许包含属性异常
	 */
	@SuppressWarnings("rawtypes")
	private IcsmodelBo getTagInfo() throws TagException {
		IcsmodelBo icsmodelBo = new IcsmodelBo();
		// 遍历icsmodel节点的所有属性
		for (Iterator it = icsmodelEl.attributeIterator(); it.hasNext();) {
			Attribute attribute = (Attribute) it.next();
			if ("id".equals(attribute.getName())) {
				icsmodelBo.setId(attribute.getValue());
			} else if ("gatherclass".equals(attribute.getName())) {
				icsmodelBo.setGatherclass(attribute.getValue());
			} else if ("name".equals(attribute.getName())) {
				icsmodelBo.setName(attribute.getValue());
			} else {
				throw new TagException("icsmodel 标签不允许包含属性: "
						+ attribute.getName());
			}
		}
		return icsmodelBo;
	}

	/**
	 * 检查标签中的参数配置是否符合规则
	 * 
	 * @param icsmodelBo
	 *            icsmodel标签业务对象
	 * @return true:检查成功 false:检查失败
	 */
	private boolean checkTagAttribute(IcsmodelBo icsmodelBo) {
		// id为必配属性
		if (null == icsmodelBo.getId() || "".equals(icsmodelBo.getId())) {
			logger.error("icsmodel标签中id属性值不能为空!");
			return false;
		}

		// gatherclass为必配属性
		if (null == icsmodelBo.getGatherclass()
				|| "".equals(icsmodelBo.getGatherclass())) {
			logger.error("icsmodel标签中gatherclass属性值不能为空!");
			return false;
		}
		return true;
	}

	/**
	 * 关联子标签
	 * 
	 * @param icsmodelBo
	 *            icsmodel标签业务对象（保存父子标签关联）
	 * @return true：关联成功 false：关联失败
	 */
	@SuppressWarnings("rawtypes")
	private boolean connSubTag(IcsmodelBo icsmodelBo) {
		Map<String, FactoryBo> factoryBoMap = new HashMap<String, FactoryBo>();

		// 遍历icsmodel节点下子节点信息
		for (Iterator it = icsmodelEl.elementIterator(); it.hasNext();) {
			Element element = (Element) it.next();
			// 当解析的标签为factory时，进行处理
			if ("factory".equals(element.getName())) {
				IBaseService baseService = new FactoryTagServiceImpl(element);
				FactoryBo factoryBo = (FactoryBo) baseService.parse();
				// 如果factory标签解析成功，则保存信息到内存
				if (null != factoryBo) {
					String key = factoryBo.getValue();
					// 如果icsmodel标签下的factory标签存在重复的信息，则提示配置错误，解析失败
					if (null == key || factoryBoMap.containsKey(key)) {
						logger.error("icsmodel id = "
								+ icsmodelBo.getId()
								+ "，获取factory信息出现重复配置，请确认配置文件的正确性，factory value = "
								+ key);
						return false;

					} else {
						factoryBoMap.put(key, factoryBo);
					}
				}
				// factory标签解析失败
				else {
					return false;
				}
			} else {
				logger.error("icsmodel id = " + icsmodelBo.getId()
						+ "，icsmodel 标签下不允许包含子标签: " + element.getName());
				return false;
			}
		}

		// 当icsmodel标签下存在factory标签时，factoryBoMap大于0，解析成功
		if (0 < factoryBoMap.size()) {
			icsmodelBo.setFactoryBoMap(factoryBoMap);
			return true;
		} else {
			logger.error("解析配置文件失败，无法从配置文件中解析出factory节点信息，" + icsmodelBo);
			return false;
		}
	}

	/**
	 * 通过factoryvalue找到icsmodel标签中的factory标签信息
	 * 
	 * @param icsmodelBo
	 *            icsmodel标签业务对象
	 * @param factoryValue
	 *            factory唯一标识value
	 * @return factory标签业务对象
	 */
	public static FactoryBo getFactoryInfoByValue(IcsmodelBo icsmodelBo,
			String factoryValue) {
		if (null == icsmodelBo) {
			logger.error("icsmodel标签业务对象信息为null，无法获取factory信息");
			return null;
		} else {
			Map<String, FactoryBo> factoryBoMap = icsmodelBo.getFactoryBoMap();
			if (factoryBoMap.containsKey(factoryValue)) {
				return factoryBoMap.get(factoryValue);
			} else {
				logger.error("icsmodel标签业务对象没有对应的factory关联信息，factory value = "
						+ factoryValue);
				return null;
			}
		}
	}
}
