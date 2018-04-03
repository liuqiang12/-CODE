package org.activiti.process.service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.idc.service.SysUserinfoService;

/**
 * 自定义组实体的service
 *
 */
@Service
public class CustomGroupEntityService extends GroupEntityManager{
	private static final Log logger = LogFactory.getLog(CustomGroupEntityService.class);
	@Autowired
	private SysUserinfoService sysUserinfoserService;//用于查询实际业务中用户表、角色等表
	
	public GroupEntity findGroupById(final String groupCode) {  
		if (groupCode == null)  
            return null;  
          
            try {  
                Map<String, String> roleToActByGroupCode = sysUserinfoserService.getRoleToActByGroupCode(groupCode);  
                  
                GroupEntity e = new GroupEntity();  
                e.setRevision(1);  
  
                // activiti有3种预定义的组类型：security-role、assignment、user  
                // 如果使用Activiti  
                // Explorer，需要security-role才能看到manage页签，需要assignment才能claim任务  
                e.setType("assignment");  
  
                e.setId(roleToActByGroupCode.get("role_id"));  
                e.setName(roleToActByGroupCode.get("role_name"));  
                return e;  
            } catch (EmptyResultDataAccessException e) {  
                return null;  
            }  
    }  
	/**
	 * 通过用户编码:即登录用户名查找该用户对应的角色
	 * 设置相应的    角色id   角色名称
	 */
	@Override
    public List<Group> findGroupsByUser(final String userCode) {
        if (userCode == null)
            return null;
        //获取对应的角色类型
        List<Map<String,String>> rolesToActByUserName = sysUserinfoserService.getRoleToActByUserName(userCode);
        //拼凑activit的任务角色
        List<Group> gs = new java.util.ArrayList<>();
        GroupEntity g;
        for (Map<String, String> roleMap : rolesToActByUserName) {
            g = new GroupEntity();
            g.setRevision(1);
            //activiti有3种预定义的组类型：security-role、assignment、user
            //如果使用Activiti Explorer，需要security-role才能看到manage页签，需要assignment才能claim任务  
            g.setType("assignment");//授权人类型
            g.setId(roleMap.get("role_id"));
            g.setName(roleMap.get("role_type"));
            gs.add(g);
        }
        return gs;
    }
	@Override
	public List<Group> findGroupByQueryCriteria(GroupQueryImpl query, Page page) {
		 throw new RuntimeException("not implement method.");  
	}

	@Override
	public long findGroupCountByNativeQuery(Map<String, Object> parameterMap) {
		 throw new RuntimeException("not implement method.");  
	}

	@Override
	public long findGroupCountByQueryCriteria(GroupQueryImpl query) {
		 throw new RuntimeException("not implement method.");  
	}

	@Override
	public List<Group> findGroupsByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults) {
		 throw new RuntimeException("not implement method.");  
	}
	
}
