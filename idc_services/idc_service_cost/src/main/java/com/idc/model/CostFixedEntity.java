/*
 * Welcome to use the TableGo Tools.
 * 
 * http://vipbooks.iteye.com
 * http://blog.csdn.net/vipbooks
 * http://www.cnblogs.com/vipbooks
 * 
 * Author:bianj
 * Email:edinsker@163.com
 * Version:4.1.2
 */

package com.idc.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 固定成本(COST_FIXED)
 * 
 * @author bianj
 * @version 1.0.0 2018-02-28
 */
@Entity
@Table(name = "COST_FIXED")
public class CostFixedEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -7145678385854739392L;
    
    /** 固定成本主键 */
    @Id
    @Column(name = "COST_FIXED_ID", unique = true, nullable = false, length = 32)
    private String costFixedId;
    
    /** 房间成本 */
    @Column(name = "COST_FIXED", nullable = true)
    private BigDecimal costFixed;
    
    /** 基础设施成本 */
    @Column(name = "COST_BASE_DEV", nullable = true)
    private BigDecimal costBaseDev;
    
    /** IT设备成本 */
    @Column(name = "COST_IT_DEV", nullable = true)
    private BigDecimal costItDev;
    
    /** 时间单位 */
    @Column(name = "UNIT_TIME", nullable = true, length = 16)
    private String unitTime;
    
    /** 对象编码 */
    @Column(name = "OBJECT_CODE", nullable = true, length = 32)
    private String objectCode;
    
    /** 类别1机房2机架3机楼 */
    @Column(name = "COST_FIXED_TYPE", nullable = true, length = 16)
    private String costFixedType;
    
    /** 平均成本 */
    @Column(name = "COST_FIXED_AVG", nullable = true)
    private BigDecimal costFixedAvg;
    
    /** 楼分摊年限 */
    @Column(name = "COST_SHARE_BUILDING_YEAR", nullable = true, length = 22)
    private Integer costShareBuildingYear;
    
    /** 基础设施分摊年限 */
    @Column(name = "COST_SHARE_BASE_DEV_YEAR", nullable = true, length = 22)
    private Integer costShareBaseDevYear;
    
    /** IT设备分摊年限 */
    @Column(name = "COST_SHARE_IT_DEV_YEAR", nullable = true, length = 22)
    private Integer costShareItDevYear;
    
    /** 分摊机架数量 */
    @Column(name = "COST_SHARE_RACK_COUNT", nullable = true, length = 22)
    private Integer costShareRackCount;
    
    /** 楼成本/月 */
    @Column(name = "COST_BUILDING_MONTH", nullable = true)
    private BigDecimal costBuildingMonth;
    
    /** 基础设施成本/月 */
    @Column(name = "COST_BASE_DEV_MONTH", nullable = true)
    private BigDecimal costBaseDevMonth;
    
    /** IT成本/月 */
    @Column(name = "COST_IT_MONTH", nullable = true)
    private BigDecimal costItMonth;
    
    /** 成本/机架/月 */
    @Column(name = "COST_RACK_MONTH", nullable = true)
    private BigDecimal costRackMonth;
    
    /**
     * 获取固定成本主键
     * 
     * @return 固定成本主键
     */
    public String getCostFixedId() {
        return this.costFixedId;
    }
     
    /**
     * 设置固定成本主键
     * 
     * @param costFixedId
     *          固定成本主键
     */
    public void setCostFixedId(String costFixedId) {
        this.costFixedId = costFixedId;
    }
    
    /**
     * 获取房间成本
     * 
     * @return 房间成本
     */
    public BigDecimal getCostFixed() {
        return this.costFixed;
    }
     
    /**
     * 设置房间成本
     * 
     * @param costFixed
     *          房间成本
     */
    public void setCostFixed(BigDecimal costFixed) {
        this.costFixed = costFixed;
    }
    
    /**
     * 获取基础设施成本
     * 
     * @return 基础设施成本
     */
    public BigDecimal getCostBaseDev() {
        return this.costBaseDev;
    }
     
    /**
     * 设置基础设施成本
     * 
     * @param costBaseDev
     *          基础设施成本
     */
    public void setCostBaseDev(BigDecimal costBaseDev) {
        this.costBaseDev = costBaseDev;
    }
    
    /**
     * 获取IT设备成本
     * 
     * @return IT设备成本
     */
    public BigDecimal getCostItDev() {
        return this.costItDev;
    }
     
    /**
     * 设置IT设备成本
     * 
     * @param costItDev
     *          IT设备成本
     */
    public void setCostItDev(BigDecimal costItDev) {
        this.costItDev = costItDev;
    }
    
    /**
     * 获取时间单位
     * 
     * @return 时间单位
     */
    public String getUnitTime() {
        return this.unitTime;
    }
     
    /**
     * 设置时间单位
     * 
     * @param unitTime
     *          时间单位
     */
    public void setUnitTime(String unitTime) {
        this.unitTime = unitTime;
    }
    
    /**
     * 获取对象编码
     * 
     * @return 对象编码
     */
    public String getObjectCode() {
        return this.objectCode;
    }
     
    /**
     * 设置对象编码
     * 
     * @param objectCode
     *          对象编码
     */
    public void setObjectCode(String objectCode) {
        this.objectCode = objectCode;
    }
    
    /**
     * 获取类别1机房2机架3机楼
     * 
     * @return 类别1机房2机架3机楼
     */
    public String getCostFixedType() {
        return this.costFixedType;
    }
     
    /**
     * 设置类别1机房2机架3机楼
     * 
     * @param costFixedType
     *          类别1机房2机架3机楼
     */
    public void setCostFixedType(String costFixedType) {
        this.costFixedType = costFixedType;
    }
    
    /**
     * 获取平均成本
     * 
     * @return 平均成本
     */
    public BigDecimal getCostFixedAvg() {
        return this.costFixedAvg;
    }
     
    /**
     * 设置平均成本
     * 
     * @param costFixedAvg
     *          平均成本
     */
    public void setCostFixedAvg(BigDecimal costFixedAvg) {
        this.costFixedAvg = costFixedAvg;
    }
    
    /**
     * 获取楼分摊年限
     * 
     * @return 楼分摊年限
     */
    public Integer getCostShareBuildingYear() {
        return this.costShareBuildingYear;
    }
     
    /**
     * 设置楼分摊年限
     * 
     * @param costShareBuildingYear
     *          楼分摊年限
     */
    public void setCostShareBuildingYear(Integer costShareBuildingYear) {
        this.costShareBuildingYear = costShareBuildingYear;
    }
    
    /**
     * 获取基础设施分摊年限
     * 
     * @return 基础设施分摊年限
     */
    public Integer getCostShareBaseDevYear() {
        return this.costShareBaseDevYear;
    }
     
    /**
     * 设置基础设施分摊年限
     * 
     * @param costShareBaseDevYear
     *          基础设施分摊年限
     */
    public void setCostShareBaseDevYear(Integer costShareBaseDevYear) {
        this.costShareBaseDevYear = costShareBaseDevYear;
    }
    
    /**
     * 获取IT设备分摊年限
     * 
     * @return IT设备分摊年限
     */
    public Integer getCostShareItDevYear() {
        return this.costShareItDevYear;
    }
     
    /**
     * 设置IT设备分摊年限
     * 
     * @param costShareItDevYear
     *          IT设备分摊年限
     */
    public void setCostShareItDevYear(Integer costShareItDevYear) {
        this.costShareItDevYear = costShareItDevYear;
    }
    
    /**
     * 获取分摊机架数量
     * 
     * @return 分摊机架数量
     */
    public Integer getCostShareRackCount() {
        return this.costShareRackCount;
    }
     
    /**
     * 设置分摊机架数量
     * 
     * @param costShareRackCount
     *          分摊机架数量
     */
    public void setCostShareRackCount(Integer costShareRackCount) {
        this.costShareRackCount = costShareRackCount;
    }
    
    /**
     * 获取楼成本/月
     * 
     * @return 楼成本/月
     */
    public BigDecimal getCostBuildingMonth() {
        return this.costBuildingMonth;
    }
     
    /**
     * 设置楼成本/月
     * 
     * @param costBuildingMonth
     *          楼成本/月
     */
    public void setCostBuildingMonth(BigDecimal costBuildingMonth) {
        this.costBuildingMonth = costBuildingMonth;
    }
    
    /**
     * 获取基础设施成本/月
     * 
     * @return 基础设施成本/月
     */
    public BigDecimal getCostBaseDevMonth() {
        return this.costBaseDevMonth;
    }
     
    /**
     * 设置基础设施成本/月
     * 
     * @param costBaseDevMonth
     *          基础设施成本/月
     */
    public void setCostBaseDevMonth(BigDecimal costBaseDevMonth) {
        this.costBaseDevMonth = costBaseDevMonth;
    }
    
    /**
     * 获取IT成本/月
     * 
     * @return IT成本/月
     */
    public BigDecimal getCostItMonth() {
        return this.costItMonth;
    }
     
    /**
     * 设置IT成本/月
     * 
     * @param costItMonth
     *          IT成本/月
     */
    public void setCostItMonth(BigDecimal costItMonth) {
        this.costItMonth = costItMonth;
    }
    
    /**
     * 获取成本/机架/月
     * 
     * @return 成本/机架/月
     */
    public BigDecimal getCostRackMonth() {
        return this.costRackMonth;
    }
     
    /**
     * 设置成本/机架/月
     * 
     * @param costRackMonth
     *          成本/机架/月
     */
    public void setCostRackMonth(BigDecimal costRackMonth) {
        this.costRackMonth = costRackMonth;
    }
}