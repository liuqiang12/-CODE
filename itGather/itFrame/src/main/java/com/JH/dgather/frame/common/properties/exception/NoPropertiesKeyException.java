/*
 * @(#)NoPropertiesKeyException.java 12/26/2011
 *
 * Copyright 2011 Zone, All rights reserved.
 */
package com.JH.dgather.frame.common.properties.exception;

/**
 * <code>NoPropertiesKeyException</code> 是继承<code>Exception</code>的一个异常类
 * 当从指定属性文件中获取某一属性时，如未获取到该属性，则抛出此异常
 * @author Wang Xuedan
 * @version 1.0, 12/26/2011
 */
public class NoPropertiesKeyException extends Exception {
	private static final long serialVersionUID = 6040432056563739740L;
	
	public NoPropertiesKeyException() {
		super();
	}
	
	public NoPropertiesKeyException(String message) {
		super(message);
	}
	
	public NoPropertiesKeyException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public NoPropertiesKeyException(Throwable cause) {
		super(cause);
	}
}
