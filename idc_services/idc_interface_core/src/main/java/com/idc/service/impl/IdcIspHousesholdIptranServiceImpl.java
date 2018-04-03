package com.idc.service.impl;

import com.idc.mapper.IdcIspHousesholdIptranMapper;
import com.idc.model.IpAttr;
import com.idc.service.IdcIspHousesholdIptranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.data.supper.service.impl.SuperServiceImpl;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>IDC_ISP_HOUSESHOLD_IPTRAN:在该机房中所分配的IP地址段信息，内容见表27，有地址转换的需要填写对应的私网地址（可重复多次）<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Aug 17,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("idcIspHousesholdIptranService")
public class IdcIspHousesholdIptranServiceImpl extends SuperServiceImpl<IpAttr, Long> implements IdcIspHousesholdIptranService {
	@Autowired
	private IdcIspHousesholdIptranMapper mapper;
	/**
	 *   Special code can be written here 
	 */
}
