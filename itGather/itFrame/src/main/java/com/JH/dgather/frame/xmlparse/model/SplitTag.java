package com.JH.dgather.frame.xmlparse.model;

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
import com.JH.dgather.frame.xmlparse.exception.DataStorageException;
import com.JH.dgather.frame.xmlparse.exception.ProcessException;
import com.JH.dgather.frame.xmlparse.exception.TagException;

public class SplitTag extends BaseTag {
	private Logger logger = Logger.getLogger(SplitTag.class);

	private List<BaseTag> tagLs = new ArrayList<BaseTag>();

	// 拆分的值
	private String value = "";
	// 拆分的条件
	private String regex = "";
	// 当统计不为空的时候，会统计拆分出来并通过校验的数量，存储在stat定义的变量中。存在stat的时候，不能存在mathcer子标签
	private String stat = "";

	// 定位
	private List<Integer> fix = new ArrayList<Integer>();
	// 忽略
	private List<Integer> ignore = new ArrayList<Integer>();
	private String resultKey = "";

	public SplitTag(Element element, BaseTag parent) throws TagException {
		super("split", element, parent);
		this.parse();
	}

	@Override
	protected void parse() throws TagException {
		String errMsg = "";
		errMsg = this.getTagName() + " 标签规则: \r\n";
		errMsg += "【必须包含属性】\r\n";
		errMsg += "value: 拆分的值\r\n";
		errMsg += "regex: 拆分的条件\r\n";
		errMsg += "【可选属性】\r\n";
		errMsg += "stat: 统计split后，并通过validate的数据长度\r\n";
		errMsg += "fix: 定位，对拆分出来的数据进行定位，多个以';'分隔\r\n";
		errMsg += "ignore: 忽略，对拆分出来的数据进行忽略，多个以';'分隔\r\n";
		errMsg += "result: 当fix/ignore存在时，result属性必须存在，存储拆分后的数据列表";
		errMsg += "fix与ignore不能同时出现\r\n";
		errMsg += "【可包含的子标签】\r\n";
		errMsg += "<validate> <matcher> <fix> <replace> <split>";

		/**
		 * 判断split标签属性值
		 */
		List propLs = this.getElement().attributes();
		if (propLs.size() > 0) {
			for (Iterator it = propLs.iterator(); it.hasNext();) {
				Attribute attr = (Attribute) it.next();
				if (attr.getName().equals("value")) {
					this.value = attr.getValue();
				} else if (attr.getName().equals("regex")) {
					this.regex = attr.getValue();
				} else if (attr.getName().equals("stat")) {
					this.stat = attr.getValue();
				} else if (attr.getName().equals("fix")) {
					if (this.ignore.size() == 0) {
						checkFixProperty(attr.getValue());
					} else {
						logger.error(this.getTagName() + " 标签中, fix与ignore属性不能同时出现！");
						logger.error(errMsg);
						throw new TagException(this.getTagName() + " 标签中, fix与ignore属性不能同时出现！");
					}
				} else if (attr.getName().equals("ignore")) {
					if (this.ignore.size() == 0) {
						this.checkIgnoreProperty(attr.getValue());
					} else {
						logger.error(this.getTagName() + " 标签中, fix与ignore属性不能同时出现！");
						logger.error(errMsg);
						throw new TagException(this.getTagName() + " 标签中, fix与ignore属性不能同时出现！");
					}
				} else if (attr.getName().equals("result")) {
					this.resultKey = attr.getValue();
				} else {
					logger.error(this.getTagName() + " 标签不允许包含属性: " + attr.getName());
					logger.error(errMsg);
					throw new TagException(this.getTagName() + " 标签不允许包含属性: " + attr.getName());
				}
			}

			// 验证fix,ignore result三者的关系
			if ((this.fix.size() > 0 || this.ignore.size() > 0) && "".equals(this.resultKey)) {
				logger.error(this.getTagName() + " 标签中，fix或ignore属性存在时，result属性必须存在!");
				throw new TagException(this.getTagName() + " 标签中，fix或ignore属性存在时，result属性必须存在!");
			}

			// value属性为必配属性，不得为空
			if (value == null || "".equals(value)) {
				logger.error(this.getTagName() + " 标签中value属性不得为空!");
				logger.error(errMsg);
				throw new TagException(this.getTagName() + " 标签中value属性不得为空!");
			}
			// regex属性为必配属性，不得为空
			if (regex == null || "".equals(regex)) {
				logger.error(this.getTagName() + " 标签中regex属性不得为空!");
				logger.error(errMsg);
				throw new TagException(this.getTagName() + " 标签中regex属性不得为空!");
			}
		}

		/**
		 * 获取split标签下的内容
		 */
		List tagls = this.getElement().elements();
		boolean isMatcher = false;
		if (tagls.size() > 0) {
			for (Iterator it = tagls.iterator(); it.hasNext();) {
				Element em = (Element) it.next();
				if (!isMatcher) {
					if (em.getName().equals("split")) {
						checkSameTag("split");
						SplitTag splitT = new SplitTag(em, this);
						tagLs.add(splitT);
					} else if (em.getName().equals("validate")) {
						checkSameTag("split");
						ValidateTag validateTag = new ValidateTag(em, this);
						tagLs.add(validateTag);
					} else if (em.getName().equals("matcher")) {
						// if (this.stat != null && !this.stat.equals("")) {
						// throw new TagException("matcher标签与stat属性不能同时出现！");
						// }
						isMatcher = true;
						checkSameTag("matcher");
						MatcherTag matcherTag = new MatcherTag(em, this);
						tagLs.add(matcherTag);
					} else if (em.getName().equals("replace")) {
						checkSameTag("replace");
						ReplaceTag replaceTag = new ReplaceTag(em, this);
						tagLs.add(replaceTag);
					} else if (em.getName().equals("result")) {
						ResultTag resultTag = new ResultTag(em, this);
						tagLs.add(resultTag);
					} else {
						logger.error(this.getTagName() + " 标签不允许包含子标签: " + em.getName());
						logger.error(errMsg);
						throw new TagException(this.getTagName() + " 标签不允许包含子标签: " + em.getName());
					}
				} else {
					logger.error(this.getTagName() + " 标签子标签中matcher标签后不得包含其它子标签，当前子标签为： " + em.getName());
					logger.error(errMsg);
					throw new TagException(this.getTagName() + " 标签子标签中matcher标签后不得包含其它子标签，当前子标签为： " + em.getName());
				}
			}
		} else {
			logger.warn(this.getTagName() + "标签内没做配置!");
		}
	}

