package com.JH.dgather.frame.eventalarmsender.service.northif;

public class AlarmNorthIfResponse {
	// 发送失败
	public static final int SENT_STATUS_FAIL = 0;
	// 发送成功
	public static final int SENT_STATUS_SUCCESS = 1;
	// 不发送
	public static final int SENT_STATUS_NOACTION = -1000;

	private String name;
	private String uri;
	private int sentResult;
	private String errorMsg;

	public AlarmNorthIfResponse() {
		super();
	}

	public AlarmNorthIfResponse(String name, String uri) {
		super();
		this.name = name;
		this.uri = uri;
	}

	public AlarmNorthIfResponse(String name) {
		super();
		this.name = name;
	}

	public AlarmNorthIfResponse(String name, String uri, int sentResult,
			String errorMsg) {
		super();
		this.name = name;
		this.uri = uri;
		this.sentResult = sentResult;
		this.errorMsg = errorMsg;
	}

	public AlarmNorthIfResponse(String name, String uri, int sentResult) {
		super();
		this.name = name;
		this.uri = uri;
		this.sentResult = sentResult;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public int getSentResult() {
		return sentResult;
	}

	public void setSentResult(int sentResult) {
		this.sentResult = sentResult;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	@Override
	public String toString() {
		return name + ":发送结果" + sentResult + "," + errorMsg;
	}

}
