package com.JH.dgather.frame.common.bean;


/**
 * 配置文件分析设备信息bean
 * 
 * @author luoxiaoyong
 * 
 */
public class ConfigAnalyseDevice {
	
	// 设备ID
	private int id;
	// 设备名称
	private String name;
	// 设备ip地址
	private String ipAddress;
	// 设备telnet端口
	private int port;
	// telnet登陆设备用户名
	private String username;
	// telnet登陆设备密码
	private String password;
	// telnet登陆设备特权密码
	private String priviledgePSW;
	// 运行配置命令
	private String runCommand;
	// 启动配置命令
	private String startCommand;
	// 对比模板文件路径
	private String templateFile;
	// 厂家信息
	private int factory;
	// 归属地
	private int ownerProvince;
	
	//telnetVPN参数
	private String telnetVPNparm;
	//设备类型名称
	private String modelName;
	//设备类型
	private int routType;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getIpAddress() {
		return ipAddress;
	}
	
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPriviledgePSW() {
		return priviledgePSW;
	}
	
	public void setPriviledgePSW(String priviledgePSW) {
		this.priviledgePSW = priviledgePSW;
	}
	
	public String getRunCommand() {
		return runCommand;
	}
	
	public void setRunCommand(String runCommand) {
		this.runCommand = runCommand;
	}
	
	public String getStartCommand() {
		return startCommand;
	}
	
	public void setStartCommand(String startCommand) {
		this.startCommand = startCommand;
	}
	
	public String getTemplateFile() {
		return templateFile;
	}
	
	public void setTemplateFile(String templateFile) {
		this.templateFile = templateFile;
	}
	
	public int getFactory() {
		return factory;
	}
	
	public void setFactory(int factory) {
		this.factory = factory;
	}
	
	public int getOwnerProvince() {
		return ownerProvince;
	}
	
	public void setOwnerProvince(int ownerProvince) {
		this.ownerProvince = ownerProvince;
	}

	public String getTelnetVPNparm() {
		return telnetVPNparm;
	}

	public void setTelnetVPNparm(String telnetVPNparm) {
		this.telnetVPNparm = telnetVPNparm;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public int getRoutType() {
		return routType;
	}

	public void setRoutType(int routType) {
		this.routType = routType;
	}
	
}
