package com.JH.itBusi.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * 文件操作工具类
 * 
 * @author zhengbin
 * 
 */
public class FileTools {
	private static Logger logger = Logger.getLogger(FileTools.class);

	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 * 
	 * @param filePath
	 *            文件路径包含文件名
	 * @return 文件内容，以行形式保存（采用\r\n换行分隔）
	 */
	public static String readFileByLines(String filePath) {
		if (null == filePath) {
			logger.error("filePath为null，无法读取指定的文件。");
			return null;
		}
		File file = new File(filePath);
		BufferedReader reader = null;
		StringBuffer fileText = null;
		try {
			reader = new BufferedReader(new FileReader(file));

			// 一次读入一行，直到读入null为文件结束
			String tempString = null;
			fileText = new StringBuffer();
			while (null != (tempString = reader.readLine())) {
				fileText.append(tempString);
				fileText.append("\r\n");
			}
		} catch (FileNotFoundException e) {
			logger.error("无法读取文件信息，请确认文件是否存在，filePath = " + filePath, e);
			return null;
		} catch (IOException e) {
			logger.error("读取文件信息异常异常，filePath = " + filePath, e);
			return null;
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error("关闭BufferedReader异常", e);
				}
			}
		}
		return fileText.toString();
	}
}
