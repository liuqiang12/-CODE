package system.authority.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
public class PwdAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
	private String faildPage;
	//需要用户角色管理器处理
	private AuthenticationManager authenticationManager;
	
    public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	/** 
     * 必须要实现无参构造 
     * spring项目名称、loginCheck访问路径 
     */  
    protected PwdAuthenticationProcessingFilter() {  
        super("/login.do");//设置访问路径  
    }  
  
    @Override  
    public Authentication attemptAuthentication(HttpServletRequest request,  
            HttpServletResponse response) throws AuthenticationException,  
            IOException, ServletException {  
    	System.out.println(request.getParameterNames());
    	/**
    	 * 只有三个参数被表单提交上来了[用户名、密码、验证码]
    	 */
        String id = request.getParameter("id");  
        String name = request.getParameter("username");  
        String pwd = request.getParameter("password");  
          
        PwdAuthenticationToken token = new PwdAuthenticationToken();  
        token.setIdCode(id);  
        token.setUserName(name);  
        token.setPassword(pwd);  
          
        Authentication auth = null;  
          
        try {  
            auth = this.getAuthenticationManager().authenticate(token);  
        } catch (AuthenticationException e) {  
            e.printStackTrace();  
        }  
        return auth;  
    }  
  
    @Override  
    public void afterPropertiesSet() {  
        super.afterPropertiesSet();  
          
//        AuthenticationFaildPage  faild = new AuthenticationFaildPage();  
//        faild.setFaildPage(faildPage);  
          
//        this.setAuthenticationFailureHandler(faild);  
          
    }  
  
    public String getFaildPage() {  
        return faildPage;  
    }  
  
    public void setFaildPage(String faildPage) {  
        this.faildPage = faildPage;  
    }  
}
