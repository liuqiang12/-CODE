package system.data.handler;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import system.data.springEvent.EmailEvent;
import utils.DynamicProxy;

import java.lang.reflect.Proxy;

/**
 * Created by DELL on 2017/8/23.
 */
public class MainPoxyTest {
    @Test
    public void test(){
        Persion persion = new PersionImpl();
        DynamicProxy handler = new DynamicProxy();

        Persion proxy = (Persion) Proxy.newProxyInstance(persion.getClass().getClassLoader(), persion.getClass().getInterfaces(), handler);
        System.out.println(proxy.getClass().getName());
        proxy.say();

        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

        //HelloBean hello = (HelloBean) context.getBean("helloBean");
        //hello.setApplicationContext(context);
        EmailEvent event = new EmailEvent("hello","boylmx@163.com","this is a email text!");
        context.publishEvent(event);
        //System.out.println();
    }
}
