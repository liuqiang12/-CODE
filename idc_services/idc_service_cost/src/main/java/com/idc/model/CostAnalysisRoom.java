package com.idc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 
 */
public class CostAnalysisRoom implements Serializable {
    private String costAnalysisRoomId;

    private String costRoomId;

    private BigDecimal costIncome;

    private BigDecimal costBandwidthIncome;

    private BigDecimal costOtherIncome;

    private BigDecimal costOperatingProfit;

    private BigDecimal costIncomeProfit;

    private BigDecimal costRingGrowth;

    private BigDecimal costEnergyUseRate;

    private BigDecimal costFrameUseRate;

    private BigDecimal costElectricMatchingRate;

    private Date costCreateTime;

    private Date costStartTime;

    private Date costEndTime;

    private String costCreateUser;

    private static final long serialVersionUID = 1L;

    public String getCostAnalysisRoomId() {
        return costAnalysisRoomId;
    }

    public void setCostAnalysisRoomId(String costAnalysisRoomId) {
        this.costAnalysisRoomId = costAnalysisRoomId;
    }

    public String getCostRoomId() {
        return costRoomId;
    }

    public void setCostRoomId(String costRoomId) {
        this.costRoomId = costRoomId;
    }

    public BigDecimal getCostIncome() {
        return costIncome;
    }

    public void setCostIncome(BigDecimal costIncome) {
        this.costIncome = costIncome;
    }

    public BigDecimal getCostBandwidthIncome() {
        return costBandwidthIncome;
    }

    public void setCostBandwidthIncome(BigDecimal costBandwidthIncome) {
        this.costBandwidthIncome = costBandwidthIncome;
    }

    public BigDecimal getCostOtherIncome() {
        return costOtherIncome;
    }

    public void setCostOtherIncome(BigDecimal costOtherIncome) {
        this.costOtherIncome = costOtherIncome;
    }

    public BigDecimal getCostOperatingProfit() {
        return costOperatingProfit;
    }

    public void setCostOperatingProfit(BigDecimal costOperatingProfit) {
        this.costOperatingProfit = costOperatingProfit;
    }

    public BigDecimal getCostIncomeProfit() {
        return costIncomeProfit;
    }

    public void setCostIncomeProfit(BigDecimal costIncomeProfit) {
        this.costIncomeProfit = costIncomeProfit;
    }

    public BigDecimal getCostRingGrowth() {
        return costRingGrowth;
    }

    public void setCostRingGrowth(BigDecimal costRingGrowth) {
        this.costRingGrowth = costRingGrowth;
    }

    public BigDecimal getCostEnergyUseRate() {
        return costEnergyUseRate;
    }

    public void setCostEnergyUseRate(BigDecimal costEnergyUseRate) {
        this.costEnergyUseRate = costEnergyUseRate;
    }

    public BigDecimal getCostFrameUseRate() {
        return costFrameUseRate;
    }

    public void setCostFrameUseRate(BigDecimal costFrameUseRate) {
        this.costFrameUseRate = costFrameUseRate;
    }

    public BigDecimal getCostElectricMatchingRate() {
        return costElectricMatchingRate;
    }

    public void setCostElectricMatchingRate(BigDecimal costElectricMatchingRate) {
        this.costElectricMatchingRate = costElectricMatchingRate;
    }

    public Date getCostCreateTime() {
        return costCreateTime;
    }

