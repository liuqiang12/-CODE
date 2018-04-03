package com.idc.service.impl;

import com.idc.model.IdcHisTicket;
import com.idc.model.IdcRunTicket;
import com.idc.model.SysUserinfo;
import com.idc.service.SysUserinfoService;
import com.idc.service.TicketRelationService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisHashCommands;
import org.springframework.data.redis.connection.RedisSetCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import system.exception.JbpmException;
import utils.DevContext;

import java.util.List;
import java.util.Map;

@Service("ticketRelationService")
public class TicketRelationServiceImpl implements TicketRelationService {
    private static final Log log = LogFactory.getLog(TicketRelationServiceImpl.class);
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SysUserinfoService sysUserinfoService;

    @Override
    public void singleRunTicketIntoRedis(IdcRunTicket idcRunTicket,RedisHashCommands redisHashCommands,RedisSetCommands redisSetCommands) {


        //正在运行时的工单的所有数据
        if(idcRunTicket != null ){
            byte[] idcRunTicketKeyByte = redisTemplate.getStringSerializer().serialize(DevContext.IDC_RUN_TICKET_EXT+":ticketInstId:"+idcRunTicket.getId());
            /**
             * 1:保存正在运行的工单信息
             */
            //log.debug(idcRunTicket.getSerialNumber()+":--------------序列化map--------------start");
            Map map = null;// stringSerializerToMap(idcRunTicket);

            redisHashCommands.hMSet(idcRunTicketKeyByte, map);
            //log.debug(idcRunTicket.getSerialNumber()+":--------------将工单与[用户、自有政企、序列号、预勘开通等建立内存外键]分为不同类别的工单的关联关系内存表--------------start");
            categoryTicketRelation(redisSetCommands,idcRunTicket,idcRunTicketKeyByte);
                    /*
                    *  2:该用户具有的工单信息
                    * */
            //查询指定用户集合:当前使用通过查询数据库方式。还是可以采用获取缓存中的数据
            /*if(idcRunTicket.getUser_ids() != null && !"".equals(idcRunTicket.getUser_ids())){
                List<SysUserinfo> sysUserinfoList = sysUserinfoService.getUserListByTicketUserIds(idcRunTicket.getUser_ids());
                if(sysUserinfoList != null && !sysUserinfoList.isEmpty()){
                    //log.debug("---------------设置用户具有的工单:目的是为了过滤权限使用--------------start");
                    userTicket(redisSetCommands,idcRunTicketKeyByte,sysUserinfoList,idcRunTicket);
                }
            }*/

            /**
             * select * from act_hi_identitylink t2 where to_char(t1.procInstId) = to_char(t2.proc_inst_id_)
                and to_char(t2.user_id_) = #{params.apply_id_str}
             */

                    /*工单分为政企工单还是自由工单:通过工单号查询*/
            //log.debug("---------------该工单具有的工单序列号,目的是为了模糊查询使用--------------start");
            byte[]  serialNumberKeyValByte = redisTemplate.getStringSerializer().serialize(DevContext.IDC_RUN_TICKET_EXT+":serialNumber:"+idcRunTicket.getSerialNumber());
            redisSetCommands.sAdd(serialNumberKeyValByte, idcRunTicketKeyByte);
                    /*byte[] idcReProductKeyValByte = redisTemplate.getStringSerializer().serialize("prodInstId:"+idcRunTicket.getProdInstId());
                    redisSetCommands.sAdd(idcReProductKeyValByte, idcRunTicketKeyByte);*/

            //是否是自有业务
            //log.debug("---------------该工单属于政企业务还是自有业务--------------start");
            byte[] ticketCategoryKeyValByte = redisTemplate.getStringSerializer().serialize(DevContext.IDC_RUN_TICKET_EXT+":ticketcategory:"/*+idcRunTicket.getTicketCategory()*/);
            redisSetCommands.sAdd(ticketCategoryKeyValByte, idcRunTicketKeyByte);
        }
    }

