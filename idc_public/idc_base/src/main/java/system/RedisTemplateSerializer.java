package system;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.SerializationException;
import system.data.supper.service.JavaSerializer;
import utils.plugins.ReflectUtil;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DELL on 2017/9/19.
 */
public class RedisTemplateSerializer {
    public static Map<byte[],byte[]> getMOdelByByte(RedisTemplate redisTemplate, JavaSerializer serializableMethod, Object obj){
        Map<byte[],byte[]> map = new HashMap<byte[],byte[]>();
        Field[] fields = ReflectUtil.getFields(obj);
        for(int i = 0 ;i < fields.length; i++){
            //通过field 通过实体中获取值
            Object  fieldObj = ReflectUtil.getFieldValue(obj,fields[i].getName());
            Field field = fields[i];

            if(fieldObj != null){

                byte[] byteKey = redisTemplate.getStringSerializer().serialize(fields[i].getName());
                /*获取的类型*/
                if(fieldObj != null){
                    byte[] byteKeyVal = redisTemplate.getStringSerializer().serialize(getKeyVal(field.getType().getName(),fieldObj));
                    map.put(byteKey,byteKeyVal);
                }
            }
        }
        return map;
    }
    public static String getKeyVal(String dataType,Object fieldObj) {
        String resultStr = null;
        dataType = dataType.toLowerCase();
        if (dataType.contains("char"))
            resultStr = String.valueOf(fieldObj);
        else if (dataType.contains("string"))
            resultStr = String.valueOf(fieldObj);
        else if (dataType.contains("tinyint"))
            resultStr = String.valueOf(fieldObj);
        else if (dataType.contains("bigint"))
            resultStr = String.valueOf(fieldObj);
        else if (dataType.contains("int"))
            resultStr = String.valueOf(fieldObj);
        else if (dataType.contains("float"))
            resultStr = String.valueOf(fieldObj);
        else if (dataType.contains("double"))
            resultStr = String.valueOf(fieldObj);
        else if (dataType.contains("Long")) {
            resultStr = String.valueOf(fieldObj);
        } else if (dataType.contains("decimal"))
            resultStr = String.valueOf(fieldObj);
        else if (dataType.contains("date"))
            //日期格式
            try{
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date_ = null;
                try {
                    date_ = sdf.format(fieldObj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                resultStr = date_;
             } catch (SerializationException e) {
                e.printStackTrace();
            }
        return resultStr;
    }
}
