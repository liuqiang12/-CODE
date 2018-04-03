package system.systemlog;

import com.idc.model.SysOperateLog;
import com.idc.model.SysUserinfo;
import com.idc.service.SysOperateLogService;
import com.idc.service.SysUserinfoService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import utils.typeHelper.MapHelper;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 参数说明：${0}...{N}.key
 * Created by mylove on 2016/12/14.
 */
@Component
@Aspect
public class SystemLogAspect {
    @Resource
    private SysOperateLogService sysOperateLogService;
    @Resource
    private SysUserinfoService sysUserinfoService;
    private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);

    @Pointcut("@annotation(system.systemlog.SystemLog)")
    public void serviceAspect() {
    }
    //    @After(value = "execution(* com.idc..service.impl.*Impl.*(..))")
//    public void after() {
//        System.out.println("after.....................");
//    }
    @After("serviceAspect()")
    public void doBefore(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        //读取session中的用户
//        User user = (User) session.getAttribute(WebConstants.CURRENT_USER);
        //请求的IP
        String ip = request.getRemoteAddr();
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();
            //*========控制台输出=========*//
            System.out.println("=====前置通知开始=====");
            System.out.println("请求方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
//                    System.out.println("方法描述:" + getControllerMethodDescription(joinPoint));
            System.out.println("请求人:" + userDetails.getUsername());
            System.out.println("请求IP:" + ip);
            //*========数据库日志=========*//
            SysOperateLog sysOperateLog = getControllerMethodDescription(joinPoint);
            sysOperateLog.setCreateTime(new Timestamp(new Date().getTime()));
            Map<String,Object> map = new HashMap<>();
            map.put("username",userDetails.getUsername());
            map.put("password",userDetails.getPassword());
            SysUserinfo modelByMap = sysUserinfoService.getModelByMap(map);
            sysOperateLog.setUserId(modelByMap.getId());
            //保存数据库
            sysOperateLogService.insert(sysOperateLog);
            System.out.println("=====前置通知结束=====");
        } catch (Exception e) {
            //记录本地异常日志
            logger.error("==前置通知异常==");
            logger.error("异常信息:{}", e.getMessage());
        }
    }
    @AfterThrowing(pointcut = "serviceAspect()", throwing = "e")
    public  void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        System.out.println(22222);
    }
    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public static SysOperateLog getControllerMethodDescription(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        SysOperateLog sysOperateLog = new SysOperateLog();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    String description = method.getAnnotation(SystemLog.class).description();
                    description =  ConverParmar(description,arguments);
                    sysOperateLog.setDescription(description);
                    sysOperateLog.setType(BigDecimal.valueOf(method.getAnnotation(SystemLog.class).type()));
                    break;
                }
            }
        }
        return sysOperateLog;
    }

    /***
     *TODO 替换里面的参数${0}N
     */
    private static String ConverParmar(String description, Object[] arguments)throws Exception{
        Pattern pattern = Pattern.compile("\\$\\{(\\d+(\\.\\w+)*)\\}");
        Matcher matcher = pattern.matcher(description);
        while (matcher.find()){

            try {
                String pname = matcher.group(1);
                String[] split = pname.split("\\.");
                Integer pindex = Integer.parseInt(split[0]);
                if(pindex>arguments.length){
                    throw new Exception("参数数目不正确");
                }
                Object argument = arguments[pindex - 1];
                String tmp="";
                if(argument!=null){
                    String[] des =new String[split.length-1];
                    System.arraycopy(split,1,des,0,split.length-1);
                    Object value = getValue(des, argument);
                    if(value!=null){
                        tmp = value.toString();
                    }
                }
                description = description.replace(matcher.group(0),tmp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return description;
    }

    private static Object getValue(String[] name,Object o){
        if(name.length==0)return o;
        String beanname = name[0];
        try {
            Map<String, Object> objectMap = MapHelper.beanToMap(o);
            if(objectMap!=null){
                Object o1 = objectMap.get(beanname);
                if(name.length==1){
                    return o1;
                }else{
                    String[] newnames =new String[name.length-1];
                    System.arraycopy(name,1,newnames,0,name.length-1);
                    return getValue(newnames,o1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
//    /* 异常通知 用于拦截service层记录异常日志
//    *
//     * @param joinPoint
//    * @param e
//    */
//    @AfterThrowing(pointcut = "serviceAspect()", throwing = "e")
//    public  void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        HttpSession session = request.getSession();
//        //读取session中的用户
//        User user = (User) session.getAttribute(WebConstants.CURRENT_USER);
//        //获取请求ip
//        String ip = request.getRemoteAddr();
//        //获取用户请求方法的参数并序列化为JSON格式字符串
//        String params = "";
//        if (joinPoint.getArgs() !=  null && joinPoint.getArgs().length > 0) {
//            for ( int i = 0; i < joinPoint.getArgs().length; i++) {
//                params += JSONUtil.toJsonString(joinPoint.getArgs()[i]) + ";";
//            }
//        }
//        try {
//              /*========控制台输出=========*/
//            System.out.println("=====异常通知开始=====");
//            System.out.println("异常代码:" + e.getClass().getName());
//            System.out.println("异常信息:" + e.getMessage());
//            System.out.println("异常方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
//            System.out.println("方法描述:" + getServiceMthodDescription(joinPoint));
//            System.out.println("请求人:" + user.getName());
//            System.out.println("请求IP:" + ip);
//            System.out.println("请求参数:" + params);
//               /*==========数据库日志=========*/
//            Log log = SpringContextHolder.getBean("logxx");
//            log.setDescription(getServiceMthodDescription(joinPoint));
//            log.setExceptionCode(e.getClass().getName());
//            log.setType("1");
//            log.setExceptionDetail(e.getMessage());
//            log.setMethod((joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
//            log.setParams(params);
//            log.setCreateBy(user);
//            log.setCreateDate(DateUtil.getCurrentDate());
//            log.setRequestIp(ip);
//            //保存数据库
//            logService.add(log);
//            System.out.println("=====异常通知结束=====");
//        }  catch (Exception ex) {
//            //记录本地异常日志
//            logger.error("==异常通知异常==");
//            logger.error("异常信息:{}", ex.getMessage());
//        }
//         /*==========记录本地异常日志==========*/
//        logger.error("异常方法:{}异常代码:{}异常信息:{}参数:{}", joinPoint.getTarget().getClass().getName() + joinPoint.getSignature().getName(), e.getClass().getName(), e.getMessage(), params);
//
//    }

}
