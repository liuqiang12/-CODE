//package com.commer.app;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * Created by mylove on 2018/1/11.
// */
//@Configuration
//public class SchedledConfiguration {
//
//    // 配置中设定了
//    // ① targetMethod: 指定需要定时执行scheduleInfoAction中的simpleJobTest()方法
//    // ② concurrent：对于相同的JobDetail，当指定多个Trigger时, 很可能第一个job完成之前，
//    // 第二个job就开始了。指定concurrent设为false，多个job不会并发运行，第二个job将不会在第一个job完成之前开始。
//    // ③ cronExpression：0/10 * * * * ?表示每10秒执行一次，具体可参考附表。
//    // ④ triggers：通过再添加其他的ref元素可在list中放置多个触发器。 scheduleInfoAction中的simpleJobTest()方法
//    @Bean(name = "detailFactoryBean")
//    public MethodInvokingJobDetailFactoryBean detailFactoryBean(ScheduledTasks scheduledTasks){
//        MethodInvokingJobDetailFactoryBean bean = new MethodInvokingJobDetailFactoryBean ();
//        bean.setTargetObject (scheduledTasks);
//        bean.setTargetMethod ("reportCurrentByCron");
//        bean.setConcurrent (false);
//        return bean;
//    }
//
//    @Bean(name = "cronTriggerBean")
//    public CronTriggerBean cronTriggerBean(MethodInvokingJobDetailFactoryBean detailFactoryBean){
//        CronTriggerBean tigger = new CronTriggerBean ();
//        tigger.setJobDetail (detailFactoryBean.getObject ());
//        try {
//            tigger.setCronExpression ("0/5 * * * * ? ");//每5秒执行一次
//        } catch (ParseException e) {
//            e.printStackTrace ();
//        }
//        return tigger;
//
//    }
//
//    @Bean
//    public SchedulerFactoryBean schedulerFactory(CronTriggerBean[] cronTriggerBean){
//        SchedulerFactoryBean bean = new SchedulerFactoryBean ();
//        System.err.println (cronTriggerBean[0]);
//        bean.setTriggers (cronTriggerBean);
//        return bean;
//    }
//}