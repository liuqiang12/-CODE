package utils.strategy.code.utils;

import com.google.common.collect.Lists;
import org.apache.velocity.VelocityContext;
import utils.plugins.cfg.CfgHelper;
import utils.plugins.xml.xpath.XpathHelper;
import utils.strategy.code.model.*;
import utils.typeHelper.DateHelper;
import utils.typeHelper.StringHelper;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class CodeGenerateFactory {

    private static final Logger log = Logger.getLogger(CodeGenerateFactory.class.getName());
    // 工程路径
    private static String projectPath = getProjectPath();
    // 包路径
    private static String buss_package = CodeResourceUtil.bussiPackage;
    // table_schema
    private static String table_schema = CodeResourceUtil.table_schema;
    // author
    private static String author = CodeResourceUtil.author;

    public static void main(String[] args) {
        try {
            //独立表
//			codeGenerate("asset_attachmentinfo", "");

            //关联表
//			codeGenerate("ln_doc_category", "");
//			codeGenerate("ln_doc_shacatgy_dpt", "");
//			codeGenerate("ln_doc_shacatgy_org", "");
//			codeGenerate("ln_doc_shacatgy_user", "");
//			codeGenerate("ln_doc_sharecategory", "");
//			codeGenerate("sys_ln_menu_permissioninfo", "");
//			codeGenerate("ln_operate_permissioninfo", "");
//			codeGenerate("sys_ln_role_permissioninfo", "");
//			codeGenerate("ln_user_department", "");

//            codeGenerate("sys_user_group", "");
//			codeGenerate("sys_ln_user_usergrp", "");
//			codeGenerate("sys_ln_usergrp_role", "");
//
//			codeGenerate("sys_organization", "");
//			codeGenerate("sys_department", "");
//			codeGenerate("sys_menuinfo", "");
//			codeGenerate("sys_page_operate", "");
//			codeGenerate("sys_permissioninfo", "");
//
//			codeGenerate("sys_operate_log", "");
//			codeGenerate("sys_userlogin", "");
//			codeGenerate("sys_ln_role_permissioninfo", "");
//            codeGenerate("sys_region_tree_closure", "");


//			codeGenerate("rpt_cap_bts", "");

//			codeGenerate("t_doc_share_category", "");
//			codeGenerate("t_document_bsaeinfo", "");
//			codeGenerate("t_document_category", "");
            codeGenerate("APP_USER", "");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * <p/>
     * 代码生成方法
     * <p/>
     *
     * @param entityPackage
     * @throws Exception
     */
    public static void codeGenerate(String tableName, String entityPackage)
            throws Exception {
        StringHelper stringHelper = StringHelper.getInstance();

        String entityFileName = stringHelper.reEscapeName(
                tableName.toLowerCase(), true);

        String className = stringHelper.getStrByUpperFirstChar(entityFileName);

        // 生成的实体类的路径
        String modelPath = entityPackage != null && !"".equals(entityPackage) ? "model\\"
                + entityPackage + "\\" + className + ".java"
                : "model\\" + className + ".java";
        String sqlPath = entityPackage != null && !"".equals(entityPackage) ? "mapper\\"
                + entityPackage + "\\" + className + "Mapper.xml"
                : "mapper\\" + className + "Mapper.xml";
        String mapperPath = entityPackage != null && !"".equals(entityPackage) ? "mapper\\"
                + entityPackage + "\\" + className + "Mapper.java"
                : "mapper\\" + className + "Mapper.java";
        String servicePath = entityPackage != null && !"".equals(entityPackage) ? "service\\"
                + entityPackage + "\\" + className + "Service.java"
                : "service\\" + className + "Service.java";
        String serviceImplPath = entityPackage != null && !"".equals(entityPackage) ? "service\\"
                + entityPackage + "\\impl\\" + className + "ServiceImpl.java"
                : "service\\impl\\" + className + "ServiceImpl.java";
        // model包路径
        String model_path = entityPackage != null && !"".equals(entityPackage) ? "model."
                + entityPackage
                : "model";
        // sql包路径
        String sql_path = entityPackage != null && !"".equals(entityPackage) ? "mapper."
                + entityPackage
                : "mapper";
        // service 接口包路径
        String service_path = entityPackage != null && !"".equals(entityPackage) ? "service."
                + entityPackage
                : "service";
        // java绝对路径
        String srcPath = projectPath + CodeResourceUtil.source_root_package
                + "\\";

        String pckPath = srcPath + CodeResourceUtil.bussiPackageUrl + "\\";
        // 获取表信息
        String propertyPath = "config/jdbc.properties";

        CodeUtils codeUtils = CodeUtils.getInstance();
        XpathHelper xpathHelper = XpathHelper.getInstance();

        CfgHelper cfgHelper = CfgHelper.getInstance().load(propertyPath);
        // 表信息
        List<TableData> list = Lists.newArrayList();
        TableData tableData = new TableData();
        // 列信息
        List<ColumnData> colList = Lists.newArrayList();
        ColumnData columnData = new ColumnData();
        /**** 改造 参数传递采用map形式: ****/
        Map<String, String> map = new HashMap<String, String>();
        map.put("tableName", tableName);
        map.put("table_schema", table_schema);
        /**
         * 查询一张表信息
         */
        String sqlContent_single = xpathHelper.getXMLSqlByPath(cfgHelper.getKeyValue("code_generator_sqlMapper"), "queryTable");
        codeUtils = codeUtils.load(sqlContent_single, propertyPath, cfgHelper.getKeyValue("param_prefix"));
        Map<String, Object> codeMap = codeUtils.queryTable(map);
        EffectClassData effectClassData_param = (EffectClassData) codeMap.get("effectClassData");
        TableData tableData_param = (TableData) codeMap.get("tableData");
        List<EffectModel> entityBeanWithDate = effectClassData_param.getEffectModelDataWithDateList();//实体类中的日期属性
        List<EffectModel> entityBean = effectClassData_param.getEffectModelDataList();//实体类属性
        List<ColumnData> databaseProperty = tableData_param.getColumnDataList();// 数据库属性
        List<ColumnBeanDataVO> entityAndDatabase = effectClassData_param.getColumnBeanDataVOList();// 实体类和数据库属性的综合体【遍历获取主键】
        TablePrimary tablePrimary = codeUtils.getTablePrimary(entityAndDatabase);//主键信息
        String columnToEntity = codeUtils.getColumnToEntity(entityAndDatabase);//列表信息
        String columnInfo = codeUtils.getColumnInfo(entityAndDatabase);//列表信息[数据库列信息]
        String columnInfoWithBatchInsert = codeUtils.getColumnInfoWithBatchInsert(entityAndDatabase);//列表信息[数据库列信息[批量保存有关参数问题]]
        // 相关的列信息

        VelocityContext context = new VelocityContext();
        // 包径路
        context.put("bussPackage", buss_package);
        context.put("entityPackage", entityPackage);
        context.put("tableData", tableData_param);
        context.put("effectClassData", effectClassData_param);
        context.put("effectModelDataList", entityBean);
        context.put("effectModelDataListWithDate", entityBeanWithDate);
        context.put("databaseProperty", databaseProperty);
        context.put("entityAndDatabase", entityAndDatabase);
        context.put("tablePrimary", tablePrimary);

        context.put("className", className);
        context.put("classNameFirstLower", stringHelper.getStrByLowerFirstChar(className));


        context.put("author", author);
        context.put("nowDate", DateHelper.getDateUS(new Date()));
        context.put("model_path", model_path);
        context.put("sql_path", sql_path);
        context.put("service_path", service_path);
        context.put("toStringMethod", effectClassData_param.getToStringValue());
        context.put("columnToEntity", columnToEntity);
        context.put("columnInfo", columnInfo);
        context.put("columnInfoWithBatchInsert", columnInfoWithBatchInsert);
        // class 首字母大写
        CommonPageParser.WriterPage(context, "ModelTemplate.java", pckPath, modelPath);
        // xml mapper
        CommonPageParser.WriterPage(context, "mapperMysqlTemplate.xml", pckPath, sqlPath);
        // mapper java
        CommonPageParser.WriterPage(context, "MapperTemplate.java", pckPath, mapperPath);
        // service 接口层
        CommonPageParser.WriterPage(context, "ServiceTemplate.java", pckPath, servicePath);
        //service实现层
        CommonPageParser.WriterPage(context, "ServiceImplTemplate.java", pckPath, serviceImplPath);
        log.info("----------------------------代码生成完毕---------------------------");
    }

    public static String getProjectPath() {
        String path = System.getProperty("user.dir").replace("\\", "/") + "/";
        return path;
    }
}
