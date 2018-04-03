package system.data.supper.service;

import com.alibaba.fastjson.JSONObject;
import system.data.basedata.ResultSupport;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 缓存操作接口
 */
public interface RedisManager {
    Set<String> smembers(String key);
    Boolean exists(String key);
    List<String> findKeysForPage(String patternKey, int pageNum, int pageSize);
    /**
	 * 存放缓存
	 */
    ResultSupport<Boolean> putCache(String key, Object value) throws Exception;
    /**
	 * 存放缓存
	 */
    ResultSupport<Boolean> putHmsetCache(String key, JSONObject json, List<String> incrList) throws Exception;


    /**
	 * 存放缓存
	 */
    Map<String, Object> saddCache(String key, String... members) throws Exception;
    /**
     * 获取缓存数据
     * @return
     * @throws Exception
     */
    Map<String,Object> getSCache(String key) ;
    /**
     * 通过key获取相应的缓存值
     * @param key
     * @return
     */
    ResultSupport<Object> getCacheByKey(String key) throws Exception;
    /**
     * 通过key获取相应的缓存值
     * @param key
     * @return
     */
    List<String> getCacheInitByKey(String key) throws Exception;
    
    /**
     * 判断是否存在对应的值
     * @param key
     * @param fieldkey
     * @param fieldValue
     * @return
     */
    Boolean isfieldValueExist(String key, String fieldkey, String fieldValue) throws Exception;


    ResultSupport<Boolean> putBinaryJedisCache(String key, String field, Object value) throws Exception ;
    ResultSupport<Boolean> putJsonJedisZsetCache(String key, String field, Object value) throws Exception ;
    ResultSupport<Boolean> putJsonJedisCache(String key, String field, Object value) throws Exception;
    byte[] getBinaryJedisCache(String key, String field) throws Exception;
    Set<byte[]> getListBinaryJedisCache(String key, String field) throws Exception;

    ResultSupport<Boolean> putIntoListJedisCache(String key, Object record) throws Exception;
    List<String> getListJedisCache(String key) throws Exception;

    void setData(String key,Map<String,String> value);
//    /**
//     * 执行成功返回success,put成功返回true,put失败返回false
//     * @param key
//     * @param value
//     * @return
//     */
//    ResultSupport<Boolean> putIfNotExist(String key,Object value);
//
//    /**
//     * 带版本put，如果数据老版本号+1==当前版本号且对应版本key没有被put，则put进去
//     * 否则失败
//     * @param key
//     * @param value
//     * @param version
//     * @return
//     */
//    Result<Long> put(String key, Object value, long version);
//    ResultSupport<Long> removeCache(String key);

//    void setSerializableMethod(SerializeHandler handler);
//
//    /**
//     * 计数
//     * @param key
//     * @return
//     */
//    long countUp(String key);
int getPatternKeyCount(String key);
}
