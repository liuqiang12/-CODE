package utils.strategy.code.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import utils.DevContext;
import utils.XMLParser;
import utils.typeHelper.DateHelper;
import utils.typeHelper.FileHelper;

import java.io.*;
import java.util.Map;
import java.util.Properties;

public class CommonPageParser {
	private static VelocityEngine ve;
	  private static final String CONTENT_ENCODING = "UTF-8";
	  private static final Log log = LogFactory.getLog(CommonPageParser.class);

	  private static boolean isReplace = true;

	  static
	  {
	    try
	    {
	      String templateBasePath = FileHelper.getInstance().getRootClassPath() + "template";
	      Properties properties = new Properties();
	      properties.setProperty("resource.loader", "file");
	      properties.setProperty("file.resource.loader.description", "Velocity File Resource Loader");
	      properties.setProperty("file.resource.loader.path", templateBasePath);
	      properties.setProperty("file.resource.loader.cache", "true");
	      properties.setProperty("file.resource.loader.modificationCheckInterval", "30");
	      properties.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.Log4JLogChute");
	      properties.setProperty("runtime.log.logsystem.log4j.logger", "org.apache.velocity");
	      properties.setProperty("directive.set.null.allowed", "true");
	      VelocityEngine velocityEngine = new VelocityEngine();
	      
	      velocityEngine.init(properties);
	      ve = velocityEngine;
   
	    } catch (Exception e) {
	      log.error(e);
	    }
	  }

	  public static String WriterPage(VelocityContext context, String templateName, String fileDirPath, String targetFile)
	  {
		  try {
			  //初始化参数
			Properties properties=new Properties();
			//设置velocity资源加载方式为class
			properties.setProperty("resource.loader", "class");
			//设置velocity资源加载方式为file时的处理类
			properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
			//实例化一个VelocityEngine对象
			VelocityEngine velocityEngine=new VelocityEngine(properties);
			//实例化一个StringWriter
			StringWriter writerStr=new StringWriter();
			velocityEngine.mergeTemplate("template/"+templateName, "gbk", context, writerStr);

			//velocityEngine.mergeTemplate("hello.vm", "gbk", context, writer);
			String resultStr = writerStr.toString();
			textToFile(fileDirPath,resultStr,targetFile);
			return resultStr;
		} catch (Exception e) {
	    	log.error(e);
	    }
	    return null;
	  }


	public static void textToFile(final String fileDirPath, final String strBuffer,final  String targetFile)
	{
		try
		{
			// 创建文件对象
			File fileText = new File(fileDirPath+File.separator + targetFile);
			System.out.println("保存的本地文件目录是["+fileText.getPath()+"]");
			if (!fileText.exists()) {
				new File(fileText.getParent()).mkdirs();
			} else {
				log.debug("替换文件:" + fileText.getAbsolutePath());
			}
			// 向文件写入对象写入信息
			FileWriter fileWriter = new FileWriter(fileText);
			// 写文件
			fileWriter.write(strBuffer);
			// 关闭
			fileWriter.close();
		}
		catch (IOException e)
		{
			//
			e.printStackTrace();
		}
	}
	public static boolean createDir(String destDirName) {
		File dir = new File(destDirName);
		if (dir.exists()) {
			System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");
			return false;
		}
		if (!destDirName.endsWith(File.separator)) {
			destDirName = destDirName + File.separator;
		}
		//创建目录
		if (dir.mkdirs()) {
			System.out.println("创建目录" + destDirName + "成功！");
			return true;
		} else {
			System.out.println("创建目录" + destDirName + "失败！");
			return false;
		}
	}
	/**
	 * 利用freemarker生成实体类代码
	 * @param map参数
	 */
	public void freemarkModel(Map<String, String> map){
		
	}
}
