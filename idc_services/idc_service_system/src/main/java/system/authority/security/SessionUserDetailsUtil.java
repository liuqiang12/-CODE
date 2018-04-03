package system.authority.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by mylove on 2017/1/13.
 */
public class SessionUserDetailsUtil {

    public static String getLoginUserName() {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();
            return userDetails.getUsername();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取用户基本信息[add]
     * @return
     */
    public static UserDetails getLoginInfo() {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();
            return userDetails;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
