package com.idc.mapper;

import com.idc.model.IdcReCustomer;
import org.apache.ibatis.annotations.Select;
import system.data.page.PageBean;
import system.data.supper.mapper.SuperMapper;

import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_RE_CUSTOMER:客户类型信息<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcReCustomerMapper extends SuperMapper<IdcReCustomer, Long>{
    //显示部分字段信息
    /*public List<IdcReCustomer> queryGridFilterListPage(PageBean<IdcReCustomer> page);*/
    //显示部分字段信息
    List<IdcReCustomer> queryMngGridFilterListPage(PageBean<IdcReCustomer> page);
    /**
     * 通过ID获取相应的实体类
     * @param id
     * @return
     */
    IdcReCustomer getModelByReCustomerId(Long id);

    @Select("select count('a') from idc_re_customer t where t.alias=#{newAlias}")
    Long verifyAlias(String newAlias);
    /**
     * 合体方法
     * @param idcReCustomer
     * @throws Exception
     */
    void mergeInto(IdcReCustomer idcReCustomer) throws Exception;
    /**
     * 通过ID删除的实体类
     * @param id
     * @throws Exception
     */
    void deleteRecordById(Long id) throws Exception;

    /**
     * 获取移动客户
     * @return
     */
    IdcReCustomer getModelByChinaData();

    List<Map<String,Object>> queryAllCustomer();

    //通过工单id查询客户的所有信息
    List<Map<String,Object>> queryCustomerByTicketInstId(List pamas);
    String getIdcReCustomerNameById(Long customerId);

    IdcReCustomer getFilterModelByReCustomerId(Long id);
    List<IdcReCustomer> getAllCustomer();

}

 