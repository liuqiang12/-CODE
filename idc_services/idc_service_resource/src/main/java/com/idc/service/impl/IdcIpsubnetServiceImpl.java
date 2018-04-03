package com.idc.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.idc.mapper.IdcIpSubnetMapper;
import com.idc.mapper.IdcIpinfoMapper;
import com.idc.model.ExecuteResult;
import com.idc.model.IdcIpSubnet;
import com.idc.model.IdcSubnetCountVo;
import com.idc.service.IdcIpinfoService;
import com.idc.service.IdcIpsubnetService;
import com.idc.service.ReLoadInterface;
import com.idc.utils.CacheServer;
import com.idc.utils.IPUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.data.page.PageBean;
import system.data.supper.service.impl.SuperServiceImpl;
import utils.tree.TreeBuilder;
import utils.tree.TreeNode;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>IDC_IPSUBNET:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 16,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("idcIpsubnetService")
public class IdcIpsubnetServiceImpl extends SuperServiceImpl<IdcIpSubnet, Long> implements IdcIpsubnetService {
    @Autowired
    private IdcIpSubnetMapper mapper;
    @Autowired
    private IdcIpinfoMapper ipinfoMapper;
    @Autowired
    private IdcIpinfoService ipinfoService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    protected RedisTemplate<Serializable, Serializable> redisTemplate;
    @Autowired
    private CacheServer cacheServer;
    @Autowired
    @Qualifier("reLoadIPImpl")
    private ReLoadInterface reLoadInterface;
    @Override
//    @CacheEvict(value = "5M", key = "'CountByState'", allEntries = true)
    public ExecuteResult insertOrUpdate(IdcIpSubnet subnet) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            if (subnet.getId() > 0) {
                mapper.updateByObject(subnet);
            } else {
                subnet.setBegipnum(IPUtil.ipToLong(subnet.getBegip()));
                subnet.setEndipnum(IPUtil.ipToLong(subnet.getEndip()));
                subnet.setMaskstr(IPUtil.bitToMaskStr(Integer.parseInt(subnet.getMask().toString())));
                mapper.insert(subnet);//新增加的 启动线程创建IP
                boolean add = addIpTherd(subnet.getBegipnum(), subnet.getEndipnum(), subnet.getId(), subnet.getSubnetip(), subnet.getMaskstr());
                if(!add){
                    mapper.deleteById(subnet.getId());
                    executeResult.setMsg("保存IP失败");
                    return executeResult;
                }
            }
            executeResult.setState(true);
            reLoadInterface.reloadCount();
        } catch (Exception e) {
            logger.error("保存子网失败", e);
            executeResult.setMsg("保存子网失败");
        }
        return executeResult;
    }

    private boolean addIpTherd(Long begipnum, Long endipnum, Long id, String subnetIp, String maskstr) {
//        List<Long> ids = ipinfoMapper.getSeqs(endipnum - begipnum + 1);
        Connection connection = null;
        PreparedStatement ps = null;
        boolean isAuto = true;
        boolean result = true;
        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            isAuto = connection.getAutoCommit();
            connection.setAutoCommit(false);
            String sql = "insert into IDC_IPINFO (IPADDRESS ,DECIMALIP ,SUBNETID ,SUBNETIP ,MASKSTR,MASK,STATUS) values (?,?,?,?,?,?,?)";
            ps = connection.prepareStatement(sql);
            final int batchSize = 1000;
            int index = 0;
            long start = System.currentTimeMillis();
            System.err.println("Start");
            for (; begipnum <= endipnum; begipnum++) {
//                ps.setLong(1, ids.get(index));
                ps.setString(1, IPUtil.longToIP(begipnum));
                ps.setLong(2, begipnum);
                ps.setLong(3, id);
                ps.setString(4, subnetIp);
                ps.setString(5, maskstr);
                ps.setLong(6, IPUtil.maskStrTo32BitNum(maskstr));
                ps.setLong(7, 0);
                ps.addBatch();
                index++;
                if (index % batchSize == 0) {
                    ps.executeBatch();
                    connection.commit();
                    ps.clearBatch();
                }
            }
            ps.executeBatch();
            connection.commit();
            ps.clearBatch();
            long end = System.currentTimeMillis();
            System.err.println("用时：" + (end - start));
        } catch (Exception e) {
            result = false;
            logger.error("插入数据失败，即将回滚", e);
            try {
                connection.rollback();
            } catch (SQLException e1) {
                logger.error("回滚数据失败", e1);
            }
        } finally {
            try {
                ps.close();
                connection.setAutoCommit(isAuto);
                connection.close();
            } catch (SQLException e) {
                logger.error("还原提交方式error", e);
            }
        }
        return result;
    }

    @Override
