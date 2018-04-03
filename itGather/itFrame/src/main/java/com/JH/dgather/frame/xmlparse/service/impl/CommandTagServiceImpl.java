package com.JH.dgather.frame.xmlparse.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;

import com.JH.dgather.PropertiesHandle;
import com.JH.dgather.frame.common.cmd.CMDService;
import com.JH.dgather.frame.util.EchoTester;
import com.JH.dgather.frame.xmlparse.OperaterHandle;
import com.JH.dgather.frame.xmlparse.beans.VarBean;
import com.JH.dgather.frame.xmlparse.beans.VarBeanStatic;
import com.JH.dgather.frame.xmlparse.bo.CommandBo;
import com.JH.dgather.frame.xmlparse.bo.iface.IBase;
import com.JH.dgather.frame.xmlparse.exception.DataStorageException;
import com.JH.dgather.frame.xmlparse.exception.TagException;
import com.JH.dgather.frame.xmlparse.service.IBaseService;

/**
 * systemRules.xml中command标签业务处理类
 * 
 * @author zhengbin
 * 
 */
public class CommandTagServiceImpl implements IBaseService {
	private static Logger logger = Logger
			.getLogger(CommandTagServiceImpl.class);

	// 指令不支持
	// % Unrecognized command found at '^' position.
	// % Wrong parameter found at '^' position.
	private static String ERROR_COMMAND_INFO = "found at '^' position.";
	private static String NUABLE_COMMAND_INFO = "is not enabled yet";

	// command标签对应的element对象
	private Element commandEl = null;

	public CommandTagServiceImpl(Element element) {
		commandEl = element;
	}

	public IBase parse() {
		CommandBo commandBo = null;

		// 获取command标签参数信息
		try {
			commandBo = getTagAttribute();
		} catch (TagException e) {
			logger.error(e.getMessage(), e);
			return null;
		}

		// 判断必填参数是否存在，存在则直接返回command业务对象
		if (isGetRequiredAtt(commandBo)) {
			return commandBo;
		} else {
			return null;
		}
	}

	/**
	 * 从xml的command节点信息中获取标签属性
	 * 
	 * @return command标签业务对象
	 * @throws TagException
	 *             标签不允许包含属性异常
	 */
	@SuppressWarnings("rawtypes")
	private CommandBo getTagAttribute() throws TagException {
		CommandBo commandBo = new CommandBo();

		// 遍历rule节点的所有属性
		for (Iterator it = commandEl.attributeIterator(); it.hasNext();) {
			Attribute attribute = (Attribute) it.next();
			if ("buffer".equals(attribute.getName())) {
				commandBo.setBuffer(attribute.getValue());
			} else if ("defaultArgument".equals(attribute.getName())) {
				commandBo.setDefaultArgument(attribute.getValue());
			} else if ("testmode".equals(attribute.getName())) {
				commandBo.setTestmode(attribute.getValue());
			} else if ("iscmd".equals(attribute.getName())) {
				commandBo.setIscmd(attribute.getValue());
			} else {
				throw new TagException("command 标签不允许包含属性: "
						+ attribute.getName());
			}
		}
		return commandBo;
	}

	/**
	 * 是否从标签属性中获取到必填参数
	 * 
	 * @param commandBo
	 *            command标签业务对象
	 * @return true:成功获取必填参数 false:未获取到必填参数
	 */
	private boolean isGetRequiredAtt(CommandBo commandBo) {
		// 判断command标签内容是否为null或“”
		if (null == commandEl.getText() || "".equals(commandEl.getText())) {
			logger.error("command 标签的内容不能为空。");
			return false;
		}

		// 对必填参数进行赋值
		commandBo.setCommandValue(commandEl.getText());
		return true;
	}

