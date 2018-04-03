package system.data.utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import system.data.utils.excel.MergeInfo.HeadInfo;
import system.data.utils.excel.error.ImportErrorResult;
import utils.plugins.excel.Guid;

/**
 * @ClassName: ExcelUtil
 * @Description: Excel导入导出工具类
 * 
 */
public class ExcelUtils {
	private static final Logger logger = Logger.getLogger(ExcelUtils.class);
	/** 总行数 */
	private int totalRows = 0;
	/** 总列数 */
	private int totalCells = 0;
	/** 错误信息 */
	private String errorInfo;
	/** 是否为开始 **/
	private boolean isStart = false;
	/** 开始读取行数 */
	private int startRow = 0;
	// 1 核心机房 2 县级机房 3营业厅 4管理用房
	private int type = 1;
	/** 是否分场所类型 **/
	private Boolean isGroupMode=true;
	/** 场所分类型标识 **/
	private String groupPrefix="imExcel_";
	/** 当前合并信息 **/
	private MergeInfo curMergeInfo;
	private String notGroupKey="rows";
	public ExcelUtils(String groupPrefix) {
		super();
		this.groupPrefix = groupPrefix;
	}

	public ExcelUtils() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	
	public Boolean getIsGroupMode() {
		return isGroupMode;
	}

	public void setIsGroupMode(Boolean isGroupMode) {
		this.isGroupMode = isGroupMode;
		if(!isGroupMode){//若非场所分类模式，则设置默认返回信息
			this.curMergeInfo=new MergeInfo();
			curMergeInfo.setMergeKey(this.notGroupKey);
		}
	}

	/**
	 * @Title: createWorkbook
	 * @Description: 判断excel文件后缀名，生成不同的workbook
	 * @param @param is
	 * @param @param excelFileName
	 * @param @return
	 * @param @throws IOException
	 * @return Workbook
	 * @throws
	 */
	public Workbook createWorkbook(InputStream is, String excelFileName)
			throws IOException {
		if (excelFileName.endsWith(".xls")) {
			return new HSSFWorkbook(is);
		} else if (excelFileName.endsWith(".xlsx")) {
			return new XSSFWorkbook(is);
		}
		return null;
	}

	/**
	 * @Title: getSheet
	 * @Description: 根据sheet索引号获取对应的sheet
	 * @param @param workbook
	 * @param @param sheetIndex
	 * @param @return
	 * @return Sheet
	 * @throws
	 */
	public Sheet getSheet(Workbook workbook, int sheetIndex) {
		return workbook.getSheetAt(0);
	}