    public void setCostCreateTime(Date costCreateTime) {
        this.costCreateTime = costCreateTime;
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
        CostAnalysisRoom other = (CostAnalysisRoom) that;
        return (this.getCostAnalysisRoomId() == null ? other.getCostAnalysisRoomId() == null : this.getCostAnalysisRoomId().equals(other.getCostAnalysisRoomId()))
            && (this.getCostRoomId() == null ? other.getCostRoomId() == null : this.getCostRoomId().equals(other.getCostRoomId()))
            && (this.getCostIncome() == null ? other.getCostIncome() == null : this.getCostIncome().equals(other.getCostIncome()))
            && (this.getCostBandwidthIncome() == null ? other.getCostBandwidthIncome() == null : this.getCostBandwidthIncome().equals(other.getCostBandwidthIncome()))
            && (this.getCostOtherIncome() == null ? other.getCostOtherIncome() == null : this.getCostOtherIncome().equals(other.getCostOtherIncome()))
            && (this.getCostOperatingProfit() == null ? other.getCostOperatingProfit() == null : this.getCostOperatingProfit().equals(other.getCostOperatingProfit()))
            && (this.getCostIncomeProfit() == null ? other.getCostIncomeProfit() == null : this.getCostIncomeProfit().equals(other.getCostIncomeProfit()))
            && (this.getCostRingGrowth() == null ? other.getCostRingGrowth() == null : this.getCostRingGrowth().equals(other.getCostRingGrowth()))
            && (this.getCostEnergyUseRate() == null ? other.getCostEnergyUseRate() == null : this.getCostEnergyUseRate().equals(other.getCostEnergyUseRate()))
            && (this.getCostFrameUseRate() == null ? other.getCostFrameUseRate() == null : this.getCostFrameUseRate().equals(other.getCostFrameUseRate()))
            && (this.getCostElectricMatchingRate() == null ? other.getCostElectricMatchingRate() == null : this.getCostElectricMatchingRate().equals(other.getCostElectricMatchingRate()))
            && (this.getCostCreateTime() == null ? other.getCostCreateTime() == null : this.getCostCreateTime().equals(other.getCostCreateTime()))
            && (this.getCostStartTime() == null ? other.getCostStartTime() == null : this.getCostStartTime().equals(other.getCostStartTime()))
            && (this.getCostEndTime() == null ? other.getCostEndTime() == null : this.getCostEndTime().equals(other.getCostEndTime()))
            && (this.getCostCreateUser() == null ? other.getCostCreateUser() == null : this.getCostCreateUser().equals(other.getCostCreateUser()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCostAnalysisRoomId() == null) ? 0 : getCostAnalysisRoomId().hashCode());
        result = prime * result + ((getCostRoomId() == null) ? 0 : getCostRoomId().hashCode());
        result = prime * result + ((getCostIncome() == null) ? 0 : getCostIncome().hashCode());
        result = prime * result + ((getCostBandwidthIncome() == null) ? 0 : getCostBandwidthIncome().hashCode());
        result = prime * result + ((getCostOtherIncome() == null) ? 0 : getCostOtherIncome().hashCode());
        result = prime * result + ((getCostOperatingProfit() == null) ? 0 : getCostOperatingProfit().hashCode());
        result = prime * result + ((getCostIncomeProfit() == null) ? 0 : getCostIncomeProfit().hashCode());
        result = prime * result + ((getCostRingGrowth() == null) ? 0 : getCostRingGrowth().hashCode());
        result = prime * result + ((getCostEnergyUseRate() == null) ? 0 : getCostEnergyUseRate().hashCode());
        result = prime * result + ((getCostFrameUseRate() == null) ? 0 : getCostFrameUseRate().hashCode());
        result = prime * result + ((getCostElectricMatchingRate() == null) ? 0 : getCostElectricMatchingRate().hashCode());
        result = prime * result + ((getCostCreateTime() == null) ? 0 : getCostCreateTime().hashCode());
        result = prime * result + ((getCostStartTime() == null) ? 0 : getCostStartTime().hashCode());
        result = prime * result + ((getCostEndTime() == null) ? 0 : getCostEndTime().hashCode());
        result = prime * result + ((getCostCreateUser() == null) ? 0 : getCostCreateUser().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", costAnalysisRoomId=").append(costAnalysisRoomId);
        sb.append(", costRoomId=").append(costRoomId);
        sb.append(", costIncome=").append(costIncome);
        sb.append(", costBandwidthIncome=").append(costBandwidthIncome);
        sb.append(", costOtherIncome=").append(costOtherIncome);
        sb.append(", costOperatingProfit=").append(costOperatingProfit);
        sb.append(", costIncomeProfit=").append(costIncomeProfit);
        sb.append(", costRingGrowth=").append(costRingGrowth);
        sb.append(", costEnergyUseRate=").append(costEnergyUseRate);
        sb.append(", costFrameUseRate=").append(costFrameUseRate);
        sb.append(", costElectricMatchingRate=").append(costElectricMatchingRate);
        sb.append(", costCreateTime=").append(costCreateTime);
        sb.append(", costStartTime=").append(costStartTime);
        sb.append(", costEndTime=").append(costEndTime);
        sb.append(", costCreateUser=").append(costCreateUser);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}