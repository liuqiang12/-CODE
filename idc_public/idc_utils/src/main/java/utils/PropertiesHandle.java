package utils;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesHandle {

	private static Logger logger = Logger.getLogger(PropertiesHandle.class);
	private volatile static PropertiesHandle ph = new PropertiesHandle(
			"ziyuan.properties");
	private String fileName;
	private Properties props;

	/**
	 * 通过key获取ziyuan.Properties配置中对应key的value值
	 * 
	 * @param key
	 *            properties的key
	 * @return properties的value
	 */
	public static String getResuouceInfo(String key) {
		return ph.getProperty(key);
	}

	public static String getResuouceInfo_utf8(String key){
		try{
		return new String(ph.getProperty(key).getBytes("iso-8859-1"),"utf8");
		}catch(Exception e){
			return null;
		}
	}
	
	public PropertiesHandle(String fileName) {
		
		this.fileName = fileName;
		loadData();
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public String getProperty(String key) {
		return props.getProperty(key);
	}
	
	public String getProperty_gbk(String key){
		try{
		return new String(props.getProperty(key).getBytes(),"utf8");
		}catch(Exception e){
			return null;
		}
	}

	/**
	 * @param key
	 * @param value
	 */
	public void setProperty(String key, String value) {
		props.setProperty(key, value);

	}

	/**
	 * 
	 * 
	 */
	private void loadData() {
		try {
			props = new Properties();
			InputStream in = null;
			String currentDir ;
			currentDir = PropertiesHandle.class.getProtectionDomain().getCodeSource().getLocation().getFile();
			if(currentDir.indexOf("/lib")!=-1){
				currentDir = currentDir.split("/lib")[0];
			}else if(currentDir.indexOf("/target")!=-1){
				currentDir = currentDir.split("/target")[0];
			}else{
				currentDir = currentDir.substring(0,currentDir.lastIndexOf("/"));
			}
			fileName = currentDir + File.separator + fileName;
			logger.info("fileName:"+fileName);
			System.out.println("fileName:"+fileName);
			if (null == in) {
				in = new FileInputStream(fileName);
			}
			props.load(in);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			props = null;
		}
	}

	public String getFileName() {
		return this.fileName;
	}
}
