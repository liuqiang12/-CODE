package com.JH.dgather.frame.eventalarmsender.bean;

import org.apache.commons.lang.builder.ToStringBuilder;

public class UserBean {
	private String mail;
	private String tel;
	
	public UserBean() {
		mail = "";
		tel = "";
	}
	
	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}
	
	/**
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}
	
	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("mail", this.mail).append("tel", this.tel).toString();
	}
	
}
