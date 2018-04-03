package com.JH.dgather.frame.xmlparse.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;

import com.JH.dgather.frame.common.cmd.CMDService;
import com.JH.dgather.frame.xmlparse.OperaterHandle;
import com.JH.dgather.frame.xmlparse.beans.VarBean;
import com.JH.dgather.frame.xmlparse.beans.VarBeanStatic;
import com.JH.dgather.frame.xmlparse.bo.ForeachBo;
import com.JH.dgather.frame.xmlparse.bo.ResultBo;
import com.JH.dgather.frame.xmlparse.bo.StepBo;
import com.JH.dgather.frame.xmlparse.bo.iface.IBase;
import com.JH.dgather.frame.xmlparse.bo.iface.IForeachBase;
import com.JH.dgather.frame.xmlparse.exception.DataStorageException;
import com.JH.dgather.frame.xmlparse.exception.TagException;
import com.JH.dgather.frame.xmlparse.service.IBaseService;

/**
 * systemRules.xml中foreach标签业务处理类
 * 
 * @author zhengbin
 */
public class ForeachTagServiceImpl implements IBaseService {
	private static Logger logger = Logger
			.getLogger(ForeachTagServiceImpl.class);

	// foreach标签对应elment对象
	private Element foreachEl = null;

	public ForeachTagServiceImpl(Element element) {
		foreachEl = element;
	}

	@Override
	public IBase parse() {

		ForeachBo foreachBo = null;

		// 获取foreach标签参数
		try {
			foreachBo = getTagAttribute();
		} catch (TagException e) {
			logger.error(e.getMessage(), e);
			return null;
		}

		// 判断foreach标签必填参数是否存在，不存在则返回null表示解析xml失败
		if (!isGetRequiredAtt(foreachBo)) {
			return null;
		}

		// 获取foreach子节点信息并放入foreachBo，获取成功返回true
		if (isConnSubTag(foreachBo)) {
			return foreachBo;
		} else {
			return null;
		}
	}

	/**
	 * 从xml的foreach节点信息中获取标签属性
	 * 
	 * @return foreach标签业务对象
	 * @throws TagException
	 *             标签不允许包含属性异常
	 */
	@SuppressWarnings("rawtypes")
	private ForeachBo getTagAttribute() throws TagException {
		ForeachBo foreachBo = new ForeachBo();

		// 遍历rule节点的所有属性
		for (Iterator it = foreachEl.attributeIterator(); it.hasNext();) {
			Attribute attr = (Attribute) it.next();
			if ("var".equals(attr.getName())) {
				foreachBo.setVar(attr.getValue());
			} else if ("items".equals(attr.getName())) {
				foreachBo.setItems(attr.getValue());
			} else if ("varStatus".equals(attr.getName())) {
				foreachBo.setVarStatus(attr.getValue());
			} else if ("startWith".equals(attr.getName())) {
				foreachBo.setStartWith(attr.getValue());
			} else {
				throw new TagException("foreach 标签不允许包含属性: " + attr.getName());
			}
		}

		return foreachBo;
	}

	/**
	 * 是否从标签属性中获取到必填参数
	 * 
	 * @param foreachBo
	 *            foreach标签业务对象
	 * @return true:成功获取必填参数 false:未获取到必填参数
	 */
	private boolean isGetRequiredAtt(ForeachBo foreachBo) {
		// foreach标签的var属性为必配属性
		if (null == foreachBo.getVar() || "".equals(foreachBo.getVar())) {
			logger.error("foreach 标签中var属性值不能为空!");
			return false;
		}
		// foreach标签的item属性为必配属性
		if (null == foreachBo.getItems() || "".equals(foreachBo.getItems())) {
			logger.error("foreach 标签中item属性值不能为空!");
			return false;
		}
		// 当startWith存在时，varStatus必须存在
		if ((null != foreachBo.getStartWith() && !"".equals(foreachBo
				.getStartWith()))
				&& (null == foreachBo.getVarStatus() || "".equals(foreachBo
						.getVarStatus()))) {
			logger.error("foreach 标签中，startWith属性存在时，varStatus属性必须存在！");
			return false;
		}

		// 当varstatus存在时，startWith默认值为0，必须大于等于0
		if (!"".equals(foreachBo.getVarStatus())) {
			if (null == foreachBo.getStartWith()
					|| "".equals(foreachBo.getStartWith())) {
				foreachBo.setStartWith("0");
			} else {
				int num = 0;
				// 如果不能转成整数数字，则表示startwith不为数字
				try {
					num = Integer.parseInt(foreachBo.getStartWith());
				} catch (NumberFormatException e) {
					logger.error("foreach 标签中，startWith必须是大于等于0的整数！startWith:"
							+ foreachBo.getStartWith());
					return false;
				}
				// startWith必须大于等于0
				if (0 > num) {
					logger.error("foreach 标签中，startWith必须是大于等于0的整数！startWith:"
							+ foreachBo.getStartWith());
				}
			}
		}
		return true;
	}

