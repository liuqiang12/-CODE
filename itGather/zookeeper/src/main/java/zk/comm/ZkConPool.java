package zk.comm;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Created by 54074 on 2017-09-29.
 */
public class ZkConPool {
    CuratorFramework client;
    static final int SESSION_OUTTIME = 5000;
   public ZkConPool(String zkAddress, String namespace)
   {
       RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 10);
       CuratorFramework cf = CuratorFrameworkFactory.builder()
               .connectString(zkAddress)
               .sessionTimeoutMs(SESSION_OUTTIME)
               .retryPolicy(retryPolicy)
               .namespace(namespace)
               .build();
       cf.start();
       client = cf;
   }

    public CuratorFramework getClient() {
        return client;
    }
}