	/**
	 * @Title: importDataFromExcel
	 * @Description: 将sheet中的数据保存到list中，
	 *               1、调用此方法时，vo的属性个数必须和excel文件每行数据的列数相同且一一对应，vo的所有属性都为String
	 *               2、在action调用此方法时，需声明 private File excelFile;上传的文件 private
	 *               String excelFileName;原始文件的文件名 3、页面的file控件name需对应File的文件名
	 * @param @param vo javaBean
	 * @param @param is 输入流
	 * @param @param excelFileName
	 * @param @return
	 * @return List<Object>
	 * @throws
	 */
	public Map<String, List<Map<String,ColumnModel>>> importDataFromExcel(InputStream is,
			String excelFileName) {
		Map<String, List<Map<String,ColumnModel>>> result= new HashMap<String, List<Map<String,ColumnModel>>>();
		try {
		    String filename=excelFileName.substring(0,excelFileName.indexOf(".")).replace("-", "");
		    System.out.println(filename);
			// 创建工作簿
			Workbook wb = this.createWorkbook(is, excelFileName);
			/** 得到第一个shell */
 			Sheet sheet = wb.getSheetAt(0);
			/** 得到Excel的行数 */
			this.totalRows = sheet.getLastRowNum();//sheet.getPhysicalNumberOfRows();
			/** 得到Excel的列数 */
			if (this.totalRows >= 1 && sheet.getRow(0) != null) {
				this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
			}

			
			short minColIx = 0;//row.getFirstCellNum();
			short maxColIx = 0;
			
			/** 循环Excel的行 */
			for (int r = 0; r <= this.totalRows; r++) {
				Row row = sheet.getRow(r);
				if (row == null ) {
					logMsg(r , "出现空行。");
					continue;
				}
				
				Map<String,ColumnModel> rowPros=null;//临时存放row的【属性】
				//迭代row中的所有cell
				/*Iterator<Cell> cells=row.cellIterator();
				while (cells.hasNext()) {*/
//				Cell cell = cells.next();
				 maxColIx = maxColIx<row.getLastCellNum()?row.getLastCellNum():maxColIx;
				 for(short colIx=minColIx; colIx<maxColIx; colIx++) {
				   Cell cell = row.getCell(colIx);
				   int cellColumnIndex=colIx;//cell.getColumnIndex();
				   MergeInfo mergedInfo = isMergedRegion(sheet, r, cellColumnIndex);//判断Cell是否为合并列
					if(null!=mergedInfo||cell!=null){//过滤空Cell
						String comment=getComment(cell);
						if(null!=comment){//存在备注的Cell
							Boolean isNormalTitle=true;
							if(this.isGroupMode){//是场所分类
								if(comment.indexOf(this.groupPrefix)>-1){//场所类型表头
									isNormalTitle=false;//不是普通表头
									String mergeKey=comment.replaceAll(this.groupPrefix, "");
									if(null!=mergedInfo){
										String mergeName = getMergedRegionValue(sheet,row.getRowNum(), cellColumnIndex);
										if(null==this.curMergeInfo||(null!=this.curMergeInfo&&
												!this.curMergeInfo.getMergeKey().equals(mergeKey))){//若当前场所类型为null 或 mergeKey与之前的合并类型不一致时，进入下一个类型
											mergedInfo.setMergeKey(mergeKey);
											mergedInfo.setMergeName(mergeName);
											this.curMergeInfo=mergedInfo;
										}
									}
								}
							}
							if(isNormalTitle){//普通表头
								if(null!=this.curMergeInfo){//当前分组表头不为空
									if(!this.curMergeInfo.getPropertyColumnMap().containsKey(comment)){//若分组【属性-列索引】中不存在 该属性,则新增
										String title="";
										if(mergedInfo!=null){//若为合并列
											title=getMergedRegionValue(sheet, r, cellColumnIndex);//以合并方式获取Cell值
										}else{//若当前Cell不为合并列，则赋值本列信息
											mergedInfo=new MergeInfo(cellColumnIndex,cellColumnIndex,r,r);//以普通方式获取Cell值
											title=getCellValue(cell);
										}
										this.curMergeInfo.putPropertyColumnMap(comment,title, cellColumnIndex,mergedInfo);
									}
								}else{
									logMsg(r,"存放分组表头时【分组表头信息为空】");
								}
							}
							
						}else{//普通Cell
							if(null!=this.curMergeInfo&&this.curMergeInfo.getPropertyColumnMap().size()>0){//当前合并表头不为空,并且【属性-列索引】数量大于0
								HeadInfo head=this.curMergeInfo.getHeadByColumnIndex(r,cellColumnIndex);
								if(null!=head){//判断改Cell列索引 在【属性-列索引】中是否存在，存在则赋值到rowPros中
									String property=head.getProperty();
									String value=null;
									if(mergedInfo!=null){//判断Cell是否为合并列
										value=getMergedRegionValue(sheet, r, cellColumnIndex);
									}else{
										value=getCellValue(cell);
									}
									
//									if(null!=value&&!"".equals(value)){//判断获取的值是否为空
										if(null==rowPros){
											rowPros=new HashMap<String, ColumnModel>();
										}
										rowPros.put(property, new ColumnModel(head.getProperty(), head.getTitle(), head.getSort(), value) );
//									}
								}
							}else{
								logMsg(r,"获取普通列信息时【表头信息为空】");
							}
						}
						
					}else{//空cell
						if(null!=this.curMergeInfo&&this.curMergeInfo.getPropertyColumnMap().size()>0){//当前合并表头不为空,并且【属性-列索引】数量大于0
							HeadInfo head=this.curMergeInfo.getHeadByColumnIndex(r,cellColumnIndex);
							if(null!=head){//判断改Cell列索引 在【属性-列索引】中是否存在，存在则赋值到rowPros中
								String property=head.getProperty();
								if(null==rowPros){
									rowPros=new HashMap<String, ColumnModel>();
								}
								rowPros.put(property, new ColumnModel(head.getProperty(), head.getTitle(), head.getSort(), "") );
							}
						}else{
							logMsg(r,"获取NULL列信息时【表头信息为空】");
						}
						
					}
				}
				
				if(null!=this.curMergeInfo){//当前表头不为空
					if(!result.containsKey(this.curMergeInfo.getMergeKey())){//判断是否存在外层数据KEY
						result.put(this.curMergeInfo.getMergeKey(), new ArrayList<Map<String,ColumnModel>>());
					}
					if(null!=rowPros){//若当前rowPros不为空
						if(rowPros.size()>(this.curMergeInfo.getColspan()*0.5)){//若空行数小于总数的70%，则将数据加入集合中
							result.get(this.curMergeInfo.getMergeKey()).add(rowPros);
						}
						rowPros=null;
					}
				}
			}
			
//			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		} finally {
			try {
				is.close();// 关闭流
			} catch (Exception e2) {
				logger.error(e2);
			}
		}
		return result;
	}


