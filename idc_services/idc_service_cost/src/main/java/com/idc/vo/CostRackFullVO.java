package com.idc.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by mylove on 2018/3/1.
 */
public class CostRackFullVO {
    private String costFixedId;

    private BigDecimal costFixed = new BigDecimal(0);

    private BigDecimal costBaseDev = new BigDecimal(0);

    private BigDecimal costItDev = new BigDecimal(0);

    private String unitTime;

    private String objectCode;

    private String costFixedType;

    private BigDecimal costFixedAvg = new BigDecimal(0);

    private BigDecimal costShareBuildingYear = new BigDecimal(0);

    private BigDecimal costShareBaseDevYear = new BigDecimal(0);

    private BigDecimal costShareItDevYear = new BigDecimal(0);

    private BigDecimal costShareRackCount = new BigDecimal(0);

    private BigDecimal costBuildingMonth = new BigDecimal(0);

    private BigDecimal costBaseDevMonth = new BigDecimal(0);

    private BigDecimal costItMonth = new BigDecimal(0);

    private BigDecimal costRackMonth = new BigDecimal(0);
    private String costRoomId;
    private BigDecimal costDepreciation = new BigDecimal(0);

    private BigDecimal costMaintenance = new BigDecimal(0);

    private BigDecimal costRepair = new BigDecimal(0);

    private BigDecimal costRenovation = new BigDecimal(0);

    private BigDecimal costDevDepreciation = new BigDecimal(0);

    private BigDecimal costDevRepair = new BigDecimal(0);

    private BigDecimal costWaterRate = new BigDecimal(0);

    private BigDecimal costEnergy = new BigDecimal(0);

    private BigDecimal costWork = new BigDecimal(0);

    private BigDecimal costNetwork = new BigDecimal(0);

    private String costTimeUnit;

    private String roomCode;

    private String roomName;

    private BigDecimal costYearOrMonth = new BigDecimal(0);

    private String buidingCode;

    private Date costStartTime;

    private Date costEndTime;

    private Date costCreateTime;

    private BigDecimal costMarketting = new BigDecimal(0);

    private BigDecimal costRockIncome = new BigDecimal(0);

    private BigDecimal bandwidthIncome = new BigDecimal(0);


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

    public String getCostRoomId() {
        return costRoomId;
    }

    public void setCostRoomId(String costRoomId) {
        this.costRoomId = costRoomId;
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


    public BigDecimal getCostRockIncome() {
        return costRockIncome;
    }

    public void setCostRockIncome(BigDecimal costRockIncome) {
        this.costRockIncome = costRockIncome;
    }

    public BigDecimal getBandwidthIncome() {
        return bandwidthIncome;
    }

    public void setBandwidthIncome(BigDecimal bandwidthIncome) {
        this.bandwidthIncome = bandwidthIncome;
    }
}
