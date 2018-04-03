package com.idc.utils;


/**
 * @author Administrator
 *
 */
public enum SendEmailEnum {
	/*步骤顺序*/
	/*邮件发件人地址("15902857434@139.com"),
	邮件发件人密码("hcw@IDC308"),
	邮件_smtp_host("smtp.139.com"),
	邮件_smtp_auth("true"),
	邮件_smtp_port("465"),
	邮件_smtp_ssl_enable("true"),*/
	邮件收件人地址_测试用("295252343@qq.com");

	/*
	* 修改资源状态的时候，要在评分这一步进行修改
	*
	* */

	private final String value;

	SendEmailEnum(final String value) {
		this.value = value;
	}
	public String value() {
		return this.value;
	}





}
