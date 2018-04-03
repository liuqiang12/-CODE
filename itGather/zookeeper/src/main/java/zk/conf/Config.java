package zk.conf;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author lcw
 * @date 2016-70-15
 */
public class Config {

    private static Logger logger = Logger.getLogger(Config.class);

    private static final String configFile = "/config.properties";

    private String zkAddess;
    private String listenNode;
    private String lockNode;
    private String namespace;
    private String pNode;
    private String selfNode;
    private String master;

    private static final Config config = new Config();


    private Config() {
        Properties p = new Properties();
        InputStream inStream = this.getClass().getResourceAsStream(configFile);
        if (inStream == null) {
            logger.error("根目录下找不到config.properties文件");
            return;
        }
        try {
            p.load(inStream);
            this.zkAddess = p.getProperty("zk.address").trim();
            this.listenNode = p.getProperty("zk.listen_node").trim();
            this.lockNode = p.getProperty("zk.lock_node").trim();
            this.namespace = p.getProperty("zk.name_space").trim();
            this.pNode = p.getProperty("zk.p_node").trim();
            this.selfNode=p.getProperty("zk.self_node").trim();
            this.master=p.getProperty("zk.master").trim();
            inStream.close();
        } catch (IOException e) {
            logger.error("load config.properties error,class根目录下找不到config.properties文件");
            e.printStackTrace();
        }
        logger.info("load config.properties success");
    }

    public static Config instance() {
        return config;
    }



    public static void main(String[] args) throws Exception {

    }

    public String getZkAddess() {
        return zkAddess;
    }

    public String getListenNode() {
        return listenNode;
    }

    public String getLockNode() {
        return lockNode;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getSelfNode() {
        return selfNode;
    }
    public String getMaster()
    {
        return master;
    }
    public String getpNode() {
        return pNode;
    }
}
