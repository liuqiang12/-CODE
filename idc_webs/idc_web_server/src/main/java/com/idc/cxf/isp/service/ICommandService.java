package com.idc.cxf.isp.service;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * Created by DELL on 2017/9/1.
 */

@WebService(targetNamespace="http://service.isp.cxf.idc.com/")
public interface ICommandService {
    /** isp **/
    @WebResult(name = "result")String idc_command(
            @WebParam(name = "idcIdc") String idcIdc,
            @WebParam(name = "randVal") String randVal,
            @WebParam(name = "pwdHash") String pwdHash,
            @WebParam(name = "command") String command,
            @WebParam(name = "commandHash") String commandHash,
            @WebParam(name = "commandType") int commandType,
            @WebParam(name = "commandSequence") Long commandSequence,
            @WebParam(name = "encryptAlgorithm") String encryptAlgorithm,
            @WebParam(name = "hashAlgorithm") String hashAlgorithm,
            @WebParam(name = "compressionFormat") String compressionFormat,
            @WebParam(name = "commandVersion") String commandVersion
    );
}
