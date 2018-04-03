package com.idc.action;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/12.
 */
public class ImportExcelUtils {
    private final static String excel2013L = ".xls"; //2003-版Excel
    private final static String excel2017L = ".xlsx"; //2007+版Excel

    public static List<List<Object>> getDataListByExcel(InputStream in, String fileName) throws Exception{
        List<List<Object>>  dataList = null;
        //创建Excel工作簿
        Workbook workbook = getWorkbook(in, fileName);
        if(workbook == null){
            throw new Exception("创建Excel工作簿为空！");
        }
        //Excel的sheet页
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;
        dataList = new ArrayList<List<Object>>();
        //遍历Excel中所有sheet-----------i
        for(int i=0;i<workbook.getNumberOfSheets();i++){
            sheet = workbook.getSheetAt(i);
            if(sheet == null){
                continue;
            }
            //遍历当前sheet中的所有行------------j
            for(int j=sheet.getFirstRowNum();j<=sheet.getLastRowNum();j++){
                row = sheet.getRow(j);
                if(row == null || row.getFirstCellNum() == j){
                    continue;
                }
                //遍历所有的列
                List<Object> li = new ArrayList<Object>();
                for(int k = row.getFirstCellNum();k<row.getLastCellNum();k++){
                    cell = row.getCell(k);
                    li.add(getCellValue(cell));
                }
                //将每条数据插入到集合中
                dataList.add(li);
            }
        }
        workbook.close();
        return dataList;
    }
    //根据文件后缀名自动匹配Excel版本
    public static Workbook getWorkbook(InputStream in,String fileName) throws Exception{
        Workbook wb = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if(fileType.equals(excel2013L)){
            wb = new HSSFWorkbook(in);
        }else if(fileType.equals(excel2017L)){
            wb = new XSSFWorkbook(in);
        }else{
            throw new Exception("传入文件格式有误！");
        }
        return wb;
    }
    //格式化表格中数据
    public static Object getCellValue(Cell cell){
        Object object = null;
        DecimalFormat df = new DecimalFormat("0"); //格式化number String字符
        DecimalFormat df1 = new DecimalFormat("0.00");//格式化数字
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");//日期格式化
        switch(cell.getCellType()){
            case Cell.CELL_TYPE_STRING:
                object = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if("General".equals(cell.getCellStyle().getDataFormatString())){
                    object = df.format(cell.getNumericCellValue());
                }else if(HSSFDateUtil.isCellDateFormatted(cell)){
                    object = sdf.format(cell.getDateCellValue());
                }else{
                    object = df1.format(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                object = cell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_BLANK:
                object = "";
                break;
            default:
                break;
        }
        return object;
    }
}
