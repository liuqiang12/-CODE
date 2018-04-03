package com.idc.model;


import org.springframework.context.ApplicationEvent;

public class JbpmResourceEvent extends ApplicationEvent  {
 	public Object target;

	/**
	 * Create a new ApplicationEvent.
	 *
	 * @param source the object on which the event initially occurred (never {@code null})
	 */
	public JbpmResourceEvent(Object source, Object target) {
		super(source);
		this.target = target;
	}
}