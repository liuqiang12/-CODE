package com.idc.listener;

import constant.isp.ISPContant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import utils.BasicEncodeUtils;
import utils.DevContext;
import utils.typeHelper.FileHelper;
import utils.typeHelper.ISPFTPUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PageParser {
	private static VelocityEngine ve;
	  private static final String CONTENT_ENCODING = "UTF-8";
	  private static final Log log = LogFactory.getLog(PageParser.class);

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

	  public static  String WriterPage(VelocityContext context, String templateName, String fileDirPath, String targetFile)
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
			createISPEventService(context,resultStr,fileDirPath+File.separator+"xml", "encoding"+targetFile);
			return resultStr;
		} catch (Exception e) {
	    	log.error(e);
	    }
	    return null;
	  }
	/*        生成相应的加密压缩的xml文件           */
	public static String createISPEventService(VelocityContext context,String xmlContent,final String fileDirPath, final String targetFile) {
		FileLoad fileLoad = new FileLoad();
		fileLoad.setIdcId(ISPContant.许可证号.value());
		fileLoad.setCompressionFormat(ISPContant.压缩格式_Zip压缩格式.value());
		fileLoad.setHashAlgorithm(ISPContant.哈希算法_MD5.value());
		fileLoad.setEncryptAlgorithm(ISPContant.对称加密算法_AES加密.value());
		fileLoad.setCommandVersion(ISPContant.版本.value());
		//添加xmlstr：dataUpload
		byte[] zipByte = BasicEncodeUtils.getInstance().zipstr(xmlContent);//打包zip
		//外加偏移量问题
		try {
			String aesEncode = BasicEncodeUtils.getInstance().AESEncode(zipByte,ISPContant.AES密钥.value(),ISPContant.AES偏移量.value(),ISPContant.AES密码器.value());
			fileLoad.setDataUpload(aesEncode);
			String baseEncoder = BasicEncodeUtils.getInstance().baseEncoder(zipByte);
			fileLoad.setDataHash(baseEncoder);
			/* 生成相应的xml模板。不用JABX方式了  */
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
				context.put("fileLoad",fileLoad);
				StringWriter writerStr=new StringWriter();
				velocityEngine.mergeTemplate("template/EncodingTemplateName.xml", "gbk", context, writerStr);

				//velocityEngine.mergeTemplate("hello.vm", "gbk", context, writer);
				final String resultStr = writerStr.toString();
				final String fileName = targetFile;
				/**
				 * 开启线程调用-------------start
				 */
				ExecutorService executorByCall = Executors.newSingleThreadExecutor();
				Future<String> future = executorByCall.submit(new Callable<String>() {   //接受一上callable实例
					public String call() throws Exception {
						try {
							ISPFTPUtils ftpUtils = ISPFTPUtils.getInstance();
							//年月日
							ftpUtils.upload(DevContext.FTP_ISP_REMOTE_PATH, fileDirPath,targetFile);
						} catch (Exception e) {
							System.out.println("操作情况："+ e);
							e.printStackTrace();
						}
						return "OK FTP调用完成";
					}
				});
				System.out.println("任务的执行结果："+future.get());
				/**
				 * 开启线程调用-------------end
				 */

				textToFile(fileDirPath,resultStr,targetFile);
				return resultStr;
			} catch (Exception e) {
				log.error(e);
			}
		}catch (Exception e){
			e.printStackTrace();
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
	 */
	public void freemarkModel(Map<String, String> map){
		
	}
}
