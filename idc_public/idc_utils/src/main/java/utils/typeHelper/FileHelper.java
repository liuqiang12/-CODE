package utils.typeHelper;

import utils.DevLog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

public class FileHelper {

	
	private static Properties props = new Properties();
	private static String propertyPath ;
	private static FileHelper instance;


	private FileHelper() {}

    //单例模式【线程安全】
	public static synchronized  FileHelper getInstance() {
		if (instance == null) {
			instance = new FileHelper();
		}
		return instance;
	}
	/**
	 * 传入文件名以及字符串, 将字符串信息保存到文件中
	 *
	 * @param strFilename
	 * @param strBuffer
	 */
	public void textToFile(final String strFilename, final String strBuffer) throws Exception
	{
		FileWriter fileWriter = null;
		try
		{
			// 创建文件对象
			File fileText = new File(strFilename);
			// 向文件写入对象写入信息
			fileWriter = new FileWriter(fileText);
			// 写文件
			fileWriter.write(strBuffer);
			// 关闭
			fileWriter.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}finally {
			if(fileWriter != null){
				fileWriter.close();
			}
		}
	}

	/**
	 * 获取classpath路径
	 * 
	 * @return
	 */
	public String getRootClassPath() {
		URL classpath = this.getClass().getResource("/");
		String basePath = "/";
		try {
			basePath = new File(classpath.toURI()).getAbsolutePath();
			if(basePath.indexOf("classes") > -1){
				basePath = basePath.substring(0,basePath.indexOf("classes")+7);
			}
		} catch (URISyntaxException localURISyntaxException) {
		}
		basePath = basePath.replace("\\", "/");
		if (!basePath.endsWith("/")) {
			basePath = basePath + "/";
		}
		return basePath;
	}
	public String getRootName() {
		String basePath = getRootClassPath();
		System.out.println(">>>>>>>>>>>>>>>>>>"+basePath+"<<<<<<<<<<<<<<<<<<<");
		/**
		 * 获取classpath路径
		 */
		return null;
	}
	/**
	 * 获取项目路径
	 * @return
	 */
	public  String getProjectPath() {
		String path = System.getProperty("user.dir").replace("\\", "/") + "/";
		return path;
	}

	public static void main(String[] args) {
		String str = "E:/IntelliJ/CODE/idc_war/target/idc/WEB-INF/classes/";
		/*WEB---info和target之间*/
	}
}
