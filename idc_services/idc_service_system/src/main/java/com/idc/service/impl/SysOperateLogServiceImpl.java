package com.idc.service.impl;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.idc.model.LogType;
import com.idc.model.SysUserinfo;
import com.idc.service.SysUserinfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import system.data.page.EasyUIPageBean;
import system.data.supper.service.impl.SuperServiceImpl;

import com.idc.mapper.SysOperateLogMapper;
import com.idc.model.SysOperateLog;
import com.idc.service.SysOperateLogService;
import utils.typeHelper.MapHelper;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>sys_operate_log:用户操作日志<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Dec 09,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("sysOperateLogService")
public class SysOperateLogServiceImpl extends SuperServiceImpl<SysOperateLog, Integer> implements SysOperateLogService {
	@Autowired
    private SysOperateLogMapper mapper;
    @Autowired
    private SysUserinfoService sysUserinfoService;
    //当前登陆用户
    private Integer userid=null;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void insert(LogType logType, String desc) {
        SysOperateLog sysOperateLog = new SysOperateLog();
        sysOperateLog.setType(BigDecimal.valueOf(logType.getIvalue()));
        sysOperateLog.setDescription(desc);
        sysOperateLog.setCreateTime(new Timestamp(new Date().getTime()));
        if(getUserid()!=null){
            sysOperateLog.setUserId(userid);
            try {
                insert(sysOperateLog);
            } catch (Exception e) {
                logger.info(sysOperateLog.toString());
                logger.error("插入当前操作日志失败,插入本地日志",e);
            }
        }else{
            logger.info(sysOperateLog.toString());
        }

    }

    @Override
    public List<Map<String, Object>> queryListMap() {
        return mapper.queryListMap();
    }

    @Override
    public List<Map<String, Object>> queryListMapPage(EasyUIPageBean page, Map<String, Object> queryMap) {
        page.setParams(MapHelper.queryCondition(queryMap));
        return mapper.queryListMapPage(page);
    }

    @Override
    public void insertList(List<SysOperateLog> list) throws Exception {
        for (SysOperateLog sysOperateLog : list) {
            sysOperateLog.setUserId(getUserid());
        }
        mapper.insertList(list);
    }
    private Integer getUserid(){
        if(userid==null){
            try {
                UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal();
                String username = userDetails.getUsername();
                String password = userDetails.getPassword();
                Map<String,Object> map = new HashMap<>();
                map.put("username",username);
                map.put("password",password);
                SysUserinfo modelByMap = sysUserinfoService.getModelByMap(map);
                userid = modelByMap.getId();
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("获取当前操作用户失败");
            }
        }
        return userid;
    }
	/**
	 *   Special code can be written here 
	 */
}