	/**
	 * 处理ignore属性的数据
	 * 
	 * @param str
	 * @throws TagException
	 */
	private void checkIgnoreProperty(String string) throws TagException {
		String str = string;
		if (!str.endsWith(";")) {
			str += ";";
		}

		String[] s = str.split(";");
		for (String sr : s) {
			if (sr.equals("")) {
				continue;
			}

			if (StringUtil.isNumberString(sr)) {
				int iS = Integer.parseInt(sr);
				if (iS <= 0) {
					logger.error(this.getTagName() + " 标签中ignore属性的数据必须为大于1的整数，当前数据为: " + iS);
					throw new TagException(this.getTagName() + " 标签中ignore属性的数据必须为大于1的整数，当前数据为: " + iS);
				} else
					this.ignore.add(iS);
			} else {
				logger.error(this.getTagName() + " 标签中ignore属性的数据并不是数字，当前数据为: " + sr);
				throw new TagException(this.getTagName() + " 标签中ignore属性的数据并不是数字，当前数据为: " + sr);
			}
		}
	}

	/**
	 * 处理fix属性的数据
	 * 
	 * @param str
	 * @throws TagException
	 */
	private void checkFixProperty(String string) throws TagException {
		String str = string;
		if (!str.endsWith(";")) {
			str += ";";
		}

		String[] s = str.split(";");
		for (String sr : s) {
			if (sr.equals("")) {
				continue;
			}

			// 添加last定位 add by gamesdoa 2012/5/30
			if ("last".equals(sr))
				this.fix.add(0);
			else if (StringUtil.isNumberString(sr)) {
				int iS = Integer.parseInt(sr);
				if (iS <= 0) {
					logger.error(this.getTagName() + " 标签中fix属性的数据必须为大于1的整数，当前数据为: " + iS);
					throw new TagException(this.getTagName() + " 标签中fix属性的数据必须为大于1的整数，当前数据为: " + iS);
				} else
					this.fix.add(iS);
			} else {
				logger.error(this.getTagName() + " 标签中fix属性的数据并不是数字，当前数据为: " + sr);
				throw new TagException(this.getTagName() + " 标签中fix属性的数据并不是数字，当前数据为: " + sr);
			}
		}
	}

	/**
	 * <code>checkSameTag</code> 判断当前的标签表列中是否已包含相同名称的标签。因为有些标签只能出现一次
	 * 
	 * @param tagName
	 *            : 需要判断的标签名称
	 * @return
	 */
	private void checkSameTag(String tagName) throws TagException {
		for (BaseTag tag : this.tagLs) {
			if (tag.getTagName().equals(tagName)) {
				throw new TagException(this.getTagName() + "标签中不得重复出现 " + tag.getTagName());
			}
		}
	}

