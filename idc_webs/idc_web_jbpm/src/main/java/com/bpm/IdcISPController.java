package com.bpm;

import com.idc.model.*;
import com.idc.service.IdcIspInfoService;
import com.idc.service.RemoteIspService;
import modules.utils.ResponseJSON;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import utils.DevContext;
import utils.strategy.code.utils.CommonPageParser;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2017/8/17.
 *  根据powerDesigner中的表字段对应com.core.repository.model包中的实体类。
 *  注意：1如果表中有属性而实体类没有，则在实体类中添加该属性同时增加注解:@XmlTransient
 *       2:界面结构就是单标的增加功能和删除功能。不需要其他功能。
 *
 */
@Controller
@RequestMapping("/idcISPController")
public class IdcISPController {
    private static final Log log = LogFactory.getLog(IdcISPController.class);

    @Autowired
    private IdcIspInfoService idcIspInfoService;
    @Autowired
    private RemoteIspService remoteIspService;

    /**
     * 【客户信息】
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/ispCustomerUpload.do")
    @ResponseBody
    public ResponseJSON ispCustomerUpload(HttpServletRequest request, Model model) {
        ResponseJSON result = new ResponseJSON();
        try {

            remoteIspService.loadIspCustomerList();
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    @RequestMapping("/ispCustomerDelete.do")
    @ResponseBody
    public ResponseJSON delUserDataXml(){
        /*删除相应的数据*/
        ResponseJSON result = new ResponseJSON();
        try {
            remoteIspService.delUserDataXml();
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;

    }
    /* 资源情况 */
    @RequestMapping("/ispResourceUpload.do")
    @ResponseBody
    public ResponseJSON ispResourceUpload(HttpServletRequest request, Model model) {
        ResponseJSON result = new ResponseJSON();
        try {

            remoteIspService.loadIspResourceUpload();
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /* 资源情况 */
    @RequestMapping("/recoverResourceUpload.do")
    @ResponseBody
    public ResponseJSON recoverResourceUpload(HttpServletRequest request, Model model) {
        ResponseJSON result = new ResponseJSON();
        try {

            remoteIspService.loadIspResourceUpload();
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    /* 资源情况 */
    @RequestMapping("/ispResourceDelete.do")
    @ResponseBody
    public ResponseJSON ispResourceDelete(HttpServletRequest request, Model model) {
        ResponseJSON result = new ResponseJSON();
        try {

            remoteIspService.loadIspResourceDelete();
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
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

    @RequestMapping("/ispRackUpload.do")
    @ResponseBody
    public ResponseJSON ispRackUpload(HttpServletRequest request, Model model) {
        ResponseJSON result = new ResponseJSON();
        try {
            //执行远程生成所有的文件信息
            //首先删除。然后上报
            VelocityContext context = new VelocityContext();
            delHouseForGatewayDataXml(context,result);//删除机房链路信息
            addHouseDataXml(context,result);//新增机房信息
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    @RequestMapping("/ispRackDelete.do")
    @ResponseBody
    public ResponseJSON ispRackDelete(HttpServletRequest request, Model model) {
        ResponseJSON result = new ResponseJSON();
        try {
            VelocityContext context = new VelocityContext();
            delHouseForGatewayDataXml(context,result);//删除机房链路信息
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public void addHouseDataXml(VelocityContext context,ResponseJSON result){
        initAddHouseDataXML(context);
        List<FrameInfo> frameInfoList = idcIspInfoService.loadFrameInfoList(0);
        if(frameInfoList != null && !frameInfoList.isEmpty()){
            context.put("frameInfoList",frameInfoList);
            CommonPageParser.WriterPage(context,
                    "AddHouseTemplate.xml",
                    DevContext.LOCAL_ISP_TEMPFIELPATH,
                    getPreFileStr()+"[AddHouseTemplate].xml");
            result.setMsg("成功创建 【新增机架资源文件】  ");
        }else{
            result.setMsg("empty");
        }
    }
    public void initAddHouseDataXML(VelocityContext context){
        getHouseGateWayInfo(context);//链路信息
        getHouseIpSegInfo(context);//ip信息
        //getHouseFrameInfo(context);//机架信息
        //getReCustomerData(context);//客户信息
        getIspSolidDataInfo(context);//固化信息
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
    public void getHouseFrameInfo(VelocityContext context){
        //通过【idc_rack】表中字段IS_SEND_ISP标志是否上报：1表示已经是上报了

    }

    public void delHouseForGatewayDataXml(VelocityContext context,ResponseJSON result){
        getHouseGateWayInfo(context);//链路信息
        getIspSolidDataInfo(context);//固化信息
        CommonPageParser.WriterPage(context,
                "DelHouseForGatewayTemplate.xml",
                DevContext.LOCAL_ISP_TEMPFIELPATH,
                getPreFileStr()+"[DelHouseForGatewayTemplate].xml");
        result.setMsg("成功创建 【删除机房链路文件】  ");
    }
    public void delHouseForIpDataXml(VelocityContext context,Long ticketInstId,Integer status,ResponseJSON result){
        List<IpAttr> ipAttrList = idcIspInfoService.loadIpAttrList(ticketInstId,status);
        if(ipAttrList != null && !ipAttrList.isEmpty()){
            context.put("ipAttrList",ipAttrList);
            getIspSolidDataInfo(context);//固化信息

            CommonPageParser.WriterPage(context,
                    "DelHouseForIpTemplate.xml",
                    DevContext.LOCAL_ISP_TEMPFIELPATH,
                    getPreFileStr()+"[DelHouseForIpTemplate].xml");
            result.setMsg("生成文件成功");
        }else{
            result.setMsg("empty");
        }
    }



    public void updateHouseForBusDataXml(VelocityContext context,Long ticketInstId,ResponseJSON result){
        log.debug("更新机房相关数据..............");
        updateHouseBusDataXmlParams(context,ticketInstId);
        getIspSolidDataInfo(context);
        List<FrameInfo> frameInfoList = idcIspInfoService.loadFrameInfoListByTicketInstId(0,ticketInstId,999);
        context.put("frameInfoList",frameInfoList);

        if( frameInfoList != null && !frameInfoList.isEmpty()){
            CommonPageParser.WriterPage(
                    context,
                    "UpdateHouseForBusTemplate.xml",
                    DevContext.LOCAL_ISP_TEMPFIELPATH,
                    getPreFileStr()+"[UpdateHouseForBusTemplate].xml"
            );
            result.setMsg("上报机架成功");
        }else{
            result.setMsg("没有机架数据,请查看数据...");
        }

    }
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
    public void updateHouseBusDataXmlParams(VelocityContext context,Long ticketInstId){
        //第一获取链路
        getHouseGateWayInfo(context);
        //获取ip地址
        getHouseIpSegInfo(context);
        //通过工单ID：获取所有的机架信息

    }
    //获取机房的链路信息
    public void getHouseGateWayInfo(VelocityContext context){
        List<GatewayInfo> gatewayInfoList = idcIspInfoService.loadGatewayInfoList();
        context.put("gatewayInfoList",gatewayInfoList);
    }
    //获取机房的ip地址段
    public void getHouseIpSegInfo(VelocityContext context){
        List<IpSegInfo> ipSegInfoList = idcIspInfoService.loadIpSegInfoForIpList(null);
        context.put("ipSegInfoList",ipSegInfoList);
    }
    public String getPreFileStr(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentDateStr = sdf.format(new Date());
        return currentDateStr;
    }
}
