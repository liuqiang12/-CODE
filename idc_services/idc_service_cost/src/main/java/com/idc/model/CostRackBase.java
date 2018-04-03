package com.idc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 
 */
public class CostRackBase implements Serializable {
    private String costRackId;

    private String costFixedId;

    private String rackCode;

    private String roomCode;

    private String costTimeUnit;

    private BigDecimal costDepreciation;

    private BigDecimal costMaintenanceFee;

    private BigDecimal costInvest;

    private Date costCreateTime;

    private static final long serialVersionUID = 1L;

    public String getCostRackId() {
        return costRackId;
    }

    public void setCostRackId(String costRackId) {
        this.costRackId = costRackId;
    }

    public String getCostFixedId() {
        return costFixedId;
    }

    public void setCostFixedId(String costFixedId) {
        this.costFixedId = costFixedId;
    }

    public String getRackCode() {
        return rackCode;
    }

    public void setRackCode(String rackCode) {
        this.rackCode = rackCode;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getCostTimeUnit() {
        return costTimeUnit;
    }

    public void setCostTimeUnit(String costTimeUnit) {
        this.costTimeUnit = costTimeUnit;
    }

    public BigDecimal getCostDepreciation() {
        return costDepreciation;
    }

    public void setCostDepreciation(BigDecimal costDepreciation) {
        this.costDepreciation = costDepreciation;
    }

    public BigDecimal getCostMaintenanceFee() {
        return costMaintenanceFee;
    }

    public void setCostMaintenanceFee(BigDecimal costMaintenanceFee) {
        this.costMaintenanceFee = costMaintenanceFee;
    }

    public BigDecimal getCostInvest() {
        return costInvest;
    }

    public void setCostInvest(BigDecimal costInvest) {
        this.costInvest = costInvest;
    }

    public Date getCostCreateTime() {
        return costCreateTime;
    }

    public void setCostCreateTime(Date costCreateTime) {
        this.costCreateTime = costCreateTime;
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
        CostRackBase other = (CostRackBase) that;
        return (this.getCostRackId() == null ? other.getCostRackId() == null : this.getCostRackId().equals(other.getCostRackId()))
            && (this.getCostFixedId() == null ? other.getCostFixedId() == null : this.getCostFixedId().equals(other.getCostFixedId()))
            && (this.getRackCode() == null ? other.getRackCode() == null : this.getRackCode().equals(other.getRackCode()))
            && (this.getRoomCode() == null ? other.getRoomCode() == null : this.getRoomCode().equals(other.getRoomCode()))
            && (this.getCostTimeUnit() == null ? other.getCostTimeUnit() == null : this.getCostTimeUnit().equals(other.getCostTimeUnit()))
            && (this.getCostDepreciation() == null ? other.getCostDepreciation() == null : this.getCostDepreciation().equals(other.getCostDepreciation()))
            && (this.getCostMaintenanceFee() == null ? other.getCostMaintenanceFee() == null : this.getCostMaintenanceFee().equals(other.getCostMaintenanceFee()))
            && (this.getCostInvest() == null ? other.getCostInvest() == null : this.getCostInvest().equals(other.getCostInvest()))
            && (this.getCostCreateTime() == null ? other.getCostCreateTime() == null : this.getCostCreateTime().equals(other.getCostCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCostRackId() == null) ? 0 : getCostRackId().hashCode());
        result = prime * result + ((getCostFixedId() == null) ? 0 : getCostFixedId().hashCode());
        result = prime * result + ((getRackCode() == null) ? 0 : getRackCode().hashCode());
        result = prime * result + ((getRoomCode() == null) ? 0 : getRoomCode().hashCode());
        result = prime * result + ((getCostTimeUnit() == null) ? 0 : getCostTimeUnit().hashCode());
        result = prime * result + ((getCostDepreciation() == null) ? 0 : getCostDepreciation().hashCode());
        result = prime * result + ((getCostMaintenanceFee() == null) ? 0 : getCostMaintenanceFee().hashCode());
        result = prime * result + ((getCostInvest() == null) ? 0 : getCostInvest().hashCode());
        result = prime * result + ((getCostCreateTime() == null) ? 0 : getCostCreateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", costRackId=").append(costRackId);
        sb.append(", costFixedId=").append(costFixedId);
        sb.append(", rackCode=").append(rackCode);
        sb.append(", roomCode=").append(roomCode);
        sb.append(", costTimeUnit=").append(costTimeUnit);
        sb.append(", costDepreciation=").append(costDepreciation);
        sb.append(", costMaintenanceFee=").append(costMaintenanceFee);
        sb.append(", costInvest=").append(costInvest);
        sb.append(", costCreateTime=").append(costCreateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}