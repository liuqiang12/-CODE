package com.JH.dgather.frame.eventalarmsender.webservice.config;

import java.util.HashMap;
import java.util.Map;

import com.JH.dgather.frame.eventalarmsender.webservice.WebServiceInterface;
import com.JH.dgather.frame.globaldata.GloableDataArea;

public class WebService {

	public String name;
	public String wsdlLocation;
	public String namespaceURI;
	public String serviceName;
	public String className;
	public WebServiceInterface wsInterface;
	public Map<Integer, KPIObject> kpiidObjectMap = new HashMap<Integer, KPIObject>();

	public Integer add(KPIObject kpiObject) {
		Integer kpiid = GloableDataArea.getKpiBase().get(
				kpiObject.key.toLowerCase());
		if (kpiid != null) {
			kpiidObjectMap.put(kpiid, kpiObject);
		}
		return kpiid;
	}

	public KPIObject getKPIObject(int kpiId) {
		return kpiidObjectMap.get(kpiId);
	}

}
