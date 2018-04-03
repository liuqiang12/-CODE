package com.idc.service;


import com.idc.mapper.IdcLocationCountMapper;
import com.idc.model.IdcLocationCount;
import com.idc.model.TicketData;
import com.idc.model.count.TrendBean;
import com.idc.springtool.SpringBeanUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.data.page.PageBean;
import system.data.supper.service.impl.SuperServiceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by mylove on 2017/8/8.
 */
@Service
public class ResourceViewServiceImpl extends SuperServiceImpl<IdcLocationCount, Long> implements ResourceViewService {
    protected Logger logger = Logger.getLogger(this.getClass().getName());
    @Autowired
    private IdcLocationCountMapper idcLocationCountMapper;

    @Override
    public IdcLocationCount getLocalCount(Integer localId) {
        return idcLocationCountMapper.getModelById(localId.longValue());
    }

    @Override
    public void reloadCount(Integer localId) {

    }

    @Override
    public void reloadCount(TicketData ticketLog) {
    }

    /*
    * 获取各种业务分类好评数
    *  type 0自有业务， 1政企业务
    * */
    @Override
    public Map<String, Long> getGoodRate(int type) {
        //此结果分为政企业务和自有业务，他们分别的每个流程的好评数量
        List<Map<String, Object>> goodEvaluate = idcLocationCountMapper.getGoodEvaluate();

        //政企业务的好评数量
        Map<String, Long> governmentMap=new HashMap();

        //自有业务的好评数量
        Map<String, Long> selfMap=new HashMap();

        for (Map<String,Object> resultMap : goodEvaluate){
            //governmentBusiness 0代表自有业务，1代表政企业务
            Long governmentBusiness=Long.valueOf(resultMap.get("TICKETCATEGORY").toString());
            //CATEGORY 工单类型100:预勘 200 开通 400停机 500复通  600下线 700变更开通 800临时测试 900业务变更
            Long category=Long.valueOf(resultMap.get("CATEGORY").toString());
            //goodCount好评的数量
            //Long goodCount=Long.valueOf(resultMap.get("SATISFACTION").toString());

            if(governmentBusiness == 1){
                //下面是政企业务各个流程5星好评数量
                if(category ==100){
                    governmentMap.put("government100",Long.valueOf(resultMap.get("SATISFACTIONCOUNT").toString()));
                }else if(category ==200){
                    governmentMap.put("government200",Long.valueOf(resultMap.get("SATISFACTIONCOUNT").toString()));
                }else if(category ==400){
                    governmentMap.put("government400",Long.valueOf(resultMap.get("SATISFACTIONCOUNT").toString()));
                }else if(category ==500){
                    governmentMap.put("government500",Long.valueOf(resultMap.get("SATISFACTIONCOUNT").toString()));
                }else if(category ==600){
                    governmentMap.put("government600",Long.valueOf(resultMap.get("SATISFACTIONCOUNT").toString()));
                }else if(category ==700){
                    governmentMap.put("government700",Long.valueOf(resultMap.get("SATISFACTIONCOUNT").toString()));
                }else if(category ==800){
                    governmentMap.put("government800",Long.valueOf(resultMap.get("SATISFACTIONCOUNT").toString()));
                }else if(category ==900){
                    governmentMap.put("government900",Long.valueOf(resultMap.get("SATISFACTIONCOUNT").toString()));
                }
            }else if(governmentBusiness == 0){
                //工单类型100:预勘 200 开通 400停机 500复通  600下线 700变更开通 800临时测试 900业务变更
                //下面是自有业务各个流程5星好评数量
                if(category ==100){
                    selfMap.put("self100",Long.valueOf(resultMap.get("SATISFACTIONCOUNT").toString()));
                }else if(category ==200){
                    selfMap.put("self200",Long.valueOf(resultMap.get("SATISFACTIONCOUNT").toString()));
                }else if(category ==400){
                    selfMap.put("self400",Long.valueOf(resultMap.get("SATISFACTIONCOUNT").toString()));
                }else if(category ==500){
                    selfMap.put("self500",Long.valueOf(resultMap.get("SATISFACTIONCOUNT").toString()));
                }else if(category ==600){
                    selfMap.put("self600",Long.valueOf(resultMap.get("SATISFACTIONCOUNT").toString()));
                }else if(category ==700){
                    selfMap.put("self700",Long.valueOf(resultMap.get("SATISFACTIONCOUNT").toString()));
                }else if(category ==800){
                    selfMap.put("self800",Long.valueOf(resultMap.get("SATISFACTIONCOUNT").toString()));
                }else if(category ==900){
                    selfMap.put("self900",Long.valueOf(resultMap.get("SATISFACTIONCOUNT").toString()));
                }
            }

        }
        if(type ==1){
            return governmentMap;
        }else{
            return selfMap;

        }
    }

