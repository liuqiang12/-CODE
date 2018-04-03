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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 机架成本分析(COST_ANALYSIS_RACK)
 * 
 * @author bianj
 * @version 1.0.0 2018-02-28
 */
@Entity
@Table(name = "COST_ANALYSIS_RACK")
public class CostAnalysisRackEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -7465461213573918233L;
    
    /** 主键ID */
    @Id
    @Column(name = "COST_ANALYSIS_RACK_ID", unique = true, nullable = false, length = 32)
    private String costAnalysisRackId;
    
    /** 机架成本ID */
    @Column(name = "COST_RACK_ID", nullable = true, length = 32)
    private String costRackId;
    
    /**  */
    @Column(name = "COST_MAINTENANCE", nullable = true)
    private BigDecimal costMaintenance;
    
    /**  */
    @Column(name = "COST_REPAIR", nullable = true)
    private BigDecimal costRepair;
    
    /** 机架电费 */
    @Column(name = "COST_RACK_POWER_FEE", nullable = true)
    private BigDecimal costRackPowerFee;
    
    /** 人工成本 */
    @Column(name = "COST_WORK", nullable = true)
    private BigDecimal costWork;
    
    /**  */
    @Column(name = "COST_WATER_RATE", nullable = true)
    private BigDecimal costWaterRate;
    
    /** 营销成本 */
    @Column(name = "COST_MARKETTING", nullable = true)
    private BigDecimal costMarketting;
    
    /** 其他 */
    @Column(name = "COST_OTHER", nullable = true)
    private BigDecimal costOther;
    
    /** 综合成本 */
    @Column(name = "COST_COLLIGATE", nullable = true)
    private BigDecimal costColligate;
    
    /** 年度 */
    @Column(name = "COST_YEAR", nullable = true, length = 4)
    private String costYear;
    
    /** 开始时间 */
    @Column(name = "COST_START_TIME", nullable = true)
    private Date costStartTime;
    
    /** 结束时间 */
    @Column(name = "COST_END_TIME", nullable = true)
    private Date costEndTime;
    
    /** 创建时间 */
    @Column(name = "COST_CREATE_TIME", nullable = true)
    private Date costCreateTime;
    
    /** 带宽收入 */
    @Column(name = "BANDWIDTH_INCOME", nullable = true)
    private BigDecimal bandwidthIncome;
    
    /** 已售收入 */
    @Column(name = "SOLD_INCOME_FEE", nullable = true)
    private BigDecimal soldIncomeFee;
    
    /** 带宽成本（万/年） */
    @Column(name = "COST_BANDWIDTH", nullable = true)
    private BigDecimal costBandwidth;
    
    /** 机架收入 */
    @Column(name = "COST_ROCK_INCOME", nullable = true)
    private BigDecimal costRockIncome;
    
    /** 已售机架 */
    @Column(name = "SOLD_ROCK_FEE", nullable = true)
    private BigDecimal soldRockFee;
    
    /** 平均折旧年限 */
    @Column(name = "AVG_DEPRECIATION_YEAR", nullable = true)
    private BigDecimal avgDepreciationYear;
    
    /** 利润率 */
    @Column(name = "COST_PROFIT_RATE", nullable = true)
    private BigDecimal costProfitRate;
    
    /** 创建人 */
    @Column(name = "COST_CREATE_USER", nullable = true, length = 64)
    private String costCreateUser;
    
    /**
     * 获取主键ID
     * 
     * @return 主键ID
     */
    public String getCostAnalysisRackId() {
        return this.costAnalysisRackId;
    }
     
    /**
     * 设置主键ID
     * 
     * @param costAnalysisRackId
     *          主键ID
     */
    public void setCostAnalysisRackId(String costAnalysisRackId) {
        this.costAnalysisRackId = costAnalysisRackId;
    }
    
    /**
     * 获取机架成本ID
     * 
     * @return 机架成本ID
     */
    public String getCostRackId() {
        return this.costRackId;
    }
     
    /**
     * 设置机架成本ID
     * 
     * @param costRackId
     *          机架成本ID
     */
    public void setCostRackId(String costRackId) {
        this.costRackId = costRackId;
    }
    
    /**
     * 获取costMaintenance
     * 
     * @return costMaintenance
     */
    public BigDecimal getCostMaintenance() {
        return this.costMaintenance;
    }
     
    /**
     * 设置costMaintenance
     * 
     * @param costMaintenance
     *          costMaintenance
     */
    public void setCostMaintenance(BigDecimal costMaintenance) {
        this.costMaintenance = costMaintenance;
    }
    
    /**
     * 获取costRepair
     * 
     * @return costRepair
     */
    public BigDecimal getCostRepair() {
        return this.costRepair;
    }
     
    /**
     * 设置costRepair
     * 
     * @param costRepair
     *          costRepair
     */
    public void setCostRepair(BigDecimal costRepair) {
        this.costRepair = costRepair;
    }
    
    /**
     * 获取机架电费
     * 
     * @return 机架电费
     */
    public BigDecimal getCostRackPowerFee() {
        return this.costRackPowerFee;
    }
     
    /**
     * 设置机架电费
     * 
     * @param costRackPowerFee
     *          机架电费
     */
    public void setCostRackPowerFee(BigDecimal costRackPowerFee) {
        this.costRackPowerFee = costRackPowerFee;
    }
    
    /**
     * 获取人工成本
     * 
     * @return 人工成本
     */
    public BigDecimal getCostWork() {
        return this.costWork;
    }
     
    /**
     * 设置人工成本
     * 
     * @param costWork
     *          人工成本
     */
    public void setCostWork(BigDecimal costWork) {
        this.costWork = costWork;
    }
    
    /**
     * 获取costWater rate
     * 
     * @return costWater rate
     */
    public BigDecimal getCostWaterRate() {
        return this.costWaterRate;
    }
     
    /**
     * 设置costWater rate
     * 
     * @param costWaterRate
     *          costWater rate
     */
    public void setCostWaterRate(BigDecimal costWaterRate) {
        this.costWaterRate = costWaterRate;
    }
    
    /**
     * 获取营销成本
     * 
     * @return 营销成本
     */
    public BigDecimal getCostMarketting() {
        return this.costMarketting;
    }
     
    /**
     * 设置营销成本
     * 
     * @param costMarketting
     *          营销成本
     */
    public void setCostMarketting(BigDecimal costMarketting) {
        this.costMarketting = costMarketting;
    }
    
    /**
     * 获取其他
     * 
     * @return 其他
     */
    public BigDecimal getCostOther() {
        return this.costOther;
    }
     
    /**
     * 设置其他
     * 
     * @param costOther
     *          其他
     */
    public void setCostOther(BigDecimal costOther) {
        this.costOther = costOther;
    }
    
    /**
     * 获取综合成本
     * 
     * @return 综合成本
     */
    public BigDecimal getCostColligate() {
        return this.costColligate;
    }
     
    /**
     * 设置综合成本
     * 
     * @param costColligate
     *          综合成本
     */
    public void setCostColligate(BigDecimal costColligate) {
        this.costColligate = costColligate;
    }
    
    /**
     * 获取年度
     * 
     * @return 年度
     */
    public String getCostYear() {
        return this.costYear;
    }
     
    /**
     * 设置年度
     * 
     * @param costYear
     *          年度
     */
    public void setCostYear(String costYear) {
        this.costYear = costYear;
    }
    
    /**
     * 获取开始时间
     * 
     * @return 开始时间
     */
    public Date getCostStartTime() {
        return this.costStartTime;
    }
     
    /**
     * 设置开始时间
     * 
     * @param costStartTime
     *          开始时间
     */
    public void setCostStartTime(Date costStartTime) {
        this.costStartTime = costStartTime;
    }
    
    /**
     * 获取结束时间
     * 
     * @return 结束时间
     */
    public Date getCostEndTime() {
        return this.costEndTime;
    }
     
    /**
     * 设置结束时间
     * 
     * @param costEndTime
     *          结束时间
     */
    public void setCostEndTime(Date costEndTime) {
        this.costEndTime = costEndTime;
    }
    
    /**
     * 获取创建时间
     * 
     * @return 创建时间
     */
    public Date getCostCreateTime() {
        return this.costCreateTime;
    }
     
    /**
     * 设置创建时间
     * 
     * @param costCreateTime
     *          创建时间
     */
    public void setCostCreateTime(Date costCreateTime) {
        this.costCreateTime = costCreateTime;
    }
    
    /**
     * 获取带宽收入
     * 
     * @return 带宽收入
     */
    public BigDecimal getBandwidthIncome() {
        return this.bandwidthIncome;
    }
     
    /**
     * 设置带宽收入
     * 
     * @param bandwidthIncome
     *          带宽收入
     */
    public void setBandwidthIncome(BigDecimal bandwidthIncome) {
        this.bandwidthIncome = bandwidthIncome;
    }
    
    /**
     * 获取已售收入
     * 
     * @return 已售收入
     */
    public BigDecimal getSoldIncomeFee() {
        return this.soldIncomeFee;
    }
     
    /**
     * 设置已售收入
     * 
     * @param soldIncomeFee
     *          已售收入
     */
    public void setSoldIncomeFee(BigDecimal soldIncomeFee) {
        this.soldIncomeFee = soldIncomeFee;
    }
    
    /**
     * 获取带宽成本（万/年）
     * 
     * @return 带宽成本（万/年）
     */
    public BigDecimal getCostBandwidth() {
        return this.costBandwidth;
    }
     
    /**
     * 设置带宽成本（万/年）
     * 
     * @param costBandwidth
     *          带宽成本（万/年）
     */
    public void setCostBandwidth(BigDecimal costBandwidth) {
        this.costBandwidth = costBandwidth;
    }
    
    /**
     * 获取机架收入
     * 
     * @return 机架收入
     */
    public BigDecimal getCostRockIncome() {
        return this.costRockIncome;
    }
     
    /**
     * 设置机架收入
     * 
     * @param costRockIncome
     *          机架收入
     */
    public void setCostRockIncome(BigDecimal costRockIncome) {
        this.costRockIncome = costRockIncome;
    }
    
    /**
     * 获取已售机架
     * 
     * @return 已售机架
     */
    public BigDecimal getSoldRockFee() {
        return this.soldRockFee;
    }
     
    /**
     * 设置已售机架
     * 
     * @param soldRockFee
     *          已售机架
     */
    public void setSoldRockFee(BigDecimal soldRockFee) {
        this.soldRockFee = soldRockFee;
    }
    
    /**
     * 获取平均折旧年限
     * 
     * @return 平均折旧年限
     */
    public BigDecimal getAvgDepreciationYear() {
        return this.avgDepreciationYear;
    }
     
    /**
     * 设置平均折旧年限
     * 
     * @param avgDepreciationYear
     *          平均折旧年限
     */
    public void setAvgDepreciationYear(BigDecimal avgDepreciationYear) {
        this.avgDepreciationYear = avgDepreciationYear;
    }
    
    /**
     * 获取利润率
     * 
     * @return 利润率
     */
    public BigDecimal getCostProfitRate() {
        return this.costProfitRate;
    }
     
    /**
     * 设置利润率
     * 
     * @param costProfitRate
     *          利润率
     */
    public void setCostProfitRate(BigDecimal costProfitRate) {
        this.costProfitRate = costProfitRate;
    }
    
    /**
     * 获取创建人
     * 
     * @return 创建人
     */
    public String getCostCreateUser() {
        return this.costCreateUser;
    }
     
    /**
     * 设置创建人
     * 
     * @param costCreateUser
     *          创建人
     */
    public void setCostCreateUser(String costCreateUser) {
        this.costCreateUser = costCreateUser;
    }
}