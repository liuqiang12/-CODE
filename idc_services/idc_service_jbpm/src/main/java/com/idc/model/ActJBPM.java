package com.idc.model;

/**
 * Created by DELL on 2017/6/22.
 */
public class ActJBPM {
    private byte[] bpmBytes;//模型图片二进制
    private String id;//资源ID
    private String deployId;//部署ID

    public byte[] getBpmBytes() {
        return bpmBytes;
    }

    public void setBpmBytes(byte[] bpmBytes) {
        this.bpmBytes = bpmBytes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeployId() {
        return deployId;
    }

    public void setDeployId(String deployId) {
        this.deployId = deployId;
    }
}
