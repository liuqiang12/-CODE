package com.idc.service.impl;

import com.idc.mapper.IdcRunTicketResourceMapper;
import com.idc.model.BasicInfo;
import com.idc.model.BasicInfoEvent;
import com.idc.model.IdcRunProcCment;
import com.idc.service.BasicInfoEventService;
import com.idc.service.IdcIspBasicInfoService;
import com.idc.utils.IspDynamicProxy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import utils.XMLParser;
import utils.typeHelper.FileHelper;

import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2017/8/24.
 * 监听资源信息表:控制相应的数据
 * 1:此处需要调用的是生成的接口的webservice接口信息
 *
 */
@Component("basicInfoEventListener")
@Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class BasicInfoEventListener  implements ApplicationListener,BasicInfoEventService {
    private static final Log log = LogFactory.getLog(BasicInfoEventListener.class);
    @Autowired
    private IdcIspBasicInfoService idcIspBasicInfoService;
    @Autowired
    private IdcRunTicketResourceMapper idcRunTicketResourceMapper;

    @Override
    public void onApplicationEvent(ApplicationEvent event){
        if(event instanceof BasicInfoEvent){
            log.debug("...《=============。。。。。。。。开始创建ISP压缩文件。。。。。。。。=============》...start");
            BasicInfoEvent basicInfoEvent = (BasicInfoEvent)event;
            /*if(basicInfoEvent.target instanceof IdcRunProcCment){
                //目标对象
                BasicInfoEventService real = new BasicInfoEventListener();
                //拦截对象new IspDynamicProxy()
                BasicInfoEventService proxy = (BasicInfoEventService)new IspDynamicProxy().bind(this);
                Map<String,Object> map = basicInfoEvent.map;
                try{
                    System.out.println("========================listener start==========================");
                    System.out.println("=============================fileUpload start=====================");
                    IdcRunProcCment idcRunProcCment = (IdcRunProcCment)basicInfoEvent.target;
                    Boolean isResourcesHaveChangeNum = getIsHaveRackChangeNum(idcRunProcCment.getTicketInstId());
                    Boolean isIpHaveChangeNum = getIsHaveIpChangeNum(idcRunProcCment.getTicketInstId());
                   // String addHouseStr = proxy.getAddHouseFun(idcRunProcCment);

                    *//*if(isResourcesHaveChangeNum){*//*
                        //此时需要处理有关的数据:update 所有的机架信息
                        System.out.println("新增机架上报情况");
                        //String addHouseStr = proxy.getAddHouseFun(idcRunProcCment);
                        System.out.println("修改机架上报情况");
                        //String updateHouseStr = proxy.getUpdateHouseFun(idcRunProcCment);
                        System.out.println("删除机架上报情况");
                        //String deleteHouseStr = proxy.getDeleteHouseFun(idcRunProcCment);
                        System.out.println("修改用户上报情况");
                        //String updateUserStr = proxy.getUpdateUserFun(idcRunProcCment);

                    *//*}*//*
                    if(isIpHaveChangeNum){
                        //删除的用户信息
                        //String deleteUserStr = proxy.getDeleteUserFun(idcRunProcCment);
                        //新增的用户信息
                        //String addUserStr = proxy.getAddUserFun(idcRunProcCment);
                    }
                    System.out.println("=============================fileUpload  end=====================");
                    System.out.println("========================listener end==========================");
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {

                }
            }*/
        }
    }

    @Override
    public String getDeleteHouseFun(IdcRunProcCment idcRunProcCment) {
        List<BasicInfo> basicInfos = idcIspBasicInfoService.loadDeleteHouseInfoByTicketId(idcRunProcCment.getTicketInstId());
        String update_str = XMLParser.marshal(basicInfos.get(0), BasicInfo.class,"UTF-8");
        try {
            FileHelper.getInstance().textToFile("D:/360驱动大师目录/下载保存目录/xxxxxxxxx_deleteHouseTicket.xml",update_str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return update_str;
        /*try {
            FileHelper.getInstance().textToFile("D:/360驱动大师目录/下载保存目录/xxxxxxxxx_updateHouseTicket.xml",update_str);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public String getUpdateHouseFun(IdcRunProcCment idcRunProcCment) {
        List<BasicInfo> basicInfos = idcIspBasicInfoService.loadUpdatHouseInfoByTicketId(idcRunProcCment.getTicketInstId());
        String update_str = XMLParser.marshal(basicInfos.get(0), BasicInfo.class,"UTF-8");
        try {
            FileHelper.getInstance().textToFile("D:/360驱动大师目录/下载保存目录/xxxxxxxxx_updateHouseTicket.xml",update_str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return update_str;
        /*try {
            FileHelper.getInstance().textToFile("D:/360驱动大师目录/下载保存目录/xxxxxxxxx_updateHouseTicket.xml",update_str);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
    @Override
    public String getUpdateUserFun(IdcRunProcCment idcRunProcCment) {
        List<BasicInfo> basicInfos = idcIspBasicInfoService.loadUpdatHouseInfoByTicketId(idcRunProcCment.getTicketInstId());
        String update_str = XMLParser.marshal(basicInfos.get(0), BasicInfo.class,"UTF-8");
        try {
            //FileHelper.getInstance().textToFile("D:/360驱动大师目录/下载保存目录/xxxxxxxxx_updateUserTicket.xml",update_str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return update_str;
        /*try {
            FileHelper.getInstance().textToFile("D:/360驱动大师目录/下载保存目录/xxxxxxxxx_updateHouseTicket.xml",update_str);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }


    @Override
    public String getAddHouseFun(IdcRunProcCment idcRunProcCment) {
        List<BasicInfo> basicInfos = idcIspBasicInfoService.loadAddByTicketId(idcRunProcCment.getTicketInstId());
        String update_str = XMLParser.marshal(basicInfos.get(0), BasicInfo.class,"UTF-8");
        try {
            FileHelper.getInstance().textToFile("D:/360驱动大师目录/下载保存目录/xxxxxxxxx_addHouseTicket.xml",update_str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return update_str;
    }

    @Override
    public String addContion(int num1, int num2) {
        String s =String.valueOf(num1+num2);
        return s;
    }

    @Override
    public Boolean getIsHaveRackChangeNum(Long ticketInstId) {
        Boolean isResourcesHaveChangeNum = idcIspBasicInfoService.getIsHaveChangeNum(ticketInstId);
        return isResourcesHaveChangeNum;
    }

    @Override
    public Boolean getIsHaveIpChangeNum(Long ticketInstId) {
        //ip情况是否有变动
        Boolean isIpHaveChangeNum = idcIspBasicInfoService.getIsHaveChangeWithIpNum(ticketInstId);
        return isIpHaveChangeNum;
    }

    @Override
    public String getDeleteUserFun(IdcRunProcCment idcRunProcCment) {
        //客户中的ip情况:创建删除该用户的信息,然后新增该用户信息
        List<BasicInfo> delteInfos = idcIspBasicInfoService.loadDeleteByTicketId(idcRunProcCment.getTicketInstId());
        //生成的相应的文件
        String delete_str = XMLParser.marshal(delteInfos.get(0), BasicInfo.class,"UTF-8");
        try {
            FileHelper.getInstance().textToFile("D:/360驱动大师目录/下载保存目录/xxxxxxxxx_deleteUserTicket.xml",delete_str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return delete_str;
    }

    @Override
    public String getAddUserFun(IdcRunProcCment idcRunProcCment) {
        //然后在新增用户信息:只是新增了用户信息
        List<BasicInfo> newInfos = idcIspBasicInfoService.loadNewInfoByTicketId(idcRunProcCment.getTicketInstId());
        String add_str = XMLParser.marshal(newInfos.get(0), BasicInfo.class,"UTF-8");
        /*try {
            FileHelper.getInstance().textToFile("D:/360驱动大师目录/下载保存目录/xxxxxxxxx_addWithUserTicket.xml",add_str);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return add_str;
    }
}
