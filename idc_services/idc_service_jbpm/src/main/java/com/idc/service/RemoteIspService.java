package com.idc.service;


import com.idc.model.LocalIspCustomer;

import java.util.List;

public interface RemoteIspService {
	List<LocalIspCustomer> loadIspCustomerList();
	void delUserDataXml();
	/*
		加载资源数据【其中】
	 */
	void loadIspResourceUpload();
	/*
		 加载资源删除信息
	 */
	void loadIspResourceDelete();

	/**
	 * update资源数据:
	 */
	void updateResourceData();
	void callProc_resource_hs(Long ticketInstId, Integer ticketStatus);

	void callProcResourceBh(Long ticketInstId, Integer ticketStatus);



}
