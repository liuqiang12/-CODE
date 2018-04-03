package com.JH.dgather.frame.common.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SFTPClientUpfile {
	Logger logger = Logger.getLogger(SFTPClientUpfile.class);
	private Channel channel;
	private Session session;
	private ChannelSftp sftp;
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
	 * @throws Exception 
	 */
	public SFTPClientUpfile(String url,int port,String username, String password) throws Exception{
		
//		try {
				JSch jsch=new JSch();
				this.session=jsch.getSession(username, url, port);
				this.session.setPassword(password);
				//设置第一次登陆的时候提示，可选值：(ask | yes | no)
				this.session.setConfig("StrictHostKeyChecking", "no");
				//设置登陆超时时间   
				this.session.connect(30000);
				//创建sftp通信通道
				this.channel = (Channel) session.openChannel("sftp");
				this.sftp = (ChannelSftp) channel;
				this.sftp.connect();
				
//			}catch(Exception e){
//				e.printStackTrace();
//				throw new Exception("sftp登陆失败");
//				
//			}finally{
//				if(sftp!=null) sftp.disconnect();
//				if(session!=null) session.disconnect();
//			}
	
		
	}
	
	
	public void disconnect(){
		try {
			if(sftp!=null)
				sftp.disconnect();
			
		if (sftp.isConnected()) {
			try {
				sftp.disconnect();
				channel.disconnect();
			} catch (Exception ioe) {
			}
		}
		} catch (Exception e) {
			
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
			sftp.cd(path);
			//以下代码实现从本地上传一个文件到服务器，如果要实现下载，对换以下流就可以了
			OutputStream outstream = sftp.put(filename);
			InputStream instream = input;
			
			byte b[] = new byte[1024];
			int n;
		    while ((n = instream.read(b)) != -1) {
		    	outstream.write(b, 0, n);
		    }
		    outstream.flush();
		    outstream.close();
		    instream.close();
		    success = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sftp.disconnect();
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
			sftp.cd(remotePath);
		    sftp.get(remotePath+"/"+fileName,localPath);
		    success = true;
		} catch (Exception e) {
			logger.error("sFTP下载文件{path="+remotePath+";filename="+fileName+"}异常", e);
		} 
		return success;
	}
	//下载多个文件
	public boolean downFiles(
			String remotePath,// FTP服务器上的相对路径
			String fileName,// 要下载的文件名正则表达式
			String localPath// 下载后保存到本地的路径
	) throws Exception {
		boolean success = false;
		if((this.sftp==null)||(!this.sftp.isConnected())){
			System.out.println("未连接。");
			return false;}
		try {
			//进入服务器指定的文件夹
			sftp.cd(remotePath);
			//列出服务器指定的文件列表
			Vector v = sftp.ls(fileName);
			for(int i=0;i<v.size();i++){
				String s =remotePath+((com.jcraft.jsch.ChannelSftp.LsEntry)(v.get(i))).getFilename();
				System.out.println(s);//test
				sftp.get(s,localPath);
			}
			success = true;
		} catch (Exception e) {
			logger.error("FTP下载文件{path="+remotePath+";filename="+fileName+"}异常", e);
			e.printStackTrace();
			throw new Exception("FTP下载文件{path="+remotePath+";filename="+fileName+"}异常");
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
	   return this.sftp.isConnected();
   }

}
