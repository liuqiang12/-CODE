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

import com.JH.dgather.frame.util.StringUtil;
import com.JH.dgather.frame.xmlparse.OperaterHandle;
import com.JH.dgather.frame.xmlparse.beans.VarBean;
import com.JH.dgather.frame.xmlparse.beans.VarBeanStatic;
import com.JH.dgather.frame.xmlparse.bo.ResultBo;
import com.JH.dgather.frame.xmlparse.bo.iface.IBase;
import com.JH.dgather.frame.xmlparse.exception.DataStorageException;
import com.JH.dgather.frame.xmlparse.exception.TagException;
import com.JH.dgather.frame.xmlparse.service.IBaseService;

/**
 * systemRules.xml中result标签业务处理类
 * 
 * <result name="test">Total Nets: (\d++)</result> //将通过正则提取的数据存放到test键值中
 * <result name="test" operater="count">${result.isisNeighbors}</result>
 * //将${result.isisNeighbors} 的数据求合，将结果数据存到test键值中 <result name="test"
 * operater="count">Total Nets: (\d++)<result> //将正则提取的数据求合，将结果存到test键值中 <result
 * name="test" operater="size">${result.isisNeighbors}</result>
 * //将${result.isisNeighbors} 数据长度存到test键值中 <result name="test"
 * operater="size">Total Nets: (\d++)</result> //将符合正则的数据长度存到test键值中 <result
 * name="test" operater="list"> Total Nets: (\d++)></result>
 * //将符合正则的所有数据以List方式存到test键值中
 * 
 * @author zhengbin
 * 
 */
public class ResultTagServiceImpl implements IBaseService {
	private static Logger logger = Logger.getLogger(ResultTagServiceImpl.class);

	// result标签对应的element对象
	private Element resultEl = null;

	public ResultTagServiceImpl(Element element) {
		resultEl = element;
	}

	@Override
	public IBase parse() {

		ResultBo resultBo = null;

		// 获取result标签参数与内容值
		try {
			resultBo = getTagInfo();
		} catch (TagException e) {
			logger.error(e.getMessage(), e);
			return null;
		}

		// 根据result标签规则检查配置的属性值，检查失败返回null
		if (!checkTagInfo(resultBo)) {
			return null;
		}
		return resultBo;
	}

	/**
	 * 从xml的result节点信息中获取标签属性
	 * 
	 * @return result标签业务对象
	 * @throws TagException
	 *             标签不允许包含属性异常
	 */
	@SuppressWarnings("rawtypes")
	private ResultBo getTagInfo() throws TagException {

		ResultBo resultBo = new ResultBo();

		// 遍历result节点的所有属性
		for (Iterator it = resultEl.attributeIterator(); it.hasNext();) {
			Attribute attribute = (Attribute) it.next();
			if ("name".equals(attribute.getName())) {
				resultBo.setName(attribute.getValue());
			} else if ("operater".equals(attribute.getName())) {
				resultBo.setOperater(attribute.getValue());
			} else if ("prefix".equals(attribute.getName())) {
				resultBo.setPrefix(attribute.getValue());
			} else if ("resultKey".equals(attribute.getName())) {
				resultBo.setResultKey(attribute.getValue());
			} else if ("include".equals(attribute.getName())) {
				splitStringToList(attribute.getValue(), "\\|", "|",
						resultBo.getIncludeList());
			} else if ("exclude".equals(attribute.getName())) {
				splitStringToList(attribute.getValue(), "\\|", "|",
						resultBo.getExcludeList());
			} else {
				throw new TagException("result 标签不允许包含属性: "
						+ attribute.getName());
			}
		}
		resultBo.setResultValue(resultEl.getText());
		return resultBo;
	}

