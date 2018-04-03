package utils.strategy.code.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import utils.typeHelper.FileHelper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.Properties;

public class CommonPageParser {
	private static VelocityEngine ve;
	  private static final String CONTENT_ENCODING = "UTF-8";
	  private static final Log log = LogFactory.getLog(utils.strategy.code.utils.CommonPageParser.class);

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

	  public static void WriterPage(VelocityContext context, String templateName, String fileDirPath, String targetFile)
	  {
	    try
	    {
	      File file = new File(fileDirPath + targetFile);
	      if (!file.exists()) {
	        new File(file.getParent()).mkdirs();
	      }
	      else if (isReplace) {
	    	  log.debug("替换文件:" + file.getAbsolutePath());
	      }

	      Template template = ve.getTemplate(templateName, "UTF-8");
	      FileOutputStream fos = new FileOutputStream(file);
	      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
	      template.merge(context, writer);
	      writer.flush();
	      writer.close();
	      fos.close();
	      log.debug("生成文件：" + file.getAbsolutePath());
	    } catch (Exception e) {
	    	log.error(e);
	    }
	  }
	/**
	 * 利用freemarker生成实体类代码
	 * @param map参数
	 */
	public void freemarkModel(Map<String, String> map){
		
	}
}
