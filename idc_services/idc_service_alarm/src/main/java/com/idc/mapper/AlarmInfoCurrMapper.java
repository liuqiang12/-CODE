package com.idc.mapper;

import com.idc.model.AlarmInfoCurr;
import com.idc.model.AlarmInfoCurrExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AlarmInfoCurrMapper {
    long countByExample(AlarmInfoCurrExample example);

    int deleteByExample(AlarmInfoCurrExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AlarmInfoCurr record);

    int insertSelective(AlarmInfoCurr record);

    List<AlarmInfoCurr> selectByExample(AlarmInfoCurrExample example);

    AlarmInfoCurr selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AlarmInfoCurr record, @Param("example") AlarmInfoCurrExample example);

    int updateByExample(@Param("record") AlarmInfoCurr record, @Param("example") AlarmInfoCurrExample example);

    int updateByPrimaryKeySelective(AlarmInfoCurr record);

    int updateByPrimaryKey(AlarmInfoCurr record);
}