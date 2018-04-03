/*
package com.idc.threadPool;

import org.apache.hadoop.io.IntWritable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

*/
/**
 * ISP的线程处理
 *//*

public class ISPThreadHand {
    public static void main(String[] args) {
        long start = System.currentTimeMillis() ;
        ISPThreadHand blt = new ISPThreadHand() ;
        List<IntWritable> messages = new ArrayList<IntWritable>();

        for(int i =0 ;i<10000; i++) {
            messages.add(new IntWritable(i)) ;
        }
        RejectedExecutionHandler retryHandler = new RetryRejectedExecutionHandler();
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        */
/*executor.setCorePoolSize(1);//核心线程数，如果运行的线程少于corePoolSize，则创建新线程来执行新任务，即使线程池中的其他线程是空闲的*//*

        executor.setMaximumPoolSize(64);*/
/**最大线程数，可允许创建的线程数，corePoolSize和maximumPoolSize设置的边界自动调整池大小：
         corePoolSize <运行的线程数< maximumPoolSize:仅当队列满时才创建新线程
         corePoolSize=运行的线程数= maximumPoolSize：创建固定大小的线程池
         keepAliveTime:如果线程数多于corePoolSize,则这些多余的线程的空闲时间超过keepAliveTime时将被终止
         unit:keepAliveTime参数的时间单位
         **//*


        executor.setRejectedExecutionHandler(retryHandler);
        */
/*public ThreadPoolExecutor(int corePoolSize,
        int maximumPoolSize,
        long keepAliveTime,
        TimeUnit unit,
        BlockingQueue<Runnable> workQueue,
        ThreadFactory threadFactory,
        RejectedExecutionHandler handler) //后两个参数为可选参数*//*


        for (IntWritable i : messages) {
            executor.execute(blt.new Worker(i));
        }

        executor.shutdown();
        try {
            executor.awaitTermination(60, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace() ;
        }
        System.out.println("Last " + (System.currentTimeMillis() - start) + " ms");
    }

    class Worker implements  Runnable {
        IntWritable msg;

        public Worker(IntWritable msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            try {
                int sum = msg.get() ;
            } catch (Exception e) {
                e.printStackTrace() ;
            }
        }
    }

}

class RetryRejectedExecutionHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace() ;
        }
        executor.execute(r);
    }
}*/
