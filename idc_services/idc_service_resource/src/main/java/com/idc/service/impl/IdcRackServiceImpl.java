package com.idc.service.impl;

import com.idc.mapper.*;
import com.idc.model.*;
import com.idc.service.DcimRackModelService;
import com.idc.service.IdcRackService;
import org.nutz.dao.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import system.data.page.PageBean;
import system.data.supper.service.impl.SuperServiceImpl;
import utils.typeHelper.MapHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;


/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>idc_rack:IDC机架<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> May 23,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("idcRackService")
public class IdcRackServiceImpl extends SuperServiceImpl<IdcRack, Integer> implements IdcRackService {
    @Autowired
    private IdcRackMapper idcRackMapper;
    @Autowired
    private IdcDeviceMapper idcDeviceMapper;
    @Autowired
    private NetPortMapper netPortMapper;
    @Autowired
    private IdcLinkMapper idcLinkMapper;
    @Autowired
    private IdcConnectorMapper idcConnectorMapper;
    @Autowired
    private ZbMachineroomMapper zbMachineroomMapper;
    @Autowired
    private DcimRackModelMapper dcimRackModelMapper;
    @Autowired
    private IdcMcbMapper idcMcbMapper;
    @Autowired
    private IdcRackunitMapper idcRackunitMapper;


//    @Autowired
//    private Dao nutDao;
    @Override
    public List<Map<String, Object>> queryIdcRackInfoListPage(PageBean page, Object param) {
        //这里讲查询条件进行处理
        page.setParams(MapHelper.queryCondition(param));
        List<Map<String, Object>> maps = idcRackMapper.queryIdcRackInfoListPage(page);
        return maps;
    }

    @Override
    public List<Map<String, Object>> queryAllIdcRackInfoList() {
        return idcRackMapper.queryAllIdcRackInfoList();
    }

    @Override
    public List<Map<String, Object>> getAllRackInfo(IdcRack idcRack) {
        return idcRackMapper.getAllRackInfo(idcRack);
    }

    @Override
    public Map<String, Object> getMapModelById(int id) {
        return idcRackMapper.getMapModelById(id);
    }

    @Override
    public List<Map<String, Object>> getRackModel() {
        return idcRackMapper.getRackModel();
    }

    @Override
    public List<Map<String, Object>> getInternetexport() {
        return idcRackMapper.getInternetexport();
    }

    @Override
    public List<Map<String, Object>> queryPdfRackInfoList(IdcRack idcRack) {
        return idcRackMapper.queryPdfRackInfoList(idcRack);
    }

    @Override
    public List<Map<String, Object>> queryOdfRackInfoList(IdcRack idcRack) {
        return idcRackMapper.queryOdfRackInfoList(idcRack);
    }

    @Override
    public List<Map<String, Object>> queryPdfRackInfoListPage(PageBean page, Object param) {
        page.setParams(MapHelper.queryCondition(param));
        return idcRackMapper.queryPdfRackInfoListPage(page);
    }

    @Override
    public List<Map<String, Object>> queryOdfRackInfoListPage(PageBean page, Object param) {
        page.setParams(MapHelper.queryCondition(param));
        return idcRackMapper.queryOdfRackInfoListPage(page);
    }

    @Override
    public List<Map<String, Object>> getAllOdfRackInfoList(IdcRack idcRack) {
        return idcRackMapper.getAllOdfRackInfoList(idcRack);
    }

    @Override
    public void insertOrUpdate(IdcRack idcRack, Integer rackId, Integer mcbNum) throws Exception {
        if (idcRack.getBusinesstypeId() != null && "wiring".equals(idcRack.getBusinesstypeId())) {
            idcRack.setBusinesstypeId("df");
            idcRack.setDftype("wiring");
        }
        if (idcRack.getRackmodelid() != null) {
            DcimRackModel dcimRackModel = dcimRackModelMapper.getModelById(idcRack.getRackmodelid());
            idcRack.setHeight(dcimRackModel.getUheight());
        }
        if (rackId != null && rackId != 0) {//修改
            idcRack.setId(rackId);
            idcRackMapper.updateByObject(idcRack);
        } else {//新增
            idcRackMapper.insert(idcRack);
            //生成MCB
            autoCreateMcb(idcRack, mcbNum);
            //创建机位
            autoCreateRackunit(idcRack);
        }
    }

