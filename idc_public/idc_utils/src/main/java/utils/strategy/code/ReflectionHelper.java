package utils.strategy.code;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import utils.strategy.code.model.ColumnData;
import utils.strategy.code.utils.JdbcHelper;
import utils.typeHelper.StringHelper;

public class ReflectionHelper {

	private static Log logger = LogFactory.getLog(ReflectionHelper.class);

	private static Object operate(Object obj, String fieldName,
			Object fieldVal, String type) {
		Object ret = null;
		try {
			// 获得对象类型
			Class<? extends Object> classType = obj.getClass();
			// 获得对象的所有属性
			Field fields[] = classType.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				if (field.getName().equals(fieldName)) {

					String firstLetter = fieldName.substring(0, 1)
							.toUpperCase(); // 获得和属性对应的getXXX()方法的名字
					if ("set".equals(type)) {
						String setMethodName = "set" + firstLetter
								+ fieldName.substring(1); // 获得和属性对应的getXXX()方法
						Method setMethod = classType.getMethod(setMethodName,
								new Class[] { field.getType() }); // 调用原对象的getXXX()方法
						ret = setMethod.invoke(obj, new Object[] { fieldVal });
					}
					if ("get".equals(type)) {
						String getMethodName = "get" + firstLetter
								+ fieldName.substring(1); // 获得和属性对应的setXXX()方法的名字
						Method getMethod = classType.getMethod(getMethodName,
								new Class[] {});
						ret = getMethod.invoke(obj, new Object[] {});
					}
					return ret;
				}
			}
		} catch (Exception e) {
			logger.warn("reflect error:" + fieldName, e);
		}
		return ret;
	}

	public static Object getVal(Object obj, String fieldName) {
		return operate(obj, fieldName, null, "get");
	}

	public static void setVal(Object obj, String fieldName, Object fieldVal) {
		operate(obj, fieldName, fieldVal, "set");
	}

	private static Method getDeclaredMethod(Object object, String methodName,
			Class<?>[] parameterTypes) {
		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				// superClass.getMethod(methodName, parameterTypes);
				return superClass.getDeclaredMethod(methodName, parameterTypes);
			} catch (NoSuchMethodException e) {
				// Method 不在当前类定义, 继续向上转型
			}
		}

		return null;
	}

	private static void makeAccessible(Field field) {
		if (!Modifier.isPublic(field.getModifiers())) {
			field.setAccessible(true);
		}
	}

	private static Field getDeclaredField(Object object, String filedName) {
		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(filedName);
			} catch (NoSuchFieldException e) {
				// Field 不在当前类定义, 继续向上转型
			}
		}
		return null;
	}

	public static Object invokeMethod(Object object, String methodName,
			Class<?>[] parameterTypes, Object[] parameters)
			throws InvocationTargetException {
		Method method = getDeclaredMethod(object, methodName, parameterTypes);

		if (method == null) {
			throw new IllegalArgumentException("Could not find method ["
					+ methodName + "] on target [" + object + "]");
		}

		method.setAccessible(true);

		try {
			return method.invoke(object, parameters);
		} catch (IllegalAccessException e) {

		}

		return null;
	}

	public static void setFieldValue(Object object, String fieldName,
			Object value) {
		Field field = getDeclaredField(object, fieldName);

		if (field == null)
			throw new IllegalArgumentException("Could not find field ["
					+ fieldName + "] on target [" + object + "]");

		makeAccessible(field);

		try {
			field.set(object, value);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static Object getFieldValue(Object object, String fieldName) {
		Field field = getDeclaredField(object, fieldName);
		if (field == null)
			throw new IllegalArgumentException("Could not find field ["
					+ fieldName + "] on target [" + object + "]");

		makeAccessible(field);

		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 得到相应的字段
	 * 
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Field getFieldByFieldName(Object obj, String fieldName) {
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
			}
		}
		return null;
	}

	/**
	 * @param obj
	 * @param fieldName
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Object getValueByFieldName(Object obj, String fieldName)
			throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		Field field = getFieldByFieldName(obj, fieldName);
		Object value = null;
		if (field != null) {
			if (field.isAccessible()) {
				value = field.get(obj);
			} else {
				field.setAccessible(true);
				value = field.get(obj);
				field.setAccessible(false);
			}
		}
		return value;
	}

	/**
	 * @param obj
	 * @param fieldName
	 * @param value
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void setValueByFieldName(Object obj, String fieldName,
			Object value) throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		Field field = obj.getClass().getDeclaredField(fieldName);
		if (field.isAccessible()) {
			field.set(obj, value);
		} else {
			field.setAccessible(true);
			field.set(obj, value);
			field.setAccessible(false);
		}
	}

	public static void main(String[] args) {
		// getMethods(ReflectUtils.class);
	}

	/**
	 * 获得参数化类型的泛型类型，取第一个参数的泛型类型，（默认去的第一个）
	 * 
	 * @param clazz
	 *            参数化类型
	 * @return 泛型类型
	 */
	@SuppressWarnings("unchecked")
	public static Class getClassGenricType(final Class clazz) {
		return getClassGenricType(clazz, 0);
	}

	/**
	 * 根据参数索引获得参数化类型的泛型类型，（通过索引取得）
	 * 
	 * @param clazz
	 *            参数化类型
	 * @param index
	 *            参数索引
	 * @return 泛型类型
	 */
	@SuppressWarnings("unchecked")
	public static Class getClassGenricType(final Class clazz, final int index) {
		Type genType = clazz.getGenericSuperclass();
		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (index >= params.length || index < 0) {
			throw new RuntimeException("Index outof bounds");
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}
		return (Class) params[index];
	}

	public String getId(Object obj) {
		try {
			Field[] fields = obj.getClass().getDeclaredFields();
			String id = null;
			for (Field field : fields) {
				if (field.isAnnotationPresent(javax.persistence.Id.class)) {
					String fieldName = field.getName();
					String getMethodName = "get"
							+ StringHelper.getInstance()
									.getStrByUpperFirstChar(fieldName);
					Method getMethod = obj.getClass().getMethod(getMethodName,
							new Class[] {});
					id = (String) getMethod.invoke(obj, new Object[] {});
					break;
				}
			}
			return id;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String setId(Object obj) {
		try {
			Field[] fields = obj.getClass().getDeclaredFields();
			String id = null;
			for (Field field : fields) {
				if (field.isAnnotationPresent(javax.persistence.Id.class)) {
					String fieldName = field.getName();
					String setMethodName = "set"
							+ StringHelper.getInstance()
									.getStrByUpperFirstChar(fieldName);
					Method setMethod = obj.getClass().getMethod(setMethodName,
							new Class[] { field.getType() });
					// id = StringHelper.getInstance().createUUID();
					setMethod.invoke(obj, new Object[] { id });// 调用对象的setXXX方法
					break;
				}
			}
			return id;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getTableName() {
		javax.persistence.Table table = this.getClass().getAnnotation(
				javax.persistence.Table.class);
		if (null != table) {
			return table.name();
		}
		return null;
	}

	/**
	 * 通过构造函数实例化对象
	 * 
	 * @param className
	 *            类的全路径名称
	 * @param parameterTypes
	 *            参数类型
	 * @param initargs
	 *            参数值
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Object constructorNewInstance(String className,
			Class[] parameterTypes, Object[] initargs) {
		try {
			Constructor<?> constructor = (Constructor<?>) Class.forName(
					className).getDeclaredConstructor(parameterTypes); // 暴力反射
			constructor.setAccessible(true);
			return constructor.newInstance(initargs);
		} catch (Exception ex) {
			throw new RuntimeException();
		}

	}

	/**
	 * 暴力反射获取字段值
	 * 
	 * @param fieldName
	 *            属性名
	 * @param obj
	 *            实例对象
	 * @return 属性值
	 */
	public static Object getFieldValue(String propertyName, Object obj) {
		try {
			Field field = obj.getClass().getDeclaredField(propertyName);
			field.setAccessible(true);
			return field.get(obj);
		} catch (Exception ex) {
			throw new RuntimeException();
		}
	}

	/**
	 * 暴力反射获取字段值
	 * 
	 * @param propertyName
	 *            属性名
	 * @param object
	 *            实例对象
	 * @return 字段值
	 */
	public static Object getProperty(String propertyName, Object object) {
		try {

			PropertyDescriptor pd = new PropertyDescriptor(propertyName,
					object.getClass());
			Method method = pd.getReadMethod();
			return method.invoke(object);
		} catch (Exception ex) {
			throw new RuntimeException();
		}
	}

	/**
	 * 通过BeanUtils工具包获取反射获取字段值,注意此值是以字符串形式存在的,它支持属性连缀操作:如,.对象.属性
	 * 
	 * @param propertyName
	 *            属性名
	 * @param object
	 *            实例对象
	 * @return 字段值
	 */
	public static Object getBeanInfoProperty(String propertyName, Object object) {
		try {
			return BeanUtils.getProperty(object, propertyName);
		} catch (Exception ex) {
			throw new RuntimeException();
		}
	}

	/**
	 * 通过BeanUtils工具包获取反射获取字段值,注意此值是以字符串形式存在的
	 * 
	 * @param object
	 *            实例对象
	 * @param propertyName
	 *            属性名
	 * @param value
	 *            字段值
	 * @return
	 */
	public static void setBeanInfoProperty(Object object, String propertyName,
			String value) {
		try {
			BeanUtils.setProperty(object, propertyName, value);
		} catch (Exception ex) {
			throw new RuntimeException();
		}
	}

	/**
	 * 通过BeanUtils工具包获取反射获取字段值,注意此值是以对象属性的实际类型
	 * 
	 * @param propertyName
	 *            属性名
	 * @param object
	 *            实例对象
	 * @return 字段值
	 */
	public static Object getPropertyUtilByName(String propertyName,
			Object object) {
		try {
			return PropertyUtils.getProperty(object, propertyName);
		} catch (Exception ex) {
			throw new RuntimeException();
		}
	}

	/**
	 * 通过BeanUtils工具包获取反射获取字段值,注意此值是以对象属性的实际类型,这是PropertyUtils与BeanUtils的根本区别
	 * 
	 * @param object
	 *            实例对象
	 * @param propertyName
	 *            属性名
	 * @param value
	 *            字段值
	 * @return
	 */
	public static void setPropertyUtilByName(Object object,
			String propertyName, Object value) {
		try {
			PropertyUtils.setProperty(object, propertyName, value);
		} catch (Exception ex) {
			throw new RuntimeException();
		}
	}

	/**
	 * 设置字段值
	 * 
	 * @param obj
	 *            实例对象
	 * @param propertyName
	 *            属性名
	 * @param value
	 *            新的字段值
	 * @return
	 */
	public static void setProperties(Object object, String propertyName,
			Object value) throws IntrospectionException,
			IllegalAccessException, InvocationTargetException {
		PropertyDescriptor pd = new PropertyDescriptor(propertyName,
				object.getClass());
		Method methodSet = pd.getWriteMethod();
		methodSet.invoke(object, value);
	}

	/**
	 * 设置字段值
	 * 
	 * @param className
	 *            类的全路径名称
	 * @param methodName
	 *            调用方法名
	 * @param parameterTypes
	 *            参数类型
	 * @param values
	 *            参数值
	 * @param object
	 *            实例对象
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Object methodInvoke(String className, String methodName,
			Class[] parameterTypes, Object[] values, Object object) {
		try {
			Method method = Class.forName(className).getDeclaredMethod(
					methodName, parameterTypes);
			method.setAccessible(true);
			return method.invoke(object, values);
		} catch (Exception ex) {
			throw new RuntimeException();
		}
	}

	/**
	 * 生成get方法名称
	 * 
	 * @param pro
	 * @param type
	 * @return
	 */
	public static String createModelGetMethodName(String pro, String type) {
		StringBuffer getset = new StringBuffer();

		if (pro != null && !"".equals(pro)) {
			String proName = StringHelper.getInstance().reEscapeName(
					pro.toLowerCase(), true);
			// 首字母大写
			String strByUpperFirstChar = StringHelper.getInstance()
					.getStrByUpperFirstChar(proName);

			getset.append("\r\t").append("public " + type + " ")
					.append("get" + strByUpperFirstChar + "() {\r\t");
			getset.append("    return this." + proName + "").append(";\r\t}");
		}
		return getset.toString();
	}

	/**
	 * 生成set方法名称
	 * 
	 * @param pro
	 * @param type
	 * @param isCreatAnno
	 *            false 不生成注解 true生成注解;默认是生成
	 * @return
	 */
	public static String createModelSetMethodName(String pro, String type,
			ColumnData columnData, Boolean isCreatAnno) {
		StringBuffer getset = new StringBuffer();

		if (pro != null && !"".equals(pro)) {
			String proName = StringHelper.getInstance().reEscapeName(
					pro.toLowerCase(), true);
			// 首字母大写
			String strByUpperFirstChar = StringHelper.getInstance()
					.getStrByUpperFirstChar(proName);
			// 这里增加 列信息
			// 判断type类型 后处理注解方式
			if (isCreatAnno) {
				getset.append("\r\t").append(
						JdbcHelper.getInstance().getTypeAnnoName(columnData));
			}
			getset.append("\r\t")
					.append("public void ")
					.append("set" + strByUpperFirstChar + "(" + type + " "
							+ proName + ") {\r\t");
			getset.append("    this." + proName + "=").append(proName)
					.append(";\r\t}");
		}
		return getset.toString();
	}

	/**
	 * 生成set方法名称
	 * 
	 * @param pro
	 * @param type
	 * @param isCreatAnno
	 *            false 不生成注解 true生成注解;默认是生成
	 * @return
	 */
	public static String createModelSetMethodNameWithDateStr(String pro,
			String type, ColumnData columnData, Boolean isCreatAnno) {
		StringBuffer getset = new StringBuffer();

		if (pro != null && !"".equals(pro)) {
			String proName = StringHelper.getInstance().reEscapeName(
					pro.toLowerCase(), true);
			// 首字母大写
			String strByUpperFirstChar = StringHelper.getInstance()
					.getStrByUpperFirstChar(proName + "Str");
			getset.append("\r\t")
					.append("public void ")
					.append("set" + strByUpperFirstChar + "(" + type + " "
							+ proName + ") {");
			// 数据格式化
			getset.append("\r\t\t")
					.append("SimpleDateFormat sdf = new SimpleDateFormat(\"yyyy-MM-dd HH:mm:ss\");");
			getset.append("\r\t\t").append("String date_ = null;");
			getset.append("\r\t\t").append("try {");
			getset.append("\r\t\t").append(
					"	date_ = sdf.format(" + proName + ");");
			getset.append("\r\t\t").append("} catch (Exception e) {");
			getset.append("\r\t\t").append("	e.printStackTrace();");
			getset.append("\r\t\t").append("}");
			getset.append("\r\t\t").append("this." + proName + "Str = date_;");
			getset.append("\r\t}");
		}
		return getset.toString();
	}

	/**
	 * 利用发射的方式，将pageBean里面的东西，进行修改
	 */
	public static void reflectBean(Object obj, String filedName, Object value) {
		// 获取对应的setItems(List list)
		Field field = getDeclaredField(obj, filedName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field ["
					+ filedName + "] on target [" + obj + "]");
		}
		makeAccessible(field);
		try {
			field.set(obj, value);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
