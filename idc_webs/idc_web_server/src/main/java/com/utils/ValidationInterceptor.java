package com.utils;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import utils.DevLog;

/**
 * Created by DELL on 2017/9/1.
 * 数据验证方面的拦截器
 *
 */
public class ValidationInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

    public ValidationInterceptor() {
        super(Phase.PRE_PROTOCOL);//配置拦截时机，一定要有，准备协议化得时候拦截　
    }
    @Override
    public void handleMessage(SoapMessage msg) throws Fault {
        // 读header里的数据
        /*Header header=msg.getHeader(new QName("userName"));//参数和客户端传的一样
        if(header!=null){
        }*/
        //没通过校验,抛出异常System.out.println("getInMessage------------------"+message.getExchange().getInMessage());
        System.out.println("--->>>>>-----getInFaultMessage------------------>>>>>"+msg.getExchange().getInFaultMessage());
        System.out.println("--->>>>>-----getOutMessage------------------>>>>>"+msg.getExchange().getOutMessage());
        System.out.println("--->>>>>-----getOutFaultMessage------------------>>>>>"+msg.getExchange().getOutFaultMessage());
        //throw new Fault(new RuntimeException("用户名或密码不对"));
    }
}
