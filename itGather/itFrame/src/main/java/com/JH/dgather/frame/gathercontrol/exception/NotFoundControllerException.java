/*
 * @(#)NotFoundControllerException.java 12/23/2011
 *
 * Copyright 2011 Zone, All rights reserved.
 */
package com.JH.dgather.frame.gathercontrol.exception;

/**
 * <code>NotFoundControllerException</code> 继承<code>Exception</code>的一个异常类。
 * 它用于控制器解析时，如果根据<code>TaskRule</code>未找到相应的控制器时，则抛出该异常。
 * 
 * @author  Wang Xuedan
 * @version 1.0, 12/23/2011
 */
public class NotFoundControllerException extends Exception {
	private static final long serialVersionUID = 3561202612460175398L;
	
	public NotFoundControllerException() {
		super();
	}
	
	public NotFoundControllerException(String message) {
		super(message);
	}
	
	public NotFoundControllerException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public NotFoundControllerException(Throwable cause) {
		super(cause);
	}
}
