package com.JH.dgather.frame.gathercontrol.task.bean;
/**
 * @author gamesdoa
 * @email gamesdoa@gmail.com
 * @date 2014-12-5
 */

public class TaskGcBean {
	private int gc;
	private String rule;
	private String controller;
	/* add @author gamesdoa
	 * @email gamesdoa@gmail.com
	 * @date 2014-12-5
	 * @return the gc
	 */
	public int getGc() {
		return gc;
	}
	/* add @author gamesdoa
	 * @email gamesdoa@gmail.com
	 * @date 2014-12-5
	 * @param gc the gc to set
	 */
	public void setGc(int gc) {
		this.gc = gc;
	}
	/* add @author gamesdoa
	 * @email gamesdoa@gmail.com
	 * @date 2014-12-5
	 * @return the rule
	 */
	public String getRule() {
		return rule;
	}
	/* add @author gamesdoa
	 * @email gamesdoa@gmail.com
	 * @date 2014-12-5
	 * @param rule the rule to set
	 */
	public void setRule(String rule) {
		this.rule = rule;
	}
	/* add @author gamesdoa
	 * @email gamesdoa@gmail.com
	 * @date 2014-12-5
	 * @return the controller
	 */
	public String getController() {
		return controller;
	}
	/* add @author gamesdoa
	 * @email gamesdoa@gmail.com
	 * @date 2014-12-5
	 * @param controller the controller to set
	 */
	public void setController(String controller) {
		this.controller = controller;
	}
	/*
	 * @author gamesdoa
	 * @email gamesdoa@gmail.com
	 * @date 2014-12-5
	 *(non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TaskGcBean [gc=" + gc + ", rule=" + rule + ", controller=" + controller + "]";
	}
	
}
