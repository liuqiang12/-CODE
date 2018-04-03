package utils;


import com.idc.cxf.isp.service.IEventService;
import com.idc.cxf.isp.service.impl.IspEventService;

/**
 * Created by DELL on 2017/9/4.
 */
public class ISPEventInvoke {
    private static IspEventService eventFactory;
    private static ISPEventInvoke ourInstance = new ISPEventInvoke();

    public static ISPEventInvoke getInstance() {
        if(eventFactory == null){
            eventFactory = new IspEventService();
        }
        return ourInstance;
    }

    private ISPEventInvoke() {
    }
    /**
     * 生成对应的压缩文件
     * @param xmlContent
     * @param suffixAliasName
     */
    public String stubEventForISPXMLUtils(String xmlContent,String suffixAliasName){
        /*客户端的接口信息，包含了接口方法和参数*/

        IEventService iEventService = eventFactory.getEventServiceImplPort();
        return iEventService.createISPEventService(xmlContent,suffixAliasName);
    }
    /*生成对应的xml文件*/
    public String xmlToFile(String xmlContent,String directory,String suffixAliasName){
        /*客户端的接口信息，包含了接口方法和参数*/
        IEventService iEventService = eventFactory.getEventServiceImplPort();
        return iEventService.xmlToFile(xmlContent,directory,suffixAliasName);
    }
}
