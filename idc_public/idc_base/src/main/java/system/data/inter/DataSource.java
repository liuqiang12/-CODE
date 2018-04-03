package system.data.inter;

import java.lang.annotation.*;

/**
 * 自定义注解,该注解是在方法和类型上
 * 目的是切换数据源
 * @author Administrator
 *
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
	public String value() default "mysql_master";
}