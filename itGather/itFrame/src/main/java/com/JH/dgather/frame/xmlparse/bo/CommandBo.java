package com.JH.dgather.frame.xmlparse.bo;

import com.JH.dgather.frame.xmlparse.bo.iface.IStepBase;

/**
 * command标签业务对象
 * 
 * @author zhengbin
 * 
 */
public class CommandBo implements IStepBase {

	// 如果buffer不为空表示需要保存得到的回显值
	private String buffer;

	// 默认的参数值，如果是多个参数，以'|'相隔
	private String defaultArgument;

	// 测试模式。存储一个txt文件的路径。如果此属性存在，那么利用当前指令获取的回显为文件的内容。
	private String testmode;

	private String iscmd;

	// 标签的内容
	private String commandValue;

	// 是否提取回显
	private boolean isOperater;

	public String getBuffer() {
		return buffer;
	}

	public void setBuffer(String buffer) {
		this.buffer = buffer;
	}

	public String getDefaultArgument() {
		return defaultArgument;
	}

	public void setDefaultArgument(String defaultArgument) {
		this.defaultArgument = defaultArgument;
	}

	public String getTestmode() {
		return testmode;
	}

	public void setTestmode(String testmode) {
		this.testmode = testmode;
	}

	public String getIscmd() {
		return iscmd;
	}

	public void setIscmd(String iscmd) {
		this.iscmd = iscmd;
	}

	public String getCommandValue() {
		return commandValue;
	}

	public void setCommandValue(String commandValue) {
		this.commandValue = commandValue;
	}

	public boolean isOperater() {
		return isOperater;
	}

	public void setOperater(boolean isOperater) {
		this.isOperater = isOperater;
	}

	@Override
	public String getTagNameByObject() {
		return "command";
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("command标签 [buffer属性=");
		builder.append(buffer);
		builder.append(", defaultArgument属性=");
		builder.append(defaultArgument);
		builder.append(", testmode属性=");
		builder.append(testmode);
		builder.append(", iscmd属性=");
		builder.append(iscmd);
		builder.append(", commandValue标签内容值=");
		builder.append(commandValue);
		builder.append("]");
		return builder.toString();
	}
	
}
