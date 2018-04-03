/**
 * 
 */
package com.JH.dgather.frame.common.cmd;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.JH.dgather.frame.common.exception.TelnetException;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * @author muyp
 *
 */
public class SSHService extends CMDService {
	Logger logger = Logger.getLogger(SSHService.class.getName());
	
	private String LINE = "\n";
	private byte line = 0x0a;
	private byte space = 0x1a;
	private Session session;// SSH会话实例
	private ChannelShell chshell;// SSH通信管道实例
	
	@Override
	public boolean connectDevice(String ip, int port, String loginUser, String loginpsw) throws TelnetException {
		try {
			createSession(loginUser, loginpsw, ip, port);
			if (session != null)
				chshell = (ChannelShell) getChannel("shell");// 获取shell通信管道
			chshell.connect();
			
		} catch (JSchException e) {
			logger.error(this.flow.getDevice().getDeviceName() + "SSH登录JSchException：", e);
			throw new TelnetException(ip + "连接失败");
		}catch (Exception e) {
			logger.error(this.flow.getDevice().getDeviceName() + "SSH登录Exception：", e);
		}
		return true;
	}
	
	@Override
	public InputStream createReader() {
		try {
			return chshell.getInputStream();
		} catch (IOException e) {
			logger.error("", e);
			return null;
		}
	}
	
	@Override
	public PrintStream createWriter() {
		try {
			return new PrintStream(chshell.getOutputStream());
		} catch (IOException e) {
			logger.error("", e);
			return null;
		}
	}
	
	@Override
	public void disconnect(String deviceName) {
		logger.info(deviceName + "执行SSH销毁方法！");
		try {
			if (out != null) {
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			logger.error(deviceName, e);
		} finally {
			out = null;
		}
		
		try {
			if (in != null) {
				in.close();
			}
		} catch (Exception e) {
			logger.error(deviceName, e);
		} finally {
			in = null;
		}
		
		flow =null;
		orderPrompt = null;
		
		if (chshell != null && chshell.isEOF()) {
			chshell.disconnect();
		}
		if (session != null && session.isConnected()) {
			session.disconnect();
		}
		session = null;
		chshell = null;

		//System.gc();
	}
	
	@Override
	public Boolean ifLoginAuth() {
		
		return false;
	}
	
	@Override
	public Boolean ifReadOver(String conPrompt, String prompt) {
		if (null != conPrompt && prompt.toLowerCase().indexOf(conPrompt) > -1)
			return true;
		else
			return false;
	}
	
	@Override
	public void write(String command) {
		logger.error(this.flow.getDevice().getDeviceName() + "写人执行指令："+command);
		if (command == null)
			throw new NullPointerException(this.flow.getDevice().getDeviceName() + "发送的message不可以为空");
		if (!command.endsWith(LINE)) {
			command += LINE;
		}
		
		
		try {
			out.write(command.getBytes());
			out.flush();
		} catch (Exception e) {
			logger.error(this.flow.getDevice().getDeviceName() + "写人执行指令："+command+"异常", e);
		}
		
	}
	
	/**
	 * 与目标机器建立session
	 * 
	 * @param username
	 *            登陆用户名
	 * @param password
	 *            密码口令
	 * @param hostIP
	 *            目的主机ip
	 * @return
	 */
	private void createSession(String username, String password, String targetIP, int port) throws JSchException {
		JSch jsch = new JSch();
		// try {
		
		session = jsch.getSession(username, targetIP, port);
		session.setPassword(password);
		Properties prop = new Properties();
		prop.setProperty("StrictHostKeyChecking", "no");// StrictHostKeyChecking:
		// ask | yes | no
		session.setConfig(prop);
		session.connect();//ConnectTimeout);
		
		//        } catch (JSchException e) {
		//
		//            e.printStackTrace();
		//        }
		//       return null;
	}
	
	/**
	 * 得到通信管道
	 * 
	 * @param session
	 *            会话实例
	 * @param channelName
	 *            通道名称 ，如shell
	 * @return
	 * @throws JSchException
	 */
	private Channel getChannel(String channelName) throws JSchException {
		
		return session.openChannel(channelName);
	}
	
}
