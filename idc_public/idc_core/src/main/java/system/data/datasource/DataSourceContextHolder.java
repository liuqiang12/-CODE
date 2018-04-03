package system.data.datasource;

public class DataSourceContextHolder {
	
	public final static String DATA_SOURCE_MYSQL_MASTER = "mysql_master";
	public final static String DATA_SOURCE_MYSQL_SALVE= "mysql_salve";
	
	private static ThreadLocal<String> contextHolder= new ThreadLocal<String>();
	
	public static void setDataSourceType(String dataSourceType){
		contextHolder.set(dataSourceType);
	}
	
	public static String getDataSourceType(){
		return contextHolder.get();
	}
	
	public static void clearDataSourceType(){
		contextHolder.remove();
	}
	
}
