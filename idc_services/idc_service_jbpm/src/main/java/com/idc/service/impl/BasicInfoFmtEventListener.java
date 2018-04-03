package com.idc.service.impl;

import com.idc.model.*;
import com.idc.service.IdcIspBasicInfoService;
import com.idc.service.IdcIspInfoService;
import com.idc.service.IdcNetServiceinfoService;
import com.idc.service.IdcReCustomerService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import utils.DevContext;
import utils.plugins.excel.Guid;
import utils.strategy.code.utils.CommonPageParser;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DELL on 2017/8/24.
 * 监听资源信息表:控制相应的数据
 * 1:此处需要调用的是生成的接口的webservice接口信息
 *
 */
@Component("basicInfoFmtEventListener")
@Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class BasicInfoFmtEventListener implements ApplicationListener {
    private static final Log log = LogFactory.getLog(BasicInfoFmtEventListener.class);
    @Autowired
    private IdcIspBasicInfoService idcIspBasicInfoService;
    @Autowired
    private IdcReCustomerService idcReCustomerService;
    @Autowired
    private IdcNetServiceinfoService idcNetServiceinfoService;
    @Autowired
    private IdcIspInfoService idcIspInfoService;

    @Override
    public void onApplicationEvent(ApplicationEvent event){
        if(event instanceof BasicInfoEvent){
            try{
                log.debug("...《=============。。。。。。。。开始创建ISP压缩文件。。。。。。。。=============》...start");
                log.debug("...《=========================。。。。。。。。新增机房所有数据。。。。。。。。=========================》...start");
                //生成的文件目录是：
                VelocityContext context = new VelocityContext();
                addHouseDataXml(context);//新增机房信息
                addUserDataXml(context,null);//新增用户信息
                updateHouseForBusDataXml(context,null);//更新机房数据
                updateUserForBusDataXml(context,null);//更新用户数据根据业务更新
                //删除机房中的信息
                delHouseForIpDataXml(context,null,999);//删除机房的IP资源数据
                delHouseForGatewayDataXml(context);//删除机房链路信息
                //删除用户信息
                delUserDataXml(context,null);
            }catch (Exception e){
                e.printStackTrace();
                log.error(e.getMessage());
            }
        }
    }
    public void delUserDataXml(VelocityContext context,Long ticketInstId){
        delUserDataXMLParam(context,ticketInstId);
        CommonPageParser.WriterPage(context,"DelUserTemplate.xml", DevContext.LOCAL_ISP_TEMPFIELPATH,"2017-11-17DelUserTemplate.xml");
    }

    /**
     * 开通流程
     * 开通变更  调用
     * @param context
     * @param ticketInstId
     */
    public void delHouseForIpDataXml(VelocityContext context,Long ticketInstId,Integer status){
        delHouseForIpDataXMLParam(context,ticketInstId,status);
        getIspSolidDataInfo(context);//固化信息
        CommonPageParser.WriterPage(context,"DelHouseForIpTemplate.xml", DevContext.LOCAL_ISP_TEMPFIELPATH,"2017-11-17DelHouseForIpTemplate.xml");
    }

    /**
     * 一般不调用
     * @param context
     */
    public void delHouseForGatewayDataXml(VelocityContext context){
        getHouseGateWayInfo(context);//链路信息
        getIspSolidDataInfo(context);//固化信息
        CommonPageParser.WriterPage(context,"DelHouseForGatewayTemplate.xml", DevContext.LOCAL_ISP_TEMPFIELPATH,"2017-11-17DelHouseForGatewayTemplate.xml");
    }
    /*public void delHouseDataXml(VelocityContext context){
        delHouseDataXMLParam(context);
        CommonPageParser.WriterPage(context,"DelHouseTemplate.xml", DevContext.LOCAL_ISP_TEMPFIELPATH,"2017-11-17DelHouseTemplate.xml");
    }*/
    /***********************[ 修改用户信息。目前只需要传递工单ID：
     * [修改上报用户信息]
     * 1：开通工单结束后需要上报一次用户信息。
     * 2:开通变更结束后需要上报一次用户信息
     * ]*********************************/
    public void updateUserForBusDataXml(VelocityContext context,Long ticketInstId){
        log.debug("更新用户相关数据..............");
        updateUserBusDataXmlParams(context,ticketInstId);
        getIspSolidDataInfo(context);
        CommonPageParser.WriterPage(context,"UpdateUserForBusTemplate.xml", DevContext.LOCAL_ISP_TEMPFIELPATH,"2017-11-17UpdateUserForBusTemplate.xml");
    }
    /***********************[ 修改机房信息。目前只需要传递工单ID
     * 开通完成。开通变更完成。调用该方法
     * ]*********************************/
    public void updateHouseForBusDataXml(VelocityContext context,Long ticketInstId){
        log.debug("更新机房相关数据..............");
        updateHouseBusDataXmlParams(context,ticketInstId);
        getIspSolidDataInfo(context);
        CommonPageParser.WriterPage(context,"UpdateHouseForBusTemplate.xml", DevContext.LOCAL_ISP_TEMPFIELPATH,"2017-11-17UpdateHouseForBusTemplate.xml");
    }
    public void updateHouseDataXml(VelocityContext context){
        updateHouseDataXML(context);
        CommonPageParser.WriterPage(context,"UpdateHouseTemplate.xml", DevContext.LOCAL_ISP_TEMPFIELPATH,"2017-11-17UpdateHouseTemplate.xml");
    }
    public void addUserDataXml(VelocityContext context,Long ticketInstId){
        updateUserBusDataXmlParams(context,ticketInstId);
        getIspSolidDataInfo(context);
        CommonPageParser.WriterPage(context,"AddUserTemplate.xml", DevContext.LOCAL_ISP_TEMPFIELPATH,"2017-11-17AddUserTemplate.xml");
    }
    public void addHouseDataXml(VelocityContext context){
        initAddHouseDataXML(context);
        CommonPageParser.WriterPage(context,"AddHouseTemplate.xml", DevContext.LOCAL_ISP_TEMPFIELPATH,"2017-11-21AddHouseTemplate.xml");
    }
    /**
     * 初始化时获取所有的机房或者用户数据
     * 其中针对于【互联网数据的用户正常】||其他用户还没有测试
     * 新增机房或者客户信息的数据需要重新整理
     * @param context
     */
    public void addHouseOrUserDataXml(VelocityContext context){
        initAddHouseDataXML(context);
        CommonPageParser.WriterPage(context,"AddUserTemplate.xml", DevContext.LOCAL_ISP_TEMPFIELPATH,"2017-11-21AddUserTemplate.xml");
    }

    public void delUserDataXMLParam(VelocityContext context,Long ticketInstId){
        /* 查询用户。获取用户下的所有服务:通过工单获取相应的删除数据 */
        /**
         * 一个服务对应一个客户:服务对应的客户Id
         */
        List<ServiceInfo> netServiceinfoList = idcIspInfoService.loadNetServiceinfoListByTicketId(ticketInstId);
        //遍历获取机房信息
        loadNetServerHouseId(context,netServiceinfoList);
    }
    public void loadNetServerHouseId(VelocityContext context,List<ServiceInfo> netServiceinfoList){
        Map<Long,ServiceInfo> serverHashMap = new HashMap<Long,ServiceInfo>();
        Map<Long,List<HousesHoldInfo>> hhidMap = new HashMap<Long,List<HousesHoldInfo>>();
        if(netServiceinfoList != null && !netServiceinfoList.isEmpty()){
            context.put("netServiceinfoList",netServiceinfoList);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        context.put("timeStamp",sdf.format(new Date()));
    }


    public void delHouseForIpDataXMLParam(VelocityContext context,Long ticketInstId,Integer status){
        /*getHouseGateWayInfo(context);//链路信息
        getIspSolidDataInfo(context);//固化信息*/
        /*
        * 通过工单获取ip地址id
        * */
        List<IpAttr> ipAttrList = idcIspInfoService.loadIpAttrList(ticketInstId,status);
        if(ipAttrList != null && !ipAttrList.isEmpty()){
            context.put("ipAttrList",ipAttrList);
        }
    }
/*
    public void delHouseDataXMLParam(VelocityContext context){
        getHouseGateWayInfo(context);//链路信息
        getHouseIpSegInfoForDel(context);//ip信息:删除
        getIspSolidDataInfo(context);//固化信息
    }*/

    public void updateHouseBusDataXmlParams(VelocityContext context,Long ticketInstId){
        //第一获取链路
        getHouseGateWayInfo(context);
        //获取ip地址
        getHouseIpSegInfo(context);
        //通过工单ID：获取所有的机架信息
        List<FrameInfo> frameInfoList = idcIspInfoService.loadFrameInfoListByTicketInstId(0,ticketInstId,999);
        context.put("frameInfoList",frameInfoList);
    }
    public void updateUserBusDataXmlParams(VelocityContext context,Long ticketInstId){
        /**
         * 更新业务服务：根据工单查询服务
         */
        List<ServiceInfo> netServiceinfoList = idcIspInfoService.loadNetServiceinfoList(ticketInstId);
        Map<Long,ServiceInfo> serviceInfoMap = new HashMap<Long,ServiceInfo>(); /* 获取ServiceInfo */
        /*通过服务获取用户信息*/
        if(netServiceinfoList != null && !netServiceinfoList.isEmpty()){
            for(int i = 0 ;i < netServiceinfoList.size() ; i++){
                ServiceInfo serviceInfo = netServiceinfoList.get(i);
                Long customerId = serviceInfo.getCustomerIdTmp();
                //用户信息
                UserInfo userInfoTmp = idcIspInfoService.loadReCustomerDataById(customerId);
                //通过工单ID获取
                List<IpAttr> ipAttrList = idcIspInfoService.loadIpAttrList(serviceInfo.getTicketInstId(),999);
                /*如果用户信息没有，则该节点不存在*/

                //随机随机机架id
                String rackId = idcIspInfoService.getRandomSingleRackId(serviceInfo.getTicketInstId());
                if(rackId != null && !"".equals(rackId)){
                    serviceInfo.setRandomSingleRackId(rackId);
                }
                if(userInfoTmp != null && ipAttrList != null && !ipAttrList.isEmpty()){
                    serviceInfo.setUserInfo(userInfoTmp);
                    serviceInfo.setIpAttrList(ipAttrList);
                    serviceInfoMap.put(serviceInfo.getAid(),serviceInfo);
                    serviceInfo.setDistributeTime(ipAttrList.get(0).getDistributeTime());
                }
                String setModel = serviceInfo.getSetMode();
                if(setModel != null && setModel.equals("2")){
                    //虚拟机情况
                    String virtualhostName = serviceInfo.getVirtualhostName();
                    if(virtualhostName == null || "".equals(virtualhostName)){
                        serviceInfoMap.remove(serviceInfo.getAid());
                    }
                }else{
                    if(setModel == null || "".equals(setModel)){
                        serviceInfoMap.remove(serviceInfo.getAid());
                    }
                }
            }
        }
        if(serviceInfoMap != null && !serviceInfoMap.isEmpty()){
            context.put("serviceInfoMap",serviceInfoMap);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        context.put("timeStamp",sdf.format(new Date()));
    }
    public void updateHouseDataXML(VelocityContext context){
        getHouseGateWayInfo(context);//链路信息
        getHouseIpSegInfo(context);//ip信息
        getIspSolidDataInfo(context);//固化信息
        getHouseFrameInfo(context);//机架信息:需要更新的机架
    }
    public void initAddHouseDataXML(VelocityContext context){
        getHouseGateWayInfo(context);//链路信息
        getHouseIpSegInfo(context);//ip信息
        getHouseFrameInfo(context);//机架信息
        getReCustomerData(context);//客户信息
        getIspSolidDataInfo(context);//固化信息
    }
    //获取机房的链路信息
    public void getHouseGateWayInfo(VelocityContext context){
        List<GatewayInfo> gatewayInfoList = idcIspInfoService.loadGatewayInfoList();
        context.put("gatewayInfoList",gatewayInfoList);
    }

    //获取机房的ip地址段
    public void getHouseIpSegInfoForDel(VelocityContext context){
        Long ticketInstId = null;
        //通过工单获取所有的ip信息
        List<IpSegInfo> ipSegInfoList = idcIspInfoService.loadIpSegInfoForIpList(ticketInstId);
        context.put("ipSegInfoList",ipSegInfoList);
    }
    //获取机房的ip地址段
    public void getHouseIpSegInfo(VelocityContext context){
        List<IpSegInfo> ipSegInfoList = idcIspInfoService.loadIpSegInfoForIpList(null);
        context.put("ipSegInfoList",ipSegInfoList);
    }
    //获取未上报的机房  机架信息
    public void getHouseFrameInfo(VelocityContext context){
        //通过【idc_rack】表中字段IS_SEND_ISP标志是否上报：1表示已经是上报了
        List<FrameInfo> frameInfoList = idcIspInfoService.loadFrameInfoList(0);
        context.put("frameInfoList",frameInfoList);
    }

    //固化的相关数据。。。
    public void getIspSolidDataInfo(VelocityContext context){
        //通过【idc_rack】表中字段IS_SEND_ISP标志是否上报：1表示已经是上报了
        IspSolidData ispSolidData = idcIspInfoService.loadIspSolidData();
        //获取相应的人员信息
        List<IdcOfficer> idcOfficerList = idcIspInfoService.loadIdcOfficerList();
        context.put("idcOfficerList",idcOfficerList);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        context.put("timeStamp",sdf.format(new Date()));
        context.put("ispSolidData",ispSolidData);
    }
    //获取所有的用户

    /**
     * =[客户数据重新初始化]=
     * @param context
     */
    public void getReCustomerData_back(VelocityContext context){
        List<UserInfo> userInfoList = idcIspInfoService.loadReCustomerData();
        /*该客户的所有*/
        /*客户ID,对应的服务*/
        /*
        * 1:控制移除
        * */
        Map<String,List<ServiceInfo>> idcUserServerMap = new HashMap<String,List<ServiceInfo>>();
        //该服务对应的机房信息
        Map<Long,List<HousesHoldInfo>> serviceWithHousesHoldMap = new HashMap<Long,List<HousesHoldInfo>>();
        Map<Long,List<IpAttr>> serviceIpAttrMap = new HashMap<Long,List<IpAttr>>();
        Map<Long,String> resourceAttrMap = new HashMap<Long,String>();
        List<Long> ipAttrRemoveServerList = new ArrayList<Long>();
        Set<String> userRemoveAidList = new HashSet<String>();
        for(int i = 0 ;i < userInfoList.size(); i++){
            String aid = userInfoList.get(i).getAid();
            //通过aid查询服务信息【获取域名用户】
            Integer nature = userInfoList.get(i).getNature();//1:域名用户  2:其他用户
            if(nature == 1){
                List<ServiceInfo> idcUserServerList = idcIspInfoService.loadNetServiceinfoListByCustomerId(aid);
                //服务内容是多个。故需要遍历分解【其中如果该用户下没有】







                if(idcUserServerList != null && !idcUserServerList.isEmpty()){
                    idcUserServerMap.put(aid,idcUserServerList);//用户对应的域名服务
                    for(int k = 0 ; k < idcUserServerList.size() ; k++){
                        ServiceInfo serviceInfo = idcUserServerList.get(k);
                        /**
                         * 服务中对应的资源：
                         * 首先要确定是那个机房；然后任意一个机架；然后IP资源
                         */
                        //机房
                        List<HousesHoldInfo> housesHoldInfoList = idcIspInfoService.loadHousesHoldInfoList(serviceInfo.getTicketInstId());
                        if(housesHoldInfoList != null && !housesHoldInfoList.isEmpty()){
                            serviceWithHousesHoldMap.put(serviceInfo.getAid(),housesHoldInfoList);
                        }else{
                            HousesHoldInfo housesHoldInfoTmp = new HousesHoldInfo();
                            housesHoldInfoTmp.setHhId(Guid.getLongUuid());
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            housesHoldInfoTmp.setDistributeTime(sdf.format(new Date()));
                            housesHoldInfoList = new ArrayList<HousesHoldInfo>();
                            housesHoldInfoList.add(housesHoldInfoTmp);
                            serviceWithHousesHoldMap.put(serviceInfo.getAid(),housesHoldInfoList);
                        }
                        //ip
                        List<IpAttr> ipAttrList = idcIspInfoService.loadIpAttrList(serviceInfo.getTicketInstId(),999);
                        if(ipAttrList != null && !ipAttrList.isEmpty()){
                            serviceIpAttrMap.put(serviceInfo.getAid(),ipAttrList);
                            resourceAttrMap.put(serviceInfo.getAid(),ipAttrList.get(0).getDistributeTime());
                            userRemoveAidList.remove(aid);
                        }else{
                            //需要移除服务
                            serviceWithHousesHoldMap.remove(serviceInfo.getAid());
                            idcUserServerMap.remove(""+aid);
                            userRemoveAidList.add(aid);//移除的用户id
                            //idcUserServerMap.remove(aid+"");

                        }
                    }
                }else{
                    userRemoveAidList.add(aid);//移除的用户id
                }
            }
        }
        //effectiveUserServerList(idcUserServerMap,ipAttrRemoveServerList);
        if(idcUserServerMap != null && !idcUserServerMap.isEmpty()){
            context.put("idcReCustomer_server",idcUserServerMap);
        }
        if(serviceWithHousesHoldMap != null && !serviceWithHousesHoldMap.isEmpty()){
            context.put("server_housesHoldInfo",serviceWithHousesHoldMap);
        }
        if(serviceIpAttrMap != null && !serviceIpAttrMap.isEmpty()){
            context.put("server_housesHoldInfo_ipAttr",serviceIpAttrMap);
        }
        /**
         * 获取有效的用户信息，即移除了没有服务的用户
         */
        effectiveUserList(userInfoList,userRemoveAidList);
        context.put("idcReCustomerList",userInfoList);
    }
    public void getReCustomerData(VelocityContext context){
        List<UserInfo> userInfoList = idcIspInfoService.loadReCustomerData();
        Map<String,UserInfo> userInfoMap = new HashMap<String,UserInfo>();//用户信息
        Map<String,Map<Long,ServiceInfo>> serverByUserIdKeyMap = new HashMap<String,Map<Long,ServiceInfo>>();//服务信息:包含了服务中的ip信息
        for(int i = 0 ;i < userInfoList.size(); i++){
            String aid = userInfoList.get(i).getAid();
            Integer nature = userInfoList.get(i).getNature();//1:域名用户  2:其他用户
            userInfoMap.put(aid,userInfoList.get(i));//[用户信息]
            if(nature == 1){
                /**
                 * 1如果该用户中没有服务，则不需要上报 用户
                 * 2如果没有ip信息。则移除当前服务。如果服务serviceInfoMap为空，表示已经没有了服务。则不需要上报用户。
                 */
                List<ServiceInfo> idcUserServerList = idcIspInfoService.loadNetServiceinfoListByCustomerId(aid);
                if(idcUserServerList != null && !idcUserServerList.isEmpty()){
                    Map<Long,ServiceInfo> serviceInfoMap = new HashMap<Long,ServiceInfo>();
                    for(int k = 0 ; k < idcUserServerList.size() ; k++){
                        ServiceInfo serviceInfo = idcUserServerList.get(k);
                        //服务中还需要获取IP信息 start
                        List<IpAttr> ipAttrList = idcIspInfoService.loadIpAttrList(serviceInfo.getTicketInstId(),999);
                        if(ipAttrList != null && !ipAttrList.isEmpty()){
                            serviceInfo.setIpAttrList(ipAttrList);
                            //服务中还需要获取IP信息 end
                            //distributeTime
                            String distributeTime = ipAttrList.get(0).getDistributeTime();
                            if( distributeTime != null && !"".equals(distributeTime) ){
                                serviceInfo.setDistributeTime(distributeTime);
                            }
                            //获取任意一个机架id
                            String rackId = idcIspInfoService.getRandomSingleRackId(serviceInfo.getTicketInstId());
                            if( rackId != null && !"".equals(rackId) ){
                                serviceInfo.setRandomSingleRackId(rackId);
                            }
                            serviceInfoMap.put(serviceInfo.getAid(),serviceInfo);
                        }else{
                            //移除该服务
                            serviceInfoMap.remove(serviceInfo.getAid());
                        }
                    }
                    if(serviceInfoMap != null && !serviceInfoMap.isEmpty()){
                        serverByUserIdKeyMap.put(aid,serviceInfoMap);
                    }else{
                        userInfoMap.remove(aid);//如果没有服务，则需要移除该用户
                    }
                }else{
                    userInfoMap.remove(aid);//如果没有服务，则需要移除该用户
                }
            }
        }
        context.put("userInfoMap",userInfoMap);
        context.put("serverByUserIdKeyMap",serverByUserIdKeyMap);
    }
    public void effectiveUserList(List<UserInfo> userInfoList,Set<String> userRemoveAidList){
        if(userRemoveAidList != null && !userRemoveAidList.isEmpty()){
            Iterator<UserInfo> it = userInfoList.iterator();
            while(it.hasNext()){
                UserInfo userInfo = it.next();
                String aid = userInfo.getAid();
                Iterator<String> userRemoveAidIt = userRemoveAidList.iterator();
                while(userRemoveAidIt.hasNext()){
                    String aid_tmp = userRemoveAidIt.next();
                    if(aid.equals(aid_tmp)){
                        it.remove();
                    }
                }
            }
        }
    }
    public void effectiveUserServerList(Map<String,List<ServiceInfo>> idcUserServerMap,List<Long> ipAttrRemoveServerList){
        if(ipAttrRemoveServerList != null && !ipAttrRemoveServerList.isEmpty()){
            Set<Map.Entry<String,List<ServiceInfo>>> entrySet = idcUserServerMap.entrySet();
            Iterator<Map.Entry<String,List<ServiceInfo>>> it = entrySet.iterator();
            while(it.hasNext()){
                Map.Entry<String,List<ServiceInfo>> entryMap = it.next();
                String serverAid = entryMap.getKey();
                for(int i = 0 ;i < ipAttrRemoveServerList.size(); i++){
                    String serverAid_tmp = String.valueOf(ipAttrRemoveServerList.get(i));
                    if(serverAid_tmp.equals(serverAid)){
                        entrySet.remove(entryMap);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        List<UserInfo> userInfoList = new ArrayList<UserInfo>();
        for(int i = 0 ;i < 4; i++){
            UserInfo userInfo = new UserInfo();
            userInfo.setAid("aid_"+i);
            userInfo.setUnitName("xxxx"+i);
            userInfoList.add(userInfo);
        }
        Iterator<UserInfo> it = userInfoList.iterator();
        while(it.hasNext()){
            UserInfo userInfo = it.next();
            String aid = userInfo.getAid();
            if(aid.equals("aid_2")){
                it.remove();
            }
        }
        for(int i = 0 ;i < userInfoList.size(); i++){
            System.out.println(userInfoList.get(i).getAid());
        }

    }
    public void wapperUserInfo(VelocityContext context){
        /*查询获取所有的机架*/
        /* 查询所有的客户 */
        List<IdcReCustomer> list = idcReCustomerService.getAllCustomer();
        List<UserInfo> userInfoList = new ArrayList<UserInfo>();
        for(int i = 0 ; i< list.size(); i++){
            IdcReCustomer idcReCustomer = list.get(i);
            UserInfo userInfo = new UserInfo();
            userInfo.setId(initId(idcReCustomer.getId().intValue()));
            userInfo.setNature(idcReCustomer.getDomainStatus()?1:2);
            Info info = new Info();
            info.setUnitName(idcReCustomer.getName());
            info.setUnitNature(Integer.valueOf(idcReCustomer.getAttribute()));
            info.setIdType(Integer.valueOf(idcReCustomer.getIdcardtype()));
            info.setIdNumber(idcReCustomer.getIdcardno());
            info.setAdd(idcReCustomer.getAddr());
            info.setZipCode(idcReCustomer.getZipcode());
            userInfo.setInfo(info);
            /*客户中的所有服务*/
            if(idcReCustomer.getDomainStatus()){
                /*获取服务:根据客户查询*/
                /*查询IP信息*/
                //List<IdcRunTicketResource> idcRunTicketResourceList =  idcRunTicketResourceService.getIdcRunTicketResourceByCustomerId(idcReCustomer.getId());
                List<IdcNetServiceinfo> idcNetServiceinfoList =  idcNetServiceinfoService.getIdcNetServiceinfoByCustomerId(idcReCustomer.getId());
                List<ServiceInfo> serviceInfos = loadNetDomainService(userInfo,idcNetServiceinfoList);
                if(serviceInfos != null && !serviceInfos.isEmpty()){
                    userInfo.setServiceInfos(serviceInfos);
                }
            }
            userInfoList.add(userInfo);
        }
        context.put("userInfoList",userInfoList);
    }

    public List<IpTran> loadIpTranList(Long ticketInstId){
         List<IpTran> list = new ArrayList<IpTran>();
         //List<IdcRunTicketResource> resourceList = idcRunTicketResourceService.loadIpTranList(ticketInstId);
         /*for(int i = 0 ; i< resourceList.size(); i++){
             IpTran ipTran = new IpTran();
             ipTran.setStartIp(resourceList.get(i).getIpName());
             ipTran.setEndIp(resourceList.get(i).getEndip());
             list.add(ipTran);
         }*/
         return list;
    }
    public List<ServiceInfo> loadNetDomainService(UserInfo userInfo,List<IdcNetServiceinfo> idcNetServiceinfoList){
        List<ServiceInfo> serviceInfos = new ArrayList<ServiceInfo>();
        if(idcNetServiceinfoList != null && !idcNetServiceinfoList.isEmpty()){
            for(int i = 0 ;i < idcNetServiceinfoList.size(); i++){
                IdcNetServiceinfo idcNetServiceinfo = idcNetServiceinfoList.get(i);
                ServiceInfo serviceInfo = new ServiceInfo();
                serviceInfo.setServiceId(idcNetServiceinfo.getId());
                //设置服务内容serviceContents
                String  icpsrvcontentcode = idcNetServiceinfo.getIcpsrvcontentcode();//保存的时候以逗号隔开
                if(icpsrvcontentcode != null && !"".equals(icpsrvcontentcode)){
                    String[] icpsrvcontentcodeList = icpsrvcontentcode.split(",");
                    List<Integer> serviceContents = new ArrayList<Integer>();
                    for(int j = 0 ; j < icpsrvcontentcodeList.length; j++){
                        String icpsrvcontentcodeTmp = icpsrvcontentcodeList[j];
                        if(icpsrvcontentcodeTmp != null && !"".equals(icpsrvcontentcodeTmp)){
                            serviceContents.add(Integer.valueOf(icpsrvcontentcodeTmp));
                        }
                    }
                    if(serviceContents != null && !serviceContents.isEmpty()){
                        serviceInfo.setServiceContents(serviceContents);
                    }
                }
                //icprecordtype
                String icprecordtype = idcNetServiceinfo.getIcprecordtype();
                String icprecordno = idcNetServiceinfo.getIcprecordno();//备案号
                String icpaccesstype = idcNetServiceinfo.getIcpaccesstype();//接入方式
                String icpbusinesstype = idcNetServiceinfo.getIcpbusinesstype();//业务类型
                String icpdomain = idcNetServiceinfo.getIcpdomain();//域名
                if(icprecordtype != null && !"".equals(icprecordtype)){
                    serviceInfo.setRegType(Integer.valueOf(icprecordtype));
                }
                serviceInfo.setRegId(icprecordno);
                serviceInfo.setSetMode(icpaccesstype);
                serviceInfo.setBusiness(icpbusinesstype);
                /*域名ID情况*/
                //域名服务:此时只有一个域名
                Domain domain = new Domain();
                domain.setName(icpdomain);
                domain.setId(idcNetServiceinfo.getId()+90000);
                serviceInfo.setDomain(domain);
                /***** 数据中心信息 *****/
                List<HousesHoldInfo> housesHoldInfos = loadHousesHoldInfo(userInfo,idcNetServiceinfo);
                if(housesHoldInfos == null || housesHoldInfos.isEmpty()){
                    continue;
                }
                serviceInfo.setHousesHoldInfos(housesHoldInfos);
                    serviceInfos.add(serviceInfo);
            }
        }
        return serviceInfos;
    }
    public boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    /**
     * 加载
     * @param idcNetServiceinfo
     * @return
     */
    public List<HousesHoldInfo> loadHousesHoldInfo(UserInfo userInfo,IdcNetServiceinfo idcNetServiceinfo){
        /*客户下的所有服务*/
        List<HousesHoldInfo> list = new ArrayList<HousesHoldInfo>();
        for (int i = 0 ;i < 1; i++ ){
            HousesHoldInfo housesHoldInfo = new HousesHoldInfo();
            housesHoldInfo.setHhId(Long.valueOf(idcNetServiceinfo.getId()+80000));
            housesHoldInfo.setDistributeTime(idcNetServiceinfo.getCreateTimeStr());
            //通过用户任意选一个[]
            //String rackId = idcRunTicketResourceService.getSingleRackId(userInfo.getId());
           // housesHoldInfo.setFrameInfoId(rackId);//任意一个
            //然后 bandWidth 网络带宽
            //设置网络带宽。通过相应的工单对应的带宽
            //Long bandWidth = idcRunTicketResourceService.getSingleBandWidth(idcNetServiceinfo.getTicketInstId());
            //housesHoldInfo.setBandWidth(bandWidth);
            //然后配置资源分配的ip  起止
            List<IpTran> ipTrans = loadIpTranList(idcNetServiceinfo.getTicketInstId());
            if(ipTrans == null || ipTrans.isEmpty()){
                continue;
            }
            housesHoldInfo.setIpTrans(ipTrans);
            list.add(housesHoldInfo);
        }
        return list;
    }
    public void loadFrameInfo(VelocityContext context){
        /*查询获取所有的机架*/
        List<Map<String,Object>> loadFrameInfos = idcIspBasicInfoService.loadAllFrameInfo();
        List<FrameInfo> frameInfos = new ArrayList<FrameInfo>();
        for(int i = 0 ; i< loadFrameInfos.size(); i++){
            Map<String,Object> map = loadFrameInfos.get(i);
            FrameInfo frameInfo = new FrameInfo();
            frameInfo.setId(Long.valueOf(String.valueOf(map.get("ID"))));
            frameInfo.setUseType(String.valueOf(map.get("USETYPE")));
            frameInfo.setDistribution(String.valueOf(map.get("DISTRIBUTION")));
            frameInfo.setOccupancy(String.valueOf(map.get("OCCUPANCY")));
            frameInfo.setFrameName(String.valueOf(map.get("FRAMENAME")));
            frameInfos.add(frameInfo);
        }
        context.put("frameInfoList",frameInfos);
    }

    public void wapperHouseInfo(VelocityContext context){
        context.put("houseId",initId(451));
        context.put("gatewayInfoId_1",initId(85));
        context.put("gatewayInfoId_2",initId(86));
        context.put("gatewayInfoId_3",initId(87));
        context.put("gatewayInfoId_4",initId(88));
        context.put("ipSegInfoId_1",initId(90));
        context.put("ipSegInfoId_2",initId(91));
        context.put("ipSegInfoId_3",initId(92));
        context.put("ipSegInfoId_4",initId(93));
    }
    public Long initId(int init){
        return 5000000000l+init;
    }
}
