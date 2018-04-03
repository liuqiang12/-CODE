package utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

import javax.xml.namespace.QName;

/**
 *
 * 使用cxf 调用webservice 接口
 *
 *
 */
public class CxfInvokeUtil {

    /**
     *
     * 调用webservice 接口
     *
     * @param wsdlUrl  wsdl 地址
     *
     * @param method  调用方法名
     *
     * @param params  接口传入参数
     *
     * @return
     *
     */
    public static synchronized Object[] invoke(String wsdlUrl, QName method, Object... params){

        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        org.apache.cxf.endpoint.Client client = dcf.createClient(wsdlUrl);

        Object[] objects = null;

        if(StringUtils.isEmpty(wsdlUrl)){

            System.out.println("cxf 调用webservice 参数缺失：wsdl url 未传入");
            return objects;
        }

        if(StringUtils.isEmpty(method.getLocalPart())){

            System.out.println("cxf 调用webservice 执行方法名缺失：method 未传入");
            return objects;
        }

        try {
            objects=client.invoke(method,params);
            System.out.println(objects[0].toString());
        } catch (Exception e) {
            e.printStackTrace();
            DevLog.debug("cxf 调用webservice 执行错误：",e);

        }

        return objects;
    }
}