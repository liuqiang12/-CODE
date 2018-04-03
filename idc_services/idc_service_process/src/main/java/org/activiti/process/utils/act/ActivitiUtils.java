package org.activiti.process.utils.act;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;

public class ActivitiUtils {
	 
	/*设置对应的act用户实体*/
	public static UserEntity toActivitiUserWithMap(Map<String, String> userEntityMap){
		UserEntity userEntity = new UserEntity();
		//设置成用户ID
		userEntity.setId(userEntityMap.get("id"));
		userEntity.setRevision(1);
		return userEntity;
	}
	//装换成角色实体类
	public static GroupEntity  toActivitiGroupWithMap(Map<String, String> map){
		GroupEntity groupEntity = new GroupEntity();
		groupEntity.setRevision(1);
		groupEntity.setType("assignment");
		groupEntity.setId(map.get("user_id"));
		groupEntity.setName(map.get("role_type"));
		return groupEntity;
	}
	public static List<Group> toActivitiGroupsWithMaps(List<Map<String,String>> roleMaps){
		List<Group> groupEntitys = new ArrayList<Group>();
		for (Map<String, String> roleMap : roleMaps) {
			GroupEntity groupEntity = toActivitiGroupWithMap(roleMap);
			groupEntitys.add(groupEntity);
		}
		return groupEntitys;
	}
}