	/**
	 * 根据result标签规则检查配置的属性值，检查失败返回null
	 * 
	 * @param resultBo
	 *            result标签业务对象
	 * @return true:检查成功 false:检查失败
	 */
	private boolean checkTagInfo(ResultBo resultBo) {
		// name不能为null
		if (null == resultBo.getName() || "".equals(resultBo.getName())) {
			logger.error("result 标签中的name属性不能为空！");
			return false;
		}

		// 标签的内容不能为null或""
		if (null == resultBo.getResultValue()
				|| "".equals(resultBo.getResultValue())) {
			logger.error("result 标签的内容不能为空！");
			return false;
		}

		// 操作符为 count
		if (null != resultBo.getOperater()) {
			// 如果操作符号为count或size
			if ("count".equals(resultBo.getOperater())
					|| "size".equals(resultBo.getOperater())) {
				// 如果标签匹配${...}，则可以为${xxx}或者${xxx.xxx}
				if (resultBo.getResultValue().matches("\\$\\{.+\\}$")) {
					if (!resultBo.getResultValue().matches(
							"\\$\\{\\w+(?:\\.\\w+)?\\}")) {
						logger.error("当result标签中operater操作符为count或size时，标签内容必须符合 ${xxx.xxx} 或 ${xxx} 的规则，当前内容为： "
								+ resultBo.getResultValue());
						return false;
					}
				}
			}
			// operater为list时，result标签内容不能为${...}
			else if ("list".equals(resultBo.getOperater())) {
				if (resultBo.getResultValue().matches("\\$\\{.+\\}$")) {
					logger.error("当result标签中operater操作符为list时，标签内容必须为正则表达式，当前内容为： "
							+ resultBo.getResultValue());
					return false;
				}
			}

		}

		// 如果resultKey属性不为空，则属性格式必须为${xxx.xxx}
		if (null != resultBo.getResultKey()) {
			Pattern p = Pattern.compile("\\$\\{\\w+.\\w+\\}$");
			Matcher m = p.matcher(resultBo.getResultKey());
			if (!m.matches()) {
				logger.error("当result标签中resultKey属性不为空时，属性格式必须为${xxx.xxx}，当前内容为： "
						+ resultBo.getResultKey());
				return false;
			}
		}

		return true;
	}

	/**
	 * 拆分字符串到list中
	 * 
	 * @param source
	 *            被拆分的数据源
	 * @param regex
	 *            拆分正则表达式
	 * @param connStr
	 *            字符串连接符
	 * @param strList
	 *            执行结果保存对象
	 */
	private void splitStringToList(String source, String regex, String connStr,
			List<String> strList) {
		String ctx = source;
		if (!ctx.endsWith(connStr)) {
			ctx += connStr;
		}
		String[] s = ctx.split(regex);
		for (String sr : s) {
			if ("".equals(sr)) {
				continue;
			} else {
				strList.add(sr);
			}
		}
	}

	/**
	 * 执行result标签处理
	 * 
	 * @param handle
	 *            操作类
	 * @param resultBo
	 *            result标签业务对象
	 * @param result
	 *            result标签处理的数据来源
	 * @return 获取结果内容并封装的varBean数据对象返回,返回null表示获取失败
	 */
	public static VarBean process(OperaterHandle handle, ResultBo resultBo,
			String result, Map<String, VarBean> localVarHm) {
		// 情况一: <result name="test">Total Nets: (\d++)</result>
		if (null == resultBo.getOperater() || "".equals(resultBo.getOperater())) {
			return resultTagFirstType(resultBo, result, localVarHm);
		} else {
			if ("count".equals(resultBo.getOperater())) {
				// 情况二: <result name="test"
				// operater="count">${result.isisNeighbors}</result>
				// 判断是否是${...}关联的参数
				if (resultBo.getResultValue().matches("\\$\\{.+\\}$")) {
					return resultTagSecondType(handle, resultBo, localVarHm);
				}
				// 情况三: <result name="test" operater="count">Total Nets:
				// (\d++)<result>
				else {
					return resultTagThridType(resultBo, result, localVarHm);
				}
			} else if ("list".equals(resultBo.getOperater())) {

				// 情况五: <result name="test" resultKey="${arg.aclNumber}"
				// * operater="list"> Total Nets:// (\d++) </result>
				if (null != resultBo.getResultKey()) {
					return resultTagFifthType(handle, resultBo, result,
							localVarHm);
				}
				// 情况四: <result name="test" operater="list"> Total Nets:
				// (\d++)</result>
				else {
					return resultTagFourthType(resultBo, result, localVarHm);
				}
			} else if ("size".equals(resultBo.getOperater())) {
				// 情况六: <result name="test"
				// operater="size">${result.isisNeighbors}</result>
				// 判断是否是${}关联的参数
				if (resultBo.getResultValue().matches("\\$\\{.+\\}$")) {
					return resultTagSixthType(handle, resultBo, localVarHm);
				}
				// 情况七: <result name="test" operater="size">Total Nets:
				// (\d++)</result>
				else {
					return resultTagSeventhType(resultBo, result, localVarHm);
				}
			} else {
				logger.error("result 标签的operater属性不能为: "
						+ resultBo.getOperater());
				return null;
			}
		}
	}

