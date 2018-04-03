package system;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import system.rest.ResultObject;
import utils.typeHelper.MapHelper;

import java.util.*;

/**
 * 使用Spring的restTemplate进行http请求
 * Created by DELL on 2017/9/7.
 */
public class RestUtil {
    private static RestTemplate restTemplate = new RestTemplate();

    /**
     * Get方法
     *
     * @param url:地址
     * @param returnClassName:返回对象类型,如:String.class
     * @param parameters:parameter参数
     * @return
     */
    public static <T> T get(RestTemplate restTemplate,String url, Class<T> returnClassName, Map<String, Object> parameters){
        if (parameters == null) {
            return restTemplate.getForObject(url, returnClassName);
        }
        return restTemplate.getForObject(url, returnClassName, parameters);
    }
    public static ResponseEntity<ResultObject> exchange(RestTemplate restTemplate, String url,
                                                        Object targetObj){
        HttpHeaders httpHeaders = new HttpHeaders();
        //设置header
        httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
        Map<String, Object> hashmap = MapHelper.beanToMap(targetObj);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(
                hashmap,
                httpHeaders
        );
        ResponseEntity<ResultObject> resp = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                requestEntity,
                ResultObject.class
        );
        return resp;
    }



    /**
     * post请求,包含了路径,返回类型,Header,Parameter
     *
     * @param url:地址
     * @param returnClassName:返回对象类型,如:String.class
     * @param inputHeader
     * @param inputParameter
     * @param jsonBody
     * @return
     */
    public static <T> T post(String url,Class<T> returnClassName,Map<String,Object> inputHeader,Map<String,Object> inputParameter,String jsonBody){
        //请求Header
        HttpHeaders httpHeaders = new HttpHeaders();
        //拼接Header
        if (inputHeader != null) {
            Set<String> keys = inputHeader.keySet();
            for (Iterator<String> i = keys.iterator(); i.hasNext();) {
                String key = i.next();
                httpHeaders.add(key, inputHeader.get(key).toString());
            }
        }
        //设置请求的类型及编码
        MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8");
        httpHeaders.setContentType(type);
        httpHeaders.add("Accept", "application/json");
        List<MediaType> acceptableMediaTypes = new ArrayList<>();
        acceptableMediaTypes.add(MediaType.ALL);
        httpHeaders.setAccept(acceptableMediaTypes);

        HttpEntity<String> formEntity = new HttpEntity<String>(jsonBody, httpHeaders);
        if (inputParameter==null) {
            return restTemplate.postForObject(url, formEntity, returnClassName);
        }
        return restTemplate.postForObject(url, formEntity, returnClassName, inputParameter);
    }
}
