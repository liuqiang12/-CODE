package com.idc.spring;

import com.idc.mapper.*;
import com.idc.model.*;
import com.idc.service.JbmpProcessEventService;
import com.idc.utils.JBPMModelKEY;
import com.idc.utils.JbpmProcessDynamicProxy;
import com.idc.utils.TASKNodeURL;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by DELL on 2017/8/24.
 * 监听资源信息表:控制相应的数据
 * 1:此处需要调用的是生成的接口的webservice接口信息
 *
 */
@Component("jbpmProcessEventListener")
@Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class JbpmProcessEventListener implements ApplicationListener,JbmpProcessEventService {
    @Override
    public void onApplicationEvent(ApplicationEvent event){
        if(event instanceof JbpmProcessEvent){
            JbpmProcessEvent jbpmProcessEvent = (JbpmProcessEvent)event;
            if(jbpmProcessEvent.target instanceof IdcRunProcCment) {
                /*这里进行处理流程过程中的审批信息*/
                System.out.println("[被JbpmResource的handlerJbpmProcess方法中handTicketLinkedInfo调用.....................start");
                IdcRunProcCment idcRunProcCment = (IdcRunProcCment)jbpmProcessEvent.target;
                try {
                    handerTicketProcMain(idcRunProcCment);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("[被JbpmResource的handlerJbpmProcess方法中handTicketLinkedInfo调用.....................end");
            }
        }
    }
    @Override
    public void handerTicketProcMain(IdcRunProcCment idcRunProcCment) throws Exception{
        /*动态代理一下,目的是打印相应的日志*/
        System.out.println("调用代理JbpmProcessDynamicProxy.....................start");
        JbmpProcessEventService proxy = (JbmpProcessEventService)new JbpmProcessDynamicProxy().bind(this);
        TASKNodeURL taskNodeURL = TASKNodeURL.currentBeanWithFormKey(idcRunProcCment.getFormKey());
        if(taskNodeURL.isUpdateHandler() == true){
            String processKey = taskNodeURL.processKey();
            if(JBPMModelKEY.预占预勘察流程.toString().equals(processKey) || JBPMModelKEY.自有业务_预占预勘察流程.toString().equals(processKey)){
                proxy.handerTicketPreAccept(idcRunProcCment,taskNodeURL);
            }else if(JBPMModelKEY.停机流程.toString().equals(processKey) || JBPMModelKEY.自有业务_停机流程.toString().equals(processKey)){
                proxy.handerTicketPause(idcRunProcCment,taskNodeURL);
            }else if(JBPMModelKEY.复通流程.toString().equals(processKey) || JBPMModelKEY.自有业务_复通流程.toString().equals(processKey)) {
                proxy.handerTicketRecover(idcRunProcCment, taskNodeURL);
            }else if(JBPMModelKEY.开通变更流程.toString().equals(processKey)||JBPMModelKEY.自有业务_开通变更流程.toString().equals(processKey) || JBPMModelKEY.业务变更流程.toString().equals(processKey)||JBPMModelKEY.自有业务_业务变更流程.toString().equals(processKey)){
                proxy.handerTicketChange(idcRunProcCment,taskNodeURL);
            }else if(JBPMModelKEY.下线流程.toString().equals(processKey) || JBPMModelKEY.自有业务_下线流程.toString().equals(processKey)){
                proxy.handerTicketHalt(idcRunProcCment,taskNodeURL);
            }
        }
        System.out.println("调用代理JbpmProcessDynamicProxy.....................end");
    }
    @Override
    public void handerTicketPreAccept(IdcRunProcCment idcRunProcCment,TASKNodeURL taskNodeURL) throws Exception {
        IdcRunTicketPreAccept idcRunTicketPreAccept = new IdcRunTicketPreAccept();
        idcRunTicketPreAccept.setTicketInstId(idcRunProcCment.getTicketInstId());
        if("pre_accept_apply_form".equals(taskNodeURL.formkey()) || "self_pre_accept_apply_form".equals(taskNodeURL.formkey()) ){
                                /*审批意见就是申请描述*/
            idcRunTicketPreAccept.setApplydesc(idcRunProcCment.getComment());
            idcRunTicketPreAccept.setReservestart(idcRunProcCment.getReservestart());
            idcRunTicketPreAccept.setReserveend(idcRunProcCment.getReserveend());
        }else if("occupy_relonnissanle_form".equals(taskNodeURL.formkey()) || "self_occupy_relonnissanle_form".equals(taskNodeURL.formkey())){
                                /*审批意见就是施工描述*/
            idcRunTicketPreAccept.setConstructComment(idcRunProcCment.getComment());
        }
        IdcHisTicketPreAccept idchisTicketPreAccept = new IdcHisTicketPreAccept();
        BeanUtils.copyProperties(idcRunTicketPreAccept,idchisTicketPreAccept);

        idcRunTicketPreAcceptMapper.updateByData(idcRunTicketPreAccept);
        idcHisTicketPreAcceptMapper.updateByData(idchisTicketPreAccept);
    }

    @Override
    public void handerTicketPause(IdcRunProcCment idcRunProcCment,TASKNodeURL taskNodeURL) throws Exception {
        IdcRunTicketPause idcRunTicketPause=new IdcRunTicketPause();
        idcRunTicketPause.setTicketInstId(idcRunProcCment.getTicketInstId());
        if("pause_accept_apply_form".equals(taskNodeURL.formkey()) || "self_pause_accept_apply_form".equals(taskNodeURL.formkey())){
            /*审批意见就是申请描述*/
            //添加开始停机时间
            idcRunTicketPause.setReservestart(idcRunProcCment.getReservestart());
            //添加结束停机时间
            idcRunTicketPause.setReserveend(idcRunProcCment.getReserveend());
            //添加停机类型是临时停机还是长期停机
            idcRunTicketPause.setPausetype(idcRunProcCment.getPauseTpye());
            //添加备注信息  idcRunTicketPause表的Applydesc信息和idcRunProcCment的Comment信息对应
            idcRunTicketPause.setApplydesc(idcRunProcCment.getComment());
        }else if("pause_shelveuping_form".equals(taskNodeURL.formkey()) || "self_pause_shelveuping_form".equals(taskNodeURL.formkey())){
                                /*审批意见就是施工描述*/
            idcRunTicketPause.setConstructComment(idcRunProcCment.getComment());
        }
        IdcHisTicketPause idcHisTicketPause=new IdcHisTicketPause();
        //把停机运行表的数据复制到停机历史表中
        BeanUtils.copyProperties(idcRunTicketPause,idcHisTicketPause);
        //开始持久化到数据库中
        idcRunTicketPauseMapper.updateByData(idcRunTicketPause);
        idcHisTicketPauseMapper.updateByData(idcHisTicketPause);
    }

    @Override
    public void handerTicketRecover(IdcRunProcCment idcRunProcCment,TASKNodeURL taskNodeURL) throws Exception {
/*自行处理*/
        IdcRunTicketRecover idcRunTicketRecover=new IdcRunTicketRecover();
        idcRunTicketRecover.setTicketInstId(idcRunProcCment.getTicketInstId());
        if("recover_accept_apply_form".equals(taskNodeURL.formkey()) || "self_recover_accept_apply_form".equals(taskNodeURL.formkey())){
            //添加开始复通时间 recovertimeStr
            idcRunTicketRecover.setRecovertime(idcRunProcCment.getReservestart());
            //添加备注信息
            idcRunTicketRecover.setApplydesc(idcRunProcCment.getComment());
        }else if("recover_shelveuping_form".equals(taskNodeURL.formkey()) || "self_recover_shelveuping_form".equals(taskNodeURL.formkey())){
                                /*审批意见就是施工描述*/
            idcRunTicketRecover.setConstructComment(idcRunProcCment.getComment());
        }
        IdcHisTicketRecover idcHisTicketRecover=new IdcHisTicketRecover();
        //把停机运行表的数据复制到停机历史表中
        BeanUtils.copyProperties(idcRunTicketRecover,idcHisTicketRecover);
        //开始持久化到数据库中
        idcRunTicketRecoverMapper.updateByData(idcRunTicketRecover);
        idcHisTicketRecoverMapper.updateByData(idcHisTicketRecover);
    }

    @Override
    public void handerTicketChange(IdcRunProcCment idcRunProcCment,TASKNodeURL taskNodeURL) throws Exception {
        /*自行处理*/
        /*** 业务变更情况 ****/
        IdcRunTicketChange idcRunTicketChange = new IdcRunTicketChange();
        idcRunTicketChange.setTicketInstId(idcRunProcCment.getTicketInstId());

        if("open_change_accept_apply_form".equals(taskNodeURL.formkey()) || "business_change_accept_apply_form".equals(taskNodeURL.formkey()) || "self_business_change_accept_apply_form".equals(taskNodeURL.formkey()) ){
                        /*审批意见就是申请描述*/
            idcRunTicketChange.setApplydesc(idcRunProcCment.getComment());
        }else if("open_change_shelveuping_form".equals(taskNodeURL.formkey()) || "business_change_shelveuping_form".equals(taskNodeURL.formkey()) || "self_business_change_shelveuping_form".equals(taskNodeURL.formkey() )){
                        /*审批意见就是施工描述*/
            idcRunTicketChange.setOccupycomment(idcRunProcCment.getComment());
        }
        if((idcRunTicketChange.getOccupycomment() != null && !"".equals(idcRunTicketChange.getOccupycomment())) || (idcRunTicketChange.getApplydesc() != null && !"".equals(idcRunTicketChange.getApplydesc())) ){
            IdcHisTicketChange idcHisTicketChange = new IdcHisTicketChange();
            BeanUtils.copyProperties(idcRunTicketChange,idcHisTicketChange);
            idcRunTicketChangeMapper.updateByData(idcRunTicketChange);
            idcHisTicketChangeMapper.updateByData(idcHisTicketChange);
        }
    }

    @Override
    public void handerTicketHalt(IdcRunProcCment idcRunProcCment,TASKNodeURL taskNodeURL) throws Exception {
        IdcRunTicketHalt idcRunTicketHalt=new IdcRunTicketHalt();
        idcRunTicketHalt.setTicketInstId(idcRunProcCment.getTicketInstId());
        if("halt_accept_apply_form".equals(taskNodeURL.formkey()) || "self_halt_accept_apply_form".equals(taskNodeURL.formkey())){
            //添加备注信息对应idcRunProcCment的applydesc字段
            idcRunTicketHalt.setApplydesc(idcRunProcCment.getApplydesc());
            //添加退订原因haltreason对应idcRunProcCment的comment字段
            idcRunTicketHalt.setHaltreason(idcRunProcCment.getComment());
        }else if("halt_shelveuping_form".equals(taskNodeURL.formkey()) || "self_halt_shelveuping_form".equals(taskNodeURL.formkey())){
            //最
            idcRunTicketHalt.setConstructComment(idcRunProcCment.getComment());
        }
        IdcHisTicketHalt idcHisTicketHalt=new IdcHisTicketHalt();
        //把复通运行表的数据复制到停机历史表中
        BeanUtils.copyProperties(idcRunTicketHalt,idcHisTicketHalt);
        //开始持久化到数据库中
        idcRunTicketHaltMapper.updateByData(idcRunTicketHalt);
        idcHisTicketHaltMapper.updateByData(idcHisTicketHalt);
    }

    @Autowired
    private IdcRunTicketPreAcceptMapper idcRunTicketPreAcceptMapper;//预勘察信息
    @Autowired
    private IdcHisTicketPreAcceptMapper idcHisTicketPreAcceptMapper;//预勘察信息
    @Autowired
    private IdcRunTicketPauseMapper idcRunTicketPauseMapper;     //停机运行表
    @Autowired
    private IdcHisTicketPauseMapper idcHisTicketPauseMapper;    //停机历史表
    @Autowired
    private IdcRunTicketRecoverMapper idcRunTicketRecoverMapper;//复通运行表
    @Autowired
    private IdcHisTicketRecoverMapper idcHisTicketRecoverMapper;//复通历史表
    /*业务变更*/
    @Autowired
    private IdcRunTicketChangeMapper idcRunTicketChangeMapper;
    @Autowired
    private IdcHisTicketChangeMapper idcHisTicketChangeMapper;

    @Autowired
    private IdcRunTicketHaltMapper  idcRunTicketHaltMapper;    //下线运行表
    @Autowired
    private IdcHisTicketHaltMapper  idcHisTicketHaltMapper;     //下线历史表

}
