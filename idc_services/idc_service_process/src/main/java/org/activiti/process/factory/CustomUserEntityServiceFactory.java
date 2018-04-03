package org.activiti.process.factory;

import javax.annotation.Resource;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * 自定义用户工程，实现SessionFactory接口
 * @author DELL
 *
 */
public class CustomUserEntityServiceFactory implements SessionFactory{
	/**
	 * 注入用户服务类
	 */
	@Resource(name="customUserEntityService")
	private UserEntityManager userEntityManager;

    @Autowired
    public void setUserEntityManager(UserEntityManager userEntityManager) {
        this.userEntityManager = userEntityManager;
    }

    public Class<?> getSessionType() {
        // 返回原始的UserManager类型
        return UserEntityManager.class;
    }
    public Session openSession() {
        // 返回自定义的UserManager实例
        return userEntityManager;
    }

}
