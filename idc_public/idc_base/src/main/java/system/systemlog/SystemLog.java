package system.systemlog;

import java.lang.annotation.*;

/**
 * Created by mylove on 2016/12/14.
 * 日志管理
 * 参数使用说明：
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented

public @interface SystemLog {
    String description() default "";

    int type() default 1004;
}
