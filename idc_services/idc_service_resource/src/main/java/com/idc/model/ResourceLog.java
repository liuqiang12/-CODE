package com.idc.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mylove on 2017/8/9.
 */
public class ResourceLog implements Serializable{
    private ResourceType resourceType;//资源类型
    private List<Object> objects;//资源对象 要求可以序列化
    private OperaType operaType;//操作类型

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public List<Object> getObjects() {
        return objects;
    }

    public void setObjects(List<Object> objects) {
        this.objects = objects;
    }

    public OperaType getOperaType() {
        return operaType;
    }

    public void setOperaType(OperaType operaType) {
        this.operaType = operaType;
    }
}
