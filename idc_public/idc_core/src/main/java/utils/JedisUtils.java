//package utils;
//
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;
//import com.google.common.collect.Sets;
//import org.apache.commons.lang3.BooleanUtils;
//import org.apache.commons.lang3.StringEscapeUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.exceptions.JedisException;
//
//import java.io.*;
//import java.lang.reflect.Method;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * Created by mylove on 2017/6/26.
// * <p/>
// * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
// * <p/>
// * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
// * <p/>
// * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
// * <p/>
// * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
// * <p/>
// * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
// * <p/>
// * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
// * <p/>
// * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
// * <p/>
// * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
// */
///**
// * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
// */
//
///**
// * Jedis Cache 工具类
// *
// * @author ThinkGem
// * @version 2014-6-29
// */
//public class JedisUtils {
//
//    private static Logger logger = LoggerFactory.getLogger(JedisUtils.class);
//
//    private static JedisPool jedisPool = SpringContextHolder.getBean(JedisPool.class);
//
//    public static final String KEY_PREFIX = "ecmp";
//
//    /**
//     * 获取缓存
//     * @param key 键
//     * @return 值
//     */
//    public static String get(String key) {
//        String value = null;
//        Jedis jedis = null;
//        try {
//            jedis = getResource();
//            if (jedis.exists(key)) {
//                value = jedis.get(key);
//                value = org.apache.commons.lang3.StringUtils.isNotBlank(value) && !"nil".equalsIgnoreCase(value) ? value : null;
//                logger.debug("get {} = {}", key, value);
//            }
//        } catch (Exception e) {
//            logger.warn("get {} = {}", key, value, e);
//        } finally {
//            returnResource(jedis);
//        }
//        return value;
//    }
//
//    /**
//     * 获取缓存
//     * @param key 键
//     * @return 值
//     */
//    public static Object getObject(String key) {
//        Object value = null;
//        Jedis jedis = null;
//        try {
//            jedis = getResource();
//            if (jedis.exists(getBytesKey(key))) {
//                value = toObject(jedis.get(getBytesKey(key)));
//                logger.debug("getObject {} = {}", key, value);
//            }
//        } catch (Exception e) {
//            logger.warn("getObject {} = {}", key, value, e);
//        } finally {
//            returnResource(jedis);
//        }
//        return value;
//    }
//
//    /**
//     * 设置缓存
//     * @param key 键
//     * @param value 值
//     * @param cacheSeconds 超时时间，0为不超时
//     * @return
//     */
//    public static String set(String key, String value, int cacheSeconds) {
//        String result = null;
//        Jedis jedis = null;
//        try {
//            jedis = getResource();
//            result = jedis.set(key, value);
//            if (cacheSeconds != 0) {
//                jedis.expire(key, cacheSeconds);
//            }
//            logger.debug("set {} = {}", key, value);
//        } catch (Exception e) {
//            logger.warn("set {} = {}", key, value, e);
//        } finally {
//            returnResource(jedis);
//        }
//        return result;
//    }
//
//    /**
//     * 设置缓存
//     * @param key 键
//     * @param value 值
//     * @param cacheSeconds 超时时间，0为不超时
//     * @return
//     */
//    public static String setObject(String key, Object value, int cacheSeconds) {
//        String result = null;
//        Jedis jedis = null;
//        try {
//            jedis = getResource();
//            result = jedis.set(getBytesKey(key), toBytes(value));
//            if (cacheSeconds != 0) {
//                jedis.expire(key, cacheSeconds);
//            }
//            logger.debug("setObject {} = {}", key, value);
//        } catch (Exception e) {
//            logger.warn("setObject {} = {}", key, value, e);
//        } finally {
//            returnResource(jedis);
//        }
//        return result;
//    }
//
//    /**
//     * 获取List缓存
//     * @param key 键
//     * @return 值
//     */
//    public static List<String> getList(String key) {
//        List<String> value = null;
//        Jedis jedis = null;
//        try {
//            jedis = getResource();
//            if (jedis.exists(key)) {
//                value = jedis.lrange(key, 0, -1);
//                logger.debug("getList {} = {}", key, value);
//            }
//        } catch (Exception e) {
//            logger.warn("getList {} = {}", key, value, e);
//        } finally {
//            returnResource(jedis);
//        }
//        return value;
//    }
//
//    /**
//     * 获取List缓存
//     * @param key 键
//     * @return 值
//     */
//    public static List<Object> getObjectList(String key) {
//        List<Object> value = null;
//        Jedis jedis = null;
//        try {
//            jedis = getResource();
//            if (jedis.exists(getBytesKey(key))) {
//                List<byte[]> list = jedis.lrange(getBytesKey(key), 0, -1);
//                value = Lists.newArrayList();
//                for (byte[] bs : list) {
//                    value.add(toObject(bs));
//                }
//                logger.debug("getObjectList {} = {}", key, value);
//            }
//        } catch (Exception e) {
//            logger.warn("getObjectList {} = {}", key, value, e);
//        } finally {
//            returnResource(jedis);
//        }
//        return value;
//    }
//
//    /**
//     * 设置List缓存
//     * @param key 键
//     * @param value 值
//     * @param cacheSeconds 超时时间，0为不超时
//     * @return
//     */
//    public static long setList(String key, List<String> value, int cacheSeconds) {
//        long result = 0;
//        Jedis jedis = null;
//        try {
//            jedis = getResource();
//            if (jedis.exists(key)) {
//                jedis.del(key);
//            }
//            result = jedis.rpush(key, (String[]) value.toArray());
//            if (cacheSeconds != 0) {
//                jedis.expire(key, cacheSeconds);
//            }
//            logger.debug("setList {} = {}", key, value);
//        } catch (Exception e) {
//            logger.warn("setList {} = {}", key, value, e);
//        } finally {
//            returnResource(jedis);
//        }
//        return result;
//    }
//
//    /**
//     * 设置List缓存
//     * @param key 键
//     * @param value 值
//     * @param cacheSeconds 超时时间，0为不超时
//     * @return
//     */
//    public static long setObjectList(String key, List<Object> value, int cacheSeconds) {
//        long result = 0;
//        Jedis jedis = null;
//        try {
//            jedis = getResource();
//            if (jedis.exists(getBytesKey(key))) {
//                jedis.del(key);
//            }
//            List<byte[]> list = Lists.newArrayList();
//            for (Object o : value) {
//                list.add(toBytes(o));
//            }
//            result = jedis.rpush(getBytesKey(key), (byte[][]) list.toArray());
//            if (cacheSeconds != 0) {
//                jedis.expire(key, cacheSeconds);
//            }
//            logger.debug("setObjectList {} = {}", key, value);
//        } catch (Exception e) {
//            logger.warn("setObjectList {} = {}", key, value, e);
//        } finally {
//            returnResource(jedis);
//        }
//        return result;
//    }
//
//    /**
//     * 向List缓存中添加值
//     * @param key 键
//     * @param value 值
//     * @return
//     */
//    public static long listAdd(String key, String... value) {
//        long result = 0;
//        Jedis jedis = null;
//        try {
//            jedis = getResource();
//            result = jedis.rpush(key, value);
//            logger.debug("listAdd {} = {}", key, value);
//        } catch (Exception e) {
//            logger.warn("listAdd {} = {}", key, value, e);
//        } finally {
//            returnResource(jedis);
//        }
//        return result;
//    }
//
//    /**
//     * 向List缓存中添加值
//     * @param key 键
//     * @param value 值
//     * @return
//     */
//    public static long listObjectAdd(String key, Object... value) {
//        long result = 0;
//        Jedis jedis = null;
//        try {
//            jedis = getResource();
//            List<byte[]> list = Lists.newArrayList();
//            for (Object o : value) {
//                list.add(toBytes(o));
//            }
//            result = jedis.rpush(getBytesKey(key), (byte[][]) list.toArray());
//            logger.debug("listObjectAdd {} = {}", key, value);
//        } catch (Exception e) {
//            logger.warn("listObjectAdd {} = {}", key, value, e);
//        } finally {
//            returnResource(jedis);
//        }
//        return result;
//    }
//
//    /**
//     * 获取缓存
//     * @param key 键
//     * @return 值
//     */
//    public static Set<String> getSet(String key) {
//        Set<String> value = null;
//        Jedis jedis = null;
//        try {
//            jedis = getResource();
//            if (jedis.exists(key)) {
//                value = jedis.smembers(key);
//                logger.debug("getSet {} = {}", key, value);
//            }
//        } catch (Exception e) {
//            logger.warn("getSet {} = {}", key, value, e);
//        } finally {
//            returnResource(jedis);
//        }
//        return value;
//    }
//
//    /**
//     * 获取缓存
//     * @param key 键
//     * @return 值
//     */
//    public static Set<Object> getObjectSet(String key) {
//        Set<Object> value = null;
//        Jedis jedis = null;
//        try {
//            jedis = getResource();
//            if (jedis.exists(getBytesKey(key))) {
//                value = Sets.newHashSet();
//                Set<byte[]> set = jedis.smembers(getBytesKey(key));
//                for (byte[] bs : set) {
//                    value.add(toObject(bs));
//                }
//                logger.debug("getObjectSet {} = {}", key, value);
//            }
//        } catch (Exception e) {
//            logger.warn("getObjectSet {} = {}", key, value, e);
//        } finally {
//            returnResource(jedis);
//        }
//        return value;
//    }
//
//    /**
//     * 设置Set缓存
//     * @param key 键
//     * @param value 值
//     * @param cacheSeconds 超时时间，0为不超时
//     * @return
//     */
//    public static long setSet(String key, Set<String> value, int cacheSeconds) {
//        long result = 0;
//        Jedis jedis = null;
//        try {
//            jedis = getResource();
//            if (jedis.exists(key)) {
//                jedis.del(key);
//            }
//            result = jedis.sadd(key, (String[]) value.toArray());
//            if (cacheSeconds != 0) {
//                jedis.expire(key, cacheSeconds);
//            }
//            logger.debug("setSet {} = {}", key, value);
//        } catch (Exception e) {
//            logger.warn("setSet {} = {}", key, value, e);
//        } finally {
//            returnResource(jedis);
//        }
//        return result;
//    }
//
//    /**
//     * 设置Set缓存
//     * @param key 键
//     * @param value 值
//     * @param cacheSeconds 超时时间，0为不超时
//     * @return
//     */
//    public static long setObjectSet(String key, Set<Object> value, int cacheSeconds) {
//        long result = 0;
//        Jedis jedis = null;
//        try {
//            jedis = getResource();
//            if (jedis.exists(getBytesKey(key))) {
//                jedis.del(key);
//            }
//            Set<byte[]> set = Sets.newHashSet();
//            for (Object o : value) {
//                set.add(toBytes(o));
//            }
//            result = jedis.sadd(getBytesKey(key), (byte[][]) set.toArray());
//            if (cacheSeconds != 0) {
//                jedis.expire(key, cacheSeconds);
//            }
//            logger.debug("setObjectSet {} = {}", key, value);
//        } catch (Exception e) {
//            logger.warn("setObjectSet {} = {}", key, value, e);
//        } finally {
//            returnResource(jedis);
//        }
//        return result;
//    }
//
//    /**
//     * 向Set缓存中添加值
//     * @param key 键
//     * @param value 值
//     * @return
//     */
//    public static long setSetAdd(String key, String... value) {
//        long result = 0;
//        Jedis jedis = null;
//        try {
//            jedis = getResource();
//            result = jedis.sadd(key, value);
//            logger.debug("setSetAdd {} = {}", key, value);
//        } catch (Exception e) {
//            logger.warn("setSetAdd {} = {}", key, value, e);
//        } finally {
//            returnResource(jedis);
//        }
//        return result;
//    }
//
//    /**
//     * 向Set缓存中添加值
//     * @param key 键
//     * @param value 值
//     * @return
//     */
//    public static long setSetObjectAdd(String key, Object... value) {
//        long result = 0;
//        Jedis jedis = null;
//        try {
//            jedis = getResource();
//            Set<byte[]> set = Sets.newHashSet();
//            for (Object o : value) {
//                set.add(toBytes(o));
//            }
//            result = jedis.rpush(getBytesKey(key), (byte[][]) set.toArray());
//            logger.debug("setSetObjectAdd {} = {}", key, value);
//        } catch (Exception e) {
//            logger.warn("setSetObjectAdd {} = {}", key, value, e);
//        } finally {
//            returnResource(jedis);
//        }
//        return result;
//    }
//
//    /**
//     * 获取Map缓存
//     * @param key 键
//     * @return 值
//     */
//    public static Map<String, String> getMap(String key) {
//        Map<String, String> value = null;
//        Jedis jedis = null;
//        try {
//            jedis = getResource();
//            if (jedis.exists(key)) {
//                value = jedis.hgetAll(key);
//                logger.debug("getMap {} = {}", key, value);
//            }
//        } catch (Exception e) {
//            logger.warn("getMap {} = {}", key, value, e);
//        } finally {
//            returnResource(jedis);
//        }
//        return value;
//    }
//
//    /**
//     * 获取Map缓存
//     * @param key 键
//     * @return 值
//     */
//    public static Map<String, Object> getObjectMap(String key) {
//        Map<String, Object> value = null;
//        Jedis jedis = null;
//        try {
//            jedis = getResource();
//            if (jedis.exists(getBytesKey(key))) {
//                value = Maps.newHashMap();
//                Map<byte[], byte[]> map = jedis.hgetAll(getBytesKey(key));
//                for (Map.Entry<byte[], byte[]> e : map.entrySet()) {
//                    value.put(new String(e.getKey(), "UTF-8"), toObject(e.getValue()));
//                }
//                logger.debug("getObjectMap {} = {}", key, value);
//            }
//        } catch (Exception e) {
//            logger.warn("getObjectMap {} = {}", key, value, e);
//        } finally {
//            returnResource(jedis);
//        }
//        return value;
//    }
//
//    /**
//     * 设置Map缓存
//     * @param key 键
//     * @param value 值
//     * @param cacheSeconds 超时时间，0为不超时
//     * @return
//     */
//    public static String setMap(String key, Map<String, String> value, int cacheSeconds) {
//        String result = null;
//        Jedis jedis = null;
//        try {
//            jedis = getResource();
//            if (jedis.exists(key)) {
//                jedis.del(key);
//            }
//            result = jedis.hmset(key, value);
//            if (cacheSeconds != 0) {
//                jedis.expire(key, cacheSeconds);
//            }
//            logger.debug("setMap {} = {}", key, value);
//        } catch (Exception e) {
//            logger.warn("setMap {} = {}", key, value, e);
//        } finally {
//            returnResource(jedis);
//        }
//        return result;
//    }
//
//    /**
//     * 设置Map缓存
//     * @param key 键
//     * @param value 值
//     * @param cacheSeconds 超时时间，0为不超时
//     * @return
//     */
//    public static String setObjectMap(String key, Map<String, Object> value, int cacheSeconds) {
//        String result = null;
//        Jedis jedis = null;
//        try {
//            jedis = getResource();
//            if (jedis.exists(getBytesKey(key))) {
//                jedis.del(key);
//            }
//            Map<byte[], byte[]> map = Maps.newHashMap();
//            for (Map.Entry<String, Object> e : value.entrySet()) {
//                map.put(getBytesKey(e.getKey()), toBytes(e.getValue()));
//            }
//            result = jedis.hmset(getBytesKey(key), (Map<byte[], byte[]>) map);
//            if (cacheSeconds != 0) {
//                jedis.expire(key, cacheSeconds);
//            }
//            logger.debug("setObjectMap {} = {}", key, value);
//        } catch (Exception e) {
//            logger.warn("setObjectMap {} = {}", key, value, e);
//        } finally {
//            returnResource(jedis);
//        }
//        return result;
//    }
//
//    /**
//     * 向Map缓存中添加值
//     * @param key 键
//     * @param value 值
//     * @return
//     */
//    public static String mapPut(String key, Map<String, String> value) {
//        String result = null;
//        Jedis jedis = null;
//        try {
//            jedis = getResource();
//            result = jedis.hmset(key, value);
//            logger.debug("mapPut {} = {}", key, value);
//        } catch (Exception e) {
//            logger.warn("mapPut {} = {}", key, value, e);
//        } finally {
//            returnResource(jedis);
//        }
//        return result;
//    }
//
//    /**
//     * 向Map缓存中添加值
//     * @param key 键
//     * @param value 值
//     * @return
//     */
//    public static String mapObjectPut(String key, Map<String, Object> value) {
//        String result = null;
//        Jedis jedis = null;
//        try {
//            jedis = getResource();
//            Map<byte[], byte[]> map = Maps.newHashMap();
//            for (Map.Entry<String, Object> e : value.entrySet()) {
//                map.put(getBytesKey(e.getKey()), toBytes(e.getValue()));
//            }
//            result = jedis.hmset(getBytesKey(key), (Map<byte[], byte[]>) map);
//            logger.debug("mapObjectPut {} = {}", key, value);
//        } catch (Exception e) {
//            logger.warn("mapObjectPut {} = {}", key, value, e);
//        } finally {
//            returnResource(jedis);
//        }
//        return result;
//    }
//
//    /**
//     * 移除Map缓存中的值
//     * @param key 键
//     * @param value 值
//     * @return
//     */
//    public static long mapRemove(String key, String mapKey) {
//        long result = 0;
//        Jedis jedis = null;
//        try {
//            jedis = getResource();
//            result = jedis.hdel(key, mapKey);
//            logger.debug("mapRemove {}  {}", key, mapKey);
//        } catch (Exception e) {
//            logger.warn("mapRemove {}  {}", key, mapKey, e);
//        } finally {
//            returnResource(jedis);
//        }
//        return result;
//    }
//
//    /**
//     * 移除Map缓存中的值
//     * @param key 键
//     * @param value 值
//     * @return
//     */
//    public static long mapObjectRemove(String key, String mapKey) {
//        long result = 0;
//        Jedis jedis = null;
//        try {
//            jedis = getResource();
//            result = jedis.hdel(getBytesKey(key), getBytesKey(mapKey));
//            logger.debug("mapObjectRemove {}  {}", key, mapKey);
//        } catch (Exception e) {
//            logger.warn("mapObjectRemove {}  {}", key, mapKey, e);
//        } finally {
//            returnResource(jedis);
//        }
//        return result;
//    }
//
//    /**
//     * 判断Map缓存中的Key是否存在
//     * @param key 键
//     * @param value 值
//     * @return
//     */
//    public static boolean mapExists(String key, String mapKey) {
//        boolean result = false;
//        Jedis jedis = null;
//        try {
//            jedis = getResource();
//            result = jedis.hexists(key, mapKey);
//            logger.debug("mapExists {}  {}", key, mapKey);
//        } catch (Exception e) {
//            logger.warn("mapExists {}  {}", key, mapKey, e);
//        } finally {
//            returnResource(jedis);
//        }
//        return result;
//    }
//
//    /**
//     * 判断Map缓存中的Key是否存在
//     * @param key 键
//     * @param value 值
//     * @return
//     */
//    public static boolean mapObjectExists(String key, String mapKey) {
//        boolean result = false;
//        Jedis jedis = null;
//        try {
//            jedis = getResource();
//            result = jedis.hexists(getBytesKey(key), getBytesKey(mapKey));
//            logger.debug("mapObjectExists {}  {}", key, mapKey);
//        } catch (Exception e) {
//            logger.warn("mapObjectExists {}  {}", key, mapKey, e);
//        } finally {
//            returnResource(jedis);
//        }
//        return result;
//    }
//
//    /**
//     * 删除缓存
//     * @param key 键
//     * @return
//     */
//    public static long del(String key) {
//        long result = 0;
//        Jedis jedis = null;
//        try {
//            jedis = getResource();
//            if (jedis.exists(key)) {
//                result = jedis.del(key);
//                logger.debug("del {}", key);
//            } else {
//                logger.debug("del {} not exists", key);
//            }
//        } catch (Exception e) {
//            logger.warn("del {}", key, e);
//        } finally {
//            returnResource(jedis);
//        }
//        return result;
//    }
//
//    /**
//     * 删除缓存
//     * @param key 键
//     * @return
//     */
//    public static long delObject(String key) {
//        long result = 0;
//        Jedis jedis = null;
//        try {
//            jedis = getResource();
//            if (jedis.exists(getBytesKey(key))) {
//                result = jedis.del(getBytesKey(key));
//                logger.debug("delObject {}", key);
//            } else {
//                logger.debug("delObject {} not exists", key);
//            }
//        } catch (Exception e) {
//            logger.warn("delObject {}", key, e);
//        } finally {
//            returnResource(jedis);
//        }
//        return result;
//    }
//
//    /**
//     * 缓存是否存在
//     * @param key 键
//     * @return
//     */
//    public static boolean exists(String key) {
//        boolean result = false;
//        Jedis jedis = null;
//        try {
//            jedis = getResource();
//            result = jedis.exists(key);
//            logger.debug("exists {}", key);
//        } catch (Exception e) {
//            logger.warn("exists {}", key, e);
//        } finally {
//            returnResource(jedis);
//        }
//        return result;
//    }
//
//    /**
//     * 缓存是否存在
//     * @param key 键
//     * @return
//     */
//    public static boolean existsObject(String key) {
//        boolean result = false;
//        Jedis jedis = null;
//        try {
//            jedis = getResource();
//            result = jedis.exists(getBytesKey(key));
//            logger.debug("existsObject {}", key);
//        } catch (Exception e) {
//            logger.warn("existsObject {}", key, e);
//        } finally {
//            returnResource(jedis);
//        }
//        return result;
//    }
//
//    /**
//     * 获取资源
//     * @return
//     * @throws JedisException
//     */
//    public static Jedis getResource() throws JedisException {
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
////			logger.debug("getResource.", jedis);
//        } catch (JedisException e) {
//            logger.warn("getResource.", e);
//            returnBrokenResource(jedis);
//            throw e;
//        }
//        return jedis;
//    }
//
//    /**
//     * 归还资源
//     * @param jedis
//     * @param isBroken
//     */
//    public static void returnBrokenResource(Jedis jedis) {
//        if (jedis != null) {
//            jedisPool.returnBrokenResource(jedis);
//        }
//    }
//
//    /**
//     * 释放资源
//     * @param jedis
//     * @param isBroken
//     */
//    public static void returnResource(Jedis jedis) {
//        if (jedis != null) {
//            jedisPool.returnResource(jedis);
//        }
//    }
//
//    /**
//     * 获取byte[]类型Key
//     * @param key
//     * @return
//     */
//    public static byte[] getBytesKey(Object object) {
//        if (object instanceof String) {
//            return StringUtils.getBytes((String) object);
//        } else {
//            return ObjectUtils.serialize(object);
//        }
//    }
//
//    /**
//     * 获取byte[]类型Key
//     * @param key
//     * @return
//     */
//    public static Object getObjectKey(byte[] key) {
//        try {
//            return StringUtils.toString(key);
//        } catch (UnsupportedOperationException uoe) {
//            try {
//                return JedisUtils.toObject(key);
//            } catch (UnsupportedOperationException uoe2) {
//                uoe2.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    /**
//     * Object转换byte[]类型
//     * @param key
//     * @return
//     */
//    public static byte[] toBytes(Object object) {
//        return ObjectUtils.serialize(object);
//    }
//
//    /**
//     * byte[]型转换Object
//     * @param key
//     * @return
//     */
//    public static Object toObject(byte[] bytes) {
//        return ObjectUtils.unserialize(bytes);
//    }
//
//}
//
//class ObjectUtils extends org.apache.commons.lang3.ObjectUtils {
//
//    /**
//     * 注解到对象复制，只复制能匹配上的方法。
//     * @param annotation
//     * @param object
//     */
//    public static void annotationToObject(Object annotation, Object object) {
//        if (annotation != null) {
//            Class<?> annotationClass = annotation.getClass();
//            if (null == object) {
//                return;
//            }
//            Class<?> objectClass = object.getClass();
//            for (Method m : objectClass.getMethods()) {
//                if (org.apache.commons.lang3.StringUtils.startsWith(m.getName(), "set")) {
//                    try {
//                        String s = org.apache.commons.lang3.StringUtils.uncapitalize(org.apache.commons.lang3.StringUtils.substring(m.getName(), 3));
//                        Object obj = annotationClass.getMethod(s).invoke(annotation);
//                        if (obj != null && !"".equals(obj.toString())) {
//                            m.invoke(object, obj);
//                        }
//                    } catch (Exception e) {
//                        // 忽略所有设置失败方法
//                    }
//                }
//            }
//        }
//    }
//
//    /**
//     * 序列化对象
//     * @param object
//     * @return
//     */
//    public static byte[] serialize(Object object) {
//        ObjectOutputStream oos = null;
//        ByteArrayOutputStream baos = null;
//        try {
//            if (object != null) {
//                baos = new ByteArrayOutputStream();
//                oos = new ObjectOutputStream(baos);
//                oos.writeObject(object);
//                return baos.toByteArray();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * 反序列化对象
//     * @param bytes
//     * @return
//     */
//    public static Object unserialize(byte[] bytes) {
//        ByteArrayInputStream bais = null;
//        try {
//            if (bytes != null && bytes.length > 0) {
//                bais = new ByteArrayInputStream(bytes);
//                ObjectInputStream ois = new ObjectInputStream(bais);
//                return ois.readObject();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}
//
//class StringUtils extends org.apache.commons.lang3.StringUtils {
//
//    private static final char SEPARATOR = '_';
//    private static final String CHARSET_NAME = "UTF-8";
//
//    /**
//     * 转换为字节数组
//     * @param str
//     * @return
//     */
//    public static byte[] getBytes(String str) {
//        if (str != null) {
//            try {
//                return str.getBytes(CHARSET_NAME);
//            } catch (UnsupportedEncodingException e) {
//                return null;
//            }
//        } else {
//            return null;
//        }
//    }
//
//    /**
//     * 转换为Boolean类型
//     * 'true', 'on', 'y', 't', 'yes' or '1' (case insensitive) will return true. Otherwise, false is returned.
//     */
//    public static Boolean toBoolean(final Object val) {
//        if (val == null) {
//            return false;
//        }
//        return BooleanUtils.toBoolean(val.toString()) || "1".equals(val.toString());
//    }
//
//    /**
//     * 转换为字节数组
//     * @param str
//     * @return
//     */
//    public static String toString(byte[] bytes) {
//        try {
//            return new String(bytes, CHARSET_NAME);
//        } catch (UnsupportedEncodingException e) {
//            return EMPTY;
//        }
//    }
//
//    /**
//     * 如果对象为空，则使用defaultVal值
//     * 	see: ObjectUtils.toString(obj, defaultVal)
//     * @param obj
//     * @param defaultVal
//     * @return
//     */
//    public static String toString(final Object obj, final String defaultVal) {
//        return obj == null ? defaultVal : obj.toString();
//    }
//
//    /**
//     * 是否包含字符串
//     * @param str 验证字符串
//     * @param strs 字符串组
//     * @return 包含返回true
//     */
//    public static boolean inString(String str, String... strs) {
//        if (str != null) {
//            for (String s : strs) {
//                if (str.equals(trim(s))) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 替换掉HTML标签方法
//     */
//    public static String replaceHtml(String html) {
//        if (isBlank(html)) {
//            return "";
//        }
//        String regEx = "<.+?>";
//        Pattern p = Pattern.compile(regEx);
//        Matcher m = p.matcher(html);
//        String s = m.replaceAll("");
//        return s;
//    }
//
//    /**
//     * 替换为手机识别的HTML，去掉样式及属性，保留回车。
//     * @param html
//     * @return
//     */
//    public static String replaceMobileHtml(String html) {
//        if (html == null) {
//            return "";
//        }
//        return html.replaceAll("<([a-z]+?)\\s+?.*?>", "<$1>");
//    }
//
//
//    /**
//     * 缩略字符串（不区分中英文字符）
//     * @param str 目标字符串
//     * @param length 截取长度
//     * @return
//     */
//    public static String abbr(String str, int length) {
//        if (str == null) {
//            return "";
//        }
//        try {
//            StringBuilder sb = new StringBuilder();
//            int currentLength = 0;
//            for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray()) {
//                currentLength += String.valueOf(c).getBytes("GBK").length;
//                if (currentLength <= length - 3) {
//                    sb.append(c);
//                } else {
//                    sb.append("...");
//                    break;
//                }
//            }
//            return sb.toString();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
//
//    public static String abbr2(String param, int length) {
//        if (param == null) {
//            return "";
//        }
//        StringBuffer result = new StringBuffer();
//        int n = 0;
//        char temp;
//        boolean isCode = false; // 是不是HTML代码
//        boolean isHTML = false; // 是不是HTML特殊字符,如&nbsp;
//        for (int i = 0; i < param.length(); i++) {
//            temp = param.charAt(i);
//            if (temp == '<') {
//                isCode = true;
//            } else if (temp == '&') {
//                isHTML = true;
//            } else if (temp == '>' && isCode) {
//                n = n - 1;
//                isCode = false;
//            } else if (temp == ';' && isHTML) {
//                isHTML = false;
//            }
//            try {
//                if (!isCode && !isHTML) {
//                    n += String.valueOf(temp).getBytes("GBK").length;
//                }
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//
//            if (n <= length - 3) {
//                result.append(temp);
//            } else {
//                result.append("...");
//                break;
//            }
//        }
//        // 取出截取字符串中的HTML标记
//        String temp_result = result.toString().replaceAll("(>)[^<>]*(<?)",
//                "$1$2");
//        // 去掉不需要结素标记的HTML标记
//        temp_result = temp_result
//                .replaceAll(
//                        "</?(AREA|BASE|BASEFONT|BODY|BR|COL|COLGROUP|DD|DT|FRAME|HEAD|HR|HTML|IMG|INPUT|ISINDEX|LI|LINK|META|OPTION|P|PARAM|TBODY|TD|TFOOT|TH|THEAD|TR|area|base|basefont|body|br|col|colgroup|dd|dt|frame|head|hr|html|img|input|isindex|li|link|meta|option|p|param|tbody|td|tfoot|th|thead|tr)[^<>]*/?>",
//                        "");
//        // 去掉成对的HTML标记
//        temp_result = temp_result.replaceAll("<([a-zA-Z]+)[^<>]*>(.*?)</\\1>",
//                "$2");
//        // 用正则表达式取出标记
//        Pattern p = Pattern.compile("<([a-zA-Z]+)[^<>]*>");
//        Matcher m = p.matcher(temp_result);
//        List<String> endHTML = Lists.newArrayList();
//        while (m.find()) {
//            endHTML.add(m.group(1));
//        }
//        // 补全不成对的HTML标记
//        for (int i = endHTML.size() - 1; i >= 0; i--) {
//            result.append("</");
//            result.append(endHTML.get(i));
//            result.append(">");
//        }
//        return result.toString();
//    }
//
//    /**
//     * 转换为Double类型
//     */
//    public static Double toDouble(Object val) {
//        if (val == null) {
//            return 0D;
//        }
//        try {
//            return Double.valueOf(trim(val.toString()));
//        } catch (Exception e) {
//            return 0D;
//        }
//    }
//
//    /**
//     * 转换为Float类型
//     */
//    public static Float toFloat(Object val) {
//        return toDouble(val).floatValue();
//    }
//
//    /**
//     * 转换为Long类型
//     */
//    public static Long toLong(Object val) {
//        return toDouble(val).longValue();
//    }
//
//    /**
//     * 转换为Integer类型
//     */
//    public static Integer toInteger(Object val) {
//        return toLong(val).intValue();
//    }
//
//
//    /**
//     * 驼峰命名法工具
//     * @return
//     * 		toCamelCase("hello_world") == "helloWorld"
//     * 		toCapitalizeCamelCase("hello_world") == "HelloWorld"
//     * 		toUnderScoreCase("helloWorld") = "hello_world"
//     */
//    public static String toCamelCase(String s) {
//        if (s == null) {
//            return null;
//        }
//
//        s = s.toLowerCase();
//
//        StringBuilder sb = new StringBuilder(s.length());
//        boolean upperCase = false;
//        for (int i = 0; i < s.length(); i++) {
//            char c = s.charAt(i);
//
//            if (c == SEPARATOR) {
//                upperCase = true;
//            } else if (upperCase) {
//                sb.append(Character.toUpperCase(c));
//                upperCase = false;
//            } else {
//                sb.append(c);
//            }
//        }
//
//        return sb.toString();
//    }
//
//    /**
//     * 驼峰命名法工具
//     * @return
//     * 		toCamelCase("hello_world") == "helloWorld"
//     * 		toCapitalizeCamelCase("hello_world") == "HelloWorld"
//     * 		toUnderScoreCase("helloWorld") = "hello_world"
//     */
//    public static String toCapitalizeCamelCase(String s) {
//        if (s == null) {
//            return null;
//        }
//        s = toCamelCase(s);
//        return s.substring(0, 1).toUpperCase() + s.substring(1);
//    }
//
//    /**
//     * 驼峰命名法工具
//     * @return
//     * 		toCamelCase("hello_world") == "helloWorld"
//     * 		toCapitalizeCamelCase("hello_world") == "HelloWorld"
//     * 		toUnderScoreCase("helloWorld") = "hello_world"
//     */
//    public static String toUnderScoreCase(String s) {
//        if (s == null) {
//            return null;
//        }
//
//        StringBuilder sb = new StringBuilder();
//        boolean upperCase = false;
//        for (int i = 0; i < s.length(); i++) {
//            char c = s.charAt(i);
//
//            boolean nextUpperCase = true;
//
//            if (i < (s.length() - 1)) {
//                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
//            }
//
//            if ((i > 0) && Character.isUpperCase(c)) {
//                if (!upperCase || !nextUpperCase) {
//                    sb.append(SEPARATOR);
//                }
//                upperCase = true;
//            } else {
//                upperCase = false;
//            }
//
//            sb.append(Character.toLowerCase(c));
//        }
//
//        return sb.toString();
//    }
//
//    /**
//     * 转换为JS获取对象值，生成三目运算返回结果
//     * @param objectString 对象串
//     *   例如：row.user.id
//     *   返回：!row?'':!row.user?'':!row.user.id?'':row.user.id
//     */
//    public static String jsGetVal(String objectString) {
//        StringBuilder result = new StringBuilder();
//        StringBuilder val = new StringBuilder();
//        String[] vals = split(objectString, ".");
//        for (int i = 0; i < vals.length; i++) {
//            val.append("." + vals[i]);
//            result.append("!" + (val.substring(1)) + "?'':");
//        }
//        result.append(val.substring(1));
//        return result.toString();
//    }
//
//}
