package com.JH.dgather.frame.xmlparse;

import java.io.File;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public abstract class XmlParse {
	private Logger logger = Logger.getLogger(XmlParse.class);

	private SAXReader reader = new SAXReader();

	/**
	 * 根据配置文件路径内存化xml信息
	 * 
	 * @param fileDir
	 *            文件路径
	 * @return 内存化结果：true成功false失败
	 */
	protected boolean cacheXMLLoad(String fileDir) {
		Document document = getDocByFileDir(fileDir);
		// 如果无法获取document对象则解析失败
		if (null == document) {
			return false;
		}
		return parseXml(document);
	}

	/**
	 * 根据文件地址路径获取文件document对象
	 * 
	 * @param fileDir
	 *            文件路径
	 * @return document对象
	 */
	private Document getDocByFileDir(String fileDir) {
		// 获取文件地址如果为null或""表示地址有误
		if (null == fileDir || "".equals(fileDir)) {
			logger.error("获取需要加载解析的xml文件地址为空，请确认配置是否正确。");
			return null;
		} else {
			File xmlFile = new File(fileDir);
			// 如果文件地址文件存在则解析，不存在异常记录
			if (xmlFile.exists()) {
				Document document = null;
				try {
					document = reader.read(xmlFile);
				} catch (DocumentException e) {
					logger.error("解析地址文件异常，文件路径：" + fileDir, e);
					return null;
				}
				return document;
			} else {
				logger.error("不存在指定的文件地址！文件路径: " + fileDir);
				return null;
			}
		}
	}

	/**
	 * 初始化配置解析
	 * 
	 * @param propertiesName
	 *            配置文件中的key
	 * @return 配置信息内存化结果，true成功，false失败
	 */
	public abstract boolean initXmlParse(String propertiesName);

	/**
	 * 解析xml文件
	 * 
	 * @param document
	 * @return 解析结果:true解析成功，false解析失败
	 */
	protected abstract boolean parseXml(Document document);
}
