package com.JH.dgather.frame.common.bean;

import java.util.ArrayList;

import com.JH.dgather.frame.common.reflect.ReflectUtil;

/**
 * 板信息bean
 * 
 * @author yangDS
 *
 */
public class BoardInfoBean {
	
	private int deviceId;
	private int slotsNo;
	private int boardNo;
	private String boardName;
	private String boardType;
	private String boardSerial;
	private int boardIndex;
	
	public int getBoardIndex() {
		
		return boardIndex;
	}
	
	public void setBoardIndex(int boardIndex) {
		
		this.boardIndex = boardIndex;
	}
	
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
	
	public int getBoardNo() {
		return boardNo;
	}
	
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	
	public String getBoardName() {
		
		return boardName;
	}
	
	public void setBoardName(String boardName) {
		if (boardName != null && boardName.length() > 255) {
			this.boardName = boardName.substring(0, 255);
		}
		else {
			this.boardName = boardName;
		}
		
	}
	
	public String getBoardType() {
		return boardType;
	}
	
	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}
	
	public String getBoardSerial() {
		return boardSerial;
	}
	
	public void setBoardSerial(String boardSerial) {
		this.boardSerial = boardSerial;
	}
	
	public boolean equals(Object obj) {
		BoardInfoBean temp = null;
		ArrayList<String> compareFields = new ArrayList<String>() {
			{
				add("deviceId");
				add("boardNo");
				add("slotsNo");
				add("boardIndex");
				//			add("boardType");
				//			add("boardSerial");
			}
		};
		if (obj instanceof BoardInfoBean) {
			temp = (BoardInfoBean) obj;
			return ReflectUtil.beanCompare(this, temp, compareFields);
		}
		else {
			return false;
		}
		
	}
	
	public static void main(String[] args) {
		BoardInfoBean b1 = new BoardInfoBean(), b2 = new BoardInfoBean();
		b1.setDeviceId(1);
		b2.setDeviceId(1);
		b2.setBoardNo(2);
		b1.setBoardNo(2);
		System.out.println(b1.equals(b2));
	}
	
}
