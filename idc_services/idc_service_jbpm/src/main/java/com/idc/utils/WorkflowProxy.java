package com.idc.utils;

import com.idc.model.IdcRunProcCment;
import com.idc.model.IdcRunTicket;
import com.idc.service.ActJBPMService;
import com.idc.service.impl.IdcRunProcCmentServiceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by DELL on 2017/8/23.
 */
/*
* 动态代理情况
* */
public class WorkflowProxy implements InvocationHandler {

    public Object originalObj;

    public Object bind(Object originalObj) {
        this.originalObj = originalObj;
        return Proxy.newProxyInstance(
                // 被代理类的ClassLoader
                originalObj.getClass().getClassLoader(),
                // 要被代理的接口,本方法返回对象会自动声称实现了这些接口
                originalObj.getClass().getInterfaces(),
                // 代理处理器对象
                this);
    }

    /**
     * 动态代理信息
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(method.getName().equals("jbpmEndComment")){
            System.out.println("=======================开始处理流程中的审批记录=======================");
            Object result = method.invoke(originalObj, args);
            System.out.println("=======================开始处理流程中的审批记录=======================");

            System.out.println("处理流程相关信息workflow workflow workflow workflow=======================start");
            IdcRunProcCment idcRunProcCment = getIdcRunProcCment(args);

            IdcRunTicket idcRunTicketing = new IdcRunTicket();
            idcRunTicketing.setProcInstId(idcRunProcCment.getProcInstId());
            idcRunTicketing.setTicketInstId(idcRunProcCment.getTicketInstId());
            idcRunTicketing.setTaskId(String.valueOf(idcRunProcCment.getTaskId()));
            /*获取service bean*/
            ActJBPMService actJBPMService =((IdcRunProcCmentServiceImpl)originalObj).getLocalActJBPMService();
            actJBPMService.completeTaskNode(idcRunTicketing,idcRunProcCment.getStatus(),idcRunProcCment.getComment());//完成第一步：申请任务节点
            System.out.println("处理流程相关信息workflow workflow workflow workflow=======================end");
            return result;
        }else{
            //判断是否存在结束标志
            Object result = method.invoke(originalObj, args);
            return result;
        }


    }
    public IdcRunProcCment getIdcRunProcCment(Object[] params) {
        for(int i = 0 ;i < params.length; i++){
            Object obj = params[i];
            if(obj instanceof IdcRunProcCment){
                return (IdcRunProcCment)obj ;
            }
        }
        return null;
    }
}
