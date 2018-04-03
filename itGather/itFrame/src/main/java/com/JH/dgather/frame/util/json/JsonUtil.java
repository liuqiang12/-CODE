package com.JH.dgather.frame.util.json;

import java.util.*;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * json 转换工具
 *
 * @author lcw
 *         2016.05.31
 */
public class JsonUtil {
    public static String datePattern = "yyyy-MM-dd";
    public static String dateTimePattern = "yyyy-MM-dd HH:mm:ss";
    public static JsonConfig jsonDateConfig = new JsonConfig();

    public static void setJsonDateConfig() {
        jsonDateConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor(datePattern));
    }

    public static void setJsonDateConfig(Class type) {
        jsonDateConfig.registerJsonValueProcessor(type, new JsonDateValueProcessor(datePattern));
    }

    /**
     * 对象转换成JSON对象
     *
     * @param IgnoreFields 需要被忽略掉的属性（字段）集合
     * @paramobj 转换的对象
     */
    public static JSONObject object2jsonWithIgnoreFields(Object obj, String[] IgnoreFields) {
        if (null != obj) {
            JsonConfig ignoreConfig = new JsonConfig();
            ignoreConfig.setJsonPropertyFilter(new IgnoreFieldProcessorImpl(true, IgnoreFields));
            return JSONObject.fromObject(obj, ignoreConfig);
        }

        return null;
    }

    /**
     * JSON字符串转换成对象
     *
     * @param jsonString 需要转换的字符串
     * @param type       需要转换的对象类型
     * @return 对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T jsonStr2beanByType(String jsonString, Class<T> type) {
        if ((!jsonString.isEmpty()) && JsonValidator.validate(jsonString) && type != null) {
            JSONObject jsonObject = JSONObject.fromObject(jsonString);
            return json2beanByType(jsonObject, type);
        }

        return null;
    }

    /**
     * JSONObject转换成对象
     *
     * @param jsonObject 需要转换的JSONObject
     * @param type       需要转换的对象类型
     * @return 对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T json2beanByType(JSONObject jsonObject, Class<T> type) {
        if (null != jsonObject && null != type) {
            return (T) JSONObject.toBean(jsonObject, type);
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public static Object json2bean(JSONObject jsonObject) {
        if (null != jsonObject) {
            return JSONObject.toBean(jsonObject);
        }

        return null;
    }

    /**
     * 将JSONArray对象转换成list集合
     *
     * @param jsonArr
     * @return
     */
    public static List jsonArray2list(JSONArray jsonArr) {
        if (null != jsonArr && jsonArr.size() > 0) {
            List<Map> list = new ArrayList<Map>();

            for (int i = 0; i < jsonArr.size(); i++) {
                list.add(json2map(jsonArr.getJSONObject(i)));
            }

            return list;
        }

        return null;
    }

    public static List<Object> json2listByType(JSONArray jsonArr, Class type) {
        if (null != jsonArr && jsonArr.size() > 0) {
            Collection collection = JSONArray.toCollection(jsonArr, type);

            if (collection != null && collection instanceof List) {
                return (List) collection;
            }
        }

        return null;
    }

    public static Set json2set(JSONArray jsonArr, Class type) {
        if (null != jsonArr && jsonArr.size() > 0) {
            Collection collection = JSONArray.toCollection(jsonArr, type);

            if (collection != null && collection instanceof Set) {
                return (Set) collection;
            }
        }

        return null;
    }

    public static Set<Object> json2setByType(JSONArray jsonArr, Class type) {
        if (null != jsonArr && jsonArr.size() > 0) {
            Collection collection = JSONArray.toCollection(jsonArr, type);

            if (collection != null && collection instanceof Set) {
                return (Set) collection;
            }
        }

        return null;
    }

    /**
     * 将json字符串转换成map对象
     *
     * @param str
     * @return
     */
    public static Map<String, Object> jsonStr2map(String str) {
        if ((!str.isEmpty()) && JsonValidator.validate(str)) {
            JSONObject obj = JSONObject.fromObject(str);
            return json2map(obj);
        }

        return null;
    }

    /**
     * 将JSONObject转换成map对象
     *
     * @param obj
     * @return
     */
    public static Map<String, Object> json2map(JSONObject obj) {
        if (null != obj) {
            Set set = obj.keySet();
            Map<String, Object> map = new HashMap<String, Object>(set.size());

            for (Object key : obj.keySet()) {
                Object value = obj.get(key);
                map.put(key.toString(), value);
            }

            return map;
        }

        return null;
    }

    /**
     * @param bean bean对象
     * @return String
     */
    public static JSONObject bean2json(Object bean) {
        if (null != bean) {
            setJsonDateConfig();
            return JSONObject.fromObject(bean, jsonDateConfig);
        }

        return null;
    }

    public static JSONObject bean2json(Object bean, Class type) {
        if (null != bean && null != type) {
            setJsonDateConfig(type);
            return JSONObject.fromObject(bean, jsonDateConfig);
        }

        return null;
    }

    /**
     * @param collection 集合对象转 JSONArray
     * @return JSONArray
     */
    public static JSONArray list2json(Collection<?> collection) {
        JSONArray jsonArray = new JSONArray();

        if (collection != null && collection.size() > 0) {
            for (Object obj : collection) {
                if (obj instanceof Map) {
                    jsonArray.add(simpleMap2json((Map) obj));
                }
            }
        }

        return jsonArray;
    }

    /**
     * @param map map对象
     * @return String
     */
    public static JSONObject map2json(Map map) {
        JSONObject jsonObject = new JSONObject();

        if (map != null && map.size() > 0) {
            for (Object key : map.keySet()) {
                Object value = map.get(key);

                if (value instanceof List) {
                    jsonObject.put(key, list2json((List) value));
                } else if (value instanceof Set) {
                    jsonObject.put(key, list2json((Set) value));
                } else {
                    jsonObject.put(key, value);
                }
            }
        }

        return jsonObject;
    }

    /**
     * @param map map对象
     * @return String
     */
    public static JSONObject simpleMap2json(Map map) {
        JSONObject jsonObject = new JSONObject();

        if (map != null && map.size() > 0) {
            for (Object key : map.keySet()) {
                Object value = map.get(key);
                jsonObject.put(key, value);
            }
        }

        return jsonObject;
    }

    /**
     * @param set 集合对象
     * @return String
     */
    public static JSONArray set2json(Set<?> set) {
        JSONArray jsonArray = new JSONArray();

        if (set != null && set.size() > 0) {
            for (Object obj : set) {
                if (obj instanceof HashMap) {
                    jsonArray.add(map2json((Map) obj));
                }
            }
        }

        return jsonArray;
    }

    /**
     * @param str 参数
     * @return String
     */
    public static JSONObject string2json(String str) {
        if ((!str.isEmpty())&& JsonValidator.validate(str)) {
            return JSONObject.fromObject(str);
        }

        return null;
    }

    /**
     * @param str 参数
     * @return String
     */
    public static JSONArray stringToJSONArray(String str) {
        if ((!str.isEmpty()) && JsonValidator.validate(str)) {
            return JSONArray.fromObject(str);
        }

        return null;
    }

    public static List<String> jsonArray2StrList(JSONArray arr) {
        if (null != arr && arr.size() > 0) {
            List<String> list = new ArrayList<String>();

            for (int i = 0; i < arr.size(); i++) {
                list.add(arr.getString(i));
            }

            return list;
        }

        return null;
    }
    
}
