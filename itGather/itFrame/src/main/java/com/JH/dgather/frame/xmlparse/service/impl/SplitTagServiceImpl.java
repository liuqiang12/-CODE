package com.JH.dgather.frame.xmlparse.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;

import com.JH.dgather.frame.common.exception.TelnetException;
import com.JH.dgather.frame.util.StringUtil;
import com.JH.dgather.frame.xmlparse.OperaterHandle;
import com.JH.dgather.frame.xmlparse.beans.VarBean;
import com.JH.dgather.frame.xmlparse.beans.VarBeanStatic;
import com.JH.dgather.frame.xmlparse.bo.FilterBo;
import com.JH.dgather.frame.xmlparse.bo.MatcherBo;
import com.JH.dgather.frame.xmlparse.bo.ReplaceBo;
import com.JH.dgather.frame.xmlparse.bo.ResultBo;
import com.JH.dgather.frame.xmlparse.bo.SplitBo;
import com.JH.dgather.frame.xmlparse.bo.ValidateBo;
import com.JH.dgather.frame.xmlparse.bo.iface.IBase;
import com.JH.dgather.frame.xmlparse.bo.iface.ISplitBase;
import com.JH.dgather.frame.xmlparse.exception.DataStorageException;
import com.JH.dgather.frame.xmlparse.exception.TagException;
import com.JH.dgather.frame.xmlparse.service.IBaseService;

public class SplitTagServiceImpl implements IBaseService {
	private static Logger logger = Logger.getLogger(SplitTagServiceImpl.class);

	// split标签的element对象
	private Element splitEl = null;

	public SplitTagServiceImpl(Element element) {
		splitEl = element;
	}

	@Override
	public IBase parse() {

		SplitBo splitBo = null;

		// 获取split标签参数
		try {
			splitBo = getTagAttribute();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}

		// 判断split标签的必填参数是否存在
		if (!isGetRequiredAtt(splitBo)) {
			return null;
		}

		if (isConnSubTag(splitBo)) {
			return splitBo;
		} else {
			return null;
		}
	}

	/**
	 * 从xml的split节点信息中获取标签属性
	 * 
	 * @return split标签业务对象
	 * @throws TagException
	 *             标签不允许包含属性异常
	 */
	@SuppressWarnings("rawtypes")
	private SplitBo getTagAttribute() throws TagException {
		SplitBo splitBo = new SplitBo();

		// 遍历split节点的属性
		for (Iterator it = splitEl.attributeIterator(); it.hasNext();) {
			Attribute attr = (Attribute) it.next();
			if ("value".equals(attr.getName())) {
				splitBo.setValue(attr.getValue());
			} else if ("regex".equals(attr.getName())) {
				splitBo.setRegex(attr.getValue());
			} else if ("stat".equals(attr.getName())) {
				splitBo.setStat(attr.getValue());
			} else if ("fix".equals(attr.getName())) {
				// fix和ignore数据不能同时存在
				if (null == splitBo.getIgnore()) {
					List<Integer> tempFix = getFixAtt(attr.getValue());
					// 解析fix结果大于0则存在有效数据，保存到split业务对象中
					if (0 < tempFix.size()) {
						splitBo.setFix(tempFix);
					} else {
						throw new TagException(
								"split 标签中fix属性的数据无法获取有效信息 fix = "
										+ attr.getValue());
					}
				} else {
					throw new TagException("split 标签中, fix与ignore属性不能同时出现！");
				}
			} else if ("ignore".equals(attr.getName())) {
				// fix和ignore数据不能同时存在
				if (null == splitBo.getFix()) {
					List<Integer> tempIgnore = getIgnoreAtt(attr.getValue());
					if (0 < tempIgnore.size()) {
						splitBo.setIgnore(tempIgnore);
					} else {
						throw new TagException(
								"split 标签中ignore属性的数据无法获取有效信息 ignore = "
										+ attr.getValue());
					}
				} else {
					throw new TagException("split 标签中, fix与ignore属性不能同时出现！");
				}
			} else if ("result".equals(attr.getName())) {
				splitBo.setResultKey(attr.getValue());
			} else {
				throw new TagException("split 标签不允许包含属性: " + attr.getName());
			}
		}
		return splitBo;
	}

