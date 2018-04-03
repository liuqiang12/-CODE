package com.idc.rest.org_restlet_ext.resource.impl;

import com.idc.mapper.IdcHisTicketMapper;
import com.idc.model.*;
import com.idc.rest.org_restlet_ext.resource.IIspRemoteCall;
import com.idc.service.IdcIspInfoService;
import com.idc.service.RemoteIspService;
import com.idc.utils.CategoryEnum;
import com.idc.utils.JbpmStatusEnum;
import modules.utils.ResponseJSON;
import org.apache.velocity.VelocityContext;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import utils.DevContext;
import utils.strategy.code.utils.CommonPageParser;
import utils.typeHelper.GsonUtil;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Path("isp") // this is the root resource
@Produces("application/json;utf-8")
@Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
@Component
public class IspRemoteCall extends ServerResource implements IIspRemoteCall {
    private Logger logger = LoggerFactory.getLogger(IspRemoteCall.class);
    @Autowired
    private RemoteIspService remoteIspService;
    /* 资源情况 */
    @POST
    @Path("ispResourceUpload.do")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseJSON ispResourceUpload(String jsonParams) {
        logger.debug("远程调用接口方法：传递的参数是【"+jsonParams+"】:支持新增和修改操作");

        ResponseJSON result = new ResponseJSON();
        try {

            /* 新增资源信息 */
            remoteIspService.loadIspResourceUpload();//新增资源信息
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        try {
            /* 首先先删除用户 */
            remoteIspService.delUserDataXml();
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }

        try {
            remoteIspService.loadIspCustomerList();//再新增用户
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @POST
    @Path("recoverRsourceUpload.do")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseJSON recoverRsourceUpload(String jsonParams) {
        logger.debug("远程调用接口方法：传递的参数是【"+jsonParams+"】:支持新增和修改操作");
        ResponseJSON result = new ResponseJSON();
        try {
            /* update资源信息 */
            Map<String,String> paramMap = GsonUtil.json2Object(jsonParams, Map.class);
            String ticketInstIdStr = String.valueOf(paramMap.get("ticketInstId"));
            String ticketStatusStr = String.valueOf(paramMap.get("ticketStatus"));
            BigDecimal ticketInstIdBd = new BigDecimal(ticketInstIdStr);
            BigDecimal ticketStatusBd = new BigDecimal(ticketStatusStr);
            Long ticketInstId = ticketInstIdBd.longValue();
            Integer ticketStatus = ticketStatusBd.intValue();
            if( ticketStatus == JbpmStatusEnum.流程驳回或不同意.value() ){
                remoteIspService.callProcResourceBh(ticketInstId,ticketStatus);
            }else{
                remoteIspService.callProc_resource_hs(ticketInstId,ticketStatus);
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    /* 删除  */
    @POST
    @Path("delHouseForIpDataXml")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseJSON delHouseForIpDataXml(String jsonParams) throws Exception{
        ResponseJSON responseJSON = new ResponseJSON();
        logger.debug("远程调用接口方法：传递的参数是【"+jsonParams+"】");
        Map<String,String> paramMap = GsonUtil.json2Object(jsonParams, Map.class);
        String ticketInstIdStr = String.valueOf(paramMap.get("ticketInstId"));
        String ticketStatusStr = String.valueOf(paramMap.get("ticketStatus"));
        BigDecimal ticketInstIdBd = new BigDecimal(ticketInstIdStr);
        BigDecimal ticketStatusBd = new BigDecimal(ticketStatusStr);
        Long ticketInstId = ticketInstIdBd.longValue();
        Integer ticketStatus = ticketStatusBd.intValue();
        IdcHisTicket idcHisTicket = idcHisTicketMapper.getIdcHisTicketByIdTicketInstId(ticketInstId);
        VelocityContext context = new VelocityContext();
        String ticketCategory = idcHisTicket.getTicketCategory();
        if ( !ticketCategory.equals(CategoryEnum.临时测试流程.value()) && !ticketCategory.equals(CategoryEnum.预堪预占流程.value()) ){
            if(ticketStatus == JbpmStatusEnum.流程作废.value() || ticketStatus == 1){
                delHouseForIpDataXmlBody(context,ticketInstId,999);//999代表是所有的资源都删除..
            }else if(ticketStatus == JbpmStatusEnum.删除到回收站.value()){
                recoveryResource(context,ticketCategory,idcHisTicket);
            }else if(ticketStatus == JbpmStatusEnum.流程结束.value()){
                deleteTicketResource(context,ticketCategory,idcHisTicket);
                //修改机房信息
                updateHouseWithEndTicketResource(context,ticketCategory,idcHisTicket);
            }
            responseJSON.setResult("xxxxxxxxxxxxxxxxxxxxxxxxxx");
        }else{
            responseJSON.setResult("[empty]");
        }
        return responseJSON;
    }
    public void recoveryResource(VelocityContext context,String ticketCategory,IdcHisTicket idcHisTicket){
        logger.debug("ISP调用删除资源start.........................");
        if(ticketCategory.equals(CategoryEnum.开通变更流程.value())){
            logger.debug("【变更开通】=【通过父工单id查询业务变更中的新增的ip资源进行删除】");
            delHouseForIpDataXmlBody(context,idcHisTicket.getParentId(),1);
        }else if(ticketCategory.equals(CategoryEnum.开通流程.value())){
            logger.debug("【开通流程】=【通过工单ID查询业务中的ip资源进行删除】");
            delHouseForIpDataXmlBody(context,idcHisTicket.getId(),999);//999代表是所有的资源都删除..
        }
        logger.debug("ISP调用删除资源 end.........................");
    }

    /**
     * 工单结束情况处理
     * 1:开通工单结束 修改机房资源数据
     * 2：开通变更结束 新增的资源进行修改。
     * @param context
     * @param ticketCategory
     * @param idcHisTicket
     */
    public void updateHouseWithEndTicketResource(VelocityContext context,String ticketCategory,IdcHisTicket idcHisTicket){
        if(ticketCategory.equals(CategoryEnum.开通流程.value()) ){
            updateHouseForBusDataXml(context,idcHisTicket.getId(),999);
        }else if(ticketCategory.equals(CategoryEnum.开通变更流程.value())){
            /* 新增的资源数据 */
            updateHouseForBusDataXml(context,idcHisTicket.getId(),1);
        }
    }
    public void updateHouseForBusDataXml(VelocityContext context,Long ticketInstId,Integer status){
        logger.debug("更新机房相关数据..............");
        updateHouseBusDataXmlParams(context,ticketInstId,status);
        getIspSolidDataInfo(context);
        CommonPageParser.WriterPage(context,"UpdateHouseForBusTemplate.xml", DevContext.LOCAL_ISP_TEMPFIELPATH,"2017-11-17UpdateHouseForBusTemplate.xml");
    }
    public void updateHouseBusDataXmlParams(VelocityContext context,Long ticketInstId,Integer status){
        //第一获取链路
        getHouseGateWayInfo(context);
        //获取ip地址
        getHouseIpSegInfo(context);
        //通过工单ID：获取所有的机架信息
        List<FrameInfo> frameInfoList = idcIspInfoService.loadFrameInfoListByTicketInstId(0,ticketInstId,status);
        context.put("frameInfoList",frameInfoList);
    }
    //获取机房的ip地址段
    public void getHouseIpSegInfo(VelocityContext context){
        List<IpSegInfo> ipSegInfoList = idcIspInfoService.loadIpSegInfoForIpList(null);
        context.put("ipSegInfoList",ipSegInfoList);
    }
    //获取机房的链路信息
    public void getHouseGateWayInfo(VelocityContext context){
        List<GatewayInfo> gatewayInfoList = idcIspInfoService.loadGatewayInfoList();
        context.put("gatewayInfoList",gatewayInfoList);
    }
    public void deleteTicketResource(VelocityContext context,String ticketCategory,IdcHisTicket idcHisTicket){
        logger.debug("ISP调用删除资源start........" +
                "1：下线流程完成，删除ip资源信息" +
                "2：变更开通流程，通过父工单获取业务变更中的所有删除的资源，进行删除IP" +
                ".................");
        if(ticketCategory.equals(CategoryEnum.下线流程.value())){
            delHouseForIpDataXmlBody(context,idcHisTicket.getId(),999);//999代表是所有的资源都删除..
        }else if(ticketCategory.equals(CategoryEnum.开通变更流程.value())){
            logger.debug("【变更开通】=【通过父工单id查询业务变更中的新增的ip资源进行删除】");
            delHouseForIpDataXmlBody(context,idcHisTicket.getParentId(),-1);
        }
        logger.debug("ISP调用删除资源start.........................");
    }
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentDateStr = sdf.format(new Date());
        String preFileStr = currentDateStr.replace(":","_").replace(" ","-").replace("-","_");
        System.out.println(currentDateStr);
    }
    public String getPreFileStr(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentDateStr = sdf.format(new Date());
        return currentDateStr;
    }
    public void delHouseForIpDataXmlBody(VelocityContext context, Long ticketInstId,Integer status){
        delHouseForIpDataXMLParam(context,ticketInstId,status);
        getIspSolidDataInfo(context);//固化信息
        if ( context.get("ipAttrList") != null ){
            CommonPageParser.WriterPage(context,"DelHouseForIpTemplate.xml", DevContext.LOCAL_ISP_TEMPFIELPATH,
                    getPreFileStr()+"[DelHouseForIpTemplate].xml");
        }else{
            logger.debug("没有ip资源[   DelHouseForIpTemplate.xml     ]");
        }
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
    @Autowired
    private IdcHisTicketMapper idcHisTicketMapper;
    @Autowired
    private IdcIspInfoService idcIspInfoService;
}
