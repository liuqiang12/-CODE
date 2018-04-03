package zk.comm;

/**
 * Created by 54074 on 2017-09-22.
 */


import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import zk.conf.Config;
import zk.observer.ServiceMapObservable;


import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by zhuzs on 2017/4/15.
 */
public class ZkStartUp {
    private final static ExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadExecutor();
    private ExampleClientThatLocks example = null;
    CuratorManager cm = null;

    public void zkInit(ServiceMapObservable observable) {
        ZkConPool pool = new ZkConPool(Config.instance().getZkAddess(), Config.instance().getNamespace());
        FakeLimitedResource fakeLimitedResource = new FakeLimitedResource();
        if (example == null) {
            example = new ExampleClientThatLocks(pool.getClient(), Config.instance().getLockNode(), fakeLimitedResource, Config.instance().getSelfNode());
        }
        CuratorManager cm = new CuratorManager(pool.getClient(), example,observable);
        System.out.println("node:" + Config.instance().getSelfNode() + "start....");
        cm.start(example);
    }

    public void zkClose() {
        try {
            if (cm != null) {
                cm.stop();
            }
            if (example != null) {
                example.releaseLock();
            }

        } catch (Exception e) {
        }
    }

    private static CuratorFramework getClient() {
        String namespace = Config.instance().getNamespace();
        String zkAddess = Config.instance().getZkAddess();
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(zkAddess)
                .retryPolicy(retryPolicy)
                .sessionTimeoutMs(6000)
                .connectionTimeoutMs(3000)
                .namespace(namespace)
                .build();
        client.start();
        return client;
    }
}