package system.data.cache.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * <b>描述<b>：requent缓存类
 */
public class RequestCache {

    private static ThreadLocal<Map<String,Object>> REQUESTLOCAL = new ThreadLocal<Map<String,Object>>();

    private static Map<String,Object> init(){
        if(REQUESTLOCAL.get()==null){
            Map<String, Object> map= new HashMap<String, Object>();
            REQUESTLOCAL.set(map);
            return map;
        }
        return REQUESTLOCAL.get();
    }

    /**
     * 此requent为spring接管后的requent
     * @return
     */
    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) REQUESTLOCAL.get().get("request");
    }

    /**
     * 此requent为spring接管后的requent
     * @return
     */
    public static void setRequest(HttpServletRequest request) {
        Map<String,Object> map= init();
        map.put("request", request);
    }

    /**
     * 此requent为原始requent
     * @return
     */
    public static HttpServletRequest getOriginalRequest() {
        return (HttpServletRequest) REQUESTLOCAL.get().get("originalRequest");
    }

    /**
     * 此requent为原始requent
     *
     * @return
     */
    public static void setOriginalRequest(HttpServletRequest request) {
        Map<String, Object> map = init();
        map.put("originalRequest", request);
    }

    public static HttpServletResponse getResponse() {
        return (HttpServletResponse) REQUESTLOCAL.get().get("response");
    }


    public static void setResponse(HttpServletResponse response) {
        Map<String, Object> map=init();
        map.put("response", response);
    }

    public static void clear() {
        REQUESTLOCAL.remove();
    }

}
