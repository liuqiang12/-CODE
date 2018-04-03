package utils;

import javax.xml.namespace.QName;

/**
 * Created by DELL on 2017/9/4.
 * 各种方法测试
 */
public class CxfClientInvoke {
    private static final String WSDL = "http://localhost:8080/idc/webService/isp_publish?wsdl";
    private static final String NAMESPACE = "http://service.isp.idc.com/";

    /**
     * 存根方法测试
     */
    /**
     * 生成对应的压缩文件
     * @param xmlContent
     * @param suffixAliasName
     */
    public void stubEventForISPXMLUtils(String xmlContent,String suffixAliasName){
        /*客户端的接口信息，包含了接口方法和参数*/
        /*IspEventService factory = new IspEventService();
        IEventService iEventService = factory.getEventServiceImplPort();
        iEventService.createISPEventService(xmlContent,suffixAliasName);*/
    }

    /**
     * 动态调用测试
     */
    public static void dynamicUtils(){
        QName methodName = new QName(NAMESPACE, "addition");

        Object[] objects = CxfInvokeUtil.invoke(WSDL,methodName,1,2);
        System.out.println(objects[0]);
        System.out.println(objects[0].toString());
        /*JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        org.apache.cxf.endpoint.Client client = dcf.createClient();*/
        // url为调用webService的wsdl地址
       /* QName name = new QName("http://service.isp.idc.com/", "addition");
        // paramvalue为参数值
        Object[] objects;
        try {
            objects = client.invoke(name, 1,2);
            System.out.println("1+2:"+objects[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
    public static void proxyFactory(){
       /* JaxWsProxyFactoryBean soapFactoryBean = new JaxWsProxyFactoryBean();
        soapFactoryBean.setAddress(WSDL);
        soapFactoryBean.setServiceClass(IEventService.class);
        Object object = soapFactoryBean.create();

        IEventService service = (IEventService)object;
        int s = service.addition(1,2);
        System.out.println(s);*/
    }

    public static void main(String[] args) {
        proxyFactory();
    }


}
