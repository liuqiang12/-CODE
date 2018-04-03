package com.idc.service.impl;

import com.idc.mapper.SysOrganizationMapper;
import com.idc.mapper.SysRegionMapper;
import com.idc.model.*;
import com.idc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.data.page.PageBean;
import system.data.supper.service.impl.SuperServiceImpl;
import utils.tree.TreeBuilder;
import utils.tree.TreeNode;
import utils.typeHelper.ListHelper;

import java.util.*;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>sys_region:区域表信息<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 22,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("sysRegionService")
public class SysRegionServiceImpl extends SuperServiceImpl<SysRegion, Integer> implements SysRegionService {
    @Autowired
    private SysRegionMapper mapper;
    @Autowired
    private SysOrganizationMapper sysOrganizationMapper;
    @Autowired
    private SysRegionTreeClosureService sysRegionTreeClosureService;
    @Autowired
    private SysLnRoleDataPermissionService sysLnRoleDataPermissionService;
    @Autowired
    private SysUserinfoService sysUserinfoService;
    @Autowired
    private SysRoleinfoService sysRoleinfoService;
    @Override
    public List<TreeNode> getTree(Boolean isShowOrg, Integer defaultLevel, Integer pid) {
        return getTree(isShowOrg,defaultLevel,pid,false);
    }
    /**
     * Special code can be written here
     * @param isShowOrg
     * @param defaultLevel
     * @param pid
     * @param isSimpDate 是否简单数据格式
     */
    @Override
    public List<TreeNode> getTree(Boolean isShowOrg, Integer defaultLevel, Integer pid, boolean isSimpDate) {
        return getTree(isShowOrg,defaultLevel,pid,isSimpDate,false);
    }

