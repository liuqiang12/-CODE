package com.idc.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mylove on 2017/8/9.
 */
public class TicketData implements Serializable {
    private long localId;
    private long ticketId;
    private Object ticketData;
    //资源列表
    List<ResourceLog> resources = new ArrayList<>();

    public long getTicketId() {
        return ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

    public List<ResourceLog> getResources() {
        return resources;
    }

    public void setResources(List<ResourceLog> resources) {
        this.resources = resources;
    }

    public Object getTicketData() {
        return ticketData;
    }

    public void setTicketData(Object ticketData) {
        this.ticketData = ticketData;
    }

    public long getLocalId() {
        return localId;
    }

    public void setLocalId(long localId) {
        this.localId = localId;
    }
}
