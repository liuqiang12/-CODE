package com.JH.dgather.frame.eventalarmsender.webservice.config;

public class KPIFilter {
	public int minLevel = -1;

	public boolean isFiltered(int alarmlevel) {
		return alarmlevel < minLevel;
	}

}