	/**
	 * 情况七: &lt;result name="test" operater="size"&gt;Total Nets:
	 * (\d++)&lt;/result&gt;
	 * 
	 * @param resultBo
	 *            result标签业务对象
	 * @param result
	 *            result获取结果的数据来源
	 * @return 获取结果内容并封装的varBean数据对象
	 */
	private static VarBean resultTagSeventhType(ResultBo resultBo,
			String result, Map<String, VarBean> localVarHm) {
		if (null == resultBo) {
			logger.error("result标签业务对象为空，无法进行标签结果处理！");
			return null;
		}
		if (null == result || "".equals(result)) {
			logger.error("在执行result标签操作时，用于分析的回显内容不能为空！");
			return null;
		}
		VarBean varBean = null;
		Pattern p = Pattern.compile(resultBo.getResultValue());
		Matcher m = p.matcher(result);
		int size = 0;
		// 返回匹配次数
		while (m.find()) {
			size++;
		}
		varBean = transResult(resultBo, Integer.toString(size));
		if (null != varBean) {
			localVarHm.put(resultBo.getName(), varBean);
		}
		return varBean;
	}

	/**
	 * 情况六: &lt;result name="test"
	 * operater="size"&gt;${result.isisNeighbors}&lt;/result&gt;
	 * 
	 * @param resultBo
	 *            result标签业务对象
	 * @param handle
	 *            handle操作类
	 * @return 获取结果内容并封装的varBean数据对象
	 */
	@SuppressWarnings("rawtypes")
	private static VarBean resultTagSixthType(OperaterHandle handle,
			ResultBo resultBo, Map<String, VarBean> localVarHm) {
		if (null == handle) {
			logger.error("OperaterHandle操作对象为空，无法从全局参数中获取信息！");
			return null;
		}
		if (null == resultBo) {
			logger.error("result标签业务对象为空，无法进行标签结果处理！");
			return null;
		}
		VarBean varBean = null;
		Pattern p = Pattern.compile("\\$\\{(\\w+)\\.(\\w+)\\}");
		Matcher m = p.matcher(resultBo.getResultValue());
		String mainK = null;
		String subK = null;
		// 如果找到匹配数据，则获取捕获内容
		if (m.find()) {
			mainK = m.group(1);
			subK = m.group(2);
		}

		// 获取全局仓库信息中的对应数据
		VarBean vb = null;
		try {
			vb = handle.getData(mainK, subK);
		} catch (DataStorageException e) {
			logger.error("从全局参数中获取数据异常", e);
			return null;
		}
		// 当获取仓库数据成功，且数据类型为list时，计算list的个数，并返回对应的varBean对象
		if (null != vb && VarBeanStatic.TYPE_LIST.equals(vb.getType())) {
			List ls = (List) vb.getObject();
			int count = ls.size();
			varBean = transResult(resultBo, Integer.toString(count));
			if (null != varBean) {
				localVarHm.put(resultBo.getName(), varBean);
			}
			return varBean;
		} else {
			logger.error("在执行result标签操作时，计算指定数据列表长度时，数据类型不是List类型或无法从数据仓库中获取对应数据, 数据关联值为："
					+ resultBo.getResultValue());
			return null;
		}
	}