	/**
	 * 获取fix参数信息
	 * 
	 * @param fixStr
	 *            fix源信息
	 * @return fix解析处理结果集
	 * @throws TagException
	 *             fix处理异常
	 */
	private List<Integer> getFixAtt(String fixStr) throws TagException {
		List<Integer> fix = new ArrayList<Integer>();

		// 防止配置fix中未包含;符号
		if (!fixStr.endsWith(";")) {
			fixStr += ";";
		}
		// 根据;符号拆分fixString
		String[] fixSplit = fixStr.split(";");
		for (String str : fixSplit) {
			// 不处理“”
			if ("".equals(str)) {
				continue;
			}
			// 如果处理数据包含last字符串则用数字0代替
			else if ("last".equals(str)) {
				fix.add(0);
			}
			// 如果处理数据为数字需要处理
			else if (StringUtil.isNumberString(str)) {
				int number = Integer.parseInt(str);
				// 如果fix解析数据必须大于1
				if (0 > number) {
					throw new TagException("split 标签中fix属性的数据必须为大于1的整数，当前数据为: "
							+ number);
				} else {
					fix.add(number);
				}
			} else {
				throw new TagException("split 标签中fix属性的数据并不是数字，当前数据为: " + str);
			}
		}
		return fix;
	}

	/**
	 * 获取ignore参数信息
	 * 
	 * @param ignoreStr
	 *            ignore源信息
	 * @return ignore解析处理结果集
	 * @throws TagException
	 *             ignore处理异常
	 */
	private List<Integer> getIgnoreAtt(String ignoreStr) throws TagException {
		List<Integer> ignore = new ArrayList<Integer>();

		// 防止配置ignore中未包含;符号
		if (!ignoreStr.endsWith(";")) {
			ignoreStr += ";";
		}
		// 根据;符号拆分ignoreStr
		String[] ignoreSplit = ignoreStr.split(";");
		for (String sr : ignoreSplit) {
			if ("".equals(sr)) {
				continue;
			} else if (StringUtil.isNumberString(sr)) {
				int iS = Integer.parseInt(sr);
				if (0 >= iS) {
					throw new TagException(
							"split 标签中ignore属性的数据必须为大于1的整数，当前数据为: " + iS);
				} else
					ignore.add(iS);
			} else {
				throw new TagException("split 标签中ignore属性的数据并不是数字，当前数据为: " + sr);
			}
		}
		return ignore;
	}

	/**
	 * 是否从标签属性中获取到必填参数
	 * 
	 * @param splitBo
	 *            split标签业务对象
	 * @return true:成功获取必填参数 false:未获取到必填参数
	 */
	private boolean isGetRequiredAtt(SplitBo splitBo) {
		// 必填参数验证
		// 当fix存在或者ignore存在时，result属性必须存在
		if (((null != splitBo.getFix() && 0 < splitBo.getFix().size()) || (null != splitBo
				.getIgnore() && 0 < splitBo.getIgnore().size()))
				&& (null == splitBo.getResultKey() || "".equals(splitBo
						.getResultKey()))) {
			logger.error("split 标签中，fix属性或ignore属性存在时，result属性必须存在!");
			return false;
		}

		// value属性为必配属性，不得为空
		if (null == splitBo.getValue() || "".equals(splitBo.getValue())) {
			logger.error("split 标签中value属性不得为空!");
			return false;
		}
		// regex属性为必配属性，不得为空
		if (null == splitBo.getRegex() || "".equals(splitBo.getRegex())) {
			logger.error("split 标签中regex属性不得为空!");
			return false;
		}
		return true;
	}

