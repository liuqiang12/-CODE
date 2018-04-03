package com.idc.runnable;

import com.idc.service.IdcContractService;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Map;

public class ResourceRunnable implements Runnable{
    private  ApplicationContext applicationContext;
    private  List<Map<String, Object>> ticketResourceList;
    private  Map<String,Object> ticketMaps;
    private  Boolean resourceFree;
    private  Long ticketInstId;
    private static Integer ticketsCount=0;
    private static IdcContractService idcContractService;

    public ResourceRunnable(){
        super();
    }

    public ResourceRunnable(ApplicationContext applicationContext, List<Map<String, Object>> ticketResourceList, Map<String, Object> ticketMaps, Boolean resourceFree, Long ticketInstId, Integer ticketsCount,IdcContractService idcContractService) {
        this.applicationContext = applicationContext;
        this.ticketResourceList = ticketResourceList;
        this.ticketMaps = ticketMaps;
        this.resourceFree = resourceFree;
        this.ticketInstId = ticketInstId;
        this.ticketsCount = ticketsCount;
        this.idcContractService = idcContractService;
        System.out.println(  "ResourceRunnable类第 30行。构造方法：wcg多线程 applicationContext值：" + applicationContext
                            +";  ticketResourceList值：" + ticketResourceList
                            +";  ticketMaps值：" + ticketMaps
                            +";  resourceFree值：" + resourceFree
                            +";  ticketInstId值：" + ticketInstId
                            +";  ticketsCount值：" + ticketsCount
                            +";  idcContractService值：" + idcContractService);
    }

    @Override
    public void run() {
        try {
            System.out.println(  "ResourceRunnable类第 42行。启动线程：wcg多线程 。  applicationContext值：" + applicationContext
                                +";  ticketResourceList值：" + ticketResourceList
                                +";  ticketMaps值：" + ticketMaps
                                +";  resourceFree值：" + resourceFree
                                +";  ticketInstId值：" + ticketInstId
                                +";  ticketsCount值：" + ticketsCount
                                +";  idcContractService值：" + idcContractService);

            while (true) {
                synchronized (ResourceRunnable.class) {
                    if (ticketsCount == 0)
                        break;
                    try {
                        //Thread.sleep(10);
                        System.out.println("ResourceRunnable类第 56行。 tticketsCount值：" + ticketResourceList.get(ticketsCount - 1));
                        System.out.println("ResourceRunnable类第 57行。 ticketsCount-1值：" + (ticketsCount - 1));
                        updateStatus(applicationContext, ticketResourceList.get(ticketsCount - 1), ticketMaps, resourceFree, ticketInstId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "...这是第" + ticketsCount-- + "线程");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateStatus(ApplicationContext applicationContext, Map<String, Object> resourceMaps, Map<String,Object> ticketMaps, Boolean resourceFree, Long ticketInstId)throws Exception{
        System.out.println("已经进入了 多线程的  ：updateStatus方法了哦");
        System.out.println(  "ResourceRunnable类第 72行。修改头状态调用idcContractService.runnableStatus()方法 ：wcg多线程   applicationContext值：" + applicationContext
                            +"; resourceMaps值：" + resourceMaps
                            +"; ticketMaps值：" + ticketMaps
                            +"; resourceFree值：" + resourceFree
                            +"; idcContractService值：" + idcContractService
                            +";判断idcContractService是否为空 值：" + idcContractService == null ? "为空" : "不为空"
                            +"; ticketInstId>>" + ticketInstId);
        idcContractService.runnableStatus(applicationContext,resourceMaps,ticketMaps,resourceFree,ticketInstId);
    }
}
