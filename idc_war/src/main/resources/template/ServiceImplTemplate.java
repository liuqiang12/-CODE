package ${bussPackage}.${service_path}.impl;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import system.data.inter.DataSource;
import system.data.supper.service.impl.SuperServiceImpl;

import ${bussPackage}.${sql_path}.${className}Mapper;
import ${bussPackage}.${model_path}.${className};
import ${bussPackage}.${service_path}.${className}Service;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>${tableData.tableName}:${tableData.tableComment}<br>
 * <b>作者：${author}</b><br>
 * <b>日期：</b> ${nowDate} <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("${classNameFirstLower}Service")
public class ${className}ServiceImpl extends SuperServiceImpl<${className}, $tablePrimary.entityproType> implements ${className}Service {
	@Autowired
	private ${className}Mapper mapper;
	/**
	 *   Special code can be written here 
	 */
}
