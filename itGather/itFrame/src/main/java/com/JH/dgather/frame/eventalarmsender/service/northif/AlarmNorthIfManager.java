package com.JH.dgather.frame.eventalarmsender.service.northif;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.JH.dgather.PropertiesHandle;
import com.JH.dgather.frame.eventmanager.bean.UnwarnEventBean;
import com.JH.dgather.frame.eventmanager.bean.WarnEventBean;
import com.JH.dgather.frame.globaldata.GloableDataArea;

public class AlarmNorthIfManager {
	private static Logger logger = Logger.getLogger(AlarmNorthIfManager.class);
	private static final AlarmNorthIfManager instance = new AlarmNorthIfManager();

	public static AlarmNorthIfManager getInstance() {
		return instance;
	}

	private List<AlarmNorthInterface> niList = new ArrayList<AlarmNorthInterface>();

	public AlarmNorthIfManager() {

	}

	public void start() {
		for (AlarmNorthInterface alarmNorthInterface : niList) {
			try {
				alarmNorthInterface.start();
			} catch (Exception e) {
				logger.error("", e);
			}
		}
	}

	public void stop() {
		for (AlarmNorthInterface alarmNorthInterface : niList) {
			try {
				alarmNorthInterface.stop();
			} catch (Exception e) {
				logger.error("", e);
			}
		}
	}

	public void init() {
		String fileName = PropertiesHandle
				.getResuouceInfo("AlarmNorthInterface");
		if (fileName == null || fileName.trim().equals("")) {
			fileName = "./AlarmNorthInterface.xml";
		}
		logger.info("[AlarmNorthInterface]告警AlarmNorthInterface文件加载路径是："
				+ fileName);
		File file = new File(fileName);
		if (!file.exists() || file.isDirectory()) {
			logger.info("[AlarmNorthInterface]告警AlarmNorthInterface文件"
					+ fileName + "不存在或者没有配置，系统不会通过AlarmNorthInterface发送告警。");
			return;
		}
		SAXReader reader = new SAXReader();
		Document document;
		try {
			document = reader.read(file);
		} catch (DocumentException e) {
			logger.error("[AlarmNorthInterface]告警AlarmNorthInterface文件加载失败：", e);
			return;
		}
		Element rootElm = document.getRootElement();
		List<Element> elementList = rootElm.elements("AlarmNorthInterface");
		for (Element element : elementList) {
			String classname = element.attributeValue("classname").trim();
			if (element.attributeValue("enable").trim()
					.equalsIgnoreCase("true")) {
				try {
					Class onwClass = Class.forName(classname);
					AlarmNorthInterface niInterface = (AlarmNorthInterface) onwClass
							.newInstance();
					niInterface.init();
					this.niList.add(niInterface);
				} catch (Exception e) {
					logger.error("", e);
				}
			}
		}
	}

	public List<AlarmNorthIfResponse> sendWarnEvent(
			Collection<WarnEventBean> warnBeanLs) {
		List<AlarmNorthIfResponse> resList = new ArrayList<AlarmNorthIfResponse>(
				niList.size());
		for (AlarmNorthInterface alarmNorthInterface : niList) {
			try {
				resList.add(alarmNorthInterface.sendWarnEvent(warnBeanLs));
			} catch (Exception e) {
				logger.error("", e);
			}
		}
		return resList;

	}

	public List<AlarmNorthIfResponse> sendUnWarnEvent(
			Collection<UnwarnEventBean> unwarnBeanls) {
		List<AlarmNorthIfResponse> resList = new ArrayList<AlarmNorthIfResponse>(
				niList.size());
		for (AlarmNorthInterface alarmNorthInterface : niList) {
			try {
				resList.add(alarmNorthInterface.sendUnWarnEvent(unwarnBeanls));
			} catch (Exception e) {
				logger.error("", e);
			}
		}
		return resList;
	}

	public static void main(String[] args) {
	}
}
