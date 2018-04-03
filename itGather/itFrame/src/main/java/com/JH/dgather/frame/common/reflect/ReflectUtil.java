package com.JH.dgather.frame.common.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * 反射工具类
 * 
 * @author yangDS
 *
 */
@SuppressWarnings("unchecked")
public class ReflectUtil {
	static Logger logger = Logger.getLogger(ReflectUtil.class.getName());
	
	public static Object getBeans(String className, HashMap attributeAndAttibuteValue) {
		if (attributeAndAttibuteValue == null)
			return null;
		Set<Entry> s = attributeAndAttibuteValue.entrySet();
		String attribute = null;
		Object attriValue = null;
		String methodName = null;
		Method m = null;
		Object instance = null;
		
		try {
			//logger.info("className: " + className);
			Class<?> clazz = Class.forName(className);
			instance = clazz.newInstance();
			
			for (Entry e : s) {
				attribute = e.getKey().toString().trim();
				attriValue = e.getValue();
				methodName = "set" + attribute.substring(0, 1).toUpperCase() + attribute.substring(1);
				m = clazz.getMethod(methodName, getClassType(attriValue));
				m.invoke(instance, attriValue);
				/****
				 * 测试
				 */
				if (attriValue == null) {
					
					logger.error(methodName + "is null");
				}
				
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			//			LogUtil.writeToLogFile("没有找到"+className+"指定的java类（注意使用java全限定名）");
			//			System.out.println();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			
			logger.error(className + "不允许访问" + methodName + "方法，请修改其修饰符", e);
			
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(className + "没有" + methodName + "方法", e);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return instance;
	}
	
	/**
	 * @param className
	 * @param attributeAndAttibuteValue
	 * @return
	 */
	public static Object getBeans(Class clazz, HashMap attributeAndAttibuteValue) {
		if (clazz == null)
			return null;
		if (attributeAndAttibuteValue == null)
			return null;
		Set<Entry> s = attributeAndAttibuteValue.entrySet();
		String attribute = null;
		Object attriValue = null;
		String methodName = null;
		Method m = null;
		Object instance = null;
		
		try {
			
			instance = clazz.newInstance();
			
			for (Entry e : s) {
				attribute = e.getKey().toString().trim();
				attriValue = e.getValue();
				methodName = "set" + attribute.substring(0, 1).toUpperCase() + attribute.substring(1);
				m = clazz.getMethod(methodName, getClassType(attriValue));
				m.invoke(instance, attriValue);
				
			}
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			logger.error(clazz.getName() + "不允许访问" + methodName + "方法，请修改其修饰符", e);
			
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return instance;
	}
	
	/**
	 * 如 com.application.sour.Action.innerAction
	 * outterName:com.application.sour.Action ;innerName=innerAction
	 * 
	 * @param outterObject 外部类全限定名
	 * @param innerName 内部类的名称simpleName
	 * @param avv 内部类的属性与值
	 */
	public static Object getBeanFromInnerClass(String outterName, String innerName, HashMap avv) {
		if (avv == null)
			return null;
		try {
			
			Class outterClass = Class.forName(outterName);
			
			Class innerClass = null;
			Object innerInstance = null;
			Object outterInstance = null;
			String attribute = null;
			Object attriValue = null;
			String methodName = null;
			Method m = null;
			outterInstance = outterClass.newInstance();
			
			for (Class c : outterClass.getDeclaredClasses()) {
				
				if (c.getSimpleName().equals(innerName)) {
					innerClass = c;
					break;
				}
				
			}
			
			if (innerClass == null) {
				System.err.println("在" + outterClass.getName() + "中没有找到内部类名为:" + innerName);
				return null;
			}
			
			Constructor c1 = innerClass.getConstructor(new Class[] { outterClass });
			innerInstance = c1.newInstance(new Object[] { outterInstance });
			
			Set<Entry<String, Object>> s = avv.entrySet();
			//		
			for (Entry<String, Object> e : s) {
				attribute = e.getKey().trim();
				attriValue = e.getValue();
				methodName = "set" + attribute.substring(0, 1).toUpperCase() + attribute.substring(1);
				m = innerClass.getMethod(methodName, getClassType(attriValue));
				m.invoke(innerInstance, attriValue);
				/****
				 * 测试
				 */
				
			}
			
			methodName = "set" + innerName.substring(0, 1).toUpperCase() + innerName.substring(1);
			m = outterClass.getMethod(methodName, new Class[] { innerClass });
			m.invoke(outterInstance, new Object[] { innerInstance });
			return outterInstance;
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 如 com.application.sour.Action.innerAction
	 * innerName=innerAction
	 * 
	 * @param outterObject 外部类实例
	 * @param innerName 内部类的名称simpleName
	 * @param avv 内部类的属性与值
	 */
	public static void setBeanFromInnerClass(Object outterInstance, String innerName, HashMap avv) {
		if (avv == null)
			return;
		try {
			
			Class outterClass = outterInstance.getClass();
			
			Class innerClass = null;
			Object innerInstance = null;
			
			String attribute = null;
			Object attriValue = null;
			String methodName = null;
			Method m = null;
			
			for (Class c : outterClass.getDeclaredClasses()) {
				
				if (c.getSimpleName().equals(innerName)) {
					innerClass = c;
					break;
				}
				
			}
			
			if (innerClass == null) {
				System.err.println("在" + outterClass.getName() + "中没有找到内部类名为:" + innerName);
				return;
			}
			
			Constructor c1 = innerClass.getConstructor(new Class[] { outterClass });
			innerInstance = c1.newInstance(new Object[] { outterInstance });
			
			Set<Entry<String, Object>> s = avv.entrySet();
			//		
			for (Entry<String, Object> e : s) {
				attribute = e.getKey().trim();
				attriValue = e.getValue();
				methodName = "set" + attribute.substring(0, 1).toUpperCase() + attribute.substring(1);
				m = innerClass.getMethod(methodName, getClassType(attriValue));
				m.invoke(innerInstance, attriValue);
				/****
				 * 测试
				 */
				
			}
			
			methodName = "set" + innerName.substring(0, 1).toUpperCase() + innerName.substring(1);
			m = outterClass.getMethod(methodName, new Class[] { innerClass });
			m.invoke(outterInstance, new Object[] { innerInstance });
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 得到简单对象的类型
	 * 
	 * @param ob
	 * @return
	 */
	public static Class getClassType(Object ob) {
		String typeStr = ob.getClass().getSimpleName();
		if (typeStr.equalsIgnoreCase("String")) {
			return String.class;
		}
		else
			if (typeStr.equalsIgnoreCase("Integer")) {
				return Integer.class;
			}
			else
				if (typeStr.equalsIgnoreCase("Float")) {
					return Float.class;
				}
				else
					if (typeStr.equalsIgnoreCase("Double")) {
						return Double.class;
					}
					else
						if (typeStr.equalsIgnoreCase("Long")) {
							return Long.class;
						}
						else
							if (typeStr.equalsIgnoreCase("Byte")) {
								return Byte.class;
							}
							else
								if (typeStr.equalsIgnoreCase("Character")) {
									return Character.class;
								}
								else
									if (typeStr.equalsIgnoreCase("Date")) {
										return Date.class;
									}
									else {
										return Object.class;
									}
	}
	
	/**
	 * 注：compareFields不包含复杂对象（如 数组，集合）
	 * @param bean1 需要比较的bean
	 * @param bean2 需要比较的bean
	 * @param compareFields    bean需要具体比较的的属性集合
	 * @return true 表明俩个bean值相同，false不相同 
	 * 
	 */
	public static boolean beanCompare(Object bean1, Object bean2, ArrayList<String> compareFields) {
		String methodName = null;
		Method m1 = null, m2 = null;
		Object v1 = null, v2 = null;
		for (String filed : compareFields) {
			methodName = "get" + filed.substring(0, 1).toUpperCase() + filed.substring(1);
			//			System.out.println("method::"+methodName);
			try {
				
				m1 = bean1.getClass().getMethod(methodName, null);
				m2 = bean2.getClass().getMethod(methodName, null);
				v1 = m1.invoke(bean1, null);
				v2 = m2.invoke(bean2, null);
				if (v1 == null && v2 == null) {
					
					continue;
				}
				else
					if (v1 == null || v2 == null) {
						return false;
					}
					else {
						if (!v1.toString().trim().equals(v2.toString().trim())) {
							return false;
						}
						else {
							continue;
						}
					}
				
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}
	
}
