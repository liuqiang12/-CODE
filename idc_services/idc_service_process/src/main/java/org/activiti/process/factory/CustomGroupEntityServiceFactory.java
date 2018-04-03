package org.activiti.process.factory;

import javax.annotation.Resource;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * 自定义角色工程，实现SessionFactory接口
 * @author DELL
 *
 */
public class CustomGroupEntityServiceFactory implements SessionFactory {
	/**
	 * 注入角色服务类
	 */
	@Resource(name="customGroupEntityService")
	private GroupEntityManager groupEntityManager;

    @Autowired
    public void setGroupEntityManager(GroupEntityManager groupEntityManager) {
        this.groupEntityManager = groupEntityManager;
    }

    public Class<?> getSessionType() {
        // 返回原始的GroupEntityManager类型
        return GroupEntityManager.class;
    }

    public Session openSession() {
        // 返回自定义的GroupEntityManager实例
        return groupEntityManager;
    }
}
