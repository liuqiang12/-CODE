package com.idc.action;

import com.idc.model.IdcLocation;
import com.idc.service.IdcLocationService;
import com.idc.service.ResourceViewService;
import com.idc.service.SysUserinfoService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 */
@Component
@Aspect
public class MyInterceptor {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ResourceViewService resourceViewService;
    @Autowired
    private IdcLocationService idcLocationService;
    @Autowired
    private SysUserinfoService sysUserinfoService;

    //声明一个切入点
//    @Pointcut("execution(* com.idc.action.device.DeviceAction.*(..))")
//    private void anyMethod() {
//        System.out.println("======");
//    }

    //声明前置通知
//    @Before("execution(* com.idc.action.*.*.addOrUpdateResourceInfo(..)) || execution(* com.idc.action.*.*.deleteResourceInfos(..))")
//    public void doBefore(JoinPoint joinPoint) {
//        System.out.println(joinPoint.getTarget().getClass());
//        System.out.println("前置通知");
//    }

    //声明后置通知
//    @AfterReturning(pointcut = "anyMethod()", returning = "result")
//    public void doAfterReturning(String result) {
//        System.out.println("后置通知");
//        System.out.println("---" + result + "---");
//    }
//
    // 声明例外通知
//    @AfterThrowing(pointcut = "anyMethod()", throwing = "e")
//    public void doAfterThrowing(Exception e) {
//        System.out.println("例外通知");
//        System.out.println(e.getMessage());
//    }

    //声明最终通知
    @After("execution(* com.idc.action.*.*.addOrUpdateResourceInfo(..)) " +
            "|| execution(* com.idc.action.*.*.deleteResourceInfos(..)) " +
            "|| execution(* com.idc.action.*.*.importResourceData(..)) " +
            "|| execution(* com.idc.service.impl.NetPortServiceImpl.updatePortStatusByPortIds(..)) " +
            "|| execution(* com.idc.service.impl.IdcRackServiceImpl.updateRackStatusByRackIds(..)) " +
            "|| execution(* com.idc.service.impl.IdcRackunitServiceImpl.updateRackunitStatusByIds(..)) " +
            "|| execution(* com.idc.service.impl.IdcDeviceServiceImpl.updateDeviceStatusByDeviceIds(..)) ")
    public void doAfter(JoinPoint joinPoint) {
        logger.debug(joinPoint.getTarget().getClass().toString());
        String classStr = joinPoint.getTarget().getClass().toString();
        List<IdcLocation> idcLocationList = idcLocationService.queryList();
        Integer locationId = 0;
        if (idcLocationList != null && idcLocationList.size() > 0) {
            locationId = idcLocationList.get(0).getId();
        }
        String type = getRaloadType(classStr);
        try {
            resourceViewService.reLoadCountByColumn(locationId, type);
            if ((classStr.indexOf("Rack") > -1 && classStr.indexOf("Rackunit") == -1) || classStr.indexOf("Prot") > -1) {
                //更新各机房统计值
                resourceViewService.reloadRoomStatistics();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*通过角色将相关用户信息维护到表JBPM_OA_AUTHOR*/
    @After("execution(* com.idc.service.impl.SysUserinfoServiceImpl.saveOrUpdate(..)) "+
        "||execution(* com.idc.service.impl.SysUserinfoServiceImpl.deleteById(..)) "+
        "||execution(* com.idc.service.impl.SysUserinfoServiceImpl.bindUserAndGroup(..)) "+
        "||execution(* com.idc.service.impl.SysUserGroupServiceImpl.saveOrUpdate(..)) "+
        "||execution(* com.idc.service.impl.SysUserGroupServiceImpl.deleteGroupsAndRoles(..)) "+
        "||execution(* com.idc.service.impl.LnUserUsergrpServiceImpl.insertList(..)) "+
        "||execution(* com.idc.service.impl.LnUserUsergrpServiceImpl.deleteByLnUserUsergrps(..)) "+
        "||execution(* com.idc.service.impl.LnUsergrpRoleServiceImpl.insertList(..)) "+
        "||execution(* com.idc.service.impl.LnUsergrpRoleServiceImpl.deleteByLnUsergrpRoles(..)) "+
        "||execution(* com.idc.service.impl.SysRoleinfoServiceImpl.saveOrUpdate(..)) "+
        "||execution(* com.idc.service.impl.SysRoleinfoServiceImpl.deleteByList(..)) ")
    public void doAuthorFromGroup() {
        try{
            sysUserinfoService.authorFromGroup();
        }catch(Exception e){
            logger.debug("通过角色将相关用户信息维护到表JBPM_OA_AUTHOR,执行失败！");
            e.printStackTrace();
        }
    }
//    //声明最终通知
//    @After("execution(* com.idc.service.impl.NetProtServiceImpl.updatePortStatusByPortIds(..)) " +
//            "|| execution(* com.idc.service.impl.IdcRackServiceImpl.updateRackStatusByRackIds(..))" +
//            "|| execution(* com.idc.service.impl.IdcRackunitServiceImpl.updateRackunitStatusByIds(..))" +
//            "|| execution(* com.idc.service.impl.IdcDeviceServiceImpl.updateDeviceStatusByDeviceIds(..))")
//    public void reloadStatus(JoinPoint joinPoint) {
//        logger.debug(joinPoint.getTarget().getClass().toString());
//        String classStr = joinPoint.getTarget().getClass().toString();
//        List<IdcLocation> idcLocationList = idcLocationService.queryList();
//        Integer locationId = 0;
//        if (idcLocationList != null && idcLocationList.size() > 0) {
//            locationId = idcLocationList.get(0).getId();
//        }
//        String type = getRaloadType(classStr);
//        try {
//            resourceViewService.reLoadCountByColumn(locationId, type);
//            logger.debug("最终通知");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    //声明环绕通知
//    @Around("anyMethod()")
//    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
//        System.out.println("进入方法---环绕通知");
//        //显示调用，确保被代理的方法被调用
//        Object o = pjp.proceed();
//        System.out.println("退出方法---环绕通知");
//        return o;
//    }
    public String getRaloadType(String classStr) {
        if (classStr.indexOf("Location") > -1) {//数据中心
            //classStr = "reLoadLocationImpl";
            classStr = "reLoadBandWidthImpl";
        } else if (classStr.indexOf("Building") > -1) {//机楼
            classStr = "reLoadBuildingImpl";
        } else if (classStr.indexOf("Machineroom") > -1) {//机房
            //classStr = "reLoadMachineroomImpl";
            classStr = "reLoadBandWidthImpl";
        } else if (classStr.indexOf("Rack") > -1 && classStr.indexOf("Rackunit") == -1) {//机架
            classStr = "reLoadRackImpl";
        } else if (classStr.indexOf("Rackunit") > -1) {//机位
            classStr = "reLoadRackunitImpl";
        } else if (classStr.indexOf("Device") > -1) {//设备
            classStr = "reLoadDeviceImpl";
        } else if (classStr.indexOf("Prot") > -1) {//端口
            classStr = "reLoadPortImpl";
        } else {
            classStr = null;
        }
        return classStr;
    }
}