	/**
	 * 情况五: &lt;result name="test" resultKey="${arg.aclNumber}"
	 * operater="list"&gt; Total Nets:// (\d++)&lt;/result&gt;
	 * 
	 * @param resultBo
	 *            result标签业务对象
	 * @param result
	 *            result获取结果的数据来源
	 * @return 获取结果内容并封装的varBean数据对象
	 */
	private static VarBean resultTagFifthType(OperaterHandle handle,
			ResultBo resultBo, String result, Map<String, VarBean> localVarHm) {
		if (null == resultBo) {
			logger.error("result标签业务对象为空，无法进行标签结果处理！");
			return null;
		}
		if (null == result || "".equals(result)) {
			logger.error("在执行result标签操作时，用于分析的回显内容不能为空！");
			return null;
		}
		VarBean varBean = resultTagFourthType(resultBo, result, localVarHm);
		// 如果获取list成功
		if (null != varBean) {
			// 创建关联关系Map<key, VarBean(type=list,obj=list<String>)
			Map<String, VarBean> tempMap = new HashMap<String, VarBean>();
			String resultKey = transformResultKey(handle,
					resultBo.getResultKey(), localVarHm);
			tempMap.put(resultKey, varBean);
			VarBean tempVarBean = new VarBean(VarBeanStatic.TYPE_HASHMAP,
					tempMap);
			if (null != varBean) {
				localVarHm.put(resultBo.getName(), tempVarBean);
			}
			return tempVarBean;
		}
		return null;
	}

	/**
	 * 情况四: &lt;result name="test" operater="list"&gt; Total Nets://
	 * (\d++)&lt;/result&gt;
	 * 
	 * @param resultBo
	 *            result标签业务对象
	 * @param result
	 *            result获取结果的数据来源(同一个结束数据源段可能出现多个匹配数据)
	 * @return 获取结果内容并封装的varBean数据对象
	 */
	private static VarBean resultTagFourthType(ResultBo resultBo,
			String result, Map<String, VarBean> localVarHm) {
		if (null == resultBo) {
			logger.error("result标签业务对象为空，无法进行标签结果处理！");
			return null;
		}
		if (null == result || "".equals(result)) {
			logger.error("在执行result标签操作时，用于分析的回显内容不能为空！");
			return null;
		}
		List<String> resultLs = new ArrayList<String>();
		Pattern p = Pattern.compile(resultBo.getResultValue());
		Matcher m = p.matcher(result);
		// 如果没有找到匹配的数据结果，则
		while (m.find()) {
			resultLs.add(m.group(1));
		}
		VarBean varBean = transResult(resultBo, resultLs);
		if (null != varBean) {
			localVarHm.put(resultBo.getName(), varBean);
		}
		return varBean;
	}

	/**
	 * 情况三: &lt;result name="test" operater="count"&gt;Total
	 * Nets:(\d++)&lt;/result&gt;
	 * 
	 * @param resultBo
	 *            result标签业务对象
	 * @param result
	 *            result获取结果的数据来源
	 * @return 获取结果内容并封装的varBean数据对象
	 */
	private static VarBean resultTagThridType(ResultBo resultBo, String result,
			Map<String, VarBean> localVarHm) {
		if (null == resultBo) {
			logger.error("result标签业务对象为空，无法进行标签结果处理！");
			return null;
		}
		if (null == result || "".equals(result)) {
			logger.error("在执行result标签操作时，用于分析的回显内容不能为空！");
			return null;
		}
		VarBean varBean = null;
		int count = 0;
		Pattern p = Pattern.compile(resultBo.getResultValue());
		Matcher m = p.matcher(result);
		// 循环查找对应数据
		while (m.find()) {
			if (StringUtil.isNumberString(m.group(1))) {
				count += Integer.parseInt(m.group(1));
			} else {
				logger.error("result 标签通过 " + resultBo.getResultValue()
						+ " 取符合数据列表，并求和时，存在数据不是整数，当前查询出的数据为: " + m.group(1));
				return null;
			}
		}
		// 如果没有找到符合的数据，则count为 0
		varBean = transResult(resultBo, Integer.toString(count));
		if (null != varBean) {
			localVarHm.put(resultBo.getName(), varBean);
		}
		return varBean;
	}

