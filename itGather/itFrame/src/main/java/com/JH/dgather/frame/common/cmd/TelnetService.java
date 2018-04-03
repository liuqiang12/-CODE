package com.JH.dgather.frame.common.cmd;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.apache.commons.net.telnet.EchoOptionHandler;
import org.apache.commons.net.telnet.SuppressGAOptionHandler;
import org.apache.commons.net.telnet.TelnetClient;
import org.apache.commons.net.telnet.TerminalTypeOptionHandler;
import org.apache.log4j.Logger;

import com.JH.dgather.frame.common.exception.TelnetException;
import com.JH.dgather.frame.globaldata.GloableDataArea;
import com.JH.dgather.frame.util.WindowSizeOptionHandler;

/**
 * @author muyp
 * 
 */
public class TelnetService extends CMDService {
	Logger logger = Logger.getLogger(TelnetService.class.getName());
	TelnetClient tc;

	@Override
	public InputStream createReader() {
		return tc.getInputStream();
	}

	@Override
	public PrintStream createWriter() {
		return new PrintStream(tc.getOutputStream());
	}

	@Override
	public void disconnect(String deviceName) {
		
		logger.info(deviceName + "执行telnet销毁方法！");
		
		try {
			if (super.out != null) {
				super.out.close();
			}
		} catch (Exception e) {
			logger.error(deviceName , e);
		} finally {
			super.out = null;
		}
		
		try {
			if (super.in != null) {
				super.in.close();
			}
		} catch (Exception e) {
			logger.error(deviceName, e);
		} finally {
			super.in = null;
		}
		
		super.flow =null;
		super.orderPrompt = null;
		
		
		try {
			if (tc != null && tc.isConnected())
				tc.disconnect();
		} catch (Exception e) {
			logger.error(deviceName, e);
		} finally {
			tc = null;
		}
		//System.gc();
	}

	@Override
	public Boolean ifLoginAuth() {
		return true;// 需要经过认证
	}

	@Override
	public Boolean ifReadOver(String conPrompt, String prompt) {
		/*if (null != conPrompt && prompt.toLowerCase().indexOf(conPrompt) > -1 || ch == '$')
			// if(null != conPrompt &&
			// prompt.trim().toLowerCase().equals(conPrompt)||ch=='$')
			return true;
		else
			return false;*/
		
		if (null != conPrompt && prompt.toLowerCase().indexOf(conPrompt) > -1)
			// if(null != conPrompt &&
			// prompt.trim().toLowerCase().equals(conPrompt)||ch=='$')
			return true;
		else
			return false;
	}

	@Override
	public void write(String command) {

		try {
			logger.info(this.flow.getDevice().getDeviceName() + "write执行指令："+command);
		} catch (Exception e) {
			logger.info("write执行指令："+command);
		}
		try {
						
			if (!command.endsWith("\r\n")) {
				command = command + "\r\n";
			}

			out.write(command.getBytes());
			out.flush();
		} catch (Exception e) {
			logger.error(this.flow.getDevice().getDeviceName() + "写人执行指令："+command+"异常", e);
		}

	}

	@Override
	public boolean connectDevice(String ip, int port, String loginUser, String loginpsw) throws TelnetException {
		tc = new TelnetClient();
		openCnt(ip, port);
		try {
			tc.setSoTimeout(GloableDataArea.SoTimeout);// 定义接收延迟5秒，在制定时间没有read到数据则跳出阻塞，*注意时间定义过长，影响鉴权认证
			tc.setReceiveBufferSize(GloableDataArea.SoReceiveBufferSize);
			tc.setTcpNoDelay(GloableDataArea.SoTcpNoDelay);
		} catch (Exception e) {
			logger.error("", e);
		}

		return true;
	}

	/**
	 * 连接设备
	 * 
	 * @param tc
	 * @param hostIp
	 * @param port
	 * @throws TelnetException
	 */
	private void openCnt(String hostIp, int port) throws TelnetException {
		try {
			TerminalTypeOptionHandler ttopt = new TerminalTypeOptionHandler("VT320", false, false, true, false);
			EchoOptionHandler echoopt = new EchoOptionHandler(true, true, true, true);
			SuppressGAOptionHandler gaopt = new SuppressGAOptionHandler(true, true, true, true);
			WindowSizeOptionHandler winSize = new WindowSizeOptionHandler(true, false, true, false);
			tc.addOptionHandler(winSize);
			tc.addOptionHandler(ttopt);
			tc.addOptionHandler(echoopt);
			tc.addOptionHandler(gaopt);
			tc.connect(hostIp, port);
		} catch (Exception e) {
			logger.error("", e);
			try {
				tc.disconnect();
			} catch (IOException e1) {
				
			}finally{
				tc=null;
			}
			throw new TelnetException(hostIp + "telnet连接异常");
		}
	}

}
