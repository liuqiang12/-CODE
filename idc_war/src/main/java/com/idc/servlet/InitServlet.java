package com.idc.servlet;

import com.idc.model.*;
import com.idc.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisHashCommands;
import org.springframework.data.redis.connection.RedisSetCommands;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import system.data.supper.service.RedisManager;
import system.data.supper.service.impl.RedisCacheUtil;
import utils.DevContext;
import utils.typeHelper.DateHelper;

import java.util.*;

/**
 * Created by DELL on 2017/9/13.
 */
//@Component
public class InitServlet implements InitializingBean {
    private static final Log log = LogFactory.getLog(InitServlet.class);
    @Autowired
    private RedisManager redisManager;
    @Autowired
    private ActJBPMService actJBPMService;
    @Autowired
    private AssetAttachmentinfoService assetAttachmentinfoService;
    @Autowired
    private IdcReProddefService idcReProddefService;//产品模型
    @Autowired
    TicketRelationService ticketRelationService;
    @Autowired
    RedisCacheUtil redisCacheUtil;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private AssetBaseinfoService assetBaseinfoService;
    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            assetBaseinfoIntoRedis();
            //EGNORE_REDIS_SYNCH
            String EGNORE_REDIS_SYNCH = DevContext.EGNORE_REDIS_SYNCH;
            log.debug("--------------EGNORE_REDIS_SYNCH：【"+EGNORE_REDIS_SYNCH+"】;/r/n EGNORE_REDIS_SYNCH没有值或者false表示需要将数据%n保存到REDIS中..........");
            if(EGNORE_REDIS_SYNCH == null || DevContext.FALSE.equalsIgnoreCase(EGNORE_REDIS_SYNCH)){
                long startTime=System.currentTimeMillis();//记录开始时间
                //设置redisTemplate的ValueSerializer为StringSerializer
                redisTemplate.setValueSerializer(redisTemplate.getStringSerializer());
                //此时采用监听方式发布出去
                log.debug(">>>>>>>>>>>>>>>>:电脑机子慢的情况，大概耗时60s开始将流程中所用到的数据都缓存到REDIS中,[流程变动的同时也会维护他........] start");
                Map<String,Object> procJbpmMap = new HashMap<String,Object>();
                String PROJECT_MANAGER_USE_REDIS = DevContext.PROJECT_MANAGER_USE_REDIS;
                /*目前只是因为其他数据维护没必要。。。。。。*/
                procJbpmMap.put("projectManagerUseRedis",PROJECT_MANAGER_USE_REDIS.toLowerCase());
                if(PROJECT_MANAGER_USE_REDIS != null && "true".equalsIgnoreCase(PROJECT_MANAGER_USE_REDIS)){
                    log.debug("项目启动的时候，如果是PROJECT_MANAGER_USE_REDIS:true则我只需要[<<<<维护运行时工单和合同信息>>>>]");
                    //这里只是需要维护工单和合同情况
                    actJBPMService.callJbpmLinkedIntoRedis(procJbpmMap);
                    List<IdcRunTicket> idcRunTicketList = (List<IdcRunTicket>)procJbpmMap.get("run_ticket_cursor");
                    List<IdcHisTicket> idcHisTicketList = (List<IdcHisTicket>)procJbpmMap.get("his_ticket_cursor");
                    List<IdcContract> idcContractList = (List<IdcContract>)procJbpmMap.get("contract_cursor");

                    runTicketIntoRedis(idcRunTicketList);
                    hisTicketIntoRedis(idcHisTicketList);
                    contractIntoRedis(idcContractList);
                }else{
                    //传递进去的参数后台处理。。。。
                    actJBPMService.callJbpmIntoRedis(procJbpmMap);
                /* 【这个是所有的数据】 */

                    List<IdcRunTicket> idcRunTicketList = (List<IdcRunTicket>)procJbpmMap.get("run_ticket_cursor");
                    List<SysUserinfo> sysUserinfoList = (List<SysUserinfo>)procJbpmMap.get("userinfo_cursor");
                    List<Map<String,Object>> ticketrunTaskList = (List<Map<String,Object>>)procJbpmMap.get("run_ticket_task_cursor");

                    log.debug("接下来就是维护基础信息表。。。。。。。。。。。。。。");

                    List<IdcHisTicket> idcHisTicketList = (List<IdcHisTicket>)procJbpmMap.get("his_ticket_cursor");
                    List<IdcContract> idcContractList = (List<IdcContract>)procJbpmMap.get("contract_cursor");
                    List<IdcReCustomer> idcReCustomerList = (List<IdcReCustomer>)procJbpmMap.get("customer_cursor");
                    List<IdcRunProcCment> idcRunProcCmentList = (List<IdcRunProcCment>)procJbpmMap.get("run_proccment_cursor");
                    List<IdcHisProcCment> idcHisProcCmentList = (List<IdcHisProcCment>)procJbpmMap.get("his_proccment_cursor");
                    List<IdcRunTicketResource> idcRunTicketResourceList = (List<IdcRunTicketResource>)procJbpmMap.get("run_ticket_resource_cursor");
                    List<IdcHisTicketResource> idcHisTicketResourceList = (List<IdcHisTicketResource>)procJbpmMap.get("his_ticket_resource_cursor");
                    List<AssetAttachmentinfo> assetAttachList = (List<AssetAttachmentinfo>)procJbpmMap.get("asset_attachmentinfo_cursor");
                    //订单信息
                    List<IdcReProduct> reProductList = (List<IdcReProduct>)procJbpmMap.get("re_product_cursor");
                    List<IdcNetServiceinfo> idcNetServiceinfoList = (List<IdcNetServiceinfo>)procJbpmMap.get("net_service_cursor");

                    log.debug("以下的方法在服务接口LocalTicketRedisService服务实现类localTicketRedisEventListener中需要保持一致;n%" +
                            "*******************************");

                    runTaskTicketIntoRedis(ticketrunTaskList);

                    runTicketIntoRedis(idcRunTicketList);
                    hisTicketIntoRedis(idcHisTicketList);
                    contractIntoRedis(idcContractList);
                    runProcCmentIntoRedis(idcRunProcCmentList);
                    hisProcCmentIntoRedis(idcHisProcCmentList);
                    runTicketResourceIntoRedis(idcRunTicketResourceList);
                    hisTicketResourceIntoRedis(idcHisTicketResourceList);
                    customerIntoRedis(idcReCustomerList);
                    //订单信息也放到缓存中
                    reProductIntoRedis(reProductList);
                    //IDC_NET_SERVICEINFO 服务数据也保存到redis中
                    netServerInfoIntoRedis(idcNetServiceinfoList);
                    //idcReRackModel
                    log.debug("*******************************");
                    log.debug("手动先拷贝数据库中附件的数据,后期是不会用关系数据库保存附件..............start");
                    assetAttachmentinfoIntoRedisWithBytes(assetAttachList);
                    log.debug("手动先拷贝数据库中附件的数据,后期是不会用关系数据库保存附件..............end");
                }
                long endTime=System.currentTimeMillis();//记录结束时间
                float excTime=(float)(endTime-startTime)/1000;

                log.debug("执行时间>>>>>>>>>>>>>>>>>>>>>>"+excTime+"s");

                log.debug(">>>>>>>>>>>>>>>>:电脑机子慢的情况，大概耗时"+excTime+"s开始将流程中所用到的数据都缓存到REDIS中,[流程变动的同时也会维护他........]   end");
            }else{
                //维护一些有关过期定时查找工单的公共方法
                //..................[]..................
                log.debug("【单独完成动态】");
                //PROJECT_MANAGER_USE_REDIS
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存合同到REDIS缓存中
     * @param contractEntity
     * @throws Exception
     */
    public void cashContractIntoRedis(IdcContract contractEntity) throws Exception{
        /*1：处理过和没有处理过的
          2：过期前三个月的合同都需要保存到未处理过的单子中
         */
        if(!contractEntity.getIshandStatus()){
            Date contractstart =  contractEntity.getContractstart();//合同开始时间
            Long contractperiod =  contractEntity.getContractperiod();//合同期限
            //结束时间
            if(contractstart != null && contractperiod != null){
                Date contractEnd = DateHelper.addDataWithMonth(contractstart,contractperiod.intValue());
                //减三个月
                Date contractPre3MonthEnd = DateHelper.subDataWithMonth(contractEnd,3);
                contractEntity.setContractEnd(contractEnd);
                contractEntity.setContractPre3MonthEnd(contractPre3MonthEnd);
            }
            redisManager.putIntoListJedisCache(DevContext.NOT_HAND_IDC_CONTRACT,contractEntity);
        }else{
            //已经处理过了的合同
            redisManager.putIntoListJedisCache(DevContext.HAND_IDC_CONTRACT,contractEntity);
        }
    }


    public void hisTaskTicketIntoRedis(List<Map<String,Object>> ticketTaskList) throws Exception {
        if(ticketTaskList != null && !ticketTaskList.isEmpty()){
            log.debug("开始将[历史的工单:查询列表使用]都保存如REDIS...................start");
            Iterator<Map<String,Object>> runIt = ticketTaskList.iterator();
            while(runIt.hasNext()){
                Map<String,Object> ticketTaskEntity = runIt.next();
                redisManager.putBinaryJedisCache(DevContext.IDC_HIS_TICKET_TASK,String.valueOf(ticketTaskEntity.get("TICKETINSTID")),ticketTaskEntity);
            }
            log.debug("开始将[历史的工单:查询列表使用]都保存如REDIS...................end");
        }
    }
    public void runTaskTicketIntoRedis(List<Map<String,Object>> ticketTaskList) throws Exception {
        if(ticketTaskList != null && !ticketTaskList.isEmpty()){
            log.debug("开始将[正在运行的工单:查询列表使用]都保存如REDIS...................start");
            Iterator<Map<String,Object>> runIt = ticketTaskList.iterator();
            while(runIt.hasNext()){
                Map<String,Object> ticketTaskEntity = runIt.next();
                redisManager.putBinaryJedisCache(DevContext.IDC_RUN_TICKET_TASK,String.valueOf(ticketTaskEntity.get("TICKETINSTID")),ticketTaskEntity);
            }
            log.debug("开始将[正在运行的工单:查询列表使用]都保存如REDIS...................end");
        }
    }
    public void runTicketIntoRedis(List<IdcRunTicket> idcRunTicketList) throws Exception {
        RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
        RedisConnection redisConnection = RedisConnectionUtils.getConnection(factory);
        //开启管道
        RedisHashCommands redisHashCommands = factory.getConnection();
        RedisSetCommands redisSetCommands = factory.getConnection();
        ((RedisConnection) redisSetCommands).openPipeline();
        ((RedisConnection) redisHashCommands).openPipeline();

        if(idcRunTicketList != null && !idcRunTicketList.isEmpty()){
            log.debug("开始将[正在运行的工单]都保存如REDIS...................start");
            Iterator<IdcRunTicket> runIt = idcRunTicketList.iterator();
            while(runIt.hasNext()){
                IdcRunTicket ticketEntity = runIt.next();
                if(ticketEntity != null){
                    //同时将工单保存到zset中，方便进行分页查询
                    //log.debug(ticketEntity.getSerialNumber()+":将工单建立不同的工单关联关系内存子表。。。。。。。。。。。。。。。。start:里面部分是没有使用。可以去掉。。。");
                    ticketRelationService.singleRunTicketIntoRedis(ticketEntity,redisHashCommands,redisSetCommands);
                    //log.debug(ticketEntity.getSerialNumber()+":将工单建立不同的工单关联关系内存子表。。。。。。。。。。。。。。。。end里面部分是没有使用。可以去掉。。。");
                    redisManager.putBinaryJedisCache(DevContext.IDC_RUN_TICKET,String.valueOf(ticketEntity.getId()),ticketEntity);
                }
            }
            log.debug("开始将[正在运行的工单]都保存如REDIS...................end");
        }
        //关闭管道
        ((RedisConnection) redisSetCommands).closePipeline();
        ((RedisConnection) redisSetCommands).close();
        ((RedisConnection) redisHashCommands).closePipeline();
        ((RedisConnection) redisHashCommands).close();
    }

    public void hisTicketIntoRedis(List<IdcHisTicket> idcHisTicketList) throws Exception {
        RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
        RedisConnection redisConnection = RedisConnectionUtils.getConnection(factory);
        //开启管道
        RedisHashCommands redisHashCommands = factory.getConnection();
        RedisSetCommands redisSetCommands = factory.getConnection();
        ((RedisConnection) redisSetCommands).openPipeline();
        ((RedisConnection) redisHashCommands).openPipeline();

        if(idcHisTicketList != null && !idcHisTicketList.isEmpty()){
            log.debug("开始将[历史工单]都保存如REDIS...................start");
            Iterator<IdcHisTicket> it = idcHisTicketList.iterator();
            while(it.hasNext()){
                IdcHisTicket ticketEntity = it.next();
                if(ticketEntity != null){
                    //将该类保存如redis中
                    //log.debug(ticketEntity.getSerialNumber()+":将工单建立不同的工单关联关系内存子表。。。。。。。。。。。。。。。。start");
                    ticketRelationService.singleHisTicketIntoRedis(ticketEntity,redisHashCommands,redisSetCommands);
                    //log.debug(ticketEntity.getSerialNumber()+":将工单建立不同的工单关联关系内存子表。。。。。。。。。。。。。。。。end");
                    redisManager.putBinaryJedisCache(DevContext.IDC_HIS_TICKET,String.valueOf(ticketEntity.getId()),ticketEntity);
                }

            }
            log.debug("开始将[历史工单]都保存如REDIS...................end");
        }
        //关闭管道
        ((RedisConnection) redisSetCommands).closePipeline();
        ((RedisConnection) redisSetCommands).close();
        ((RedisConnection) redisHashCommands).closePipeline();
        ((RedisConnection) redisHashCommands).close();
    }

    public void contractIntoRedis(List<IdcContract> contractList) throws Exception {
        if(contractList != null && !contractList.isEmpty()){
            log.debug("开始将[合同]都保存如REDIS...................start");
            Iterator<IdcContract> contractIt = contractList.iterator();
            while(contractIt.hasNext()){
                IdcContract contractEntity = contractIt.next();
                //将该类保存如redis中
                redisManager.putBinaryJedisCache(DevContext.IDC_CONTRACT,String.valueOf(contractEntity.getId()),contractEntity);
                if(contractEntity.getTicketInstId() != null ){
                    redisManager.putBinaryJedisCache(DevContext.IDC_CONTRACT_TICKET_KEY,String.valueOf(contractEntity.getTicketInstId()),contractEntity);
                }
                cashContractIntoRedis(contractEntity);
            }
            log.debug("开始将[合同]都保存如REDIS...................end");
        }
    }

    public void runProcCmentIntoRedis(List<IdcRunProcCment> idcRunProcCmentList) throws Exception {
        if(idcRunProcCmentList != null && !idcRunProcCmentList.isEmpty()){
            log.debug("开始将[运行时流程意见]都保存如REDIS...................start");
            Iterator<IdcRunProcCment> idcRunProcCmentIt = idcRunProcCmentList.iterator();
            while(idcRunProcCmentIt.hasNext()){
                IdcRunProcCment runProcCmentEntity = idcRunProcCmentIt.next();
                //将该类保存如redis中:
                if(runProcCmentEntity != null){
                    redisManager.putBinaryJedisCache(DevContext.IDC_RUN_PROC_CMENT,runProcCmentEntity.getProdInstId()+"||"+runProcCmentEntity.getTicketInstId()+"||"+runProcCmentEntity.getId(),runProcCmentEntity);
                }
            }
            log.debug("开始将[运行时流程意见]都保存如REDIS...................end");
        }
    }
    public void hisProcCmentIntoRedis(List<IdcHisProcCment> idcHisProcCmentList) throws Exception {
        if(idcHisProcCmentList != null && !idcHisProcCmentList.isEmpty()){
            log.debug("开始将[历史流程意见]都保存如REDIS...................start");
            Iterator<IdcHisProcCment> idcHisProcCmentListIt = idcHisProcCmentList.iterator();
            while(idcHisProcCmentListIt.hasNext()){
                IdcHisProcCment hisProcCmentEntity = idcHisProcCmentListIt.next();
                //将该类保存如redis中:  订单||工单
                if(hisProcCmentEntity != null){
                    redisManager.putBinaryJedisCache(DevContext.IDC_HIS_PROC_CMENT,hisProcCmentEntity.getProdInstId()+"||"+hisProcCmentEntity.getTicketInstId()+"||"+hisProcCmentEntity.getId(),hisProcCmentEntity);
                }

            }
            log.debug("开始将[历史流程意见]都保存如REDIS...................end");
        }
    }

    public void runTicketResourceIntoRedis(List<IdcRunTicketResource> idcRunTicketResourceList) throws Exception {
        if(idcRunTicketResourceList != null && !idcRunTicketResourceList.isEmpty()){
            log.debug("开始将[运行工单资源]都保存如REDIS...................start");
            Iterator<IdcRunTicketResource> idcRunTicketResourceListIt = idcRunTicketResourceList.iterator();
            while(idcRunTicketResourceListIt.hasNext()){
                IdcRunTicketResource runTicketResourceEntity = idcRunTicketResourceListIt.next();
                //将该类保存如redis中
                if(runTicketResourceEntity != null){
                    redisManager.putBinaryJedisCache(DevContext.IDC_RUN_TICKET_RESOURCE,String.valueOf(runTicketResourceEntity.getId()),runTicketResourceEntity);
                }
            }
            log.debug("开始将[运行工单资源]都保存如REDIS...................end");
        }
    }
    public void assetBaseinfoIntoRedis() throws Exception {
        RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
        RedisConnection redisConnection = RedisConnectionUtils.getConnection(factory);
        //开启管道
        RedisHashCommands redisHashCommands = factory.getConnection();
        RedisSetCommands redisSetCommands = factory.getConnection();
        ((RedisConnection) redisSetCommands).openPipeline();
        ((RedisConnection) redisHashCommands).openPipeline();

        log.debug("【【【【【【【【【【【【【【【【【加载数据字典数据】】】】】】】】】】】】】】----start");
            /*查询[业务字典]*/
        List<AssetBaseinfo> assetBaseinfoList = assetBaseinfoService.queryComboboxDataIntoRedis();
        Iterator<AssetBaseinfo> assetIt = assetBaseinfoList.iterator();
        while(assetIt.hasNext()){
            AssetBaseinfo assetBaseinfo = assetIt.next();
            String parentCode = assetBaseinfo.getParentCode();
            String code = assetBaseinfo.getValue();
            Long id = assetBaseinfo.getId();
            String redisKey_asset = null;
            if(code != null){
                if(parentCode != null){
                    //log.debug("格式：ASSET_BASEINFO:PARENTCODE_xxxx_CODE_yyyy");
                    redisKey_asset = DevContext.ASSET_BASEINFO+":PARENTCODE_"+parentCode+"_CODE_("+assetBaseinfo.getValue()+"):_Label_("+assetBaseinfo.getLabel()+")";
                    byte[] assetBaseinfoKeyValByte = redisTemplate.getStringSerializer().serialize(redisKey_asset);
                        /*首先获取*/
                    redisSetCommands.sAdd(assetBaseinfoKeyValByte,assetBaseinfoKeyValByte);
                }else{
                    //log.debug("格式：ASSET_BASEINFO:CODE_xxxx   egnore");
                    redisKey_asset = DevContext.ASSET_BASEINFO+":CODE_"+assetBaseinfo.getValue();
                }

            }
        }
        //关闭管道
        ((RedisConnection) redisSetCommands).closePipeline();
        ((RedisConnection) redisSetCommands).close();
        ((RedisConnection) redisHashCommands).closePipeline();
        ((RedisConnection) redisHashCommands).close();
        log.debug("【【【【【【【【【【【【【【【【【加载数据字典数据】】】】】】】】】】】】】】----  end");
    }

    public void hisTicketResourceIntoRedis(List<IdcHisTicketResource> idcHisTicketResourceList) throws Exception {
        if(idcHisTicketResourceList != null && !idcHisTicketResourceList.isEmpty()){
            log.debug("开始将[历史工单资源]都保存如REDIS...................start");
            Iterator<IdcHisTicketResource> idcHisTicketResourceIt = idcHisTicketResourceList.iterator();
            while(idcHisTicketResourceIt.hasNext()){
                IdcHisTicketResource hisTicketResourceEntity = idcHisTicketResourceIt.next();
                //将该类保存如redis中
                if(hisTicketResourceEntity != null){
                    redisManager.putBinaryJedisCache(DevContext.IDC_HIS_TICKET_RESOURCE,String.valueOf(hisTicketResourceEntity.getId()),hisTicketResourceEntity);
                }

            }
            log.debug("开始将[历史工单资源]都保存如REDIS...................end");
        }
    }

    public void customerIntoRedis(List<IdcReCustomer> idcReCustomerList) throws Exception {
        if(idcReCustomerList != null && !idcReCustomerList.isEmpty()){
            log.debug("开始将[客户资源]都保存如REDIS...................start");
            Iterator<IdcReCustomer> idcReCustomerIt = idcReCustomerList.iterator();
            while(idcReCustomerIt.hasNext()){
                IdcReCustomer customerEntity = idcReCustomerIt.next();
                //将该类保存如redis中
                if(customerEntity != null){
                    redisManager.putBinaryJedisCache(DevContext.IDC_RE_CUSTOMER,String.valueOf(customerEntity.getId()),customerEntity);
                }

            }
            log.debug("开始将[客户资源]都保存如REDIS...................end");
        }
    }


    public void netServerInfoIntoRedis(List<IdcNetServiceinfo> idcNetServiceinfoList) throws Exception {
        if(idcNetServiceinfoList != null && !idcNetServiceinfoList.isEmpty()){
            log.debug("开始将[工单的服务]都保存如REDIS...................start");
            Iterator<IdcNetServiceinfo> idcNetServiceinfoIt = idcNetServiceinfoList.iterator();
            while(idcNetServiceinfoIt.hasNext()) {
                IdcNetServiceinfo netServiceinfoEntity = idcNetServiceinfoIt.next();
                //将该类保存如redis中
                if (netServiceinfoEntity != null) {
                    redisManager.putBinaryJedisCache(DevContext.IDC_NET_SERVICEINFO, String.valueOf(netServiceinfoEntity.getTicketInstId()), netServiceinfoEntity);
                }
            }
            log.debug("开始将[工单的服务]都保存如REDIS...................end");
        }
    }

    public void reProductIntoRedis(List<IdcReProduct> reProductList) throws Exception {
        /*if(reProductList != null && !reProductList.isEmpty()){
            log.debug("开始将[订单信息]都保存如REDIS...................start");
            Iterator<IdcReProduct> idcReProductIt = reProductList.iterator();
            while(idcReProductIt.hasNext()){
                IdcReProduct reProductEntity = idcReProductIt.next();
                if(reProductEntity != null){
                    //将该类保存如redis中
                    redisManager.putBinaryJedisCache(DevContext.IDC_RE_PRODUCT,String.valueOf(reProductEntity.getId()),reProductEntity);
                    log.debug("保存其余几张子表信息");
                *//*视图一次性查出来处理*//*
                    Map<String,Object> idcReRackModelMap = idcReProddefService.getModelByCategory(ServiceApplyEnum.机架.value(),reProductEntity.getId());
                    if(idcReRackModelMap != null){
                        redisManager.putBinaryJedisCache(DevContext.IDC_RE_RACK_MODEL,ServiceApplyEnum.机架.value()+"||"+reProductEntity.getId(),idcReRackModelMap);
                    }

                    Map<String,Object> idcRePortModelMap = idcReProddefService.getModelByCategory(ServiceApplyEnum.端口带宽.value(),reProductEntity.getId());
                    if(idcRePortModelMap != null){
                        redisManager.putBinaryJedisCache(DevContext.IDC_RE_PORT_MODEL,ServiceApplyEnum.端口带宽.value()+"||"+reProductEntity.getId(),idcRePortModelMap);
                    }

                    Map<String,Object> idcReIpModelMap = idcReProddefService.getModelByCategory(ServiceApplyEnum.IP租用.value(),reProductEntity.getId());
                    if(idcReIpModelMap != null){
                        redisManager.putBinaryJedisCache(DevContext.IDC_RE_IP_MODEL,ServiceApplyEnum.IP租用.value()+"||"+reProductEntity.getId(),idcReIpModelMap);
                    }


                    Map<String,Object> idcReServerModelMap = idcReProddefService.getModelByCategory(ServiceApplyEnum.主机租赁.value(),reProductEntity.getId());
                    if(idcReServerModelMap != null){
                        redisManager.putBinaryJedisCache(DevContext.IDC_RE_SERVER_MODEL,ServiceApplyEnum.主机租赁.value()+"||"+reProductEntity.getId(),idcReServerModelMap);
                    }

                    Map<String,Object> idcReNewlyModelMap = idcReProddefService.getModelByCategory(ServiceApplyEnum.增值业务.value(),reProductEntity.getId());
                    if(idcReNewlyModelMap !=  null){
                        redisManager.putBinaryJedisCache(DevContext.IDC_RE_NEWLY_MODEL,ServiceApplyEnum.增值业务.value()+"||"+reProductEntity.getId(),idcReNewlyModelMap);
                    }

                    ServiceApplyImgStatus serviceApplyImgStatus = idcReProddefService.getSelfModelByProdInstId(reProductEntity.getId());
                    if(serviceApplyImgStatus != null){
                        redisManager.putBinaryJedisCache(DevContext.SERVICE_APPLY_CATEGORY,String.valueOf(serviceApplyImgStatus.getProdInstId()),serviceApplyImgStatus);
                    }
                }

            }
            log.debug("开始将[订单信息]都保存如REDIS...................end");
        }*/
    }
    public void assetAttachmentinfoIntoRedisWithBytes(List<AssetAttachmentinfo> assetAttachList) throws Exception {
        if(assetAttachList != null && !assetAttachList.isEmpty()){
            Iterator<AssetAttachmentinfo> it = assetAttachList.iterator();
            while(it.hasNext()){
                AssetAttachmentinfo assetAttachmentinfo = it.next();
                if(assetAttachmentinfo != null){
                    byte[] attachByte = assetAttachmentinfo.getAttachByte();
                    if(attachByte != null){
                        try {
                            log.debug("这里是只需要保存附件到redis中....................start");
                            //然后修改附件的数据
                            //查询该附件是哪个工单
                            List<Long> ticketInsIds = assetAttachmentinfoService.getTicketInsId(assetAttachmentinfo.getId());
                            if(ticketInsIds != null && !ticketInsIds.isEmpty()){
                                assetAttachmentinfo.setTicketInstId(ticketInsIds.get(0));
                            }
                            redisManager.putBinaryJedisCache(DevContext.ATTACHMENT,
                                    assetAttachmentinfo.getLogicTablename()+"||" +
                                            ""+assetAttachmentinfo.getLogicColumn()+"||" +
                                            ""+assetAttachmentinfo.getRelationalValue()+"||" +
                                            ""+ AssetAttachmentinfo.tableName +"||" +
                                            "ID||" +
                                            ""+assetAttachmentinfo.getId(),
                                    assetAttachmentinfo);

                            log.debug("这里是只需要保存附件到redis中....................end");
                        } catch (Exception e) {
                            e.printStackTrace();
                            log.error("居然出错了。请检查一下数据源是否有问题。。。。。。。。。。。。。");
                        }
                    }
                }
            }
        }
    }
}