	/**
	 * <code>judgeSameTag</code> 判断当前标签列表中是否已包含指定标签
	 * 
	 * @param tagName
	 *            : 需要判断的标签名称
	 * @return true:表示已包含
	 */
	private boolean judgeSameTag(String tagName) {
		boolean rtn = false;
		for (BaseTag tag : this.tagLs) {
			if (tag.getTagName().equals(tagName)) {
				rtn = true;
				break;
			}
		}
		return rtn;
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
	public void process(OperaterHandle handle, List<String> logLs, HashMap<String, VarBean> localVarHm) throws ProcessException, DataStorageException {
		logLs.add("开始执行拆分操作.");
		logger.info("开始从数据仓库中获取split的数据，value数据为:" + this.value);
		logLs.add("开始从数据仓库中获取split的数据，value数据为:" + this.value);
		VarBean varBean = this.getItemsData(handle, localVarHm);
		logLs.add("取到迭代数据, 数据类型为: " + varBean.getType());

		if (!varBean.getType().equals(VarBeanStatic.TYPE_STRING)) {
			logger.error("split标签中value指向的数据类型必须为String!, 当前的类型是:" + varBean.getType());
			throw new ProcessException("split标签中value指向的数据类型必须为String!, 当前的类型是:" + varBean.getType());
		}

		String result = varBean.getObject().toString().trim();
		
		if (result == null || "".equals(result)) {
			throw new ProcessException("需要拆分的回显内容为null或空！");
		}
		logger.info("需要拆分的内容为:" + result);
		logger.info("~~~~拆分表达式regex:"+regex);

		// 先进行拆分
		String[] splitStr = result.split(this.regex);
		List<String> splitLs = this.transList(splitStr);

		if (this.fix.size() > 0) {
			splitLs = this.doFix(splitLs);
		} else if (this.ignore.size() > 0) {
			splitLs = this.doIgnore(splitLs);
		}

		logger.info("~~~~splitTag拆分后存储拆分的值之前resultKey为：" + this.resultKey);
		Map<Integer, List<String>> spMap = handle.getResult(this.resultKey) == null ? null : (Map<Integer, List<String>>) handle.getResult(this.resultKey).getObject();
		if (spMap == null) {
			spMap = new HashMap<Integer, List<String>>();
			handle.addResult(this.resultKey, spMap);
		}
		logger.info("spMap:"+spMap);
		logger.info("splitLs:"+splitLs);
		spMap.put(spMap.size() + 1, splitLs);
		// List<VarBean> varBeanList = (List<VarBean>)
		// handle.getResult(this.resultKey);
		// if (varBeanList != null)
		// handle.addResult(this.resultKey, splitLs);

		HashMap<String, List<VarBean>> resultHm = new HashMap<String, List<VarBean>>();
		int count = 0;
		logger.info("splitLs size:" + splitLs.size());
		for (String str : splitLs) {
			String nResult = str;
			boolean isValidate = true;
			logLs.add("操作数据: " + str);
			logger.info("操作数据: " + str);
			if(str==null||"".equals(str))
				continue;
			for (BaseTag tag : this.tagLs) {
				if (tag.getTagName().equals("validate")) {
					ValidateTag validateTag = (ValidateTag) tag;
					isValidate = validateTag.process(nResult, logLs);
					if (!isValidate) {
						break;
					} else {
						count++;
					}
				} else if (tag.getTagName().equals("matcher")) {
					MatcherTag matcherTag = (MatcherTag) tag;
					nResult = matcherTag.process(handle, nResult, logLs);
				} else if (tag.getTagName().equals("result")) {
					ResultTag resultTag = (ResultTag) tag;
					String resultName = resultTag.getName();
					List<VarBean> rLs = null;
					if (resultHm.containsKey(resultName)) {
						rLs = resultHm.get(resultName);
						logger.info("=====resultName:"+resultName);
						logger.info("======rLs:"+rLs);
					} else {
						rLs = new ArrayList<VarBean>();
						resultHm.put(resultName, rLs);
					}
					VarBean vb = resultTag.process(handle, nResult, logLs);
					if (vb != null && vb.getType().equals(VarBeanStatic.TYPE_STRING)) {
						rLs.add(vb);
					}
				}
			}
		}

		// 将结果数据存储到仓储中
		for (Entry<String, List<VarBean>> entry : resultHm.entrySet()) {
			List<VarBean> tempList = handle.getResult(entry.getKey()) == null ? null : (List<VarBean>) handle.getResult(entry.getKey()).getObject();
			if (tempList == null) {
				handle.addResult(entry.getKey(), entry.getValue());
			} else
				tempList.addAll(entry.getValue());
		}

		if (!this.stat.equals("")) {
			handle.addResult(this.stat, Integer.toString(count));
		}
	}

	/**
	 * 将拆分出来的String数组转成List类型
	 * 
	 * @param splitStr
	 * @return
	 */
	private List<String> transList(String[] splitStr) {
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
	private List<String> doFix(List<String> splitLs) {
		List<String> rtnLs = new ArrayList<String>();
		int len = splitLs.size();
		logger.debug("fix处理;被处理数据的长度是："+len);
		for (int i : this.fix) {
			logger.debug("i:"+i);
			if (i > len) {
				continue;
			} else if (i == 0) {
				// 添加last定位 add by gamesdoa 2012/5/30
				rtnLs.add(splitLs.get(splitLs.size() - 1));
			} else {
				rtnLs.add(splitLs.get(i - 1));
			}
		}

		return rtnLs;
	}

	/**
	 * 对拆分出来的数据进行忽略
	 * 
	 * @param splitLs
	 * @return
	 */
	private List<String> doIgnore(List<String> splitLs) {
		List<String> rtnLs = new ArrayList<String>();
		int k = 0;
		for (String s : splitLs) {
			k++;
			if (this.ignore.contains(k)) {
				continue;
			} else {
				rtnLs.add(s);
			}
		}

		return rtnLs;
	}

	/**
	 * <code>getItemsData</code> 提取参数值
	 * 
	 * @param handle
	 * @param localVarHm
	 * @return
	 */
	private VarBean getItemsData(OperaterHandle handle, HashMap<String, VarBean> localVarHm) throws ProcessException, DataStorageException {
		VarBean varBean = null;
		boolean isFind = false;
		/**
		 * 提取参数值
		 */
		// 判断数据是否符合${xxx.xxx} 或${xxx}结果
		if (this.value.matches("\\$\\{\\w+(\\.\\w+)?}$")) {
			Pattern pa = Pattern.compile("\\$\\{(\\w+)\\.?(\\w+)?\\}");
			Matcher ma = pa.matcher(this.value);

			if (ma.find()) {
				String mainKey = ma.group(1);
				String subKey = ma.group(2);

				// 先在局部参数中查找
				if (localVarHm != null && localVarHm.containsKey(mainKey)) {
					logger.debug("在局部数据变量中找到主键为:" + mainKey + "的数据!");
					VarBean vb = localVarHm.get(mainKey);
					if (subKey == null) {
						varBean = vb;
						isFind = true;
					} else {
						if (vb.getType().equals(VarBeanStatic.TYPE_STRING)) {
							HashMap<String, VarBean> thm = (HashMap<String, VarBean>) vb.getObject();
							if (thm.containsKey(subKey)) {
								varBean = thm.get(subKey);
								logger.debug(">>> " + varBean.getType());
								isFind = true;
							} else {
								logger.error("局部数据中，主键为:" + mainKey + "的HashMap数据集合中，不存在键值为: " + subKey + "的数据!");
								throw new ProcessException("局部数据中，主键为:" + mainKey + "的HashMap数据集合中，不存在键值为: " + subKey + "的数据!");
							}
						} else {
							logger.error("局部数据中，主键为：" + mainKey + "的数据，不是HashMap类型，因为要从中取键值为: " + subKey + "的数据!, 当前数据类型为: " + vb.getType());
							throw new ProcessException("局部数据中，主键为：" + mainKey + "的数据，不是HashMap类型，因为要从中取键值为: " + subKey + "的数据!, 当前数据类型为: " + vb.getType());
						}
					}
				}

				// 在全局参数中查找
				if (!isFind) {
					varBean = handle.getData(mainKey, subKey);
					if (!varBean.getType().equals(VarBeanStatic.TYPE_STRING)) {
						logger.error("split标签中value指向的数据类型必须为String!, 当前的类型是:" + varBean.getType());
						throw new ProcessException("split标签中value指向的数据类型必须为String!");
					}
				}
			}
		} else {
			logger.error("split标签中value的内容不符合${xxx} 或 ${xxx.xxx}规则!");
			throw new ProcessException("split标签中value的内容不符合${xxx} 或 ${xxx.xxx}规则!");
		}
		return varBean;
	}
}
