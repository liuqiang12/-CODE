package com.JH.dgather.frame.gathercontrol.exception;

public class UserTaskException extends Exception {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4121606532215986664L;
	int exceptionType;//异常类型
	String message;//异常信息
	public UserTaskException(int type,String message){
		this.exceptionType = type;
		this.message = message;
	}
	public int getExceptionType() {
		return exceptionType;
	}
	public void setExceptionType(int exceptionType) {
		this.exceptionType = exceptionType;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	


}
