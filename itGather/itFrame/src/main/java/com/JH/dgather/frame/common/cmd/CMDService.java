package com.JH.dgather.frame.common.cmd;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.JH.dgather.frame.common.bean.DeviceInfoBean;
import com.JH.dgather.frame.common.cmd.bean.FlowPointBean;
import com.JH.dgather.frame.common.cmd.bean.TelnetFlowBean;
import com.JH.dgather.frame.common.exception.TelnetException;
import com.JH.dgather.frame.util.DateFormate;
import com.JH.dgather.frame.util.EchoTester;
import com.JH.dgather.frame.util.EncryptAnalyse;

/**
 * @author muyp 指令执行模式的父类
 */
public abstract class CMDService {
	private Logger logger = Logger.getLogger(CMDService.class.getName());

	private int DELAY = 10000;// 读延迟毫秒
	//private int ConnectTimeout = 120000;// 链接的延迟时间毫秒

	//public int SoTimeout = 30000;// SOCKET阻塞读取数据延迟时间10秒钟
	//private boolean flag = false;// 用于标识连接过程是否结束
	protected TelnetFlowBean flow;
	protected InputStream in;// 接收通道
	protected PrintStream out;// 输入通道
	protected String orderPrompt;// 指令输入提示符，便于快速的判断指令执行结束
	private boolean isConnect;
	private boolean isFinish = false;// 数据读取是否结束标志，因waitThread的 原因将其放在这里申明


	private String moreStr = "more";
	private String morAlcart = "press any key to continue (q to quit)";
	private String moreHwoltalcart = "more ( press 'q' to break )";
	private String moreBegs, moreEnds;
	//烽火
	private String morefiberhome = "press any key to continue ctrl+c to stop";
	
	private String pattern = "[\\s]{0,2}\\-*\\[\\d+(A|B|C|D|a|b|c|d)\\s+\\[\\d+(A|B|C|D|a|b|c|d)";
	private String patternG = "\\[\\d+(A|a)\r\n";
	private String patternN = "-*\\[\\d+(N|n)";
	//private WorkingAreaBean ipwab = null;
	private WaitThread waitThread;

	/*
	 * 登陆入口
	 */
	public void land(TelnetFlowBean telnetFlowInfo) throws TelnetException {
		this.flow = telnetFlowInfo;
		if (this.flow.getFlowList().size() < 1) {
			throw new TelnetException(this.flow.getDevice().getDeviceName() + "登陆流程没有设置");
		}

		// ----确定登陆设备BEGIN，考虑可能多级跳转，提取第一个跳转登陆设备,实际目前仅保证支持1级跳转，
		// 后面的代码仅支持1级跳
		DeviceInfoBean lastJumpDevice;
		lastJumpDevice = getLastJumpFlow(telnetFlowInfo).getDevice();
		// **注意这里仅考虑了一次跳转，通过跳转设备的鉴权
		if (telnetFlowInfo.getJumpFlow() != null) {// 有跳转
			telnetDevice(telnetFlowInfo.getJumpFlow().getFlowList(), lastJumpDevice);
			telnetJumpToAim(telnetFlowInfo.getFlowList(), telnetFlowInfo.getVPNparam(), telnetFlowInfo.getDevice());
		} else {
			telnetDevice(telnetFlowInfo.getFlowList(), telnetFlowInfo.getDevice());
		}
		waitThread = new WaitThread();
	}

	/**
	 * 从整个登陆流程中提取首先登陆设备流程
	 * 
	 * @param telnetFlowInfo
	 * @return
	 */
	private TelnetFlowBean getLastJumpFlow(TelnetFlowBean telnetFlowInfo) {
		TelnetFlowBean jumpTelnetFlowTmp = telnetFlowInfo.getJumpFlow();
		TelnetFlowBean jumpTelnetFlow = null;
		while (jumpTelnetFlowTmp != null) {
			jumpTelnetFlow = jumpTelnetFlowTmp;
			jumpTelnetFlowTmp = jumpTelnetFlow.getJumpFlow();
		}
		if (jumpTelnetFlow != null)
			return jumpTelnetFlow;
		else
			return telnetFlowInfo;
	}

	/**
	 * 通过跳转设备进入目标设备
	 * 
	 * @param flowList
	 * @param aimDevice
	 * @throws TelnetException
	 */
	private void telnetJumpToAim(ArrayList<FlowPointBean> flowList, String vpnPara, DeviceInfoBean aimDevice) throws TelnetException {
		// 在跳转是检查是否是VPN连接,如果是那么就有命令区别
		String cmd="";
		if(aimDevice.getLoginModel()==0)
			cmd = "telnet ";
		else
			cmd = "ssh ";
		if ((vpnPara == null) || (vpnPara.equals(""))) {
			write(cmd + aimDevice.getDeviceIp() + " " + aimDevice.getTelnetPort());
		} else {
			write(cmd + vpnPara + " " + aimDevice.getDeviceIp() + " " + aimDevice.getTelnetPort());
		}
		// telnetDevice(flowList,aimDevice);
		if (!doLogin(flowList, aimDevice.getLoginUser(), aimDevice.getLoginPsw(), aimDevice.getPriviledge(), aimDevice.getLoginModel(),true)) {
			logger.error(aimDevice.getDeviceName() + "设备登陆认证失败");
			try {
				disconnect(aimDevice.getDeviceName());
				isConnect = false;
			} catch (Exception e) {
				logger.error(aimDevice.getDeviceName() + "设备的链接释放失败");
				throw new TelnetException(aimDevice.getDeviceIp() + "的连接释放失败");
			}
			// throw new TelnetException(2,"telnet认证失败，请检查目标机器的用户名密码配置");
			throw new TelnetException("通过跳转后，登陆到" + aimDevice.getDeviceIp() + "失败");
		}

	}