	/**
	 * 情况二: &lt;result name="test"
	 * operater="count"&gt;${result.isisNeighbors}&lt;/result&gt;处理
	 * 
	 * @param handle
	 *            操作内，获取全局仓库数据入口
	 * @param resultBo
	 *            result标签业务对象
	 * @return 获取结果内容并封装的varBean数据对象
	 */
	@SuppressWarnings("rawtypes")
	private static VarBean resultTagSecondType(OperaterHandle handle,
			ResultBo resultBo, Map<String, VarBean> localVarHm) {
		if (null == handle) {
			logger.error("OperaterHandle操作对象为空，无法从全局参数中获取信息！");
			return null;
		}
		if (null == resultBo) {
			logger.error("result标签业务对象为空，无法进行标签结果处理！");
			return null;
		}
		VarBean varBean = null;
		String mainK = null;
		String subK = null;
		// 由于DataStorage内的数据必须要求maink与subk都存在，因此必须匹配下面正则
		Pattern p = Pattern.compile("\\$\\{(\\w+)\\.(\\w+)\\}");
		Matcher m = p.matcher(resultBo.getResultValue());
		// 如果result标签内容与上面正则部分匹配，则获取2个捕获组数据
		if (m.find()) {
			mainK = m.group(1);
			subK = m.group(2);
		} else {
			logger.error("result标签属性operater='count'时，内容值配置不符合规则，无法获取对应数据");
			return null;
		}

		VarBean vb = null;
		try {
			vb = handle.getData(mainK, subK);
		} catch (DataStorageException e) {
			logger.error("通过maink = " + mainK + "，subk = " + subK
					+ "，获取数据仓库信息异常", e);
			return null;
		}
		// 如果匹配的数据仓库数据为list，则遍历数据结果集，转换成int相加
		if (null != vb && VarBeanStatic.TYPE_LIST.equals(vb.getType())) {
			List ls = (List) vb.getObject();
			int count = 0;
			for (int i = 0; i < ls.size(); i++) {
				String s = (String) ls.get(i);
				if (StringUtil.isNumberString(s)) {
					count += Integer.parseInt(s);
				} else {
					logger.error("result 标签通过 " + resultBo.getResultValue()
							+ " 关联的数据列表求和时，存在数据不是整数，当前查询出的数据为: " + s);
					return null;
				}
			}
			varBean = transResult(resultBo, Integer.toString(count));
			if (null != varBean) {
				localVarHm.put(resultBo.getName(), varBean);
			}
			return varBean;
		} else {
			logger.error("在执行result标签操作时，计算指定数据列表的和值时，数据类型不是List类型。 当前类型为: "
					+ vb.getType() + ", 数据关联值为：" + resultBo.getResultValue());
			return varBean;
		}
	}

	/**
	 * 针对情况一:&lt;result name="test"&gt; Total Nets: (\d++) &lt;result&gt;的数据处理
	 * 
	 * @param resultBo
	 *            result标签业务对象
	 * @param result
	 *            result标签数据来源
	 * @return 获取结果内容并封装的varBean数据对象
	 */
	private static VarBean resultTagFirstType(ResultBo resultBo, String result,
			Map<String, VarBean> localVarHm) {
		if (null == resultBo) {
			logger.error("result标签业务对象为空，无法进行标签结果处理！");
			return null;
		}
		if (null == result || "".equals(result)) {
			logger.error("在执行result标签操作时，用于分析的回显内容不能为空！");
			return null;
		}

		VarBean varBean = null;
		if (logger.isInfoEnabled()) {
			logger.info("Result operater：" + resultBo.getOperater());
			logger.info("通过正则取结果，正则为: " + resultBo.getResultValue());
		}

		Pattern p = Pattern.compile(resultBo.getResultValue());
		Matcher m = p.matcher(result);
		// 当存在匹配正则的result结果，则获取内容封装为varBean对象返回
		if (m.find()) {
			String r = m.group(1);
			if (logger.isInfoEnabled()) {
				logger.info("通过正则取结果，结果为: " + r);
			}
			varBean = transResult(resultBo, r);
		}
		// 未找到匹配正则的数据结果，则返回""构造的varBean对象（主要考虑存在一些回显信息不是必定存在的内容）
		else {
			if (logger.isDebugEnabled()) {
				logger.debug("未找到匹配正则的结果，将存储一个空值");
			}
			varBean = transResult(resultBo, "");
		}
		if (null != varBean) {
			localVarHm.put(resultBo.getName(), varBean);
		}
		return varBean;
	}

