package com.idc.model;

/**
 * Created by mylove on 2017/6/26.
 */
public class IdcSubnetCountVo extends IdcIpSubnet {
    private long allip;
    private long free;
    private long preused;
    private long used;
    private long recovery;
    /**
     * 空闲率
     */
    private long freerate;
    private long userate;

    public long getAllip() {
        return allip;
    }

    public void setAllip(long allip) {
        this.allip = allip;
    }

    public long getFree() {
        return free;
    }

    public void setFree(long free) {
        this.free = free;
    }

    public long getPreused() {
        return preused;
    }

    public void setPreused(long preused) {
        this.preused = preused;
    }

    public long getUsed() {
        return used;
    }

    public void setUsed(long used) {
        this.used = used;
    }

    public long getRecovery() {
        return recovery;
    }

    public void setRecovery(long recovery) {
        this.recovery = recovery;
    }

    public long getFreerate() {
        return freerate;
    }

    public void setFreerate(long freerate) {
        this.freerate = freerate;
    }

    public long getUserate() {
        return userate;
    }

    public void setUserate(long userate) {
        this.userate = userate;
    }
}
