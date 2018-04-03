package com.idc.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/*
* 动态代理情况
* 流程部署的时候需要 保存到缓存池中
* */
public class JbpmDeployProxy implements InvocationHandler {

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
        if(method.getName().equals("jbpmTicketDeploy")){
            System.out.println("1:================ 部署工单或者说创建工单start ================");
            Object resultObj = handLocalInvoke(method,args);
            System.out.println("1:================ 部署工单或者说创建工单end ================工单ID=["+resultObj+"]");
            System.out.println("2:================ 将该工单保存到redis缓存中 ================工单ID=["+resultObj+"],start");
            System.out.println("[发布监听，在监听接听中进行保存。目的是现有数据和redis缓存保存保持异步处理]" +
                    "同时其他有关工单数据保存或者修改redis数据的时候，容易扩展。支持新增、修改、删除、过期功能！");
            System.out.println("2:================ 将该工单保存到redis缓存中 ================工单ID=["+resultObj+"],end");
            return resultObj;
        }else{
            return  method.invoke(originalObj, args);
        }

    }

    //传递过来的参数时str类型
    public Object handLocalInvoke(Method method, Object[] args) throws Exception{
        Object result = null;
        try {
            result = method.invoke(originalObj, args);
        } catch (Exception e) {
            e.printStackTrace();
            //报错了。请查看一下原因
            if(method.getName().equals("jbpmTicketDeploy")){
                System.out.println("1:ERROR!ERROR!ERROR!ERROR![com.idc.service.ActJBPMService.handlerJbpmProcess]部署工单或者说创建工单 end ================");
            }
            throw new Exception();
        }
        return result;
    }

}