	/**
	 * 登陆到目标设备,没有跳转
	 * 
	 * @param aimDevice
	 */
	private void telnetDevice(ArrayList<FlowPointBean> flowList, DeviceInfoBean aimDevice) throws TelnetException {
		if (!connectDevice(aimDevice.getDeviceIp(), aimDevice.getTelnetPort(), aimDevice.getLoginUser(), new EncryptAnalyse().MNSA_Decrypt(aimDevice.getLoginPsw()))) {
			throw new TelnetException("连接" + aimDevice.getDeviceIp() + "设备失败");
		}
		in = createReader();
		out = createWriter();
		isConnect = true;
		if (!doLogin(flowList, aimDevice.getLoginUser(), aimDevice.getLoginPsw(), aimDevice.getPriviledge(), aimDevice.getLoginModel(),false)) {
			logger.error(aimDevice.getDeviceName() + "设备登陆认证失败");
			try {
				disconnect(aimDevice.getDeviceName());
				isConnect = false;
			} catch (Exception e) {
				logger.error(aimDevice.getDeviceName() + "设备的链接释放失败");
				throw new TelnetException(aimDevice.getDeviceIp() + "的连接释放失败");
			}
			throw new TelnetException(aimDevice.getDeviceName()+"telnet认证失败，请检查目标机器的用户名密码配置");
		}
	}

	/*
	 * 连接设备。目前使用的SSH包在连接设备时直接做了账号密码认证过程
	 */
	public abstract boolean connectDevice(String ip, int port, String loginUser, String loginpsw) throws TelnetException;

	/**
	 * 断开连接
	 * 
	 * @throws IOException
	 */
	public abstract void disconnect(String deviceName);

	/**
	 * @param command
	 */
	public abstract void write(String command);

	public abstract PrintStream createWriter();

	public abstract InputStream createReader();

	public abstract Boolean ifLoginAuth();// 在DOLOGIN中判断是否要做登陆验证，只要是SSH就返回false,telnet返回true

	public abstract Boolean ifReadOver(String conPrompt, String prompt);// 在读取回显信息时判断是读取完成

