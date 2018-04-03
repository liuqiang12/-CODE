package com.idc.spring;

import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 * 基本的监听事件类
 */
public class JbpmProcessEvent extends ApplicationEvent {
    public Object target;
    public Map<String,Object> map;//参数设置

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public JbpmProcessEvent(Object source, Object target, Map<String,Object> map) {
        super(source);
        this.target = target;
        this.map = map;
    }
}
