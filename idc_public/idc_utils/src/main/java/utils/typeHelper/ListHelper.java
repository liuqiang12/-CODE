package utils.typeHelper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mylove on 2016/12/28.
 */
public class ListHelper {

    /***
     * @param <T>
     * @param list
     * @param name 反射对象的属性方法，必须是完整的 如getXX,..不能为空，也不能为set
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public static <T> Map<Object, T> ListToMap(List<T> list, String name)
            throws InstantiationException, IllegalAccessException,
            SecurityException, NoSuchMethodException, IllegalArgumentException,
            InvocationTargetException, NoSuchFieldException {
        Map<Object, T> map = new HashMap<Object, T>();
        for (T t : list) {
            Field declaredField = t.getClass().getDeclaredField(name);
            declaredField.setAccessible(true);
            Object val = declaredField.get(t);
//            Field fields[]=t.getClass().getDeclaredFields();
//            Method m = (Method) t.getClass().getMethod(name);
//            Object val = m.invoke(t);
            map.put(val, t);
        }
        return map;
    }
}
