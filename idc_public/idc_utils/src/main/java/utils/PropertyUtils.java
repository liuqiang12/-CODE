package utils;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class PropertyUtils extends org.apache.commons.beanutils.PropertyUtils {
    public static <T> List<T> copyListProperties(T t, Collection objs)
            throws Exception {
        List<T> listVo = new ArrayList<T>();
        for (Object obj : objs) {
            T tempT = (T) t.getClass().newInstance();
            if (obj != null) {
                PropertyUtils.copyProperties(tempT, obj);
            }
            listVo.add(tempT);
        }
        return listVo;
    }
    public static void copyProperties(Object dest, Object orig)
            throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        if (dest == null) {
            throw new IllegalArgumentException("No destination bean specified");
        }

        if (orig == null) {
            throw new IllegalArgumentException("No origin bean specified");
        }

        if ((orig instanceof DynaBean)) {
            DynaProperty[] origDescriptors = ((DynaBean) orig).getDynaClass()
                    .getDynaProperties();

            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();
                if ((dest instanceof DynaBean)) {
                    if (isWriteable(dest, name)) {
//						 Object value = ((DynaBean) orig).get(name);
//						((DynaBean) dest).set(name, value);
                    }
                } else if (isWriteable(dest, name)) {
                    Object value = ((DynaBean) orig).get(name);
                    setSimpleProperty(dest, name, value);
                }
            }
        } else if ((orig instanceof Map)) {
            Iterator names = ((Map) orig).keySet().iterator();
            while (names.hasNext()) {
                String name = (String) names.next();
                if ((dest instanceof DynaBean)) {
                    if (isWriteable(dest, name)) {
                        Object value = ((Map) orig).get(name);
                        ((DynaBean) dest).set(name, value);
                    }
                } else if (isWriteable(dest, name)) {
                    Object value = ((Map) orig).get(name);
                    setSimpleProperty(dest, name, value);
                }
            }
        } else {
            PropertyDescriptor[] origDescriptors = getPropertyDescriptors(orig);

            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();
                if (isReadable(orig, name))
                    if ((dest instanceof DynaBean)) {
                        if (isWriteable(dest, name)) {
                            Object value = getSimpleProperty(orig, name);
                            ((DynaBean) dest).set(name, value);
                        }
                    } else if (isWriteable(dest, name)) {
                        Object value = getSimpleProperty(orig, name);
                        setSimpleProperty(dest, name, value);
                    }
            }
        }
    }
}