	/**
	 * 执行command指令并提取回显
	 * 
	 * @param tl
	 *            服务器信息
	 * @param handle
	 *            操作类
	 * @param commandBo
	 *            command标签业务对象(回显结果放入对象result属性)
	 * @param localVarHm
	 *            临时参数
	 * @return 回显结果
	 */
	public static String process(CMDService tl, OperaterHandle handle,
			CommandBo commandBo, Map<String, VarBean> localVarHm) {
		if (null == commandBo) {
			logger.error("command标签业务对象为空，无法获取command的子标签信息");
			return null;
		}
		Pattern p = Pattern.compile("\\$\\{.+?\\}");
		Matcher m = p.matcher(commandBo.getCommandValue());

		// 当command内容匹配\\$\\{.+?\\}，直接buffer读取数据
		if (m.find() && commandBo.getCommandValue().equals(m.group())
				&& commandBo.getIscmd().equals("false")) {
			String result = transformCommand(handle,
					commandBo.getCommandValue(), localVarHm);
			// 如果result为null表示数据替换出现异常，则返回null
			if (null == result) {
				// 此处无需打印日志，在transformCommand方法中已经根据具体失败情况打印了日志
				return null;
			}

			// 如果存在buffer属性，则把result保存到全区参数中，如果保存失败返回null
			if (setResultBuffer(handle, commandBo, result, localVarHm)) {
				return result;
			} else {
				return null;
			}
		} else {
			EchoTester et = new EchoTester();
			// 通过配置文件获取是通过telnet还是文件获取回显数据
			if (!et.isLandTelnet()) {
				// 如果是通过文件获取回显，则获取文件url地址
				commandBo.setTestmode(PropertiesHandle
						.getResuouceInfo("GET_ECHO_FILE_URL"));
			}
			// 如果testmode不为null或“”表示能获取文件url地址，则是测试模式
			if (null != commandBo.getTestmode()
					&& !"".equals(commandBo.getTestmode())) {
				return testModeExecute(handle, commandBo, localVarHm);
			}

			// 如果不是测试模式，则获取command标签内容，并替换其中的参数
			String cmd = transformCommand(handle, commandBo.getCommandValue(),
					localVarHm);
			// 如果获取command指令为null，则表示transformCommand方法执行失败，直接返回false表示执行失败
			if (null == cmd || "".equals(cmd)) {
				return null;
			}

			if (logger.isDebugEnabled()) {
				logger.debug("执行指令: " + cmd);
			}

			String result = null;
			try {
				result = tl.exeCmd2(cmd, 0);
			} catch (Exception e) {
				StringBuffer error = new StringBuffer();
				error.append("操作服务器：");
				error.append(tl.toString());
				error.append("。执行指令：");
				error.append("失败");
				logger.error(error.toString(), e);
				return null;
			}

			if (logger.isInfoEnabled()) {
				logger.info("command 执行指令: " + cmd + "，回显示：" + result);
			}
			try {
				handle.addBuffer("cmd", cmd);
			} catch (Exception e) {
				logger.error("向数据仓库添加指令数据失败，指令cmd: " + cmd);
				return null;
			}
			if (null == result || "".equals(result)) {
				logger.error(cmd + "指令获取的回显为空!");
				return null;
			} else {
				// 通过配置获取是否保存回显信息
				if (et.isSaveEcho()) {
					// 日志保存回显信息
					if (logger.isInfoEnabled()) {
						logger.info("回显信息: \n" + result);
					}
				}

				// 当回显内容提示指令不支持时，打印异常日志
				if (result.contains(ERROR_COMMAND_INFO)
						|| result.contains(NUABLE_COMMAND_INFO)) {
					StringBuffer error = new StringBuffer();
					error.append("当前指令是获取到的回显信息是: \n");
					error.append(result);
					error.append("\n请检查指令是否正确！或者设备是否支持该指令！");
					logger.error(error.toString());
					return null;
				}
				if (setResultBuffer(handle, commandBo, result, localVarHm)) {
					return result;
				} else {
					return null;
				}
			}
		}
	}

