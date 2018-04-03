package com.JH.dgather.frame.xmlparse;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.JH.dgather.PropertiesHandle;
import com.JH.dgather.frame.common.bean.DeviceInfoBean;
import com.JH.dgather.frame.xmlparse.bo.FactoryBo;
import com.JH.dgather.frame.xmlparse.bo.IcsmodelBo;
import com.JH.dgather.frame.xmlparse.bo.ModelBo;
import com.JH.dgather.frame.xmlparse.bo.RuleIdBo;
import com.JH.dgather.frame.xmlparse.bo.VersionBo;
import com.JH.dgather.frame.xmlparse.service.IBaseService;
import com.JH.dgather.frame.xmlparse.service.impl.FactoryTagServiceImpl;
import com.JH.dgather.frame.xmlparse.service.impl.IcsmodelTagServiceImpl;
import com.JH.dgather.frame.xmlparse.service.impl.ModelTagServiceImpl;

/**
 * FactoryAndRuleId配置文件解析处理类
 * 
 * @author zhengbin
 * @version 1.0 2013/9/17
 * 
 */
public class FactoryAndRuleIdParse extends XmlParse {
	private static Logger logger = Logger
			.getLogger(FactoryAndRuleIdParse.class);

	/**
	 * 用于存储FactoryAndRuleId.xml解析信息
	 */
	private static final Map<String, IcsmodelBo> FACTORY_AND_RULEID = new HashMap<String, IcsmodelBo>();

	@Override
	public boolean initXmlParse(String propertiesName) {
		// "factoryAndRules","syslogfacAndRules"
		String fileDir = PropertiesHandle.getResuouceInfo(propertiesName);
		return cacheXMLLoad(fileDir);
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected boolean parseXml(Document document) {
		// 获取root根节点
		Element rootElm = document.getRootElement();

		// 遍历根节点信息
		for (Iterator it = rootElm.elementIterator(); it.hasNext();) {
			Element element = (Element) it.next();
			// 当解析的标签tag为icsmodel时，对tag进行处理
			if ("icsmodel".equals(element.getName())) {
				IBaseService baseService = new IcsmodelTagServiceImpl(element);
				IcsmodelBo icsmodelBo = (IcsmodelBo) baseService.parse();

				// 如果icsmodel解析成功，则保存信息到内存中
				if (null != icsmodelBo) {
					String key = icsmodelBo.getGatherclass();
					// 如果key已经存在相同配置，则报错
					if (null == key || FACTORY_AND_RULEID.containsKey(key)) {
						logger.error("获取icsmodel信息出现重复配置，请确认配置文件的正确性，icsmodel gatherclass = "
								+ key);
						return false;
					} else {
						FACTORY_AND_RULEID.put(key, icsmodelBo);
					}
				} else {
					// icsmodel解析失败原因的日志信息在parse（）方法中打印此处不再重复记录日志
					return false;
				}
			} else {
				logger.error("root 不允许包含标签：" + element.getName());
				return false;
			}
		}

		// 成功解析出icsmodel子节点信息时，FACTORY_AND_RULEID大于0，表示配置解析成功
		if (0 < FACTORY_AND_RULEID.size()) {
			return true;
		} else {
			logger.error("解析配置文件失败，无法从配置文件中解析出icsmodel节点信息");
			return false;
		}
	}

	/**
	 * 通过gatherclass与设备信息获取对应的规则信息
	 * 
	 * @param deviceInfoBean
	 *            设备信息
	 * @param gatherClass
	 *            业务标识
	 * @return 规则信息 如果返回null则表示获取规则失败
	 */
	public static Map<String, RuleIdBo> getRuleIdHm(
			DeviceInfoBean deviceInfoBean, int gatherClass) {
		String gcStr = Integer.toString(gatherClass);
		if (null == deviceInfoBean) {
			logger.error("设备信息为空，无法通过设备信息获取icsmodel标签信息!");
			return null;
		}
		// 判断是否存在key
		if (FACTORY_AND_RULEID.containsKey(gcStr)) {
			// 从FACTORY_AND_RULEID中通过gatherclass获取icsmodel标签业务对象信息
			IcsmodelBo icsmodelBo = FACTORY_AND_RULEID.get(gcStr);

			FactoryBo factoryBo = IcsmodelTagServiceImpl.getFactoryInfoByValue(
					icsmodelBo, Integer.toString(deviceInfoBean.getFactory()));
			// 如果未找到factory标签信息，则返回null
			if (null == factoryBo) {
				return null;
			}

			ModelBo modelBo = FactoryTagServiceImpl.getModelInfoByValue(
					factoryBo, deviceInfoBean.getModelName());
			// 如果未找到model标签信息，则返回null
			if (null == modelBo) {
				return null;
			}

			VersionBo versionBo = ModelTagServiceImpl.getVersionInfoByValue(
					modelBo, deviceInfoBean.getDeviceVersion());
			// 如果未找到version标签信息，则返回null
			if (null == versionBo) {
				return null;
			} else {
				return versionBo.getRuleIdBoMap();
			}
		} else {
			logger.error("未找到gatherclass属性为：" + gatherClass + " 的icsmode标签信息!");
			return null;
		}
	}

	public static Map<String, IcsmodelBo> getFactoryAndRuleid() {
		return FACTORY_AND_RULEID;
	}
}