	/**
	 * 获取合并单元格的值
	 * 
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	public String getMergedRegionValue(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();

		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress ca = sheet.getMergedRegion(i);
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			int firstRow = ca.getFirstRow();
			int lastRow = ca.getLastRow();

			if (row >= firstRow && row <= lastRow) {

				if (column >= firstColumn && column <= lastColumn) {
					Row fRow = sheet.getRow(firstRow);
					Cell fCell = fRow.getCell(firstColumn);
					return getCellValue(fCell);
				}
			}
		}

		return null;
	}

	/**
	 * 获取Cell的备注
	 * @param cell
	 * @return
	 */
	private String getComment(Cell cell){
		String result=null;
		if(null!=cell){
			Comment cm=cell.getCellComment();
			if(null!=cm){
				//去掉作者信息
				String richStr=cell.getCellComment().getString().toString().replaceAll("\n", "");
				richStr=richStr.indexOf(":")>-1?richStr.substring(richStr.indexOf(":")+1):richStr;
				result=richStr;
			}
		}
		return result;
	}
	
	
	private void logMsg(Integer row,String msg){
		System.out.println("在第" + (row+1) + "行 ,"+msg);
	}
	
	/**
	 * 判断合并了行
	 * 
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	private boolean isMergedRow(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			if (row == firstRow && row == lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断合并列
	 * 
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	private boolean isMergedCol(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			if (row >= firstRow && row <= lastRow) {
				if (column == firstColumn && column == lastColumn) {
					return true;

				}
			}
		}
		return false;
	}

	/**
	 * 判断指定的单元格是否是合并单元格
	 * 
	 * @param sheet
	 * @param row
	 *            行下标
	 * @param column
	 *            列下标
	 * @return
	 */
	private MergeInfo isMergedRegion(Sheet sheet, int row, int column) {
		MergeInfo result=null;
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			if (row >= firstRow && row <= lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					result=new MergeInfo(firstColumn, lastColumn, firstRow, lastRow);
				}
			}
		}
		return result;
	}

	/**
	 * 判断sheet页中是否含有合并单元格
	 * 
	 * @param sheet
	 * @return
	 */
	private boolean hasMerged(Sheet sheet) {
		return sheet.getNumMergedRegions() > 0 ? true : false;
	}

	/**
	 * 合并单元格
	 * 
	 * @param sheet
	 * @param firstRow
	 *            开始行
	 * @param lastRow
	 *            结束行
	 * @param firstCol
	 *            开始列
	 * @param lastCol
	 *            结束列
	 */
	private void mergeRegion(Sheet sheet, int firstRow, int lastRow,
			int firstCol, int lastCol) {
		sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol,
				lastCol));
	}

	/**
	 * 判断单元格的值类型
	 * 
	 * @param cell
	 * @return
	 */
	private String getCellValue(Cell cell) {
		String cellValue = "";
		DataFormatter formatter = new DataFormatter();
		if (cell != null) {
			// 判断单元格数据的类型，不同类型调用不同的方法
			switch (cell.getCellType()) {
			// 数值类型
			case Cell.CELL_TYPE_NUMERIC:
				// 进一步判断 ，单元格格式是日期格式
				if (DateUtil.isCellDateFormatted(cell)) {
					cellValue = formatter.formatCellValue(cell);
				} else {
					// 数值
					Double value = cell.getNumericCellValue();
					Long intValue = value.longValue();
					cellValue = value - intValue == 0 ? String
							.valueOf(intValue) : String.valueOf(value);
				}
				break;
			case Cell.CELL_TYPE_STRING:
				cellValue = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				cellValue = String.valueOf(cell.getBooleanCellValue());
				break;
			// 判断单元格是公式格式，需要做一种特殊处理来得到相应的值
			case Cell.CELL_TYPE_FORMULA: {
				try {
					cellValue = String.valueOf(cell.getNumericCellValue());
				} catch (IllegalStateException e) {
					cellValue = String.valueOf(cell.getRichStringCellValue());
				}

			}
				break;
			case Cell.CELL_TYPE_BLANK:
				cellValue = "";
				break;
			case Cell.CELL_TYPE_ERROR:
				cellValue = "";
				break;
			default:
				cellValue = cell.toString().trim();
				break;
			}
		}
		return cellValue.trim();
	}

	/**
	 * @Title: isHasValues
	 * @Description: 判断一个对象所有属性是否有值，如果一个属性有值(分空)，则返回true
	 * @param @param object
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public boolean isHasValues(Object object) {
		Field[] fields = object.getClass().getDeclaredFields();
		boolean flag = false;
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getName();
			String methodName = "get" + fieldName.substring(0, 1).toUpperCase()
					+ fieldName.substring(1);
			Method getMethod;
			try {
				getMethod = object.getClass().getMethod(methodName);
				Object obj = getMethod.invoke(object);
				if (null != obj && "".equals(obj)) {
					flag = true;
					break;
				}
			} catch (Exception e) {
				logger.error(e);
			}

		}
		return flag;

	}

	public <T> void exportDataToExcel(List<T> list, String[] headers,
			String title, OutputStream os) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽15个字节
		sheet.setDefaultColumnWidth(15);
		// 生成一个样式
		HSSFCellStyle style = this.getCellStyle(workbook);
		// 生成一个字体
		HSSFFont font = this.getFont(workbook);
		// 把字体应用到当前样式
		style.setFont(font);

		// 生成表格标题
		HSSFRow row = sheet.createRow(0);
		row.setHeight((short) 300);
		HSSFCell cell = null;

		for (int i = 0; i < headers.length; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}

		// 将数据放入sheet中
		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow(i + 1);
			T t = list.get(i);
			// 利用反射，根据JavaBean属性的先后顺序，动态调用get方法得到属性的值
			Field[] fields = t.getClass().getFields();
			try {
				for (int j = 0; j < fields.length; j++) {
					cell = row.createCell(j);
					Field field = fields[j];
					String fieldName = field.getName();
					String methodName = "get"
							+ fieldName.substring(0, 1).toUpperCase()
							+ fieldName.substring(1);
					Method getMethod = t.getClass().getMethod(methodName,
							new Class[] {});
					Object value = getMethod.invoke(t, new Object[] {});

					if (null == value)
						value = "";
					cell.setCellValue(value.toString());

				}
			} catch (Exception e) {
				logger.error(e);
			}
		}

		try {
			workbook.write(os);
		} catch (Exception e) {
			logger.error(e);
		} finally {
			try {
				os.flush();
				os.close();
			} catch (IOException e) {
				logger.error(e);
			}
		}

	}

	/**
	 * @Title: getCellStyle
	 * @Description: 获取单元格格式
	 * @param @param workbook
	 * @param @return
	 * @return HSSFCellStyle
	 * @throws
	 */
	public HSSFCellStyle getCellStyle(HSSFWorkbook workbook) {
		HSSFCellStyle style = workbook.createCellStyle();
		/*style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);*/
		
		 style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		return style;
	}

	/**
	 * @Title: getFont
	 * @Description: 生成字体样式
	 * @param @param workbook
	 * @param @return
	 * @return HSSFFont
	 * @throws
	 */
	public HSSFFont getFont(HSSFWorkbook workbook) {
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.WHITE.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		return font;
	}

	public boolean isIE(HttpServletRequest request) {
		return request.getHeader("USER-AGENT").toLowerCase().indexOf("msie") > 0 ? true
				: false;
	}


	public static void main(String[] args) {
		ExcelUtils util=new ExcelUtils();
//		util.setIsGroupMode(false);
		 InputStream is = null;
		try {
            /** 调用本类提供的根据流读取的方法 */
            File file = new File("E:/recieved/云南能耗/导入模板/保山-.xlsx");
            is = new FileInputStream(file);
            String fileName = file.getName();  
            String filename=fileName.substring(0,fileName.indexOf("."));
           if(filename.contains("-")){
        	   System.out.println("ss");
           }else{
        	   System.out.println("文件名不正确");
           }
            util.importDataFromExcel(is,fileName);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}

	public void outPutErrorResult(List<ImportErrorResult> result, String title ,HttpServletResponse response) {
		
		
		OutputStream os=null;
		try {
			os=response.getOutputStream();
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition","attachment;filename="+new String(title.getBytes("GBK"), "ISO-8859-1")+Guid.Instance()+".xls");
			
			
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 生成一个样式
			HSSFCellStyle style = this.getCellStyle(workbook);
			// 生成一个字体
			HSSFFont font = this.getFont(workbook);
			// 把字体应用到当前样式
			style.setFont(font);
			
			if(result!=null&&result.size()>0){
				
				for (int i = 0; i < result.size(); i++) {
					//sheet组对象
					ImportErrorResult reusltSheet=result.get(i);
					// 生成一个表格
					HSSFSheet sheet = workbook.createSheet(reusltSheet.getText());
					// 设置表格默认列宽15个字节
					sheet.setDefaultColumnWidth(15);
					
					List<List<ColumnModel>> rows=reusltSheet.getRows();
					
					for (int j = -1; j < rows.size(); j++) {
						List<ColumnModel> rdata=null;
						if(j==-1){//生成标题行
							rdata=rows.get(0);
						}else{
							rdata=rows.get(j);
						}
						// 生成表格标题
						HSSFRow row = sheet.createRow(j+1);
						row.setHeight((short) 300);
						
						for (int k = 0; k < rdata.size(); k++) {
							String headerTitle=rdata.get(k).getTitle();
							String value=rdata.get(k).getValue();
							HSSFCell cell = null;
							cell = row.createCell(k);
							if(j==-1){//标题
								cell.setCellStyle(style);
								HSSFRichTextString text = new HSSFRichTextString(headerTitle);
								cell.setCellValue(text);
							}else{//内容
								cell.setCellValue(value);
							}
						}
						
					}
					
				}
				
			}
			
			workbook.write(os);
		} catch (Exception e) {
			logger.error(e);
		} finally {
			try {
				os.flush();
				os.close();
			} catch (IOException e) {
				logger.error(e);
			}
		}
		
	}
}
