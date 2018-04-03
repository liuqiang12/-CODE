package com.JH.dgather.frame.eventmanager.bean;

public class ArtificialPlanCmdResult {
	int taskid;
	int routid;
	String routName;
	String routIp;
	int cmdname;
	int ruleid;
	int cmdresult;
	String dictatevalue;
	String ruleerrorcontent;

	public int getTaskid() {
		return taskid;
	}

	public void setTaskid(int taskid) {
		this.taskid = taskid;
	}

	public int getRoutid() {
		return routid;
	}

	public void setRoutid(int routid) {
		this.routid = routid;
	}

	public int getCmdname() {
		return cmdname;
	}

	public void setCmdname(int cmdname) {
		this.cmdname = cmdname;
	}

	public int getRuleid() {
		return ruleid;
	}

	public void setRuleid(int ruleid) {
		this.ruleid = ruleid;
	}

	public int getCmdresult() {
		return cmdresult;
	}

	public void setCmdresult(int cmdresult) {
		this.cmdresult = cmdresult;
	}

	public String getRuleerrorcontent() {
		return ruleerrorcontent;
	}

	public void setRuleerrorcontent(String ruleerrorcontent) {
		this.ruleerrorcontent = ruleerrorcontent;
	}

	public String getDictatevalue() {
		return dictatevalue;
	}

	public void setDictatevalue(String dictatevalue) {
		this.dictatevalue = dictatevalue;
	}

	/*
	 * add @author gamesdoa
	 * 
	 * @email gamesdoa@gmail.com
	 * 
	 * @date 2013-11-8
	 * 
	 * @return the routName
	 */
	public String getRoutName() {
		return routName;
	}

	/*
	 * add @author gamesdoa
	 * 
	 * @email gamesdoa@gmail.com
	 * 
	 * @date 2013-11-8
	 * 
	 * @param routName the routName to set
	 */
	public void setRoutName(String routName) {
		this.routName = routName;
	}

	/*
	 * add @author gamesdoa
	 * 
	 * @email gamesdoa@gmail.com
	 * 
	 * @date 2013-11-8
	 * 
	 * @return the routIp
	 */
	public String getRoutIp() {
		return routIp;
	}

	/*
	 * add @author gamesdoa
	 * 
	 * @email gamesdoa@gmail.com
	 * 
	 * @date 2013-11-8
	 * 
	 * @param routIp the routIp to set
	 */
	public void setRoutIp(String routIp) {
		this.routIp = routIp;
	}

}