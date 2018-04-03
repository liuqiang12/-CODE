package utils.plugins.cfg;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import utils.typeHelper.FileHelper;

@SuppressWarnings("unused")
/**
 * 单例
 * 
 * @author Administrator
 * 
 */
public class CfgHelper {
	
	private static Properties props = new Properties();
	private static String propertyPath ;
	private static CfgHelper instance;

	private CfgHelper() {};
	//单例模式【线程安全】
	public static synchronized  CfgHelper getInstance() {
		if (instance == null) {
			instance = new CfgHelper();
		}
		return instance;
	}
	/**
	 * 加载文件
	 * @param path
	 */
	public CfgHelper load(String path){
		File file = new File(FileHelper.getInstance().getRootClassPath()+path);
		propertyPath = FileHelper.getInstance().getRootClassPath()+path;
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			props.load(new FileInputStream(FileHelper.getInstance().getRootClassPath()+path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			System.exit(-1);
		}
		return instance;
	}
	/**
	 * 读取属性文件中相应键的值
	 * 
	 * @param key
	 *            主键
	 * @return String
	 */
	public String getKeyValue(String key) {
		return props.getProperty(key);
	}

	/**
	 * 根据主键key读取主键的值value
	 * 
	 * @param filePath
	 *            属性文件路径
	 * @param key
	 *            键名
	 */
	public String readValue(String filePath, String key) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(
					filePath));
			props.load(in);
			String value = props.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 更新（或插入）一对properties信息(主键及其键值) 如果该主键已经存在，更新该主键的值； 如果该主键不存在，则插件一对键值。
	 */
	public void writeProperties(String keyname, String keyvalue) {

		try {
			OutputStream fos = new FileOutputStream(propertyPath);
			props.setProperty(keyname, keyvalue);
			props.store(fos, "Update '" + keyname + "' value");
		} catch (IOException e) {
			System.err.println("属性文件更新错误");
		}
	}

	/**
	 * 更新properties文件的键值对 如果该主键已经存在，更新该主键的值； 如果该主键不存在，则插件一对键值。
	 */
	public void updateProperties(String keyname, String keyvalue) {
		try {
			props.load(new FileInputStream(propertyPath));
			OutputStream fos = new FileOutputStream(propertyPath);
			props.setProperty(keyname, keyvalue);
			props.store(fos, "Update '" + keyname + "' value");
		} catch (IOException e) {
			System.err.println("属性文件更新错误");
		}
	}
}
