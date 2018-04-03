package com.idc.service.impl;

import com.idc.mapper.JbpmResourceChildshapeMapper;
import com.idc.mapper.JbpmResourceModelMapper;
import com.idc.mapper.JbpmTaskAuthorizeMapper;
import com.idc.model.JbpmResourceChildshape;
import com.idc.model.JbpmResourceModel;
import com.idc.model.JbpmResourceModelVo;
import com.idc.model.JbpmTaskAuthorize;
import com.idc.service.JbpmTaskAuthorizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.data.supper.service.impl.SuperServiceImpl;

import java.util.List;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>JBPM_TASK_AUTHORIZE:任务授权表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jul 17,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("jbpmTaskAuthorizeService")
public class JbpmTaskAuthorizeServiceImpl extends SuperServiceImpl<JbpmTaskAuthorize, Integer> implements JbpmTaskAuthorizeService {
	@Autowired
	private JbpmTaskAuthorizeMapper mapper;
	@Autowired
	private JbpmResourceModelMapper jbpmResourceModelMapper;
	@Autowired
	private JbpmResourceChildshapeMapper jbpmResourceChildshapeMapper;
	@Autowired
	private JbpmTaskAuthorizeMapper jbpmTaskAuthorizeMapper;
	/**
	 * 创建信息
	 * @param jbpmResourceModel
	 * @param jbpmResourceChildshapeList
	 * @throws Exception
	 */
	@Override
	public void createAuthorize(JbpmResourceModel jbpmResourceModel, List<JbpmResourceChildshape> jbpmResourceChildshapeList) throws Exception{
		//首先删除以name相同的数据
		/*Long tmpId = jbpmResourceModelMapper.getJbpmResourceModelId(jbpmResourceModel.getName());
		 if(tmpId != null && tmpId != 0){*/
			/*jbpmTaskAuthorizeMapper.deleteByResource(tmpId);
			jbpmResourceChildshapeMapper.deleteByResource(tmpId);
			jbpmResourceModelMapper.deleteByResource(tmpId);*/
			 jbpmResourceModelMapper.callClearJbpmResourceModel(jbpmResourceModel.getName());
		/*}*/
		/*创建model*/
		jbpmResourceModelMapper.createJbpmResourceModel(jbpmResourceModel);

		/*然后批量保存*/
		for(JbpmResourceChildshape jbpmResourceChildshape : jbpmResourceChildshapeList){
			jbpmResourceChildshape.setResourceId(jbpmResourceModel.getId());
			jbpmResourceChildshapeMapper.createJbpmResourceChildshape(jbpmResourceChildshape);
			/*然后保存权限信息*/
			for(String key : jbpmResourceChildshape.getGroups()){
				JbpmTaskAuthorize jbpmTaskAuthorize = new JbpmTaskAuthorize();
				jbpmTaskAuthorize.setUserTaskId(jbpmResourceChildshape.getId());
				jbpmTaskAuthorize.setTargetId(key);
				jbpmTaskAuthorize.setResourceId(jbpmResourceModel.getId());
				jbpmTaskAuthorizeMapper.createJbpmTaskAuthorize(jbpmTaskAuthorize);
			}
		}


	}

	/**
	 *
	 * @param key：角色key
	 * @param roleId:角色ID
	 * @return
	 */
	@Override
	public List<JbpmResourceModelVo> queryActModelWithRoleKeyRoleId(String key, Long roleId) {
		String key_id = "role_key_"+key+"@id_"+roleId;
		JbpmResourceModelVo jbpmResourceModelVo = new JbpmResourceModelVo();
		if(key != null && !"".equals(key)
				&& roleId != null && roleId != 0){
			jbpmResourceModelVo.setTargetId(key_id);
		}
		return mapper.queryActModelWithRoleKeyRoleId(jbpmResourceModelVo);
	}
}
