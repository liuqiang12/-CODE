package zk.comm;

import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;

/**
 * Created by 54074 on 2017-09-27.
 */
public class IdcNodeCacheListener implements NodeCacheListener {

    private NodeCache node;

    public IdcNodeCacheListener(NodeCache node) {
        this.node = node;
    }

    @Override
    public void nodeChanged() throws Exception {

        if (node.getCurrentData() != null) {
            System.out.println("change node:" + node.getCurrentData().getPath() + "status:" + node.getCurrentData().getStat());

        } else {

            System.out.println("node is down,node:" + node);
        }

    }
}
