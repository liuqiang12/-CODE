package com.idc.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import system.data.inter.DataSource;
import system.data.supper.service.impl.SuperServiceImpl;

import com.idc.mapper.LocalIspCustomerMapper;
import com.idc.model.LocalIspCustomer;
import com.idc.service.LocalIspCustomerService;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>LOCAL_ISP_CUSTOMER:本地客户数据<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 27,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("localIspCustomerService")
public class LocalIspCustomerServiceImpl extends SuperServiceImpl<LocalIspCustomer, Long> implements LocalIspCustomerService {
	@Autowired
	private LocalIspCustomerMapper mapper;
	/**
	 *   Special code can be written here 
	 */
}