	/**
	 * 登陆认证
	 * 
	 * @param flow
	 *            =登陆流程
	 * @param InputStream
	 *            =读取通道
	 * @param PrintStream
	 *            =输出通道
	 * @param usrName
	 *            =登陆账号
	 * @param psw
	 *            =登陆密码
	 * @param privilegePsw
	 *            =特权密码
	 * @return 是否认证成功
	 */
	private boolean doLogin(ArrayList<FlowPointBean> flow, String usrName, String psw, String privilegePsw, int loginModel,boolean isJump) throws TelnetException {

		String sR = "";// 采集到的回显内容
		String sRSrc = "";// 采集到的回显内容
		String sP;// 回写内容
		String sT;// 用于匹配的回显内容
		boolean b = false;// 是否满足回显要求
		try {
			psw = new EncryptAnalyse().MNSA_Decrypt(psw);
			if (privilegePsw != null){
				privilegePsw = new EncryptAnalyse().MNSA_Decrypt(privilegePsw);
				logger.info(this.flow.getDevice().getDeviceName() +"【"+ this.flow.getDevice().getDeviceIp() +"】" +"特权密码："+privilegePsw);
			}
		} catch (Exception e) {
			logger.info(this.flow.getDevice().getDeviceName() + "【"+ this.flow.getDevice().getDeviceIp() +"】" +"设备没有配置密码或特权密码");
		}
		try {
			int step = 0;
			Iterator<FlowPointBean> it = flow.iterator();
			FlowPointBean flowPoint;
			while (it.hasNext()) {
				flowPoint = it.next();
				step++;
				sP = flowPoint.getFillParam();
				// 针对SSH做特殊处理,因为SSH在连接时就通过了账号密码认证
				// logger.info("step：" + step + ";认证SSH：" + (!ifLoginAuth() &&
				// (sP.equals("%usr%") || sP.equals("%psw%"))));
				if ((((loginModel == 1) && !isJump ? true : false) && (sP.equals("%usr%") || sP.equals("%psw%"))))
					continue;
				
				sT = flowPoint.getPrompt().toLowerCase();
				logger.info("获取回显提示符"+sT);
				if (sT.indexOf("*") == -1) {// 不包含*
					if (sT.length() == 0) {// 为空字符则表示不做任何判断
						sR = "";
						sRSrc = "";
					} else {
						sRSrc = readPrompt(DELAY, sT).trim();// 不区分大小写;
						sR = sRSrc.toLowerCase();
					}
					b = sR.equals(sT);
				} else {
					if (sT.startsWith("*") && sT.endsWith("*")) {
						sT = sT.substring(1, sT.length() - 1);
						sRSrc = readPrompt(DELAY, sT).trim();// 不区分大小写;
						sR = sRSrc.toLowerCase();
						if (sR.indexOf(sT) > -1)
							b = true;
					} else {
						if (sT.startsWith("*")) {
							sT = sT.substring(1, sT.length());
							sRSrc = readPrompt(DELAY, sT).trim();// 不区分大小写;
							sR = sRSrc.toLowerCase();
							logger.info(this.flow.getDevice().getDeviceName() + "sT:" + sT + "；获取的回显：" + sR + ";认证：" + sR.endsWith(sT));
							b = sR.endsWith(sT);
						} else {
							sT = sT.substring(0, sT.length() - 1);
							sRSrc = readPrompt(DELAY, sT).trim();// 不区分大小写;
							sR = sRSrc.toLowerCase();
							b = sR.startsWith(sT);
						}
					}
				}
				logger.info(this.flow.getDevice().getDeviceName() + "【"+ this.flow.getDevice().getDeviceIp() +"】" +">>登录流程第 " + step + " 步!");
				logger.info(this.flow.getDevice().getDeviceName() + "【"+ this.flow.getDevice().getDeviceIp() +"】" +">>设备回显: " + sR);
				logger.info(this.flow.getDevice().getDeviceName() + "【"+ this.flow.getDevice().getDeviceIp() +"】" +">>流程内容: " + flowPoint.getFillParam());
				logger.info(this.flow.getDevice().getDeviceName() + "【"+ this.flow.getDevice().getDeviceIp() +"】" +">>如果提示符与定义的相同则执行下步，否则就认为失败:" + b);
				if (b)// 如果提示符与定义的相同则执行下步，否则就认为失败
				{
					sP = flowPoint.getFillParam();
					boolean isWrite = true;
					if ((sP.endsWith("%")) && (sP.startsWith("%"))) {// 是参数保留字
						sP = sP.substring(1, sP.length() - 1);
						logger.info(this.flow.getDevice().getDeviceName() + " sP:" + sP);
						if (sP.equals("usr")) {
							sP = usrName;
							logger.info(this.flow.getDevice().getDeviceName() + " usr:" + sP);
						}
						if (sP.equals("psw")) {
							sP = psw;
							logger.info(this.flow.getDevice().getDeviceName() + " psw:" + sP);
						}
						if (sP.equals("privilegePsw")) {
							sP = privilegePsw;
							logger.info(this.flow.getDevice().getDeviceName() + " privilegePsw:" + sP);
						}
						if (sP.equals("order")) {// 认为登陆结束，可以开始输入指令
							isWrite = false;
							orderPrompt = sP;
							logger.info(this.flow.getDevice().getDeviceName() + " sP:" + orderPrompt);
							String[] resArr = sRSrc.split("[\r\n]+");
							orderPrompt = resArr[resArr.length - 1];
							if(orderPrompt.length() > 0){
							  orderPrompt = getOrderPromptPattern(orderPrompt);
							}
							logger.info(this.flow.getDevice().getDeviceName() + " orderPrompt:" + orderPrompt);
						}
					}
					logger.info(this.flow.getDevice().getDeviceName() + "【"+ this.flow.getDevice().getDeviceIp() +"】" +" 执行完sP:" + sP);
					if (isWrite) {
						logger.info(this.flow.getDevice().getDeviceName() + "【"+ this.flow.getDevice().getDeviceIp() +"】" +" writeFillParam(sP):" + sP);
						writeFillParam(sP);
					}
				} else// ?myp是否需要针对反馈的信息做特殊处理
				{
					String errorLogs = this.flow.getDevice().getDeviceName() + "【"+ this.flow.getDevice().getDeviceIp() +"】" +"登陆失败，提示信息:" + sR + "，登陆流程为：\"";
					Iterator<FlowPointBean> itFlow = this.flow.getFlowList().iterator();
					FlowPointBean point;
					while (itFlow.hasNext()) {
						point = itFlow.next();
						errorLogs = errorLogs + point.getPrompt() + "->";
					}
					logger.error(errorLogs + "\"");
					throw new TelnetException(this.flow.getDevice().getDeviceName() + "【"+ this.flow.getDevice().getDeviceIp() +"】" +"鉴权过程失败, 流程提示符: " + flowPoint.getFillParam());
				}
				logger.info(this.flow.getDevice().getDeviceName() + "【"+ this.flow.getDevice().getDeviceIp() +"】" +" 是否需要再继续执行：" + it.hasNext());
			}
		} catch (Exception e) {
			logger.error(this.flow.getDevice().getDeviceName() + "【"+ this.flow.getDevice().getDeviceIp() +"】" +"telnet登录失败：", e);
			return false;
		}
		logger.info(this.flow.getDevice().getDeviceName() + "【"+ this.flow.getDevice().getDeviceIp() +"】" +"返回登录结果：" + b);
		return b;

	}
	
