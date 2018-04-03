package com.idc.rest.org_restlet.application;

import com.idc.rest.org_restlet.component.RestSimpleComponent;
import com.idc.rest.org_restlet_ext.resource.impl.JbpmResource;
import org.restlet.Component;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.ext.jaxrs.JaxRsApplication;
import org.restlet.routing.Router;
import utils.DevLog;

/**
 * Created by DELL on 2017/9/5.
 * 扩展javax.ws.rs.core.Application类
 * 为资源配置路径
 */
@Deprecated
public class JbpmApplication extends JaxRsApplication {
    /*@Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> rrcs = new HashSet<Class<?>>();
        System.out.println("开始绑定资源[如果有多个资源则可以多次绑定]");
        rrcs.add(JbpmResource.class);
        return rrcs;
    }*/
    public static void main(String[] args) throws Exception {
        //开启web服务，端口号为8182
        Component component = new RestSimpleComponent();
        component.getServers().add(Protocol.HTTP, 8182);
        component.start();
    }

     public JbpmApplication(Context context) {
        super(context);
        setContext(this.getContext().createChildContext());
    }
    @Override
    public Restlet createInboundRoot() {
        Router router = new Router(this.getContext());
        /* 工单id */
        System.out.println(">>>>>>>>>>>>>>工单路由资源处理>>>>>>>>>>>");
        router.attach("/ticket/{ticketInstId}", JbpmResource.class);
        return router;

    }

}
