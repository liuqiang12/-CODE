package com.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by DELL on 2017/9/5.
 * 资源代理处理
 */
public class JbpmResourceProxy implements InvocationHandler {

    public Object obj;

    public Object bind(Object obj) {
        this.obj = obj;
        return Proxy.newProxyInstance(
                // 被代理类的ClassLoader
                obj.getClass().getClassLoader(),
                // 要被代理的接口,本方法返回对象会自动声称实现了这些接口
                obj.getClass().getInterfaces(),
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
        System.out.println("before method excute======================!");
        System.out.println("Method:" + method.getName());
        Object result = method.invoke(obj, args);
        System.out.println("after method excute======================!");
        return result;
    }
}
