package utils.plugins.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.dom4j.io.SAXReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.sun.org.apache.xalan.internal.xsltc.dom.LoadDocument;

import utils.plugins.excel.model.CellInfo;
import utils.plugins.excel.model.RowInfo;
import utils.plugins.excel.model.SheetInfo;
import utils.plugins.excel.model.WorkbookInfo;
import utils.typeHelper.FileHelper;
import utils.typeHelper.StringHelper;

/**
 * 利用xpath 获取xml中的数据
 * 单例  安全
 * @author Administrator
 */
public class ExportHelper {
	private static ExportHelper instance;
	private static Document doc;//静态数据
	private static org.dom4j.Document dom4jDoc;//静态数据
	
	private ExportHelper(){};
	
	private String propertyPath;
	/**
	 * 实例化
	 * @return
	 */
	public static synchronized ExportHelper getInstance(String path){
		if(instance == null){
			instance = new ExportHelper();
			//然后加载doc
			try {
				loadDoc(path);
				loadDom4jDoc(path);
			} catch (Exception e) {
				e.printStackTrace();
			}
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
	public static DocumentBuilder getDocumentBuilder() throws ParserConfigurationException{
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		factory.setIgnoringElementContentWhitespace(true);
		
		DocumentBuilder db = factory.newDocumentBuilder();
		
		return db;
	}
	/**
	 * 加载文件到静态缓存中
	 * @param path
	 * @throws Exception
	 */
	public static void loadDoc(String path) throws Exception{
		DocumentBuilder db = getDocumentBuilder();
		if(doc == null){
			InputStream in = ExportHelper.class.getResourceAsStream("/"+path);
			doc = db.parse(in);
		}
	}
	/**
	 * 加载文件到静态缓存中
	 * @param path
	 * @throws Exception
	 */
	public static void loadDom4jDoc(String path) throws Exception{
		SAXReader reader = new SAXReader();
		if (dom4jDoc == null) {
			InputStream in = ExportHelper.class.getResourceAsStream("/"+path);
			dom4jDoc = reader.read(in);
		}
	}
	/**
	 * @param path
	 * @param exportId
	 * @return
	 * @throws Exception
	 */
	public WorkbookInfo getXMLByPath(String path, String exportId) throws Exception {

		DocumentBuilder db = getDocumentBuilder();
		if(doc == null){
			InputStream in = this.getClass().getResourceAsStream("/"+path);
			doc = db.parse(in);
		}
		Element startElement = (Element) selectSingleNode("/export/Workbook[@id='" + exportId + "']",doc);
		//查找到了后，就是查询所有的行信息
		WorkbookInfo workbookInfo = new WorkbookInfo();
		//获取工作簿的相关信息情况
		String name = startElement.getAttribute("name");
		String id = startElement.getAttribute("id");
		workbookInfo.setName(name);
		workbookInfo.setId(id);
		//然后获取sheet的基本信息
		String sheetId = "0";
		NodeList nodeList = selectSingleNodeSet("./sheet", startElement);
		//获取所有的sheet信息
		List<SheetInfo> sheets =  getSheetList(nodeList);
		workbookInfo.setSheetList(sheets);
		
		
		return workbookInfo;
	}
	//全篇中获取相应的id对应的所有的值
	public org.dom4j.Node getCellNodeById(String path, String exportId) throws Exception {

		SAXReader reader = new SAXReader();
		if (dom4jDoc == null) {
			if(path == null){
				throw new Exception("path is null..............");
			}else{
				InputStream in = ExportHelper.class.getResourceAsStream("/"+path);
				dom4jDoc = reader.read(in);
			}
		}
		//获取指定的id
		org.dom4j.Node selectSingleNode = dom4jDoc.selectSingleNode("//cell[@id='"+exportId+"']");
		return selectSingleNode;
	}
	
	
	
	public List<SheetInfo> getSheetList(NodeList nodeList){
		List<SheetInfo> list = new ArrayList<SheetInfo>();
		for(int i = 0; i < nodeList.getLength(); i++){
			Node node = nodeList.item(i);
			NamedNodeMap namedNodeMap = node.getAttributes();
			SheetInfo sheetInfo = new SheetInfo();
			//名称name/id
			if(namedNodeMap.getNamedItem("name").getNodeValue() != null)sheetInfo.setName(namedNodeMap.getNamedItem("name").getNodeValue());
			if(namedNodeMap.getNamedItem("id").getNodeValue() != null)sheetInfo.setId(namedNodeMap.getNamedItem("id").getNodeValue());
			//每一个sheet都有多个rows
			//获取多个rows
			try {
				NodeList rowsList = selectSingleNodeSet("./rows/row", node);
				List<RowInfo> rowsInfos = getRowList(rowsList);
				sheetInfo.setRowList(rowsInfos);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(sheetInfo);
		}
		return list;
	}
	
	public List<RowInfo> getRowList(NodeList nodeList){
		List<RowInfo> list = new ArrayList<RowInfo>();
		for(int i = 0; i < nodeList.getLength(); i++){
			Node node = nodeList.item(i);
			NamedNodeMap namedNodeMap = node.getAttributes();
			RowInfo rowInfo = new RowInfo();
			//名称name/id
			if(namedNodeMap.getNamedItem("desc") != null)rowInfo.setDesc(namedNodeMap.getNamedItem("desc").getNodeValue());
			if(namedNodeMap.getNamedItem("id") != null)rowInfo.setId(namedNodeMap.getNamedItem("id").getNodeValue());
			//row中的所有的单元格
			try {
				NodeList cellList = selectSingleNodeSet("./cell", node);
				List<CellInfo> cellInfos = getCellList(cellList);
				rowInfo.setCellInfos(cellInfos);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(rowInfo);
		}
		return list;
	}
	public List<CellInfo> getCellList(NodeList nodeList){
		List<CellInfo> list = new ArrayList<CellInfo>();
		for(int i = 0; i < nodeList.getLength(); i++){
			Node node = nodeList.item(i);
			NamedNodeMap namedNodeMap = node.getAttributes();
			CellInfo cellInfo = new CellInfo();
			//名称name/id
			if(namedNodeMap.getNamedItem("id") != null)cellInfo.setId(namedNodeMap.getNamedItem("id").getNodeValue());
			if(namedNodeMap.getNamedItem("fieldDispName") != null)cellInfo.setFieldDispName(namedNodeMap.getNamedItem("fieldDispName").getNodeValue());
			if(namedNodeMap.getNamedItem("fieldName") != null)cellInfo.setFieldName(namedNodeMap.getNamedItem("fieldName").getNodeValue());
			if(namedNodeMap.getNamedItem("cols") != null)cellInfo.setCols(namedNodeMap.getNamedItem("cols").getNodeValue());
			if(namedNodeMap.getNamedItem("cellRangeAddress_rows") != null)cellInfo.setCellRangeAddress_rows(namedNodeMap.getNamedItem("cellRangeAddress_rows").getNodeValue());
			if(namedNodeMap.getNamedItem("cellIndex_from") != null)cellInfo.setCellIndex_from(namedNodeMap.getNamedItem("cellIndex_from").getNodeValue());
			if(namedNodeMap.getNamedItem("foreach") != null)cellInfo.setForeach(namedNodeMap.getNamedItem("foreach").getNodeValue());
			if(namedNodeMap.getNamedItem("level") != null)cellInfo.setLevel(namedNodeMap.getNamedItem("level").getNodeValue());
			//row中的所有的单元格
			list.add(cellInfo);
		}
		return list;
	}
	//获取单元格
	
	
	/**
	 * 通过表达式节点数据
	 * @param express
	 * @param source
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private NodeList selectSingleNodeSet(String express, Object source) throws Exception {
		
		XPath xpath = getXpath();
		
		NodeList result = (NodeList) xpath.evaluate(express, source, XPathConstants.NODESET);
		
		return result;
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
		
		ExportHelper helper = ExportHelper.getInstance("utils/plugins/excel/export.xml");
		//获取了【全省年度能耗环比分析】的所有配置信息然后就开始拼装excel的复杂表头
		
		WorkbookInfo workbookInfo = helper.getXMLByPath("utils/plugins/excel/export.xml", "qsndnhhbfx");
		
		System.out.println(workbookInfo);
		
	}
}