    @Override
    public void singleHisTicketIntoRedis(IdcHisTicket idcHisTicket,RedisHashCommands redisHashCommands,RedisSetCommands redisSetCommands) {
        //log.debug("后期在做。。。。。。。。。。。。。。。。。。。。。");
        /**
         * 历史工单中需要做两件事儿
         * 1：按照userTicket(redisSetCommands,idcRunTicketKeyByte,sysUserinfoList,idcRunTicket);方式建立历史工单和具有权限审批的人关联
         * 2：审批过了的人员工单关联
         * 分页查询的时候，就按照两个的并集分页显示
         */
        //正在运行时的工单的所有数据
        if(idcHisTicket != null ){
            byte[] idcRunTicketKeyByte = redisTemplate.getStringSerializer().serialize(DevContext.IDC_HIS_TICKET_EXT+":ticketInstId:"+idcHisTicket.getId());
            /**
             * 1:保存正在运行的工单信息
             */
            //log.debug(idcHisTicket.getSerialNumber()+":--------------序列化map--------------start");
            Map map = null;//stringSerializerToHisMap(idcHisTicket);

            redisHashCommands.hMSet(idcRunTicketKeyByte, map);
            //log.debug(idcHisTicket.getSerialNumber()+":--------------将工单与[用户、自有政企、序列号、预勘开通等建立内存外键]分为不同类别的工单的关联关系内存表--------------start");
           // categoryHisTicketRelation(redisSetCommands,idcHisTicket,idcRunTicketKeyByte);
                    /*
                    *  2:该用户具有的工单信息
                    * */
            //查询指定用户集合:当前使用通过查询数据库方式。还是可以采用获取缓存中的数据
            /*if(idcHisTicket.getUser_ids() != null && !"".equals(idcHisTicket.getUser_ids())){
                List<SysUserinfo> sysUserinfoList = sysUserinfoService.getUserListByTicketUserIds(idcHisTicket.getUser_ids());
                if(sysUserinfoList != null && !sysUserinfoList.isEmpty()){
                    //log.debug("---------------设置用户具有的工单:目的是为了过滤权限使用--------------start");
                    userHisTicket(redisSetCommands,idcRunTicketKeyByte,sysUserinfoList,idcHisTicket);
                }
            }*/

            /**
             * select * from act_hi_identitylink t2 where to_char(t1.procInstId) = to_char(t2.proc_inst_id_)
             and to_char(t2.user_id_) = #{params.apply_id_str}
             */

                    /*工单分为政企工单还是自由工单:通过工单号查询*/
            //log.debug("---------------该工单具有的工单序列号,目的是为了模糊查询使用--------------start");
            byte[]  serialNumberKeyValByte = redisTemplate.getStringSerializer().serialize(DevContext.IDC_HIS_TICKET_EXT+":serialNumber:"+idcHisTicket.getSerialNumber());
            redisSetCommands.sAdd(serialNumberKeyValByte, idcRunTicketKeyByte);
                    /*byte[] idcReProductKeyValByte = redisTemplate.getStringSerializer().serialize("prodInstId:"+idcRunTicket.getProdInstId());
                    redisSetCommands.sAdd(idcReProductKeyValByte, idcRunTicketKeyByte);*/

            //是否是自有业务
            //log.debug("---------------该工单属于政企业务还是自有业务--------------start");
            byte[] ticketCategoryKeyValByte = redisTemplate.getStringSerializer().serialize(DevContext.IDC_HIS_TICKET_EXT+":ticketcategory:"+idcHisTicket.getTicketCategory());
            redisSetCommands.sAdd(ticketCategoryKeyValByte, idcRunTicketKeyByte);
            //log.debug("---------------该工单的审批过的审批人：--------------start  通过任务ID查找 审批人");


        }
    }
    /**
     *子表的关联关系
     * @param redisSetCommands
     * @param idcHisTicket
     * @param idcRunTicketKeyByte
     */
    /*public void categoryHisTicketRelation(RedisSetCommands redisSetCommands,IdcHisTicket idcHisTicket,byte[] idcRunTicketKeyByte){
        if(idcHisTicket.getCategory() != null && "100".equals(idcHisTicket.getCategory())){
            byte[]  idcRunTicketKeyValByte = redisTemplate.getStringSerializer().serialize("RUN_IDC_TICKET_PRE_ACCEPT");
            redisSetCommands.sAdd(idcRunTicketKeyValByte, idcRunTicketKeyByte);
        }else if(idcHisTicket.getCategory() != null && "200".equals(idcHisTicket.getCategory())){
            byte[]  idcRunTicketKeyValByte = redisTemplate.getStringSerializer().serialize("HIS_IDC_TICKET_OPEN");
            redisSetCommands.sAdd(idcRunTicketKeyValByte, idcRunTicketKeyByte);
        }else if(idcHisTicket.getCategory() != null && "400".equals(idcHisTicket.getCategory())){
            byte[]  idcRunTicketKeyValByte = redisTemplate.getStringSerializer().serialize("HIS_IDC_TICKET_PAUSE");
            redisSetCommands.sAdd(idcRunTicketKeyValByte, idcRunTicketKeyByte);
        }else if(idcHisTicket.getCategory() != null && "500".equals(idcHisTicket.getCategory())){
            byte[]  idcRunTicketKeyValByte = redisTemplate.getStringSerializer().serialize("HIS_IDC_TICKET_RECOVER");
            redisSetCommands.sAdd(idcRunTicketKeyValByte, idcRunTicketKeyByte);
        }else if(idcHisTicket.getCategory() != null && "600".equals(idcHisTicket.getCategory())){
            byte[]  idcRunTicketKeyValByte = redisTemplate.getStringSerializer().serialize("HIS_IDC_TICKET_HALT");
            redisSetCommands.sAdd(idcRunTicketKeyValByte, idcRunTicketKeyByte);
        }else if(idcHisTicket.getCategory() != null && "700".equals(idcHisTicket.getCategory())){
            byte[]  idcRunTicketKeyValByte = redisTemplate.getStringSerializer().serialize("HIS_IDC_TICKET_OPEN_CHANGE");
            redisSetCommands.sAdd(idcRunTicketKeyValByte, idcRunTicketKeyByte);
        }else if(idcHisTicket.getCategory() != null && "800".equals(idcHisTicket.getCategory())){
            byte[]  idcRunTicketKeyValByte = redisTemplate.getStringSerializer().serialize("HIS_IDC_TICKET_TEMPORARY");
            redisSetCommands.sAdd(idcRunTicketKeyValByte, idcRunTicketKeyByte);
        }else if(idcHisTicket.getCategory() != null && "900".equals(idcHisTicket.getCategory())){
            byte[]  idcRunTicketKeyValByte = redisTemplate.getStringSerializer().serialize("HIS_IDC_TICKET_BUSINESS_CHANGE");
            redisSetCommands.sAdd(idcRunTicketKeyValByte, idcRunTicketKeyByte);
        }else if(idcHisTicket.getCategory() != null && "300".equals(idcHisTicket.getCategory())){
            byte[]  idcRunTicketKeyValByte = redisTemplate.getStringSerializer().serialize("HIS_IDC_TICKET_PRE_CHANGE");
            redisSetCommands.sAdd(idcRunTicketKeyValByte, idcRunTicketKeyByte);
        }
    }*/

