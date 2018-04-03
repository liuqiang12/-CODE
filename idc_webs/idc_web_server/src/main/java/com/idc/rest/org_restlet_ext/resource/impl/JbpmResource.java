package com.idc.rest.org_restlet_ext.resource.impl;

import com.idc.mapper.IdcHisProcCmentMapper;
import com.idc.mapper.IdcRunProcCmentMapper;
import com.idc.model.IdcHisProcCment;
import com.idc.model.IdcRunProcCment;
import com.idc.rest.org_restlet_ext.resource.IJbpmResource;
import com.idc.service.JbpmProcessService;
import com.idc.spring.JbpmProcessEvent;
import com.idc.utils.PreProcessDynamicProxy;
import com.idc.utils.TASKNodeURL;
import net.sf.json.JSONObject;
import org.restlet.resource.ServerResource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import utils.DevContext;
import system.data.supper.service.RedisManager;
import system.rest.ResultObject;
import utils.DevLog;
import utils.PropertyConfig;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 后期的接口都存放在此:
 * 后期优化  去掉ApplicationContextAware
 * Created by DELL on 2017/9/5.
 */

@Path("jbpm") // this is the root resource
@Produces("application/json;utf-8")
@Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
@Component
public class JbpmResource extends ServerResource implements IJbpmResource,JbpmProcessService,ApplicationContextAware {
    @Autowired
    private RedisManager redisManager;
    @PUT
    @Path("handlerRunTikcetProcess")
    public ResultObject handlerRunTikcetProcess(String handlerRunTikcetParam) throws Exception{
        System.out.println("[ActJBPMController.handlerRunTikcetProcess>IdcRunProcCmentServiceImpl.handlerRunTikcetProcess[RestUtil.exchange]调用.....................start");
        ResultObject resultObject = handlerJbpmProcess(handlerRunTikcetParam);
        System.out.println("[ActJBPMController.handlerRunTikcetProcess>IdcRunProcCmentServiceImpl.handlerRunTikcetProcess[RestUtil.exchange]调用.....................end");
        return resultObject;
    }

    @PUT
    @Path("commingExpireTicket")
    public ResultObject commingExpireTicket(String redisKey) throws Exception {
        System.out.println("[获取过期的工单信息]");
        ResultObject resultObject = new ResultObject();
        resultObject.setEncoding(Boolean.FALSE);
        //获取配置文件信息
        String COMMING_EXPIRE_TICKET = PropertyConfig.getInstance().getPropertyValue("COMMING_EXPIRE_TICKET");
        String default_EXPIRE_TICKET = DevContext.COMMING_7_DAY_EXPIRE_TICKET;
        if(COMMING_EXPIRE_TICKET != null){
            if("3DAY".equalsIgnoreCase(COMMING_EXPIRE_TICKET)){
                default_EXPIRE_TICKET = DevContext.COMMING_3_DAY_EXPIRE_TICKET;
            }else if("7DAY".equalsIgnoreCase(COMMING_EXPIRE_TICKET)){
                default_EXPIRE_TICKET = DevContext.COMMING_7_DAY_EXPIRE_TICKET;
            }else if("14DAY".equalsIgnoreCase(COMMING_EXPIRE_TICKET)){
                default_EXPIRE_TICKET = DevContext.COMMING_14_DAY_EXPIRE_TICKET;
            }else if("1MONTH".equalsIgnoreCase(COMMING_EXPIRE_TICKET)){
                default_EXPIRE_TICKET = DevContext.COMMING_1_MONTH_EXPIRE_TICKET;
            }else if("2MONTH".equalsIgnoreCase(COMMING_EXPIRE_TICKET)){
                default_EXPIRE_TICKET = DevContext.COMMING_2_MONTH_EXPIRE_TICKET;
            }else if("3MONTH".equalsIgnoreCase(COMMING_EXPIRE_TICKET)){
                default_EXPIRE_TICKET = DevContext.COMMING_3_MONTH_EXPIRE_TICKET;
            }
        }
        List<String> list = redisManager.getListJedisCache(default_EXPIRE_TICKET);
        resultObject.setStatus(Boolean.TRUE);
        resultObject.setResultObj(list);//请求获取到的过期工单
        return resultObject;
    }


    @Override
    public ResultObject handlerJbpmProcess(String handlerRunTikcetParam) throws Exception {
        ResultObject resultObject = new ResultObject();
        resultObject.setEncoding(Boolean.FALSE);
        //1:转换实体类[idcRunProcCment]
        /*先用代理方式，目的是打印日志*/
        System.out.println("调用代理PreProcessDynamicProxy.....................start");
        JbpmProcessService proxy = (JbpmProcessService)new PreProcessDynamicProxy().bind(this);
        //1.1
        IdcRunProcCment idcRunProcCment  = proxy.paramstoBean(handlerRunTikcetParam);
        //1.2:调用方法[handRunCommnetToMergeInto]
        proxy.handRunCommnetToMergeInto(idcRunProcCment);
        //1.3:调用方法[handTicketLinkedInfo]
        proxy.handTicketLinkedInfo(idcRunProcCment);
        resultObject.setStatus(Boolean.TRUE);
        System.out.println("调用代理PreProcessDynamicProxy.....................end");
        return resultObject;
    }

    @Override
    public IdcRunProcCment paramstoBean(String handlerRunTikcetParam) throws Exception {
        IdcRunProcCment idcRunProcCment = (IdcRunProcCment) JSONObject.toBean(JSONObject.fromObject(handlerRunTikcetParam),IdcRunProcCment.class);
        return idcRunProcCment;
    }
    /**
     * 处理工单中间表的方法
     */
    @Override
    public void handTicketLinkedInfo(IdcRunProcCment idcRunProcCment) throws Exception{
        /*这里需要动态代理相应处理*/
        System.out.println("该方法处在的class["+this.getClass()+"],发起[com.idc.spring.JbpmProcessEventListener]处理。。。。。start");
        JbpmProcessEvent jbpmProcessEvent = new JbpmProcessEvent(applicationContext,idcRunProcCment,null);
        applicationContext.publishEvent(jbpmProcessEvent);
        System.out.println("该方法处在的class["+this.getClass()+"],发起[com.idc.spring.JbpmProcessEventListener]处理。。。。。end");
    }
    @Override
    public void handRunCommnetToMergeInto(IdcRunProcCment idcRunProcCment) throws  Exception{
        /*任务审批意见 start*/
        idcRunProcCment.setActName(TASKNodeURL.taskNodeWithFormKey(idcRunProcCment.getFormKey()));

        idcRunProcCmentMapper.mergeInto(idcRunProcCment);
        IdcHisProcCment idcHisProcCment = new IdcHisProcCment();
        Collection<String> excludes = new ArrayList<String>();
        excludes.add("id");
        BeanUtils.copyProperties(idcRunProcCment,idcHisProcCment,excludes.toArray(new String[excludes.size()]));
        idcHisProcCmentMapper.mergeInto(idcHisProcCment);
    }


    private static ApplicationContext applicationContext;
    @Autowired
    private IdcHisProcCmentMapper idcHisProcCmentMapper;
    @Autowired
    private IdcRunProcCmentMapper idcRunProcCmentMapper;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        JbpmResource.applicationContext = applicationContext;
    }
}
