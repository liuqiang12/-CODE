package com.JH.dgather.frame.xmlparse.bo;

import com.JH.dgather.frame.xmlparse.bo.iface.ISplitBase;
import com.JH.dgather.frame.xmlparse.bo.iface.IStepBase;

/**
 * validate标签业务对象
 * 
 * @author zhengbin
 * 
 */
public class ValidateBo implements IStepBase, ISplitBase {
	// validate标签值
	private String validateValue;

	public String getValidateValue() {
		return validateValue;
	}

	public void setValidateValue(String validateValue) {
		this.validateValue = validateValue;
	}

	@Override
	public String getTagNameByObject() {
		return "validate";
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ValidateBo [validateValue标签内容值=");
		builder.append(validateValue);
		builder.append("]");
		return builder.toString();
	}
}
