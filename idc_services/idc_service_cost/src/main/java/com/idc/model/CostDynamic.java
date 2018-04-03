package com.idc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 
 */
public class CostDynamic implements Serializable {
    private String costRoomId;

    private String costFixedId;

    private BigDecimal costDepreciation;

    private BigDecimal costMaintenance;

    private BigDecimal costRepair;

    private BigDecimal costRenovation;

    private BigDecimal costDevDepreciation;

    private BigDecimal costDevRepair;

    private BigDecimal costWaterRate;

    private BigDecimal costEnergy;

    private BigDecimal costWork;

    private BigDecimal costNetwork;

    private String costTimeUnit;

    private String roomCode;

    private String roomName;

    private BigDecimal costYearOrMonth;

    private String buidingCode;

    private Date costStartTime;

    private Date costEndTime;

    private Date costCreateTime;

    private BigDecimal costMarketting;

    private BigDecimal costCustomIncome;

    private String customId;

    private static final long serialVersionUID = 1L;

    public String getCostRoomId() {
        return costRoomId;
    }

    public void setCostRoomId(String costRoomId) {
        this.costRoomId = costRoomId;
    }

    public String getCostFixedId() {
        return costFixedId;
    }

    public void setCostFixedId(String costFixedId) {
        this.costFixedId = costFixedId;
    }

    public BigDecimal getCostDepreciation() {
        return costDepreciation;
    }

