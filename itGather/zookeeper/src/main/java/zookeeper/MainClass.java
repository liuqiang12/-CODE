//package zookeeper;
//
//import org.apache.curator.RetryPolicy;
//import org.apache.curator.framework.CuratorFramework;
//import org.apache.curator.framework.CuratorFrameworkFactory;
//import org.apache.curator.framework.recipes.cache.NodeCache;
//import org.apache.curator.framework.recipes.cache.NodeCacheListener;
//import org.apache.curator.retry.ExponentialBackoffRetry;
//import org.apache.zookeeper.CreateMode;
//public class MainClass {
//
//    public static void main(String[] args) throws Exception {
//
//        CuratorFramework client = getClient();
//        String path = "/p1";
//        if(client.checkExists().forPath("/p1")==null) {
//            client.create().withMode(CreateMode.EPHEMERAL).forPath("/p1");
//        }
//        final NodeCache nodeCache = new NodeCache(client,path);
//        nodeCache.start();
//        nodeCache.getListenable().addListener(new NodeCacheListener() {
//            @Override
//            public void nodeChanged() throws Exception {
//                System.out.println(""+nodeCache.getCurrentData());
//                System.out.println("" + new String(nodeCache.getCurrentData().getData()));
//            }
//        });
//        client.setData().forPath(path,"test1".getBytes());
//        client.setData().forPath(path,"test2".getBytes());
//        client.setData().forPath(path,"test3".getBytes());
//        client.setData().forPath(path,"test3".getBytes());
//        client.setData().forPath(path,"test4".getBytes());
//        client.setData().forPath(path,"test5".getBytes());
//        Thread.sleep(Integer.MAX_VALUE);
//
//    }
//
//    private static CuratorFramework getClient(){
//        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,3);
//        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183")
//                .retryPolicy(retryPolicy)
//                .sessionTimeoutMs(6000)
//                .connectionTimeoutMs(3000)
//                .namespace("demo")
//                .build();
//        client.start();
//        return client;
//    }
//}
