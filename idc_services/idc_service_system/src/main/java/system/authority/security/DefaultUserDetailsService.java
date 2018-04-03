package system.authority.security;

import com.idc.mapper.SysPermissioninfoMapper;
import com.idc.mapper.SysUserinfoMapper;
import com.idc.model.*;
import com.idc.service.SysOperateService;
import com.idc.service.SysPermissioninfoService;
import com.idc.service.SysRoleinfoService;
import com.idc.service.SysUserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utils.strategy.code.utils.CodeResourceUtil;

import java.util.ArrayList;
import java.util.List;

@Service("defaultUserDetailsService")
public class DefaultUserDetailsService implements UserDetailsService {

    @Autowired
    private SysUserinfoMapper userMapper;
    @Autowired
    private SysPermissioninfoMapper sysPermissioninfoMapper;
    @Autowired
    private SysPermissioninfoService sysPermissioninfoService;
    @Autowired
    private SysOperateService sysOperateService;
    @Autowired
    private SysRoleinfoService sysRoleinfoService;
    @Autowired
    private SysUserGroupService sysUserGroupService;

//    @Autowired
//    private JdbcTemplate jdbcTemplate;
    /**
     * 登录用户名 查找用户
     * 1：user == null 用户不存在
     * 2：
     */
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /**
         * 自定义方法：通过用户查询所有的角色信息
         */
        SysUserinfo user = userMapper.queryUserAndRoleListThroughUsername(username);
        if (user == null) {
            System.out.println("User not found");
            throw new UsernameNotFoundException("Username not found");
        }
        //获取: 此处获取角色信息
        List<SysRoleinfo> sysRoleinfos = sysRoleinfoService.getRoleinfosByUserId(user.getId());
        //用户 区域信息
        String region_users = sysRoleinfoService.getRegionsWithUserId(user.getId());
        //用户组信息:所对应的角色信息
        String roles_user = sysRoleinfoService.getRolesWithUserId(user.getId());
        // 给用户添加对应的权限（从数据库中读取，将用户的角色下面对应的所有菜单权限读取出来）
        return new SysUserinfo(
                        user.getId(),
                        user.getUsername(),
                        user.getPassword(),
                        user.isEnabled(),//enabled:true激活状态 false非激活状态
                        true,
                        true,
                        true,
                        getGrantedAuthorities(user),/*这种写法效率很慢，建议换掉:本可以一次性查出来*/
                        sysRoleinfos,
                        user.getPhone(),
                        region_users,
                        roles_user,
                        user.getNick()
                 );
    }

    /**
     * 封装角色
     * 角色、操作、用户组封装  通过用户ID
     * @param sysUserinfo
     * @return
     */
    private List<GrantedAuthority> getGrantedAuthorities(SysUserinfo sysUserinfo) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        /*List<SysPermissioninfo> sysPermissioninfos = sysPermissioninfoService.getAllResourceByUserId(sysUserinfo.getId());
        List<Integer> pids = new ArrayList<Integer>();
        if(sysPermissioninfos!=null)
        *//* 菜单权限。 *//*
        for (SysPermissioninfo sysPermissioninfo : sysPermissioninfos) {
            pids.add(sysPermissioninfo.getId());
        }*/

        for (String roleType : sysUserinfo.getSysRoleinfoTypes()) {
            //角色ID:即角色
            authorities.add(new SimpleGrantedAuthority(CodeResourceUtil.getRolePrefix() + roleType));
        }
        /* 封装操作权限 */
        List<SysOperate> sysOperates = sysOperateService.getListByPermit(sysUserinfo.getId());
        for (SysOperate sysOperate : sysOperates) {
            authorities.add(new SimpleGrantedAuthority(CodeResourceUtil.getRolePrefix() + sysOperate.getOptName()));
        }

        //加入用户组信息
        List<SysUserGroup> sysUserGroups = sysUserGroupService.getListByUserId(sysUserinfo.getId());
        for (SysUserGroup sysUserGroup : sysUserGroups) {
            authorities.add(new SimpleGrantedAuthority(CodeResourceUtil.getRolePrefix() + sysUserGroup.getGrpCode()));
        }
        //模拟数据
        System.out.print("authorities :" + authorities);
        return authorities;
    }

}
