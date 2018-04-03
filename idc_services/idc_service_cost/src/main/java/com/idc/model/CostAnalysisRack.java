package com.idc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 
 */
public class CostAnalysisRack implements Serializable {
    private String costAnalysisRackId;

    private String costRackId;

    private BigDecimal costMaintenance;

    private BigDecimal costRepair;

    private BigDecimal costRackPowerFee;

    private BigDecimal costWork;

    private BigDecimal costWaterRate;

    private BigDecimal costMarketting;

    private BigDecimal costOther;

    private BigDecimal costColligate;

    private String costYear;

    private Date costStartTime;

    private Date costEndTime;

    private Date costCreateTime;

    private BigDecimal bandwidthIncome;

    private BigDecimal soldIncomeFee;

    private BigDecimal costBandwidth;

    private BigDecimal costRockIncome;

    private BigDecimal soldRockFee;

    private BigDecimal avgDepreciationYear;

    private BigDecimal costProfitRate;

    private String costCreateUser;

    private static final long serialVersionUID = 1L;

    public String getCostAnalysisRackId() {
        return costAnalysisRackId;
    }

    public void setCostAnalysisRackId(String costAnalysisRackId) {
        this.costAnalysisRackId = costAnalysisRackId;
    }

    public String getCostRackId() {
        return costRackId;
    }

    public void setCostRackId(String costRackId) {
        this.costRackId = costRackId;
    }

    public BigDecimal getCostMaintenance() {
        return costMaintenance;
    }

    public void setCostMaintenance(BigDecimal costMaintenance) {
        this.costMaintenance = costMaintenance;
    }

    public BigDecimal getCostRepair() {
        return costRepair;
    }

    public void setCostRepair(BigDecimal costRepair) {
        this.costRepair = costRepair;
    }

    public BigDecimal getCostRackPowerFee() {
        return costRackPowerFee;
    }

    public void setCostRackPowerFee(BigDecimal costRackPowerFee) {
        this.costRackPowerFee = costRackPowerFee;
    }

    public BigDecimal getCostWork() {
        return costWork;
    }

    public void setCostWork(BigDecimal costWork) {
        this.costWork = costWork;
    }

    public BigDecimal getCostWaterRate() {
        return costWaterRate;
    }

    public void setCostWaterRate(BigDecimal costWaterRate) {
        this.costWaterRate = costWaterRate;
    }

    public BigDecimal getCostMarketting() {
        return costMarketting;
    }

    public void setCostMarketting(BigDecimal costMarketting) {
        this.costMarketting = costMarketting;
    }

    public BigDecimal getCostOther() {
        return costOther;
    }

    public void setCostOther(BigDecimal costOther) {
        this.costOther = costOther;
    }

    public BigDecimal getCostColligate() {
        return costColligate;
    }

    public void setCostColligate(BigDecimal costColligate) {
        this.costColligate = costColligate;
    }

    public String getCostYear() {
        return costYear;
    }

    public void setCostYear(String costYear) {
        this.costYear = costYear;
    }

    public Date getCostStartTime() {
        return costStartTime;
    }

    public void setCostStartTime(Date costStartTime) {
        this.costStartTime = costStartTime;
    }

    public Date getCostEndTime() {
        return costEndTime;
    }

    public void setCostEndTime(Date costEndTime) {
        this.costEndTime = costEndTime;
    }

    public Date getCostCreateTime() {
        return costCreateTime;
    }

    public void setCostCreateTime(Date costCreateTime) {
        this.costCreateTime = costCreateTime;
    }

    public BigDecimal getBandwidthIncome() {
        return bandwidthIncome;
    }

    public void setBandwidthIncome(BigDecimal bandwidthIncome) {
        this.bandwidthIncome = bandwidthIncome;
    }

    public BigDecimal getSoldIncomeFee() {
        return soldIncomeFee;
    }

    public void setSoldIncomeFee(BigDecimal soldIncomeFee) {
        this.soldIncomeFee = soldIncomeFee;
    }

    public BigDecimal getCostBandwidth() {
        return costBandwidth;
    }

