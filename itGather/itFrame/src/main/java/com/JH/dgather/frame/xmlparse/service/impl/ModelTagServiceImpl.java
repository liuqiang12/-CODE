package com.JH.dgather.frame.xmlparse.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;

import com.JH.dgather.frame.xmlparse.bo.ModelBo;
import com.JH.dgather.frame.xmlparse.bo.VersionBo;
import com.JH.dgather.frame.xmlparse.bo.iface.IBase;
import com.JH.dgather.frame.xmlparse.exception.TagException;
import com.JH.dgather.frame.xmlparse.service.IBaseService;

public class ModelTagServiceImpl implements IBaseService {
	private static Logger logger = Logger.getLogger(ModelTagServiceImpl.class);

	// model对应的element对象
	private Element modelEl = null;

	public ModelTagServiceImpl(Element element) {
		modelEl = element;
	}

	public IBase parse() {

		ModelBo modelBo = null;

		// 获取model标签参数信息，获取失败返回null
		try {
			modelBo = getTagAttribute();
		} catch (TagException e) {
			logger.error(e.getMessage(), e);
			return null;
		}

		// 根据model规则对配置的属性进行检查
		if (!checkTagAttribute(modelBo)) {
			return null;
		}

		// 关联model与子标签version，如果成功返回true
		if (connSubTag(modelBo)) {
			return modelBo;
		} else {
			return null;
		}
	}

	/**
	 * 从xml的model节点信息中获取标签属性
	 * 
	 * @return model标签业务对象
	 * @throws TagException
	 *             标签不允许包含属性异常
	 */
	@SuppressWarnings("rawtypes")
	private ModelBo getTagAttribute() throws TagException {
		ModelBo modelBo = new ModelBo();

		// 遍历model节点的所有属性
		for (Iterator it = modelEl.attributeIterator(); it.hasNext();) {
			Attribute attribute = (Attribute) it.next();
			if ("value".equals(attribute.getName())) {
				modelBo.setValue(attribute.getValue());
			} else {
				throw new TagException("model 标签不允许包含属性: "
						+ attribute.getName());
			}
		}
		return modelBo;
	}

	/**
	 * 检查标签中的参数配置是否符合规则
	 * 
	 * @param modelBo
	 *            model标签业务对象
	 * @return true:检查成功 false:检查失败
	 */
	private boolean checkTagAttribute(ModelBo modelBo) {
		// value为必配属性
		if (null == modelBo.getValue() || "".equals(modelBo.getValue())) {
			logger.error("model 标签中value属性值不能为空!");
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
	private boolean connSubTag(ModelBo modelBo) {
		Map<String, VersionBo> versionBoMap = new HashMap<String, VersionBo>();

		// 遍历model节点下子节点信息
		for (Iterator it = modelEl.elementIterator(); it.hasNext();) {
			Element element = (Element) it.next();
			// 当解析的标签为version时，进行处理
			if ("version".equals(element.getName())) {
				IBaseService baseService = new VersionTagServiceImpl(element);
				VersionBo versionBo = (VersionBo) baseService.parse();
				// 如果version标签解析成功，则保存信息到内存
				if (null != versionBo) {
					String key = versionBo.getValue();
					// 如果model标签下的version标签存在重复的信息，则提示配置错误，解析失败
					if (null == key || versionBoMap.containsKey(key)) {
						logger.error("model value = "
								+ modelBo.getValue()
								+ "，获取version信息出现重复配置，请确认配置文件的正确性，version value = "
								+ key);
						return false;

					} else {
						versionBoMap.put(key, versionBo);
					}
				}
				// version标签解析失败
				else {
					return false;
				}
			} else {
				logger.error("model value = " + modelBo.getValue()
						+ "，model 标签下不允许包含子标签: " + element.getName());
			}
		}

		// 当model标签下存在version标签时，versionBoMap大于0，解析成功
		if (0 < versionBoMap.size()) {
			modelBo.setVersionBoMap(versionBoMap);
			return true;
		} else {
			logger.error("解析配置文件失败，无法从配置文件中解析出version节点信息，" + modelBo);
			return false;
		}
	}

	/**
	 * 通过versionvalue找到model标签中的version标签信息
	 * 
	 * @param modelBo
	 *            model标签业务对象
	 * @param versionValue
	 *            version唯一标识value
	 * @return version标签业务对象
	 */
	public static VersionBo getVersionInfoByValue(ModelBo modelBo,
			String versionValue) {
		if (null == modelBo) {
			logger.error("model标签业务对象信息为null，无法获取version信息");
			return null;
		} else {
			Map<String, VersionBo> versionBoMap = modelBo.getVersionBoMap();
			if (versionBoMap.containsKey(versionValue)) {
				return versionBoMap.get(versionValue);
			}
			// 如果不存在指定的version，先用正则表达式获取“.”后面的数据
			else {
				// 如果存在*.*的配置则启用该标签为默认标签
				if (versionBoMap.containsKey("*.*")) {
					return versionBoMap.get("*.*");
				} else {
					logger.error("无法从内存中获取model标签与version标签的关联信息，version value = "
							+ versionValue);
					return null;
				}
			}
		}
	}
}
