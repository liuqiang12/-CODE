package zk.comm;

import org.apache.commons.collections.map.HashedMap;
import zk.conf.Config;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.zookeeper.CreateMode;
import zk.observer.ServiceMapObservable;


import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CuratorManager {
    public static boolean IS_LOCK=false;
    private ExampleClientThatLocks locks;
    private ServiceMapObservable observable;

    private final static ExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadExecutor();
    private CuratorFramework client;
public CuratorManager(CuratorFramework client, ExampleClientThatLocks locks) {
    this.locks=locks;
    this.client=client;
}
    public CuratorManager(CuratorFramework client, ExampleClientThatLocks locks,ServiceMapObservable observable) {
        this.locks=locks;
        this.client=client;
        this.observable=observable;
    }
    public CuratorFramework getClient() {
        return client;
    }

    public void listenNode(String path) {
        final NodeCache nodeCache = new NodeCache(client, path);
        try {
            nodeCache.start();
            NodeCacheListener nodeCacheListener = new IdcNodeCacheListener(nodeCache);
            nodeCache.getListenable().addListener(nodeCacheListener, EXECUTOR_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void start(ExampleClientThatLocks locks) {
        String zkAddres = Config.instance().getZkAddess();
        String listenNode = Config.instance().getListenNode();
        String namespace = Config.instance().getNamespace();
        String lockNode = Config.instance().getLockNode();
        String pNode = Config.instance().getpNode();
        String selfNode=Config.instance().getSelfNode();
        listenNode(listenNode);
        createPathCacheNodeWithListener(pNode);

        try {

            {
                createNode(lockNode, 0);
                createNode(pNode, 1);
                if(Config.instance().getMaster().equals("1") &&Config.instance().getSelfNode().equals("/n1"))
                {
                    if(!this.locks.getLock().isAcquiredInThisProcess()) {
                     //   CuratorManager.IS_LOCK = this.locks.doWork(10000, TimeUnit.MILLISECONDS);
                        boolean tag=this.locks.doWork(10000, TimeUnit.MILLISECONDS);
                        Map<String,Object> map=new HashedMap();
                        map.put("isWork",false);
                        map.put("isLock",tag);
                        map.put("nameNode", Config.instance().getSelfNode());
                        System.out.println("开始......");
                        this.observable.setData(map);
                    }
                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }




    }

    private void createNode(String path, int model) {
        String p = "";
        try {
            if (client.checkExists().forPath(path) == null) {
                if (model == 0) {

                    p = client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
                    System.out.println("create ephemeral node:" + p);

                } else {

                    p = client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path);
                    System.out.println("create persistent node:" + p);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * create parent  node
     *
     * @param parentPath
     */
    private void createPathCacheNodeWithListener(String parentPath) {

        try {
            createNode(parentPath + Config.instance().getSelfNode(), 0);
            PathChildrenCache pathChildrenCache = new PathChildrenCache(client, parentPath, true);
            pathChildrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
            PathChildrenCacheListener pathChildrenCacheListener = new IdcPathChildrenCacheListener(locks,observable);
            pathChildrenCache.getListenable().addListener(pathChildrenCacheListener);
            System.out.println("start linstening" + parentPath + "...");
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteNode(String path) {
        try {
            if (client.checkExists().forPath(path) == null) {

                client.delete().guaranteed().forPath(path);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void stop() {
        client.close();
    }

}
