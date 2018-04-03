package com.JH.dgather.frame.common.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * XMl文件处理类
 * 
 * @author yangDS
 */
@SuppressWarnings("unchecked")
public class XMLFileHandler {
	private Logger logger = Logger.getLogger(XMLFileHandler.class);
	
	private SAXReader saxReader = new SAXReader();
	private Document document;
	private XMLWriter writer;
	private String fileName;
	private URL file;
	
	public XMLFileHandler() {
		
	}
	
	public XMLFileHandler(URL file) {
		this.file = file;
		this.fileName = file.getFile();
		
		try {
			this.document = saxReader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
			logger.error(e);
		}
	}
	
	public XMLFileHandler(String filepath) {
		File file  = new File(filepath);
		
		try {
			this.document = saxReader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
			logger.error(e);
		}
	}
	
	/**
	 * 得到xmlFileName指定的文件中nodeName指定的元素且该元素的attribute属性的值为attrValue
	 * 
	 * @param attribute
	 * @param value
	 */
	public Element getElementBy(String nodeName, String attribute, String attrValue) {
		String xpath = getXpathByENameAndAttributeVa(nodeName, attribute, attrValue);
		logger.info("xpath: " + xpath);
		return (Element) document.selectSingleNode(xpath);
	}
	
	/**
	 * 得到e的text值
	 * 
	 * @param e
	 * @return
	 */
	public String getElementValue(Element e) {
		return e.getTextTrim();
	}
	
	/**
	 * 根据条件得到xpath字符串
	 * 
	 * @param eName
	 * @param eAttr
	 * @param eAttVal
	 * @return
	 */
	private String getXpathByENameAndAttributeVa(String eName, String eAttr, String eAttVal) {
		
		if (eName == null || eName.length() == 0)
			return "//";
		
		if (eAttr == null || eAttr.length() == 0 || eAttVal == null || eAttVal.length() == 0) {
			return "//" + eName;
		}
		else {
			return "//" + eName + "[@" + eAttr + "='" + eAttVal + "']";
		}
	}
	
	/**
	 * 根据xpath得到element
	 * 
	 * @param xmlFile
	 * @param xpath
	 * @return
	 */
	public Element getElementByXpath(String xpath) {
		
		if (xpath == null)
			return null;
		
		return (Element) document.selectSingleNode(xpath);
	}
	
	/**
	 * 得到文档实例
	 * 
	 * @param xmlFileName
	 * @return
	 */
	private Document getDocInstance() {
		return document;
	}
	
	/**
	 * 创建新的Document对象
	 * 
	 * @return
	 */
	public static Document createDocument() {
		Document document = DocumentHelper.createDocument();
		return document;
	}
	
	/**
	 * 创建新的document对象，而且根元素为root
	 * 
	 * @param root
	 * @return
	 */
	public static Document createDocument(String root, Element rootE) {
		Document document = DocumentHelper.createDocument();
		rootE = document.addElement(root);
		return document;
	}
	
	/**
	 * @param root
	 * @param rootE
	 * @return
	 */
	public static Document createDocument(String root) {
		Document document = DocumentHelper.createDocument();
		document.addElement(root);
		return document;
	}
	
	/**
	 * @param xmlFileName
	 * @param pElementName
	 * @param pElementAttribute
	 * @param pElementAttributeVlaue
	 * @param ElementName
	 * @param attrAndValue
	 * @return
	 */
	
	public boolean addElementOn(String pElementName, String pElementAttribute, String pElementAttributeVlaue, String ElementName,
			HashMap<String, String> attrAndValue) {
		
		if (pElementName == null || pElementName.length() == 0 || ElementName == null || ElementName.length() == 0)
			return false;
		String xpath = getXpathByENameAndAttributeVa(pElementName, pElementAttribute, pElementAttributeVlaue);
		
		Element el = null;
		
		List elements = document.selectNodes(xpath);
		
		if (elements == null)
			return false;
		el = (Element) elements.get(0);
		
		Element newE = el.addElement(ElementName);
		
		if (attrAndValue == null)
			return true;
		
		String att = null;
		String attV = null;
		for (Entry<String, String> entry : attrAndValue.entrySet()) {
			
			att = entry.getKey();
			attV = entry.getValue();
			
			if (att != null && attV != null) {
				
				newE.addAttribute(att, attV);
				
			}
			
		}
		
		return true;
		
	}
	
