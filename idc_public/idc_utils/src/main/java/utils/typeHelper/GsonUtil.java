package utils.typeHelper;

import com.google.gson.Gson;

/**
 * Created by DELL on 2017/9/14.
 */
public class GsonUtil {
    public static String object2Json(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public static <T> T json2Object(String json, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }
}