	/**
	 * 是否关联子标签
	 * 
	 * @param splitbo
	 *            split标签业务对象（保存父子标签关联）
	 * @return true：关联成功 false：关联失败
	 */
	@SuppressWarnings("rawtypes")
	private boolean isConnSubTag(SplitBo splitBo) {
		List<ISplitBase> splitBaseList = new ArrayList<ISplitBase>();
		boolean isMatcher = false;
		// 遍历split节点下子节点信息
		for (Iterator splitI = splitEl.elementIterator(); splitI.hasNext();) {
			Element element = (Element) splitI.next();
			if (!isMatcher) {
				if ("split".equals(element.getName())) {
					// 如果存在相同的split标签，则解析失败
					if (splitBo.isExistSplit()) {
						logger.error("split标签不能在split标签中重复出现。");
						return false;
					}
					// 解析split标签
					IBaseService baseService = new SplitTagServiceImpl(element);
					SplitBo tempSplitBo = (SplitBo) baseService.parse();
					// 如果split标签解析成功，则保存信息到内存
					if (null != tempSplitBo) {
						splitBaseList.add(tempSplitBo);
						splitBo.setExistSplit(true);
					} else {
						return false;
					}
				} else if ("validate".equals(element.getName())) {
					// 如果存在相同的validate标签，则解析失败
					if (splitBo.isExistValidate()) {
						logger.error("validate标签不能在split标签中重复出现。");
						return false;
					}

					// 解析validate标签
					IBaseService baseService = new ValidateTagServiceImpl(
							element);
					ValidateBo validateBo = (ValidateBo) baseService.parse();
					// 如果validate标签解析成功，则保存信息到内存
					if (null != validateBo) {
						splitBaseList.add(validateBo);
						splitBo.setExistValidate(true);
					} else {
						return false;
					}
				} else if ("filter".equals(element.getName())) {
					// 解析filter标签
					IBaseService baseService = new FilterTagServiceImpl(element);
					FilterBo filterBo = (FilterBo) baseService.parse();
					// 如果validate标签解析成功，则保存信息到内存
					if (null != filterBo) {
						splitBaseList.add(filterBo);
					} else {
						return false;
					}
				} else if ("matcher".equals(element.getName())) {
					isMatcher = true;
					// 如果存在相同的matcher标签，则解析失败
					if (splitBo.isExistMatcher()) {
						logger.error("matcher标签不能在split标签中重复出现。");
						return false;
					}
					// 解析matcher标签
					IBaseService baseService = new MatcherTagServiceImpl(
							element);
					MatcherBo matcherBo = (MatcherBo) baseService.parse();
					// 如果matcher标签解析成功，则保存信息到内存中
					if (null != matcherBo) {
						splitBaseList.add(matcherBo);
						// 设置matcher标签已存在标记
						splitBo.setExistMatcher(true);
					} else {
						// matcherTag解析失败原因的日志信息在parse（）方法中打印此处不再重复记录日志
						return false;
					}
				} else if ("replace".equals(element.getName())) {
					// 如果存在相同的replace标签，则解析失败
					if (splitBo.isExistReplace()) {
						logger.error("replace标签不能在split标签中重复出现。");
						return false;
					}
					// 解析replace标签
					IBaseService baseService = new ReplaceTagServiceImpl(
							element);
					ReplaceBo replaceBo = (ReplaceBo) baseService.parse();
					// 如果replace标签解析成功，则保存信息到内存中
					if (null != replaceBo) {
						splitBaseList.add(replaceBo);
						// 设置replace标签已存在标记
						splitBo.setExistReplace(true);
					} else {
						// replace解析失败原因的日志信息在parse（）方法中打印此处不再重复记录日志
						return false;
					}
				} else if ("result".equals(element.getName())) {
					// 解析result标签
					IBaseService baseService = new ResultTagServiceImpl(element);
					ResultBo resultBo = (ResultBo) baseService.parse();
					// 如果result标签解析成功，则保存信息到内存中
					if (null != resultBo) {
						splitBaseList.add(resultBo);
					} else {
						// result解析失败原因的日志信息在parse（）方法中打印此处不再重复记录日志
						return false;
					}
				} else {
					logger.error("split 标签不允许包含子标签: " + element.getName());
				}
			} else {
				logger.error("split 标签子标签中matcher标签后不得包含其它子标签，当前子标签为： "
						+ element.getName());
			}
		}
		// 成功解析出split标签下子节点信息时，解析结果大于0，解析成功
		if (0 < splitBaseList.size()) {
			splitBo.setSplitBaseList(splitBaseList);
			return true;
		} else {
			// 由于split标签可以没有子标签，因此打印警告，继续执行
			logger.warn("无法从配置文件中解析出split的子节点信息，" + splitBo);
			return true;
		}
	}

