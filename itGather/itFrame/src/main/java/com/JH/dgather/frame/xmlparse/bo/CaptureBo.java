package com.JH.dgather.frame.xmlparse.bo;

import java.util.List;

import com.JH.dgather.frame.xmlparse.bo.iface.IStepBase;

/**
 * capture业务对象
 * 
 * @author zhengbin
 * 
 */
public class CaptureBo implements IStepBase {

	private static final String TAG_NAME = "capture";

	private String name;

	// keyposition标签对象集合（有序）
	private List<KeyPositionBo> keyPositionList;

	// capturestep标签对象集合（有序）
	private List<CaptureStepBo> captureStepList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<KeyPositionBo> getKeyPositionList() {
		return keyPositionList;
	}

	public void setKeyPositionList(List<KeyPositionBo> keyPositionList) {
		this.keyPositionList = keyPositionList;
	}

	public List<CaptureStepBo> getCaptureStepList() {
		return captureStepList;
	}

	public void setCaptureStepList(List<CaptureStepBo> captureStepList) {
		this.captureStepList = captureStepList;
	}

	@Override
	public String getTagNameByObject() {
		return TAG_NAME;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("capture标签 [name属性=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}
}
