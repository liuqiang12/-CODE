package utils.plugins.xml.xpath;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import utils.typeHelper.FileHelper;
import utils.typeHelper.StringHelper;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * 利用xpath 获取xml中的数据
 * 单例  安全
 * @author Administrator
 */
public class XpathHelper {
	private static XpathHelper instance;
	private XpathHelper(){};
	
	private String propertyPath;
	/**
	 * 实例化
	 * @return
	 */
	public static synchronized XpathHelper getInstance(){
		if(instance == null){
			instance = new XpathHelper();
		}
		return instance;
	}
	/**
	 * 加载文件
	 * @param propertyPath
	 */
	public void load(String propertyPath){
		this.propertyPath = propertyPath;
	}
	/**
	 *  获取文件流
	 * @throws FileNotFoundException 
	 * 
	 */
	public InputSource getInputStream() throws FileNotFoundException{
		
		File file = new File(FileHelper.getInstance().getRootClassPath()+propertyPath);
		
		if(!file.exists()){
			new Exception("文件不存在");
		}
		
		FileInputStream fileStream = new FileInputStream(file);
		
		InputSource inputSource = new InputSource(fileStream) ;
		
		return inputSource;
	}
	/**
	 * 获取xpath对象
	 * @return
	 */
	public XPath getXpath(){
		
		XPathFactory factory = XPathFactory.newInstance(); 
		
		XPath xPath = factory.newXPath();  
		
		return xPath;
	}
	/**
	 * 获取documentbuilder
	 * @return
	 * @throws ParserConfigurationException
	 */
	public DocumentBuilder getDocumentBuilder() throws ParserConfigurationException{
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		factory.setIgnoringElementContentWhitespace(true);
		
		DocumentBuilder db = factory.newDocumentBuilder();
		
		return db;
	}
	/**
	 * 获取id为sqlId的数据
	 * @param path
	 * @param sqlId
	 * @return
	 * @throws Exception
	 */
	public String getXMLSqlByPath(String path, String sqlId) throws Exception {

		DocumentBuilder db = getDocumentBuilder();
		
		Element startElement = (Element) selectSingleNode("/codeMapper/sql[@id='" + sqlId + "']",
				db.parse(new File(FileHelper.getInstance().getRootClassPath() + path)));
		
		return startElement.getTextContent();
	}
	/**
	 * 通过表达式节点数据
	 * @param express
	 * @param source
	 * @return
	 * @throws Exception
	 */
	private Node selectSingleNode(String express, Object source) throws Exception {
		
		XPath xpath = getXpath();
		
		Node result = (Node) xpath.evaluate(express, source, XPathConstants.NODE);
		
		return result;
	}
	public static void main(String[] args) throws Exception{
		
		XpathHelper helper = XpathHelper.getInstance();
		
		String textContent = helper.getXMLSqlByPath("utils/strategy/code/utils/code.xml", "getTables");
		
		System.out.println(StringHelper.getInstance().trim(textContent));
	}
}
