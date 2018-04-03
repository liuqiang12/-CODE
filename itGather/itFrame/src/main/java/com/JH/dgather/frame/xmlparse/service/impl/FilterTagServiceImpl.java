package com.JH.dgather.frame.xmlparse.service.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;

import com.JH.dgather.frame.xmlparse.OperaterHandle;
import com.JH.dgather.frame.xmlparse.beans.VarBean;
import com.JH.dgather.frame.xmlparse.beans.VarBeanStatic;
import com.JH.dgather.frame.xmlparse.bo.FilterBo;
import com.JH.dgather.frame.xmlparse.bo.iface.IBase;
import com.JH.dgather.frame.xmlparse.exception.DataStorageException;
import com.JH.dgather.frame.xmlparse.exception.TagException;
import com.JH.dgather.frame.xmlparse.service.IBaseService;

/**
 * systemRules.xml中filter标签业务处理类
 * 
 * @author zhengbin
 * 
 */
public class FilterTagServiceImpl implements IBaseService {
	private static Logger logger = Logger.getLogger(FilterTagServiceImpl.class);

	// filter标签对应的element对象
	private Element filterEl = null;

	public FilterTagServiceImpl(Element element) {
		filterEl = element;
	}

	@Override
	public IBase parse() {
		FilterBo filterBo = null;

		// 获取filter标签参数
		try {
			filterBo = getTagInfo();
		} catch (TagException e) {
			logger.error(e.getMessage(), e);
			return null;
		}

		// 判断必填参数是否存在，存在则直接返回filter业务对象
		if (checkTagInfo(filterBo)) {
			return filterBo;
		} else {
			return null;
		}
	}

	/**
	 * 从xml的filter节点信息中获取标签信息
	 * 
	 * @return filter标签业务对象
	 * @throws TagException
	 *             标签不允许包含属性异常
	 */
	@SuppressWarnings("rawtypes")
	private FilterBo getTagInfo() throws TagException {
		FilterBo filterBo = new FilterBo();

		// 遍历filter节点的所有属性
		for (Iterator it = filterEl.attributeIterator(); it.hasNext();) {
			Attribute attr = (Attribute) it.next();
			if ("bufferName".equals(attr.getName())) {
				filterBo.setBufferName(attr.getValue());
			} else if ("connector".equals(attr.getName())) {
				filterBo.setConnector(attr.getValue());
			} else {
				throw new TagException("filter 标签不允许包含属性: " + attr.getName());
			}
		}

		filterBo.setFilterValue(filterEl.getText());
		return filterBo;
	}

	/**
	 * 检查标签中的参数配置是否符合规则
	 * 
	 * @param filterBo
	 *            filter标签业务对象
	 * @return true: 检查成功 false: 检查失败
	 */
	private boolean checkTagInfo(FilterBo filterBo) {
		// 判断filterBo标签buffername是否为null或""
		if (null == filterBo.getBufferName()
				|| "".equals(filterBo.getBufferName())) {
			logger.error("filter 标签的bufferName不能为空。");
			return false;
		}

		// 判断filterBo标签connector是否为null或""
		if (null == filterBo.getConnector()
				|| "".equals(filterBo.getConnector())) {
			logger.error("filter 标签的connector不能为空。");
			return false;
		}

		// 判断filterBo标签内容是否为null或""
		if (null == filterBo.getFilterValue()
				|| "".equals(filterBo.getFilterValue())) {
			logger.error("filter 标签的内容不能为空。");
			return false;
		}

		return true;
	}

