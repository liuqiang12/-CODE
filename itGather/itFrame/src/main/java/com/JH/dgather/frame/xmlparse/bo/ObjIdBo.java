package com.JH.dgather.frame.xmlparse.bo;

import com.JH.dgather.frame.xmlparse.bo.iface.IStepBase;

/**
 * ObjId业务对象
 * 
 * @author zhengbin
 * 
 */
public class ObjIdBo implements IStepBase {

	private String buffer;

	// ObjId标签内容
	private String objIdValue;

	public String getBuffer() {
		return buffer;
	}

	public void setBuffer(String buffer) {
		this.buffer = buffer;
	}

	public String getObjIdValue() {
		return objIdValue;
	}

	public void setObjIdValue(String objIdValue) {
		this.objIdValue = objIdValue;
	}

	@Override
	public String getTagNameByObject() {
		return "objId";
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("objId标签 [buffer属性=");
		builder.append(buffer);
		builder.append(", objIdValue标签内容值=");
		builder.append(objIdValue);
		builder.append("]");
		return builder.toString();
	}
}