	/**
	 * 对需要封装的数据在includeList，encludeList中做过滤
	 * 
	 * @param resultBo
	 *            result标签业务对象
	 * @param s
	 *            result标签获取的对应数据结果
	 * @return true，表示该数据结果符合匹配规则，false表示该数据不符合匹配规则
	 */
	private static boolean judgeXXclude(ResultBo resultBo, String s) {
		boolean inOk = false;
		boolean exOk = true;
		// 查看数据结果是否包含在允许集合中
		if (null != resultBo.getIncludeList()
				&& 0 < resultBo.getIncludeList().size()) {
			for (String in : resultBo.getIncludeList()) {
				if (s.contains(in)) {
					inOk = true;
					break;
				}
			}
		} else {
			inOk = true;
		}
		// 查看数据结果是否包含在不允许集合中
		if (null != resultBo.getExcludeList()
				&& 0 < resultBo.getExcludeList().size()) {
			for (String ex : resultBo.getExcludeList()) {
				if (s.contains(ex)) {
					exOk = false;
					break;
				}
			}
		}
		// 当结果包含在允许结果中同时不包含在不允许结果中时，数据结果符合匹配规则
		if (inOk && exOk) {
			return true;
		} else {
			logger.error("数据采集结果没有存在于属性include的允许包含中或存在于属性enclude的不允许包含中，result = "
					+ s);
			return false;
		}

	}

	/**
	 * 将数据结果封装为VarBean对象
	 * 
	 * @param resultBo
	 *            result业务对象
	 * @param result
	 *            需要封装的结果
	 * @return varBean对象，如果返回null表示封装失败
	 */
	@SuppressWarnings({ "unchecked" })
	private static VarBean transResult(ResultBo resultBo, Object result) {
		VarBean varbean = null;

		if (result instanceof String) {
			varbean = creVarBeanOfStrType(resultBo, (String) result);
		}
		// 如果result为list类型
		else if (result instanceof List) {
			List<String> ls = null;
			try {
				ls = (List<String>) result;
			}
			// 如果list集合中的数据不为String类型，则报错
			catch (Exception e) {
				logger.error("result标签只支持针对String和List<String>类型的数据处理！", e);
				return null;
			}

			List<String> rtnLs = new ArrayList<String>();
			// 循环遍历结果集
			for (String value : ls) {
				VarBean tempVarBean = creVarBeanOfStrType(resultBo, value);
				// 如果标签处理结果不为null则添加到list中
				if (null != tempVarBean) {
					rtnLs.add((String) tempVarBean.getObject());
				}
			}

			// 如果存在匹配result处理结果的数据，如果不存在对应数据，则封装一个空对象，封装为list
			varbean = new VarBean(VarBeanStatic.TYPE_LIST, rtnLs);
		} else {
			logger.error("result标签只支持针对String和List<String>类型的数据处理！" + resultBo);
			return null;
		}

		return varbean;
	}

	/**
	 * 构建String类型的varBean对象
	 * 
	 * @param resultBo
	 *            result标签业务对象
	 * @param result
	 *            需要处理的数据
	 * @return varBean对象，当数据不满足result标签采集范围时返回null
	 */
	public static VarBean creVarBeanOfStrType(ResultBo resultBo, String result) {
		String rtnSresult = null;
		// 判断数据结果是否在采集范围内
		if (judgeXXclude(resultBo, result)) {
			// 如果结果前缀不为空，则将结果前缀增加到采集结果前
			if (null != resultBo.getPrefix()
					&& !"".equals(resultBo.getPrefix())) {
				rtnSresult = resultBo.getPrefix() + result;
			} else {
				rtnSresult = result;
			}
			// 封装采集结果
			return new VarBean(VarBeanStatic.TYPE_STRING, rtnSresult);
		} else {
			return null;
		}
	}

