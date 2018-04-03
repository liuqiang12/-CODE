package system.authority.aop;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import system.data.datasource.DataSourceContextHolder;
import system.data.inter.DataSource;

@Component
@Aspect
public class MultiDataSourceAop implements Ordered {
	private final Log log = LogFactory.getLog(this.getClass());
	/**
	 * 拦截目标方法，获取由@DataSource指定的数据源标识，设置到线程存储中以便切换数据源
	 * 
	 * @param joinPoint
	 */
	@Before(value = "execution(* com.idc..service.impl.*Impl.*(..))")
	public void dynamicSetDataSource(JoinPoint joinPoint) {
		Class<?> target = joinPoint.getTarget().getClass();
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		// 默认使用目标类型的注解，如果没有则使用其实现接口的注解
		for (Class<?> clazz : target.getInterfaces()) {
			resolveDataSource(clazz, signature.getMethod());
		}
		resolveDataSource(target, signature.getMethod());
	}

	/**
	 * 提取目标对象方法注解和类型注解中的数据源标识
	 * 
	 * @param clazz
	 * @param method
	 */
	private void resolveDataSource(Class<?> clazz, Method method) {
		try {
			Class<?>[] types = method.getParameterTypes();
			// 默认使用类型注解
			if (clazz.isAnnotationPresent(DataSource.class)) {
				DataSource source = clazz.getAnnotation(DataSource.class);
				DataSourceContextHolder.setDataSourceType(source.value());
			}else{
				DataSourceContextHolder.setDataSourceType(DataSourceContextHolder.DATA_SOURCE_MYSQL_MASTER);
			}
			// 方法注解可以覆盖类型注解
			Method m = clazz.getMethod(method.getName(), types);
			if (m != null && m.isAnnotationPresent(DataSource.class)) {
				DataSource source = m.getAnnotation(DataSource.class);
				DataSourceContextHolder.setDataSourceType(source.value());
			}
		} catch (Exception e) {
			log.error(clazz + ":" + e.getMessage());
		}
	}

	@Override
	public int getOrder() {
		return 1;
	}
}