    /**
     *子表的关联关系
     * @param redisSetCommands
     * @param idcRunTicket
     * @param idcRunTicketKeyByte
     */
    public void categoryTicketRelation(RedisSetCommands redisSetCommands,IdcRunTicket idcRunTicket,byte[] idcRunTicketKeyByte){
        if(idcRunTicket.getTicketCategory() != null && "100".equals(idcRunTicket.getTicketCategory())){
            byte[]  idcRunTicketKeyValByte = redisTemplate.getStringSerializer().serialize("RUN_IDC_TICKET_PRE_ACCEPT");
            redisSetCommands.sAdd(idcRunTicketKeyValByte, idcRunTicketKeyByte);
        }else if(idcRunTicket.getTicketCategory() != null && "200".equals(idcRunTicket.getTicketCategory())){
            byte[]  idcRunTicketKeyValByte = redisTemplate.getStringSerializer().serialize("RUN_IDC_TICKET_OPEN");
            redisSetCommands.sAdd(idcRunTicketKeyValByte, idcRunTicketKeyByte);
        }else if(idcRunTicket.getTicketCategory() != null && "400".equals(idcRunTicket.getTicketCategory())){
            byte[]  idcRunTicketKeyValByte = redisTemplate.getStringSerializer().serialize("RUN_IDC_TICKET_PAUSE");
            redisSetCommands.sAdd(idcRunTicketKeyValByte, idcRunTicketKeyByte);
        }else if(idcRunTicket.getTicketCategory() != null && "500".equals(idcRunTicket.getTicketCategory())){
            byte[]  idcRunTicketKeyValByte = redisTemplate.getStringSerializer().serialize("RUN_IDC_TICKET_RECOVER");
            redisSetCommands.sAdd(idcRunTicketKeyValByte, idcRunTicketKeyByte);
        }else if(idcRunTicket.getTicketCategory() != null && "600".equals(idcRunTicket.getTicketCategory())){
            byte[]  idcRunTicketKeyValByte = redisTemplate.getStringSerializer().serialize("RUN_IDC_TICKET_HALT");
            redisSetCommands.sAdd(idcRunTicketKeyValByte, idcRunTicketKeyByte);
        }else if(idcRunTicket.getTicketCategory() != null && "700".equals(idcRunTicket.getTicketCategory())){
            byte[]  idcRunTicketKeyValByte = redisTemplate.getStringSerializer().serialize("RUN_IDC_TICKET_OPEN_CHANGE");
            redisSetCommands.sAdd(idcRunTicketKeyValByte, idcRunTicketKeyByte);
        }else if(idcRunTicket.getTicketCategory() != null && "800".equals(idcRunTicket.getTicketCategory())){
            byte[]  idcRunTicketKeyValByte = redisTemplate.getStringSerializer().serialize("RUN_IDC_TICKET_TEMPORARY");
            redisSetCommands.sAdd(idcRunTicketKeyValByte, idcRunTicketKeyByte);
        }else if(idcRunTicket.getTicketCategory() != null && "900".equals(idcRunTicket.getTicketCategory())){
            byte[]  idcRunTicketKeyValByte = redisTemplate.getStringSerializer().serialize("RUN_IDC_TICKET_BUSINESS_CHANGE");
            redisSetCommands.sAdd(idcRunTicketKeyValByte, idcRunTicketKeyByte);
        }else if(idcRunTicket.getTicketCategory() != null && "300".equals(idcRunTicket.getTicketCategory())){
            byte[]  idcRunTicketKeyValByte = redisTemplate.getStringSerializer().serialize("RUN_IDC_TICKET_PRE_CHANGE");
            redisSetCommands.sAdd(idcRunTicketKeyValByte, idcRunTicketKeyByte);
        }
    }