    /***
     *待处理工单
     * @return
     */
    @Override
    public List<Map<String, Object>> getTicketByWait() {
        //actJBPMMapper.jbpmRunTicketTaskListPage(new PageBean<>());
       /* actJBPMService.jbpmRunTicketTaskListPage(new PageBean<>(),new HashedMap());*/
        List<Map<String, Object>> untreatedTicket = idcLocationCountMapper.getUntreatedTicket();
        return untreatedTicket;
    }


    /*得到是域名客户获取客户占比
     * @return  key:有域名客户，其他客户
     * */
    @Override
    public Map<String, Long> getHavDomainUser() {
        Integer dns = idcLocationCountMapper.getDNS();
        Integer notDNS = idcLocationCountMapper.getNotDNS();
        //int a=dns/notDNS *100;
        Map map = new HashMap();
        map.put("有域名客户",Long.parseLong(dns.toString()));
        map.put("无域名客户",Long.parseLong(notDNS.toString()));
        return map;
    }
    /****
     * 返回每月入退网客户数可当前客户数 返回数据参照测试数据
     * type cussum,outsum,insum
     * @return
     */
    @Override
    public List<Map<String, Object>> getUserAddTread() {
        List<TrendBean> list = new ArrayList<>();

        //得到每个月的新增开通工单
        List<Map<String, Object>> onlineTicketList=idcLocationCountMapper.onlineTicketList();

        List<Map<String, Object>> result= new ArrayList<>();

        for (Map<String, Object> maps: onlineTicketList) {
            Map<String, Object> element=new HashMap<>();

            //添加月份
            element.put("month",maps.get("DATE_"));

            if(maps.get("KT") != null && maps.get("KT") != "" ){
                //添加开通工单的数量
                element.put("insum",maps.get("KT"));
            }

            if(maps.get("XX") != null && maps.get("XX") != "" ){
                //添加下线工单的数量
                element.put("outsum",maps.get("XX"));
            }
            result.add(element);
        }




        //以下测试数据
       /* SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String [] times  = {"2017-2","2017-3","2017-4","2017-5","2017-7","2017-6","2017-8"};
        String [] types  = {"cussum","outsum","insum"};
        for (String time : times) {
            try {
                Date parse = sdf.parse(time);
                for (String type : types) {
                    TrendBean t = new TrendBean();
                    t.setTime(parse);
                    t.setType(type);
                    t.setValue(Math.round(Math.random()*100));
                    list.add(t);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }*/
        return result;
    }



    @Override
    public void reloadCount(Long localId, Object ticketData) {
        Map<String, ReLoadInterface> result = SpringBeanUtil.getApplicationContext().getBeansOfType(ReLoadInterface.class);
        IdcLocationCount count = new IdcLocationCount();
        for (String s : result.keySet()) {
            try {
                result.get(s).reloadCount(localId, count);
            } catch (Exception e) {
                logger.error("",e);
            }
        }
        count.setId(localId);
        try {
            logger.debug("更新统计信息："+count);
            idcLocationCountMapper.updateByObject(count);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }


    @Override
    public void reloadCountByColumn(Integer localId, IdcLocationCount idcLocationCount) {
        idcLocationCount.setId(Long.parseLong(localId.toString()));
    }

    @Override
    public long getRoomCount(Integer localId) {
        return idcLocationCountMapper.getRoomCount(localId);
    }

    @Override
    public long getBuildCount(Integer localId) {
        return idcLocationCountMapper.getBuildCount(localId);
    }

    @Override
    public long getOdfCount(Integer localId) {
        return idcLocationCountMapper.getOdfCount(localId);
    }

    @Override
    public long getPdfCount(Integer localId) {
        return idcLocationCountMapper.getPdfCount(localId);
    }


    @Override
    public void reLoadCountByColumn(Integer locationId, String type) throws Exception {
        Map<String, ReLoadInterface> result = SpringBeanUtil.getApplicationContext().getBeansOfType(ReLoadInterface.class);
        IdcLocationCount count = new IdcLocationCount();
        if (type != null && !"".equals(type)) {
            ReLoadInterface reLoadInterface = result.get(type);
            if (reLoadInterface != null) {
                reLoadInterface.reloadCount(locationId.longValue(), count);
            }
            count.setId(locationId.longValue());
            idcLocationCountMapper.updateByObject(count);
        }
    }

    @Override
    public List<Map<String, Object>> getPortFlowTopN() {
        Map<String,Object> map;
        List<Map<String,Object>> list =new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            map =new HashMap<>();
            map.put("portname","port_"+i);
            map.put("outflow",Math.random()*10000);
            list.add(map);
        }
        return list;
    }


    @Override
    public List<Map<String, Object>> getRoomFreeUsgeTOP() {
        return idcLocationCountMapper.getRoomFreeUsgeTOP();
    }

    @Override
    public Map<String, Object> getIdcResourceCountByCustomerId(Long cusId) {
        return idcLocationCountMapper.getIdcResourceCountByCustomerId(cusId);
    }

    @Override
    public void reloadRoomStatistics() {
        try {
            logger.debug("更新各机房统计值");
            idcLocationCountMapper.reloadRoomStatistics();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