	/**
	 * 执行filter标签
	 * 
	 * @param handle
	 *            全局参数
	 * @param filterBo
	 *            filter业务对象
	 * @param result
	 *            上一级回显
	 * @param localVarHm
	 *            临时参数
	 * @return 执行结果true：执行成功，false：执行失败
	 */
	@SuppressWarnings("unchecked")
	public static boolean process(OperaterHandle handle, FilterBo filterBo,
			String result, Map<String, VarBean> localVarHm) {
		if (null == result || "".equals(result)) {
			logger.error("filter 标签需要验证的回显内容为null或空！");
			return false;
		}

		// 如果filter中存在${}的，则匹配替换临时参数中的数据
		Pattern p = Pattern.compile("\\$\\{(.+?)\\}");
		Matcher m = p.matcher(filterBo.getFilterValue());
		StringBuffer sb = new StringBuffer();
		String filterV = null;
		if (m.find()) {
			// 重置匹配器
			m = m.reset();
			// 当filter内容匹配\\$\\{.+?\\}
			while (m.find()) {
				String key = m.group(1);
				String rp = null;

				Pattern pa = Pattern.compile("\\$\\{(\\w+)(?:\\.(\\w+))?\\}");
				Matcher ma = pa.matcher(key);

				// 符合则获取捕获数据，并通过捕获数据获取对应数据
				if (ma.find()) {
					String mainKey = ma.group(1);
					String subKey = ma.group(2);

					// 优先在局部参数中查找（是否需要考虑局部参数中与全局参数中存在相同key值的数据?）
					if (null != localVarHm && localVarHm.containsKey(mainKey)) {
						VarBean varBean = localVarHm.get(mainKey);

						// 当subKey为null时，局部参数中的数据类型必须为String
						if (null == subKey) {
							if (null != varBean
									&& VarBeanStatic.TYPE_STRING.equals(varBean
											.getType())) {
								rp = (String) varBean.getObject();
							} else {
								StringBuffer error = new StringBuffer();
								error.append("局部数据仓库中，键值为: ");
								error.append(mainKey);
								error.append("的数据类型必须是String类型, 当前类型为: ");
								error.append(varBean.getType());
								error.append("。filter标签指令执行失败。");
								logger.error(error.toString());
								return false;
							}
						}
						// 当subkey不为null时，局部参数中的数据类型必须为HashMap
						else {
							if (null != varBean
									&& VarBeanStatic.TYPE_HASHMAP
											.equals(varBean.getType())) {
								Map<String, VarBean> hm = (Map<String, VarBean>) varBean
										.getObject();
								// 如果Map中存在对应的subkey，则获取Map中的数据
								if (null != hm && hm.containsKey(subKey)) {
									VarBean obj = hm.get(subKey);
									// 判断hashmap中的数据类型是否存在且为String类型，不是则报错
									if (null != obj
											&& VarBeanStatic.TYPE_STRING
													.equals(obj.getType())) {
										rp = (String) obj.getObject();
									} else {
										StringBuffer error = new StringBuffer();
										error.append("局部数据仓库中，键值为: ");
										error.append(subKey);
										error.append("的数据不是String类型！当前类型为:");
										error.append(obj.getClass().getName());
										logger.error(error.toString());
										return false;
									}
								} else {
									StringBuffer error = new StringBuffer();
									error.append("局部数据仓库中不存在键值为： ");
									error.append(subKey);
									error.append("的数据!");
									logger.error(error.toString());
									return false;
								}

							} else {
								StringBuffer error = new StringBuffer();
								error.append("局部数据仓库中，键值为: ");
								error.append(mainKey);
								error.append("的数据须为HashMap，因为有通过键值");
								error.append(subKey);
								error.append("提取数据！当前类型为：");
								error.append(varBean.getType());
								logger.error(error.toString());
								return false;
							}
						}
					}
					// 如果在局部参数中未找到对应数据，rp=null，则在全局参数中查找
					if (null == rp) {
						VarBean gvb = null;
						try {
							gvb = handle.getData(mainKey, subKey);
						} catch (DataStorageException e) {
							logger.error("从数据仓库中获取filter指令对应数据异常", e);
							return false;
						}

						// 数据仓库中存在数据，且数据类型为String,则获取数据
						if (null != gvb
								&& VarBeanStatic.TYPE_STRING.equals(gvb
										.getType())) {
							rp = (String) gvb.getObject();
						} else {
							StringBuffer error = new StringBuffer();
							error.append("主键值为：");
							error.append(mainKey);
							error.append("的全局数据列表中，键值为:");
							error.append(subKey);
							error.append("的数据类型必须为String!");
							logger.error(error.toString());
							return false;
						}
					}
				} else {
					// 日志信息在调用方法出打印
					StringBuffer error = new StringBuffer();
					error.append("参数不符合 ${xxx} 或 ${xxx.xxx} 的规则，参数信息为： ");
					error.append(key);
					logger.error(error.toString());
					return false;
				}
				m.appendReplacement(sb, rp);
			}
			m.appendTail(sb);
			filterV = sb.toString();
		} else {
			filterV = filterBo.getFilterValue();
		}

		// 验证是否符合匹配要求
		Pattern pa = Pattern.compile(filterV);
		Matcher ma = pa.matcher(result);
		if (ma.find()) {
			VarBean varBean = null;
			// 先从全局参数中查找是否存在数据
			try {
				varBean = handle.getData("filter", filterBo.getBufferName());
			} catch (DataStorageException e) {
				logger.error(
						"从全局参数中获取filter标签保存的验证通过数据异常，filter: "
								+ filterBo.getBufferName(), e);
				return false;
			}
			VarBean tempVarBean = null;
			// 如果全局参数中已经存在匹配的数据，则将新匹配的数据用connector拼接保存
			if (null != varBean) {
				String bufferStr = (String) varBean.getObject();
				StringBuffer conStr = new StringBuffer();
				conStr.append(bufferStr);
				conStr.append(filterBo.getConnector());
				conStr.append(result);
				tempVarBean = new VarBean(VarBeanStatic.TYPE_STRING,
						conStr.toString());
				return handle.replaceData("filter", filterBo.getBufferName(), tempVarBean);
			} else {
				tempVarBean = new VarBean(VarBeanStatic.TYPE_STRING, result);
				try {
					handle.addData("filter", filterBo.getBufferName(), tempVarBean);
				} catch (DataStorageException e) {
					logger.error(
							"向全局参数中添加filter标签验证通过的数据异常，buffer: "
									+ filterBo.getBufferName(), e);
					return false;
				}
			}
		}
		return false;
	}
}
