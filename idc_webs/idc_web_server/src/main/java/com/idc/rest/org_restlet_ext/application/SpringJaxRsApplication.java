package com.idc.rest.org_restlet_ext.application;

import com.idc.rest.org_restlet_ext.resource.impl.IspRemoteCall;
import com.idc.rest.org_restlet_ext.resource.impl.JbpmResource;
import org.restlet.Component;
import org.restlet.ext.jaxrs.InstantiateException;
import org.restlet.ext.jaxrs.JaxRsApplication;
import org.restlet.ext.jaxrs.ObjectFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * 定义了多个相应的resource和application后，还要创建运行环境。
 * restlet架构定义了jaxRsApplication类来初始化基于JAX-RS的WEB-SERVICE运行环境
 *
 */
@org.springframework.stereotype.Component
public class SpringJaxRsApplication extends JaxRsApplication implements InitializingBean, BeanFactoryAware {
    // NOTE: restletComponent instantiation point will be given below (we do it in the spring config)
    @Autowired
    @Qualifier("restletComponent")
    private Component component;

    /**
     * {@inheritDoc}
     */
    /**
     * afterPropertiesSet:初始化BEAN的时候
     * 配置JaxRsApplication的环境信息
     * @throws Exception
     */
    public void afterPropertiesSet() throws Exception {
        //
        setContext(component.getContext().createChildContext());

        final Set<Class<?>> classes = new HashSet<Class<?>>();

        // NOTE: Here we must list all the provider JAX RS classes

        classes.add(JbpmResource.class);
        classes.add(IspRemoteCall.class);
       /* classes.add(LogResource.class);
        classes.add(CategoryResource.class);
        classes.add(BuddyResource.class);
        classes.add(UserResource.class);
        classes.add(ShopResource.class);*/
        add(new Application() {
            @Override
            public Set<Class<?>> getClasses() { return classes; }

        });

        component.getDefaultHost().attach(this);
    }

    /**
     *
     * @param factory
     * @throws BeansException
     */
    public void setBeanFactory(final BeanFactory factory) throws BeansException {
        // TODO Auto-generated method stub
        System.out.println("<<<<<<<<<<<<<<<<<<<<bean初始化时,通过jaxRsClass名称获取jaxRs对象>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        setObjectFactory(new ObjectFactory() {
            public <T> T getInstance(Class<T> jaxRsClass) throws InstantiateException {
                // NOTE: We delegated bean instantiation to spring
                String str =jaxRsClass.getSimpleName();
                System.out.println(str+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>==============");
                return (T) factory.getBean(str.substring(0,1).toLowerCase()+str.substring(1,str.length()));
            }
        });
    }

    /*@Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        *//*该bean初始化前*//*
        //System.out.println( "<<<<<<<<<<<<<<<<<<<<<<<<<对象" + beanName + "开始初始化>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" );
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        *//*该bean初始化后*//*
       // System.out.println( "<<<<<<<<<<<<<<<<<<<<<<<<<对象" + beanName + "实例化完成>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" );
        return bean;
    }*/
}
