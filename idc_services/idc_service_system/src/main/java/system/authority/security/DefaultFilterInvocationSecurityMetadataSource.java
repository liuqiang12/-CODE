package system.authority.security;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

/**
 * 通过存储，并可以识别出适用于特定安全对象调用的配置属性类实现。
 * 对象加载配置信息
 */
@Service("defaultFilterInvocationSecurityMetadataSource")
public class DefaultFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	private AntPathMatcher antPathMatcher = new AntPathMatcher();
	/**
	 * 用来过滤的不做验证的url
	 */
	private List<String> patternList;
	public static void main(String[] args) {
		//以字符"/"开头且去掉点后面的所有字符
		System.out.println("/asdf/sdf".replaceFirst("^/[^/]+", "").replaceFirst("\\..*$", ""));
	}
	// 填写以/开头
	
	
	
	/**
	 * 访问适用于特定安全对象的配置属性。 每次访问时请求路径
	 */
	@Override
	public Collection<ConfigAttribute> getAttributes(Object obj) throws IllegalArgumentException {
		// 过滤器
		HttpServletRequest request = ((FilterInvocation) obj).getRequest();
		// 提交的路径
		String url = request.getRequestURI().replaceFirst("^/[^/]+", "").replaceFirst("\\..*$", "");
		// 读取xml配置文件中的地址，跳过权限验证
		if (null != patternList) {
			for (String path : patternList) {
				if (antPathMatcher.match(path, url)) {
					return null;
//					return SecurityConfig.createList(StaticSecurity.LOGIN_INDEX.toString());
				}
			}
		}
		// 提交的functionID
//		String functionId = request.getParameter("functionId");
//		// 缓存的路径配置
//		IPathPermissionService pathPermissionService = SpringContextUtil.getBean(IPathPermissionService.class);
//		Map<Boolean, Collection<ConfigAttribute>> matching = pathPermissionService.getPathPermissionByURL(url);
//		if (matching != null) {
//			// 判断路径是否标识是CMDB功能路径
//			if (matching.containsKey(true)) {
//
//				// 如果是CMDB路径则根据提交的functionId参数返回对应的权限集合ID
//				if (!StringUtil.isEmpty(functionId)) {
//					// 缓存的功能ID配置
//					IFunctionPermissionService functionPermissionService = SpringContextUtil
//							.getBean(IFunctionPermissionService.class);
//					Collection<ConfigAttribute> configAttribute = functionPermissionService
//							.getFunctionPermissionByFunctionId(functionId);
//					if (null != configAttribute) {
//						return configAttribute;
//					}
//				}
//			} else {
//				// 如果是开发路径则返回路径对应的权限ID集合
//				return matching.get(false);
//			}
//		}
		return SecurityConfig.createList("NO_CONFIG");
	}

	/**
	 * 如果有的话，将返回所有的实现类中定义的配置属性的。 启动时配置的域访问权限
	 */
	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	/**
	 * 指示安全元数据源执行是否能够提供配置属性为指定的安全对象类型。 启动时是否可用拦截器
	 */
	@Override
	public boolean supports(Class<?> ObjClass) {
		return true;
	}

	public List<String> getPatternList() {
		return patternList;
	}

	public void setPatternList(List<String> patternList) {
		this.patternList = patternList;
	}

}
