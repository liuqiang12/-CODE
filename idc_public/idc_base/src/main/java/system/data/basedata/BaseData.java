package system.data.basedata;//package system.data.basedata;
//
//import com.crowdcrystal.model.LnUserRole;
//import com.crowdcrystal.model.SysMenuinfo;
//import com.crowdcrystal.model.SysRoleinfo;
//import com.crowdcrystal.model.SysUserinfo;
//import com.crowdcrystal.service.LnUserRoleService;
//import com.crowdcrystal.service.SysMenuinfoService;
//import com.crowdcrystal.service.SysRoleinfoService;
//import com.crowdcrystal.service.SysUserinfoService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//import utils.typeHelper.EncryptUtil;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
///**
// * Created by mylove on 2016/12/27.
// */
//public class BaseData implements InitializingBean {
//    private static final Logger logger = LoggerFactory.getLogger(BaseData.class);
//    @Autowired
//    private SysMenuinfoService sysMenuinfoService;
//    @Autowired
//    private SysRoleinfoService sysRoleinfoService;
//    @Autowired
//    private SysUserinfoService sysUserinfoService;
//    @Autowired
//    private LnUserRoleService lnUserRoleService;
//    public static void main(String[] args) {
//        //ToDo
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        try {
//            logger.info("修复基础数据");
//            SysRoleinfo sysRoleinfo = repairRole();
//            repairUser(sysRoleinfo);
//            repairMenu(sysRoleinfo);
//        } catch (Exception e) {
//            logger.error("修复系统基础信息失败",e);
//            throw e;
//        }
//    }
//
//    /***
//     * 修复用户 添加基础用户
//     */
//    @Transactional
//    private void repairUser(SysRoleinfo sysRoleinfo) throws Exception{
//        List<SysUserinfo> sysUserinfos = sysUserinfoService.queryList();
//        if(sysUserinfos.size()==0){
//            SysUserinfo userinfo = new SysUserinfo();
//            userinfo.setUsername("ecmpadmin");
//            EncryptUtil util = new EncryptUtil();
//            String hashedPassword = util.encode("ecmpadmin@123");
//            userinfo.setPassword(hashedPassword);
//            userinfo.setAge(0);
//            userinfo.setSex("男");
//            userinfo.setNick("默认管理员");
//            sysUserinfoService.insert(userinfo);
//
//            Map<String,Object> qmap = new HashMap<>();
//            qmap.put("type","admin");//获取管理员角色信息
//            SysRoleinfo modelByMap = sysRoleinfoService.getModelByMap(qmap);
//            LnUserRole lnUserRole = new LnUserRole();
//            lnUserRole.setUserId(userinfo.getId());
//            lnUserRole.setRoleId(modelByMap.getId());
//            lnUserRoleService.insert(lnUserRole);
//        }
//    }
//
//    /***
//     * 修复角色 防止角色被清空找不到管理员情况
//     */
//    @Transactional
//    private SysRoleinfo repairRole()throws Exception{
//        Map<String,Object> qmap = new HashMap<>();
//        qmap.put("type","admin");//获取管理员角色信息
//        SysRoleinfo modelByMap = sysRoleinfoService.getModelByMap(qmap);
//        if(modelByMap==null){
//            modelByMap = new SysRoleinfo();
//            modelByMap.setType("admin");
//            modelByMap.setName("系统管理人员");
//            modelByMap.setDescription("系统管理人员，拥有所有权限");
//            sysRoleinfoService.insert(modelByMap);
//        }
//        return modelByMap;
//    }
//
//    private void repairMenu(SysRoleinfo sysRoleinfo) throws Exception{
//
//        List<SysMenuinfo> sysMenuinfos = sysMenuinfoService.queryList();
//        if(sysMenuinfos.size()==0){
//            SysMenuinfo sysMenuinfo = new SysMenuinfo();
//            sysMenuinfo.setName("系统管理");
//            sysMenuinfo.setSort(9);
//            sysMenuinfo.setUrl("#");
//            sysMenuinfo.setEnabled(1);
//            sysMenuinfo.setDescription("系统管理");//创建基础模块
//            sysMenuinfoService.insert(sysMenuinfo);
//            SysMenuinfo subsysMenuinfo = new SysMenuinfo();
//            subsysMenuinfo.setSort(8);
//            subsysMenuinfo.setName("菜单管理");
//            subsysMenuinfo.setUrl("/sysmenu/index.do");
//            subsysMenuinfo.setParentId(sysMenuinfo.getId());
//            subsysMenuinfo.setDescription("菜单管理");
//            subsysMenuinfo.setEnabled(1);
//            sysMenuinfoService.insert(subsysMenuinfo);
////
////            Map<String,Object> qmap = new HashMap<>();
////            qmap.put("type","admin");//获取管理员角色信息
////            SysRoleinfo modelByMap = sysRoleinfoService.getModelByMap(qmap);
////            LnUserRole  = new LnUserRole();
////            lnUserRole.setUserId(userinfo.getId());
////            lnUserRole.setRoleId(modelByMap.getId());
////            lnUserRoleService.insert(lnUserRole);
//        }
////        Map<String,Object> qmap = new HashMap<>();
////        qmap.put("url","/sysmenu/index.do");//获取菜单信息
////        SysMenuinfo menuinfo = sysMenuinfoService.getModelByMap(qmap);
////        if(menuinfo==null){
////            menuinfo=new SysMenuinfo();
////            menuinfo.setName("系统管理");
////        }
////        if()
//    }
//}