	/**
	 * <code>process</code> 执行split标签
	 * 
	 * @param handle
	 * @param result
	 *            上一级回显信息
	 * @param logLs
	 * @return none
	 * @throws DataStorageException
	 * @throws TelnetException
	 * @throws IOException
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	public static boolean process(OperaterHandle handle, SplitBo splitBo,
			Map<String, VarBean> localVarHm) {
		if (logger.isInfoEnabled()) {
			logger.info("开始从数据仓库中获取split的数据。" + splitBo);
		}
		VarBean varBean = getItemsData(handle, splitBo, localVarHm);

		if (null == varBean
				|| !VarBeanStatic.TYPE_STRING.equals(varBean.getType())) {
			return false;
		}

		String result = (String) varBean.getObject();

		if (null == result || "".equals(result.trim())) {
			logger.error("需要拆分的回显内容为空！");
			return false;
		}
		result = result.trim();

		if (logger.isDebugEnabled()) {
			logger.debug("需要拆分的内容为:" + result);
			logger.debug("~~~~拆分表达式regex:" + splitBo.getRegex());
		}

		// 先进行拆分
		String[] splitStr = result.split(splitBo.getRegex());
		List<String> splitLs = null;

		if (null != splitBo.getFix() && 0 < splitBo.getFix().size()) {
			splitLs = doFix(splitBo, splitStr);
		} else if (null != splitBo.getIgnore()
				&& 0 < splitBo.getIgnore().size()) {
			splitLs = doIgnore(splitBo, splitStr);
		} else {
			splitLs = transList(splitStr);
		}

		if (logger.isInfoEnabled()) {
			logger.info("~~~~splitTag拆分后存储拆分的值之前resultKey为："
					+ splitBo.getResultKey());
		}
		Map<Integer, List<String>> spMap = null;
		try {
			spMap = handle.getResult(splitBo.getResultKey()) == null ? null
					: (Map<Integer, List<String>>) handle.getResult(
							splitBo.getResultKey()).getObject();
		} catch (DataStorageException e) {
			logger.error("无法从handle中的全局参数中获取split信息", e);
			return false;
		}
		if (null == spMap) {
			spMap = new HashMap<Integer, List<String>>();
			try {
				handle.addResult(splitBo.getResultKey(), spMap);
			} catch (DataStorageException e) {
				logger.error("添加split标签 key = " + splitBo.getResultKey()
						+ " 信息到全局参数中异常", e);
				return false;
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("spMap:" + spMap);
			logger.debug("splitLs:" + splitLs);
		}
		spMap.put(spMap.size() + 1, splitLs);

		Map<String, List<VarBean>> resultHm = new HashMap<String, List<VarBean>>();
		int count = 0;
		logger.info("splitLs size:" + splitLs.size());
		// 对分割后的数据逐一执行
		for (String str : splitLs) {
			String nResult = str;
			if (logger.isDebugEnabled()) {
				logger.debug("操作数据: " + nResult);
			}
			if (null == nResult || "".equals(nResult)) {
				continue;
			}
			// 对split下的标签逐一执行
			for (ISplitBase tag : splitBo.getSplitBaseList()) {
				if ("validate".equals(tag.getTagNameByObject())) {
					ValidateBo validateBo = (ValidateBo) tag;

					if (!ValidateTagServiceImpl.process(handle, validateBo,
							nResult, localVarHm)) {
						break;
					} else {
						count++;
					}
				} else if ("filter".equals(tag.getTagNameByObject())) {
					FilterBo filterBo = (FilterBo) tag;

					if (!FilterTagServiceImpl.process(handle, filterBo,
							nResult, localVarHm)) {
						// 没找到匹配的数据，不做操作
						logger.warn("没有找到能够过滤并缓存的配置数据，匹配规则：" + filterBo);
					}
				} else if ("matcher".equals(tag.getTagNameByObject())) {
					MatcherBo matcherBo = (MatcherBo) tag;
					nResult = MatcherTagServiceImpl.process(handle, matcherBo,
							nResult);
					if (null == nResult) {
						break;
					}
				} else if ("result".equals(tag.getTagNameByObject())) {
					ResultBo resultBo = (ResultBo) tag;
					String resultName = resultBo.getName();
					List<VarBean> varBeanList = null;
					// 正对数据内容nResult获取result标签的处理结果
					VarBean vb = ResultTagServiceImpl.process(handle, resultBo,
							nResult, localVarHm);
					// 当result标签执行结果成功时，操作数据
					if (null != vb) {
						String existType = null;
						// 如果存在result的name信息，则获取result标签name的Object
						if (resultHm.containsKey(resultName)) {
							varBeanList = resultHm.get(resultName);
							existType = varBeanList.get(0).getType();
						} else {
							varBeanList = new ArrayList<VarBean>();
						}

						// 如果已保存了resultName的数据时，如果本次执行结果数据type为String，或与已存在类型不同，则说明配置存在问题
						if (null != existType) {
							if (!existType.equals(vb.getType())) {
								logger.error("result标签存在不同类型的结果值，xml配置有误无法处理，result name = "
										+ resultName);
								return false;
							}
						}
						varBeanList.add(vb);
						resultHm.put(resultName, varBeanList);
					} else {
						return false;
					}
				}
			}
		}

		// 将结果数据存储到仓储中
		for (Entry<String, List<VarBean>> entry : resultHm.entrySet()) {
			VarBean splitVB = null;
			try {
				splitVB = handle.getResult(entry.getKey());
			} catch (DataStorageException e) {
				logger.error(
						"从全局参数中获取result标签对应数据异常，subName = " + entry.getKey(), e);
				return false;
			}
			if (null == splitVB) {
				try {
					handle.addResult(entry.getKey(), entry.getValue());
				} catch (DataStorageException e) {
					logger.error(
							"向全局参数中添加result标签结果异常，subName = " + entry.getKey(),
							e);
					return false;
				}
			} else {
				List<VarBean> addList = (List<VarBean>) splitVB.getObject();
				addList.addAll(entry.getValue());
			}
		}

		if (null != splitBo.getStat() && !"".equals(splitBo.getStat())) {
			try {
				handle.addResult(splitBo.getStat(), Integer.toString(count));
			} catch (DataStorageException e) {
				logger.error("向全局参数中保存split标签执行匹配成功次数出现异常", e);
				return false;
			}
		}
		return true;
	}

	/**
	 * 将拆分出来的String数组转成List类型
	 * 
	 * @param splitStr
	 * @return
	 */
	private static List<String> transList(String[] splitStr) {
		List<String> rtnLs = new ArrayList<String>();
		for (String s : splitStr) {
			rtnLs.add(s);
		}
		return rtnLs;
	}