    /*public Map stringSerializerToHisMap(IdcHisTicket idcHisTicket){
        Map map = new HashMap();
        byte[] runTicket_key_ticketInstId = redisTemplate.getStringSerializer().serialize("ticketInstId");
        byte[] runTicket_ticketInstId = redisTemplate.getStringSerializer().serialize(String.valueOf(idcHisTicket.getTicketInstId()));
        map.put(runTicket_key_ticketInstId, runTicket_ticketInstId);

        if(idcHisTicket.getCustomerId() != null){
            byte[] runTicket_key_customerId = redisTemplate.getStringSerializer().serialize("customerId");
            byte[] runTicket_customerId = redisTemplate.getStringSerializer().serialize(String.valueOf(idcHisTicket.getCustomerId()));
            map.put(runTicket_key_customerId, runTicket_customerId);
        }

        if(idcHisTicket.getCustomerName() != null){
            byte[] runTicket_key_customerName = redisTemplate.getStringSerializer().serialize("customerName");
            byte[] runTicket_customerName = redisTemplate.getStringSerializer().serialize(String.valueOf(idcHisTicket.getCustomerName()));
            map.put(runTicket_key_customerName, runTicket_customerName);
        }

        if(idcHisTicket.getSerialNumber() != null) {
            byte[] runTicket_key_serialNumber = redisTemplate.getStringSerializer().serialize("serialNumber");
            byte[] runTicket_serialNumber = redisTemplate.getStringSerializer().serialize(String.valueOf(idcHisTicket.getSerialNumber()));
            map.put(runTicket_key_serialNumber, runTicket_serialNumber);
        }

        if(idcHisTicket.getCustomerAttr() != null) {
            byte[] runTicket_key_customerAttr = redisTemplate.getStringSerializer().serialize("customerAttr");
            byte[] runTicket_customerAttr = redisTemplate.getStringSerializer().serialize(String.valueOf(idcHisTicket.getCustomerAttr()));
            map.put(runTicket_key_customerAttr, runTicket_customerAttr);
        }


        if(idcHisTicket.getContactMobile() != null) {
            byte[] runTicket_key_contactMobile = redisTemplate.getStringSerializer().serialize("contactMobile");
            byte[] runTicket_contactMobile = redisTemplate.getStringSerializer().serialize(String.valueOf(idcHisTicket.getContactMobile()));
            map.put(runTicket_key_contactMobile, runTicket_contactMobile);
        }
        if(idcHisTicket.getProdInstId() != null) {
            byte[] runTicket_key_ProdInstId = redisTemplate.getStringSerializer().serialize("prodInstId");
            byte[] runTicket_ProdInstId = redisTemplate.getStringSerializer().serialize(String.valueOf(idcHisTicket.getProdInstId()));
            map.put(runTicket_key_ProdInstId, runTicket_ProdInstId);
        }
        if(idcHisTicket.getTicketCategory() != null) {
            byte[] runTicket_key_ticketcategory = redisTemplate.getStringSerializer().serialize("ticketcategory");
            byte[] runTicket_ticketcategory = redisTemplate.getStringSerializer().serialize(String.valueOf(idcHisTicket.getTicketCategory()));
            map.put(runTicket_key_ticketcategory, runTicket_ticketcategory);
        }

        if(idcHisTicket.getId() != null) {
            byte[] runTicket_key_id= redisTemplate.getStringSerializer().serialize("id");
            byte[] runTicket_id = redisTemplate.getStringSerializer().serialize(String.valueOf(idcHisTicket.getId()));
            map.put(runTicket_key_id, runTicket_id);
        }

        if(idcHisTicket.getParentId() != null) {
            byte[] runTicket_key_parentId= redisTemplate.getStringSerializer().serialize("parentId");
            byte[] runTicket_parentId= redisTemplate.getStringSerializer().serialize(String.valueOf(idcHisTicket.getParentId()));
            map.put(runTicket_key_parentId, runTicket_parentId);
        }

        if(idcHisTicket.getProcInstId() != null) {
            byte[] runTicket_key_procInstId= redisTemplate.getStringSerializer().serialize("procInstId");
            byte[] runTicket_procInstId= redisTemplate.getStringSerializer().serialize(String.valueOf(idcHisTicket.getProcInstId()));
            map.put(runTicket_key_procInstId, runTicket_procInstId);
        }
        if(idcHisTicket.getCategory() != null) {
            byte[] runTicket_key_category= redisTemplate.getStringSerializer().serialize("category");
            byte[] runTicket_category= redisTemplate.getStringSerializer().serialize(String.valueOf(idcHisTicket.getCategory()));
            map.put(runTicket_key_category, runTicket_category);
        }

        if(idcHisTicket.getApprovalStatus() != null) {
            byte[] runTicket_key_approvalStatus= redisTemplate.getStringSerializer().serialize("approvalStatus");
            byte[] runTicket_approvalStatus= redisTemplate.getStringSerializer().serialize(String.valueOf(idcHisTicket.getApprovalStatus()));
            map.put(runTicket_key_approvalStatus, runTicket_approvalStatus);
        }

        if(idcHisTicket.getRemark() != null) {
            byte[] runTicket_key_remark= redisTemplate.getStringSerializer().serialize("remark");
            byte[] runTicket_remark= redisTemplate.getStringSerializer().serialize(String.valueOf(idcHisTicket.getRemark()));
            map.put(runTicket_key_remark, runTicket_remark);
        }
        if(idcHisTicket.getCreateTime() != null) {
            //日期格式化
            Date create_time_ = idcHisTicket.getCreateTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date_ = null;
            try {
                date_ = sdf.format(create_time_);
            } catch (Exception e) {
                log.error("日期转换出错啦。。。。。");
            }
            if(date_ != null){
                byte[] runTicket_key_createTime= redisTemplate.getStringSerializer().serialize("createTime");
                byte[] runTicket_createTime= redisTemplate.getStringSerializer().serialize(date_);
                map.put(runTicket_key_createTime, runTicket_createTime);
            }
        }
        if(idcHisTicket.getInitId() != null) {
            byte[] runTicket_key_initId= redisTemplate.getStringSerializer().serialize("initId");
            byte[] runTicket_initId= redisTemplate.getStringSerializer().serialize(String.valueOf(idcHisTicket.getInitId()));
            map.put(runTicket_key_initId, runTicket_initId);
        }
        if(idcHisTicket.getTaskName() != null) {
            byte[] runTicket_key_taskName= redisTemplate.getStringSerializer().serialize("taskName");
            byte[] runTicket_taskName= redisTemplate.getStringSerializer().serialize(String.valueOf(idcHisTicket.getTaskName()));
            map.put(runTicket_key_taskName, runTicket_taskName);
        }
        if(idcHisTicket.getTaskId() != null) {
            byte[] runTicket_key_taskId= redisTemplate.getStringSerializer().serialize("taskId");
            byte[] runTicket_taskId= redisTemplate.getStringSerializer().serialize(String.valueOf(idcHisTicket.getTaskId()));
            map.put(runTicket_key_taskId, runTicket_taskId);
        }
        if(idcHisTicket.getFormKey() != null) {
            byte[] runTicket_key_formKey= redisTemplate.getStringSerializer().serialize("formKey");
            byte[] runTicket_formKey= redisTemplate.getStringSerializer().serialize(String.valueOf(idcHisTicket.getFormKey()));
            map.put(runTicket_key_formKey, runTicket_formKey);
        }
        if(idcHisTicket.getProcDefId() != null) {
            byte[] runTicket_key_ProcDefId= redisTemplate.getStringSerializer().serialize("procDefId");
            byte[] runTicket_ProcDefId= redisTemplate.getStringSerializer().serialize(String.valueOf(idcHisTicket.getProcDefId()));
            map.put(runTicket_key_ProcDefId, runTicket_ProcDefId);
        }
        if(idcHisTicket.getProcessdefinitonkey() != null) {
            byte[] runTicket_key_processdefinitonkey= redisTemplate.getStringSerializer().serialize("processdefinitonkey");
            byte[] runTicket_processdefinitonkey= redisTemplate.getStringSerializer().serialize(String.valueOf(idcHisTicket.getProcessdefinitonkey()));
            map.put(runTicket_key_processdefinitonkey, runTicket_processdefinitonkey);
        }

        if(idcHisTicket.getCurTaskName() != null) {
            byte[] runTicket_key_curTaskName= redisTemplate.getStringSerializer().serialize("curTaskName");
            byte[] runTicket_curTaskName= redisTemplate.getStringSerializer().serialize(String.valueOf(idcHisTicket.getCurTaskName()));
            map.put(runTicket_key_curTaskName, runTicket_curTaskName);
        }
        if(idcHisTicket.getProcticketStatus() != null) {
            byte[] runTicket_key_procticketStatus= redisTemplate.getStringSerializer().serialize("procticketStatus");
            byte[] runTicket_procticketStatus= redisTemplate.getStringSerializer().serialize(String.valueOf(idcHisTicket.getProcticketStatus()));
            map.put(runTicket_key_procticketStatus, runTicket_procticketStatus);
        }

        if(idcHisTicket.getApplyId() != null) {
            byte[] runTicket_key_applyId= redisTemplate.getStringSerializer().serialize("applyId");
            byte[] runTicket_applyId= redisTemplate.getStringSerializer().serialize(String.valueOf(idcHisTicket.getApplyId()));
            map.put(runTicket_key_applyId, runTicket_applyId);
        }
        if(idcHisTicket.getApplyName() != null) {
            byte[] runTicket_key_applyName= redisTemplate.getStringSerializer().serialize("applyName");
            byte[] runTicket_applyName= redisTemplate.getStringSerializer().serialize(String.valueOf(idcHisTicket.getApplyName()));
            map.put(runTicket_key_applyName, runTicket_applyName);
        }

        if(idcHisTicket.getCandidate() != null) {
            byte[] runTicket_key_candidate= redisTemplate.getStringSerializer().serialize("candidate");
            byte[] runTicket_candidate= redisTemplate.getStringSerializer().serialize(String.valueOf(idcHisTicket.getCandidate()));
            map.put(runTicket_key_candidate, runTicket_candidate);
        }
        if(idcHisTicket.getGroupId() != null) {
            byte[] runTicket_key_groupId= redisTemplate.getStringSerializer().serialize("groupId");
            byte[] runTicket_groupId= redisTemplate.getStringSerializer().serialize(String.valueOf(idcHisTicket.getGroupId()));
            map.put(runTicket_key_groupId, runTicket_groupId);
        }
        if(idcHisTicket.getCategorylinked() != null) {
            byte[] runTicket_key_categorylinked= redisTemplate.getStringSerializer().serialize("categorylinked");
            byte[] runTicket_categorylinked= redisTemplate.getStringSerializer().serialize(String.valueOf(idcHisTicket.getCategorylinked()));
            map.put(runTicket_key_categorylinked, runTicket_categorylinked);
        }
        if(idcHisTicket.getTitle() != null) {
            byte[] runTicket_key_title= redisTemplate.getStringSerializer().serialize("title");
            byte[] runTicket_title= redisTemplate.getStringSerializer().serialize(String.valueOf(idcHisTicket.getTitle()));
            map.put(runTicket_key_title, runTicket_title);
        }
        if(idcHisTicket.getFirstnode() != null) {
            byte[] runTicket_key_getFirstnode= redisTemplate.getStringSerializer().serialize("firstnode");
            byte[] runTicket_getFirstnode= redisTemplate.getStringSerializer().serialize(String.valueOf(idcHisTicket.getFirstnode()));
            map.put(runTicket_key_getFirstnode, runTicket_getFirstnode);
        }
        if(idcHisTicket.getIsProcessEnd() != null) {
            byte[] runTicket_key_isProcessEnd= redisTemplate.getStringSerializer().serialize("isProcessEnd");
            byte[] runTicket_isProcessEnd= redisTemplate.getStringSerializer().serialize(String.valueOf(idcHisTicket.getIsProcessEnd()));
            map.put(runTicket_key_isProcessEnd, runTicket_isProcessEnd);
        }
        if(idcHisTicket.getIsRejectTicket() != null) {
            byte[] runTicket_key_getIsRejectTicket= redisTemplate.getStringSerializer().serialize("isRejectTicket");
            byte[] runTicket_getIsRejectTicket= redisTemplate.getStringSerializer().serialize(String.valueOf(idcHisTicket.getIsRejectTicket()));
            map.put(runTicket_key_getIsRejectTicket, runTicket_getIsRejectTicket);
        }
        if(idcHisTicket.getRejectComment() != null) {
            byte[] runTicket_key_getRejectComment= redisTemplate.getStringSerializer().serialize("rejectComment");
            byte[] runTicket_getRejectComment= redisTemplate.getStringSerializer().serialize(String.valueOf(idcHisTicket.getRejectComment()));
            map.put(runTicket_key_getRejectComment, runTicket_getRejectComment);
        }
        if(idcHisTicket.getIsForceRefuse() != null) {
            byte[] runTicket_key_isForceRefuse= redisTemplate.getStringSerializer().serialize("isForceRefuse");
            byte[] runTicket_isForceRefuse= redisTemplate.getStringSerializer().serialize(String.valueOf(idcHisTicket.getIsForceRefuse()));
            map.put(runTicket_key_isForceRefuse, runTicket_isForceRefuse);
        }
        if(idcHisTicket.getApplyRegions() != null) {
            byte[] runTicket_key_applyRegions= redisTemplate.getStringSerializer().serialize("applyRegions");
            byte[] runTicket_applyRegions= redisTemplate.getStringSerializer().serialize(String.valueOf(idcHisTicket.getApplyRegions()));
            map.put(runTicket_key_applyRegions, runTicket_applyRegions);
        }
        if(idcHisTicket.getBusname() != null) {
            byte[] runTicket_key_busname= redisTemplate.getStringSerializer().serialize("busname");
            byte[] runTicket_busname= redisTemplate.getStringSerializer().serialize(String.valueOf(idcHisTicket.getBusname()));
            map.put(runTicket_key_busname, runTicket_busname);
        }
        if(idcHisTicket.getUserNames() != null) {
            byte[] runTicket_key_userNames= redisTemplate.getStringSerializer().serialize("userNames");
            byte[] runTicket_userNames= redisTemplate.getStringSerializer().serialize(String.valueOf(idcHisTicket.getUserNames()));
            map.put(runTicket_key_userNames, runTicket_userNames);
        }
        if(idcHisTicket.getUser_ids() != null) {
            byte[] runTicket_key_getUser_ids= redisTemplate.getStringSerializer().serialize("user_ids");
            byte[] runTicket_getUser_ids= redisTemplate.getStringSerializer().serialize(String.valueOf(idcHisTicket.getUser_ids()));
            map.put(runTicket_key_getUser_ids, runTicket_getUser_ids);
        }
        if(idcHisTicket.getUserRegions() != null) {
            byte[] runTicket_key_userRegions= redisTemplate.getStringSerializer().serialize("userRegions");
            byte[] runTicket_userRegions= redisTemplate.getStringSerializer().serialize(String.valueOf(idcHisTicket.getUserRegions()));
            map.put(runTicket_key_userRegions, runTicket_userRegions);
        }
        if(idcHisTicket.getApplyedIds() != null) {
            byte[] runTicket_key_applyedIds= redisTemplate.getStringSerializer().serialize("applyedIds");
            byte[] runTicket_applyedIdss= redisTemplate.getStringSerializer().serialize(String.valueOf(idcHisTicket.getApplyedIds()));
            map.put(runTicket_key_applyedIds, runTicket_applyedIdss);
        }
        if(idcHisTicket.getIsRubbish() != null) {
            byte[] runTicket_key_isRubbish= redisTemplate.getStringSerializer().serialize("isRubbish");
            byte[] runTicket_isRubbish= redisTemplate.getStringSerializer().serialize(String.valueOf(idcHisTicket.getIsRubbish()));
            map.put(runTicket_key_isRubbish, runTicket_isRubbish);
        }
        return map;
    }*/
    //工单序列号赋值的情况
   /* public Map stringSerializerToMap(IdcRunTicket idcRunTicket){
        Map map = new HashMap();
        byte[] runTicket_key_ticketInstId = redisTemplate.getStringSerializer().serialize("ticketInstId");
        byte[] runTicket_ticketInstId = redisTemplate.getStringSerializer().serialize(String.valueOf(idcRunTicket.getTicketInstId()));
        map.put(runTicket_key_ticketInstId, runTicket_ticketInstId);

        if(idcRunTicket.getCustomerId() != null){
            byte[] runTicket_key_customerId = redisTemplate.getStringSerializer().serialize("customerId");
            byte[] runTicket_customerId = redisTemplate.getStringSerializer().serialize(String.valueOf(idcRunTicket.getCustomerId()));
            map.put(runTicket_key_customerId, runTicket_customerId);
        }

        if(idcRunTicket.getCustomerName() != null){
            byte[] runTicket_key_customerName = redisTemplate.getStringSerializer().serialize("customerName");
            byte[] runTicket_customerName = redisTemplate.getStringSerializer().serialize(String.valueOf(idcRunTicket.getCustomerName()));
            map.put(runTicket_key_customerName, runTicket_customerName);
        }

        if(idcRunTicket.getSerialNumber() != null) {
            byte[] runTicket_key_serialNumber = redisTemplate.getStringSerializer().serialize("serialNumber");
            byte[] runTicket_serialNumber = redisTemplate.getStringSerializer().serialize(String.valueOf(idcRunTicket.getSerialNumber()));
            map.put(runTicket_key_serialNumber, runTicket_serialNumber);
        }

        if(idcRunTicket.getCustomerAttr() != null) {
            byte[] runTicket_key_customerAttr = redisTemplate.getStringSerializer().serialize("customerAttr");
            byte[] runTicket_customerAttr = redisTemplate.getStringSerializer().serialize(String.valueOf(idcRunTicket.getCustomerAttr()));
            map.put(runTicket_key_customerAttr, runTicket_customerAttr);
        }


        if(idcRunTicket.getContactMobile() != null) {
            byte[] runTicket_key_contactMobile = redisTemplate.getStringSerializer().serialize("contactMobile");
            byte[] runTicket_contactMobile = redisTemplate.getStringSerializer().serialize(String.valueOf(idcRunTicket.getContactMobile()));
            map.put(runTicket_key_contactMobile, runTicket_contactMobile);
        }
        if(idcRunTicket.getProdInstId() != null) {
            byte[] runTicket_key_ProdInstId = redisTemplate.getStringSerializer().serialize("prodInstId");
            byte[] runTicket_ProdInstId = redisTemplate.getStringSerializer().serialize(String.valueOf(idcRunTicket.getProdInstId()));
            map.put(runTicket_key_ProdInstId, runTicket_ProdInstId);
        }
        if(idcRunTicket.getTicketCategory() != null) {
            byte[] runTicket_key_ticketcategory = redisTemplate.getStringSerializer().serialize("ticketcategory");
            byte[] runTicket_ticketcategory = redisTemplate.getStringSerializer().serialize(String.valueOf(idcRunTicket.getTicketCategory()));
            map.put(runTicket_key_ticketcategory, runTicket_ticketcategory);
        }

        if(idcRunTicket.getId() != null) {
            byte[] runTicket_key_id= redisTemplate.getStringSerializer().serialize("id");
            byte[] runTicket_id = redisTemplate.getStringSerializer().serialize(String.valueOf(idcRunTicket.getId()));
            map.put(runTicket_key_id, runTicket_id);
        }

        if(idcRunTicket.getParentId() != null) {
            byte[] runTicket_key_parentId= redisTemplate.getStringSerializer().serialize("parentId");
            byte[] runTicket_parentId= redisTemplate.getStringSerializer().serialize(String.valueOf(idcRunTicket.getParentId()));
            map.put(runTicket_key_parentId, runTicket_parentId);
        }

        if(idcRunTicket.getProcInstId() != null) {
            byte[] runTicket_key_procInstId= redisTemplate.getStringSerializer().serialize("procInstId");
            byte[] runTicket_procInstId= redisTemplate.getStringSerializer().serialize(String.valueOf(idcRunTicket.getProcInstId()));
            map.put(runTicket_key_procInstId, runTicket_procInstId);
        }
        if(idcRunTicket.getTicketCategory() != null) {
            byte[] runTicket_key_category= redisTemplate.getStringSerializer().serialize("category");
            byte[] runTicket_category= redisTemplate.getStringSerializer().serialize(String.valueOf(idcRunTicket.getTicketCategory()));
            map.put(runTicket_key_category, runTicket_category);
        }

        if(idcRunTicket.getApprovalStatus() != null) {
            byte[] runTicket_key_approvalStatus= redisTemplate.getStringSerializer().serialize("approvalStatus");
            byte[] runTicket_approvalStatus= redisTemplate.getStringSerializer().serialize(String.valueOf(idcRunTicket.getApprovalStatus()));
            map.put(runTicket_key_approvalStatus, runTicket_approvalStatus);
        }

        if(idcRunTicket.getRemark() != null) {
            byte[] runTicket_key_remark= redisTemplate.getStringSerializer().serialize("remark");
            byte[] runTicket_remark= redisTemplate.getStringSerializer().serialize(String.valueOf(idcRunTicket.getRemark()));
            map.put(runTicket_key_remark, runTicket_remark);
        }
        if(idcRunTicket.getCreateTime() != null) {
            //日期格式化
            Date create_time_ = idcRunTicket.getCreateTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date_ = null;
            try {
                date_ = sdf.format(create_time_);
            } catch (Exception e) {
                log.error("日期转换出错啦。。。。。");
            }
            if(date_ != null){
                byte[] runTicket_key_createTime= redisTemplate.getStringSerializer().serialize("createTime");
                byte[] runTicket_createTime= redisTemplate.getStringSerializer().serialize(date_);
                map.put(runTicket_key_createTime, runTicket_createTime);
            }
        }
        if(idcRunTicket.getInitId() != null) {
            byte[] runTicket_key_initId= redisTemplate.getStringSerializer().serialize("initId");
            byte[] runTicket_initId= redisTemplate.getStringSerializer().serialize(String.valueOf(idcRunTicket.getInitId()));
            map.put(runTicket_key_initId, runTicket_initId);
        }
        if(idcRunTicket.getTaskName() != null) {
            byte[] runTicket_key_taskName= redisTemplate.getStringSerializer().serialize("taskName");
            byte[] runTicket_taskName= redisTemplate.getStringSerializer().serialize(String.valueOf(idcRunTicket.getTaskName()));
            map.put(runTicket_key_taskName, runTicket_taskName);
        }
        if(idcRunTicket.getTaskId() != null) {
            byte[] runTicket_key_taskId= redisTemplate.getStringSerializer().serialize("taskId");
            byte[] runTicket_taskId= redisTemplate.getStringSerializer().serialize(String.valueOf(idcRunTicket.getTaskId()));
            map.put(runTicket_key_taskId, runTicket_taskId);
        }
        if(idcRunTicket.getFormKey() != null) {
            byte[] runTicket_key_formKey= redisTemplate.getStringSerializer().serialize("formKey");
            byte[] runTicket_formKey= redisTemplate.getStringSerializer().serialize(String.valueOf(idcRunTicket.getFormKey()));
            map.put(runTicket_key_formKey, runTicket_formKey);
        }
        if(idcRunTicket.getProcDefId() != null) {
            byte[] runTicket_key_ProcDefId= redisTemplate.getStringSerializer().serialize("procDefId");
            byte[] runTicket_ProcDefId= redisTemplate.getStringSerializer().serialize(String.valueOf(idcRunTicket.getProcDefId()));
            map.put(runTicket_key_ProcDefId, runTicket_ProcDefId);
        }
        if(idcRunTicket.getProcessdefinitonkey() != null) {
            byte[] runTicket_key_processdefinitonkey= redisTemplate.getStringSerializer().serialize("processdefinitonkey");
            byte[] runTicket_processdefinitonkey= redisTemplate.getStringSerializer().serialize(String.valueOf(idcRunTicket.getProcessdefinitonkey()));
            map.put(runTicket_key_processdefinitonkey, runTicket_processdefinitonkey);
        }

        if(idcRunTicket.getProcticketStatus() != null) {
            byte[] runTicket_key_procticketStatus= redisTemplate.getStringSerializer().serialize("procticketStatus");
            byte[] runTicket_procticketStatus= redisTemplate.getStringSerializer().serialize(String.valueOf(idcRunTicket.getProcticketStatus()));
            map.put(runTicket_key_procticketStatus, runTicket_procticketStatus);
        }

        if(idcRunTicket.getApplyId() != null) {
            byte[] runTicket_key_applyId= redisTemplate.getStringSerializer().serialize("applyId");
            byte[] runTicket_applyId= redisTemplate.getStringSerializer().serialize(String.valueOf(idcRunTicket.getApplyId()));
            map.put(runTicket_key_applyId, runTicket_applyId);
        }
        if(idcRunTicket.getApplyName() != null) {
            byte[] runTicket_key_applyName= redisTemplate.getStringSerializer().serialize("applyName");
            byte[] runTicket_applyName= redisTemplate.getStringSerializer().serialize(String.valueOf(idcRunTicket.getApplyName()));
            map.put(runTicket_key_applyName, runTicket_applyName);
        }

        if(idcRunTicket.getCandidate() != null) {
            byte[] runTicket_key_candidate= redisTemplate.getStringSerializer().serialize("candidate");
            byte[] runTicket_candidate= redisTemplate.getStringSerializer().serialize(String.valueOf(idcRunTicket.getCandidate()));
            map.put(runTicket_key_candidate, runTicket_candidate);
        }
        if(idcRunTicket.getGroupId() != null) {
            byte[] runTicket_key_groupId= redisTemplate.getStringSerializer().serialize("groupId");
            byte[] runTicket_groupId= redisTemplate.getStringSerializer().serialize(String.valueOf(idcRunTicket.getGroupId()));
            map.put(runTicket_key_groupId, runTicket_groupId);
        }
        if(idcRunTicket.getCategorylinked() != null) {
            byte[] runTicket_key_categorylinked= redisTemplate.getStringSerializer().serialize("categorylinked");
            byte[] runTicket_categorylinked= redisTemplate.getStringSerializer().serialize(String.valueOf(idcRunTicket.getCategorylinked()));
            map.put(runTicket_key_categorylinked, runTicket_categorylinked);
        }
        if(idcRunTicket.getTitle() != null) {
            byte[] runTicket_key_title= redisTemplate.getStringSerializer().serialize("title");
            byte[] runTicket_title= redisTemplate.getStringSerializer().serialize(String.valueOf(idcRunTicket.getTitle()));
            map.put(runTicket_key_title, runTicket_title);
        }
        if(idcRunTicket.getFirstnode() != null) {
            byte[] runTicket_key_getFirstnode= redisTemplate.getStringSerializer().serialize("firstnode");
            byte[] runTicket_getFirstnode= redisTemplate.getStringSerializer().serialize(String.valueOf(idcRunTicket.getFirstnode()));
            map.put(runTicket_key_getFirstnode, runTicket_getFirstnode);
        }
        if(idcRunTicket.getIsProcessEnd() != null) {
            byte[] runTicket_key_isProcessEnd= redisTemplate.getStringSerializer().serialize("isProcessEnd");
            byte[] runTicket_isProcessEnd= redisTemplate.getStringSerializer().serialize(String.valueOf(idcRunTicket.getIsProcessEnd()));
            map.put(runTicket_key_isProcessEnd, runTicket_isProcessEnd);
        }
        if(idcRunTicket.getIsRejectTicket() != null) {
            byte[] runTicket_key_getIsRejectTicket= redisTemplate.getStringSerializer().serialize("isRejectTicket");
            byte[] runTicket_getIsRejectTicket= redisTemplate.getStringSerializer().serialize(String.valueOf(idcRunTicket.getIsRejectTicket()));
            map.put(runTicket_key_getIsRejectTicket, runTicket_getIsRejectTicket);
        }
        if(idcRunTicket.getRejectComment() != null) {
            byte[] runTicket_key_getRejectComment= redisTemplate.getStringSerializer().serialize("rejectComment");
            byte[] runTicket_getRejectComment= redisTemplate.getStringSerializer().serialize(String.valueOf(idcRunTicket.getRejectComment()));
            map.put(runTicket_key_getRejectComment, runTicket_getRejectComment);
        }
        if(idcRunTicket.getIsForceRefuse() != null) {
            byte[] runTicket_key_isForceRefuse= redisTemplate.getStringSerializer().serialize("isForceRefuse");
            byte[] runTicket_isForceRefuse= redisTemplate.getStringSerializer().serialize(String.valueOf(idcRunTicket.getIsForceRefuse()));
            map.put(runTicket_key_isForceRefuse, runTicket_isForceRefuse);
        }
        if(idcRunTicket.getApplyRegions() != null) {
            byte[] runTicket_key_applyRegions= redisTemplate.getStringSerializer().serialize("applyRegions");
            byte[] runTicket_applyRegions= redisTemplate.getStringSerializer().serialize(String.valueOf(idcRunTicket.getApplyRegions()));
            map.put(runTicket_key_applyRegions, runTicket_applyRegions);
        }
        if(idcRunTicket.getBusname() != null) {
            byte[] runTicket_key_busname= redisTemplate.getStringSerializer().serialize("busname");
            byte[] runTicket_busname= redisTemplate.getStringSerializer().serialize(String.valueOf(idcRunTicket.getBusname()));
            map.put(runTicket_key_busname, runTicket_busname);
        }
        if(idcRunTicket.getUserNames() != null) {
            byte[] runTicket_key_userNames= redisTemplate.getStringSerializer().serialize("userNames");
            byte[] runTicket_userNames= redisTemplate.getStringSerializer().serialize(String.valueOf(idcRunTicket.getUserNames()));
            map.put(runTicket_key_userNames, runTicket_userNames);
        }
        if(idcRunTicket.getUser_ids() != null) {
            byte[] runTicket_key_getUser_ids= redisTemplate.getStringSerializer().serialize("user_ids");
            byte[] runTicket_getUser_ids= redisTemplate.getStringSerializer().serialize(String.valueOf(idcRunTicket.getUser_ids()));
            map.put(runTicket_key_getUser_ids, runTicket_getUser_ids);
        }
        if(idcRunTicket.getUserRegions() != null) {
            byte[] runTicket_key_userRegions= redisTemplate.getStringSerializer().serialize("userRegions");
            byte[] runTicket_userRegions= redisTemplate.getStringSerializer().serialize(String.valueOf(idcRunTicket.getUserRegions()));
            map.put(runTicket_key_userRegions, runTicket_userRegions);
        }
        return map;
    }*/