    //生成MCB
    public void autoCreateMcb(IdcRack idcRack, Integer mcbNum) throws Exception {
        List<IdcMcb> list = new ArrayList<>();
        //生成mcb
        if (mcbNum != null && mcbNum > 0) {
            for (int i = 0; i < mcbNum; i++) {
                int mcbNo = i + 1;
                int mcbNo1 = i * 2 + 1;
                IdcMcb idcMcb1 = new IdcMcb();
                IdcMcb idcMcb2 = new IdcMcb();
                idcMcb1.setName(idcRack.getName() + "-L" + mcbNo + "-1");
                idcMcb1.setPwrMcbno("PDF-" + mcbNo1);
                idcMcb1.setPwrInstalledrackId(idcRack.getId().longValue());
                idcMcb1.setPwrPwrstatus(20);
                list.add(idcMcb1);
                mcbNo1 = i * 2 + 2;
                idcMcb2.setName(idcRack.getName() + "-L" + mcbNo + "-2");
                idcMcb2.setPwrMcbno("PDF-" + mcbNo1);
                idcMcb2.setPwrInstalledrackId(idcRack.getId().longValue());
                idcMcb2.setPwrPwrstatus(20);
                list.add(idcMcb2);
            }
            idcMcbMapper.insertList(list);
        }
    }
    //创建机位
    public void autoCreateRackunit(IdcRack idcRack) throws Exception{//客户柜和网络柜才有机位
        if(idcRack!=null && idcRack.getBusinesstypeId()!=null &&
                ("equipment".equals(idcRack.getBusinesstypeId())||"cabinet".equals(idcRack.getBusinesstypeId()))
                && idcRack.getHeight()!=null&&idcRack.getHeight()>0){
            List<IdcRackunit> rackunitList = new ArrayList<>();
            for(int i=1;i<=idcRack.getHeight();i++){
                IdcRackunit param = new IdcRackunit();
                param.setCode(idcRack.getCode()+"-"+i);
                param.setRackid(Long.valueOf(idcRack.getId()));
                param.setStatus(20);
                param.setUno(i);
                param.setRoomid(Long.valueOf(idcRack.getRoomid()));
                rackunitList.add(param);
            }
            idcRackunitMapper.insertList(rackunitList);
        }
    }

