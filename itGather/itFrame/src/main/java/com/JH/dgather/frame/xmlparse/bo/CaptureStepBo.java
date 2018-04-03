package com.JH.dgather.frame.xmlparse.bo;

import com.JH.dgather.frame.xmlparse.bo.iface.IBase;

/**
 * capturestep标签业务对象
 * @author zhengbin
 *
 */
public class CaptureStepBo implements IBase {
	
	// capturestep标签内容
	private String captureStepValue;

	public String getCaptureStepValue() {
		return captureStepValue;
	}

	public void setCaptureStepValue(String captureStepValue) {
		this.captureStepValue = captureStepValue;
	}

	@Override
	public String getTagNameByObject() {
		return "captureStep";
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("captureStep标签 [captureStepValue标签内容值=");
		builder.append(captureStepValue);
		builder.append("]");
		return builder.toString();
	}
}