    @Override
    public void userHisTicket(RedisSetCommands redisSetCommands, byte[] idcRunTicketKeyByte, List<SysUserinfo> sysUserinfoList, IdcHisTicket idcHisTicket) throws JbpmException {
        /*for(int rc = 0; rc < sysUserinfoList.size(); rc++){
            SysUserinfo sysUserinfo = sysUserinfoList.get(rc);
            Integer reID= sysUserinfo.getId();
            String userIdsStr = ","+idcHisTicket.getUser_ids()+",";
            if(userIdsStr.contains(","+reID+",")){
                *//*是否是政企*//*
                String ticketCategory = String.valueOf(idcHisTicket.getTicketCategory());
                String GDDL = "政企";//工单大类，政企还是自有
                if(ticketCategory != null && "0".equals(ticketCategory)){
                    GDDL = "自有";
                }
                //工单级别
                String category = idcHisTicket.getCategory();
                String ticketjb_tmp = DevContext.getTicketCategory(null,category);
                byte[] idcReCustomerKeyValByte = redisTemplate.getStringSerializer().serialize(
                        DevContext.JBPM_SYS_USERINFO_FORHIS+":USER_" + reID+"_"+GDDL+"_"+ticketjb_tmp+":"+
                                idcHisTicket.getSerialNumber()
                );
                //key是USER_[ID]_政企业务_预受理_代办工单
                redisSetCommands.sAdd(idcReCustomerKeyValByte, idcRunTicketKeyByte);
            }
        }*/
    }