    //导入机架信息
    @Override
    @Transactional
    public void importIdcRackByExcel(List<List<Object>> list, String type) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            List<Object> obj = list.get(i);
            IdcRack idcRack = new IdcRack();
            if (type != null && "other".equals(type)) {//导入普通机架
                idcRack.setName(String.valueOf(obj.get(0)));
                idcRack.setCode(String.valueOf(obj.get(1)));
                //设置机架类型
                if (obj.get(2) != null && !"".equals(String.valueOf(obj.get(2)))) {
                    if ("equipment".equals(String.valueOf(obj.get(2)))) {
                        idcRack.setBusinesstypeId("equipment");
                    } else if ("cabinet".equals(String.valueOf(obj.get(2)))) {
                        idcRack.setBusinesstypeId("cabinet");
                    } else if ("wiring".equals(String.valueOf(obj.get(2)))) {
                        idcRack.setBusinesstypeId("df");
                        idcRack.setDftype("wiring");
                    }
                }
                //设置归属机房
                if (obj.get(3) != null && !"".equals(String.valueOf(obj.get(3)))) {
                    ZbMachineroom zbMachineroom = new ZbMachineroom();
                    zbMachineroom.setSitename(String.valueOf(obj.get(3)));
                    zbMachineroom = zbMachineroomMapper.getModelByObject(zbMachineroom);
                    idcRack.setRoomid(zbMachineroom != null ? zbMachineroom.getId().toString() : null);
                    idcRack.setPduLocation(zbMachineroom != null ? zbMachineroom.getSitename() : null);
                }
                idcRack.setRoomareaid(String.valueOf(obj.get(4)));
                //设置机架型号
                if (obj.get(5) != null && !"".equals(String.valueOf(obj.get(5)))) {
                    DcimRackModel dcimRackModel = new DcimRackModel();
                    dcimRackModel.setName(String.valueOf(obj.get(5)));
                    dcimRackModel = dcimRackModelMapper.getModelByObject(dcimRackModel);
                    idcRack.setRackmodelid(dcimRackModel != null ? dcimRackModel.getId() : null);
                    idcRack.setHeight(dcimRackModel != null ? dcimRackModel.getUheight() : null);
                }
                idcRack.setUsefor(Integer.parseInt(String.valueOf(obj.get(6))));
                idcRack.setRenttype(Integer.parseInt(String.valueOf(obj.get(7))));
                idcRack.setPduPowertype(String.valueOf(obj.get(8)));
                idcRack.setRatedelectricenergy(BigDecimal.valueOf(Double.valueOf(String.valueOf(obj.get(9)))));
                idcRack.setStatus(20);
                idcRack.setIsrackinstalled(0);
            } else if (type != null && "pdu".equals(type)) {//导入pdf架
                idcRack.setBusinesstypeId("pdu");
                idcRack.setStatus(20);
                idcRack.setIsrackinstalled(0);
                idcRack.setName(String.valueOf(obj.get(0)));
                idcRack.setCode(String.valueOf(obj.get(1)));
                //设置归属机房
                if (obj.get(2) != null && !"".equals(String.valueOf(obj.get(2)))) {
                    ZbMachineroom zbMachineroom = new ZbMachineroom();
                    zbMachineroom.setSitename(String.valueOf(obj.get(2)));
                    zbMachineroom = zbMachineroomMapper.getModelByObject(zbMachineroom);
                    idcRack.setRoomid(zbMachineroom != null ? zbMachineroom.getId().toString() : null);
                    idcRack.setPduLocation(zbMachineroom != null ? zbMachineroom.getSitename() : null);
                }
                idcRack.setPduPowertype(String.valueOf(obj.get(3)));
            } else if (type != null && "df".equals(type)) {//导入odf架
                idcRack.setBusinesstypeId("df");
                idcRack.setDftype("odf");
                idcRack.setStatus(20);
                idcRack.setIsrackinstalled(0);
                idcRack.setName(String.valueOf(obj.get(0)));
                idcRack.setCode(String.valueOf(obj.get(1)));
                //设置归属机房
                if (obj.get(2) != null && !"".equals(String.valueOf(obj.get(2)))) {
                    ZbMachineroom zbMachineroom = new ZbMachineroom();
                    zbMachineroom.setSitename(String.valueOf(obj.get(2)));
                    zbMachineroom = zbMachineroomMapper.getModelByObject(zbMachineroom);
                    idcRack.setRoomid(zbMachineroom != null ? zbMachineroom.getId().toString() : null);
                    idcRack.setPduLocation(zbMachineroom != null ? zbMachineroom.getSitename() : null);
                }
                //设置机架型号
                if (obj.get(3) != null && !"".equals(String.valueOf(obj.get(3)))) {
                    DcimRackModel dcimRackModel = new DcimRackModel();
                    dcimRackModel.setName(String.valueOf(obj.get(3)));
                    dcimRackModel = dcimRackModelMapper.getModelByObject(dcimRackModel);
                    idcRack.setRackmodelid(dcimRackModel != null ? dcimRackModel.getId() : null);
//                    idcRack.setHeight(dcimRackModel != null ? dcimRackModel.getUheight() : null);
                }
                idcRack.setPduPowertype(String.valueOf(obj.get(4)));
                idcRack.setRatedelectricenergy(BigDecimal.valueOf(Double.valueOf(String.valueOf(obj.get(5)))));
            }

