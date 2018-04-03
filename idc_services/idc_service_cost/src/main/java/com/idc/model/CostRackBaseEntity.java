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
 * 机架成本表(COST_RACK_BASE)
 * 
 * @author bianj
 * @version 1.0.0 2018-02-28
 */
@Entity
@Table(name = "COST_RACK_BASE")
public class CostRackBaseEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 8542978480345759019L;
    
    /**  */
    @Id
    @Column(name = "COST_RACK_ID", unique = true, nullable = false, length = 32)
    private String costRackId;
    
    /**  */
    @Column(name = "COST_FIXED_ID", nullable = true, length = 32)
    private String costFixedId;
    
    /** 机架编码 */
    @Column(name = "RACK_CODE", nullable = true, length = 32)
    private String rackCode;
    
    /** 机架所属机房 */
    @Column(name = "ROOM_CODE", nullable = true, length = 32)
    private String roomCode;
    
    /** 年，半年，季度，月 */
    @Column(name = "COST_TIME_UNIT", nullable = true, length = 16)
    private String costTimeUnit;
    
    /** 折旧率--固定 */
    @Column(name = "COST_DEPRECIATION", nullable = true)
    private BigDecimal costDepreciation;
    
    /** 维护成本——固定 */
    @Column(name = "COST_MAINTENANCE_FEE", nullable = true)
    private BigDecimal costMaintenanceFee;
    
    /** 机架投资 */
    @Column(name = "COST_INVEST", nullable = true)
    private BigDecimal costInvest;
    
    /** 创建时间 */
    @Column(name = "COST_CREATE_TIME", nullable = true)
    private Date costCreateTime;
    
    /**
     * 获取costRackId
     * 
     * @return costRackId
     */
    public String getCostRackId() {
        return this.costRackId;
    }
     
    /**
     * 设置costRackId
     * 
     * @param costRackId
     *          costRackId
     */
    public void setCostRackId(String costRackId) {
        this.costRackId = costRackId;
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
     * @param costFixedId
     *          costFixedId
     */
    public void setCostFixedId(String costFixedId) {
        this.costFixedId = costFixedId;
    }
    
    /**
     * 获取机架编码
     * 
     * @return 机架编码
     */
    public String getRackCode() {
        return this.rackCode;
    }
     
    /**
     * 设置机架编码
     * 
     * @param rackCode
     *          机架编码
     */
    public void setRackCode(String rackCode) {
        this.rackCode = rackCode;
    }
    
    /**
     * 获取机架所属机房
     * 
     * @return 机架所属机房
     */
    public String getRoomCode() {
        return this.roomCode;
    }
     
    /**
     * 设置机架所属机房
     * 
     * @param roomCode
     *          机架所属机房
     */
    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }
    
    /**
     * 获取年，半年，季度，月
     * 
     * @return 年，半年，季度，月
     */
    public String getCostTimeUnit() {
        return this.costTimeUnit;
    }
     
    /**
     * 设置年，半年，季度，月
     * 
     * @param costTimeUnit
     *          年，半年，季度，月
     */
    public void setCostTimeUnit(String costTimeUnit) {
        this.costTimeUnit = costTimeUnit;
    }
    
    /**
     * 获取折旧率--固定
     * 
     * @return 折旧率--固定
     */
    public BigDecimal getCostDepreciation() {
        return this.costDepreciation;
    }
     
    /**
     * 设置折旧率--固定
     * 
     * @param costDepreciation
     *          折旧率--固定
     */
    public void setCostDepreciation(BigDecimal costDepreciation) {
        this.costDepreciation = costDepreciation;
    }
    
    /**
     * 获取维护成本——固定
     * 
     * @return 维护成本——固定
     */
    public BigDecimal getCostMaintenanceFee() {
        return this.costMaintenanceFee;
    }
     
    /**
     * 设置维护成本——固定
     * 
     * @param costMaintenanceFee
     *          维护成本——固定
     */
    public void setCostMaintenanceFee(BigDecimal costMaintenanceFee) {
        this.costMaintenanceFee = costMaintenanceFee;
    }
    
    /**
     * 获取机架投资
     * 
     * @return 机架投资
     */
    public BigDecimal getCostInvest() {
        return this.costInvest;
    }
     
    /**
     * 设置机架投资
     * 
     * @param costInvest
     *          机架投资
     */
    public void setCostInvest(BigDecimal costInvest) {
        this.costInvest = costInvest;
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
}