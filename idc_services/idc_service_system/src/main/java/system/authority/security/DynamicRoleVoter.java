package system.authority.security;

import com.idc.model.SysUserinfo;
import com.idc.service.SysMenuinfoService;
import com.idc.service.SysUserinfoService;
import constant.security.StaticSecurity;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.*;

/**
 * 投票管理器:目前處理是只要有一角色有访问就投票通过
 */
@Component("dynamicRoleVoter")
public class DynamicRoleVoter implements AccessDecisionVoter<Object> {
	Logger logger = LoggerFactory.getLogger(DynamicRoleVoter.class);
	@Autowired
	protected SysMenuinfoService sysMenuinfoService;
	@Autowired
	private SysUserinfoService sysUserinfoService;

	private PathMatcher pathMatcher = new AntPathMatcher();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.vote.AccessDecisionVoter#supports(java.lang
	 * .Class)
	 */
//	指示访问选民决定实施是否能够提供访问控制票显示保护对象的类型
	@SuppressWarnings("unchecked")
	public boolean supports(Class clazz) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.springframework.security.vote.AccessDecisionVoter#supports(org.
	 * springframework.security.ConfigAttribute)
	 */
//	表示此访问决策选民是否能够表决通过配置属性
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.springframework.security.vote.AccessDecisionVoter#vote(org.
	 * springframework.security.Authentication, java.lang.Object,
	 * org.springframework.security.ConfigAttributeDefinition)
	 */
//	表示连接是否批准
	public int vote(Authentication authentication, Object object,Collection<ConfigAttribute> attributes) {


        int result = ACCESS_ABSTAIN;
        // 域需要权限循环
        for (ConfigAttribute attribute : attributes) {
            if (this.supports(attribute)) {
                result = ACCESS_DENIED;
                // 人的权限循环
                for (GrantedAuthority userAuthority : authentication.getAuthorities()) {
                    if (userAuthority.toString().equals(StaticSecurity.ADMIN.getPermission())) {
                        return ACCESS_GRANTED;
                    } else if (userAuthority.toString().equals(attribute.getAttribute())) {
                        // 如果匹配上
                        return ACCESS_GRANTED;
                    }
                }
            }
        }

        logger.trace("attributes : {}", attributes);
        return result;
	}

	protected boolean containTmp(Set<GrantedAuthority> authorities,
			Set<String> mappedAuths) {
		if (CollectionUtils.isEmpty(mappedAuths)
				|| CollectionUtils.isEmpty(authorities))
			return false;
		for (GrantedAuthority item : authorities) {
			if (mappedAuths.contains(item.getAuthority()))
				return true;
		}
		return false;
	}
	protected boolean contain(Set<GrantedAuthority> authorities,
			Set<String> mappedAuths) {
		if (CollectionUtils.isEmpty(mappedAuths)
				|| CollectionUtils.isEmpty(authorities))
			return false;
		for (GrantedAuthority item : authorities) {
			if (mappedAuths.contains(item.getAuthority()))
				return true;
		}
		return false;
	}

	/**
	 * 根据用户获取其相应的权限列表
	 * 
	 * @param user
	 *            用户信息
	 * @return 权限列表
	 */
	protected Set<GrantedAuthority> loadUserAuthorities(SysUserinfo user) {
		List<String> sysauths = user.getSysRoleinfoTypes();
		if (sysauths == null)
			return null;
		Set<GrantedAuthority> results = new HashSet<GrantedAuthority>();
		for (Iterator<String> it = sysauths.iterator(); it.hasNext();) {
			results.add(new SimpleGrantedAuthority(it.next()));
			// results.add(new
			// SimpleGrantedAuthority(authService.getAuthorityPrefix() +
			// it.next().getId()));
		}
		return results;
	}

}
