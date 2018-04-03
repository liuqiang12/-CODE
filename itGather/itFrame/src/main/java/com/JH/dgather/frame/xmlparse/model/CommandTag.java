package com.JH.dgather.frame.xmlparse.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;

import com.JH.dgather.PropertiesHandle;
import com.JH.dgather.frame.common.cmd.CMDService;
import com.JH.dgather.frame.common.exception.TelnetException;
import com.JH.dgather.frame.util.EchoTester;
import com.JH.dgather.frame.xmlparse.OperaterHandle;
import com.JH.dgather.frame.xmlparse.beans.VarBean;
import com.JH.dgather.frame.xmlparse.beans.VarBeanStatic;
import com.JH.dgather.frame.xmlparse.exception.DataStorageException;
import com.JH.dgather.frame.xmlparse.exception.ProcessException;
import com.JH.dgather.frame.xmlparse.exception.TagException;

public class CommandTag extends BaseTag {
	private Logger logger = Logger.getLogger(CommandTag.class);

	// 如果buffer不为空表示需要保存得到的回显值
	private String buffer = "";
	// 默认的参数值，如果是多个参数，以'|'相隔
	private String defaultArgument = "";
	// 测试模式。存储一个txt文件的路径。如果此属性存在，那么利用当前指令获取的回显为文件的内容。
	private String testmode = "";
	// 标签的内容
	private String commandValue = "";

	// 回显结果
	private String result = "";
	// 是否提取回显
	private boolean isOperater = false;
	private String iscmd = "false";

	//指令不支持
	//% Unrecognized command found at '^' position.
	//% Wrong parameter found at '^' position.
	private static String ERROR_COMMAND_INFO = "found at '^' position.";
	private static String NUABLE_COMMAND_INFO = "is not enabled yet";
	public CommandTag(Element element, BaseTag parent) throws TagException {
		super("command", element, parent);
		this.parse();
	}

	protected void parse() throws TagException {
		String errMsg = "";
		errMsg = this.getTagName() + " 标签规则: \r\n";
		errMsg += "【可选属性】\r\n";
		errMsg += "buffer: 表示需要保存当前指令执行的回显内容，存储的键值为buffer定义的字符串\r\n";
		errMsg += "defaultArgument: 默认的参数值，多个参数以|相隔\r\n";
		errMsg += "testmode: 测试模式，存储一个txt文件的路径。如果此属性存在，那么利用当前指令获取的回显为文件的内容。\r\n";
		errMsg += "【不包含子标签】\r\n";
		errMsg += "标签必须包含内容。例如: <command>display ip</command>";

		/**
		 * 判断command标签的属性值
		 */
		List propLs = this.getElement().attributes();
		if (propLs.size() > 0) {
			for (Iterator it = propLs.iterator(); it.hasNext();) {
				Attribute attr = (Attribute) it.next();
				if (attr.getName().equals("buffer")) {
					this.buffer = attr.getValue();
				} else if (attr.getName().equals("defaultArgument")) {
					this.defaultArgument = attr.getValue();
				} else if (attr.getName().equals("testmode")) {
					this.testmode = attr.getValue();
				} else if (attr.getName().equals("iscmd")) {
					this.iscmd = attr.getValue();
				} else {
					logger.error(this.getTagName() + " 标签不允许包含属性: " + attr.getName());
					logger.error(errMsg);
					throw new TagException(this.getTagName() + " 标签不允许包含属性: " + attr.getName());
				}
			}
		}

		/**
		 * 获取command的内容
		 */
		if (this.getElement().getText().equals("")) {
			logger.error(this.getTagName() + " 标签的内容不能为空。");
			logger.error(errMsg);
			throw new TagException(this.getTagName() + " 标签的内容不能为空。");
		}
		this.commandValue = this.getElement().getText();
	}

