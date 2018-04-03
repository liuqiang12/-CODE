package com.JH.dgather.frame.xmlparse.bo;

import com.JH.dgather.frame.xmlparse.bo.iface.IBase;

/**
 * keyposition标签业务对象
 * 
 * @author zhengbin
 * 
 */
public class KeyPositionBo implements IBase {

	// keyposition标签内容
	private String keyPositionValue;

	public String getKeyPositionValue() {
		return keyPositionValue;
	}

	public void setKeyPositionValue(String keyPositionValue) {
		this.keyPositionValue = keyPositionValue;
	}

	@Override
	public String getTagNameByObject() {
		return "keyposition";
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("keyposition标签 [keyPositionValue标签内容=");
		builder.append(keyPositionValue);
		builder.append("]");
		return builder.toString();
	}
}