	/**
	 * 将拆分出来的数据进行定位
	 * 
	 * @param splitStr
	 * @return
	 */
	private static List<String> doFix(SplitBo splitBo, String[] splitStr) {
		List<String> rtnLs = new ArrayList<String>();
		int len = splitStr.length;
		if (logger.isDebugEnabled()) {
			logger.debug("fix处理;被处理数据的长度是：" + len);
		}
		for (int i : splitBo.getFix()) {
			if (logger.isDebugEnabled()) {
				logger.debug("i:" + i);
			}
			// 如果需要定位获取的数据位置数大于数据长度则无法获取
			if (i > len) {
				continue;
			}
			// 0表示last
			else if (0 == i) {
				// 添加last定位 add by gamesdoa 2012/5/30
				rtnLs.add(splitStr[len - 1]);
			}
			// 需要定位的数据索引大于0，小于等于数据长度时，获取其对应位置数据
			else {
				rtnLs.add(splitStr[i - 1]);
			}
		}

		return rtnLs;
	}

	/**
	 * 对拆分出来的数据进行忽略
	 * 
	 * @param splitBo
	 *            split标签业务对象
	 * @param splitStr
	 *            拆分出来的数据源
	 * @return 剔除忽略索引位置的数据信息
	 */
	private static List<String> doIgnore(SplitBo splitBo, String[] splitStr) {
		List<String> rtnLs = new ArrayList<String>();
		// 将需要拆分出来忽略数据的数据源保存到List中
		for (int i = 0; i < splitStr.length; i++) {
			rtnLs.add(splitStr[i]);
		}
		// 获取迭代器
		Iterator<String> strIter = rtnLs.iterator();
		int index = 0;
		while (strIter.hasNext()) {
			// index为迭代器当前能删除的数据索引位置(索引从1开始)
			index++;
			strIter.next();
			for (Integer integer : splitBo.getIgnore()) {
				// 当index与ignore中需要忽略的索引值一致时，迭代器移除当前元素
				if (index == integer.intValue()) {
					strIter.remove();
				}
			}
		}

		return rtnLs;
	}

