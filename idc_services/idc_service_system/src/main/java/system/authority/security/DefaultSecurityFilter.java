package system.authority.security;

import com.idc.service.SysMenuinfoService;
import com.idc.service.SysUserinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 自己实现的过滤用户请求类，也可以直接使用 FilterSecurityInterceptor
 */
@Service
public class DefaultSecurityFilter extends AbstractSecurityInterceptor implements Filter {
    //与applicationContext-security.xml里的myFilter的属性securityMetadataSource对应，
    //其他的两个组件，已经在AbstractSecurityInterceptor定义
    @Autowired
    private DefaultSecurityMetadataSource securityMetadataSource;
    @Autowired
    private DefaultAccessDecisionManager accessDecisionManager;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private SysUserinfoService userService;
    @Autowired
    private SysMenuinfoService sysMenuinfoService;
//    @Autowired(required=false)
//    private StringRedisTemplate stringRedisTemplate;

    private List<String> admins = null;
    @PostConstruct
    public void init(){
//		System.err.println(" ---------------  MySecurityFilter init--------------- ");
        super.setAuthenticationManager(authenticationManager);
        super.setAccessDecisionManager(accessDecisionManager);
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        String url = httpRequest.getRequestURI().replaceFirst(httpRequest.getContextPath(), "");
        if(admins==null||ConfigAttributeHelper.UserIsModify){
            admins = new ArrayList<>();
            /* 【所有admin用户的名称】 */
            admins = userService.getAdminNams();

            /*List<SysUserinfo> sysUserinfos = userService.queryList();
            for (SysUserinfo sysUserinfo : sysUserinfos) {
                sysUserinfo = userService.queryUserAndRoleListThroughUsername(sysUserinfo.getUsername());
                if(sysUserinfo!=null&&sysUserinfo.getSysRoleinfoTypes().contains("admin")) {
                    admins.add(sysUserinfo.getName());
                }
            }*/
        }
        ///  1）过用户白名单：如果为超级管理员，则直接执行
        if(admins!=null&&admins.contains(SessionUserDetailsUtil.getLoginUserName())) {
            chain.doFilter(request, response);
            return;
        }
//        List<SysMenuinfo> sysMenuinfos = sysMenuinfoService.queryList();
//        Map<Object, SysMenuinfo> menuinfos = new HashMap<>();
//        try {
//            menuinfos = ListHelper.ListToMap(sysMenuinfos, "url");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println(url);
//        if(!menuinfos.containsKey(url)){
//            chain.doFilter(request, response);
//            return;
//        }
        FilterInvocation fi = new FilterInvocation(request, response, chain);
//		String isOpenCas = SystemProperties.getProperty("isOpenCas");
//		if(isOpenCas!=null&&isOpenCas.equals("true")){
//			if(fi.getRequest().getRemoteUser()!=null&&fi.getRequest().getSession().getAttribute("userSession")==null){
//				User user = new User();
//				user.setUserName(fi.getRequest().getRemoteUser());
//				try {
//					user = (User)userService.find(user).get(0);
//				} catch (Exception e) {
//					// TODO 自动生成的 catch 块
//					e.printStackTrace();
//				}
//				fi.getRequest().getSession().setAttribute("userSession", user);
//				stringRedisTemplate.opsForValue().set("loginUserSessionId:"+fi.getRequest().getSession().getId(), user.getUserId());
//			}
//		}
        String loginUserSessionIdJcqxUpdate = "loginUserSessionIdJcqxUpdate:" + fi.getRequest().getSession().getId();
//		if(stringRedisTemplate.hasKey(loginUserSessionIdJcqxUpdate)){
//			String jcqxUpdate = stringRedisTemplate.opsForValue().get(loginUserSessionIdJcqxUpdate);
//			if(jcqxUpdate!=null&&jcqxUpdate.equals("yes")){
//				fi.getResponse().sendRedirect(fi.getRequest().getContextPath()+"/j_spring_security_logout");
//			}
//		}
        invoke(fi);
    }

    private void invoke(FilterInvocation fi) throws IOException, ServletException {
        // object为FilterInvocation对象
        //super.beforeInvocation(fi);源码
        //1.获取请求资源的权限
        //执行Collection<ConfigAttribute> attributes = SecurityMetadataSource.getAttributes(object);
        //2.是否拥有权限
        //this.accessDecisionManager.decide(authenticated, object, attributes);
//		System.err.println(" ---------------  MySecurityFilter invoke--------------- ");
        InterceptorStatusToken token = super.beforeInvocation(fi);
		/*System.out.println(fi.getRequest().getSession().getId());*/
        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    public void init(FilterConfig arg0) throws ServletException {
    }

    public void destroy() {

    }

    @Override
    public Class<? extends Object> getSecureObjectClass() {
        //下面的MyAccessDecisionManager的supports方面必须放回true,否则会提醒类型错误
        return FilterInvocation.class;
    }
}