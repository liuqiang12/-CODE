package com.idc.service;

import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by 54074 on 2017-09-20.
 */
public interface SysRedisPermissionService {
   /**
    * 获取所有用户的权限信息
    * @return
    */
   Map<String,String> getAllPermissionInfo();

}
