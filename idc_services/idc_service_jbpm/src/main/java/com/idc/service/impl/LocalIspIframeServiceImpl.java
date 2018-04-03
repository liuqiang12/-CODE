package com.idc.service.impl;

import com.idc.mapper.LocalIspIframeMapper;
import com.idc.model.LocalIspIframe;
import com.idc.service.LocalIspIframeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.data.supper.service.impl.SuperServiceImpl;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>LOCAL_ISP_IFRAME:机架信息和LOCAL_ISP_HOUSEHOLD表中是对应关系<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Dec 04,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("localIspIframeService")
public class LocalIspIframeServiceImpl extends SuperServiceImpl<LocalIspIframe, Long> implements LocalIspIframeService {
	@Autowired
	private LocalIspIframeMapper mapper;
	/**
	 *   Special code can be written here 
	 */
}