    public void setCostBandwidth(BigDecimal costBandwidth) {
        this.costBandwidth = costBandwidth;
    }

    public BigDecimal getCostRockIncome() {
        return costRockIncome;
    }

    public void setCostRockIncome(BigDecimal costRockIncome) {
        this.costRockIncome = costRockIncome;
    }

    public BigDecimal getSoldRockFee() {
        return soldRockFee;
    }

    public void setSoldRockFee(BigDecimal soldRockFee) {
        this.soldRockFee = soldRockFee;
    }

    public BigDecimal getAvgDepreciationYear() {
        return avgDepreciationYear;
    }

    public void setAvgDepreciationYear(BigDecimal avgDepreciationYear) {
        this.avgDepreciationYear = avgDepreciationYear;
    }

    public BigDecimal getCostProfitRate() {
        return costProfitRate;
    }

    public void setCostProfitRate(BigDecimal costProfitRate) {
        this.costProfitRate = costProfitRate;
    }

    public String getCostCreateUser() {
        return costCreateUser;
    }

    public void setCostCreateUser(String costCreateUser) {
        this.costCreateUser = costCreateUser;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        CostAnalysisRack other = (CostAnalysisRack) that;
        return (this.getCostAnalysisRackId() == null ? other.getCostAnalysisRackId() == null : this.getCostAnalysisRackId().equals(other.getCostAnalysisRackId()))
            && (this.getCostRackId() == null ? other.getCostRackId() == null : this.getCostRackId().equals(other.getCostRackId()))
            && (this.getCostMaintenance() == null ? other.getCostMaintenance() == null : this.getCostMaintenance().equals(other.getCostMaintenance()))
            && (this.getCostRepair() == null ? other.getCostRepair() == null : this.getCostRepair().equals(other.getCostRepair()))
            && (this.getCostRackPowerFee() == null ? other.getCostRackPowerFee() == null : this.getCostRackPowerFee().equals(other.getCostRackPowerFee()))
            && (this.getCostWork() == null ? other.getCostWork() == null : this.getCostWork().equals(other.getCostWork()))
            && (this.getCostWaterRate() == null ? other.getCostWaterRate() == null : this.getCostWaterRate().equals(other.getCostWaterRate()))
            && (this.getCostMarketting() == null ? other.getCostMarketting() == null : this.getCostMarketting().equals(other.getCostMarketting()))
            && (this.getCostOther() == null ? other.getCostOther() == null : this.getCostOther().equals(other.getCostOther()))
            && (this.getCostColligate() == null ? other.getCostColligate() == null : this.getCostColligate().equals(other.getCostColligate()))
            && (this.getCostYear() == null ? other.getCostYear() == null : this.getCostYear().equals(other.getCostYear()))
            && (this.getCostStartTime() == null ? other.getCostStartTime() == null : this.getCostStartTime().equals(other.getCostStartTime()))
            && (this.getCostEndTime() == null ? other.getCostEndTime() == null : this.getCostEndTime().equals(other.getCostEndTime()))
            && (this.getCostCreateTime() == null ? other.getCostCreateTime() == null : this.getCostCreateTime().equals(other.getCostCreateTime()))
            && (this.getBandwidthIncome() == null ? other.getBandwidthIncome() == null : this.getBandwidthIncome().equals(other.getBandwidthIncome()))
            && (this.getSoldIncomeFee() == null ? other.getSoldIncomeFee() == null : this.getSoldIncomeFee().equals(other.getSoldIncomeFee()))
            && (this.getCostBandwidth() == null ? other.getCostBandwidth() == null : this.getCostBandwidth().equals(other.getCostBandwidth()))
            && (this.getCostRockIncome() == null ? other.getCostRockIncome() == null : this.getCostRockIncome().equals(other.getCostRockIncome()))
            && (this.getSoldRockFee() == null ? other.getSoldRockFee() == null : this.getSoldRockFee().equals(other.getSoldRockFee()))
            && (this.getAvgDepreciationYear() == null ? other.getAvgDepreciationYear() == null : this.getAvgDepreciationYear().equals(other.getAvgDepreciationYear()))
            && (this.getCostProfitRate() == null ? other.getCostProfitRate() == null : this.getCostProfitRate().equals(other.getCostProfitRate()))
            && (this.getCostCreateUser() == null ? other.getCostCreateUser() == null : this.getCostCreateUser().equals(other.getCostCreateUser()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCostAnalysisRackId() == null) ? 0 : getCostAnalysisRackId().hashCode());
        result = prime * result + ((getCostRackId() == null) ? 0 : getCostRackId().hashCode());
        result = prime * result + ((getCostMaintenance() == null) ? 0 : getCostMaintenance().hashCode());
        result = prime * result + ((getCostRepair() == null) ? 0 : getCostRepair().hashCode());
        result = prime * result + ((getCostRackPowerFee() == null) ? 0 : getCostRackPowerFee().hashCode());
        result = prime * result + ((getCostWork() == null) ? 0 : getCostWork().hashCode());
        result = prime * result + ((getCostWaterRate() == null) ? 0 : getCostWaterRate().hashCode());
        result = prime * result + ((getCostMarketting() == null) ? 0 : getCostMarketting().hashCode());
        result = prime * result + ((getCostOther() == null) ? 0 : getCostOther().hashCode());
        result = prime * result + ((getCostColligate() == null) ? 0 : getCostColligate().hashCode());
        result = prime * result + ((getCostYear() == null) ? 0 : getCostYear().hashCode());
        result = prime * result + ((getCostStartTime() == null) ? 0 : getCostStartTime().hashCode());
        result = prime * result + ((getCostEndTime() == null) ? 0 : getCostEndTime().hashCode());
        result = prime * result + ((getCostCreateTime() == null) ? 0 : getCostCreateTime().hashCode());
        result = prime * result + ((getBandwidthIncome() == null) ? 0 : getBandwidthIncome().hashCode());
        result = prime * result + ((getSoldIncomeFee() == null) ? 0 : getSoldIncomeFee().hashCode());
        result = prime * result + ((getCostBandwidth() == null) ? 0 : getCostBandwidth().hashCode());
        result = prime * result + ((getCostRockIncome() == null) ? 0 : getCostRockIncome().hashCode());
        result = prime * result + ((getSoldRockFee() == null) ? 0 : getSoldRockFee().hashCode());
        result = prime * result + ((getAvgDepreciationYear() == null) ? 0 : getAvgDepreciationYear().hashCode());
        result = prime * result + ((getCostProfitRate() == null) ? 0 : getCostProfitRate().hashCode());
        result = prime * result + ((getCostCreateUser() == null) ? 0 : getCostCreateUser().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", costAnalysisRackId=").append(costAnalysisRackId);
        sb.append(", costRackId=").append(costRackId);
        sb.append(", costMaintenance=").append(costMaintenance);
        sb.append(", costRepair=").append(costRepair);
        sb.append(", costRackPowerFee=").append(costRackPowerFee);
        sb.append(", costWork=").append(costWork);
        sb.append(", costWaterRate=").append(costWaterRate);
        sb.append(", costMarketting=").append(costMarketting);
        sb.append(", costOther=").append(costOther);
        sb.append(", costColligate=").append(costColligate);
        sb.append(", costYear=").append(costYear);
        sb.append(", costStartTime=").append(costStartTime);
        sb.append(", costEndTime=").append(costEndTime);
        sb.append(", costCreateTime=").append(costCreateTime);
        sb.append(", bandwidthIncome=").append(bandwidthIncome);
        sb.append(", soldIncomeFee=").append(soldIncomeFee);
        sb.append(", costBandwidth=").append(costBandwidth);
        sb.append(", costRockIncome=").append(costRockIncome);
        sb.append(", soldRockFee=").append(soldRockFee);
        sb.append(", avgDepreciationYear=").append(avgDepreciationYear);
        sb.append(", costProfitRate=").append(costProfitRate);
        sb.append(", costCreateUser=").append(costCreateUser);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}