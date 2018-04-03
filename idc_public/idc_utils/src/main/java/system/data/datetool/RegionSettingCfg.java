package system.data.datetool;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@SuppressWarnings("unused")
/**
 * 单例
 * 
 * @author Administrator
 * 
 */
public class RegionSettingCfg {
	
	
	private static Properties props = new Properties();
	private static RegionSettingCfg instance;

	private RegionSettingCfg() {};
	//单例模式【线程安全】
	public static synchronized  RegionSettingCfg getInstance() {
		if (instance == null) {
			instance = new RegionSettingCfg();
		}
		return instance;
	}
	/**
	 * 加载文件
	 * @param path
	 */
	public RegionSettingCfg load(){
		try {
			InputStream in = this.getClass().getResourceAsStream("/region.properties");
			props.load(in);
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
}