	/**
	 * <code>process</code> 执行command指令并提取回显
	 * 
	 * @param tl
	 * @param handle
	 * @param logLs
	 * @param localVarHm
	 *            局部变量仓库
	 * @return 获取的回显
	 * @throws TelnetException
	 * @throws IOException
	 * @throws Exception
	 */
	public String process(CMDService tl, OperaterHandle handle, List<String> logLs, HashMap<String, VarBean> localVarHm) throws TelnetException, IOException, Exception {
		logger.debug("执行command标签操作！");

		/*
		 * modify by gamesdoa in 2012/5/29 添加判断是不是直接读取数据分析
		 */
		Pattern p = Pattern.compile("\\$\\{.+?\\}");
		Matcher m = p.matcher(this.commandValue);

		if (m.find() && this.commandValue.equals(m.group()) && this.iscmd.equals("false")) {// 直接buffer读取数据
			logger.error("直接buffer读取数据");
			this.result = transformCommand(handle, localVarHm, this.commandValue);
			setResultBuffer(logLs, localVarHm, handle);
			return this.result;
		} else {// 取数据
			/*
			 * 文件读取需要获取文件路径 add by gamesdoa 2012/5/29
			 */
			EchoTester et = new EchoTester();
			if (!et.isLandTelnet()) {
				this.testmode = PropertiesHandle.getResuouceInfo("GET_ECHO_FILE_URL");
			}
			// 是否是测试模式
			if (this.testmode != null && !"".equals(this.testmode)) {
				logger.info("testmode:" + this.testmode);
				logger.debug("当前指令是测试模式，将从文件读取回显, 路径为: " + this.testmode);
				logLs.add("当前指令是测试模式，将从文件读取回显, 路径为: " + this.testmode);
				File file = new File(this.testmode);
				if (file.exists()) {
					Reader reader = new FileReader(this.testmode);
					BufferedReader br = new BufferedReader(reader);
					StringBuffer sb = new StringBuffer();
					String data = null;
					while ((data = br.readLine()) != null) {
						sb.append(data + "\r\n");
					}
					this.result = sb.toString();
					br.close();
					reader.close();

					setResultBuffer(logLs, localVarHm, handle);
					// logger.debug("回显信息:" + this.result);
					return this.result;
				} else {
					logger.error("当前指令是测试模式，将从文件读取回显, 但是文件不存在，路径为: " + this.testmode);
					throw new ProcessException("当前指令是测试模式，将从文件读取回显, 但是文件不存在，路径为: " + this.testmode);
				}
			}

			String cmd = transformCommand(handle, localVarHm, this.commandValue);
			logLs.add("执行指令: " + cmd);
			this.result = tl.exeCmd2(cmd, 0);
			logger.debug("command 执行指令: " + cmd + "，回显示：" + this.result);
			try {
				handle.addBuffer("cmd", cmd);
			} catch (Exception e) {
			}
			if (this.result.equals("")) {
				logLs.add("获取的回显为空");
				logger.error("command 执行指令: " + cmd + "，获取的回显为空!");
				throw new ProcessException("command 执行指令: " + cmd + "，获取的回显为空");
			} else {
				if (et.isSaveEcho()) {
					logger.debug("command 执行指令: " + cmd + "，回显信息: \n" + this.result);
				}

				if (this.result.contains(ERROR_COMMAND_INFO)||this.result.contains(NUABLE_COMMAND_INFO)) 
					throw new TelnetException("当前指令是获取到的回显信息是: \n" + this.result+"\n请检查指令是否正确！或者设备是否支持该指令！");
				setResultBuffer(logLs, localVarHm, handle);
			}
			return this.result;
		}
	}

	/**
	 * <code>setResultBuffer</code> 设置回显信息
	 * 
	 * @author gamesdoa
	 * @email gamesdoa@gmail.com
	 * @date 2012-5-24(non-Javadoc)
	 * 
	 * @param handle
	 * @param localVarHm
	 * @param logLs
	 * @throws DataStorageException
	 * @throws ProcessException
	 */
	private void setResultBuffer(List<String> logLs, HashMap<String, VarBean> localVarHm, OperaterHandle handle) throws DataStorageException, ProcessException {
		this.isOperater = true;
		logLs.add("回显为: ");
		logLs.add(this.result);
		// 是否将回显存到buffer中
		logger.debug("buffer:" + buffer);
		if (this.buffer != null && !"".equals(this.buffer)) {
			String bufName = this.transformCommand(handle, localVarHm, this.buffer);
			handle.addBuffer(bufName, this.result);
			logger.debug("存储BUFF的键值: " + bufName);
			// logger.debug("回显为:\r\n" + this.result);
		}
	}

