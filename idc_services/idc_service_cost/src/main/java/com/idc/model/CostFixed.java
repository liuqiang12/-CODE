package com.idc.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 
 */
public class CostFixed implements Serializable {
    private String costFixedId;

    private BigDecimal costFixed;

    private BigDecimal costBaseDev;

    private BigDecimal costItDev;

    private String unitTime;

    private String objectCode;

    private String costFixedType;

    private BigDecimal costFixedAvg;

    private BigDecimal costShareBuildingYear;

    private BigDecimal costShareBaseDevYear;

    private BigDecimal costShareItDevYear;

    private BigDecimal costShareRackCount;

    private BigDecimal costBuildingMonth;

    private BigDecimal costBaseDevMonth;

    private BigDecimal costItMonth;

    private BigDecimal costRackMonth;

    private static final long serialVersionUID = 1L;

    public String getCostFixedId() {
        return costFixedId;
    }

    public void setCostFixedId(String costFixedId) {
        this.costFixedId = costFixedId;
    }

    public BigDecimal getCostFixed() {
        return costFixed;
    }

    public void setCostFixed(BigDecimal costFixed) {
        this.costFixed = costFixed;
    }

    public BigDecimal getCostBaseDev() {
        return costBaseDev;
    }

    public void setCostBaseDev(BigDecimal costBaseDev) {
        this.costBaseDev = costBaseDev;
    }

    public BigDecimal getCostItDev() {
        return costItDev;
    }

    public void setCostItDev(BigDecimal costItDev) {
        this.costItDev = costItDev;
    }

    public String getUnitTime() {
        return unitTime;
    }

    public void setUnitTime(String unitTime) {
        this.unitTime = unitTime;
    }

    public String getObjectCode() {
        return objectCode;
    }

    public void setObjectCode(String objectCode) {
        this.objectCode = objectCode;
    }

    public String getCostFixedType() {
        return costFixedType;
    }

    public void setCostFixedType(String costFixedType) {
        this.costFixedType = costFixedType;
    }

    public BigDecimal getCostFixedAvg() {
        return costFixedAvg;
    }

    public void setCostFixedAvg(BigDecimal costFixedAvg) {
        this.costFixedAvg = costFixedAvg;
    }

    public BigDecimal getCostShareBuildingYear() {
        return costShareBuildingYear;
    }

    public void setCostShareBuildingYear(BigDecimal costShareBuildingYear) {
        this.costShareBuildingYear = costShareBuildingYear;
    }

    public BigDecimal getCostShareBaseDevYear() {
        return costShareBaseDevYear;
    }

    public void setCostShareBaseDevYear(BigDecimal costShareBaseDevYear) {
        this.costShareBaseDevYear = costShareBaseDevYear;
    }

    public BigDecimal getCostShareItDevYear() {
        return costShareItDevYear;
    }

    public void setCostShareItDevYear(BigDecimal costShareItDevYear) {
        this.costShareItDevYear = costShareItDevYear;
    }

    public BigDecimal getCostShareRackCount() {
        return costShareRackCount;
    }

    public void setCostShareRackCount(BigDecimal costShareRackCount) {
        this.costShareRackCount = costShareRackCount;
    }

    public BigDecimal getCostBuildingMonth() {
        return costBuildingMonth;
    }

    public void setCostBuildingMonth(BigDecimal costBuildingMonth) {
        this.costBuildingMonth = costBuildingMonth;
    }

    public BigDecimal getCostBaseDevMonth() {
        return costBaseDevMonth;
    }

    public void setCostBaseDevMonth(BigDecimal costBaseDevMonth) {
        this.costBaseDevMonth = costBaseDevMonth;
    }

    public BigDecimal getCostItMonth() {
        return costItMonth;
    }

    public void setCostItMonth(BigDecimal costItMonth) {
        this.costItMonth = costItMonth;
    }

    public BigDecimal getCostRackMonth() {
        return costRackMonth;
    }

