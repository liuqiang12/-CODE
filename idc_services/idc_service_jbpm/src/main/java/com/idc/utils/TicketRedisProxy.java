package com.idc.utils;

import com.idc.model.AssetAttachmentinfo;
import com.idc.model.IdcRunProcCment;
import com.idc.model.JbpmResourceEvent;
import com.idc.model.LocalTicketRedisEvent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import utils.DevContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/*
* 动态代理情况
* */
public class TicketRedisProxy implements InvocationHandler {
    private static final Log log = LogFactory.getLog(TicketRedisProxy.class);
    public Object originalObj;
    public ApplicationContext applicationContext;

    public Object bind(Object originalObj,ApplicationContext applicationContext) {
        this.originalObj = originalObj;
        this.applicationContext = applicationContext;
        return Proxy.newProxyInstance(
                // 被代理类的ClassLoader
                originalObj.getClass().getClassLoader(),
                // 要被代理的接口,本方法返回对象会自动声称实现了这些接口
                originalObj.getClass().getInterfaces(),
                // 代理处理器对象
                this);
    }

    /**
     * 动态代理信息:
     * 目前都是在工单创建的时候 和工单审批修改的时候  也就是说工单有变动的时候需要将【工单相关数据保存到REDIS中。】     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //判断是否需要保存到redis中
        if (method.getName().equals("handlerRunTikcetProcess")) {
            log.debug("1:流程审批中的情况================[/actJBPMController/procCmentFormSaveOrUpdate.do]================");
            Object resultObj = handLocalInvoke(method, args);
            /**
             * 1:执行完成后的数据:保存到redis中：发送到保存redis监听
             * 获取对应的数据
             * 第一个参数idcRunProcCment获取对应的工单信息
             */
            Long ticketInstId = getTicketInstIdFromProcCmentFormSaveOrUpdate(args);
            ticketRedisPublishEvent(ticketInstId, null, null);
            return resultObj;
        } else if (method.getName().equals("jbpmTicketDeploy") || method.getName().equals("handTicketComment")) {
            log.debug("创建工单的时候，就需要将工单的详细信息保存到REDIS。。。;/r/n" +
                    "[com.idc.service.ActJBPMService.jbpmTicketDeploy]||;/r/n[IdcRunProcCmentServiceImpl.handlerRunTikcetProcess];/r/n" +
                    "------------start");
            Object resultObj = handLocalInvoke(method, args);
            //此时是返回值中有工单ID
            if (resultObj != null) {
                //发起预勘的时候，时候需要将返回的工单信息所关联的流程数据都缓存到REDIS中
                Long ticketInstId = Long.valueOf(String.valueOf(resultObj));
                log.debug("创建工单的时候---发布到redis中。。。。。。。。start");
                /*步骤*/
                Map<String,Object> remoteMap = new HashMap<String,Object>();
                remoteMap.put("step",method.getName().equals("jbpmTicketDeploy")?CreateTicketEnum.新建后部署阶段.value():null);
                ticketRedisPublishEvent(ticketInstId, null, remoteMap);
                log.debug("创建工单的时候---发布到redis中。。。。。。。。end");
            }
            return resultObj;
        } else if (method.getName().equals("contractHandlerData")) {

            log.debug("目的是修改REDIS中的合同相关数据。。。;/r/n" +
                    "[com.idc.service.impl.IdcContractServiceImpl.handOpenTicketMergeInto;/r/n" +
                    "------------start");
            Object resultObj = handLocalInvoke(method, args);
            //此时是返回值中有工单ID
            if (resultObj != null) {
                //发起预勘的时候，时候需要将返回的工单信息所关联的流程数据都缓存到REDIS中
                Long ticketInstId = Long.valueOf(String.valueOf(resultObj));
                log.debug("开始将redis中合同的有关数据保存或者修改  start");
                ticketRedisPublishEvent(ticketInstId, DevContext.OPEN_CONTRACT, null);
                log.debug("开始将redis中合同的有关数据保存或者修改  end");
            }
            return resultObj;

        } else if (method.getName().equals("insertAttachInfo")) {
            /*log.debug("目的是修改REDIS中的合同对应的附件信息。。。;/r/n" +
                    "[com.idc.service.impl.IdcContractServiceImpl.contractHandlerData方法体中[insertAttachInfo];/r/n" +
                    "------------start");*/
            Object resultObj = handLocalInvoke(method, args);
            /*AssetAttachmentinfo assetAttachmentinfo = getModelFromAssetAttachmentinfo(args);
            Long ticketInstId = assetAttachmentinfo.getTicketInstId();
            Map<String, Object> remoteMap = new HashMap<String, Object>();
            remoteMap.put(DevContext.ATTACHMENT, assetAttachmentinfo);
            //是获取了的附件的实体类
            log.debug("开始将redis中合同的有关数据保存或者修改  start");
            ticketRedisPublishEvent(ticketInstId, DevContext.ATTACHMENT, remoteMap);
            log.debug("开始将redis中合同的有关数据保存或者修改  end");*/
            return resultObj;

        } else {
            return method.invoke(originalObj, args);
        }
    }
    public Long getTicketInstIdFromProcCmentFormSaveOrUpdate(Object[] args) throws Exception{
        if(args == null || args.length == 0){
            throw new Exception("兄弟:你传的参数有问题。。。。。。请你再次检查");
        }
        for(int i = 0  ;i < args.length; i++){
            if(args[i] instanceof IdcRunProcCment) {
                return ((IdcRunProcCment)args[i]).getTicketInstId();
            }
        }
        return null;
    }



    public AssetAttachmentinfo getModelFromAssetAttachmentinfo(Object[] args) throws Exception{
        if(args == null || args.length == 0){
            throw new Exception("兄弟:你传的参数有问题。。。。。。请你再次检查");
        }
        for(int i = 0  ;i < args.length; i++){
            if(args[i] instanceof AssetAttachmentinfo) {
                return ((AssetAttachmentinfo)args[i]);
            }
        }
        return null;
    }

    public void ticketRedisPublishEvent(Long ticketInstId,String singleStatus,Map<String,Object> remoteMap) throws Exception {
		/*通过传递过来的工单ID获取相应的工单有关信息保存到redis中*/
        System.out.println("------------------- 开始工单保存到redis中 -------------------------start");

        Map<String,Object> map = new HashMap<String,Object>();
        //remoteMap是代表传入的其他参数
        if(remoteMap != null && !remoteMap.isEmpty()){
            map.putAll(remoteMap);
        }
        if(singleStatus != null && !"".equals(singleStatus)){
            map.put("singleStatus",singleStatus);
        }
        map.put("ticketInstId",ticketInstId);
        //localTicketRedisEventListener
        LocalTicketRedisEvent ticketRedisEvent = new LocalTicketRedisEvent(applicationContext,map);
        //此时需要保存相应的工单到redis中
        applicationContext.publishEvent(ticketRedisEvent);
        System.out.println("------------------- 开始工单保存到redis中 -------------------------end");
    }
    public void ticketPublishEvent(Long ticketInstId) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("ticketInstId",ticketInstId);
        JbpmResourceEvent jbpmResourceEvent = new JbpmResourceEvent(applicationContext,map);
        applicationContext.publishEvent(jbpmResourceEvent);
    }

    public Object handLocalInvoke(Method method, Object[] args) throws Exception{
        Object result = null;
        try {
            result = method.invoke(originalObj, args);
        } catch (Exception e) {
            e.printStackTrace();
            //报错了。请查看一下原因
            if(method.getName().equals("handlerRunTikcetProcess")){
                System.out.println("1:ERROR!ERROR!ERROR!ERROR!;/r/n[/actJBPMController/procCmentFormSaveOrUpdate.do]。。。");
            }
            throw new Exception();
        }
        return result;
    }
}
