package com.bpm;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2017/7/3.
 */
public interface ProcessCoreService {
    /**
     * 驳回流程
     *
     * @param taskId     当前任务ID
     * @param activityId 驳回节点ID
     * @param variables  流程存储参数
     * @throws Exception
     */
    void backProcess(String taskId, String activityId, Map<String, Object> variables) throws Exception;


    /**
     * 取回流程
     *
     * @param taskId     当前任务ID
     * @param activityId 取回节点ID
     * @throws Exception
     */
    void callBackProcess(String taskId, String activityId)
            throws Exception;


    /**
     * 清空指定活动节点流向
     *
     * @param activityImpl 活动节点
     * @return 节点流向集合
     */
    List<PvmTransition> clearTransition(ActivityImpl activityImpl);


    /**
     * @param taskId     当前任务ID
     * @param variables  流程变量
     * @param activityId 流程转向执行任务节点ID<br>
     *                   此参数为空，默认为提交操作
     * @throws Exception
     */
    void commitProcess(String taskId, Map<String, Object> variables,
                       String activityId) throws Exception;


    /**
     * 中止流程(特权人直接审批通过等)
     *
     * @param taskId
     */
    void endProcess(String taskId) throws Exception;


    /**
     * 根据流入任务集合，查询最近一次的流入任务节点
     *
     * @param processInstance 流程实例
     * @param tempList        流入任务集合
     * @return
     */
    ActivityImpl filterNewestActivity(ProcessInstance processInstance,
                                      List<ActivityImpl> tempList);


    /**
     * 根据任务ID和节点ID获取活动节点 <br>
     *
     * @param taskId     任务ID
     * @param activityId 活动节点ID <br>
     *                   如果为null或""，则默认查询当前活动节点 <br>
     *                   如果为"end"，则查询结束节点 <br>
     * @return
     * @throws Exception
     */
    ActivityImpl findActivitiImpl(String taskId, String activityId)
            throws Exception;


    /**
     * 根据当前任务ID，查询可以驳回的任务节点
     *
     * @param taskId 当前任务ID
     */
    List<ActivityImpl> findBackAvtivity(String taskId) throws Exception;

    /**
     * 查询指定任务节点的最新记录
     *
     * @param processInstance 流程实例
     * @param activityId
     * @return
     */
    HistoricActivityInstance findHistoricUserTask(
            ProcessInstance processInstance, String activityId);

    /**
     * 根据当前节点，查询输出流向是否为并行终点，如果为并行终点，则拼装对应的并行起点ID
     *
     * @param activityImpl 当前节点
     * @return
     */
    String findParallelGatewayId(ActivityImpl activityImpl);

    /**
     * 根据任务ID获取流程定义
     *
     * @param taskId 任务ID
     * @return
     * @throws Exception
     */
    ProcessDefinitionEntity findProcessDefinitionEntityByTaskId(
            String taskId) throws Exception;

    /**
     * 根据任务ID获取对应的流程实例
     *
     * @param taskId 任务ID
     * @return
     * @throws Exception
     */
    ProcessInstance findProcessInstanceByTaskId(String taskId)
            throws Exception;

    /**
     * 根据任务ID获得任务实例
     *
     * @param taskId 任务ID
     * @return
     * @throws Exception
     */
    TaskEntity findTaskById(String taskId) throws Exception;


    /**
     * 根据流程实例ID和任务key值查询所有同级任务集合
     *
     * @param processInstanceId
     * @param key
     * @return
     */
    List<Task> findTaskListByKey(String processInstanceId, String key);


    /**
     * 迭代循环流程树结构，查询当前节点可驳回的任务节点
     *
     * @param taskId       当前任务ID
     * @param currActivity 当前活动节点
     * @param rtnList      存储回退节点集合
     * @param tempList     临时存储节点集合（存储一次迭代过程中的同级userTask节点）
     * @return 回退节点集合
     */
    List<ActivityImpl> iteratorBackActivity(String taskId,
                                            ActivityImpl currActivity, List<ActivityImpl> rtnList,
                                            List<ActivityImpl> tempList) throws Exception;


    /**
     * 还原指定活动节点流向
     *
     * @param activityImpl         活动节点
     * @param oriPvmTransitionList 原有节点流向集合
     */
    void restoreTransition(ActivityImpl activityImpl,
                           List<PvmTransition> oriPvmTransitionList);

    /**
     * 反向排序list集合，便于驳回节点按顺序显示
     *
     * @param list
     * @return
     */
    List<ActivityImpl> reverList(List<ActivityImpl> list);

    /**
     * 转办流程
     *
     * @param taskId   当前任务节点ID
     * @param userCode 被转办人Code
     */
    void transferAssignee(String taskId, String userCode);

    /**
     * 获取当前流程的下一个节点
     * @param procInstanceId
     * @return
     */
    List<PvmTransition> getOutgoingTransitions(String procInstanceId);
    /**
     * 流程转向操作
     *
     * @param taskId     当前任务ID
     * @param activityId 目标节点任务ID
     * @param variables  流程变量
     * @throws Exception
     */
    void turnTransition(String taskId, String activityId, Map<String, Object> variables) throws Exception;

    InputStream getImageStream(String taskId) throws Exception;
}