	/**
	 * 替换resultKey中的${}数据
	 * 
	 * @param handle
	 *            操作类
	 * @param sourceStr
	 *            需要进行参数替换的内容
	 * @param localVarHm
	 *            局部数据
	 * @return 替换后的resultKey内容
	 */
	@SuppressWarnings("unchecked")
	private static String transformResultKey(OperaterHandle handle,
			String sourceStr, Map<String, VarBean> localVarHm) {
		// 虽然String类型参数传递也是地址传递，但是由于String是final类型因此，方法内修改不影响方法外参数原值
		String execStr = sourceStr;

		// 先找出包含${...}的内容
		Pattern p = Pattern.compile("\\$\\{.+?\\}");
		Matcher m = p.matcher(execStr);
		StringBuffer sbuf = new StringBuffer();

		// 如果找到可以匹配的${...}内容，则进行替换操作
		if (m.find()) {
			// 重置匹配器
			m = m.reset();
			// 对于${...}需要做替换，需要进行参数替换的内容中可能存在多个${...}
			while (m.find()) {
				// 替换字符串
				String rp = null;

				// 验证参数是否符合 ${xxx} 或 ${xxx.xxx} 的规则
				String param = m.group();
				Pattern pa = Pattern.compile("\\$\\{(\\w+)(?:\\.(\\w+))?\\}");
				Matcher ma = pa.matcher(param);

				// 符合则获取捕获数据，并通过捕获数据获取对应数据
				if (ma.find()) {
					String mainKey = ma.group(1);
					String subKey = ma.group(2);

					// 优先在局部参数中查找（是否需要考虑局部参数中与全局参数中存在相同key值的数据?）
					if (null != localVarHm && localVarHm.containsKey(mainKey)) {
						VarBean vb = localVarHm.get(mainKey);

						// 当subKey为null时，局部参数中的数据类型必须为String
						if (null == subKey) {
							if (null != vb
									&& VarBeanStatic.TYPE_STRING.equals(vb
											.getType())) {
								rp = (String) vb.getObject();
							} else if (null != vb
									&& VarBeanStatic.TYPE_LIST.equals(vb
											.getType())) {
								List<String> tempList = (List<String>) vb
										.getObject();
								if (1 == tempList.size()) {
									rp = tempList.get(0);
								} else {
									logger.error("需要替换的数据个数大于1，请确认配置信息。替换的数据源："
											+ execStr + "，替换结果：" + tempList);
									return null;
								}
							} else {
								StringBuffer error = new StringBuffer();
								error.append("局部数据仓库中，键值为: ");
								error.append(mainKey);
								error.append("的数据类型必须是String类型, 当前类型为: ");
								error.append(vb.getType());
								error.append("。command标签指令执行失败。");
								logger.error(error.toString());
								return null;
							}
						}
						// 当subkey不为null时，局部参数中的数据类型必须为HashMap
						else {
							if (null != vb
									&& VarBeanStatic.TYPE_HASHMAP.equals(vb
											.getType())) {
								HashMap<String, VarBean> hm = (HashMap<String, VarBean>) vb
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
										return null;
									}
								} else {
									StringBuffer error = new StringBuffer();
									error.append("局部数据仓库中不存在键值为： ");
									error.append(subKey);
									error.append("的数据!");
									logger.error(error.toString());
									return null;
								}

							} else if (null != vb
									&& VarBeanStatic.TYPE_LIST.equals(vb
											.getType())) {
								List<String> tempList = (List<String>) vb
										.getObject();
								if (1 == tempList.size()) {
									rp = tempList.get(0);
								} else {
									logger.error("需要替换的数据个数大于1，请确认配置信息。替换的数据源："
											+ execStr + "，替换结果：" + tempList);
									return null;
								}
							} else {
								StringBuffer error = new StringBuffer();
								error.append("局部数据仓库中，键值为: ");
								error.append(mainKey);
								error.append("的数据须为HashMap，因为有通过键值");
								error.append(subKey);
								error.append("提取数据！当前类型为：");
								error.append(vb.getType());
								logger.error(error.toString());
								return null;
							}
						}
					}

					// 如果在局部参数中未找到对应数据，rp=null，则在全局参数中查找
					if (null == rp) {
						VarBean gvb = null;
						try {
							gvb = handle.getData(mainKey, subKey);
						} catch (DataStorageException e) {
							logger.error("从数据仓库中获取command指令对应数据异常", e);
							return null;
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
							return null;
						}
					}
				} else {
					// 日志信息在调用方法出打印
					StringBuffer error = new StringBuffer();
					error.append("参数不符合 ${xxx} 或 ${xxx.xxx} 的规则，参数信息为： ");
					error.append(param);
					logger.error(error.toString());
					return null;
				}
				m.appendReplacement(sbuf, rp);
			}
			// 实现替换数据操作
			m.appendTail(sbuf);
			execStr = sbuf.toString();
		}
		return execStr;
	}
}
