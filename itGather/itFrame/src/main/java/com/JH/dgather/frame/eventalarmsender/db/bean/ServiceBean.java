package com.JH.dgather.frame.eventalarmsender.db.bean;

/**
 * 服务BEAN，比如邮件服务器信息，短信服务器信息，根据类型组装集合
 * 
 * @author Administrator
 * 
 */
public class ServiceBean {
	private int ANSId;
	private int ANSMode;
	private int ANSType;
	private int ANSInterval;
	private String ServiceAPIConn;
	private String ServiceAPIName;
	private String ServicAPIIde;
	private String ServiceLoginName;
	private String ServcieLoginPwd;
	private String servicePort;
	private String serviceAuth;
	private String ssl;
	private String socketFactory;
	private String personal;
	private boolean textSubject;
	public ServiceBean(String port, String auth, String ssl, String socketFactory) {
		this.ANSId = -1;
		this.ANSMode = -1;
		this.ANSType = 0;
		this.ANSInterval = 0;
		this.ServcieLoginPwd = "";
		this.ServicAPIIde = "";
		this.ServiceAPIConn = "";
		this.ServiceAPIName = "";
		this.ServiceLoginName = "";
		this.servicePort = port;
		this.serviceAuth = auth;
		this.ssl = ssl;
		this.socketFactory = socketFactory;
	}

	public boolean isTextSubject() {
		return textSubject;
	}

	public void setTextSubject(boolean textSubject) {
		this.textSubject = textSubject;
	}

	public String getPersonal() {
		return personal;
	}

	public void setPersonal(String personal) {
		this.personal = personal;
	}
	/**
	 * @return the aNSId
	 */
	public int getANSId() {
		return ANSId;
	}

	/**
	 * @param id
	 *            the aNSId to set
	 */
	public void setANSId(int id) {
		ANSId = id;
	}

	/**
	 * @return the aNSMode
	 */
	public int getANSMode() {
		return ANSMode;
	}

	/**
	 * @param mode
	 *            the aNSMode to set
	 */
	public void setANSMode(int mode) {
		ANSMode = mode;
	}

	/**
	 * @return the aNSType
	 */
	public int getANSType() {
		return ANSType;
	}

	/**
	 * @param type
	 *            the aNSType to set
	 */
	public void setANSType(int type) {
		ANSType = type;
	}

	/**
	 * @return the aNSInterval
	 */
	public int getANSInterval() {
		return ANSInterval;
	}

	/**
	 * @param interval
	 *            the aNSInterval to set
	 */
	public void setANSInterval(int interval) {
		ANSInterval = interval;
	}

	/**
	 * @return the serviceAPIConn
	 */
	public String getServiceAPIConn() {
		return ServiceAPIConn;
	}

	/**
	 * @param serviceAPIConn
	 *            the serviceAPIConn to set
	 */
	public void setServiceAPIConn(String serviceAPIConn) {
		ServiceAPIConn = serviceAPIConn;
	}

	/**
	 * @return the serviceAPIName
	 */
	public String getServiceAPIName() {
		return ServiceAPIName;
	}

	/**
	 * @param serviceAPIName
	 *            the serviceAPIName to set
	 */
	public void setServiceAPIName(String serviceAPIName) {
		ServiceAPIName = serviceAPIName;
	}

	/**
	 * @return the servicAPIIde
	 */
	public String getServicAPIIde() {
		return ServicAPIIde;
	}

	/**
	 * @param servicAPIIde
	 *            the servicAPIIde to set
	 */
	public void setServicAPIIde(String servicAPIIde) {
		ServicAPIIde = servicAPIIde;
	}

	/**
	 * @return the serviceLoginName
	 */
	public String getServiceLoginName() {
		return ServiceLoginName;
	}

	/**
	 * @param serviceLoginName
	 *            the serviceLoginName to set
	 */
	public void setServiceLoginName(String serviceLoginName) {
		ServiceLoginName = serviceLoginName;
	}

	/**
	 * @return the servcieLoginPwd
	 */
	public String getServcieLoginPwd() {
		return ServcieLoginPwd;
	}

	/**
	 * @param servcieLoginPwd
	 *            the servcieLoginPwd to set
	 */
	public void setServcieLoginPwd(String servcieLoginPwd) {
		ServcieLoginPwd = servcieLoginPwd;
	}

	/*
	 * add @author gamesdoa
	 * 
	 * @email gamesdoa@gmail.com
	 * 
	 * @date 2014-4-4
	 * 
	 * @return the servicePort
	 */
	public String getServicePort() {
		return servicePort;
	}

	/*
	 * add @author gamesdoa
	 * 
	 * @email gamesdoa@gmail.com
	 * 
	 * @date 2014-4-4
	 * 
	 * @param servicePort the servicePort to set
	 */
	public void setServicePort(String servicePort) {
		this.servicePort = servicePort;
	}

	/*
	 * add @author gamesdoa
	 * 
	 * @email gamesdoa@gmail.com
	 * 
	 * @date 2014-4-4
	 * 
	 * @return the serviceAuth
	 */
	public String getServiceAuth() {
		return serviceAuth;
	}

	/*
	 * add @author gamesdoa
	 * 
	 * @email gamesdoa@gmail.com
	 * 
	 * @date 2014-4-4
	 * 
	 * @param serviceAuth the serviceAuth to set
	 */
	public void setServiceAuth(String serviceAuth) {
		this.serviceAuth = serviceAuth;
	}

	/*
	 * add @author gamesdoa
	 * 
	 * @email gamesdoa@gmail.com
	 * 
	 * @date 2014-4-28
	 * 
	 * @return the ssl
	 */
	public String getSsl() {
		return ssl;
	}

	/*
	 * add @author gamesdoa
	 * 
	 * @email gamesdoa@gmail.com
	 * 
	 * @date 2014-4-28
	 * 
	 * @param ssl the ssl to set
	 */
	public void setSsl(String ssl) {
		this.ssl = ssl;
	}

	/*
	 * add @author gamesdoa
	 * 
	 * @email gamesdoa@gmail.com
	 * 
	 * @date 2014-4-28
	 * 
	 * @return the socketFactory
	 */
	public String getSocketFactory() {
		return socketFactory;
	}

	/*
	 * add @author gamesdoa
	 * 
	 * @email gamesdoa@gmail.com
	 * 
	 * @date 2014-4-28
	 * 
	 * @param socketFactory the socketFactory to set
	 */
	public void setSocketFactory(String socketFactory) {
		this.socketFactory = socketFactory;
	}

	/*
	 * @author gamesdoa
	 * 
	 * @email gamesdoa@gmail.com
	 * 
	 * @date 2014-4-4(non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ServiceBean [ANSId=" + ANSId + ", ANSMode=" + ANSMode + ", ANSType=" + ANSType + ", ANSInterval=" + ANSInterval + ", ServiceAPIConn=" + ServiceAPIConn + ", ServiceAPIName="
				+ ServiceAPIName + ", ServicAPIIde=" + ServicAPIIde + ", ServiceLoginName=" + ServiceLoginName + ", ServcieLoginPwd=" + ServcieLoginPwd + ", servicePort=" + servicePort
				+ ", serviceAuth=" + serviceAuth + "]";
	}

}
