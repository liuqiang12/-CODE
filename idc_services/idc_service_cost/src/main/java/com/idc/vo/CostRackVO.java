package com.idc.vo;

/**
 * 机架成本数据对象
 * Created by mylove on 2017/10/27.
 */
public class CostRackVO {
    /***
     * 机架固定成本
     */
    private Long costRackId;
    private String costRackName;
    private String roomName;
    private String roomId;
    //折旧率
    private double costDepreciation;
    //维护成本
    private double costMaintenanceFee;
    //机架投资
    private double costInvest;
    private Long avgDepreciationYear;

    /***
     * 成本
     */
    //电费
    private double costRackPowerFee;
    //营销成本
    private double costMarketting;
    private double costOther;
    //综合成本
    private double costColligate;
    private double costYear;
    //带宽成本
    private double costBandwidth;

    /***
     * 收入
     */
    //带宽收入
    private double bandwidthIncome;
    //已售收入
    private double soldIncomeFee;
    //机架收入
    private double costRockIncome;
    //已售机架
    private double soldRockFee;

    public Long getCostRackId() {
        return costRackId;
    }

    public void setCostRackId(Long costRackId) {
        this.costRackId = costRackId;
    }

    public String getCostRackName() {
        return costRackName;
    }

    public void setCostRackName(String costRackName) {
        this.costRackName = costRackName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public double getCostDepreciation() {
        return costDepreciation;
    }

    public void setCostDepreciation(double costDepreciation) {
        this.costDepreciation = costDepreciation;
    }

    public double getCostMaintenanceFee() {
        return costMaintenanceFee;
    }

    public void setCostMaintenanceFee(double costMaintenanceFee) {
        this.costMaintenanceFee = costMaintenanceFee;
    }

    public double getCostInvest() {
        return costInvest;
    }

    public void setCostInvest(double costInvest) {
        this.costInvest = costInvest;
    }

    public Long getAvgDepreciationYear() {
        return avgDepreciationYear;
    }

    public void setAvgDepreciationYear(Long avgDepreciationYear) {
        this.avgDepreciationYear = avgDepreciationYear;
    }

    public double getCostRackPowerFee() {
        return costRackPowerFee;
    }

    public void setCostRackPowerFee(double costRackPowerFee) {
        this.costRackPowerFee = costRackPowerFee;
    }

    public double getCostMarketting() {
        return costMarketting;
    }

    public void setCostMarketting(double costMarketting) {
        this.costMarketting = costMarketting;
    }

    public double getCostOther() {
        return costOther;
    }

    public void setCostOther(double costOther) {
        this.costOther = costOther;
    }

    public double getCostColligate() {
        return costColligate;
    }

    public void setCostColligate(double costColligate) {
        this.costColligate = costColligate;
    }

    public double getCostYear() {
        return costYear;
    }

    public void setCostYear(double costYear) {
        this.costYear = costYear;
    }

    public double getCostBandwidth() {
        return costBandwidth;
    }

    public void setCostBandwidth(double costBandwidth) {
        this.costBandwidth = costBandwidth;
    }

    public double getBandwidthIncome() {
        return bandwidthIncome;
    }

    public void setBandwidthIncome(double bandwidthIncome) {
        this.bandwidthIncome = bandwidthIncome;
    }

    public double getSoldIncomeFee() {
        return soldIncomeFee;
    }

    public void setSoldIncomeFee(double soldIncomeFee) {
        this.soldIncomeFee = soldIncomeFee;
    }

    public double getCostRockIncome() {
        return costRockIncome;
    }

    public void setCostRockIncome(double costRockIncome) {
        this.costRockIncome = costRockIncome;
    }

    public double getSoldRockFee() {
        return soldRockFee;
    }

    public void setSoldRockFee(double soldRockFee) {
        this.soldRockFee = soldRockFee;
    }
}
