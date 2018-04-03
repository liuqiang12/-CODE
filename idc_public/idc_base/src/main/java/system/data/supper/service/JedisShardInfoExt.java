package system.data.supper.service;

import org.apache.commons.lang.StringUtils;
import redis.clients.jedis.JedisShardInfo;

import java.net.URI;

/**
 */
public class JedisShardInfoExt extends JedisShardInfo {
    public JedisShardInfoExt(String host) {
        super(host);
    }

    public JedisShardInfoExt(URI uri) {
        super(uri);
    }

    public JedisShardInfoExt(String host, String name) {
        super(host, name);
    }

    public JedisShardInfoExt(String host, int port) {
        super(host, port);
    }

    public JedisShardInfoExt(String host, int port, String name) {
        super(host, port, name);
    }

    public JedisShardInfoExt(String host, int port, int timeout) {
        super(host, port, timeout);
    }

    public JedisShardInfoExt(String host, int port, int timeout, String name) {
        super(host, port, timeout, name);
    }

    public JedisShardInfoExt(String host, int port, int connectionTimeout, int soTimeout, int weight) {
        super(host, port, connectionTimeout, soTimeout, weight);
    }

    public JedisShardInfoExt(String host, String name, int port, int timeout, int weight) {
        super(host, name, port, timeout, weight);
    }

    public void setPassword(String auth) {
        if (!StringUtils.isBlank(auth)){
            super.setPassword(auth);
        }
    }
}