    @Override
    public void userTicket(RedisSetCommands redisSetCommands, byte[] idcRunTicketKeyByte, List<SysUserinfo> sysUserinfoList, IdcRunTicket idcRunTicket) throws JbpmException {
        /*for(int rc = 0; rc < sysUserinfoList.size(); rc++){
            SysUserinfo sysUserinfo = sysUserinfoList.get(rc);
            Integer reID= sysUserinfo.getId();
            String userIdsStr = ","+idcRunTicket.getUser_ids()+",";
            if(userIdsStr.contains(","+reID+",")){
                *//*是否是政企*//*
                 String ticketCategory = idcRunTicket.getTicketCategory();
                String GDDL = "政企";//工单大类，政企还是自有
                if(ticketCategory != null && "0".equals(ticketCategory)){
                    GDDL = "自有";
                }
                //工单级别
                String category = idcRunTicket.getTicketCategory();
                String ticketjb_tmp = DevContext.getTicketCategory(null,category);
                byte[] idcReCustomerKeyValByte = redisTemplate.getStringSerializer().serialize(
                        DevContext.JBPM_SYS_USERINFO+":USER_" + reID+"_"+GDDL+"_"+ticketjb_tmp+":"+
                                idcRunTicket.getSerialNumber()
                );
                //key是USER_[ID]_政企业务_预受理_代办工单
                redisSetCommands.sAdd(idcReCustomerKeyValByte, idcRunTicketKeyByte);
            }
        }*/
    }
}