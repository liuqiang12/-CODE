package com.idc.mapper;

import com.idc.model.IdcOuterCode;
import com.idc.model.IdcPdfDayPowerInfo;
import org.apache.ibatis.annotations.Param;
import system.data.supper.mapper.SuperMapper;

import java.util.List;

/**
 * Created by mylove on 2017/7/17.
 */
public interface IdcOuterCodeMapper extends SuperMapper<IdcOuterCode, String> {
    public IdcOuterCode getBySysCode(@Param("syscode") String syscode);

    public IdcOuterCode getByOuterCode(@Param("outercode") String outercode);
}
