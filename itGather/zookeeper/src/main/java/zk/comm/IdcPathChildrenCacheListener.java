package zk.comm;

import zk.conf.Config;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import zk.observer.ServiceMapObservable;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class IdcPathChildrenCacheListener implements PathChildrenCacheListener {
    private ExampleClientThatLocks example;
    private ServiceMapObservable observable;

    public IdcPathChildrenCacheListener(ExampleClientThatLocks example) {
        this.example = example;
    }

    public IdcPathChildrenCacheListener(ExampleClientThatLocks example, ServiceMapObservable observable) {
        this.example = example;
        this.observable = observable;
    }

    @Override
    public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
        if (event.getData() != null && event.getType() != null) {
            if (event.getType().equals(PathChildrenCacheEvent.Type.INITIALIZED)) {
                System.out.println("client init ok" + event.getData().getPath());
            } else if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_ADDED)) {
                System.out.println("add node ok" + event.getData().getPath());
                // add node is n1, seft is not n1 , get lock, release lock!
                if (event.getData().getPath().equals("/group_01/n1") && !Config.instance().getSelfNode().equals("/n1")) {
                    System.out.println("node:" + Config.instance().getSelfNode() + "start release lock");
                    if (this.example.getLock().isAcquiredInThisProcess()) {
                        example.releaseLock();
                        //  CuratorManager.IS_LOCK=false;
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("isWork", false);
                        map.put("isLock", false);
                        map.put("nameNode", Config.instance().getSelfNode());
                        this.observable.setData(map);
                        System.out.println("update parms ..." + map.toString());
                        Thread.sleep(6000);

                    } else {
                        System.out.println("node:" + Config.instance().getSelfNode() + "not lock release");
                    }
                }
                // add node is n1, seft is n1 ,not lock,  sleep 6 s get lock!
                else if (event.getData().getPath().equals("/group_01/n1") && Config.instance().getSelfNode().equals("/n1")) {
                    System.out.println("n1 " + " lock status:" + example.getLock().isAcquiredInThisProcess());
                    Thread.sleep(3000);
                    System.out.println("当前工作状态:"+this.observable.getEventInfo());
                    /*
                    if (!example.getLock().isAcquiredInThisProcess()) {
                        System.out.println("n1" + "start..get lock");
                        Thread.sleep(6000);

                        //  CuratorManager.IS_LOCK= example.doWork(10000,TimeUnit.MILLISECONDS);
                        boolean tag = example.doWork(10000, TimeUnit.MILLISECONDS);
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("isWork", false);
                        map.put("isLock", tag);
                        map.put("nameNode", Config.instance().getSelfNode());
                        this.observable.setData(map);
                        System.out.println("update params ..." + map.toString());
                        //   observable.notifyObservers(new EventInfo());
                        System.out.println("n1" + "curr status" + tag);
                    }*/
                }
            } else if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_REMOVED)) {
                System.out.println("delete node ok" + event.getData().getPath());
                System.out.println("node:" + Config.instance().getSelfNode() + "start release lock!");
                List<String> nodes = client.getChildren().forPath("/group_01");
                Collections.sort(nodes);
                System.out.println("sort::" + nodes);

                //n1 down ,other node get lock!
                if (event.getData().getPath().equals("/group_01/n1") && !Config.instance().getSelfNode().equals("/n1")) {
                    if (!example.getLock().isAcquiredInThisProcess()) {
                        System.out.println("curr node" + Config.instance().getSelfNode() + "min node" + nodes.get(0));
                        if (nodes.get(0).equals(Config.instance().getSelfNode().substring(1))) {
                            System.out.println("n1 down" + Config.instance().getSelfNode() + "start get lock!");
                            System.out.println("curr node:" + Config.instance().getSelfNode() + "min node:" + nodes.get(0) + "ok!!");
                            // CuratorManager.IS_LOCK = example.doWork(1000, TimeUnit.MILLISECONDS);
                            boolean tag = example.doWork(10000, TimeUnit.MILLISECONDS);
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("isWork", false);
                            map.put("isLock", tag);
                            map.put("nameNode", Config.instance().getSelfNode());
                            this.observable.setData(map);
                            System.out.println("update params..." + map.toString());
                            Thread.sleep(6000);

                        }
                    }
                } else if (!event.getData().getPath().equals("/group_01/n1")) {
                    if (!example.getLock().isAcquiredInThisProcess()) {
                        System.out.println("curr node" + Config.instance().getSelfNode() + "min node" + nodes.get(0));
                        if (nodes.get(0).equals(Config.instance().getSelfNode().substring(1))) {
                            System.out.println("node" + event.getData().getPath() + "down" + Config.instance().getSelfNode() + "start get lock!");
                            System.out.println("curr node" + Config.instance().getSelfNode() + "min node" + nodes.get(0) + "ok!!");
                            //   CuratorManager.IS_LOCK = example.doWork(1000, TimeUnit.MILLISECONDS);
                            boolean tag = example.doWork(10000, TimeUnit.MILLISECONDS);
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("isWork", false);
                            map.put("isLock", tag);
                            map.put("nameNode", Config.instance().getSelfNode());

                            this.observable.setData(map);
                            System.out.println("update params...");
                            Thread.sleep(6000);
                        }
                    }
                }
            } else if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_UPDATED)) {
                System.out.println("updae node ok" + event.getData().getPath());
            } else if (event.getType().equals(PathChildrenCacheEvent.Type.CONNECTION_LOST)) {
                System.out.println("conn down" + event.getData().getPath());
            } else if (event.getType().equals(PathChildrenCacheEvent.Type.CONNECTION_SUSPENDED)) {
                System.out.println("conn gua" + event.getData().getPath());
            } else if (event.getType().equals(PathChildrenCacheEvent.Type.CONNECTION_RECONNECTED)) {
                System.out.println("re conn" + event.getData().getPath());
            }

        }
    }

}

