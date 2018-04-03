package system.authority.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.idc.model.SysRoleinfo;
import com.idc.service.SysRoleinfoService;

@Component
public class RoleToUserProfileConverter implements Converter<Object, SysRoleinfo>{

	@Autowired
	SysRoleinfoService sysRoleinfoService;

	/*
	 * Gets SysRoleinfo by Id
	 * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
	 */
	public SysRoleinfo convert(Object element) {
		Integer id = Integer.parseInt((String)element);
		SysRoleinfo profile= sysRoleinfoService.getModelById(id);
		System.out.println("Profile : "+profile);
		return profile;
	}
	
	/*
	 * Gets UserProfile by type
	 * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
	 */
	/*
	public UserProfile convert(Object element) {
		String type = (String)element;
		UserProfile profile= userProfileService.findByType(type);
		System.out.println("Profile ... : "+profile);
		return profile;
	}
	*/

}