package com.idc.rest.org_restlet_ext.resource;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import system.rest.ResultObject;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;

/**
 * ezmorph
 * Created by DELL on 2017/9/5.
 */
@Path("jbpm")
@Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public interface IJbpmResource {
    @PUT
    @Path("handlerRunTikcetProcess")
    ResultObject handlerRunTikcetProcess(String handlerRunTikcetParam) throws Exception;
    /* 获取相应的即将过期的工单*/
    @PUT
    @Path("commingExpireTicket")
    ResultObject commingExpireTicket(String redisKey) throws Exception;
}
