package com.idc.service;

import com.idc.model.IdcRunProcCment;

/**
 * Created by DELL on 2017/8/29.
 * 动态代理
 */
public interface BasicInfoEventService {
    //获取机架的有关数据
    String getUpdateHouseFun(IdcRunProcCment idcRunProcCment);
    //用户
    String getUpdateUserFun(IdcRunProcCment idcRunProcCment);

    //获取机架的有关数据
    String getDeleteHouseFun(IdcRunProcCment idcRunProcCment);
    //获取删除的用户信息
    String getDeleteUserFun(IdcRunProcCment idcRunProcCment);
    //获取新增的用户信息方法
    String getAddUserFun(IdcRunProcCment idcRunProcCment);
    //获取机架新增的xml方法
    String getAddHouseFun(IdcRunProcCment idcRunProcCment);
    //测试方法
    String addContion(int num1, int num2);
    //判断是否存在资源有变动的情况:是否存在机架资源有变动
    Boolean getIsHaveRackChangeNum(Long ticketInstId);
    //是否存在机架资源有变动
    Boolean getIsHaveIpChangeNum(Long ticketInstId);


}
