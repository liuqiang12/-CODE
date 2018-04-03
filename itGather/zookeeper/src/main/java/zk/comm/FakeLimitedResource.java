package zk.comm;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by 54074 on 2017-09-28.
 */
public class FakeLimitedResource {
    private final AtomicBoolean inUse = new AtomicBoolean(false);
    public void use() throws Exception {
        if(!inUse.compareAndSet(false, true)) {
            throw new IllegalStateException("Needs to be used by one client at a time");
        }

        try {
            Thread.sleep((long) (3 * Math.random()));
        } finally {
            inUse.set(false);
        }
    }
}
