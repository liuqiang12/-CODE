package system.authority.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.idc.model.SysOperateLog;
import com.idc.service.SysOperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import utils.typeHelper.EncryptUtil;

import com.idc.model.SysUserinfo;
import com.idc.service.SysUserinfoService;
import com.octo.captcha.service.image.ImageCaptchaService;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 自定义的登录类
 * 
 * @author Administrator
 * 
 */
public class MyUsernamePasswordAuthenticationFilter extends
		UsernamePasswordAuthenticationFilter {
	public static final String VALIDATE_CODE = "validateCode";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	private int showCheckCode = 0;

	private PasswordEncoder passwordEncoder;

	private SysUserinfoService sysUserinfoService;
    private SysOperateLogService sysOperateLogService;
	
	@Autowired
    private ImageCaptchaService imageCaptchaService;

	public int getShowCheckCode() {
		return showCheckCode;
	}

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public SysUserinfoService getSysUserinfoService() {
		return sysUserinfoService;
	}

	public void setSysUserinfoService(SysUserinfoService sysUserinfoService) {
		this.sysUserinfoService = sysUserinfoService;
	}

    public SysOperateLogService getSysOperateLogService() {
        return sysOperateLogService;
    }

    public void setSysOperateLogService(SysOperateLogService sysOperateLogService) {
        this.sysOperateLogService = sysOperateLogService;
    }

    public void setShowCheckCode(int showCheckCode) {
		this.showCheckCode = showCheckCode;
	}

	@SuppressWarnings("unused")
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException(
					"Authentication method not supported: "
							+ request.getMethod());
		}
        HttpSession session = request.getSession();
		// 检测验证码
        try {
            checkValidateCode(request);
        } catch (Exception e) {
            session.setAttribute("showCheckCode", "1");
            session.setAttribute("SECURITY_LOGIN_EXCEPTION", "验证码错误！");
            throw new AuthenticationServiceException("验证码错误！");
        }

        String username = obtainUsername(request);
		String password = obtainPassword(request);

		// 验证用户账号与密码是否对应
		username = username.trim();

		SysUserinfo users = sysUserinfoService.queryUserAndRoleListThroughUsername(username);

		Date nowDate = new Date();

		session = request.getSession(false);// false代表不创建新的session，直接获取当前的session

		// 将用户名存进session，如果登录成功，显示在主页
		session.setAttribute("login_account", username);
		//密码匹配类
		EncryptUtil encryptUtil = new EncryptUtil();
		if (users == null) {

			session.setAttribute("showCheckCode", "1");
			session.setAttribute("SECURITY_LOGIN_EXCEPTION", "用户名或密码错误！");
			session.setMaxInactiveInterval(1);

			throw new AuthenticationServiceException("用户名或密码错误！");

		} else if (users.getPassword() == "" || users.getPassword() == null) {

			session.setAttribute("showCheckCode", "1");
			session.setAttribute("SECURITY_LOGIN_EXCEPTION", "用户名或密码错误！");
			session.setMaxInactiveInterval(1);

			throw new AuthenticationServiceException("用户名或密码错误！");

		} else if (!encryptUtil.matches(password, users.getPassword())) {// password加密后再进行验证
			//密码随时都在变动,但是通过springSecurity的加密后始终是可以匹配的
			session.setAttribute("showCheckCode", "1");
			session.setAttribute("SECURITY_LOGIN_EXCEPTION", "用户名或密码错误！");
			session.setMaxInactiveInterval(1);

			throw new AuthenticationServiceException("用户名或密码错误！");

		} else if(users.getLoseEfficacyTime().getTime()<nowDate.getTime()){
			session.setAttribute("showCheckCode", "1");
			session.setAttribute("SECURITY_LOGIN_EXCEPTION", "密码已失效，请联系管理员！");
			session.setMaxInactiveInterval(1);

			throw new AuthenticationServiceException("密码已失效，请联系管理员！");
		}else {

			if(users.getLoseEfficacyTime().getTime()<(nowDate.getTime()+3*24*60*60*1000L)){
				session.setAttribute("showCheckCode", "1");
				session.setAttribute("SECURITY_LOGIN_EXCEPTION", "密码即将失效，是否修改密码！");
			}else{
				if (session.getAttribute("showCheckCode") == "1") {
					session.setAttribute("showCheckCode", "0");
				}
			}
		}
        SysOperateLog sysOperateLog = new SysOperateLog();
        sysOperateLog.setUserId(users.getId());
        sysOperateLog.setDescription(users.getUsername() + "登陆成功");
        sysOperateLog.setType(new BigDecimal(1004));
		sysOperateLog.setCreateTime(new Timestamp(new Date().getTime()));
        try {
            session.setAttribute("userInfo",users );
            session.setAttribute("userid",users.getId() );
            sysOperateLogService.insert(sysOperateLog);
        } catch (Exception e) {
            logger.error("插入登陆日志失败",e);
        }
        // UsernamePasswordAuthenticationToken实现 Authentication
		// 这里要注意了，我第二个参数是用自己的md5加密了密码再去传参的，因为我的密码都是加密后存进数据库的。
		// 如果这里不加密，那么和在数据库取出来的不匹配，最终即使登录账号和密码都正确，也将无法登录成功。
		// 因为在AbstractUserDetailsAuthenticationProvider里还会对用户和密码验证，分别是
		// user = retrieveUser(username, (UsernamePasswordAuthenticationToken)
		// authentication);//这个通过才能顺利通过
		// 另一个是 additionalAuthenticationChecks(user,
		// (UsernamePasswordAuthenticationToken)
		// authentication);//如果retrieveUser方法验证不通过，将无法访问
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, users.getPassword());
		// Place the last username attempted into HttpSession for views

		// 允许子类设置详细属性
		setDetails(request, authRequest);

		// 运行UserDetailsService的loadUserByUsername 再次封装Authentication
		return this.getAuthenticationManager().authenticate(authRequest);
	}
	/**
	 * 验证码验证
	 * @param request
	 */
	
	protected void checkValidateCode(HttpServletRequest request) {
		HttpSession session = request.getSession();

//		String sessionValidateCode = obtainSessionValidateCode(session);
		
//		if (session.getAttribute("showCheckCode") == "1") {

			// 让上一次的验证码失效
			session.setAttribute(VALIDATE_CODE, null);
//			String validateCodeParameter = obtainValidateCodeParameter(request);
			String captchaId = request.getSession().getId();
	        String code = request.getParameter(VALIDATE_CODE);
	        if(code != null && !"".equals(code)){
	        	code = code.toLowerCase();
	        }

	        Boolean isCorrect = imageCaptchaService.validateResponseForID(captchaId, code);

	        if (!isCorrect){
	            throw new AuthenticationServiceException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCaptcha", "Bad captcha"));
	        }
	        
			// 判断输入的验证码和保存在session中的验证码是否相同，这里不区分大小写进行验证
//			if (StringUtils.isEmpty(validateCodeParameter)
//					|| !sessionValidateCode
//							.equalsIgnoreCase(validateCodeParameter)) {
//
//				session = request.getSession(false);// false代表不创建新的session，直接获取当前的session
//				session.setAttribute("SECURITY_LOGIN_EXCEPTION", "验证码错误");
//
//				throw new AuthenticationServiceException("验证码错误！");
//			}
//		}
	}

	private String obtainValidateCodeParameter(HttpServletRequest request) {
		Object obj = request.getParameter(VALIDATE_CODE);
		return null == obj ? "" : obj.toString();
	}

	protected String obtainSessionValidateCode(HttpSession session) {
		Object obj = session.getAttribute(VALIDATE_CODE);
		return null == obj ? "" : obj.toString();
	}

	@Override
	protected String obtainUsername(HttpServletRequest request) {
		Object obj = request.getParameter(USERNAME);
		return null == obj ? "" : obj.toString();
	}

	@Override
	protected String obtainPassword(HttpServletRequest request) {
		Object obj = request.getParameter(PASSWORD);
		return null == obj ? "" : obj.toString();
	}
}
