package com.idc.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by DELL on 2017/8/23.
 */
/*
* 动态代理情况
* */
public class JbpmProcessDynamicProxy implements InvocationHandler {

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
        if(method.getName().equals("handerTicketPreAccept")){
            System.out.println("1:================保存[预勘预占]流程相关数据================");
            return handLocalInvoke(method,args);
        }else if(method.getName().equals("handerTicketPause")){
            System.out.println("2:================保存[停机]流程相关数据================");
            return handLocalInvoke(method,args);
        }else if(method.getName().equals("handerTicketRecover")){
            System.out.println("3:================保存[复通]流程相关数据================");
            return handLocalInvoke(method,args);
        }else if(method.getName().equals("handerTicketChange")){
            System.out.println("4:================保存[变更流程]流程相关数据================");
            return handLocalInvoke(method,args);
        }else if(method.getName().equals("handerTicketHalt")){
            System.out.println("5:================保存[下线流程]流程相关数据================");
            return handLocalInvoke(method,args);
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
            if(method.getName().equals("handerTicketPreAccept")){
                System.out.println("1:ERROR!ERROR!ERROR!ERROR![com.idc.spring.JbpmProcessEventListener.handerTicketProcMain]保存[预勘预占]流程相关数据================");
            }else if(method.getName().equals("handerTicketPause")){
                System.out.println("2:ERROR!ERROR!ERROR!ERROR![com.idc.spring.JbpmProcessEventListener.handerTicketProcMain]保存[停机]流程相关数据================");
            }else if(method.getName().equals("handerTicketRecover")){
                System.out.println("3:ERROR!ERROR!ERROR!ERROR![com.idc.spring.JbpmProcessEventListener.handerTicketProcMain]保存[复通]流程相关数据================");
            }else if(method.getName().equals("handerTicketChange")){
                System.out.println("4:ERROR!ERROR!ERROR!ERROR![com.idc.spring.JbpmProcessEventListener.handerTicketProcMain]保存[变更流程]流程相关数据================");
            }else if(method.getName().equals("handerTicketHalt")){
                System.out.println("5:ERROR!ERROR!ERROR!ERROR![com.idc.spring.JbpmProcessEventListener.handerTicketProcMain]保存[下线流程]流程相关数据================");
            }
            throw new Exception();
        }
        return result;
    }

}
