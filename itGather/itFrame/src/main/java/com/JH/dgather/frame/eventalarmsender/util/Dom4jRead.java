package com.JH.dgather.frame.eventalarmsender.util;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.JH.dgather.frame.eventalarmsender.db.bean.ServiceBean;
import com.JH.dgather.frame.eventalarmsender.exception.EventAlarmSenderException;

/**
 * @author gamesdoa
 * @email gamesdoa@gmail.com
 * @date 2012-11-15
 */

public class Dom4jRead {
	private Logger logger = Logger.getLogger(Dom4jRead.class);

	public HashMap<Integer, ServiceBean> iterateWholeXML(String filename) {
		HashMap<Integer, ServiceBean> serviceHm = new HashMap<Integer, ServiceBean>();
		SAXReader saxReader = new SAXReader();
		try {
			ServiceBean serviceBean;
			Document document = saxReader.read(new File(filename));
			Element root = document.getRootElement();

			@SuppressWarnings("unchecked")
			List<Element> childList = root.elements();
			Iterator<Element> it = childList.iterator();
			while(it.hasNext()){
				Element service = it.next();
				int key = Integer.parseInt(service.elementText("anstype"));
				String port = service.elementText("port");
				String auth = service.elementText("auth");
				String ssl = service.elementText("ssl");
				String socketFactory = service.elementText("socketFactory");
				serviceBean = new ServiceBean(port,auth,ssl,socketFactory);
				serviceBean.setANSType(key);
				serviceBean.setServiceAPIConn(service.elementText("mailHost"));
				serviceBean.setServicAPIIde(service.elementText("from"));
				serviceBean.setServiceLoginName(service.elementText("username"));
				serviceBean.setServcieLoginPwd(service.elementText("password"));
				serviceBean.setPersonal(service.elementText("personal"));
				serviceBean.setTextSubject("true".equalsIgnoreCase(service.elementText("textsubject")));

				if (serviceHm.containsKey(key)) {
					logger.error("数据存在相同类型的服务信息。ANSType=" + key);
					throw new EventAlarmSenderException("数据存在相同类型的服务信息。ANSType=" + key);
				} else {
					serviceHm.put(key, serviceBean);
				}	
			}
		} catch (Exception e) {
			logger.error("读取发送邮件的服务器信息异常：", e);
		}
		return serviceHm;
	}

	public static void main(String[] args) {
		Dom4jRead dr = new Dom4jRead();
		HashMap<Integer, ServiceBean> serviceHm = dr.iterateWholeXML("D:\\zone\\workspace4eclipse\\10_ICSGatherBusiness\\mailService.xml");
		System.out.println(serviceHm.size());
	}
}
