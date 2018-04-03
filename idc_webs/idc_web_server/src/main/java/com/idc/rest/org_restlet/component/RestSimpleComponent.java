package com.idc.rest.org_restlet.component;

import com.idc.rest.org_restlet_ext.application.SpringJaxRsApplication;
import org.restlet.ext.spring.SpringComponent;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by DELL on 2017/9/6.
 */
@Deprecated
public class RestSimpleComponent extends SpringComponent implements InitializingBean {
    /**
     * 重写createInboundRoot通过attach方法绑定资源类，并且制定了访问路径
     * */
    public RestSimpleComponent() {
       /* System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>流程资源应用组件绑定<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        getDefaultHost().attach("/jbpm", new JbpmApplication());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>其它资源应用组件绑定...<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");*/
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>流程资源应用组件绑定<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        getDefaultHost().attach("/jbpm", new SpringJaxRsApplication());
        //System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>其它资源应用组件绑定...<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    }
}
