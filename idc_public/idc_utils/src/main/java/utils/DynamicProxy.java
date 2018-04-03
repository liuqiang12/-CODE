package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by DELL on 2017/8/23.
 */
/*
* 动态代理情况
* */
public class DynamicProxy implements InvocationHandler {

    private Logger logger = LoggerFactory.getLogger(DynamicProxy.class);
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

        if(!"toString".equals(method.getName())){
            System.out.println("before method excute======================!");
            System.out.println("Method:" + method.getName());
            Object result = method.invoke(obj, args);
            System.out.println("after method excute======================!");
            return result;
        }
        return null;
    }

    public static void main(String[] args) {
        Object o = new Object();
        System.out.println(o.getClass().getMethods()[0].getName());
    }
}
