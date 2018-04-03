package com.JH.dgather.frame.util;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.JH.dgather.frame.gathercontrol.controller.ExecutiveController;
import com.JH.dgather.frame.gathercontrol.task.UserTask;
import com.JH.dgather.frame.gathercontrol.task.bean.TaskGcBean;

/**
 * @author gamesdoa
 * @email gamesdoa@gmail.com
 * @date 2014-12-5
 */

public class LoadClass {
	static Logger logger = Logger.getLogger(LoadClass.class);
	
	public static Class LoadClass(String classUrl) {
		try {
			return Class.forName(classUrl);
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}
	
	public static Object newClass(String classUrl) {
		try {
			Class c = Class.forName(classUrl);
			return c.newInstance();

		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}
	
	public static Object newClass(Class c) {
		try {
			return c.newInstance();
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}
	
	public static ExecutiveController newClass(String classUrl,UserTask task) {
		try {
			Class c = LoadClass.LoadClass(classUrl);
			Constructor constructor = c.getConstructor(UserTask.class);

			return (ExecutiveController) constructor.newInstance(task);
		} catch (Exception e) {
			logger.error("", e);
			handleException(e);
			
			
		}
		return null;
	}
	
	
	
	 private static void handleException(Exception e)
	    {
	        String msg = null;
	        if (e instanceof InvocationTargetException)
	        {
	            Throwable targetEx = ((InvocationTargetException) e)
	                    .getTargetException();
	            if (targetEx != null)
	            {
	                msg = targetEx.getMessage();
	            }
	        } else
	        {
	            msg = e.getMessage();
	        }
	      /*  MessageDialog.openError(Activator.getDefault().getWorkbench()
	                .getDisplay().getActiveShell(), "error", msg);*/
	        logger.error("errorMsg:"+msg);
	        e.printStackTrace();
	    }
	
	
	
	
	 /**
     * 获取指定xml文档的Document对象,xml文件必须在classpath中可以找到
     *
     * @param xmlFilePath xml文件路径
     * @return Document对象
     */ 
    public static Document parse2Document(String xmlFilePath){
        SAXReader reader = new SAXReader();
        Document doc = null;
        try {
            doc = reader.read(new File(xmlFilePath));
        } catch (DocumentException e) {
        	logger.error("", e);
        }
        return doc;
    }
    //<Integer,Map<String,String>>
    public static Map<Integer,TaskGcBean> parseXmlData(String xmlFilePath){
        //将xml解析为Document对象
        Document doc = LoadClass.parse2Document(xmlFilePath);
        //获取文档的根元素
        Element root  = doc.getRootElement();
        //定义保存xml数据的缓冲字符串
        //StringBuffer sb = new StringBuffer();
        //定义保存属性、值的map
        Map<Integer,TaskGcBean> map = new HashMap<Integer,TaskGcBean>();
        TaskGcBean bean;
        for(Iterator task=root.elementIterator();task.hasNext();){
            Element e_task = (Element)task.next();
            bean = new TaskGcBean();
            int gc = 0;
            for(Iterator a_task=e_task.attributeIterator();a_task.hasNext();){
                Attribute attribute = (Attribute)a_task.next();
                logger.info(attribute.getName()+";"+ attribute.getValue());
                if(attribute.getName().toLowerCase().equals("gc")){
                	gc = Integer.parseInt(attribute.getValue());
                	bean.setGc(gc);
                }else if(attribute.getName().toLowerCase().equals("rule")){
                	bean.setRule(attribute.getValue());
                }else if(attribute.getName().toLowerCase().equals("controller")){
                	bean.setController(attribute.getValue());
                }
            }
            map.put(gc, bean);
        }
        return map;
        
    }
    public static void main(String[] args) {
    	Map<Integer, TaskGcBean> map = LoadClass.parseXmlData("D:\\zone\\workspace4eclipse\\V4_ICSGatherV4\\taskgc.xml");
    	logger.info(map);
    }
}