    public void setCostRackMonth(BigDecimal costRackMonth) {
        this.costRackMonth = costRackMonth;
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
        CostFixed other = (CostFixed) that;
        return (this.getCostFixedId() == null ? other.getCostFixedId() == null : this.getCostFixedId().equals(other.getCostFixedId()))
            && (this.getCostFixed() == null ? other.getCostFixed() == null : this.getCostFixed().equals(other.getCostFixed()))
            && (this.getCostBaseDev() == null ? other.getCostBaseDev() == null : this.getCostBaseDev().equals(other.getCostBaseDev()))
            && (this.getCostItDev() == null ? other.getCostItDev() == null : this.getCostItDev().equals(other.getCostItDev()))
            && (this.getUnitTime() == null ? other.getUnitTime() == null : this.getUnitTime().equals(other.getUnitTime()))
            && (this.getObjectCode() == null ? other.getObjectCode() == null : this.getObjectCode().equals(other.getObjectCode()))
            && (this.getCostFixedType() == null ? other.getCostFixedType() == null : this.getCostFixedType().equals(other.getCostFixedType()))
            && (this.getCostFixedAvg() == null ? other.getCostFixedAvg() == null : this.getCostFixedAvg().equals(other.getCostFixedAvg()))
            && (this.getCostShareBuildingYear() == null ? other.getCostShareBuildingYear() == null : this.getCostShareBuildingYear().equals(other.getCostShareBuildingYear()))
            && (this.getCostShareBaseDevYear() == null ? other.getCostShareBaseDevYear() == null : this.getCostShareBaseDevYear().equals(other.getCostShareBaseDevYear()))
            && (this.getCostShareItDevYear() == null ? other.getCostShareItDevYear() == null : this.getCostShareItDevYear().equals(other.getCostShareItDevYear()))
            && (this.getCostShareRackCount() == null ? other.getCostShareRackCount() == null : this.getCostShareRackCount().equals(other.getCostShareRackCount()))
            && (this.getCostBuildingMonth() == null ? other.getCostBuildingMonth() == null : this.getCostBuildingMonth().equals(other.getCostBuildingMonth()))
            && (this.getCostBaseDevMonth() == null ? other.getCostBaseDevMonth() == null : this.getCostBaseDevMonth().equals(other.getCostBaseDevMonth()))
            && (this.getCostItMonth() == null ? other.getCostItMonth() == null : this.getCostItMonth().equals(other.getCostItMonth()))
            && (this.getCostRackMonth() == null ? other.getCostRackMonth() == null : this.getCostRackMonth().equals(other.getCostRackMonth()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCostFixedId() == null) ? 0 : getCostFixedId().hashCode());
        result = prime * result + ((getCostFixed() == null) ? 0 : getCostFixed().hashCode());
        result = prime * result + ((getCostBaseDev() == null) ? 0 : getCostBaseDev().hashCode());
        result = prime * result + ((getCostItDev() == null) ? 0 : getCostItDev().hashCode());
        result = prime * result + ((getUnitTime() == null) ? 0 : getUnitTime().hashCode());
        result = prime * result + ((getObjectCode() == null) ? 0 : getObjectCode().hashCode());
        result = prime * result + ((getCostFixedType() == null) ? 0 : getCostFixedType().hashCode());
        result = prime * result + ((getCostFixedAvg() == null) ? 0 : getCostFixedAvg().hashCode());
        result = prime * result + ((getCostShareBuildingYear() == null) ? 0 : getCostShareBuildingYear().hashCode());
        result = prime * result + ((getCostShareBaseDevYear() == null) ? 0 : getCostShareBaseDevYear().hashCode());
        result = prime * result + ((getCostShareItDevYear() == null) ? 0 : getCostShareItDevYear().hashCode());
        result = prime * result + ((getCostShareRackCount() == null) ? 0 : getCostShareRackCount().hashCode());
        result = prime * result + ((getCostBuildingMonth() == null) ? 0 : getCostBuildingMonth().hashCode());
        result = prime * result + ((getCostBaseDevMonth() == null) ? 0 : getCostBaseDevMonth().hashCode());
        result = prime * result + ((getCostItMonth() == null) ? 0 : getCostItMonth().hashCode());
        result = prime * result + ((getCostRackMonth() == null) ? 0 : getCostRackMonth().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", costFixedId=").append(costFixedId);
        sb.append(", costFixed=").append(costFixed);
        sb.append(", costBaseDev=").append(costBaseDev);
        sb.append(", costItDev=").append(costItDev);
        sb.append(", unitTime=").append(unitTime);
        sb.append(", objectCode=").append(objectCode);
        sb.append(", costFixedType=").append(costFixedType);
        sb.append(", costFixedAvg=").append(costFixedAvg);
        sb.append(", costShareBuildingYear=").append(costShareBuildingYear);
        sb.append(", costShareBaseDevYear=").append(costShareBaseDevYear);
        sb.append(", costShareItDevYear=").append(costShareItDevYear);
        sb.append(", costShareRackCount=").append(costShareRackCount);
        sb.append(", costBuildingMonth=").append(costBuildingMonth);
        sb.append(", costBaseDevMonth=").append(costBaseDevMonth);
        sb.append(", costItMonth=").append(costItMonth);
        sb.append(", costRackMonth=").append(costRackMonth);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}