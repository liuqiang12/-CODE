package com.idc.mapper;

import com.idc.model.CostFixed;
import com.idc.model.CostFixedExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CostFixedMapper {
    long countByExample(CostFixedExample example);

    int deleteByExample(CostFixedExample example);

    int deleteByPrimaryKey(String costFixedId);

    int insert(CostFixed record);

    int insertSelective(CostFixed record);

    List<CostFixed> selectByExample(CostFixedExample example);

    CostFixed selectByPrimaryKey(String costFixedId);

    int updateByExampleSelective(@Param("record") CostFixed record, @Param("example") CostFixedExample example);

    int updateByExample(@Param("record") CostFixed record, @Param("example") CostFixedExample example);

    int updateByPrimaryKeySelective(CostFixed record);

    int updateByPrimaryKey(CostFixed record);
}