	/**
	 * 根据split标签的value值，匹配局部或全局参数
	 * 
	 * @param handle
	 *            操作类
	 * @param splitBo
	 *            split标签业务对象
	 * @param localVarHm
	 *            局部参数
	 * @return 与split标签的value值匹配的数据对象
	 */
	@SuppressWarnings("unchecked")
	private static VarBean getItemsData(OperaterHandle handle, SplitBo splitBo,
			Map<String, VarBean> localVarHm) {
		if (null == splitBo) {
			logger.error("split标签业务对象不能为空，无法获取split标签信息！");
			return null;
		}
		VarBean varBean = null;
		// 判断数据是否符合${xxx.xxx} 或${xxx}结果
		if (null != splitBo.getValue()
				&& splitBo.getValue().matches("\\$\\{\\w+(\\.\\w+)?}$")) {
			Pattern pa = Pattern.compile("\\$\\{(\\w+)(?:\\.(\\w+))?\\}");
			Matcher ma = pa.matcher(splitBo.getValue());

			if (ma.find()) {
				String mainKey = ma.group(1);
				String subKey = ma.group(2);

				// 先在局部参数中查找
				if (null != localVarHm && localVarHm.containsKey(mainKey)) {
					if (logger.isDebugEnabled()) {
						logger.debug("在局部数据变量中找到主键为:" + mainKey + "的数据!");
					}
					VarBean vb = localVarHm.get(mainKey);
					if (null == subKey) {
						varBean = vb;
					} else {
						if (VarBeanStatic.TYPE_HASHMAP.equals(vb.getType())) {
							Map<String, VarBean> thm = (Map<String, VarBean>) vb
									.getObject();
							if (thm.containsKey(subKey)) {
								varBean = thm.get(subKey);
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

				// 在全局参数中查找
				if (null == varBean) {
					try {
						varBean = handle.getData(mainKey, subKey);
					} catch (DataStorageException e) {
						logger.error("从数据仓库中获取数据异常，mainkey=" + mainKey
								+ "，subkey=" + subKey, e);
						return null;
					}
					if (null == varBean
							|| !VarBeanStatic.TYPE_STRING.equals(varBean
									.getType())) {
						return null;
					}
				}
			}
		} else {
			logger.error("split标签中value的内容不符合${xxx} 或 ${xxx.xxx}规则!");
			return null;
		}
		return varBean;
	}
}
