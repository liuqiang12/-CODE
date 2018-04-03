package com.JH.dgather.frame.common.snmp.bean;

public class OIDbean {
	
	private String oid; //oid字符串
	private String value;//取得的值
	private boolean success;//取值是否成功
	private String errMsg;//如果不成功，错误信息
	
	public OIDbean(String oid, String value, boolean success, String errMsg) {
		this.oid = oid;
		this.value = value;
		this.success = success;
		this.errMsg = errMsg;
	}
	
	public OIDbean() {
		
	}
	
	public OIDbean(String oid) {
		this.oid = oid;
	}
	
	public String getOid() {
		return oid;
	}
	
	public void setOid(String oid) {
		this.oid = oid;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public boolean isSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public String getErrMsg() {
		return errMsg;
	}
	
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	/*
	 * @author gamesdoa
	 * @email gamesdoa@gmail.com
	 * @date 2014-5-4
	 *(non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OIDbean [oid=" + oid + ", value=" + value + ", success=" + success + ", errMsg=" + errMsg + "]";
	}
	
}
