package com.JH.dgather;


public class GloabVar {
//	private static Logger logger = Logger.getLogger(GloabVar.class);
	public static String appDir;
	static{
		appDir = initAppDir();
	//	logger.info("系统路径"+appDir );
		System.out.println(appDir);
	}
	private static String initAppDir() {
		//取当前路径的上级路径
		String currentDir = GloabVar.class.getProtectionDomain().getCodeSource().getLocation().getFile();
		if(currentDir.indexOf("/lib")!=-1){
			currentDir = currentDir.split("/lib")[0];
		}else if(currentDir.indexOf("/target")!=-1){
			currentDir = currentDir.split("/target")[0];
		}else{
			currentDir = currentDir.substring(0,currentDir.lastIndexOf("/"));
		}
		return currentDir;
	}
	public static void main(String[] s){
		System.out.println(appDir);
	}
}
