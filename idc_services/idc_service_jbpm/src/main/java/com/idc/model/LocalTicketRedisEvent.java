package com.idc.model;

import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 * Created by DELL on 2017/9/14.
 */
public class LocalTicketRedisEvent extends ApplicationEvent {
    public Map<String,Object> map;//参数设置
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public LocalTicketRedisEvent(Object source,Map<String,Object> map) {
        super(source);
        this.map = map;
    }
}