//    @CacheEvict(value = "5M", key = "'CountByState'", allEntries = true)
    public ExecuteResult split(long pid, List<IdcIpSubnet> subnetips) {
        ExecuteResult executeResult = new ExecuteResult();
        IdcIpSubnet psubnet = getModelById(pid);
        if (psubnet.isParent()) {
            executeResult.setMsg("该网段已经被拆分，请检查");
            return executeResult;
        }
        for (IdcIpSubnet subnet : subnetips) {
            subnet.setPid(pid);
            subnet.setLocalid(psubnet.getLocalid());
            subnet.setMaskstr(IPUtil.bitToMaskStr(Integer.parseInt(subnet.getMask().toString())));
            long[] longs = IPUtil.subnetIpNumRange(subnet.getSubnetip(), subnet.getMaskstr());
            subnet.setBegipnum(longs[0]);
            subnet.setEndipnum(longs[1]);
            subnet.setBegip(IPUtil.longToIP(subnet.getBegipnum()));
            subnet.setEndip(IPUtil.longToIP(subnet.getEndipnum()));
        }
        try {
            insertList(subnetips);
            psubnet.setParent(true);
            updateByObject(psubnet);
            IdcIpSubnet subnet = new IdcIpSubnet();
            subnet.setPid(pid);
            subnetips = queryListByObject(subnet);
            updateIP(subnetips);
            executeResult.setState(true);
            reLoadInterface.reloadCount();
        } catch (Exception e) {
            logger.error("", e);
            executeResult.setMsg("拆分子网失败");
        }
        return executeResult;
    }

    @Override
    public List<IdcIpSubnet> queryListAndCountPage(PageBean page, Object o) {
        List<IdcIpSubnet> idcIpSubnets = queryListPage(page, o);
        return idcIpSubnets;
    }

    @Override
//    @Cacheable(value = "5M", key = "'CountByState'")
    public List<IdcSubnetCountVo> getCountByState() {
        long time = System.currentTimeMillis();
        List<IdcIpSubnet> idcIpSubnets = queryList();//所有节点关系
        logger.info("耗时》》》》》》》》》》》》》：" + (System.currentTimeMillis() - time));
        List<IdcSubnetCountVo> results = mapper.sumUsageBySubnet();//可以用来统计的节点
        logger.info("耗时》》》》》》》》》》》》》：" + (System.currentTimeMillis() - time));
        List<IdcSubnetCountVo> count = getCount(idcIpSubnets, results);
        logger.info("耗时》》》》》》》》》》》》》：" + (System.currentTimeMillis() - time));
        return count;
    }

    /***
     * @param idcIpSubnets 向上汇总关系
     * @param valueSource  统计数据来源
     * @return
     */
    private List<IdcSubnetCountVo> getCount(List<IdcIpSubnet> idcIpSubnets, List<IdcSubnetCountVo> valueSource) {
        //results 按子网统计Map
        ImmutableMap<String, IdcSubnetCountVo> resultMap = Maps.uniqueIndex(valueSource, new Function<IdcSubnetCountVo, String>() {
            @Override
            public String apply(IdcSubnetCountVo idcSubnetCountVo) {
                return idcSubnetCountVo.getId().toString();
            }
        });
        List<TreeNode> nodes = new ArrayList<TreeNode>();
        //将网段组装为Tree结构
        for (IdcIpSubnet idcIpSubnet : idcIpSubnets) {
            TreeNode node = new TreeNode();
            Long pid = idcIpSubnet.getPid();
            Long id = idcIpSubnet.getId();
            node.setParentId(pid == null ? "" : pid + "");
            node.setId(id.toString());
            node.setName(idcIpSubnet.getSubnetip());
            IdcSubnetCountVo idcSubnetCountVo = resultMap.get(id.toString());//获取该节点的统计信息
            if (idcSubnetCountVo != null) {
                node.setSelf(idcSubnetCountVo);
            } else {//如果统计结果里面没有 说明是上级节点 手动创建一个统计节点
                IdcSubnetCountVo tmp = new IdcSubnetCountVo();
                tmp.setId(id);
                node.setSelf(tmp);
            }
            nodes.add(node);
        }
        List<TreeNode> treeNodes = TreeBuilder.buildListToTree(nodes);//构建树
        List<IdcSubnetCountVo> result = new ArrayList<>();
        for (TreeNode treeNode : treeNodes) {
            count(result, treeNode);//对树进行向上汇总;
        }
        return result;
    }

    private IdcSubnetCountVo count(List<IdcSubnetCountVo> result, TreeNode treeNode) {
        List<TreeNode> children = treeNode.getChildren();//先判断是否有下级节点
        IdcSubnetCountVo node = (IdcSubnetCountVo) treeNode.getSelf();
        result.add(node);
        if (children != null && children.size() > 0) {
            for (TreeNode child : children) {
                //如果有 先递归统计下级节点 在向上汇报
                IdcSubnetCountVo count = count(result, child);
                //累加下级节点
                node.setAllip(node.getAllip() + count.getAllip());
                node.setPreused(node.getPreused() + count.getPreused());
                node.setFree(node.getFree() + count.getFree());
                node.setUsed(node.getUsed() + count.getUsed());
                node.setRecovery(node.getRecovery() + count.getRecovery());
            }
        }
        return node;
    }

    @Override