	/**
	 * 测试模式执行获取回显
	 * 
	 * @param handle
	 *            操作类
	 * @param commandBo
	 *            command标签业务对象
	 * @param localVarHm
	 *            临时参数
	 * @return 测试执行结果，文件内容回显
	 */
	private static String testModeExecute(OperaterHandle handle,
			CommandBo commandBo, Map<String, VarBean> localVarHm) {
		if (null == commandBo) {
			logger.error("command标签业务对象为空，无法获取测试模式信息！");
			return null;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("当前指令是测试模式，将从文件读取回显, 路径为: " + commandBo.getTestmode());
		}
		File file = new File(commandBo.getTestmode());
		// 判断测试模式路径文件是否存在，如果存在，读取测试文件内容，获取回显
		if (file.exists()) {
			String result = null;
			Reader reader = null;
			BufferedReader br = null;
			// 读取文件回显
			try {
				reader = new FileReader(commandBo.getTestmode());
				br = new BufferedReader(reader);
				StringBuffer sb = new StringBuffer();
				String data = null;
				while (null != (data = br.readLine())) {
					sb.append(data);
					sb.append("\r\n");
				}
				result = sb.toString();
			} catch (FileNotFoundException e) {
				StringBuffer error = new StringBuffer();
				error.append("当前指令是测试模式，无法获取指定的文件，路径为: ");
				error.append(commandBo.getTestmode());
				logger.error(error.toString(), e);
				return null;
			} catch (IOException e) {
				StringBuffer error = new StringBuffer();
				error.append("当前指令是测试模式，读取指定文件内容异常，路径为: ");
				error.append(commandBo.getTestmode());
				logger.error(error.toString(), e);
				return null;
			} finally {
				if (null != br) {
					try {
						br.close();
					} catch (IOException e) {
						logger.error("关闭BufferedReader异常", e);
					}
				}
				if (null != reader) {
					try {
						reader.close();
					} catch (IOException e) {
						logger.error("关闭Reader异常", e);
					}
				}
			}
			// 读取的文件内容为null或""时，无法获取回显
			if (null == result || "".equals(result)) {
				StringBuffer error = new StringBuffer();
				error.append("当前指令是测试模式，从文件无法读取回显，路径为: ");
				error.append(commandBo.getTestmode());
				logger.error(error.toString());
				return null;
			}

			// 处理回显与buffer
			if (setResultBuffer(handle, commandBo, result, localVarHm)) {
				return result;
			} else {
				return null;
			}
		} else {
			StringBuffer error = new StringBuffer();
			error.append("当前指令是测试模式，将从文件读取回显, 但是文件不存在，路径为: ");
			error.append(commandBo.getTestmode());
			logger.error(error.toString());
			return null;
		}
	}

	/**
	 * 如果buff存在，将回显信息保存到数据仓库中
	 * 
	 * @param handle
	 *            操作类对象
	 * @param commandBo
	 *            command标签对应实体
	 * @param localVarHm
	 *            临时参数
	 * @return 执行结果，false表示执行失败，true表示执行成功
	 */
	private static boolean setResultBuffer(OperaterHandle handle,
			CommandBo commandBo, String result, Map<String, VarBean> localVarHm) {
		boolean exeResult = false;
		commandBo.setOperater(true);
		// 是否将回显存到buffer中
		if (logger.isDebugEnabled()) {
			logger.debug("buffer:" + commandBo.getBuffer());
		}
		if (null != commandBo.getBuffer() && !"".equals(commandBo.getBuffer())) {
			// 获取替换后的buffer名称
			String bufferName = transformCommand(handle, commandBo.getBuffer(),
					localVarHm);
			try {
				handle.addBuffer(bufferName, result);
			} catch (DataStorageException e) {
				StringBuffer error = new StringBuffer();
				error.append("command指令回显信息对应buff键值: ");
				error.append(bufferName);
				error.append("，存储数据到数据仓库出现异常。");
				logger.error(error.toString(), e);
				return exeResult;
			}
			if (logger.isDebugEnabled()) {
				logger.debug("存储BUFF的键值: " + bufferName);
			}
		}
		return true;
	}

	/**
	 * 替换command中的${}数据
	 * 
	 * @param handle
	 *            操作类
	 * @param sourceStr
	 *            需要进行参数替换的内容
	 * @param localVarHm
	 *            局部数据
	 * @return 替换后的command指令
	 */
	@SuppressWarnings("unchecked")
	private static String transformCommand(OperaterHandle handle,
			String sourceStr, Map<String, VarBean> localVarHm) {
		// 虽然String类型参数传递也是地址传递，但是由于String是final类型因此，方法内修改不影响方法外参数原值
		String cmd = sourceStr;

		// 先找出包含${...}的内容
		Pattern p = Pattern.compile("\\$\\{.+?\\}");
		Matcher m = p.matcher(cmd);
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
			cmd = sbuf.toString();
		}
		return cmd;
	}
}
