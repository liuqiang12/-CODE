package com.idc.model;


import java.io.Serializable;

/**
 * 工单资源分配
 * @author Administrator
 */
@SuppressWarnings("serial")
public class TicketResourceAllocation implements Serializable {
	private Boolean resourceAllocationStatus = Boolean.FALSE;
	private Boolean ticketResourceHandStatus = Boolean.FALSE;

	public Boolean getResourceAllocationStatus() {
		return resourceAllocationStatus;
	}

	public void setResourceAllocationStatus(Boolean resourceAllocationStatus) {
		this.resourceAllocationStatus = resourceAllocationStatus;
	}

	public Boolean getTicketResourceHandStatus() {
		return ticketResourceHandStatus;
	}

	public void setTicketResourceHandStatus(Boolean ticketResourceHandStatus) {
		this.ticketResourceHandStatus = ticketResourceHandStatus;
	}
}
