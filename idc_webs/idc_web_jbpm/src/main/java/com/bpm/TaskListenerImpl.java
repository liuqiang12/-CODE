package com.bpm;

import com.idc.model.SysUserinfo;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import system.authority.security.SessionUserDetailsUtil;

import java.util.Map;

/**
 * Created by DELL on 2017/7/24.
 * 设置任务的班里人信息【开始】
 */

public class TaskListenerImpl  implements TaskListener {
    protected final Logger logger = LoggerFactory.getLogger(TaskListenerImpl.class);
    private org.activiti.engine.impl.el.FixedValue orgLevel ;
    /**设置任务的办理人（个人任务和组任务）*/
    @Override
    public void notify(DelegateTask delegateTask) {
        Map<String,Object> map = delegateTask.getVariables();
        String taskId = delegateTask.getId();

        logger.debug("in taskUserQuer class variable is:"+map.toString());
        logger.debug("in taskUserQuer class taskid is:"+taskId);

        String defined_user_name = delegateTask.getAssignee();
        //指定个人任务：托管为当前登录人
        SysUserinfo userDetails = (SysUserinfo)SessionUserDetailsUtil.getLoginInfo();
        String userAssignee = String.valueOf(userDetails.getId());
        logger.debug("当前的分配人="+defined_user_name+",name:"+delegateTask.getName()+",TaskDefinitionKey="+delegateTask.getTaskDefinitionKey()+",FormKey="+delegateTask.getFormKey()+",Variables="+delegateTask.getVariables());
        //根据流程变量的内容设置下一个节点的审批人
        delegateTask.setAssignee(userAssignee);
    }

    /*@Override
    public void notify(DelegateExecution execution) throws Exception {
        //指定个人任务：托管为当前登录人
        SysUserinfo userDetails = (SysUserinfo)SessionUserDetailsUtil.getLoginInfo();
        String userAssignee = "user_key_"+userDetails.getUsername()+"@id_"+userDetails.getId();
        String eventName = execution.getEventName();
        *//*delegateTask.setAssignee(userAssignee);*//*

        //start
        if ("start".equals(eventName)) {
            System.out.println("start=========");
        }else if ("end".equals(eventName)) {
            System.out.println("end=========");
        }
        else if ("take".equals(eventName)) {
            System.out.println("take=========");
        }
    }*/
}
