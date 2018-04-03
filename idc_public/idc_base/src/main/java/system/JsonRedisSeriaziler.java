package system;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;

/**
 * Created by DELL on 2017/9/19.
 */
public class JsonRedisSeriaziler {
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    protected ObjectMapper objectMapper = new ObjectMapper();

    private static JsonRedisSeriaziler ourInstance = new JsonRedisSeriaziler();

    public static JsonRedisSeriaziler getInstance() {
        return ourInstance;
    }
    public static final String EMPTY_JSON = "{}";


    public JsonRedisSeriaziler(){}

    /**
     * java-object as json-string
     * @param object
     * @return
     */
    public String seriazileAsString(Object object){
        if (object== null) {
            return EMPTY_JSON;
        }
        try {
            return this.objectMapper.writeValueAsString(object);
        } catch (Exception ex) {
            throw new SerializationException("Could not write JSON: " + ex.getMessage(), ex);
        }
    }

    /**
     * json-string to java-object
     * @param str
     * @return
     */
    public <T> T deserializeAsObject(String str,Class<T> clazz){
        if(str == null || clazz == null){
            return null;
        }
        try{
            return this.objectMapper.readValue(str, clazz);
        }catch (Exception ex) {
            throw new SerializationException("Could not write JSON: " + ex.getMessage(), ex);
        }
    }


}
