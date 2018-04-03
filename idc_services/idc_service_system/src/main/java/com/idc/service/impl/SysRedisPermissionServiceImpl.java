package com.idc.service.impl;


import com.google.common.collect.Lists;
import com.idc.mapper.*;

import com.idc.service.SysRedisPermissionService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by 54074 on 2017-09-20.
 */
@Service("sysRedisPermissionService")
class SysRedisPermissionServiceImpl implements SysRedisPermissionService {
    @Autowired
    private  SysOperateMapper sysOperateMapper;
    @Autowired
    private SysRoleinfoMapper sysRoleinfoMapper;
    @Autowired
    private SysMenuinfoMapper sysMenuinfoMapper;
    @Autowired
    private SysUserinfoMapper sysUserinfoMapper;
    /*@Autowired
    RedisTemplate<String, Object> redisTemplate;*/


    @Override
    public Map<String,String> getAllPermissionInfo() {

        Map<String,String> obj=new HashMap<String,String>();
        List<Map> sysOperateList = sysOperateMapper.getAllSysOperate();
        List<Map> regionsList = sysRoleinfoMapper.getAllRegions();
        List<Map> menuList=sysMenuinfoMapper.getAllMenu();
        List<Map> roleInfos = sysRoleinfoMapper.getAllRoleinfos();
        List<Map> userList = sysUserinfoMapper.getAllUser();

        for (int i = 0; i < userList.size(); i++) {
           Map userMap= userList.get(i);
            JSONObject allPermission=new JSONObject();
           String user_id=userMap.get("ID").toString();
            List<Map> im_sysOperateList =getFilterList(sysOperateList, user_id);
            List<Map> im_regionsList = getFilterList(regionsList, user_id);
            List<Map> im_roleInfos =getFilterList(roleInfos, user_id);
            List<Map> im_menuList =getFilterList(menuList, user_id);
            allPermission.put("operatelist",im_sysOperateList);
            allPermission.put("regionsList",im_regionsList);
            allPermission.put("roleInfos",im_roleInfos);
            allPermission.put("menuList",im_menuList);
            obj.put(user_id,allPermission.toString());

           // .put("USER_PERMISSION_INFO",user_id,allPermission.toString());
        }
   //     redisTemplate.opsForHash().putAll("USER_PERMISSION_INFO",obj);
      return obj;
    }
    List<Map> getFilterList(List<Map> list,String key)
    {
        List<Map> result= Lists.newArrayList();
        for (int i=0;i<list.size();i++)
        {
            Map temMap=list.get(i);
            if( temMap.get("USER_ID")!=null&&temMap.get("USER_ID").toString().equals(key))
            {
                result.add(list.get(i));
            }

        }
        return result;
    }
}
