package com.idc.vo;

import com.idc.model.IdcResourcesPool;
import com.idc.model.IdcVmDisk;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mylove on 2017/10/18.
 */
public class IdcVmHostVO {
    private IdcResourcesPool idcResourcesPool;
    private List<IdcVmDisk> idcVmDisk = new ArrayList<>();
    private String hostName;
    private String hostid;
    private String ticketId;
    private String ticketUser;
    private Double menSize;
    private Double diskSize;
    private Long cpuNum;

    public IdcResourcesPool getIdcResourcesPool() {
        return idcResourcesPool;
    }

    public void setIdcResourcesPool(IdcResourcesPool idcResourcesPool) {
        this.idcResourcesPool = idcResourcesPool;
    }

    public List<IdcVmDisk> getIdcVmDisk() {
        return idcVmDisk;
    }

    public void setIdcVmDisk(List<IdcVmDisk> idcVmDisk) {
        this.idcVmDisk = idcVmDisk;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketUser() {
        return ticketUser;
    }

    public void setTicketUser(String ticketUser) {
        this.ticketUser = ticketUser;
    }

    public String getHostid() {
        return hostid;
    }

    public void setHostid(String hostid) {
        this.hostid = hostid;
    }

    public Double getDiskSize() {
        if (diskSize == null) {
            Double l = 0D;
            for (IdcVmDisk vmDisk : this.getIdcVmDisk()) {
                l += vmDisk.getIdcVmDiskAvailableSize() == null ? 0 : vmDisk.getIdcVmDiskAvailableSize().doubleValue();
            }
            diskSize = l;
        }
        return diskSize;
    }

    public Double getMenSize() {
        return menSize;
    }

    public void setMenSize(Double menSize) {
        this.menSize = menSize;
    }

    public void setDiskSize(Double diskSize) {
        this.diskSize = diskSize;
    }

    public Long getCpuNum() {
        return cpuNum;
    }

    public void setCpuNum(Long cpuNum) {
        this.cpuNum = cpuNum;
    }
}
