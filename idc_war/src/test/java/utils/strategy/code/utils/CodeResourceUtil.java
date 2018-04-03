package utils.strategy.code.utils;

import java.util.ResourceBundle;

@SuppressWarnings("unused")
public class CodeResourceUtil {
	// 代码生成器的配置文件
	private static final ResourceBundle bundle = ResourceBundle.getBundle("config/jdbc");
	// webapp路径
	public static String webroot_package = "webapp";
	// 源代码路径
	public static String source_root_package = "src/main/java";
	// 业务包路径
	public static String bussiPackage = "sun";
	// 业务包url
	public static String bussiPackageUrl = "sun";
	// 实体类包路径
	public static String entity_package = "model";
	//默认数据库
	public static String table_schema = "sys";
	//作者
	public static String author = "Administrator";
	
	// 页面路径
	static {
		source_root_package = getSourceRootPackage();
		bussiPackage = getBussiPackage();
		bussiPackageUrl = bussiPackage.replace(".", "/");
		table_schema = getTableSchema();
		author = getAuthor();
	}

	private void ResourceUtil() {
	}

	private static String getAuthor() {
		return bundle.getString("author");
	}

	private static String getBussiPackage() {
		return bundle.getString("bussi_package");
	}

	public static final String getSourceRootPackage() {
		return bundle.getString("source_root_package");
	}

	public static final String getTableSchema() {
		return bundle.getString("table_schema");
	}
	//获取springsecurity的权限前缀
	public static final String getRolePrefix() {
		return bundle.getString("ROLEPREFIX");
	}

	public static void main(String[] args) {
		System.out.println(getProjectPath());
	}

	public static String getProjectPath() {
		String path = System.getProperty("user.dir").replace("\\", "/") + "/";
		return path;
	}
}
