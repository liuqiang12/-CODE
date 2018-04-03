package system.data.supper.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;
import system.data.basedata.ErrorEnum;
import system.data.basedata.ResultSupport;
import system.data.supper.service.JavaSerializer;
import system.data.supper.service.RedisManager;
import utils.typeHelper.GsonUtil;

import java.nio.charset.Charset;
import java.util.*;

/**
 * redis:业务层的处理方法
 */
@Component
public class RedisManagerImpl implements RedisManager {

    private static final Charset default_charset = Charset.forName("utf-8");

    private static final Logger log = LoggerFactory.getLogger(RedisManagerImpl.class);
    @Autowired
    private JavaSerializer serializableMethod;//序列化方式
    
    @Autowired
    private ShardedJedisPool shardedJedisPool;//切片连接池,即采用分片缓存
    @Autowired
    private RedisTemplate redisTemplate;
    public Boolean exists(String key) {
        Boolean result = false;
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        if (shardedJedis == null) {
            return result;
        }
        try {
            result = shardedJedis.exists(key);
        } catch (Exception e) {
            shardedJedisPool.returnBrokenResource(shardedJedis);
        } finally {
            shardedJedisPool.returnResource(shardedJedis);
        }
        return result;
    }
    public Set<String> smembers(String key) {
        Set<String> result = null;
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        if (shardedJedis == null) {
            return result;
        }
        try {
            result = shardedJedis.smembers(key);
        } catch (Exception e) {
            shardedJedisPool.returnBrokenResource(shardedJedis);
        } finally {
            shardedJedisPool.returnResource(shardedJedis);
        }
        return result;
    }
    @Override
    public List<String> findKeysForPage(String patternKey, int pageNum, int pageSize) {
        ScanOptions options = ScanOptions.scanOptions().match(patternKey).build();
        RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
        RedisConnection rc = factory.getConnection();
        Cursor<byte[]> cursor = rc.scan(options);
        List<String> result = new ArrayList<String>(pageSize);
        int tmpIndex = 0;
        int startIndex = (pageNum - 1) * pageSize;
        int end = pageNum * pageSize;
        while (cursor.hasNext()) {
            if (tmpIndex >= startIndex && tmpIndex < end) {
                result.add(new String(cursor.next()));
                tmpIndex++;
                continue;
            }

            // 获取到满足条件的数据后,就可以退出了
            if(tmpIndex >=end) {
                break;
            }

            tmpIndex++;
            cursor.next();
        }

        try {
            // cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            RedisConnectionUtils.releaseConnection(rc, factory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public ResultSupport<Boolean> putCache(String key, Object value) throws Exception {
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        try {
            String status = shardedJedis.set(key.getBytes(default_charset), serializableMethod.serialize(value));
            shardedJedis.hset(key.getBytes(default_charset),"22222".getBytes(),serializableMethod.serialize(value));
            shardedJedis.hset(key.getBytes(default_charset),"343".getBytes(),serializableMethod.serialize(value));
            shardedJedis.hset(key.getBytes(default_charset),"222".getBytes(),serializableMethod.serialize(value));
            shardedJedis.hset(key.getBytes(default_charset),"4343".getBytes(),serializableMethod.serialize(value));
            shardedJedis.hset(key.getBytes(default_charset),"22222".getBytes(),serializableMethod.serialize(value));
            shardedJedis.hset(key.getBytes(default_charset),"22222".getBytes(),serializableMethod.serialize(value));
            shardedJedis.hset(key.getBytes(default_charset),"22222".getBytes(),serializableMethod.serialize(value));


           // log.debug("put cache status="+status+",key="+key);
        }catch (JedisConnectionException e){
            shardedJedisPool.returnBrokenResource(shardedJedis);
            return ResultSupport.newErrorResult("get redis error");
        }catch (Exception e){
            return ResultSupport.newErrorResult("put redis error");
        }finally {
            shardedJedisPool.returnResource(shardedJedis);
        }
        return ResultSupport.newSuccessResult(Boolean.TRUE);
    }

    /**
     * 获取所有匹配的keys
     * @param key
     * @return
     */
    public int getPatternKeyCount(String key)  {
        Set<String> set = redisTemplate.keys(key);
        if(set != null ){
            return set.size();
        }
        return 0;
    }
    public ResultSupport<Object> getCacheByKey(String key)  {
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        Object obj;
        try{
            byte[] value = shardedJedis.get(key.getBytes(default_charset));
            if (value==null){
                return ResultSupport.newErrorResult(ErrorEnum.CACHE_IS_NULL);
            }
            obj = serializableMethod.unserialize(value);
            return ResultSupport.newSuccessResult(obj);
        }catch (JedisConnectionException e){
            shardedJedisPool.returnBrokenResource(shardedJedis);
            return ResultSupport.newErrorResult("get redis error");
        }catch(Exception e){
            return ResultSupport.newErrorResult("get redis error");
        }finally {
            shardedJedisPool.returnResource(shardedJedis);
        }
    }
     
    public List<String> getCacheInitByKey(String key)  throws Exception {
    	ShardedJedis shardedJedis = shardedJedisPool.getResource();
    	List<String> obj = new ArrayList<String>();
    	try{
    		obj = shardedJedis.hmget(key);
    		return obj;
    	}catch (JedisConnectionException e){
    		shardedJedisPool.returnBrokenResource(shardedJedis);
    	}catch(Exception e){
    	}finally {
    		shardedJedisPool.returnResource(shardedJedis);
    	}
    	return null;
    }
    
    
    /**
     * 存放hset信息到redis中
     * @param key:一般是表信息
     * @return
     */
    public ResultSupport<Boolean> putHmsetCache(String key, JSONObject json,List<String> incrList)  throws Exception {
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        try {
            Map data = new HashMap();   
        	for(String jsonKey : json.keySet()){
        		data.put(jsonKey, String.valueOf(json.get(jsonKey)));
        		String status = shardedJedis.hmset(key, data);
        		//log.debug("put cache status="+status+",key="+key);
        	}
        	//自动增加
        	for(String incrKey : incrList){
        		shardedJedis.hincrBy(key,incrKey, 1);
        	}
        }catch (JedisConnectionException e){
            shardedJedisPool.returnBrokenResource(shardedJedis);
            return ResultSupport.newErrorResult("get redis error");
        }catch (Exception e){
            return ResultSupport.newErrorResult("put redis error");
        }finally {
            shardedJedisPool.returnResource(shardedJedis);
        }
        return ResultSupport.newSuccessResult(Boolean.TRUE);
    }


    public Boolean isfieldValueExist(String key, String fieldkey,
			String fieldValue) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

    /**
     *
     * @param key
     * @param field
     * @param value
     * @return
     * @throws Exception
     */
    @Override
    public ResultSupport<Boolean> putJsonJedisZsetCache(String key, String field, Object value) throws Exception {
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        try {
            Long resultStr = shardedJedis.hsetnx(key.getBytes(default_charset),field.getBytes(default_charset),serializableMethod.serialize(value));
            //log.debug("put cache resultStr="+resultStr+",key="+key);
        }catch (JedisConnectionException e){
            shardedJedisPool.returnBrokenResource(shardedJedis);
            return ResultSupport.newErrorResult("get redis error");
        }catch (Exception e){
            return ResultSupport.newErrorResult("put redis error");
        }finally {
            shardedJedisPool.returnResource(shardedJedis);
        }
        return ResultSupport.newSuccessResult(Boolean.TRUE);
    }

    @Override
    public ResultSupport<Boolean> putBinaryJedisCache(String key, String field, Object value) throws Exception {
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        try {
            Long resultStr = shardedJedis.hsetnx(key.getBytes(default_charset),field.getBytes(default_charset),serializableMethod.serialize(value));
            //log.debug("put cache resultStr="+resultStr+",key="+key);

        }catch (JedisConnectionException e){
            shardedJedisPool.returnBrokenResource(shardedJedis);
            return ResultSupport.newErrorResult("get redis error");
        }catch (Exception e){
            return ResultSupport.newErrorResult("put redis error");
        }finally {
            shardedJedisPool.returnResource(shardedJedis);
        }
        return ResultSupport.newSuccessResult(Boolean.TRUE);
    }
    @Override
    public ResultSupport<Boolean> putJsonJedisCache(String key, String field, Object value) throws Exception {
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        try {
            Long resultStr = shardedJedis.hsetnx(key,field,GsonUtil.object2Json(value));
            log.debug("put cache resultStr="+resultStr+",key="+key);
        }catch (JedisConnectionException e){
            shardedJedisPool.returnBrokenResource(shardedJedis);
            return ResultSupport.newErrorResult("get redis error");
        }catch (Exception e){
            return ResultSupport.newErrorResult("put redis error");
        }finally {
            shardedJedisPool.returnResource(shardedJedis);
        }
        return ResultSupport.newSuccessResult(Boolean.TRUE);
    }
    //rpush
    @Override
    public ResultSupport<Boolean> putIntoListJedisCache(String key, Object record) throws Exception {
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        try {
            Long resultStr = shardedJedis.lpush(key, GsonUtil.object2Json(record));
            //log.debug("put cache resultStr="+resultStr+",key="+key);
        }catch (JedisConnectionException e){
            shardedJedisPool.returnBrokenResource(shardedJedis);
            return ResultSupport.newErrorResult("get redis error");
        }catch (Exception e){
            return ResultSupport.newErrorResult("put redis error");
        }finally {
            shardedJedisPool.returnResource(shardedJedis);
        }
        return ResultSupport.newSuccessResult(Boolean.TRUE);
    }
    @Override
    public List<String> getListJedisCache(String key) throws Exception {
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        try {
            List<String> list = shardedJedis.lrange(key, 0, -1);
            return list;
        }catch (JedisConnectionException e){
            shardedJedisPool.returnBrokenResource(shardedJedis);
        }catch(Exception e){
        }finally {
            shardedJedisPool.returnResource(shardedJedis);
        }
        return null;
    }

    @Override

    public void setData(String key, Map<String,String> value) {
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        try {
            shardedJedis.hmset(key, value);

        }catch (JedisConnectionException e){
            shardedJedisPool.returnBrokenResource(shardedJedis);

        }catch (Exception e){

        }finally {
            shardedJedisPool.returnResource(shardedJedis);
        }

    }

    @Override
    public Set<byte[]> getListBinaryJedisCache(String key, String field) throws Exception {
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        try {
            Set<byte[]> objbyte = shardedJedis.hkeys(field.getBytes(default_charset));
            return objbyte;
            //log.debug("put cache resultStr="+resultStr+",key="+key);
        }catch (JedisConnectionException e){
            shardedJedisPool.returnBrokenResource(shardedJedis);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            shardedJedisPool.returnResource(shardedJedis);
        }
        return null;
    }

    @Override
    public byte[] getBinaryJedisCache(String key, String field) throws Exception {
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        try {
            byte[] objbyte = shardedJedis.hget(key.getBytes(default_charset),field.getBytes(default_charset));
            return objbyte;
            //log.debug("put cache resultStr="+resultStr+",key="+key);
        }catch (JedisConnectionException e){
            shardedJedisPool.returnBrokenResource(shardedJedis);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            shardedJedisPool.returnResource(shardedJedis);
        }
        return null;
    }

    /**
	 * 返回回来的数据格式
	 * [
	 * 	resultData:列表中的值
	 * 	status : 状态0标识已经存在，1标识不存在
	 * ]
	 */
	public Map<String, Object> saddCache(String key,String... members)  throws Exception {
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        Map<String, Object> map = new HashMap<String, Object>();
        Set<String> set = new HashSet<String>();
        try {
        	//建立表key
        	Long status = shardedJedis.sadd(key, members);

            shardedJedis.hset("sys_userinfo","1","{id=1,name=liuqiang,nick=xxx}");
            shardedJedis.hset("sys_userinfo","2","{id=23423423,name=liuqiang,nick=xxx}");
            shardedJedis.hset("sys_userinfo","3","{id=3,name=liuqiang,nick=xxx}");
            shardedJedis.hset("sys_userinfo","4","{id=4,name=liuqiang,nick=xxx}");
            shardedJedis.hset("sys_userinfo","5","{id=5,name=liuqiang,nick=xxx} ");



            shardedJedis.hset("sys_userinfo","6","{id=1,name=asdfasfd,nick=xxx}");
            shardedJedis.hset("sys_userinfo","7","{id=3423423,name=liuqiang,nick=xxx}");
            shardedJedis.hset("sys_userinfo","8","{id=234234234,name=liuqiang,nick=xxx}");
            shardedJedis.hset("sys_userinfo","4","{id=121214,name=liuqiang,nick=xxx}");
            shardedJedis.hset("sys_userinfo","5","{id=5,name=liuqiang,nick=xxx} ");
        	set = shardedJedis.smembers(key);
        	map.put("resultData", set);
        	map.put("status", status);
            //log.debug("put cache status="+status+",key="+key);
        }catch (JedisConnectionException e){
            shardedJedisPool.returnBrokenResource(shardedJedis);
        }catch (Exception e){
        }finally {
            shardedJedisPool.returnResource(shardedJedis);
        }
        return map;
    }

	public Map<String, Object> getSCache(String key){
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        Map<String, Object> map = new HashMap<String, Object>();
        Set<String> set = new HashSet<String>();
        try {
        	set = shardedJedis.smembers(key);
        	map.put(key, set);
        }catch (JedisConnectionException e){
            shardedJedisPool.returnBrokenResource(shardedJedis);
        }catch (Exception e){
        }finally {
            shardedJedisPool.returnResource(shardedJedis);
        }
        return map;
    }

//    public ResultSupport<Boolean> putIfNotExist(String key,Object value){
//        ShardedJedis shardedJedis = shardedJedisPool.getResource();
//         try {
//            long status = shardedJedis.setnx(key.getBytes(default_charset), serializableMethod.serialize(value));
//            log.debug("put cache status=" + status + ",key=" + key);
//             return status==1?ResultSupport.newSuccessResult(false):ResultSupport.newSuccessResult(true);
//         }catch (JedisConnectionException e){
//             shardedJedisPool.returnBrokenResource(shardedJedis);
//             return ResultSupport.newErrorResult("get redis error");
//         }catch (Exception e){
//            return ResultSupport.newErrorResult("put redis error");
//         }finally {
//             shardedJedisPool.returnResource(shardedJedis);
//         }
//    }
//
//    public ResultSupport<Long> removeCache(String key) {
//        ShardedJedis shardedJedis = shardedJedisPool.getResource();
//        try{
//            Long valueLen;
//            try{
//                valueLen = shardedJedis.del(key.getBytes(default_charset));
//            }catch (Exception e){
//                return ResultSupport.newErrorResult("remove redis error");
//            }
//            return ResultSupport.newSuccessResult(valueLen);
//        }catch (JedisConnectionException e){
//            shardedJedisPool.returnBrokenResource(shardedJedis);
//            return ResultSupport.newErrorResult("get redis error");
//        }finally {
//            shardedJedisPool.returnResource(shardedJedis);
//        }
//    }
//
//
//    public Result<Long> put(String key, Object value, long version){
//        ShardedJedis shardedJedis = shardedJedisPool.getResource();
//        try {
//            Long newVersion = shardedJedis.incr(("__num_prefix_" + key).getBytes(default_charset));
//            if (newVersion == version) {
//                Long re = shardedJedis.setnx((key + "_" + newVersion).getBytes(default_charset), serializableMethod.serialize(value));
//                if (re == 0) {
//                    return ResultSupport.newSuccessResult(newVersion);
//                } else {
//                    Result<Long> error = ResultSupport.<Long>newErrorResult(ErrorEnum.CACHE_ALREADY_EXIST);
//                    error.setDefaultModel(newVersion);
//                    return error;
//                }
//            } else {
//                Result<Long> error = ResultSupport.newErrorResult(ErrorEnum.CACHE_VERSION_INVALID);
//                error.setDefaultModel(newVersion);
//                return error;
//            }
//        }catch (JedisConnectionException e){
//            shardedJedisPool.returnBrokenResource(shardedJedis);
//            return ResultSupport.newErrorResult("get redis error");
//        }finally {
//            shardedJedisPool.returnResource(shardedJedis);
//        }
//    }
//
//    public long countUp(String key){
//        ShardedJedis shardedJedis = shardedJedisPool.getResource();
//        try {
//            return shardedJedis.incr(key);
//        }catch (JedisConnectionException e){
//            shardedJedisPool.returnBrokenResource(shardedJedis);
//        }finally {
//            shardedJedisPool.returnResource(shardedJedis);
//        }
//        return 0;
//    }
}
