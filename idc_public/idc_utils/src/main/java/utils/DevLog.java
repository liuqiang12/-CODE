package utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by DELL on 2017/9/1.
 */
public class DevLog {
    private static final Log log = LogFactory.getLog(DevLog.class);

    public static void trace(Object paramObject)
    {
        if (log.isTraceEnabled())
            log.trace(paramObject);
    }

    public static void trace(Object paramObject, Throwable paramThrowable)
    {
        if (log.isTraceEnabled())
            log.trace(paramObject, paramThrowable);
    }

    public static void debug(Object paramObject)
    {
        if (log.isDebugEnabled())
            log.debug(paramObject);
    }

    public static void debug(Object paramObject, Throwable paramThrowable)
    {
        if (log.isDebugEnabled())
            log.debug(paramObject, paramThrowable);
    }

    public static void info(Object paramObject)
    {
        if (log.isInfoEnabled())
            log.info(paramObject);
    }

    public static void info(Object paramObject, Throwable paramThrowable)
    {
        if (log.isInfoEnabled())
            log.info(paramObject, paramThrowable);
    }

    public static void warn(Object paramObject)
    {
        if (log.isWarnEnabled())
            log.warn(paramObject);
    }

    public static void warn(Object paramObject, Throwable paramThrowable)
    {
        if (log.isWarnEnabled())
            log.warn(paramObject, paramThrowable);
    }

    public static void error(Object paramObject)
    {
        if (log.isErrorEnabled())
            log.error(paramObject);
    }

    public static void error(Object paramObject, Throwable paramThrowable)
    {
        if (log.isErrorEnabled())
            log.error(paramObject, paramThrowable);
    }

    public static void fatal(Object paramObject)
    {
        if (log.isFatalEnabled())
            log.fatal(paramObject);
    }

    public static void fatal(Object paramObject, Throwable paramThrowable)
    {
        if (log.isFatalEnabled())
            log.fatal(paramObject, paramThrowable);
    }
}
