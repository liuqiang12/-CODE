package com.JH.dgather.frame.util;

import org.apache.commons.net.telnet.TelnetOption;
import org.apache.commons.net.telnet.TelnetOptionHandler;

/**
 * 
 * 用于telnet窗口大小选项协商的类
 * 
 * @author YangDS
 *
 */
public class WindowSizeOptionHandler extends TelnetOptionHandler {
	
	public WindowSizeOptionHandler(boolean initlocal, boolean initremote, boolean acceptlocal, boolean acceptremote) {
		super(TelnetOption.WINDOW_SIZE, initlocal, initremote, acceptlocal, acceptremote);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int[] answerSubnegotiation(int[] arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int[] startSubnegotiationLocal() {
		// TODO Auto-generated method stub
		int[] subData = { TelnetOption.WINDOW_SIZE, 600, 400 };
		return subData;
	}
	
	@Override
	public int[] startSubnegotiationRemote() {
		// TODO Auto-generated method stub
		int[] subData = { TelnetOption.WINDOW_SIZE, 600, 400 };
		return subData;
	}
	
}
