package com.JH.dgather.frame.common.exception;

public class TelnetException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *  错误消息
	 */
	private String errMsg;
	
	public TelnetException(String errMsg) {
		
		this.errMsg = errMsg;
	}
	
	public String getMessage() {
		return errMsg;
	}
	
	public String getErrMsg() {
		return errMsg;
	}
	
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
}
