package com.idc.cxf.isp.service;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * Created by DELL on 2017/9/1.
 */

@WebService(targetNamespace="http://service.isp.cxf.idc.com/")
public interface IEventService {
    /**
     * 更加文件str生成对应的文件;文件目录自定义
     * @param xmlContent:文件内容
     * @param suffixAliasName:文件后缀
     * @param directory:文件目录
     * @return
     */
    @WebResult(name = "result")String xmlToFile(@WebParam(name = "xmlContent") String xmlContent, @WebParam(name = "directory") String directory, @WebParam(name = "suffixAliasName") String suffixAliasName);
     //该接口针对ISP生成xml文件的方法

    /**
     * 创建ISP所生成的压缩解压接口
     * @param xmlContent
     * @param suffixAliasName
     * operationName：方法名
     * exclude：设置为true表示此方法不是webservice方法，反之则表示webservice方法
     * @return
     */

    @WebResult(name = "result")String createISPEventService(@WebParam(name = "xmlContent") String xmlContent, @WebParam(name = "suffixAliasName") String suffixAliasName);

    /**
     * 两个整数相加：作为测试用
     * @param opr1
     * @param opr2
     * @return
     */
    int addition_yyyy(int opr1, int opr2);
}
