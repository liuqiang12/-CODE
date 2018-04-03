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
 * 机房成本表(COST_ROOM_BASE)
 *
 * @author bianj
 * @version 1.0.0 2018-02-28
 */
@Entity
@Table(name = "COST_ROOM_BASE")
public class CostRoomBaseEntity implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = 7984794287608041550L;

    /**
     * 房间成本ID
     */
    @Id
    @Column(name = "COST_ROOM_ID", unique = true, nullable = false, length = 64)
    private String costRoomId;

    /**  */
    @Column(name = "COST_FIXED_ID", nullable = true, length = 32)
    private String costFixedId;

    /**
     * 折旧摊销
     */
    @Column(name = "COST_DEPRECIATION", nullable = true)
    private BigDecimal costDepreciation;

    /**  */
    @Column(name = "COST_MAINTENANCE", nullable = true)
    private BigDecimal costMaintenance;

    /**
     * 修理费
     */
    @Column(name = "COST_REPAIR", nullable = true)
    private BigDecimal costRepair;

    /**
     * 装修摊销费
     */
    @Column(name = "COST_RENOVATION", nullable = true)
    private BigDecimal costRenovation;

    /**
     * 设备折旧摊销
     */
    @Column(name = "COST_DEV_DEPRECIATION", nullable = true)
    private BigDecimal costDevDepreciation;

    /**
     * 设备修理费
     */
    @Column(name = "COST_DEV_REPAIR", nullable = true)
    private BigDecimal costDevRepair;

    /**  */
    @Column(name = "COST_WATER_RATE", nullable = true)
    private BigDecimal costWaterRate;

    /**
     * 能源费用
     */
    @Column(name = "COST_ENERGY", nullable = true)
    private BigDecimal costEnergy;

    /**
     * 人工成本
     */
    @Column(name = "COST_WORK", nullable = true)
    private BigDecimal costWork;

    /**
     * 网络费用
     */
    @Column(name = "COST_NETWORK", nullable = true)
    private BigDecimal costNetwork;

    /**
     * 时间单位
     */
    @Column(name = "COST_TIME_UNIT", nullable = true, length = 64)
    private String costTimeUnit;

    /**
     * 机房编码
     */
    @Column(name = "ROOM_CODE", nullable = true, length = 64)
    private String roomCode;

    /**
     * 机房名称
     */
    @Column(name = "ROOM_NAME", nullable = true, length = 64)
    private String roomName;

    /**
     * 时间(年/月)
     * 记录时间单位数量
     * 如果时间单位是年，这里记录就是年数。
     * 如果是月就是月数
     * 如果是季度，那就是季度数量
     */
    @Column(name = "COST_YEAR_OR_MONTH", nullable = true)
    private BigDecimal costYearOrMonth;

    /**
     * 所属楼栋
     */
    @Column(name = "BUIDING_CODE", nullable = true, length = 64)
    private String buidingCode;

    /**  */
    @Column(name = "COST_START_TIME", nullable = true)
    private Date costStartTime;

    /**
     * 结束时间
     */
    @Column(name = "COST_END_TIME", nullable = true)
    private Date costEndTime;

    /**
     * 创建时间
     */
    @Column(name = "COST_CREATE_TIME", nullable = true)
    private Date costCreateTime;

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
     * @param costRoomId 房间成本ID
     */
    public void setCostRoomId(String costRoomId) {
        this.costRoomId = costRoomId;
    }

    /**
     * 获取costFixedId
     *
     * @return costFixedId
     */
    public String getCostFixedId() {
        return this.costFixedId;
    }

    /**
     * 设置costFixedId
     *
     * @param costFixedId costFixedId
     */
    public void setCostFixedId(String costFixedId) {
        this.costFixedId = costFixedId;
    }

    /**
     * 获取折旧摊销
     *
     * @return 折旧摊销
     */
    public BigDecimal getCostDepreciation() {
        return this.costDepreciation;
    }

    /**
     * 设置折旧摊销
     *
     * @param costDepreciation 折旧摊销
     */
    public void setCostDepreciation(BigDecimal costDepreciation) {
        this.costDepreciation = costDepreciation;
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
     * @param costMaintenance costMaintenance
     */
    public void setCostMaintenance(BigDecimal costMaintenance) {
        this.costMaintenance = costMaintenance;
    }

    /**
     * 获取修理费
     *
     * @return 修理费
     */
    public BigDecimal getCostRepair() {
        return this.costRepair;
    }

    /**
     * 设置修理费
     *
     * @param costRepair 修理费
     */
    public void setCostRepair(BigDecimal costRepair) {
        this.costRepair = costRepair;
    }

    /**
     * 获取装修摊销费
     *
     * @return 装修摊销费
     */
    public BigDecimal getCostRenovation() {
        return this.costRenovation;
    }

    /**
     * 设置装修摊销费
     *
     * @param costRenovation 装修摊销费
     */
    public void setCostRenovation(BigDecimal costRenovation) {
        this.costRenovation = costRenovation;
    }

    /**
     * 获取设备折旧摊销
     *
     * @return 设备折旧摊销
     */
    public BigDecimal getCostDevDepreciation() {
        return this.costDevDepreciation;
    }

    /**
     * 设置设备折旧摊销
     *
     * @param costDevDepreciation 设备折旧摊销
     */
    public void setCostDevDepreciation(BigDecimal costDevDepreciation) {
        this.costDevDepreciation = costDevDepreciation;
    }

    /**
     * 获取设备修理费
     *
     * @return 设备修理费
     */
    public BigDecimal getCostDevRepair() {
        return this.costDevRepair;
    }

    /**
     * 设置设备修理费
     *
     * @param costDevRepair 设备修理费
     */
    public void setCostDevRepair(BigDecimal costDevRepair) {
        this.costDevRepair = costDevRepair;
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
     * costWater rate
     */
    public void setCostWaterRate(BigDecimal costWaterRate) {
        this.costWaterRate = costWaterRate;
    }

    /**
     * 获取能源费用
     *
     * @return 能源费用
     */
    public BigDecimal getCostEnergy() {
        return this.costEnergy;
    }

    /**
     * 设置能源费用
     *
     * @param costEnergy 能源费用
     */
    public void setCostEnergy(BigDecimal costEnergy) {
        this.costEnergy = costEnergy;
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
     * @param costWork 人工成本
     */
    public void setCostWork(BigDecimal costWork) {
        this.costWork = costWork;
    }

    /**
     * 获取网络费用
     *
     * @return 网络费用
     */
    public BigDecimal getCostNetwork() {
        return this.costNetwork;
    }

    /**
     * 设置网络费用
     *
     * @param costNetwork 网络费用
     */
    public void setCostNetwork(BigDecimal costNetwork) {
        this.costNetwork = costNetwork;
    }

    /**
     * 获取时间单位
     *
     * @return 时间单位
     */
    public String getCostTimeUnit() {
        return this.costTimeUnit;
    }

    /**
     * 设置时间单位
     *
     * @param costTimeUnit 时间单位
     */
    public void setCostTimeUnit(String costTimeUnit) {
        this.costTimeUnit = costTimeUnit;
    }

    /**
     * 获取机房编码
     *
     * @return 机房编码
     */
    public String getRoomCode() {
        return this.roomCode;
    }

    /**
     * 设置机房编码
     *
     * @param roomCode 机房编码
     */
    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    /**
     * 获取机房名称
     *
     * @return 机房名称
     */
    public String getRoomName() {
        return this.roomName;
    }

    /**
     * 设置机房名称
     *
     * @param roomName 机房名称
     */
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    /**
     * 获取时间(年/月)
     * 记录时间单位数量
     * 如果时间单位是年，这里记录就是年数。
     * 如果是月就是月数
     * 如果是季度，那就是季度数量
     *
     * @return 时间(年/月)
     * 记录时间单位数量
     * 如果时间单位是年
     */
    public BigDecimal getCostYearOrMonth() {
        return this.costYearOrMonth;
    }

    /**
     * 设置时间(年/月)
     * 记录时间单位数量
     * 如果时间单位是年，这里记录就是年数。
     * 如果是月就是月数
     * 如果是季度，那就是季度数量
     *
     * @param costYearOrMonth 时间(年/月)
     *                        记录时间单位数量
     *                        如果时间单位是年，这里记录就是年数。
     *                        如果是月就是月数
     *                        如果是季度，那就是季度数量
     */
    public void setCostYearOrMonth(BigDecimal costYearOrMonth) {
        this.costYearOrMonth = costYearOrMonth;
    }

    /**
     * 获取所属楼栋
     *
     * @return 所属楼栋
     */
    public String getBuidingCode() {
        return this.buidingCode;
    }

    /**
     * 设置所属楼栋
     *
     * @param buidingCode 所属楼栋
     */
    public void setBuidingCode(String buidingCode) {
        this.buidingCode = buidingCode;
    }

    /**
     * 获取costStartTime
     *
     * @return costStartTime
     */
    public Date getCostStartTime() {
        return this.costStartTime;
    }

    /**
     * 设置costStartTime
     *
     * @param costStartTime costStartTime
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
     * @param costEndTime 结束时间
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
     * @param costCreateTime 创建时间
     */
    public void setCostCreateTime(Date costCreateTime) {
        this.costCreateTime = costCreateTime;
    }
}