    public void setCostDepreciation(BigDecimal costDepreciation) {
        this.costDepreciation = costDepreciation;
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

    public BigDecimal getCostRenovation() {
        return costRenovation;
    }

    public void setCostRenovation(BigDecimal costRenovation) {
        this.costRenovation = costRenovation;
    }

    public BigDecimal getCostDevDepreciation() {
        return costDevDepreciation;
    }

    public void setCostDevDepreciation(BigDecimal costDevDepreciation) {
        this.costDevDepreciation = costDevDepreciation;
    }

    public BigDecimal getCostDevRepair() {
        return costDevRepair;
    }

    public void setCostDevRepair(BigDecimal costDevRepair) {
        this.costDevRepair = costDevRepair;
    }

    public BigDecimal getCostWaterRate() {
        return costWaterRate;
    }

    public void setCostWaterRate(BigDecimal costWaterRate) {
        this.costWaterRate = costWaterRate;
    }

    public BigDecimal getCostEnergy() {
        return costEnergy;
    }

    public void setCostEnergy(BigDecimal costEnergy) {
        this.costEnergy = costEnergy;
    }

    public BigDecimal getCostWork() {
        return costWork;
    }

    public void setCostWork(BigDecimal costWork) {
        this.costWork = costWork;
    }

    public BigDecimal getCostNetwork() {
        return costNetwork;
    }

    public void setCostNetwork(BigDecimal costNetwork) {
        this.costNetwork = costNetwork;
    }

    public String getCostTimeUnit() {
        return costTimeUnit;
    }

    public void setCostTimeUnit(String costTimeUnit) {
        this.costTimeUnit = costTimeUnit;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public BigDecimal getCostYearOrMonth() {
        return costYearOrMonth;
    }

    public void setCostYearOrMonth(BigDecimal costYearOrMonth) {
        this.costYearOrMonth = costYearOrMonth;
    }

    public String getBuidingCode() {
        return buidingCode;
    }

    public void setBuidingCode(String buidingCode) {
        this.buidingCode = buidingCode;
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

    public BigDecimal getCostMarketting() {
        return costMarketting;
    }

    public void setCostMarketting(BigDecimal costMarketting) {
        this.costMarketting = costMarketting;
    }

    public BigDecimal getCostCustomIncome() {
        return costCustomIncome;
    }

    public void setCostCustomIncome(BigDecimal costCustomIncome) {
        this.costCustomIncome = costCustomIncome;
    }

    public String getCustomId() {
        return customId;
    }

    public void setCustomId(String customId) {
        this.customId = customId;
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
        CostDynamic other = (CostDynamic) that;
        return (this.getCostRoomId() == null ? other.getCostRoomId() == null : this.getCostRoomId().equals(other.getCostRoomId()))
            && (this.getCostFixedId() == null ? other.getCostFixedId() == null : this.getCostFixedId().equals(other.getCostFixedId()))
            && (this.getCostDepreciation() == null ? other.getCostDepreciation() == null : this.getCostDepreciation().equals(other.getCostDepreciation()))
            && (this.getCostMaintenance() == null ? other.getCostMaintenance() == null : this.getCostMaintenance().equals(other.getCostMaintenance()))
            && (this.getCostRepair() == null ? other.getCostRepair() == null : this.getCostRepair().equals(other.getCostRepair()))
            && (this.getCostRenovation() == null ? other.getCostRenovation() == null : this.getCostRenovation().equals(other.getCostRenovation()))
            && (this.getCostDevDepreciation() == null ? other.getCostDevDepreciation() == null : this.getCostDevDepreciation().equals(other.getCostDevDepreciation()))
            && (this.getCostDevRepair() == null ? other.getCostDevRepair() == null : this.getCostDevRepair().equals(other.getCostDevRepair()))
            && (this.getCostWaterRate() == null ? other.getCostWaterRate() == null : this.getCostWaterRate().equals(other.getCostWaterRate()))
            && (this.getCostEnergy() == null ? other.getCostEnergy() == null : this.getCostEnergy().equals(other.getCostEnergy()))
            && (this.getCostWork() == null ? other.getCostWork() == null : this.getCostWork().equals(other.getCostWork()))
            && (this.getCostNetwork() == null ? other.getCostNetwork() == null : this.getCostNetwork().equals(other.getCostNetwork()))
            && (this.getCostTimeUnit() == null ? other.getCostTimeUnit() == null : this.getCostTimeUnit().equals(other.getCostTimeUnit()))
            && (this.getRoomCode() == null ? other.getRoomCode() == null : this.getRoomCode().equals(other.getRoomCode()))
            && (this.getRoomName() == null ? other.getRoomName() == null : this.getRoomName().equals(other.getRoomName()))
            && (this.getCostYearOrMonth() == null ? other.getCostYearOrMonth() == null : this.getCostYearOrMonth().equals(other.getCostYearOrMonth()))
            && (this.getBuidingCode() == null ? other.getBuidingCode() == null : this.getBuidingCode().equals(other.getBuidingCode()))
            && (this.getCostStartTime() == null ? other.getCostStartTime() == null : this.getCostStartTime().equals(other.getCostStartTime()))
            && (this.getCostEndTime() == null ? other.getCostEndTime() == null : this.getCostEndTime().equals(other.getCostEndTime()))
            && (this.getCostCreateTime() == null ? other.getCostCreateTime() == null : this.getCostCreateTime().equals(other.getCostCreateTime()))
            && (this.getCostMarketting() == null ? other.getCostMarketting() == null : this.getCostMarketting().equals(other.getCostMarketting()))
            && (this.getCostCustomIncome() == null ? other.getCostCustomIncome() == null : this.getCostCustomIncome().equals(other.getCostCustomIncome()))
            && (this.getCustomId() == null ? other.getCustomId() == null : this.getCustomId().equals(other.getCustomId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCostRoomId() == null) ? 0 : getCostRoomId().hashCode());
        result = prime * result + ((getCostFixedId() == null) ? 0 : getCostFixedId().hashCode());
        result = prime * result + ((getCostDepreciation() == null) ? 0 : getCostDepreciation().hashCode());
        result = prime * result + ((getCostMaintenance() == null) ? 0 : getCostMaintenance().hashCode());
        result = prime * result + ((getCostRepair() == null) ? 0 : getCostRepair().hashCode());
        result = prime * result + ((getCostRenovation() == null) ? 0 : getCostRenovation().hashCode());
        result = prime * result + ((getCostDevDepreciation() == null) ? 0 : getCostDevDepreciation().hashCode());
        result = prime * result + ((getCostDevRepair() == null) ? 0 : getCostDevRepair().hashCode());
        result = prime * result + ((getCostWaterRate() == null) ? 0 : getCostWaterRate().hashCode());
        result = prime * result + ((getCostEnergy() == null) ? 0 : getCostEnergy().hashCode());
        result = prime * result + ((getCostWork() == null) ? 0 : getCostWork().hashCode());
        result = prime * result + ((getCostNetwork() == null) ? 0 : getCostNetwork().hashCode());
        result = prime * result + ((getCostTimeUnit() == null) ? 0 : getCostTimeUnit().hashCode());
        result = prime * result + ((getRoomCode() == null) ? 0 : getRoomCode().hashCode());
        result = prime * result + ((getRoomName() == null) ? 0 : getRoomName().hashCode());
        result = prime * result + ((getCostYearOrMonth() == null) ? 0 : getCostYearOrMonth().hashCode());
        result = prime * result + ((getBuidingCode() == null) ? 0 : getBuidingCode().hashCode());
        result = prime * result + ((getCostStartTime() == null) ? 0 : getCostStartTime().hashCode());
        result = prime * result + ((getCostEndTime() == null) ? 0 : getCostEndTime().hashCode());
        result = prime * result + ((getCostCreateTime() == null) ? 0 : getCostCreateTime().hashCode());
        result = prime * result + ((getCostMarketting() == null) ? 0 : getCostMarketting().hashCode());
        result = prime * result + ((getCostCustomIncome() == null) ? 0 : getCostCustomIncome().hashCode());
        result = prime * result + ((getCustomId() == null) ? 0 : getCustomId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", costRoomId=").append(costRoomId);
        sb.append(", costFixedId=").append(costFixedId);
        sb.append(", costDepreciation=").append(costDepreciation);
        sb.append(", costMaintenance=").append(costMaintenance);
        sb.append(", costRepair=").append(costRepair);
        sb.append(", costRenovation=").append(costRenovation);
        sb.append(", costDevDepreciation=").append(costDevDepreciation);
        sb.append(", costDevRepair=").append(costDevRepair);
        sb.append(", costWaterRate=").append(costWaterRate);
        sb.append(", costEnergy=").append(costEnergy);
        sb.append(", costWork=").append(costWork);
        sb.append(", costNetwork=").append(costNetwork);
        sb.append(", costTimeUnit=").append(costTimeUnit);
        sb.append(", roomCode=").append(roomCode);
        sb.append(", roomName=").append(roomName);
        sb.append(", costYearOrMonth=").append(costYearOrMonth);
        sb.append(", buidingCode=").append(buidingCode);
        sb.append(", costStartTime=").append(costStartTime);
        sb.append(", costEndTime=").append(costEndTime);
        sb.append(", costCreateTime=").append(costCreateTime);
        sb.append(", costMarketting=").append(costMarketting);
        sb.append(", costCustomIncome=").append(costCustomIncome);
        sb.append(", customId=").append(customId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}