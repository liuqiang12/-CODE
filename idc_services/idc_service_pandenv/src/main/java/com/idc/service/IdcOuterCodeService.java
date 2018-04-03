package com.idc.service;

import com.idc.model.IdcOuterCode;
import com.idc.model.IdcPdfDayPowerInfo;
import system.data.supper.service.SuperService;

/**
 * Created by mylove on 2017/7/17.
 */
public interface IdcOuterCodeService extends SuperService<IdcOuterCode, String> {


    IdcOuterCode getBySysCode(String s);
    IdcOuterCode getByOuterCode(String s);
}