	@SuppressWarnings("rawtypes")
	private boolean isConnSubTag(ForeachBo foreachBo) {
		List<IForeachBase> foreachBaseList = new ArrayList<IForeachBase>();

		// 遍历foreach节点下子节点信息
		for (Iterator foreachBaseI = foreachEl.elementIterator(); foreachBaseI
				.hasNext();) {
			Element element = (Element) foreachBaseI.next();
			// 当解析的标签tag为step时，对tag进行处理
			if ("step".equals(element.getName())) {
				IBaseService baseService = new StepTagServiceImpl(element);
				StepBo stepBo = (StepBo) baseService.parse();
				// 如果step标签解析成功，则保存信息到内存中
				if (null != stepBo) {
					foreachBaseList.add(stepBo);
				} else {
					// stepTag解析失败原因的日志信息在parse（）方法中打印此处不再重复记录日志
					return false;
				}
			} else if ("foreach".equals(element.getName())) {
				IBaseService baseService = new ForeachTagServiceImpl(element);
				ForeachBo tempForeachBo = (ForeachBo) baseService.parse();
				// 如果foreach标签解析成功，则保存信息到内存中
				if (null != tempForeachBo) {
					foreachBaseList.add(tempForeachBo);
				} else {
					// foreach解析失败原因的日志信息在parse（）方法中打印此处不再重复记录日志
					return false;
				}
			} else if ("result".equals(element.getName())) {
				// 解析result标签
				IBaseService baseService = new ResultTagServiceImpl(element);
				ResultBo resultBo = (ResultBo) baseService.parse();
				// 如果result标签解析成功，则保存信息到内存中
				if (null != resultBo) {
					foreachBaseList.add(resultBo);
				} else {
					// result解析失败原因的日志信息在parse（）方法中打印此处不再重复记录日志
					return false;
				}
			} else {
				logger.error("foreach 标签不允许包含子标签: " + element.getName());
				return false;
			}
		}

		// 成功解析出foreach标签下step子节点信息时，解析结果大于0，解析成功
		if (0 < foreachBaseList.size()) {
			foreachBo.setForeachBaseList(foreachBaseList);
			return true;
		} else {
			logger.error("解析配置文件失败，无法从配置文件中解析出froeach节点的子节点信息，" + foreachBo);
			return false;
		}
	}

