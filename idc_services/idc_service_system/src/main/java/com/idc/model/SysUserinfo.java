package com.idc.model;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>sys_userinfo:系统用户信息表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 21,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class SysUserinfo implements Serializable, UserDetails,Authentication {

    public static final String tableName = "sys_userinfo";


    public SysUserinfo() {
        super();
    }

    @Id
    @GeneratedValue
    private Integer id; //   主键
    private String username; //   用户名称
    private Integer region; //   用户名称
    private String password; //   登录用户密码(采用bcrpt其中salt可以是随机的提高安全性)
    private Boolean enabled; //   激活状态:1激活、0未激活、2已锁状态、3可删除、4不可删除
    private String email;
    private String nick; //   用户昵称
    private Integer age; //   年龄
    private String nativeStr; //   籍贯
    private String sex; //   性别
    private Integer count; //   登陆次数
    private Boolean isDepartLeading; //   部门负责人
    private Boolean accountnonlocked; //   1账号锁定0未锁定
    private Boolean credentialsnonexpired; //   1证书没有过期；0过期
    private Boolean accountnonexpired; //   1账户没有过期；0过期
    private Timestamp createTime; //   创建时间
    private String createTimeStr; //   创建时间 [日期格式化后的字符串]
    private String phone;//联系电话
    private Integer department;//所属部门
    private String regions;//流程使用 工单匹配区域信息
    private Timestamp loseEfficacyTime;//账号失效时间
    private String locationCode;//用户归属数据中心
    private List<String> sysRoleinfoTypes = new ArrayList<String>();//用户所关联的所有角色[本来用户和角色是多对多关系;但是我们只关心单独用户有哪些角色的问题]必须是字符串
    private List<SysRoleinfo> sysRoleinfos;
    private String roles_user;//用户对应的角色名称
    private Collection<? extends GrantedAuthority> authorities; //暂存权限

    // 以上五个springSecurity需要使用
    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
/************************get set method**************************/

    /**********
     * spring security 使用 start
     ***********/

    public SysUserinfo(Integer id, String username, String password, Boolean enabled) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    public SysUserinfo(Integer id, String username, String password, boolean enabled,
                       boolean accountNonExpired, boolean credentialsNonExpired,
                       boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,List<SysRoleinfo> sysRoleinfos,String phone,String regions,String roles_user,String nick) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
        this.sysRoleinfos = sysRoleinfos;
        this.phone = phone;
        this.regions = regions;
        this.roles_user = roles_user;
        this.nick = nick;
    }
    private static SortedSet<GrantedAuthority> sortAuthorities(
            Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
        // Ensure array iteration order is predictable (as per
        // UserDetails.getAuthorities() contract and SEC-717)
        SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<GrantedAuthority>(
                new SysUserinfo.AuthorityComparator());

        for (GrantedAuthority grantedAuthority : authorities) {
            Assert.notNull(grantedAuthority,
                    "GrantedAuthority list cannot contain any null elements");
            sortedAuthorities.add(grantedAuthority);
        }

        return sortedAuthorities;
    }


    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return enabled==null?true:enabled;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**********
     * spring security 使用 end
     ***********/

    public Integer getId() {
        return this.id;
    }

    @Column(name = "id", columnDefinition = "主键", nullable = false)
    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "username", columnDefinition = "用户名称", nullable = true, length = 30)
    public void setUsername(String username) {
        this.username = username;
    }

    public String getNick() {
        return this.nick;
    }

    @Column(name = "name", columnDefinition = "用户昵称", nullable = true, length = 50)
    public void setNick(String nick) {
        this.nick = nick;
    }

    public Integer getAge() {
        return this.age;
    }

    @Column(name = "age", columnDefinition = "年龄", nullable = true)
    public void setAge(Integer age) {
        this.age = age;
    }

    @Column(name = "password", columnDefinition = "登录用户密码(采用bcrpt其中salt可以是随机的提高安全性)", nullable = true, length = 60)
    public void setPassword(String password) {
        this.password = password;
    }

    public String getNativeStr() {
        return this.nativeStr;
    }

    @Column(name = "native_str", columnDefinition = "籍贯", nullable = true, length = 50)
    public void setNativeStr(String nativeStr) {
        this.nativeStr = nativeStr;
    }

    public String getSex() {
        return this.sex;
    }

    @Column(name = "sex", columnDefinition = "性别", nullable = true, length = 4)
    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getCount() {
        return this.count;
    }

    @Column(name = "count", columnDefinition = "登陆次数", nullable = true)
    public void setCount(Integer count) {
        this.count = count;
    }

    public Boolean getIsDepartLeading() {
        return this.isDepartLeading;
    }

    @Column(name = "is_depart_leading", columnDefinition = "部门负责人", nullable = true)
    public void setIsDepartLeading(Boolean isDepartLeading) {
        this.isDepartLeading = isDepartLeading;
    }


    @Column(name = "accountEnabled", columnDefinition = "激活状态:1激活、0未激活、2已锁状态、3可删除、4不可删除", nullable = true)
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }


    @Column(name = "accountLocked", columnDefinition = "1账号锁定0未锁定", nullable = true)
    public void setAccountnonlocked(Boolean accountnonlocked) {
        this.accountnonlocked = accountnonlocked;
    }

    @Column(name = "credentialsExpired", columnDefinition = "1证书没有过期；0过期", nullable = true)
    public void setCredentialsnonexpired(Boolean credentialsnonexpired) {
        this.credentialsnonexpired = credentialsnonexpired;
    }

    @Column(name = "accountExpired", columnDefinition = "1账户没有过期；0过期", nullable = true)
    public void setAccountnonexpired(Boolean accountnonexpired) {
        this.accountnonexpired = accountnonexpired;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    @Column(name = "create_time", columnDefinition = "创建时间", nullable = false)
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getRegion() {
        return region;
    }
    @Column(name = "region_id", columnDefinition = "区域", nullable = false)
    public void setRegion(Integer region) {
        this.region = region;
    }

    public String getCreateTimeStr() {
        return this.createTimeStr;
    }

    public void setCreateTimeStr(Timestamp createTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date_ = null;
        try {
            date_ = sdf.format(createTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.createTimeStr = date_;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public Timestamp getLoseEfficacyTime() {
        return loseEfficacyTime;
    }

    public void setLoseEfficacyTime(Timestamp loseEfficacyTime) {
        this.loseEfficacyTime = loseEfficacyTime;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((this.username == null) ? 0 : this.username.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof SysUserinfo))
            return false;
        SysUserinfo other = (SysUserinfo) obj;
        if (id != other.id)
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }


    @Override
    public String toString() {
        return "sysUserinfo [id = " + this.id + ",username = " + this.username + ",nick = " + this.nick + ",age = " + this.age + ",password = " + this.password +
                ",nativeStr = " + this.nativeStr + ",sex = " + this.sex + ",count = " + this.count + ",isDepartLeading = " + this.isDepartLeading + ",enabled = " + this.enabled +
                ",accountnonlocked = " + this.accountnonlocked + ",credentialsnonexpired = " + this.credentialsnonexpired + ",accountnonexpired = " + this.accountnonexpired +
                ",createTime = " + this.createTime + "phone = " + this.phone + ",department = " + this.department + ",email=" + this.email + "]";
    }


    //判断是否验证
    private boolean authenticated = false;

    public Object getCredentials() {
        return null;
    }

    public Object getDetails() {
        return null;
    }

    public Object getPrincipal() {
        return this.username;
    }

    /**
     * 是否已验证
     */
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    public void setAuthenticated(boolean arg0) throws IllegalArgumentException {
        this.authenticated = arg0;
    }

    public String getName() {
        // TODO Auto-generated method stub
        return this.username;
    }

    public List<String> getSysRoleinfoTypes() {
        return sysRoleinfoTypes;
    }

    public void setSysRoleinfoTypes(List<String> sysRoleinfoTypes) {
        this.sysRoleinfoTypes = sysRoleinfoTypes;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public List<SysRoleinfo> getSysRoleinfos() {
        return sysRoleinfos;
    }

    public void setSysRoleinfos(List<SysRoleinfo> sysRoleinfos) {
        this.sysRoleinfos = sysRoleinfos;
    }

    /**
     * 内部类
     */
    private static class AuthorityComparator implements Comparator<GrantedAuthority>,
            Serializable {
        private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            // Neither should ever be null as each entry is checked before adding it to
            // the set.
            // If the authority is null, it is a custom authority and should precede
            // others.
            if (g2.getAuthority() == null) {
                return -1;
            }

            if (g1.getAuthority() == null) {
                return 1;
            }

            return g1.getAuthority().compareTo(g2.getAuthority());
        }
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRegions() {
        return regions;
    }

    public void setRegions(String regions) {
        this.regions = regions;
    }

    public String getRoles_user() {
        return roles_user;
    }

    public void setRoles_user(String roles_user) {
        this.roles_user = roles_user;
    }
}
