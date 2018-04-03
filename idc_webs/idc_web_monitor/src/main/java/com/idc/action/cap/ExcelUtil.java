package com.idc.action.cap;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.export.ExcelExportServer;
import org.jeecgframework.poi.excel.export.template.ReportUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class ExcelUtil {
    private Map<String, Object> getMap(List<User> data) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(NormalExcelConstants.CLASS, User.class);
        map.put(NormalExcelConstants.FILE_NAME, "业务周报");
        ExportParams exportParams = new ExportParams("周报", "zhoubao");
        map.put(NormalExcelConstants.PARAMS, exportParams);
        map.put(NormalExcelConstants.DATA_LIST, data);
        return map;
    }


    public Workbook ExportToXls(List<Map<String, Object>> map) {
        Workbook workbook = new XSSFWorkbook();
        for (Map<String, Object> objectMap : map) {
            ExcelExportServer es = new ExcelExportServer();
            ExportParams exportParams = (ExportParams) objectMap.get(NormalExcelConstants.PARAMS);
            Class<?> entry = (Class<?>) objectMap.get(NormalExcelConstants.CLASS);
            Collection data = (Collection) objectMap.get(NormalExcelConstants.DATA_LIST);
            es.createSheet(workbook, exportParams, entry, data);
        }

        return workbook;
    }

    public static void main(String[] args) {
        ExcelUtil ex = new ExcelUtil();
        Map<String, Object> map = ex.getMap(ex.getList());
        Map<String, Object> map1 = ex.getMap(ex.getList());
        List<Map<String, Object>> l = new ArrayList<>();
        l.add(map);
        l.add(map1);
        TemplateExportParams templateExportParams = new TemplateExportParams(
                "/com/idc/action/cap/" + "aaa" + ".xls", true);
//        Workbook workbook = new ReportUtil().createExcleByTemplate(templateExportParams,sheetMap);
        Workbook sb = ex.ExportToXls(l);
        try {
            sb.write(new FileOutputStream(new File("d:/test.xlxs")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<User> getList() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setId(i);
            user.setName("name"+i);
            users.add(user);
        }
        return users;
    }


    public class User {

        @Excel(name="NAME")
        private int id;
        @Excel(name="NAME" )
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}