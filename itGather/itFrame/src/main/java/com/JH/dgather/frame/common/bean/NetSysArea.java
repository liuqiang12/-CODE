package com.JH.dgather.frame.common.bean;

public class NetSysArea {
	int areaid;
	int areapid;
	String areaname;
	int arearange;
	//1.国家，2.省份，3地区/直辖/治制区 4.城市 5.县/区
	int areasort;
	String description;
	int delflag;
	
	public int getAreaid() {
		return areaid;
	}
	
	public void setAreaid(int areaid) {
		this.areaid = areaid;
	}
	
	public int getAreapid() {
		return areapid;
	}
	
	public void setAreapid(int areapid) {
		this.areapid = areapid;
	}
	
	public String getAreaname() {
		return areaname;
	}
	
	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	
	public int getArearange() {
		return arearange;
	}
	
	public void setArearange(int arearange) {
		this.arearange = arearange;
	}
	
	public int getAreasort() {
		return areasort;
	}
	
	public void setAreasort(int areasort) {
		this.areasort = areasort;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getDelflag() {
		return delflag;
	}
	
	public void setDelflag(int delflag) {
		this.delflag = delflag;
	}
}