	/**
	 * 得到xmlFileName的writer
	 * 
	 * @return
	 */
	public XMLWriter getWriter(String xmlFileName, String encode) {
		
		XMLWriter writer = getWriter();
		if (xmlFileName == null || xmlFileName.length() == 0)
			return null;
		if (writer != null && xmlFileName.equals(getFileName())) {
			return writer;
		}
		else {
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding(encode);
			try {
				writer = new XMLWriter(new FileWriter(xmlFileName), format);
				setWriter(writer);
			} catch (IOException e) {
				e.printStackTrace();
				if (writer != null) {
					try {
						writer.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
		
		// 指定XML编码
		return writer;
		
	}
	
	/**
	 * 将数据写到xml文件
	 * 
	 * @param xmlFileName
	 * @param encode
	 */
	public void flushData(String xmlFileName, String encode, Document doc) {
		
		XMLWriter write = getWriter(xmlFileName, encode);
		try {
			write.write(doc);
			write.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				write.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 加载parentEl元素下的所有名为elementName的元素
	 * 
	 * @param parentEl
	 * @param elementName
	 * @return
	 */
	public ArrayList<Element> loadElementsUnderParentE(Element parentEl, String elementName) {
		
		if (elementName == null || elementName.length() == 0) {
			return (ArrayList<Element>) parentEl.elements();
		}
		else {
			return (ArrayList<Element>) parentEl.elements(elementName);
		}
		
	}
	
	/**
	 * 加载 inputStream
	 * 下元素名为elementName的所有元素（且每个元素属性attribute为attributeValue的所有元素）
	 * 
	 * @param xmlFileName
	 * @param elementName
	 * @param attribute
	 * @param attributeValue
	 * @return
	 */
	public List loadElementsByAttribute(String elementName, String attribute, String attributeValue) {
		
		String xpath = null;
		if (elementName == null || elementName.length() == 0) {
			return null;
		}
		if (attribute == null || attribute.length() == 0 || attributeValue == null || attributeValue.length() == 0) {
			xpath = "//" + elementName;
		}
		else {
			xpath = "//" + elementName + "[@" + attribute + "='" + attributeValue + "']";
		}
		
		return document.selectNodes(xpath);
		
	}
	
	/**
	 * 加载 xmlFileName
	 * 下元素名为elementName的所有元素（且每个元素属性attribute为attributeValue的所有元素）
	 * 
	 * @param xmlFileName
	 * @param elementName
	 * @param attribute
	 * @param attributeValue
	 * @return
	 */
	public List loadElementsByAttribute(String xmlFileName, String elementName, String attribute, String attributeValue) {
		
		String xpath = null;
		if (elementName == null || elementName.length() == 0) {
			return null;
		}
		if (attribute == null || attribute.length() == 0 || attributeValue == null || attributeValue.length() == 0) {
			xpath = "//" + elementName;
		}
		else {
			xpath = "//" + elementName + "[@" + attribute + "='" + attributeValue + "']";
		}
		
		return document.selectNodes(xpath);
		
	}
	
	/**
	 * 得到elment的属性及值，以元素名称为key,值为value
	 * 
	 * @param e
	 * @return
	 */
	public HashMap<String, String> getElementAttributesAndValue(Element e) {
		
		if (e == null)
			return null;
		Iterator it = e.attributeIterator();
		Attribute att = null;
		String name = null;
		String value = null;
		HashMap<String, String> hm = new HashMap();
		while (it.hasNext()) {
			att = (Attribute) it.next();
			name = att.getName();
			
			value = att.getValue().trim();
			
			if (name != null && name.length() > 0) {
				hm.put(name, value);
			}
		}
		
		return hm;
	}
	
	/**
	 * 得到子元素
	 * 
	 * @param e
	 */
	public List getSubElements(Element e) {
		
		return e.elements();
		
	}
	
	private SAXReader getSaxReader() {
		
		return saxReader;
	}
	
	private void setSaxReader(SAXReader saxReader) {
		
		this.saxReader = saxReader;
	}
	
	private void setDocument(Document document) {
		
		this.document = document;
	}
	
	private String getFileName() {
		
		return fileName;
	}
	
	private void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public XMLWriter getWriter() {
		
		return writer;
	}
	
	public void setWriter(XMLWriter writer) {
		
		this.writer = writer;
	}
}