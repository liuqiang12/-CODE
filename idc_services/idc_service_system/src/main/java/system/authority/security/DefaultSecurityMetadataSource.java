package system.authority.security;

import com.idc.mapper.SysMenuinfoMapper;
import com.idc.mapper.SysUserinfoMapper;
import com.idc.model.SysMenuinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;
import utils.strategy.code.utils.CodeResourceUtil;
import utils.typeHelper.StringHelper;

import javax.annotation.PostConstruct;
import java.util.*;


/**
 * 加载资源与权限的对应关系
 * */
@Service
public class DefaultSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private SysMenuinfoMapper sysMenuinfoMapper;
    @Autowired
    private SysUserinfoMapper sysUserinfoMapper;
    public static Map<String, Collection<ConfigAttribute>> resourceMap = null;

    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    public boolean supports(Class<?> clazz) {
        return true;
    }
    /**
     * @PostConstruct是Java EE 5引入的注解，
     * Spring允许开发者在受管Bean中使用它。当DI容器实例化当前受管Bean时，
     * @PostConstruct注解的方法会被自动触发，从而完成一些初始化工作，
     *
     * //加载所有资源与权限的关系
     */
    @PostConstruct
    private void loadResourceDefine() {
//		System.err.println("-----------MySecurityMetadataSource loadResourceDefine ----------- ");
        if (resourceMap == null||ConfigAttributeHelper.isModify) {
            resourceMap = new HashMap<String, Collection<ConfigAttribute>>();

            List<SysMenuinfo> sysMenuinfos = sysMenuinfoMapper.getUrlAuthorities();
            StringHelper stringHelper = StringHelper.getInstance();
            String resourceUrl = "";
//            if(true){
//                Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
//                ConfigAttribute configAttribute = new SecurityConfig(CodeResourceUtil.getRolePrefix() + "");
//                configAttributes.add(configAttribute);
//                resourceMap.put(resourceUrl, configAttributes);
//            }

            for (SysMenuinfo sysMenuinfo : sysMenuinfos) {
//                Set<String> roles  = sysMenuinfoMapper.getSysRoleinfoTypes(sysMenuinfo.getId());
                Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
                // TODO:ZZQ 通过资源名称来表示具体的权限 注意：必须"ROLE_"开头
                // 关联代码：applicationContext-security.xml
                // 关联代码：com.huaxin.security.MyUserDetailServiceImpl#obtionGrantedAuthorities
                Set<String> roles = sysMenuinfo.getSysRoleinfoTypes();
                Iterator<String> rolesIt =  roles.iterator();
                while(rolesIt.hasNext()){
                    String roleinfotype = rolesIt.next();
                    //大写字母
                    ConfigAttribute configAttribute = new SecurityConfig(CodeResourceUtil.getRolePrefix() + roleinfotype);
                    configAttributes.add(configAttribute);
                }
                configAttributes.add(new SecurityConfig(CodeResourceUtil.getRolePrefix() + "admin"));
                configAttributes.add(new SecurityConfig("ROLE_idc_customer_manager"));
                resourceUrl = stringHelper.subAntUrl(sysMenuinfo.getUrl());

                resourceMap.put(resourceUrl, configAttributes);
            }
            //按钮权限



            ConfigAttributeHelper.isModify=false;
        }
    }
    //返回所请求资源所需要的权限
    @SuppressWarnings("unused")
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
//		System.err.println("-----------MySecurityMetadataSource getAttributes ----------- ");
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        //	System.out.println("requestUrl is " + requestUrl);
        if(resourceMap == null||ConfigAttributeHelper.isModify) {
            loadResourceDefine();
        }
        //table_bootstrapTable_layout.do
        //System.err.println("resourceMap.get(requestUrl); "+resourceMap.get(requestUrl));

        StringHelper stringHelper = StringHelper.getInstance();

        requestUrl = stringHelper.subAntUrl(requestUrl);

        Collection<ConfigAttribute> configAttributes = resourceMap.get(requestUrl);
//		if(configAttributes == null){
//			Collection<ConfigAttribute> returnCollection = new ArrayList<ConfigAttribute>(); returnCollection.add(new SecurityConfig(CodeResourceUtil.getRolePrefix()+"NO_USER"));
//			return returnCollection;
//		}
        return configAttributes;
    }


}