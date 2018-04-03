/*
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.junit.Test;

import java.util.List;

*/
/**
 * Created by DELL on 2017/7/24.
 *//*

public class HistoricTest {
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    */
/**
     * 查询历史流程实例
     *//*

    @Test
    public void queryHistoricInstance() {
        List<HistoricProcessInstance> list = processEngine.getHistoryService()
                .createHistoricProcessInstanceQuery()
                .orderByProcessInstanceStartTime().asc()//排序
                .list();
        if (list != null && list.size() > 0) {
            for (HistoricProcessInstance hpi : list) {
                System.out.println("流程定义ID：" + hpi.getProcessDefinitionId());
                System.out.println("流程实例ID：" + hpi.getId());
                System.out.println("开始时间：" + hpi.getStartTime());
                System.out.println("结束时间：" + hpi.getEndTime());
                System.out.println("流程持续时间：" + hpi.getDurationInMillis());
                System.out.println("=======================================");
            }
        }
    }

    */
/**
     * 某一次流程执行了多少步
     *//*

    @Test
    public void queryHistoricActivitiInstance() {
        String processInstanceId = "27501";
        List<HistoricActivityInstance> list = processEngine.getHistoryService()
                .createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .list();
        if (list != null && list.size() > 0) {
            for (HistoricActivityInstance hai : list) {
                System.out.println(hai.getId());
                System.out.println("步骤ID：" + hai.getActivityId());
                System.out.println("步骤名称：" + hai.getActivityName());
                System.out.println("执行人：" + hai.getAssignee());
                System.out.println("====================================");
            }
        }
    }

    */
/**
     * 某一次流程的执行经历的多少任务
     *//*

    @Test
    public void queryHistoricTask() {
        String processInstanceId = "27501";
        List<HistoricTaskInstance> list = processEngine.getHistoryService()
                .createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .list();
        if (list != null && list.size() > 0) {
            for (HistoricTaskInstance hti : list) {
                System.out.print("taskId:" + hti.getId()+"，");
                System.out.print("name:" + hti.getName()+"，");
                System.out.print("pdId:" + hti.getProcessDefinitionId()+"，");
                System.out.print("assignee:" + hti.getAssignee()+"，");
            }
        }
    }

    */
/**
     * 某一次流程的执行时设置的流程变量
     *//*

    @Test
    public void queryHistoricVariables() {
        String processInstanceId = "37501";
        List<HistoricVariableInstance> list = processEngine.getHistoryService()
                .createHistoricVariableInstanceQuery()
                .processInstanceId(processInstanceId)
                .list();
        if(list != null && list.size()>0){
            for(HistoricVariableInstance hvi : list){
                System.out.print("piId:"+hvi.getProcessInstanceId()+"，");
                System.out.print("variablesName:"+hvi.getVariableName()+"，");
                System.out.println("variablesValue:"+hvi.getValue()+";");
            }
        }
    }
}
*/
