package com.client;

/**
 * Created by DuLida on 2016/11/15.
 *
 * 该类为java发布的webservice（服务端客户端在一起，该客户端并非由wsdl2java生成）通过main方法访问
 *
 */
public class ClientForCXF {
    /*
        需要传递的有WSDL_URL   URI  方法名称 参数
     */


   /* public static IspService getInterFace(HttpServletRequest request){
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        org.apache.cxf.endpoint.Client client = dcf
                .createClient("http://localhost:8080/idc/webService/isp_publish?wsdl");
        // url为调用webService的wsdl地址
        //QName name = new QName("http://impl.service.com/", "addition");
        // namespace是命名空间，methodName是方法名
        Integer xmlStr = 1;
        Integer xmlStr1 = 2;
        // paramvalue为参数值
        Object[] objects;
        try {
            objects = client.invoke("addition", 1,2);
            System.out.println("1+2:"+objects[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args) {
        //IspService myWebService = getInterFace();
        //System.out.println(myWebService.createISPEventService("xx","xx"));
    }*/
}
