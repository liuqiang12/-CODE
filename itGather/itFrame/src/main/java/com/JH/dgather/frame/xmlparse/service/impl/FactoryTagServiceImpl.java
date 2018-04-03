package com.JH.dgather.frame.xmlparse.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;

import com.JH.dgather.frame.xmlparse.bo.FactoryBo;
import com.JH.dgather.frame.xmlparse.bo.ModelBo;
import com.JH.dgather.frame.xmlparse.bo.iface.IBase;
import com.JH.dgather.frame.xmlparse.exception.TagException;
import com.JH.dgather.frame.xmlparse.service.IBaseService;

/**
 * systemRules.xml中factory标签业务处理类
 * 
 * @author zhengbin
 * 
 */
public class FactoryTagServiceImpl implements IBaseService {
	private static Logger logger = Logger
			.getLogger(FactoryTagServiceImpl.class);

	// factory对应的element对象
	private Element factoryEl = null;

	public FactoryTagServiceImpl(Element element) {
		factoryEl = element;
	}

	@Override
	public IBase parse() {

		FactoryBo factoryBo = null;

		// 获取factory标签参数信息，获取失败返回null
		try {
			factoryBo = getTagAttribute();
		} catch (TagException e) {
			logger.error(e.getMessage(), e);
			return null;
		}

		// 根据factory规则对配置的属性进行检查
		if (!checkTagAttribute(factoryBo)) {
			return null;
		}

		// 关联factory与子标签model，如果成功返回true
		if (connSubTag(factoryBo)) {
			return factoryBo;
		} else {
			return null;
		}
	}

	/**
	 * 从xml的factory节点信息中获取标签属性
	 * 
	 * @return factory标签业务对象
	 * @throws TagException
	 *             标签不允许包含属性异常
	 */
	@SuppressWarnings("rawtypes")
	private FactoryBo getTagAttribute() throws TagException {
		FactoryBo factoryBo = new FactoryBo();

		// 遍历factory节点的所有属性
		for (Iterator it = factoryEl.attributeIterator(); it.hasNext();) {
			Attribute attribute = (Attribute) it.next();
			if ("value".equals(attribute.getName())) {
				factoryBo.setValue(attribute.getValue());
			} else if ("name".equals(attribute.getName())) {
				factoryBo.setName(attribute.getValue());
			} else {
				throw new TagException("factory 标签不允许包含属性: "
						+ attribute.getName());
			}
		}
		return factoryBo;
	}

	/**
	 * 检查标签中的参数配置是否符合规则
	 * 
	 * @param factoryBo
	 *            factory标签业务对象
	 * @return true:检查成功 false:检查失败
	 */
	private boolean checkTagAttribute(FactoryBo factoryBo) {
		// factory标签的value属性为必配属性
		if (null == factoryBo.getValue() || "".equals(factoryBo.getValue())) {
			logger.error("factory 标签中value属性值不能为空!");
			return false;
		}
		return true;
	}

	/**
	 * 关联子标签
	 * 
	 * @param factoryBo
	 *            factory标签业务对象（保存父子标签关联）
	 * @return true：关联成功 false：关联失败
	 */
	@SuppressWarnings("rawtypes")
	private boolean connSubTag(FactoryBo factoryBo) {
		Map<String, ModelBo> modelBoMap = new HashMap<String, ModelBo>();

		// 遍历factory节点下子节点信息
		for (Iterator it = factoryEl.elementIterator(); it.hasNext();) {
			Element element = (Element) it.next();
			// 当解析的标签为model时，进行处理
			if ("model".equals(element.getName())) {
				IBaseService baseService = new ModelTagServiceImpl(element);
				ModelBo modelBo = (ModelBo) baseService.parse();
				// 如果model标签解析成功，则保存信息到内存
				if (null != modelBo) {
					String key = modelBo.getValue().toLowerCase();
					// 如果factory标签下的model标签存在重复的信息，则提示配置错误，解析失败
					if (null == key || modelBoMap.containsKey(key)) {
						logger.error("factory value = " + factoryBo.getValue()
								+ "，获取model信息出现重复配置，请确认配置文件的正确性，model value = "
								+ key);
						return false;

					} else {
						modelBoMap.put(key, modelBo);
					}
				}
				// model标签解析失败
				else {
					return false;
				}
			} else {
				logger.error("factory value = " + factoryBo.getValue()
						+ "，factory 标签下不允许包含子标签: " + element.getName());
			}
		}

		// 当factory标签下存在model标签时，modelBoMap大于0，解析成功
		if (0 < modelBoMap.size()) {
			factoryBo.setModelBoMap(modelBoMap);
			return true;
		} else {
			logger.error("解析配置文件失败，无法从配置文件中解析出model节点信息，" + factoryBo);
			return false;
		}
	}

	/**
	 * 通过modelvalue找到factory标签中的model标签信息
	 * 
	 * @param factoryBo
	 *            factory标签业务对象
	 * @param modelValue
	 *            model唯一标识value
	 * @return model标签业务对象
	 */
	public static ModelBo getModelInfoByValue(FactoryBo factoryBo,
			String modelValue) {
		if (null == factoryBo) {
			logger.error("factory标签业务对象信息为null，无法获取model信息");
			return null;
		} else {
			Map<String, ModelBo> modelBoMap = factoryBo.getModelBoMap();
			if (modelBoMap.containsKey(modelValue)) {
				return modelBoMap.get(modelValue);
			}
			// 如果不存在指定的modelValue，则采用*的modelValue
			else {
				if (modelBoMap.containsKey("*")) {
					return modelBoMap.get("*");
				} else {
					logger.error("无法从内存中获取factory标签与model标签的关联信息，model value = "
							+ modelValue);
					return null;
				}
			}
		}
	}
}