	/**
	 * <code>transformCommand</code> 如果指令中存在需要外界附值的参数，则进行翻译
	 * 
	 * @param handle
	 * @param localVarHm
	 * @param str
	 * @return
	 * @throws DataStorageException
	 * @throws ProcessException
	 */
	private String transformCommand(OperaterHandle handle, HashMap<String, VarBean> localVarHm, String str) throws DataStorageException, ProcessException {
		String cmd = str;
		Pattern p = Pattern.compile("\\$\\{.+?\\}");
		Matcher m = p.matcher(cmd);
		StringBuffer sbuf = new StringBuffer();
		while (m.find()) {
			String rp = "";
			String param = m.group();
			if (!param.matches("\\$\\{\\w+(\\.\\w+)?}$")) {
				logger.error("参数不符合 ${xxx} 或 ${xxx.xxx} 的规则，参数信息为： " + param);
				throw new ProcessException("参数不符合 ${xxx} 或 ${xxx.xxx} 的规则，参数信息为： " + param);
			}

			Pattern pa = Pattern.compile("\\$\\{(\\w+)\\.?(\\w+)?\\}");
			Matcher ma = pa.matcher(param);
			if (ma.find()) {
				String mainKey = ma.group(1);
				String subKey = ma.group(2);
				boolean isFind = false;
				/**
				 * 优先在局部参数中查找
				 */
				if (localVarHm != null && localVarHm.containsKey(mainKey)) {
					VarBean vb = localVarHm.get(mainKey);

					// 判断subKey 是否为null，如果为null,那么参数类型必须为List，否则为HashMap
					if (subKey == null) {
						if (vb.getType().equals(VarBeanStatic.TYPE_STRING)) {
							rp = (String) vb.getObject();
							isFind = true;
						} else {
							logger.error("局部数据仓库中，键值为: " + mainKey + "的数据类型必须是String类型, 当前类型为: " + vb.getType());
							throw new ProcessException("局部数据仓库中，键值为: " + mainKey + "的数据类型必须是String类型, 当前类型为: " + vb.getType());
						}
					} else {
						if (vb.getType().equals(VarBeanStatic.TYPE_HASHMAP)) {
							HashMap<String, VarBean> hm = (HashMap<String, VarBean>) vb.getObject();
							if (!hm.containsKey(subKey)) {
								logger.error("局部数据仓库中不存在键值为： " + subKey + "的数据!");
								throw new ProcessException("局部数据仓库中不存在键值为： " + subKey + "的数据!");
							}

							VarBean obj = hm.get(subKey);
							if (obj.getType().equals(VarBeanStatic.TYPE_STRING)) {
								rp = (String) obj.getObject();
								isFind = true;
							} else {
								logger.error("局部数据仓库中，键值为: " + subKey + "的数据不是String类型！当前类型为:" + obj.getClass().getName());
								throw new ProcessException("局部数据仓库中，键值为: " + subKey + "的数据不是String类型！当前类型为:" + obj.getClass().getName());
							}
						} else {
							logger.error("局部数据仓库中，键值为：" + mainKey + "的数据须为HashMap，因为有通过键值" + subKey + "提取数据！当前类型为：" + vb.getType());
							throw new ProcessException("局部数据仓库中，键值为：" + mainKey + "的数据须为HashMap，因为有通过键值" + subKey + "提取数据！当前类型为：" + vb.getType());
						}
					}
				}

				/**
				 * 在全局参数中查找
				 */
				if (!isFind) {
					VarBean gvb = handle.getData(mainKey, subKey);
					if (gvb.getType().equals(VarBeanStatic.TYPE_STRING)) {
						rp = (String) gvb.getObject();
						isFind = true;
					} else {
						logger.error("主键值为：" + mainKey + "的全局数据列表中，键值为:" + subKey + "的数据类型必须为String!");
						throw new ProcessException("主键值为：" + mainKey + "的全局数据列表中，键值为:" + subKey + "的数据类型必须为String!");
					}
				}
			}

			m.appendReplacement(sbuf, rp);
		}
		m.appendTail(sbuf);
		cmd = sbuf.toString();

		return cmd;
	}

	/**
	 * @return the buffer
	 */
	public String getBuffer() {
		return buffer;
	}

	/**
	 * @return the defaultArgument
	 */
	public String getDefaultArgument() {
		return defaultArgument;
	}

	/**
	 * @return the commandValue
	 */
	public String getCommandValue() {
		return commandValue;
	}

	/**
	 * @return the result
	 */
	public String getResult() throws TagException {
		if (!this.isOperater) {
			logger.error("无法得到回显结果，请选运行process方法！");
			throw new TagException("无法得到回显结果，请选运行process方法！");
		}
		return result;
	}

	/*
	 * add @author gamesdoa
	 * 
	 * @email gamesdoa@gmail.com
	 * 
	 * @date 2012-5-28
	 * 
	 * @return
	 */
	public String getTestmode() {
		return testmode;
	}

	/*
	 * add @author gamesdoa
	 * 
	 * @email gamesdoa@gmail.com
	 * 
	 * @date 2012-5-28
	 * 
	 * @param testmode
	 */
	public void setTestmode(String testmode) {
		this.testmode = testmode;
	}

}
