package com.idc.threadPool;

import java.util.concurrent.*;

/**
 *
 *
 * Created by DELL on 2017/11/21.
 */
public class HeartBeat {
    public static void main(String[] args) throws Exception{
        System.out.println("心跳操作。。。。。。。。。。。。。。。。。。。。start");
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
        Runnable task = new Runnable() {
            public void run() {
                //System.out.println("HeartBeat.........................");
            }
        };
        executor.scheduleAtFixedRate(task,5,3, TimeUnit.SECONDS);   //5秒后第一次执行，之后每隔3秒执行一次
        System.out.println("心跳操作。。。。。。。。。。。。。。。。。。。。end");
        System.out.println("创建可缓存的线程池，如果线程池中的线程在60秒未被使用就将被移除" +
                "，在执行新的任务时，当线程池中有之前创建的可用线程就重用可用线程，否则就新建一条线程start");
        ExecutorService threadPool = Executors.newCachedThreadPool();//线程池里面的线程数会动态变化，并可在线程线被移除前重用
        for (int i = 1; i <= 3; i ++) {
            final  int taskTmp = i;   //10个任务
            TimeUnit.SECONDS.sleep(1);
            threadPool.execute(new Runnable() {    //接受一个Runnable实例
                public void run() {
                    System.out.println("线程名字： " + Thread.currentThread().getName() +  "  任务名为： "+taskTmp);
                }
            });
        }
        System.out.println("ExecutorService在初始化创建时处于运行状态。" +
                "shutdown方法等待提交的任务执行完成并不再接受新任务，在完成全部提交的任务后关闭" +
                "shutdownNow方法将强制终止所有运行中的任务并不再允许提交新任务start");
        ExecutorService executorByCall = Executors.newSingleThreadExecutor();
        Future<String> future = executorByCall.submit(new Callable<String>() {   //接受一上callable实例
            public String call() throws Exception {
                return "MOBIN";
            }
        });
        System.out.println("任务的执行结果："+future.get());
    }
}
