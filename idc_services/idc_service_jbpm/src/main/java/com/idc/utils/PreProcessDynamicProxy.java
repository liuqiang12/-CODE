package com.idc.utils;

import utils.DevLog;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by DELL on 2017/8/23.
 */
/*
* 动态代理情况
* */
public class PreProcessDynamicProxy implements InvocationHandler {

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

        if(method.getName().equals("paramstoBean")){
            System.out.println("1:================流程传递过来的数据["+args[0]+"]转换成实体类idcRunProcCment================");
            return handLocalInvoke(method,args);
        }else if(method.getName().equals("handRunCommnetToMergeInto")){
            System.out.println("2:>>>>>>>>>>>>>>>>>>>开始处理流程审批表IDC_RUN_PROC_CMENT、IDC_HIS_PROC_CMENT START<<<<<<<<<<<<<<< ");
            return handLocalInvoke(method,args);
        }else if(method.getName().equals("handTicketLinkedInfo")){
            System.out.println("3:>>>>>>>>>>>>>>>>>>>开始处理工单中间表:调用方法handTicketLinkedInfo<<<<<<<<<<<<<<< ");
            return handLocalInvoke(method,args);
        }else{
            return method.invoke(originalObj, args);
        }
    }
    public Object handLocalInvoke(Method method, Object[] args) throws Exception{
        Object result = null;
        try {
            result = method.invoke(originalObj, args);
        } catch (Exception e) {
            e.printStackTrace();
            //报错了。请查看一下原因
            if(method.getName().equals("paramstoBean")){
                System.out.println("1:ERROR!ERROR!ERROR!ERROR![com.idc.rest.org_restlet_ext.resource.impl.JbpmResource]接收数据["+args[0]+"]转换成实体类idcRunProcCment时报错。。。");
            }else if(method.getName().equals("handRunCommnetToMergeInto")){
                System.out.println("2:ERROR!ERROR!ERROR!ERROR![com.idc.rest.org_restlet_ext.resource.impl.JbpmResource]开始处理流程审批表IDC_RUN_PROC_CMENT、IDC_HIS_PROC_CMENT 时报错。。。 ");
            }else if(method.getName().equals("handTicketLinkedInfo")){
                System.out.println("3:ERROR!ERROR!ERROR!ERROR![com.idc.rest.org_restlet_ext.resource.impl.JbpmResource]开始处理工单中间表:调用方法handTicketLinkedInfo时报错。。。 ");
            }
            throw new Exception();
        }
        return result;
    }
}
