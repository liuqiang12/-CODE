package zk.comm;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.RevocationListener;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by 54074 on 2017-09-28.
 */
public class ExampleClientThatLocks {
    private final FakeLimitedResource resource;
    private final String clientName;
    private InterProcessMutex lock;
    private RevocationListener<InterProcessMutex> revocationListener;
    private Map<Thread, Boolean> lockedThread = new WeakHashMap<Thread, Boolean>();
    private String path;

    public ExampleClientThatLocks(CuratorFramework framework, String path, FakeLimitedResource resource, String clientName) {
        this.lock = new InterProcessMutex(framework, path);
        this.resource = resource;
        this.clientName = clientName;
        this.path = path;
    }

    public void releaseLock() {
        try {
            if (lock != null && lock.isAcquiredInThisProcess()) {
                lock.release();
            }
            System.out.println(clientName + "release lock!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean doWork(long time, TimeUnit timeUnit) throws Exception {
        boolean islock =false;
        if (lock.isAcquiredInThisProcess())
        {
            System.out.println("lock is use!!!");
            return  islock;
        }else
        {
            islock = lock.acquire(time, timeUnit);
        }


        System.out.println(clientName +"get lock!");

        try {
            resource.use();
            return islock;
        } finally {
            return islock;
        }
    }

    public InterProcessMutex getLock() {
        return lock;
    }


}
