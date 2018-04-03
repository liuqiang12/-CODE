//package system.data.page.core;
//
//import java.sql.Statement;
//import java.util.Properties;
//
//import org.apache.ibatis.executor.resultset.ResultSetHandler;
//import org.apache.ibatis.mapping.BoundSql;
//import org.apache.ibatis.plugin.Interceptor;
//import org.apache.ibatis.plugin.Intercepts;
//import org.apache.ibatis.plugin.Invocation;
//import org.apache.ibatis.plugin.Plugin;
//import org.apache.ibatis.plugin.Signature;
//import org.apache.ibatis.session.RowBounds;
//
///**
// * 获取结果集合
// * @author Administrator
// *
// */
//@Intercepts( {@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})})
//public class DiclectResultSetHandlerInterceptor implements Interceptor
//{
//
//    public Object intercept(Invocation invocation) throws Throwable
//    {
//    	ResultSetHandler resultSet = (ResultSetHandler)invocation.getTarget();
//
//        RowBounds rowBounds = (RowBounds)ReflectionHelper.getFieldValue(resultSet,"rowBounds");
//
//        BoundSql boundsql = (BoundSql) ReflectionHelper.getFieldValue(resultSet, "boundSql");
//        //sql
//        String sql = boundsql.getSql();
//        // 获取handleResultSets
////        Object o = invocation.proceed();
////        DefaultResultHandler list =    (DefaultResultHandler) ReflectionHelper.getFieldValue(resultSet, "resultHandler");
////
////		System.out.println(list.getResultList());
////
//
//
//        if (rowBounds.getLimit() > 0  && rowBounds.getLimit() < RowBounds.NO_ROW_LIMIT)
//        {
//        	ReflectionHelper.setFieldValue(resultSet, "rowBounds", new RowBounds());
//        }
//        return invocation.proceed();
//    }
//
//    public Object plugin(Object target)
//    {
//        return Plugin.wrap(target, this);
//    }
//
//    public void setProperties(Properties properties)
//    {
//    }
//}
