package system.authority.security;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
/**
 * 自己实现的过滤用户请求类
 */
@Service
public class DefaultAccessDecisionManager implements AccessDecisionManager {
	/**
	 * 基于请求的上下文和安全配置，允许AccessDecisionManager去核实访问是否被允许以及请求是否能够被接受。decide方法实际上没有返回值，通过抛出异常来表明对请求访问的拒绝
	 */
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
//		System.err.println(" ---------------  MyAccessDecisionManager --------------- ");
		
		if(configAttributes == null) {
			return;
		}
		//所请求的资源拥有的权限(一个资源对多个权限)
		Iterator<ConfigAttribute> iterator = configAttributes.iterator();
		while(iterator.hasNext()) {
			ConfigAttribute configAttribute = iterator.next();
			//访问所请求资源所需要的权限
			String needPermission = configAttribute.getAttribute();
			//System.out.println("needPermission is " + needPermission);
			//用户所拥有的权限authentication
			for(GrantedAuthority ga : authentication.getAuthorities()) {
				System.out.println(ga.getAuthority());
				if(needPermission.equals(ga.getAuthority())) {
					return;
				}
			}
		}
		//没有权限
		throw new AccessDeniedException(" 没有权限访问！ ");
	}

	public boolean supports(ConfigAttribute attribute) {
		// TODO Auto-generated method stub
		return true;
	}
	/**
	 * 允许AccessDecisionManager的实现类判断是否支持当前的请求
	 */
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return true;
	}
	
}