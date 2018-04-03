package com.idc.springtool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by mylove on 2017/8/10.
 *
 * @author zhoushun
 * @ClassName: SpringBeanUtil
 * @Description: TODO(spring功能类，用于获取bean)
 * @date 2012-11-27 下午04:22:36
 */
@Component("springBeanUtil")
public class SpringBeanUtil implements ApplicationContextAware {
    protected final static Log logger = LogFactory.getLog(SpringBeanUtil.class);

    private static ApplicationContext ctx = null;

    private static Map<String, Properties> propMap = new HashMap<String, Properties>(
            0);

    public void setApplicationContext(ApplicationContext ctx)
            throws BeansException {
        SpringBeanUtil.ctx = ctx;
    }

    public static ApplicationContext getApplicationContext() {
        return ctx;
    }

    public static <T> T getBean(String prop) {
        Object obj = ctx.getBean(prop);
        if (logger.isDebugEnabled()) {
            logger.debug("property=[" + prop + "],object=[" + obj + "]");
        }
        return (T) obj;
    }

    public static Properties getProperties(String filepath) {
        if (propMap.containsKey(filepath))
            return propMap.get(filepath);

        Resource resource = ctx.getResource(filepath);
        Properties prop = new Properties();
        try {
            prop.load(resource.getInputStream());
            propMap.put(filepath, prop);
            return prop;
        } catch (IOException e) {
            logger
                    .error("can not find the resource file:[" + filepath + "]",
                            e);
            return null;
        }
    }
}