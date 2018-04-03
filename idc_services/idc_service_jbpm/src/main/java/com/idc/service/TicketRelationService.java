package com.idc.service;

import com.idc.model.IdcHisTicket;
import com.idc.model.IdcRunTicket;
import com.idc.model.SysUserinfo;
import org.springframework.data.redis.connection.RedisHashCommands;
import org.springframework.data.redis.connection.RedisSetCommands;
import system.exception.JbpmException;

import java.util.List;

/**
 * Created by DELL on 2017/9/19.
 */
public interface TicketRelationService {
    /**
     * 保存有关运行时工单的关系：包括主外键。目的是在主界面上查询使用
     * @param idcRunTicket
     */
    void singleRunTicketIntoRedis(IdcRunTicket idcRunTicket, RedisHashCommands redisHashCommands, RedisSetCommands redisSetCommands);
    /**
     * 保存有关历史工单的关系：包括主外键。目的是在主界面上查询使用
     * @param idcHisTicket
     */
    void singleHisTicketIntoRedis(IdcHisTicket idcHisTicket, RedisHashCommands redisHashCommands, RedisSetCommands redisSetCommands);
    /**
     * key是USER_[ID]
     * @param redisSetCommands
     * @param idcRunTicketKeyByte
     * @param sysUserinfoList
     * @param idcRunTicket
     * @throws Exception
     */
    void userTicket(RedisSetCommands redisSetCommands, byte[] idcRunTicketKeyByte, List<SysUserinfo> sysUserinfoList, IdcRunTicket idcRunTicket) throws JbpmException ;
    void userHisTicket(RedisSetCommands redisSetCommands, byte[] idcRunTicketKeyByte, List<SysUserinfo> sysUserinfoList, IdcHisTicket idcHisTicket) throws JbpmException ;
}
