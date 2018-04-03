package com.JH.dgather.frame.eventalarmsender.webservice;

import com.JH.dgather.frame.eventalarmsender.webservice.config.WebService;

public class WebServiceResponse {
	private WebService webService;
	private int sentResult;
	private String errorMsg;

	public WebService getWebService() {
		return webService;
	}

	public void setWebService(WebService webService) {
		this.webService = webService;
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

	public WebServiceResponse(WebService webService, int sentResult,
			String errorMsg) {
		super();
		this.webService = webService;
		this.sentResult = sentResult;
		this.errorMsg = errorMsg;
	}

	public WebServiceResponse(WebService webService, int sentResult) {
		super();
		this.webService = webService;
		this.sentResult = sentResult;
	}

	public WebServiceResponse(WebService webService) {
		super();
		this.webService = webService;
	}

	@Override
	public String toString() {
		return webService.name + ":发送结果" + sentResult + "," + errorMsg;
	}

}
