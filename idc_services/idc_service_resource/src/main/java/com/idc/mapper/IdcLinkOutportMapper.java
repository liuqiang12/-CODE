package com.idc.mapper;

import org.apache.poi.ss.formula.functions.T;
import system.data.page.PageBean;
import system.data.supper.mapper.SuperMapper;
import com.idc.model.IdcLinkOutport;

import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_LINK_INFO:链路信息表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Sep 28,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcLinkOutportMapper extends SuperMapper<IdcLinkOutport, Long> {

    /*获取链路基本信息及调单号、附件  page*/
    List<Map<String, Object>> queryLinkOutportListPage(PageBean<T> page);

    /*获取链路基本信息及调单号、附件*/
    List<Map<String, Object>> queryLinkOutportList(Map<String, Object> map);

}

 