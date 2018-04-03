package com.JH.dgather.frame.gathercontrol.exception;


/**
 * 业务异常类
 * @author Administrator
 *
 */
public class BusinessException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//异常错误消息
	private String errMsg;
	
	public BusinessException() {
		super();
	}
	
	public BusinessException(String errMsg) {
		super();
		this.errMsg = errMsg;
	}
	
	/**
	 * @return the errMsg
	 */
	public String getErrMsg() {
		return errMsg;
	}
	
	/**
	 * @param errMsg the errMsg to set
	 */
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
}
