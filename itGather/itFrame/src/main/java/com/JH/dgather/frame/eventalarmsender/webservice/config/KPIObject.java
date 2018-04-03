package com.JH.dgather.frame.eventalarmsender.webservice.config;

public class KPIObject {

	public String key;
	public String name;
	public String wsType;
	public String wsLevel;
	public KPIFilter kpiFilter = new KPIFilter();

	@Override
	public String toString() {
		return "kpi key:" + key + ",name:" + name + ",wsType:" + wsType
				+ ",wsLevel:" + wsLevel;
	}

	public boolean isFiltered(int alarmlevel) {
		return kpiFilter.isFiltered(alarmlevel);
	}

}
