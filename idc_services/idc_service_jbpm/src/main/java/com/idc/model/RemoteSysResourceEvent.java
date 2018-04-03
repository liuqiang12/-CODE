package com.idc.model;

import org.springframework.context.ApplicationEvent;

/**
 * Created by DELL on 2017/8/29.
 * 基本的监听事件类
 */
public class RemoteSysResourceEvent extends ApplicationEvent {
    public Object target;/*通过map传递参数*/
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public RemoteSysResourceEvent(Object source, Object target) {
        super(source);
        this.target = target;
    }
}
