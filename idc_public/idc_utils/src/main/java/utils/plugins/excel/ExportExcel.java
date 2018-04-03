package utils.plugins.excel;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import utils.plugins.excel.model.CellInfo;
import utils.plugins.excel.model.ExcelColumn;
import utils.plugins.excel.model.RowInfo;
import utils.plugins.excel.model.SheetInfo;
import utils.plugins.excel.model.WorkbookInfo;
import utils.typeHelper.StringHelper;

import com.idc.model.BaseExclinfo;



/**
 * --获取数据库中的所有的表名
	select * from dba_tables t where t.TABLESPACE_NAME = 'ODES';
	--得到所有的表的字段
	select * from user_tab_columns t where t.TABLE_NAME = 'BAS_EXCLINFO';
	--得到注释
	select * from user_col_comments t where t.table_name = 'BAS_EXCLINFO';
	--得到所有的属性
	select * from all_col_comments t where t.TABLE_NAME = 'BAS_EXCLINFO';
 * 利用开源组件POI3.0.2动态导出EXCEL文档 转载时请保留以下信息，注明出处！
 * 
 * @author leno
 * @version v1.0
 * @param <T>
 *            应用泛型，代表任意一个符合javabean风格的类
 *            注意这里为了简单起见，boolean型的属性xxx的get器方式为getXxx(),而不是isXxx()
 *            byte[]表jpg格式的图片数据
 */
public class ExportExcel<T> {
	public static int DEFAULT_COLOUMN_WIDTH = 17;
//    public void exportExcel(String title,Collection<Map<String, Object>> dataset,List<BaseExclinfo> exclInfos, OutputStream out) {
//        try {
//			exportExcel(title, exclInfos, dataset, out, "yyyy-MM-dd");
//		} catch (NoSuchFieldException e) {
//			e.printStackTrace();
//		} catch (NoSuchMethodException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			e.printStackTrace();
//		}
//    }
    
    public void exportExcel(String title,Class model,Collection<Object> dataset, OutputStream out) {
        try {
			exportExcel(title, model, dataset, out, "yyyy-MM-dd");
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
    }

//    public void exportExcel(Collection<T> dataset,List<BaseExclinfo> exclInfos,
//            OutputStream out) {
//        exportExcel("测试POI导出EXCEL文档", exclInfos, dataset, out, "yyyy-MM-dd");
//    }
    
    
    
//    PUBLIC VOID EXPORTEXCEL(STRING[] HEADERS, COLLECTION<T> DATASET,LIST<BASEXCLINFO> EXCLINFOS,
//            OUTPUTSTREAM OUT, STRING PATTERN) {
//        EXPORTEXCEL("测试POI导出EXCEL文档", EXCLINFOS, DATASET, OUT, PATTERN);
//    }

    /**
     * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
     * 
     * @param title
     *            表格标题名
     * @param exclInfos
     *            表格属性列名数组
     * @param dataset
     *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param out
     *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern
     *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
     */
    @SuppressWarnings({ "unchecked", "deprecation" })
    public void exportExcel(String title, List<BaseExclinfo> exclInfos,
            Collection<Map<String, Object>> dataset, OutputStream out, String pattern) throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 15);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 生成并设置另一个样式
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style2.setWrapText(true);
        HSSFDataFormat format=workbook.createDataFormat();
        style2.setDataFormat(format.getFormat("@"));
        
        // 生成另一个字体
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style2.setFont(font2);