//    @CacheEvict(value = "5M", key = "'CountByState'", allEntries = true)
    public ExecuteResult merger(IdcIpSubnet idcIpSubnet, String delNetSegs, String updateIds, String targetNetSeg) {
        long time = System.currentTimeMillis();
        ExecuteResult executeResult = new ExecuteResult();
        IdcIpSubnet query= new IdcIpSubnet();
        query.setSubnetip(idcIpSubnet.getSubnetip());
        query.setMask(idcIpSubnet.getMask());
        String[] dids = delNetSegs.split(",");
        for (String did : dids) {
            List<String> childId = mapper.getChildId(did);
            for (String s : childId) {
                delNetSegs+=","+s;
            }
        }
        List<IdcIpSubnet> idcIpSubnets = mapper.queryListByObject(query);
        if(idcIpSubnets.size()>0){
            delNetSegs+=","+updateIds;
            updateIds= idcIpSubnets.get(0).getId().toString();
        }
//        IdcIpSubnet modelById = mapper.getModelById(Long.parseLong(updateIds));
        List<IdcIpSubnet> netsubInfoList = mapper.queryListByIdIn(Arrays.asList(updateIds.split(",")));
        Map<String, String> map = new HashMap<>();
        String maskStr = IPUtil.bitToMaskStr(Integer.valueOf(idcIpSubnet.getMask().toString()));
        if (netsubInfoList != null) {
            List<IdcIpSubnet> netsubInfoSave = new ArrayList<IdcIpSubnet>();
            IdcIpSubnet netSubPlan = null;
            for (int i = 0; i < netsubInfoList.size(); i++) {
                netSubPlan = netsubInfoList.get(i);
                netSubPlan.setMask(idcIpSubnet.getMask());
                netSubPlan.setRemark(idcIpSubnet.getRemark());
                netSubPlan.setSubnetip(idcIpSubnet.getSubnetip());
                netSubPlan.setMaskstr(maskStr);
                Long[] ipLongs = IPUtil.getLongIPStartEnd(idcIpSubnet.getSubnetip(),
                        maskStr);
                netSubPlan.setBegipnum(ipLongs[0]);
                netSubPlan.setEndipnum(ipLongs[1]);
                netSubPlan.setBegip(IPUtil.longToIP(ipLongs[0]));
                netSubPlan.setEndip(IPUtil.longToIP(ipLongs[1]));
                netSubPlan.setParent(false);
                netsubInfoSave.add(netSubPlan);
                map.put(netSubPlan.getId().toString(), ipLongs[0] + ":" + ipLongs[1]);
                try {
                    updateByObject(netSubPlan);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // 只有一个更新对象时结束方法
                if (netsubInfoList.size() == 1
                        && StringUtils.isBlank(delNetSegs)) {
                    return executeResult;
                }
            }
        }
        List<IdcIpSubnet> subList = mapper.queryListByIdIn(Arrays.asList(delNetSegs.split(",")));
        upIpTherd(subList, map, maskStr);
        executeResult.setState(true);
        logger.info("耗时" + (System.currentTimeMillis() - time));
        reLoadInterface.reloadCount();
        return executeResult;
    }

    private boolean upIpTherd(List<IdcIpSubnet> subList, Map<String, String> map, String maskstr) {
        Connection connection = null;
        PreparedStatement ps = null, ps1 = null;
        boolean isAuto = true;
        boolean result = true;
        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            isAuto = connection.getAutoCommit();
            connection.setAutoCommit(false);
            String sql = "UPDATE  IDC_IPINFO SET SUBNETID=?,MASK=?,MASKSTR=? WHERE SUBNETID=? OR SUBNETID=?";
            String DELSQL = "DELETE  FROM IDC_IPSUBNET  WHERE ID=? ";
            ps = connection.prepareStatement(sql);
            ps1 = connection.prepareStatement(DELSQL);
            final int batchSize = 1000;
            int index = 0;
            long start = System.currentTimeMillis();
            System.err.println("Start");
            String subnetId;
            long begNum = 0L, endNum = 0L;
            for (IdcIpSubnet netsubInfo : subList) {
                subnetId = null;
                // 5、预留；6、规划分配
                int ipStatus = 6;
                // 0、分配；1、预留
                Set<String> keys = map.keySet();
                for (String key : keys) {
                    String[] temps = map.get(key).split(":");
                    begNum = Long.parseLong(temps[0]);
                    endNum = Long.parseLong(temps[1]);
                    if (begNum <= netsubInfo.getBegipnum()
                            && endNum >= netsubInfo.getEndipnum()) {
                        subnetId = key;
                        // map.remove(key); //这里不能remove 否则，如果合并时 一次拖动了2位以上，就出问题了
                        break;
                    }
                }
                if (subnetId == null)
                    continue;
                ps.setString(1, subnetId);
                ps.setInt(2, IPUtil.maskStrTo32BitNum(maskstr));
                ps.setString(3, maskstr);
                ps.setString(4, netsubInfo.getId().toString());
                ps.setString(5, subnetId);
                ps.addBatch();
                ps1.setString(1, netsubInfo.getId().toString());
                ps1.addBatch();
//                ps.addBatch("DELETE  form IDC_IPINFO  WHERE SUBNETID="+netsubInfo.getId().toString());
            }
            ps.executeBatch();
            ps1.executeBatch();
            connection.commit();
            ps.clearBatch();
            ps1.clearBatch();
            long end = System.currentTimeMillis();
            System.err.println("用时：" + (end - start));

        } catch (Exception e) {
            result = false;
            logger.error("插入数据失败，即将回滚", e);
            try {
                connection.rollback();
            } catch (SQLException e1) {
                logger.error("回滚数据失败", e1);
            }
        } finally {
            try {
                ps.close();
                ps1.close();
                connection.setAutoCommit(isAuto);
                connection.close();
            } catch (SQLException e) {
                logger.error("还原提交方式error", e);
            }
        }
        return result;
    }

    private void updateIP(List<IdcIpSubnet> subnetips) {
        for (IdcIpSubnet subnetip : subnetips) {
            ipinfoMapper.updateIpBySubnet(subnetip);
        }
    }

    @Override
    @Transactional
//    @CacheEvict(value="5M",key="'CountByState'", allEntries=true)
    public ExecuteResult recoverySubnet(List<String> subnetids) {
        ExecuteResult executeResult = new ExecuteResult();
//        List<IdcIpSubnet> idcIpSubnets = mapper.queryListByIdIn(subnetids);
//        ArrayList<String> ipids = new ArrayList<>();
//        for (IdcIpSubnet idcIpSubnet : idcIpSubnets) {
//            IdcIpInfo query = new IdcIpInfo();
//            query.setSubnetid(idcIpSubnet.getId());
//            List<IdcIpInfo> idcIpInfos = ipinfoService.queryListByObject(query);
//            for (IdcIpInfo idcIpInfo : idcIpInfos) {
//                ipids.add(idcIpInfo.getId().toString());
//            }
//        }
        ipinfoService.recoveryBySubnetids(subnetids);
//        ipinfoService.recoveryByids(ipids);
        executeResult.setState(true);
        reLoadInterface.reloadCount();
        return executeResult;
    }
}