	private String getOrderPromptPattern(String aorderPrompt) {
		String orderPrompt = aorderPrompt;
		boolean isEricsson = orderPrompt.startsWith("[local]");
		if (isEricsson) {
			orderPrompt = orderPrompt.substring("[local]".length());
		}
		orderPrompt = orderPrompt.replaceAll("\\.", "\\\\.");
		orderPrompt = orderPrompt.replaceAll("\\*", "\\\\*");
		orderPrompt = orderPrompt.replaceAll("\\^", "\\\\^");
		orderPrompt = orderPrompt.replaceAll("\\$", "\\\\$");
		orderPrompt = orderPrompt.replaceAll("\\+", "\\\\+");
		orderPrompt = orderPrompt.replaceAll("\\?", "\\\\?");
		orderPrompt = orderPrompt.replaceAll("\\|", "\\\\|");
		orderPrompt = orderPrompt.replaceAll("\\[", "\\\\[");
		orderPrompt = orderPrompt.replaceAll("\\]", "\\\\]");
		orderPrompt = orderPrompt.replaceAll("\\{", "\\\\{");
		orderPrompt = orderPrompt.replaceAll("\\}", "\\\\}");
		orderPrompt = orderPrompt.replaceAll("\\(", "\\\\(");
		orderPrompt = orderPrompt.replaceAll("\\)", "\\\\)");
		orderPrompt = orderPrompt.replaceAll("\\-", "\\\\-");
		orderPrompt = ".*" + orderPrompt.substring(0, orderPrompt.length() - 1)
				+ ".*" + orderPrompt.charAt(orderPrompt.length() - 1);
		if (isEricsson) {
			orderPrompt = "\\[.*\\]" + orderPrompt;
		}
		return orderPrompt;
	}
//muyp modify 2015-01-15解决登录信息读取不稳定情况
	private String readPrompt(int nDelay, String conPrompt) throws IOException {
		byte[] buff = new byte[10240];
		int ret_read = 0;
		String _line = "";
		String str = "";
		StringBuffer prompt = new StringBuffer();
		isFinish = false;
		try {
			while (isConnect) {
				if (in.available() < 1) {
					Date begTime = new Date();
					while (true) {
						if (in.available() > 0)
							break;
						if (DateFormate.comparaSecon(new Date(), begTime) * 1000 > nDelay) {
							//logger.info("延迟线程等待超时");
							isFinish = true;
							break;
						}
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
						}
					}
					
				}
				if (isFinish){
					logger.info("读取超时跳出回显提取。"+str);
					break;
				}
				ret_read = in.read(buff);
				if (ret_read > 0) {
					String line = new String(buff, 0, ret_read, "GBK");
					str += line;
//					_line += line;
//					muyp 先不考虑登陆时有more的情况
//					if (ifMore(line) || ifMore(_line)) {
//						writeSpaceToNextPage();
//						_line = "";
//						continue;
//					}
					
				}else{
					logger.info("读取超时跳出回显提取。"+str);
					break;
				}
				// 为保证快速登录做了以下处理
				if (ifReadOver(conPrompt, str))
				{
					logger.info("读取到指令提示符跳出，回显："+str);
					break;
				}
			}

		} catch (SocketTimeoutException e) {
			logger.error(this.flow.getDevice().getDeviceName() + "登录失败", e);
		}
		catch (IOException e) {
			logger.error(this.flow.getDevice().getDeviceName() + "读取回显时发生IOException错误", e);
		} catch (Exception ex) {
			logger.error(this.flow.getDevice().getDeviceName() + "发生错误:", ex);
		}
		return str;
	}
	
	
	private String readPrompt_old(int nDelay, String conPrompt) throws IOException {
		StringBuffer prompt = new StringBuffer();
		isFinish = false;
		try {
			while (isConnect) {
				logger.info(this.flow.getDevice().getDeviceName() + "连接状态：" + in.available());
				if (in.available() < 1) {
					Date begTime = new Date();
					while (true) {
						if (in.available() > 0)
							break;
						if (DateFormate.comparaSecon(new Date(), begTime) * 1000 > nDelay) {
							isFinish = true;
							break;
						}
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
						}
					}
				}
				if (isFinish){
					logger.info("读取超时跳出回显提取。"+prompt.toString());
					break;
				}
				char ch = (char) in.read();
				//logger.info(this.flow.getDevice().getDeviceName() + "当前字符：" + ch + ";对应的编码：" + (int) ch);
				if (ch == 65535) {
					logger.error(this.flow.getDevice().getDeviceName() + "被对方中断");
					break;
					// 一些链接在对方已经断开的情况，却无法判断连接是否正常，并且直至可以read字符出来
				}
				prompt.append(ch);
				
				if (ifMore(prompt.toString())) {
					writeSpaceToNextPage();
					// logger.info(this.flow.getDevice().getDeviceName() + "登陆当前字符：" + ch + ";对应的编码：" + (int) ch);
					continue;
				}

				// 为保证快速登录做了以下处理
				if (ifReadOver(conPrompt, prompt.toString()))
				{
					logger.info("读取到指令提示符跳出，回显："+prompt.toString());
					break;
				}
			}

		} catch (SocketTimeoutException e) {
			logger.error(this.flow.getDevice().getDeviceName() + "登录失败", e);
		}

		return prompt.toString();
	}

	private boolean writeFillParam(String fillParam) {
		write(fillParam);
		return true;
	}
	
	
	public boolean isPrintable(char c) {
		if (Character.isISOControl(c)
				&& ((!Character.isWhitespace(c)) || (((c & 0xFF) >= 0x1C))
						&& ((c & 0xFF) <= 0x1F))) {
			return false;
		}
		return true;
	}
	
	private String readData(int nDelay, String prompt, String command) throws TelnetException {
		return readData(nDelay,prompt,command,null,null,true);
	}

	/**
	 * 
	 * @param nDelay
	 * @param prompt
	 * @param command
	 * @param choosePattern 匹配是否出现交互输入选择命令的模式
	 * @param chooseCommand 需要输入的选择命令
	 * @param chooseRunOnce 选择命令是否只出现一次，例如取配置的时候只会出现一次“{ <cr>|ont<K>|port<K>|section<K>|service-port<K>|simple<K>||<K> }: ”
	 *                      设置为true是为了避免无谓的判断，浪费处理效率
	 * @return
	 * @throws TelnetException
	 */
	private String readData(int nDelay, String prompt, String command,
			Pattern choosePattern, String chooseCommand, boolean chooseRunOnce)
			throws TelnetException {
		byte[] buff = new byte[10240];
		int ret_read = 0;
		boolean stop = false;
		isFinish = false;
		boolean isNeedMatchChooseCMD = true;
		StringBuilder resultBuf = new StringBuilder();
		Pattern endPattern = Pattern.compile(prompt);
		long startTime = System.currentTimeMillis();
		try {
			do {
				// muyp modify 2014-12-5
				try {
					ret_read = in.read(buff);
				} catch (SocketTimeoutException e) {
					logger.error("设备:" + this.flow.getDevice().getDeviceName()
							+ "读取回显时发生SocketTimeoutException错误", e);
					logger.error(System.currentTimeMillis() - startTime + ","
							+ DELAY + ",," + ret_read);
					if (System.currentTimeMillis() - startTime > DELAY) {
						logger.error("设备:" + flow.getDevice().getDeviceName()
								+ "执行" + command + "读取时间超时：结束读取!");
						break;
					} else {
						logger.error("设备:" + flow.getDevice().getDeviceName()
								+ "执行" + command + "未到超时时间" + DELAY + "：继续读取!");
					}
					continue;
				}

				if (ret_read != -1) {
					String curString = new String (buff,0,ret_read).replaceAll("[^\\p{Print}\r\n]", "");
					resultBuf.append(curString);
					String lastLine = null;
					int p1 = resultBuf.length() - 1;
					// 找到第一个非\r\n字符
					for (int index = resultBuf.length() - 1; index >= 0; index--) {
						if (resultBuf.charAt(index) != '\n'
								&& resultBuf.charAt(index) != '\r') {
							p1 = index;
							break;
						}
					}
					// 找到第二个\r\n字符
					int p2 = p1;
					for (int index = p1; index >= 0; index--) {
						if (resultBuf.charAt(index) == '\n'
								|| resultBuf.charAt(index) == '\r') {
							p2 = index;
							lastLine = resultBuf.substring(p2 + 1, p1 + 1);
							break;
						}
					}
					if (lastLine != null) {
						Matcher matcher = endPattern.matcher(lastLine);
						if (matcher.find()) {
							logger.info("设备:"
									+ flow.getDevice().getDeviceName()
									+ "获取到结束标记，表示该命令执行完毕，结束读!" + lastLine);
							break;
						}

						if (ifMore(lastLine)) {
							logger.debug("设备:"
									+ flow.getDevice().getDeviceName()
									+ "取得more，需要输入空命令获取回显:" + lastLine);
							//删除多余的\r\n
/*							for (int index = p2; index >= 0; index--) {
								if (resultBuf.charAt(index) != '\n'
										&& resultBuf.charAt(index) != '\r') {
									p2 = index;
									break;
								}
							}*/
							resultBuf.delete(p2 + 1, resultBuf.length());
							String tmpStr = substringWithoutMoreChars(lastLine);
							if(tmpStr != null){
								logger.debug("设备:"
										+ flow.getDevice().getDeviceName()
										+ "从more行中分离出有用行：" + tmpStr);
								resultBuf.append(tmpStr);
							}
							writeSpaceToNextPage();
						} else if (choosePattern != null
								&& isNeedMatchChooseCMD) {
							matcher = choosePattern.matcher(lastLine);
							if (matcher.find()) {
								logger.info("设备:"
										+ flow.getDevice().getDeviceName()
										+ "获取到选择输入命令提示：" + lastLine);
								write(chooseCommand);
								if (chooseRunOnce) {
									isNeedMatchChooseCMD = false;
								}
							}
						}
					}
				} else {
					stop = true;
					logger.info("设备:" + flow.getDevice().getDeviceName() + "执行"
							+ command + "，ret_read 返回 -1 数据已经读取完。");
				}
			} while (!stop);

		} catch (SocketTimeoutException e) {
			logger.error(this.flow.getDevice().getDeviceName()
					+ "读取回显时发生SocketTimeoutException错误", e);
		} catch (IOException e) {
			logger.error(this.flow.getDevice().getDeviceName()
					+ "读取回显时发生IOException错误", e);
		} catch (Exception ex) {
			if(this.flow == null){
				logger.error("设备:"+prompt+"的采集任务可能已经超时被终止！",ex);
			}else{
				logger.error("设备:"+this.flow.getDevice().getDeviceName() + "发生错误:", ex);
			}
		}
		String result = trim(resultBuf.toString());
		if (logger.isDebugEnabled())
			logger.debug("设备:" + flow.getDevice().getDeviceName() + "执行"
					+ command + "获取的完整回显:" + result);
		return result;
	}
	
	private String moveMore(String str,int factory){
		if(str == null)return null;
		String pattern = " *\\-{2,}\\s*more\\s*\\-{2,}";
		String tempPattern = " *\\<*\\-{2,}\\s+more\\s+\\-{2,}\\>";
//		if(factory==1){
//			tempPattern = " \\<*\\-{2,}\\s+more\\s+\\-{2,}\\>";
//		}else{
//			tempPattern = pattern;
//		}
		boolean isRun = true;
		do {
			Pattern p = Pattern.compile(tempPattern,Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(str);
			if (m.find()) {
				int i = str.indexOf(m.group());
				str = str.substring(0, i) + str.substring(i + m.group().length(), str.length());
			} else {
				p = Pattern.compile(pattern,Pattern.CASE_INSENSITIVE);
				m = p.matcher(str);
				if (m.find()) {
					tempPattern = pattern;
					int i = str.indexOf(m.group());
					str = str.substring(0, i) + str.substring(i + m.group().length(), str.length());
				}else{
					isRun = false;
				}
			}
		} while (isRun);
		return str;
	}
	
	private String moveCursor(String str,int factory) {
		if (str == null)
			return null;
		String pattern = "( |-){0,}\\[(\\d+)(D|d)";
		if(factory==1){
			pattern ="( |-){0,}\\[\\d*(M|m)(\\r)*";
		}
		boolean isRun = true;
		// System.out.println("第一次正则匹配：" + m.find());
		do {
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(str);
			if (m.find()) {
				int i = str.indexOf(m.group());
				str = str.substring(0, i) + str.substring(i + m.group().length(), str.length());
			} else {
				isRun = false;
			}
		} while (isRun);
//		logger.info("处理光标之后的回显：" + str);
		/*byte[] b = str.getBytes();
		StringBuffer s = new StringBuffer("");
		for (byte c : b) {
			s.append(c).append(":");
		}
		str = s.toString();
		logger.info(str);
		*//**
		 *202d2d2d6d6f72652d2d2d2000
		  0a0d20202020202020202020200d
		 *//*
		str = str.replaceAll("32:45:45:45:109:111:114:101:45:45:45:32:0:", "");
		str = str.replaceAll("10:13:32:32:32:32:32:32:32:32:32:32:32:13:", "");
		String[] strArray = str.split(":");
		s = new StringBuffer("");
		for (String c : strArray) {
			if (c == null || c.equalsIgnoreCase(""))
				continue;
			s.append((char) Integer.parseInt(c));
		}
		str = s.toString();*/
		return str;
	}

	
	// 判断是否是屏幕断页 支持的格式为“----*more*-------”
	private boolean ifMore(String comparaStr) {
		comparaStr = comparaStr.toLowerCase().trim();
		int n = comparaStr.indexOf(moreStr);
		if (n > -1) {
			moreBegs = comparaStr.substring(0, n).replace(" ", "");
			moreEnds = comparaStr.substring(n + moreStr.length(), comparaStr.length()).replace(" ", "");
			// alert by gamesdoa 为了华为OLT设备的登录，增加begs.endsWith("--")条件
			if ((moreBegs.startsWith("--") || moreBegs.endsWith("--")) &&(moreEnds.startsWith("--")|| moreEnds.endsWith("--"))) {
				return true;
			}
		} else {
			if (comparaStr.indexOf(morAlcart) != -1) {
				return true;
			} else if (comparaStr.indexOf(moreHwoltalcart) != -1) {
				return true;
			} else if (comparaStr.indexOf(morefiberhome) != -1) {
				return true;
			}
		}
		return false;
	}
	
	private String substringWithoutMoreChars(String moreline) {
		int index = moreline.toLowerCase().indexOf("---- more");
		if (index != -1) {
			String tmpStr = moreline.substring(0, index);
			if (!tmpStr.trim().equals("")) {
				return tmpStr;
			}
		}
		return null;
	}
	

	// 判断是否是屏幕断页 支持的格式为“----*more*-------”并且非独立行存在
	/*private String dealMore_noAlone(String AnalyseStr) {
		String comparaStr = AnalyseStr;
		String s = "more";
		String begs;
		// comparaStr = comparaStr.toLowerCase().trim();muyp 2011-01-26
		int n = comparaStr.toLowerCase().indexOf(s);
		if (n > -1) {// 包含more
			begs = comparaStr.substring(0, n).replace(" ", "");
			// ends =
			// comparaStr.substring(n+s.length(),comparaStr.length()).replace(" ","");
			if (begs.startsWith("--")) {// 以--开头
				n = n + s.length();
				int m = comparaStr.lastIndexOf("--", n);
				if (m > -1)// 以--定位more标志的结束
					return comparaStr.substring(n + m + 2).trim(); // 2是--的长度
				else {
					m = comparaStr.lastIndexOf(" -", n);
					if (m > -1)// 以 -定位more标志的结束
						return comparaStr.substring(n + m + 2).trim();
				}
			}
		}
		return AnalyseStr;

	}*/


	private void writeSpaceToNextPage() {
		synchronized (out) {
			try {
				out.write(" ".getBytes());
				out.flush();
			} catch (IOException e) {
				logger.error("", e);
			}

		}
	}

	/**
	 * 进行特殊字符过滤
	 * 
	 * @param str
	 *            传入要过滤的字符串
	 * @return 返回过滤后的字符串
	 * modify by gamesdoa 2012/9/10 整体过滤光标的显示
	 */

	 private String trim(String str) {
		//logger.info(this.flow.getDevice().getDeviceName() + "原始回显：" + str);
		if (str == null)
			return null;
		//cisco采集的配置出现如下乱码
		if(this.getFlow().getDevice().getFactory() == 1){
			str = str.replaceAll("\\[7m\\[m\\s+", "");
			str = str.replaceAll("\\[K\\s+", "");
		}else if(this.getFlow().getDevice().getFactory() == 0 || this.getFlow().getDevice().getFactory() == 61){
			//华为，华三采集的配置出现如下乱码
			str = str.replaceAll("\\[(?:42|16|37)D\\s+\\[(?:42|16|37)D", "");
		}
		/*byte[] b = str.getBytes();
		StringBuffer s = new StringBuffer("");
		for (byte c : b) {
			s.append(c).append(":");
		}
		str = s.toString().replaceAll("13:10:32:45:32:0:13:10:32:45:32:0:13:10:32:45:32:0:13:10:32:45:32:0:13:10:32:45:32:0:13:10:32:45:32:0:13:10", "13:10:13:10");
		str = str.toString().replaceAll("13:10:32:45:32:0:13:10:32:45:32:0:13:10:32:45:32:0:13:10:32:45:32:0:13:10", "13:10:32");
		str = str.toString().replaceAll("13:10:32:45:32:0:13:10:32:45:32:0:13:10:32:45:32:0", "");
		str = str.toString().replaceAll("13:10:32:45:32:0:13:10:32:45:32:0:13:10", "32");
		str = str.toString().replaceAll("13:10:32:45:32:0:13:10", "");
		str = str.toString().replaceAll("13:10:32:45:32:0", "");
		str = str.replaceAll("::", ":");
		
		String[] strArray = str.split(":");
		s = new StringBuffer("");
		for (String c : strArray) {
			if (c == null || c.equalsIgnoreCase(""))
				continue;
			s.append((char) Integer.parseInt(c));
		}
		str = s.toString();

		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		if (m.find()) {
			str = m.replaceAll("");
			p = Pattern.compile(patternG);
			m = p.matcher(str);
			if (m.find()) {
				str = m.replaceAll("");
			}
		}

		p = Pattern.compile(patternN);
		m = p.matcher(str);
		if (m.find()) {
			str = m.replaceAll("");
		}
*/
		//logger.info(this.flow.getDevice().getDeviceName() + "替换特殊字符后回显：" + str);
		return str;
		//
		/*StringBuffer sb = new StringBuffer();
		String[] strArray = str.split("\r\n");
		
		for (String temp : strArray) {
			if (temp.indexOf("[42D") != -1) {
				temp = temp.substring(temp.lastIndexOf("[42D") + "[42D".length());
			} else if (temp.indexOf("[37D") != -1) {
				temp = temp.substring(temp.lastIndexOf("[37D") + "[37D".length());
			}
			temp = dealMore_noAlone(temp);
			if (temp.trim().length() <= 0)
				continue;
			if (!ifMore(temp)) {
				sb.append(temp);
				sb.append("\r\n");
			}
		}
		strArray = null;
		str = null;

		return sb.toString();*/

	}


	/**
	 * 控制单一机器只保持1条连接
	 */
/*	public void telnetWakeNWait(DeviceInfoBean lastJumpDevice) {
		TelnetWorkingArea area = TelnetWorkingArea.getInstance();
		synchronized (area) {
			if (area.hasJumpIp(lastJumpDevice.getDeviceIp())) {
				ipwab = area.getLock(lastJumpDevice.getDeviceIp());
				synchronized (ipwab) {
					if (ipwab.isUsed()) {

						try {
							ipwab.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} else {
						ipwab.setUsed(true);
					}
				}
			} else {
				ipwab = new WorkingAreaBean();
				ipwab.setJumpIP(lastJumpDevice.getDeviceIp());
				ipwab.setUsed(true);
				area.addJumpIp(lastJumpDevice.getDeviceIp(), ipwab);
			}
		}
	}*/


	/**
	 * @param command
	 *            要写给服务端的数据（命令）
	 * @param delay
	 *            操作最多持续时间。为 0则忽略时间，1 表示最后的登录程序（输入密码）
	 * @return 若为登陆则不返回服务端写入的数据，若为命令，则返回命令执行结果
	 * @throws IOException
	 * @throws TelnetException
	 */
	public String exeCmd(String command, int delay) throws TelnetException, IOException, Exception {

		logger.info(this.flow.getDevice().getDeviceName() + "执行指令："+command+";系统结束符："+orderPrompt);
		write(" ");// 避免出现连续提取指令信息时出现乱字符
		readData(DELAY, orderPrompt," ");
		write(command);
		return readData(DELAY, orderPrompt ,command);
	}

	/**
	 * @param command
	 *            要写给服务端的数据（命令）
	 * @param delay
	 *            操作最多持续时间。为 0则忽略时间，1 表示最后的登录程序（输入密码）
	 * @return 不需要在执行真正命令时，先输入空格信息
	 * @throws IOException
	 * @throws TelnetException
	 */
	public String exeCmd2(String command, int delay,boolean readNull) throws TelnetException, IOException, Exception {
		try {
			logger.info(this.flow.getDevice().getDeviceName() + "执行指令："+command+";系统结束符："+orderPrompt+";是否需要空读取："+readNull);
		} catch (Exception e) {
			logger.error("执行指令："+command+";系统结束符："+orderPrompt+"异常！",e);
		}
		if(readNull){
			write(command);
			readData(DELAY, orderPrompt , command);
		}
		write(command);
		return readData(DELAY, orderPrompt , command);
	}
	
	public String exeCmd2(String command, int delay) throws TelnetException, IOException, Exception {
		try {
			logger.info(this.flow.getDevice().getDeviceName() + "执行指令："+command+";系统结束符："+orderPrompt);
		} catch (Exception e) {
			logger.error("执行指令："+command+";系统结束符："+orderPrompt+"异常！",e);
		}
		write(command);
		return readData(DELAY, orderPrompt , command);
	}
	
	private class WaitThread extends Thread {
		private int nDelay;

		private String commend;
		
		public void setDalay(int vnDelay) {
			nDelay = vnDelay;
		}

		public void setCommend(String commend){
			this.commend = commend;
		}
		
		public void run() {
			try {
				Date begTime = new Date();
				while (true) {
					// int i = in.available();
					if (in.available() > 0)
						break;
					if (DateFormate.comparaSecon(new Date(), begTime) * 1000 > nDelay) {
						logger.debug(flow.getDevice().getDeviceName() + "执行指令："+commend + "延迟线程等待超时");
						isFinish = true;
						break;
					}
					sleep(10);
				}
			} catch (SocketTimeoutException e) {
				logger.error(flow.getDevice().getDeviceName() + "执行指令："+commend + "延迟线程等待报出SocketTimeoutException错误",e);
			} catch (IOException e) {
				logger.error(flow.getDevice().getDeviceName() + "执行指令："+commend + "延迟线程等待报出IOException错误",e);
			} catch (Exception e) {
				logger.error(flow.getDevice().getDeviceName() + "执行指令："+commend + "延迟线程等待报出Exception错误",e);
			}
		}
	}

	/* add @author gamesdoa
	 * @email gamesdoa@gmail.com
	 * @date 2015-1-26
	 * @return the flow
	 */
	public TelnetFlowBean getFlow() {
		return flow;
	}
	public static void main(String[] args) {
		/*String s = "service-port 1245 vlan 1450 gpon 0/6/5 ont 16 gemport 0 multi-service user-vlan 100 tag-transform translate-and-add inner-vlan 116 inbound traffic-table index ----more----";
		System.out.println(s.substring(s.indexOf("--")));
		Pattern p = Pattern.compile("\\-*\\[\\d+(A|B|C|D|a|b|c|d)\\s+\\[\\d+(A|B|C|D|a|b|c|d)");
		Matcher m = p.matcher(s);

		if (m.find()) {
			System.out.println("true");
			System.out.println(s);
			s = m.replaceAll("");
			System.out.println(s);
		} else {
			System.out.println("false");
		}*/

		// System.out.println( new EncryptAnalyse().MNSA_Encrypt("123"));
//		System.out.println(new EncryptAnalyse().MNSA_Decrypt("112153182015118"));
//		System.out.println(new EncryptAnalyse().MNSA_Encrypt("SJJF#13981599467"));
//		
//		String text = "\r\n";
//		System.out.println("-----------");
//		System.out.println(text.replaceAll("\r", "").replaceAll("\n", "").trim().length() == 0);
//		System.out.println("-----------");
		EchoTester et = new EchoTester();
		String temp = et.getEchoByFileName("D:/zone/ziyuan/routeEcho/atk.txt");
		CMDService service = new TelnetService();
		temp = service.moveMore(temp,1);
		temp = service.moveCursor(temp,1);
		System.out.println(temp);
//		System.out.println("-----------------------");
//		System.out.println(temp);
//		System.out.println("-----------------------");
	}

	public String exeCmd2(String command, int delay, Pattern choosePattern,
			String chooseCommand) throws TelnetException, IOException,
			Exception {
		try {
			logger.info(this.flow.getDevice().getDeviceName() + "执行指令："
					+ command + ";系统结束符：" + orderPrompt);
		} catch (Exception e) {
			logger.error("执行指令：" + command + ";系统结束符：" + orderPrompt + "异常！", e);
		}
		write(command);
		return readData(DELAY, orderPrompt, command, choosePattern,
				chooseCommand, true);
	}
}