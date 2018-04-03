package com.comer.util;

import org.apache.commons.net.ftp.*;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.regex.Pattern;

/**
 * @author gamesdoa
 * @email gamesdoa@gmail.com
 * @date 2013-5-13
 */

public class FTPClientUpfile {
	Logger logger = Logger.getLogger(FTPClientUpfile.class);
	
	private FTPClient ftp;
	/**
	 * Description: 初始FTP工具类
	 * 
	 * @Version1.0
	 * @param url
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 */
	public FTPClientUpfile(String url, int port, String username, String password){
		ftp = new FTPClient();
		int reply;
		try {
			ftp.connect(url, port);
			ftp.setControlEncoding("GBK");
			FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
			conf.setServerLanguageCode("zh");

			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			//FTPClient在缺省情况下是按ASCII形式进行传输的，如果你是传输的BINARY二进 制文件（如zip），
			//那么上传完后的文件就会被破坏，但是传输ASCII文件（如txt）是没有问题的。
			//所以如果你是传输的BINARY二进制文件的话，就需要在建立连接、登陆后，接下来设置文件类型
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
			}
		} catch (Exception e) {
			logger.error("初始化FTP工具类异常：", e);
		}// 连接FTP服务器

	}


	public void disconnect(){
		try {
			if(ftp!=null)
				ftp.logout();
		} catch (IOException e) {

		}
		if (ftp.isConnected()) {
			try {
				ftp.disconnect();
			} catch (IOException ioe) {
			}
		}
	}
	/**
	 * Description: 向FTP服务器上传文件
	 *
	 * @Version1.0
	 * @param url
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param path
	 *            FTP服务器保存目录
	 * @param filename
	 *            上传到FTP服务器上的文件名
	 * @param input
	 *            输入流
	 * @return 成功返回true，否则返回false
	 */
	public boolean uploadFile(
			String path, // FTP服务器保存目录
			String filename, // 上传到FTP服务器上的文件名
			InputStream input // 输入流
	) {
		boolean success = false;
		try {

			//ftp.changeWorkingDirectory(path);
			boolean chgDir=ftp.changeWorkingDirectory(new String(path.getBytes("GBK"),"ISO-8859-1"));
			if(!chgDir){
				if(path.endsWith("/")||path.endsWith("\\"))
					path = path.substring(0, path.length()-1);
				path.replaceAll("\\\\", "/");
				String[] pathDirectory = path.split("/");
				for (String string : pathDirectory) {
					ftp.makeDirectory(new String(string.getBytes("GBK"),"ISO-8859-1"));
					ftp.changeWorkingDirectory(new String(string.getBytes("GBK"),"ISO-8859-1"));
				}
			}
			ftp.storeFile(new String(filename.getBytes("GBK"), "ISO-8859-1"), input);
			ftp.changeWorkingDirectory("/");
			success = true;
		} catch (Exception e) {
			logger.error("FTP上传文件{path="+path+";filename="+filename+"}异常：", e);
		} finally {
			try {
				input.close();
			} catch (IOException e) {
			}
		}
		return success;
	}

	/**
	 * 将本地文件上传到FTP服务器上
	 *
	 */
	public void testUpLoadFromDisk() {
		try {
			FileInputStream in = new FileInputStream(new File("D:/贝尔20130513162955.xml"));
			boolean flag = uploadFile("test/中午/test", "贝尔20130513162955.xml", in);
			System.out.println(flag);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}



	/**
	 * Description: 从FTP服务器下载文件
	 *
	 * @Version1.0
	 * @param remotePath
	 *            FTP服务器上的相对路径
	 * @param fileName
	 *            要下载的文件名
	 * @param localPath
	 *            下载后保存到本地的路径
	 * @return
	 */
	public boolean downFile(
			String remotePath,// FTP服务器上的相对路径
			String fileName,// 要下载的文件名
			String localPath// 下载后保存到本地的路径
	) {
		boolean success = false;
		try {
			boolean chgDir=ftp.changeWorkingDirectory(new String(remotePath.getBytes("GBK"),"ISO-8859-1"));
			if(!chgDir){
				String[] pathDirectory = remotePath.split(File.separator);
				for (String string : pathDirectory) {
					//ftp.makeDirectory(new String(string.getBytes("GBK"),"ISO-8859-1"));
					ftp.changeWorkingDirectory(new String(string.getBytes("GBK"),"ISO-8859-1"));
				}
			}
			ftp.enterLocalPassiveMode();//每次数据连接之前，ftp client告诉ftp server开通一个端口来传输数据
			FTPFile[] fs = ftp.listFiles();
			for (FTPFile ff : fs) {
				String ftpFileName = new String(ff.getName().getBytes("ISO-8859-1"), "GBK");
				System.out.println(ftpFileName);
				if (ftpFileName.equals(fileName)) {
					File localFile = new File(localPath + File.separator + ftpFileName);
					OutputStream output = new FileOutputStream(localFile);
					ftp.retrieveFile(ff.getName(), output);

					output.flush();
					output.close();
				}
			}

			success = true;
		} catch (IOException e) {
			logger.error("FTP下载文件{path="+remotePath+";filename="+fileName+"}异常", e);
		}
		return success;
	}
	//下载多个文件
	public boolean downFiles(
			String remotePath,// FTP服务器上的相对路径
			String fileName,// 要下载的文件名正则表达式
			String localPath// 下载后保存到本地的路径
	) {
		/*过滤器，不支持
		final String fname = fileName;
		FilenameFilter fileNameFilter = new FilenameFilter() {
            private Pattern pattern= Pattern.compile(fname);
            @Override
            public boolean accept(File dir, String name) {
                return pattern.matcher(name).matches();
            }
		};*/
		boolean success = false;
		Pattern pattern= Pattern.compile(fileName);

		try {
			boolean chgDir=ftp.changeWorkingDirectory(new String(remotePath.getBytes("GBK"),"ISO-8859-1"));
			if(!chgDir){
				String[] pathDirectory = remotePath.split(File.separator);
				for (String string : pathDirectory) {
					ftp.changeWorkingDirectory(new String(string.getBytes("GBK"),"ISO-8859-1"));
				}
			}
			ftp.enterLocalPassiveMode();//每次数据连接之前，ftp client告诉ftp server开通一个端口来传输数据
			FTPFile[] fs = ftp.listFiles();
			for (FTPFile ff : fs) {
				String ftpFileName = new String(ff.getName().getBytes("ISO-8859-1"), "GBK");
				if(pattern.matcher(ftpFileName).matches()){
					File localFile = new File(localPath + File.separator + ftpFileName);
					OutputStream output = new FileOutputStream(localFile);
					ftp.retrieveFile(ff.getName(), output);
					output.flush();
					output.close();
				}
			}

			success = true;
		} catch (IOException e) {
			logger.error("FTP下载文件{path="+remotePath+";filename="+fileName+"}异常", e);
			e.printStackTrace();
		} 
		return success;
	}


	/**
	 * 将FTP服务器上文件下载到本地
	 * 
	 */
	public void testDownFile() {
		try {
			boolean flag = downFile("test", "EventCountTop.class", "D:");
			System.out.println(flag);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
   public boolean isConnected(){
	   return this.ftp.isConnected();
   }
	
	public static void main(String[] args) {
		try{//用FTPSClient仍然不能连接SFTP，必须用jsch
		FTPSClient ftps = new FTPSClient();
		ftps.connect("192.168.28.128", 22);
		ftps.login("uftp", "123");// 登录
		ftps.disconnect();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		//	ftps.disconnect();
		}

		FTPClientUpfile ftp = new FTPClientUpfile("127.0.0.1", 21, "a", "a");
		try {
			ftp.uploadFile("/", "报表sql1.rar",
					new FileInputStream("d:/报表sql.rar"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
