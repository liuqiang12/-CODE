package com.idc.mapper;

import com.idc.model.IdcLink;
import org.apache.ibatis.annotations.Param;
import system.data.supper.mapper.SuperMapper;

import java.util.List;
import java.util.Map;
/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_LINK:连接表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 01,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcLinkMapper extends SuperMapper<IdcLink, Long>{

    List<Map<String, Object>> queryListByObjectMap(IdcLink idcLink);

    /*通过A端机架获取所有对端信息*/
    List<IdcLink> queryListByRackIdA(Map<String, Object> mapQ);

    /*通过AZ端信息获取链路信息  机架组-机架*/
    List<Map<String, Object>> queryIdcLinksByAZRsR(Map<String, Object> map);

    /*通过AZ端信息获取链路信息  机架组-设备*/
    List<Map<String, Object>> queryIdcLinksByAZRsD(Map<String, Object> map);

    /*通过AZ端信息获取链路信息  机架-机架*/
    List<Map<String, Object>> queryIdcLinksByAZRR(Map<String, Object> map);

    /*通过AZ端信息获取链路信息  机架-设备*/
    List<Map<String, Object>> queryIdcLinksByAZRD(Map<String, Object> map);

    /*通过AZ端信息获取链路信息  设备-机架*/
    List<Map<String, Object>> queryIdcLinksByAZDR(Map<String, Object> map);

    /*通过AZ端信息获取链路信息  设备-设备*/
    List<Map<String, Object>> queryIdcLinksByAZDD(Map<String, Object> map);

    /*通过链路IDS获取链路信息*/
    List<IdcLink> queryLinkModelByIds(List list);

    /*通过AZ信息获取链路信息  不区分AZ端是机架组、机架、设备     同一在xml中通过类型区分*/
    List<Map<String,Object>> queryIdcLinksByAZMap(Map<String, Object> map);

    /*通过AZ端端口删除链路   如：352352_352352*/
    void deleteLinkByAZPortStrList(List list) throws Exception;

    /*跟据端子ID，获取上行端口信息*/
    List<Map<String,Object>> queryUpPortInfoByPortIdZ(@Param("zid") Long zid);

    /*跟据端子ID，获取下行端口信息*/
    List<Map<String,Object>> queryDownPortInfoByPortIdA(@Param("aid") Long aid);

    List<IdcLink> queryListByZendrackId(@Param("zrackId") Long zrackId);

    List<IdcLink> queryListByAendrackId(Map<String,Object> map);

    List<IdcLink> queryListByZenddeviceId(@Param("zdeviceId") Long zdeviceId);

    List<IdcLink> queryListByAenddeviceId(@Param("adeviceId") Long adeviceId);

}

 