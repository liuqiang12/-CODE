package com.idc.model;


import org.springframework.context.ApplicationEvent;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TicketResourceEvent extends ApplicationEvent  {
 	public Object target;
 	public String jbpm_ticket_category;//流程工单类型

	/**
	 * Create a new ApplicationEvent.
	 *
	 * @param source the object on which the event initially occurred (never {@code null})
	 */
	public TicketResourceEvent(Object source,Object target,String jbpm_ticket_category) {
		super(source);
		this.target = target;
		this.jbpm_ticket_category = jbpm_ticket_category;
	}
}