        // 声明一个画图的顶级管理器
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        
        //通过查询进行相关的处理
        int[] arrColWidth = new int[exclInfos.size()];
        for (short i = 0; i < exclInfos.size(); i++) {
        	HSSFCell cell = row.createCell(i);
        	HSSFRichTextString text = new HSSFRichTextString(exclInfos.get(i).getZhname());
        	String txtval = "";
        	if(text != null && !"null".equalsIgnoreCase(text.getString())){
        		txtval = text.getString();
        	}
        	cell.setCellValue(txtval);
        	// 定义注释的大小和位置,详见文档
        	///此时增加注释
        	 //(int dx1, int dy1, int dx2, int dy2, short col1, int row1, short col2, int row2)
        	  //前四个参数是坐标点,后四个参数是编辑和显示批注时的大小.
        	HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, i, 0, i, (short) 4, 2, (short) 6, 5));
        	// 设置注释内容
        	comment.setString(new HSSFRichTextString(getModelPro(exclInfos.get(i).getProname())));
        	// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
        	comment.setAuthor("导出的本人信息");
        	
        	cell.setCellComment(comment);
        	// 锁定第一行
        	//设置此列style为非锁定     style.setLocked(false); 
        	//设置到新的单元格上     cell.setCellStyle(style);   
    		style.setLocked(true);
    		cell.setCellStyle(style);
    		
    		int bytes = title.getBytes().length;
    		arrColWidth[i] =  bytes < DEFAULT_COLOUMN_WIDTH ? DEFAULT_COLOUMN_WIDTH : bytes;
    		sheet.setColumnWidth(i,arrColWidth[i]*256);
    		
        }
        // 遍历集合数据，产生数据行
        Iterator<Map<String, Object>> it = dataset.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            Map<String, Object> t = it.next();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            //获取相关的field的信息
            for (short i = 0; i < exclInfos.size(); i++) {
            		try {
            			//如果需要获取的属性名称 需要转参
//            			String fieldShow = (exclInfos.get(i).getRefpro() !=null && !"".equals(exclInfos.get(i).getRefpro()))?
//            						exclInfos.get(i).getRefpro():
//            						exclInfos.get(i).getProname();            						
            			//获取需要显示的字段的名字
            			String refTable = exclInfos.get(i).getReftable();
            			//本表默认显示的字段
            			String proName = exclInfos.get(i).getProname();
            			String textValue = null;
            			
            			if(refTable != null && !"".equals(refTable)){
            				//此时应该将现实的 字段_本字段 作为键值 获取该value
            				String refShow = exclInfos.get(i).getRefshow();
            				String showValue = (refShow + "_"+proName).toUpperCase();
            				//得到其值
            				textValue = String.valueOf(t.get(showValue));
            			}else{
            				proName = proName.replace("_", "");
            				// 只是本表的数据
            				textValue = String.valueOf(t.get(proName));
            			}
						HSSFCell cell = row.createCell(i);
						cell.setCellStyle(style2);
						// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
						if (textValue != null) {
//						    Pattern p = Pattern.compile("^[0]//d+(//.//d+)?$");
//						    Matcher matcher = p.matcher(textValue);
//						    if (matcher.matches()) {
//						        // 是数字当作double处理
//						        cell.setCellValue(Double.parseDouble(textValue));
//						    } else {
						        HSSFRichTextString richString = new HSSFRichTextString(textValue+"");
						        HSSFFont font3 = workbook.createFont();
						        font3.setColor(HSSFColor.BLUE.index);
						        richString.applyFont(font3);
						        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						        String txtval = "";
					        	if(richString != null && !"null".equalsIgnoreCase(richString.getString())){
					        		txtval = richString.getString();
					        	}
					        	cell.setCellValue(txtval);
					        	
//						        cell.setCellValue(richString);
//						    }
						}
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					}
            }
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    
    
    
    /**
     * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
     * 
     * @param title
     *            表格标题名
     * @param model
     *            导出模型
     * @param dataset
     *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param out
     *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern
     *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
     */
    @SuppressWarnings({ "unchecked", "deprecation" })
    public void exportExcel(String title, Class model,
            Collection<Object> dataset, OutputStream out, String pattern) throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 15);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 生成并设置另一个样式
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style2.setWrapText(true);
        HSSFDataFormat format=workbook.createDataFormat();
        style2.setDataFormat(format.getFormat("@"));
        
        // 生成另一个字体
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style2.setFont(font2);

        // 声明一个画图的顶级管理器
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        
        List<Map> headers=new LinkedList<Map>();
        //通过查询进行相关的处理
        Field[] fields=model.getDeclaredFields();
        int headerindex=0;
        int[] arrColWidth = new int[fields.length];
        int minBytes = DEFAULT_COLOUMN_WIDTH;//至少字节数
        for (int i = 0; i < fields.length; i++) {
        	Field field=fields[i];
        	if(field.isAnnotationPresent(ExpTag.class)){
        		ExpTag a=field.getAnnotation(ExpTag.class);
        		Map<String, Object> header=new HashMap<String, Object>();
        		header.put("field", field);
        		header.put("proname", field.getName());
        		header.put("title", a.name());
        		header.put("gettype", field.getType());
        		if(a.keyValue()!=null&&a.keyValue().length>0){
        			header.put("keyvalue", a.keyValue());
        		}
        		header.put("pattern", a.pattern());
        		headers.add(header);
        		
        		
        		HSSFCell cell = row.createCell(headerindex);
            	HSSFRichTextString text = new HSSFRichTextString(a.name());
            	String txtval = "";
            	if(text != null && !"null".equalsIgnoreCase(text.getString())){
            		txtval = text.getString();
            	}
            	cell.setCellValue(txtval);
            	// 定义注释的大小和位置,详见文档
            	///此时增加注释
            	 //(int dx1, int dy1, int dx2, int dy2, short col1, int row1, short col2, int row2)
            	  //前四个参数是坐标点,后四个参数是编辑和显示批注时的大小.
            	HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, i, 0, i, (short) 4, 2, (short) 6, 5));
            	// 设置注释内容
            	comment.setString(new HSSFRichTextString(getModelPro(field.getName())));
            	// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
            	comment.setAuthor("导出的本人信息");
            	
            	cell.setCellComment(comment);
            	// 锁定第一行
            	//设置此列style为非锁定     style.setLocked(false); 
            	//设置到新的单元格上     cell.setCellStyle(style);   
        		style.setLocked(true);
        		cell.setCellStyle(style);
        		int bytes = title.getBytes().length;
        		
        		arrColWidth[headerindex] =  bytes < minBytes ? minBytes : bytes;
        		sheet.setColumnWidth(headerindex,arrColWidth[headerindex]*256);
        		
        		headerindex++;
        		
        	}
        	
		}
        if(dataset != null && !dataset.isEmpty()){
        	Iterator<Object> it = dataset.iterator();
        	int index = 1;
        	while (it.hasNext()) {
        		row = sheet.createRow(index);
        		Object t = it.next();
        		// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
        		//获取相关的field的信息
        		for (int i = 0; i < headers.size(); i++) {
        			try {
        				Map header=headers.get(i);
        				//如果需要获取的属性名称 需要转参
//            			String fieldShow = (exclInfos.get(i).getRefpro() !=null && !"".equals(exclInfos.get(i).getRefpro()))?
//            						exclInfos.get(i).getRefpro():
//            						exclInfos.get(i).getProname();            						
        				//获取需要显示的字段的名字
        				//本表默认显示的字段
        				String proName = header.get("proname").toString();
        				String textValue = null;
        				
        				PropertyDescriptor pd = new PropertyDescriptor(proName,model);
        				Method getMethod = pd.getReadMethod();//获得get方法
        				Object o = getMethod.invoke(t);//执行get方法返回一个Objec
        				
        				if(o!=null){
        					if(o instanceof Date){
        						textValue = new SimpleDateFormat(header.get("pattern").toString()).format(o);
        					}else{
        						textValue = o.toString();
        					}
        				}else{
        					textValue = "";
        				}
        				
        				if(header.containsKey("keyvalue")){
        					String[] keyvalue=(String[]) header.get("keyvalue");
        					for (int j = 0; j < keyvalue.length; j++) {
        						String [] kv=keyvalue[j].split(":");
        						if(null!=kv&&kv.length==2&&textValue.equals(kv[0])){
        							//得到其值
        							textValue=kv[1];
        							break;
        						}
        					}
        				}
        				
        				HSSFCell cell = row.createCell(i);
        				cell.setCellStyle(style2);
        				// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
        				if (textValue != null) {
//						    Pattern p = Pattern.compile("^[0]//d+(//.//d+)?$");
//						    Matcher matcher = p.matcher(textValue);
//						    if (matcher.matches()) {
//						        // 是数字当作double处理
//						        cell.setCellValue(Double.parseDouble(textValue));
//						    } else {
        					HSSFRichTextString richString = new HSSFRichTextString(textValue+"");
        					HSSFFont font3 = workbook.createFont();
        					font3.setColor(HSSFColor.BLUE.index);
        					richString.applyFont(font3);
        					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        					String txtval = "";
        					if(richString != null && !"null".equalsIgnoreCase(richString.getString())){
        						txtval = richString.getString();
        					}
        					cell.setCellValue(txtval);
        					
//						        cell.setCellValue(richString);
//						    }
        				}
        			} catch (NumberFormatException e) {
        				e.printStackTrace();
        			} catch (SecurityException e) {
        				e.printStackTrace();
        			} catch (IllegalArgumentException e) {
        				e.printStackTrace();
        			} catch (IntrospectionException e) {
        				e.printStackTrace();
        			}
        		}
        		index++;
        	}
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    
    
    /**
     * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
     * 
     * @param title
     *            表格标题名
     * @param model
     *            导出模型
     * @param dataset
     *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param out
     *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern
     *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
     *            
     *            
     * 222222222222222222222222           
     *            
     */
    @SuppressWarnings({ "unchecked", "deprecation" })
    public void exportExcel(String title, Class model,Collection<Object> dataset,Collection<Object> totalDataSet, OutputStream out, String pattern) throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    	// 声明一个工作薄
    	HSSFWorkbook workbook = new HSSFWorkbook();
    	// 生成一个表格
    	HSSFSheet sheet = workbook.createSheet(title);
    	// 设置表格默认列宽度为15个字节
    	sheet.setDefaultColumnWidth((short) 15);
    	// 生成一个样式
    	HSSFCellStyle style = workbook.createCellStyle();
    	// 设置这些样式
    	style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
    	style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
    	style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    	style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    	style.setBorderRight(HSSFCellStyle.BORDER_THIN);
    	style.setBorderTop(HSSFCellStyle.BORDER_THIN);
    	style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    	// 生成一个字体
    	HSSFFont font = workbook.createFont();
    	font.setColor(HSSFColor.VIOLET.index);
    	font.setFontHeightInPoints((short) 12);
    	font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    	// 把字体应用到当前的样式
    	style.setFont(font);
    	// 生成并设置另一个样式
    	HSSFCellStyle style2 = workbook.createCellStyle();
    	style2.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
    	style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
    	style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    	style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    	style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
    	style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
    	style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    	style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    	style2.setWrapText(true);
    	HSSFDataFormat format=workbook.createDataFormat();
    	style2.setDataFormat(format.getFormat("@"));
    	
    	// 生成另一个字体
    	HSSFFont font2 = workbook.createFont();
    	font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
    	// 把字体应用到当前的样式
    	style2.setFont(font2);
    	
    	// 声明一个画图的顶级管理器
    	HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
    	// 产生表格标题行
    	HSSFRow row = sheet.createRow(0);
    	
    	List<Map> headers=new LinkedList<Map>();
    	//通过查询进行相关的处理
    	Field[] fields=model.getDeclaredFields();
    	int headerindex=0;
    	int[] arrColWidth = new int[fields.length];
    	int minBytes = DEFAULT_COLOUMN_WIDTH;//至少字节数
    	for (int i = 0; i < fields.length; i++) {
    		Field field=fields[i];
    		if(field.isAnnotationPresent(ExpTag.class)){
    			ExpTag a=field.getAnnotation(ExpTag.class);
    			Map<String, Object> header=new HashMap<String, Object>();
    			header.put("field", field);
    			header.put("proname", field.getName());
    			header.put("title", a.name());
    			header.put("gettype", field.getType());
    			if(a.keyValue()!=null&&a.keyValue().length>0){
    				header.put("keyvalue", a.keyValue());
    			}
    			header.put("pattern", a.pattern());
    			headers.add(header);
    			
    			
    			HSSFCell cell = row.createCell(headerindex);
    			HSSFRichTextString text = new HSSFRichTextString(a.name());
    			String txtval = "";
    			if(text != null && !"null".equalsIgnoreCase(text.getString())){
    				txtval = text.getString();
    			}
    			cell.setCellValue(txtval);
    			// 定义注释的大小和位置,详见文档
    			///此时增加注释
    			//(int dx1, int dy1, int dx2, int dy2, short col1, int row1, short col2, int row2)
    			//前四个参数是坐标点,后四个参数是编辑和显示批注时的大小.
    			HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, i, 0, i, (short) 4, 2, (short) 6, 5));
    			// 设置注释内容
    			comment.setString(new HSSFRichTextString(getModelPro(field.getName())));
    			// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
    			comment.setAuthor("导出的本人信息");
    			
    			cell.setCellComment(comment);
    			// 锁定第一行
    			//设置此列style为非锁定     style.setLocked(false); 
    			//设置到新的单元格上     cell.setCellStyle(style);   
    			style.setLocked(true);
    			cell.setCellStyle(style);
    			int bytes = title.getBytes().length;
    			
    			arrColWidth[headerindex] =  bytes < minBytes ? minBytes : bytes;
    			sheet.setColumnWidth(headerindex,arrColWidth[headerindex]*256);
    			
    			headerindex++;
    			
    		}
    		
    	}
    	int index = 1;
    	if(dataset != null && !dataset.isEmpty()){
    		Iterator<Object> it = dataset.iterator();
    		while (it.hasNext()) {
    			row = sheet.createRow(index);
    			Object t = it.next();
    			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
    			//获取相关的field的信息
    			for (int i = 0; i < headers.size(); i++) {
    				try {
    					Map header=headers.get(i);
    					//如果需要获取的属性名称 需要转参
//            			String fieldShow = (exclInfos.get(i).getRefpro() !=null && !"".equals(exclInfos.get(i).getRefpro()))?
//            						exclInfos.get(i).getRefpro():
//            						exclInfos.get(i).getProname();            						
    					//获取需要显示的字段的名字
    					//本表默认显示的字段
    					String proName = header.get("proname").toString();
    					String textValue = null;
    					
    					PropertyDescriptor pd = new PropertyDescriptor(proName,model);
    					Method getMethod = pd.getReadMethod();//获得get方法
    					Object o = getMethod.invoke(t);//执行get方法返回一个Objec
    					
    					if(o!=null){
    						if(o instanceof Date){
    							textValue = new SimpleDateFormat(header.get("pattern").toString()).format(o);
    						}else{
    							textValue = o.toString();
    						}
    					}else{
    						textValue = "";
    					}
    					
    					if(header.containsKey("keyvalue")){
    						String[] keyvalue=(String[]) header.get("keyvalue");
    						for (int j = 0; j < keyvalue.length; j++) {
    							String [] kv=keyvalue[j].split(":");
    							if(null!=kv&&kv.length==2&&textValue.equals(kv[0])){
    								//得到其值
    								textValue=kv[1];
    								break;
    							}
    						}
    					}
    					
    					HSSFCell cell = row.createCell(i);
    					cell.setCellStyle(style2);
    					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
    					if (textValue != null) {
//						    Pattern p = Pattern.compile("^[0]//d+(//.//d+)?$");
//						    Matcher matcher = p.matcher(textValue);
//						    if (matcher.matches()) {
//						        // 是数字当作double处理
//						        cell.setCellValue(Double.parseDouble(textValue));
//						    } else {
    						HSSFRichTextString richString = new HSSFRichTextString(textValue+"");
    						HSSFFont font3 = workbook.createFont();
    						font3.setColor(HSSFColor.BLUE.index);
    						richString.applyFont(font3);
    						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
    						String txtval = "";
    						if(richString != null && !"null".equalsIgnoreCase(richString.getString())){
    							txtval = richString.getString();
    						}
    						cell.setCellValue(txtval);
    						
//						        cell.setCellValue(richString);
//						    }
    					}
    				} catch (NumberFormatException e) {
    					e.printStackTrace();
    				} catch (SecurityException e) {
    					e.printStackTrace();
    				} catch (IllegalArgumentException e) {
    					e.printStackTrace();
    				} catch (IntrospectionException e) {
    					e.printStackTrace();
    				}
    			}
    			index++;
    		}
    	}
    	if(totalDataSet != null && !totalDataSet.isEmpty()){
    		Iterator<Object> it = totalDataSet.iterator();
    		while (it.hasNext()) {
    			row = sheet.createRow(index);
    			Object t = it.next();
    			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
    			//获取相关的field的信息
    			for (int i = 0; i < headers.size(); i++) {
    				try {
    					Map header=headers.get(i);
    					//如果需要获取的属性名称 需要转参
//            			String fieldShow = (exclInfos.get(i).getRefpro() !=null && !"".equals(exclInfos.get(i).getRefpro()))?
//            						exclInfos.get(i).getRefpro():
//            						exclInfos.get(i).getProname();            						
    					//获取需要显示的字段的名字
    					//本表默认显示的字段
    					String proName = header.get("proname").toString();
    					String textValue = null;
    					
    					PropertyDescriptor pd = new PropertyDescriptor(proName,model);
    					Method getMethod = pd.getReadMethod();//获得get方法
    					Object o = getMethod.invoke(t);//执行get方法返回一个Objec
    					
    					if(o!=null){
    						if(o instanceof Date){
    							textValue = new SimpleDateFormat(header.get("pattern").toString()).format(o);
    						}else{
    							textValue = o.toString();
    						}
    					}else{
    						textValue = "";
    					}
    					
    					if(header.containsKey("keyvalue")){
    						String[] keyvalue=(String[]) header.get("keyvalue");
    						for (int j = 0; j < keyvalue.length; j++) {
    							String [] kv=keyvalue[j].split(":");
    							if(null!=kv&&kv.length==2&&textValue.equals(kv[0])){
    								//得到其值
    								textValue=kv[1];
    								break;
    							}
    						}
    					}
    					
    					HSSFCell cell = row.createCell(i);
    					cell.setCellStyle(style2);
    					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
    					if (textValue != null) {
//						    Pattern p = Pattern.compile("^[0]//d+(//.//d+)?$");
//						    Matcher matcher = p.matcher(textValue);
//						    if (matcher.matches()) {
//						        // 是数字当作double处理
//						        cell.setCellValue(Double.parseDouble(textValue));
//						    } else {
    						HSSFRichTextString richString = new HSSFRichTextString(textValue+"");
//    						HSSFFont font3 = workbook.createFont();
//    						font3.setColor(HSSFColor.BLUE.index);
//    						richString.applyFont(font3);
//    						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
    						cell.setCellStyle(style);
    						String txtval = "";
    						if(richString != null && !"null".equalsIgnoreCase(richString.getString())){
    							txtval = richString.getString();
    						}
    						cell.setCellValue(txtval);
    						
//						        cell.setCellValue(richString);
//						    }
    					}
    				} catch (NumberFormatException e) {
    					e.printStackTrace();
    				} catch (SecurityException e) {
    					e.printStackTrace();
    				} catch (IllegalArgumentException e) {
    					e.printStackTrace();
    				} catch (IntrospectionException e) {
    					e.printStackTrace();
    				}
    			}
    			index++;
    		}
    	}
    	try {
    		workbook.write(out);
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    }
    
    
    
    /**
	 *替换字符
	 * @param srcStr：源字符串
	 * @param complie：被替换的字符
	 * @param descStr：替换成的字符
	 * @throws Exception
	 */
	public static String appendTail(String srcStr,String complie,String descStr){
		 Pattern p = Pattern.compile(complie);
		 Matcher m = p.matcher(srcStr);
		 StringBuffer sb = new StringBuffer();
		 while (m.find()) {
		     m.appendReplacement(sb, descStr);
		 }
		 m.appendTail(sb);
		 return sb.toString();
	}
	
	public static String getExportFile(String srcStr){
		String webRoot = getWebRoot().replaceAll("\\\\", "/");
		return appendTail(srcStr, "%workspace%", webRoot);
	}
	//将//replace ////
    public static String getWebRoot(){
    	String classPath = getClasspath();
    	if(classPath.indexOf("WEB-INF") > -1){
    		File file = new File(classPath);
    		if(file.exists()){
    			return file.getParentFile().getParent();
    		}
    	}
    	return classPath;
    }
    public static void main1(String[] args) throws Exception {
    	System.out.println(getExportFile("%workspace%/contant/person/export"));
        // 测试学生
//        ExportExcel<PersonBaseinfo> ex = new ExportExcel<PersonBaseinfo>();
//        List<PersonBaseinfo> dataset = new ArrayList<PersonBaseinfo>();
//        dataset.add(new PersonBaseinfo("1", "1L", "2"));
//        dataset.add(new PersonBaseinfo("2", "2L", "3"));
//        dataset.add(new PersonBaseinfo("3", "3L", "4"));
//        dataset.add(new PersonBaseinfo("4", "4L", "5"));
//        dataset.add(new PersonBaseinfo("5", "5L", "6"));
        // 测试图书
//        ExportExcel<Book> ex2 = new ExportExcel<Book>();
//        String[] headers2 = { "图书编号", "图书名称", "图书作者", "图书价格", "图书ISBN",
//                "图书出版社", "封面图片" };
//        List<Book> dataset2 = new ArrayList<Book>();
        try {
//            BufferedInputStream bis = new BufferedInputStream(
//                    new FileInputStream("book.jpg"));
//            byte[] buf = new byte[bis.available()];
//            while ((bis.read(buf)) != -1) {
//                //
//            }
//            dataset2.add(new Book(1, "jsp", "leno", 300.33f, "1234567",
//                    "清华出版社", buf));
//            dataset2.add(new Book(2, "java编程思想", "brucl", 300.33f, "1234567",
//                    "阳光出版社", buf));
//            dataset2.add(new Book(3, "DOM艺术", "lenotang", 300.33f, "1234567",
//                    "清华出版社", buf));
//            dataset2.add(new Book(4, "c++经典", "leno", 400.33f, "1234567",
//                    "清华出版社", buf));
//            dataset2.add(new Book(5, "c#入门", "leno", 300.33f, "1234567",
//                    "汤春秀出版社", buf));

            OutputStream out = new FileOutputStream("E://a.xls");
            List<BaseExclinfo> exclInfos = new ArrayList<BaseExclinfo>();
//            exclInfos.add(new BaseExclinfo("bas_personinfo", "姓名", "personname", 0));
//            exclInfos.add(new BaseExclinfo("bas_personinfo", "籍贯", "nation", 1));
//            exclInfos.add(new BaseExclinfo("bas_personinfo", "电话", "telephone", 2));
//            ex.exportExcel("sheet",dataset,exclInfos, out);
            out.close();
            //输出文件
            
            
//            JOptionPane.showMessageDialog(null, "导出成功!");
            System.out.println("excel导出成功！");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

	public ResponseEntity responseExcl(String exportFile,String downFileName) {
		ResponseEntity<byte[]> responseEntity = null;
		File file = new File(exportFile); // 指定文件

		HttpHeaders headers = new HttpHeaders();
		System.out.println(downFileName+"sssssssss.xls");
		String dn = downFileName+"[excel"+Guid.Instance()+"].xls";
				
		
		List<Charset> lcset = new ArrayList<Charset>();
		lcset.add(Charset.forName("UTF-8"));
//		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);//附件流类型
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);//附件流类型
		headers.setAcceptCharset(lcset);
		try {
			headers.setContentDispositionFormData("attachment;", new String(downFileName.getBytes("GBK"), "ISO-8859-1")+".xls");//附件名称
			System.out.println("成功返回地址:"+ exportFile);
			responseEntity =  new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
		} catch (Exception e) {
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", "error.txt");//表示文件已经不存在
			responseEntity =  new ResponseEntity<byte[]>("文件不存在".getBytes(), headers,HttpStatus.OK);
		}

		return responseEntity;
	}

	//获取相应的工程路径
	public static String getClasspath(){
		String path = "";
		try {
			path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		} catch (Exception e) {
			path = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
		}
		if(!(path.indexOf("classes") > -1)){
			String libpath = path;
			path = libpath.replace("lib", "webapps") + File.separator +getProject() + "WebRoot\\WEB-INF\\classes" ;
		}
		return path;
	}
	/**
	 * 获取工程名
	 * @return
	 */
	public static String getProject(){
		String path = System.getProperty("user.dir");
		String projectname = "";
		int lastchar = path.lastIndexOf("\\");
		if(lastchar> -1){
			projectname = path.substring(lastchar+1);
		}
		return projectname;
	}
	public String getExportFile(ExclBsModel bsModel) {
		String exportFile = getExportFile(bsModel.getExportPath()) + File.separator + bsModel.getDownFileName()+Guid.Instance()+ ".xls";
		return exportFile;
	}
	//在处理相应字段属性时需要处理
	public String getModelPro(String column){
		String tempStr = column.toLowerCase();
		if(column != null && !"".equals(column)){
			if(column.indexOf("_") > -1){
				String[] temps = tempStr.split("_");
				String lastChar = temps[1];
				tempStr =temps[0] + upperFirstChar(lastChar);
			}
		}
		return tempStr;
	}
	
	//设置头字母大写
	private static String upperFirstChar(String str){
		if(str != null && !"".equals(str)){
			str = str.replaceFirst(str.charAt(0) + "", (char)(str.charAt(0) - ' ') + "");
		}
		return str;
	}
	 public void exportExcel(String title,Class model,Collection<Object> dataset,Collection<Object> totalDataSet, OutputStream out) {
        try {
			exportExcel(title, model, dataset,totalDataSet, out, "yyyy-MM-dd");
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
    }

	public void exportExcel(String title,Set<ExcelColumn> excelColumnHeadSet,List<HashMap<String, Object>> dataset,List<HashMap<String, Object>> totalDataSet, OutputStream out) {
			try {
				exportExcel(title, excelColumnHeadSet, dataset,totalDataSet, out, "yyyy-MM-dd");
			} catch (NoSuchFieldException | NoSuchMethodException
					| IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			 
	}
	public HSSFCellStyle headStyleSetting(HSSFWorkbook workbook){
		HSSFCellStyle style = workbook.createCellStyle();
    	// 设置这些样式
    	style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
    	style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
    	style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    	style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    	style.setBorderRight(HSSFCellStyle.BORDER_THIN);
    	style.setBorderTop(HSSFCellStyle.BORDER_THIN);
    	style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    	return style;
	}
	//设置单元格的样式
	public static int mulitCellWidth(HSSFCell hssfCell,String text){
		return text!=null?(text.getBytes().length < DEFAULT_COLOUMN_WIDTH?DEFAULT_COLOUMN_WIDTH:text.getBytes().length)*256 + 384:DEFAULT_COLOUMN_WIDTH*256+384;
	}
	//设置单元格的样式
	public static HSSFCellStyle mulitCellStyle(HSSFWorkbook workbook){
		HSSFCellStyle cellStyle = workbook.createCellStyle();
    	// 设置这些样式
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中  
		// 设置背景色    
		cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		//设置边框
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		//垂直居中
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		//自动换行
		cellStyle.setWrapText(true);
		
		HSSFDataFormat format=workbook.createDataFormat();
		cellStyle.setDataFormat(format.getFormat("@"));
    	
		HSSFFont font = workbook.createFont();    
		font.setFontName("黑体");    
		font.setFontHeightInPoints((short) 14);//设置字体大小
		cellStyle.setFont(font);//选择需要用到的字体格式
		
    	return cellStyle;
	}
	//设置单元格的样式
	public static HSSFCellStyle mulitGridCellStyle(HSSFWorkbook workbook){
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		// 设置这些样式
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中  
		// 设置背景色    
//		cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
//		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		//设置边框
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		//垂直居中
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		//自动换行
		cellStyle.setWrapText(true);
		
		HSSFDataFormat format=workbook.createDataFormat();
		cellStyle.setDataFormat(format.getFormat("@"));
		
		HSSFFont font = workbook.createFont();    
		font.setFontName("宋体");    
		font.setFontHeightInPoints((short) 12);//设置字体大小
		cellStyle.setFont(font);//选择需要用到的字体格式
		
		return cellStyle;
	}
	public HSSFCellStyle gridStyleSetting(HSSFWorkbook workbook){
		HSSFCellStyle style2 = workbook.createCellStyle();
    	style2.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
    	style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
    	style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    	style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    	style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
    	style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
    	style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    	style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    	style2.setWrapText(true);
    	HSSFDataFormat format=workbook.createDataFormat();
    	style2.setDataFormat(format.getFormat("@"));
		return style2;
	}
	//创建表头信息
	public void createXlsHead(Set<ExcelColumn> excelColumnHeadSet,HSSFCellStyle style,HSSFSheet sheet,HSSFRow row,int maxCellNum){
		Iterator<ExcelColumn> it = excelColumnHeadSet.iterator();
		while(it.hasNext()){
			ExcelColumn excelColumn = it.next();
			//判断是否存在子项目
    		Set<ExcelColumn> columns = excelColumn.getColumns();
    		maxCellNum=row.getPhysicalNumberOfCells();//获取总列数
    		//生成N行1列数据
    		createMulitCell(sheet, row,style, excelColumn,maxCellNum);
    		if(columns != null && !columns.isEmpty()){
    			//这样就换行了
    			int rowNum=sheet.getLastRowNum();//最大的行数
    			//获取最大列
    			row = sheet.createRow(rowNum+1);
    			createXlsHead(columns, style, sheet,row,maxCellNum);
    		}
		}
	}
//	HSSFCell cell = row.createCell(headerindex);
	public void createMulitCell(HSSFSheet sheet,HSSFRow row,HSSFCellStyle style,ExcelColumn excelColumn,int maxCellNum){
		int rowNum=sheet.getLastRowNum();//最大的行数
//		int columnNum=row.getPhysicalNumberOfCells();//获取总列数
		int cols = excelColumn.getCols();
		int rows = excelColumn.getRows();
		//设置列名称
		HSSFCell cell = row.getCell(maxCellNum);
		if(cols > 1){
			//创建三列然后合并
			for(int i = maxCellNum; i <= cols; i++){
				HSSFCell  hssfRowTmp = row.createCell(i);
				hssfRowTmp.setCellStyle(mulitCellStyle(sheet.getWorkbook()));
				sheet.setColumnWidth(hssfRowTmp.getColumnIndex(), mulitCellWidth(hssfRowTmp, ""));
			}
			//创建后合并
			sheet.addMergedRegion(new CellRangeAddress(0,(short)0,maxCellNum,(short)(maxCellNum+cols-1))); //起始行号，终止行号， 起始列号，终止列号
			 
		}else{
			cell = row.createCell(maxCellNum);
		}
		//设置列名称
		cell.setCellValue(excelColumn.getFieldDispName());
		cell.setCellStyle(style);
		System.out.println(cell);
	}
    @SuppressWarnings({ "unchecked", "deprecation" })
    public void exportExcel(String title,
    		Set<ExcelColumn> excelColumnHeadSet,
			List<HashMap<String, Object>> dataset,
			List<HashMap<String, Object>> totalDataSet, OutputStream out,
			String pattern) throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    	// 声明一个工作薄
    	HSSFWorkbook workbook = new HSSFWorkbook();
    	// 生成一个表格
    	HSSFSheet sheet = workbook.createSheet(title);
    	// 设置表格默认列宽度为15个字节
    	sheet.autoSizeColumn(1, true);
    	// 生成一个样式
    	HSSFCellStyle style = headStyleSetting(workbook);
    	// 生成一个字体
    	HSSFFont font = workbook.createFont();
    	font.setColor(HSSFColor.VIOLET.index);
    	font.setFontHeightInPoints((short) 12);
    	font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    	// 把字体应用到当前的样式
    	style.setFont(font);
    	// 生成并设置另一个样式
    	HSSFCellStyle style2 = gridStyleSetting(workbook);
    	// 生成另一个字体
    	HSSFFont font2 = workbook.createFont();
    	font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
    	// 把字体应用到当前的样式
    	style2.setFont(font2);
    	// 声明一个画图的顶级管理器
    	HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
    	// 产生表格标题行
    	HSSFRow row = sheet.createRow(0);
    	
    	createXlsHead(excelColumnHeadSet, style, sheet,row,0);
    	
    	
//    	List<Map> headers=new LinkedList<Map>();
//    	//通过查询进行相关的处理
//    	Field[] fields=model.getDeclaredFields();
//    	int headerindex=0;
//    	int[] arrColWidth = new int[fields.length];
//    	int minBytes = DEFAULT_COLOUMN_WIDTH;//至少字节数
//    	for (int i = 0; i < fields.length; i++) {
//    		Field field=fields[i];
//    		if(field.isAnnotationPresent(ExpTag.class)){
//    			ExpTag a=field.getAnnotation(ExpTag.class);
//    			Map<String, Object> header=new HashMap<String, Object>();
//    			header.put("field", field);
//    			header.put("proname", field.getName());
//    			header.put("title", a.name());
//    			header.put("gettype", field.getType());
//    			if(a.keyValue()!=null&&a.keyValue().length>0){
//    				header.put("keyvalue", a.keyValue());
//    			}
//    			header.put("pattern", a.pattern());
//    			headers.add(header);
//    			
//    			
//    			HSSFCell cell = row.createCell(headerindex);
//    			HSSFRichTextString text = new HSSFRichTextString(a.name());
//    			String txtval = "";
//    			if(text != null && !"null".equalsIgnoreCase(text.getString())){
//    				txtval = text.getString();
//    			}
//    			cell.setCellValue(txtval);
//    			// 定义注释的大小和位置,详见文档
//    			///此时增加注释
//    			//(int dx1, int dy1, int dx2, int dy2, short col1, int row1, short col2, int row2)
//    			//前四个参数是坐标点,后四个参数是编辑和显示批注时的大小.
//    			HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, i, 0, i, (short) 4, 2, (short) 6, 5));
//    			// 设置注释内容
//    			comment.setString(new HSSFRichTextString(getModelPro(field.getName())));
//    			// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
//    			comment.setAuthor("导出的本人信息");
//    			
//    			cell.setCellComment(comment);
//    			// 锁定第一行
//    			//设置此列style为非锁定     style.setLocked(false); 
//    			//设置到新的单元格上     cell.setCellStyle(style);   
//    			style.setLocked(true);
//    			cell.setCellStyle(style);
//    			int bytes = title.getBytes().length;
//    			
//    			arrColWidth[headerindex] =  bytes < minBytes ? minBytes : bytes;
//    			sheet.setColumnWidth(headerindex,arrColWidth[headerindex]*256);
//    			
//    			headerindex++;
//    			
//    		}
//    		
//    	}
//    	int index = 1;
//    	if(dataset != null && !dataset.isEmpty()){
//    		Iterator<Object> it = dataset.iterator();
//    		while (it.hasNext()) {
//    			row = sheet.createRow(index);
//    			Object t = it.next();
//    			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
//    			//获取相关的field的信息
//    			for (int i = 0; i < headers.size(); i++) {
//    				try {
//    					Map header=headers.get(i);
//    					//如果需要获取的属性名称 需要转参
////            			String fieldShow = (exclInfos.get(i).getRefpro() !=null && !"".equals(exclInfos.get(i).getRefpro()))?
////            						exclInfos.get(i).getRefpro():
////            						exclInfos.get(i).getProname();            						
//    					//获取需要显示的字段的名字
//    					//本表默认显示的字段
//    					String proName = header.get("proname").toString();
//    					String textValue = null;
//    					
//    					PropertyDescriptor pd = new PropertyDescriptor(proName,model);
//    					Method getMethod = pd.getReadMethod();//获得get方法
//    					Object o = getMethod.invoke(t);//执行get方法返回一个Objec
//    					
//    					if(o!=null){
//    						if(o instanceof Date){
//    							textValue = new SimpleDateFormat(header.get("pattern").toString()).format(o);
//    						}else{
//    							textValue = o.toString();
//    						}
//    					}else{
//    						textValue = "";
//    					}
//    					
//    					if(header.containsKey("keyvalue")){
//    						String[] keyvalue=(String[]) header.get("keyvalue");
//    						for (int j = 0; j < keyvalue.length; j++) {
//    							String [] kv=keyvalue[j].split(":");
//    							if(null!=kv&&kv.length==2&&textValue.equals(kv[0])){
//    								//得到其值
//    								textValue=kv[1];
//    								break;
//    							}
//    						}
//    					}
//    					
//    					HSSFCell cell = row.createCell(i);
//    					cell.setCellStyle(style2);
//    					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
//    					if (textValue != null) {
////						    Pattern p = Pattern.compile("^[0]//d+(//.//d+)?$");
////						    Matcher matcher = p.matcher(textValue);
////						    if (matcher.matches()) {
////						        // 是数字当作double处理
////						        cell.setCellValue(Double.parseDouble(textValue));
////						    } else {
//    						HSSFRichTextString richString = new HSSFRichTextString(textValue+"");
//    						HSSFFont font3 = workbook.createFont();
//    						font3.setColor(HSSFColor.BLUE.index);
//    						richString.applyFont(font3);
//    						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//    						String txtval = "";
//    						if(richString != null && !"null".equalsIgnoreCase(richString.getString())){
//    							txtval = richString.getString();
//    						}
//    						cell.setCellValue(txtval);
//    						
////						        cell.setCellValue(richString);
////						    }
//    					}
//    				} catch (NumberFormatException e) {
//    					e.printStackTrace();
//    				} catch (SecurityException e) {
//    					e.printStackTrace();
//    				} catch (IllegalArgumentException e) {
//    					e.printStackTrace();
//    				} catch (IntrospectionException e) {
//    					e.printStackTrace();
//    				}
//    			}
//    			index++;
//    		}
//    	}
//    	if(totalDataSet != null && !totalDataSet.isEmpty()){
//    		Iterator<Object> it = totalDataSet.iterator();
//    		while (it.hasNext()) {
//    			row = sheet.createRow(index);
//    			Object t = it.next();
//    			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
//    			//获取相关的field的信息
//    			for (int i = 0; i < headers.size(); i++) {
//    				try {
//    					Map header=headers.get(i);
//    					//如果需要获取的属性名称 需要转参
////            			String fieldShow = (exclInfos.get(i).getRefpro() !=null && !"".equals(exclInfos.get(i).getRefpro()))?
////            						exclInfos.get(i).getRefpro():
////            						exclInfos.get(i).getProname();            						
//    					//获取需要显示的字段的名字
//    					//本表默认显示的字段
//    					String proName = header.get("proname").toString();
//    					String textValue = null;
//    					
//    					PropertyDescriptor pd = new PropertyDescriptor(proName,model);
//    					Method getMethod = pd.getReadMethod();//获得get方法
//    					Object o = getMethod.invoke(t);//执行get方法返回一个Objec
//    					
//    					if(o!=null){
//    						if(o instanceof Date){
//    							textValue = new SimpleDateFormat(header.get("pattern").toString()).format(o);
//    						}else{
//    							textValue = o.toString();
//    						}
//    					}else{
//    						textValue = "";
//    					}
//    					
//    					if(header.containsKey("keyvalue")){
//    						String[] keyvalue=(String[]) header.get("keyvalue");
//    						for (int j = 0; j < keyvalue.length; j++) {
//    							String [] kv=keyvalue[j].split(":");
//    							if(null!=kv&&kv.length==2&&textValue.equals(kv[0])){
//    								//得到其值
//    								textValue=kv[1];
//    								break;
//    							}
//    						}
//    					}
//    					
//    					HSSFCell cell = row.createCell(i);
//    					cell.setCellStyle(style2);
//    					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
//    					if (textValue != null) {
////						    Pattern p = Pattern.compile("^[0]//d+(//.//d+)?$");
////						    Matcher matcher = p.matcher(textValue);
////						    if (matcher.matches()) {
////						        // 是数字当作double处理
////						        cell.setCellValue(Double.parseDouble(textValue));
////						    } else {
//    						HSSFRichTextString richString = new HSSFRichTextString(textValue+"");
////    						HSSFFont font3 = workbook.createFont();
////    						font3.setColor(HSSFColor.BLUE.index);
////    						richString.applyFont(font3);
////    						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//    						cell.setCellStyle(style);
//    						String txtval = "";
//    						if(richString != null && !"null".equalsIgnoreCase(richString.getString())){
//    							txtval = richString.getString();
//    						}
//    						cell.setCellValue(txtval);
//    						
////						        cell.setCellValue(richString);
////						    }
//    					}
//    				} catch (NumberFormatException e) {
//    					e.printStackTrace();
//    				} catch (SecurityException e) {
//    					e.printStackTrace();
//    				} catch (IllegalArgumentException e) {
//    					e.printStackTrace();
//    				} catch (IntrospectionException e) {
//    					e.printStackTrace();
//    				}
//    			}
//    			index++;
//    		}
//    	}
    	try {
    		workbook.write(out);
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    }

	@SuppressWarnings("resource")
	public static void exportExcel(WorkbookInfo workbookXML,Map<String, Object> map,ExportHelper helper, OutputStream out) throws Exception {
		// 声明一个工作薄
    	HSSFWorkbook workbook = new HSSFWorkbook();
		
    	// 生成一个表格
    	List<SheetInfo> sheetinfoXML = workbookXML.getSheetList();
    	Iterator<SheetInfo> it = sheetinfoXML.iterator();
    	while(it.hasNext()){
    		SheetInfo sheetInfoXML = it.next();
    		//创建了sheet
    		HSSFSheet sheet = workbook.createSheet(sheetInfoXML.getName());
    		 // 声明一个画图的顶级管理器
        	HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
    		// 设置表格默认列宽度为15个字节
    		sheet.autoSizeColumn(1, true);
    		List<RowInfo> rowsInfoXML = sheetInfoXML.getRowList();
    		exportCreateRows(rowsInfoXML, sheet,map,helper,patriarch);
    		//遍历最后一行的表头的备注情况
    		Map<String, Integer> headParamMap = getHeadParams(sheet);
    		System.out.println(headParamMap);
    		//创建表格内容（gridDataMap）
    		appendGridData(sheet, map, headParamMap);
    	}
    	try {
            workbook.write(out);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	public static void appendGridData(HSSFSheet sheet,Map<String, Object> map,Map<String, Integer> headParamMap){
		//列表信息
		List<Map<String, Object>> gridData = (List<Map<String, Object>>) map.get("gridDataMap");
		if( gridData != null && !gridData.isEmpty()){
			Iterator<Map<String, Object>> it = gridData.iterator();
			int maxRow = sheet.getLastRowNum();
			while(it.hasNext()){
				Map<String, Object> dataMap = it.next();
				//开始创建数据
				maxRow = sheet.getLastRowNum();
				HSSFRow hssfRow = sheet.createRow(maxRow+1);
				//创建所有的表格
				for(String paramKey : headParamMap.keySet()){
					HSSFCell hssfCell = hssfRow.createCell(headParamMap.get(paramKey));
					//设置数据
					HSSFRichTextString text = new HSSFRichTextString(String.valueOf(null!=dataMap.get(paramKey)?dataMap.get(paramKey):""));
					hssfCell.setCellValue(text);
					sheet.setColumnWidth(hssfCell.getColumnIndex(), mulitCellWidth(hssfCell, text.toString()));
					hssfCell.setCellStyle(mulitGridCellStyle(sheet.getWorkbook()));
				}
			}
		}
	}
	
	
	public static Map<String, Integer> getHeadParams(HSSFSheet sheet) throws Exception {
		int lastRowIndex = sheet.getLastRowNum();
		//获取最后的行
		HSSFRow row = sheet.getRow(lastRowIndex);
		//遍历行中的所有单元格 Java5+ 才能使用  
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		for (Cell cell : row) { 
			if(null!=cell.getCellComment()){
				map.put(cell.getCellComment().getString().toString(), cell.getColumnIndex());
			}
		}
		return map;
	}
	public static void exportCreateRows(List<RowInfo> rowsInfoXML,HSSFSheet sheet,Map<String, Object> map,ExportHelper helper,HSSFPatriarch patriarch) throws Exception{
		//开始创建  首先创建第一行
		Iterator<RowInfo> it = rowsInfoXML.iterator();
		int i = 0;
    	while(it.hasNext()){
    		RowInfo rowInfoXML = it.next();
    		//第一行的情况【行内创建单元格】
    		List<CellInfo> cellInfoXML = rowInfoXML.getCellInfos();
    		HSSFRow hssfRow = sheet.createRow(i);//表头 rowIndex
    		hssfRow.setHeightInPoints(20);  
    		exportCreateCells(cellInfoXML, sheet,hssfRow,map,helper,patriarch);
    		i++;
    	}
	}
	@SuppressWarnings("unchecked")
	public static void exportCreateCells(List<CellInfo> cellInfoXML,HSSFSheet sheet,HSSFRow hssfRow,Map<String, Object> map,ExportHelper helper,HSSFPatriarch patriarch) throws Exception{
		//开始创建  首先创建第一行
		Iterator<CellInfo> it = cellInfoXML.iterator();
		while(it.hasNext()){
			CellInfo cellInfo = it.next();
			//创建单元格
			/**
			 * 1：首先是否存在循环参数，有则先创建单元格
			 * 2：cols是否有跨列参数，有则先创建
			 * 3：前两个都没有则创建单元格
			 * 4：是否存在上下合并单元格
			 */
			String foreach = cellInfo.getForeach();
			//跨行的情况也应该先处理
			String cols = cellInfo.getCols();
			String cellIndex_from = cellInfo.getCellIndex_from();
			String cellRangeAddress_rows = cellInfo.getCellRangeAddress_rows();
			String cellLevel = cellInfo.getLevel();
			if(foreach != null && !"".equals(foreach)){
				//正则表达式 获取参数
				String foreachKey = StringHelper.getInstance().matchKhnr(foreach,"$");
				//获取参数值【以数组作为例子】
				if(cols != null && !"".equals(cols) && (cellIndex_from == null || "".equals(cellIndex_from))){
					//[只有cols 无cellIndex_from]
					Map<String, String> foreachParam = (Map<String, String>) map.get(foreachKey);
					createCellColsWithForeachToWorkbook(sheet,hssfRow, cellInfo,map,foreachParam,patriarch);
				}else if(cellIndex_from != null && !"".equals(cellIndex_from) && (cols == null || "".equals(cols)) ){
					//单元格开始的地方:[只有cellIndex_from 无cols]
					Map<String, Map<String, String>> foreachParam = (Map<String, Map<String,String>>) map.get(foreachKey);
					//获取
					String cellstartindexWithParam =  StringHelper.getInstance().matchKhnr(cellIndex_from,"#");
					//通过id：100001_1查找到对应的cell
					org.dom4j.Node node = null;
					try {
						node = helper.getCellNodeById(null, cellstartindexWithParam);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//获取node的描述名称
					String foreachTmp = node.valueOf("attribute::foreach");//获取foreach属性
					if(foreachTmp != null && !"".equals(foreachTmp)){
						//如果存在cols 则
						String colsTmp = node.valueOf("attribute::cols");//获取cols属性
						if(colsTmp != null && !"".equals(colsTmp) && cellLevel != null && !"".equals(cellLevel)){
							//要开始循环了
							createCellColsNestToWorkbook(sheet,hssfRow, cellInfo,map,foreachParam,node,patriarch,helper);
						}else{
							createCellColsWithForeachNestToWorkbook(sheet,hssfRow, cellInfo,map,foreachParam,patriarch,helper);
						}
					}else{
						//查找cellIndex_from的值
						cellIndex_from = cellInfo.getCellIndex_from();
						Map<String, String> foreachParam_tmp = (Map<String, String>) map.get(foreachKey);
						String cellstartindexWithParam_tmp =  StringHelper.getInstance().matchKhnr(cellIndex_from,"#");
						//通过id：100001_1查找到对应的cell
						org.dom4j.Node node_tmp = helper.getCellNodeById(null, cellstartindexWithParam_tmp);
						//获取node的描述名称
						String fieldDispName = node_tmp.valueOf("attribute::fieldDispName");//获取fieldDispName属性
						String searchComment = node_tmp.valueOf("attribute::fieldName");//获取fieldName属性
						//如果fieldName是null 则保存的是id：id必须唯一
						if(searchComment == null || "".equals(searchComment)){
							searchComment = node.valueOf("attribute::id");//获取id属性
						}
						//如果此处又出现了
						
						
						//在sheet中开始查找
						Cell cell = getCellIndexWithComment(sheet,hssfRow, searchComment);
						// 找到了后获取cell的开始列
						if (cell != null) {
							createCellForeachToWorkbook(sheet,hssfRow, cellInfo,cell,foreachParam_tmp,patriarch);
						}
					}
				}else if(cols != null && !"".equals(cols) && cellIndex_from != null && !"".equals(cellIndex_from) ){
					//单元格开始的地方:[既有cellIndex_from 又有cols]
					/**
					 * 1:先找到上一个参数:循环第一层参数
					 */
					cellIndex_from = cellInfo.getCellIndex_from();
					Map<String, Map<String, String>> foreachParam = (Map<String, Map<String, String>>) map.get(foreachKey);
					String cellstartindexWithParam =  StringHelper.getInstance().matchKhnr(cellIndex_from,"#");
					//cols
					
					String colsKey = StringHelper.getInstance().matchKhnr(cols,"$");
					String colsStr = (String) map.get(colsKey);
					Integer col_int = Integer.valueOf(colsStr);
					
					
//					
//					for(int i = 0; i < col_int; i++){
//						HSSFCell  hssfRowTmp = hssfRow.createCell(columnNum+i);
//						
//						HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, columnNum+i, 0, columnNum+i, (short) 4, 2, (short) 6, 5));
//						// 设置注释内容
//						comment.setString(new HSSFRichTextString(mapKey));
//						// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
//						comment.setAuthor("ecmp");
//						hssfRowTmp.setCellComment(comment);
//						
//						//value
//						//设置相应的值
//						HSSFRichTextString text = new HSSFRichTextString(foreachParam.get(mapKey));
//						hssfRowTmp.setCellValue(text);
//						hssfRowTmp.setCellStyle(mulitCellStyle(sheet.getWorkbook()));
//						sheet.setColumnWidth(hssfRowTmp.getColumnIndex(), mulitCellWidth(hssfRowTmp, text.toString()));
//					}
//					//然后跨行合并
//					sheet.addMergedRegion(new CellRangeAddress(0, (short) 0, columnNum, (short) columnNum + col_int-1));//起始行号，终止行号， 起始列号，终止列号
					
					
					
					//通过id：100001_1查找到对应的cell
					org.dom4j.Node node = helper.getCellNodeById(null, cellstartindexWithParam);
					//然后
					String upForeach = node.valueOf("attribute::foreach");//获取fieldDispName属性
					String upForeachWithParam =  StringHelper.getInstance().matchKhnr(upForeach,"$");
					Map<String,String> upForeachMap = (Map<String, String>) map.get(upForeachWithParam);
					
					
					for(String upKey : upForeachMap.keySet()){
						//找到对应的
						System.out.println(upKey);
						Cell cell = getCellIndexWithComment(sheet,hssfRow, upKey);
						int startCellIndex = cell.getColumnIndex();
						Integer index = startCellIndex;
						Map<String, String> foreachYearMap = foreachParam.get(upKey);
						int yearIndex = 0;
						for(String mapKey : foreachYearMap.keySet()){
							//其中key是comment  value是显示的名称
							for(int i = 0 ;i < col_int;i++){
								HSSFCell hssfCell = hssfRow.createCell(index);
								HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, index, 0, index, (short) 4, 2, (short) 6, 5));
								// 设置注释内容
								comment.setString(new HSSFRichTextString(mapKey));
								// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
								comment.setAuthor("ecmp");
								hssfCell.setCellComment(comment);
								//设置相应的值
								HSSFRichTextString text = new HSSFRichTextString(foreachYearMap.get(mapKey));
								hssfCell.setCellValue(text);
								hssfCell.setCellStyle(mulitCellStyle(sheet.getWorkbook()));
								sheet.setColumnWidth(hssfCell.getColumnIndex(), mulitCellWidth(hssfCell, text.toString()));
								index++;
							}
							//合并
							sheet.addMergedRegion(new CellRangeAddress(hssfRow.getRowNum(), (short) hssfRow.getRowNum(), startCellIndex+yearIndex*col_int, (short) startCellIndex+(yearIndex+1)*col_int - 1));//起始行号，终止行号， 起始列号，终止列号
							yearIndex++;
						}
						
					}
				}else{
					//查找cellIndex_from的值
					cellIndex_from = cellInfo.getCellIndex_from();
					Map<String, String> foreachParam = (Map<String, String>) map.get(foreachKey);
					String cellstartindexWithParam =  StringHelper.getInstance().matchKhnr(cellIndex_from,"#");
					//通过id：100001_1查找到对应的cell
					org.dom4j.Node node = helper.getCellNodeById(null, cellstartindexWithParam);
					//获取node的描述名称
					String fieldDispName = node.valueOf("attribute::fieldDispName");//获取fieldDispName属性
					String searchComment = node.valueOf("attribute::fieldName");//获取fieldName属性
					//如果fieldName是null 则保存的是id：id必须唯一
					if(searchComment == null || "".equals(searchComment)){
						searchComment = node.valueOf("attribute::id");//获取id属性
					}
					//在sheet中开始查找
					Cell cell = getCellIndexWithComment(sheet,hssfRow, searchComment);
					// 找到了后获取cell的开始列
					if (cell != null) {
						createCellForeachToWorkbook(sheet,hssfRow, cellInfo,cell,foreachParam,patriarch);
					}
					//获取总能耗列的专门ID
				}
			}else if(cellRangeAddress_rows != null && !"".equals(cellRangeAddress_rows)){
				//然后合并项目
				Cell upCell = getCellIndexWithReference(sheet,hssfRow, cellRangeAddress_rows.split(":")[0]);
				if(null!=upCell&&null!=upCell.getCellComment()){
					cellInfo.setFieldName(upCell.getCellComment().getString().toString());
					createCellToWorkbook(sheet,hssfRow, cellInfo, patriarch);
					Cell downCell = getCellIndexWithReference(sheet,hssfRow, cellRangeAddress_rows.split(":")[1]);
					//将下方的cell的备注和上方一致
//				downCell.setCellComment(upCell.getCellComment());
					//知道了开始的列号
					sheet.addMergedRegion(new CellRangeAddress(upCell.getRowIndex(), (short) downCell.getRowIndex(), upCell.getColumnIndex(), (short) downCell.getColumnIndex()));//起始行号，终止行号， 起始列号，终止列号
				}
			}else if(cols != null && !"".equals(cols)){
				createCellColsToWorkbook(sheet,hssfRow, cellInfo,map,patriarch);
			}else{
				//开始创建单元格
				createCellToWorkbook(sheet,hssfRow, cellInfo,patriarch);
			}
		}
	}
	/**
	 * 双重循环情况
	 * @param hssfRow
	 * @param cellInfo
	 */
	public static void createCellColsNestToWorkbook(HSSFSheet sheet,HSSFRow hssfRow,CellInfo cellInfo,Map<String, Object> paramMap,Map<String, Map<String, String>> foreachParam,org.dom4j.Node upNode,HSSFPatriarch patriarch,ExportHelper helper){
		//获取
		String curForeach = cellInfo.getForeach();
		String curForeachWithParam =  StringHelper.getInstance().matchKhnr(curForeach,"$");
		Map<String, Map<String, String>> curForeachMap = (Map<String, Map<String, String>>) paramMap.get(curForeachWithParam);
		//获取node的描述名称
		String foreach = upNode.valueOf("attribute::foreach");//获取foreach属性
		//up
		String upForeach =  StringHelper.getInstance().matchKhnr(foreach,"$");
		Map<String, Map<String,String>> upForeachMap = (Map<String, Map<String, String>>) paramMap.get(upForeach);
		for(String rootUpKey : upForeachMap.keySet()){
			Map<String, String> yearUpMap = upForeachMap.get(rootUpKey);
			for(String upYearKey:yearUpMap.keySet()){
				//查询相应的cell
				Cell cell = getCellIndexWithComment(sheet,hssfRow, upYearKey);
				//获取相应的最底层参数
				Map<String, String> curYearElcMap = curForeachMap.get(upYearKey);
				int i = 0 ;
				for(String elcKey : curYearElcMap.keySet()){
					HSSFCell hssfCell = hssfRow.createCell(cell.getColumnIndex()+i);
					//然后设置相应的值
					HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, cell.getColumnIndex()+i, 0, cell.getColumnIndex()+i, (short) 4, 2, (short) 6, 5));
					// 设置注释内容
					comment.setString(new HSSFRichTextString(elcKey));
					// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
					comment.setAuthor("ecmp");
					hssfCell.setCellComment(comment);

					//设置相应的值
					HSSFRichTextString text = new HSSFRichTextString(curYearElcMap.get(elcKey));
					hssfCell.setCellValue(text);
					hssfCell.setCellStyle(mulitCellStyle(sheet.getWorkbook()));
					sheet.setColumnWidth(hssfCell.getColumnIndex(), mulitCellWidth(hssfCell, text.toString()));
					i++;
				}
			}
		}
	}
	/**
	 * 双重循环情况
	 * @param hssfRow
	 * @param cellInfo
	 */
	public static void createCellColsWithForeachNestToWorkbook(HSSFSheet sheet,HSSFRow hssfRow,CellInfo cellInfo,Map<String, Object> paramMap,Map<String, Map<String, String>> foreachParam,HSSFPatriarch patriarch,ExportHelper helper){
		//获取
		String cellIndex_from = cellInfo.getCellIndex_from();
		String cellstartindexWithParam =  StringHelper.getInstance().matchKhnr(cellIndex_from,"#");
		//通过id：100001_1查找到对应的cell
		org.dom4j.Node node = null;
		try {
			node = helper.getCellNodeById(null, cellstartindexWithParam);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//获取node的描述名称
		String foreach = node.valueOf("attribute::foreach");//获取foreach属性
		String level = node.valueOf("attribute::level");//获取level属性
		if(foreach != null && !"".equals(foreach) && (level == null || "".equals(level))){
			String foreachKey = StringHelper.getInstance().matchKhnr(foreach,"$");
			//获取参数值【以数组作为例子】
			Map<String, String> firstForeachParam = (Map<String, String>) paramMap.get(foreachKey);
			//循环查找firstForeachParam在cell中的备注
			for(String paramKey : firstForeachParam.keySet()){
				//此处需要通过paramKey 找到对应在cell中的位置
				Cell cell = getCellIndexWithComment(sheet,hssfRow, paramKey);
				//然后就根据foreachParam创建表格
				//获取迭代循环的子参数
				Map<String, String> curForeachParam = foreachParam.get(paramKey);
				int i = 0 ;
				for(String createKey : curForeachParam.keySet()){
					HSSFCell hssfCell = hssfRow.createCell(hssfRow.getPhysicalNumberOfCells());
					//然后设置相应的值
					HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, cell.getColumnIndex()+i, 0, cell.getColumnIndex()+i, (short) 4, 2, (short) 6, 5));
					// 设置注释内容
					comment.setString(new HSSFRichTextString(createKey));
					// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
					comment.setAuthor("ecmp");
					hssfCell.setCellComment(comment);
					
					//value
					//设置相应的值
					HSSFRichTextString text = new HSSFRichTextString(curForeachParam.get(createKey));
					hssfCell.setCellValue(text);
					hssfCell.setCellStyle(mulitCellStyle(sheet.getWorkbook()));
					sheet.setColumnWidth(hssfCell.getColumnIndex(), mulitCellWidth(hssfCell, text.toString()));
					i++;
				}
			}
		}else if(foreach != null && !"".equals(foreach) && level != null && !"".equals(level)){
			//即存在foreach 有存在level的情况
			org.dom4j.Node upNode = null;
			try {
				upNode = helper.getCellNodeById(null, cellstartindexWithParam);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//然后
			String upForeach = upNode.valueOf("attribute::foreach");//获取fieldDispName属性
			String upForeachWithParam =  StringHelper.getInstance().matchKhnr(upForeach,"$");
			Map<String,Map<String, String>> upForeachMap = (Map<String, Map<String, String>>) paramMap.get(upForeachWithParam);
			/*上一层数据*/
			System.out.println(upForeachMap);
			
		}
	}
	/**
	 * 循环常见cell
	 * @param hssfRow
	 * @param cellInfo
	 */
	public static void createCellColsWithForeachToWorkbook(HSSFSheet sheet,HSSFRow hssfRow,CellInfo cellInfo,Map<String, Object> paramMap,Map<String, String> foreachParam,HSSFPatriarch patriarch){
		//先创建第一个cell  合并四个单元格
		Integer index = sheet.getPhysicalNumberOfRows();
		for(String mapKey : foreachParam.keySet()){
			//然后看是否存在cols 如果存在则表示需要在该行多创建几个cell
			String cols = cellInfo.getCols();
			
			if(cols != null && !"".equals(cols)){
				String colsKey = StringHelper.getInstance().matchKhnr(cols,"$");
				String colsStr = (String) paramMap.get(colsKey);
				int columnNum=hssfRow.getPhysicalNumberOfCells();//获取总列数
				Integer col_int = Integer.valueOf(colsStr);
				for(int i = 0; i < col_int; i++){
					HSSFCell  hssfRowTmp = hssfRow.createCell(columnNum+i);
					
					HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, index, 0, index, (short) 4, 2, (short) 6, 5));
					// 设置注释内容
					comment.setString(new HSSFRichTextString(mapKey));
					// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
					comment.setAuthor("ecmp");
					hssfRowTmp.setCellComment(comment);
					
					//value
					//设置相应的值
					HSSFRichTextString text = new HSSFRichTextString(foreachParam.get(mapKey));
					hssfRowTmp.setCellValue(text);
					hssfRowTmp.setCellStyle(mulitCellStyle(sheet.getWorkbook()));
					sheet.setColumnWidth(hssfRowTmp.getColumnIndex(), mulitCellWidth(hssfRowTmp, text.toString()));
				}
				//然后跨行合并
				sheet.addMergedRegion(new CellRangeAddress(0, (short) 0, columnNum, (short) columnNum + col_int-1));//起始行号，终止行号， 起始列号，终止列号  
			}else{
				HSSFCell hssfCell = hssfRow.createCell(index);
				HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, index, 0, index, (short) 4, 2, (short) 6, 5));
				// 设置注释内容
				comment.setString(new HSSFRichTextString(mapKey));
				// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
				comment.setAuthor("ecmp");
				hssfCell.setCellComment(comment);
				//设置相应的值
				HSSFRichTextString text = new HSSFRichTextString(foreachParam.get(mapKey));
				hssfCell.setCellValue(text);
				hssfCell.setCellStyle(mulitCellStyle(sheet.getWorkbook()));
				sheet.setColumnWidth(hssfCell.getColumnIndex(), mulitCellWidth(hssfCell, text.toString()));
			}
			index++;
		}
	}
	/**
	 * 循环常见cell
	 * @param hssfRow
	 * @param cellInfo
	 */
	public static void createCellForeachToWorkbook(HSSFSheet sheet,HSSFRow hssfRow,CellInfo cellInfo,Cell cell,Map<String, String> map,HSSFPatriarch patriarch){
		 //开始创建
		Integer startCellIndex = cell.getColumnIndex();
		Integer index = startCellIndex;
		for(String mapKey : map.keySet()){
			//其中key是comment  value是显示的名称
			HSSFCell hssfCell = hssfRow.createCell(index);
			HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, index, 0, index, (short) 4, 2, (short) 6, 5));
        	// 设置注释内容
        	comment.setString(new HSSFRichTextString(mapKey));
        	// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
        	comment.setAuthor("ecmp");
			hssfCell.setCellComment(comment);
			//设置相应的值
			HSSFRichTextString text = new HSSFRichTextString(map.get(mapKey));
			hssfCell.setCellValue(text);
			hssfCell.setCellStyle(mulitCellStyle(sheet.getWorkbook()));
			sheet.setColumnWidth(hssfCell.getColumnIndex(), mulitCellWidth(hssfCell, text.toString()));
			index++;
		}
	}
	
	/**
	 * 跨行创建多个cell
	 * @param hssfRow
	 * @param cellInfo
	 */
	public static void createCellColsToWorkbook(HSSFSheet sheet,HSSFRow hssfRow,CellInfo cellInfo,Map<String, Object> map,HSSFPatriarch patriarch){
		String cols = cellInfo.getCols();
		if(cols != null && !"".equals(cols)){
			String colsKey="";
			String colsStr="";
			if((cols.indexOf("$")>-1)){
				colsKey = StringHelper.getInstance().matchKhnr(cols,"$");
				colsStr = (String) map.get(colsKey);
			}else{
				colsStr = cols;
			}
			//获取单元格最
			int columnNum=hssfRow.getPhysicalNumberOfCells();//获取总列数
			Integer col_int = Integer.valueOf(colsStr);
			for(int i = columnNum; i <= columnNum + col_int-1; i++){
				HSSFCell  hssfRowTmp = hssfRow.createCell(i);
				hssfRowTmp.setCellStyle(mulitCellStyle(sheet.getWorkbook()));
				sheet.setColumnWidth(hssfRowTmp.getColumnIndex(), mulitCellWidth(hssfRowTmp, ""));
			}
			try {
				//然后跨行合并
				if(col_int != 1){
					int rowNum=null!=hssfRow?hssfRow.getRowNum():0;
					sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, columnNum, (short) columnNum + col_int-1));//起始行号，终止行号， 起始列号，终止列号  
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, columnNum, 0, columnNum, (short) 4, 2, (short) 6, 5));
        	// 设置注释内容
			//判断如果没有filedName 则是id
			String commentVal = cellInfo.getFieldName();
			if(commentVal == null || "".equals(commentVal)){
				commentVal = cellInfo.getId();
			}
			
        	comment.setString(new HSSFRichTextString(commentVal));
        	// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
        	comment.setAuthor("ecmp");
        	
			HSSFCell hssfCell = hssfRow.getCell(columnNum);
			hssfCell.setCellComment(comment);
			//设置相应的值
			HSSFRichTextString text = new HSSFRichTextString(cellInfo.getFieldDispName());
			hssfCell.setCellValue(text);
			sheet.setColumnWidth(hssfCell.getColumnIndex(), mulitCellWidth(hssfCell, text.toString()));
			hssfCell.setCellStyle(mulitCellStyle(sheet.getWorkbook()));
		}
		
	}
	
	
	
	
	public static void createCellToWorkbook(HSSFSheet sheet,HSSFRow hssfRow,CellInfo cellInfo,HSSFPatriarch patriarch){
		//获取单元格最
		int columnNum=hssfRow.getPhysicalNumberOfCells();//获取总列数
		HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, columnNum, 0, columnNum, (short) 4, 2, (short) 6, 5));
    	// 设置注释内容
		//判断如果没有filedName 则是id
		String commentVal = cellInfo.getFieldName();
		if(commentVal == null || "".equals(commentVal)){
			commentVal = cellInfo.getId();
		}
    	comment.setString(new HSSFRichTextString(commentVal));
    	// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
    	comment.setAuthor("ecmp");
    	
		HSSFCell hssfCell = hssfRow.getCell(columnNum);
		if(hssfCell == null){
			hssfCell = hssfRow.createCell(columnNum);
			hssfCell.setCellStyle(mulitCellStyle(sheet.getWorkbook()));
			sheet.setColumnWidth(hssfCell.getColumnIndex(), mulitCellWidth(hssfCell, ""));
		}
		hssfCell.setCellComment(comment);
		//设置相应的值
		HSSFRichTextString text = new HSSFRichTextString(cellInfo.getFieldDispName());
		hssfCell.setCellValue(text);
		hssfCell.setCellStyle(mulitCellStyle(sheet.getWorkbook()));
		sheet.setColumnWidth(hssfCell.getColumnIndex(), mulitCellWidth(hssfCell, text.toString()));
	}
	
	
	
	//开始遍历
	public static Cell getCellIndexWithReference(HSSFSheet sheet,HSSFRow hssfRow,String reference){
		//遍历工作薄中的所有行,注意该foreach只有Java5或者以上才支持
		for (Row row : sheet) {  
			//遍历行中的所有单元格 Java5+ 才能使用  
			for (Cell cell : row) { 
				//单元格的参照 ,根据行和列确定某一个单元格的位置  
				//获取cell
				CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
				if(reference.equals(cellRef.formatAsString())){
					return cell;
				}
			}
		}
		return null;
	}
	//开始遍历
	public static Cell getCellIndexWithComment(HSSFSheet sheet,HSSFRow hssfRow,String comment){
		//遍历工作薄中的所有行,注意该foreach只有Java5或者以上才支持
		for (Row row : sheet) {  
			//遍历行中的所有单元格 Java5+ 才能使用  
			for (Cell cell : row) { 
				//单元格的参照 ,根据行和列确定某一个单元格的位置  
				//获取cell
//				CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex()); 
//				System.out.println(cellRef.formatAsString());
		           if(cell.getRichStringCellValue() != null && !"".equals(cell.getRichStringCellValue().toString())){
		        	   if(comment.equals(cell.getCellComment().getString().toString())){
		        		   return cell;
		        	   }
		           }
			}
		}
		return null;
	}
	public String trim(String textContent) {
		textContent = textContent.trim();
		while (textContent.startsWith("　")) {// 这里判断是不是全角空格
			textContent = textContent.substring(1, textContent.length()).trim();
		}
		while (textContent.endsWith("　")) {
			textContent = textContent.substring(0, textContent.length() - 1)
					.trim();
		}
		return textContent;
	}
	/**
	 * 主程序
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		ExportHelper helper = ExportHelper.getInstance("utils/plugins/excel/export.xml");
		WorkbookInfo workbookInfo = null;
		try {
			workbookInfo = helper.getXMLByPath("utils/plugins/excel/export.xml", "yntbfx_qsyntbfx_upLevel");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//设置参数情况
		//设置月：1，2，3
		Map<String, String> monthMap = new LinkedHashMap<String, String>();
		monthMap.put("01", "一月");
		monthMap.put("02", "二月");
		monthMap.put("03", "三月");
		//年份
		Map<String, Map<String, String>> yearMap = new LinkedHashMap<String, Map<String, String>>();
		Map<String, String> year01Map = new LinkedHashMap<String, String>();
		year01Map.put("201701", "2017年");
		year01Map.put("201601", "2016年");
		Map<String, Map<String, String>> foreachElcMap = new LinkedHashMap<String, Map<String, String>>();
		Map<String, String> year201701Map = new LinkedHashMap<String, String>();
		year201701Map.put("2017_01_totalUsage", "总能耗");
		year201701Map.put("2017_01_mainUsage", "主设备能耗");
		year201701Map.put("2017_01_airconUsage", "空调设备能耗");
		year201701Map.put("2017_01_otherUsage", "其他设备能耗");
		
		Map<String, String> year201601Map = new LinkedHashMap<String, String>();
		year201601Map.put("2016_01_totalUsage", "总能耗");
		year201601Map.put("2016_01_mainUsage", "主设备能耗");
		year201601Map.put("2016_01_airconUsage", "空调设备能耗");
		year201601Map.put("2016_01_otherUsage", "其他设备能耗");
		
		foreachElcMap.put("201701", year201701Map);
		foreachElcMap.put("201601", year201601Map);
		
		Map<String, String> year02Map = new LinkedHashMap<String, String>();
		year02Map.put("201702", "2017年");
		year02Map.put("201602", "2016年");
		
		Map<String, String> year201702Map = new LinkedHashMap<String, String>();
		year201702Map.put("2017_02_totalUsage", "总能耗");
		year201702Map.put("2017_02_mainUsage", "主设备能耗");
		year201702Map.put("2017_02_airconUsage", "空调设备能耗");
		year201702Map.put("2017_02_otherUsage", "其他设备能耗");
		
		Map<String, String> year201602Map = new LinkedHashMap<String, String>();
		year201602Map.put("2016_02_totalUsage", "总能耗");
		year201602Map.put("2016_02_mainUsage", "主设备能耗");
		year201602Map.put("2016_02_airconUsage", "空调设备能耗");
		year201602Map.put("2016_02_otherUsage", "其他设备能耗");
		
		foreachElcMap.put("201702", year201702Map);
		foreachElcMap.put("201602", year201602Map);
		
		Map<String, String> year03Map = new LinkedHashMap<String, String>();
		year03Map.put("201703", "2017年");
		year03Map.put("201603", "2016年");
		
		Map<String, String> year201703Map = new LinkedHashMap<String, String>();
		year201703Map.put("2017_03_totalUsage", "总能耗");
		year201703Map.put("2017_03_mainUsage", "主设备能耗");
		year201703Map.put("2017_03_airconUsage", "空调设备能耗");
		year201703Map.put("2017_03_otherUsage", "其他设备能耗");
		
		Map<String, String> year201603Map = new LinkedHashMap<String, String>();
		year201603Map.put("2016_03_totalUsage", "总能耗");
		year201603Map.put("2016_03_mainUsage", "主设备能耗");
		year201603Map.put("2016_03_airconUsage", "空调设备能耗");
		year201603Map.put("2016_03_otherUsage", "其他设备能耗");
		
		foreachElcMap.put("201703", year201703Map);
		foreachElcMap.put("201603", year201603Map);
		
		yearMap.put("01", year01Map);
		yearMap.put("02", year02Map);
		yearMap.put("03", year03Map);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("yearLen", "4");//默认情况 
		map.put("monthLen",year01Map.size() * 4 + "");//默认情况
		map.put("monthMap", monthMap);
		map.put("foreachYearMap", yearMap);
		map.put("foreachElcMap", foreachElcMap);
		OutputStream out = new FileOutputStream(new File("C:\\Users\\Administrator\\Desktop\\file\\"+Guid.Instance()+"xxx.xls"));
		exportExcel(workbookInfo,map,helper,out);
		
	}
	public static void rootRun(String[] args) throws Exception {
		ExportHelper helper = ExportHelper.getInstance("utils/plugins/excel/export.xml");
		WorkbookInfo workbookInfo = null;
		try {
			workbookInfo = helper.getXMLByPath("utils/plugins/excel/export.xml", "qsndnhhbfx_txjf");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("yearLen", "4");//默认情况
//		String[] yearsMap = new String[]{"2015年","2016年","2017年"};
		Map<String, String> foreachMap = new LinkedHashMap<String, String>();
		foreachMap.put("2015year", "2015年");
		foreachMap.put("2016year", "2016年");
		foreachMap.put("2017year", "2017年");
		map.put("yearsMap", foreachMap);//默认情况
		Map<String, Map<String, String>> nestForeachMap = new LinkedHashMap<String, Map<String,String>>();
		Map<String, String> foreachNextMap2015 = new LinkedHashMap<String, String>();
		foreachNextMap2015.put("2015year_totalUsage", "总能耗");
		foreachNextMap2015.put("2015year_mainUsage", "主设备能耗");
		foreachNextMap2015.put("2015year_airconUsage", "空调设备能耗");
		foreachNextMap2015.put("2015year_otherUsage", "其他设备能耗");
		nestForeachMap.put("2015year", foreachNextMap2015);
		
		Map<String, String> foreachNextMap2016 = new LinkedHashMap<String, String>();
		foreachNextMap2016.put("2016year_totalUsage", "总能耗");
		foreachNextMap2016.put("2016year_mainUsage", "主设备能耗");
		foreachNextMap2016.put("2016year_airconUsage", "空调设备能耗");
		foreachNextMap2016.put("2016year_otherUsage", "其他设备能耗");
		nestForeachMap.put("2016year", foreachNextMap2016);
		
		Map<String, String> foreachNextMap2017 = new LinkedHashMap<String, String>();
		
		foreachNextMap2017.put("2017year_totalUsage", "总能耗");
		foreachNextMap2017.put("2017year_mainUsage", "主设备能耗");
		foreachNextMap2017.put("2017year_airconUsage", "空调设备能耗");
		foreachNextMap2017.put("2017year_otherUsage", "其他设备能耗");
		nestForeachMap.put("2017year", foreachNextMap2017);
		
		map.put("foreachElcMap", nestForeachMap);//默认情况
		OutputStream out = new FileOutputStream(new File("C:\\Users\\Administrator\\Desktop\\file\\"+Guid.Instance()+"xxx.xls"));
		exportExcel(workbookInfo,map,helper,out);
	}
	//然后设置相应的
}
