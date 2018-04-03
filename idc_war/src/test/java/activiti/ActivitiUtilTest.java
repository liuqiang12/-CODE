//package activiti;
//
//import org.activiti.engine.HistoryService;
//import org.activiti.engine.ProcessEngine;
//import org.activiti.engine.RepositoryService;
//import org.activiti.engine.RuntimeService;
//import org.activiti.engine.TaskService;
//import org.activiti.engine.repository.Deployment;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.transaction.TransactionConfiguration;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.transaction.annotation.Transactional;
//
///**
// * 单元测试
// */
//// 告诉spring怎样执行
////@RunWith(SpringJUnit4ClassRunner.class)
//// 来标明是web应用测试
//@WebAppConfiguration
//@ContextConfiguration(locations = { "classpath:config/activiti-context.xml" })
//// 声明一个事务管理 每个单元测试都进行事务回滚 无论成功与否
//@TransactionConfiguration(defaultRollback = false)
//@Transactional
//
//public class ActivitiUtilTest {
//
//    @Autowired
//    private ProcessEngine processEngine;//流程引擎对象
//    @Autowired
//    private RepositoryService repositoryService;//工作流仓储服务
//    @Autowired
//    private RuntimeService runtimeService;//工作流运行服务
//    @Autowired
//    private TaskService taskService;//工作流任务服务
//    @Autowired
//    private HistoryService historyService;//工作流历史数据服务
//
//    @Test
//    public void test1() throws Exception {
//        System.out.println(processEngine);
//    }
//    /**
//     * 发布流程
//     * @throws Exception
//     */
//    @Test
//    public void publish() throws Exception {
//        // 获取仓库服务的实例     leave.bpmn 和 leave.png 都存放在src目录下
////        Deployment deployment = processEngine.getRepositoryService()//
////        .createDeployment()//
////        .addClasspathResource("leave.bpmn")//
////        .addClasspathResource("leave.png")//
////        .deploy();
//        System.out.println("发布ID : ");
////        System.out.println("发布ID : " +deployment.getId());
//    }
//
//}