	/**
	 * <code>process</code> 执行foreach操作
	 * 
	 * @param tl
	 *            : 设备telnet登录对象
	 * @param globalArgHm
	 *            : 全局参数列表
	 * @param forArgHm
	 *            : foreach迭代参数列表
	 * @param logLs
	 *            : 执行的log信息
	 * @param localVarHm
	 *            : 局部变量
	 */
	@SuppressWarnings("unchecked")
	public static boolean process(CMDService cmdService, OperaterHandle handle,
			ForeachBo foreachBo , Map<String, VarBean> localVarHm) {
		if (logger.isDebugEnabled()) {
			logger.debug("开始从数据仓库中获取foreach的迭代数据，item数据为:"
					+ foreachBo.getItems());
		}

		VarBean varBean = getItemsData(handle, foreachBo, localVarHm);
		if (null == varBean) {
			return false;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("取到迭代数据, 数据类型为: " + varBean.getType());
		}

		if (!VarBeanStatic.TYPE_LIST.equals(varBean.getType())) {
			logger.error("foreach标签中items指向的数据类型必须为List!, 当前的类型是:"
					+ varBean.getType());
			return false;
		}

		List<VarBean> vbLs = (List<VarBean>) varBean.getObject();
		if (logger.isDebugEnabled()) {
			logger.debug("开始执行foreach，共循环 " + vbLs.size() + " 次, var属性为:"
					+ foreachBo.getVar());
		}

		for (int i = 0; i < vbLs.size(); i++) {
			VarBean localVar = vbLs.get(i);
			if (logger.isDebugEnabled()) {
				logger.debug("执行第 " + (i + 1) + "次循环, var值为:"
						+ foreachBo.getVar() + "，item: " + localVar.getType()
						+ ",var对应的数据：" + localVar.getObject().toString());
			}
			HashMap<String, VarBean> localHm = new HashMap<String, VarBean>();
			// 为防止foreach嵌套使用时，定义同时名称的var
			if (null != localVarHm) {
				if (localVarHm.containsKey(foreachBo.getVar())) {
					logger.error("当前foreach标签中var属性值与外层的foreach标签中的var属性值相同！");
					return false;
				} else {
					localHm.putAll(localVarHm);

				}
			}
			// 将迭代参数存到局部参数中
			logger.debug("添加一个局部变量, key: " + foreachBo.getVar());
			localHm.put(foreachBo.getVar(), localVar);
			// 如果有设置循环索引，也存到局部参数中
			if (!"".equals(foreachBo.getVarStatus())) {
				int mId = Integer.parseInt(foreachBo.getStartWith()) + i;
				VarBean vsVB = new VarBean(VarBeanStatic.TYPE_STRING,
						Integer.toString(mId));
				logger.debug("添加一个局部变量, key: " + foreachBo.getVarStatus()
						+ ", 值为String类型，数据是: " + mId);
				localHm.put(foreachBo.getVarStatus(), vsVB);
			}
			for (IForeachBase tag : foreachBo.getForeachBaseList()) {
				if ("step".equals(tag.getTagNameByObject())) {

					StepBo stepBo = (StepBo) tag;
					if (!StepTagServiceImpl.process(cmdService, handle, stepBo,
							localVarHm)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * <code>getItemsData</code> 提取参数值
	 * 
	 * @param handle
	 * @param localVarHm
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static VarBean getItemsData(OperaterHandle handle,
			ForeachBo foreachBo, Map<String, VarBean> localVarHm) {
		VarBean varBean = null;
		// 判断数据item是否符合${xxx.xxx} 或${xxx}结果
		if (foreachBo.getItems().matches("\\$\\{\\w+(\\.\\w+)?}$")) {
			Pattern pa = Pattern.compile("\\$\\{(\\w+)(?:\\.(\\w+))?\\}");
			Matcher ma = pa.matcher(foreachBo.getItems());

			// 符合则获取捕获数据，并通过捕获数据获取对应数据
			if (ma.find()) {
				String mainKey = ma.group(1);
				String subKey = ma.group(2);

				// 先在局部参数中查找
				if (null != localVarHm && localVarHm.containsKey(mainKey)) {
					if (logger.isDebugEnabled()) {
						logger.debug("在局部数据变量中找到主键为:" + mainKey + "的数据!");
					}
					VarBean vb = localVarHm.get(mainKey);

					// 当subKey为null时，局部参数中的数据类型必须为String
					if (null == subKey) {
						varBean = vb;
					} else {
						if (VarBeanStatic.TYPE_HASHMAP.equals(vb.getType())) {
							Map<String, VarBean> thm = (Map<String, VarBean>) vb
									.getObject();
							if (thm.containsKey(subKey)) {
								varBean = thm.get(subKey);
								if (logger.isDebugEnabled()) {
									logger.debug(">>> " + varBean.getType());
								}
							} else {
								logger.error("局部数据中，主键为:" + mainKey
										+ "的HashMap数据集合中，不存在键值为: " + subKey
										+ "的数据!");
								return null;
							}
						} else {
							logger.error("局部数据中，主键为：" + mainKey
									+ "的数据，不是HashMap类型，因为要从中取键值为: " + subKey
									+ "的数据!, 当前数据类型为: " + vb.getType());
							return null;
						}
					}
				}

				// 如果在局部参数中未找到对应数据，varBean=null，则在全局参数中查找
				if (null == varBean) {
					try {
						varBean = handle.getData(mainKey, subKey);
					} catch (DataStorageException e) {
						logger.error("无法从全局数据中，查找主键为：" + mainKey + "子健为: "
								+ subKey + "的数据!)", e);
						return null;
					}
					if (!VarBeanStatic.TYPE_HASHMAP.equals(varBean.getType())
							&& !VarBeanStatic.TYPE_LIST.equals(varBean
									.getType())) {
						logger.error("foreach标签中items指向的VarBean数据的类型不是HashMap或List!");
						return null;
					}
				}
			}
		} else {
			logger.error("foreach标签中items的内容不符合${xxx} 或 ${xxx.xxx}规则!");
			return null;
		}
		return varBean;
	}
}
