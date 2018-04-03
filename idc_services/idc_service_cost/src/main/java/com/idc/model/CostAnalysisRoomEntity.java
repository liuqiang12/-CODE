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
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 机房成本分析表(COST_ANALYSIS_ROOM)
 * 
 * @author bianj
 * @version 1.0.0 2018-02-28
 */
@Entity
@Table(name = "COST_ANALYSIS_ROOM")
public class CostAnalysisRoomEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 6771517156570414864L;
    
    /** 房间成本ID */
    @Column(name = "COST_ROOM_ID", nullable = false, length = 64)
    private String costRoomId;
    
    /** 机房成本分析表ID */
    @Id
    @Column(name = "COST_ANALYSIS_ROOM_ID", unique = true, nullable = false, length = 64)
    private String costAnalysisRoomId;
    
    /** 托管收入 */
    @Column(name = "COST_INCOME", nullable = true)
    private BigDecimal costIncome;
    
    /** 带宽收入 */
    @Column(name = "COST_BANDWIDTH_INCOME", nullable = true)
    private BigDecimal costBandwidthIncome;
    
    /** 其他收入 */
    @Column(name = "COST_OTHER_INCOME", nullable = true)
    private BigDecimal costOtherIncome;
    
    /** 经营利润 */
    @Column(name = "COST_OPERATING_PROFIT", nullable = true)
    private BigDecimal costOperatingProfit;
    
    /** 收入利润 */
    @Column(name = "COST_INCOME_PROFIT", nullable = true)
    private BigDecimal costIncomeProfit;
    
    /** 利润环比增长率 */
    @Column(name = "COST_RING_GROWTH", nullable = true)
    private BigDecimal costRingGrowth;
    
    /** 能源费用占比 */
    @Column(name = "COST_ENERGY_USE_RATE", nullable = true)
    private BigDecimal costEnergyUseRate;
    
    /** 机架利用率 */
    @Column(name = "COST_FRAME_USE_RATE", nullable = true)
    private BigDecimal costFrameUseRate;
    
    /** 电量匹配率 */
    @Column(name = "COST_ELECTRIC_MATCHING_RATE", nullable = true)
    private BigDecimal costElectricMatchingRate;
    
    /** 创建时间 */
    @Column(name = "COST_CREATE_TIME", nullable = true)
    private Date costCreateTime;
    
    /** 开始时间 */
    @Column(name = "COST_START_TIME", nullable = true)
    private Date costStartTime;
    
    /** 结束时间 */
    @Column(name = "COST_END_TIME", nullable = true)
    private Date costEndTime;
    
    /** 创建人 */
    @Column(name = "COST_CREATE_USER", nullable = true, length = 64)
    private String costCreateUser;
    
    /**
     * 获取房间成本ID
     * 
     * @return 房间成本ID
     */
    public String getCostRoomId() {
        return this.costRoomId;
    }
     
    /**
     * 设置房间成本ID
     * 
     * @param costRoomId
     *          房间成本ID
     */
    public void setCostRoomId(String costRoomId) {
        this.costRoomId = costRoomId;
    }
    
    /**
     * 获取机房成本分析表ID
     * 
     * @return 机房成本分析表ID
     */
    public String getCostAnalysisRoomId() {
        return this.costAnalysisRoomId;
    }
     
    /**
     * 设置机房成本分析表ID
     * 
     * @param costAnalysisRoomId
     *          机房成本分析表ID
     */
    public void setCostAnalysisRoomId(String costAnalysisRoomId) {
        this.costAnalysisRoomId = costAnalysisRoomId;
    }
    
    /**
     * 获取托管收入
     * 
     * @return 托管收入
     */
    public BigDecimal getCostIncome() {
        return this.costIncome;
    }
     
    /**
     * 设置托管收入
     * 
     * @param costIncome
     *          托管收入
     */
    public void setCostIncome(BigDecimal costIncome) {
        this.costIncome = costIncome;
    }
    
    /**
     * 获取带宽收入
     * 
     * @return 带宽收入
     */
    public BigDecimal getCostBandwidthIncome() {
        return this.costBandwidthIncome;
    }
     
    /**
     * 设置带宽收入
     * 
     * @param costBandwidthIncome
     *          带宽收入
     */
    public void setCostBandwidthIncome(BigDecimal costBandwidthIncome) {
        this.costBandwidthIncome = costBandwidthIncome;
    }
    
    /**
     * 获取其他收入
     * 
     * @return 其他收入
     */
    public BigDecimal getCostOtherIncome() {
        return this.costOtherIncome;
    }
     
    /**
     * 设置其他收入
     * 
     * @param costOtherIncome
     *          其他收入
     */
    public void setCostOtherIncome(BigDecimal costOtherIncome) {
        this.costOtherIncome = costOtherIncome;
    }
    
    /**
     * 获取经营利润
     * 
     * @return 经营利润
     */
    public BigDecimal getCostOperatingProfit() {
        return this.costOperatingProfit;
    }
     
    /**
     * 设置经营利润
     * 
     * @param costOperatingProfit
     *          经营利润
     */
    public void setCostOperatingProfit(BigDecimal costOperatingProfit) {
        this.costOperatingProfit = costOperatingProfit;
    }
    
    /**
     * 获取收入利润
     * 
     * @return 收入利润
     */
    public BigDecimal getCostIncomeProfit() {
        return this.costIncomeProfit;
    }
     
    /**
     * 设置收入利润
     * 
     * @param costIncomeProfit
     *          收入利润
     */
    public void setCostIncomeProfit(BigDecimal costIncomeProfit) {
        this.costIncomeProfit = costIncomeProfit;
    }
    
    /**
     * 获取利润环比增长率
     * 
     * @return 利润环比增长率
     */
    public BigDecimal getCostRingGrowth() {
        return this.costRingGrowth;
    }
     
    /**
     * 设置利润环比增长率
     * 
     * @param costRingGrowth
     *          利润环比增长率
     */
    public void setCostRingGrowth(BigDecimal costRingGrowth) {
        this.costRingGrowth = costRingGrowth;
    }
    
    /**
     * 获取能源费用占比
     * 
     * @return 能源费用占比
     */
    public BigDecimal getCostEnergyUseRate() {
        return this.costEnergyUseRate;
    }
     
    /**
     * 设置能源费用占比
     * 
     * @param costEnergyUseRate
     *          能源费用占比
     */
    public void setCostEnergyUseRate(BigDecimal costEnergyUseRate) {
        this.costEnergyUseRate = costEnergyUseRate;
    }
    
    /**
     * 获取机架利用率
     * 
     * @return 机架利用率
     */
    public BigDecimal getCostFrameUseRate() {
        return this.costFrameUseRate;
    }
     
    /**
     * 设置机架利用率
     * 
     * @param costFrameUseRate
     *          机架利用率
     */
    public void setCostFrameUseRate(BigDecimal costFrameUseRate) {
        this.costFrameUseRate = costFrameUseRate;
    }
    
    /**
     * 获取电量匹配率
     * 
     * @return 电量匹配率
     */
    public BigDecimal getCostElectricMatchingRate() {
        return this.costElectricMatchingRate;
    }
     
    /**
     * 设置电量匹配率
     * 
     * @param costElectricMatchingRate
     *          电量匹配率
     */
    public void setCostElectricMatchingRate(BigDecimal costElectricMatchingRate) {
        this.costElectricMatchingRate = costElectricMatchingRate;
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