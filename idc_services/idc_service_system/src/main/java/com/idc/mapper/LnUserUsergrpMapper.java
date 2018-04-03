package com.idc.mapper;

import com.idc.model.LnUserUsergrp;
import system.data.supper.mapper.SuperMapper;

import java.util.List;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>sys_ln_user_usergrp:用户与用户组关联表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 24,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface LnUserUsergrpMapper extends SuperMapper<LnUserUsergrp, Integer>{

    void deleteByLnUserUsergrps(List<LnUserUsergrp> list) throws Exception;
    /**
	 *   Special code can be written here 
	 */
}

 