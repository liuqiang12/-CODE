package org.activiti.process.service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.persistence.entity.IdentityInfoEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import org.activiti.process.utils.act.ActivitiUtils;
import com.idc.service.SysUserinfoService;
/**
 * 自定义用户认证 服务类
 */
@Service
public class CustomUserEntityService extends UserEntityManager{
	private static final Log logger = LogFactory.getLog(CustomUserEntityService.class);

    @Autowired
    private SysUserinfoService sysUserinfoService;//自己实现的获取用户数据的Service

    @Override
    public UserEntity findUserById(final String userCode) {
        if (userCode == null)
            return null;

        try {
            UserEntity userEntity = null;
            //获取用户信息【本地的用户】
            Map<String, String> userToActMapByUserName = sysUserinfoService.getUserToActMapByUserName(userCode);
            userEntity = ActivitiUtils.toActivitiUserWithMap(userToActMapByUserName);
            return userEntity;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Group> findGroupsByUser(final String userCode) {
        if (userCode == null)
            return null;
        List<Map<String, String>> rolesToActByUserName = sysUserinfoService.getRoleToActByUserName(userCode);
        List<Group> gs = null;
        gs = ActivitiUtils.toActivitiGroupsWithMaps(rolesToActByUserName);
        return gs;
    }

    @Override
    public List<User> findUserByQueryCriteria(UserQueryImpl query, Page page) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public IdentityInfoEntity findUserInfoByUserIdAndKey(String userId,
                                                         String key) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public List<String> findUserInfoKeysByUserIdAndType(String userId,
                                                        String type) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public long findUserCountByQueryCriteria(UserQueryImpl query) {
        throw new RuntimeException("not implement method.");
    }
}
