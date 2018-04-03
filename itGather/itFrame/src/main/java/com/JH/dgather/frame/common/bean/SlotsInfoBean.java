package com.JH.dgather.frame.common.bean;

import java.util.ArrayList;

import com.JH.dgather.frame.common.reflect.ReflectUtil;

/**
 * 
 * 槽信息
 * 
 * @author yangDS
 *
 */
public class SlotsInfoBean {
	
	private int deviceId;
	private int slotsNo;
	private String slotsName;
	private int status;
	
	public int getDeviceId() {
		return deviceId;
	}
	
	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}
	
	public int getSlotsNo() {
		return slotsNo;
	}
	
	public void setSlotsNo(int slotsNo) {
		this.slotsNo = slotsNo;
	}
	
	public String getSlotsName() {
		return slotsName;
	}
	
	public void setSlotsName(String slotsName) {
		if (slotsName != null && slotsName.length() > 100) {
			this.slotsName = slotsName.substring(0, 100);
		}
		else {
			this.slotsName = slotsName;
		}
		
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public boolean equals(Object obj) {
		SlotsInfoBean temp = null;
		ArrayList<String> compareFields = new ArrayList<String>() {
			{
				add("deviceId");
				add("slotsNo");
				//			add("boardName");
				//			add("boardType");
				//			add("boardSerial");
			}
		};
		if (obj instanceof SlotsInfoBean) {
			temp = (SlotsInfoBean) obj;
			return ReflectUtil.beanCompare(this, temp, compareFields);
		}
		else {
			return false;
		}
		
	}
	
	public static void main(String[] args) {
		SlotsInfoBean sb1 = new SlotsInfoBean(), s2 = new SlotsInfoBean();
		sb1.setDeviceId(1);
		s2.setDeviceId(1);
		sb1.setSlotsNo(1);
		s2.setSlotsNo(2);
		System.out.println(sb1.equals(s2));
	}
	
}
