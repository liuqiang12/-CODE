package com.JH.dgather.frame.common.cmd.bean;
/**
 * 记录每个操作点的信息
 * @author muyp
 *
 */
public class FlowPointBean {
	private int SequenceNo;//操作序号
	private String prompt;//提示关键字
	//填写参数，参数用&+参数保留字（如用&psw、&usr代表输入账号和密码），
	//如果是输入固定字符串则直接填写字串（如：enable）
	private String fillParam;
	private String errorInfo;//错误提示信息
	public String getFillParam() {
		return fillParam;
	}
	public void setFillParam(String fillParam) {
		this.fillParam = fillParam;
	}
	public String getPrompt() {
		return prompt;
	}
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}
	public int getSequenceNo() {
		return SequenceNo;
	}
	public void setSequenceNo(int sequenceNo) {
		SequenceNo = sequenceNo;
	}
	public String getErrorInfo() {
		return errorInfo;
	}
	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

}