    @Override
    public List<SysRegion> queryListPage(PageBean<SysRegion> pageBean, Integer region_pid, String regionName) {
        HashMap queryMap = new HashMap();
        if(region_pid!=null){
//            SysRegionTreeClosure treeClosure = new SysRegionTreeClosure();
//            treeClosure.setAncestor(region_pid);
//            List<SysRegionTreeClosure> sysRegionTreeClosures = sysRegionTreeClosureService.queryListByObject(treeClosure);
//            List<Integer> ids = new ArrayList<>();
//            for (SysRegionTreeClosure sysRegionTreeClosure : sysRegionTreeClosures) {
//                ids.add(sysRegionTreeClosure.getDescendant());
//            }
//            queryMap.put("ids", ids);
            queryMap.put("pid", region_pid);
//            queryMap.put("name",regionName);
        }
        queryMap.put("name",regionName);

        pageBean.setParams(queryMap);
        List<SysRegion> result =new ArrayList<>();
        try {
            // mapper.queryListPageByPid(pageBean,queryMap);
            result =  mapper.queryListPage(pageBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }
    //
//    @Override
//    public List<SysRegion> queryListPage(PageBean<SysRegion> pageBean, Object map) {
//        Map<String, Object> objectMap = MapHelper.queryCondition(map);
//        return null;
//    }
    /*迭代处理
     * (non-Javadoc)
     * @see com.idc.service.SysRegionService#getBootstrapTree(com.idc.model.TreeModel, java.lang.Integer)
     */
    @Override
    public TreeModel getBootstrapTree(TreeModel treeModel,Integer nodeid) {
        List<TreeModel> sysRegions = new ArrayList<TreeModel>();
        if(nodeid == null){
            //初始化方法的时候需要加载顶级节点
            sysRegions = mapper.queryFirstNode();
        }else{
            //后续就直接获取下一节点信息
            sysRegions = mapper.queryNodes(nodeid);
        }
        if (sysRegions != null) {
            Iterator<TreeModel> it = sysRegions.iterator();
            while(it.hasNext()){
                TreeModel treeModelTmp = it.next();
                //获取下一节点
                if(treeModelTmp.getIsparent()){
                    getBootstrapTree(treeModelTmp, treeModelTmp.getId());
                }
                treeModel.addChildren(treeModelTmp);
            }
        }
        return treeModel;
    }

    @Override
    public Map<Object, SysRegion> getRegionMapBy(String name) {
        Map<Object, SysRegion> RegionMap = new HashMap<>();
        List<SysRegion> sysRegions = queryList();
        try {
            RegionMap = ListHelper.ListToMap(sysRegions, name);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取区域Map失败");
        }
        return RegionMap;
    }


    @Override
    public List<SysRegion> getAllChild(Integer regionid) {
        return null;
    }

    public void iteratorData(){

    }
	@Override
	public TreeModel getTree_homeCity() {
		//初始化方法的时候需要加载顶级节点
        List<TreeModel> firstList = mapper.queryFirstNode();
        TreeModel rootModel = (firstList!=null && !firstList.isEmpty())?firstList.get(0):null;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("isroot", true);
        rootModel.setAttributes(map);
        //然后获取下面的所有地级市情况
        List<TreeModel> nextNodes = mapper.queryNodes(rootModel.getId());
        rootModel.setChildren(nextNodes);
		return rootModel;
	}

    @Override
    public List<TreeNode> getTree(Boolean isShowOrg, Integer defaultLevel, Integer id, boolean isSimpDate, Boolean isrole) {
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
//        List<TreeModel> treeModels=new ArrayList<>();
//        if(pid==null){
//             treeModels = mapper.queryFirstNode();
//        }else{
//            treeModels = mapper.queryNodes(pid);
//        }
//        for (TreeModel rootNode : treeModels) {
//            TreeNode t = new TreeNode();
//            t.setId(rootNode.getId()+"");
//            t.setName(rootNode.getText());
//            t.setSelf(rootNode);
//            t.setIsparent(rootNode.getIsparent());
//            treeNodes.add(t);
//        }
//        if(isShowOrg!=null&&isShowOrg){
//            //获取所有机构
//            List<SysOrganization> sysOrganizations = sysOrganizationMapper.getRootNodesByRegion(pid);
//            for (SysOrganization sysOrganization : sysOrganizations) {
//                TreeNode t = new TreeNode();
//                t.setId("org_"+sysOrganization.getId()+"");
//                t.setName(sysOrganization.getName());
//                t.setSelf(sysOrganization);
//                treeNodes.add(t);
//            }
//        }
//        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        Map<String,List<Integer>> queryMap =new HashMap<>();
        if(isrole!=null&&isrole){
            SysUserinfo currUser = sysUserinfoService.getCurrUser();
            currUser= sysUserinfoService.queryUserAndRoleListThroughUsername(currUser.getUsername());
            if(!currUser.getSysRoleinfoTypes().contains("admin")){
                List<Integer> roleIdsListByUserIdAddGroup = sysRoleinfoService.getRoleIdsListByUserIdAddGroup(currUser.getId());
                List<SysLnRoleDataPermission> sysLnRoleDataPermissions =  sysLnRoleDataPermissionService.get(0,roleIdsListByUserIdAddGroup);
                List<Integer> ids = new ArrayList<>();
                for (SysLnRoleDataPermission sysLnRoleDataPermission : sysLnRoleDataPermissions) {
                    ids.add(sysLnRoleDataPermission.getPermId());
                }
                queryMap.put("ids",ids);
            }
        }
        List<SysRegion> sysRegions = mapper.queryListByMap(queryMap);
        Map<String,Object> qmap =new HashMap<>();
        qmap.put("distance",1);
        List<SysRegionTreeClosure> sysRegionTreeClosures = sysRegionTreeClosureService.queryListByMap(qmap);
        Map<String, String> id_Pid = new HashMap<String, String>();
        for (SysRegionTreeClosure sysRegionTreeClosure : sysRegionTreeClosures) {
            if (sysRegionTreeClosure.getDistance() == 1)
                id_Pid.put(sysRegionTreeClosure.getDescendant()+"", sysRegionTreeClosure.getAncestor()==null?"":sysRegionTreeClosure.getAncestor()+"");
        }
        for (SysRegion sysRegion : sysRegions) {
            TreeNode t = new TreeNode();
            t.setId(sysRegion.getId()+"");
            t.setName(sysRegion.getName());
            t.setSelf(sysRegion);
            t.setParentId(id_Pid.get(sysRegion.getId()+""));
            treeNodes.add(t);
        }
        if(isShowOrg!=null&&isShowOrg){
            //获取所有机构
            List<SysOrganization> rootNodesByRegion = sysOrganizationMapper.getRootNodesByRegion(null);

            for (SysOrganization sysOrganization : rootNodesByRegion) {
                TreeNode t = new TreeNode();
                t.setId("org_"+sysOrganization.getId()+"");
                t.setName(sysOrganization.getName());
                t.setParentId(sysOrganization.getRegionId()+"");
                t.setSelf(sysOrganization);
                treeNodes.add(t);
            }
        }
        if(isSimpDate){
            return treeNodes;
        }else{
            List<TreeNode> treeNodes1 = TreeBuilder.buildListToTree(treeNodes);
            for (TreeNode treeNode : treeNodes1) {
                treeNode.setOpen(true);
            }
            return treeNodes1;
        }
    }

    @Override
    public List<Integer> getDataPermByUser(Integer userid){

        List<Integer> roleIdsListByUserIdAddGroup = sysRoleinfoService.getRoleIdsListByUserIdAddGroup(userid);
        List<SysLnRoleDataPermission> sysLnRoleDataPermissions =  sysLnRoleDataPermissionService.get(0,roleIdsListByUserIdAddGroup);
        List<Integer> ids = new ArrayList<>();
        for (SysLnRoleDataPermission sysLnRoleDataPermission : sysLnRoleDataPermissions) {
            ids.add(sysLnRoleDataPermission.getPermId());
        }
        return ids;
    }
	/**
	 * 获取多地市的
	 */
	@Override
	public List<TreeModel> getTree_homeCity_Qx() {
		/* 获取所有的地市  */
		List<TreeModel> homeCityList = mapper.getHomeCityNodes();
		//然后再查找地级市下的区县
		Iterator<TreeModel> it = homeCityList.iterator();
		int i = 0 ;
		while(it.hasNext()){
			TreeModel treeModel = it.next();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("homeCityId", treeModel.getHomeCityId());
			treeModel.setAttributes(map);
			if(i == 0){
				treeModel.setChecked(true);
				treeModel.setState("open");
			}else{
				treeModel.setChecked(false);
				treeModel.setState("closed");
			}
			i++;
			//获取区县结构
			List<TreeModel> nextCityList = mapper.getNextCityNodes(treeModel.getId());
			treeModel.setChildren(nextCityList);
		}
		return homeCityList;
	}

    @Override
    public List<SysRegion> getRegionListByUserId(Integer userId) {
        return mapper.getRegionListByUserId(userId);
    }

    @Override
    public List<TreeNode> getRegionTree() throws Exception {
        //获取所有地市
        List<TreeNode> treeNodes = new ArrayList<>();
        List<SysRegion> sysRegions = mapper.queryList();
        for (SysRegion sysRegion : sysRegions) {
            TreeNode treeNode = new TreeNode();
            treeNode.setId(sysRegion.getId().toString());
            treeNode.setName(sysRegion.getName());
            treeNode.setSelf(sysRegion);
            treeNode.setParentId(sysRegion.getParentid() == null ? null : sysRegion.getParentid().toString());
            treeNodes.add(treeNode);
        }
        List<TreeNode> treeNodes1 = TreeBuilder.buildListToTree(treeNodes);
//        for (TreeNode treeNode : treeNodes1) {
//            treeNode.setOpen(true);
//        }
        return treeNodes1;
    }
}
