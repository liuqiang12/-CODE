package com.idc.bo;

/**
 * 机房用电信息
 * Created by mylove on 2017/10/10.
 */
public class RoomPE {
    private long roomid;
    private String roomName;
    private double pue;
    private double electric;
    private String IDC_START_TIME;
    private String IDC_END_TIME;

    public long getRoomid() {
        return roomid;
    }

    public void setRoomid(long roomid) {
        this.roomid = roomid;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public double getPue() {
        return pue;
    }

    public void setPue(double pue) {
        this.pue = pue;
    }

    public String getIDC_START_TIME() {
        return IDC_START_TIME;
    }

    public void setIDC_START_TIME(String IDC_START_TIME) {
        this.IDC_START_TIME = IDC_START_TIME;
    }

    public String getIDC_END_TIME() {
        return IDC_END_TIME;
    }

    public void setIDC_END_TIME(String IDC_END_TIME) {
        this.IDC_END_TIME = IDC_END_TIME;
    }

    public double getElectric() {
        return electric;
    }

    public void setElectric(double electric) {
        this.electric = electric;
    }
}
