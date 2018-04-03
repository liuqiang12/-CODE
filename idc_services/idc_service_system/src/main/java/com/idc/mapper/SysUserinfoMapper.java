package com.idc.mapper;

import com.idc.model.SysUserinfo;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.PathVariable;
import system.data.page.PageBean;
import system.data.supper.mapper.SuperMapper;

import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>sys_userinfo:系统用户信息表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Sep 23,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface SysUserinfoMapper extends SuperMapper<SysUserinfo, Integer>{
    /**
     *   Special code can be written here
     */
    /**
     *  query user and roles of user through username
     * @param username
     * @return
     */
    SysUserinfo queryUserAndRoleListThroughUsername(String username);
    SysUserinfo queryUserAndRoleListThroughUsernameNotJl(String username);

    SysUserinfo queryUserById(String id);

    String queryApplyIDByTicket(String ticketInstId);

    String queryUserAreaById(String userId);
    /**
     * activit自定义的组方法
     * @param userCode
     * @return
     */
    List<Map<String, String>> getRoleToActByUserName(String userCode);
    /**
     * activit自定义的用户方法
     * @param userCode
     * @return
     */
    Map<String, String> getUserToActMapByUserName(String userCode);
    /**
     * activit自定义的角色的方法
     * @param groupCode
     * @return
     */
    Map<String, String> getRoleToActByGroupCode(String groupCode);
    /**
     * 获取用户id
     * @param username
     * @param password
     * @return
     */
    String getIdByUserNameAndPassword(@PathVariable("username") String username, @PathVariable("password") String password);

    List<Map<String, Object>> queryListPageMap(PageBean<T> page);

    List<Map<String, Object>> queryListMap(SysUserinfo sysUserinfo);

    /**
     * 获取组内所有用户
     * @return
     */
    List<Map> getAllUser();


    List<SysUserinfo> getUserListByTicketUserIds(String user_ids);

    /*查询满足条件的所有用户*/
    List<Map<String, Object>> queryUserListByObjectMap(Map<String, Object> map);

    /*查询满足条件的所有用户 page*/
    List<Map<String, Object>> queryUserListPageByObjectMap(PageBean<T> page);
    List<String> getAdminNams();

    /*通过角色将相关用户信息维护到表JBPM_OA_AUTHOR*/
    void authorFromGroup() throws Exception;

}

 