            IdcRack oldrack = idcRackMapper.queryRackInfoByName(idcRack.getName());
            //若此机架已存在就进行修改，否则就新增
            if (oldrack != null && oldrack.getName().equals(idcRack.getName())) {
                idcRack.setId(oldrack.getId());
                idcRack.setIsdelete(0);
                idcRackMapper.updateByObject(idcRack);
            } else {
                idcRackMapper.insert(idcRack);
            }
        }
        //进行机架信息维护  先关闭此入口   因为可能会将已分配的机位给清除掉
        //idcRackunitMapper.autoVindicateRackunit();
    }

    //获取资源分配的ODF架
    @Override
    public List<Map<String, Object>> queryDistributionOdfRackInfoListPage(PageBean page, Object param) {
        page.setParams(MapHelper.queryCondition(param));
        return idcRackMapper.queryDistributionOdfRackInfoListPage(page);
    }
    /* 绑定端口数据 */
    @Override
    public void deviceBindNetPort(Long portIdA, String portIds, Long deviceId, Integer roomId, String rackIds) throws Exception {
        HttpSession session = getSession();
        List linkIdList = (List) session.getAttribute("linkIdList");
        if (linkIdList == null) {
            linkIdList = new ArrayList();
            session.setAttribute("linkIdList", linkIdList);
        }
        List<String> portIdList = null;
        NetPort netProtZ = new NetPort();
        IdcDevice idcDeviceZ = new IdcDevice();
        IdcRack idcRackZ = new IdcRack();
        IdcRack idcRackA = new IdcRack();
        IdcDevice idcDeviceA = new IdcDevice();
        if(deviceId!=null){
            //获取Z端设备（网络设备）
            idcDeviceZ = idcDeviceMapper.getModelById(deviceId);
            //获取Z端机架
            idcRackZ = idcRackMapper.getModelById(idcDeviceZ.getRackId().intValue());
        }
        if(portIds!=null&&!"".equals(portIds)){
            portIdList = Arrays.asList(portIds.split(","));
            for(String portId:portIdList){
                //获取Z端端口
                netProtZ = netPortMapper.getModelById(Long.parseLong(portId));
                //进行关系绑定
                IdcLink idcLink = new IdcLink();
                idcLink.setAendportId(portIdA);
                List<String> arackIdList = Arrays.asList(rackIds.split("_")[1].split("-"));
                for (String aId : arackIdList) {
                    if (rackIds.indexOf("rack") > -1) {//A端为机架
                        //获取A端机架
                        idcRackA = idcRackMapper.getModelById(Integer.parseInt(aId));
                        //建立关系
                        idcLink.setName(idcRackA.getName() + "-x-" + netProtZ.getPortname() + "-x");
                        idcLink.setCode(idcRackA.getName() + "-x-" + netProtZ.getPortname() + "-x");
                        idcLink.setCreatedate(new Date());
                        idcLink.setAendrackId(idcRackA.getId().longValue());

                        idcLink.setZendportId(Long.parseLong(portId));
                        idcLink.setZenddeviceId(deviceId);
                        idcLink.setZendrackId(idcRackZ.getId().longValue());
                        idcLinkMapper.insert(idcLink);
                        linkIdList.add(idcLink.getId());
                    } else if (rackIds.indexOf("device") > -1) {//A端为设备
                        //获取A端设备
                        idcDeviceA = idcDeviceMapper.getModelById(Long.parseLong(aId));
                        //获取A端机架
                        idcRackA = idcRackMapper.getModelById(idcDeviceA.getRackId().intValue());
                        //建立关系
                        idcLink.setName(idcRackA.getName() + "-x-" + netProtZ.getPortname());
                        idcLink.setCode(idcRackA.getName() + "-x-" + netProtZ.getPortname());
                        idcLink.setCreatedate(new Date());
                        idcLink.setAendrackId(idcRackA.getId().longValue());
                        idcLink.setAenddeviceId(idcDeviceA.getDeviceid());

                        idcLink.setZendportId(Long.parseLong(portId));
                        idcLink.setZenddeviceId(deviceId);
                        idcLink.setZendrackId(idcRackZ.getId().longValue());
                        idcLinkMapper.insert(idcLink);
                        linkIdList.add(idcLink.getId());
                    }
                }
            }
        }
        session.setAttribute("linkIdList", linkIdList);
    }

    //获取session
    public HttpSession getSession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getSession();
    }

    /**
     * 绑定端子数据
     *
     * @param ids
     * @param roomId
     * @param rackIds
     * @param odfRackId
     * @throws Exception
     */
    @Override
    @Transactional
    public void bindRackAndConnector(String ids, Integer roomId, String rackIds, Integer odfRackId, Long portIdA) throws Exception {
        HttpSession session = getSession();
        List linkIdList = (List) session.getAttribute("linkIdList");
        if (linkIdList == null) {
            linkIdList = new ArrayList();
            session.setAttribute("linkIdList", linkIdList);
        }
        List<String> idList = null;
        IdcRack idcRackZ = new IdcRack();
        IdcConnector idcConnector = new IdcConnector();
        IdcRack idcRackA = new IdcRack();
        IdcDevice idcDeviceA = new IdcDevice();
        if (odfRackId != null) {
            //获取Z端机架
            idcRackZ = idcRackMapper.getModelById(odfRackId);
        }
        if (ids != null && !"".equals(ids)) {
            idList = Arrays.asList(ids.split(","));
            //先修改端子状态
            Map<String,Object> param = new HashMap<>();
            param.put("status",60);
            param.put("list",idList);
            idcConnectorMapper.updateStatusByConnIds(param);
            for (String id : idList) {
                //获取Z端端子
                idcConnector = idcConnectorMapper.getModelById(Long.parseLong(id));
                //进行关系绑定
                IdcLink idcLink = new IdcLink();
                idcLink.setAendportId(portIdA);
                List<String> arackIdList = Arrays.asList(rackIds.split("_")[1].split("-"));
                for (String aId : arackIdList) {
                    if (rackIds.indexOf("rack") > -1) {//A端为机架
                        //获取A端机架
                        idcRackA = idcRackMapper.getModelById(Integer.parseInt(aId));
                        //建立关系
                        idcLink.setName(idcRackA.getName() + "-x-" + idcConnector.getName());
                        idcLink.setCode(idcRackA.getName() + "-x-" + idcConnector.getName());
                        idcLink.setCreatedate(new Date());
                        idcLink.setAendrackId(idcRackA.getId().longValue());

                        idcLink.setZendportId(Long.parseLong(id));
                        idcLink.setZendrackId(idcRackZ.getId().longValue());
                        idcLinkMapper.insert(idcLink);
                        linkIdList.add(idcLink.getId());
                    } else if (rackIds.indexOf("device") > -1) {//A端为设备
                        //获取A端设备
                        idcDeviceA = idcDeviceMapper.getModelById(Long.parseLong(aId));
                        //获取A端机架
                        idcRackA = idcRackMapper.getModelById(idcDeviceA.getRackId().intValue());
                        //建立关系
                        idcLink.setName(idcRackA.getName() + "-x-" + idcConnector.getName());
                        idcLink.setCode(idcRackA.getName() + "-x-" + idcConnector.getName());
                        idcLink.setCreatedate(new Date());
                        idcLink.setAendrackId(idcRackA.getId().longValue());
                        idcLink.setAenddeviceId(idcDeviceA.getDeviceid());

                        idcLink.setZendportId(Long.parseLong(id));
                        idcLink.setZendrackId(idcRackZ.getId().longValue());
                        idcLinkMapper.insert(idcLink);
                        linkIdList.add(idcLink.getId());
                    }
                }
            }
        }
        session.setAttribute("linkIdList", linkIdList);
    }

    /**
     * @return
     */
    @Override
    public List<Map<String, Object>> getRackListByRackIds(PageBean page, Object param) {
        page.setParams(MapHelper.queryCondition(param));
        return idcRackMapper.getRackListByRackIds(page);
    }

    /*修改机架状态 map中key:id-机架ID，status-状态，customerId-客户ID，customerName-客户名称，ticketId-工单号,*/
    @Override
    @Transactional
    public void updateRackStatusByRackIds(List<Map<String, Object>> list) throws Exception {
        if (list != null && list.size() > 0) {
            idcRackMapper.updateRackStatusByRackIds(list);
            if(list.get(0).get("status")!=null&&"40".equals(list.get(0).get("status").toString())){//释放资源
                Iterator<Map<String, Object>> it = list.iterator();
                while(it.hasNext()) {
                    Map<String, Object> maptmp = it.next();
                    maptmp.put("status",20);
                }
            }
            if(list.get(0).get("status")!=null&&"60".equals(list.get(0).get("status").toString())){//释放占用
                Iterator<Map<String, Object>> it = list.iterator();
                while(it.hasNext()) {
                    Map<String, Object> maptmp = it.next();
                    maptmp.put("status",55);
                }
            }
            //将机架下的机位全部修改状态
            idcRackunitMapper.updateStatusByParamsMap(list);
        }
    }

    /*统计数据中心客户架总数及使用率*/
    @Override
    public Map<String, Object> getCustomerRackNum(Long locationId) {
        return idcRackMapper.getCustomerRackNum(locationId);
    }

    @Override
    public List<Map<String, Object>> upordownForDeviceRackList(PageBean page, Object param) {
        page.setParams(MapHelper.queryCondition(param));
        return idcRackMapper.upordownForDeviceRackList(page);
    }

    @Override
    public void autoVindicateRackunit() throws Exception {
        idcRackunitMapper.autoVindicateRackunit();
    }

    /*获取客户所有占用机架*/
    @Override
    public List<Map<String, Object>> queryUseredRackByCustomerId(Map map) {
        return idcRackMapper.queryUseredRackByCustomerId(map);
    }

    /*获取客户所有占用机架  page*/
    @Override
    public List<Map<String, Object>> queryUseredRackByCustomerIdPage(PageBean page, Object param) {
        page.setParams(MapHelper.queryCondition(param));
        return idcRackMapper.queryUseredRackByCustomerIdPage(page);
    }

    @Override
    public List<IdcRack> distributionPdfRackList(PageBean page, Object param) {
        page.setParams(MapHelper.queryCondition(param));
        return idcRackMapper.distributionPdfRackList(page);
    }

    /*通过机房ID获取该机房所有模块*/
    @Override
    public List<Map<String, Object>> queryModuleByRoomId(Long roomId) {
        return idcRackMapper.queryModuleByRoomId(roomId);
    }

    /*删除机架  对机架进行软删除*/
    @Override
    public void updateRackToInvalidByIds(List<String> list) throws Exception {
        idcRackMapper.updateRackToInvalidByIds(list);
    }

    /*通过机架名称获取机架信息*/
    @Override
    public IdcRack queryRackInfoByName(String name) {
        return idcRackMapper.queryRackInfoByName(name);
    }

    /*通过机架IDS获取机架*/
    @Override
    public List<IdcRack> queryRackListByIds(Map<String,Object> map) {
        return idcRackMapper.queryRackListByIds(map);
    }

    /*通过数据中心ID查询所有的客户柜*/
    @Override
    public List<IdcRack> queryRackListByLocationId(Map<String, Object> map) {
        return idcRackMapper.queryRackListByLocationId(map);
    }
}
