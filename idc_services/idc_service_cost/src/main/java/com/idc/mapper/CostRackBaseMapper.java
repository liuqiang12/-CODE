package com.idc.mapper;

import com.idc.model.CostRackBase;
import com.idc.model.CostRackBaseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CostRackBaseMapper {
    long countByExample(CostRackBaseExample example);

    int deleteByExample(CostRackBaseExample example);

    int deleteByPrimaryKey(String costRackId);

    int insert(CostRackBase record);

    int insertSelective(CostRackBase record);

    List<CostRackBase> selectByExample(CostRackBaseExample example);

    CostRackBase selectByPrimaryKey(String costRackId);

    int updateByExampleSelective(@Param("record") CostRackBase record, @Param("example") CostRackBaseExample example);

    int updateByExample(@Param("record") CostRackBase record, @Param("example") CostRackBaseExample example);

    int updateByPrimaryKeySelective(CostRackBase record);

    int updateByPrimaryKey(CostRackBase record);
}