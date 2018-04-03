/*
 * @(#)NotFoundTaskRuleException.java 12/23/2011
 *
 * Copyright 2011 Zone, All rights reserved.
 */
package com.JH.dgather.frame.gathercontrol.exception;

public class NotFoundTaskRuleException extends Exception {
	private static final long serialVersionUID = -6872570576443502320L;
	
	public NotFoundTaskRuleException() {
		super();
	}
	
	public NotFoundTaskRuleException(String message) {
		super(message);
	}
	
	public NotFoundTaskRuleException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public NotFoundTaskRuleException(Throwable cause) {
		super(cause);
	}
}
