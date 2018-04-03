package com.JH.dgather.frame.eventalarmsender.webservice;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.JH.dgather.PropertiesHandle;
import com.JH.dgather.frame.eventalarmsender.webservice.config.KPIObject;
import com.JH.dgather.frame.eventalarmsender.webservice.config.WebService;
import com.JH.dgather.frame.eventmanager.bean.UnwarnEventBean;
import com.JH.dgather.frame.eventmanager.bean.WarnEventBean;

public class WebServiceSender {
	private static Logger logger = Logger.getLogger(WebServiceSender.class);
	private boolean enable;
	private List<WebService> webServices = new ArrayList<WebService>();
	private static WebServiceSender instance;

	public WebServiceSender() {
		super();
		init();
	}

	public synchronized static WebServiceSender getInstance() {
		if (instance == null) {
			instance = new WebServiceSender();
		}
		return instance;
	}

	private void init() {
		String fileName = PropertiesHandle
				.getResuouceInfo("AlarmWebServiceFile");
		if (fileName == null || fileName.trim().equals("")) {
			fileName = "./alarm.webservice.xml";
		}
		logger.info("[WEBSERVICE]告警webservice文件加载路径是：" + fileName);
		File file = new File(fileName);
		if (!file.exists() || file.isDirectory()) {
			logger.info("[WEBSERVICE]告警webservice文件" + fileName
					+ "不存在或者没有配置，系统不会通过webservice发送告警。");
			return;
		}
		SAXReader reader = new SAXReader();
		Document document;
		try {
			document = reader.read(file);
		} catch (DocumentException e) {
			logger.error("[WEBSERVICE]告警webservice文件加载失败：", e);
			return;
		}
		Element rootElm = document.getRootElement();
		Element enableElement = rootElm.element("enable");
		enable = "true".equalsIgnoreCase(enableElement.getTextTrim());
		List<Element> webserviceElements = rootElm.elements("webservice");
		for (Element webserviceElement : webserviceElements) {
			try {
				WebService service = loadWebServiceElement(webserviceElement);
				webServices.add(service);
			} catch (Exception e) {
				logger.error("[WEBSERVICE]告警webservice文件加载错误webservice节点：", e);
			}
		}
		logger.info("[WEBSERVICE]共加载webservice个数:" + webServices.size()
				+ ",enable = " + enable);
	}

	private WebService loadWebServiceElement(Element webserviceElement)
			throws Exception {
		WebService service = new WebService();
		service.name = webserviceElement.element("name").getTextTrim();
		service.wsdlLocation = webserviceElement.element("wsdlLocation")
				.getTextTrim();
		service.namespaceURI = webserviceElement.element("namespaceURI")
				.getTextTrim();
		service.serviceName = webserviceElement.element("serviceName")
				.getTextTrim();
		service.className = webserviceElement.element("className")
				.getTextTrim();
		Element alarmConfigElement = webserviceElement.element("alarmConfig");
		Element sendAlarmKPIElement = alarmConfigElement
				.element("sendAlarmKPI");
		List<Element> kpiElements = sendAlarmKPIElement.elements("kpi");
		for (Element kpiElement : kpiElements) {
			KPIObject kpiObject = new KPIObject();
			kpiObject.key = kpiElement.attributeValue("key").trim();
			kpiObject.name = kpiElement.attributeValue("name").trim();
			kpiObject.wsType = kpiElement.attributeValue("wsType").trim();
			kpiObject.wsLevel = kpiElement.attributeValue("wsLevel").trim();
			Element kpiFilterElement = kpiElement.element("filter");
			if (kpiFilterElement != null) {
				String minLevelStr = kpiFilterElement
						.attributeValue("minLevel");
				if (minLevelStr != null && !minLevelStr.trim().equals("")) {
					kpiObject.kpiFilter.minLevel = Integer.parseInt(minLevelStr
							.trim());
				}
			}
			if (service.add(kpiObject) == null) {
				logger.error("[WEBSERVICE]没有找到webservice中定义的kpi key！请检查配置。"
						+ kpiObject);
			}
		}

		Class onwClass = Class.forName(service.className);
		service.wsInterface = (WebServiceInterface) onwClass.newInstance();
		service.wsInterface.setWebService(service);
		logger.info("[WEBSERVICE]创建接口实例成功："
				+ service.wsInterface.getClass().getName());
		return service;
	}

	public List<WebServiceResponse> sendWarnEvent(
			Collection<WarnEventBean> warnBeanLs) {
		if (!enable || webServices.size() == 0)
			return new ArrayList<WebServiceResponse>(0);
		List<WebServiceResponse> responses = new ArrayList<WebServiceResponse>(
				webServices.size());
		for (Iterator iterator = webServices.iterator(); iterator.hasNext();) {
			WebService webService = (WebService) iterator.next();
			WebServiceResponse response = new WebServiceResponse(webService);
			logger.info("[WEBSERVICE]开始调用告警接口:" + webService.name);
			try {
				int sentResult = webService.wsInterface
						.sendWarnEvent(warnBeanLs);
				logger.info("[WEBSERVICE]调用告警接口:" + webService.name + "返回接口："
						+ sentResult);
				response.setSentResult(sentResult);
			} catch (IOException e1) {
				response.setSentResult(WebServiceConsts.SENT_STATUS_FAIL);
				response.setErrorMsg("发送告警失败！访问webservice网络接口失败.");
				logger.error("[WEBSERVICE]" + webService.name + "发送告警失败！", e1);
			} catch (Exception e) {
				response.setSentResult(WebServiceConsts.SENT_STATUS_FAIL);
				response.setErrorMsg("发送告警失败！" + e);
				logger.error("[WEBSERVICE]" + webService.name + "发送告警失败！", e);
			}
			if (response.getSentResult() != WebServiceConsts.SENT_STATUS_NOACTION)
				responses.add(response);
		}
		return responses;
	}

	public List<WebServiceResponse> sendUnWarnEvent(
			Collection<UnwarnEventBean> unwarnBeanls) {
		if (!enable || webServices.size() == 0)
			return new ArrayList<WebServiceResponse>(0);
		List<WebServiceResponse> responses = new ArrayList<WebServiceResponse>(
				webServices.size());
		for (Iterator iterator = webServices.iterator(); iterator.hasNext();) {
			WebService webService = (WebService) iterator.next();
			WebServiceResponse response = new WebServiceResponse(webService);
			logger.info("[WEBSERVICE]开始调用恢复告警接口:" + webService.name);
			try {
				int sentResult = webService.wsInterface
						.sendUnWarnEvent(unwarnBeanls);
				logger.info("[WEBSERVICE]调用恢复告警接口:" + webService.name + "返回接口："
						+ sentResult);
				response.setSentResult(sentResult);
			} catch (Exception e) {
				response.setSentResult(WebServiceConsts.SENT_STATUS_FAIL);
				response.setErrorMsg(e + "");
				logger.error("[WEBSERVICE]" + webService.name + "发送恢复告警失败！", e);
			}
			if (response.getSentResult() != WebServiceConsts.SENT_STATUS_NOACTION)
				responses.add(response);
		}
		return responses;
	}

}
