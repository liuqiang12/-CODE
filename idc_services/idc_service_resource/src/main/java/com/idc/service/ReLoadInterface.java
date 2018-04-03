package com.idc.service;

import com.idc.mapper.IdcConnectorMapper;
import com.idc.mapper.IdcLocationCountMapper;
import com.idc.mapper.IdcLocationMapper;
import com.idc.model.IdcLocationCount;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 所有要统计的信息实现该借口并返回统计信息
 *
 * @author Created by mylove on 2017/8/10.
 */
public abstract class ReLoadInterface {
    protected Logger logger = Logger.getLogger(this.getClass().getName());
    @Autowired
    protected IdcLocationCountMapper idcLocationCountMapper;
    @Autowired
    protected IdcLocationMapper idcLocationMapper;
    /**
     * @param localId
     * @param count   获取统计数据 放到count中  由调用方统一更新
     */
    public abstract void reloadCount(Long localId, IdcLocationCount count);


    public void reloadCount(Long localId) {

    }

    public void reloadCount() {

    }

}
