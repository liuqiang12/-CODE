package com.idc.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
/**
 * 业务描述: 导入导出基本配置表
 */
public @Data class BaseExclinfo implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	public static final String tableName = "BASE_EXCLINFO";
	@Id
    @GeneratedValue
    @Column(length=10,name="id",nullable=false,columnDefinition="NUMERIC")
    private Integer id;/****/
    
    @Column(length=60,name="proName",nullable=true,columnDefinition="VARCHAR")
    private String proname;/**显示的属性**/
    
    @Column(length=255,name="jdbcName",nullable=true,columnDefinition="VARCHAR")
    private String jdbcname;/**数据库字段[如果数据库是不区分大小写的话，此参数无用]**/
    
    @Column(length=10,name="jb",nullable=true,columnDefinition="NUMERIC")
    private Integer jb;/**级别顺序**/
    
    @Column(length=60,name="refpro",nullable=true,columnDefinition="VARCHAR")
    private String refpro;/**关联其他表字段**/
    
    @Column(length=255,name="zhName",nullable=true,columnDefinition="VARCHAR")
    private String zhname;/**显示的名称（中文）**/
    
    @Column(length=60,name="tableName",nullable=true,columnDefinition="VARCHAR")
    private String tablename;/**表名**/
    
    @Column(length=125,name="path",nullable=true,columnDefinition="VARCHAR")
    private String path;/**保存的路径**/
    
    @Column(length=125,name="refTable",nullable=true,columnDefinition="VARCHAR")
    private String reftable;/**关联表**/
    
    @Column(length=60,name="curForeign",nullable=true,columnDefinition="VARCHAR")
    private String curforeign;/**本表关联字段**/
    
    @Column(length=60,name="refShow",nullable=true,columnDefinition="VARCHAR")
    private String refshow;/**指向其它的参数字段**/
    
    @Column(length=60,name="curPk",nullable=true,columnDefinition="VARCHAR")
    private String curpk;/**本表主键字段**/
    
    @Column(length=60,name="parentCode",nullable=true,columnDefinition="VARCHAR")
    private String parentcode;/**若为数据字典时，表示父级code**/
    
    @Column(length=19,name="createTime",nullable=true,columnDefinition="TIMESTAMP")
    private java.util.Date createtime;/**创建时间**/

}